package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuTest {

    @Autowired
    MenuService menuService;

    @Test
    public void getMenu(){
        JSONObject jsonObject = menuService.getTreeNode(null);
        System.out.println(jsonObject.toJSONString());
    }

}
