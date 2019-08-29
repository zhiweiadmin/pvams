package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerGenerateStat;
import com.goodpower.pvams.model.PowerGenerateStatKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface PowerGenerateStatMapper {
    int deleteByPrimaryKey(PowerGenerateStatKey key);

    int insert(PowerGenerateStat record);

    int insertSelective(PowerGenerateStat record);

    PowerGenerateStat selectByPrimaryKey(PowerGenerateStatKey key);

    int updateByPrimaryKeySelective(PowerGenerateStat record);

    int updateByPrimaryKey(PowerGenerateStat record);

    List<Map<String,Object>> queryPowerStatByMonth(Long stationId,String startDate,String endDate);

    List<Map<String,Object>> queryPowerStatByQuarter(Long stationId,String startDate,String endDate);

    List<Map<String,Object>> getYearPowerStat(Long stationId);

    List<Map<String,Object>> getMonthPowerStat(Long stationId,Long year);

    Map<String,Object> queryPowerStatByFields(Map<String,Object> paramMap);

    List<PowerGenerateStat> selectByFields(Map<String,Object> paramMap);
}

