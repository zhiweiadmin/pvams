package com.goodpower.pvams.quartz;

import com.goodpower.pvams.mapper.MaintainInfoMapper;
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
 * 重要维护检查任务
 */
public class ImportantMaintainScheduledJob implements Job {

    @Autowired
    MaintainInfoMapper maintainInfoMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Map<String,Object> param = Maps.newHashMap();
            String dateParam = sdf.format(date);
            param.put("status",3);//已过期
            param.put("date",dateParam);
            //将超过有效期的任务变成已过期
            maintainInfoMapper.updateStatus(param);
    }

}
