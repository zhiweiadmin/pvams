package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationGirdInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PowerStationGirdInfoMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(PowerStationGirdInfo record);

    int insertSelective(PowerStationGirdInfo record);

    PowerStationGirdInfo selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(PowerStationGirdInfo record);

    int updateByPrimaryKey(PowerStationGirdInfo record);
}