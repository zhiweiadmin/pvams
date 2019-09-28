package com.goodpower.pvams.controller.data;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.service.StationDataStatService;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据分析
 */
@RestController
    @RequestMapping("data")
public class DataController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StationDataStatService dataStatService;

    /**
     *
     * @param stationId
     * @param type 0：月  1:年
     * @param val
     *
     * num 中间显示多少个柱子
     * @return
     */
    @GetMapping("/getDeviceStat")
    public ResultMap getDeviceStat(Long stationId,Integer type,String val,Integer num,String deviceName){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.fail().message("请先选择电站!");
            }
            if(num < 0){
                return result.fail().message("请输入正整数!");
            }
            int year = getYear(val);
            int month = getMonth(val);
            JSONObject jsonObject = dataStatService.getDeviceStat(stationId,type,year,month,num,deviceName);
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    /**
     *
     * @param stationId
     * @param type 0：月  1:年
     * @param val
     * @return
     */
    @GetMapping("/getStatData")
    public ResultMap getStatData(Long stationId,Integer type,String val){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.fail().message("请先选择电站!");
            }
            if(val == null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                val = sdf.format(new Date());
            }
            int year = getYear(val);
            int month = getMonth(val);
            JSONObject jsonObject = new JSONObject();
            if(type == 0){
                jsonObject = dataStatService.getMonthStatData(stationId,year,month);
            }else if(type == 1){
                jsonObject = dataStatService.getYearStatData(stationId,year);
            }
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    @GetMapping("/getDeviceStatDetail")
    public ResultMap getDeviceStatDetail(Long stationId,Integer statType,Integer type,String val,Integer pageNo,Integer pageSize){
        ResultMap result = new ResultMap();
        try{
            if(stationId== null){
                return result.fail().message("请先选择电站!");
            }
            if(statType == null){
                statType = 3;
            }

            int year = getYear(val);
            int month = getMonth(val);
            JSONObject jsonObject = new JSONObject();
            if(type == 0){
                jsonObject = dataStatService.getMonthDeviceStatDetail(stationId,statType,year,month,pageNo,pageSize);
            }else if(type == 1){
                jsonObject = dataStatService.getYearDeviceStatDetail(stationId,statType,year,pageNo,pageSize);
            }
            return result.success().code(200).setData(jsonObject).message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            return result.success().code(400).message("查询失败");
        }
    }

    @PostMapping("/import/{stationId}")
    public ResultMap upload(@PathVariable Long stationId, @RequestParam("file") MultipartFile file){
        ResultMap resultMap = new ResultMap();
        try{
            if(stationId == null){
                return resultMap.fail().message("请先选择电站!");
            }
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                Workbook workbook;
                if (fileName.endsWith("xlsx")){
                    workbook = new XSSFWorkbook(file.getInputStream());
                    boolean flag = dataStatService.checkDataCorrect(workbook);
                    if(!flag){
                        return resultMap.fail().message("数据错误!");
                    }
                    dataStatService.importDeviceData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    boolean flag = dataStatService.checkDataCorrect(workbook);
                    if(!flag){
                        return resultMap.fail().message("数据错误!");
                    }
                    dataStatService.importDeviceData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else{
                    resultMap.fail().message("文件格式错误").code(400);
                }
            }
        }catch (Exception e){
            if(e instanceof ParseException){
                logger.error("导入信息失败,转换错误",e);
                return resultMap.fail().message("导入失败,请检查文件格式是否正确!");
            }else{
                logger.error("导入信息失败",e);
                return resultMap.fail().message("导入失败!");
            }
        }
        return resultMap;
    }

    @GetMapping("/template/{stationId}")
    public ResultMap template(@PathVariable Long stationId,HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        OutputStream output = response.getOutputStream();
        dataStatService.createDevicePowerSheet(wb);
        //dataStatService.createDeviceHourSheet(wb);
        //dataStatService.createStatSheet(wb);
        response.reset();
        String fileName = "数据分析模板"+".xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

    @GetMapping("/template")
    public ResultMap template2(HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        OutputStream output = response.getOutputStream();
        dataStatService.createDevicePowerSheet(wb);
        //dataStatService.createDeviceHourSheet(wb);
        //dataStatService.createStatSheet(wb);
        response.reset();
        String fileName = "数据分析模板"+".xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

    public int getYear(String dateVal){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            if(StringUtils.isBlank(dateVal)){
                return DateUtil.getCurYear();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(dateVal));
            return calendar.get(Calendar.YEAR);
        }catch (Exception e){
            logger.error("转换时间失败的参数是:",dateVal);
            logger.error("转换时间失败:",e);
        }
        return DateUtil.getCurYear();

    }

    public int getMonth(String dateVal) throws ParseException {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            if(StringUtils.isBlank(dateVal)){
                return DateUtil.getCurMonth();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(dateVal));
            return calendar.get(Calendar.MONTH)+1;
        }catch (Exception e){
            logger.error("转换时间失败的参数是:",dateVal);
            logger.error("转换时间失败:",e);
        }
        return DateUtil.getCurMonth();
    }

}
