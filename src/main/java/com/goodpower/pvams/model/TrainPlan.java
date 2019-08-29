package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrainPlan implements Serializable {
    private Long trainId;

    private Long stationId;

    private String title;

    private String content;

    private Integer year;

    private String msg;

    private Long creator;

    private Long updater;

    private Date createDttm;

    private Date updateDttm;

    private List<Object> trainPlanDetailList;

    private static final long serialVersionUID = 1L;

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getUpdater() {
        return updater;
    }

    public void setUpdater(Long updater) {
        this.updater = updater;
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

    public List<Object> getTrainPlanDetailList() {
        return trainPlanDetailList;
    }

    public void setTrainPlanDetailList(List<Object> trainPlanDetailList) {
        this.trainPlanDetailList = trainPlanDetailList;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}