package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:28
 * @description 抽奖参数
 */
@Data
public class LuckyReq {
    @NotEmpty(message = "用户id不能为空")
    private String userId;
}