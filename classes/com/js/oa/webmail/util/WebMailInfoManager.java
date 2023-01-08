package com.js.oa.webmail.util;

import com.js.oa.webmail.po.WebMail;
import com.js.oa.webmail.service.WebMailBD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebMailInfoManager {
  private static WebMailInfoManager webMailAccManager;
  
  private static Map webMailInfoMap = new HashMap<Object, Object>();
  
  public static final String INPUT_BOX = "0";
  
  public static final String SEND_BOX = "1";
  
  public static final String DROP_BOX = "2";
  
  public static final String BAK_BOX = "3";
  
  public static WebMailInfoManager getInstance() {
    if (webMailAccManager == null) {
      webMailAccManager = new WebMailInfoManager();
      webMailAccManager.init();
    } 
    return webMailAccManager;
  }
  
  public Map getMailInfoMap() {
    return webMailInfoMap;
  }
  
  public int getMailSize(String mailId) {
    int size = 0;
    try {
      WebMail temp = (WebMail)webMailInfoMap.get(mailId);
      if (temp != null)
        size = temp.getMailSize(); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return size;
  }
  
  public void init() {
    try {
      webMailInfoMap.clear();
      WebMailBD wmd = new WebMailBD();
      List<WebMail> list = wmd.getWebMailList();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          WebMail temp = list.get(i);
          webMailInfoMap.put(temp.getMailId(), temp);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
