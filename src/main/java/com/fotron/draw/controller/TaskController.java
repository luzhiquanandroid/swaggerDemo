package com.fotron.draw.controller;

import com.fotrontimes.utils.verification.annotation.SignVerification;
import com.fotron.draw.bean.req.UserReq;
import com.fotron.draw.bean.req.task.DoTaskReq;
import com.fotron.draw.bean.req.task.FinishTaskReq;
import com.fotron.draw.service.TaskService;
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
import javax.validation.Valid;
import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description: 任务相关
 */
@RequestMapping("api/task")
@RestController
public class TaskController extends BaseController {
    @Resource
    private TaskService taskService;

    /**
     * 任务列表
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("allList")
    public ApiResponse allList(@RequestBody @Validated ApiRequest<UserReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        Map map = this.taskService.allList(data.getData());
        result.put("everyDayTaskList", map.get("everyDayTaskList"));
        result.put("trySmallGameList", map.get("trySmallGameList"));
        return result;
    }


    /**
     * 做任务
     *
     * @param data
     * @param check
     * @return
     */
    @PostMapping("doTask")
    @SignVerification
    public ApiResponse doTask(@RequestBody @Validated ApiRequest<DoTaskReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        this.taskService.doTask(data.getData());
        return result;
    }


    /**
     * 完成添加小程序/关注公众号任务
     *
     * @param request
     * @param br
     * @return
     */
    @PostMapping("/finishTask")
    @SignVerification
    public ApiResponse finishTask(@RequestBody @Valid ApiRequest<FinishTaskReq> request, BindingResult br){
        checkParameters(br);
        ApiResponse response = ApiResponse.buildSuccess();
        taskService.finishTask(request.getData());
        return response;
    }
}
