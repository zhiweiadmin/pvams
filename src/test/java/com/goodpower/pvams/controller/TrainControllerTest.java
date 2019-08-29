package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintaininfo.TrainController;
import com.goodpower.pvams.model.TrainPlanDetail;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainControllerTest {

    @Autowired
    TrainController trainController;

    @Test
    public void addTest(){
        String jsonString = "{\n" +
                "    \"title\":\"2019年训练计划2\",\n" +
                "    \"content\":\"训练内容2\",\n" +
                "    \"week\":[1,2,3,6],\n" +
                "    \"stationId\": 1,\n" +
                "    \"userId\":1\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        jsonObject.put("trainId",9);
        trainController.updateTrainPlan(jsonObject);
    }

    @Test
    public void query22(){
        ResultMap resultMap = trainController.query(9L);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void query(){
        ResultMap resultMap = trainController.getTrainPlan(1L,null);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.print(jsonObject.toJSONString());
    }

    @Test
    public void submitPlan(){
        List<String> fileList = Lists.newArrayList();
        fileList.add("/xxxxx");
        fileList.add("/yyyyy");
        TrainPlanDetail record = new TrainPlanDetail();
        record.setTrainDetailId(7L);
        record.setContent("这里是内容");
        record.setFlow("这里是流程");
        record.setResult("这里是结果");
        record.setSubmitUserId(1L);
        record.setFileList(fileList);
        ResultMap resultMap = trainController.submitTrainPlan(record);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void submitPlan2(){

        TrainPlanDetail record = new TrainPlanDetail();
        record.setTrainDetailId(7L);
        record.setConfirmUserId(1L);
        ResultMap resultMap = trainController.confirmTrainPlan(record);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

}
