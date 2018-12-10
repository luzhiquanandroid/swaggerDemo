package com.fotron.draw.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/12/7
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 集中跳转图片
 */
@Data
public class TranSport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 任务id
     */
    private String taskId;

    /**
     * 跳转中间显示图片
     */
    private String transportImg;


    /**
     * 用户状态    1:正常  2:无效
     */
    private Integer status;


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;


}
