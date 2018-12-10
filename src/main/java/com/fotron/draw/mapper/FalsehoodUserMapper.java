package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.FalsehoodUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: niuhuan
 * @createDate: 2018/11/7
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 用户
 */
@org.apache.ibatis.annotations.Mapper
public interface FalsehoodUserMapper extends Mapper<FalsehoodUser> {

    /**
     * 根据openId查询用户
     *
     * @param openid
     * @return
     */
    FalsehoodUser selectByOpenId(@Param("openId") String openid);

    /**
     * 随机获取20个
     * @return
     */
    List<FalsehoodUser> selectByRanod20();
}
