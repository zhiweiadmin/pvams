package com.goodpower.pvams.model;

import java.io.Serializable;

public class City implements Serializable {
    private Long cityId;

    private Long provinceId;

    private String cityName;

    private static final long serialVersionUID = 1L;

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }
}