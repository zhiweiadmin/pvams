package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.power.PowerManageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoweControllerTest {

    @Autowired
    PowerManageController powerManageController;

    @Test
    public void getData(){
        //ResultMap resultMap = powerManageController.getPowerGeneration(1001l,"2018-02-01","2019-05-04",1);
        //JSONObject jsonObject = new JSONObject(resultMap);
        //System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void getData2(){
        ResultMap resultMap = powerManageController.getPowerWeakRate(1001l);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void getData3(){
        ResultMap resultMap = powerManageController.getPowerWeakRateMonth(1001L,2018L);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void ss() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2019-01-06");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        System.out.println("-------------- : "+week);
    }

}
