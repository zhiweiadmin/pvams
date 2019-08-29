package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.List;

public class TreeNode implements Serializable {
    private String id;

    private String showName;

    private String type;

    private String pId;

    private List<TreeNode> childNodeList;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName == null ? null : showName.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public List<TreeNode> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<TreeNode> childNodeList) {
        this.childNodeList = childNodeList;
    }
}