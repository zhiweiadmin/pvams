package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.StationMemberImg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface StationMemberImgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StationMemberImg record);

    int insertSelective(StationMemberImg record);

    StationMemberImg selectByPrimaryKey(Long id);

    List<StationMemberImg> selectByFields(Map<String,Object> param);

    int updateByPrimaryKeySelective(StationMemberImg record);

    int updateByPrimaryKey(StationMemberImg record);
}