package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintaininfo.ComponentController;
import com.goodpower.pvams.controller.maintaininfo.FireMaintainController;
import com.goodpower.pvams.controller.maintaininfo.MaintainInfoController;
import com.goodpower.pvams.controller.power.PowerManageController;
import com.goodpower.pvams.controller.maintaininfo.ToolMaintainController;
import com.goodpower.pvams.model.MaintainInfo;
import com.goodpower.pvams.service.PowerStatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaintainInfoControllerTest {

    @Autowired
    MaintainInfoController maintainInfoController;

    @Autowired
    ToolMaintainController toolMaintainController;

    @Autowired
    FireMaintainController fireMaintainController;

    @Autowired
    PowerStatService powerStatService;

    @Test
    public void add(){
        String json = "{\"presentUserId\":\"1\",\"stationId\":\"1\",\"presenter\":\"超级管理员\",\"type\":1,\"presentDate\":\"2019-08-14\",\"startDate\":\"2019-08-08 00:00:00\",\"endDate\":\"2019-08-28 00:00:00\",\"subject\":\"测试\",\"infoDetail\":\"测试\"}";
        MaintainInfo info = JSONObject.parseObject(json,MaintainInfo.class);
        maintainInfoController.add(info);
    }

    @Test
    public void query(){
        String json = "{\"presentUserId\":\"1\",\"stationId\":\"1\",\"presenter\":\"超级管理员\",\"type\":1,\"presentDate\":\"2019-08-14\",\"startDate\":\"2019-08-08 00:00:00\",\"endDate\":\"2019-08-28 00:00:00\",\"subject\":\"测试\",\"infoDetail\":\"测试\"}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        ResultMap resultMap = maintainInfoController.update(JSONObject.parseObject(json,MaintainInfo.class));
        JSONObject jsonObject1 = new JSONObject(resultMap);
        System.out.println(jsonObject1.toJSONString());
    }

    @Test
    public void getUser(){
        ResultMap resultMap = maintainInfoController.getPresenter(1001L);
        JSONObject jsonObject1 = new JSONObject(resultMap);
        System.out.println(jsonObject1.toJSONString());
    }

    @Test
    public void ddd(){
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        System.out.print("------");
        System.out.print(day);
    }

    @Test
    public void aaa() throws ParseException {
        JSONObject jsonObject = powerStatService.getMonthProgress(1001L);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void queryAllToolPlan(){
        ResultMap resultMap = toolMaintainController.query(1001L,null,null);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void queryAllFirePlan(){
        //ResultMap resultMap = fireMaintainController.query(1001L);
        //JSONObject jsonObject = new JSONObject(resultMap);
        //System.out.println(jsonObject.toJSONString());
    }

    @Autowired
    ComponentController componentController;

    @Test
    public void queryAll(){
        ResultMap resultMap = componentController.query(1001L,null,null);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void queryRecord(){
    }

    @Test
    public void queryRevord(){

    }

    @Test
    public void addWork(){

    }

    @Test
    public void addRecord(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",111);
        jsonObject.put("stationId",1011);
        jsonObject.put("id",1);
        jsonObject.put("action",1);
        componentController.action(jsonObject);
    }

    @Autowired
    PowerManageController powerManageController;

    @Test
    public void dddd() throws UnknownHostException {
//         ResultMap resultMap = powerManageController.progress(1001L);
//         JSONObject jsonObject = new JSONObject(resultMap);
//         System.out.print(jsonObject);
        InetAddress addr = InetAddress.getLocalHost();
        System.out.println(addr.getHostAddress());
    }

}
