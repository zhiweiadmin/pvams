package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.CarMaintainMapper;
import com.goodpower.pvams.mapper.CarTravelRecordMapper;
import com.goodpower.pvams.model.CarMaintain;
import com.goodpower.pvams.model.CarTravelRecord;
import com.goodpower.pvams.model.FireMaintain;
import com.goodpower.pvams.model.ToolCheckRecord;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CarMaintainService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CarMaintainMapper carMaintainMapper;

    @Autowired
    CarTravelRecordMapper carTravelRecordMapper;

    public List<CarMaintain> query(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return carMaintainMapper.selectByFields(param);
    }

    public JSONObject selectByFields(Map<String,Object> param){
        List<CarMaintain> resultList = carMaintainMapper.selectByFields(param);
        int count = carMaintainMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public void addCarTravelRecord(CarTravelRecord travelRecord){
        travelRecord.setConfirmStatus(0);
        carTravelRecordMapper.insert(travelRecord);
    }

    public JSONObject queryCarTravelRecord(Map<String,Object> param){
        List<Map<String,Object>> resultList = carTravelRecordMapper.selectByFields(param);
        int count = carTravelRecordMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public void createExcelTemplate(Long stationId, HSSFWorkbook wb){
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("车辆信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
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

        int[] width = {256*20+184, 256*20+184, 256*20+184, 256*20+184,256*40+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);
        sheet.setColumnWidth(2,width[2]);
        sheet.setColumnWidth(3,width[3]);
        sheet.setColumnWidth(4,width[4]);

        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("车辆信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        createRowCell(row2,0,"汽车编号",boderStyle2);
        createRowCell(row2,1,"车牌号",boderStyle2);
        createRowCell(row2,2,"负责人",boderStyle2);
        createRowCell(row2,3,"品牌",boderStyle2);
        createRowCell(row2,4,"参数",boderStyle2);

        List<CarMaintain> dataList = query(stationId);
        if(CollectionUtils.isNotEmpty(dataList)){
            for(int i=0;i<dataList.size();i++){
                CarMaintain carBean = dataList.get(i);
                HSSFRow row = sheet.createRow(i+2);
                row.setRowStyle(boderStyle2);
                if(null != carBean.getId()){
                    createRowCell(row,0,carBean.getId()+"",boderStyle2);
                }
                if(StringUtils.isNotBlank(carBean.getCarNum())){
                    createRowCell(row,1,carBean.getCarNum()+"",boderStyle2);
                }
                if(StringUtils.isNotBlank(carBean.getHeader())){
                    createRowCell(row,2,carBean.getHeader()+"",boderStyle2);
                }
                if(StringUtils.isNotBlank(carBean.getBrand())){
                    createRowCell(row,3,carBean.getBrand(),boderStyle2);
                }
                if(StringUtils.isNotBlank(carBean.getParam())){
                    createRowCell(row,4,carBean.getParam(),boderStyle2);
                }

            }
        }
    }

    public void createRowCell(HSSFRow row,Integer colNum,String val,HSSFCellStyle cellStyle){
        HSSFCell cell = row.createCell(colNum);
        if(cellStyle != null){
            cell.setCellStyle(cellStyle);
        }
        cell.setCellValue(val);
    }

    public void saveExcelData(Long stationId, Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 1){
            for(int i=2;i<=lastRowNum;i++){
                try{
                    Row row = sheet.getRow(i);
                    String carId = formatter.formatCellValue(row.getCell(0));
                    String carNum = formatter.formatCellValue(row.getCell(1));
                    String header = formatter.formatCellValue(row.getCell(2));
                    String brand = formatter.formatCellValue(row.getCell(3));
                    String param = formatter.formatCellValue(row.getCell(4));

                    CarMaintain carMaintain = new CarMaintain();
                    carMaintain.setCarNum(carNum);
                    carMaintain.setStationId(stationId);
                    carMaintain.setHeader(header);
                    carMaintain.setParam(param);
                    carMaintain.setBrand(brand);

                    if(StringUtils.isNotBlank(carId)){
                        //更新
                        carMaintain.setId(Long.parseLong(carId));
                        carMaintainMapper.updateByPrimaryKey(carMaintain);
                    }else{
                        carMaintainMapper.insert(carMaintain);
                    }
                }catch (Exception e){
                    logger.error("车辆信息导入失败:",e);
                }
            }
        }
    }

    public void confirm(Long userId,Long id,Integer confirmStatus){
        CarTravelRecord record = new CarTravelRecord();
        record.setId(id);
        record.setConfirmUserId(userId);
        record.setConfirmStatus(confirmStatus);
        record.setUpdateDttm(new Date());
        record.setConfirmTime(new Date());
        carTravelRecordMapper.updateByPrimaryKeySelective(record);
    }

}
