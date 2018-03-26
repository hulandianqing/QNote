package org.hulan.util.common;

import java.text.SimpleDateFormat;

/**
 * 时间：2018-03-20
 * @author: zhaokuiqiang
 */
public class DateUtil {
	
	public final static String yyyy = "yyyy";
	public final static String MM = "MM";
	public final static String dd = "dd";
	public final static String HH = "HH";
	public final static String mm = "mm";
	public final static String ss = "ss";
	
	public static String formateNowTime() {
		return new SimpleDateFormat(getFormateDate("") + getFormateTime("")).format(System.currentTimeMillis());
	}
	
	/**
	 * 格式化日期
	 * @author:zhaokuiqiang
	 * @param fix
	 * @return
	 */
	public static String getFormateDate(String fix) {
		return yyyy + fix + mm + fix + dd;
	}
	
	/**
	 * 格式化时间
	 * @author:zhaokuiqiang
	 * @param fix
	 * @return
	 */
	public static String getFormateTime(String fix) {
		return HH + fix + mm + fix + ss;
	}
}
