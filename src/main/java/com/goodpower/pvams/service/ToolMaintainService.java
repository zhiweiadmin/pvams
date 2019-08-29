package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.ToolCheckRecordMapper;
import com.goodpower.pvams.mapper.ToolMaintainMapper;
import com.goodpower.pvams.model.ToolCheckRecord;
import com.goodpower.pvams.model.ToolMaintain;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ToolMaintainService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ToolMaintainMapper toolMaintainMapper;

    @Autowired
    ToolCheckRecordMapper toolCheckRecordMapper;

    @Autowired
    ExcelService excelService;

    private static final Integer UNCONFIRM = 0;

    private static final Integer CONFIRM = 1;


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<ToolMaintain> query(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return toolMaintainMapper.selectByFields(param);
    }

    public JSONObject selectByFields(Map<String,Object> param){
        List<ToolMaintain> resultList = toolMaintainMapper.selectByFields(param);
        int count = toolMaintainMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public void addToolCheckRecord(ToolCheckRecord record){
        record.setConfirmStatus(UNCONFIRM);
        toolCheckRecordMapper.insert(record);
    }


    public JSONObject queryToolCheckRecord(Map<String,Object> param){
        List<Map<String,Object>> resultList = toolCheckRecordMapper.selectByFields(param);
        int count = toolCheckRecordMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public void saveExcelData(Long stationId, Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 1){
            for(int i=2;i<=lastRowNum;i++){
                try{
                    Row row = sheet.getRow(i);
                    String id = excelService.getCellValue(row.getCell(0));
                    String toolName = excelService.getCellValue(row.getCell(1));
                    String position = excelService.getCellValue(row.getCell(2));
                    String num = excelService.getCellValue(row.getCell(3));
                    String type = excelService.getCellValue(row.getCell(4));
                    String curCheckTime = excelService.getRowValue(row,5);
                    String param = excelService.getCellValue(row.getCell(6));
                    String checkStatus = excelService.getCellValue(row.getCell(7));
                    String checkPlan = excelService.getCellValue(row.getCell(8));
                    String nextCheckTime = excelService.getRowValue(row,9);

                    ToolMaintain tool = new ToolMaintain();
                    tool.setStationId(stationId);
                    tool.setTool(toolName);
                    tool.setPosition(position);
                    if(StringUtils.isNotBlank(num)){
                        tool.setNum(Integer.parseInt(num));
                    }
                    if(StringUtils.isNotBlank(checkStatus)){
                        tool.setCheckStatus(Integer.parseInt(checkStatus));
                    }
                    tool.setType(type);
                    tool.setParam(param);
                    tool.setCheckPlan(checkPlan);
                    if(StringUtils.isNotBlank(curCheckTime)){
                        tool.setCheckTime(sdf.parse(curCheckTime));
                    }
                    if(StringUtils.isNotBlank(nextCheckTime)){
                        tool.setNextCheckTime(sdf.parse(nextCheckTime));
                    }

                    if(StringUtils.isNotBlank(id)){
                        //更新
                        tool.setId(Long.parseLong(id));
                        toolMaintainMapper.updateByPrimaryKey(tool);
                    }else{
                        toolMaintainMapper.insert(tool);
                    }
                }catch (Exception e){
                    logger.error("导入错误",e);
                }
            }
        }
    }

    public void confirm(Long userId,Integer id,Integer confirmStatus){
        ToolCheckRecord record = new ToolCheckRecord();
        record.setId(id);
        record.setConfirmUserId(userId);
        record.setConfirmStatus(confirmStatus);
        record.setUpdateDttm(new Date());
        record.setConfirmTime(new Date());
        toolCheckRecordMapper.updateByPrimaryKeySelective(record);
    }

}
