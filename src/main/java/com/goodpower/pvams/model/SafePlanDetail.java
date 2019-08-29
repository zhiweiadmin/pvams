package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SafePlanDetail implements Serializable {
    private Long safeDetailId;

    private Long safeId;

    private Integer safeWeek;

    private String content;

    private String flow;

    private String result;

    private Integer status;

    private Long submitUserId;

    private Date submitTime;

    private Long confirmUserId;

    private Date confirmTime;

    private Date createDttm;

    private Date updateDttm;

    private Integer month;

    private Integer week;

    private String firstDay;

    private List<String> fileList;

    private static final long serialVersionUID = 1L;

    public Long getSafeDetailId() {
        return safeDetailId;
    }

    public void setSafeDetailId(Long safeDetailId) {
        this.safeDetailId = safeDetailId;
    }

    public Long getSafeId() {
        return safeId;
    }

    public void setSafeId(Long safeId) {
        this.safeId = safeId;
    }

    public Integer getSafeWeek() {
        return safeWeek;
    }

    public void setSafeWeek(Integer safeWeek) {
        this.safeWeek = safeWeek;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow == null ? null : flow.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(Long submitUserId) {
        this.submitUserId = submitUserId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Long getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Long confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
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

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(List<String> fileList) {
        this.fileList = fileList;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public String getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }
}