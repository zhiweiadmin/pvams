package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.Province;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface ProvinceMapper {
    Province selectByPrimaryKey(Long provinceId);

    List<Province> selectByFields(Map<String,Object> map);
}