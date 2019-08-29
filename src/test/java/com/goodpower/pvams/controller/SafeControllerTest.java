package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintaininfo.SafeController;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SafeControllerTest {

    @Autowired
    SafeController safeController;

    @Test
    public void addSafeTest(){
        List<Integer> week = Lists.newArrayList();
        week.add(1);
        week.add(2);
        week.add(4);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title","消防演练");
        jsonObject.put("content","消防演练内容");
        jsonObject.put("week",week);
        jsonObject.put("userId",1);
        jsonObject.put("stationId",1);
        jsonObject.put("safeId",4);

        ResultMap resultMap = safeController.updateSafePlan(jsonObject);
        JSONObject jsonObject1 = new JSONObject(resultMap);
        System.out.println(jsonObject1.toJSONString());
    }

}
