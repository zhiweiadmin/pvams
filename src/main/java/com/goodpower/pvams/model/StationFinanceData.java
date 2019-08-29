package com.goodpower.pvams.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class StationFinanceData implements Serializable {
    private Long id;

    private Long stationId;

    private Integer year;

    private Integer month;

    private BigDecimal selfPeakPower;

    private BigDecimal selfPeakElcprice;

    private BigDecimal selfPeakDiscount;

    private BigDecimal selfPower;

    private BigDecimal selfElcprice;

    private BigDecimal selfDiscount;

    private BigDecimal selfLowPower;

    private BigDecimal selfLowElcprice;

    private BigDecimal selfLowDiscount;

    private BigDecimal sellPower;

    private BigDecimal totalPower;

    private BigDecimal pPowerPrice;

    private BigDecimal subsidyPrice;

    private BigDecimal monthSubsidyProfit;

    private BigDecimal monthSubsidyPrice;

    private BigDecimal planPower;

    private BigDecimal planProfit;

    private BigDecimal totolProfit;

    private BigDecimal totalSelfProfit;

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

    public BigDecimal getSelfPeakPower() {
        return selfPeakPower;
    }

    public void setSelfPeakPower(BigDecimal selfPeakPower) {
        this.selfPeakPower = selfPeakPower;
    }

    public BigDecimal getSelfPeakElcprice() {
        return selfPeakElcprice;
    }

    public void setSelfPeakElcprice(BigDecimal selfPeakElcprice) {
        this.selfPeakElcprice = selfPeakElcprice;
    }

    public BigDecimal getSelfPeakDiscount() {
        return selfPeakDiscount;
    }

    public void setSelfPeakDiscount(BigDecimal selfPeakDiscount) {
        this.selfPeakDiscount = selfPeakDiscount;
    }

    public BigDecimal getSelfPower() {
        return selfPower;
    }

    public void setSelfPower(BigDecimal selfPower) {
        this.selfPower = selfPower;
    }

    public BigDecimal getSelfElcprice() {
        return selfElcprice;
    }

    public void setSelfElcprice(BigDecimal selfElcprice) {
        this.selfElcprice = selfElcprice;
    }

    public BigDecimal getSelfDiscount() {
        return selfDiscount;
    }

    public void setSelfDiscount(BigDecimal selfDiscount) {
        this.selfDiscount = selfDiscount;
    }

    public BigDecimal getSelfLowPower() {
        return selfLowPower;
    }

    public void setSelfLowPower(BigDecimal selfLowPower) {
        this.selfLowPower = selfLowPower;
    }

    public BigDecimal getSelfLowElcprice() {
        return selfLowElcprice;
    }

    public void setSelfLowElcprice(BigDecimal selfLowElcprice) {
        this.selfLowElcprice = selfLowElcprice;
    }

    public BigDecimal getSelfLowDiscount() {
        return selfLowDiscount;
    }

    public void setSelfLowDiscount(BigDecimal selfLowDiscount) {
        this.selfLowDiscount = selfLowDiscount;
    }

    public BigDecimal getSellPower() {
        return sellPower;
    }

    public void setSellPower(BigDecimal sellPower) {
        this.sellPower = sellPower;
    }

    public BigDecimal getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(BigDecimal totalPower) {
        this.totalPower = totalPower;
    }

    public BigDecimal getpPowerPrice() {
        return pPowerPrice;
    }

    public void setpPowerPrice(BigDecimal pPowerPrice) {
        this.pPowerPrice = pPowerPrice;
    }

    public BigDecimal getSubsidyPrice() {
        return subsidyPrice;
    }

    public void setSubsidyPrice(BigDecimal subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    public BigDecimal getMonthSubsidyProfit() {
        return monthSubsidyProfit;
    }

    public void setMonthSubsidyProfit(BigDecimal monthSubsidyProfit) {
        this.monthSubsidyProfit = monthSubsidyProfit;
    }

    public BigDecimal getMonthSubsidyPrice() {
        return monthSubsidyPrice;
    }

    public void setMonthSubsidyPrice(BigDecimal monthSubsidyPrice) {
        this.monthSubsidyPrice = monthSubsidyPrice;
    }

    public BigDecimal getPlanPower() {
        return planPower;
    }

    public void setPlanPower(BigDecimal planPower) {
        this.planPower = planPower;
    }

    public BigDecimal getPlanProfit() {
        return planProfit;
    }

    public void setPlanProfit(BigDecimal planProfit) {
        this.planProfit = planProfit;
    }

    public BigDecimal getTotolProfit() {
        return totolProfit;
    }

    public void setTotolProfit(BigDecimal totolProfit) {
        this.totolProfit = totolProfit;
    }

    public BigDecimal getTotalSelfProfit() {
        return totalSelfProfit;
    }

    public void setTotalSelfProfit(BigDecimal totalSelfProfit) {
        this.totalSelfProfit = totalSelfProfit;
    }
}