package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.CarTravelRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface CarTravelRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarTravelRecord record);

    int insertSelective(CarTravelRecord record);

    CarTravelRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarTravelRecord record);

    int updateByPrimaryKey(CarTravelRecord record);

    List<Map<String,Object>> selectByFields(Map<String,Object> map);

    int getCount(Map<String,Object> map);

}