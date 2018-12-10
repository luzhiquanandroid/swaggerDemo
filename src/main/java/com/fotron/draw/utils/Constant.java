package com.fotron.draw.utils;

import java.math.BigDecimal;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:56
 * @description
 */
public interface Constant {

    int YES = 1;
    int NO = 2;
    int THREE = 3;
    int SIX = 6;
    long BASE_LARGESS = 100L;
    /**
     * 钻石新增
     */
    int ONE = 1;
    /**
     * 钻石消耗
     */
    int TWO = 2;
    /**
     * 赚好运抽奖消耗
     */
    int FIVE = 5;
    /**
     * 赚好运小程序钻石奖  2小时领取钻石
     */
    int FOUR = 4;

    /**
     * 加入小程序
     */
    int SEVEN = 7;
    /**
     * 公众号
     */
    int EIGHT = 8;
    /**
     * 浏览游戏
     */
    int NINE = 9;
    /**
     * 签到
     */
    int TEN = 10;

    int ELEVEN = 11;

    /**
     * 微信的默认图片
     */
    String HEAD_IMAGE = "https://rr.bushubao.cn/step_trade/game/game_20180827201414.png";

    interface LUCK_IS_OPEN {
        /**
         * 开启
         */
        int OPEN = 1;
        /**
         * 未开启
         */
        int NOT_OPEN = -1;
    }

    interface DIAMOND_TYPE {
        /**
         * 新增
         */
        int UP = 1;
        /**
         * 消耗
         */
        int DOWN = 2;
    }

    interface TELPHONE_FARE_TYPE {
        /**
         * 随机话费
         */
        int RANDMON_PHONE = 1;
        /**
         * 提取话费
         */
        int EXTRACT_PHONE = 2;
        /**
         * 助力话费
         */
        int HELP_PHONE = 3;
        /**
         * 助力宝箱
         */
        int HELP_TREASURE = 4;
    }

    interface INVITATION_STATUS {
        //0:未开启  1:已开启正在助力  2:助力完成  3:助力失效
        /**
         * 未开启
         */
        int NOT_OPEN = 0;
        /**
         * 正在助力
         */
        int INVITION_ING = 1;
        /**
         * 助力完成
         */
        int HELP_COMPLETE = 2;
        /**
         * 助力失效
         */
        int HELP_OUT_TIME = 3;
        /**
         * 已经领取
         */
        int HELP_RECEIVED = 4;
    }

    interface INVITATION_NUMBER {
        /**
         * 3人
         */
        int THREE_NUM = 3;
        /**
         * 6人
         */
        int SIX_NUM = 6;
    }

    interface INVITATION_MONEY {
        /**
         * 1元
         */
        BigDecimal ONE = BigDecimal.valueOf(1.0000);
        /**
         * 3元
         */
        BigDecimal THREE = BigDecimal.valueOf(2.0000);

        /**
         * 99元
         */
        BigDecimal NINETY_NINE = BigDecimal.valueOf(99.0000);
    }

    interface DIAMOND_UPORDOWN {
        /**
         * 消耗五个钻石
         */
        long DIAMOND_DOWN = 5L;
        /**
         * 新增十个钻石
         */
        long DIAMOND_UP = 10L;
        /**
         * 总钻石100个
         */
        long DIAMOND_ALL = 100L;
        /**
         * 两个小时领取12个钻石
         */
        long DIAMOND_RECEIVE = 12L;
    }

    /**
     * 抽中的类型 1:助力话费  2:宝箱  3:随机话费  4:钻石  5:幸运奖
     */
    interface LUCK_TYPE {
        /**
         * 助力话费
         */
        int ONE = 1;
        /**
         * 助力宝箱
         */
        int TWO = 2;
        /**
         * 随机话费
         */
        int THREE = 3;
        /**
         * 钻石
         */
        int FOUR = 4;
        /**
         * 幸运奖
         */
        int FIVE = 5;
        /**
         * 电商奖
         */
        int SIX = 6;
    }

    interface PRO {
        /**
         * 限制额度
         */
        String LIMIT_AMOUNT = "LIMIT_AMOUNT";
        /**
         * 助力话费
         */
        String PRO_ONE = "PRO_ONE";
        /**
         * 助力宝箱
         */
        String PRO_TWO = "PRO_TWO";
        /**
         * 随机话费
         */
        String PRO_THREE = "PRO_THREE";
        /**
         * 钻石
         */
        String PRO_FOUR = "PRO_FOUR";
        /**
         * 幸运奖
         */
        String PRO_FIVE = "PRO_FIVE";
        /**
         * 电商奖
         */
        String PRO_SIX = "PRO_SIX";
    }

    interface RECEIVE {
        /**
         * 不可领取
         */
        int NOT_CAN = 1;
        /**
         * 可以领取
         */
        int CAN = 2;

        /**
         * 未领取
         */
        int NOT_RECEIVED = 3;
        /**
         * 已经领取
         */
        int RECEIVED = 4;
    }

    interface TASKTYPE {
        /**
         * 加入我的小程序
         */
        int JOIN_IN = 1;
        /**
         * 关注公众号
         */
        int PUBLIC_GAME = 2;
        /**
         * 体验小程序
         */
        int SMALL_GAME = 3;
        /**
         * 签到
         */
        int SIGN_IN = 4;
    }

    interface TASKSTATUS {
        /**
         * 未完成
         */
        int TASK_NOT_COMPILED = 0;
        /**
         * 已经完成
         */
        int TASK_COMPILED = 1;
    }

    interface SIGN_DIAMOND {
        /**
         * 第一天签到
         */
        int SIGN_FIRST = 5;
        /**
         * 第二天签到
         */
        int SIGN_TWO = 15;
        /**
         * 第三天签到
         */
        int SIGN_THREE = 15;
        /**
         * 第四天签到
         */
        int SIGN_FOUR = 20;
        /**
         * 第五天签到
         */
        int SIGN_FIVE = 20;
        /**
         * 第六天签到
         */
        int SIGN_SIX = 20;
        /**
         * 第七天签到
         */
        int SIGN_SEVEN = 50;
    }

    interface SIGN_ALL_DAYS {
        /**
         * 签到周期总计为7天
         */
        int SEVEN = 7;
    }

    interface TASK_SIGN_ID {
        /**
         * 签到id
         */
        int ID = 3;
    }

    interface LEVEL {
        /**
         * 等级基数
         */
        int LEVEL_COUNT = 200;

    }
}
