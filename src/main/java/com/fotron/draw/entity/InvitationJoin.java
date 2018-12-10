package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 16:50
 * @description 加入实体
 */
@Data
@Table(name = "dw_invitation_join")
public class InvitationJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 1:助力话费/宝箱邀请 2:,首页邀请
     */
    private Integer type;
    /**
     * 邀请id，关联邀请表
     */
    @Column(name = "invitation_id")
    private Integer invitationId;
    /**
     * 加入的用户id
     */
    @Column(name = "join_user_id")
    private String joinUserId;

    /**
     * 是否授权
     */
    @Column(name = "authorize_flag")
    private Integer authorizeFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 用户图像
     */
    @Transient
    private String headImage;
    /**
     * 用户昵称
     */
    @Transient
    private String nickName;
}