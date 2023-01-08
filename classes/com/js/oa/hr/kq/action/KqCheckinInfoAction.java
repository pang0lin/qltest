package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.bean.KqCheckinInfoBean;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.page.sql.Page;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqCheckinInfoAction extends Action {
  private final int PAGESIZE = 15;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, Exception {
    HttpSession session = request.getSession(true);
    String action = (request.getParameter("action") == null) ? "list" : request.getParameter("action");
    String databaseType = SystemCommon.getDatabaseType();
    String tag = "list";
    if (action.equals("list")) {
      list(request, session, databaseType);
    } else if ("outsidelist".equals(action)) {
      tag = "outsidelist";
      outsidelist(request, session, databaseType);
    } else if ("outsidereadload".equals(action)) {
      tag = "outsidereadload";
      outsidereadload(request, session);
    } else if ("kqquery".equals(action)) {
      tag = "kqquery";
      kqquery(request, session, databaseType);
    } else if ("kqquerygr".equals(action)) {
      tag = "kqquerygr";
      kqquerygr(request, session, databaseType);
    } else if ("exportlist".equals(action)) {
      tag = "exportlist";
      exportlist(request, session, databaseType);
    } else if ("exportoutsidelist".equals(action)) {
      tag = "exportoutsidelist";
      exportoutsidelist(request, session, databaseType);
    } else if ("kqqueryexport".endsWith(action)) {
      tag = "kqqueryexport";
      kqqueryexport(request, session, databaseType);
    } else if ("kqquerygrexport".endsWith(action)) {
      tag = "kqquerygrexport";
      kqquerygrexport(request, session, databaseType);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request, HttpSession session, String databaseType) throws Exception {
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / 15 + 1;
    StringBuffer sb = new StringBuffer("where emp.userAccounts=k.userId  ");
    String userId = request.getParameter("userId");
    String userName = request.getParameter("userName");
    String orgId = request.getParameter("orgId");
    String orgName = request.getParameter("orgName");
    String position = request.getParameter("position");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String byTime = request.getParameter("byTime");
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    if (userId != null && !"".equals(userId))
      sb.append(" and emp.emp_Id='" + userId + "' "); 
    if (orgId != null && !"".equals(orgId))
      sb.append(" and org.org_id=" + orgId + " "); 
    if (position != null && !"".equals(position))
      sb.append(" and k.position like '%" + position + "%' "); 
    if (startTime != null && !"".equals(startTime))
      startTime = startTime.replace("/", "-"); 
    if (endTime != null && !"".equals(endTime))
      endTime = endTime.replace("/", "-"); 
    if (byTime != null && !"".equals(byTime))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and k.checkinTime between '").append(startTime)
          .append(" 00:00:00").append("' and '").append(endTime)
          .append(" 23:59:59'");
      } else {
        sb.append(" and k.checkinTime between to_date('").append(startTime).append(" 00:00:00")
          .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
          .append(endTime).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
      }  
    ManagerService mdb = new ManagerService();
    String sqlWhere = mdb.getRightWhere(curUserId, curOrgId, "07*55*26", "org.org_Id", "emp.emp_Id");
    sb.append("and " + sqlWhere);
    sb.append(" and emp.useraccounts = k.userid and emp.emp_id = org_user.emp_id and org.org_id = org_user.org_id and emp.userisactive=1 and userisdeleted=0 ");
    Page page = new Page("emp.empName,k.checkinTime, case when exists (select 1 from kq_weixinbmd bmd where bmd.emp_id=emp.emp_Id and k.checkinTime between bmd.begintime and bmd.endtime) then '******' else k.position end position,k.id,org.orgName", 
        " org_employee emp,org_organization org,org_organization_user org_user,kq_checkininfo k ", 
        String.valueOf(sb.toString()) + " order by k.checkinTime desc");
    page.setPageSize(15);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - 15) / 15;
      currentPage = offset + 1;
      offset *= 15;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("userName", userName);
    request.setAttribute("position", position);
    request.setAttribute("start", startTime);
    request.setAttribute("end", endTime);
    request.setAttribute("byTime", byTime);
    request.setAttribute("userId", userId);
    request.setAttribute("orgId", orgId);
    request.setAttribute("orgName", orgName);
    request.setAttribute("tableList", list);
    request.setAttribute("maxPageItems", String.valueOf(15));
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
    request.setAttribute("pageCount", pageCount);
    request.setAttribute("pageParameters", "action,userId,userName,position,startTime,endTime,byTime,orgId,orgName");
  }
  
  private void exportlist(HttpServletRequest request, HttpSession session, String databaseType) throws Exception {
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / 15 + 1;
    StringBuffer sb = new StringBuffer("where emp.userAccounts=k.userId  ");
    String userId = request.getParameter("userId");
    String userName = request.getParameter("userName");
    String orgId = request.getParameter("orgId");
    String orgName = request.getParameter("orgName");
    String position = request.getParameter("position");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String byTime = request.getParameter("byTime");
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String ids = request.getParameter("ids");
    if (ids != null && !"".equals(ids)) {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      sb.append(" and k.id in (" + ids + ") ");
    } 
    if (userId != null && !"".equals(userId))
      sb.append(" and emp.emp_Id='" + userId + "' "); 
    if (orgId != null && !"".equals(orgId))
      sb.append(" and org.org_id=" + orgId + " "); 
    if (position != null && !"".equals(position))
      sb.append(" and k.position like '%" + position + "%' "); 
    if (startTime != null && !"".equals(startTime))
      startTime = startTime.replace("/", "-"); 
    if (endTime != null && !"".equals(endTime))
      endTime = endTime.replace("/", "-"); 
    if (byTime != null && !"".equals(byTime))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and k.checkinTime between '").append(startTime)
          .append(" 00:00:00").append("' and '").append(endTime)
          .append(" 23:59:59'");
      } else {
        sb.append(" and k.checkinTime between to_date('").append(startTime).append(" 00:00:00")
          .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
          .append(endTime).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
      }  
    ManagerService mdb = new ManagerService();
    String sqlWhere = mdb.getRightWhere(curUserId, curOrgId, "07*55*26", "org.org_Id", "emp.emp_Id");
    sb.append("and " + sqlWhere);
    sb.append(" and emp.useraccounts = k.userid and emp.emp_id = org_user.emp_id and org.org_id = org_user.org_id and emp.userisactive=1 and userisdeleted=0 ");
    Page page = new Page("emp.empName,k.checkinTime, case when exists (select 1 from kq_weixinbmd bmd where bmd.emp_id=emp.emp_Id and k.checkinTime between bmd.begintime and bmd.endtime) then '******' else k.position end position,org.orgName", 
        " org_employee emp,org_organization org,org_organization_user org_user,kq_checkininfo k ", 
        String.valueOf(sb.toString()) + " order by k.checkinTime desc");
    page.setPageSize(65536);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - 15) / 15;
      currentPage = offset + 1;
      offset *= 15;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("tableList", list);
    request.setAttribute("maxPageItems", String.valueOf(15));
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
    request.setAttribute("pageCount", pageCount);
    request.setAttribute("pageParameters", "action,userId,userName,position,startTime,endTime,orgId,orgName");
  }
  
  private void outsidelist(HttpServletRequest request, HttpSession session, String databaseType) throws Exception {
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / 15 + 1;
    StringBuffer sb = new StringBuffer("where emp.userAccounts=k.userId  ");
    String userId = request.getParameter("userId");
    String userName = request.getParameter("userName");
    String orgId = request.getParameter("orgId");
    String orgName = request.getParameter("orgName");
    String position = request.getParameter("position");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String byTime = request.getParameter("byTime");
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    if (databaseType.indexOf("mysql") >= 0) {
      sb.append(" and (k.position is not null) ");
    } else {
      sb.append(" and (k.position is not null) ");
    } 
    if (userId != null && !"".equals(userId))
      sb.append(" and emp.emp_Id='" + userId + "' "); 
    if (orgId != null && !"".equals(orgId))
      sb.append(" and org.org_id=" + orgId + " "); 
    if (position != null && !"".equals(position))
      sb.append(" and k.position like '%" + position + "%' "); 
    if (startTime != null && !"".equals(startTime))
      startTime = startTime.replace("/", "-"); 
    if (endTime != null && !"".equals(endTime))
      endTime = endTime.replace("/", "-"); 
    if (byTime != null && !"".equals(byTime))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and k.checkinTime between '").append(startTime)
          .append(" 00:00:00").append("' and '").append(endTime)
          .append(" 23:59:59'");
      } else {
        sb.append(" and k.checkinTime between to_date('").append(startTime).append(" 00:00:00")
          .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
          .append(endTime).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
      }  
    ManagerService mdb = new ManagerService();
    String sqlWhere = mdb.getRightWhere(curUserId, curOrgId, "07*55*26", "org.org_Id", "emp.emp_Id");
    sb.append("and " + sqlWhere);
    sb.append(" and emp.useraccounts = k.userid and emp.emp_id = org_user.emp_id and org.org_id = org_user.org_id and emp.userisactive=1 and userisdeleted=0");
    Page page = new Page("emp.empName,k.checkinTime, case when exists (select 1 from kq_weixinbmd bmd where bmd.emp_id=emp.emp_Id and k.checkinTime between bmd.begintime and bmd.endtime) then '******' else k.position end position,k.imageurl,k.id,k.customName,k.reason,org.orgName", 
        " org_employee emp,org_organization org,org_organization_user org_user,kq_outsidecheckininfo k ", 
        String.valueOf(sb.toString()) + " order by k.checkinTime desc");
    page.setPageSize(15);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - 15) / 15;
      currentPage = offset + 1;
      offset *= 15;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("userName", userName);
    request.setAttribute("position", position);
    request.setAttribute("start", startTime);
    request.setAttribute("end", endTime);
    request.setAttribute("byTime", byTime);
    request.setAttribute("userId", userId);
    request.setAttribute("orgId", orgId);
    request.setAttribute("orgName", orgName);
    request.setAttribute("tableList", list);
    request.setAttribute("maxPageItems", String.valueOf(15));
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
    request.setAttribute("pageCount", pageCount);
    request.setAttribute("pageParameters", "action,userId,userName,position,startTime,endTime,byTime,orgId,orgName");
  }
  
  private void exportoutsidelist(HttpServletRequest request, HttpSession session, String databaseType) throws Exception {
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / 15 + 1;
    StringBuffer sb = new StringBuffer("where emp.userAccounts=k.userId  ");
    String userId = request.getParameter("userId");
    String userName = request.getParameter("userName");
    String orgId = request.getParameter("orgId");
    String orgName = request.getParameter("orgName");
    String position = request.getParameter("position");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String byTime = request.getParameter("byTime");
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    String ids = request.getParameter("ids");
    if (ids != null && !"".equals(ids)) {
      if (ids.endsWith(","))
        ids = ids.substring(0, ids.length() - 1); 
      sb.append(" and k.id in (" + ids + ") ");
    } 
    if (databaseType.indexOf("mysql") >= 0) {
      sb.append(" and (k.position is not null) ");
    } else {
      sb.append(" and (k.position is not null) ");
    } 
    if (userId != null && !"".equals(userId))
      sb.append(" and emp.emp_Id='" + userId + "' "); 
    if (orgId != null && !"".equals(orgId))
      sb.append(" and org.org_id=" + orgId + " "); 
    if (position != null && !"".equals(position))
      sb.append(" and k.position like '%" + position + "%' "); 
    if (startTime != null && !"".equals(startTime))
      startTime = startTime.replace("/", "-"); 
    if (endTime != null && !"".equals(endTime))
      endTime = endTime.replace("/", "-"); 
    if (byTime != null && !"".equals(byTime))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and k.checkinTime between '").append(startTime)
          .append(" 00:00:00").append("' and '").append(endTime)
          .append(" 23:59:59'");
      } else {
        sb.append(" and k.checkinTime between to_date('").append(startTime).append(" 00:00:00")
          .append("','yyyy-mm-dd hh24:mi:ss')").append(" and ").append(" to_date('")
          .append(endTime).append(" 23:59:59").append("','yyyy-mm-dd hh24:mi:ss')");
      }  
    ManagerService mdb = new ManagerService();
    String sqlWhere = mdb.getRightWhere(curUserId, curOrgId, "07*55*26", "org.org_Id", "emp.emp_Id");
    sb.append("and " + sqlWhere);
    sb.append(" and emp.useraccounts = k.userid and emp.emp_id = org_user.emp_id and org.org_id = org_user.org_id and emp.userisactive=1 and userisdeleted=0");
    Page page = new Page("emp.empName,k.checkinTime, case when exists (select 1 from kq_weixinbmd bmd where bmd.emp_id=emp.emp_Id and k.checkinTime between bmd.begintime and bmd.endtime) then '******' else k.position end position,k.imageurl,k.id,k.customName,k.reason,org.orgName", 
        " org_employee emp,org_organization org,org_organization_user org_user,kq_outsidecheckininfo k ", 
        String.valueOf(sb.toString()) + " order by k.checkinTime desc");
    page.setPageSize(65536);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - 15) / 15;
      currentPage = offset + 1;
      offset *= 15;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("tableList", list);
    request.setAttribute("maxPageItems", String.valueOf(15));
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
    request.setAttribute("pageCount", pageCount);
    request.setAttribute("pageParameters", "action,userId,userName,position,startTime,endTime,orgId,orgName");
  }
  
  private void outsidereadload(HttpServletRequest request, HttpSession session) {
    String readId = request.getParameter("id");
    String username = request.getParameter("username");
    String imageurl = request.getParameter("imageurl");
    String time = request.getParameter("time");
    String position = request.getParameter("position");
    String customName = request.getParameter("customName");
    String reason = request.getParameter("reason");
    request.setAttribute("customName", customName);
    request.setAttribute("reason", reason);
    request.setAttribute("username", username);
    request.setAttribute("imageurl", imageurl);
    request.setAttribute("position", position);
    request.setAttribute("readId", readId);
    request.setAttribute("time", time);
  }
  
  public void kqquery(HttpServletRequest request, HttpSession session, String databaseType) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String startDate = "";
      String endDate = "";
      String dateStrings = "";
      String searchDate = (request.getParameter("searchDate") == null) ? "" : request.getParameter("searchDate");
      if ("1".equals(searchDate)) {
        startDate = request.getParameter("start_date").replace("/", "-");
        endDate = request.getParameter("end_date").replace("/", "-");
        Date bDate = format.parse(startDate);
        Date eDate = format.parse(endDate);
        for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
          dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        request.setAttribute("searchDate", "1");
      } else {
        String year = (request.getParameter("Year") == null) ? "" : request.getParameter("Year");
        String month = (request.getParameter("Month") == null) ? "" : request.getParameter("Month");
        String day = (request.getParameter("Day") == null) ? "" : request.getParameter("Day");
        request.setAttribute("Year", year);
        request.setAttribute("Month", month);
        request.setAttribute("Day", day);
        if (!"".equals(year) && Integer.valueOf(month).intValue() != (new Date()).getMonth() + 1) {
          String searDate = format.format(format.parse(String.valueOf(year) + "-" + month + "-" + day));
          Calendar c = Calendar.getInstance();
          c.setTime(format.parse(searDate));
          int dateNum = c.getActualMaximum(5);
          String dateYM = searDate.substring(0, 8);
          startDate = String.valueOf(dateYM) + "01";
          endDate = String.valueOf(dateYM) + dateNum;
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } else {
          endDate = format.format(new Date());
          startDate = String.valueOf(endDate.substring(0, 8)) + "01";
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } 
      } 
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / 15 + 1;
      StringBuffer sb = new StringBuffer("where emp.userIsActive=1 and emp.userIsDeleted=0 ");
      String searchSQL = "";
      String orgId = (request.getParameter("kqOrgId") == null) ? "" : request.getParameter("kqOrgId").toString();
      String orgName = (request.getParameter("kqOrgName") == null) ? "" : request.getParameter("kqOrgName").toString();
      String userId = (request.getParameter("kqUserId") == null) ? "" : request.getParameter("kqUserId").toString();
      String userName = (request.getParameter("kqUserName") == null) ? "" : request.getParameter("kqUserName").toString();
      if (orgId != null && !orgId.equals("")) {
        StringTokenizer st = new StringTokenizer(orgId, "*");
        searchSQL = String.valueOf(searchSQL) + " and ( org.orgId in (";
        int count = st.countTokens();
        int index = 0;
        while (st.hasMoreTokens()) {
          String id = st.nextToken();
          index++;
          String orgIds = StaticParam.getOrgIdsByOrgId(id);
          searchSQL = String.valueOf(searchSQL) + orgIds;
          if (index < count)
            searchSQL = String.valueOf(searchSQL) + ","; 
        } 
        searchSQL = String.valueOf(searchSQL) + "))";
        sb.append(searchSQL);
      } 
      if (userId != null && !"".equals(userId)) {
        StringTokenizer st = new StringTokenizer(userId, "$");
        searchSQL = " and ( emp.empId in (";
        while (st.hasMoreTokens())
          searchSQL = String.valueOf(searchSQL) + st.nextToken() + ","; 
        searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + ")) ";
        sb.append(searchSQL);
      } 
      String curOrgId = session.getAttribute("orgId").toString();
      String curUserId = session.getAttribute("userId").toString();
      ManagerService mdb = new ManagerService();
      String sqlWhere = mdb.getRightWhere(curUserId, curOrgId, "07*55*26", "org.orgId", "emp.empId");
      sb.append(" and " + sqlWhere);
      Page page = new Page("emp.empId,emp.empName,emp.userAccounts,org.orgId,org.orgNameString,org.orgName", 
          "FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org ", 
          String.valueOf(sb.toString()) + " order by org.orgIdString,emp.empDutyLevel,emp.empId");
      page.setPageSize(15);
      page.setcurrentPage(currentPage);
      List<Object[]> list = page.getResultList();
      List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
      Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
      String[] dateStr = dateStrings.split(",");
      KqCheckinInfoBean weixinkq = new KqCheckinInfoBean();
      List<Object[]> statList = new ArrayList();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          Map<String, String> dataMap = new HashMap<String, String>();
          Object[] obj = list.get(i);
          statList = weixinkq.getWeixinKq(startDate, endDate, String.valueOf(obj[2]));
          String befodata = "";
          for (int j = 0; j < statList.size(); j++) {
            Object[] sObj = statList.get(j);
            String[] dateTime = sObj[2].split(" ");
            if (dataMap.get(dateTime[0]) == null) {
              dataMap.put(dateTime[0], "上班：" + dateTime[1].toString().substring(0, dateTime[1].toString().length() - 2));
              befodata = "";
              befodata = "上班：" + dateTime[1].toString().substring(0, dateTime[1].toString().length() - 2);
            } else {
              dataMap.put(dateTime[0], String.valueOf(befodata) + "<br><br>&nbsp;下班：" + dateTime[1].toString().substring(0, dateTime[1].toString().length() - 2));
            } 
          } 
          map.put(String.valueOf(obj[2]), dataMap);
        }  
      request.setAttribute("map", map);
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      request.setAttribute("list", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("pageCount", pageCount);
      request.setAttribute("maxPageItems", String.valueOf(15));
      request.setAttribute("pageParameters", "action,kqUserId,kqUserName,position,start_date,end_date,kqOrgId,kqOrgName,Year,Month,Day");
      request.setAttribute("kqOrgId", orgId);
      request.setAttribute("kqOrgName", orgName);
      request.setAttribute("kqUserId", userId);
      request.setAttribute("kqUserName", userName);
      request.setAttribute("dateString", dateStrings);
      request.setAttribute("start_date", startDate);
      request.setAttribute("end_date", endDate);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void kqqueryexport(HttpServletRequest request, HttpSession session, String databaseType) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String startDate = "";
      String endDate = "";
      String dateStrings = "";
      String searchDate = (request.getParameter("searchDate") == null) ? "" : request.getParameter("searchDate");
      if ("1".equals(searchDate)) {
        startDate = request.getParameter("start_date").replace("/", "-");
        endDate = request.getParameter("end_date").replace("/", "-");
        Date bDate = format.parse(startDate);
        Date eDate = format.parse(endDate);
        for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
          dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        request.setAttribute("searchDate", "1");
      } else {
        String year = (request.getParameter("Year") == null) ? "" : request.getParameter("Year");
        String month = (request.getParameter("Month") == null) ? "" : request.getParameter("Month");
        String day = (request.getParameter("Day") == null) ? "" : request.getParameter("Day");
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("day", day);
        if (!"".equals(year) && Integer.valueOf(month).intValue() != (new Date()).getMonth() + 1) {
          String searDate = format.format(format.parse(String.valueOf(year) + "-" + month + "-" + day));
          Calendar c = Calendar.getInstance();
          c.setTime(format.parse(searDate));
          int dateNum = c.getActualMaximum(5);
          String dateYM = searDate.substring(0, 8);
          startDate = String.valueOf(dateYM) + "01";
          endDate = String.valueOf(dateYM) + dateNum;
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } else {
          endDate = format.format(new Date());
          startDate = String.valueOf(endDate.substring(0, 8)) + "01";
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } 
      } 
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / 15 + 1;
      StringBuffer sb = new StringBuffer("where emp.userIsActive=1 and emp.userIsDeleted=0 ");
      String searchSQL = "";
      String orgId = (request.getParameter("kqOrgId") == null) ? "" : request.getParameter("kqOrgId").toString();
      String orgName = (request.getParameter("kqOrgName") == null) ? "" : request.getParameter("kqOrgName").toString();
      String userId = (request.getParameter("kqUserId") == null) ? "" : request.getParameter("kqUserId").toString();
      String userName = (request.getParameter("kqUserName") == null) ? "" : request.getParameter("kqUserName").toString();
      if (!orgId.equals("")) {
        StringTokenizer st = new StringTokenizer(orgId, "*");
        searchSQL = String.valueOf(searchSQL) + " and ( org.orgId in (";
        while (st.hasMoreTokens())
          searchSQL = String.valueOf(searchSQL) + st.nextToken() + ","; 
        searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + ")) ";
        sb.append(searchSQL);
      } 
      if (userId != null && !"".equals(userId)) {
        StringTokenizer st = new StringTokenizer(userId, "$");
        searchSQL = " and ( emp.empId in (";
        while (st.hasMoreTokens())
          searchSQL = String.valueOf(searchSQL) + st.nextToken() + ","; 
        searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + ")) ";
        sb.append(searchSQL);
      } 
      String curOrgId = session.getAttribute("orgId").toString();
      String curUserId = session.getAttribute("userId").toString();
      ManagerService mdb = new ManagerService();
      String sqlWhere = mdb.getRightWhere(curUserId, curOrgId, "07*55*26", "org.orgId", "emp.empId");
      sb.append("and " + sqlWhere);
      Page page = new Page("emp.empId,emp.empName,emp.userAccounts,org.orgId,org.orgNameString,org.orgName", 
          "FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org ", 
          String.valueOf(sb.toString()) + " order by org.orgIdString,emp.empDutyLevel,emp.empId");
      page.setPageSize(65536);
      page.setcurrentPage(currentPage);
      List<Object[]> list = page.getResultList();
      List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
      Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
      String[] dateStr = dateStrings.split(",");
      KqCheckinInfoBean weixinkq = new KqCheckinInfoBean();
      List<Object[]> statList = new ArrayList();
      if (!list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          Map<String, String> dataMap = new HashMap<String, String>();
          Object[] obj = list.get(i);
          statList = weixinkq.getWeixinKq(startDate, endDate, String.valueOf(obj[2]));
          String befodata = "";
          for (int j = 0; j < statList.size(); j++) {
            Object[] sObj = statList.get(j);
            String[] dateTime = sObj[2].split(" ");
            if (dataMap.get(dateTime[0]) == null) {
              dataMap.put(dateTime[0], "上班：" + dateTime[1].toString().substring(0, dateTime[1].toString().length() - 2));
              befodata = "";
              befodata = "上班：" + dateTime[1].toString().substring(0, dateTime[1].toString().length() - 2);
            } else {
              dataMap.put(dateTime[0], String.valueOf(befodata) + "<br><br>&nbsp;下班：" + dateTime[1].toString().substring(0, dateTime[1].toString().length() - 2));
            } 
          } 
          map.put(String.valueOf(obj[2]), dataMap);
        }  
      request.setAttribute("map", map);
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      request.setAttribute("list", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("pageCount", pageCount);
      request.setAttribute("maxPageItems", String.valueOf(15));
      request.setAttribute("pageParameters", "action,userId,userName,position,startTime,endTime,orgId,orgName");
      request.setAttribute("orgId", orgId);
      request.setAttribute("orgName", orgName);
      request.setAttribute("userId", userId);
      request.setAttribute("userName", userName);
      request.setAttribute("dateString", dateStrings);
      request.setAttribute("start_date", startDate);
      request.setAttribute("end_date", endDate);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void kqquerygr(HttpServletRequest request, HttpSession session, String databaseType) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String startDate = "";
      String endDate = "";
      String dateStrings = "";
      String searchDate = (request.getParameter("searchDate") == null) ? "" : request.getParameter("searchDate");
      if ("1".equals(searchDate)) {
        startDate = request.getParameter("start_date").replace("/", "-");
        endDate = request.getParameter("end_date").replace("/", "-");
        Date bDate = format.parse(startDate);
        Date eDate = format.parse(endDate);
        for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
          dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        request.setAttribute("searchDate", "1");
      } else {
        String year = (request.getParameter("Year") == null) ? "" : request.getParameter("Year");
        String month = (request.getParameter("Month") == null) ? "" : request.getParameter("Month");
        String day = (request.getParameter("Day") == null) ? "" : request.getParameter("Day");
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("day", day);
        if (!"".equals(year) && Integer.valueOf(month).intValue() != (new Date()).getMonth() + 1) {
          String searDate = format.format(format.parse(String.valueOf(year) + "-" + month + "-" + day));
          Calendar c = Calendar.getInstance();
          c.setTime(format.parse(searDate));
          int dateNum = c.getActualMaximum(5);
          String dateYM = searDate.substring(0, 8);
          startDate = String.valueOf(dateYM) + "01";
          endDate = String.valueOf(dateYM) + dateNum;
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } else {
          endDate = format.format(new Date());
          startDate = String.valueOf(endDate.substring(0, 8)) + "01";
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } 
      } 
      String userId = request.getParameter("userId");
      String empId = StaticParam.getEmpIdByAccount(userId);
      String orgName = StaticParam.getOrgNameByEmpId(empId).split(",")[1];
      String empName = StaticParam.getEmpNameByEmpId(empId);
      request.setAttribute("orgName", orgName);
      request.setAttribute("empName", empName);
      request.setAttribute("userId", userId);
      request.setAttribute("dateString", dateStrings);
      request.setAttribute("start_date", startDate);
      request.setAttribute("end_date", endDate);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void kqquerygrexport(HttpServletRequest request, HttpSession session, String databaseType) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String startDate = "";
      String endDate = "";
      String dateStrings = "";
      String searchDate = (request.getParameter("searchDate") == null) ? "" : request.getParameter("searchDate");
      if ("1".equals(searchDate)) {
        startDate = request.getParameter("start_date").replace("/", "-");
        endDate = request.getParameter("end_date").replace("/", "-");
        Date bDate = format.parse(startDate);
        Date eDate = format.parse(endDate);
        for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
          dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        request.setAttribute("searchDate", "1");
      } else {
        String year = (request.getParameter("Year") == null) ? "" : request.getParameter("Year");
        String month = (request.getParameter("Month") == null) ? "" : request.getParameter("Month");
        String day = (request.getParameter("Day") == null) ? "" : request.getParameter("Day");
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("day", day);
        if (!"".equals(year) && Integer.valueOf(month).intValue() != (new Date()).getMonth() + 1) {
          String searDate = format.format(format.parse(String.valueOf(year) + "-" + month + "-" + day));
          Calendar c = Calendar.getInstance();
          c.setTime(format.parse(searDate));
          int dateNum = c.getActualMaximum(5);
          String dateYM = searDate.substring(0, 8);
          startDate = String.valueOf(dateYM) + "01";
          endDate = String.valueOf(dateYM) + dateNum;
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } else {
          endDate = format.format(new Date());
          startDate = String.valueOf(endDate.substring(0, 8)) + "01";
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long i = bDate.getTime(); i <= eDate.getTime(); i += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(i + 2000L)) + ","; 
        } 
      } 
      String userId = request.getParameter("userId");
      String empId = StaticParam.getEmpIdByAccount(userId);
      String orgName = StaticParam.getOrgNameByEmpId(empId).split(",")[1];
      String empName = StaticParam.getEmpNameByEmpId(empId);
      request.setAttribute("orgName", orgName);
      request.setAttribute("empName", empName);
      request.setAttribute("userId", userId);
      request.setAttribute("dateString", dateStrings);
      request.setAttribute("start_date", startDate);
      request.setAttribute("end_date", endDate);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
