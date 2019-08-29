package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationFinanceData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface StationFinanceDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationFinanceData record);

    int insertSelective(StationFinanceData record);

    StationFinanceData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StationFinanceData record);

    int updateByPrimaryKey(StationFinanceData record);

    List<Map<String,Object>> getMonthDataStatReal(Map<String, Object> param);

    List<Map<String,Object>> getYearDataStatReal(Map<String, Object> param);

    List<Map<String,Object>> getMonthDataStatPlan(Map<String, Object> param);

    List<Map<String,Object>> getYearDataStatPlan(Map<String, Object> param);
}