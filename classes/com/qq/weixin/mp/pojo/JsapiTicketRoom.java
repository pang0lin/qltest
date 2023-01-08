package com.qq.weixin.mp.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsapiTicketRoom {
  private static List<JsapiTicketItem> jsapiTickets = new ArrayList<JsapiTicketItem>();
  
  private static String appid = "jsoa";
  
  public static JsapiTicketItem getJsapiTicketItem() {
    for (int i = 0; i < jsapiTickets.size(); i++) {
      if (appid.equals(((JsapiTicketItem)jsapiTickets.get(i)).getAppid()))
        return jsapiTickets.get(i); 
    } 
    return null;
  }
  
  public static void setJsapiTicket(JsapiTicket jsapiTicket) {
    JsapiTicketItem item = null;
    for (int i = 0; i < jsapiTickets.size(); i++) {
      if (appid.equals(((JsapiTicketItem)jsapiTickets.get(i)).getAppid()))
        item = jsapiTickets.get(i); 
    } 
    if (item == null) {
      item = new JsapiTicketItem();
      item.setAppid(appid);
      jsapiTickets.add(item);
    } 
    item.setJsapiTicket(jsapiTicket);
    JsapiTicketItem.setBegin((new Date()).getTime());
  }
}
