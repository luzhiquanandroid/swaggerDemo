package com.fotron.draw.mapper;

import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.Config;
import org.apache.ibatis.annotations.Param;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 15:31
 * @description
 */
public interface ConfigMapper extends Mapper<Config> {
    /**
     * 根据key  查询 value
     *
     * @param configKey
     * @return
     */
    Config selectValueByKey(@Param("configKey") String configKey);

    /**
     * 根据 key  更新  value
     *
     * @param configKey
     * @param configValue
     * @return
     */
    int updateValueByKey(@Param("configKey") String configKey, @Param("configValue") String configValue);
}