package com.fotron.draw.bean.req.task;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/12/1 13:52
 * @description 做任务请求参数
 */
@Data
public class DoTaskReq {
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    @NotNull(message = "任务id不能为空")
    private Integer taskId;

    @NotNull(message = "任务type不能为空")
    private Integer taskType;

}