package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.data.DataController;
import com.goodpower.pvams.service.StationDataStatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataStatServiceTest {

    @Autowired
    StationDataStatService stationDataStatService;

    @Autowired
    DataController dataController;

    @Test
    public void getDeviceDetail(){
        ResultMap result = dataController.getDeviceStatDetail(1L,3,0,"2019-08",null,null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list",result);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void getMonthStatData(){
        ResultMap resultMap = dataController.getStatData(1L,0,null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultMap",resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void getDeviceStat(){
        ResultMap result = dataController.getDeviceStat(1L,0,null,10,"1");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("datas",result);
        System.out.println(jsonObject.toJSONString());
    }

}
