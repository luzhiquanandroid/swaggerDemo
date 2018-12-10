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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:26
 * @description 抽奖的控制器
 */
@RequestMapping("api/random")
@RestController
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
    @PostMapping("lucky")
    @SignVerification
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
    @PostMapping("open")
    @SignVerification
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
    @GetMapping("game")
    public ApiResponse game() {
        ApiResponse result = ApiResponse.buildSuccess();
        Game game = this.randomService.game();
        result.put("data", game);
        return result;
    }

    @GetMapping("good")
    public ApiResponse good(){
        ApiResponse result = ApiResponse.buildSuccess();
        Goods good = this.randomService.good();
        result.put("data", good);
        return result;
    }
}