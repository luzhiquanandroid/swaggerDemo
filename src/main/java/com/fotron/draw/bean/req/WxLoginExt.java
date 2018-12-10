package com.fotron.draw.bean.req;

import lombok.Data;

/**
 * @author: yutong
 * @createDate: 2018/8/15
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class WxLoginExt {

    /**
     * 客户端注册来源，1步数宝，2全民游戏榜,商城
     */
    private Integer cs;

    /**
     * 用户来源，1单聊分享，2群聊分享，3海报
     */
    private Integer s;

    /**
     * 用户id
     */
    private String friendId;
}
