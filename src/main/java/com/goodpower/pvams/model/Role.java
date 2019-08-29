package com.goodpower.pvams.model;

import java.io.Serializable;

public class Role implements Serializable {
    private Integer role;

    private String roleName;

    private static final long serialVersionUID = 1L;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }
}