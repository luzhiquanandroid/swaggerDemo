package com.fotron.draw.bean.req.task;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/12/3
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class FinishTaskReq {
    @NotEmpty(message = "用户id不能为空")
    private String userId;
    /**
     * 1添加小程序  2:关注公众号
     */
    @NotNull(message = "任务type不能为空")
    private Integer taskType;



}
