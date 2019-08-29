package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationBuildInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PowerStationBuildInfoMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(PowerStationBuildInfo record);

    int insertSelective(PowerStationBuildInfo record);

    PowerStationBuildInfo selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(PowerStationBuildInfo record);

    int updateByPrimaryKey(PowerStationBuildInfo record);

    int upsert(PowerStationBuildInfo record);
}