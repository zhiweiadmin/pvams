package com.goodpower.pvams.controller.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.City;
import com.goodpower.pvams.model.County;
import com.goodpower.pvams.model.Province;
import com.goodpower.pvams.service.CommonService;
import com.goodpower.pvams.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
public class CommonController {

    @Autowired
    CommonService commonService;

    @GetMapping("/getDateWeek")
    public ResultMap getDateWeek() throws ParseException {
        JSONArray dateArray = DateUtil.getDate();
        ResultMap result = new ResultMap();
        return result.code(200).success().message("请求成功").setData(dateArray);
    }

    @GetMapping("/getProvince")
    public ResultMap getProvince(){
        ResultMap result = new ResultMap();
        List<Province> data = commonService.getProvince();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",data);
        return result.code(200).success().message("请求成功").setData(jsonObject);
    }

    @GetMapping("/getCity/{provinceId}")
    public ResultMap getCity(@PathVariable Long provinceId){
        ResultMap result = new ResultMap();
        if(provinceId == null){
            return result.fail().message("provinceId不能为空");
        }
        List<City> data = commonService.getCity(provinceId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",data);
        return result.code(200).success().message("请求成功").setData(jsonObject);
    }

    @GetMapping("/getCounty/{cityId}")
    public ResultMap getCounty(@PathVariable Long cityId){
        ResultMap result = new ResultMap();
        if(cityId == null){
            return result.fail().message("cityId不能为空");
        }
        List<County> data = commonService.getCounty(cityId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",data);
        return result.code(200).success().message("请求成功").setData(jsonObject);
    }

}
