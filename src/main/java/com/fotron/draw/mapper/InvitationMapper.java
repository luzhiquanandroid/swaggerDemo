package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.Invitation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface InvitationMapper extends Mapper<Invitation> {
    List<Invitation> selectList(@Param("userId") String userId, @Param("type") Integer type);

    Invitation selectById(@Param("id") Integer id);

}