package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationPolicy;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface StationPolicyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationPolicy record);

    int insertSelective(StationPolicy record);

    StationPolicy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StationPolicy record);

    int updateByPrimaryKey(StationPolicy record);

    List<StationPolicy> selectByFields(Map<String,Object> param);

    void deleteByField(Map<String,Object> param);
}