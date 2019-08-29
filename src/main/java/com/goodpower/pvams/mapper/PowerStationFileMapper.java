package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.PowerStationFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface PowerStationFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PowerStationFile record);

    int insertSelective(PowerStationFile record);

    PowerStationFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PowerStationFile record);

    int updateByPrimaryKey(PowerStationFile record);

    List<PowerStationFile> selectByStationId(Long stationId);

}