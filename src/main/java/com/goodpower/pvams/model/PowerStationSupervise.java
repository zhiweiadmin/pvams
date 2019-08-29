package com.goodpower.pvams.model;

import java.io.Serializable;

public class PowerStationSupervise implements Serializable {
    private Long stationId;

    private String superviseCompany;

    private String contact;

    private String phone;

    private String companyAddress;

    private String remark;

    private static final long serialVersionUID = 1L;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getSuperviseCompany() {
        return superviseCompany;
    }

    public void setSuperviseCompany(String superviseCompany) {
        this.superviseCompany = superviseCompany == null ? null : superviseCompany.trim();
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

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}