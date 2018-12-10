package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
public interface TaskMapper extends Mapper<Task> {

    /**
     * 查询任务列表
     *
     * @return
     */
    List<Task> selectAllList();

    /**
     * 根据主键id查询
     *
     * @param one
     * @return
     */
    Task selectByTaskId(@Param("id") Integer one);

}
