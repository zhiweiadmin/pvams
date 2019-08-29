package com.goodpower.pvams.controller.maintain;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.mapper.DeviceMaintainDetailFileMapper;
import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.model.DeviceMaintain;
import com.goodpower.pvams.model.DeviceMaintainDetail;
import com.goodpower.pvams.model.DeviceMaintainDetailFile;
import com.goodpower.pvams.model.PowerStation;
import com.goodpower.pvams.service.MaintainService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("maintain")
public class MaintainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MaintainService maintainService;

    @Autowired
    PowerStationMapper powerStationMapper;

    /**
     *
     * {
     *     "stationId":1,
     *     "acSide":{
     *         "name":"111",
     *         "week":[1,2,3,4,5]
     *     },
     *     "dcSide":{
     *         "name":"222",
     *         "week":[1,2,3,4,5]
     *     },
     *     "secondaryEquipment":{
     *         "name":"333",
     *         "week":[1,2,3,4,5]
     *     }
     * }
     *
     *
     * 添加发电站维保计划
     * @param request
     * @return
     */
    @PostMapping("/addMaintainPlan")
    //@RequiresPermissions(logical = Logical.OR, value = {"addMaintainPlan"})
    public ResultMap addMaintainPlan(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            maintainService.addMaintainPlan(request);
            return result.success().code(200).message("添加成功");
        }catch (Exception e){
            logger.error("添加维保计划失败",e);
            return result.success().code(400).message("添加维保计划失败");
        }

    }

    @PostMapping("/updateMaintainPlan")
    public ResultMap updateMaintainPlan(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            if(request.getLong("maintainId") == null){
                return result.success().code(400).message("maintainId不能为空");
            }
            maintainService.updateMaintainPlan(request);
            return result.success().code(200).message("更新成功");
        }catch (Exception e){
            logger.error("更新失败",e);
            return result.success().code(400).message("更新失败");
        }

    }

    @Autowired
    DeviceMaintainDetailFileMapper fileMapper;

    @PostMapping("/submitMaintainPlan")
    //@RequiresRoles(logical = Logical.OR, value = {"2","5"})
    public ResultMap submitMaintainPlan(@RequestBody DeviceMaintainDetail deviceMaintainDetail){
        ResultMap result = new ResultMap();
        if(deviceMaintainDetail.getMaintainId() == null){
            return result.fail().code(400).message("maintainId不能为空");
        }
        if(deviceMaintainDetail.getMaintainWeek() == null){
            return result.fail().code(400).message("week不能为空");
        }
        if(StringUtils.isBlank(deviceMaintainDetail.getMaintainer())){
            return result.fail().code(400).message("维保人不能为空");
        }
        if(StringUtils.isBlank(deviceMaintainDetail.getContent())){
            return result.fail().code(400).message("维保内容不能为空");
        }
        if(StringUtils.isBlank(deviceMaintainDetail.getFlow())){
            return result.fail().code(400).message("维保过程不能为空");
        }
        if(StringUtils.isBlank(deviceMaintainDetail.getResult())){
            return result.fail().code(400).message("维保结果不能为空");
        }
        try{
            Long detailId = deviceMaintainDetail.getDetailId();
            for(String path : deviceMaintainDetail.getFileList()){
                DeviceMaintainDetailFile file = new DeviceMaintainDetailFile();
                file.setDetailId(detailId);
                file.setPath(path);
                Date date = new Date();
                file.setCreateDttm(date);
                file.setUpdateDttm(date);
                fileMapper.insert(file);
            }
            maintainService.updateMaintainPlan(deviceMaintainDetail);
            return result.success().code(200).message("更新成功");
        }catch (Exception e){
            logger.error("updateMaintainPlan失败:",e);
            return result.success().code(400).message("更新失败");

        }
    }

    @GetMapping("/getMaintainPlan")
    public ResultMap getMaintainPlan(Long maintainId,Integer week){
        ResultMap result = new ResultMap();
        if(maintainId == null || week == null){
            return result.fail().code(400).message("maintainId或者week不能为空");
        }
        try{
            Map<String,Object> maintainMap = maintainService.getMaintainPlan(maintainId,week);
            return result.success().code(200).message("查询成功").setData(maintainMap);
        }catch (Exception e){
            logger.error("getMaintainPlan失败,maintainId="+maintainId,e);
            return result.success().code(400).message("查询失败");
        }
    }

    @PostMapping("/confirmMaintainPlan")
    //@RequiresRoles(logical = Logical.OR, value = {"2","5"})
    //@RequiresPermissions(logical = Logical.OR, value = {"confirmMaintainPlan"})
    public ResultMap confirmMaintainPlan(@RequestBody DeviceMaintainDetail deviceMaintainDetail){
        ResultMap result = new ResultMap();
        try{
            maintainService.confirmMaintainPlan(deviceMaintainDetail);
            return result.success().code(200).message("确认成功");
        }catch (Exception e){
            logger.error("updateMaintainPlan失败:",e);
            return result.success().code(400).message("确认失败");

        }
    }

    /**
     * 获取发电站XX年所有的维保计划
     * @param stationId
     * @param year
     * @return
     */
    @GetMapping("/getStationMaintainPlan")
    public ResultMap getStationMaintainPlan(Long stationId,String year){
        ResultMap result = new ResultMap();
        if(stationId == null || year == null){
            return result.fail().code(400).message("电站id和年份不能为空");
        }
        if(StringUtils.isBlank(year)){
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(calendar.YEAR)+"";
        }
        PowerStation powerStation = powerStationMapper.selectByPrimaryKey(stationId);
        if(powerStation == null){
            return result.fail().code(400).message("发电站不存在");
        }
        String title = year+"年维护保养计划";
        try{
            Map<String,Object> maintainMap = maintainService.getStationMaintainPlan(stationId,year);
            maintainMap.put("title",title);
            return result.success().code(200).message("查询成功").setData(maintainMap);
        }catch (Exception e){
            logger.error("getStationMaintainPlan,stationId="+stationId,e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/delete")
    public ResultMap delete(Long maintainId){
        ResultMap result = new ResultMap();
        if(maintainId == null){
            return result.fail().code(400).message("maintainId不能为空");
        }
        try{
            maintainService.deleteMaintainPlan(maintainId);
            return result.success().code(200).message("删除成功");
        }catch (Exception e){
            logger.error("删除失败",e);
            return result.success().code(400).message("删除成功");
        }
    }

    @GetMapping("/query")
    public ResultMap query(Long maintainId){
        ResultMap result = new ResultMap();
        if(maintainId == null){
            return result.fail().code(400).message("maintainId不能为空");
        }
        try{
            DeviceMaintain deviceMaintain = maintainService.getDeviceMaintain(maintainId);
            if(deviceMaintain != null){
                String msg = deviceMaintain.getMsg();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                jsonObject.put("maintainId",maintainId);
                return result.success().code(200).message("查询成功").setData(jsonObject);
            }else{
                return result.success().code(200).message("查询成功").setData(new JSONObject());
            }
        }catch (Exception e){
            logger.error("查询失败"+maintainId,e);
            return result.success().code(400).message("查询失败");
        }
    }

}
