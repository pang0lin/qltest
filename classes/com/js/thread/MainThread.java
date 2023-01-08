package com.js.thread;

import com.js.oa.search.client.SearchIndexTask;
import com.js.oa.search.client.SearchService;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MainThread {
  private static Timer timer = new Timer();
  
  private static Timer indexTimer = new Timer();
  
  public void start() {
    timer.schedule(new MainTask(), 0L, 60000L);
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch)) {
      Calendar cal = Calendar.getInstance();
      cal.set(11, 22);
      cal.set(12, 0);
      Date startTime = new Date();
      if (startTime.before(cal.getTime()))
        startTime = cal.getTime(); 
      indexTimer.schedule(new SearchIndexTask(), startTime, 86400000L);
    } 
  }
  
  public void cancel() {
    System.out.println("定时器销毁！");
    timer.cancel();
    indexTimer.cancel();
  }
}
