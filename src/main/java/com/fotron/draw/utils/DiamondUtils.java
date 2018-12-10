package com.fotron.draw.utils;

import com.fotrontimes.core.web.ApiRequest;
import com.fotron.draw.bean.req.DiamondReportReq;
import com.fotron.draw.bean.req.GetDiamondReq;
import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.core.config.VerificationClient;
import com.fotron.draw.entity.DiamondRecord;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.DiamondRecordMapper;
import com.fotron.draw.mapper.UserMapper;
import com.fotrontimes.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/11/21
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 钻石工具类
 */
@Component
public class DiamondUtils {

    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;

    @Resource
    private DiamondRecordMapper diamondRecordMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 上报游戏榜钻石消耗/新增
     *
     * @param dealAction
     * @param type
     * @param diamond
     * @param unionId
     * @param dealRefId  中奖纪录id
     * @return
     */
    public ApiRequest<DiamondReportReq> reportDiamond(Integer dealAction, Integer type, Long diamond,
                                                      String unionId, Integer dealRefId) {
        DiamondReportReq diamondRecord = new DiamondReportReq();
        diamondRecord.setDealAction(dealAction);
        diamondRecord.setType(type);
        diamondRecord.setDiamond(diamond);
        diamondRecord.setUnionId(unionId);
        diamondRecord.setDealRefId(dealRefId);
        ApiRequest<DiamondReportReq> apiRequest = new ApiRequest<>();
        apiRequest.setData(diamondRecord);
        apiRequest.setApp(appId);
        apiRequest.setSig(sign(apiRequest));
        return apiRequest;
    }

    /**
     * 获取游戏榜钻石
     *
     * @param unionId
     * @return
     */
    public ApiRequest<GetDiamondReq> getDiamond(String unionId) {
        GetDiamondReq getDiamondReq = new GetDiamondReq();
        getDiamondReq.setUnionId(unionId);
        ApiRequest<GetDiamondReq> apiRequest = new ApiRequest<>();
        apiRequest.setData(getDiamondReq);
        return apiRequest;
    }

    private String sign(ApiRequest<DiamondReportReq> request) {
        return VerificationClient.<DiamondReportReq>build(appId, secret).generate(request);
    }


    /**
     * 新增钻石记录 更新所有钻石数量 数量无限制
     *
     * @param user   用户对象
     * @param action 交易动作
     * @param type   新增还是减少
     */
    public void updateDiamond(User user, int action, int type, long amount) {
        //存入钻石记录
        DiamondRecord diamondRecordUp = new DiamondRecord();
        diamondRecordUp.setUserId(user.getUserId());
        diamondRecordUp.setType(type);
        //交易动作 1抽好运小程序幸运奖  2抽好运小程序钻石奖  3赚好运抽奖消耗
        diamondRecordUp.setCreateTime(new Date());
        diamondRecordUp.setAction(action);
        diamondRecordUp.setUpdateTime(new Date());
        diamondRecordUp.setUserId(user.getUserId());
        diamondRecordUp.setAmount(amount);
        this.diamondRecordMapper.insertSelective(diamondRecordUp);
        //总钻石添加
        int rows = this.userMapper.updateDiamondByUserId(user.getUserId(), user.getDiamond() + amount,
                user.getDiamond(), new Date());
        if (rows <= 0) {
            throw new BusinessException(MyErrorCode.UPDATE_AMOUNT_FAIL);
        }

        this.getLevel(user.getUserId());
    }

    @Async
    public void getLevel(String userId) {
        //返回用户的等级
        int level = 1;
        int diamondCount = this.diamondRecordMapper.selectDiamondCount(userId);
        if (diamondCount >= Constant.LEVEL.LEVEL_COUNT) {
            level = level + diamondCount / Constant.LEVEL.LEVEL_COUNT;
        }
        User user = new User();
        user.setLevel(level);
        this.userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 新增钻石记录 更新所有钻石数量 数量限制100
     *
     * @param user   用户对象
     * @param action 交易动作
     * @param type   新增还是减少
     */
    public void updateDiamondMax(User user, int action, int type, long amount) {
        //存入钻石记录
        DiamondRecord diamondRecordUp = new DiamondRecord();
        diamondRecordUp.setUserId(user.getUserId());
        diamondRecordUp.setType(type);
        //交易动作 1抽好运小程序幸运奖  2抽好运小程序钻石奖  3赚好运抽奖消耗
        diamondRecordUp.setCreateTime(new Date());
        diamondRecordUp.setAction(action);
        diamondRecordUp.setUpdateTime(new Date());
        diamondRecordUp.setUserId(user.getUserId());
        diamondRecordUp.setAmount(amount);
        this.diamondRecordMapper.insertSelective(diamondRecordUp);
        //总钻石添加 大于100设置为100 小于 就是当前钻石数量
        long afterDiamond = (user.getDiamond() + amount) > Constant.DIAMOND_UPORDOWN.DIAMOND_ALL
                ? Constant.DIAMOND_UPORDOWN.DIAMOND_ALL : (user.getDiamond() + amount);
        this.userMapper.updateDiamondByUserId(user.getUserId(), afterDiamond, user.getDiamond(), new Date());
    }

}
