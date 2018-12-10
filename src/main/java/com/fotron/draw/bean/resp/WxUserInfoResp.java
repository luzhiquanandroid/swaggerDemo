package com.fotron.draw.bean.resp;

import lombok.Data;

/**
 * @author: yutong
 * @createDate: 2018/8/16
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class WxUserInfoResp {
    private String country;
    private Integer gender;

    private String province;

    private String city;

    private String avatarUrl;

    private String openId;

    private String nickName;
}
