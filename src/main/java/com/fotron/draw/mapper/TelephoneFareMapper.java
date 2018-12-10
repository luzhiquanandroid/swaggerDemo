package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.AllRecord;
import com.fotron.draw.entity.TelephoneFare;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface TelephoneFareMapper extends Mapper<TelephoneFare> {
    /**
     * 根据记录id查询
     * @param recordId
     * @return
     */
    TelephoneFare selectByRecordId(@Param("recordId") Integer recordId);

    BigDecimal selectAmount();
}