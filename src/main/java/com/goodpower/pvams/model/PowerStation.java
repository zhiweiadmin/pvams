package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;

public class PowerStation implements Serializable {
    private Long stationId;

    private Long companyId;

    private String stationName;

    private String scale;

    private String address;

    private String stationType;

    private String stationTypeName;

    private String installedCapacity;

    private String gridCapacity;

    private String installedPower;

    private String gridTime;

    private Long gridNum;

    private String projectAngle;

    private String arraySpace;

    private String gridPowerLevel;

    private String lineLength;

    private String roadType;

    private String isCloseFramarea;

    private String isCorral;

    private String isOutsidersPlant;

    private String isOutsidersAncestor;

    private String bottomLandDistance;

    private String pondNum;

    private String pondAvgDepth;

    private String floatType;

    private String fixedType;

    private String bottomWaterDistance;

    private String isAquatic;

    private String isAnimal;

    private String roofType;

    private String isDaylight;

    private String upCondition;

    private String roofNum;

    private String distributionPointNum;

    private String clearWaterSource;

    private String clearWaterSourceAccess;

    private Integer weekStart = 1;

    private Integer monthStart = 1;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale == null ? null : scale.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType == null ? null : stationType.trim();
    }

    public String getInstalledCapacity() {
        return installedCapacity;
    }

    public void setInstalledCapacity(String installedCapacity) {
        this.installedCapacity = installedCapacity == null ? null : installedCapacity.trim();
    }

    public String getGridCapacity() {
        return gridCapacity;
    }

    public void setGridCapacity(String gridCapacity) {
        this.gridCapacity = gridCapacity == null ? null : gridCapacity.trim();
    }

    public String getInstalledPower() {
        return installedPower;
    }

    public void setInstalledPower(String installedPower) {
        this.installedPower = installedPower == null ? null : installedPower.trim();
    }

    public String getGridTime() {
        return gridTime;
    }

    public void setGridTime(String gridTime) {
        this.gridTime = gridTime == null ? null : gridTime.trim();
    }

    public String getProjectAngle() {
        return projectAngle;
    }

    public void setProjectAngle(String projectAngle) {
        this.projectAngle = projectAngle == null ? null : projectAngle.trim();
    }

    public String getArraySpace() {
        return arraySpace;
    }

    public void setArraySpace(String arraySpace) {
        this.arraySpace = arraySpace == null ? null : arraySpace.trim();
    }

    public String getGridPowerLevel() {
        return gridPowerLevel;
    }

    public void setGridPowerLevel(String gridPowerLevel) {
        this.gridPowerLevel = gridPowerLevel == null ? null : gridPowerLevel.trim();
    }

    public String getLineLength() {
        return lineLength;
    }

    public void setLineLength(String lineLength) {
        this.lineLength = lineLength == null ? null : lineLength.trim();
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType == null ? null : roadType.trim();
    }

    public String getIsCloseFramarea() {
        return isCloseFramarea;
    }

    public void setIsCloseFramarea(String isCloseFramarea) {
        this.isCloseFramarea = isCloseFramarea == null ? null : isCloseFramarea.trim();
    }

    public String getIsCorral() {
        return isCorral;
    }

    public void setIsCorral(String isCorral) {
        this.isCorral = isCorral == null ? null : isCorral.trim();
    }

    public String getIsOutsidersPlant() {
        return isOutsidersPlant;
    }

    public void setIsOutsidersPlant(String isOutsidersPlant) {
        this.isOutsidersPlant = isOutsidersPlant == null ? null : isOutsidersPlant.trim();
    }

    public String getIsOutsidersAncestor() {
        return isOutsidersAncestor;
    }

    public void setIsOutsidersAncestor(String isOutsidersAncestor) {
        this.isOutsidersAncestor = isOutsidersAncestor == null ? null : isOutsidersAncestor.trim();
    }

    public String getBottomLandDistance() {
        return bottomLandDistance;
    }

    public void setBottomLandDistance(String bottomLandDistance) {
        this.bottomLandDistance = bottomLandDistance == null ? null : bottomLandDistance.trim();
    }

    public String getPondNum() {
        return pondNum;
    }

    public void setPondNum(String pondNum) {
        this.pondNum = pondNum == null ? null : pondNum.trim();
    }

    public String getPondAvgDepth() {
        return pondAvgDepth;
    }

    public void setPondAvgDepth(String pondAvgDepth) {
        this.pondAvgDepth = pondAvgDepth == null ? null : pondAvgDepth.trim();
    }

    public String getFloatType() {
        return floatType;
    }

    public void setFloatType(String floatType) {
        this.floatType = floatType == null ? null : floatType.trim();
    }

    public String getFixedType() {
        return fixedType;
    }

    public void setFixedType(String fixedType) {
        this.fixedType = fixedType == null ? null : fixedType.trim();
    }

    public String getBottomWaterDistance() {
        return bottomWaterDistance;
    }

    public void setBottomWaterDistance(String bottomWaterDistance) {
        this.bottomWaterDistance = bottomWaterDistance == null ? null : bottomWaterDistance.trim();
    }

    public String getIsAquatic() {
        return isAquatic;
    }

    public void setIsAquatic(String isAquatic) {
        this.isAquatic = isAquatic == null ? null : isAquatic.trim();
    }

    public String getIsAnimal() {
        return isAnimal;
    }

    public void setIsAnimal(String isAnimal) {
        this.isAnimal = isAnimal == null ? null : isAnimal.trim();
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType == null ? null : roofType.trim();
    }

    public String getIsDaylight() {
        return isDaylight;
    }

    public void setIsDaylight(String isDaylight) {
        this.isDaylight = isDaylight == null ? null : isDaylight.trim();
    }

    public String getUpCondition() {
        return upCondition;
    }

    public void setUpCondition(String upCondition) {
        this.upCondition = upCondition == null ? null : upCondition.trim();
    }

    public String getRoofNum() {
        return roofNum;
    }

    public void setRoofNum(String roofNum) {
        this.roofNum = roofNum == null ? null : roofNum.trim();
    }

    public String getDistributionPointNum() {
        return distributionPointNum;
    }

    public void setDistributionPointNum(String distributionPointNum) {
        this.distributionPointNum = distributionPointNum == null ? null : distributionPointNum.trim();
    }

    public String getClearWaterSource() {
        return clearWaterSource;
    }

    public void setClearWaterSource(String clearWaterSource) {
        this.clearWaterSource = clearWaterSource == null ? null : clearWaterSource.trim();
    }

    public String getClearWaterSourceAccess() {
        return clearWaterSourceAccess;
    }

    public void setClearWaterSourceAccess(String clearWaterSourceAccess) {
        this.clearWaterSourceAccess = clearWaterSourceAccess == null ? null : clearWaterSourceAccess.trim();
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

    public Long getGridNum() {
        return gridNum;
    }

    public void setGridNum(Long gridNum) {
        this.gridNum = gridNum;
    }

    public Integer getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(Integer weekStart) {
        this.weekStart = weekStart;
    }

    public Integer getMonthStart() {
        return monthStart;
    }

    public void setMonthStart(Integer monthStart) {
        this.monthStart = monthStart;
    }

    public String getStationTypeName() {
        return stationTypeName;
    }

    public void setStationTypeName(String stationTypeName) {
        this.stationTypeName = stationTypeName;
    }
}