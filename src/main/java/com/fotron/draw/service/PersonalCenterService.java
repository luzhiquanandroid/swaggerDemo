package com.fotron.draw.service;

import com.fotron.draw.bean.req.DetailReq;
import com.fotron.draw.bean.req.HelpPhoneReq;
import com.fotron.draw.entity.Invitation;

import java.util.Map;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 21:31
 * @description 个人中心
 */
public interface PersonalCenterService {
    Map list(HelpPhoneReq helpPhoneReq);

    Invitation detail(DetailReq detailReq);
}
