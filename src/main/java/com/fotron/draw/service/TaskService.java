package com.fotron.draw.service;

import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.task.DoTaskReq;
import com.fotron.draw.bean.req.task.FinishTaskReq;

import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
public interface TaskService {
    /**
     * 所有任务列表
     *
     * @param userReq
     * @return
     */
    Map allList(UserReq userReq);

    /**
     * 做任务
     *
     * @param doTaskReq
     */
    void doTask(DoTaskReq doTaskReq);

    /**
     * 做任务,添加小程序 ,关注公众号
     *
     * @param data
     */
    void finishTask(FinishTaskReq data);
}
