package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.controller.maintaininfo.DataMaintainController;
import com.goodpower.pvams.model.WorkRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkRecordControllerTest {

    @Autowired
    DataMaintainController maintainController;

    @Test
    public void addTest(){
        WorkRecord record = new WorkRecord();
        record.setAttach("/file/xxx");
        record.setStationId(1L);
        record.setTitle("年报填写");
        record.setRecordDesc("填写联播网年报");
        record.setType(5);
        record.setUserId(1L);
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(record);
        System.out.println(jsonObject.toJSONString());
        //maintainController.addRecord(record);
    }

    @Test
    public void query() throws ParseException {
//        ResultMap resultMap = maintainController.getWeekHour(1L);
//        JSONObject jsonObject = new JSONObject(resultMap);
//        System.out.println(jsonObject.toJSONString());
    }

}
