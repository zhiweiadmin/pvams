package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.WorkRecord;
import com.goodpower.pvams.service.CommonService;
import com.goodpower.pvams.service.WorkRecordService;
import com.goodpower.pvams.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 常规数据维护
 */
@RestController
@RequestMapping("dataMaintain")
public class DataMaintainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WorkRecordService workRecordService;

    @Autowired
    CommonService commonService;

    @GetMapping("/queryRecord")
    public ResultMap queryRecord(Long stationId,Long userId,String time){
        ResultMap resultMap = new ResultMap();
        if(stationId == null){
            return resultMap.fail().code(400).message("请先选择电站!");
        }
        if(userId == null){
            return resultMap.fail().code(400).message("userId不能为空");

        }
        try{
            Date date = new Date();
            if(StringUtils.isNotBlank(time)){
                //默认当天
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(time);
            }
            List<WorkRecord> resultList = workRecordService.query(stationId,userId,date);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultList",resultList);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            logger.error("error",e);
            resultMap.fail().message("查询失败");
        }

        return resultMap;
    }


    @PostMapping("/addRecord")
    public ResultMap addRecord(@RequestBody WorkRecord record){
        ResultMap resultMap = new ResultMap();
        if(record.getStationId() == null){
            return resultMap.fail().message("请先选择电站!");
        }
        if(record.getUserId() == null){
            return resultMap.fail().message("userId不能为空");
        }
        if(record.getType() == null){
            return resultMap.fail().message("类型不能为空");
        }
        try{
            workRecordService.add(record);
            resultMap.success().message("填写成功");
        }catch (Exception e){
            logger.error("填写失败",e);
            resultMap.fail().message("填写失败");
        }
        return resultMap;
    }

    /**
     * 查询今日详情
     * @param stationId
     * @return
     */
    @GetMapping("/queryTodayDetail")
    public ResultMap queryTodayDetail(Long stationId,String time){
        ResultMap resultMap = new ResultMap();
        if(stationId == null){
            return resultMap.fail().code(400).message("请先选择电站!");
        }

        try{
            JSONObject jsonObject = workRecordService.queryTodayDetail(stationId,time);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            logger.error("error",e);
            resultMap.fail().message("查询失败");
        }

        return resultMap;
    }

    @GetMapping("/getTianqi")
    public ResultMap getTianqi(HttpServletRequest request){
        String ip = CommonService.getIpAddress(request);
        String host = request.getRemoteHost();
        String addr = request.getRemoteAddr();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ip",ip);
        jsonObject.put("host",host);
        jsonObject.put("addr",addr);
        ResultMap resultMap = new ResultMap();
        return resultMap.setData(ip).success();
    }

    /**
     * 获取本周的实际等效小时数
     * @param stationId
     * @return
     */
    @GetMapping("/getWeekHour")
    public ResultMap getWeekHour(Long stationId,String time) throws ParseException {
        ResultMap resultMap = new ResultMap();
        Date date = new Date();
        if(StringUtils.isNotBlank(time)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(time);
        }
        //获取本周的实际等效小时数
        String weekFirstDay = DateUtil.getWeekStartDate(date);
        String weekEndDay = DateUtil.getWeekEndDate(date);
        List<Map<String,Object>> resultList = workRecordService.getWeekRealHour(stationId,weekFirstDay,weekEndDay);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataResult",resultList);
        return resultMap.setData(jsonObject).success();
    }

}
