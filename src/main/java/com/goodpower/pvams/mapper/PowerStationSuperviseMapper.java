package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationSupervise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PowerStationSuperviseMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(PowerStationSupervise record);

    int insertSelective(PowerStationSupervise record);

    PowerStationSupervise selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(PowerStationSupervise record);

    int updateByPrimaryKey(PowerStationSupervise record);

    int upsert(PowerStationSupervise record);
}