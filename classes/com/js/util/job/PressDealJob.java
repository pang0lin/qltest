package com.js.util.job;

import com.js.oa.jsflow.service.WorkFlowButtonBD;
import com.js.oa.pressdeal.service.PersonalOAPressManageBD;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PressDealJob implements Job {
  public void execute(JobExecutionContext context) throws JobExecutionException {
    WorkFlowButtonBD bd = new WorkFlowButtonBD();
    List<E> list = bd.getPressDealList();
    StringBuffer sb = new StringBuffer("");
    for (int i = 0; i < list.size(); i++) {
      System.out.println(String.valueOf(i) + ":" + list.get(i).toString());
      Object[] obj = (Object[])list.get(i);
      PersonalOAPressManageBD pressBD = new PersonalOAPressManageBD();
      try {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date tmpDate = df.parse(obj[4].toString());
        String submitPersonTime = df.format(tmpDate);
        String rslt = pressBD.sendNewPressBySystem(
            "$" + obj[0].toString() + "$", 
            obj[1].toString(), 
            "0", 
            "催办", 
            "系统", 
            "催办：" + obj[3].toString() + submitPersonTime + "提交的" + obj[5].toString() + "等待您处理！", 
            "请抓紧时间办理！", 
            "工作流程", 
            obj[5].toString(), 
            obj[6].toString());
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      sb.append(obj[7].toString()).append(",");
    } 
    WorkFlowButtonBD workFlowButtonBD = new WorkFlowButtonBD();
    if (!sb.toString().equals(""))
      workFlowButtonBD.setWorkTask(sb.toString().substring(0, sb.toString().length() - 1), "1"); 
  }
}
