package com.goodpower.pvams.controller.power;


import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.PowerStationMember;
import com.goodpower.pvams.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MemberService memberService;

    @PostMapping("/add")
    public ResultMap add(@RequestBody PowerStationMember member){
        ResultMap resultMap = new ResultMap();
        if(StringUtils.isBlank(member.getRealName())){
            return resultMap.fail().message("姓名不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getIdCard())){
            return resultMap.fail().message("身份证号码不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getMaintainCompany())){
            return resultMap.fail().message("运维公司不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getUserPic())){
            return resultMap.fail().message("照片不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getCertificatePic())){
            return resultMap.fail().message("证书不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getPosition())){
            return resultMap.fail().message("职位不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getCredential())){
            return resultMap.fail().message("资质不能为空").code(400);
        }
        try{
            memberService.addMember(member);
            resultMap.success().code(200).message("添加成功");
        }catch (Exception e){
            logger.error("添加人员失败",e);
            resultMap.success().code(200).message("添加失败");
        }
        return resultMap;
    }

    @PostMapping("/update")
    public ResultMap update(@RequestBody PowerStationMember member){
        ResultMap resultMap = new ResultMap();
        if(member.getMemberId() == null){
            return resultMap.fail().message("memberId不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getRealName())){
            return resultMap.fail().message("姓名不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getIdCard())){
            return resultMap.fail().message("身份证号码不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getMaintainCompany())){
            return resultMap.fail().message("运维公司不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getUserPic())){
            return resultMap.fail().message("照片不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getCertificatePic())){
            return resultMap.fail().message("证书不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getPosition())){
            return resultMap.fail().message("职位不能为空").code(400);
        }
        if(StringUtils.isBlank(member.getCredential())){
            return resultMap.fail().message("资质不能为空").code(400);
        }
        try{
            memberService.updateMember(member);
            resultMap.success().code(200).message("更新成功");
        }catch (Exception e){
            logger.error("更新人员失败",e);
            resultMap.success().code(400).message("更新失败");
        }
        return resultMap;
    }

    @GetMapping("/delete")
    public ResultMap delete(@RequestParam Long memberId){
        ResultMap resultMap = new ResultMap();
        try{
            memberService.deleteMember(memberId);
            resultMap.success().code(200).message("删除成功");
        }catch (Exception e){
            logger.error("删除人员失败",e);
            resultMap.success().code(400).message("删除失败");
        }
        return resultMap;
    }

    @GetMapping("/query")
    public ResultMap query(@RequestParam Long stationId,Integer page,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            List<PowerStationMember> memberList = memberService.query(stationId,page,pageSize);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list",memberList);
            resultMap.setData(jsonObject).success().message("查询成功").code(200);
        }catch (Exception e){
            logger.error("查询失败人员信息",e);
            resultMap.success().code(400).message("查询失败");
        }
        return resultMap;
    }

}
