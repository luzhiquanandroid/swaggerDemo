package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.TranSport;
import org.apache.ibatis.annotations.Param;

/**
 * @author: niuhuan
 * @createDate: 2018/12/7
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
public interface TranSportMapper extends Mapper<TranSport> {
    /**
     * 查询跳转图片
     *
     * @param status
     * @param taskId
     * @return
     */
    TranSport selectByTask(@Param("taskId") Integer taskId, @Param("status") int status);
}
