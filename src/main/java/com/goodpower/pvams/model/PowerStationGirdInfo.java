package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.List;

public class PowerStationGirdInfo implements Serializable {
    private Long stationId;

    private String girdName;

    private String contact;

    private String phone;

    private String voltageLevel;

    private String remark;

    private List<GirdAccessFile> accessPointFiles;

    private static final long serialVersionUID = 1L;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getGirdName() {
        return girdName;
    }

    public void setGirdName(String girdName) {
        this.girdName = girdName == null ? null : girdName.trim();
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact == null ? null : contact.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(String voltageLevel) {
        this.voltageLevel = voltageLevel == null ? null : voltageLevel.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<GirdAccessFile> getAccessPointFiles() {
        return accessPointFiles;
    }

    public void setAccessPointFiles(List<GirdAccessFile> accessPointFiles) {
        this.accessPointFiles = accessPointFiles;
    }

}