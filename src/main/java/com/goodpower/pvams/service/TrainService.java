package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.TrainPlanDetailFileMapper;
import com.goodpower.pvams.mapper.TrainPlanDetailMapper;
import com.goodpower.pvams.mapper.TrainPlanMapper;
import com.goodpower.pvams.model.*;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TrainService {

    @Autowired
    TrainPlanMapper trainPlanMapper;

    @Autowired
    TrainPlanDetailMapper trainPlanDetailMapper;

    @Autowired
    TrainPlanDetailFileMapper trainPlanDetailFileMapper;

    @Autowired
    UserService userService;

    private static final Integer TRAIN_STATUS_INIT = 0;

    private static final Integer TRAIN_STATUS_SUBMIT = 1;

    private static final Integer TRAIN_STATUS_CONFIRM = 2;

    public void addTrainPlan(JSONObject jsonObject){
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        Long stationId = jsonObject.getLong("stationId");
        Long userId = jsonObject.getLong("userId");
        String msg = jsonObject.toJSONString();

        TrainPlan trainPlan = new TrainPlan();
        trainPlan.setContent(content);
        trainPlan.setTitle(title);
        trainPlan.setStationId(stationId);
        trainPlan.setCreator(userId);
        trainPlan.setUpdater(userId);
        trainPlan.setMsg(msg);
        Date date = new Date();
        trainPlan.setYear(DateUtil.getCurYear());
        trainPlan.setCreateDttm(date);
        trainPlan.setUpdateDttm(date);
        trainPlanMapper.insert(trainPlan);
        Long trainId = trainPlan.getTrainId();
        if(trainId != null){
            JSONArray weekArray = jsonObject.getJSONArray("week");
            for(Object object : weekArray){
                if(object instanceof Integer){
                    TrainPlanDetail planDetail = new TrainPlanDetail();
                    planDetail.setTrainId(trainId);
                    planDetail.setTrainWeek(Integer.parseInt(object.toString()));
                    planDetail.setStatus(TRAIN_STATUS_INIT);
                    planDetail.setCreateDttm(date);
                    planDetail.setUpdateDttm(date);
                    trainPlanDetailMapper.insert(planDetail);
                }
            }
        }
    }

    public void updateTrainPlan(JSONObject jsonObject){
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        Long stationId = jsonObject.getLong("stationId");
        Long userId = jsonObject.getLong("userId");
        Long trainId = jsonObject.getLong("trainId");
        String msg = jsonObject.toJSONString();

        TrainPlan trainPlan = new TrainPlan();
        trainPlan.setContent(content);
        trainPlan.setTitle(title);
        trainPlan.setMsg(msg);
        trainPlan.setStationId(stationId);
        trainPlan.setCreator(userId);
        trainPlan.setUpdater(userId);
        trainPlan.setTrainId(trainId);
        Date date = new Date();
        trainPlan.setYear(DateUtil.getCurYear());
        trainPlan.setCreateDttm(date);
        trainPlan.setUpdateDttm(date);
        trainPlanMapper.updateByPrimaryKey(trainPlan);
        if(trainId != null){
            JSONArray weekArray = jsonObject.getJSONArray("week");
            for(Object object : weekArray){
                if(object instanceof Integer){
                    TrainPlanDetail planDetail = new TrainPlanDetail();
                    planDetail.setTrainId(trainId);
                    planDetail.setTrainWeek(Integer.parseInt(object.toString()));
                    planDetail.setStatus(TRAIN_STATUS_INIT);
                    planDetail.setTrainTime(date);
                    planDetail.setCreateDttm(date);
                    planDetail.setUpdateDttm(date);
                    trainPlanDetailMapper.insert(planDetail);
                }
            }
            //删除操作
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String,Object> param = Maps.newHashMap();
            param.put("date",sdf.format(date));
            param.put("trainId",trainId);
            trainPlanDetailMapper.deleteByFields(param);
        }
    }

    public void uploadTrainFile(TrainPlanDetail trainPlanDetail){
        for(String path : trainPlanDetail.getFileList()){
            TrainPlanDetailFile file = new TrainPlanDetailFile();
            file.setTrainDetailId(trainPlanDetail.getTrainDetailId());
            file.setPath(path);
            Date date = new Date();
            file.setCreateDttm(date);
            file.setUpdateDttm(date);
            trainPlanDetailFileMapper.insert(file);
        }
    }

    public void updateTrainPlan(TrainPlanDetail trainPlanDetail){
        trainPlanDetail.setStatus(TRAIN_STATUS_SUBMIT);
        trainPlanDetail.setUpdateDttm(new Date());
        trainPlanDetailMapper.updateByPrimaryKeySelective(trainPlanDetail);
    }

    public void confirmTrainPlan(TrainPlanDetail trainPlanDetail){
        trainPlanDetail.setStatus(TRAIN_STATUS_CONFIRM);
        trainPlanDetail.setConfirmTime(new Date());
        trainPlanDetailMapper.updateByPrimaryKeySelective(trainPlanDetail);
    }

    public Map<String,Object> getTrainPlan(Long stationId, Integer year){
        JSONObject result = new JSONObject();
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("year",year);
        List<TrainPlan> planList =trainPlanMapper.selectByFields(param);
        //获取当年
        JSONArray weekArray = DateUtil.getDateWeek();
        for(TrainPlan trainPlan : planList){
            Map<String,Object> map = Maps.newHashMap();
            map.put("trainId",trainPlan.getTrainId());
            List<TrainPlanDetail> detailList = trainPlanDetailMapper.selectByFields(map);
            List<Object> newDetailList = Lists.newArrayList();
            Map<Integer,JSONObject> weekTrainMap = Maps.newHashMap();
            for(TrainPlanDetail trainPlanDetail : detailList){
                Long trainDetailId = trainPlanDetail.getTrainDetailId();
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("trainDetailId",trainDetailId);
                List<TrainPlanDetailFile> list = trainPlanDetailFileMapper.selectByFields(paramMap);
                List<String> fileList = Lists.newArrayList();
                for(TrainPlanDetailFile file : list){
                    fileList.add(file.getPath());
                }
                trainPlanDetail.setFileList(fileList);
                Integer week = trainPlanDetail.getTrainWeek();
                JSONObject jsonObject = (JSONObject) JSON.toJSON(trainPlanDetail);
                if(trainPlanDetail.getConfirmUserId() != null){
                    User approver = userService.findUserById(trainPlanDetail.getConfirmUserId());
                    if(approver != null){
                        jsonObject.put("confirmer",approver.getRealname());
                    }
                }
                weekTrainMap.put(week,jsonObject);

            }
            for(int i=0;i<weekArray.size();i++){
                int month =weekArray.getJSONObject(i).getInteger("month");
                int week =weekArray.getJSONObject(i).getInteger("week");
                String firstDay =weekArray.getJSONObject(i).getString("firstDay");
                if(weekTrainMap.get(week) != null){
                    JSONObject trainPlanDetail = weekTrainMap.get(week);
                    trainPlanDetail.put("month",month);
                    trainPlanDetail.put("week",week);
                    trainPlanDetail.put("firstDay",firstDay);
                    newDetailList.add(trainPlanDetail);
                }else{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("month",month);
                    jsonObject.put("week",week);
                    jsonObject.put("firstDay",firstDay);
                    newDetailList.add(jsonObject);
                }
            }

            trainPlan.setTrainPlanDetailList(newDetailList);
        }
        result.put("list",planList);
        return result;
    }


    public TrainPlan getTrainPlan(Long trainId){
        return trainPlanMapper.selectByPrimaryKey(trainId);
    }

    public void delete(Long trainId){
        trainPlanMapper.deleteByPrimaryKey(trainId);
    }

}
