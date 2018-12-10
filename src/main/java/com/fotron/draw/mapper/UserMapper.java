package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface UserMapper extends Mapper<User> {

    /**
     * 根据userId获取一个用户
     *
     * @param userId
     * @return
     */
    User selectByUserId(@Param("userId") String userId);

    /**
     * 根据openId获取一个用户
     *
     * @param openId
     * @return
     */
    User selectByOpenId(@Param("openId") String openId);


    int updateTelephoneFareById(@Param("userId") String userId, @Param("afterTelephoneFare") BigDecimal afterTelephoneFare,
                                @Param("beforeTelephoneFare") BigDecimal beforeTelephoneFare,
                                @Param("date") Date date);


    int updateDiamondByUserId(@Param("userId") String userId, @Param("afterDiamond") Long afterDiamond,
                              @Param("beforeDiamond") Long beforeDiamond,@Param("date") Date date);

    /**
     * 查询小于100个钻石的用户
     *
     * @return
     */
    List<User> selectLess100Users();
}