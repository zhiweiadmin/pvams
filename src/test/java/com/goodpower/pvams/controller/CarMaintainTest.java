package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintaininfo.CarMaintainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarMaintainTest {

    @Autowired
    CarMaintainController carMaintainController;

    @Test
    public void query(){
//        ResultMap resultMap = carMaintainController.queryRecord(1L,1,20);
//        JSONObject jsonObject = new JSONObject(resultMap);
//        System.out.println(jsonObject.toJSONString());
    }

}
