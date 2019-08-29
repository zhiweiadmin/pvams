package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.County;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface CountyMapper {
    int deleteByPrimaryKey(Long countyId);

    int insert(County record);

    int insertSelective(County record);

    County selectByPrimaryKey(Long countyId);

    int updateByPrimaryKeySelective(County record);

    int updateByPrimaryKey(County record);

    List<County> selectByFields(Map<String,Object> param);
}