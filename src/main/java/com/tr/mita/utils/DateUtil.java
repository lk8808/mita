package com.tr.mita.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 将传入指定的日期增加或减去指定量的年份
	 * @param date 传入的日期
	 * @param years 年份的增量（如果为负则减去）
	 * @return 返回计算后的日期
	 */
	public static Date addYears(Date date, int years) {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, years);
		resultDate = c.getTime();

		return resultDate;
	}
	
	/**
	 * 将传入指定的日期增加或减去指定量的月份
	 * @param date 传入的日期
	 * @param months 月份的增量（如果为负则减去）
	 * @return 返回计算后的日期
	 */
	public static Date addMonths(Date date, int months) {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, months);
		resultDate = c.getTime();
		
		return resultDate;
	}
	
	/**
	 * 将传入指定的日期增加或减去指定的天数
	 * @param date 传入的日期
	 * @param months 天数的增量（如果为负则减去）
	 * @return 返回计算后的日期
	 */
	public static Date addDays(Date date, int days) {
		Date resultDate = null;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, days);
		resultDate = c.getTime();
		
		return resultDate;
	}
	/**
	 * 计算两个日期的月份差
	 * @param 开始日期 
	 * @param 截止日期
	 * @return 月份差
	 * @throws ParseException
	 */
	public static int countMonths(Date d1,Date d2) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		d1 = sdf.parse(sdf.format(d1));
		d2 = sdf.parse(sdf.format(d2));
		
		long t1 = d1.getTime();
		long t2 = d2.getTime();
        
		return (int)((t2-t1)/(1000*3600*24*30));
	}
	
	/**
	 * 计算俩个日期之前相隔多少天
	 * @param d1
	 * @param d2
	 * @return
	 * @throws ParseException
	 */
	public static int countDays(Date d1, Date d2) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		d1 = sdf.parse(sdf.format(d1));
		d2 = sdf.parse(sdf.format(d2));
		
		long t1 = d1.getTime();
		long t2 = d2.getTime();
		
		return (int)((t2-t1)/(1000*3600*24));
	}
	
	
	/**
	 * 得到指定日期所在月份最后一天的日期 
	 * @param 指定日期
	 * @return 指定日期所在月份最后一天的日期  
	 * */ 
	public static Date getLastDayOfMonth(Date date)   {     
		Calendar cDay = Calendar.getInstance();     
	    cDay.setTime(date);  
	    cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    return cDay.getTime();     
	}
	
	 /** 
	  *  得到指定日期所在季度最后一天的日期 
	  *  @param 指定日期
	  *  @return 指定日期所在季度最后一天的日期 
	  */ 
	public static Date getLastDayOfQuarter(Date date)   {     
		Calendar cDay = Calendar.getInstance();     
		cDay.setTime(date);  
		int curMonth = cDay.get(Calendar.MONTH);  
		if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH){    
			cDay.set(Calendar.MONTH, Calendar.MARCH);  
		}  
		if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE){    
			cDay.set(Calendar.MONTH, Calendar.JUNE);  
		}  
		if (curMonth >= Calendar.JULY && curMonth <= Calendar.AUGUST) {    
			cDay.set(Calendar.MONTH, Calendar.AUGUST);  
		}  
		if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {    
			cDay.set(Calendar.MONTH, Calendar.DECEMBER);  
		}  
		cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cDay.getTime();     
	}
	
	/** 
	  *  得到 指定日期增加指定天数后的日期
	  *  @param 指定日期
	  *  @param 指定天数
	  *  @return 指定日期增加指定天数后的日期
	  *  
	  */
	public static Date getTargetDay(Date startdate,int day){
		Calendar c = Calendar.getInstance();
        c.setTime(startdate);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
	}

}
