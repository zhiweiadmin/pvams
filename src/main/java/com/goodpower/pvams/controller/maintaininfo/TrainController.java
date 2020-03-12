package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.mapper.TrainPlanDetailFileMapper;
import com.goodpower.pvams.model.SafePlan;
import com.goodpower.pvams.model.TrainPlan;
import com.goodpower.pvams.model.TrainPlanDetail;
import com.goodpower.pvams.service.TrainService;
import com.goodpower.pvams.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("train")
public class TrainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TrainService trainService;

    @Autowired
    TrainPlanDetailFileMapper trainPlanDetailFileMapper;

    @PostMapping("/addTrainPlan")
    public ResultMap addTrainPlan(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            trainService.addTrainPlan(request);
            return result.success().code(200).message("添加训练计划成功");
        }catch (Exception e){
            logger.error("添加训练计划失败",e);
            return result.success().code(400).message("添加训练计划失败");
        }
    }

    @PostMapping("/updateTrainPlan")
    public ResultMap updateTrainPlan(@RequestBody JSONObject request){
        ResultMap result = new ResultMap();
        try{
            if(request.getLong("trainId") == null){
                return result.success().code(400).message("trainId不能为空");
            }
            trainService.updateTrainPlan(request);
            return result.success().code(200).message("更新训练计划成功");
        }catch (Exception e){
            logger.error("更新安全演练计划失败",e);
            return result.success().code(400).message("更新训练计划失败");
        }
    }

    @GetMapping("/delete")
    public ResultMap delete(Long trainId){
        ResultMap result = new ResultMap();
        try{
            trainService.delete(trainId);
            return result.success().code(200).message("删除训练计划成功");
        }catch (Exception e){
            logger.error("删除训练计划失败",e);
            return result.success().code(400).message("删除训练计划失败");
        }
    }

    @PostMapping("/submitTrainPlan")
    public ResultMap submitTrainPlan(@RequestBody TrainPlanDetail trainPlanDetail){
        ResultMap result = new ResultMap();
        if(trainPlanDetail.getTrainDetailId() == null){
            return result.fail().code(400).message("trainDetailId不能为空");
        }
        if(trainPlanDetail.getSubmitUserId() == null){
            return result.fail().code(400).message("提交人不能为空");
        }
        if(StringUtils.isBlank(trainPlanDetail.getContent())){
            return result.fail().code(400).message("演练内容不能为空");
        }
        if(StringUtils.isBlank(trainPlanDetail.getFlow())){
            return result.fail().code(400).message("演练过程不能为空");
        }
        if(StringUtils.isBlank(trainPlanDetail.getResult())){
            return result.fail().code(400).message("演练结果不能为空");
        }
        try{
            if(trainPlanDetail.getFileList2() == null
                || trainPlanDetail.getFileList2().isEmpty()){
                return result.fail().code(400).message("培训记录不能为空!");
            }

            trainService.uploadTrainRecord(trainPlanDetail);
            trainService.uploadTrainFile(trainPlanDetail);
            trainService.updateTrainPlan(trainPlanDetail);
            return result.success().code(200).message("更新成功");
        }catch (Exception e){
            logger.error("submitTrainPlan失败:",e);
            return result.success().code(400).message("更新失败");
        }
    }

    @PostMapping("/confirmTrainPlan")
    public ResultMap confirmTrainPlan(@RequestBody TrainPlanDetail trainPlanDetail){
        ResultMap result = new ResultMap();
        try{
            if(trainPlanDetail.getConfirmUserId() == null){
                return result.fail().code(400).message("确认人不能为空");
            }
            if(trainPlanDetail.getTrainDetailId() == null){
                return result.fail().code(400).message("trainDetailId不能为空");
            }
            trainService.confirmTrainPlan(trainPlanDetail);
            return result.success().code(200).message("确认成功");
        }catch (Exception e){
            logger.error("confirmTrainPlan失败:",e);
            return result.success().code(400).message("确认失败");
        }
    }

    @GetMapping("/getTrainPlan")
    public ResultMap getTrainPlan(Long stationId,Integer year){
        ResultMap result = new ResultMap();
        if(stationId == null){
            return result.fail().code(400).message("电站id不能为空");
        }
        if(year == null){
            year = DateUtil.getCurYear();
        }
        String title = year+"年人员培训计划";
        try{
            Map<String,Object> resultMap = trainService.getTrainPlan(stationId,year);
            resultMap.put("title",title);
            return result.success().code(200).message("查询成功").setData(resultMap);
        }catch (Exception e){
            logger.error("getTrainPlan,stationId="+stationId,e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/query")
    public ResultMap query(Long trainId){
        ResultMap result = new ResultMap();
        if(trainId == null){
            return result.fail().code(400).message("safeId不能为空");
        }
        try{
            TrainPlan plan = trainService.getTrainPlan(trainId);
            if(plan != null){
                String msg = plan.getMsg();
                JSONObject jsonObject = JSONObject.parseObject(msg);
                jsonObject.put("trainId",trainId);
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
