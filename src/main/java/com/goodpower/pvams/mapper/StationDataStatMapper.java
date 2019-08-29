package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationDataStat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface StationDataStatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationDataStat record);

    int insertSelective(StationDataStat record);

    StationDataStat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StationDataStat record);

    int updateByPrimaryKey(StationDataStat record);

    List<Map<String,Object>> queryMonthStat(Map<String,Object> param);

    List<Map<String,Object>> queryYearStat(Map<String,Object> param);

 }