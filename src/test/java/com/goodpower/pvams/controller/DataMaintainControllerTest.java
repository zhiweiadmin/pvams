package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintaininfo.DataMaintainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataMaintainControllerTest {

    @Autowired
    DataMaintainController dataMaintainController;

    @Test
    public void query(){
//        ResultMap resultMap = dataMaintainController.queryRecord(1L,1L);
//        JSONObject jsonObject = new JSONObject(resultMap);
//        System.out.print(jsonObject.toJSONString());
    }

}
