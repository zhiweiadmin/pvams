package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.ToolMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface ToolMaintainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ToolMaintain record);

    int insertSelective(ToolMaintain record);

    ToolMaintain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ToolMaintain record);

    int updateByPrimaryKey(ToolMaintain record);

    int updateStatus(Map<String,Object> map);

    List<ToolMaintain> selectByFields(Map<String,Object> map);

    int getCount(Map<String,Object> map);

    void deleteByFields(Map<String,Object> map);
}