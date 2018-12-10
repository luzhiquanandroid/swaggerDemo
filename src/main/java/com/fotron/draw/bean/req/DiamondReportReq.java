package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/11/20
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 钻石上报
 */
@Data
public class DiamondReportReq {
    @NotNull(message = "钻石不能为空")
    private Long diamond;
    @NotEmpty(message = "unionId不能为空")
    private String unionId;
    @NotNull(message = "上报type不能为空")
    private Integer type;
    /**
     * 交易动作  3:抽好运小程序幸运奖  4:抽好运小程序钻石奖'  5:抽奖消耗
     */
    @NotNull(message = "dealAction不能为空")
    private Integer dealAction;
    /**
     * 关联id
     */
    private  Integer dealRefId;
}
