package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.DeviceMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface DeviceMaintainMapper {
    int deleteByPrimaryKey(Long maintainId);

    int insert(DeviceMaintain record);

    int insertSelective(DeviceMaintain record);

    DeviceMaintain selectByPrimaryKey(Long maintainId);

    int updateByPrimaryKeySelective(DeviceMaintain record);

    int updateByPrimaryKey(DeviceMaintain record);

    Map<String,Object> getMaintainPlan(Long maintainId,Integer week);

    List<DeviceMaintain> selectByField(Map<String,Object> param);

}