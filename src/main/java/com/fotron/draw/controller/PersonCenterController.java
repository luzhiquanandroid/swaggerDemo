package com.fotron.draw.controller;

import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import com.fotron.draw.bean.req.DetailReq;
import com.fotron.draw.bean.req.HelpPhoneReq;
import com.fotron.draw.entity.Invitation;
import com.fotron.draw.service.PersonalCenterService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 21:58
 * @description
 */
@RequestMapping("api/person")
@RestController
public class PersonCenterController extends BaseController {
    @Resource
    private PersonalCenterService personalCenterService;

    /**
     * 显示助力话费，宝箱列表
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("list")
    public ApiResponse list(@RequestBody @Validated ApiRequest<HelpPhoneReq> data, BindingResult check) {
        checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();

        Map resp = this.personalCenterService.list(data.getData());
        result.put("data", resp.get("data"));
        result.put("totalCount", resp.get("totalCount"));
        return result;
    }

    /**
     * 查看助力话费，宝箱详情/ 通过条件判断，修改助力状态
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("detail")
    public ApiResponse detail(@RequestBody @Validated ApiRequest<DetailReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        Invitation invitation = this.personalCenterService.detail(data.getData());
        result.put("data", invitation);
        return result;
    }
}