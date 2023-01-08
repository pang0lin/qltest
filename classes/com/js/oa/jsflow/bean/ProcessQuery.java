package com.js.oa.jsflow.bean;

import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public class ProcessQuery {
  public void query(HttpServletRequest request, String orgId) {
    String orgname = getOrgName(orgId);
    String para = "b.wf_work_id,b.worktable_id,b.workrecord_id,b.workfiletype,b.workstatus,b.worksubmitperson,b.submitorg,b.workcurstep,b.worksubmittime,workactivity,b.WORK_HANGUP";
    String from = " jsf_workflowprocess a LEFT JOIN jsf_work b ON a.wf_workflowprocess_id = b.workprocess_id LEFT JOIN jsf_package c ON c.wf_package_id = a.wf_package_id";
    String where = " WHERE c.wf_module_id=1 AND workstartflag=1 AND WORKLISTCONTROL=1 AND WORKDELETE=0 AND worktype=1 AND workstatus<>-2 and b.submitorg like '%" + orgname + "%'";
    String title = request.getParameter("title");
    String submitperson = request.getParameter("submitperson");
    String org = request.getParameter("org");
    String searchBeginDate = request.getParameter("searchBeginDate");
    String searchEndDate = request.getParameter("searchEndDate");
    String checkbox = request.getParameter("checkbox");
    String status = request.getParameter("status");
    DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
    if (!"".equals(title) && !"null".equals(title) && title != null)
      where = String.valueOf(where) + " and b.workfiletype like '%" + title + "%'"; 
    if (!"".equals(submitperson) && !"null".equals(submitperson) && submitperson != null)
      where = String.valueOf(where) + " and b.worksubmitperson like '%" + submitperson + "%'"; 
    if (!"".equals(org) && !"null".equals(org) && org != null)
      where = String.valueOf(where) + " and b.submitorg like '%" + org + "%'"; 
    if (!"".equals(status) && !"null".equals(status) && status != null)
      where = String.valueOf(where) + " and b.workstatus=" + status; 
    String dataType = SystemCommon.getDatabaseType();
    if ("1".equals(checkbox))
      try {
        Date d = new Date(searchBeginDate);
        Date d2 = new Date(searchEndDate);
        String s = dtm.format(d);
        String e = dtm.format(d2);
        if (dataType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and b.worksubmittime between '" + s + " 00:00:00' and '" + 
            e + " 23:59:59' ";
        } else {
          where = String.valueOf(where) + " and b.worksubmittime between JSDB.FN_STRTODATE('" + s + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
            e + " 23:59:59','L') ";
        } 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    where = String.valueOf(where) + " order by b.wf_work_id desc";
    request.setAttribute("title", title);
    request.setAttribute("submitperson", submitperson);
    request.setAttribute("org", org);
    request.setAttribute("searchBeginDate", searchBeginDate);
    request.setAttribute("searchEndDate", searchEndDate);
    request.setAttribute("checkbox", checkbox);
    request.setAttribute("status", status);
    List list = list(request, para, from, where);
    request.setAttribute("list", list);
  }
  
  public String getdealperson(String recordid, String tableid, String initactivity) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String namestr = "";
    String sql = "SELECT b.emp_id,b.empname FROM jsf_work a LEFT JOIN org_employee b ON a.wf_curemployee_id=b.emp_id WHERE a.worktable_id=? AND a.workrecord_id=? AND a.WORKLISTCONTROL=1 AND a.WORKDELETE=0 and initactivity=?";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tableid);
      pstmt.setString(2, recordid);
      pstmt.setString(3, initactivity);
      rs = pstmt.executeQuery();
      while (rs.next())
        namestr = String.valueOf(namestr) + rs.getString(2) + ","; 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return namestr;
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
    request.setAttribute("pageParameters", "action,title,submitperson,org,submittime,activityname,dealperson,status,searchBeginDate,searchEndDate");
    return list;
  }
  
  public static void main(String[] args) {
    try {
      String searchBeginDate = "2017/11/21";
      Date d = new Date(searchBeginDate);
      DateFormat dtm = new SimpleDateFormat("yyyy-MM-dd");
      String s = dtm.format(d);
      System.out.println("s:" + s);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  private String getOrgName(String orgId) {
    String orgname = "";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sql = "SELECT orgnamestring FROM org_organization WHERE org_id=?";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, orgId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        orgname = rs.getString(1);
        if (orgname.indexOf(".") > 0)
          orgname = orgname.substring(0, orgname.indexOf(".")); 
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return orgname;
  }
}
