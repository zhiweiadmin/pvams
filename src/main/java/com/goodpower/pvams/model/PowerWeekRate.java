package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;

public class PowerWeekRate implements Serializable {
    private Long stationId;

    private String yearVal;

    private String theoryWeakRate;

    private String realWeakRate;

    private String creator;

    private String updater;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getYearVal() {
        return yearVal;
    }

    public void setYearVal(String yearVal) {
        this.yearVal = yearVal == null ? null : yearVal.trim();
    }

    public String getTheoryWeakRate() {
        return theoryWeakRate;
    }

    public void setTheoryWeakRate(String theoryWeakRate) {
        this.theoryWeakRate = theoryWeakRate == null ? null : theoryWeakRate.trim();
    }

    public String getRealWeakRate() {
        return realWeakRate;
    }

    public void setRealWeakRate(String realWeakRate) {
        this.realWeakRate = realWeakRate == null ? null : realWeakRate.trim();
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
}