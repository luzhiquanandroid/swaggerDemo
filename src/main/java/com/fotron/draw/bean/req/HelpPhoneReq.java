package com.fotron.draw.bean.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 21:33
 * @description 助力话费/宝箱列表请求
 */
@Data
public class HelpPhoneReq {
    @NotNull(message = "当前页currentPage不能为空")
    private Integer currentPage;
    @NotNull(message = "每页pageSize不能为空")
    private Integer pageSize;
    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;
    /**
     * 类型
     */
    @NotNull(message = "用户type不能为空")
    private Integer type;


}