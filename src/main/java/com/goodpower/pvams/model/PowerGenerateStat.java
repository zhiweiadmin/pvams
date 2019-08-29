package com.goodpower.pvams.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PowerGenerateStat extends PowerGenerateStatKey implements Serializable {
    private String year;

    private String month;

    private String quarter;

    private String week;

    private String statType;

    private BigDecimal statVal;

    private String creator;

    private String updater;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    public String getStatType() {
        return statType;
    }

    public void setStatType(String statType) {
        this.statType = statType == null ? null : statType.trim();
    }

    public BigDecimal getStatVal() {
        return statVal;
    }

    public void setStatVal(BigDecimal statVal) {
        this.statVal = statVal;
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

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}