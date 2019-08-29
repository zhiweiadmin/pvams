package com.goodpower.pvams.controller.finance;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.StationFinanceBaseInfo;
import com.goodpower.pvams.service.FinanceService;
import com.goodpower.pvams.service.LoanProfitService;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("finance")
public class FinanceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FinanceService financeService;

    @GetMapping("/getFinanceInfo")
    public ResultMap getFinanceInfo(Long stationId){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.success().code(400).message("stationId不能为空");
            }
            StationFinanceBaseInfo stationFinanceBaseInfo = financeService.getBaseInfo(stationId);
            JSONObject jsonObject = new JSONObject();
            if(stationFinanceBaseInfo != null){
                jsonObject.put("total_investment",stationFinanceBaseInfo.getTotalCost());
                jsonObject.put("finance_cost",stationFinanceBaseInfo.getLoanCost());
                jsonObject.put("run_cost",stationFinanceBaseInfo.getRunCost());
                jsonObject.put("finance_cycle",null);
                jsonObject.put("run_cycle",null);
            }
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/getYearBenefit")
    public ResultMap getYearBenefit(Long stationId,Integer statType){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.success().code(400).message("stationId不能为空");
            }
            List<Map<String,Object>> resultList = Lists.newArrayList();
            if(statType == 3){
                resultList =  financeService.getYearDataStatReal(stationId);
            }else{
                resultList =  financeService.getYearDataStatPlan(stationId);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultData",resultList);
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/getMonthBenefit")
    public ResultMap getMonthBenefit(Long stationId,Integer statType,Integer year){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.success().code(400).message("stationId不能为空");
            }
            List<Map<String,Object>> resultList = Lists.newArrayList();
            if(statType == 3){
                resultList =  financeService.getMonthDataStatReal(stationId,year);
            }else{
                resultList =  financeService.getMonthDataStatPlan(stationId,year);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultData",resultList);
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/profit")
    public ResultMap profit(Long stationId){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.success().code(400).message("stationId不能为空");
            }
            JSONObject jsonObject = financeService.getProfit(stationId,null);
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/import/{stationId}")
    public ResultMap upload(@PathVariable Long stationId,HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        financeService.createTemplate(wb);
        financeService.createTemplate2(wb);
        financeService.createTemplate3(wb);
        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = "财务模型模板.xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

    @GetMapping("/template")
    public ResultMap template(HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        financeService.createTemplate(wb);
        financeService.createTemplate2(wb);
        financeService.createTemplate3(wb);
        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = "财务模型模板.xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

}
