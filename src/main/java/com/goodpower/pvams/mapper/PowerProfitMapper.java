package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerProfit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface PowerProfitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PowerProfit record);

    int insertSelective(PowerProfit record);

    PowerProfit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PowerProfit record);

    int updateByPrimaryKey(PowerProfit record);

    List<Map<String,Object>> selectByFields(Map<String,Object> param);

    List<Map<String,Object>> getYearMonthStat(Map<String,Object> param);

}