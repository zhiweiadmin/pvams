package com.goodpower.pvams.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.controller.unit.UnitController;
import com.goodpower.pvams.model.Company;
import com.goodpower.pvams.model.User;
import com.goodpower.pvams.service.UnitService;
import com.goodpower.pvams.service.UserService;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitControllerTest {

    @Autowired
    UnitService unitService;

    @Test
    public void addCompany(){
        Company company = new Company();
        company.setCompanyId(6L);
        company.setPhone("11101333");
        company.setCityId("100001133");
        company.setCompanyName("測試公司nnn");
        company.setContact("nnnn");
        company.setGrantAuthType(1);
        company.setProvinceId("yyyy");
        company.setLicense("yyyy");
        company.setTownAddress("yyyyyy村");
        company.setCountyId("123123123");
        System.out.println(JSONObject.toJSON(company).toString());
        //unitService.update(company);
    }

    @Autowired
    UnitController unitController;

    @Test
    public void query(){
        String cc = "{\"grantAuthType\":0,\"companyName\":\"测试\",\"provinceId\":110000000000,\"cityId\":110200000000,\"countyId\":110228000000,\"townAddress\":\"12\",\"contact\":\"12\",\"phone\":\"12\",\"license\":\"/file/1563591947069_logo.png\"}";
        Company company = JSON.parseObject(cc,Company.class);
        unitController.add(company);
    }

}
