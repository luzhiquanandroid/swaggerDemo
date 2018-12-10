package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.AllRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface AllRecordMapper extends Mapper<AllRecord> {
    /**
     * 根据id查询抽奖记录
     *
     * @param id
     * @return
     */
    AllRecord selectById(@Param("id") Integer id);
}