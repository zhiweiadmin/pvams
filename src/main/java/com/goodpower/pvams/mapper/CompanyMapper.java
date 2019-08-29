package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface CompanyMapper {
    int deleteByPrimaryKey(Long companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
}