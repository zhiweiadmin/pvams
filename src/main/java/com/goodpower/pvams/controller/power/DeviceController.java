package com.goodpower.pvams.controller.power;

import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.model.PowerStation;
import com.goodpower.pvams.model.PowerStationDevice;
import com.goodpower.pvams.model.PowerStationFile;
import com.goodpower.pvams.service.DeviceService;
import com.goodpower.pvams.service.StationService;
import com.goodpower.pvams.util.FileHandleUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    PowerStationMapper powerStationMapper;

    @PostMapping("/import/{stationId}")
    public ResultMap upload(@PathVariable Long stationId,@RequestParam("file") MultipartFile file){
        ResultMap resultMap = new ResultMap();
        try{
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                Workbook workbook;
                if (fileName.endsWith("xlsx")){
                    workbook = new XSSFWorkbook(file.getInputStream());
                    deviceService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    deviceService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else{
                    resultMap.fail().message("文件格式错误").code(400);
                }
            }
        }catch (Exception e){
            resultMap.success().message("导入失败");
        }
        return resultMap;
    }

    //创建Excel
    @GetMapping("/export/{stationId}")
    public ResultMap exportDevice(@PathVariable Long stationId,HttpServletResponse response) throws IOException {
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
        HSSFSheet sheet = wb.createSheet("设备列表");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)20);
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

        int[] width = {256*10+184,256*30+184,256*10+184,256*15+184,256*10+184,256*30+184,256*20+184,256*20+184,256*30+184,256*30+184,256*30+184,256*30+184,256*30+184};
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
        sheet.setColumnWidth(10,width[10]);
        sheet.setColumnWidth(11,width[11]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        if(powerStation != null && StringUtils.isNotBlank(powerStation.getStationName())){
            cell.setCellValue(powerStation.getStationName()+"设备列表");
        }else{
            cell.setCellValue("设备列表");
        }
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        HSSFCell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("设备编号");
        cell2_0.setCellStyle(boderStyle1);
        HSSFCell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("设备名称");
        cell2_1.setCellStyle(boderStyle1);
        HSSFCell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("数量");
        cell2_2.setCellStyle(boderStyle1);
        HSSFCell cell2_3 = row2.createCell(3);
        cell2_3.setCellValue("类型");
        cell2_3.setCellStyle(boderStyle1);
        HSSFCell cell2_4 = row2.createCell(4);
        cell2_4.setCellValue("型号");
        cell2_4.setCellStyle(boderStyle1);
        HSSFCell cell2_5 = row2.createCell(5);
        cell2_5.setCellValue("供应商");
        cell2_5.setCellStyle(boderStyle1);
        HSSFCell cell2_6 = row2.createCell(6);
        cell2_6.setCellValue("联系人");
        cell2_6.setCellStyle(boderStyle1);
        HSSFCell cell2_7 = row2.createCell(7);
        cell2_7.setCellValue("联系方式");
        cell2_7.setCellStyle(boderStyle1);
        HSSFCell cell2_8 = row2.createCell(8);
        cell2_8.setCellValue("保修起始日期");
        cell2_8.setCellStyle(boderStyle1);
        HSSFCell cell2_9 = row2.createCell(9);
        cell2_9.setCellValue("保修截止日期");
        cell2_9.setCellStyle(boderStyle1);
        HSSFCell cell2_10 = row2.createCell(10);
        cell2_10.setCellValue("主要参数");
        cell2_10.setCellStyle(boderStyle1);
        HSSFCell cell2_11 = row2.createCell(11);
        cell2_11.setCellValue("备注");
        cell2_11.setCellStyle(boderStyle1);

        List<PowerStationDevice> deviceList = deviceService.getStationDevice(stationId);
        if(deviceList != null && !deviceList.isEmpty()){
            for(int i=0;i<deviceList.size();i++){
                PowerStationDevice device = deviceList.get(i);
                HSSFRow row = sheet.createRow(i+2);
                row.setRowStyle(boderStyle2);
                if(null != device.getDeviceId()){
                    HSSFCell cell0 = row.createCell(0);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getDeviceId());
                }
                if(StringUtils.isNotBlank(device.getDeviceName())){
                    HSSFCell cell0 = row.createCell(1);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getDeviceName());
                }
                if(null != device.getNumber()){
                    HSSFCell cell0 = row.createCell(2);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getNumber());
                }
                if(StringUtils.isNotBlank(device.getType())){
                    HSSFCell cell0 = row.createCell(3);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getType());
                }
                if(StringUtils.isNotBlank(device.getModel())){
                    HSSFCell cell0 = row.createCell(4);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getModel());
                }
                if(StringUtils.isNotBlank(device.getSupplier())){
                    HSSFCell cell0 = row.createCell(5);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getSupplier());
                }
                if(StringUtils.isNotBlank(device.getContact())){
                    HSSFCell cell0 = row.createCell(6);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getContact());
                }
                if(StringUtils.isNotBlank(device.getPhone())){
                    HSSFCell cell0 = row.createCell(7);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getPhone());
                }
                if(null != device.getWarrantyStartDate()){
                    HSSFCell cell0 = row.createCell(8);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getWarrantyStartDate());
                }
                if(null != device.getWarrantyEndDate()){
                    HSSFCell cell0 = row.createCell(9);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getWarrantyEndDate());
                }
                if(StringUtils.isNotBlank(device.getRemark())){
                    HSSFCell cell0 = row.createCell(10);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getParam());
                }
                if(StringUtils.isNotBlank(device.getRemark())){
                    HSSFCell cell0 = row.createCell(11);
                    cell0.setCellStyle(boderStyle2);
                    cell0.setCellValue(device.getRemark());
                }
            }
        }

        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = "设备列表.xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

    @GetMapping("/template")
    public ResultMap template(HttpServletResponse response) throws IOException {
        ResultMap resultMap = new ResultMap();

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook wb = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("设备列表");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)20);
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

        int[] width = {256*10+184,256*20+184,256*10+184,256*15+184,256*10+184,256*20+184,256*20+184,256*20+184,256*30+184,256*30+184,256*30+184,256*30+184};
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
        sheet.setColumnWidth(10,width[10]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("发电站设备列表");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        HSSFCell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("设备编号");
        cell2_0.setCellStyle(boderStyle1);
        HSSFCell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("设备名称");
        cell2_1.setCellStyle(boderStyle1);
        HSSFCell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("数量");
        cell2_2.setCellStyle(boderStyle1);
        HSSFCell cell2_3 = row2.createCell(3);
        cell2_3.setCellValue("类型");
        cell2_3.setCellStyle(boderStyle1);
        HSSFCell cell2_4 = row2.createCell(4);
        cell2_4.setCellValue("型号");
        cell2_4.setCellStyle(boderStyle1);
        HSSFCell cell2_5 = row2.createCell(5);
        cell2_5.setCellValue("供应商");
        cell2_5.setCellStyle(boderStyle1);
        HSSFCell cell2_6 = row2.createCell(6);
        cell2_6.setCellValue("联系人");
        cell2_6.setCellStyle(boderStyle1);
        HSSFCell cell2_7 = row2.createCell(7);
        cell2_7.setCellValue("联系方式");
        cell2_7.setCellStyle(boderStyle1);
        HSSFCell cell2_8 = row2.createCell(8);
        cell2_8.setCellValue("保修起始日期");
        cell2_8.setCellStyle(boderStyle1);
        HSSFCell cell2_9 = row2.createCell(9);
        cell2_9.setCellValue("保修截止日期");
        cell2_9.setCellStyle(boderStyle1);
        HSSFCell cell2_10 = row2.createCell(10);
        cell2_10.setCellValue("备注");
        cell2_10.setCellStyle(boderStyle1);

        //输出Excel文件
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = "设备模板"+".xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

}
