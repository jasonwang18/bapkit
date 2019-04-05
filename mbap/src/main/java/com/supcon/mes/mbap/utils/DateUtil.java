package com.supcon.mes.mbap.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshizhan on 2017/11/20.
 * Email:wangshizhan@supcon.com
 */

public class DateUtil {


    /**
     * 格式化时间 long2String
     * @param time 时间毫秒
     * @param format 格式
     * @return
     */
    public static String dateFormat(long time, String format){

        if(time == 0){
            time = System.currentTimeMillis();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String t=simpleDateFormat.format(time);

        return t;
    }

    /**
     * 格式化时间 long2String
     * @param time 时间毫秒
     * @return
     */
    public static String dateTimeFormat(long time){
        if(time == 0){
            time = System.currentTimeMillis();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String t=simpleDateFormat.format(time);

        return t;
    }

    /**
     * 格式化时间 string2long
     * @param time
     * @return
     */
    public static long dateFormat(String time, String format){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date == null ? 0:date.getTime();
    }


    /**
     * 格式化时间 long2String
     * @param time 时间毫秒
     * @return
     */
    public static String dateFormat(long time){
        if(time == 0){
            time = System.currentTimeMillis();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String t=simpleDateFormat.format(time);

        return t;
    }

    /**
     * 格式化时间 string2long
     * @param time
     * @return
     */
    public static long dateFormat(String time){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date == null ? 0:date.getTime();
    }

    /**
     * 获取SimpleDateFormat
     * @param parttern 日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException 异常：非法日期格式
     */
    public static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取昨天凌晨时间
     * @return
     */
    public static Date getYesterdayStart () throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);

        date = calendar.getTime();

        SimpleDateFormat format = getDateFormat("yyyy-MM-dd");

        StringBuffer time = new StringBuffer();

        String dateString  = format.format(date);

        time.append(dateString).append(" ").append("00:00:00");

        return sdf.parse(time.toString());

    }

    /**
     * 获取昨天最后时间
     * @return
     */
    public static Date getThreeDayStart() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -3);

        date = calendar.getTime();

        SimpleDateFormat format = getDateFormat("yyyy-MM-dd");

        StringBuffer time = new StringBuffer();

        String dateString  = format.format(date);

        time.append(dateString).append(" ").append("00:00:00");

