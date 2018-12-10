package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/12/05
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Table(name = "dw_goods")
@Data
public class Goods implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 商家二维码
     */
    private String sellerCode;

    /**
     * '状态 1:使用中 2:未使用',
     */
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 商品描述
     */
    private String goodsDes;
    /**
     * 微信号
     */
    private String wxAcount;
}
