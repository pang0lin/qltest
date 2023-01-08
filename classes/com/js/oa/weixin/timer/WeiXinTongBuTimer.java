package com.js.oa.weixin.timer;

import com.js.util.config.SystemCommon;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class WeiXinTongBuTimer {
  private static final long PERIOD_DAY = 43200000L;
  
  public WeiXinTongBuTimer() {
    SystemCommon.init();
    if ("1".equals(SystemCommon.getUseWeiXinQYH()) && "1".equals(SystemCommon.getUseAutoSynchronous())) {
      Calendar calendar = Calendar.getInstance();
      String commitCount = "12:00";
      if (calendar.get(11) > 11) {
        calendar.add(5, 1);
        commitCount = "00:00";
      } 
      String[] objtime = (String[])null;
      if (commitCount != null && !"".equals(commitCount)) {
        objtime = commitCount.split(":");
      } else {
        objtime = new String[2];
        objtime[0] = "0";
        objtime[1] = "0";
      } 
      int beginHour = Integer.parseInt(objtime[0]);
      int beginMimu = Integer.parseInt(objtime[1]);
      calendar.set(11, beginHour);
      calendar.set(12, beginMimu);
      calendar.set(13, 0);
      Date date = calendar.getTime();
      Timer timer = new Timer();
      WeiXinTongBuStart task = new WeiXinTongBuStart();
      timer.schedule(task, date, 43200000L);
    } else {
      System.out.println("微信企业号自动同步未启用，未进行组织机构和人员同步...");
    } 
  }
  
  public Date addDay(Date date, int num) {
    Calendar stDT = Calendar.getInstance();
    stDT.setTime(date);
    stDT.add(5, num);
    return stDT.getTime();
  }
}
