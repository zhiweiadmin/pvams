package com.goodpower.pvams.controller.unit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.Company;
import com.goodpower.pvams.model.User;
import com.goodpower.pvams.service.UnitService;
import com.goodpower.pvams.service.UserService;
import com.goodpower.pvams.util.JWTUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("unit")
public class UnitController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UnitService unitService;

    @GetMapping("/query/{companyId}")
    //@RequiresPermissions(logical = Logical.OR, value = {"addCompany"})
    public ResultMap queryCompany(@PathVariable Long companyId){
        ResultMap result = new ResultMap();
        try{
            Company company = unitService.query(companyId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("company",company);
            return result.success().message("查询成功").setData(jsonObject);
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.fail().message("查询失败");
        }
    }

    @PostMapping("/add")
    //@RequiresPermissions(logical = Logical.OR, value = {"addCompany"})
    public ResultMap add(@RequestBody Company company) {
        ResultMap result = new ResultMap();
        try {
            if (StringUtils.isBlank(company.getCompanyName())) {
                return result.fail().message("公司名称不能为空");
            }
            if (company.getGrantAuthType() == null) {
                return result.fail().message("授权类型不能为空");
            }
            if (StringUtils.isBlank(company.getContact())) {
                return result.fail().message("联系人不能为空");
            }
            if (StringUtils.isBlank(company.getPhone())) {
                return result.fail().message("联系方式不能为空");
            }
            JSONObject jsonObject = unitService.add(company);
            return result.success().message("添加成功").setData(jsonObject);
        } catch (Exception e) {
            logger.error("添加公司失败", e);
            return result.fail().message("添加失败");
        }
    }

    @PostMapping("/update")
    //@RequiresPermissions(logical = Logical.OR, value = {"updateCompany"})
    public ResultMap update(@RequestBody Company company){
        ResultMap result = new ResultMap();
        try{
            if(company.getCompanyId() == null){
                return result.fail().message("公司id不能为空");
            }
            if (StringUtils.isBlank(company.getCompanyName())) {
                return result.fail().message("公司名称不能为空");
            }
            if (company.getGrantAuthType() == null) {
                return result.fail().message("授权类型不能为空");
            }
            if (StringUtils.isBlank(company.getContact())) {
                return result.fail().message("联系人不能为空");
            }
            if (StringUtils.isBlank(company.getPhone())) {
                return result.fail().message("联系方式不能为空");
            }
            unitService.update(company);
            return result.success().message("更新成功");
        }catch (Exception e){
            logger.error("更新公司失败",e);
            return result.fail().message("更新公司失败");
        }
    }

}
