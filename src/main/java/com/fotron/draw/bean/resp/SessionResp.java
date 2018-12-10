package com.fotron.draw.bean.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author: niuhuan
 * @createDate: 2018/11/6
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SessionResp {
    private String openid;

    @JsonProperty(value = "session_key")
    private String sessionKey;

    private String unionid;
}
