package com.fotron.draw.mapper;


import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.Game;

/**
 * @author: niuhuan
 * @createDate: 2018/8/10
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 游戏
 */
@org.apache.ibatis.annotations.Mapper
public interface GameMapper extends Mapper<Game> {


    /**
     * 随机一款在线游戏
     *
     * @return
     */
    Game selectRandomOne();
}
