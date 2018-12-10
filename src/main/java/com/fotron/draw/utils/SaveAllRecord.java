package com.fotron.draw.utils;

import com.fotron.draw.entity.AllRecord;
import com.fotron.draw.mapper.AllRecordMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: niuhuan
 * @createDate: 2018/11/26
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:
 */
@Component
public class SaveAllRecord {
    @Resource
    private AllRecordMapper allRecordMapper;


    public void saveRecord(Integer recordId, Integer type) {
        AllRecord allRecord = this.allRecordMapper.selectById(recordId);
        allRecord.setUpdateTime(new Date());
        allRecord.setIsOpen(type);
        this.allRecordMapper.updateByPrimaryKey(allRecord);

    }
}
