package com.anbang.qipai.game.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tan on 2017/9/12.
 */
public class TimeUtil {
	
	//获得多少天前的毫秒数
	public static long creducedate(long date,long day){
		day = day*24*60*60*1000; // 要加上的天数转换成毫秒数
		date-=day; // 相减得到新的毫秒数
		return date; 
	}
	
	//获取本月的开始时间
	public static long getBeginDayTimeOfCurrentMonth(long currentTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(currentTime), getNowMonth(currentTime) - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	//获取本月的结束时间
	public static long getEndDayTimeOfCurrentMonth(long currentTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(currentTime), getNowMonth(currentTime) - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(currentTime), getNowMonth(currentTime) - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	//获取某个日期的开始时间
	public static long getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	//获取某个日期的结束时间
	public static long getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTimeInMillis();
	}

	//获取今年是哪一年
	public static Integer getNowYear(long currentTime) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTimeInMillis(currentTime);
		return Integer.valueOf(gc.get(1));
	}

	//获取本月是哪一月
	public static int getNowMonth(long currentTime) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTimeInMillis(currentTime);
		return gc.get(2) + 1;
	}
}
