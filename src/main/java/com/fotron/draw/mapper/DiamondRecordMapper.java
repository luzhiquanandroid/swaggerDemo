package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.DiamondRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface DiamondRecordMapper extends Mapper<DiamondRecord> {

    /**
     * 获取用户的钻石获取记录
     *
     * @param userId
     * @return
     */
    List<DiamondRecord> selectList(@Param("userId") String userId);

    /**
     * 获取用户新增钻石记录总数
     *
     * @param userId
     * @return
     */
    Integer selectDiamondCount(@Param("userId") String userId);
}