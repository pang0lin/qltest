package com.js.doc.doc.util;

import com.js.doc.doc.service.SendFileBD;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class WorkFlowDeal {
  public String[] getCurEmpName(String tableId, String recordId) {
    String dealStatus = "办理完毕";
    String dealMenName = "";
    DataSourceBase dsb = new DataSourceBase();
    DataSource ds = dsb.getDataSource();
    Connection conn = null;
    Statement stmt = null;
    StringBuffer empNameStr = new StringBuffer();
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      ResultSet rs = null;
      for (rs = stmt.executeQuery("select empname,workcurstep from org_employee,wf_work where emp_id=wf_curemployee_id and workstatus=0 and worklistcontrol=1  and worktable_id=" + tableId + " and workrecord_id=" + recordId); rs.next(); ) {
        empNameStr.append(String.valueOf(rs.getString(1)) + " ");
        dealStatus = rs.getString(2);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception e) {
      try {
        conn.close();
      } catch (SQLException sQLException) {}
    } 
    String[] dealInfo = { dealStatus, empNameStr.toString() };
    return dealInfo;
  }
  
  public String getCommentByCommFiled(HttpServletRequest request, String commentName) {
    SendFileBD sendFielBD = new SendFileBD();
    HttpSession session = request.getSession(true);
    String tmp = sendFielBD.getCommentByCommFiled(request.getParameter("processId"), request.getParameter("table"), request.getParameter("record"), commentName, (String)session.getAttribute("userId"), (String)session.getAttribute("orgId"), request.getParameter("isEdit"));
    return tmp;
  }
}
