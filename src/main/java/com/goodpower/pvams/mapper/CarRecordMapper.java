package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.CarRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface CarRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarRecord record);

    int insertSelective(CarRecord record);

    CarRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarRecord record);

    int updateByPrimaryKey(CarRecord record);

    List<CarRecord> selectByFields(Map<String,Object> param);

    int getCount(Map<String,Object> param);

}