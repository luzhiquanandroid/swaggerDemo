package com.fotron.draw.service.impl;

import com.fotron.draw.bean.req.DiamondListReq;
import com.fotron.draw.bean.resp.DiamondListResp;
import com.fotron.draw.entity.DiamondRecord;
import com.fotron.draw.mapper.DiamondRecordMapper;
import com.fotron.draw.service.DiamondService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: niuhuan
 * @createDate: 2018/11/30
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Service
public class DiamondServiceImpl implements DiamondService {
    @Resource
    private DiamondRecordMapper diamondRecordMapper;

    @Override
    public Map diamond(DiamondListReq data) {
        Map map = new HashMap<>();
        Page<Object> objects = PageHelper.startPage(data.getCurrentPage(), data.getPageSize());
        List<DiamondListResp> result = new ArrayList<>();
        List<DiamondRecord> list = this.diamondRecordMapper.selectList(data.getUserId());
        for (DiamondRecord diamondRecord : list) {
            DiamondListResp resp = new DiamondListResp();
            BeanUtils.copyProperties(diamondRecord, resp);
            result.add(resp);
        }
        map.put("data", result);
        map.put("totalCount", objects.getTotal());
        return map;
    }
}
