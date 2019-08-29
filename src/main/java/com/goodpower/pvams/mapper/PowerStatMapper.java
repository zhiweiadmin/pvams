package com.goodpower.pvams.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 发电量统计Mapper
 */
@Repository
@Mapper
public interface PowerStatMapper {

    /**
     * 按年查询每个月的发电量
     * @param year
     * @return
     */
    List<Map<String,Object>> queryPowerGenerateStat(@Param(value="stationId")Long stationId,@Param(value="year")String year);

    /**
     * 查询发电量衰减率 近25年
     * @param
     * @return
     */
    List<Map<String,Object>> queryPowerWeakRate(@Param(value="stationId")Long stationId);

}
