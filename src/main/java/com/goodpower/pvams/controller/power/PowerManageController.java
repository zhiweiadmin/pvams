package com.goodpower.pvams.controller.power;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.Component;
import com.goodpower.pvams.model.PowerStation;
import com.goodpower.pvams.service.PowerStatService;
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
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("power")
public class PowerManageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PowerStatService powerStatService;

    public String getCurrentYear(){
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "";
    }

    /**
     * 获取发电量
     * @param stationId
     * @param
     * @param type 0:按月 1:按季度
     * @return
     */
    @GetMapping("/getPowerGeneration")
    public ResultMap getPowerGeneration(Long stationId,String starttime,String endtime,@RequestParam(defaultValue = "0") Integer type) throws ParseException {
        ResultMap result = new ResultMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(StringUtils.isBlank(starttime)){
            Date firstDay = DateUtil.getCurrYearFirst();
            starttime = sdf.format(firstDay);
        }
        if(StringUtils.isBlank(endtime)){
            Date lastDay = DateUtil.getCurrYearLast();
            endtime = sdf.format(lastDay);
        }
        if(stationId == null){
            return result.fail().code(400).message("请先选择电站!");
        }
        JSONObject data = powerStatService.getPowerGeneration(stationId,starttime,endtime,type);
        return result.setData(data).success().code(200).message("请求成功");
    }

    /**
     * 获取25年发电量
     * @param stationId
     * @return
     */
    @GetMapping("/getPowerWeakRate")
    public ResultMap getPowerWeakRate(Long stationId){
        ResultMap result = new ResultMap();
        if(stationId == null){
            return result.fail().code(400).message("请先选择电站!");
        }
        JSONObject data = powerStatService.getYearPowerStat(stationId);
        return result.setData(data).success().code(200).message("请求成功");
    }

    @GetMapping("/getPowerWeakRateMonth")
    public ResultMap getPowerWeakRateMonth(Long stationId,Long year){
        ResultMap result = new ResultMap();
        if(stationId == null){
            return result.fail().code(400).message("请先选择电站!");
        }
        if(year == null){
            return result.fail().code(400).message("year不能为空");
        }
        JSONObject data = powerStatService.getYearMonthPowerStat(stationId,year);
        return result.setData(data).success().code(200).message("请求成功");
    }

    @GetMapping("/progress/{stationId}")
    public ResultMap progress(@PathVariable Long stationId) {
        ResultMap resultMap = new ResultMap();
        try{
            JSONObject week  = powerStatService.getWeekProgress(stationId);
            JSONObject month = powerStatService.getMonthProgress(stationId);
            JSONObject quarter = powerStatService.getQuarterProgress(stationId);
            JSONObject year = powerStatService.getYearProgress(stationId);

            double weekProgress = 0;
            if(week != null){
                weekProgress = week.getDouble("powerProgress");
            }
            double monthProgress = 0;
            if(week != null){
                monthProgress = month.getDouble("powerProgress");
            }
            double quarterProgress = 0;
            if(week != null){
                quarterProgress = quarter.getDouble("powerProgress");
            }
            double yearProgress = 0;
            if(week != null){
                yearProgress = year.getDouble("powerProgress");
            }

            List<Double> oweList = Lists.newArrayList();
            List<Double> beyondList = Lists.newArrayList();
//            if(weekProgress < 100){
//                oweList.add(getDoubleVal(weekProgress-100));
//                beyondList.add(0d);
//            }else{
//                oweList.add(0d);
//                beyondList.add(getDoubleVal(weekProgress-100));
//            }
//
//            if(monthProgress < 100){
//                oweList.add(getDoubleVal(monthProgress-100));
//                beyondList.add(0d);
//            }else{
//                oweList.add(0d);
//                beyondList.add(getDoubleVal(monthProgress-100));
//            }
//
//            if(quarterProgress < 100){
//                oweList.add(getDoubleVal(quarterProgress-100));
//                beyondList.add(0d);
//            }else{
//                oweList.add(0d);
//                beyondList.add(getDoubleVal(quarterProgress-100));
//            }
            if(yearProgress < 100){
                oweList.add(getDoubleVal(yearProgress-100));
                beyondList.add(0d);
            }else{
                oweList.add(0d);
                beyondList.add(getDoubleVal(yearProgress-100));
            }

            JSONObject oweObject = new JSONObject();
            oweObject.put("name","欠发");
            oweObject.put("data",oweList);

            JSONObject byondObject = new JSONObject();
            byondObject.put("name","超发");
            byondObject.put("data",beyondList);

            JSONArray beyondArry = new JSONArray();
            beyondArry.add(oweObject);
            beyondArry.add(byondObject);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("week",week);
            jsonObject.put("month",month);
            jsonObject.put("quarter",quarter);
            jsonObject.put("year",year);
            jsonObject.put("beyond",beyondArry);
            resultMap.success().message("请求成功").setData(jsonObject);
        }catch (Exception e){
            logger.error("progress error",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/import/{stationId}")
    public ResultMap upload(@PathVariable Long stationId,@RequestParam("file") MultipartFile file){
        ResultMap resultMap = new ResultMap();
        try{
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                Workbook workbook;
                if (fileName.endsWith("xlsx")){
                    workbook = new XSSFWorkbook(file.getInputStream());
                    powerStatService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    powerStatService.saveExcelData(stationId,workbook);
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

    @GetMapping("/export/{stationId}")
    public ResultMap export(@PathVariable Long stationId,HttpServletResponse response) throws IOException {
        {
            //创建HSSFWorkbook对象(excel的文档对象)
            HSSFWorkbook wb = new HSSFWorkbook();
            //建立新的sheet对象（excel的表单）
            HSSFSheet sheet = wb.createSheet("发电量统计数据");
            //设置单元格合并
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
            HSSFCellStyle boderStyle = wb.createCellStyle();
            boderStyle.setAlignment(HorizontalAlignment.CENTER);
            HSSFFont font = wb.createFont();
            font.setBold(true);
            font.setFontName("宋体");
            font.setFontHeightInPoints((short)16);
            boderStyle.setFont(font);

            HSSFCellStyle boderStyle1 = wb.createCellStyle();
            boderStyle1.setAlignment(HorizontalAlignment.CENTER);
            HSSFFont font1 = wb.createFont();
            font1.setFontName("宋体");
            font.setBold(true);
            font1.setFontHeightInPoints((short)13);
            boderStyle1.setFont(font1);

            HSSFCellStyle boderStyle2 = wb.createCellStyle();
            boderStyle2.setAlignment(HorizontalAlignment.CENTER);
            HSSFFont font2 = wb.createFont();
            font2.setFontName("宋体");
            font2.setFontHeightInPoints((short)12);
            boderStyle2.setFont(font2);

            int[] width = {256*18+184,256*18+184,256*18+184,256*18+184,256*18+184,256*18+184,256*30+184};
            //设置宽度
            sheet.setColumnWidth(0,width[0]);
            sheet.setColumnWidth(1,width[1]);
            sheet.setColumnWidth(2,width[2]);
            sheet.setColumnWidth(3,width[3]);
            sheet.setColumnWidth(4,width[4]);
            sheet.setColumnWidth(5,width[5]);
            sheet.setColumnWidth(6,width[6]);

            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
            HSSFRow row1 = sheet.createRow(0);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
            HSSFCell cell = row1.createCell(0);
            cell.setCellValue("发电量统计数据模板");
            cell.setCellStyle(boderStyle);

            //在sheet里创建第二行
            HSSFRow row2 = sheet.createRow(1);
            //创建单元格并设置单元格内容
            HSSFCell cell2_0 = row2.createCell(0);
            cell2_0.setCellValue("日期");
            cell2_0.setCellStyle(boderStyle1);
            HSSFCell cell2_1 = row2.createCell(1);
            cell2_1.setCellValue("理论发电量");
            cell2_1.setCellStyle(boderStyle1);
            HSSFCell cell2_2 = row2.createCell(2);
            cell2_2.setCellValue("辐照发电量");
            cell2_2.setCellStyle(boderStyle1);
            HSSFCell cell2_3 = row2.createCell(3);
            cell2_3.setCellValue("实际发电量");
            cell2_3.setCellStyle(boderStyle1);
            HSSFCell cell2_4 = row2.createCell(4);
            cell2_4.setCellValue("理论等效小时数");
            cell2_4.setCellStyle(boderStyle1);
            HSSFCell cell2_5 = row2.createCell(5);
            cell2_5.setCellValue("辐照等效小时数");
            cell2_5.setCellStyle(boderStyle1);
            HSSFCell cell2_6 = row2.createCell(6);
            cell2_6.setCellValue("实际等效小时数");
            cell2_6.setCellStyle(boderStyle1);

            //输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            String fileName = "发电量统计数据模板.xls";
            response.setContentType("application/msexcel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
            wb.write(output);
            output.close();
            return null;
        }
    }

    public double getDoubleVal(double d){
        BigDecimal b = new BigDecimal(d);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
