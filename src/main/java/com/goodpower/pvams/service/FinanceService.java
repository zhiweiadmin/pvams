package com.goodpower.pvams.service;

        import com.alibaba.fastjson.JSONObject;
        import com.goodpower.pvams.common.AverageCapitalUtils;
        import com.goodpower.pvams.mapper.StationFinanceBaseInfoMapper;
        import com.goodpower.pvams.mapper.StationFinanceDataMapper;
        import com.goodpower.pvams.mapper.StationLoanInfoMapper;
        import com.goodpower.pvams.mapper.StationPolicyMapper;
        import com.goodpower.pvams.model.StationFinanceBaseInfo;
        import com.goodpower.pvams.model.StationFinanceData;
        import com.goodpower.pvams.model.StationLoanInfo;
        import com.goodpower.pvams.model.StationPolicy;
        import com.goodpower.pvams.util.DateUtil;
        import com.google.common.collect.Lists;
        import com.google.common.collect.Maps;
        import org.apache.commons.lang3.StringUtils;
        import org.apache.poi.hssf.usermodel.*;
        import org.apache.poi.ss.usermodel.*;
        import org.apache.poi.ss.util.CellRangeAddress;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.math.BigDecimal;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Map;

@Service
public class FinanceService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StationFinanceBaseInfoMapper financeBaseInfoMapper;

    @Autowired
    StationFinanceDataMapper financeDataMapper;

    @Autowired
    ExcelService excelService;

    @Autowired
    StationLoanInfoMapper stationLoanInfoMapper;

    public void saveExcelData(Long stationId, Workbook workbook) throws ParseException {
        readFinanceBaseInfo(stationId,workbook.getSheetAt(0));
        readFinanceProfitInfo(stationId,workbook.getSheetAt(1));
    }

    public void readFinanceBaseInfo(Long stationId, Sheet sheet) throws ParseException {
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 1){
            Row row = sheet.getRow(2);
            String totalCost = excelService.getRowValue(row,0);//总造价
            String loanCost = excelService.getRowValue(row,1);//融资成本
            String runCost = excelService.getRowValue(row,2);//运营成本
            String loanType = excelService.getRowValue(row,3);//贷款方式
            String loanYear = excelService.getRowValue(row,4);//贷款年限
            String firstRepaymentDate = excelService.getCellDate(row.getCell(5));
            String loanRate = excelService.getRowValue(row,6);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            StationFinanceBaseInfo financeBaseInfo = new StationFinanceBaseInfo();
            if(StringUtils.isNotBlank(firstRepaymentDate)){
                Date repaymentDate = sdf.parse(firstRepaymentDate);
                financeBaseInfo.setFirstRepaymentDate(repaymentDate);
            }

            financeBaseInfo.setStationId(stationId);
            if(StringUtils.isNotBlank(totalCost)){
                financeBaseInfo.setTotalCost(new BigDecimal(totalCost));
            }
            if(StringUtils.isNotBlank(loanCost)){
                financeBaseInfo.setLoanCost(new BigDecimal(loanCost));
            }
            if(StringUtils.isNotBlank(runCost)){
                financeBaseInfo.setRunCost(new BigDecimal(runCost));
            }
            if(StringUtils.isNotBlank(loanType)){
                financeBaseInfo.setLoanType(Integer.parseInt(loanType));
            }
            if(StringUtils.isNotBlank(loanYear)){
                financeBaseInfo.setLoanYear(Integer.parseInt(loanYear));
            }

            if(StringUtils.isNotBlank(loanRate)){
                financeBaseInfo.setLoanRate(new BigDecimal(loanRate));
            }
            financeBaseInfoMapper.insert(financeBaseInfo);

            if(StringUtils.isNotBlank(loanType) && StringUtils.isNotBlank(loanCost)
                    && StringUtils.isNotBlank(loanYear) && StringUtils.isNotBlank(loanRate)
                    && StringUtils.isNotBlank(firstRepaymentDate)){
                saveStationLoanInfo(stationId,Integer.parseInt(loanType),new BigDecimal(loanCost),Integer.parseInt(loanYear), new BigDecimal(loanRate),sdf.parse(firstRepaymentDate));
            }

        }
    }

    public void readFinanceProfitInfo(Long stationId,Sheet sheet) throws ParseException {
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 1){
            for(int i=2;i<=lastRowNum;i++){
                    Row row = sheet.getRow(i);
                    String date = excelService.getCellDate(row.getCell(0));
                    String self_peak_power = excelService.getRowValue(row,1);
                    String self_peak_elcprice = excelService.getRowValue(row,2);
                    String self_peak_discount = excelService.getRowValue(row,3);
                    String self_power = excelService.getRowValue(row,4);
                    String self_elcprice = excelService.getRowValue(row,5);
                    String self_discount = excelService.getRowValue(row,6);
                    String self_low_power = excelService.getRowValue(row,7);
                    String self_low_elcprice = excelService.getRowValue(row,8);
                    String self_low_discount = excelService.getRowValue(row,9);
                    String sell_power = excelService.getRowValue(row,10);
                    String p_power_price = excelService.getRowValue(row,11);
                    String subsidy_price = excelService.getRowValue(row,12);
                    String month_subsidy_price = excelService.getRowValue(row,13);
                    String plan_power = excelService.getRowValue(row,14);
                    String plan_profit = excelService.getRowValue(row,15);

                    //TODO
                    StationFinanceData stationFinanceData = new StationFinanceData();
                    stationFinanceData.setStationId(stationId);

                    if(StringUtils.isNotBlank(date)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = sdf.parse(date);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date1);
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        stationFinanceData.setYear(year);
                        stationFinanceData.setMonth(month);
                    }

                    if(StringUtils.isNotBlank(self_peak_power)){
                        stationFinanceData.setSelfPeakPower(new BigDecimal(self_peak_power));
                    }else{
                        stationFinanceData.setSelfPeakPower(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(self_peak_elcprice)){
                        stationFinanceData.setSelfPeakElcprice(new BigDecimal(self_peak_elcprice));
                    }else{
                        stationFinanceData.setSelfPeakElcprice(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(self_peak_discount)){
                        stationFinanceData.setSelfPeakDiscount(new BigDecimal(self_peak_discount));
                    }else{
                        stationFinanceData.setSelfPeakDiscount(BigDecimal.ONE);
                    }
                    if(StringUtils.isNotBlank(self_power)){
                        stationFinanceData.setSelfPower(new BigDecimal(self_power));
                    }else{
                        stationFinanceData.setSelfPower(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(self_elcprice)){
                        stationFinanceData.setSelfElcprice(new BigDecimal(self_elcprice));
                    }else{
                        stationFinanceData.setSelfElcprice(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(self_discount)){
                        stationFinanceData.setSelfDiscount(new BigDecimal(self_discount));
                    }else{
                        stationFinanceData.setSelfDiscount(BigDecimal.ONE);
                    }
                    if(StringUtils.isNotBlank(self_low_power)){
                        stationFinanceData.setSelfLowPower(new BigDecimal(self_low_power));
                    }else{
                        stationFinanceData.setSelfLowPower(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(self_low_elcprice)){
                        stationFinanceData.setSelfLowElcprice(new BigDecimal(self_low_elcprice));
                    }else{
                        stationFinanceData.setSelfLowElcprice(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(self_low_discount)){
                        stationFinanceData.setSelfLowDiscount(new BigDecimal(self_low_discount));
                    }else{
                        stationFinanceData.setSelfLowDiscount(BigDecimal.ONE);
                    }
                    if(StringUtils.isNotBlank(sell_power)){
                        stationFinanceData.setSellPower(new BigDecimal(sell_power));
                    }else{
                        stationFinanceData.setSellPower(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(p_power_price)){
                        stationFinanceData.setpPowerPrice(new BigDecimal(p_power_price));
                    }else{
                        stationFinanceData.setpPowerPrice(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(subsidy_price)){
                        stationFinanceData.setSubsidyPrice(new BigDecimal(subsidy_price));
                    }else{
                        stationFinanceData.setSubsidyPrice(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(month_subsidy_price)){
                        stationFinanceData.setMonthSubsidyPrice(new BigDecimal(month_subsidy_price));
                    }else{
                        stationFinanceData.setMonthSubsidyPrice(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(plan_power)){
                        stationFinanceData.setPlanPower(new BigDecimal(plan_power));
                    }else{
                        stationFinanceData.setPlanPower(BigDecimal.ZERO);
                    }
                    if(StringUtils.isNotBlank(plan_profit)){
                        stationFinanceData.setPlanProfit(new BigDecimal(plan_profit));
                    }else{
                        stationFinanceData.setPlanProfit(BigDecimal.ZERO);
                    }

                    //计算收益
                    BigDecimal selfPeakProfit = BigDecimal.ZERO;
                    BigDecimal selfPeakPower = stationFinanceData.getSelfPeakPower();
                    BigDecimal selfPeakElcprice = stationFinanceData.getSelfPeakElcprice();
                    BigDecimal selfPeakDiscount = stationFinanceData.getSelfPeakDiscount();
                    selfPeakProfit = selfPeakPower.multiply(selfPeakElcprice).multiply(selfPeakDiscount).setScale(2,BigDecimal.ROUND_HALF_UP);

                    BigDecimal selfProfit = BigDecimal.ZERO;
                    BigDecimal selfPower = stationFinanceData.getSelfPower();
                    BigDecimal selfElcprice = stationFinanceData.getSelfElcprice();
                    BigDecimal selfDiscount = stationFinanceData.getSelfDiscount();
                    selfProfit = selfPower.multiply(selfElcprice).multiply(selfDiscount).setScale(2,BigDecimal.ROUND_HALF_UP);

                    BigDecimal selfLowProfit = BigDecimal.ZERO;
                    BigDecimal selfLowPower = stationFinanceData.getSelfLowPower();
                    BigDecimal selfLowElcprice = stationFinanceData.getSelfLowElcprice();
                    BigDecimal selfLowDiscount = stationFinanceData.getSelfLowDiscount();
                    selfLowProfit = selfLowPower.multiply(selfLowElcprice).multiply(selfLowDiscount).setScale(2,BigDecimal.ROUND_HALF_UP);

                    //总发电量
                    BigDecimal totalPower = BigDecimal.ZERO;
                    totalPower = totalPower.add(stationFinanceData.getSelfPeakPower())
                            .add(stationFinanceData.getSelfPower())
                            .add(stationFinanceData.getSelfLowPower())
                            .add(stationFinanceData.getSellPower());

                    //总收益
                    BigDecimal totolProfit = BigDecimal.ZERO;
                    //自用总收益
                    BigDecimal selfTotalProfit = selfPeakProfit.add(selfProfit).add(selfLowProfit);
                    //上网收益
                    BigDecimal sellProfit = stationFinanceData.getSellPower().multiply(stationFinanceData.getpPowerPrice());
                    //月补贴收益
                    BigDecimal monthSubsidyProfit = totalPower.multiply(new BigDecimal(subsidy_price));
                    //月补贴价格
                    BigDecimal monthSubsidyPrice = stationFinanceData.getMonthSubsidyPrice();
                    totolProfit = totolProfit.add(selfTotalProfit).add(sellProfit).add(monthSubsidyProfit).add(monthSubsidyPrice);

                    stationFinanceData.setTotalPower(totalPower);
                    stationFinanceData.setTotalSelfProfit(selfTotalProfit);
                    stationFinanceData.setTotolProfit(totolProfit);
                    financeDataMapper.insert(stationFinanceData);
            }
        }
    }

    public void saveStationLoanInfo(long stationId,int loanType,BigDecimal loanCost,int loanYear,BigDecimal loanRate,Date firstRepaymentDate){
        Map<Integer, Double> dataMap = AverageCapitalUtils.getPerMonthPrincipalInterest(loanCost.doubleValue(),loanRate.doubleValue(),loanYear * 12);
        for(Integer num : dataMap.keySet()){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(firstRepaymentDate);
            calendar.add(Calendar.MONTH,num - 1);
            Date newDate = calendar.getTime();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;

            StationLoanInfo loanInfo = new StationLoanInfo();
            loanInfo.setLoanType(loanType);
            loanInfo.setPayDate(newDate);
            loanInfo.setYear(year);
            loanInfo.setMonth(month);
            loanInfo.setMonthPay(new BigDecimal(dataMap.get(num)));
            loanInfo.setCreateDttm(new Date());
            loanInfo.setUpdateDttm(new Date());
            loanInfo.setStationId(stationId);
            stationLoanInfoMapper.insert(loanInfo);
        }

    }

    public List<Map<String,Object>> getYearDataStatReal(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return financeDataMapper.getYearDataStatReal(param);
    }

    public List<Map<String,Object>> getMonthDataStatReal(Long stationId,Integer year){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("year",year);
        return financeDataMapper.getMonthDataStatReal(param);
    }

    public List<Map<String,Object>> getYearDataStatPlan(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return financeDataMapper.getYearDataStatPlan(param);
    }

    public List<Map<String,Object>> getMonthDataStatPlan(Long stationId,Integer year){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("year",year);
        return financeDataMapper.getMonthDataStatPlan(param);
    }

    public StationFinanceBaseInfo getBaseInfo(Long stationId){
        return financeBaseInfoMapper.selectByStationId(stationId);
    }

    public JSONObject getProfit(Long stationId, Integer year){
        if(year == null){
            year = DateUtil.getCurYear();
        }
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        param.put("year",year);
        List<Map<String,Object>> realList = getMonthDataStatReal(stationId,year);
        Map<Integer,Map<String,Object>> profitMap = Maps.newHashMap();
        for(Map<String,Object> map : realList){
            profitMap.put(Integer.parseInt(map.get("date").toString()),map);
        }

        List<StationLoanInfo> loanInfoList = stationLoanInfoMapper.selectByFields(param);

        //
        List<Map<String,Object>> loanList = Lists.newArrayList();
        List<Map<String,Object>> incomeList = Lists.newArrayList();
        List<Map<String,Object>> profitList = Lists.newArrayList();
        List<String> nameList = Lists.newArrayList();
        for(StationLoanInfo stationLoanInfo : loanInfoList){
            int payMonth = stationLoanInfo.getMonth();
            Map<String,Object> monthProfitMap = profitMap.get(payMonth);
            BigDecimal income = new BigDecimal(monthProfitMap.get("benefit").toString());
            //近利润
            BigDecimal profit = income.subtract(stationLoanInfo.getMonthPay());

            Map<String,Object> loanMap = Maps.newHashMap();
            loanMap.put("y",stationLoanInfo.getMonthPay());
            loanMap.put("color","#47d147");
            loanList.add(loanMap);

            Map<String,Object> incomeMap = Maps.newHashMap();
            incomeMap.put("y",income);
            incomeMap.put("color","#47d147");
            incomeList.add(incomeMap);

            Map<String,Object> benefitMap = Maps.newHashMap();
            benefitMap.put("y",profit);
            benefitMap.put("color","#47d147");
            profitList.add(benefitMap);

            nameList.add(year+"-"+payMonth);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loan",loanList);
        jsonObject.put("income",incomeList);
        jsonObject.put("profit",profitList);
        jsonObject.put("name",nameList);
        return jsonObject;
    }

    /**
     * 创建财务模型模板
     */
    public void createTemplate(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("基础信息");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)20);
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
        int[] width = new int[]{5304, 5304, 5304, 5304, 5304,5304,5304};
        sheet.setColumnWidth(0, width[0]);
        sheet.setColumnWidth(1, width[1]);
        sheet.setColumnWidth(2, width[2]);
        sheet.setColumnWidth(3, width[3]);
        sheet.setColumnWidth(4, width[4]);
        sheet.setColumnWidth(5, width[5]);
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("财务模型基础信息");
        cell.setCellStyle(boderStyle);
        HSSFRow row2 = sheet.createRow(1);
        HSSFCell cell2_0 = row2.createCell(0);
        cell2_0.setCellValue("电站总造价");
        cell2_0.setCellStyle(boderStyle1);
        HSSFCell cell2_1 = row2.createCell(1);
        cell2_1.setCellValue("融资成本");
        cell2_1.setCellStyle(boderStyle1);
        HSSFCell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("运营成本");
        cell2_2.setCellStyle(boderStyle1);
        HSSFCell cell2_3 = row2.createCell(3);
        cell2_3.setCellValue("贷款方式");
        cell2_3.setCellStyle(boderStyle1);
        HSSFCell cell2_4 = row2.createCell(4);
        cell2_4.setCellValue("贷款年限");
        cell2_4.setCellStyle(boderStyle1);
        HSSFCell cell2_5 = row2.createCell(5);
        cell2_5.setCellValue("年利率");
        cell2_5.setCellStyle(boderStyle1);
    }

    public void createTemplate2(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("收益");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)20);
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
        int[] width = new int[]{6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328, 6328,6328};

        for(int i = 0; i < width.length; ++i) {
            sheet.setColumnWidth(i, width[i]);
        }

        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue("财务模型收益信息");
        cell.setCellStyle(boderStyle);
        HSSFRow row2 = sheet.createRow(1);
        this.createCell(row2, 0, "月份", boderStyle1);
        this.createCell(row2, 1, "（自用）峰电量", boderStyle1);
        this.createCell(row2, 2, "（自用）峰电价", boderStyle1);
        this.createCell(row2, 3, "（自用）折扣", boderStyle1);
        this.createCell(row2, 4, "（自用）平电量", boderStyle1);
        this.createCell(row2, 5, "（自用）平电价", boderStyle1);
        this.createCell(row2, 6, "（自用）平折扣", boderStyle1);
        this.createCell(row2, 7, "（自用）谷电量", boderStyle1);
        this.createCell(row2, 8, "（自用）谷电价", boderStyle1);
        this.createCell(row2, 9, "（自用）谷折扣", boderStyle1);
        this.createCell(row2, 10, "上网电量", boderStyle1);
        this.createCell(row2, 11, "省脱硫煤标杆电价", boderStyle1);
        this.createCell(row2, 12, "补贴价格", boderStyle1);
        this.createCell(row2, 13, "月补贴价格", boderStyle1);
        this.createCell(row2, 13, "计划电量", boderStyle1);
        this.createCell(row2, 14, "计划收益", boderStyle1);
    }

    public void createTemplate3(HSSFWorkbook wb){
        HSSFSheet sheet = wb.createSheet("保单");
        //设置单元格合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        HSSFCellStyle boderStyle = wb.createCellStyle();
        boderStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)20);
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

        int[] width = {256*20+184,256*20+184,256*20+184,256*20+184};
        //设置宽度
        sheet.setColumnWidth(0,width[0]);
        sheet.setColumnWidth(1,width[1]);
        sheet.setColumnWidth(2,width[2]);
        sheet.setColumnWidth(3,width[3]);

        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);
        //设置单元格内容
        cell.setCellValue("财务模型保单信息");
        cell.setCellStyle(boderStyle);

        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);

        createCell(row2,0,"保单名称",boderStyle1);
        createCell(row2,1,"保单开始时间",boderStyle1);
        createCell(row2,2,"保单终止时间",boderStyle1);
        createCell(row2,3,"保额",boderStyle1);
    }

    public void createCell(HSSFRow row,int col,String cellVal,HSSFCellStyle cellStyle ){
        HSSFCell cell = row.createCell(col);
        cell.setCellValue(cellVal);
        cell.setCellStyle(cellStyle);
    }

    @Autowired
    StationPolicyMapper stationPolicyMapper;

    public List<StationPolicy> getPolicy(Long stationId){
        Map<String,Object> param = Maps.newHashMap();
        param.put("stationId",stationId);
        return stationPolicyMapper.selectByFields(param);
    }

}
