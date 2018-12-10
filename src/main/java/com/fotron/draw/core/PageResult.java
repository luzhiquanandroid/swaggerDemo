package com.fotron.draw.core;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * @author: yutong
 * @createDate: 2018/6/26
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Data
public class PageResult<T> {

    /**
     * 总数量
     */
    private long count;

    /**
     * 当前数据
     */
    private List<T> data;

    /**
     * 当前页
     */
    private int page = 1;

    public PageResult() {
    }

    public PageResult(Page page, List<T> rows) {
        this.page = page.getPageNum();
        this.count = page.getTotal();
        this.data = rows;
    }
}
