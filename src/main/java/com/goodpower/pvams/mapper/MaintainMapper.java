package com.goodpower.pvams.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface MaintainMapper {

    void addMaintainPlan(@Param(value="param")Map<String,Object> param);

    void updateMaintainPlan(@Param(value="param")Map<String,Object> param);

    Map<String,Object> getMaintainPlan(@Param(value="maintainId")Long maintainId);

    void approveMaintainPlan(@Param(value="param")Map<String,Object> param);

}
