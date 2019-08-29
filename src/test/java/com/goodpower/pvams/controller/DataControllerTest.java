package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.service.StationDataStatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataControllerTest {

    @Autowired
    StationDataStatService dataStatService;

    @Test
    public void getMonthData(){
        JSONObject jsonObject = dataStatService.getDeviceStat(1L,0,2019,8);
        System.out.println(jsonObject.toJSONString());
    }
}
