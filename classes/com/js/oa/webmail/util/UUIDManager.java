package com.js.oa.webmail.util;

import com.js.oa.webmail.po.WebMailTemp;
import com.js.oa.webmail.service.WebMailBD;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UUIDManager {
  private static UUIDManager uuIDManager;
  
  private Map uuIDMap = new HashMap<Object, Object>();
  
  private static Map uuIDMapTemp = new HashMap<Object, Object>();
  
  public static UUIDManager getInstance() {
    if (uuIDManager == null) {
      uuIDManager = new UUIDManager();
      uuIDManager.init();
    } 
    return uuIDManager;
  }
  
  public Map getUUIDMap() {
    return this.uuIDMap;
  }
  
  public static Map initUUIDMap(String userId) {
    WebMailBD wmd = new WebMailBD();
    try {
      List<String> list = wmd.getAllUUIDListById(userId);
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          String temp = list.get(i);
          uuIDMapTemp.put(temp, "");
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return uuIDMapTemp;
  }
  
  public void init() {
    try {
      this.uuIDMap.clear();
      WebMailBD wmd = new WebMailBD();
      List<WebMailTemp> list = wmd.getAllUUIDList();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          WebMailTemp temp = list.get(i);
          this.uuIDMap.put(temp.getMailId(), "");
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
