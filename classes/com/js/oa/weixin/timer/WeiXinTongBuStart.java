package com.js.oa.weixin.timer;

import com.js.oa.weixin.tongbu.WeiXinTongBuAction;
import java.util.Date;
import java.util.TimerTask;

public class WeiXinTongBuStart extends TimerTask {
  public void run() {
    try {
      (new WeiXinTongBuAction()).weixinTongBu();
      System.out.println("微信组织用户定时同步完成..." + new Date());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
