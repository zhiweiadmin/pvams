package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.MaintainInfoMapper;
import com.goodpower.pvams.model.MaintainInfo;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MaintainInfoService {

    @Autowired
    MaintainInfoMapper maintainInfoMapper;

    private final static Integer NEW_ADD = 0;

    public void add(MaintainInfo maintainInfo){
        maintainInfo.setStatus(NEW_ADD);
        maintainInfoMapper.insert(maintainInfo);
    }

    public void update(MaintainInfo maintainInfo){
        maintainInfoMapper.updateByPrimaryKeySelective(maintainInfo);
    }

    public void delete(Long maintainInfoId){
        maintainInfoMapper.deleteByPrimaryKey(maintainInfoId);
    }

    public List<MaintainInfo> selectByFields(Map<String,Object> param){
        return maintainInfoMapper.selectByFields(param);
    }

    public void approve(Long id,Integer status){
        MaintainInfo info = new MaintainInfo();
        info.setId(id);
        info.setStatus(status);
        maintainInfoMapper.updateByPrimaryKeySelective(info);
    }

    public List<Map<String,Object>> getPresenter(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return maintainInfoMapper.getPresenter(param);
    }


}