        return sdf.parse(time.toString());
    }

    /**
     * 获取昨天最后时间
     * @return
     */
    public static Date getThreeDayEnd() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -3);

        date = calendar.getTime();

        SimpleDateFormat format = getDateFormat("yyyy-MM-dd");

        StringBuffer time = new StringBuffer();

        String dateString  = format.format(date);

        time.append(dateString).append(" ").append("23:59:59");

        return sdf.parse(time.toString());
    }

    /**
     * 获取昨天最后时间
     * @return
     */
    public static Date getYesterdayEnd () throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE, -1);

        date = calendar.getTime();

        SimpleDateFormat format = getDateFormat("yyyy-MM-dd");

        StringBuffer time = new StringBuffer();

        String dateString  = format.format(date);

        time.append(dateString).append(" ").append("23:59:59");

        return sdf.parse(time.toString());
    }

    public static Date getToday () throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(new Date()));
    }

    /**
     * 获取当前周周一日期
     * @return
     * @throws ParseException
     */
    public static Date getThisWeek () throws ParseException {
        int weeks = 0;
        int mondayPlus = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus-8);
        Date monday = currentDate.getTime();

        DateFormat df = DateFormat.getDateInstance();

        String perMonday = df.format(monday);

        return df.parse(perMonday);
    }

    /**
     * 获取当前月的第一天
     * @return
     * @throws ParseException
     */
    public static Date getThisMonth () throws ParseException {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 0);

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat sdf = getDateFormat("yyyy-MM-dd");

        String first = sdf.format(calendar.getTime());

        return sdf.parse(first);
    }

    /**
     * 获取当前时间上月第一天
     * @return
     * @throws ParseException
     */
    public static Date getLastMonth () throws ParseException {
        Calendar cal_1=Calendar.getInstance();
        cal_1.add(Calendar.MONTH, -1);

        SimpleDateFormat format = getDateFormat("yyyy-MM-dd");

        //设置为1号,当前日期既为本月第一天
        cal_1.set(Calendar.DAY_OF_MONTH,1);
        String firstDay = format.format(cal_1.getTime());

        return format.parse(firstDay);
    }

    /**
     * 获取后面30天
     * @param date
     * @return
     */
    public static Date getOneMonth (Date date,int num) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    /**
     * 获取当前年第一天
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取日期中的某数值。如获取月份
     * @param date 日期
     * @param dateType 日期格式
     * @return 数值
     */
    public static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }


    /**
     * 增加日期中某类型的某数值。如增加日期
     * @param date 日期
     * @param dateType 类型
     * @param amount 数值
     * @return 计算后日期
     */
    public static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     * @param timestamps 时间long集合
     * @return 日期
     */
    public static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>();
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                // 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的
                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    // 如果timestamps的size为2，这是差值只有一个，因此要给默认值
                    minAbsoluteValue = absoluteValues.get(0);
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {
                            minAbsoluteValue = absoluteValues.get(j);
                        } else {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                    } else if (absoluteValues.size() == 1) {
                        // 当timestamps的size为2，需要与当前时间作为参照
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        if ((Math.abs(dateOne - dateTwo)) < 100000000000L) {
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                        } else {
                            long now = new Date().getTime();
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                                timestamp = dateOne;
                            } else {
                                timestamp = dateTwo;
                            }
                        }
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }


    /**
     * 将日期字符串转化为日期。失败返回null。
     * @param date 日期字符串
     * @param parttern 日期格式
     * @return 日期
     */
    public static Date StringToDate(String date, String parttern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(parttern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为时间戳。失败返回null。
     * @param date 日期字符串
     * @param parttern 日期格式
     * @return 日期
     */
    public static long StringToGetTime(String date, String parttern) {
        long myDate = 0;
        if (date != null) {
            try {
                myDate = getDateFormat(parttern).parse(date).getTime();
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将时间戳转化为日期字符串。失败返回null。
     * @param date 日期字符串
     * @param parttern 日期格式
     * @return 日期
     */
    public static Date StringToGetTime(long date, String parttern) {
        Date myDate =null;
        if (date != 0) {
            try {
                String  myDate1 = getDateFormat(parttern).format(date);
                myDate=getDateFormat(parttern).parse(myDate1);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     * @param date 日期
     * @param parttern 日期格式
     * @return 日期字符串
     */
    public static String DateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }


    /**
     * 增加日期的年份。失败返回null。
     * @param date 日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }


    /**
     * 增加日期的月份。失败返回null。
     * @param date 日期
     * @param yearAmount 增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }



    /**
     * 增加日期的天数。失败返回null。
     * @param date 日期
     * @param dayAmount 增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }



    /**
     * 增加日期的小时。失败返回null。
     * @param date 日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }



    /**
     * 增加日期的分钟。失败返回null。
     * @param date 日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int hourAmount) {
        return addInteger(date, Calendar.MINUTE, hourAmount);
    }


    /**
     * 增加日期的秒钟。失败返回null。
     * @param date 日期
     * @param hourAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int hourAmount) {
        return addInteger(date, Calendar.SECOND, hourAmount);
    }


    /**
     * 获取日期的年份。失败返回0。
     * @param date 日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }


    /**
     * 获取日期的月份。失败返回0。
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH);
    }


    /**
     * 获取日期的天数。失败返回0。
     * @param date 日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }


    /**
     * 获取日期的小时。失败返回0。
     * @param date 日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }


    /**
     * 获取日期的分钟。失败返回0。
     * @param date 日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     * @param date 日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }


    /**
     * 根据当前时间和相差时间获取开始时间
     */
    public static Date getBeforeDate(Date date,long time){
        time=time*60*60*1000;
        long beforeTime=Math.abs(date.getTime()-time);
        return StringToGetTime(beforeTime,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据当前时间和相差时间获取开始时间
     */
    public static long getDateDiff(Date date,Date time){
        long result= date.getTime()-time.getTime();
        return result;
    }


    /**获取BBIN平台注单时间进行截取  日期   时分秒*/
    public static Map<String,String> getBBINRecordBetDate(String startDate,String endDate) throws ParseException{
        SimpleDateFormat sdfs = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Calendar starCal = Calendar.getInstance();
        starCal.setTime(sdfss.parse(startDate));
        starCal.add(Calendar.HOUR_OF_DAY, -12);
        starCal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(sdfss.parse(endDate));
        endCal.add(Calendar.HOUR_OF_DAY, -12);
        endCal.getTime();

        Date ssDate = starCal.getTime();
        Date eeDate = endCal.getTime();

        String sDate = sdf.format(ssDate);
        String eDate = sdf.format(eeDate);
        String datee = sdfs.format(eeDate);

        Map<String,String> dateMap = new HashMap<String,String>();
        dateMap.put("date", datee);
        dateMap.put("starttime", sDate);
        dateMap.put("endtime", eDate);
        return dateMap;
    }
}
