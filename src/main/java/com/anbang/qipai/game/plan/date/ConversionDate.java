package com.anbang.qipai.game.plan.date;

import java.text.ParseException;

import org.springframework.stereotype.Component;

@Component
public class ConversionDate {
	
	public long cdate(long date,long day) throws ParseException {
		//long time = date.getTime();//得到指定日期的毫秒数
		day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
		date-=day; // 相减得到新的毫秒数
		return date; 
	}

}
