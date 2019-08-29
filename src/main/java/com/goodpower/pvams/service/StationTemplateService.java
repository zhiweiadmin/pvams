package com.goodpower.pvams.service;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;


@Service
public class StationTemplateService {

    public void createGroundStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"并网电压等级");
        createRowCell(sheet.createRow(6),boderStyle1,"并网点数量");
        createRowCell(sheet.createRow(7),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(8),boderStyle1,"路面类型");
        createRowCell(sheet.createRow(9),boderStyle1,"是否于耕种区毗邻");
        createRowCell(sheet.createRow(10),boderStyle1,"是否封闭围栏");
        createRowCell(sheet.createRow(11),boderStyle1,"是否有外来人员种植");
        createRowCell(sheet.createRow(12),boderStyle1,"是否有外来人员祭祖");
        createRowCell(sheet.createRow(13),boderStyle1,"组件底端距地面距离");
        createRowCell(sheet.createRow(14),boderStyle1,"分布点数量");
        createRowCell(sheet.createRow(15),boderStyle1,"周初始");
        createRowCell(sheet.createRow(16),boderStyle1,"月初始");

    }

    public void createRowCell(HSSFRow row,HSSFCellStyle style,String name){
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(name);
        cell.setCellStyle(style);
    }

    public void createAgricultureStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"并网电压等级");
        createRowCell(sheet.createRow(6),boderStyle1,"并网点数量");
        createRowCell(sheet.createRow(7),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(8),boderStyle1,"送出线路长度");
        createRowCell(sheet.createRow(9),boderStyle1,"路面类型");
        createRowCell(sheet.createRow(10),boderStyle1,"是否于耕种区毗邻");
        createRowCell(sheet.createRow(11),boderStyle1,"是否封闭围栏");
        createRowCell(sheet.createRow(12),boderStyle1,"是否有外来人员种植");
        createRowCell(sheet.createRow(13),boderStyle1,"是否有外来人员祭祖");
        createRowCell(sheet.createRow(14),boderStyle1,"组件底端距地面距离");
        createRowCell(sheet.createRow(15),boderStyle1,"是否有动物养殖");
        createRowCell(sheet.createRow(16),boderStyle1,"清洗水源");
        createRowCell(sheet.createRow(17),boderStyle1,"周初始");
        createRowCell(sheet.createRow(18),boderStyle1,"月初始");
    }

    public void createFishStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"并网电压等级");
        createRowCell(sheet.createRow(6),boderStyle1,"并网点数量");
        createRowCell(sheet.createRow(7),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(8),boderStyle1,"送出线路长度");
        createRowCell(sheet.createRow(9),boderStyle1,"路面类型");
        createRowCell(sheet.createRow(10),boderStyle1,"水塘数量");
        createRowCell(sheet.createRow(11),boderStyle1,"水塘平均深度");
        createRowCell(sheet.createRow(12),boderStyle1,"组件底端距水面距离");
        createRowCell(sheet.createRow(13),boderStyle1,"是否有水产养殖");
        createRowCell(sheet.createRow(14),boderStyle1,"清洗水源");
        createRowCell(sheet.createRow(15),boderStyle1,"周初始");
        createRowCell(sheet.createRow(16),boderStyle1,"月初始");
    }

    public void createFloatStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"并网电压等级");
        createRowCell(sheet.createRow(6),boderStyle1,"并网点数量");
        createRowCell(sheet.createRow(7),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(8),boderStyle1,"送出线路长度");
        createRowCell(sheet.createRow(9),boderStyle1,"路面类型");
        createRowCell(sheet.createRow(10),boderStyle1,"水塘数量");
        createRowCell(sheet.createRow(11),boderStyle1,"水塘平均深度");
        createRowCell(sheet.createRow(12),boderStyle1,"浮体类型");
        createRowCell(sheet.createRow(13),boderStyle1,"固定类型");
        createRowCell(sheet.createRow(14),boderStyle1,"组件底端距水面距离");
        createRowCell(sheet.createRow(15),boderStyle1,"是否有水产养殖");
        createRowCell(sheet.createRow(16),boderStyle1,"清洗水源");
        createRowCell(sheet.createRow(17),boderStyle1,"周初始");
        createRowCell(sheet.createRow(18),boderStyle1,"月初始");
    }

    public void createDistributedStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"并网电压等级");
        createRowCell(sheet.createRow(6),boderStyle1,"并网点数量");
        createRowCell(sheet.createRow(7),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(8),boderStyle1,"屋顶类型");
        createRowCell(sheet.createRow(9),boderStyle1,"有/无采光带");
        createRowCell(sheet.createRow(10),boderStyle1,"上屋面条件");
        createRowCell(sheet.createRow(11),boderStyle1,"屋顶数量");
        createRowCell(sheet.createRow(12),boderStyle1,"分布点数量");
        createRowCell(sheet.createRow(13),boderStyle1,"清洗水源接入");
        createRowCell(sheet.createRow(14),boderStyle1,"周初始");
        createRowCell(sheet.createRow(15),boderStyle1,"月初始");
    }


    public void createPoorStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"并网电压等级");
        createRowCell(sheet.createRow(6),boderStyle1,"并网点数量");
        createRowCell(sheet.createRow(7),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(8),boderStyle1,"路面类型");
        createRowCell(sheet.createRow(9),boderStyle1,"是否于耕种区毗邻");
        createRowCell(sheet.createRow(10),boderStyle1,"是否封闭围栏");
        createRowCell(sheet.createRow(11),boderStyle1,"是否有外来人员种植");
        createRowCell(sheet.createRow(12),boderStyle1,"是否有外来人员祭祖");
        createRowCell(sheet.createRow(13),boderStyle1,"组件底端距地面距离");
        createRowCell(sheet.createRow(14),boderStyle1,"分布点数量");
        createRowCell(sheet.createRow(15),boderStyle1,"周初始");
        createRowCell(sheet.createRow(16),boderStyle1,"月初始");
    }

    public void createUserDistributedStationTmp(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("基础信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"装机功率");
        createRowCell(sheet.createRow(2),boderStyle1,"并网时间");
        createRowCell(sheet.createRow(3),boderStyle1,"项目倾角");
        createRowCell(sheet.createRow(4),boderStyle1,"阵列间距");
        createRowCell(sheet.createRow(5),boderStyle1,"电站容配比");
        createRowCell(sheet.createRow(6),boderStyle1,"屋顶类型");
        createRowCell(sheet.createRow(7),boderStyle1,"有/无采光带");
        createRowCell(sheet.createRow(8),boderStyle1,"上屋面条件");
        createRowCell(sheet.createRow(9),boderStyle1,"屋顶数量");
        createRowCell(sheet.createRow(10),boderStyle1,"分布点数量");
        createRowCell(sheet.createRow(11),boderStyle1,"清洗水源接入");
        createRowCell(sheet.createRow(12),boderStyle1,"周初始");
        createRowCell(sheet.createRow(13),boderStyle1,"月初始");
    }

    public void createBuildSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("施工信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("施工信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"施工单位");
        createRowCell(sheet.createRow(2),boderStyle1,"单位地址");
        createRowCell(sheet.createRow(3),boderStyle1,"联系人");
        createRowCell(sheet.createRow(4),boderStyle1,"联系电话");
        createRowCell(sheet.createRow(5),boderStyle1,"备注");
    }

    public void createConstructSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("建设信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("建设信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"建设单位");
        createRowCell(sheet.createRow(2),boderStyle1,"单位地址");
        createRowCell(sheet.createRow(3),boderStyle1,"联系人");
        createRowCell(sheet.createRow(4),boderStyle1,"联系电话");
        createRowCell(sheet.createRow(5),boderStyle1,"备注");
    }

    public void createSuperviseSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("监理信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("监理信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        createRowCell(sheet.createRow(1),boderStyle1,"监理单位");
        createRowCell(sheet.createRow(2),boderStyle1,"单位地址");
        createRowCell(sheet.createRow(3),boderStyle1,"联系人");
        createRowCell(sheet.createRow(4),boderStyle1,"联系电话");
        createRowCell(sheet.createRow(5),boderStyle1,"备注");
    }

    public void createGirdSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("并网信息");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("并网信息");
        cell.setCellStyle(boderStyle);

        createRowCell(sheet.createRow(1),boderStyle1,"电网名称");
        createRowCell(sheet.createRow(2),boderStyle1,"联系人");
        createRowCell(sheet.createRow(3),boderStyle1,"联系电话");
        createRowCell(sheet.createRow(4),boderStyle1,"电压等级");
        createRowCell(sheet.createRow(5),boderStyle1,"备注");
    }

    public void createPositionSheet(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("位置信息");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)18);
        boderStyle.setFont(font);

        HSSFCellStyle boderStyle1 = wb.createCellStyle();
        boderStyle1.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font1 = wb.createFont();
        font1.setFontName("宋体");
        font.setBold(true);
        font1.setFontHeightInPoints((short)13);
        boderStyle1.setFont(font1);

        HSSFCellStyle boderStyle2 = wb.createCellStyle();
        boderStyle2.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font2 = wb.createFont();
        font2.setFontName("宋体");
        font2.setFontHeightInPoints((short)12);
        boderStyle2.setFont(font2);

        int[] width = {256*25+184,256*25+184};
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);

        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("位置信息");
        cell.setCellStyle(boderStyle);

        createRowCell(sheet.createRow(1),boderStyle1,"电站名称");
        createRowCell(sheet.createRow(2),boderStyle1,"电站编号");
        createRowCell(sheet.createRow(3),boderStyle1,"省");
        createRowCell(sheet.createRow(4),boderStyle1,"市");
        createRowCell(sheet.createRow(5),boderStyle1,"区");
        createRowCell(sheet.createRow(6),boderStyle1,"乡");
        createRowCell(sheet.createRow(7),boderStyle1,"村");
        createRowCell(sheet.createRow(8),boderStyle1,"电站经度");
        createRowCell(sheet.createRow(9),boderStyle1,"电站纬度");
    }



}
