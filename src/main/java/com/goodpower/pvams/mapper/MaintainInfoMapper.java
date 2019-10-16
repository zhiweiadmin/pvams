package com.goodpower.pvams.mapper;

import com.goodpower.pvams.model.MaintainInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Mapper
public interface MaintainInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaintainInfo record);

    int insertSelective(MaintainInfo record);

    MaintainInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaintainInfo record);

    int updateByPrimaryKey(MaintainInfo record);

    int updateStatus(Map<String,Object> param);

    List<MaintainInfo> selectByFields(Map<String,Object> param);

    int getCount(Map<String,Object> param);

    List<Map<String,Object>> getPresenter(Map<String,Object> param);
}