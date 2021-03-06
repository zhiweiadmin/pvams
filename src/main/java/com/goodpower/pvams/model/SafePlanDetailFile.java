package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;

public class SafePlanDetailFile implements Serializable {
    private Long id;

    private Long safeDetailId;

    private String path;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSafeDetailId() {
        return safeDetailId;
    }

    public void setSafeDetailId(Long safeDetailId) {
        this.safeDetailId = safeDetailId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
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