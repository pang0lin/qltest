package com.js.util.rss;

import com.js.util.util.DateHelper;
import java.util.Timer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class mainTask implements ServletContextListener {
  private int initMin = 60000;
  
  private int initHour = 60;
  
  private Timer timer = null;
  
  public void contextDestroyed(ServletContextEvent arg0) {
    if (this.timer == null)
      this.timer.cancel(); 
  }
  
  public void contextInitialized(ServletContextEvent arg0) {
    startTask();
  }
  
  protected void startTask() {
    this.timer = new Timer("定时更新RSS及知识置顶重置", true);
    this.timer.schedule(new InfoSetTopTask(), DateHelper.setNowTime(2, 0, 0), (this.initMin * this.initHour * 24));
  }
}
