package com.goodpower.pvams.model;

import java.math.BigDecimal;

public class PowerModel {

    Integer year;

    Integer dateParam;

    Integer dateType;//0是1月份  1是季度

    Integer dateVal;//月份还是季度

    BigDecimal powerRealVal;//实际发电量

    BigDecimal powerPlanVal;//辐照发电量

    BigDecimal powerTheoryVal;//理论发电量

    BigDecimal theoryHour;

    BigDecimal realHour;

    BigDecimal fuzhaoHour;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

    public Integer getDateVal() {
        return dateVal;
    }

    public void setDateVal(Integer dateVal) {
        this.dateVal = dateVal;
    }

    public BigDecimal getPowerRealVal() {
        return powerRealVal;
    }

    public void setPowerRealVal(BigDecimal powerRealVal) {
        this.powerRealVal = powerRealVal;
    }

    public BigDecimal getPowerPlanVal() {
        return powerPlanVal;
    }

    public void setPowerPlanVal(BigDecimal powerPlanVal) {
        this.powerPlanVal = powerPlanVal;
    }

    public BigDecimal getPowerTheoryVal() {
        return powerTheoryVal;
    }

    public void setPowerTheoryVal(BigDecimal powerTheoryVal) {
        this.powerTheoryVal = powerTheoryVal;
    }

    public BigDecimal getTheoryHour() {
        return theoryHour;
    }

    public void setTheoryHour(BigDecimal theoryHour) {
        this.theoryHour = theoryHour;
    }

    public BigDecimal getRealHour() {
        return realHour;
    }

    public void setRealHour(BigDecimal realHour) {
        this.realHour = realHour;
    }

    public BigDecimal getFuzhaoHour() {
        return fuzhaoHour;
    }

    public void setFuzhaoHour(BigDecimal fuzhaoHour) {
        this.fuzhaoHour = fuzhaoHour;
    }

    public Integer getDateParam() {
        return dateParam;
    }

    public void setDateParam(Integer dateParam) {
        this.dateParam = dateParam;
    }
}
