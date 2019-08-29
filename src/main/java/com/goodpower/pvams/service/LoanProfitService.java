package com.goodpower.pvams.service;

import com.alibaba.fastjson.JSONObject;
import com.goodpower.pvams.mapper.LoanProfitMapper;
import com.goodpower.pvams.model.LoanProfit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
public class LoanProfitService {

    private static final Integer LOAN = 1;

    private static final Integer INCOME = 2;

    @Autowired
    LoanProfitMapper loanProfitMapper;

    public JSONObject getData(Long stationId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("stationId", stationId);
        param.put("type", LOAN);
        List< Map<String, Object>> resultList = Lists.newArrayList();
        List<LoanProfit> loanList = loanProfitMapper.selectByFields(param);
        param.put("type", INCOME);
        List<LoanProfit> incomeList = loanProfitMapper.selectByFields(param);
        Map<String, LoanProfit> yearMonthLoanMap = Maps.newHashMap();
        for (LoanProfit bean : loanList) {
            Integer year = bean.getYear();
            Integer month = bean.getMonth();
            String key = year + "-" + month;
            yearMonthLoanMap.put(key, bean);
        }
        for (LoanProfit bean : incomeList) {
            Integer year = bean.getYear();
            Integer month = bean.getMonth();
            String key = year + "-" + month;

            BigDecimal loan = new BigDecimal("0");
            if (yearMonthLoanMap.get(key) != null) {
                Object valObject = yearMonthLoanMap.get(key).getVal();
                if (valObject != null) {
                    loan = new BigDecimal(valObject.toString());
                    loan = loan.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }
            BigDecimal income = new BigDecimal("0");
            if (bean.getVal()!= null){
                income = bean.getVal();
                income = income.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            BigDecimal profit = income.subtract(loan);

            Map<String,Object> map = Maps.newHashMap();
            map.put("profit",profit);
            map.put("income",income);
            map.put("loan",loan);
            map.put("date",key);
            resultList.add(map);
        }

        List<Map<String,Object>> newLoanList = Lists.newArrayList();
        List<Map<String,Object>> newIncomeList = Lists.newArrayList();
        List<Map<String,Object>> newProfitList = Lists.newArrayList();
        List<Object> nameList = Lists.newArrayList();
        for(Map<String, Object> map : resultList){
            Object loan = map.get("loan");
            Object income = map.get("income");
            Object profit = map.get("profit");
            Object date = map.get("date");

            Map<String,Object> loanMap = Maps.newHashMap();
            loanMap.put("y",loan);
            loanMap.put("color","red");
            newLoanList.add(loanMap);

            Map<String,Object> incomeMap = Maps.newHashMap();
            incomeMap.put("y",income);
            incomeMap.put("color","black");
            newIncomeList.add(incomeMap);

            Map<String,Object> profitMap = Maps.newHashMap();
            profitMap.put("y",profit);
            profitMap.put("color","blue");
            newProfitList.add(profitMap);

            nameList.add(date);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("loan",newLoanList);
        jsonObject.put("income",newIncomeList);
        jsonObject.put("profit",newProfitList);
        jsonObject.put("name",nameList);
        return jsonObject;
    }

}

