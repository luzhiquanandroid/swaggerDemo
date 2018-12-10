package com.fotron.draw.bean.resp.task;

import lombok.Data;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class TaskListResp {
    /**
     * 主键id
     */

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String desc;

    /**
     * 图标url
     */
    private String imgUrl;

    /**
     * 任务类型：
     * 1加入我的小程序
     * 2关注公众号
     */
    private Integer taskType;

    /**
     * 奖励值
     */
    private Integer awardValue;

    /**
     * 顺序
     */
    private Integer sortOrder;


    /**
     * 是否删除：1正常 1失效
     */
    private Integer status;

    /**
     * 创建时间
     */

    /**
     * 小程序进入时间
     */
    private Integer time;

    private String appId;
    private String gameLink;


    private String transportImg;
}
