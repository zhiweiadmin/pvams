package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);
}