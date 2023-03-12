package com.itlyc.common.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalTime;
import java.util.*;

/**
 * 时间处理工具类
 * 使用joda-time（DateTimeFormat,DateTimeFormatter）
 */
public class DateTimeUtil {


    public static final String TIME_FORMAT_1 = "HH:mm:ss";
    public static final String TIME_FORMAT_2 = "yyyy-MM-dd";
    public static final String TIME_FORMAT_3 = "yyyy/MM/dd";
    public static final String TIME_FORMAT_4 = "yyyy-MM";
    public static final String TIME_FORMAT_5 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_6 = "yyyyMMddHHmmssSSS";

    //将str转换成datetime
    public static Date strToDate(String dateTimeStr, String formartStr) {
        //传入格式并封装(创建一个格式化对象)
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formartStr);
        //将传进来的字符串封装为一个DateTime对象
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        //返回date对象
        return dateTime.toDate();
    }

    //将datetime转换成str
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }


    //将str转换成datetime（标准化）
    public static Date strToDate(String dateTimeStr) {
        //传入格式并封装
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(TIME_FORMAT_5);
        //将传进来的字符串封装为一个DateTime对象
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        //返回date对象
        return dateTime.toDate();
    }

    //将datetime转换成str（标准化）
    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(TIME_FORMAT_5);
    }


    /**
     * 通过两个日期字符串和格式，得到这两个日期之间的所有日期列表
     */
    public static List<Date> getDatesBetweenTwoDate(String dateStr1, String dateStr2, String format) {
        return getDatesBetweenTwoDate(strToDate(dateStr1, format), strToDate(dateStr2, format));
    }


    /**
     * 通过两个日期，得到这两个日期之间的所有日期列表
     */
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {
        switch (beginDate.compareTo(endDate)) {
            case 0:
            case 1:
                List<Date> list = new ArrayList<>();
                list.add(beginDate);
                return list;
        }

        List<Date> lDate = new ArrayList<Date>();
        lDate.add(beginDate);// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(endDate);// 把结束时间加入集合
        return lDate;
    }

    /**
     * 通过给的时间计算是上午还是下午
     * 返回：0是上午 1是下午
     */
    public static int noonType(Date date) {
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTime(date);
        return ca.get(GregorianCalendar.AM_PM);
    }


    /**
     * 判断第一给时间字符串和（第二个时间字符串+分钟数）的大小
     * ex ：HH:mm:ss 和 ( HH:mm:ss + minutes) 第一个大返回1 第一个小返回0    8:00 10
     */
    public static int compareTime(String timeStr1, String timeStr2, Integer minutes) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern(TIME_FORMAT_1);
        LocalTime localTime1 = LocalTime.parse(timeStr1, formatter);
        LocalTime localTime2 = LocalTime.parse(timeStr2, formatter);
        return localTime1.compareTo(localTime2.plusMinutes(minutes));
    }

}
