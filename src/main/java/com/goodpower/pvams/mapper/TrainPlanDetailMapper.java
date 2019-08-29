package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.TrainPlanDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface TrainPlanDetailMapper {
    int deleteByPrimaryKey(Long trainDetailId);

    int insert(TrainPlanDetail record);

    int insertSelective(TrainPlanDetail record);

    TrainPlanDetail selectByPrimaryKey(Long trainDetailId);

    int updateByPrimaryKeySelective(TrainPlanDetail record);

    int updateByPrimaryKey(TrainPlanDetail record);

    List<TrainPlanDetail> selectByFields(Map<String,Object> param);

    int deleteByFields(Map<String,Object> param);
}