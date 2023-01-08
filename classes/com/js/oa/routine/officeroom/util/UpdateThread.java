package com.js.oa.routine.officeroom.util;

import java.util.Timer;

public class UpdateThread {
  private static Timer timer = new Timer();
  
  public void start() {}
  
  public void cancel() {
    timer.cancel();
  }
}
