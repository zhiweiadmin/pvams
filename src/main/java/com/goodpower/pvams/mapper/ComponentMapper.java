package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Component;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface ComponentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Component record);

    int insertSelective(Component record);

    Component selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Component record);

    int updateByPrimaryKey(Component record);

    List<Component> selectByFields(Map<String,Object> param);

    int updateByVersion(Map<String,Object> param);

    List<Component> selectByField(Map<String,Object> param);

    void deleteByFields(Map<String,Object> map);

    int getCount(Map<String,Object> param);
}