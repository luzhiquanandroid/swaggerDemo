package com.fotron.draw.bean.resp;

import lombok.Data;

/**
 * @author: niuhuan
 * @createDate: 2018/11/23
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class FalsehoodListResp {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String headImage;
    /**
     * 充值金额
     */
    private Integer amount;
}
