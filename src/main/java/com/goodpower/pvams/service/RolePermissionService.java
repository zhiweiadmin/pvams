package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.RolePermissionMapper;
import com.goodpower.pvams.model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService {

    @Autowired
    RolePermissionMapper rolePermissionMapper;

    public List<RolePermission> getRolePermission(Integer role){
        return rolePermissionMapper.getRolePermission(role);
    }

}
