package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.FireMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface FireMaintainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FireMaintain record);

    int insertSelective(FireMaintain record);

    FireMaintain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FireMaintain record);

    int updateByPrimaryKey(FireMaintain record);

    List<FireMaintain> selectByFields(Map<String,Object> map);

    int getCount(Map<String,Object> map);

}