package com.fotron.draw.service;

import com.fotron.draw.bean.req.SubReserveMsgReq; /**
 * @author: niuhuan
 * @createDate: 2018/12/1
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 微信模板消息
 */
public interface SubReserveMsgService {
    /**
     *
     * @param data
     */
    void report(SubReserveMsgReq data);
}
