package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.power.PowerManageController;
import com.goodpower.pvams.mapper.PowerStationBuildInfoMapper;
import com.goodpower.pvams.mapper.PowerStationConstructInfoMapper;
import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.mapper.PowerStationSuperviseMapper;
import com.goodpower.pvams.model.PowerStationConstructInfo;
import com.goodpower.pvams.model.PowerStationSupervise;
import com.goodpower.pvams.service.PowerStatService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerStationControllerTest {

    @Autowired
    PowerStationMapper stationMapper;

    @Autowired
    PowerStatService powerStatService;

    @Autowired
    PowerStationBuildInfoMapper buildInfoMapper;

    @Autowired
    PowerStationConstructInfoMapper constructInfoMapper;

    @Autowired
    PowerStationSuperviseMapper superviseMapper;

    @Test
    public void add(){
        PowerStationConstructInfo buildInfo = new PowerStationConstructInfo();
        buildInfo.setConstructCompany("光电宝新能源科技有限公司");
        buildInfo.setCompanyAddress("浙江省杭州市西湖区万塘路18号");
        buildInfo.setContact("刘四");
        buildInfo.setStationId(1L);
        buildInfo.setPhone("15109301110");
        buildInfo.setRemark("zzzz");
        constructInfoMapper.insert(buildInfo);
    }

    @Test
    public void add2(){
        PowerStationSupervise info = new PowerStationSupervise();
        info.setSuperviseCompany("光电宝新能源科技有限公司");
        info.setCompanyAddress("浙江省杭州市西湖区万塘路18号");
        info.setContact("刘四");
        info.setStationId(1L);
        info.setPhone("15109301110");
        info.setRemark("hhhhh");
        superviseMapper.insert(info);
    }

    @Test
    public void query(){
        //Float aa = powerStatService.getYearPowerProgress(1001L);
        //System.out.println(aa);
    }

    @Autowired
    PowerManageController powerManageController;

    @Test
    public void progress() throws ParseException {
//        ResultMap resultMap = powerManageController.weekProgress(1001L,2019,30);
//        JSONObject jsonObject = new JSONObject(resultMap);
//        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void progress1() throws ParseException {
        Map<String,Object> param = Maps.newHashMap();
        param.put("year",2019);
        param.put("quarter",3);
        Float f = powerStatService.getDateProgress(2,param);
        System.out.println(f);
    }

}
