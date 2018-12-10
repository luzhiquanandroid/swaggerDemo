package com.fotron.draw.service;

import com.fotron.draw.bean.req.DiamondListReq;

import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
public interface DiamondService {
    /**
     * 获取用户获取的钻石列表
     *
     * @param data
     * @return
     */
    Map diamond(DiamondListReq data);
}
