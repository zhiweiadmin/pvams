package com.goodpower.pvams.quartz;

import com.goodpower.pvams.service.MaintainService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义定时任务
 */
public class ScheduledJob implements Job {

    private Logger logger = LoggerFactory.getLogger(ScheduledJob.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //执行任务逻辑....
        logger.info("执行自定义定时任务");
    }
}
