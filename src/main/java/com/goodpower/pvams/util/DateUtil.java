package com.goodpower.pvams.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DateUtil {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2019-09-01");
        String startDate = getWeekStartDate(date);
        String endDate = getWeekEndDate(date);
        System.out.println(startDate);
        System.out.println(endDate);
    }

    public static JSONArray getDate() throws ParseException {
        return dealDate(getDateWeek());
    }

    public static JSONArray getDateWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date firstDay = getCurrYearFirst();
        Date lastDay = getCurrYearLast();
        List<Date> dateList = Lists.newArrayList();
        Date firstMonday = null;
        while (!firstDay.after(lastDay)){
            if(1 == getDayWeek(firstDay)){
                firstMonday = firstDay;
                break;
            }
            firstDay = addDay(firstDay,1);
        }
        int step = 7;
        while (!firstMonday.after(lastDay)){
            dateList.add(firstMonday);
            firstMonday = addDay(firstMonday,step);
        }
        JSONArray dateArray = new JSONArray();
        for(int i = 0;i<dateList.size();i++){
            Date date = dateList.get(i);
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH) + 1;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("week",i+1);
            jsonObject.put("month",month);
            jsonObject.put("firstDay",sdf.format(date));
            dateArray.add(jsonObject);
        }
        return dateArray;
    }

    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    private static Date addDay(Date date, int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, num);
        return c.getTime();
    }

    public static int getDayWeek(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static JSONArray dealDate(JSONArray jsonArray) throws ParseException {
        Map<String,Object> map = Maps.newHashMap();
        List<String> monthList = Lists.newArrayList();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject weekObject = jsonArray.getJSONObject(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String firstDay = weekObject.getString("firstDay");
            Date monday = sdf.parse(firstDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(monday);
            int month = calendar.get(Calendar.MONTH)+1;

            Date sunDay = getDay(monday,6);
            calendar.setTime(sunDay);
            int month2 = calendar.get(Calendar.MONTH)+1;
            String newMonth = "";
            if(month != month2){
                newMonth = month + "/" +month2;
            }else{
                newMonth = month + "";
            }
            if(!monthList.contains(newMonth)){
                monthList.add(newMonth);
            }
            weekObject.remove("month");
            if(map.get(newMonth) == null){
                JSONArray weekArray = new JSONArray();
                weekArray.add(weekObject);
                map.put(newMonth,weekArray);
            }else{
                if(map.get(newMonth) instanceof JSONArray){
                    JSONArray weekArray = (JSONArray)map.get(newMonth);
                    weekArray.add(weekObject);
                    map.put(newMonth,weekArray);
                }

            }
        }
        JSONObject jsonObject = new JSONObject(map);
        System.out.print(jsonObject.toJSONString());
        JSONArray weekArray = new JSONArray();
        for(String month : monthList){
            Object children = map.get(month);
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("month",month);
            jsonObject1.put("children",children);
            weekArray.add(jsonObject1);
        }
        return weekArray;
    }

    public static Integer getCurMonth(){
        Date date = new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    public static Integer getTarMonth(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    public static Integer getCurYear(){
        Date date = new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static Integer getTarYear(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static Integer getCurWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static Integer getTarWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getMaxDaysOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取今年已经过去多少天
     * @return
     */
    public static int getYearDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获获取某天是当月的第几天
     * @return
     */
    public static int getMonthDay(){
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间进度
     * @return
     */
    public static Float getCurDateYearProgress(){
        int year = getCurYear();
        int yearDay = DateUtil.getYearDay();
        int maxDay = DateUtil.getMaxDaysOfYear(year);
        float num= (float)yearDay/maxDay;
        DecimalFormat df = new DecimalFormat("0.0000");
        return Float.parseFloat(df.format(num))*100;
    }


    public static Float getCurDateMonthProgress(){
        int monthDay = DateUtil.getMonthDay();
        int maxDay = DateUtil.getMaxDaysOfMonth(new Date());
        float num= (float)monthDay/maxDay;
        DecimalFormat df = new DecimalFormat("0.0000");
        return Float.parseFloat(df.format(num))*100;
    }

    public static String getYearLastDay(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(currYearLast);
    }

    public static String getYearFirstDay(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(currYearFirst);
    }

    public static String getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year); //设置年份
        cal.set(Calendar.MONTH, month-1);  //设置月份
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH); //获取某月最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);    //设置日历中月份的最小天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }
    public static String getLastDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year); //设置年份
        cal.set(Calendar.MONTH, month-1); //设置月份
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);    //获取某月最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);   //设置日历中月份的最大天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //格式化日期
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 获取每月多少天
     * @param date
     * @return
     */
    public static int getMaxDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static Date getDay(Date date,int num){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, num);
        return c.getTime();
    }

    /**
     * 获取当前季度多少天
     */
    public static Long getCurQuarterDays() throws ParseException {
        Date date = new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int quarter = getQuarter(month);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        if(quarter == 1){
            startDate = sdf.parse(year+"-01-01");
            endDate = sdf.parse(year+"-04-01");
        }else if(quarter == 2){
            startDate = sdf.parse(year+"-04-01");
            endDate = sdf.parse(year+"-07-01");
        }else if(quarter == 3){
            startDate = sdf.parse(year+"-07-01");
            endDate = sdf.parse(year+"-10-01");
        }else if(quarter == 4){
            startDate = sdf.parse(year+"-10-01");
            endDate = sdf.parse((year+1)+"-01-01");
        }
        Long daysBetween=(endDate.getTime()-startDate.getTime()+1000000)/(60*60*24*1000);
        return daysBetween;
    }

    /**
     * 获取当前季度已经过去了多少天
     * @return
     * @throws ParseException
     */
    public static Long getCurQuarterPastDays() throws ParseException {
        Date date = new Date();
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int quarter = getQuarter(month);
        String start = null;
        if(quarter == 1){
            start = year+"-01-01";
        }else if(quarter == 2){
            start = year+"-04-01";
        }else if(quarter == 3){
            start = year+"-07-01";
        }else if(quarter == 4){
            start = year+"-10-01";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse(start);
        Long daysBetween=(date.getTime()-startDate.getTime()+1000000)/(60*60*24*1000)+1;
        return daysBetween;
    }

    /**
     * 获取这个月已经过去多少天
     * @return
     */
    public static int getCurMonthPastDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取今年已经过去了多少天
     * @return
     * @throws ParseException
     */
    public static int getCurYearPastDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_YEAR)+1;
    }

    /**
     * 获取今年有多少天
     * @return
     */
    public static int getCurYearDays(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static Integer getQuarter(Integer m){
        if(m == null){
            return null;
        }
        if(m>=1 && m<=3){
            return 1;
        }else if(m>=4 && m<=6){
            return 2;
        }else if(m>=7 && m<=9){
            return 3;
        }else if(m>=10 && m<=12){
            return 4;
        }
        return null;
    }

    /**
     * 获取某月的第一天
     * @param offset
     */
    public static String getMonthStart(int offset){
        String newNum = "";
        if(String.valueOf(offset).length() == 1){
            newNum = "0"+offset;
        }else{
            newNum = String.valueOf(offset);
        }
        //num设置每个月从几号开始
        int year = com.goodpower.pvams.util.DateUtil.getCurYear();
        int month = com.goodpower.pvams.util.DateUtil.getCurMonth();
        int monthPastDays = com.goodpower.pvams.util.DateUtil.getCurMonthPastDays();
        if(offset > monthPastDays){
            String monthNew = "";
            //如果设置的是27号是每个月的第一天  今天是25号 那么找到上个月的27号  计算已经过去过少天
            if(String.valueOf(month-1).length() == 1){
                //补0
                monthNew = "0"+(month-1);
            }else{
                monthNew = (month-1) + "";
            }
            return year+"-"+monthNew+"-"+newNum;
        }else{
            if(String.valueOf(month).length() == 1){
                return year+"-0"+month+"-"+newNum;
            }else{
                return year+"-"+month+"-"+newNum;
            }
        }
    }

    public static String getWeekStartDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week == 0){
            week = 7;
        }
        Date startDate = getDay(date,1-week);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(startDate);
    }

    public static String getWeekEndDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week == 0){
            week = 7;
        }
        Date endDate =  getDay(date,7-week);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(endDate);
    }

}
