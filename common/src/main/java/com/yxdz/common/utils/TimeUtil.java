package com.yxdz.common.utils;

import android.os.SystemClock;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @ClassName: TimeUtil
 * @Desription: 时间工具类
 * @author: Dreamcoding
 * @date: 2017/11/2
 */
public class TimeUtil {

    public final static String DATA_FORMAT="yyyy-MM-dd HH:mm:ss";
    public final static String DATA_FORMAT2="yyyy-MM-dd HH:mm";

    /**
     * 根据时间毫秒数获取日期字符串
     * 
     * @param milliseconds
     *            毫秒
     * @return 日期字符串yyyyMMddHHmmss
     */
    public static String getDateStr(long milliseconds) {
        SimpleDateFormat df = new SimpleDateFormat(DATA_FORMAT);
        return df.format(new Date(milliseconds));
    }

    /**
     * 根据时间毫秒数获取指定格式的日期字符串
     * 
     * @param milliseconds
     *            毫秒
     * @param format
     *            日期格式，如yyyy-MM-dd hh:mm:ss
     * @return 日期字符串
     */
    public static String getDateStr(long milliseconds, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date(milliseconds));
    }

    /**
     * 指定日期对象及相应格式，获取日期字符串
     * 
     * @param date
     * @param format
     * @return
     */
    public static String getDateStr(Date date, String format) {
        if (date == null) {
            return "";
        }
        return getDateStr(date.getTime(), format);
    }

    /**
     * 指定日期字符串，以及相应的格式字符串，获取日期对象
     * 
     * @param dateStr
     * @param format
     * @return
     */
    public static Date getDate(String dateStr, String format) {
       /* if (TextUtils.isEmpty(dateStr) || TextUtils.isEmpty(format)) {
            return null;
        }*/
        Date date = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 指定日期字符串，以及相应的格式字符串，获取该时间的毫秒数
     * 
     * @param dateStr
     * @param format
     * @return
     */
    public static long getTimeMillis(String dateStr, String format) {
        return getDate(dateStr, format).getTime();
    }

    /**
     * 比较两个日期字符串的先后
     * 
     * @param dateStr1
     *            第一个日期
     * @param dateStr2
     *            第二个日期
     * @param format
     *            相应日期格式
     * @return -1表示dateStr1小于dateStr2，0表示dateStr1等于dateStr2，1
     *         表示dateStr1大于dateStr2，
     */
    public static int compareDateStr(String dateStr1, String dateStr2,
                                     String format) {
        int result = 0;
        if (TextUtils.isEmpty(dateStr1) && !TextUtils.isEmpty(dateStr2)) {
            return 1;
        }
        if (!TextUtils.isEmpty(dateStr1) && TextUtils.isEmpty(dateStr2)) {
            return -1;
        }
        if ((TextUtils.isEmpty(dateStr1) && TextUtils.isEmpty(dateStr2))
                || TextUtils.isEmpty(format)) {
            return 0;
        }
        Date d1 = getDate(dateStr1, format);
        Date d2 = getDate(dateStr2, format);
        if (d1 == null || d2 == null) {
            return result;
        }
        result = d2.compareTo(d1);
        return result;
    }


    /**
     * 设置系统时间
     * @param year
     * @param month
     * @param day
     * @param hourOfDay
     * @param minute
     */
    public static void setSystemDate(int year, int month, int day, int hourOfDay, int minute,int second) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);

        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            SystemClock.setCurrentTimeMillis(when);
        }
    }

}
