package com.js.system.timer.langxin;

import com.js.util.config.SystemCommon;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MessageTimer {
  private static final long oneMin = 60000L;
  
  public MessageTimer() {
    String openTimer = SystemCommon.getLxOpenTimer();
    String databaseName = SystemCommon.getLxDatabaseName();
    String time = (SystemCommon.getLxIntervalTime() == null || "".equals(SystemCommon.getLxIntervalTime())) ? "10" : SystemCommon.getLxIntervalTime();
    int intervalTime = 10;
    try {
      intervalTime = Integer.parseInt(time);
    } catch (Exception e) {
      intervalTime = 10;
    } finally {
      if (openTimer != null && "1".equals(openTimer))
        try {
          if (databaseName != null && !"".equals(databaseName)) {
            System.out.println("财务系统用户提醒消息同步开始，同步间隔时长为" + intervalTime + "min");
            Calendar calendar = Calendar.getInstance();
            calendar.set(12, calendar.getMinimum(12) - 1);
            Timer timer = new Timer();
            timer.schedule(new MessageTask(), calendar.getTime(), 60000L * intervalTime);
          } else {
            System.out.println("同步程序未配置数据源...");
          } 
        } catch (Exception e) {
          e.printStackTrace();
          System.out.println("请检查同步程序配置参数格式是否正确！");
        }  
    } 
  }
  
  public Date addDay(Date date, int num) {
    Calendar stDT = Calendar.getInstance();
    stDT.setTime(date);
    stDT.add(5, num);
    return stDT.getTime();
  }
}
