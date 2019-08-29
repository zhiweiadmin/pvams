package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.SafePlanDetailFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface SafePlanDetailFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SafePlanDetailFile record);

    int insertSelective(SafePlanDetailFile record);

    SafePlanDetailFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SafePlanDetailFile record);

    int updateByPrimaryKey(SafePlanDetailFile record);

    List<SafePlanDetailFile> selectByFields(Map<String,Object> param);
}