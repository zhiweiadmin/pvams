package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrainPlanDetail implements Serializable {
    private Long trainDetailId;

    private Long trainId;

    private Integer trainWeek;

    private String trainer;

    private Date trainTime;

    private String content;

    private String flow;

    private String result;

    private Integer status;

    private Long submitUserId;

    private Long confirmUserId;

    private Date confirmTime;

    private Date createDttm;

    private Date updateDttm;

    private List<String> fileList;

    private List<String> fileList2;

    private Integer month;

    private Integer week;

    private String firstDay;

    private static final long serialVersionUID = 1L;

    public Long getTrainDetailId() {
        return trainDetailId;
    }

    public void setTrainDetailId(Long trainDetailId) {
        this.trainDetailId = trainDetailId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Integer getTrainWeek() {
        return trainWeek;
    }

    public void setTrainWeek(Integer trainWeek) {
        this.trainWeek = trainWeek;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer == null ? null : trainer.trim();
    }

    public Date getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(Date trainTime) {
        this.trainTime = trainTime;
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

    public List<String> getFileList2() {
        return fileList2;
    }

    public void setFileList2(List<String> fileList2) {
        this.fileList2 = fileList2;
    }

}