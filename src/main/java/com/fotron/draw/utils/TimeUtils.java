package com.fotron.draw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luzhiquan
 * @createTime 2018/12/1 16:07
 * @description
 */
public class TimeUtils {
    /**
     * 计算日期相差天数
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int differentDaysByMillisecond(Date beginTime, Date endTime) {
        int days = (int) ((getDateFromDateTime(endTime).getTime() - getDateFromDateTime(beginTime).getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 当前时间 返回日期格式 不带时分秒
     * @param nowDate
     * @return
     */
    public static Date getDateFromDateTime(Date nowDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(nowDate);
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

}