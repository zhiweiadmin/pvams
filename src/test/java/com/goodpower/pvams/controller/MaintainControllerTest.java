package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintain.MaintainController;
import com.goodpower.pvams.model.DeviceMaintainDetail;
import com.goodpower.pvams.service.MaintainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaintainControllerTest {

    @Autowired
    MaintainController maintainController;

    @Autowired
    MaintainService maintainService;

    @Test
    public void getStationPlan(){
        JSONObject jsonObject = maintainService.getStationMaintainPlan(1L,null);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void addPlan(){
        String str = "{\n" +
                "    \"stationId\":1,\n" +
                "    \"acSide\":{\n" +
                "        \"name\":\"a\",\n" +
                "        \"week\":[1,3]\n" +
                "    },\n" +
                "    \"dcSide\":{\n" +
                "        \"name\":\"b\",\n" +
                "        \"week\":[20,21]\n" +
                "    },\n" +
                "    \"secondaryEquipment\":{\n" +
                "        \"name\":\"c\",\n" +
                "        \"week\":[40,41,42]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(str);
        jsonObject.put("maintainId",44);
        maintainController.updateMaintainPlan(jsonObject);
    }

    @Test
    public void getPlan(){
        ResultMap resultMap = maintainController.getMaintainPlan(1L,2);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void qqq(){
        ResultMap resultMap = maintainController.query(39L);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void submitPlan(){
        DeviceMaintainDetail record = new DeviceMaintainDetail();
        record.setMaintainId(27L);
        record.setMaintainWeek(1);
        record.setContent("这里是内容");
        record.setFlow("这里是流程");
        record.setResult("这里是结果");
        record.setMaintainer("蒋智伟");
        record.setMaintainImg("/file/sdasd.img");
        ResultMap resultMap = maintainController.submitMaintainPlan(record);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }


}
