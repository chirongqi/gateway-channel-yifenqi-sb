package com.namibank.df.gateway.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {

	/** 默认格式 yyyy-MM-dd HH:mm:ss **/
	public static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/** 默认格式2 yyyyMMddHHmmss **/
	public static String DEFAULT_PATTERN_2 = "yyyyMMddHHmmss";
	/** 格式yyyy-MM-dd **/
	public static String DATE_PATTERN = "yyyy-MM-dd";
	/** 格式yyyyMMdd **/
	public static String DATE_PATTERN_2 = "yyyyMMdd";

	/**
	 * 获取当前时间格式为 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		String r = "";
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN_2);
		r = sdf.format(new Date());
		return r;
	}

	/**
	 * 根据pattern格式化日期
	 * 
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static String format(String pattern, Date date) {
		String r = null;
		if (null == pattern || "".equals(pattern) || date == null) {
			return r;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		r = sdf.format(date);
		return r;
	}

	/**
	 * 返回格式化的默认格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDefault(Date date) {
		return format(DEFAULT_PATTERN, date);
	}

	/**
	 * 返回格式化的默认格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDefault2(Date date) {
		return format(DEFAULT_PATTERN_2, date);
	}

	/**
	 * 返回yyyy-MM-dd 格式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String toDate(Date date) {
		return format(DATE_PATTERN, date);
	}

	/**
	 * 返回yyyyMMdd 格式的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String toDate2(Date date) {
		return format(DATE_PATTERN_2, date);
	}

	/**
	 * 获取日期的 年份
	 * 
	 * @param date
	 * @return
	 */
	public static String toYear(Date date) {
		return format("yyyy", date);
	}

	/**
	 * 获取日期的 月份
	 * 
	 * @param date
	 * @return
	 */
	public static String toMonth(Date date) {
		return format("MM", date);
	}

	/**
	 * 返回日期的 日
	 * 
	 * @param date
	 * @return
	 */
	public static String toShortDay(Date date) {
		return format("dd", date);
	}

	/**
	 * 返回日期的 时:分:秒
	 * 
	 * @param date
	 * @return
	 */
	public static String toShortSeconds(Date date) {
		return format("HH:mm:ss", date);
	}

	/**
	 * 将时分秒置0
	 * 
	 * @param date
	 * @return
	 */
	public static Date roundToDay(Date date) {
		Date d = null;
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		d = cal.getTime();
		return d;
	}

	/**
	 * 判断日期是否相同 精确到 日
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDay(Date d1, Date d2) {
		return DateUtil.roundToDay(d1).getTime() == DateUtil.roundToDay(d2).getTime();
	}

	/**
	 * 时间相减 返回天数
	 * 
	 * @param big
	 * @param samll
	 * @return
	 * @throws Exception
	 */
	public static double dayInterval(Date big, Date samll) throws Exception {
		if (big.getTime() < samll.getTime()) {
			throw new Exception("时间参数big小于samll");
		}
		return ((big.getTime() - samll.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 时间相减 返回小时
	 * 
	 * @param big
	 * @param samll
	 * @return
	 * @throws Exception
	 */
	public static double hourInterval(Date big, Date samll) throws Exception {
		if (big.getTime() < samll.getTime()) {
			throw new Exception("时间参数big小于samll");
		}
		return ((big.getTime() - samll.getTime()) / (1000 * 60 * 60));
	}

	/**
	 * 时间相减 返回分钟
	 * 
	 * @param big
	 * @param samll
	 * @return
	 * @throws Exception
	 */
	public static double minuteInterval(Date big, Date samll) throws Exception {
		if (big.getTime() < samll.getTime()) {
			throw new Exception("时间参数big小于samll");
		}
		return ((big.getTime() - samll.getTime()) / (1000 * 60));
	}

	/**
	 * 时间相减 返回秒
	 * 
	 * @param big
	 * @param samll
	 * @return
	 * @throws Exception
	 */
	public static double secondInterval(Date big, Date samll) throws Exception {
		if (big.getTime() < samll.getTime()) {
			throw new Exception("时间参数big小于samll");
		}
		return ((big.getTime() - samll.getTime()) / (1000));
	}

	/**
	 * 返回日期的下一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDate(Date date) {
		return nextNumDate(date, 1);
	}

	public static Date lastDate(Date date) {
		return nextNumDate(date, -1);
	}

	/**
	 * 返回日期的下(num)几天
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextNumDate(Date date, int num) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, num);
		return cal.getTime();
	}

	/**
	 * 根据date 增加 amount
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date add(Date date, int field, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	/**
	 * 根据date 设置 amount
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date set(Date date, int field, int amount) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(field, amount);
		return cal.getTime();
	}

	/**
	 * 格式化时间并补成14位
	 * 
	 * @param time
	 * @return
	 */
	public static String formatDate(String time) {
		// 直接替换 看是否满足要求
		String sTime = time.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "").replaceAll("/", "");
		// 替换后 不需要补位直接返回
		if (sTime.length() == 14) {
			return sTime;
		}
		StringBuilder sb = new StringBuilder();
		String[] strArr = time.split(" ");
		if (strArr.length < 2) {
			return sTime;
		}
		// 用 - 切割
		String[] dArr = strArr[0].split("-");
		if (dArr.length <= 1) {
			// 用 / 切割
			dArr = strArr[0].split("/");
		}
		String[] hArr = strArr[1].split(":");
		String[] pArr = new String[dArr.length + hArr.length];
		System.arraycopy(dArr, 0, pArr, 0, dArr.length);
		System.arraycopy(hArr, 0, pArr, dArr.length, hArr.length);
		for (int i = 0; i < pArr.length; i++) {
			String s = pArr[i];
			if (i == 0) {
				sb.append(s);
				continue;
			}
			if (s.length() < 2) {
				sb.append("0" + s);
			} else {
				sb.append(s);
			}
		}
		if (sb.toString().length() > 14) {
			return sTime;
		}
		return sb.toString();

	}

	// public static void main(String[] args) throws Exception {
	// System.out.println(roundToDay(new Date()));
	// Date d1 = new Date();
	// Thread.sleep(1000);
	// Date d2 = new Date();
	//
	// boolean b = d1.before(d2);
	// System.out.println(b);
	// System.out.println(minuteInterval(d2,d1));
	// System.out.println(lastDate(d1));
	//
	// System.out.println("===="+getCurrentTime().substring(4, 8));
	//
	// SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	// Calendar calendar = Calendar.getInstance();
	// calendar.setTime(new Date());
	// calendar.add(Calendar.DAY_OF_MONTH, -1);
	// Date date = calendar.getTime();
	// String time = df.format(date);
	// System.out.println("time:"+time.substring(4));
	//
	// }

}
