package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.SignInLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 签到日志
 */
public interface SignInLogMapper extends Mapper<SignInLog> {

    SignInLog selectSignLogByDate(@Param("signInDate") Date signInDate);
}
