package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.FireCheckRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface FireCheckRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FireCheckRecord record);

    int insertSelective(FireCheckRecord record);

    FireCheckRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FireCheckRecord record);

    int updateByPrimaryKey(FireCheckRecord record);

    List<Map<String,Object>> selectByFields(Map<String,Object> param);

    int getCount(Map<String,Object> param);
}
