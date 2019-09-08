package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.RoleAdd;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface RoleAddMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleAdd record);

    int insertSelective(RoleAdd record);

    RoleAdd selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleAdd record);

    int updateByPrimaryKey(RoleAdd record);

    List<Map<String,Object>> selectByFields(Map<String,Object> param);
}