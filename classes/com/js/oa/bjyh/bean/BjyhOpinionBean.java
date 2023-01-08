package com.js.oa.bjyh.bean;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.page.sql.Page;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class BjyhOpinionBean {
  public List getOpinionList(HttpServletRequest request, String userAccount) {
    String sql = "select emp_id from org_employee where useraccounts='" + userAccount + "'";
    DbOpt dbopt = null;
    dbopt = new DbOpt();
    List list = null;
    try {
      String[][] empid = dbopt.executeQueryToStrArr2(sql, 1);
      if (empid != null && empid.length > 0) {
        String para = " distinct a.worktable_id,a.workrecord_id,b.workflowprocessname,a.worksubmittime,e.empname,a.wf_curemployee_id,a.initactivity ";
        String from = " jsf_work a right join JSF_WORKFLOWPROCESS b on a.workprocess_id=b.wf_workflowprocess_id right join org_employee e on a.wf_submitemployee_id=e.emp_id ";
        String where = " where a.wf_curemployee_id=" + empid[0][0];
        where = String.valueOf(where) + " order by a.worksubmittime desc";
        list = list(request, para, from, where);
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
    return list;
  }
  
  public String[] getComment(String tableid, String recordid, String empid) {
    String comment = "";
    String[] commenttemp = new String[2];
    String sql = "select d.empname,c.activityname,a.dealwithemployeecomment from jsf_dealwithcomment a left join jsf_dealwith b on a.wf_dealwith_id=b.wf_dealwith_id left join JSF_ACTIVITY c on b.activity_id=c.wf_activity_id left join org_employee d on a.dealwithemployee_id=d.emp_id where d.emp_id=" + 


      
      empid + " and b.databasetable_id=" + tableid + " and b.databaserecord_id=" + recordid;
    DbOpt dbopt = null;
    dbopt = new DbOpt();
    try {
      String[][] temp = dbopt.executeQueryToStrArr2(sql, 3);
      if (temp != null && temp.length > 0) {
        for (int i = 0; i < temp.length; i++) {
          if (temp[i][1] != null && !"".equals(temp[i][1]) && !"null".equalsIgnoreCase(temp[i][1])) {
            if (temp[i][2] != null && !"".equals(temp[i][2]) && !"null".equalsIgnoreCase(temp[i][2]))
              comment = String.valueOf(comment) + temp[i][1] + ":" + temp[i][2] + ","; 
          } else if (temp[i][2] != null && !"".equals(temp[i][2]) && !"null".equalsIgnoreCase(temp[i][2])) {
            comment = String.valueOf(comment) + temp[i][2] + ",";
          } 
        } 
        commenttemp[0] = temp[0][0];
        commenttemp[1] = comment;
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
    return commenttemp;
  }
  
  public String[] getStatus(String tableid, String recordid, String empid) {
    String comment = "";
    String[] statustemp = new String[2];
    String sql = "select a.WORK_HANGUP,a.workstatus  from jsf_work a right join JSF_WORKFLOWPROCESS b on a.workprocess_id=b.wf_workflowprocess_id  where a.worktable_id=" + 
      
      tableid + " and a.workrecord_id=" + recordid + " and a.wf_curemployee_id=" + empid;
    DbOpt dbopt = null;
    dbopt = new DbOpt();
    try {
      String[][] temp = dbopt.executeQueryToStrArr2(sql, 2);
      statustemp[0] = temp[0][0];
      statustemp[1] = temp[0][1];
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
    return statustemp;
  }
  
  private List list(HttpServletRequest request, String para, String from, String where) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", 
          String.valueOf(offset));
      request.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", 
        String.valueOf(page.getRecordCount()));
    request.setAttribute("pageParameters", "action,userAccount");
    return list;
  }
}
