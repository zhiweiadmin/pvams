package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.SafePlanDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface SafePlanDetailMapper {
    int deleteByPrimaryKey(Long safeDetailId);

    int insert(SafePlanDetail record);

    int insertSelective(SafePlanDetail record);

    SafePlanDetail selectByPrimaryKey(Long safeDetailId);

    int updateByPrimaryKeySelective(SafePlanDetail record);

    int updateByPrimaryKey(SafePlanDetail record);

    List<SafePlanDetail> selectByFields(Map<String,Object> map);

    int deleteByFields(Map<String,Object> map);
}