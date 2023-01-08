package com.js.util.rss;

import com.js.oa.info.infomanager.service.InformationBD;
import java.util.TimerTask;

public class InfoSetTopTask extends TimerTask {
  public void run() {
    update();
  }
  
  private void update() {
    InformationBD idb = new InformationBD();
    idb.resetOrderCode();
  }
}
