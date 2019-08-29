package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.FireCheckRecordMapper;
import com.goodpower.pvams.mapper.FireMaintainMapper;
import com.goodpower.pvams.model.*;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FireMaintainService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FireMaintainMapper fireMaintainMapper;

    @Autowired
    FireCheckRecordMapper checkRecordMapper;

    @Autowired
    ExcelService excelService;

    public List<FireMaintain> query(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return fireMaintainMapper.selectByFields(param);
    }

    public JSONObject selectByFields(Map<String,Object> param){
        List<FireMaintain> resultList = fireMaintainMapper.selectByFields(param);
        int count = fireMaintainMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public void addCheckRecord(FireCheckRecord record){
        record.setConfirmStatus(0);
        checkRecordMapper.insert(record);
    }

    public JSONObject queryFireRecord(Map<String,Object> param){
        List<Map<String,Object>> resultList = checkRecordMapper.selectByFields(param);
        int count = checkRecordMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public void createFireMaintainTmp(Long stationId,HSSFWorkbook wb){
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = wb.createSheet("消防器材维护");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
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

        int[] width = {256*10+184,
                256*20+184,
                256*30+184,
                256*20+184,
                256*20+184,
                256*30+184,
                256*30+184,
                256*30+184,
                256*40+184,
                256*30+184};
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
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("消防器材维护");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        //创建单元格并设置单元格内容
        createRowCell(row2,0,"器材编号",boderStyle2);
        createRowCell(row2,1,"器材名称",boderStyle2);
        createRowCell(row2,2,"位置",boderStyle2);
        createRowCell(row2,3,"数量",boderStyle2);
        createRowCell(row2,4,"类型",boderStyle2);
        createRowCell(row2,5,"本次点检日期",boderStyle2);
        createRowCell(row2,6,"参数",boderStyle2);
        createRowCell(row2,7,"点检校验计划",boderStyle2);
        createRowCell(row2,8,"下次点检日期",boderStyle2);


        List<FireMaintain> dataList = query(stationId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(CollectionUtils.isNotEmpty(dataList)){
            for(int i=0;i<dataList.size();i++){
                FireMaintain fireTool = dataList.get(i);
                HSSFRow row = sheet.createRow(i+2);
                row.setRowStyle(boderStyle2);
                if(null != fireTool.getId()){
                    createRowCell(row,0,fireTool.getId()+"",boderStyle2);
                }
                if(StringUtils.isNotBlank(fireTool.getFireName())){
                    createRowCell(row,1,fireTool.getFireName()+"",boderStyle2);
                }
                if(StringUtils.isNotBlank(fireTool.getPosition())){
                    createRowCell(row,2,fireTool.getPosition(),boderStyle2);
                }
                if(null != fireTool.getNum()){
                    createRowCell(row,3,fireTool.getNum()+"",boderStyle2);
                }
                if(StringUtils.isNotBlank(fireTool.getType())){
                    createRowCell(row,4,fireTool.getType(),boderStyle2);
                }
                if(fireTool.getCheckTime() != null){
                    createRowCell(row,5,sdf.format(fireTool.getCheckTime()),boderStyle2);
                }
                if(StringUtils.isNotBlank(fireTool.getParam())){
                    createRowCell(row,6,fireTool.getParam(),boderStyle2);
                }
                if(StringUtils.isNotBlank(fireTool.getCheckPlan())){
                    createRowCell(row,7,fireTool.getCheckPlan() + "",boderStyle2);
                }
                if(fireTool.getNextCheckTime() != null){
                    createRowCell(row,8,sdf.format(fireTool.getNextCheckTime()),boderStyle2);
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


    public void saveExcelData(Long stationId,Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        int lastRowNum = sheet.getLastRowNum();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(lastRowNum > 1){
            for(int i=2;i<=lastRowNum;i++){
                try{
                    Row row = sheet.getRow(i);
                    String fireId = formatter.formatCellValue(row.getCell(0));
                    String fireName = formatter.formatCellValue(row.getCell(1));
                    String position = formatter.formatCellValue(row.getCell(2));
                    String num = formatter.formatCellValue(row.getCell(3));
                    String type = formatter.formatCellValue(row.getCell(4));
                    String checkTime = excelService.getRowValue(row,5);
                    String param = formatter.formatCellValue(row.getCell(6));
                    String checkPlan = formatter.formatCellValue(row.getCell(7));
                    String nextCheckTime = excelService.getRowValue(row,8);

                    FireMaintain fireMaintain = new FireMaintain();
                    fireMaintain.setStationId(stationId);
                    fireMaintain.setFireName(fireName);
                    fireMaintain.setPosition(position);
                    if(StringUtils.isNotBlank(num)){
                        fireMaintain.setNum(Integer.parseInt(num));
                    }
                    fireMaintain.setType(type);
                    fireMaintain.setParam(param);
                    fireMaintain.setCheckPlan(checkPlan);
                    if(StringUtils.isNotBlank(checkTime)){
                        fireMaintain.setCheckTime(sdf.parse(checkTime));
                    }
                    if(StringUtils.isNotBlank(nextCheckTime)){
                        fireMaintain.setNextCheckTime(sdf.parse(nextCheckTime));
                    }

                    if(StringUtils.isNotBlank(fireId)){
                        //更新
                        fireMaintain.setId(Integer.parseInt(fireId));
                        fireMaintainMapper.updateByPrimaryKey(fireMaintain);
                    }else{
                        fireMaintainMapper.insert(fireMaintain);
                    }
                }catch (Exception e){
                    logger.error("消防器材导入失败",e);
                }
            }
        }
    }

    public String getCellDate(Cell currentCell) {
        String currentCellValue = "";
        // 判断单元格数据是否是日期
        if ("yyyy/mm;@".equals(currentCell.getCellStyle().getDataFormatString())
                || "m/d/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yy/m/d".equals(currentCell.getCellStyle().getDataFormatString())
                || "mm/dd/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "dd-mmm-yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yyyy/m/d".equals(currentCell.getCellStyle().getDataFormatString())) {
            if (DateUtil.isCellDateFormatted(currentCell)) {
                // 用于转化为日期格式
                Date d = currentCell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                currentCellValue = formater.format(d);
            }
        } else {
            // 不是日期原值返回
            currentCellValue = currentCell.toString();
        }
        return currentCellValue;
    }

    public void confirm(Long userId,Integer id,Integer confirmStatus){
        FireCheckRecord record = new FireCheckRecord();
        record.setId(id);
        record.setConfirmUserId(userId);
        record.setConfirmStatus(confirmStatus);
        record.setUpdateDttm(new Date());
        record.setConfirmTime(new Date());
        checkRecordMapper.updateByPrimaryKeySelective(record);
    }

}
