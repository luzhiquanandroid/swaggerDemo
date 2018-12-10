package com.fotron.draw.core.config;

import com.fotron.draw.core.MyErrorCode;
import com.fotron.draw.entity.Config;
import com.fotron.draw.entity.DiamondRecord;
import com.fotron.draw.entity.Receive;
import com.fotron.draw.entity.User;
import com.fotron.draw.mapper.*;
import com.fotron.draw.utils.Constant;
import com.fotron.draw.utils.RandomUtil;
import com.fotron.draw.utils.RedisUtil;
import com.fotrontimes.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.fotron.draw.utils.Constant.FIVE;
import static com.fotron.draw.utils.Constant.FOUR;
import static com.fotron.draw.utils.Constant.PRO.*;
import static com.fotron.draw.utils.Constant.SIX;

/*************************
 @Description: 任务定时10分钟为用户+1钻石
 @Author: 牛欢
 @CreateDate: 2018/11/22
 @since: JDK 1.8
 @company: (C) Copyright fotron
 ************************/
@Component
@Slf4j
public class SchedulerTask {

    @Resource
    private UserMapper userMapper;
    @Resource
    private DiamondRecordMapper diamondRecordMapper;
    @Resource
    private TelephoneFareMapper telephoneFareMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ConfigMapper configMapper;

    @Resource
    private ReceiveMapper receiveMapper;

    /**
     * 每三十分钟 产生钻石  一天产生四十八个  如果未领取一直是四十八个  钻石不在产生 直到领取继续产生  领取成功，修改状态  存入钻石记录表
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void dateTask() {
        try {
            log.info("任务开启执行定时任务");
            //修改当天需要上线的任务
            Date now = new Date();
            List<User> users = this.userMapper.selectLess100Users();
            for (User user : users) {
                int i = this.receiveMapper.selectAcount(user.getUserId());
                if (i < 48) {
                    Receive receive = new Receive();
                    receive.setStatus(Constant.RECEIVE.RECEIVED);
                    receive.setUpdateTime(now);
                    //过去时间当type 类型未2不起作用
                    receive.setExpireTime(now);
                    receive.setCreateTime(now);
                    receive.setType(Constant.TWO);
                    receive.setDiamond(RandomUtil.randmonDiamond());
                    this.receiveMapper.insertSelective(receive);
                }

            }
        } catch (Exception e) {
            log.info("定时任务异常:{}", e);
            throw e;
        }

    }


    /**
     * 每30分钟检查一下当前提取的金额数额 动态的设置缓存的概率
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Transactional(rollbackFor = Exception.class, transactionManager = "primaryDataSourceTransactionManager")
    public void setAmountUpdateRedisTask() {
        try {
            log.info("概率控制任务开启执行定时任务");
            BigDecimal bigDecimal = this.telephoneFareMapper.selectAmount();
            //如果产出额度 大于 限制额度
            String limit_amount = this.redisUtil.get(Constant.PRO.LIMIT_AMOUNT);
            if (limit_amount == null) {
                Config config = this.configMapper.selectValueByKey(Constant.PRO.LIMIT_AMOUNT);
                if (config != null) {
                    limit_amount = config.getConfigValue();
                    this.redisUtil.set(Constant.PRO.LIMIT_AMOUNT, limit_amount);
                }
            }
            if (bigDecimal.compareTo(BigDecimal.valueOf(Long.parseLong(limit_amount))) > 0) {
                this.configMapper.updateValueByKey(PRO_ONE, String.valueOf(FIVE));
                this.configMapper.updateValueByKey(PRO_TWO, String.valueOf(FIVE));
                this.configMapper.updateValueByKey(PRO_THREE, String.valueOf(FIVE));
                this.configMapper.updateValueByKey(PRO_FOUR, String.valueOf(FOUR));
                this.configMapper.updateValueByKey(PRO_FIVE, String.valueOf(FIVE));
                this.configMapper.updateValueByKey(PRO_SIX, String.valueOf(SIX));
                this.redisUtil.delete(PRO_ONE);
                this.redisUtil.delete(PRO_TWO);
                this.redisUtil.delete(PRO_THREE);
                this.redisUtil.delete(PRO_FOUR);
                this.redisUtil.delete(PRO_FIVE);
                this.redisUtil.delete(PRO_SIX);
            }
        } catch (Exception e) {
            log.info("定时任务异常:{}", e);
            throw e;
        }

    }


}