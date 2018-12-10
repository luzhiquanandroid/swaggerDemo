package com.fotron.draw.bean.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fotron.draw.bean.validation.WxLoginValid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author: niuhuan
 * @createDate: 2018/8/10
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WxLoginReq {
    @NotEmpty(message = "未获取到code")
    private String code;

    private String rawData;

    private String signature;

    private String encryptedData;

    private String iv;
    /**
     * 用户来源
     */
    private Integer source;


    /**
     * 邀请人id
     */
    private String friendId;
    /**
     * 首页邀请用户id
     */
    private String friendHome;

    private Integer cs;
    /**
     * 邀请连接id
     */
    private Integer refId;

    /**
     * 登录类型
     */
    @NotNull(groups = {WxLoginValid.class})
    private WxLoginExt loginExt;


}
