package com.js.system.timer.langxin;

import java.util.TimerTask;

public class MessageTask extends TimerTask {
  public void run() {
    LXMessage.getMessages();
  }
}
