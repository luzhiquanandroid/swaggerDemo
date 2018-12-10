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
 * @author luzhiquan
 * @createTime 2018/11/20 15:38
 * @description 领取表
 */
@Data
@Table(name = "dw_receive")
public class Receive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 类型  1:两小时  2:30小时随机生成
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 类型  钻石
     */
    @Column(name = "diamond")
    private Integer diamond;


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
     * 可领取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "expire_time")
    private Date expireTime;
    /**
     * 状态 1不可领取 2可以领取
     */
    @Column(name = "status")
    private Integer status;


}