package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);
}