package com.fotron.draw.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author: lipeng.shen
 * @createDate: 2018/09/05
 * @company: (C) Copyright fotron
 * @since: JDK 1.8
 * @Description:生成订单号方法
 */

public class OrderCodeCreateUtil {

	/**
	 * 订单号生成唯一方法
	 * @param userId
	 * @param number
	 * @return
	 */
	public static String getOrderId(String userId, int number) {

	    Date d = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	    String dateNowStr = sdf.format(d);

	    StringBuffer sb = new StringBuffer();
	    sb.append("f");
	
	   sb.append(dateNowStr);
	    sb.append(userId);
	    if (number<9){
	        sb.append("f"+number);
	    }else {
	        sb.append(number);
	    }

	    return sb.toString();
	}
}
