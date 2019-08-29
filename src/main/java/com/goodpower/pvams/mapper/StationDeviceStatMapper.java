package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationDeviceStat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface StationDeviceStatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationDeviceStat record);

    int insertSelective(StationDeviceStat record);

    StationDeviceStat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StationDeviceStat record);

    int updateByPrimaryKey(StationDeviceStat record);

    List<Map<String,Object>> queryMonthStat(Map<String,Object> param);

    int queryMonthStatCount(Map<String,Object> param);

    List<Map<String,Object>> queryYearStat(Map<String,Object> param);

    int queryYearStatCount(Map<String,Object> param);

    List<Map<String,Object>> getMonthDeviceStatDetail(Map<String,Object> param);

    List<Map<String,Object>> getYearDeviceStatDetail(Map<String,Object> param);

}