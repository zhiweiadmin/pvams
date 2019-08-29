package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.*;
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
public class SafeService {

    @Autowired
    SafePlanMapper safePlanMapper;

    @Autowired
    SafePlanDetailMapper safePlanDetailMapper;

    @Autowired
    SafePlanDetailFileMapper safePlanDetailFileMapper;

    @Autowired
    UserService userService;

    private static final Integer SAFE_STATUS_INIT = 0;

    private static final Integer SAFE_STATUS_SUBMIT = 1;

    private static final Integer SAFE_STATUS_CONFIRM = 2;

    public void addSafePlan(JSONObject jsonObject){
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        Long userId = jsonObject.getLong("userId");
        Long stationId = jsonObject.getLong("stationId");
        Integer year = jsonObject.getInteger("year");
        String msg = jsonObject.toJSONString();
        Date date = new Date();
        SafePlan plan = new SafePlan();
        plan.setContent(content);
        plan.setTitle(title);
        plan.setMsg(msg);
        plan.setCreator(userId);
        plan.setUpdater(userId);
        plan.setStationId(stationId);
        plan.setYear(year);
        plan.setCreateDttm(date);
        plan.setUpdateDttm(date);
        safePlanMapper.insert(plan);
        Long safeId = plan.getSafeId();
        if(safeId != null){
            JSONArray weekArray = jsonObject.getJSONArray("week");
            for(Object object : weekArray){
                if(object instanceof Integer){
                    SafePlanDetail detail = new SafePlanDetail();
                    detail.setSafeId(safeId);
                    detail.setSafeWeek(Integer.parseInt(object.toString()));
                    detail.setStatus(SAFE_STATUS_INIT);
                    detail.setCreateDttm(date);
                    detail.setUpdateDttm(date);
                    detail.setSubmitUserId(userId);
                    detail.setSubmitTime(date);
                    safePlanDetailMapper.insert(detail);
                }
            }
        }
    }

    public void updateSafePlan(JSONObject jsonObject){
        String title = jsonObject.getString("title");
        String content = jsonObject.getString("content");
        Long userId = jsonObject.getLong("userId");
        Long stationId = jsonObject.getLong("stationId");
        Long safeId = jsonObject.getLong("safeId");
        Integer year = jsonObject.getInteger("year");
        Date date = new Date();

        SafePlan plan = new SafePlan();
        plan.setContent(content);
        plan.setTitle(title);
        plan.setCreator(userId);
        plan.setUpdater(userId);
        plan.setStationId(stationId);
        plan.setSafeId(safeId);
        plan.setYear(year);
        plan.setCreateDttm(date);
        plan.setUpdateDttm(date);
        plan.setMsg(jsonObject.toJSONString());
        safePlanMapper.updateByPrimaryKeySelective(plan);
        if(safeId != null){
            JSONArray weekArray = jsonObject.getJSONArray("week");
            for(Object object : weekArray){
                if(object instanceof Integer){
                    SafePlanDetail detail = new SafePlanDetail();
                    detail.setSafeId(safeId);
                    detail.setSafeWeek(Integer.parseInt(object.toString()));
                    detail.setStatus(SAFE_STATUS_INIT);
                    detail.setCreateDttm(date);
                    detail.setUpdateDttm(date);
                    detail.setSubmitUserId(userId);
                    detail.setSubmitTime(date);
                    safePlanDetailMapper.insertSelective(detail);
                }
            }
            //删除操作
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Map<String,Object> param = Maps.newHashMap();
            param.put("date",sdf.format(date));
            param.put("safeId",safeId);
            safePlanDetailMapper.deleteByFields(param);
        }
    }

    public void uploadSafeFile(SafePlanDetail safePlanDetail){
        for(String path : safePlanDetail.getFileList()){
            SafePlanDetailFile file = new SafePlanDetailFile();
            file.setSafeDetailId(safePlanDetail.getSafeDetailId());
            file.setPath(path);
            Date date = new Date();
            file.setCreateDttm(date);
            file.setUpdateDttm(date);
            safePlanDetailFileMapper.insert(file);
        }
    }

    public void updateSafePlan(SafePlanDetail safePlanDetail){
        safePlanDetail.setStatus(SAFE_STATUS_SUBMIT);
        safePlanDetail.setUpdateDttm(new Date());
        safePlanDetailMapper.updateByPrimaryKeySelective(safePlanDetail);
    }

    public void confirmSafePlan(SafePlanDetail safePlanDetail){
        safePlanDetail.setStatus(SAFE_STATUS_CONFIRM);
        safePlanDetail.setConfirmTime(new Date());
        safePlanDetailMapper.updateByPrimaryKeySelective(safePlanDetail);
    }

    public Map<String,Object> getSafePlan(Long stationId, Integer year){
        JSONObject result = new JSONObject();
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("year",year);
        List<SafePlan> planList =safePlanMapper.selectByFields(param);
        //获取当年
        JSONArray weekArray = DateUtil.getDateWeek();
        for(SafePlan safePlan : planList){
            Map<String,Object> map = Maps.newHashMap();
            map.put("safeId",safePlan.getSafeId());
            List<SafePlanDetail> detailList = safePlanDetailMapper.selectByFields(map);
            List<Object> newDetailList = Lists.newArrayList();
            Map<Integer,JSONObject> weekSafeMap = Maps.newHashMap();
            for(SafePlanDetail safePlanDetail : detailList){
                Long safeDetailId = safePlanDetail.getSafeDetailId();
                Map<String,Object> paramMap = Maps.newHashMap();
                paramMap.put("safeDetailId",safeDetailId);
                List<SafePlanDetailFile> list = safePlanDetailFileMapper.selectByFields(paramMap);
                List<String> fileList = Lists.newArrayList();
                for(SafePlanDetailFile file : list){
                    fileList.add(file.getPath());
                }
                safePlanDetail.setFileList(fileList);
                Integer week = safePlanDetail.getSafeWeek();
                JSONObject jsonObject = (JSONObject) JSON.toJSON(safePlanDetail);
                if(safePlanDetail.getConfirmUserId() != null){
                    User approver = userService.findUserById(safePlanDetail.getConfirmUserId());
                    if(approver != null){
                        jsonObject.put("confirmer",approver.getRealname());
                    }
                }
                weekSafeMap.put(week,jsonObject);

            }
            for(int i=0;i<weekArray.size();i++){
                int month =weekArray.getJSONObject(i).getInteger("month");
                int week =weekArray.getJSONObject(i).getInteger("week");
                String firstDay =weekArray.getJSONObject(i).getString("firstDay");
                if(weekSafeMap.get(week) != null){
                    JSONObject planDetail = weekSafeMap.get(week);
                    planDetail.put("month",month);
                    planDetail.put("week",week);
                    planDetail.put("firstDay",firstDay);
                    newDetailList.add(planDetail);
                }else{
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("month",month);
                    jsonObject.put("week",week);
                    jsonObject.put("firstDay",firstDay);
                    newDetailList.add(jsonObject);
                }
            }

            safePlan.setSafePlanDetailList(newDetailList);
        }
        result.put("list",planList);
        return result;
    }

    public void delete(Long safeId){
        safePlanMapper.deleteByPrimaryKey(safeId);
    }

    public SafePlan getSafePlan(Long safeId){
        return safePlanMapper.selectByPrimaryKey(safeId);
    }

}
