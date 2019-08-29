package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.mapper.PowerGenerateStatMapper;
import com.goodpower.pvams.mapper.WorkRecordMapper;
import com.goodpower.pvams.model.PowerGenerateStat;
import com.goodpower.pvams.model.WorkRecord;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class WorkRecordService {

    @Autowired
    WorkRecordMapper workRecordMapper;

    @Autowired
    PowerGenerateStatMapper powerStatMapper;

    @Autowired
    PowerGenerateStatMapper powerGenerateStatMapper;

    public void add(WorkRecord workRecord) throws ParseException {
        Date date = new Date();
        int week = DateUtil.getCurWeek();
        int month = DateUtil.getCurMonth();
        int quarter = DateUtil.getQuarter(month);
        int year = DateUtil.getCurYear();
        workRecord.setDay(date);
        workRecord.setWeek(week);
        workRecord.setMonth(month);
        workRecord.setQuarter(quarter);
        workRecord.setYear(year);
        workRecord.setCreateDttm(date);
        workRecord.setUpdateDttm(date);
        workRecordMapper.insert(workRecord);
    }

    public List<WorkRecord> query(Long stationId, Long userId,Date date){
        int week = DateUtil.getTarWeek(date);
        int month = DateUtil.getTarMonth(date);
        int quarter = DateUtil.getQuarter(month);
        int year = DateUtil.getTarYear(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String day = sdf.format(date);
        //获取日报
        Map<String,Object> map = Maps.newHashMap();
        map.put("day",day);
        map.put("week",week);
        map.put("month",month);
        map.put("quarter",quarter);
        map.put("year",year);
        map.put("stationId",stationId);
        map.put("userId",userId);
        return workRecordMapper.selectByFields(map);
    }

    public JSONObject queryTodayDetail(Long stationId,String time){
        String day = null;
        if(StringUtils.isBlank(time)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            day = sdf.format(new Date());
        }else{
            day = time;
        }
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("statDate",day);
        //获取今日发电量数据
        List<PowerGenerateStat> statList = powerStatMapper.selectByFields(param);
        JSONObject jsonObject = new JSONObject();
        PowerGenerateStat fuzhaoPower = new PowerGenerateStat();
        PowerGenerateStat realPower = new PowerGenerateStat();
        PowerGenerateStat theoryHour = new PowerGenerateStat();
        PowerGenerateStat realHour = new PowerGenerateStat();
        for(PowerGenerateStat stat : statList){
            switch (stat.getStatType()){
                case "2":
                    fuzhaoPower = stat;
                    break;
                case "3":
                    realPower = stat;
                    break;
                case "4":
                    theoryHour = stat;
                    break;
                case "6":
                    realHour = stat;
                    break;
            }
        }
        JSONObject resultObject = new JSONObject();
        resultObject.put("realPower",realPower.getStatVal());
        resultObject.put("fuzhaoPower",fuzhaoPower.getStatVal());
        resultObject.put("realHour",realHour.getStatVal());
        if(realHour.getStatVal() != null && theoryHour.getStatVal() != null){
            resultObject.put("loseHour",theoryHour.getStatVal().subtract(realHour.getStatVal()));
        }
        return resultObject;
    }

    public List<Map<String,Object>> getWeekRealHour(Long stationId,String startDate,String endDate) throws ParseException {
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("startDate",startDate);
        param.put("endDate",endDate);
        param.put("statType",PowerStatService.REAL_HOUR);
        List<PowerGenerateStat> resultList = powerGenerateStatMapper.selectByFields(param);
        Map<String,PowerGenerateStat> statMap = Maps.newHashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(PowerGenerateStat stat : resultList){
            statMap.put(sdf.format(stat.getStatDate()),stat);
        }
        List<Map<String,Object>> dataList = Lists.newArrayList();
        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);
        while (!start.after(end)){
            String date = sdf.format(start);
            Map<String,Object> map = Maps.newHashMap();
            map.put("date",date);
            PowerGenerateStat powerGenerateStat = statMap.get(date);
            if(powerGenerateStat != null){
                map.put("realHour",powerGenerateStat.getStatVal());
            }else{
                map.put("realHour",0);
            }
            dataList.add(map);
            start = DateUtil.getDay(start,1);
        }
        return dataList;

    }


}

