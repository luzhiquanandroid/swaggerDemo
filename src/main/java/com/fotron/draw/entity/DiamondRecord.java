package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "dw_diamond_record")
public class DiamondRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 消耗类型  1:新增 2:消耗
     */
    private Integer type;
    /**
     * 钻石金额
     */
    private Long amount;
    /**
     * 1:10分钟增加1个钻石 2抽好运小程序钻石奖  3赚好运抽奖消耗   4两小时可以领取钻石
     */
    private Integer action;
    /**
     * 交易关联id
     */
    private Integer recordId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 木有等级版本的时候为1  有等级版本的为2
     */
    private Integer levelType;

}