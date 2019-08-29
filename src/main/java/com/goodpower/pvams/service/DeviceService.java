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
import java.util.List;
import java.util.Map;

@Service
public class DeviceService {

    @Autowired
    PowerStationDeviceMapper powerStationDeviceMapper;

    public void saveExcelData(Long stationId,Workbook workbook){
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();
        int lastRowNum = sheet.getLastRowNum();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        if(lastRowNum > 1){
            for(int i=2;i<=lastRowNum;i++){
                try{
                    Row row = sheet.getRow(i);
                    String deviceId = formatter.formatCellValue(row.getCell(0));
                    String deviceName = formatter.formatCellValue(row.getCell(1));
                    String number = formatter.formatCellValue(row.getCell(2));
                    String type = formatter.formatCellValue(row.getCell(3));
                    String model = formatter.formatCellValue(row.getCell(4));
                    String supplier = formatter.formatCellValue(row.getCell(5));
                    String contact = formatter.formatCellValue(row.getCell(6));
                    String phone = formatter.formatCellValue(row.getCell(7));
                    String warrantyStartDate = formatter.formatCellValue(row.getCell(8));
                    String warrantyEndDate = formatter.formatCellValue(row.getCell(9));
                    String param = formatter.formatCellValue(row.getCell(10));
                    String remark = formatter.formatCellValue(row.getCell(11));
                    PowerStationDevice device = new PowerStationDevice();
                    device.setDeviceName(deviceName);
                    device.setNumber(Integer.parseInt(number));
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
                    if(StringUtils.isNotBlank(deviceId)){
                        //更新
                        device.setDeviceId(Long.parseLong(deviceId));
                        powerStationDeviceMapper.updateByPrimaryKey(device);
                    }else{
                        powerStationDeviceMapper.insert(device);
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
    }

    public List<PowerStationDevice> getStationDevice(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return powerStationDeviceMapper.selectByField(param);
    }

}
