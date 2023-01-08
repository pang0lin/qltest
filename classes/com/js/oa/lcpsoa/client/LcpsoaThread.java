package com.js.oa.lcpsoa.client;

import java.util.Timer;

public class LcpsoaThread {
  private static Timer timer = new Timer();
  
  public void start() {
    timer.schedule(new LcpsoaTask(), 0L, 60000L);
  }
  
  public void cancel() {
    timer.cancel();
  }
}
