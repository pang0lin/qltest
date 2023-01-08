package com.qq.weixin.mp.service.impl;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_WorkReport implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    String repId = "";
    String repType = "";
    String leaderId = "";
    if (messageUrl != null && !"".equals(messageUrl)) {
      repType = messageUrl.substring(messageUrl.indexOf("reportType=") + 11, messageUrl.indexOf("&fromDesktop="));
      leaderId = messageUrl.substring(messageUrl.indexOf("receiveRecordId=") + 16, messageUrl.indexOf("&status="));
      String sql = "SELECT report_id FROM rep_leader WHERE reportleader_id=" + leaderId;
      repId = getValue(sql);
      url = "/weixin/workReport/weixinReadWMReport.jsp?repId=" + repId + "&repUser=other&backto=message&repType=" + 
        repType + "&leaderId=" + leaderId;
    } 
    return url;
  }
  
  public String[] getRemindInfo(String messageTitle, String messageUrl, String dataid, String emp_Id) {
    String empName = "";
    String messageBody = "";
    if (messageUrl != null && !"".equals(messageUrl)) {
      String leaderId = messageUrl.substring(messageUrl.indexOf("receiveRecordId=") + 16, messageUrl.indexOf("&status="));
      String sql = "SELECT reportempname FROM rep_report WHERE report_id=(SELECT report_id FROM rep_leader WHERE reportleader_id=" + leaderId + ")";
      empName = getValue(sql);
      if (!"".equals(empName)) {
        messageBody = String.valueOf(empName) + "的" + messageTitle + "需要您的办理！";
      } else {
        messageBody = "收到" + messageTitle + "需要您的办理！";
      } 
    } 
    return new String[] { messageTitle, messageBody, "" };
  }
  
  private static String getValue(String sql) {
    String value = "";
    String[] result = (String[])null;
    DbOpt db = null;
    try {
      db = new DbOpt();
      result = db.executeQueryToStrArr1(sql);
      db.close();
    } catch (Exception e) {
      if (db != null)
        try {
          db.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    if (result != null && result.length > 0)
      value = result[0]; 
    return value;
  }
}
