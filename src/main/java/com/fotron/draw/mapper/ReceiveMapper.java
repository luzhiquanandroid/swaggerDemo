package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.Receive;
import org.apache.ibatis.annotations.Param;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface ReceiveMapper extends Mapper<Receive> {

    Receive selectByUserId(@Param("userId") String userId);

    Receive selectByUserIdAndRecId(@Param("userId") String userId, @Param("receiveId") Integer receiveId);

    int selectAcount(@Param("userId") String userId);
}