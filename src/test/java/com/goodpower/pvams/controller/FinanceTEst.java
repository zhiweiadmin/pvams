package com.goodpower.pvams.controller;

import com.goodpower.pvams.service.FinanceService;
import com.goodpower.pvams.service.LoanProfitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FinanceTEst {

    @Autowired
    FinanceService financeService;

    @Autowired
    LoanProfitService loanProfitService;

    @Test
    public void getDDD(){
        //financeService.getYearRealPowerProfit(1L,3,8);
        //loanProfitService.query(1L);
    }

}
