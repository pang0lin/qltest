package com.js.oa.security.log.action;

import com.js.oa.security.log.service.LogCountBD;
import com.js.oa.security.log.util.CountUtil;
import com.js.util.config.SystemCommon;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogCountAction extends Action {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    String orglevel = SystemCommon.getLogCount();
    String action = request.getParameter("action");
    String flag = (request.getParameter("flag") == null) ? "m" : request.getParameter("flag");
    String beginDateTime = (request.getParameter("begin") == null) ? "" : request.getParameter("begin");
    String endDateTime = (request.getParameter("end") == null) ? "" : request.getParameter("end");
    if (request.getParameter("beginDate") != null && request.getParameter("endDate") != null && 
      request.getParameter("searchTime") != null && request.getParameter("searchTime").equals("1")) {
      beginDateTime = request.getParameter("beginDate");
      endDateTime = request.getParameter("endDate");
      flag = "";
      request.setAttribute("searchTime", "1");
    } 
    String[] dateStr = (new CountUtil()).getBeginAndEndDate(flag, beginDateTime, endDateTime);
    String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId").replace("**", ",").replace("*", "");
    LogCountBD bd = new LogCountBD();
    if ("orgList".equals(action)) {
      String dateString = "部门";
      String dateAllStr = "部门";
      String viewSql = "orgName";
      int num = 0;
      try {
        SimpleDateFormat md = new SimpleDateFormat("MM月dd日");
        Date beginDate = this.ymd.parse(dateStr[0]);
        Date endDate = this.ymd.parse(dateStr[1]);
        for (long l = beginDate.getTime(); l <= endDate.getTime(); l += 86400000L) {
          dateAllStr = String.valueOf(dateAllStr) + "," + md.format(new Date(l));
          dateString = String.valueOf(dateString) + "," + this.ymd.format(new Date(l));
          viewSql = String.valueOf(viewSql) + ",'' as d" + l;
          num++;
        } 
        SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        request.setAttribute("thisMonth", ym.format(calendar.getTime()));
        calendar.add(2, -1);
        request.setAttribute("lastMonth", ym.format(calendar.getTime()));
        calendar.add(2, 2);
        request.setAttribute("nextMonth", ym.format(calendar.getTime()));
      } catch (Exception e) {
        e.printStackTrace();
      } 
      dateAllStr = String.valueOf(dateAllStr) + ",平均上线人数,上线率";
      viewSql = String.valueOf(viewSql) + ",'' pingjun,'' baifen,org_id,orglevel";
      String fromSql = "org_organization";
      String whereSql = "orgstatus=0" + ("".equals(orglevel) ? "" : " and orglevel=0");
      if (!orgId.equals("")) {
        whereSql = String.valueOf(whereSql) + " and (1<>1 ";
        String[] orgs = orgId.split(",");
        for (int i = 0; i < orgs.length; i++)
          whereSql = String.valueOf(whereSql) + " or orgIdString like '%$" + orgs[i] + "$%'"; 
        whereSql = String.valueOf(whereSql) + ")";
      } 
      String orderBy = "orgIdString";
      PageUtil page = new PageSqlUtil();
      List<Object> list = page.list(request, viewSql, fromSql, whereSql, orderBy);
      request.setAttribute("dataList", bd.getOrgLog(list, dateStr[0], dateStr[1], orglevel, Integer.valueOf(num)));
      request.setAttribute("headTitle", dateAllStr);
      request.setAttribute("dateStr", dateString);
      request.setAttribute("flag", flag);
      request.setAttribute("orgId", (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId"));
      request.setAttribute("orgName", (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName"));
      request.setAttribute("begin", beginDateTime);
      request.setAttribute("end", endDateTime);
    } else if ("downloadCount".equals(action)) {
      String empName = (request.getParameter("empName") == null) ? "" : request.getParameter("empName");
      String searchTime = (request.getParameter("searchTime") == null) ? "" : request.getParameter("searchTime");
      String headTitle = "人员,部门,周下载量,月下载量,半年下载量,年下载量,总下载量 ";
      String viewSql = "e.empName,o.orgname,e.emp_id,'' month,'' halfYear,'' year,'' sum";
      String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id";
      String whereSql = "1=1";
      String orderBy = "o.ORGIDSTRING";
      PageUtil page = new PageSqlUtil();
      List<Object> list = page.list(request, viewSql, fromSql, whereSql, orderBy);
      request.setAttribute("dataList", bd.getDownloadAccount(list, beginDateTime, endDateTime, empName, searchTime));
      request.setAttribute("headTitle", headTitle);
    } else if ("downloadCountSearch".equals(action)) {
      List<Object> list;
      String headTitle, empName = (request.getParameter("empName") == null) ? "" : request.getParameter("empName");
      String searchTime = (request.getParameter("searchTime") == null) ? "" : request.getParameter("searchTime");
      if (!searchTime.equals("1")) {
        headTitle = "人员,部门,周下载量,月下载量,半年下载量,年下载量,总下载量 ";
        String viewSql = "e.empName,o.orgname,e.emp_id,'' month,'' halfYear,'' year,'' sum";
        String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id";
        String whereSql = "e.empName like '%" + empName + "%'";
        String orderBy = "o.ORGIDSTRING";
        PageUtil page = new PageSqlUtil();
        list = page.list(request, viewSql, fromSql, whereSql, orderBy);
      } else {
        headTitle = "人员,部门,下载量 ";
        String viewSql = "e.empName,o.orgname,e.emp_id";
        String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id";
        String whereSql = "e.empName like '%" + empName + "%'";
        String orderBy = "o.ORGIDSTRING";
        PageUtil page = new PageSqlUtil();
        list = page.list(request, viewSql, fromSql, whereSql, orderBy);
      } 
      request.setAttribute("dataList", bd.getDownloadAccount(list, beginDateTime, endDateTime, empName, searchTime));
      request.setAttribute("headTitle", headTitle);
    } else if ("orgShow".equals(action)) {
      String viewSql = "e.empName,'' kaishi,'' tuichu,e.emp_id";
      String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id";
      String whereSql = "e.USERISACTIVE=1 AND e.USERISDELETED=0";
      if (orglevel.equals("")) {
        whereSql = String.valueOf(whereSql) + " and o.org_id=" + orgId;
      } else {
        whereSql = String.valueOf(whereSql) + " and o.ORGIDSTRING LIKE '%$" + orgId + "$%'";
      } 
      String orderBy = "o.ORGIDSTRING ,e.USERORDERCODE , e.EMPDUTYLEVEL ,e.EMPNAME";
      List<Object> list = (new PageSqlUtil(20)).list(request, viewSql, fromSql, whereSql, orderBy);
      request.setAttribute("dataList", bd.getLogin(list, dateStr[0], dateStr[1]));
    } else if (!"empList".equals(action)) {
      if ("exportLogin".equals(action)) {
        String dateString = "部门";
        String dateAllStr = "部门";
        String viewSql = "orgName";
        int num = 0;
        try {
          SimpleDateFormat md = new SimpleDateFormat("MM月dd日");
          Date beginDate = this.ymd.parse(dateStr[0]);
          Date endDate = this.ymd.parse(dateStr[1]);
          for (long l = beginDate.getTime(); l <= endDate.getTime(); l += 86400000L) {
            dateAllStr = String.valueOf(dateAllStr) + "," + md.format(new Date(l));
            dateString = String.valueOf(dateString) + "," + this.ymd.format(new Date(l));
            viewSql = String.valueOf(viewSql) + ",'' as d" + l;
            num++;
          } 
          SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(beginDate);
          request.setAttribute("thisMonth", ym.format(calendar.getTime()));
          calendar.add(2, -1);
          request.setAttribute("lastMonth", ym.format(calendar.getTime()));
          calendar.add(2, 2);
          request.setAttribute("nextMonth", ym.format(calendar.getTime()));
        } catch (Exception e) {
          e.printStackTrace();
        } 
        dateAllStr = String.valueOf(dateAllStr) + ",平均上线人数,上线率";
        viewSql = String.valueOf(viewSql) + ",'' pingjun,'' baifen,org_id,orglevel";
        String fromSql = "org_organization";
        String whereSql = "orgstatus=0" + ("".equals(orglevel) ? "" : " and orglevel=0");
        if (!orgId.equals("")) {
          whereSql = String.valueOf(whereSql) + " and (1<>1 ";
          String[] orgs = orgId.split(",");
          for (int i = 0; i < orgs.length; i++)
            whereSql = String.valueOf(whereSql) + " or orgIdString like '%$" + orgs[i] + "$%'"; 
          whereSql = String.valueOf(whereSql) + ")";
        } 
        String orderBy = "orgIdString";
        List<Object> dataList = list(request, viewSql, fromSql, whereSql, orderBy);
        request.setAttribute("dataList", bd.getOrgLog(dataList, dateStr[0], dateStr[1], orglevel, Integer.valueOf(num)));
        request.setAttribute("headTitle", dateAllStr);
        request.setAttribute("dateStr", dateString);
        request.setAttribute("flag", flag);
        request.setAttribute("orgId", (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId"));
        request.setAttribute("orgName", (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName"));
        request.setAttribute("begin", dateStr[0]);
        request.setAttribute("end", dateStr[1]);
        request.setAttribute("num", Integer.valueOf(num));
      } else if ("exportDownload".equals(action)) {
        List<Object> list;
        String headTitle, empName = request.getParameter("empName").equals("null") ? "" : request.getParameter("empName");
        String searchTime = request.getParameter("searchTime").equals("null") ? "" : request.getParameter("searchTime");
        if (!searchTime.equals("1")) {
          headTitle = "人员,部门,周下载量,月下载量,半年下载量,年下载量,总下载量 ";
          String viewSql = "e.empName,o.orgname,e.emp_id,'' month,'' halfYear,'' year,'' sum";
          String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id";
          String whereSql = "e.empName like '%" + empName + "%'";
          String orderBy = "o.ORGIDSTRING";
          PageUtil page = new PageSqlUtil();
          list = list(request, viewSql, fromSql, whereSql, orderBy);
        } else {
          headTitle = "人员,部门,下载量 ";
          String viewSql = "e.empName,o.orgname,e.emp_id";
          String fromSql = "org_employee e JOIN org_organization_user ou ON e.emp_id=ou.EMP_ID JOIN org_organization o ON ou.ORG_ID=o.org_id";
          String whereSql = "e.empName like '%" + empName + "%'";
          String orderBy = "o.ORGIDSTRING";
          list = list(request, viewSql, fromSql, whereSql, orderBy);
        } 
        request.setAttribute("empName", empName);
        request.setAttribute("dataList", bd.getDownloadAccount(list, beginDateTime, endDateTime, empName, searchTime));
        request.setAttribute("headTitle", headTitle);
      } 
    } 
    return mapping.findForward(action);
  }
  
  public List<Object> list(HttpServletRequest request, String select, String from, String where, String orderBy) {
    List<Object> dataList = new ArrayList();
    try {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        String sql = "select " + select + " from " + from + " where " + (where.equals("") ? "1=1" : where) + ("".equals(orderBy) ? "" : (" order by " + orderBy));
        ResultSet rs = base.executeQuery(sql);
        rs = base.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
          String[] arrayOfString = new String[columnCount];
          for (int i = 0; i < columnCount; i++)
            arrayOfString[i] = (rs.getString(i + 1) == null) ? "" : rs.getString(i + 1); 
          dataList.add(arrayOfString);
        } 
        rs.close();
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
      String url = "";
      Enumeration<String> pNames = request.getParameterNames();
      while (pNames.hasMoreElements()) {
        String name = pNames.nextElement();
        if (!name.equals("pager.offset")) {
          String value = (request.getParameter(name) == null) ? "" : request.getParameter(name);
          url = String.valueOf(url) + "&" + name + "=" + value;
        } 
      } 
      request.setAttribute("paramUrl", url.replace("&1=1", ""));
      request.setAttribute("pageUtil", this);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return dataList;
  }
}
