package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.Page;
import com.goodpower.pvams.mapper.PowerGenerateStatMapper;
import com.goodpower.pvams.mapper.StationDataStatMapper;
import com.goodpower.pvams.mapper.StationDeviceStatMapper;
import com.goodpower.pvams.model.PowerGenerateStat;
import com.goodpower.pvams.model.StationDataStat;
import com.goodpower.pvams.model.StationDeviceStat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StationDataStatService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String THEORY_POWER = "1";
    public static final String FUZHAO_POWER = "2";
    public static final String REAL_POWER = "3";
    public static final String THEORY_HOUR = "4";
    public static final String FUZHAO_HOUR = "5";
    public static final String REAL_HOUR = "6";

    @Autowired
    StationDataStatMapper dataStatMapper;

    @Autowired
    StationDeviceStatMapper deviceStatMapper;

    @Autowired
    PowerGenerateStatMapper powerStatMapper;

    /**
     * 获取统计数据
     * @param stationId
     * @param year
     * @param month
     * @return
     */
    public JSONObject getMonthStatData(Long stationId,int year, int month){
        Map<String,Object> param = Maps.newHashMap();
        param.put("year",year);
        param.put("month",month);
        param.put("stationId",stationId);
        List<Map<String,Object>> dataList =  dataStatMapper.queryMonthStat(param);
        List<Object> statNameList = Lists.newArrayList();
        Map<String,Map<String,Object>> dataMap = Maps.newHashMap();
        for(Map<String,Object> map : dataList){
            String name = map.get("stat_name").toString();
            if(!statNameList.contains(map.get("stat_name"))){
                statNameList.add(map.get("stat_name"));
            }
            if(dataMap.containsKey(name)){
                dataMap.get(name).put(String.valueOf(map.get("stat_date")),map);
            }else{
                Map<String,Object> map1 = Maps.newHashMap();
                map1.put(String.valueOf(map.get("stat_date")),map);
                dataMap.put(name,map1);
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(String key : dataMap.keySet()){
            Map<String,Object> map = dataMap.get(key);
            for(int i=1;i<=monthDays;i++){
                String dateKey = getDateString(year,month,i);
                if(!map.containsKey(dateKey)){
                    Map<String,Object> newMap = Maps.newHashMap();
                    newMap.put("statVal",null);
                    newMap.put("date",i);
                    map.put(dateKey,newMap);
                }else{
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(map.get(dateKey));
                    jsonObject.put("statVal",jsonObject.get("stat_val"));
                    jsonObject.put("date",i);
                    jsonObject.remove("stat_val");
                    jsonObject.remove("stat_date");
                    jsonObject.remove("stat_name");
                    map.put(dateKey,jsonObject);
                }
            }
        }
        List<Object> resultList = Lists.newArrayList();
        for(Object statName : statNameList){
            Map<String,Object> statMap = dataMap.get(String.valueOf(statName));
            List<Object> statList = Lists.newArrayList();
            for(int k = 1;k <= monthDays ;k++){
                String date = getDateString(year,month,k);
                statList.add(statMap.get(date));
            }
            Map<String,Object> resultMap = Maps.newHashMap();
            resultMap.put("stats",statList);
            resultMap.put("statName",statName);
            resultList.add(resultMap);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dateLen",monthDays);
        jsonObject.put("resultData",resultList);
        return jsonObject;
    }

    /**
     * 获取统计数据
     * @param stationId
     * @param year
     * @param
     * @return
     */
    public JSONObject getYearStatData(Long stationId,int year){
        Map<String,Object> param = Maps.newHashMap();
        param.put("year",year);
        param.put("stationId",stationId);
        List<Map<String,Object>> dataList =  dataStatMapper.queryYearStat(param);
        List<Object> statNameList = Lists.newArrayList();
        Map<String,Map<String,Object>> dataMap = Maps.newHashMap();
        for(Map<String,Object> map : dataList){
            String name = map.get("stat_name").toString();
            if(!statNameList.contains(map.get("stat_name"))){
                statNameList.add(map.get("stat_name"));
            }
            if(dataMap.containsKey(name)){
                dataMap.get(name).put(String.valueOf(map.get("month")),map);
            }else{
                Map<String,Object> map1 = Maps.newHashMap();
                map1.put(String.valueOf(map.get("month")),map);
                dataMap.put(name,map1);
            }
        }
        int yearMonth = 12;
        for(String key : dataMap.keySet()){
            Map<String,Object> map = dataMap.get(key);
            for(int i=1;i<=yearMonth;i++){
                String dateKey = i+"";
                if(!map.containsKey(dateKey)){
                    Map<String,Object> newMap = Maps.newHashMap();
                    newMap.put("statVal",null);
                    newMap.put("date",i);
                    map.put(dateKey,newMap);
                }else{
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(map.get(dateKey));
                    jsonObject.put("statVal",jsonObject.get("sumVal"));
                    jsonObject.put("date",i);
                    jsonObject.remove("sumVal");
                    jsonObject.remove("month");
                    jsonObject.remove("stat_name");
                    map.put(dateKey,jsonObject);
                }
            }
        }
        List<Object> resultList = Lists.newArrayList();
        for(Object statName : statNameList){
            Map<String,Object> statMap = dataMap.get(String.valueOf(statName));
            List<Object> statList = Lists.newArrayList();
            for(int k = 1;k <= yearMonth ;k++){
                String date = k+"";
                statList.add(statMap.get(date));
            }
            Map<String,Object> resultMap = Maps.newHashMap();
            resultMap.put("stats",statList);
            resultMap.put("statName",statName);
            resultList.add(resultMap);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dateLen",12);
        jsonObject.put("resultData",resultList);
        return jsonObject;
    }

    /**
     * THEORY_POWER = 1;
     * FUZHAO_POWER = 2;
     * REAL_POWER = 3;
     * THEORY_HOUR = 4;
     * FUZHAO_HOUR = 5;
     * REAL_HOUR = 6;
     * @param statType
     * 获取每月设备统计详情
     */
    public JSONObject getMonthDeviceStatDetail(Long stationId, Integer statType, int year, int month,int pageNo,int pageSize){
        Map<String,Object> param = Maps.newHashMap();
        param.put("year",year);
        param.put("month",month);
        param.put("statType",statType);
        param.put("stationId",stationId);
//        int offset = (pageNo - 1)*pageSize;
//        int limit = pageSize;
        List<Map<String,Object>> dataList =  deviceStatMapper.getMonthDeviceStatDetail(param);
        List<Object> deviceNameList = Lists.newArrayList();
        Map<String,Map<String,Object>> dataMap = Maps.newHashMap();
        for(Map<String,Object> map : dataList){
            String name = map.get("device_name").toString();
            if(!deviceNameList.contains(map.get("device_name"))){
                deviceNameList.add(map.get("device_name"));
            }
            if(dataMap.containsKey(name)){
                dataMap.get(name).put(String.valueOf(map.get("stat_date")),map);
            }else{
                Map<String,Object> map1 = Maps.newHashMap();
                map1.put(String.valueOf(map.get("stat_date")),map);
                dataMap.put(name,map1);
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(String key : dataMap.keySet()){
            Map<String,Object> map = dataMap.get(key);
            for(int i=1;i<=monthDays;i++){
                String dateKey = getDateString(year,month,i);
                if(!map.containsKey(dateKey)){
                    Map<String,Object> newMap = Maps.newHashMap();
                    newMap.put("val",0);
                    newMap.put("date",i);
                    map.put(dateKey,newMap);
                }else{
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(map.get(dateKey));
                    jsonObject.put("val",jsonObject.get("stat_val"));
                    jsonObject.put("date",i);
                    jsonObject.remove("stat_val");
                    jsonObject.remove("stat_date");
                    jsonObject.remove("device_name");
                    map.put(dateKey,jsonObject);
                }
            }
        }
        List<Object> resultList = Lists.newArrayList();
        for(int i=0;i<deviceNameList.size();i++){
//            if(i>=0 && i < limit+offset){
//                String deviceName = String.valueOf(deviceNameList.get(i));
//                Map<String,Object> statMap = dataMap.get(deviceName);
//                List<Object> statList = Lists.newArrayList();
//                for(int k = 1;k <= monthDays ;k++){
//                    String date = getDateString(year,month,k);
//                    statList.add(statMap.get(date));
//                }
//                Map<String,Object> resultMap = Maps.newHashMap();
//                resultMap.put("stats",statList);
//                resultMap.put("deviceName",deviceName);
//                resultList.add(resultMap);
//            }
            String deviceName = String.valueOf(deviceNameList.get(i));
            Map<String,Object> statMap = dataMap.get(deviceName);
            List<Object> statList = Lists.newArrayList();
            for(int k = 1;k <= monthDays ;k++){
                String date = getDateString(year,month,k);
                statList.add(statMap.get(date));
            }
            Map<String,Object> resultMap = Maps.newHashMap();
            resultMap.put("stats",statList);
            resultMap.put("deviceName",deviceName);
            resultList.add(resultMap);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dateLen",monthDays);
        jsonObject.put("resultData",resultList);
//        Page page = new Page();
//        page.setPage(pageNo);
//        page.setPageSize(pageSize);
//        if(deviceNameList != null){
//            page.setCount(Long.parseLong(deviceNameList.size()+""));
//        }
//        jsonObject.put("page",page);
        return jsonObject;
    }

    public String getDateString(int year,int month,int day){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR,year);
        calendar1.set(Calendar.MONTH,month-1);
        calendar1.set(Calendar.DAY_OF_MONTH,day);
        return  sdf.format(calendar1.getTime());
    }

    //获取每年设备统计详情
    public JSONObject getYearDeviceStatDetail(Long stationId, Integer statType, int year,int pageNum,int pageSize){
        Map<String,Object> param = Maps.newHashMap();
        param.put("year",year);
        param.put("statType",statType);
        param.put("stationId",stationId);
//        int offset = (pageNum - 1)*pageSize;
//        int limit = pageSize;
        List<Map<String,Object>> dataList =  deviceStatMapper.getYearDeviceStatDetail(param);
        List<Object> deviceNameList = Lists.newArrayList();
        Map<String,Map<String,Object>> dataMap = Maps.newHashMap();
        for(Map<String,Object> map : dataList){
            String name = map.get("device_name").toString();
            if(!deviceNameList.contains(map.get("device_name"))){
                deviceNameList.add(map.get("device_name"));
            }
            if(dataMap.containsKey(name)){
                dataMap.get(name).put(String.valueOf(map.get("month")),map);
            }else{
                Map<String,Object> map1 = Maps.newHashMap();
                map1.put(String.valueOf(map.get("month")),map);
                dataMap.put(name,map1);
            }
        }
        int yearMonth = 12;
        for(String key : dataMap.keySet()){
            Map<String,Object> map = dataMap.get(key);
            for(int i=1;i<=yearMonth;i++){
                String dateKey = i+"";
                if(!map.containsKey(dateKey)){
                    Map<String,Object> newMap = Maps.newHashMap();
                    newMap.put("val",0);
                    newMap.put("date",i);
                    map.put(dateKey,newMap);
                }else{
                    JSONObject jsonObject = (JSONObject) JSON.toJSON(map.get(dateKey));
                    jsonObject.put("val",jsonObject.get("sumVal"));
                    jsonObject.put("date",i);
                    jsonObject.remove("sumVal");
                    jsonObject.remove("month");
                    jsonObject.remove("device_name");
                    map.put(dateKey,jsonObject);
                }
            }
        }
        List<Object> resultList = Lists.newArrayList();
        for(int i=0;i<deviceNameList.size();i++){
            //if(i>=offset && i < limit+offset){
                String deviceName = String.valueOf(deviceNameList.get(i));
                Map<String,Object> statMap = dataMap.get(String.valueOf(deviceName));
                List<Object> statList = Lists.newArrayList();
                for(int k = 1;k <= yearMonth ;k++){
                    String date = k+"";
                    statList.add(statMap.get(date));
                }
                Map<String,Object> resultMap = Maps.newHashMap();
                resultMap.put("stats",statList);
                resultMap.put("deviceName",deviceName);
                resultList.add(resultMap);
            //}
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dateLen",12);
        jsonObject.put("resultData",resultList);
        return jsonObject;
    }


    public void importDeviceData(Long stationId, Workbook workbook) throws ParseException {
        int i = workbook.getNumberOfSheets();
        if(i > 0){
            Sheet sheet = workbook.getSheetAt(0);
            this.importDeviceStatData(stationId, sheet);
            this.importDevicePowerData(stationId, sheet);
        }
    }

    public boolean checkDataCorrect(Workbook workbook){
        try{
            boolean flag = true;
            Sheet sheet = workbook.getSheetAt(0);
            //首先获取多少列
            Row dateRow = sheet.getRow(1);
            for(int i=2;i<=dateRow.getLastCellNum();i++){
                if(sheet.getRow(10) != null && sheet.getRow(10).getCell(i) != null){
                    Cell cell = sheet.getRow(10).getCell(i);
                    if(cell != null && StringUtils.isNotBlank(getCellValue(cell))){
                        double realVal = Double.parseDouble(getCellValue(cell));
                        double total = 0;
                        for(int row = 16;row <= sheet.getLastRowNum();row++){
                            Cell rowCell = sheet.getRow(row).getCell(i);
                            if(rowCell != null && StringUtils.isNotBlank(getCellValue(rowCell))){
                                total = total + Double.parseDouble(getCellValue(rowCell));
                            }
                        }
                        if(total < realVal-1 || total > realVal + 1){
                            logger.error("错误数据日期:"+getCellDate(dateRow.getCell(i)));
                            flag = false;
                        }
                    }
                }
            }
            flag = true;
            return flag;
        }catch (Exception e){
            logger.error("校验异常",e);
            return false;
        }
    }

    private void importDevicePowerData(Long stationId, Sheet sheet) throws ParseException {
        int startIndex = 16 ;
        new SimpleDateFormat("yyyy/MM/dd");
        int lastRow = sheet.getLastRowNum();
        Date date = new Date();
        if (lastRow >= startIndex) {
            Row dateRow = sheet.getRow(1);
            for(int i = startIndex; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                String deviceName = this.getCellValue(row.getCell(0));
                if (!StringUtils.isBlank(deviceName)) {
                    Cell scaleCell = row.getCell(1);
                    String scale = this.getCellValue(scaleCell);
                    for(int j = 2; j <= row.getLastCellNum(); ++j) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            String dateVal = this.getCellDate(dateRow.getCell(j));
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date statDate = sdf1.parse(dateVal);
                            String statVal = this.getCellValue(cell);
                            if (!StringUtils.isBlank(statVal)) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(statDate);
                                int year = calendar.get(1);
                                int month = calendar.get(2) + 1;
                                StationDeviceStat deviceStat = new StationDeviceStat();
                                deviceStat.setYear(year);
                                deviceStat.setMonth(month);
                                deviceStat.setStationId(stationId);
                                deviceStat.setDeviceName(deviceName);
                                BigDecimal bd = new BigDecimal(statVal);
                                bd = bd.setScale(2, 4);
                                deviceStat.setStatVal(bd);
                                deviceStat.setStatDate(statDate);
                                deviceStat.setStatType(3);
                                deviceStat.setCreateDttm(date);
                                deviceStat.setUpdateDttm(date);
                                this.deviceStatMapper.insert(deviceStat);

                                //计算等效小时数
                                BigDecimal scaleBD = new BigDecimal(scale);
                                BigDecimal hourBD = bd.divide(scaleBD,BigDecimal.ROUND_HALF_UP);
                                deviceStat.setStatType(6);
                                deviceStat.setStatVal(hourBD);
                                this.deviceStatMapper.insert(deviceStat);
                            }
                        }
                    }
                }
            }
        }

    }

    private void importDeviceHourData(Long stationId,Sheet sheet) throws ParseException {
        int startIndex = 1;
        new SimpleDateFormat("yyyy/MM/dd");
        int lastRow = sheet.getLastRowNum();
        Date date = new Date();
        if (lastRow >= startIndex) {
            Row dateRow = sheet.getRow(0);

            for(int i = startIndex; i <= lastRow; i++) {
                Row row = sheet.getRow(i);
                String deviceName = this.getCellValue(row.getCell(0));
                if (!StringUtils.isBlank(deviceName)) {
                    for(int j = 1; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            String dateVal = this.getCellDate(dateRow.getCell(j));
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date statDate = sdf1.parse(dateVal);
                            String statVal = this.getCellValue(cell);
                            if (!StringUtils.isBlank(statVal)) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(statDate);
                                int year = calendar.get(1);
                                int month = calendar.get(2) + 1;
                                StationDeviceStat deviceStat = new StationDeviceStat();
                                deviceStat.setYear(year);
                                deviceStat.setMonth(month);
                                deviceStat.setStationId(stationId);
                                deviceStat.setDeviceName(deviceName);
                                BigDecimal bd = new BigDecimal(statVal);
                                bd = bd.setScale(2, 4);
                                deviceStat.setStatVal(bd);
                                deviceStat.setStatDate(statDate);
                                deviceStat.setStatType(6);
                                deviceStat.setCreateDttm(date);
                                deviceStat.setUpdateDttm(date);
                                this.deviceStatMapper.insert(deviceStat);
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 从第三列开始取 第二列现在n逆变器容量
     * @param stationId
     * @param sheet
     * @throws ParseException
     */
    private void importDeviceStatData(Long stationId,Sheet sheet) throws ParseException {
        new SimpleDateFormat("yyyy/MM/dd");
        int lastRow = sheet.getLastRowNum();
        Date date = new Date();
        if (lastRow > 1) {
            Row dateRow = sheet.getRow(1);
            for(int i = 2; i < 8; ++i) {
                Row row = sheet.getRow(i);
                String statName = this.getCellValue(row.getCell(0));
                if (!StringUtils.isBlank(statName)) {
                    for(int j = 2; j <= row.getLastCellNum(); ++j) {
                        Cell cell1 = dateRow.getCell(j);
                        if (cell1 != null) {
                            String excelDate = this.getCellDate(cell1);
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date statDate = sdf1.parse(excelDate);
                            Cell cell = row.getCell(j);
                            if (cell != null) {
                                String statVal = this.getCellValue(cell);
                                if (!StringUtils.isBlank(statVal)) {
                                    //处理小数点
                                    if(i == 6 || i == 7){
                                        BigDecimal bg = new BigDecimal(statVal);
                                        statVal = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
                                    }
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(statDate);
                                    int year = calendar.get(1);
                                    int month = calendar.get(2) + 1;
                                    StationDataStat stat = new StationDataStat();
                                    stat.setYear(year);
                                    stat.setMonth(month);
                                    stat.setStationId(stationId);
                                    stat.setStatName(statName);
                                    stat.setStatVal(statVal);
                                    stat.setStatDate(statDate);
                                    stat.setCreateDttm(date);
                                    stat.setUpdateDttm(date);
                                    this.dataStatMapper.insert(stat);
                                }
                            }
                        }
                    }
                }
            }
            //TODO 处理等效小时数和发电量
            for(int i=8;i<14;i++){
                Row row = sheet.getRow(i);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String statType = "";
                if(i == 8){
                    statType = THEORY_POWER;
                }else if(i == 9){
                    statType = FUZHAO_POWER;
                }else if(i == 10){
                    statType = REAL_POWER;
                }else if(i == 11){
                    statType = THEORY_HOUR;
                }else if(i == 12){
                    statType = FUZHAO_HOUR;
                }else if(i == 13){
                    statType = REAL_HOUR;
                }
                for(int j = 2; j <= row.getLastCellNum(); ++j) {
                    Cell dateRowCell = dateRow.getCell(j);
                    if(dateRowCell != null){
                        String excelDate = this.getCellDate(dateRowCell);
                        Cell cell = row.getCell(j);
                        if(cell != null && StringUtils.isNotBlank(getCellValue(cell))){
                            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                            Date statDate = sdf.parse(excelDate);
                            String statVal = getCellValue(cell);
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
                            stat.setStatType(statType);
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
        }

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

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return "" ;
    }

    private static final Integer FIRST = 5;
    private static final Integer LAST = 15;


    /**
     * 获取设备统计柱形图
     * @param stationId
     * @param type
     * @param year
     * @param month
     * @return
     */
    public JSONObject getDeviceStat(Long stationId,Integer type,Integer year, Integer month,Integer num,String deviceName){
        Map<String,Object> param = Maps.newHashMap();
        if(StringUtils.isNotBlank(deviceName)){
            param.put("deviceName","%"+deviceName+"%");
        }
        param.put("stationId",stationId);
        param.put("year",year);
        param.put("orderBy","realHour desc,realPower desc");
        param.put("offset",0);
        param.put("limit",FIRST);
        int totalCount  = 0;
        List<Map<String,Object>> first5List = Lists.newArrayList();
        //中间的列表
        List<Map<String,Object>> middleList = Lists.newArrayList();
        List<Map<String,Object>> stepList = Lists.newArrayList();
        if(type == 0){
            param.put("month",month);
            //总长度
            totalCount = deviceStatMapper.queryMonthStatCount(param);
            if(totalCount == 0){
                return new JSONObject();
            }
            first5List =  deviceStatMapper.queryMonthStat(param);

            if(totalCount > 5 && totalCount < 20){
                //获取中间的列表
                param.put("offset",FIRST);
                param.put("limit",totalCount-FIRST);
                middleList = deviceStatMapper.queryMonthStat(param);
                if(num != 0){
                    int step = middleList.size() / num;
                    if(step == 0){
                        step = 10;
                    }
                    for(int i=0;i<middleList.size();){
                        stepList.add(middleList.get(i));
                        if(stepList.size() == num){
                            break;
                        }
                        i = i + step;
                    }
                }
                first5List.addAll(stepList);
            }else if(totalCount >= 20){
                //获取中间的列表
                param.put("offset",FIRST);
                param.put("limit",totalCount-FIRST-LAST);
                middleList = deviceStatMapper.queryMonthStat(param);

                if(num != 0){
                    int step = middleList.size() / num;
                    if(step == 0){
                        step = 10;
                    }
                    for(int i=0;i<middleList.size();){
                        stepList.add(middleList.get(i));
                        if(stepList.size() == num){
                            break;
                        }
                        i = i + step;
                    }
                }
                param.put("offset",totalCount-LAST);
                param.put("limit",LAST);
                List<Map<String,Object>> lastList =  deviceStatMapper.queryMonthStat(param);
                first5List.addAll(stepList);
                first5List.addAll(lastList);
            }


        }else if(type == 1){
            //总长度
            totalCount = deviceStatMapper.queryYearStatCount(param);
            if(totalCount == 0){
                return new JSONObject();
            }
            first5List =  deviceStatMapper.queryYearStat(param);

            if(totalCount > 5 && totalCount < 20){
                //获取中间的列表
                param.put("offset",FIRST);
                param.put("limit",totalCount-FIRST);
                middleList = deviceStatMapper.queryYearStat(param);
                if(num != 0){
                    int step = middleList.size() / num;
                    if(step == 0){
                        step = 10;
                    }
                    for(int i=0;i<middleList.size();){
                        stepList.add(middleList.get(i));
                        if(stepList.size() == num){
                            break;
                        }
                        i = i + step;
                    }
                }
                first5List.addAll(stepList);
            }else if(totalCount >= 20){
                //获取中间的列表
                param.put("offset",FIRST);
                param.put("limit",totalCount-FIRST-LAST);
                middleList = deviceStatMapper.queryYearStat(param);

                if(num != 0){
                    int step = middleList.size() / num;
                    if(step == 0){
                        step = 10;
                    }
                    for(int i=0;i<middleList.size();){
                        stepList.add(middleList.get(i));
                        if(stepList.size() == num){
                            break;
                        }
                        i = i + step;
                    }
                }

                param.put("offset",totalCount-LAST);
                param.put("limit",LAST);
                List<Map<String,Object>> lastList =  deviceStatMapper.queryYearStat(param);
                first5List.addAll(stepList);
                first5List.addAll(lastList);
            }


        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultData",first5List);
        jsonObject.put("resultDataNew",dealHighchartsData(num,first5List));
        return jsonObject;
    }

    public List<Map<String,Object>> flipList(List<Map<String,Object>> list){
        if(list == null || list.isEmpty()){
            return Lists.newArrayList();
        }
        List<Map<String,Object>> newList = Lists.newArrayList();
        for(int i=list.size()-1;i>=0;i--){
            newList.add(list.get(i));
        }
        return newList;
    }

    private static final String GREEN = "#A0C961";
    private static final String BLUE = "#547598";
    private static final String RED = "#F10E14";

    /**
     * 将数据处理成Highcharts格式数据的Data
     * 前5名都是绿色   #47d147
     * 后11名都是红色  #e62e00
     * 中间都是蓝色    #3333ff
     */
    public JSONObject dealHighchartsData(int num,List<Map<String,Object>> dataList){
        if(dataList == null || dataList.isEmpty()){
            return new JSONObject();
        }
        int dataLen = dataList.size();
        List<Object> deviceNameList = Lists.newArrayList();
        List<JSONObject> powerList = Lists.newArrayList();
        List<JSONObject> hourList = Lists.newArrayList();
        if(dataLen <= 5){
            for(Map<String,Object> dataMap : dataList){
                dealDataMap(deviceNameList,powerList,hourList,dataMap,GREEN);
            }
        }else if(dataLen <= num + 5){
            for(int i=0;i<dataLen;i++){
                if(i < 5){
                    dealDataMap(deviceNameList,powerList,hourList,dataList.get(i),GREEN);
                }else{
                    dealDataMap(deviceNameList,powerList,hourList,dataList.get(i),BLUE);
                }
            }
        }else{
            for(int i=0;i<dataLen;i++){
                if(i < 5){
                    dealDataMap(deviceNameList,powerList,hourList,dataList.get(i),GREEN);
                }else if(i< num + 5){
                    dealDataMap(deviceNameList,powerList,hourList,dataList.get(i),BLUE);
                }else{
                    dealDataMap(deviceNameList,powerList,hourList,dataList.get(i),RED);
                }
            }
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("deviceList",deviceNameList);
        resultObject.put("powerData",powerList);
        resultObject.put("hourData",hourList);
        return resultObject;
    }

    private void dealDataMap(List<Object> deviceNameList,
                            List<JSONObject> powerList,
                            List<JSONObject> hourList,
                            Map<String,Object> dataMap,
                            String color){
        Object deviceName = dataMap.get("deviceName");
        Object realPower = dataMap.get("realPower");
        Object realHour = dataMap.get("realHour");
        deviceNameList.add(deviceName);

        JSONObject powerObject = new JSONObject();
        powerObject.put("y",realPower);
        powerObject.put("color",color);

        JSONObject realObject = new JSONObject();
        realObject.put("y",realHour);
        realObject.put("color",color);

        powerList.add(powerObject);
        hourList.add(realObject);
    }

    public void createDevicePowerSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("数据分析");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*45+184,256*42+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row = sheet.createRow(0);
        createRowColCell(row,0,"数据分析",boderStyle);


        row = sheet.createRow(1);
        createRowColCell(row,0,"日期",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(2);
        createRowColCell(row,0,"天气（2019年10月1日后废止）",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(3);
        createRowColCell(row,0,"环境温度℃",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(4);
        createRowColCell(row,0,"组件温度℃",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(5);
        createRowColCell(row,0,"AQI",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(6);
        createRowColCell(row,0,"总辐照量MJ/M²",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(7);
        createRowColCell(row,0,"峰值日照时数h",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(8);
        createRowColCell(row,0,"理论发电量（kwh）",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(9);
        createRowColCell(row,0,"辐照发电量（kwh）",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(10);
        createRowColCell(row,0,"实际发电量（kwh）",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(11);
        createRowColCell(row,0,"理论等效小时数",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(12);
        createRowColCell(row,0,"辐照等效小时数",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(13);
        createRowColCell(row,0,"实际等效小时数",boderStyle1);
        createRowColCell(row,1,"\\",boderStyle1);

        row = sheet.createRow(14);

        row = sheet.createRow(15);
        createRowColCell(row,0,"设备名称",boderStyle1);
        createRowColCell(row,1,"设备功率（kw）",boderStyle1);

    }

    public void createRowCell(HSSFRow row,HSSFCellStyle style,String name){
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(name);
        cell.setCellStyle(style);
    }

    public void createRowColCell(HSSFRow row,int index,String val,HSSFCellStyle style){
        HSSFCell cell = row.createCell(index);
        cell.setCellStyle(style);
        cell.setCellValue(val);
    }

    @Deprecated
    public void createDeviceHourSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("等效小时数");
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(false);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)13);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(false);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*40+184};
        sheet.setColumnWidth(0,width[0]);

        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("名称");
        cell.setCellStyle(boderStyle);
    }

    public void createStatSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("统计");
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)13);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*30+184,256*30+184,256*30+184,256*35+184,256*30+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);
        sheet.setColumnWidth(2,width[2]);
        sheet.setColumnWidth(3,width[3]);
        sheet.setColumnWidth(4,width[4]);
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(0);
        //创建单元格并设置单元格内容
        HSSFCell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("日期/设备");
        cell2_0.setCellStyle(boderStyle1);
        HSSFCell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("温度℃");
        cell2_1.setCellStyle(boderStyle1);
        HSSFCell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("AQI");
        cell2_2.setCellStyle(boderStyle1);
        HSSFCell cell2_3 = row2.createCell(3);
        cell2_3.setCellValue("总福照量MJ/㎡");
        cell2_3.setCellStyle(boderStyle1);
        HSSFCell cell2_4 = row2.createCell(4);
        cell2_4.setCellValue("总发电量Kw·h");
        cell2_4.setCellStyle(boderStyle1);
    }

    private void createRowCell(HSSFRow row,int col,HSSFCellStyle style,String name){
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(name);
        cell.setCellStyle(style);
    }

}
