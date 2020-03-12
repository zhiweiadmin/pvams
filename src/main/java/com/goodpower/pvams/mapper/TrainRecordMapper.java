package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.TrainPlanDetailFile;
import com.goodpower.pvams.model.TrainRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface TrainRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TrainRecord record);

    int insertSelective(TrainRecord record);

    TrainRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TrainRecord record);

    int updateByPrimaryKey(TrainRecord record);

    List<TrainRecord> selectByFields(Map<String,Object> param);
}