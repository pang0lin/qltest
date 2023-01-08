package com.js.oa.security.log.action;

import com.js.oa.security.log.service.LogBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogAction extends Action {
  String domainId;
  
  LogBD service = null;
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    this.domainId = session.getAttribute("domainId").toString();
    this.service = new LogBD();
    String action = request.getParameter("action");
    if ("list".equals(action)) {
      list(request, null);
      return mapping.findForward("list");
    } 
    if ("delete".equals(action)) {
      delete(request);
      list(request, null);
    } else {
      if ("export".equals(action)) {
        export(request);
        return mapping.findForward("export");
      } 
      if ("search".equals(action)) {
        String export = request.getParameter("exportLog");
        StringBuffer where = new StringBuffer(20);
        String empName = request.getParameter("empName");
        String empOrgName = request.getParameter("empOrgName");
        String oprModule = request.getParameter("oprModule");
        String searchTime = request.getParameter("searchTime");
        String opration = request.getParameter("opration");
        String content = request.getParameter("content");
        String logIP = request.getParameter("logIP");
        String userAccounts = request.getParameter("userAccounts");
        String temp_str = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        temp_str = sdf.format(dt);
        where.append("where po.empId=eo.empId");
        if (empName != null && !"".equals(empName) && !"null".equals(empName))
          where.append(" and po.empName like '%").append(empName).append("%'"); 
        if (empOrgName != null && !"".equals(empOrgName) && !"null".equals(empOrgName))
          where.append(" and po.empOrgName like '%").append(empOrgName).append("%'"); 
        if (!"".equals(oprModule))
          where.append(" and po.moduleSerial='").append(oprModule).append("' "); 
        if (!"".equals(opration))
          where.append(" and po.oprType=").append(opration); 
        if (!"".equals(content))
          where.append(" and po.oprContent like '%").append(content).append("%'"); 
        if (!"".equals(logIP))
          where.append(" and po.logIP like '%").append(logIP).append("%'"); 
        if (!"".equals(userAccounts))
          where.append(" and eo.userAccounts like '%").append(userAccounts).append("%'"); 
        String searchToday = request.getParameter("searchToday");
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          if ("1".equals(searchToday))
            where.append(" and (po.oprStartTime<='").append(String.valueOf(temp_str) + " 23:59:59")
              .append("' and po.oprStartTime>='").append(String.valueOf(temp_str) + " 00:00:00")
              .append("')"); 
        } else if ("1".equals(searchToday)) {
          where.append(" and (po.oprStartTime<=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 23:59:59")
            .append("','') and po.oprStartTime>=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 00:00:00")
            .append("',''))");
        } 
        if ("1".equals(searchTime)) {
          String oprStartTime = request.getParameter("oprStartTime");
          String oprEndTime = request.getParameter("oprEndTime");
          oprStartTime = String.valueOf(oprStartTime) + " 00:00:00";
          oprEndTime = String.valueOf(oprEndTime) + " 23:59:59";
          if (databaseType.indexOf("mysql") >= 0) {
            where.append(" and (po.oprStartTime<='").append(oprEndTime)
              .append("' and po.oprStartTime>='").append(oprStartTime)
              .append("')");
          } else {
            where.append(" and (po.oprStartTime<=JSDB.FN_STRTODATE('").append(oprEndTime)
              .append("','') and po.oprStartTime>=JSDB.FN_STRTODATE('").append(oprStartTime)
              .append("',''))");
          } 
        } 
        request.setAttribute("pageParameters", "action,empName,empOrgName,oprModule,searchTime,oprStartTime,oprEndTime,opration,content,logIP");
        list(request, where);
        return mapping.findForward("list");
      } 
    } 
    return mapping.findForward("list");
  }
  
  private void list(HttpServletRequest request, StringBuffer where) {
    String searchHistory = request.getParameter("searchHistory");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    if (where == null || where.length() < 1) {
      if (where == null)
        where = new StringBuffer(20); 
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      temp_str = sdf.format(dt);
      String searchToday = request.getParameter("searchToday");
      String databaseType = SystemCommon.getDatabaseType();
      where.append("where po.empId=eo.empId ");
      if (databaseType.indexOf("mysql") >= 0) {
        if ("1".equals(searchToday))
          where.append(" and (po.oprStartTime<='").append(String.valueOf(temp_str) + " 23:59:59")
            .append("' and po.oprStartTime>='").append(String.valueOf(temp_str) + " 00:00:00")
            .append("')"); 
      } else if ("1".equals(searchToday)) {
        where.append(" and (po.oprStartTime<=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 23:59:59")
          .append("','') and po.oprStartTime>=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 00:00:00")
          .append("',''))");
      } 
      where.append(" and po.domainId=")
        .append(this.domainId)
        .append(" order by po.oprStartTime desc");
    } else {
      where.append(" and po.domainId=")
        .append(this.domainId)
        .append(" order by po.oprStartTime desc");
    } 
    Page page = null;
    if ("1".equals(searchHistory)) {
      page = new Page(" po.logId,po.oprStartTime,po.empName,po.empOrgName,po.oprSubModule,po.oprContent,po.oprType,po.logIP", 
          " com.js.oa.security.log.po.LogHisPO po, com.js.system.vo.usermanager.EmployeeVO eo ", 
          where.toString());
    } else {
      page = new Page(" po.logId,po.oprStartTime,po.empName,po.empOrgName,po.oprSubModule,po.oprContent,po.oprType,po.logIP", 
          " com.js.oa.security.log.po.LogPO po, com.js.system.vo.usermanager.EmployeeVO eo ", 
          where.toString());
    } 
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    request.setAttribute("logList", list);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,empName,empOrgName,oprModule,searchTime,oprStartTime,oprEndTime,opration,content,logIP,userAccounts,searchHistory,searchToday");
    request.setAttribute("moduleList", this.service.moduleList(this.domainId));
  }
  
  private void delete(HttpServletRequest request) {
    String ids = request.getParameter("ids");
    String timeScan = request.getParameter("timeScan");
    String searchHistory = request.getParameter("searchHistory");
    String tableNameString = "LogPO";
    if ("1".equals(searchHistory))
      tableNameString = "LogHisPO"; 
    StringBuffer where = new StringBuffer();
    if (ids != null && !"".equals(ids)) {
      where.append(" where po.logId in (").append(ids).append(")");
      where.append(" and po.domainId=").append(this.domainId);
    } else {
      String date = getDateStr(timeScan);
      if ("new".equals(timeScan)) {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          where.append(" where po.oprStartTime>='")
            .append(date)
            .append("' and po.domainId=")
            .append(this.domainId);
        } else {
          where.append(" where po.oprStartTime>=JSDB.FN_STRTODATE('")
            .append(date)
            .append("','') and po.domainId=")
            .append(this.domainId);
        } 
      } else if ("all".equals(timeScan)) {
        where.append(" where po.domainId=")
          .append(this.domainId);
      } else {
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          where.append(" where po.oprStartTime<'")
            .append(date)
            .append("' and po.domainId=")
            .append(this.domainId);
        } else {
          where.append(" where po.oprStartTime<JSDB.FN_STRTODATE('")
            .append(date)
            .append("','') and po.domainId=")
            .append(this.domainId);
        } 
      } 
    } 
    this.service.deleteLog(where.toString(), tableNameString);
  }
  
  private void export(HttpServletRequest request) {
    StringBuffer where = new StringBuffer();
    String empName = request.getParameter("empName");
    String empOrgName = request.getParameter("empOrgName");
    String oprModule = request.getParameter("oprModule");
    String searchTime = request.getParameter("searchTime");
    String opration = request.getParameter("opration");
    String content = request.getParameter("content");
    String logIP = request.getParameter("logIP");
    String ids = request.getParameter("ids");
    String userAccounts = request.getParameter("userAccounts");
    String temp_str = "";
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    temp_str = sdf.format(dt);
    String searchToday = request.getParameter("searchToday");
    String searchHistory = request.getParameter("searchHistory");
    String tableName = "LogPO";
    if (!"1".equals(searchToday))
      tableName = "LogHisPO"; 
    String databaseType = SystemCommon.getDatabaseType();
    if (empName != null && !"".equals(empName) && !"null".equals(empName))
      where.append(" where (po.empName like '%").append(empName).append("%'"); 
    if (empOrgName != null && !"".equals(empOrgName) && !"null".equals(empOrgName)) {
      if (where.length() > 0) {
        where.append(" and po.empOrgName like '%").append(empOrgName).append("%')");
      } else {
        where.append(" where po.empOrgName like '%").append(empOrgName).append("%'");
      } 
    } else if (where.length() > 0) {
      where.append(")");
    } else if (ids != null && !"".equals(ids) && !"null".equals(ids)) {
      where.append(" where po.logId in(" + ids + ")");
    } else {
      where.append(" where 1=1");
    } 
    if (databaseType.indexOf("mysql") >= 0) {
      if ("1".equals(searchToday))
        where.append(" and (po.oprStartTime<='").append(String.valueOf(temp_str) + " 23:59:59")
          .append("' and po.oprStartTime>='").append(String.valueOf(temp_str) + " 00:00:00")
          .append("')"); 
    } else if ("1".equals(searchToday)) {
      where.append(" and (po.oprStartTime<=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 23:59:59")
        .append("','') and po.oprStartTime>=JSDB.FN_STRTODATE('").append(String.valueOf(temp_str) + " 00:00:00")
        .append("',''))");
    } 
    if (oprModule != null && !"".equals(oprModule) && !"null".equals(opration))
      where.append(" and po.moduleSerial='").append(oprModule).append("' "); 
    if (opration != null && !"".equals(opration) && !"null".equals(opration))
      where.append(" and po.oprType=").append(opration); 
    if (content != null && !"".equals(content) && !"null".equals(content))
      where.append(" and po.oprContent like '%").append(content).append("%'"); 
    if (logIP != null && !"".equals(logIP) && !"null".equals(logIP))
      where.append(" and po.logIP like '%").append(logIP).append("%'"); 
    if (userAccounts != null && !"".equals(userAccounts) && !"null".equals(userAccounts))
      where.append(" and eo.userAccounts like '%").append(userAccounts).append("%'"); 
    if ("1".equals(searchTime)) {
      String oprStartTime = request.getParameter("oprStartTime");
      String oprEndTime = request.getParameter("oprEndTime");
      oprStartTime = String.valueOf(oprStartTime) + " 00:00:00";
      oprEndTime = String.valueOf(oprEndTime) + " 23:59:59";
      if (databaseType.indexOf("mysql") >= 0) {
        where.append(" and (po.oprStartTime<='").append(oprEndTime)
          .append("' and po.oprStartTime>='").append(oprStartTime)
          .append("')");
      } else {
        where.append(" and (po.oprStartTime<=JSDB.FN_STRTODATE('").append(oprEndTime)
          .append("','') and po.oprStartTime>=JSDB.FN_STRTODATE('").append(oprStartTime)
          .append("',''))");
      } 
    } 
    where.append("  order by po.oprStartTime desc");
    request.setAttribute("logList", this.service.export(String.valueOf(tableName) + "&" + where.toString()));
  }
  
  private String getDateStr(String timeScan) {
    Calendar calendar = Calendar.getInstance();
    if ("week".equals(timeScan) || "new".equals(timeScan)) {
      calendar.add(6, -7);
    } else if ("month".equals(timeScan)) {
      calendar.add(2, -1);
    } else if ("tmonth".equals(timeScan)) {
      calendar.add(2, -3);
    } else if ("hyear".equals(timeScan)) {
      calendar.add(2, -6);
    } else if ("year".equals(timeScan)) {
      calendar.add(2, -12);
    } 
    return String.valueOf(calendar.get(1)) + "-" + (calendar.get(2) + 1) + "-" + calendar.get(5);
  }
}
