package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.power.MemberController;
import com.goodpower.pvams.model.PowerStationMember;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    MemberController memberController;

    @Test
    public void add(){
        PowerStationMember powerStationMember = new PowerStationMember();
        powerStationMember.setCertificatePic("www.baidu.com");
        powerStationMember.setIdCard("32122222XXXXX");
        powerStationMember.setMaintainCompany("江苏苏宁");
        powerStationMember.setRealName("jiangzhiwei");
        powerStationMember.setPosition("总经理");
        powerStationMember.setCredential("工程师");
        powerStationMember.setUserPic("wwwwwww");
        powerStationMember.setStationId(1L);
        powerStationMember.setMemberId(1L);
        memberController.update(powerStationMember);
    }

    @Test
    public void query(){
        ResultMap resultMap = memberController.query(1L,null,null);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

}
