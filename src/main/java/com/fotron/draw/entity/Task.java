package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 任务
 */
@lombok.Data
@Table(name = "dw_task")
public class Task {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 描述
     */
    @Column(name = "desc")
    private String desc;

    /**
     * 图标url
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 任务类型：
     * 1加入我的小程序
     * 2关注公众号
     */
    @Column(name = "task_type")
    private Integer taskType;

    /**
     * 奖励值
     */
    @Column(name = "award_value")
    private Integer awardValue;

    /**
     * 顺序
     */
    @Column(name = "sort_order")
    private Integer sortOrder;


    /**
     * 是否删除：1正常 1失效
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 小程序进入时间
     */
    @Column(name = "time")
    private Integer time;

    private String appId;
    private String gameLink;
}