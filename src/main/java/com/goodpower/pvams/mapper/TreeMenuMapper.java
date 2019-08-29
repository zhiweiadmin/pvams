package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.TreeMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface TreeMenuMapper {
    int insert(TreeMenu record);

    int insertSelective(TreeMenu record);
}