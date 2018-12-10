package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: houshiyu
 * @createDate: 2018/11/6
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 任务记录
 */
@Data
@Table(name = "dw_task_log")
public class TaskLog {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 任务ID
     */
    private Integer taskId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 状态：0未完成 1已完成
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "finish_time")
    private Date finishTime;
}
