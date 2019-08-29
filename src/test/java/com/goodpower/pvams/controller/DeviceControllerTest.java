package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.maintaininfo.FireMaintainController;
import com.goodpower.pvams.service.StationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceControllerTest {

    @Autowired
    StationService stationService;

    @Autowired
    FireMaintainController fireMaintainController;

    @Test
    public void getStationDeviceCount(){
        Long count = stationService.getStationDeviceCount(1001L);
        System.out.println(count);
    }

    @Test
    public void query(){
        ResultMap map = fireMaintainController.queryRecord(1L,null,null);
        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject.toJSONString());
    }

}
