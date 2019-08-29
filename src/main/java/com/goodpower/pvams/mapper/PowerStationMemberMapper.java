package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationMember;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface PowerStationMemberMapper {
    int deleteByPrimaryKey(Long memberId);

    int insert(PowerStationMember record);

    int insertSelective(PowerStationMember record);

    PowerStationMember selectByPrimaryKey(Long memberId);

    int updateByPrimaryKeySelective(PowerStationMember record);

    int updateByPrimaryKey(PowerStationMember record);

    List<PowerStationMember> selectByField(Map<String,Object> param);

}