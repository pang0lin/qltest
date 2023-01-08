package com.js.thread;

import com.js.sms.WXGSMModem;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class LdSMSTask extends TimerTask {
  public void run() {
    Long b = Long.valueOf((new Date()).getTime() - 120000L);
    Long e = Long.valueOf((new Date()).getTime() - 1800000L);
    String sql = "select result from MS_HISTORY where sendLong<" + b + " and sendLong>" + e + " and (flag=0 or flag=1000 or flag=3000) and result is not null order by sendLong";
    DataSourceBase base = new DataSourceBase();
    WXGSMModem w = new WXGSMModem();
    Map<String, String> map = new HashMap<String, String>();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next())
        map.put(rs.getString(1), w.royoSendFlag(rs.getString(1))); 
      base.end();
    } catch (Exception ex) {
      base.end();
      ex.printStackTrace();
    } 
    try {
      base.begin();
      for (String key : map.keySet()) {
        String s = (map.get(key) == null) ? "0" : map.get(key);
        base.addBatch("update MS_HISTORY set flag=" + s + " where result='" + key + "'");
      } 
      base.executeBatch();
      base.end();
    } catch (Exception ex) {
      base.end();
      ex.printStackTrace();
    } 
  }
}
