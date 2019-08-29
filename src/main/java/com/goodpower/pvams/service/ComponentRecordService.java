package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.ComponentRecordMapper;
import com.goodpower.pvams.model.ComponentRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentRecordService {

    @Autowired
    ComponentRecordMapper recordMapper;

    public void add(ComponentRecord componentRecord){
        recordMapper.insert(componentRecord);
    }

    public void confirm(Long recordId,Integer status){
        ComponentRecord record = new ComponentRecord();
        record.setId(recordId);
        record.setStatus(status);
        recordMapper.updateByPrimaryKeySelective(record);
    }

}
