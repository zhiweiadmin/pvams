package com.goodpower.pvams.service;

public class LoanService {

    public static void main(String[] args){
        double amount = 1200000;
        double rate = 0.06125;
        int term = 30*12;
        //每月还款本金
        double capital = amount/term;
        for(int i=1;i<=term;i++){
            double money = capital+(amount-(i-1)*capital)*rate/12;
            System.out.println(money);
        }

    }

}
