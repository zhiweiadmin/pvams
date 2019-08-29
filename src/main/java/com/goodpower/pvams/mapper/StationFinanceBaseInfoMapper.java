package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationFinanceBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface StationFinanceBaseInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationFinanceBaseInfo record);

    int insertSelective(StationFinanceBaseInfo record);

    StationFinanceBaseInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StationFinanceBaseInfo record);

    int updateByPrimaryKey(StationFinanceBaseInfo record);

    StationFinanceBaseInfo selectByStationId(Long stationId);
}