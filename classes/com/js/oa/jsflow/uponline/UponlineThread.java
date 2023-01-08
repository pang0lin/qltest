package com.js.oa.jsflow.uponline;

import java.util.Timer;

public class UponlineThread {
  private static Timer timer = new Timer();
  
  public void start() {
    timer.schedule(new UponlineTask(), 0L, 60000L);
  }
  
  public void cancel() {
    timer.cancel();
  }
}
