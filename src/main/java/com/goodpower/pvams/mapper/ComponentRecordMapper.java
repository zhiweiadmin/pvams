package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.ComponentRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface ComponentRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComponentRecord record);

    int insertSelective(ComponentRecord record);

    ComponentRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComponentRecord record);

    int updateByPrimaryKey(ComponentRecord record);

    List<Map<String,Object>> selectByFields(Map<String,Object> param);

    int getCount(Map<String,Object> param);
}