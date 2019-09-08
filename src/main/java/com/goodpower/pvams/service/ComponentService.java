package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.ComponentMapper;
import com.goodpower.pvams.mapper.ComponentRecordMapper;
import com.goodpower.pvams.model.Component;
import com.goodpower.pvams.model.ComponentRecord;
import com.goodpower.pvams.model.PowerGenerateStat;
import com.goodpower.pvams.model.PowerStationDevice;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ComponentService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ComponentMapper componentMapper;

    @Autowired
    ComponentRecordMapper recordMapper;

    @Autowired
    ExcelService excelService;

    private static final Integer CONFIRM = 1;

    private static final Integer REJECT = 2;

    private static final Integer BORROW = 0;

    private static final Integer BACK = 1;

    public List<Component> selectByFields(Map<String,Object> param){
        return componentMapper.selectByFields(param);
    }

    public int getCount(Map<String,Object> param){
        return componentMapper.getCount(param);
    }

    public int getComponentNum(Long id){
        Component component = componentMapper.selectByPrimaryKey(id);
        if(component == null){
            return 0;
        }else{
            return component.getNum() == null?0:component.getNum();
        }
    }

    public void addRecord(ComponentRecord record){
        recordMapper.insert(record);
    }

    public JSONObject queryComponentRecord(Map<String,Object> param){
        List<Map<String,Object>> resultList = recordMapper.selectByFields(param);
        int count = recordMapper.getCount(param);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("resultList",resultList);
        jsonObject.put("count",count);
        return jsonObject;
    }

    public String confirm(Long userId,Long recordId,Integer status){
        if(status.equals(CONFIRM)){
            //如果审批通过 库存数量减一或者加1
            //获取组件库存 然后减一或者加1
            ComponentRecord componentRecord = recordMapper.selectByPrimaryKey(recordId);
            Integer action = componentRecord.getAction();
            Long componentId = componentRecord.getComponentId();
            Map<String,Object> param = Maps.newHashMap();
            Component component = componentMapper.selectByPrimaryKey(componentId);
            param.put("id",component.getId());
            param.put("ver",component.getVersion()+1);
            if(BORROW.equals(action)){
                if(component.getNum() > 0){
                    param.put("number",-componentRecord.getNum());
                    int i = componentMapper.updateByVersion(param);
                    if(i > 0){
                        confirmRecord(userId,recordId,status);
                        return "成功";
                    }else{
                        return "请当前有人正在操作，请稍后重试";
                    }
                }else{
                    return "库存不足";
                }
            }else if(BACK.equals(action)){
                param.put("number",componentRecord.getNum());
                int i = componentMapper.updateByVersion(param);
                if(i > 0){
                    confirmRecord(userId,recordId,status);
                    return "成功";
                }else{
                    return "请当前有人正在操作，请稍后重试";
                }
            }
        }else if (status == REJECT.intValue()){
            ComponentRecord record = new ComponentRecord();
            record.setStatus(REJECT);
            record.setId(recordId);
            record.setApproveUserId(userId);
            record.setApproveDate(new Date());
            recordMapper.updateByPrimaryKeySelective(record);
        }
        return "成功";
    }

    private void confirmRecord(Long userId,Long recordId,Integer status){
        ComponentRecord record = new ComponentRecord();
        record.setId(recordId);
        record.setApproveDate(new Date());
        record.setStatus(status);
        record.setApproveUserId(userId);
        recordMapper.updateByPrimaryKeySelective(record);
    }

    public void saveExcelData(Long stationId, Workbook workbook) throws ParseException {
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(sdf.format(new Date()));
        if(lastRowNum > 1){
                for(int i=2;i<=lastRowNum;i++) {
                    Row row = sheet.getRow(i);
                    String name = excelService.getCellValue(row.getCell(0));
                    String num = excelService.getCellValue(row.getCell(1));
                    String brand = excelService.getCellValue(row.getCell(2));
                    String up = excelService.getCellValue(row.getCell(3));
                    String low = excelService.getCellValue(row.getCell(4));
                    String param = excelService.getCellValue(row.getCell(5));

                    Component component = new Component();
                    component.setStationId(stationId);
                    component.setComponentName(name);
                    if (StringUtils.isNotBlank(num)) {
                        component.setNum(Integer.parseInt(num));
                    }
                    component.setBrand(brand);
                    if (StringUtils.isNotBlank(up)) {
                        component.setStockUp(Integer.parseInt(up));
                    }
                    if (StringUtils.isNotBlank(low)) {
                        component.setStockLower(Integer.parseInt(low));
                    }
                    if (StringUtils.isNotBlank(param)) {
                        component.setParam(param);
                    }
                    component.setCreateDttm(date);
                    component.setUpdateDttm(date);
                    component.setVersion(1L);
                    componentMapper.insert(component);
                }
                Map<String,Object> param = Maps.newHashMap();
                param.put("stationId",stationId);
                param.put("date",date);
                componentMapper.deleteByFields(param);
        }
    }

    public List<Component> getStationComponent(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return componentMapper.selectByField(param);
    }

}
