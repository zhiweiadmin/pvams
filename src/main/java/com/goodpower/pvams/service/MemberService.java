package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.PowerStationMemberMapper;
import com.goodpower.pvams.model.PowerStationMember;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    @Autowired
    PowerStationMemberMapper memberMapper;

    public void addMember(PowerStationMember member){
        memberMapper.insert(member);
    }

    public void updateMember(PowerStationMember member){
        if(member == null || member.getMemberId() == null){
            return;
        }
        memberMapper.updateByPrimaryKeySelective(member);
    }

    public void deleteMember(Long memberId){
        memberMapper.deleteByPrimaryKey(memberId);
    }

    public List<PowerStationMember> query(Long stationId, Integer page, Integer pageSize){
        Map<String,Object> param = Maps.newHashMap();
        if(page != null && pageSize != null && page >= 1){
            param.put("index",(page - 1)*pageSize);
            param.put("limit",pageSize);
        }
        param.put("stationId",stationId);
        return memberMapper.selectByField(param);
    }


}
