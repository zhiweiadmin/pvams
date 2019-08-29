package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.CarRecord;
import com.goodpower.pvams.service.CarRecordService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("carRecord")
public class CarTravelRecordController {

    @Autowired
    CarRecordService carRecordService;

    @GetMapping("/query/{stationId}")
    public ResultMap query(@PathVariable Long stationId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null) {
                pageSize = 10;
            }
            if(stationId == null){
                return resultMap.fail().code(400).message("电站id不能为空");
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("stationId",stationId);
            param.put("offset",(pageSize-1)*pageSize);
            param.put("limit",pageSize);
            JSONObject jsonObject =  carRecordService.query(param);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

}
