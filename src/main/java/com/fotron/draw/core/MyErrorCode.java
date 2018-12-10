package com.fotron.draw.core;

import com.fotrontimes.core.exception.ErrorCode;

/*************************
 @Description: 错误码
 @Author: 牛欢
 @CreateDate: 2018/6/26 18:08
 @since: JDK 1.8
 @company: (C) Copyright fotron
 ************************/
public interface MyErrorCode {
    ErrorCode WX_CODE_INVALID = ErrorCode.of("WX_CODE_INVALID", "授权code失效");
    ErrorCode USER_IS_NULL = ErrorCode.of("USER_IS_NULL", "没有找到此用户");
    ErrorCode OLD_USER = ErrorCode.of("OLD_USER", "您已经是老用户了");
    ErrorCode ASSISTANCE_FAIL = ErrorCode.of("ASSISTANCE_FAIL", "助力已经失效");
    ErrorCode EXCHANGE_FAIL = ErrorCode.of("EXCHANGE_FAIL", "兑换失败");
    ErrorCode CONCURRENCE_ERROR = ErrorCode.of("CONCURRENCE_ERROR", "并发异常");
    ErrorCode SESSION_KEY_INVALID = ErrorCode.of("SESSION_KEY_INVALID", "微信sessionKey失效");
    ErrorCode REPORT_FAIL = ErrorCode.of("REPORT_FAIL", "上报钻石异常");
    ErrorCode DIAMOND_NOT_ENOUGH = ErrorCode.of("DIAMOND_NOT_ENOUGH", "钻石不足");
    ErrorCode AMOUNT_NOT_ENOUGH = ErrorCode.of("AMOUNT_NOT_ENOUGH", "话费余额不足");
    ErrorCode REPEAT_OPERATE = ErrorCode.of("REPEAT_OPERATE", "重复操作");
    ErrorCode UPDATE_AMOUNT_FAIL = ErrorCode.of("UPDATE_AMOUNT_FAIL", "更新资产失败");
    ErrorCode RECEIVED = ErrorCode.of("RECEIVED", "您已经领取过");
    ErrorCode RANDOM_TEL_OPEN = ErrorCode.of("RANDOM_TEL_OPEN", "您已开过随机话费");
    ErrorCode USER_EXIST = ErrorCode.of("USER_EXIST", "用户已经存在");
    ErrorCode IT_DONE = ErrorCode.of("IT_DONE", "已有人帮助好友拆开了哦");
    ErrorCode HELP_REPEAT = ErrorCode.of("HELP_REPEAT", "您已经助力过这个分享了");
    ErrorCode ME_TO_ME = ErrorCode.of("ME_TO_ME", "自己不能帮助自己拆开哦！");
    ErrorCode TASK_UNDERCARRIAGE = ErrorCode.of("TASK_UNDERCARRIAGE", "任务已下架");
    ErrorCode TASK_FIRST = ErrorCode.of("TASK_FIRST", "任务第一步没完成");
    ErrorCode TASK_FINISH = ErrorCode.of("TASK_FINISH", "任务已经完成");


    ErrorCode PHONE_MORE_NINTYNINE = ErrorCode.of("PHONE_MORE_NINTYNINE", "话费超过了99元，请提现后进行操作");
    ErrorCode RECEIVED_ERROR = ErrorCode.of("RECEIVED_ERROR", "领取失败");
    ErrorCode RECEIVED_GET_ERROR = ErrorCode.of("RECEIVED_GET_ERROR", "获取信息异常");
    ErrorCode RECEIVED_NOT_CAN_ERROR = ErrorCode.of("RECEIVED_NOT_CAN_ERROR", "不可以领取");
    ErrorCode REPEAT_TO_RECEIVE = ErrorCode.of("REPEAT_TO_RECEIVE", "任务不能重复创建");
    ErrorCode SIGN_DAYS_ERROR = ErrorCode.of("SIGN_DAYS_ERROR", "签到天数异常");
    ErrorCode SIGNED_DAYS_ERROR = ErrorCode.of("SIGNED_DAYS_ERROR", "今日已经签到过");
    ErrorCode TASK_IS_NULL = ErrorCode.of("TASK_IS_NULL", "任务不存在");
    ErrorCode BUBBLE_IS_NULL = ErrorCode.of("BUBBLE_IS_NULL", "气泡不存在");
    ErrorCode BUBBLE_REPEAT_RECEIVE = ErrorCode.of("BUBBLE_REPEAT_RECEIVE", "气泡重复领取");
    ErrorCode RECEIVED_NOT=ErrorCode.of("RECEIVED_NOT","已到48个上线，无法产生钻石");
}
