package com.js.oa.workplan.util;

import com.js.system.service.messages.RemindUtil;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class workPlanRemind {
  public void workPlanRe() throws ParseException {
    String title = "你有工作动态需要填写！";
    String url = "/jsoa/WorkplanAction.do?action=addplan";
    String adminIdString = getUserids();
    String userIds = adminIdString;
    String moduleType = "work_plan_remind";
    String send_UserName = "系统提醒";
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    RemindUtil.sendMessageToUsers2(title, url, userIds, moduleType, new Date(), dateTimeFormat.parse("2050-01-01 00:00:00"), send_UserName, Long.valueOf(0L), 1);
  }
  
  public String getUserids() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rsgroup = null;
    ResultSet rsproxy = null;
    String sqlgroup = "select groupleaderid from oa_workplangroup";
    String sqlproxy = "select  proxyid from  oa_workplanproxy";
    String userids = "";
    String groupids = "";
    String proxyids = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rsgroup = stmt.executeQuery(sqlgroup);
      while (rsgroup.next())
        groupids = String.valueOf(groupids) + rsgroup.getString(1); 
      if (!"".equals(groupids) && groupids != null) {
        groupids = groupids.substring(1, groupids.length() - 1);
        groupids = groupids.replace("$$", ",");
      } 
      rsproxy = stmt.executeQuery(sqlproxy);
      while (rsproxy.next())
        proxyids = String.valueOf(proxyids) + rsproxy.getString(1) + ","; 
      userids = String.valueOf(groupids) + "," + proxyids;
      if (userids != null && userids.endsWith(","))
        userids = userids.substring(0, userids.length() - 1); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return userids;
  }
}
