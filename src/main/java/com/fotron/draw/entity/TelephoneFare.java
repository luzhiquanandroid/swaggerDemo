package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:38
 * @description 抽话费记录
 */
@Data
@Table(name = "dw_telephone_fare")
public class TelephoneFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 记录id
     */
    @Column(name = "record_id")
    private Integer recordId;
    /**
     * 话费
     */
    @Column(name = "telephone_fare")
    private BigDecimal telephoneFare;
    /**
     * 类型 1:新增 2:消耗
     */
    private Integer type;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "update_time")
    private Date updateTime;


}