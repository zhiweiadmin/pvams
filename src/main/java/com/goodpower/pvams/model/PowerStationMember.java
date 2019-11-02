package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PowerStationMember implements Serializable {
    private Long memberId;

    private Long stationId;

    private String realName;

    private String maintainCompany;

    private String idCard;

    private String position;

    private String credential;

    private String userPic;

    private String certificatePic;

    private List<String> fileList;

    private List<Map<String,Object>> imgList;

    private Date createDttm;

    private Date updateDttm;

    private static final long serialVersionUID = 1L;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMaintainCompany() {
        return maintainCompany;
    }

    public void setMaintainCompany(String maintainCompany) {
        this.maintainCompany = maintainCompany == null ? null : maintainCompany.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential == null ? null : credential.trim();
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic == null ? null : userPic.trim();
    }

    public String getCertificatePic() {
        return certificatePic;
    }

    public void setCertificatePic(String certificatePic) {
        this.certificatePic = certificatePic == null ? null : certificatePic.trim();
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

    public List<Map<String, Object>> getImgList() {
        return imgList;
    }

    public void setImgList(List<Map<String, Object>> imgList) {
        this.imgList = imgList;
    }
}