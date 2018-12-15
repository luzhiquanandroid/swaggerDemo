package com.fotron.draw.controller;


import com.fotron.draw.bean.req.LuckyReq;
import com.fotron.draw.bean.req.OpenReq;
import com.fotron.draw.bean.resp.LuckyResp;
import com.fotron.draw.bean.resp.OpenResp;
import com.fotron.draw.entity.Game;
import com.fotron.draw.entity.Goods;
import com.fotron.draw.service.RandomService;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import com.fotrontimes.utils.verification.annotation.SignVerification;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:26
 * @description 抽奖的控制器
 */
@RequestMapping("api/random")
@RestController
@Api(value = "api/random", tags = {"抽奖模块"}, description = "随机的控制层")
public class RandomController extends BaseController {

    @Resource
    private RandomService randomService;


    /**
     * 随机抽奖
     *
     * @param data
     * @param check
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lucky", method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "抽奖", notes = "根据用户id，随机产生了一个类型")
    public ApiResponse lucky(@RequestBody @Validated ApiRequest<LuckyReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        LuckyResp luck = this.randomService.luck(data.getData());
        result.put("data", luck);
        return result;
    }

    /**
     * 随机抽奖2
     */
    @ResponseBody
    @RequestMapping(value = "/lucky2", method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "抽奖2", notes = "根据用户id，随机产生了一个类型")
    @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String")
    public LuckyResp lucky2(@RequestParam(value = "userId") String userId) {
        LuckyResp luck = this.randomService.luck2(userId);
        return luck;
    }

    /**
     * 随机抽奖4
     */
    @ResponseBody
    @RequestMapping(value = "/lucky4", method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "抽奖4", notes = "根据用户id，随机产生了一个类型")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", required = true, dataType = "int")
    })
    public LuckyResp lucky4(@RequestParam(value = "userId") String userId,
                            @RequestParam(value = "type")  int type) {
        LuckyResp luck = this.randomService.luck4(userId,type);
        return luck;
    }


    /**
     * 随机抽奖3
     */
    @ResponseBody
    @RequestMapping(value = "/lucky3", method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "抽奖3", notes = "根据用户id，随机产生了一个类型")
    public LuckyResp lucky3(@RequestBody LuckyReq data) {
        LuckyResp luck = this.randomService.luck(data);
        return luck;
    }

    /**
     * 打开抽奖 抽话费
     *
     * @param data
     * @param check
     * @return
     */
    @RequestMapping(value = "/open", method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "开奖", notes = "----")
    public ApiResponse open(@RequestBody @Validated ApiRequest<OpenReq> data, BindingResult check) {
        checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        OpenResp luck = this.randomService.open(data.getData());
        result.put("data", luck);
        return result;
    }

    /**
     * banner图随机跳转一个游戏
     *
     * @return
     */
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    @ApiOperation(value = "随机游戏", notes = "-----")
    public ApiResponse game() {
        ApiResponse result = ApiResponse.buildSuccess();
        Game game = this.randomService.game();
        result.put("data", game);
        return result;
    }

    /**
     * 随机产生一个商品
     *
     * @return
     */
    @RequestMapping(value = "/good", method = RequestMethod.GET)
    @ApiOperation(value = "随机商品", notes = "----")
    public ApiResponse good() {
        ApiResponse result = ApiResponse.buildSuccess();
        Goods good = this.randomService.good();
        result.put("data", good);
        return result;
    }
}