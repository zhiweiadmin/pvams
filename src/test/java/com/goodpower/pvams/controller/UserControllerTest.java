package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.common.CommonController;
import com.goodpower.pvams.controller.user.UserController;
import com.goodpower.pvams.mapper.PowerStationDeviceMapper;
import com.goodpower.pvams.model.PowerStationDevice;
import com.goodpower.pvams.model.User;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    CommonController commonController;

    @Autowired
    PowerStationDeviceMapper deviceMapper;

    @Autowired
    UserController userController;

    @Test
    public void getDate(){
        PowerStationDevice device = deviceMapper.selectByPrimaryKey(35L);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("device",device);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setUsername("user_007");
        user.setRealname("蒋伟6");
        user.setRole(3);
        user.setMail("jiangzw92@gmail.com");
        user.setPhone("123123123123");
        user.setPassword("12345");
        user.setUserType(0);
        user.setUnitId(1001L);
        user.setUserId(6L);
        userController.update(user);
    }

    @Test
    public void checkName(){
        ResultMap resultMap = userController.query(1,1001L,1,10);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void add(){
        String json = "{\"username\":\"111\",\"realname\":\"111\",\"phone\":\"11\",\"mail\":\"11\",\"role\":5,\"password\":\"11\",\"userType\":\"1\",\"unitId\":\"1\"}";
        User userObject = JSON.parseObject(json,User.class);
        ResultMap resultMap = userController.add(userObject);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void getSheng(){
        ResultMap map = commonController.getCity(11L);
        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject.toJSONString());
    }

    @Test
    public void getUser(){
        ResultMap resultMap = userController.queryUserInfo(1L);
        JSONObject jsonObject = new JSONObject(resultMap);
        System.out.println(jsonObject.toJSONString());
    }

}