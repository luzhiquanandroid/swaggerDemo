package com.fotron.draw.bean.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: yutong
 * @createDate: 2018/7/3
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessTokenResp {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private int expiresIn;

    @JsonInclude
    private Integer errcode;

    private String errmsg;
}
