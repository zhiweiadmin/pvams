package com.goodpower.pvams.service;

import com.goodpower.pvams.mapper.PowerStationMapper;
import com.goodpower.pvams.model.PowerStation;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReadExcelStationInfo {

    @Autowired
    PowerStationMapper stationMapper;

    /**
     * 地面电站
     * @param sheet
     * @return
     */
    public void readGroundStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);//装机功率
        String gridTime = getSheetValue(sheet,2,1);//并网时间
        String projectAngle = getSheetValue(sheet,3,1);//项目倾角
        String arraySpace = getSheetValue(sheet,4,1);//阵列间距
        String gridPowerLevel = getSheetValue(sheet,5,1);//并网电压等级
        String gridNum = getSheetValue(sheet,6,1);//并网点数量
        String scale = getSheetValue(sheet,7,1);//电站容配比
        String lineLength = getSheetValue(sheet,8,1);//送出线路长度
        String roadType = getSheetValue(sheet,9,1);//路面类型
        String isCloseFramarea = getSheetValue(sheet,10,1);//是否于耕种区毗邻
        String isCorral = getSheetValue(sheet,11,1);//是否封闭围栏
        String isOutsidersPlant = getSheetValue(sheet,12,1);//是否有外来人员种植
        String isOutsidersAncestor = getSheetValue(sheet,13,1);//是否有外来人员种植
        String bottomLandDistance = getSheetValue(sheet,14,1);//组件底端距地面距离
        String clearWaterSource = getSheetValue(sheet,15,1);//清洗水源
        String weekStart = getSheetValue(sheet,16,1);//周初始
        String monthStart = getSheetValue(sheet,17,1);//月初始
        String installedCapacity = getSheetValue(sheet,18,1);//装机容量
        String gridCapacity = getSheetValue(sheet,19,1);//并网容量

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledCapacity(installedCapacity);
        station.setGridCapacity(gridCapacity);
        station.setInstalledPower(installedPower);
        station.setGridTime(gridTime);
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setGridPowerLevel(gridPowerLevel);
        station.setGridNum(gridNum);
        station.setScale(scale);
        station.setLineLength(lineLength);
        station.setRoadType(roadType);
        station.setIsCloseFramarea(isCloseFramarea);
        station.setIsCorral(isCorral);
        station.setIsOutsidersPlant(isOutsidersPlant);
        station.setIsOutsidersAncestor(isOutsidersAncestor);
        station.setBottomLandDistance(bottomLandDistance);
        station.setClearWaterSource(clearWaterSource);

        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }
        stationMapper.upsert(station);
    }

    /**
     * 农光电站
     * @param sheet
     * @return
     */
    public void readAgricultureStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);//装机功率
        String gridTime = getSheetValue(sheet,2,1);//并网时间
        String projectAngle = getSheetValue(sheet,3,1);//项目倾角
        String arraySpace = getSheetValue(sheet,4,1);//阵列间距
        String gridPowerLevel = getSheetValue(sheet,5,1);//并网电压等级
        String gridNum = getSheetValue(sheet,6,1);//并网点数量
        String scale = getSheetValue(sheet,7,1);//电站容配比
        String lineLength = getSheetValue(sheet,8,1);//送出线路长度
        String roadType = getSheetValue(sheet,9,1);//路面类型
        String isCloseFramarea = getSheetValue(sheet,10,1);//是否于耕种区毗邻
        String isCorral = getSheetValue(sheet,11,1);//是否封闭围栏
        String isOutsidersPlant = getSheetValue(sheet,12,1);//是否有外来人员种植
        String isOutsidersAncestor = getSheetValue(sheet,13,1);//是否有外来人员种植
        String bottomLandDistance = getSheetValue(sheet,14,1);//组件底端距地面距离
        String isAnimal = getSheetValue(sheet,15,1);//组件底端距地面距离
        String clearWaterSource = getSheetValue(sheet,16,1);
        String weekStart = getSheetValue(sheet,17,1);//周初始
        String monthStart = getSheetValue(sheet,18,1);//月初始
        String installedCapacity = getSheetValue(sheet,19,1);//装机容量
        String gridCapacity = getSheetValue(sheet,20,1);//并网容量

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledCapacity(installedCapacity);
        station.setGridCapacity(gridCapacity);
        station.setInstalledPower(installedPower);
        station.setGridTime(gridTime);
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setGridPowerLevel(gridPowerLevel);
        station.setGridNum(gridNum);
        station.setScale(scale);
        station.setLineLength(lineLength);
        station.setRoadType(roadType);
        station.setIsCloseFramarea(isCloseFramarea);
        station.setIsOutsidersPlant(isOutsidersPlant);
        station.setIsOutsidersAncestor(isOutsidersAncestor);
        station.setBottomLandDistance(bottomLandDistance);
        station.setIsAnimal(isAnimal);
        station.setClearWaterSource(clearWaterSource);
        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }
        stationMapper.upsert(station);
    }

    /**
     * 渔光电站
     * @param sheet
     * @return
     */
    public void readFishStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);//装机功率
        String gridTime = getSheetValue(sheet,2,1);//并网时间
        String projectAngle = getSheetValue(sheet,3,1);//项目倾角
        String arraySpace = getSheetValue(sheet,4,1);//阵列间距
        String gridPowerLevel = getSheetValue(sheet,5,1);//并网电压等级
        String gridNum = getSheetValue(sheet,6,1);//并网点数量
        String scale = getSheetValue(sheet,7,1);//电站容配比
        String lineLength = getSheetValue(sheet,8,1);//送出线路长度
        String roadType = getSheetValue(sheet,9,1);//路面类型

        String pondNum = getSheetValue(sheet,10,1);//是否于耕种区毗邻
        String pondAvgDepth = getSheetValue(sheet,11,1);//水塘平均深度
        String bottomWaterDistance = getSheetValue(sheet,12,1);//组件底端距地面距离
        String isAquatic = getSheetValue(sheet,13,1);//组件底端距地面距离
        String clearWaterSource = getSheetValue(sheet,14,1);//清洗水源
        String weekStart = getSheetValue(sheet,15,1);//周初始
        String monthStart = getSheetValue(sheet,16,1);//月初始
        String installedCapacity = getSheetValue(sheet,17,1);//装机容量
        String gridCapacity = getSheetValue(sheet,18,1);//并网容量

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledCapacity(installedCapacity);
        station.setGridCapacity(gridCapacity);
        station.setInstalledPower(installedPower);
        station.setGridTime(gridTime);
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setGridPowerLevel(gridPowerLevel);
        if(StringUtils.isNotBlank(gridNum)){
            station.setGridNum(gridNum);
        }
        station.setGridNum(gridNum);
        station.setScale(scale);
        station.setLineLength(lineLength);
        station.setRoadType(roadType);
        station.setPondNum(pondNum);
        station.setPondAvgDepth(pondAvgDepth);
        station.setBottomWaterDistance(bottomWaterDistance);
        station.setIsAquatic(isAquatic);
        station.setClearWaterSource(clearWaterSource);

        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }
        stationMapper.upsert(station);
    }

    /**
     * 漂浮电站
     * @param sheet
     * @return
     */
    public void readFloatStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);//装机功率
        String gridTime = getSheetValue(sheet,2,1);//并网时间
        String projectAngle = getSheetValue(sheet,3,1);//项目倾角
        String arraySpace = getSheetValue(sheet,4,1);//阵列间距
        String gridPowerLevel = getSheetValue(sheet,5,1);//并网电压等级
        String gridNum = getSheetValue(sheet,6,1);//并网点数量
        String scale = getSheetValue(sheet,7,1);//电站容配比
        String lineLength = getSheetValue(sheet,8,1);//送出线路长度
        String roadType = getSheetValue(sheet,9,1);//路面类型

        String pondNum = getSheetValue(sheet,10,1);//是否于耕种区毗邻
        String pondAvgDepth = getSheetValue(sheet,11,1);//水塘平均深度
        String floatType = getSheetValue(sheet,12,1);//水塘平均深度
        String fixedType = getSheetValue(sheet,13,1);//水塘平均深度

        String bottomWaterDistance = getSheetValue(sheet,14,1);//组件底端距水面距离
        String isAquatic = getSheetValue(sheet,15,1);//是否有水产养殖
        String clearWaterSource = getSheetValue(sheet,16,1);//清洗水源
        String weekStart = getSheetValue(sheet,17,1);//周初始
        String monthStart = getSheetValue(sheet,18,1);//月初始
        String installedCapacity = getSheetValue(sheet,19,1);//装机容量
        String gridCapacity = getSheetValue(sheet,20,1);//并网容量

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledCapacity(installedCapacity);
        station.setGridCapacity(gridCapacity);
        station.setInstalledPower(installedPower);
        station.setGridTime(gridTime);
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setGridPowerLevel(gridPowerLevel);
        station.setGridNum(gridNum);
        station.setScale(scale);
        station.setLineLength(lineLength);
        station.setRoadType(roadType);
        station.setPondNum(pondNum);
        station.setPondAvgDepth(pondAvgDepth);
        station.setBottomWaterDistance(bottomWaterDistance);
        station.setIsAquatic(isAquatic);
        station.setClearWaterSource(clearWaterSource);
        station.setFloatType(floatType);
        station.setFixedType(fixedType);

        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }
        stationMapper.upsert(station);
    }

    /**
     * 分布式电站
     * @param sheet
     * @return
     */
    public void readDistributedStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);
        String gridTime = getSheetValue(sheet,2,1);
        String projectAngle = getSheetValue(sheet,3,1);
        String arraySpace = getSheetValue(sheet,4,1);
        String gridPowerLevel = getSheetValue(sheet,5,1);
        String gridNum = getSheetValue(sheet,6,1);
        String scale = getSheetValue(sheet,7,1);
        String roofType = getSheetValue(sheet,8,1);
        String isDaylight = getSheetValue(sheet,9,1);
        String upCondition = getSheetValue(sheet,10,1);
        String roofNum = getSheetValue(sheet,11,1);
        String distributionPointNum = getSheetValue(sheet,12,1);
        String clearWaterSourceAccess = getSheetValue(sheet,13,1);
        String weekStart = getSheetValue(sheet,14,1);
        String monthStart = getSheetValue(sheet,15,1);
        String installedCapacity = getSheetValue(sheet,16,1);//装机容量
        String gridCapacity = getSheetValue(sheet,17,1);//并网容量

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledPower(installedPower);
        station.setGridCapacity(gridCapacity);
        station.setInstalledCapacity(installedCapacity);
        if(StringUtils.isNotBlank(gridTime)){
            station.setGridTime(gridTime);
        }
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setGridPowerLevel(gridPowerLevel);
        station.setGridNum(gridNum);
        station.setScale(scale);
        station.setRoofType(roofType);
        station.setIsDaylight(isDaylight);
        station.setUpCondition(upCondition);
        station.setRoofNum(roofNum);
        station.setDistributionPointNum(distributionPointNum);
        station.setClearWaterSourceAccess(clearWaterSourceAccess);
        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }

        stationMapper.upsert(station);
    }

    /**
     * 扶贫电站
     * @param sheet
     * @return
     */
    public void readPoorStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);//装机功率
        String gridTime = getSheetValue(sheet,2,1);//并网时间
        String projectAngle = getSheetValue(sheet,3,1);//项目倾角
        String arraySpace = getSheetValue(sheet,4,1);//阵列间距
        String gridPowerLevel = getSheetValue(sheet,5,1);//并网电压等级
        String gridNum = getSheetValue(sheet,6,1);//并网点数量
        String scale = getSheetValue(sheet,7,1);//电站容配比
        String roadType = getSheetValue(sheet,8,1);//路面类型
        String isCloseFramarea = getSheetValue(sheet,9,1);//是否于耕种区毗邻
        String isCorral = getSheetValue(sheet,10,1);//是否封闭围栏
        String isOutsidersPlant = getSheetValue(sheet,11,1);//是否有外来人员种植
        String isOutsidersAncestor = getSheetValue(sheet,12,1);//是否有外来人员祭祖
        String bottomLandDistance = getSheetValue(sheet,13,1);//是否有外来人员祭祖
        String distributionPointNum = getSheetValue(sheet,14,1);
        String weekStart = getSheetValue(sheet,15,1);//周初始
        String monthStart = getSheetValue(sheet,16,1);//月初始

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledPower(installedPower);
        station.setGridTime(gridTime);
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setGridPowerLevel(gridPowerLevel);
        station.setGridNum(gridNum);
        station.setScale(scale);
        station.setRoadType(roadType);
        station.setIsCloseFramarea(isCloseFramarea);
        station.setIsCorral(isCorral);
        station.setIsOutsidersPlant(isOutsidersPlant);
        station.setIsOutsidersAncestor(isOutsidersAncestor);
        station.setBottomLandDistance(bottomLandDistance);
        station.setDistributionPointNum(distributionPointNum);
        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }
        stationMapper.upsert(station);
    }

    public void readUserDistributedStation(Sheet sheet,Long stationId){
        String installedPower = getSheetValue(sheet,1,1);
        String gridTime = getSheetValue(sheet,2,1);
        String projectAngle = getSheetValue(sheet,3,1);
        String arraySpace = getSheetValue(sheet,4,1);
        String scale = getSheetValue(sheet,5,1);
        String roofType = getSheetValue(sheet,6,1);
        String isDaylight = getSheetValue(sheet,7,1);
        String upCondition = getSheetValue(sheet,8,1);
        String roofNum = getSheetValue(sheet,9,1);
        String distributionPointNum = getSheetValue(sheet,10,1);
        String clearWaterSourceAccess = getSheetValue(sheet,11,1);
        String weekStart = getSheetValue(sheet,12,1);
        String monthStart = getSheetValue(sheet,13,1);
        String installedCapacity = getSheetValue(sheet,14,1);//装机容量
        String gridCapacity = getSheetValue(sheet,15,1);//并网容量

        PowerStation station = new PowerStation();
        station.setStationId(stationId);
        station.setInstalledPower(installedPower);
        station.setInstalledCapacity(installedCapacity);
        station.setGridCapacity(gridCapacity);
        if(StringUtils.isNotBlank(gridTime)){
            station.setGridTime(gridTime);
        }
        station.setProjectAngle(projectAngle);
        station.setArraySpace(arraySpace);
        station.setScale(scale);
        station.setRoofType(roofType);
        station.setIsDaylight(isDaylight);
        station.setUpCondition(upCondition);
        station.setRoofNum(roofNum);
        station.setDistributionPointNum(distributionPointNum);
        station.setClearWaterSourceAccess(clearWaterSourceAccess);
        if(StringUtils.isNotBlank(weekStart)){
            station.setWeekStart(Integer.parseInt(weekStart));
        }
        if(StringUtils.isNotBlank(monthStart)){
            station.setMonthStart(Integer.parseInt(monthStart));
        }
        stationMapper.upsert(station);
    }

    public String getSheetValue(Sheet sheet,int rowNum,int colNum){
        Row row = sheet.getRow(rowNum);
        DataFormatter formatter = new DataFormatter();
        if(row != null){
            Cell cell = row.getCell(colNum);
            if(cell != null){
                if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    short format = cell.getCellStyle().getDataFormat();
                    System.out.println("format:"+format+";;;;;value:"+cell.getNumericCellValue());
                    SimpleDateFormat sdf = null;
                    if (format == 14 || format == 31 || format == 57 || format == 58
                            || (176<=format && format<=178) || (182<=format && format<=196)
                            || (210<=format && format<=213) || (208==format ) ) { // 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                        sdf = new SimpleDateFormat("HH:mm");
                    } else { // 不是日期格式
                        return formatter.formatCellValue(cell);
                    }
                    double value = cell.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    if(date==null || "".equals(date)){
                        return null;
                    }
                    String result=null;
                    try {
                        result = sdf.format(date);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    return result;
                }else{
                    return formatter.formatCellValue(cell);
                }

            }
            return null;
        }
        return null;
    }

}
