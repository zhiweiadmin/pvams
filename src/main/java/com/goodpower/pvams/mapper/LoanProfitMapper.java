package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.LoanProfit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface LoanProfitMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoanProfit record);

    int insertSelective(LoanProfit record);

    LoanProfit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanProfit record);

    int updateByPrimaryKey(LoanProfit record);

    List<LoanProfit> selectByFields(Map<String,Object> param);
}