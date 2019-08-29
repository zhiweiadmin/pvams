package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.GirdAccessFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface GirdAccessFileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GirdAccessFile record);

    int insertSelective(GirdAccessFile record);

    GirdAccessFile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GirdAccessFile record);

    int updateByPrimaryKey(GirdAccessFile record);

    List<GirdAccessFile> selectByStationId(Long stationId);
}