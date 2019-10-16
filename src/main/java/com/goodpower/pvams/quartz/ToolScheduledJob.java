package com.goodpower.pvams.quartz;

import com.goodpower.pvams.mapper.ToolMaintainMapper;
import com.goodpower.pvams.util.DateUtil;
import com.google.common.collect.Maps;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 工器具检查任务
 */
public class ToolScheduledJob implements Job {

    @Autowired
    ToolMaintainMapper toolMaintainMapper;

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
            toolMaintainMapper.updateStatus(param);
            System.out.println("执行了toolMaintainMapper");
    }

}
