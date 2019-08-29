package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.*;
import com.goodpower.pvams.model.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StationService {

    private static final Integer STATION_FILE = 0;

    private static final Integer STATION_GIRD_ACCESS_FILE = 1;

    @Autowired
    PowerStationFileMapper powerStationFileMapper;

    @Autowired
    PowerStationDeviceMapper powerStationDeviceMapper;

    @Autowired
    PowerStationMapper powerStationMapper;

    @Autowired
    PowerStationConstructInfoMapper constructInfoMapper;

    @Autowired
    PowerStationBuildInfoMapper buildInfoMapper;

    @Autowired
    PowerStationSuperviseMapper superviseMapper;

    @Autowired
    PowerStationGirdInfoMapper girdInfoMapper;

    @Autowired
    GirdAccessFileMapper accessFileMapper;

    @Autowired
    PowerStationFileMapper fileMapper;

    @Autowired
    PowerStationBaseMapper stationBaseMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    ReadExcelStationInfo readExcelStationInfo;

    public PowerStation getStation(Long stationId){
        return powerStationMapper.selectByPrimaryKey(stationId);
    }

    public void uploadStationFile(PowerStationFile stationFile){
        powerStationFileMapper.insert(stationFile);
    }

    public String deleteStationFile(Long fileId){
        PowerStationFile file = powerStationFileMapper.selectByPrimaryKey(fileId);
        powerStationFileMapper.deleteByPrimaryKey(fileId);
        return file.getPath();
    }

    /**
     * 上传并网接入点图片
     * @param girdAccessFile
     */
    public void uploadAccessImg(GirdAccessFile girdAccessFile){
        accessFileMapper.insert(girdAccessFile);
    }

    public List<PowerStationDevice> getStationDevice(Long stationId, Integer pageNo, Integer pageSize){
        Integer index = (pageNo - 1)*pageSize;
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("index",index);
        param.put("limit",pageSize);
        return powerStationDeviceMapper.selectByField(param);
    }

    public Long getStationDeviceCount(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return powerStationDeviceMapper.getStationDeviceCount(param);
    }

    public void addStationDevice(PowerStationDevice stationDevice){
        powerStationDeviceMapper.insert(stationDevice);
    }

    public JSONObject getStationInfo(Long stationId){
        PowerStation powerStation = powerStationMapper.selectByPrimaryKey(stationId);
        PowerStationBuildInfo buildInfo = buildInfoMapper.selectByPrimaryKey(stationId);
        PowerStationSupervise superviseInfo = superviseMapper.selectByPrimaryKey(stationId);
        PowerStationConstructInfo constructInfo = constructInfoMapper.selectByPrimaryKey(stationId);
        PowerStationGirdInfo girdInfo = girdInfoMapper.selectByPrimaryKey(stationId);
        PowerStationBase baseInfo = stationBaseMapper.selectByPrimaryKey(stationId);

        if(powerStation == null){
            powerStation = new PowerStation();
        }

        if(buildInfo == null){
            buildInfo = new PowerStationBuildInfo();
        }

        if(superviseInfo == null){
            superviseInfo = new PowerStationSupervise();
        }

        if(constructInfo == null){
            constructInfo = new PowerStationConstructInfo();
        }

        if(girdInfo == null){
            girdInfo = new PowerStationGirdInfo();
            List<GirdAccessFile> accessPointFiles = Lists.newArrayList();
            girdInfo.setAccessPointFiles(accessPointFiles);
        }

        if(baseInfo == null){
            baseInfo = new PowerStationBase();
        }


        List<PowerStationFile> fileList = fileMapper.selectByStationId(stationId);
        if(girdInfo != null){
            List<GirdAccessFile> accessPointFiles = accessFileMapper.selectByStationId(stationId);
            girdInfo.setAccessPointFiles(accessPointFiles);
        }
        if(powerStation != null){
            switch (powerStation.getStationType()){
                case "1":
                    powerStation.setStationTypeName("地面电站");
                    break;
                case "2":
                    powerStation.setStationTypeName("山面电站");
                    break;
                case "3":
                    powerStation.setStationTypeName("农光电站");
                    break;
                case "4":
                    powerStation.setStationTypeName("渔光电站");
                    break;
                case "5":
                    powerStation.setStationTypeName("漂浮电站");
                    break;
                case "6":
                    powerStation.setStationTypeName("分布式电站");
                    break;
                case "7":
                    powerStation.setStationTypeName("扶贫电站");
                    break;
                case "8":
                    powerStation.setStationTypeName("户用分布式电站");
                    break;
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stationInfo",powerStation);
        jsonObject.put("baseInfo",baseInfo);
        jsonObject.put("buildInfo",buildInfo);
        jsonObject.put("superviseInfo",superviseInfo);
        jsonObject.put("constructInfo",constructInfo);
        jsonObject.put("girdInfo",girdInfo);
        jsonObject.put("stationFileList",fileList);
        return jsonObject;
    }

    public void addMenu(TreeNode treeNode){
        menuMapper.insert(treeNode);
    }

    public void importExcelData(Long stationId,Workbook workbook){
        Sheet positionInfoSheet = workbook.getSheetAt(0);//位置信息
        Sheet baseInfoSheet = workbook.getSheetAt(1);//基础信息
        Sheet buildInfoSheet = workbook.getSheetAt(2);//施工信息
        Sheet constructInfoSheet = workbook.getSheetAt(3);//建设信息
        Sheet superviseInfoSheet = workbook.getSheetAt(4);//监理信息
        Sheet girdInfoSheet = workbook.getSheetAt(5);//并网信息
        //保存数据
        dealPositionInfo(positionInfoSheet,stationId);
        dealBaseInfo(baseInfoSheet,stationId);
        dealBulidInfo(buildInfoSheet,stationId);
        dealConstructInfo(constructInfoSheet,stationId);
        dealSuperviseInfo(superviseInfoSheet,stationId);
        dealGirdInfo(girdInfoSheet,stationId);
    }

    private void dealPositionInfo(Sheet sheet,Long stationId){
        PowerStationBase stationBase = new PowerStationBase();
        String stationName = getSheetValue(sheet,1,1);
        String province = getSheetValue(sheet,3,1);
        String city = getSheetValue(sheet,4,1);
        String county = getSheetValue(sheet,5,1);
        String town = getSheetValue(sheet,6,1);
        String village = getSheetValue(sheet,7,1);
        String longitude = getSheetValue(sheet,8,1);//经度
        String latitude = getSheetValue(sheet,9,1);//维度

        stationBase.setStationId(stationId);
        stationBase.setStationName(stationName);
        stationBase.setProvince(province);
        stationBase.setCity(city);
        stationBase.setCounty(county);
        stationBase.setTown(town);
        stationBase.setVillage(village);
        stationBase.setLongitude(longitude);
        stationBase.setLatitude(latitude);
        stationBaseMapper.upsert(stationBase);
    }

    private void dealBaseInfo(Sheet sheet,Long stationId){
        PowerStation station = powerStationMapper.selectByPrimaryKey(stationId);
        if(station != null){
            String type = station.getStationType();
            switch (type){
                //地面电站
                case "1":
                    readExcelStationInfo.readGroundStation(sheet,stationId);
                    break;
                //山地电站
                case "2":
                    readExcelStationInfo.readGroundStation(sheet,stationId);
                    break;
                //农光电站
                case "3":
                    readExcelStationInfo.readAgricultureStation(sheet,stationId);
                    break;
                //渔光电站
                case "4":
                    readExcelStationInfo.readFishStation(sheet,stationId);
                    break;
                //漂浮电站
                case "5":
                    readExcelStationInfo.readFloatStation(sheet,stationId);
                    break;
                //分布式电站
                case "6":
                    readExcelStationInfo.readDistributedStation(sheet,stationId);
                    break;
                //扶贫电站
                case "7":
                    readExcelStationInfo.readFloatStation(sheet,stationId);
                    break;
                //户用分布式电站
                case "8":
                    readExcelStationInfo.readUserDistributedStation(sheet,stationId);
                    break;
            }
        }
    }

    private void dealBulidInfo(Sheet sheet,Long stationId){
        buildInfoMapper.deleteByPrimaryKey(stationId);
        PowerStationBuildInfo stationBuildInfo = new PowerStationBuildInfo();
        String buildCompany = getSheetValue(sheet,1,1);
        String address = getSheetValue(sheet,2,1);
        String contact = getSheetValue(sheet,3,1);
        String phone = getSheetValue(sheet,4,1);
        String remark = getSheetValue(sheet,5,1);
        if(stationId == null){
            return;
        }
        if(StringUtils.isBlank(buildCompany)
                && StringUtils.isBlank(address)
                && StringUtils.isBlank(contact)
                && StringUtils.isBlank(phone)
                && StringUtils.isBlank(remark)){
            return;
        }
        stationBuildInfo.setBuildCompany(buildCompany);
        stationBuildInfo.setCompanyAddress(address);
        stationBuildInfo.setContact(contact);
        stationBuildInfo.setPhone(phone);
        stationBuildInfo.setRemark(remark);
        stationBuildInfo.setStationId(stationId);
        buildInfoMapper.upsert(stationBuildInfo);
    }

    private void dealConstructInfo(Sheet sheet,Long stationId){
        PowerStationConstructInfo constructInfo = new PowerStationConstructInfo();
        String constructCompany = getSheetValue(sheet,1,1);
        String address = getSheetValue(sheet,2,1);
        String contact = getSheetValue(sheet,3,1);
        String phone = getSheetValue(sheet,4,1);
        String remark = getSheetValue(sheet,5,1);

        if(stationId == null){
            return;
        }

        if(StringUtils.isBlank(constructCompany)
            && StringUtils.isBlank(address)
            && StringUtils.isBlank(contact)
            && StringUtils.isBlank(phone)
            && StringUtils.isBlank(remark)){
            return;
        }

        constructInfo.setConstructCompany(constructCompany);
        constructInfo.setCompanyAddress(address);
        constructInfo.setContact(contact);
        constructInfo.setPhone(phone);
        constructInfo.setRemark(remark);
        constructInfo.setStationId(stationId);
        constructInfoMapper.upsert(constructInfo);
    }

    private void dealSuperviseInfo(Sheet sheet,Long stationId){
        PowerStationSupervise infoBean = new PowerStationSupervise();
        if(stationId == null) {
            return;
        }
        String superviseCompany = getSheetValue(sheet,1,1);
        String address = getSheetValue(sheet,2,1);
        String contact = getSheetValue(sheet,3,1);
        String phone = getSheetValue(sheet,4,1);
        String remark = getSheetValue(sheet,5,1);

        if(stationId == null){
            return;
        }

        if(StringUtils.isBlank(superviseCompany)
                && StringUtils.isBlank(address)
                && StringUtils.isBlank(contact)
                && StringUtils.isBlank(phone)
                && StringUtils.isBlank(remark)){
            return;
        }

        infoBean.setSuperviseCompany(superviseCompany);
        infoBean.setCompanyAddress(address);
        infoBean.setContact(contact);
        infoBean.setPhone(phone);
        infoBean.setRemark(remark);
        infoBean.setStationId(stationId);
        superviseMapper.upsert(infoBean);
    }

    private void dealGirdInfo(Sheet sheet,Long stationId){
        PowerStationGirdInfo girdInfo = new PowerStationGirdInfo();
        String girdName = getSheetValue(sheet,1,1);
        String contact = getSheetValue(sheet,2,1);
        String phone = getSheetValue(sheet,3,1);
        String voltageLevel = getSheetValue(sheet,4,1);
        String remark = getSheetValue(sheet,5,1);

        if(stationId == null){
            return;
        }

        if(StringUtils.isBlank(girdName)
                && StringUtils.isBlank(voltageLevel)
                && StringUtils.isBlank(contact)
                && StringUtils.isBlank(phone)
                && StringUtils.isBlank(remark)){
            return;
        }

        girdInfo.setGirdName(girdName);
        girdInfo.setVoltageLevel(voltageLevel);
        girdInfo.setContact(contact);
        girdInfo.setPhone(phone);
        girdInfo.setRemark(remark);
        girdInfo.setStationId(stationId);
        girdInfoMapper.insert(girdInfo);
    }


    public String getSheetValue(Sheet sheet,int rowNum,int colNum){
        Row row = sheet.getRow(rowNum);
        DataFormatter formatter = new DataFormatter();
        if(row != null){
            Cell cell = row.getCell(colNum);
            if(cell != null){
                return formatter.formatCellValue(cell);
            }
            return null;
        }
        return null;
    }

}
