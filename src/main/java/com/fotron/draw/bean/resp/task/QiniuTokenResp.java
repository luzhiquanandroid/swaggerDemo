package com.fotron.draw.bean.resp.task;

import lombok.Data;

import java.util.Date;

/**
 * @author: yutong
 * @createDate: 2018/7/19
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class QiniuTokenResp {

    private String token;

    private Date expireTime;
}
