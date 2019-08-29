package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.List;

public class DeviceMaintain  extends BaseModel implements Serializable{
    private Long maintainId;

    private String yearVal;

    private Long stationId;

    private String acSide;

    private String secondaryEquipment;

    private String dcSide;

    private String msg;

    private List<Object> deviceMaintainDetailList;

    private static final long serialVersionUID = 1L;

    public Long getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getAcSide() {
        return acSide;
    }

    public void setAcSide(String acSide) {
        this.acSide = acSide == null ? null : acSide.trim();
    }

    public String getSecondaryEquipment() {
        return secondaryEquipment;
    }

    public void setSecondaryEquipment(String secondaryEquipment) {
        this.secondaryEquipment = secondaryEquipment == null ? null : secondaryEquipment.trim();
    }

    public String getDcSide() {
        return dcSide;
    }

    public void setDcSide(String dcSide) {
        this.dcSide = dcSide == null ? null : dcSide.trim();
    }

    public String getYearVal() {
        return yearVal;
    }

    public void setYearVal(String yearVal) {
        this.yearVal = yearVal;
    }

    public List<Object> getDeviceMaintainDetailList() {
        return deviceMaintainDetailList;
    }

    public void setDeviceMaintainDetailList(List<Object> deviceMaintainDetailList) {
        this.deviceMaintainDetailList = deviceMaintainDetailList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}