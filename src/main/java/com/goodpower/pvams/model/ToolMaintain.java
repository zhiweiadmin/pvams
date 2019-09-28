package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;

public class ToolMaintain implements Serializable {
    private Long id;

    private Long stationId;

    private String tool;

    private String position;

    private Integer num;

    private String type;

    private Date checkTime;

    private Date nextCheckTime;

    private String param;

    private Integer checkStatus;

    private String checkPlan;

    private Long checkUserId;

    private String checker;

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

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool == null ? null : tool.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getNextCheckTime() {
        return nextCheckTime;
    }

    public void setNextCheckTime(Date nextCheckTime) {
        this.nextCheckTime = nextCheckTime;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckPlan() {
        return checkPlan;
    }

    public void setCheckPlan(String checkPlan) {
        this.checkPlan = checkPlan == null ? null : checkPlan.trim();
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

    public Long getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(Long checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }
}