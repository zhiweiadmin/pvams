package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;

public class PowerStationDevice implements Serializable {
    private Long deviceId;

    private Long stationId;

    private String deviceName;

    private Integer number;

    private String type;

    private String model;

    private String supplier;

    private String contact;

    private String phone;

    private String warrantyStartDate;

    private String warrantyEndDate;

    private String stockUpper;

    private String stockLower;

    private String param;

    private String remark;

    private Date createDttm;

    private Date updateDttm;

    private String creator;

    private String updater;

    private static final long serialVersionUID = 1L;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
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

    public String getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(String warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public String getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(String warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public String getStockUpper() {
        return stockUpper;
    }

    public void setStockUpper(String stockUpper) {
        this.stockUpper = stockUpper == null ? null : stockUpper.trim();
    }

    public String getStockLower() {
        return stockLower;
    }

    public void setStockLower(String stockLower) {
        this.stockLower = stockLower == null ? null : stockLower.trim();
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateDttm() {
        return createDttm;
    }

    public void setCreateDttm(Date createDttm) {
        this.createDttm = createDttm;
    }

    public Date getUpdateDttm() {
        return updateDttm;
    }

    public void setUpdateDttm(Date updateDttm) {
        this.updateDttm = updateDttm;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }



}