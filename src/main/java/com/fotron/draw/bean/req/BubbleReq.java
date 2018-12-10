package com.fotron.draw.bean.req;


import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/12/10
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 戳气泡领取奖励钻石请求参数
 */
@Data
public class BubbleReq {
    @NotNull(message = "必要参数为空")
    private Integer receiveId;
    @NotNull(message = "用户参数为空")
    private String userId;


}
