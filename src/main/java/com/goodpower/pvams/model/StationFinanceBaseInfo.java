package com.goodpower.pvams.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class StationFinanceBaseInfo implements Serializable {
    private Long id;

    private Long stationId;

    private BigDecimal totalCost;

    private BigDecimal loanCost;

    private BigDecimal runCost;

    private Integer loanType;

    private Integer loanYear;

    private Date firstRepaymentDate;

    private BigDecimal loanRate;

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

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getLoanCost() {
        return loanCost;
    }

    public void setLoanCost(BigDecimal loanCost) {
        this.loanCost = loanCost;
    }

    public BigDecimal getRunCost() {
        return runCost;
    }

    public void setRunCost(BigDecimal runCost) {
        this.runCost = runCost;
    }

    public Integer getLoanType() {
        return loanType;
    }

    public void setLoanType(Integer loanType) {
        this.loanType = loanType;
    }

    public Integer getLoanYear() {
        return loanYear;
    }

    public void setLoanYear(Integer loanYear) {
        this.loanYear = loanYear;
    }

    public Date getFirstRepaymentDate() {
        return firstRepaymentDate;
    }

    public void setFirstRepaymentDate(Date firstRepaymentDate) {
        this.firstRepaymentDate = firstRepaymentDate;
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
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