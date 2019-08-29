package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.DeviceMaintain;
import com.goodpower.pvams.model.SafePlan;
import com.goodpower.pvams.model.SafePlanDetail;
import com.goodpower.pvams.service.SafeService;
import com.goodpower.pvams.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("safe")
public class SafeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SafeService safeService;

    @PostMapping("/addSafePlan")
    public ResultMap addSafePlan(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            if(request.getInteger("year") == null){
                request.put("year",DateUtil.getCurYear());
            }
            if(request.getInteger("stationId") == null){
                return result.success().code(400).message("电站id不能为空");
            }
            safeService.addSafePlan(request);
            return result.success().code(200).message("添加安全演练计划成功");
        }catch (Exception e){
            logger.error("添加安全演练计划失败",e);
            return result.success().code(400).message("添加安全演练计划失败");
        }

    }

    @PostMapping("/updateSafePlan")
    public ResultMap updateSafePlan(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            if(request.getLong("safeId") == null){
                return result.success().code(400).message("safeId不能为空");
            }
            safeService.updateSafePlan(request);
            return result.success().code(200).message("更新安全演练计划成功");
        }catch (Exception e){
            logger.error("更新安全演练计划失败",e);
            return result.success().code(400).message("添加安全演练计划失败");
        }
    }

    @GetMapping("/delete")
    public ResultMap delete(Long safeId){
        ResultMap result = new ResultMap();
        try{
            if(safeId == null){
                return result.success().code(400).message("safeId不能为空");
            }
            safeService.delete(safeId);
            return result.success().code(200).message("删除安全演练计划成功");
        }catch (Exception e){
            logger.error("删除安全演练计划失败",e);
            return result.success().code(400).message("删除安全演练计划失败");
        }
    }

    @PostMapping("/submitSafePlan")
    public ResultMap submitSafePlan(@RequestBody SafePlanDetail planDetail){
        ResultMap result = new ResultMap();
        if(planDetail.getSafeDetailId() == null){
            return result.fail().code(400).message("safeDetailId不能为空");
        }
        if(planDetail.getSubmitUserId() == null){
            return result.fail().code(400).message("提交人不能为空");
        }
        if(StringUtils.isBlank(planDetail.getContent())){
            return result.fail().code(400).message("安全演练内容不能为空");
        }
        if(StringUtils.isBlank(planDetail.getFlow())){
            return result.fail().code(400).message("安全演练过程不能为空");
        }
        if(StringUtils.isBlank(planDetail.getResult())){
            return result.fail().code(400).message("安全演练结果不能为空");
        }
        try{
            safeService.uploadSafeFile(planDetail);
            safeService.updateSafePlan(planDetail);
            return result.success().code(200).message("更新成功");
        }catch (Exception e){
            logger.error("submitSafePlan:",e);
            return result.success().code(400).message("更新失败");
        }
    }

    @PostMapping("/confirmSafePlan")
    public ResultMap confirmSafePlan(@RequestBody SafePlanDetail planDetail){
        ResultMap result = new ResultMap();
        try{
            if(planDetail.getConfirmUserId() == null){
                return result.fail().code(400).message("确认人不能为空");
            }
            if(planDetail.getSafeDetailId() == null){
                return result.fail().code(400).message("SafeDetailId不能为空");
            }
            safeService.confirmSafePlan(planDetail);
            return result.success().code(200).message("确认成功");
        }catch (Exception e){
            logger.error("confirmSafePlan:",e);
            return result.success().code(400).message("确认失败");
        }
    }

    @GetMapping("/getSafePlan")
    public ResultMap getSafePlan(Long stationId,Integer year){
        ResultMap result = new ResultMap();
        if(stationId == null){
            return result.fail().code(400).message("电站id不能为空");
        }
        if(year == null){
            year = DateUtil.getCurYear();
        }
        String title = year+"年安全演练计划";
        try{
            Map<String,Object> resultMap = safeService.getSafePlan(stationId,year);
            resultMap.put("title",title);
            return result.success().code(200).message("查询成功").setData(resultMap);
        }catch (Exception e){
            logger.error("getSafePlan,stationId="+stationId,e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/query")
    public ResultMap query(Long safeId){
        ResultMap result = new ResultMap();
        if(safeId == null){
            return result.fail().code(400).message("safeId不能为空");
        }
        try{
            SafePlan safePlan = safeService.getSafePlan(safeId);
            if(safePlan != null){
                String msg = safePlan.getMsg();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                jsonObject.put("safeId",safeId);
                return result.success().code(200).message("查询成功").setData(jsonObject);
            }else{
                return result.success().code(200).message("查询成功").setData(new JSONObject());
            }
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

}
