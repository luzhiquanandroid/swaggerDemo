package com.fotron.draw.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: yutong
 * @createDate: 2018/8/7
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
@Table(name = "gb_sub_reserve_msg")
public class SubReserveMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    private String openId;

    private String formId;

    /**
     * 1:游戏榜  2:游戏榜单
     */
    private Integer boxType;
    private Integer reserveType;

    private Integer sendCount;

    private Date sendTime;

    private Date reserveTime;

    private Date createTime;

    private Date expireTime;
}
