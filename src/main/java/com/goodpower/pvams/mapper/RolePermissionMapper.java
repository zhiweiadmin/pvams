package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface RolePermissionMapper {

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> getRolePermission(Integer role);

}