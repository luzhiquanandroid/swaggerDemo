package com.fotron.draw.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/11/07
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
@Table(name = "dep_user")
public class FalsehoodUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "open_id")
    private String openId;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户来源  2:分享
     */
    private Integer source;

    /**
     * 头像
     */
    private String headImage;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
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
