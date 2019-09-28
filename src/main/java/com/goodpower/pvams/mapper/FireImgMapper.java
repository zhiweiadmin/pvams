package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.FireImg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface FireImgMapper {
    int deleteByPrimaryKey(Long stationId);

    int insert(FireImg record);

    int insertSelective(FireImg record);

    FireImg selectByPrimaryKey(Long stationId);

    int updateByPrimaryKeySelective(FireImg record);

    int updateByPrimaryKey(FireImg record);
}