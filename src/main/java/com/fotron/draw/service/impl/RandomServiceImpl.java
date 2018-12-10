package com.fotron.draw.service.impl;

import com.fotrontimes.utils.verification.annotation.SignVerification;
import com.fotron.draw.bean.req.LuckyReq;
import com.fotron.draw.bean.req.OpenReq;
import com.fotron.draw.bean.resp.LuckyResp;
import com.fotron.draw.bean.resp.OpenResp;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.*;
import com.fotron.draw.mapper.*;
import com.fotron.draw.service.RandomService;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.RandomUtil;
import com.fotron.draw.utils.SaveAllRecord;
import com.fotrontimes.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:35
 * @description
 */
@Service
@Slf4j
public class RandomServiceImpl implements RandomService {
    @Resource
    private AllRecordMapper allRecordMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private TelephoneFareMapper telephoneFareMapper;
    @Resource
    private RestTemplate restTemplate;
    @Value("${diamond.diamondReportUrl}")
    private String diamondReportUrl;

    @Value("${diamond.diamondUrl}")
    private String diamondUrl;

    @Resource
    private DiamondRecordMapper diamondRecordMapper;
    @Resource
    private GameMapper gameMapper;

    @Resource
    private SaveAllRecord saveAllRecord;

    @Resource
    private RandomUtil randomUtil;

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public LuckyResp luck(LuckyReq luckyReq) {
        Date now = new Date();
        //判断是否存在用户  随机出现抽奖类型 存入所有记录表 默认是未开启
        AllRecord allRecord = new AllRecord();
        LuckyResp resp = new LuckyResp();
        User user = this.userMapper.selectByUserId(luckyReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        //获取钻石
        Long allDiamond = user.getDiamond();
        //钻石不足
        if (allDiamond < Constant.DIAMOND_UPORDOWN.DIAMOND_DOWN) {
            throw new BusinessException(MyErrorCode.DIAMOND_NOT_ENOUGH);
        }
        //进行钻石的消耗
        DiamondRecord diamondRecord = new DiamondRecord();
        diamondRecord.setUserId(user.getUserId());
        diamondRecord.setType(Constant.DIAMOND_TYPE.DOWN);
        diamondRecord.setCreateTime(now);
        //交易动作 1抽好运小程序幸运奖  2抽好运小程序钻石奖  3赚好运抽奖消耗
        diamondRecord.setUpdateTime(now);
        diamondRecord.setAction(Constant.THREE);
        diamondRecord.setAmount(Constant.DIAMOND_UPORDOWN.DIAMOND_DOWN);
        this.diamondRecordMapper.insertSelective(diamondRecord);
        //总钻石的修改
        this.userMapper.updateDiamondByUserId(user.getUserId(),
                user.getDiamond() - Constant.DIAMOND_UPORDOWN.DIAMOND_DOWN, user.getDiamond(), new Date());
        int type = this.randomUtil.randomType();
        user.setDiamond(user.getDiamond() - Constant.DIAMOND_UPORDOWN.DIAMOND_DOWN);

        //1:助力话费 2:宝箱  3:随机红包  4:钻石  5:幸运奖
        allRecord.setUserId(luckyReq.getUserId());
        allRecord.setIsOpen(Constant.LUCK_IS_OPEN.NOT_OPEN);
        allRecord.setCreateTime(now);
        allRecord.setUpdateTime(now);
        allRecord.setType(type);
        int rows = this.allRecordMapper.insertSelective(allRecord);
        log.info("get before id----" + allRecord.getId());
        if (rows > 0) {
            //插入成功
            log.info("get after id----" + allRecord.getId());
            resp.setId(allRecord.getId());
            resp.setType(type);
            if (type == Constant.LUCK_TYPE.FOUR) {
                //如果转到钻石，直接开，进行添加上报
                allRecord.setIsOpen(Constant.LUCK_IS_OPEN.OPEN);
                allRecord.setUpdateTime(now);
                this.allRecordMapper.updateByPrimaryKeySelective(allRecord);
                DiamondRecord diamondRecordUp = new DiamondRecord();
                diamondRecordUp.setUserId(user.getUserId());
                diamondRecordUp.setType(Constant.DIAMOND_TYPE.UP);
                //交易动作 1抽好运小程序幸运奖  2抽好运小程序钻石奖  3赚好运抽奖消耗
                diamondRecordUp.setCreateTime(now);
                diamondRecordUp.setAction(Constant.TWO);
                diamondRecordUp.setUpdateTime(now);
                diamondRecordUp.setRecordId(allRecord.getId());
                diamondRecordUp.setAmount(Constant.DIAMOND_UPORDOWN.DIAMOND_UP);
                this.diamondRecordMapper.insertSelective(diamondRecordUp);
                //总钻石添加 大于100设置为100 小于 就是当前钻石数量
                //1.0.1需求钻石数量无上线
//                long afterDiamond = (user.getDiamond() + Constant.DIAMOND_UPORDOWN.DIAMOND_UP) > Constant.DIAMOND_UPORDOWN.DIAMOND_ALL
//                        ? Constant.DIAMOND_UPORDOWN.DIAMOND_ALL : (user.getDiamond() + Constant.DIAMOND_UPORDOWN.DIAMOND_UP);
                long afterDiamond = user.getDiamond() + Constant.DIAMOND_UPORDOWN.DIAMOND_UP;
                int i = this.userMapper.updateDiamondByUserId(user.getUserId(), afterDiamond, user.getDiamond(), new Date());
                if (i > 0) {
                    resp.setId(allRecord.getId());
                    resp.setType(type);
                    resp.setDiamondNum((int) Constant.DIAMOND_UPORDOWN.DIAMOND_UP);
                } else {
                    log.error("更新钻石失败");
                    throw new BusinessException(MyErrorCode.UPDATE_AMOUNT_FAIL);
                }
            }
        }
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public OpenResp open(OpenReq openReq) {
        //查询id是否存在 根据类型进行开除的奖  加入话费记录表  修改所有记录表的is_open状态，更新时间
        //1:助力话费 2:宝箱  3:随机红包  4:钻石  5:幸运奖
        Date nowDate = new Date();
        OpenResp openResp = new OpenResp();
        User user = this.userMapper.selectByUserId(openReq.getUserId());
        if (user == null) {
            throw new BusinessException(MyErrorCode.USER_IS_NULL);
        }
        int type = openReq.getType();
        if (type == Constant.LUCK_TYPE.THREE) {
            //开出随机话费
            //如果记录已经有了记录，说明已经开过了，不能继续开
            TelephoneFare tel = this.telephoneFareMapper.selectByRecordId(openReq.getRecordId());
            if (tel != null) {
                throw new BusinessException(MyErrorCode.RANDOM_TEL_OPEN);
            }
            BigDecimal randomDecimals = RandomUtil.randomDecimals();
            TelephoneFare telephoneFare = new TelephoneFare();
            telephoneFare.setUserId(openReq.getUserId());
            telephoneFare.setRecordId(openReq.getRecordId());
            telephoneFare.setTelephoneFare(randomDecimals);
            telephoneFare.setCreateTime(nowDate);
            telephoneFare.setUpdateTime(nowDate);
            telephoneFare.setType(Constant.TELPHONE_FARE_TYPE.RANDMON_PHONE);
            int rows = this.telephoneFareMapper.insertSelective(telephoneFare);
            if (rows > 0) {
                //修改记录开状态
                this.saveAllRecord.saveRecord(openReq.getRecordId(), Constant.LUCK_IS_OPEN.OPEN);
            }
            //进行话费金额的添加
            BigDecimal beforeTelphoneFare = user.getTelephoneFare();
            BigDecimal afterTelphoneFare = beforeTelphoneFare.add(randomDecimals);
            if (afterTelphoneFare.compareTo(Constant.INVITATION_MONEY.NINETY_NINE) > 0) {
                afterTelphoneFare = Constant.INVITATION_MONEY.NINETY_NINE;
                //当计算话费超过了99元，抛出异常提醒用户，先提现 在进行领取
            }
            int i = this.userMapper.updateTelephoneFareById(openReq.getUserId(), afterTelphoneFare, beforeTelphoneFare, new Date());
            if (i > 0) {
                openResp.setMount(randomDecimals);
                openResp.setDiamondNum(0);
            }
        } else if (type == Constant.LUCK_TYPE.FIVE) {
            //幸运奖  跳转了小程序游戏
            AllRecord allRecord = this.allRecordMapper.selectById(openReq.getRecordId());
            allRecord.setUpdateTime(nowDate);
            allRecord.setIsOpen(Constant.LUCK_IS_OPEN.OPEN);
            this.allRecordMapper.updateByPrimaryKeySelective(allRecord);
            //总钻石新增  抽到幸运奖进入小程序 进行钻石的修改（1.0.1未定需求）
//            DiamondRecord diamondRecordUp = new DiamondRecord();
//            diamondRecordUp.setUserId(user.getUserId());
//            diamondRecordUp.setType(Constant.DIAMOND_TYPE.UP);
//            //1:10分钟增加1个钻石 2抽好运小程序钻石奖  3赚好运抽奖消耗 4首页邀请好友奖励 5:两个小时领取奖励 6幸运奖进入小程序得钻石
//            diamondRecordUp.setAction(Constant.SIX);
//            diamondRecordUp.setRecordId(allRecord.getId());
//            diamondRecordUp.setAmount(Constant.DIAMOND_UPORDOWN.DIAMOND_UP);
//            this.diamondRecordMapper.updateByPrimaryKeySelective(diamondRecordUp);


//            int i = this.userMapper.updateDiamondByUserId(openReq.getUserId(),
//                    user.getDiamond() + Constant.DIAMOND_UPORDOWN.DIAMOND_UP,
//                    user.getDiamond(), new Date());
//            if (i > 0) {
//                openResp.setMount(BigDecimal.valueOf(0));
//                openResp.setDiamondNum((int) Constant.DIAMOND_UPORDOWN.DIAMOND_UP);
//            } else {
//                log.error("更新钻石失败");
//                throw new BusinessException(MyErrorCode.UPDATE_AMOUNT_FAIL);
//            }
        } else if (type == Constant.LUCK_TYPE.SIX) {
            //电商奖 修改状态
            AllRecord allRecord = this.allRecordMapper.selectById(openReq.getRecordId());
            allRecord.setUpdateTime(nowDate);
            allRecord.setIsOpen(Constant.LUCK_IS_OPEN.OPEN);
            this.allRecordMapper.updateByPrimaryKeySelective(allRecord);
        }
        return openResp;
    }

    @Override
    public Game game() {
        Game game = this.gameMapper.selectRandomOne();
        return game;
    }

    @Override
    public Goods good() {
        Goods goods = this.goodsMapper.selectByRandomOne();
        return goods;
    }
}