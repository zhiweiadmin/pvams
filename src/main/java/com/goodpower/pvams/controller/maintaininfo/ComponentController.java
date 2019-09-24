package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.Page;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.model.Component;
import com.goodpower.pvams.model.ComponentRecord;
import com.goodpower.pvams.model.PowerStation;
import com.goodpower.pvams.model.PowerStationDevice;
import com.goodpower.pvams.service.ComponentService;
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
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("component")
public class ComponentController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ComponentService componentService;

    @Autowired
    PowerStationMapper powerStationMapper;

    private static final Integer UNCONFIRM = 0;

    private static final Integer CONFIRM = 1;

    @PostMapping("/action")
    public ResultMap action(@RequestBody JSONObject request){
        ResultMap resultMap = new ResultMap();
        try{
            Long userId = request.getLong("userId");
            Long stationId = request.getLong("stationId");
            Long componentId = request.getLong("id");
            Integer action  = request.getInteger("action");//0 领取  1:入库
            Integer num = request.getInteger("num");
            if(userId == null){
                return resultMap.fail().code(400).message("userId不能为空");
            }
            if(stationId == null){
                return resultMap.fail().code(400).message("请先选择电站!");
            }
            if(componentId == null){
                return resultMap.fail().code(400).message("备件id不能为空");
            }
            //获取备件总数量
            if(action == 0){
                int totalNum = componentService.getComponentNum(componentId);
                if(num > totalNum){
                    return resultMap.fail().code(400).message("领取的数量超过备件总数量!");
                }
            }

            Date date = new Date();
            ComponentRecord componentRecord = new ComponentRecord();
            componentRecord.setUserId(userId);
            componentRecord.setStationId(stationId);
            componentRecord.setComponentId(componentId);
            componentRecord.setAction(action);
            componentRecord.setNum(num);
            componentRecord.setSubmitDate(date);
            componentRecord.setCreateDttm(date);
            componentRecord.setUpdateDttm(date);
            componentRecord.setStatus(UNCONFIRM);//待确认
            componentService.addRecord(componentRecord);
            resultMap.success().message("请求成功");
        }catch (Exception e){
            logger.error("请求失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/query")
    public ResultMap query(Long stationId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(stationId == null){
                return resultMap.fail().code(400).message("请先选择电站!");
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
            List<Component> resultList =  componentService.selectByFields(param);
            int count = componentService.getCount(param);
            Page page = new Page(pageNo,pageSize,Long.parseLong(count+""));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("resultList",resultList);
            jsonObject.put("page",page);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/queryRecord")
    public ResultMap queryRecord(Long componentId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null) {
                pageSize = 10;
            }
            if(componentId == null){
                return resultMap.fail().code(400).message("componentId不能为空");
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("componentId",componentId);
            JSONObject jsonObject= componentService.queryComponentRecord(param);
            resultMap.success().message("请求成功").setData(jsonObject);
        }catch (Exception e){
            logger.error("请求失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/confirm")
    public ResultMap confirm(Long userId,Long id,Integer status){
        ResultMap resultMap = new ResultMap();
        try{
            String msg = componentService.confirm(userId,id,status);
            resultMap.success().message(msg);
        }catch (Exception e){
            logger.error("确认失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/import/{stationId}")
    public ResultMap upload(@PathVariable Long stationId,@RequestParam("file") MultipartFile file){
        ResultMap resultMap = new ResultMap();
        try{
            if(stationId == null){
                return resultMap.fail().message("请先选择电站").code(400);
            }
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                Workbook workbook;
                if (fileName.endsWith("xlsx")){
                    workbook = new XSSFWorkbook(file.getInputStream());
                    componentService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    componentService.saveExcelData(stationId,workbook);
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


    //创建Excel
    @GetMapping("/export/{stationId}")
    public ResultMap exportDevice(@PathVariable Long stationId,HttpServletResponse response) throws IOException {
        ResultMap resultMap = new ResultMap();
        if(stationId == null){
            return resultMap.fail().message("请先选择电站").code(400);
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
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
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

        int[] width = {256*30+184,256*30+184,256*30+184,256*30+184,256*30+184,256*30+184,256*50+184};
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
        //设置单元格内容
        cell.setCellValue("备件列表");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        HSSFCell cell2_1 = row2.createCell(0);
        cell2_1.setCellValue("备件名称");
        cell2_1.setCellStyle(boderStyle1);
        HSSFCell cell2_2 = row2.createCell(1);
        cell2_2.setCellValue("备件数量");
        cell2_2.setCellStyle(boderStyle1);
        HSSFCell cell2_3 = row2.createCell(2);
        cell2_3.setCellValue("品牌");
        cell2_3.setCellStyle(boderStyle1);
        HSSFCell cell2_4 = row2.createCell(3);
        cell2_4.setCellValue("库存上限");
        cell2_4.setCellStyle(boderStyle1);
        HSSFCell cell2_5 = row2.createCell(4);
        cell2_5.setCellValue("库存下限");
        cell2_5.setCellStyle(boderStyle1);
        HSSFCell cell2_6 = row2.createCell(5);
        cell2_6.setCellValue("参数");
        cell2_6.setCellStyle(boderStyle1);

        List<Component> componentList = componentService.getStationComponent(stationId);
        if(componentList != null && !componentList.isEmpty()){
            for(int i=0;i<componentList.size();i++){
                Component bean = componentList.get(i);
                HSSFRow row = sheet.createRow(i+2);
                row.setRowStyle(boderStyle2);
                if(StringUtils.isNotBlank(bean.getComponentName())){
                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getComponentName());
                }
                if(bean.getNum() != null){
                    HSSFCell cell0 = row.createCell(1);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getNum());
                }
                if(StringUtils.isNotBlank(bean.getBrand())){
                    HSSFCell cell0 = row.createCell(2);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getBrand());
                }
                if(bean.getStockUp() != null){
                    HSSFCell cell0 = row.createCell(3);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getStockUp());
                }
                if(bean.getStockLower() != null){
                    HSSFCell cell0 = row.createCell(4);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getStockLower());
                }
                if(StringUtils.isNotBlank(bean.getParam())){
                    HSSFCell cell0 = row.createCell(5);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(bean.getParam());
                }
            }
        }

        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = "备件列表.xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

}
