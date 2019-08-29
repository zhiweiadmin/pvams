package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;

public class PowerGenerateStatKey implements Serializable {
    private Long stationId;

    private Date statDate;

    private static final long serialVersionUID = 1L;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }
}