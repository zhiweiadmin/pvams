package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.City;
import com.goodpower.pvams.model.County;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface CityMapper {
    int deleteByPrimaryKey(Long cityId);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Long cityId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    List<City> selectByFields(Map<String,Object> param);
}