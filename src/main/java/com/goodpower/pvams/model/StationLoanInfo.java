package com.goodpower.pvams.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StationLoanInfo implements Serializable {
    private Long id;

    private Long stationId;

    private Integer loanType;

    private BigDecimal monthPay;

    private Integer year;

    private Integer month;

    private Date payDate;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public BigDecimal getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(BigDecimal monthPay) {
        this.monthPay = monthPay;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
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