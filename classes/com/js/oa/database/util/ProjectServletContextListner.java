package com.js.oa.database.util;

import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProjectServletContextListner implements ServletContextListener {
  private static Timer timer = null;
  
  public void contextDestroyed(ServletContextEvent event) {
    timer.cancel();
    event.getServletContext().log("定时器已销毁,任务执行结束");
  }
  
  public void contextInitialized(ServletContextEvent event) {
    timer = new Timer(true);
    ServletContext ctx = event.getServletContext();
    ctx.log("定时器已启动,任务开始执行");
    timer.schedule(new NewTask(), 
        1200000L, 
        28800000L);
  }
}
