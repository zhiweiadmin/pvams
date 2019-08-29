package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.DeviceMaintainDetail;
import com.goodpower.pvams.model.DeviceMaintainDetailKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface DeviceMaintainDetailMapper {
    int deleteByPrimaryKey(DeviceMaintainDetailKey key);

    int insert(DeviceMaintainDetail record);

    int insertSelective(DeviceMaintainDetail record);

    DeviceMaintainDetail selectByPrimaryKey(DeviceMaintainDetailKey key);

    int updateByPrimaryKeySelective(DeviceMaintainDetail record);

    int updateByPrimaryKey(DeviceMaintainDetail record);

    List<DeviceMaintainDetail> selectByField(Map<String,Object> map);

    int deleteByFields(Map<String,Object> map);
}