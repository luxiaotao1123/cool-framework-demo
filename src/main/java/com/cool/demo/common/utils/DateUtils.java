package com.cool.demo.common.utils;

import com.core.common.Arith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 * Created by vincent on 2019-04-15
 */
public class DateUtils {

    public final static String yyyyMMdd_C="yyyy年MM月dd日";
    public final static String yyyyMM_F="yyyy-MM";
    public final static String yyyyMMdd_F="yyyy-MM-dd";
    public final static String yyyyMMddHHmmss_F="yyyy-MM-dd HH:mm:ss";
    public final static String yyyyMMddHHmmsssss_F="yyyy-MM-dd HH:mm:ss,SSS";
    public final static String yyyy="yyyy";
    public final static String yyyyMM="yyyyMM";
    public final static String yyyyMMdd="yyyyMMdd";
    public final static String yyyyMMddHH="yyyyMMddHH";
    public final static String yyyyMMddHHmmss="yyyyMMddHHmmss";
    public final static String YYMMDDHHMMSS="YYMMDDHHMMSS";
    public final static String yyyyMMddHHmmsssss="yyyyMMddHHmmssSSS";

    /**
     * date ==>> string
     */
    public static String convert(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String convert(Date date){
        return convert(date, yyyyMMddHHmmss_F);
    }

    /**
     * string ==>> date
     */
    public static Date convert(String str, String pattern){
        if (str.length() < pattern.length()){
            throw new RuntimeException("时间解析失败 ==>> "+str);
        }
        if (str.length() > pattern.length()){
            str = str.substring(0, pattern.length());
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("时间解析失败 ==>> "+str);
        }
        return date;
    }

    public static Date convert(String str){
        return convert(str, yyyyMMddHHmmss_F);
    }

    /**
     * 两个date之间相差的天数，不满一天算一天
     */
    public static int diff(Date date1, Date date2){
        return getDaysByTimestamp(Math.abs(date2.getTime() - date1.getTime()));
    }

    public static long diffToMinute(Date date1, Date date2){
        return Math.abs(date2.getTime() - date1.getTime())/1000/60;
    }

    public static long diffToSeconds(Date date1, Date date2){
        return Math.abs(date2.getTime() - date1.getTime())/1000;
    }

    private static int getDaysByTimestamp(long timestamp){
        double daysPoint = Arith.divides(2, timestamp, (1000 * 3600 * 24));
        int daysPoint1 = (int) daysPoint;
        double daysPoint2 = (double) daysPoint1;
        if (daysPoint > daysPoint2){
            return daysPoint1 + 1;
        }
        return daysPoint1;
    }

    /**
     * 入参date距离现在的秒数
     */
    public static int diffToNow(Date date){
        long diff = new Date().getTime() - date.getTime();
        return (int) (Math.abs(diff) / 1000);
    }

    /**
     * 当前时间戳（单位：秒）
     */
    public static String createTimeStamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 时间计算函数
     * @param date 被计算时间实例
     * @param val 计算值
     * @param timeUnit 计算值单位
     * @param subtraction 减法布尔 true：当前函数为减法计算，false：反之
     * @return 计算结果 Date
     */
    public static Date calculate(Date date, Long val, TimeUnit timeUnit, boolean subtraction){
        if (Objects.isNull(date) || Objects.isNull(val) || Objects.isNull(timeUnit)){
            return null;
        }
        return new Date(subtraction?date.getTime()-timeUnit.toMillis(val):date.getTime()+timeUnit.toMillis(val));
    }

    public static Date calculate(Date date, Long val, TimeUnit timeUnit){
        return calculate(date, val, timeUnit, false);
    }

    /**
     * 时间对象DateEntity
     */
    public static DateEntity getDateEntity(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DateEntity dateEntity = new DateEntity();
        dateEntity.setYear(cal.get(Calendar.YEAR));
        dateEntity.setMonth(cal.get(Calendar.MONTH));
        dateEntity.setDay(cal.get(Calendar.DATE));
        dateEntity.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateEntity.setMinute(cal.get(Calendar.MINUTE));
        dateEntity.setSecond(cal.get(Calendar.SECOND));
        return dateEntity;
    }

    static class DateEntity {
        int year;
        int month;
        int day;
        int hour;
        int minute;
        int second;

        public int getYear() {
            return year;
        }

        public void setYear(final int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(final int month) {
            this.month = month + 1;
        }

        public int getDay() {
            return day;
        }

        public void setDay(final int day) {
            this.day = day;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(final int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(final int minute) {
            this.minute = minute;
        }

        public int getSecond() {
            return second;
        }

        public void setSecond(final int second) {
            this.second = second;
        }
    }
}
