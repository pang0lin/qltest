package com.js.oa.security.log.util;

import java.util.Timer;

public class LogThread {
  private static Timer timer = new Timer();
  
  public void start() {
    timer.schedule(new LogTask(), 0L, 60000L);
  }
  
  public void cancel() {
    timer.cancel();
  }
}
