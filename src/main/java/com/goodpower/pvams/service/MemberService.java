package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.PowerStationMemberMapper;
import com.goodpower.pvams.mapper.StationMemberImgMapper;
import com.goodpower.pvams.model.PowerStationMember;
import com.goodpower.pvams.model.StationMemberImg;
import com.google.common.collect.Lists;
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

    @Autowired
    StationMemberImgMapper stationMemberImg;

    public void addMember(PowerStationMember member){
        memberMapper.insert(member);
        if(member.getFileList() != null && !member.getFileList().isEmpty()){
            for(String url : member.getFileList()){
                Long stationId = member.getStationId();
                Long memberId = member.getMemberId();
                StationMemberImg memberImg = new StationMemberImg();
                memberImg.setImgUrl(url);
                memberImg.setMemberId(memberId);
                memberImg.setStationId(stationId);
                stationMemberImg.insert(memberImg);
            }
        }
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
        List<PowerStationMember> members = memberMapper.selectByField(param);
        for(PowerStationMember member : members){
            Map<String,Object> map = Maps.newHashMap();
            map.put("stationId",member.getStationId());
            map.put("memberId",member.getMemberId());
            List<StationMemberImg> imgs = stationMemberImg.selectByFields(map);
            List<String> fileList = Lists.newArrayList();
            List<Map<String,Object>> imgMapList = Lists.newArrayList();
            for(StationMemberImg memberImg : imgs){
                fileList.add(memberImg.getImgUrl());
                Map<String,Object> imgMap = Maps.newHashMap();
                imgMap.put("name",memberImg.getImgUrl());
                imgMap.put("url",memberImg.getImgUrl());
                imgMapList.add(imgMap);
            }
            member.setFileList(fileList);
            member.setImgList(imgMapList);
        }
        return members;
    }
}
