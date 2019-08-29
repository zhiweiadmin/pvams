package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.mapper.ToolMaintainMapper;
import com.goodpower.pvams.model.Component;
import com.goodpower.pvams.model.PowerStation;
import com.goodpower.pvams.model.ToolCheckRecord;
import com.goodpower.pvams.model.ToolMaintain;
import com.goodpower.pvams.service.ToolMaintainService;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tool")
public class ToolMaintainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ToolMaintainService toolMaintainService;

    @Autowired
    ToolMaintainMapper toolMaintainMapper;

    @Autowired
    PowerStationMapper powerStationMapper;

    @GetMapping("/query")
    public ResultMap query(Long stationId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(stationId == null){
                return resultMap.fail().code(400).message("stationId不能为空");
            }
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null){
                pageSize = 20;
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("stationId",stationId);

            JSONObject jsonObject =  toolMaintainService.selectByFields(param);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/check")
    public ResultMap check(@RequestBody ToolCheckRecord toolCheckRecord){
        ResultMap resultMap = new ResultMap();
        try{
            toolMaintainService.addToolCheckRecord(toolCheckRecord);
            resultMap.success().message("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/confirm")
    public ResultMap confirm(Long userId,Integer id,Integer confirmStatus){
        ResultMap resultMap = new ResultMap();
        try{
            toolMaintainService.confirm(userId,id,confirmStatus);
            resultMap.success().message("成功");
        }catch (Exception e){
            logger.error("确认失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/queryRecord")
    public ResultMap queryRecord(Long toolId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null) {
                pageSize = 10;
            }
            if(toolId == null){
                return resultMap.fail().code(400).message("toolId");
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("toolId",toolId);
            JSONObject jsonObject= toolMaintainService.queryToolCheckRecord(param);
            resultMap.success().message("请求成功").setData(jsonObject);
        }catch (Exception e){
            logger.error("请求失败",e);
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
                    toolMaintainService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    toolMaintainService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else{
                    resultMap.fail().message("文件格式错误").code(400);
                }
            }
        }catch (Exception e){
            logger.error("导入失败",e);
            e.printStackTrace();
            resultMap.success().message("导入失败");
        }
        return resultMap;
    }


    //创建Excel
    @GetMapping("/export/{stationId}")
    public ResultMap export(@PathVariable Long stationId, HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ResultMap resultMap = new ResultMap();
        if(stationId == null){
            return resultMap.fail().message("文件格式错误").code(400);
        }
        PowerStation powerStation = powerStationMapper.selectByPrimaryKey(stationId);
        if(powerStation == null){
            return resultMap.fail().message("未找到该发电站数据").code(400);
        }
        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("备件列表");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
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

        int[] width = {256*18+184,
                256*18+184,
                256*18+184,
                256*18+184,
                256*25+184,
                256*40+184,
                256*18+184,
                256*30+184,
                256*25+184,
                256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);
        sheet.setColumnWidth(2,width[2]);
        sheet.setColumnWidth(3,width[3]);
        sheet.setColumnWidth(4,width[4]);
        sheet.setColumnWidth(5,width[5]);
        sheet.setColumnWidth(6,width[6]);
        sheet.setColumnWidth(7,width[7]);
        sheet.setColumnWidth(8,width[8]);
        sheet.setColumnWidth(9,width[9]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        if(powerStation != null && StringUtils.isNotBlank(powerStation.getStationName())){
            cell.setCellValue(powerStation.getStationName()+"工器具列表");
        }else{
            cell.setCellValue("工器具列表");
        }
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        HSSFCell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("工具编号");
        cell2_0.setCellStyle(boderStyle1);
        HSSFCell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("工器具名称");
        cell2_1.setCellStyle(boderStyle1);
        HSSFCell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("位置");
        cell2_2.setCellStyle(boderStyle1);
        HSSFCell cell2_3 = row2.createCell(3);
        cell2_3.setCellValue("数量");
        cell2_3.setCellStyle(boderStyle1);
        HSSFCell cell2_4 = row2.createCell(4);
        cell2_4.setCellValue("类型");
        cell2_4.setCellStyle(boderStyle1);
        HSSFCell cell2_5 = row2.createCell(5);
        cell2_5.setCellValue("本次点检日期");
        cell2_5.setCellStyle(boderStyle1);
        HSSFCell cell2_6 = row2.createCell(6);
        cell2_6.setCellValue("参数");
        cell2_6.setCellStyle(boderStyle1);
        HSSFCell cell2_7 = row2.createCell(7);
        cell2_7.setCellValue("点检状态");
        cell2_7.setCellStyle(boderStyle1);
        HSSFCell cell2_8 = row2.createCell(8);
        cell2_8.setCellValue("点检校验计划");
        cell2_8.setCellStyle(boderStyle1);
        HSSFCell cell2_9 = row2.createCell(9);
        cell2_9.setCellValue("下次点检日期");
        cell2_9.setCellStyle(boderStyle1);

        List<ToolMaintain> toolList = toolMaintainService.query(stationId);
        if(toolList != null && !toolList.isEmpty()){
            for(int i=0;i<toolList.size();i++){
                ToolMaintain bean = toolList.get(i);
                HSSFRow row = sheet.createRow(i+2);
                row.setRowStyle(boderStyle2);
                if(null != bean.getId()){
                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getId());
                }
                if(StringUtils.isNotBlank(bean.getTool())){
                    HSSFCell cell0 = row.createCell(1);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getTool());
                }
                if(StringUtils.isNotBlank(bean.getPosition())){
                    HSSFCell cell0 = row.createCell(2);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getPosition());
                }
                if(bean.getNum() != null){
                    HSSFCell cell0 = row.createCell(3);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getNum());
                }
                if(StringUtils.isNotBlank(bean.getType())){
                    HSSFCell cell0 = row.createCell(4);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getType());
                }
                if(bean.getCheckTime() != null){
                    HSSFCell cell0 = row.createCell(5);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(sdf.format(bean.getCheckTime()));
                }
                if(StringUtils.isNotBlank(bean.getParam())){
                    HSSFCell cell0 = row.createCell(6);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getParam());
                }
                if(bean.getCheckStatus() != null){
                    HSSFCell cell0 = row.createCell(7);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getCheckStatus());
                }
                if(StringUtils.isNotBlank(bean.getCheckPlan())){
                    HSSFCell cell0 = row.createCell(8);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getCheckPlan());
                }
                if(bean.getNextCheckTime() != null){
                    HSSFCell cell0 = row.createCell(9);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(sdf.format(bean.getNextCheckTime()));
                }
            }
        }

        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = "工器具列表.xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

}
