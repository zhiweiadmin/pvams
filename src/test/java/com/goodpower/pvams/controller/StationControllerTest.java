package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.common.CommonController;
import com.goodpower.pvams.controller.power.StationController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StationControllerTest {

    @Autowired
    StationController stationController;

    @Autowired
    CommonController commonController;

    @Test
    public void getStationInfo(){
        ResultMap resultMap = stationController.getStationInfo(1001L);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void addStation(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("companyId",1);
        jsonObject.put("stationName","1111常州市武进4区光伏电站");
        jsonObject.put("stationType","1");
        stationController.add(jsonObject);
    }

    @Test
    public void delete(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("companyId",1);
        jsonObject.put("stationName","常州市武进区光伏电站");
        stationController.add(jsonObject);
    }

}
