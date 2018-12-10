package com.fotron.draw.mapper;


import com.fotron.draw.core.Mapper;
import com.fotron.draw.entity.Goods;

/**
 * @author: niuhuan
 * @createDate: 2018/12/05
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 商品
 */
@org.apache.ibatis.annotations.Mapper
public interface GoodsMapper extends Mapper<Goods> {
    /**
     * 随机取一个商品
     *
     * @return
     */
    Goods selectByRandomOne();


}
