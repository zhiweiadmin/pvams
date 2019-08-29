package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.PowerGenerateStatMapper;
import com.goodpower.pvams.mapper.PowerWeekRateMapper;
import com.goodpower.pvams.model.PowerGenerateStat;
import com.goodpower.pvams.model.PowerModel;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PowerStatService {

    public static final Integer THEORY_POWER = 1;
    public static final Integer FUZHAO_POWER = 2;
    public static final Integer REAL_POWER = 3;
    public static final Integer THEORY_HOUR = 4;
    public static final Integer FUZHAO_HOUR = 5;
    public static final Integer REAL_HOUR = 6;

    @Autowired
    PowerGenerateStatMapper powerStatMapper;

    @Autowired
    PowerWeekRateMapper powerWeekRateMapper;

    public JSONObject getPowerGeneration(Long stationId, String startDate,String endDate,Integer type) throws ParseException {
        long time1 = System.currentTimeMillis();
        List<PowerModel> statList = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(endDate));
        calendar.add(Calendar.MONTH, 1);
        Date nextDate = calendar.getTime();
        if(type == 0){
            List<Map<String,Object>> dataList = powerStatMapper.queryPowerStatByMonth(stationId,startDate,sdf.format(nextDate));
            statList = getLastData(dataList,type);
        }else if(type == 1){
            List<Map<String,Object>> dataList = powerStatMapper.queryPowerStatByQuarter(stationId,startDate,sdf.format(nextDate));
            statList = getLastData(dataList,type);
        }
        JSONObject data = new JSONObject();
        data.put("list",statList);
        return data;
    }

    /**
     * 获取年发电量
     * @param stationId
     * @return
     */
    public JSONObject getYearPowerStat(Long stationId){
        List<Map<String,Object>> statList = powerStatMapper.getYearPowerStat(stationId);
        List<PowerModel> dataList = getLastData(statList,2);
        JSONObject data = new JSONObject();
        data.put("list",dataList);
        return data;
    }

    public JSONObject getYearMonthPowerStat(Long stationId,Long year){
        List<Map<String,Object>> statList = powerStatMapper.getMonthPowerStat(stationId,year);
        List<PowerModel> dataList = getLastData(statList,0);
        JSONObject data = new JSONObject();
        data.put("list",dataList);
        return data;
    }

    public void saveExcelData(Long stationId, Workbook workbook) throws ParseException {
        Sheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 1) {
            for (int i = 2; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                String excelDate = getCellDate(row.getCell(0));
                if(StringUtils.isBlank(excelDate)){
                    continue;
                }
                for(int j=1;j < row.getLastCellNum() ;j++){
                    Date statDate = sdf.parse(excelDate);
                    Cell cell = row.getCell(j);
                    if(cell == null){
                        continue;
                    }
                    String statVal = getCellValue(cell);
                    if(StringUtils.isBlank(statVal)){
                        continue;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setFirstDayOfWeek(Calendar.MONDAY);//周一是每周的第一天
                    calendar.setTime(statDate);
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int quarter = CommonService.getMonthQuarter(month);
                    int week = calendar.get(Calendar.WEEK_OF_YEAR);

                    PowerGenerateStat stat = new PowerGenerateStat();
                    stat.setStationId(stationId);
                    stat.setYear(String.valueOf(year));
                    stat.setMonth(String.valueOf(month));
                    stat.setQuarter(String.valueOf(quarter));
                    stat.setStatType(String.valueOf(j));
                    stat.setStatDate(sdf.parse(excelDate));
                    stat.setWeek(String.valueOf(week));
                    BigDecimal bd =new BigDecimal(statVal);
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    stat.setStatVal(bd);
                    powerStatMapper.insert(stat);
                }
            }
        }
    }

    public String getCellValue(Cell cell){
        String value = null;
        DataFormatter formatter = new DataFormatter();
        switch (cell.getCellTypeEnum()) {
            case FORMULA:
                try {
                    value = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    value = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                value = formatter.formatCellValue(cell);
                break;
        }
        return  value;
    }

    public String getCellDate(Cell currentCell) {
        String currentCellValue = "";
        // 判断单元格数据是否是日期
        if ("yyyy/mm;@".equals(currentCell.getCellStyle().getDataFormatString())
                || "m/d/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yy/m/d".equals(currentCell.getCellStyle().getDataFormatString())
                || "mm/dd/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "dd-mmm-yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yyyy/m/d".equals(currentCell.getCellStyle().getDataFormatString())) {
            if (DateUtil.isCellDateFormatted(currentCell)) {
                // 用于转化为日期格式
                Date d = currentCell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                currentCellValue = formater.format(d);
            }
        } else {
            // 不是日期原值返回
            currentCellValue = currentCell.toString();
        }
        return currentCellValue;
    }

//    public List<PowerModel> dealDataNew(List<Map<String,Object>> dataList,Integer dateType){
//        List<PowerModel> statDataList = Lists.newArrayList();
//        for(int i=0;i<dataList.size();i++){
//            Map<String,Object> dataMap = dataList.get(i);
//            Integer year = Integer.parseInt(String.valueOf(dataMap.get("year")));
//            Integer statType = Integer.parseInt(String.valueOf(dataMap.get("statType")));
//            BigDecimal statValSum = new BigDecimal(String.valueOf(dataMap.get("statValSum")));
//            Integer dateVal;
//            if(dateType == 0){
//                dateVal = Integer.parseInt(String.valueOf(dataMap.get("month")));
//            }else{
//                dateVal = Integer.parseInt(String.valueOf(dataMap.get("quarter")));
//            }
//            if(statDataList.size()==0){
//                PowerModel powerModel = new PowerModel();
//                statDataList.add(powerModel);
//            }
//            PowerModel powerModel = statDataList.get(statDataList.size()-1);
//            if((null == powerModel.getYear() && null == powerModel.getDateVal())
//              || (powerModel.getYear().equals(year) && powerModel.getDateVal().equals(dateVal))){
//                setModelValue(powerModel,year,dateType,dateVal,statType,statValSum);
//            }else if(!powerModel.getYear().equals(year) || !powerModel.getDateVal().equals(dateVal)){
//                PowerModel powerModelTmp = new PowerModel();
//                setModelValue(powerModelTmp,year,dateType,dateVal,statType,statValSum);
//                statDataList.add(powerModelTmp);
//            }
//        }
//        return statDataList;
//    }

    public void setModelValue(PowerModel powerModel,Integer year,Integer dateType,Integer dateVal,Integer statType,BigDecimal statValSum){
        powerModel.setYear(year);
        powerModel.setDateType(dateType);
        powerModel.setDateVal(dateVal);
        switch (statType){
            case 1 :
                powerModel.setPowerTheoryVal(statValSum);
                break;
            case 2 :
                powerModel.setPowerPlanVal(statValSum);
                break;
            case 3 :
                powerModel.setPowerRealVal(statValSum);
                break;
            case 4 :
                powerModel.setTheoryHour(statValSum);
                break;
            case 5 :
                powerModel.setFuzhaoHour(statValSum);
                break;
            case 6 :
                powerModel.setRealHour(statValSum);
                break;
        }
    }

    public List<PowerModel> dealDataAgain(List<PowerModel> statList){
        for(PowerModel powerModel : statList){
            if(powerModel.getPowerPlanVal() == null){
                powerModel.setPowerPlanVal(new BigDecimal(0));
            }
            if(powerModel.getPowerRealVal() == null){
                powerModel.setPowerRealVal(new BigDecimal(0));
            }
            if(powerModel.getPowerTheoryVal() == null){
                powerModel.setPowerTheoryVal(new BigDecimal(0));
            }
            if(powerModel.getFuzhaoHour() == null){
                powerModel.setFuzhaoHour(new BigDecimal(0));
            }
            if(powerModel.getTheoryHour() == null){
                powerModel.setTheoryHour(new BigDecimal(0));
            }
            if(powerModel.getRealHour() == null){
                powerModel.setRealHour(new BigDecimal(0));
            }
        }
        return statList;
    }

    public List<PowerModel> getLastData(List<Map<String,Object>> statList,Integer dateType){
        List<String> dateList = copyDateList(statList,dateType);
        Map<String,List<Map<String,Object>>> dataMap = dealInitialData(statList,dateType);
        List<PowerModel> newDataList = Lists.newArrayList();
        for(String dateKey : dateList){
            if(dataMap.get(dateKey) != null && !dataMap.get(dateKey).isEmpty()){
                List<Map<String,Object>> mapList = dataMap.get(dateKey);
                PowerModel powerModel = new PowerModel();
                for(Map<String,Object> map : mapList){
                    setModelValue(powerModel,map,dateType);
                }
                newDataList.add(powerModel);
            }
        }
        return newDataList;
    }

    public void setModelValue(PowerModel powerModel,Map<String,Object> itemMap,Integer dateType){
        Integer year = Integer.valueOf(String.valueOf(itemMap.get("year")));
        if(dateType == 0){
            Integer dateParam = Integer.valueOf(String.valueOf(itemMap.get("month")));
            powerModel.setDateVal(dateParam);
        }else if(dateType == 1){
            Integer dateParam = Integer.valueOf(String.valueOf(itemMap.get("quarter")));
            powerModel.setDateVal(dateParam);
        }else if(dateType == 2){
            Integer dateParam = Integer.valueOf(String.valueOf(itemMap.get("year")));
            powerModel.setDateVal(dateParam);
        }
        powerModel.setDateType(dateType);
        powerModel.setYear(year);
        Integer statType = Integer.valueOf(String.valueOf(itemMap.get("statType")));
        BigDecimal statValSum = new BigDecimal(String.valueOf(itemMap.get("statValSum")));
        switch (statType){
            case 1 :
                powerModel.setPowerTheoryVal(statValSum);
                break;
            case 2 :
                powerModel.setPowerPlanVal(statValSum);
                break;
            case 3 :
                powerModel.setPowerRealVal(statValSum);
                break;
            case 4 :
                powerModel.setTheoryHour(statValSum);
                break;
            case 5 :
                powerModel.setFuzhaoHour(statValSum);
                break;
            case 6 :
                powerModel.setRealHour(statValSum);
                break;
        }
    }

    public Map<String,List<Map<String,Object>>> dealInitialData(List<Map<String,Object>> statList,Integer dateType){
        Map<String,List<Map<String,Object>>> dataMap = Maps.newHashMap();
        for(Map<String,Object> statMap : statList){
            Object year = statMap.get("year");
            Object dateVal;
            String dateKey = "";
            if(dateType == 0){
                dateVal = statMap.get("month");
                dateKey = year+"-"+dateVal;
            }else if(dateType == 1){
                dateVal = statMap.get("quarter");
                dateKey = year+"-"+dateVal;
            }else if(dateType == 2){
                dateKey = String.valueOf(year);
            }
            if(dataMap.get(dateKey) == null){
                List<Map<String,Object>> dataList = Lists.newArrayList();
                dataList.add(statMap);
                dataMap.put(dateKey,dataList);
            }else{
                List<Map<String,Object>> dataList = dataMap.get(dateKey);
                dataList.add(statMap);
            }
        }
        return dataMap;
    }


    public List<String> copyDateList(List<Map<String,Object>> statList,Integer dateType){
        List<String> dateList = Lists.newArrayList();
        for(Map<String,Object> statMap : statList){
            Object year = statMap.get("year");
            String dateKey = "";
            Object dateVal = null;
            if(dateType == 0){
                dateVal = statMap.get("month");
                dateKey = year+"-"+dateVal;
            }else if(dateType == 1){
                dateVal = statMap.get("quarter");
                dateKey = year+"-"+dateVal;
            }else if(dateType == 2){//年
                dateKey = String.valueOf(year);
            }else if(dateType == 3){//周
                //TODO
            }
            if(!dateList.contains(dateKey)){
                dateList.add(dateKey);
            }
        }
        return dateList;
    }

    /**
     * 获取某段时间内的理论值和实际值
     * type 0:week 1:month 2:quarter 3:year
     */
    public JSONObject getPowerProgress(Map<String,Object> paramMap) {
        JSONObject jsonObject = new JSONObject();
        //获取实际发电量
        paramMap.put("statType", "3");//实际发电量(全年)
        Map<String, Object> realStatMap = powerStatMapper.queryPowerStatByFields(paramMap);
        Float realVal = 0F;
        Float theoryVal = 0F;
        if (realStatMap != null) {
            realVal = Float.parseFloat(realStatMap.get("statValSum").toString());
            jsonObject.put("powerRealVal",realVal);
        }
        //获取理论发电量
        paramMap.put("statType", "1");
        Map<String, Object> theoryStatMap = powerStatMapper.queryPowerStatByFields(paramMap);
        if (theoryStatMap != null) {
            theoryVal = Float.parseFloat(theoryStatMap.get("statValSum").toString());
            jsonObject.put("powerTheoryVal",theoryVal);
        }
        if (theoryVal != 0) {
            float num= (float)realVal/theoryVal;
            DecimalFormat df = new DecimalFormat("0.0000");
            Float percent = Float.parseFloat(df.format(num)) * 100;
            jsonObject.put("powerProgress",percent);
        }else{
            jsonObject.put("powerProgress",0);
        }
        return jsonObject;
    }

    public Float  getDateProgress(Integer type,Map<String,Object> paramMap) throws ParseException {
        Float dateProgress = 0F;
        int nowYear = com.goodpower.pvams.util.DateUtil.getCurYear();
        if(type == 0){
            Integer year = Integer.parseInt(paramMap.get("year").toString());
            Integer week = Integer.parseInt(paramMap.get("week").toString());
            if(year < nowYear){
                dateProgress = 100F;
                return dateProgress;
            }else if (year.intValue() == nowYear){
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);//周一是每周的第一天
                calendar.setTime(date);
                int nowWeek = calendar.get(Calendar.WEEK_OF_YEAR);//获取当前时间是今年的第几周
                if(week < nowWeek){
                    return 100F;
                }else if (week == nowWeek){
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    int whichDay = cal.get(Calendar.DAY_OF_WEEK);//获取当前时间是当前周的低级天
                    float num= (float)whichDay/7;
                    DecimalFormat df = new DecimalFormat("0.0000");
                    return Float.parseFloat(df.format(num))*100;
                }else{
                    return 0F;
                }
            }
        }else if(type == 1){
            Integer year = Integer.parseInt(paramMap.get("year").toString());
            Integer month = Integer.parseInt(paramMap.get("month").toString());
            if(year < nowYear){
                dateProgress = 100F;
                return dateProgress;
            }else{
                Integer nowMonth = com.goodpower.pvams.util.DateUtil.getCurMonth();
                if(month < nowMonth){
                    return 100F;
                }else if (month == nowMonth){
                    int monthDays = com.goodpower.pvams.util.DateUtil.getMaxDaysOfMonth(new Date());
                    int whichDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                    float num= (float)whichDay/monthDays;
                    DecimalFormat df = new DecimalFormat("0.0000");
                    return Float.parseFloat(df.format(num))*100;
                }else{
                    return 0F;
                }
            }
        }else if(type == 2){
            Integer year = Integer.parseInt(paramMap.get("year").toString());
            Integer quarter = Integer.parseInt(paramMap.get("quarter").toString());
            if(year < nowYear){
                dateProgress = 100F;
                return dateProgress;
            }else if (year.intValue() == nowYear){
                Integer month = com.goodpower.pvams.util.DateUtil.getCurMonth();
                Integer nowQuarter = getQuarter(month);
                if(quarter < nowQuarter){
                    return 100F;
                }else if(quarter == nowQuarter){
                    //获取当前时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String quarterFirstDay = getQuarterFirstMonth(year,nowQuarter);
                    //计算两个日期差了多少天
                    Date nowDate = new Date();
                    Date quarterFirstDate = sdf.parse(quarterFirstDay);
                    long daysBetween = (nowDate.getTime()-quarterFirstDate.getTime()+1000000)/(60*60*24*1000) + 1;
                    String nextQuarterFirstDay = getQuarterFirstMonth(year,nowQuarter+1);
                    Date nextQuarterFirstDate = sdf.parse(nextQuarterFirstDay);
                    long daysBetween2=(nextQuarterFirstDate.getTime()-quarterFirstDate.getTime()+1000000)/(60*60*24*1000);
                    float num= (float)daysBetween/daysBetween2;
                    DecimalFormat df = new DecimalFormat("0.0000");
                    return Float.parseFloat(df.format(num))*100;
                }else{
                    return 0F;
                }
            }else{
                return 0F;
            }
        }else if(type == 3){
            Integer year = Integer.parseInt(paramMap.get("year").toString());
            if(year < nowYear) {
                return 100F;
            }else if(year == nowYear){
                int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);//今年已经过去多少天
                int yearDay = com.goodpower.pvams.util.DateUtil.getMaxDaysOfYear(nowYear);//今年总共多少天
                float num= (float)day/yearDay;
                DecimalFormat df = new DecimalFormat("0.0000");
                return Float.parseFloat(df.format(num))*100;
            }else{
                return 0F;
            }
        }
        return 0F;
    }

    public Integer getQuarter(int m){
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

    public String getQuarterFirstMonth(int year,int n){
        if(n == 1){
            return year+"-"+"01-01";
        }else if(n == 2){
            return year+"-"+"04-01";
        }else if(n == 3){
            return year+"-"+"07-01";
        }else if(n == 4){
            return year+"-"+"10-01";
        }
        return null;
    }

    /**
     * 获取当天是这周的第几天
     * @param moveNum
     * @return
     */
    public int getWeek(int moveNum){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        int[] weekArry = move(moveNum);
        int dayWeek = weekArry[i-1];
        return dayWeek;
    }

    /**
     * 如果设置周日为周一的话   num=0  默认就是周日
     * 周一  移动1
     * 周二  移动2
     * 周三  移动3
     * 周四  移动4
     * 周六  移动6
     * 周日  移动0
     * @param num
     * @return
     */
    public static int[] move(int num){
        int[] weekArray = {1,2,3,4,5,6,7};
        for(int k=1;k<=num;k++){
            int tmp = weekArray[6];
            for(int i=weekArray.length-1;i>=0;i--){
                if(i == 0){
                    weekArray[0] = tmp;
                }else{
                    weekArray[i] = weekArray[i-1];
                }
            }
        }
        return weekArray;
    }

    public JSONObject getWeekProgress(Long stationId) throws ParseException {
        //获取今天是这周的第几天
        int num1 = getWeek(1);
        //获取这周的第一个天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        Date weekFirstDay = com.goodpower.pvams.util.DateUtil.getDay(sdf.parse(now),-(num1-1));
        Date weekLastDay = com.goodpower.pvams.util.DateUtil.getDay(sdf.parse(now),7-num1);
        //获取周发电量进度
        Map<String,Object> param = Maps.newHashMap();
        param.put("startDate",sdf.format(weekFirstDay));
        param.put("endDate",sdf.format(weekLastDay));
        param.put("stationId",stationId);
        param.put("statType", "1");//理論
        Map<String, Object> theoryStatMap = powerStatMapper.queryPowerStatByFields(param);
        param.put("endDate",now);
        param.put("statType", "3");//实际发电量
        Map<String, Object> realStatMap = powerStatMapper.queryPowerStatByFields(param);

        Float theoryVal = 0F;
        Float realVal = 0F;
        JSONObject jsonObject = new JSONObject();
        if (theoryStatMap != null) {
            theoryVal = Float.parseFloat(String.valueOf(theoryStatMap.get("statValSum")));
        }
        if (realStatMap != null) {
            realVal = Float.parseFloat(String.valueOf(realStatMap.get("statValSum")));
        }
        if (theoryVal != 0) {
            float num= (float)realVal/theoryVal;
            DecimalFormat df = new DecimalFormat("0.0000");
            Float percent = Float.parseFloat(df.format(num)) * 100;
            jsonObject.put("powerProgress",percent);
        }else{
            jsonObject.put("powerProgress",0);
        }

        float num= (float)num1/7;
        DecimalFormat df = new DecimalFormat("0.0000");
        Float percent = Float.parseFloat(df.format(num)) * 100;
        jsonObject.put("dateProgress",percent);

        return jsonObject;
    }

    public JSONObject getMonthProgress(Long stationId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        //获取今天是这周的第几天
        int offset = 1;//从电站基础信息里获取
        String monthStart = com.goodpower.pvams.util.DateUtil.getMonthStart(offset);
        Date monthEndDate = com.goodpower.pvams.util.DateUtil.getDay(sdf.parse(monthStart),30);
        String monthEnd = sdf.format(monthEndDate);
        //获取这周的第一个天

        //获取周发电量进度
        Map<String,Object> param = Maps.newHashMap();
        param.put("startDate",monthStart);
        param.put("endDate",monthEnd);
        param.put("stationId",stationId);
        param.put("statType", "1");//理論
        Map<String, Object> theoryStatMap = powerStatMapper.queryPowerStatByFields(param);
        param.put("endDate",now);
        param.put("statType", "3");//实际发电量
        Map<String, Object> realStatMap = powerStatMapper.queryPowerStatByFields(param);

        Float theoryVal = 0F;
        Float realVal = 0F;
        JSONObject jsonObject = new JSONObject();
        if (theoryStatMap != null) {
            theoryVal = Float.parseFloat(String.valueOf(theoryStatMap.get("statValSum")));
        }
        if (realStatMap != null) {
            realVal = Float.parseFloat(String.valueOf(realStatMap.get("statValSum")));
        }
        if (theoryVal != 0) {
            Float percent = getPercent(realVal,theoryVal);
            jsonObject.put("powerProgress",percent);
        }else{
            jsonObject.put("powerProgress",0);
        }
        //计算已经过去多少天
        long daysBetween = (sdf.parse(now).getTime()-sdf.parse(monthStart).getTime()+1000000)/(60*60*24*1000) + 1;
        Float percent = getPercent(daysBetween,30);
        jsonObject.put("dateProgress",percent);

        return jsonObject;
    }

    public JSONObject getQuarterProgress(Long stationId) throws ParseException {
        //获取当前属于哪个季度
        Integer month = com.goodpower.pvams.util.DateUtil.getCurMonth();
        Integer quarter = getQuarter(month);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        //获取周发电量进度
        Map<String,Object> param = Maps.newHashMap();
        param.put("quarter",quarter);
        param.put("stationId",stationId);
        param.put("statType", "1");//理論
        Map<String, Object> theoryStatMap = powerStatMapper.queryPowerStatByFields(param);
        param.put("endDate",now);
        param.put("statType", "3");//实际发电量
        Map<String, Object> realStatMap = powerStatMapper.queryPowerStatByFields(param);

        Float theoryVal = 0F;
        Float realVal = 0F;
        JSONObject jsonObject = new JSONObject();
        if (theoryStatMap != null) {
            theoryVal = Float.parseFloat(String.valueOf(theoryStatMap.get("statValSum")));
        }
        if (realStatMap != null) {
            realVal = Float.parseFloat(String.valueOf(realStatMap.get("statValSum")));
        }
        if (theoryVal != 0) {
            Float percent = getPercent(realVal,theoryVal);
            jsonObject.put("powerProgress",percent);
        }else{
            jsonObject.put("powerProgress",0);
        }
        Long quarterDays = com.goodpower.pvams.util.DateUtil.getCurQuarterDays();
        Long quarterPastDays = com.goodpower.pvams.util.DateUtil.getCurQuarterPastDays();

        float percent = getPercent(quarterPastDays,quarterDays);
        jsonObject.put("dateProgress",percent);

        return jsonObject;
    }

    public JSONObject getYearProgress(Long stationId) throws ParseException {
        //获取当前属于哪个季度
        int year = com.goodpower.pvams.util.DateUtil.getCurYear();
        int pastDays = com.goodpower.pvams.util.DateUtil.getCurYearPastDays();
        int yearDays = com.goodpower.pvams.util.DateUtil.getCurYearDays();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        //获取周发电量进度
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("statType", "1");//理论发电量
        param.put("year",year);
        Map<String, Object> theoryStatMap = powerStatMapper.queryPowerStatByFields(param);
        param.put("endDate",now);
        param.put("statType", "3");//实际发电量
        Map<String, Object> realStatMap = powerStatMapper.queryPowerStatByFields(param);

        Float theoryVal = 0F;
        Float realVal = 0F;
        JSONObject jsonObject = new JSONObject();
        if (theoryStatMap != null) {
            theoryVal = Float.parseFloat(String.valueOf(theoryStatMap.get("statValSum")));
        }
        if (realStatMap != null) {
            realVal = Float.parseFloat(String.valueOf(realStatMap.get("statValSum")));
        }
        if (theoryVal != 0) {
            Float percent = getPercent(realVal,theoryVal);
            jsonObject.put("powerProgress",percent);
        }else{
            jsonObject.put("powerProgress",0);
        }
        float percent = getPercent(pastDays,yearDays);
        jsonObject.put("dateProgress",percent);

        return jsonObject;
    }

    public float getPercent(int a,int b){
        float num= (float)a/b;
        DecimalFormat df = new DecimalFormat("0.0000");
        return Float.parseFloat(df.format(num)) * 100;
    }

    public float getPercent(float a,float b){
        float num= (float)a/b;
        DecimalFormat df = new DecimalFormat("0.0000");
        return Float.parseFloat(df.format(num)) * 100;
    }

}
