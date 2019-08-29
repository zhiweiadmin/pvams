package com.goodpower.pvams.model;

import java.io.Serializable;

public class Province implements Serializable {
    private Long provinceId;

    private String provinceName;

    private static final long serialVersionUID = 1L;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }
}