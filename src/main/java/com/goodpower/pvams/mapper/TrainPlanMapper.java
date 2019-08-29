package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.TrainPlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface TrainPlanMapper {
    int deleteByPrimaryKey(Long trainId);

    int insert(TrainPlan record);

    int insertSelective(TrainPlan record);

    TrainPlan selectByPrimaryKey(Long trainId);

    int updateByPrimaryKeySelective(TrainPlan record);

    int updateByPrimaryKey(TrainPlan record);

    List<TrainPlan> selectByFields(Map<String,Object> param);

}