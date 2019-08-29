package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.SafePlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface SafePlanMapper {
    int deleteByPrimaryKey(Long safeId);

    int insert(SafePlan record);

    int insertSelective(SafePlan record);

    SafePlan selectByPrimaryKey(Long safeId);

    int updateByPrimaryKeySelective(SafePlan record);

    int updateByPrimaryKey(SafePlan record);

    List<SafePlan> selectByFields(Map<String,Object> param);
}