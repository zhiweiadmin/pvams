package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SafePlan implements Serializable {
    private Long safeId;

    private Long stationId;

    private Integer year;

    private String title;

    private String content;

    private Long creator;

    private Long updater;

    private String msg;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    private List<Object> safePlanDetailList;

    public Long getSafeId() {
        return safeId;
    }

    public void setSafeId(Long safeId) {
        this.safeId = safeId;
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

    public List<Object> getSafePlanDetailList() {
        return safePlanDetailList;
    }

    public void setSafePlanDetailList(List<Object> safePlanDetailList) {
        this.safePlanDetailList = safePlanDetailList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
