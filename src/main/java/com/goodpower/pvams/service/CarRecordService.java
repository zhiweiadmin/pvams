package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.CarRecordMapper;
import com.goodpower.pvams.model.CarRecord;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CarRecordService {

    @Autowired
    CarRecordMapper carRecordMapper;

    public JSONObject query(Map<String,Object> param){
        List<CarRecord> carRecords = carRecordMapper.selectByFields(param);
        int count = carRecordMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",carRecords);
        jsonObject.put("count",count);
        return jsonObject;
    }

}
