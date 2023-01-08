package com.js.util.job;

import com.js.oa.jsflow.service.WorkFlowButtonBD;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class WorkFlowUserLock implements Job {
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("正在执行清理工作流在线用户垃圾锁任务……\n" + new Date() + " by " + context.getTrigger().getName());
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    bd.cleanWFOnlineUserInvalidate();
  }
}
