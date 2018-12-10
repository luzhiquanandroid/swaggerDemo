package com.fotron.draw.mapper;


import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.TaskLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */

public interface TaskLogMapper extends Mapper<TaskLog> {
    TaskLog selectTaskLog(@Param("userId") String userId, @Param("taskId") Integer taskId);
}
