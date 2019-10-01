package com.goodpower.pvams.quartz;

import com.goodpower.pvams.mapper.FireMaintainMapper;
import com.goodpower.pvams.model.FireMaintain;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Maps;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 消防器材检查任务
 */
public class FireScheduledJob implements Job {

    @Autowired
    FireMaintainMapper fireMaintainMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Map<String,Object> param = Maps.newHashMap();
        Date nextDate = DateUtil.addDay(date,1);
        String dateParam = sdf.format(date);
        String nextDateParam = sdf.format(nextDate);
        param.put("checkStatus",0);
        param.put("date",dateParam);
        param.put("nextDate",nextDateParam);
        fireMaintainMapper.updateStatus(param);
        System.out.println("执行了fireMaintainMapper");
    }

}
