package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.SignIn;
import org.apache.ibatis.annotations.Param;

/**
 * 签到
 * @author luzhiquan
 */
public interface SignInMaper extends Mapper<SignIn> {
    /**
     * 根据userID 查询
     *
     * @param userId
     * @return
     */
    SignIn selectSignInByUserId(@Param("userId") String userId);

}
