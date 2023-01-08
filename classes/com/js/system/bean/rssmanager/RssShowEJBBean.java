package com.js.system.bean.rssmanager;

import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RssShowEJBBean {
  public List<Map<String, String>> getRssList(Map<String, String> rssSet, Map<String, String> rqInfo) {
    String type = rssSet.get("data.type");
    if ("database".equals(type.toLowerCase()))
      return getRssListFromDatabase(rssSet, rqInfo); 
    return getRssListFromWebservice(rssSet, rqInfo);
  }
  
  private List<Map<String, String>> getRssListFromDatabase(Map<String, String> rssSet, Map<String, String> rqInfo) {
    List<Map<String, String>> rssList = new ArrayList<Map<String, String>>();
    int num = Integer.valueOf((rssSet.get("data.num") == null) ? "10" : rssSet.get("data.num")).intValue();
    String databaseSource = ((String)rssSet.get("data.databaseSource")).toLowerCase();
    String databaseType = ((String)rssSet.get("data.databaseType")).toLowerCase();
    String operate = rssSet.get("data.operate");
    String sql = replaceStr(rqInfo, rssSet.get("data.sql"));
    String executeSql = "";
    if ("oracle".equals(databaseType)) {
      executeSql = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (" + sql + ") A ) WHERE RN BETWEEN 0 AND " + num;
    } else if ("mysql".equals(databaseType)) {
      executeSql = String.valueOf(sql) + " limit 0, " + num;
    } else if ("sqlserver".equals(databaseType)) {
      executeSql = "SELECT * FROM ( " + sql + " ) a WHERE RN BETWEEN 0 AND " + num;
    } 
    IO2File.printFile("rss取值sql：" + executeSql, "rss");
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stat = null;
    try {
      if ("system".equals(databaseSource)) {
        conn = base.getDataSource().getConnection();
      } else {
        conn = base.getDataSource(databaseSource).getConnection();
      } 
      if ("1".equals(operate)) {
        stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(executeSql);
        while (rs.next()) {
          Map<String, String> map = new HashMap<String, String>();
          map.put("title", (rs.getString("title") == null) ? "" : rs.getString("title"));
          map.put("link", (rs.getString("link") == null) ? "" : rs.getString("link").replaceAll("&", "&amp;"));
          map.put("author", (rs.getString("author") == null) ? "" : rs.getString("author"));
          map.put("pubDate", (rs.getString("pubDate") == null) ? "" : ((rs.getString("pubDate").length() > 20) ? 
              rs.getString("pubDate").substring(0, 19) : rs.getString("pubDate")));
          map.put("description", (rs.getString("description") == null) ? "" : rs.getString("description"));
          rssList.add(map);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        stat.close();
        conn.close();
      } catch (Exception e) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception ex) {
            ex.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } 
    return rssList;
  }
  
  private List<Map<String, String>> getRssListFromWebservice(Map<String, String> rssSet, Map<String, String> rqInfo) {
    List<Map<String, String>> rssList = new ArrayList<Map<String, String>>();
    int num = Integer.valueOf((rssSet.get("data.num") == null) ? "10" : rssSet.get("data.num")).intValue();
    return rssList;
  }
  
  public Map<String, String> getRssHeader(Map<String, String> rssSet, Map<String, String> rqInfo) {
    Map<String, String> header = new HashMap<String, String>();
    for (String key : rssSet.keySet()) {
      if (key.startsWith("header."))
        header.put(key.replace("header.", ""), replaceStr(rqInfo, rssSet.get(key))); 
    } 
    return header;
  }
  
  private String replaceStr(Map<String, String> rqInfo, String str) {
    if (str.indexOf("@$@") >= 0) {
      for (String key : rqInfo.keySet())
        str = str.replaceAll("\\@\\$\\@" + key + "\\@\\$\\@", rqInfo.get(key)); 
      while (str.indexOf("@$@[") >= 0 && str.indexOf("]@$@") >= 0) {
        String dateStr = str.substring(str.indexOf("@$@[") + 4, str.indexOf("]@$@"));
        str = str.replaceAll("\\@\\$\\@\\[" + dateStr + "\\]\\@\\$\\@", (new SimpleDateFormat(dateStr)).format(new Date()));
      } 
    } 
    return str.replaceAll("&", "&amp;");
  }
  
  public Map<String, String> getRqInfo(String userId) {
    Map<String, String> rqInfo = new HashMap<String, String>();
    String sql = "SELECT e.empName,e.useraccounts,o.org_id,o.ORGNAME,o.ORGNAMESTRING FROM org_employee e JOIN org_organization_user ou ON e.EMP_ID=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.ORG_ID WHERE e.USERISACTIVE=1 AND e.USERISDELETED=0 AND e.EMP_ID>0 AND e.emp_id=" + 
      
      userId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        rqInfo.put("userId", userId);
        rqInfo.put("userName", (rs.getString(1) == null) ? "" : rs.getString(1));
        rqInfo.put("userAccount", (rs.getString(2) == null) ? "" : rs.getString(2));
        rqInfo.put("orgName", (rs.getString(4) == null) ? "" : rs.getString(4));
        rqInfo.put("orgId", (rs.getString(3) == null) ? "" : rs.getString(3));
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return rqInfo;
  }
}
