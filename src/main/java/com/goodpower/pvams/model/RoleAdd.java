package com.goodpower.pvams.model;

import java.io.Serializable;

public class RoleAdd implements Serializable {
    private Integer id;

    private Integer role;

    private Integer userType;

    private Integer useRole;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getUseRole() {
        return useRole;
    }

    public void setUseRole(Integer useRole) {
        this.useRole = useRole;
    }
}