package com.js.oa.jsflow.action;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.RemindUtil;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WorkFlowOpinionRemind {
  public void messageRemind(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String recordId = request.getParameter("recordId");
    String tableId = request.getParameter("tableId");
    String processid = request.getParameter("processId");
    String userId = session.getAttribute("userId").toString();
    String userName = (String)session.getAttribute("userName");
    String[][] idTemp = (String[][])null;
    Object[] obj = (Object[])null;
    String userid = "";
    String workid = "";
    String[][] workidtemp = (String[][])null;
    String url = "";
    String processName = getProcessName(processid);
    String title = String.valueOf(userName) + "对于" + processName + "的意见等待您查看!";
    String sql = "select distinct wf_curemployee_id from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + recordId + " and workprocess_id=" + processid + " and workstatus<>0 and wf_curemployee_id<>" + userId;
    String sqltemp = "";
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      idTemp = dbopt.executeQueryToStrArr2(sql, 1);
      if (idTemp != null && idTemp.length > 0)
        for (int i = 0; i < idTemp.length; i++) {
          String[] arrayOfString = idTemp[i];
          userid = arrayOfString[0].toString();
          sqltemp = "select distinct wf_work_id from jsf_work where worktable_id=" + tableId + " and workrecord_id=" + recordId + " and wf_curemployee_id=" + userid + " and workprocess_id=" + processid;
          workidtemp = dbopt.executeQueryToStrArr2(sqltemp, 1);
          if (workidtemp != null && workidtemp.length > 0) {
            workid = workidtemp[0][0];
            url = "/jsoa/jsflow/item/jump_dealwith.jsp?status=0&workId=" + workid;
            RemindUtil.sendMessageToUsers2(title, url, userid, "jsflow", new Date(), (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse("2050-01-01 00:00:00"), userName, Long.valueOf(workid), 1, 0);
          } 
        }  
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  public boolean userOpinionRemind(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = (String)session.getAttribute("userName");
    boolean flag = true;
    DbOpt dbopt = null;
    String[][] temp = (String[][])null;
    String sql = "select opinionRemind from org_employee where emp_id=" + userId;
    try {
      dbopt = new DbOpt();
      temp = dbopt.executeQueryToStrArr2(sql, 1);
      if (temp != null && temp.length > 0) {
        if (!"1".equals(temp[0][0]))
          flag = false; 
      } else {
        flag = false;
      } 
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return flag;
  }
  
  public String getProcessName(String processId) {
    DbOpt dbopt = null;
    String[][] temp = (String[][])null;
    String processName = "";
    String sql = "select workflowprocessname from JSF_WORKFLOWPROCESS  where wf_workflowprocess_id=" + processId;
    try {
      dbopt = new DbOpt();
      temp = dbopt.executeQueryToStrArr2(sql, 1);
      if (temp != null && temp.length > 0)
        processName = temp[0][0]; 
      dbopt.close();
    } catch (Exception e) {
      if (dbopt != null)
        try {
          dbopt.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return processName;
  }
}
