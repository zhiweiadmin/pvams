package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.CarMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface CarMaintainMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CarMaintain record);

    int insertSelective(CarMaintain record);

    CarMaintain selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CarMaintain record);

    int updateByPrimaryKey(CarMaintain record);

    List<CarMaintain> selectByFields(Map<String,Object> map);

    int getCount(Map<String,Object> map);
}