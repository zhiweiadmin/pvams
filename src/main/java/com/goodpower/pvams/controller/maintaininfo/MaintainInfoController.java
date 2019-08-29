package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.MaintainInfo;
import com.goodpower.pvams.service.MaintainInfoService;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("info")
public class MaintainInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MaintainInfoService maintainInfoService;

    @PostMapping("/add")
    public ResultMap add(@RequestBody MaintainInfo maintainInfo){
        ResultMap resultMap = new ResultMap();
        try{
            if(maintainInfo.getPresentUserId() == null){
                return resultMap.fail().code(400).message("提报人不能为空");
            }
            if(maintainInfo.getPresentDate() == null){
                return resultMap.fail().code(400).message("提报时间不能为空");
            }
            if(maintainInfo.getStartDate() == null){
                return resultMap.fail().code(400).message("开始时间不能为空");
            }
            if(maintainInfo.getEndDate() == null){
                return resultMap.fail().code(400).message("结束时间不能为空");
            }
            if(maintainInfo.getSubject() == null){
                return resultMap.fail().code(400).message("主题不能为空");
            }
            if(maintainInfo.getInfoDetail() == null){
                return resultMap.fail().code(400).message("信息详情不能为空");
            }
            if(maintainInfo.getType() == null){
                return resultMap.fail().code(400).message("信息类型不能为空");
            }
            maintainInfoService.add(maintainInfo);
            resultMap.success().message("添加成功");
        }catch (Exception e){
            logger.error("添加维护信息失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/update")
    public ResultMap update(@RequestBody MaintainInfo maintainInfo){
        ResultMap resultMap = new ResultMap();
        try{
            if(maintainInfo.getPresentUserId() == null){
                return resultMap.fail().code(400).message("提报人不能为空");
            }
            if(maintainInfo.getPresentDate() == null){
                return resultMap.fail().code(400).message("提报时间不能为空");
            }
            if(maintainInfo.getStartDate() == null){
                return resultMap.fail().code(400).message("开始时间不能为空");
            }
            if(maintainInfo.getEndDate() == null){
                return resultMap.fail().code(400).message("结束时间不能为空");
            }
            if(maintainInfo.getSubject() == null){
                return resultMap.fail().code(400).message("主题不能为空");
            }
            if(maintainInfo.getInfoDetail() == null){
                return resultMap.fail().code(400).message("信息详情不能为空");
            }
            if(maintainInfo.getType() == null){
                return resultMap.fail().code(400).message("信息类型不能为空");
            }
            maintainInfoService.update(maintainInfo);
            resultMap.success().message("更新成功");
        }catch (Exception e){
            logger.error("更新维护信息失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/delete/{maintainInfoId}")
    public ResultMap delete(@PathVariable Long maintainInfoId){
        ResultMap resultMap = new ResultMap();
        try{
            if(maintainInfoId == null){
                return resultMap.fail().code(400).message("id不能为空");
            }
            maintainInfoService.delete(maintainInfoId);
            resultMap.success().message("删除成功");
        }catch (Exception e){
            logger.error("删除维护信息失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/query")
    public ResultMap query(@RequestBody JSONObject request){
        ResultMap resultMap = new ResultMap();
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //check prop must
            Map<String,Object> param = Maps.newHashMap();
            if(request.getLong("presentUserId") != null){
                param.put("presentUserId",request.getLong("presentUserId"));
            }
            if(StringUtils.isNotBlank(request.getString("startDate"))){
                Date startDate = sdf.parse(request.getString("startDate"));
                param.put("startDate",sdf.format(startDate));
            }
            if(StringUtils.isNotBlank(request.getString("endDate"))){
                Date endDate = sdf.parse(request.getString("endDate"));
                Date endDateNew = DateUtil.getDay(endDate,1);
                param.put("endDate",sdf.format(endDateNew));
            }
            if(request.getInteger("status") != null){
                param.put("status",request.getInteger("status"));
            }
            if(request.getInteger("type") != null){
                param.put("type",request.getInteger("type"));
            }
            if(request.getLong("stationId") != null){
                param.put("stationId",request.getLong("stationId"));
            }
            Integer pageNo = request.getInteger("pageNo");
            Integer pageSize = request.getInteger("pageSize");
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null){
                pageSize = 20;
            }
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            List<MaintainInfo> infoList = maintainInfoService.selectByFields(param);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("infoList",infoList);
            resultMap.success().message("查询成功").setData(jsonObject);
        }catch (Exception e){
            logger.error("查询失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    /**
     *
     * @param id
     * @param status  0:新提报 1:已通过 2:已拒绝 3:已过期
     * @return
     */
    @GetMapping("/approve")
    public ResultMap approve(Long approveUserId,Long id,Integer status){
        ResultMap resultMap = new ResultMap();
        try{
            if(approveUserId == null){
                return resultMap.fail().code(400).message("审批人不能为空");
            }
            if(status == null){
                return resultMap.fail().code(400).message("审批状态不能为空");
            }
            maintainInfoService.approve(id,status);
            resultMap.success().message("操作成功");
        }catch (Exception e){
            logger.error("查询失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/resubmit")
    public ResultMap resubmit(@RequestBody MaintainInfo maintainInfo){
        ResultMap resultMap = new ResultMap();
        try{
            maintainInfo.setId(null);
            maintainInfoService.add(maintainInfo);
            resultMap.success().message("操作成功");
        }catch (Exception e){
            logger.error("操作失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/getPresenter/{stationId}")
    public ResultMap getPresenter(@PathVariable Long stationId){
        ResultMap resultMap = new ResultMap();
        try{
            List<Map<String,Object>> resultList = maintainInfoService.getPresenter(stationId);
            resultMap.success().message("操作成功").setData(resultList);
        }catch (Exception e){
            logger.error("操作失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

}
