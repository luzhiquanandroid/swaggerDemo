package com.fotron.draw.entity;

import com.fotron.draw.bean.resp.WxUserInfoResp;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:23
 * @description 用户表
 */
@Data
@Table(name = "dw_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 用户中心返回唯一id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户来源    1:正常进来的  2:分享进来的
     */
    private Integer source;

    /**
     * 用户状态    1:正常  2:无效
     */
    private Integer status;
    /**
     * 用户头像
     */
    @Column(name = "head_image")
    private String headImage;

    /**
     * 钻石数量
     */
    @Column(name = "diamond")
    private Long diamond;
    /**
     * 话费余额
     */
    @Column(name = "telephone_fare")
    private BigDecimal telephoneFare;
    /**
     * 性别，1男性，2女性，0未知
     */
    private Integer sex;
    private Integer authorizeFlag;
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

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "level")
    private Integer level;

    public User(WxUserInfoResp userInfo) {
        this.headImage = userInfo.getAvatarUrl();
        this.nickname = userInfo.getNickName();
        this.city = userInfo.getCity();
        this.country = userInfo.getCountry();
        this.province = userInfo.getProvince();
        this.sex = userInfo.getGender();
    }

    public User() {

    }

}