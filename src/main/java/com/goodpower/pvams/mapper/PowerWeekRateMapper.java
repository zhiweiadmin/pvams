package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerWeekRate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface PowerWeekRateMapper {
    int insert(PowerWeekRate record);

    int insertSelective(PowerWeekRate record);

    List<PowerWeekRate> queryPowerWeakRate(Long stationId);
}