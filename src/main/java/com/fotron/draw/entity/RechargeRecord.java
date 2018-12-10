package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:38
 * @description 充值记录
 */
@Data
@Table(name = "dw_recharge_record")
public class RechargeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * 是否成功 0:充值中,1充值成功，2:充值失败
     */
    private Integer status;
    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;
    /**
     * 充值话费
     */
    private String phone;

    /**
     * 充值金额
     */
    private Integer amount;

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