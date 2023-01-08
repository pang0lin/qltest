package com.qq.weixin.mp.service.impl;

import com.js.oa.userdb.util.DbOpt;
import com.qq.weixin.mp.service.WeixinMessageUrlGetter;

public class WeixinMessageUrlGetterImpl_WorkReportPostil implements WeixinMessageUrlGetter {
  public String getWeiXinUrl(String messageUrl, String dataId, String currentUserId) {
    String url = "";
    String repId = "";
    String repType = "";
    String leaderId = "";
    if (messageUrl != null && !"".equals(messageUrl)) {
      repType = messageUrl.substring(messageUrl.indexOf("reportType=") + 11, messageUrl.length());
      leaderId = messageUrl.substring(messageUrl.indexOf("receiveRecordId=") + 16, messageUrl.indexOf("&status="));
      String sql = "SELECT report_id FROM rep_leader WHERE reportleader_id=?";
      repId = getValue(sql, new String[] { leaderId });
      if (repId == null || "".equals(repId))
        repId = leaderId; 
      url = "/weixin/workReport/weixinReadWMReport.jsp?repId=" + repId + "&repUser=mine&backto=message&repType=" + 
        repType;
    } 
    return url;
  }
  
  public String[] getRemindInfo(String messageTitle, String messageUrl, String dataid, String emp_Id) {
    return new String[] { messageTitle, messageTitle, "" };
  }
  
  private static String getValue(String sql, String[] para) {
    String value = "";
    String[] result = (String[])null;
    DbOpt db = null;
    try {
      db = new DbOpt();
      result = db.executeQueryToStrArr1PS(sql, para);
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
