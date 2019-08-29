package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.DeviceMaintainDetailFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface DeviceMaintainDetailFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceMaintainDetailFile record);

    int insertSelective(DeviceMaintainDetailFile record);

    DeviceMaintainDetailFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceMaintainDetailFile record);

    int updateByPrimaryKey(DeviceMaintainDetailFile record);

    List<DeviceMaintainDetailFile> selectByFields(Map<String,Object> param);
}