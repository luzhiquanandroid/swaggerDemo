package com.fotron.draw.utils;

import com.fotron.draw.mapper.ConfigMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Random;

import static com.fotron.draw.utils.Constant.PRO.*;

/**
 * @author luzhiquan
 * @createTime 2018/11/19 18:22
 * @description
 */
@Component
public class RandomUtil {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ConfigMapper configMapper;

    /**
     * 抽奖操作  添加商城项目
     *
     * @return 抽到的类型
     */
    public int randomType() {
        int type = 5;
        Random r = new Random();
        //生成0-1间的随机数
        Double d = r.nextDouble() * 100;
        while (d == 0d) {
            //防止生成0.0
            d = r.nextDouble() * 100;
        }
        //抽中的类型 1:助力话费8%  2:宝箱5%  3:随机话费46%  4:钻石8%  5:幸运奖24% 6电商9%
        int number = d.intValue();
        if (number >= 0 && number < 46) {
            return getRedisType(PRO_THREE);
        } else if (number >= 46 && number < 70) {
            return getRedisType(PRO_FIVE);
        } else if (number >= 70 && number < 78) {
            return getRedisType(PRO_FOUR);
        } else if (number >= 78 && number < 86) {
            return getRedisType(PRO_ONE);
        } else if (number >= 86 && number < 91) {
            return getRedisType(PRO_TWO);
        } else if (number >= 91 && number < 100) {
            return getRedisType(PRO_SIX);
        }
        return type;
    }

    private int getRedisType(String proType) {
        String type = this.redisUtil.get(proType);
        if (type == null) {
            String configValue = this.configMapper.selectValueByKey(proType).getConfigValue();
            this.redisUtil.set(proType, configValue);
            return Integer.parseInt(configValue);
        } else {
            return Integer.parseInt(type);
        }
    }

    /**
     * 抽到的话费 随机话费10到29之间 除以10000 保留4位精度
     *
     * @return
     */
    public static BigDecimal randomDecimals() {
        int random = com.xiaoleilu.hutool.util.RandomUtil.randomInt(10, 29);
        return BigDecimal.valueOf(random).divide(BigDecimal.valueOf(10000)).setScale(4);
    }

    /**
     * 每半小时随机产生的钻石数量 1到3
     *
     * @return
     */
    public static int randmonDiamond() {
        return com.xiaoleilu.hutool.util.RandomUtil.randomInt(1, 4);
    }
}