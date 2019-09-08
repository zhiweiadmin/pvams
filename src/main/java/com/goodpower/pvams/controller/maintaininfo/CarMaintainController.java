package com.goodpower.pvams.controller.maintaininfo;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.common.ResultMap;
import com.goodpower.pvams.model.CarTravelRecord;
import com.goodpower.pvams.service.CarMaintainService;
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
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("car")
public class CarMaintainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CarMaintainService carMaintainService;

    private static final Integer UN_CONFIRM = 0;

    private static final Integer CONFIRM = 1;

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

            JSONObject jsonObject =  carMaintainService.selectByFields(pageNo,pageSize,param);
            resultMap.setData(jsonObject).success().message("查询成功");
        }catch (Exception e){
            logger.error("请求失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    @PostMapping("/borrow")
    public ResultMap borrow(@RequestBody CarTravelRecord record){
        ResultMap resultMap = new ResultMap();
        try{
            if(record.getSubmitUserId() == null){
                return resultMap.fail().code(400).message("借出人不能为空");
            }
            if(record.getCarId() == null){
                return resultMap.fail().code(400).message("车辆id不能为空");
            }
            record.setSubmitTime(new Date());
            record.setCreateDttm(new Date());
            record.setConfirmStatus(UN_CONFIRM);
            //将汽车状态变更为未确认
            carMaintainService.addCarTravelRecord(record);
            carMaintainService.updatCarConfirmStatus(record.getCarId(),UN_CONFIRM);
            resultMap.success().message("请求成功");
        }catch (Exception e){
            logger.error("请求失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

    /**
     * 记录查询
     * @param carId
     * @return
     */
    @GetMapping("/queryRecord")
    public ResultMap queryRecord(Long userId,Long carId,Integer pageNo,Integer pageSize){
        ResultMap resultMap = new ResultMap();
        try{
            if(carId == null){
                return resultMap.fail().code(400).message("carId不能为空");
            }
            if(pageNo == null){
                pageNo = 1;
            }
            if(pageSize == null){
                pageSize = 20;
            }
            if(userId == null){
                return resultMap.fail().code(400).message("userId不能为空");
            }
            Map<String,Object> param = Maps.newHashMap();
            param.put("offset",(pageNo-1)*pageSize);
            param.put("limit",pageSize);
            param.put("carId",carId);
            param.put("userId",userId);
            JSONObject jsonObject = carMaintainService.queryCarTravelRecord(param);
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
            return resultMap.fail().code(400).message("请先选择电站!");
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        String name = "车辆信息";
        carMaintainService.createExcelTemplate(stationId,wb);
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
            if(stationId == null){
                return resultMap.fail().code(400).message("请先选择电站!");
            }
            String fileName = file.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                Workbook workbook;
                if (fileName.endsWith("xlsx")){
                    workbook = new XSSFWorkbook(file.getInputStream());
                    carMaintainService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else if(fileName.endsWith("xls")){
                    workbook = new HSSFWorkbook(file.getInputStream());
                    carMaintainService.saveExcelData(stationId,workbook);
                    resultMap.success().message("导入成功");
                }else{
                    resultMap.fail().message("文件格式错误").code(400);
                }
            }
        }catch (Exception e){
            if(e instanceof ParseException){
                logger.error("导入信息失败,转换错误",e);
                return resultMap.fail().message("导入失败,请检查文件格式是否正确!");
            }else{
                logger.error("导入信息失败",e);
                return resultMap.fail().message("导入失败!");
            }
        }
        return resultMap;
    }

    @GetMapping("/confirm")
    public ResultMap confirm(Long userId,Long id,Integer confirmStatus){
        ResultMap resultMap = new ResultMap();
        try{
            carMaintainService.confirm(userId,id,confirmStatus);
            resultMap.success().message("成功");
        }catch (Exception e){
            logger.error("确认失败",e);
            resultMap.fail().message(e.getMessage());
        }
        return resultMap;
    }

}
