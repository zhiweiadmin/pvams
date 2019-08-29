package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.DeviceMaintainDetailFileMapper;
import com.goodpower.pvams.mapper.DeviceMaintainDetailMapper;
import com.goodpower.pvams.mapper.DeviceMaintainMapper;
import com.goodpower.pvams.model.DeviceMaintain;
import com.goodpower.pvams.model.DeviceMaintainDetail;
import com.goodpower.pvams.model.DeviceMaintainDetailFile;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MaintainService {

    private Logger logger = LoggerFactory.getLogger(MaintainService.class.getName());

    @Autowired
    DeviceMaintainMapper maintainMapper;

    @Autowired
    DeviceMaintainDetailMapper maintainDetailMapper;

    @Autowired
    DeviceMaintainDetailFileMapper detailFileMapper;

    //未保养
    private static final Integer UNMAINTAINED = 0;

    //保养未确认
    private static final Integer MAINTAINED_NOCONFIRM = 1;

    //保养并确认
    private static final Integer MAINTAINED_CONFIRM = 2;


    public void addMaintainPlan(JSONObject jsonObject){
        Long stationId = jsonObject.getLong("stationId");
        JSONObject acJSONObject = jsonObject.getJSONObject("acSide");//交流
        JSONObject dcJSONObject = jsonObject.getJSONObject("dcSide");//直流
        JSONObject seJSONObject = jsonObject.getJSONObject("secondaryEquipment");//二次设备
        String year = jsonObject.getString("year");
        DeviceMaintain deviceMaintain = new DeviceMaintain();
        deviceMaintain.setStationId(stationId);
        if(StringUtils.isBlank(year)){
            Calendar calendar = Calendar.getInstance();
            int yearVal = calendar.get(Calendar.YEAR);
            deviceMaintain.setYearVal(String.valueOf(yearVal));
        }else{
            deviceMaintain.setYearVal(year);
        }
        if(acJSONObject != null && acJSONObject.size() != 0){
            deviceMaintain.setAcSide(acJSONObject.getString("name"));
        }
        if(dcJSONObject != null && dcJSONObject.size() != 0){
            deviceMaintain.setDcSide(dcJSONObject.getString("name"));
        }
        if(seJSONObject != null && seJSONObject.size() != 0){
            deviceMaintain.setSecondaryEquipment(seJSONObject.getString("name"));
        }
        deviceMaintain.setMsg(jsonObject.toJSONString());
        //插入详细维保计划
        Date date = new Date();
        deviceMaintain.setCreateDttm(date);
        deviceMaintain.setUpdateDttm(date);
        maintainMapper.insert(deviceMaintain);
        if(acJSONObject != null && acJSONObject.size() != 0){
            addMaintainDetail(deviceMaintain.getMaintainId(),acJSONObject,"A");
        }
        if(dcJSONObject != null && dcJSONObject.size() != 0){
            addMaintainDetail(deviceMaintain.getMaintainId(),dcJSONObject,"C");
        }
        if(seJSONObject != null && seJSONObject.size() != 0){
            addMaintainDetail(deviceMaintain.getMaintainId(),seJSONObject,"B");
        }
    }

    public void updateMaintainPlan(JSONObject jsonObject){
        Long maintainId = jsonObject.getLong("maintainId");
        Long stationId = jsonObject.getLong("stationId");
        JSONObject acJSONObject = jsonObject.getJSONObject("acSide");//交流
        JSONObject dcJSONObject = jsonObject.getJSONObject("dcSide");//直流
        JSONObject seJSONObject = jsonObject.getJSONObject("secondaryEquipment");//二次设备
        String year = jsonObject.getString("year");
        DeviceMaintain deviceMaintain = new DeviceMaintain();
        deviceMaintain.setStationId(stationId);
        if(StringUtils.isBlank(year)){
            Calendar calendar = Calendar.getInstance();
            int yearVal = calendar.get(Calendar.YEAR);
            deviceMaintain.setYearVal(String.valueOf(yearVal));
        }else{
            deviceMaintain.setYearVal(year);
        }
        if(acJSONObject != null){
            deviceMaintain.setAcSide(acJSONObject.getString("name"));
        }
        if(dcJSONObject != null){
            deviceMaintain.setDcSide(dcJSONObject.getString("name"));
        }
        if(seJSONObject != null){
            deviceMaintain.setSecondaryEquipment(seJSONObject.getString("name"));
        }
        deviceMaintain.setMsg(jsonObject.toJSONString());
        //插入详细维保计划
        Date date = new Date();
        deviceMaintain.setCreateDttm(date);
        deviceMaintain.setUpdateDttm(date);
        deviceMaintain.setMaintainId(maintainId);
        deviceMaintain.setMsg(jsonObject.toJSONString());
        maintainMapper.updateByPrimaryKey(deviceMaintain);
        if(acJSONObject != null){
            addMaintainDetail(maintainId,acJSONObject,"A");
        }
        if(dcJSONObject != null){
            addMaintainDetail(maintainId,dcJSONObject,"C");
        }
        if(seJSONObject != null){
            addMaintainDetail(maintainId,seJSONObject,"B");
        }
    }

    public void addMaintainDetail(long maintainId,JSONObject jsonObject,String deviceType){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
        DeviceMaintainDetail maintainDetail = new DeviceMaintainDetail();
        maintainDetail.setMaintainId(maintainId);
        JSONArray weeks = jsonObject.getJSONArray("week");
        Date date = new Date();
        for(Object weekObject : weeks){
            if(weekObject instanceof Integer){
                maintainDetail.setMaintainWeek((Integer)weekObject);
                maintainDetail.setDeviceType(deviceType);
                maintainDetail.setStatus(UNMAINTAINED);
                maintainDetail.setUpdateDttm(date);
                maintainDetailMapper.insert(maintainDetail);
            }
        }
        //删除操作
        Map<String,Object> param = Maps.newHashMap();
        param.put("date",sdf.format(date));
        param.put("maintainId",maintainId);
        maintainDetailMapper.deleteByFields(param);
    }

    public void updateMaintainPlan(DeviceMaintainDetail maintainDetail){
        maintainDetail.setStatus(MAINTAINED_NOCONFIRM);
        maintainDetail.setMaintainTime(new Date());
        maintainDetailMapper.updateByPrimaryKeySelective(maintainDetail);
    }

    public void confirmMaintainPlan(DeviceMaintainDetail maintainDetail){
        maintainDetail.setStatus(MAINTAINED_CONFIRM);
        maintainDetail.setConfirmTime(new Date());
        maintainDetailMapper.updateByPrimaryKeySelective(maintainDetail);
    }

    public Map<String,Object> getMaintainPlan(Long maintainId, Integer week){
        return maintainMapper.getMaintainPlan(maintainId,week);
    }

    public JSONObject getStationMaintainPlan(Long stationId,String year){
        JSONObject result = new JSONObject();
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("year",year);
        List<DeviceMaintain> planList =maintainMapper.selectByField(param);
        //获取当年
        JSONArray weekArray = DateUtil.getDateWeek();
        for(DeviceMaintain deviceMaintain : planList){
            Map<String,Object> map = Maps.newHashMap();
            map.put("maintainId",deviceMaintain.getMaintainId());
            List<DeviceMaintainDetail> detailList = maintainDetailMapper.selectByField(map);
            List<Object> newDetailList = Lists.newArrayList();
            Map<Integer,DeviceMaintainDetail> maintainMap = Maps.newHashMap();
            for(DeviceMaintainDetail deviceMaintainDetail : detailList){
                Long detailId = deviceMaintainDetail.getDetailId();
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("detailId",detailId);
                List<DeviceMaintainDetailFile> list = detailFileMapper.selectByFields(paramMap);
                List<String> fileList = Lists.newArrayList();
                for(DeviceMaintainDetailFile file : list){
                    fileList.add(file.getPath());
                }
                deviceMaintainDetail.setFileList(fileList);
                Integer week = deviceMaintainDetail.getMaintainWeek();
                maintainMap.put(week,deviceMaintainDetail);

            }
            for(int i=0;i<weekArray.size();i++){
                int month =weekArray.getJSONObject(i).getInteger("month");
                int week =weekArray.getJSONObject(i).getInteger("week");
                String firstDay =weekArray.getJSONObject(i).getString("firstDay");
                if(maintainMap.get(week) != null){
                    DeviceMaintainDetail maintainDetail = maintainMap.get(week);
                    maintainDetail.setMonth(month);
                    maintainDetail.setWeek(week);
                    maintainDetail.setFirstDay(firstDay);
                    newDetailList.add(maintainDetail);
                }else{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("month",month);
                    jsonObject.put("week",week);
                    jsonObject.put("firstDay",firstDay);
                    newDetailList.add(jsonObject);
                }
            }

            deviceMaintain.setDeviceMaintainDetailList(newDetailList);
        }
        result.put("list",planList);
        return result;
    }

    public void deleteMaintainPlan(Long maintainId){
        maintainMapper.deleteByPrimaryKey(maintainId);
        Map<String,Object> param = Maps.newHashMap();
        param.put("maintainId",maintainId);
        maintainDetailMapper.deleteByFields(param);
    }

    public DeviceMaintain getDeviceMaintain(Long maintainId){
        return maintainMapper.selectByPrimaryKey(maintainId);
    }

}
