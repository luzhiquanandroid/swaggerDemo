package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface RechargeRecordMapper extends Mapper<RechargeRecord> {


    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 根据订单id查询充值记录
     *
     * @param orderId
     * @return
     */
    RechargeRecord selectByOrderId(@Param("orderId") String orderId);

    /**
     * 根据用户id查询充值记录
     * @param userId
     * @return
     */
    List<RechargeRecord> selectByUserIdList(@Param("userId") String userId);


}