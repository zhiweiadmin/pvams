package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.FireCheckRecord;
import com.goodpower.pvams.model.PowerStation;
import com.goodpower.pvams.service.FireMaintainService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@RestController
@RequestMapping("fire")
public class FireMaintainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FireMaintainService fireMaintainService;

    @GetMapping("/query")
    public ResultMap query(Long stationId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(stationId == null){
                return resultMap.fail().code(400).message("stationId不能为空");
            }
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null){
                pageSize = 20;
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("stationId",stationId);

            JSONObject jsonObject =  fireMaintainService.selectByFields(param);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/check")
    public ResultMap check(@RequestBody FireCheckRecord checkRecord){
        ResultMap resultMap = new ResultMap();
        try{
            fireMaintainService.addCheckRecord(checkRecord);
            resultMap.success().message("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/confirm")
    public ResultMap confirm(Long userId,Integer id,Integer confirmStatus){
        ResultMap resultMap = new ResultMap();
        try{
            fireMaintainService.confirm(userId,id,confirmStatus);
            resultMap.success().message("成功");
        }catch (Exception e){
            logger.error("确认失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @GetMapping("/queryRecord")
    public ResultMap queryRecord(Long fireId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null) {
                pageSize = 20;
            }
            if(fireId == null){
                return resultMap.fail().code(400).message("fireId不能为空");
            }

            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("fireId",fireId);
            JSONObject jsonObject= fireMaintainService.queryFireRecord(param);
            resultMap.success().message("请求成功").setData(jsonObject);
        }catch (Exception e){
            logger.error("请求失败",e);
            resultMap.fail().message(e.getMessage());
        }

        return resultMap;
    }

    @GetMapping("/export/{stationId}")
    public ResultMap export(@PathVariable Long stationId, HttpServletResponse response) throws IOException {
        ResultMap resultMap = new ResultMap();
        if(stationId == null){
            return resultMap.fail().code(400).message("电站id不能为空");
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        String name = "消防器材维护模板";
        fireMaintainService.createFireMaintainTmp(stationId,wb);
        OutputStream output = response.getOutputStream();
        response.reset();
        String fileName = name+".xls";
        response.setContentType("application/msexcel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));
        wb.write(output);
        output.close();
        return null;
    }

    @PostMapping("/import/{stationId}")
    public ResultMap upload(@PathVariable Long stationId,@RequestParam("file") MultipartFile file){
        ResultMap resultMap = new ResultMap();
        try{
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                Workbook workbook;
                if (fileName.endsWith("xlsx")){
                    workbook = new XSSFWorkbook(file.getInputStream());
                    fireMaintainService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    fireMaintainService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else{
                    resultMap.fail().message("文件格式错误").code(400);
                }
            }
        }catch (Exception e){
            resultMap.success().message("导入失败");
        }
        return resultMap;
    }

}
