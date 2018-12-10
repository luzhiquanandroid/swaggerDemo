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
import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 16:50
 * @description 邀请实体
 */
@Data
@Table(name = "dw_invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 邀请人数
     */
    @Column(name = "invitation_num")
    private Integer invitationNum;
    /**
     * 邀请类型，1:代表助力红包，2:代表宝箱
     */
    private Integer type;
    /**
     * 状态 0:未开启  1:已开启正在助力  2:助力完成  3:助力失效 4已经领取过
     */
    private Integer status;
    /**
     * 失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "expire_time")
    private Date expireTime;
    /**
     * 开启时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "open_time")
    private Date openTime;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 邀请人集合
     */
    private List<InvitationJoin> invitationJoinList;
    /**
     * 剩余时间
     */
    @Transient
    private Long remainingTime;
}