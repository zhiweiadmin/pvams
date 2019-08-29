package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PowerStationMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(PowerStation record);

    int insertSelective(PowerStation record);

    PowerStation selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(PowerStation record);

    int updateByPrimaryKey(PowerStation record);

    int upsert(PowerStation record);
}