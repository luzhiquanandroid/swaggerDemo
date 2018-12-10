package com.fotron.draw.controller;

import com.fotron.draw.bean.req.DiamondListReq;
import com.fotron.draw.bean.resp.DiamondListResp;
import com.fotron.draw.service.DiamondService;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@RequestMapping("api/asset")
@RestController
public class DiamondController extends BaseController {
    @Resource
    private DiamondService diamondService;

    /**
     * 获取钻石列表
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("diamond")
    public ApiResponse diamond(@RequestBody @Validated ApiRequest<DiamondListReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = super.createSuccessResult();
        Map map = this.diamondService.diamond(data.getData());
        result.put("data", map.get("data"));
        result.put("totalCount",  map.get("totalCount"));
        return result;
    }
}
