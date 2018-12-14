package com.fotron.draw.service;

import com.fotron.draw.bean.req.LuckyReq;
import com.fotron.draw.bean.req.OpenReq;
import com.fotron.draw.bean.resp.LuckyResp;
import com.fotron.draw.bean.resp.OpenResp;
import com.fotron.draw.entity.Game;
import com.fotron.draw.entity.Goods;

/**
 * @author luzhiquan
 * @createTime 2018/11/20 17:30
 * @description 抽奖
 */
public interface RandomService {
    LuckyResp luck(LuckyReq luckyReq);
    LuckyResp luck2(String userId);
    LuckyResp luck4(String userId,int type);

    OpenResp open(OpenReq openReq);

    /**
     * 随机游戏
     *
     * @return
     */
    Game game();

    /**
     * 商品
     * @return
     */
    Goods good();
}
