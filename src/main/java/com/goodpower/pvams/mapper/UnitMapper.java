package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UnitMapper {

    List<Map<String,Object>> getUserByUnitId(Long unitId, Integer type);

    void addCompany(Company company);

}
