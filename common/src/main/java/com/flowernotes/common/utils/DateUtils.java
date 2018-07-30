package com.flowernotes.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author cyk
 * @date 2018/7/20/020 10:06
 * @email choe0227@163.com
 * @desc 时间工具类，仅限java jdk8 以上使用
 * @modifier
 * @modify_time
 * @modify_remark
 */
public class DateUtils {


    /*
     LocalDateTime
     LocalTime
     LocalDate
     */
    /** 一天的毫秒数 */
    private static final long oneDayTime = 24*3600*1000;
    /** 年月日 yy/MM/dd */
    public static final String DATE_PATTERN_0 = "yy/MM/dd";
    /** 年月日 yyyy-MM-dd */
    public static final String DATE_PATTERN_1 = "yyyy-MM-dd";
    /** 年月 yyyyMM */
    public static final String DATE_PATTERN_2 = "yyyyMM";
    /** 年月日时分秒 yyyyMMddHHmmss */
    public static final String DATE_PATTERN_3 = "yyyyMMddHHmmss";
    /** 年月日时分秒 yyyy-MM-dd HH:mm:ss */
    public static final String DATE_PATTERN_4 = "yyyy-MM-dd HH:mm:ss";
    /** 年月日时分秒毫秒 yyyyMMddHHmmssSSS */
    public static final String DATE_PATTERN_5 = "yyyyMMddHHmmssSSS";
    /** 年月日时 yyyy-MM-dd_HH */
    public static final String DATE_PATTERN_6 = "yyyy-MM-dd_HH";
    /** 年月日 yyyyMMdd */
    public static final String DATE_PATTERN_7 = "yyyyMMdd";


    /**
     * 根据时间毫秒转成LocalDateTime 对象
     * @param timestamp 时间毫秒
     * @return
     */
    public static LocalDateTime getLocalDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 根据时间字符转转换成LocalDateTime 对象
     * @param timeStr 时间字符串
     * @param pattern 时间字符串对应的时间格式
     * @return LocalDateTime 对象
     */
    public static LocalDateTime getLocalDateTimeOfTimeString(String timeStr,String pattern){
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(timeStr, ftf);
    }

    /**
     * 根据LocalDateTime 对象转换成时间戳
     * @param localDateTime
     * @return
     */
    public static Long parseLocalDateTimeToLong(LocalDateTime localDateTime){
        return LocalDateTime.from(localDateTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 根据LocalDateTime 对象转换成时间串
     * @param localDateTime
     * @param pattern
     * @return
     */
    public static String parseLocalDateTimeToTimeString(LocalDateTime localDateTime , String pattern){
        DateTimeFormatter df= DateTimeFormatter.ofPattern(pattern);
        return df.format(localDateTime);
    }

    /**
     * 获取当前时间时间戳
     * @return
     */
    public static Long getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        return parseLocalDateTimeToLong(now);
    }

    /**
     * 获取当前时间的格式化字符串
     * @param pattern
     * @return
     */
    public static String getNowTimeString(String pattern){
        LocalDateTime now = LocalDateTime.now();
        return parseLocalDateTimeToTimeString(now,pattern);
    }

    /**
     * 根据Long 转换成目标格式的字符串
     * @param timeMillis
     * @param pattern
     * @return
     */
    public static String dateLongToString(Long timeMillis,String pattern){
        LocalDateTime localDateTimeOfTimestamp = getLocalDateTimeOfTimestamp(timeMillis);
        return parseLocalDateTimeToTimeString(localDateTimeOfTimestamp,pattern);
    }

    /**
     * 将时间字符串转换成字符串
     * @param timeStr
     * @param pattern
     * @return
     */
    public static Long dateStringToLong(String timeStr,String pattern){
        LocalDateTime parse = getLocalDateTimeOfTimeString(timeStr,pattern);
        return parseLocalDateTimeToLong(parse);
    }

    /***
     *  添加天数
     * @param
     * @return
     */
    public static Long addDay(Long nowTime, int days){
        return nowTime+days*oneDayTime;
    }

    /**
     * 获取传入某天的零点时间
     * @return
     */
    public static Long getTheDayZero(Long dayTime){
        LocalDateTime localDateTimeOfTimestamp = getLocalDateTimeOfTimestamp(dayTime);
        return getTheDayZero(localDateTimeOfTimestamp);
    }

    /**
     * 根据LocalDateTime 对象获取0点时间
     * @param localDateTime
     * @return
     */
    public static Long getTheDayZero(LocalDateTime localDateTime){
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();
        LocalDateTime zeroDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0);
        return parseLocalDateTimeToLong(zeroDateTime);
    }

    /**
     * 根据输入的时间字符串来获取当天的0点时间
     * @param timeStr
     * @param pattern
     * @return
     */
    public static Long getTheDayZero(String timeStr, String pattern){
        LocalDateTime localDateTimeOfTimeString = getLocalDateTimeOfTimeString(timeStr, pattern);
        return getTheDayZero(localDateTimeOfTimeString);
    }

    /**
     * 获取当天的结束时间
     * @param dayTime
     * @return
     */
    public static Long getTheDayEnd(Long dayTime){
        LocalDateTime localDateTimeOfTimestamp = getLocalDateTimeOfTimestamp(dayTime);
        return getTheDayEnd(localDateTimeOfTimestamp);
    }

    /**
     * 获取当天的结束时间
     * @param localDateTime
     * @return
     */
    public static Long getTheDayEnd(LocalDateTime localDateTime){
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int dayOfMonth = localDateTime.getDayOfMonth();
        LocalDateTime zeroDateTime = LocalDateTime.of(year, month, dayOfMonth, 23, 59,59,999);
        return parseLocalDateTimeToLong(zeroDateTime);
    }

    /**
     * 获取当天的结束时间
     * @param timeStr
     * @param pattern
     * @return
     */
    public static Long getTheDayEnd(String timeStr, String pattern){
        LocalDateTime localDateTimeOfTimeString = getLocalDateTimeOfTimeString(timeStr,pattern);
        return getTheDayEnd(localDateTimeOfTimeString);
    }


    public static void main(String[] args) {
        System.out.println(getCurrentTime() + "=="+System.currentTimeMillis());
        System.out.println(getNowTimeString(DATE_PATTERN_4));
        //2018-07-20 14:40:10
        // 1532068810604
        System.out.println(dateLongToString(1532068810604L,DATE_PATTERN_4));
        System.out.println(dateStringToLong("2018-07-20 14:40:10",DATE_PATTERN_4));

        System.out.println("=================");
        // 2018-07-20 00:00:00
        System.out.println(dateStringToLong("2018-07-20 00:00:00",DATE_PATTERN_4));
        System.out.println(getTheDayZero("2018-07-20 10:20:00",DATE_PATTERN_4));
        System.out.println(getTheDayEnd("2018-07-20 10:20:00",DATE_PATTERN_4));
        System.out.println(1532016000000L+oneDayTime);
    }
}
