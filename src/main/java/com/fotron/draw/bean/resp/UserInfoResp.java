package com.fotron.draw.bean.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: yutong
 * @createDate: 2018/7/5
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class UserInfoResp implements Serializable {
    private String userId;
    private Boolean flag;
}
