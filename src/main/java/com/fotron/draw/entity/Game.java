package com.fotron.draw.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/8/10
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Table(name = "dw_game_random")
@Data
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 游戏名称
     */
    @Column(name = "game_name")
    private String gameName;

    /**
     * 集中跳转图片
     */
    @Column(name = "transport_img")
    private String transportImg;


    /**
     * 游戏描述
     */
    @Column(name = "app_id")
    private String appId;
    /**
     * 游戏链接
     */
    @Column(name = "game_link")
    private String gameLink;
    /**
     * 游戏图片
     */
    @Column(name = "game_img")
    private String gameImg;

    /**
     * 二维码
     */
    @Column(name = "two_dimension_code")
    private String twoDimensionCode;
    /**
     * '游戏状态 1:正常 2:二维码',
     */
    @Column(name = "skip_type")
    private Integer skipType;

    /**
     * '游戏状态 1:使用中 2:未使用',
     */
    @Column(name = "status")
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "update_time")
    private Date updateTime;
}
