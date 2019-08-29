package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationDevice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface PowerStationDeviceMapper {
    int deleteByPrimaryKey(Long deviceId);

    int insert(PowerStationDevice record);

    int insertSelective(PowerStationDevice record);

    PowerStationDevice selectByPrimaryKey(Long deviceId);

    int updateByPrimaryKeySelective(PowerStationDevice record);

    int updateByPrimaryKey(PowerStationDevice record);

    List<PowerStationDevice> selectByField(Map<String,Object> param);

    Long getStationDeviceCount(Map<String,Object> param);

}