package com.goodpower.pvams.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JobAutoRunService implements ApplicationRunner {

    @Autowired
    public SchedulerManager schedulerManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //每天凌晨10秒的时候开始运行
        schedulerManager.startJob("10 0 0 * * ? ","fireJob","fireJobGroup", FireScheduledJob.class);
        //每天凌晨20秒的时候开始运行
        schedulerManager.startJob("20 0 0 * * ? ","toolJob","toolJobGroup", ToolScheduledJob.class);
        //每天凌晨30秒的时候开始运行
        schedulerManager.startJob("30 0 0 * * ? ","infoJob","infoJobGroup", ImportantMaintainScheduledJob.class);
    }


}
