package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.InvitationJoin;
import com.xiaoleilu.hutool.date.DateTime;
import org.apache.ibatis.annotations.Param;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface InvitationJoinMapper extends Mapper<InvitationJoin> {
    /**
     * 根据加入者id查询一条记录
     *
     * @param userId
     * @return
     */
    InvitationJoin selectByJoinUserId(@Param("userId") String userId);

    /**
     * 根据邀请id和用户id查询用户邀请记录
     *
     * @param userId
     * @return
     */
    InvitationJoin selectByJoinUserIdAndRefId(@Param("userId") String userId, @Param("refId") Integer refId);

    /**
     * 查询当天是否被这个朋友邀请过
     *
     * @param friendHome
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    InvitationJoin selectByToday(@Param("friendHome") String friendHome, @Param("userId") String userId, @Param("startDate") DateTime startDate, @Param("endDate") DateTime endDate);

    /**
     * 查询用户首页页面当天邀请了多少个好友
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    int selectByTenInvitation(@Param("userId") String userId, @Param("startDate") DateTime startDate, @Param("endDate") DateTime endDate);
}