package com.js.thread;

import java.util.Timer;

public class LdSMSThread {
  private static Timer timer = new Timer();
  
  public void start() {
    timer.schedule(new LdSMSTask(), 0L, 60000L);
  }
  
  public void cancel() {
    System.out.println("短信定时器销毁！");
    timer.cancel();
  }
}
