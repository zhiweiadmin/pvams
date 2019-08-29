package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.WorkRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface WorkRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WorkRecord record);

    int insertSelective(WorkRecord record);

    WorkRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WorkRecord record);

    int updateByPrimaryKey(WorkRecord record);

    List<WorkRecord> selectByFields(Map<String,Object> map);
}