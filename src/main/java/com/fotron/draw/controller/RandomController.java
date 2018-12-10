package com.fotron.draw.controller;


import com.fotrontimes.utils.verification.annotation.SignVerification;
import com.fotron.draw.bean.req.LuckyReq;
import com.fotron.draw.bean.req.OpenReq;
import com.fotron.draw.bean.resp.LuckyResp;
import com.fotron.draw.bean.resp.OpenResp;
import com.fotron.draw.entity.Game;
import com.fotron.draw.entity.Goods;
import com.fotron.draw.mapper.GoodsMapper;
import com.fotron.draw.service.RandomService;
import com.fotrontimes.core.web.ApiRequest;
import com.fotrontimes.core.web.ApiResponse;
import com.fotrontimes.core.web.BaseController;
import com.fotrontimes.utils.verification.annotation.SignVerification;
import io.swagger.annotations.Api;
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
@Api(value = "随机的控制层")
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
    @RequestMapping(value = "/lucky",method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "抽奖",notes = "-----")
    public ApiResponse lucky(@RequestBody @Validated ApiRequest<LuckyReq> data, BindingResult check) {
        super.checkParameters(check);
        ApiResponse result = ApiResponse.buildSuccess();
        LuckyResp luck = this.randomService.luck(data.getData());
        result.put("data", luck);
        return result;
    }

    /**
     * 打开抽奖 抽话费
     *
     * @param data
     * @param check
     * @return
     */
    @RequestMapping(value = "/open",method = RequestMethod.POST)
    @SignVerification
    @ApiOperation(value = "开奖",notes = "----")
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
    @RequestMapping(value = "/game",method = RequestMethod.GET)
    @ApiOperation(value = "随机游戏",notes = "-----")
    public ApiResponse game() {
        ApiResponse result = ApiResponse.buildSuccess();
        Game game = this.randomService.game();
        result.put("data", game);
        return result;
    }

    /**
     * 随机产生一个商品
     * @return
     */
    @RequestMapping(value = "/good",method = RequestMethod.GET)
    @ApiOperation(value = "随机商品",notes = "----")
    public ApiResponse good(){
        ApiResponse result = ApiResponse.buildSuccess();
        Goods good = this.randomService.good();
        result.put("data", good);
        return result;
    }
}