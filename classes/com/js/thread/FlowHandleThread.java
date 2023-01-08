package com.js.thread;

import java.util.Timer;

public class FlowHandleThread extends Thread {
  private static Timer timer = new Timer();
  
  public void start() {
    timer.schedule(new FlowHandleMask(), 0L, 60000L);
  }
  
  public void cancel() {
    timer.cancel();
  }
}
