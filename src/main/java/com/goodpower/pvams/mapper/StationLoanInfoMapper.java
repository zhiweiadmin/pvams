package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationLoanInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface StationLoanInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationLoanInfo record);

    int insertSelective(StationLoanInfo record);

    StationLoanInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StationLoanInfo record);

    int updateByPrimaryKey(StationLoanInfo record);

    List<StationLoanInfo> selectByFields(Map<String,Object> param);
}