package com.itlyc.common.util;

import cn.hutool.core.date.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author WangHao
 * @date 2019/10/16 18:41
 */
public class DateUtil {

/*    public static void main(String[] args) throws Exception {

      *//*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2019-12-19");
        Date date2 = sdf.parse("2020-01-16");
        for (Date date : getDatesBetweenTwoDate(date1, date2)) {
            System.out.println(sdf.format(date));
        }*//*

        *//*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = sdf.parse("2019-12-19 11:00:00");
        System.out.println(noonType(date1));*//*
        String timeStr1 = "09:12:00";
        String timeStr2 = "09:00:00";
        System.out.println(compareTime(timeStr1, timeStr2, 0));
    }*/

    /**
     * 参数：Object、要转换的格式
     * 返回：时间字符串
     */
    public static String timestamp2DateStr(Long timeStamp, String format) {
        Date date = new Date(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String timeStr = sdf.format(date);
        return timeStr;
    }

    /**
     * 参数：Object、要转换的格式
     * 返回：时间字符串
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String timeStr = sdf.format(date);
        return timeStr;
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateStr 特定格式的日期
     * @param format  格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static DateTime parse(String dateStr, String format) {
        return new DateTime(dateStr, format);
    }

    /**
     * 判断两个给定时间字符串 HH:mm 和 HH:mm 的大小
     */
    public static int compareTime(String timeStr1, String timeStr2) {
        return compareTime(timeStr1, timeStr2, 0);
    }

    /**
     * 判断第一给时间字符串和（第二个时间字符串+分钟数）的大小
     * ex ：HH:mm:ss 和 ( HH:mm:ss + minutes) 第一个大返回1 第一个小返回0
     */
    public static int compareTime(String timeStr1, String timeStr2, Integer minutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime localTime1 = LocalTime.parse(timeStr1, formatter);
        LocalTime localTime2 = LocalTime.parse(timeStr2, formatter);
        return localTime1.compareTo(localTime2.plusMinutes(minutes));
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
     * 通过两个日期字符串和格式，得到这两个日期之间的所有日期列表
     */
    public static List<Date> getDatesBetweenTwoDate(String dateStr1, String dateStr2, String format) {
        return getDatesBetweenTwoDate(parse(dateStr1, format), parse(dateStr2, format));
    }

    /**
     * 一个时间字符串转化为另一个种格式的时间字符串
     */
    public static String dateStr2DateStr(String dateStrFrom, String formatFrom, String formatTo) {
        return new DateTime(dateStrFrom, formatFrom).toString(formatTo);
    }

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format {@link SimpleDateFormat}
     * @return 格式化后的字符串
     */
    public static String format(Date date, DateFormat format) {
        if (null == format || null == date) {
            return null;
        }
        return format.format(date);
    }
}
