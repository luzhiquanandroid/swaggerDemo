package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:38
 * @description 所有中奖记录
 */
@Data
@Table(name = "dw_all_record")
public class AllRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 是否打开
     */
    @Column(name = "is_open")
    private Integer isOpen;
    /**
     * 抽中的类型 1:助力话费 2:宝箱  3:随机红包  4:钻石  5:幸运奖
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
    @Column(name = "update_Time")
    private Date updateTime;
}