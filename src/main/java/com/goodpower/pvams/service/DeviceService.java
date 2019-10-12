package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.PowerStationDeviceMapper;
import com.goodpower.pvams.model.PowerStationDevice;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeviceService {

    @Autowired
    PowerStationDeviceMapper powerStationDeviceMapper;

    @Autowired
    ExcelService excelService;

    public void saveExcelData(Long stationId,Workbook workbook) throws ParseException {
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        int lastRowNum = sheet.getLastRowNum();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(sdf.format(new Date()));
        if(lastRowNum > 1){
            for(int i=2;i<=lastRowNum;i++){
                    Row row = sheet.getRow(i);
                    String deviceName = formatter.formatCellValue(row.getCell(0));
                    String number = formatter.formatCellValue(row.getCell(1));
                    String type = formatter.formatCellValue(row.getCell(2));
                    String model = formatter.formatCellValue(row.getCell(3));
                    String supplier = formatter.formatCellValue(row.getCell(4));
                    String contact = formatter.formatCellValue(row.getCell(5));
                    String phone = formatter.formatCellValue(row.getCell(6));
                    String warrantyStartDate = excelService.getRowValue(row,7);
                    String warrantyEndDate = excelService.getRowValue(row,8);
                    String param = formatter.formatCellValue(row.getCell(9));
                    String remark = formatter.formatCellValue(row.getCell(10));
                    PowerStationDevice device = new PowerStationDevice();
                    device.setDeviceName(deviceName);
                    device.setNumber(number);
                    device.setType(type);
                    device.setModel(model);
                    device.setSupplier(supplier);
                    device.setContact(contact);
                    device.setPhone(phone);
                    device.setParam(param);
                    device.setRemark(remark);
                    device.setStationId(stationId);
                    device.setWarrantyEndDate(warrantyEndDate);
                    device.setWarrantyStartDate(warrantyStartDate);
                    device.setCreateDttm(date);
                    device.setUpdateDttm(date);
                    powerStationDeviceMapper.insert(device);
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("stationId",stationId);
            param.put("date",date);
            powerStationDeviceMapper.deleteByFields(param);
        }
    }

    public List<PowerStationDevice> getStationDevice(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return powerStationDeviceMapper.selectByField(param);
    }

}
