package com.goodpower.pvams.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class JobAutoRunService implements ApplicationRunner {

    @Autowired
    public SchedulerManager myScheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //每天凌晨10秒的时候开始运行
        myScheduler.startJob("10 * * * * ? *","fireJob","fireJobGroup", FireScheduledJob.class);
        //每天凌晨20秒的时候开始运行
        myScheduler.startJob("20 * * * * ? *","toolJob","toolJobGroup", ToolScheduledJob.class);
    }


}
