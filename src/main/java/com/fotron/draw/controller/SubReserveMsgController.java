package com.fotron.draw.controller;

import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import com.fotron.draw.bean.req.SubReserveMsgReq;
import com.fotron.draw.service.SubReserveMsgService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author: niuhuan
 * @createDate: 2018/12/1
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@RestController
@RequestMapping("/api/subReserve")
public class SubReserveMsgController extends BaseController {

    @Resource
    private SubReserveMsgService subReserveMsgService;

    @RequestMapping("report")
    public ApiResponse report(@RequestBody @Valid ApiRequest<SubReserveMsgReq> request, BindingResult br) {
        checkParameters(br);
        ApiResponse response = ApiResponse.buildSuccess();
        subReserveMsgService.report(request.getData());
        return response;
    }

}
