package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.ToolCheckRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface ToolCheckRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ToolCheckRecord record);

    int insertSelective(ToolCheckRecord record);

    ToolCheckRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ToolCheckRecord record);

    int updateByPrimaryKey(ToolCheckRecord record);

    List<Map<String,Object>> selectByFields(Map<String,Object> param);

    int getCount(Map<String,Object> param);
}