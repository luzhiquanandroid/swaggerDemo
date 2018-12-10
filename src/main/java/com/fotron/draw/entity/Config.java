package com.fotron.draw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:38
 * @description 配置表
 */
@Data
@Table(name = "dw_config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 配置项名
     */
    @Column(name = "config_name")
    private String configName;
    /**
     * 配置项key
     */
    @Column(name = "config_key")
    private String configKey;

    /**
     * 配置项value
     */
    @Column(name = "config_value")
    private String configValue;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除，1删除，2未删除
     */
    @Column(name = "is_delete")
    private String isDelete;


}