package com.js.oa.workplan.action;

import com.js.oa.workplan.po.WorkplanPO;
import com.js.oa.workplan.service.WorkplanService;
import com.js.oa.workplan.service.WorkplanSetService;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

public class WorkplanAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    if ("list".equals(action)) {
      workplanList(request);
      return actionMapping.findForward("list");
    } 
    if ("view".equals(action)) {
      workplanData(request);
      return actionMapping.findForward("view");
    } 
    if ("addplan".equals(action)) {
      WorkplanSetService workplanSetService = new WorkplanSetService();
      boolean isLeader = workplanSetService.userIsLeader(session.getAttribute("userId").toString());
      if (isLeader) {
        request.setAttribute("leaderId", session.getAttribute("userId").toString());
        request.setAttribute("leaderName", session.getAttribute("userName").toString());
      } else {
        List<Object[]> leaderList = (new WorkplanSetService()).getLeaderList(session.getAttribute("userId").toString());
        if (leaderList != null && leaderList.size() == 1) {
          Object[] obj = leaderList.get(0);
          request.setAttribute("leaderId", obj[0].toString());
          request.setAttribute("leaderName", obj[1].toString());
        } else {
          request.setAttribute("leaderList", leaderList);
        } 
      } 
      request.setAttribute("workStatusList", (new WorkplanSetService()).searchStatusList());
      return actionMapping.findForward("addplan");
    } 
    if ("saveclose".equals(action)) {
      WorkplanService workplanService = new WorkplanService();
      String recordId = session.getAttribute("userId").toString();
      String recordName = session.getAttribute("userName").toString();
      Date recordTime = new Date();
      String leaderId = request.getParameter("leaderId");
      String[] leaderInfo = workplanService.getLeaderInfoById(leaderId);
      String workYearMonth = request.getParameter("workYearMonth");
      String[] workType = request.getParameterValues("workType");
      String[] workStatus = request.getParameterValues("workStatus");
      String[] workPlace = request.getParameterValues("workPlace");
      String[] description = request.getParameterValues("description");
      String[] workDateYear = request.getParameterValues("workYear");
      String[] workDateMonth = request.getParameterValues("workMonth");
      String[] workDateDate = request.getParameterValues("workDate");
      List<WorkplanPO> planList = new ArrayList();
      for (int i = 0; i < workDateDate.length; i++) {
        WorkplanPO po = new WorkplanPO();
        po.setLeaderId(leaderId);
        po.setLeaderName(leaderInfo[0]);
        po.setDutyName(leaderInfo[1]);
        po.setOrgName(leaderInfo[2]);
        po.setRecordId(recordId);
        po.setRecordName(recordName);
        po.setWorkYearMonth(workYearMonth);
        po.setWorkDate(DateHelper.string2Date(String.valueOf(workDateYear[i]) + "-" + workDateMonth[i] + "-" + workDateDate[i]));
        po.setWorkType(workType[i]);
        po.setWorkStatus(workStatus[i]);
        po.setWorkPlace(workPlace[i]);
        po.setDescription(description[i]);
        po.setRecordTime(recordTime);
        planList.add(po);
      } 
      workplanService.saveWorkplan(planList);
      return actionMapping.findForward("saveclose");
    } 
    if ("checkdata".equals(action)) {
      String cdate = request.getParameter("cdate");
      String leaderId = request.getParameter("leaderId");
      String checkResult = "";
      if (cdate != null && cdate.length() > 1)
        checkResult = (new WorkplanService()).checkData(cdate, leaderId); 
      try {
        StringBuffer xml = new StringBuffer(1024);
        response.setContentType("text/xml;charset=GBK");
        PrintWriter out = response.getWriter();
        String code = "0";
        xml.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>\n");
        xml.append("<result>\n");
        xml.append("  <message>" + checkResult + "</message>\n");
        xml.append("</result>\n");
        out.print(xml.toString());
        out.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      return null;
    } 
    if ("modifyplan".equals(action)) {
      String workplanId = request.getParameter("workplanId");
      WorkplanPO po = (new WorkplanService()).loadWorkplan(workplanId);
      request.setAttribute("workplan", po);
      request.setAttribute("workStatusList", (new WorkplanSetService()).searchStatusList());
      return actionMapping.findForward("modifyplan");
    } 
    if ("modifysave".equals(action)) {
      String[] workType = request.getParameterValues("workType");
      String[] workStatus = request.getParameterValues("workStatus");
      String[] workPlace = request.getParameterValues("workPlace");
      String[] description = request.getParameterValues("description");
      String[] workDate = request.getParameterValues("workTime");
      WorkplanPO po = new WorkplanPO();
      po.setId(Long.valueOf(request.getParameter("workplanId")).longValue());
      po.setWorkType(request.getParameter("workType"));
      po.setWorkStatus(request.getParameter("workStatus"));
      po.setWorkPlace(request.getParameter("workPlace"));
      po.setDescription(request.getParameter("description"));
      (new WorkplanService()).updateWorkplan(po);
      return actionMapping.findForward("saveclose");
    } 
    if ("delete".equals(action)) {
      String workplanId = request.getParameter("workplanId");
      (new WorkplanService()).deleteWorkplan(workplanId);
      workplanList(request);
      return actionMapping.findForward("list");
    } 
    return actionMapping.findForward("error");
  }
  
  private void workplanData(HttpServletRequest request) {
    String workYearWeek;
    HttpSession session = request.getSession(true);
    int dateOffset = 0;
    String dateOffsetStr = request.getParameter("dateOffset");
    if (dateOffsetStr != null && !"null".equals(dateOffsetStr) && !"".equals(Integer.valueOf(dateOffset)))
      dateOffset = Integer.parseInt(dateOffsetStr); 
    Calendar calendar = Calendar.getInstance();
    calendar.add(6, dateOffset);
    int currentYear = calendar.get(1);
    int currentDayOfWeek = calendar.get(7);
    calendar.add(6, 0 - currentDayOfWeek - 2);
    int currentWeekOfYear = calendar.get(3);
    calendar.add(6, 6);
    currentYear = calendar.get(1);
    if (currentWeekOfYear < 10) {
      workYearWeek = String.valueOf(String.valueOf(currentYear)) + "0" + String.valueOf(currentWeekOfYear);
    } else {
      workYearWeek = String.valueOf(String.valueOf(currentYear)) + String.valueOf(currentWeekOfYear);
    } 
    WorkplanService ws = new WorkplanService();
    String groupId = request.getParameter("groupId");
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String leaderIds = "";
      String sql = "select groupleaderId from oa_workplangroup where id=" + groupId;
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        leaderIds = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
      String viewSql = "vo.empId";
      String fromSql = "com.js.system.vo.usermanager.EmployeeVO vo,com.js.system.vo.usermanager.EmployeeOrgVO evo,com.js.system.vo.organizationmanager.OrganizationVO ovo";
      String whereSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        whereSql = String.valueOf(whereSql) + "where vo.empId=evo.empId and evo.orgId=ovo.orgId and '" + leaderIds + "' like '%$'||vo.empId||'$%' order by ovo.orgIdString ";
      } else {
        whereSql = String.valueOf(whereSql) + "where vo.empId=evo.empId and evo.orgId=ovo.orgId and '" + leaderIds + "' like CONCAT('%$',vo.empId,'$%') order by ovo.orgIdString ";
      } 
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(viewSql, fromSql, whereSql);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List flist = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      request.setAttribute("workplanData", ws.searchWorkplanList(groupId, workYearWeek, flist));
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,groupId");
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
  
  private void workplanList(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String viewSql = "po.id,po.leaderName,po.dutyName,po.orgName,po.workDate,po.workType,po.workStatus,po.workPlace,po.description,po.workYearMonth ";
    String fromSql = " com.js.oa.workplan.po.WorkplanPO po ";
    ManagerService managerBD = new ManagerService();
    WorkplanSetService workplanSetService = new WorkplanSetService();
    boolean isManager = managerBD.hasRight(userId, "04*60*02");
    boolean isLeader = workplanSetService.userIsLeader(session.getAttribute("userId").toString());
    if (isManager) {
      request.setAttribute("isManager", "1");
      whereSql = " where 1=1 ";
    } else if (isLeader) {
      whereSql = " where po.leaderId=" + userId;
    } else {
      List<Object[]> leaderList = (new WorkplanSetService()).getLeaderList(userId);
      if (leaderList != null && leaderList.size() > 0) {
        whereSql = " where (";
        for (int i = 0; i < leaderList.size(); i++) {
          Object[] obj = leaderList.get(i);
          if (i == 0) {
            whereSql = String.valueOf(whereSql) + " po.leaderId=" + obj[0].toString();
          } else {
            whereSql = String.valueOf(whereSql) + " or po.leaderId=" + obj[0].toString();
          } 
        } 
        whereSql = String.valueOf(whereSql) + ")";
      } else {
        whereSql = " where 1>2 ";
      } 
    } 
    String searchName = request.getParameter("searchName");
    String searchDate = request.getParameter("searchDate");
    if (searchName != null && !"null".equals(searchName))
      whereSql = String.valueOf(whereSql) + " and po.leaderName like '%" + searchName + "%' "; 
    if ("1".equals(searchDate)) {
      String startDate = request.getParameter("startDate");
      String endDate = request.getParameter("endDate");
      startDate = DateHelper.date2String(DateHelper.string2Date(startDate, "yyyy/MM/dd"));
      endDate = DateHelper.date2String(DateHelper.string2Date(endDate, "yyyy/MM/dd"));
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        whereSql = String.valueOf(whereSql) + " and (po.workDate>=to_date('" + startDate + "','yyyy-MM-dd') and po.workDate<=to_date('" + endDate + "','yyyy-MM-dd'))";
      } else {
        whereSql = String.valueOf(whereSql) + " and (po.workDate>='" + startDate + "' and po.workDate<='" + endDate + "')";
      } 
    } 
    String whereSql = String.valueOf(whereSql) + " order by po.workYearMonth desc,po.workDate desc";
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("workplanList", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,searchName,startDate,endDate,searchDate");
  }
}
