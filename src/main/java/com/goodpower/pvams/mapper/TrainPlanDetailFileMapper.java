package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.TrainPlanDetailFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface TrainPlanDetailFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrainPlanDetailFile record);

    int insertSelective(TrainPlanDetailFile record);

    TrainPlanDetailFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrainPlanDetailFile record);

    int updateByPrimaryKey(TrainPlanDetailFile record);

    List<TrainPlanDetailFile> selectByFields(Map<String,Object> param);
}