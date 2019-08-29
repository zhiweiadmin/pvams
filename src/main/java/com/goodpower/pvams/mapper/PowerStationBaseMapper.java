package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationBase;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PowerStationBaseMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(PowerStationBase record);

    int insertSelective(PowerStationBase record);

    PowerStationBase selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(PowerStationBase record);

    int updateByPrimaryKey(PowerStationBase record);

    int upsert(PowerStationBase record);
}