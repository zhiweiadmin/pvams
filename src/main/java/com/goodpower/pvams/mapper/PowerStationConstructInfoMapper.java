package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationConstructInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface PowerStationConstructInfoMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(PowerStationConstructInfo record);

    int insertSelective(PowerStationConstructInfo record);

    PowerStationConstructInfo selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(PowerStationConstructInfo record);

    int updateByPrimaryKey(PowerStationConstructInfo record);

    int upsert(PowerStationConstructInfo record);
}