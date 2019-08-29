package com.goodpower.pvams.model;

import java.io.Serializable;

public class County implements Serializable {
    private Long countyId;

    private Long cityId;

    private String countyName;

    private static final long serialVersionUID = 1L;

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName == null ? null : countyName.trim();
    }
}