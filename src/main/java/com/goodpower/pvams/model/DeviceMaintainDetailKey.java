package com.goodpower.pvams.model;

import java.io.Serializable;

public class DeviceMaintainDetailKey implements Serializable {
    private Long maintainId;

    private Integer maintainWeek;

    private static final long serialVersionUID = 1L;

    public Long getMaintainId() {
        return maintainId;
    }

    public void setMaintainId(Long maintainId) {
        this.maintainId = maintainId;
    }

    public Integer getMaintainWeek() {
        return maintainWeek;
    }

    public void setMaintainWeek(Integer maintainWeek) {
        this.maintainWeek = maintainWeek;
    }
}