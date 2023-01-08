package com.js.oa.hr.kq.action;

import com.js.oa.form.kq.KqFlowImport;
import com.js.oa.form.kq.KqImportUtil;
import com.js.oa.hr.kq.service.KqDutyOutBD;
import com.js.oa.hr.kq.util.ImportInfo;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
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

public class KqStatAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    if (action.equals("list")) {
      list(httpServletRequest, 15);
    } else if (action.equals("orgList")) {
      tag = "orgList";
      orgList(httpServletRequest);
    } else if (action.equals("emp")) {
      getEmpMore(httpServletRequest);
      tag = "emp";
    } else {
      if (action.equals("import")) {
        ImportInfo ik = new ImportInfo();
        try {
          if (httpServletRequest.getParameter("all") == null) {
            ik.importData("", "");
          } else {
            ik.importData(httpServletRequest.getParameter("all"), "");
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
        return null;
      } 
      if ("export".equals(action)) {
        list(httpServletRequest, 9999);
        tag = "excel";
      } else if (action.equals("importExcel")) {
        ImportInfo ik = new ImportInfo();
        String dataSaveName = httpServletRequest.getParameter("dataSaveName");
        String srcTr = "0000";
        if (dataSaveName != null && dataSaveName.length() > 6 && dataSaveName.substring(4, 5).equals("_")) {
          srcTr = dataSaveName.substring(0, 4);
        } else {
          srcTr = "0000";
        } 
        String filePath = httpServletRequest.getSession().getServletContext().getRealPath("/upload/" + srcTr + "/kq/");
        String[] value = ik.importData("excel", String.valueOf(filePath) + "\\" + dataSaveName);
        if ("true".equals(value[1])) {
          httpServletRequest.setAttribute("flag", "1");
        } else {
          httpServletRequest.setAttribute("flag", "2");
        } 
        httpServletRequest.setAttribute("count", value[0]);
        tag = "importExcel";
      } else if (action.equals("flow")) {
        KqFlowImport kqFlowImport = new KqFlowImport();
        kqFlowImport.importKqInfo();
        return null;
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, int pageSize) throws NumberFormatException, Exception {
    HttpSession session = httpServletRequest.getSession(true);
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String orgId = (httpServletRequest.getParameter("orgId") == null) ? "" : httpServletRequest.getParameter("orgId").toString();
    String orgName = (httpServletRequest.getParameter("orgName") == null) ? "" : httpServletRequest.getParameter("orgName").toString();
    String userId = (httpServletRequest.getParameter("userId") == null) ? "" : httpServletRequest.getParameter("userId").toString();
    String userName = (httpServletRequest.getParameter("userName") == null) ? "" : httpServletRequest.getParameter("userName").toString();
    String searchDate = (httpServletRequest.getParameter("searchDate") == null) ? "" : httpServletRequest.getParameter("searchDate").toString();
    String year = (httpServletRequest.getParameter("Year") == null) ? "" : httpServletRequest.getParameter("Year");
    String month = (httpServletRequest.getParameter("Month") == null) ? "" : httpServletRequest.getParameter("Month");
    String day = (httpServletRequest.getParameter("Day") == null) ? "" : httpServletRequest.getParameter("Day");
    String start_date = "";
    String end_date = "";
    String searchSQL = " where emp.userIsDeleted=0 and emp.userIsActive=1 ";
    if (!orgId.equals("")) {
      StringTokenizer st = new StringTokenizer(orgId, "*");
      searchSQL = String.valueOf(searchSQL) + " and ( org.orgId in (";
      int count = st.countTokens();
      int index = 0;
      while (st.hasMoreTokens()) {
        String id = st.nextToken();
        index++;
        String str1 = StaticParam.getOrgIdsByOrgId(id);
        searchSQL = String.valueOf(searchSQL) + str1;
        if (index < count)
          searchSQL = String.valueOf(searchSQL) + ","; 
      } 
      searchSQL = String.valueOf(searchSQL) + "))";
    } 
    if (!userId.equals("")) {
      StringTokenizer st = new StringTokenizer(
          userId, "$");
      searchSQL = String.valueOf(searchSQL) + " and ( emp.empId in (";
      while (st.hasMoreTokens())
        searchSQL = String.valueOf(searchSQL) + st.nextToken() + ","; 
      searchSQL = String.valueOf(searchSQL.substring(0, searchSQL.length() - 1)) + ")) ";
    } 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String searchEnddate = dateFormat.format(new Date());
    Calendar thisMonth = Calendar.getInstance();
    thisMonth.setTime(new Date());
    int lastDay = thisMonth.getActualMaximum(5);
    if (!"".equals(year) && Integer.valueOf(month).intValue() != (new Date()).getMonth() + 1) {
      Calendar endC = Calendar.getInstance();
      searchEnddate = String.valueOf(year) + "-" + month + "-" + day;
      endC.setTime(dateFormat.parse(searchEnddate));
      int d = endC.getActualMaximum(5);
      searchEnddate = String.valueOf(year) + "-" + month + "-" + d;
      lastDay = d;
    } 
    String searchBegindate = String.valueOf(searchEnddate.substring(0, 8)) + "01";
    String chuQingEnd = String.valueOf(searchEnddate.substring(0, 8)) + lastDay;
    if ("1".equals(searchDate)) {
      start_date = (httpServletRequest.getParameter("start_date") == null) ? "" : httpServletRequest.getParameter("start_date");
      end_date = (httpServletRequest.getParameter("end_date") == null) ? "" : httpServletRequest.getParameter("end_date");
      Date bdate = new Date(start_date);
      searchBegindate = dateFormat.format(bdate);
      Date edate = new Date(end_date);
      searchEnddate = dateFormat.format(edate);
    } 
    searchSQL = String.valueOf(searchSQL) + " and (";
    ManagerService mdb = new ManagerService();
    String[] orgIds = (String.valueOf(curOrgId) + "," + StaticParam.getSidelineorg(curUserId)).split(",");
    for (int i = 0; i < orgIds.length; i++)
      searchSQL = String.valueOf(searchSQL) + mdb.getRightWhere(curUserId, orgIds[i], "07*55*06", "org.orgId", "emp.empId") + " or "; 
    searchSQL = String.valueOf(searchSQL) + " emp.empId=" + curUserId + ")";
    Page page = new Page("emp.empId,emp.empName,org.orgId,org.orgNameString", 
        "FROM com.js.system.vo.usermanager.EmployeeVO emp join emp.organizations org ", 
        String.valueOf(searchSQL) + " order by org.orgIdString,emp.empDutyLevel,emp.empId");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    KqDutyOutBD kqDutyOutBD = new KqDutyOutBD();
    List<Object[]> statList = new ArrayList();
    KqImportUtil util = new KqImportUtil();
    if (!list.isEmpty()) {
      DecimalFormat dformat = new DecimalFormat("0.00");
      for (int j = 0; j < list.size(); j++) {
        Object[] obj = list.get(j);
        float[] hours = kqDutyOutBD.getHour(searchBegindate, searchEnddate, obj[0].toString());
        int[] dutyInfo = kqDutyOutBD.getDutyInfo(searchBegindate, searchEnddate, obj[0].toString());
        float chuqin = util.getHour(String.valueOf(searchBegindate) + " 00:00", String.valueOf(chuQingEnd) + " 23:59", "5", obj[0].toString());
        Object[] obj1 = new Object[15];
        obj1[0] = obj[0];
        obj1[1] = obj[1];
        obj1[2] = obj[2];
        obj1[3] = obj[3];
        obj1[4] = Float.valueOf(chuqin);
        obj1[5] = dformat.format((chuqin - hours[1] - dutyInfo[4]));
        obj1[6] = Float.valueOf(hours[0]);
        obj1[7] = Float.valueOf(hours[1]);
        obj1[8] = Float.valueOf(hours[2]);
        obj1[9] = Float.valueOf(hours[3]);
        obj1[10] = Integer.valueOf(dutyInfo[0]);
        obj1[11] = Integer.valueOf(dutyInfo[1]);
        obj1[12] = Integer.valueOf(dutyInfo[2]);
        obj1[13] = Integer.valueOf(dutyInfo[3]);
        obj1[14] = Integer.valueOf(dutyInfo[4]);
        statList.add(obj1);
      } 
    } 
    String url = "";
    Enumeration<String> pNames = httpServletRequest.getParameterNames();
    while (pNames.hasMoreElements()) {
      String name = pNames.nextElement();
      if (!name.equals("pager.offset") && !name.equals("action")) {
        String value = (httpServletRequest.getParameter(name) == null) ? "" : httpServletRequest.getParameter(name);
        url = String.valueOf(url) + "&" + name + "=" + value;
      } 
    } 
    httpServletRequest.setAttribute("paraUrl", url);
    httpServletRequest.setAttribute("title", String.valueOf(searchBegindate) + "到" + searchEnddate);
    httpServletRequest.setAttribute("list", statList);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,userId,orgId,userName,orgName,searchDate,start_date,end_date,Year,Month,Day");
  }
  
  private void orgList(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String orgId = (httpServletRequest.getParameter("orgId") == null) ? "" : httpServletRequest.getParameter("orgId").toString();
    String orgName = (httpServletRequest.getParameter("orgName") == null) ? "" : httpServletRequest.getParameter("orgName").toString();
    String searchDate = (httpServletRequest.getParameter("searchDate") == null) ? "" : httpServletRequest.getParameter("searchDate").toString();
    String start_date = "";
    String end_date = "";
    String searchSQL = " where org.orgStatus=0 and orgParentOrgId<>-1";
    if (!orgId.equals("")) {
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
    } 
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String searchEnddate = dateFormat.format(new Date());
    String searchBegindate = String.valueOf(searchEnddate.substring(0, 8)) + "01";
    if ("1".equals(searchDate)) {
      start_date = (httpServletRequest.getParameter("start_date") == null) ? "" : httpServletRequest.getParameter("start_date");
      end_date = (httpServletRequest.getParameter("end_date") == null) ? "" : httpServletRequest.getParameter("end_date");
      Date bdate = new Date(start_date);
      searchBegindate = dateFormat.format(bdate);
      Date edate = new Date(end_date);
      searchEnddate = dateFormat.format(edate);
    } 
    ManagerService mdb = new ManagerService();
    List<Object[]> scopeList = mdb.getRightScope(session.getAttribute("userId").toString(), "07*55*06");
    if (scopeList.size() <= 0) {
      searchSQL = String.valueOf(searchSQL) + " and 1<>1 ";
    } else {
      Object[] obj = scopeList.get(0);
      if ("1".equals(obj[0].toString())) {
        searchSQL = String.valueOf(searchSQL) + " and 1<>1 ";
      } else if ("3".equals(obj[0].toString())) {
        searchSQL = String.valueOf(searchSQL) + " and org.orgId=" + session.getAttribute("orgId") + " ";
      } else if ("2".equals(obj[0].toString())) {
        String orgIds = StaticParam.getOrgIdsByOrgId(session.getAttribute("orgId").toString());
        searchSQL = String.valueOf(searchSQL) + " and org.orgId in (" + orgIds + ") ";
      } else if ("4".equals(obj[0].toString())) {
        String orgIds = obj[1].toString().replaceAll("\\*\\*", ",").replaceAll("\\*", "");
        searchSQL = String.valueOf(searchSQL) + " and org.orgId in (" + orgIds + ") ";
      } 
    } 
    Page page = new Page("org.orgId,org.orgName,org.orgNameString", "com.js.system.vo.organizationmanager.OrganizationVO org", 
        String.valueOf(searchSQL) + " order by org.orgId");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<Object[]> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    KqDutyOutBD kqDutyOutBD = new KqDutyOutBD();
    List<Object[]> statList = new ArrayList();
    if (!list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        Object[] obj1 = new Object[15];
        obj1[0] = obj[0];
        obj1[1] = obj[1];
        obj1[2] = obj[2];
        obj1[3] = Float.valueOf(kqDutyOutBD.getHourByOrg(searchBegindate, searchEnddate, obj[0].toString(), "4"));
        obj1[4] = Float.valueOf(kqDutyOutBD.getHourByOrg(searchBegindate, searchEnddate, obj[0].toString(), "3"));
        obj1[5] = Float.valueOf(kqDutyOutBD.getHourByOrg(searchBegindate, searchEnddate, obj[0].toString(), "1"));
        obj1[6] = Float.valueOf(kqDutyOutBD.getHourByOrg(searchBegindate, searchEnddate, obj[0].toString(), "2"));
        obj1[7] = Integer.valueOf(kqDutyOutBD.getDutyByOrg(searchBegindate, searchEnddate, obj[0].toString(), "2"));
        obj1[8] = Integer.valueOf(kqDutyOutBD.getDutyByOrg(searchBegindate, searchEnddate, obj[0].toString(), "3"));
        obj1[9] = Integer.valueOf(kqDutyOutBD.getDutyByOrg(searchBegindate, searchEnddate, obj[0].toString(), "4"));
        statList.add(obj1);
      }  
    httpServletRequest.setAttribute("list", statList);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action,orgId,orgName,searchDate,start_date,end_date");
  }
  
  public void getEmpMore(HttpServletRequest request) {
    try {
      String userId = request.getParameter("empId");
      KqDutyOutBD kqOut = new KqDutyOutBD();
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
        for (long l = bDate.getTime(); l <= eDate.getTime(); l += 86400000L)
          dateStrings = String.valueOf(dateStrings) + format.format(new Date(l + 2000L)) + ","; 
        request.setAttribute("searchDate", "1");
      } else {
        String year = (request.getParameter("Year") == null) ? "" : request.getParameter("Year");
        String month = (request.getParameter("Month") == null) ? "" : request.getParameter("Month");
        String day = (request.getParameter("Day") == null) ? "" : request.getParameter("Day");
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
          for (long l = bDate.getTime(); l <= eDate.getTime(); l += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(l + 2000L)) + ","; 
        } else {
          endDate = format.format(new Date());
          startDate = String.valueOf(endDate.substring(0, 8)) + "01";
          Date bDate = format.parse(startDate);
          Date eDate = format.parse(endDate);
          for (long l = bDate.getTime(); l <= eDate.getTime(); l += 86400000L)
            dateStrings = String.valueOf(dateStrings) + format.format(new Date(l + 2000L)) + ","; 
        } 
      } 
      request.setAttribute("dateString", dateStrings);
      Map<String, String> kqDuty = new HashMap<String, String>();
      List<Object[]> list = kqOut.searchByEmpId(startDate, endDate, userId);
      Map<String, String> map = new HashMap<String, String>();
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        String riqi = format.format(obj[0]);
        if (map.get(riqi) != null) {
          if (((String)map.get(riqi)).indexOf((String)obj[2]) < 0) {
            map.put(riqi, String.valueOf(map.get(riqi)) + "," + obj[2]);
            if (obj[2].equals("1"))
              kqDuty.put(riqi, String.valueOf(kqDuty.get(riqi)) + "<div><font color='red'>外出：</font><br/>" + typeString(userId, riqi, "1") + "</div>"); 
            if (obj[2].equals("3"))
              kqDuty.put(riqi, String.valueOf(kqDuty.get(riqi)) + "<div><font color='red'>请假：</font><br/>" + typeString(userId, riqi, "2") + "</div>"); 
            if (obj[2].equals("4"))
              kqDuty.put(riqi, String.valueOf(kqDuty.get(riqi)) + "<div><font color='red'>出差：</font><br/>" + typeString(userId, riqi, "3") + "</div>"); 
          } 
        } else {
          map.put(riqi, obj[2]);
          if (obj[2].equals("1"))
            kqDuty.put(riqi, "<div><font color='red'>外出：</font><br/>" + typeString(userId, riqi, "1") + "</div>"); 
          if (obj[2].equals("3"))
            kqDuty.put(riqi, "<div><font color='red'>请假：</font><br/>" + typeString(userId, riqi, "2") + "</div>"); 
          if (obj[2].equals("4"))
            kqDuty.put(riqi, "<div><font color='red'>出差：</font><br/>" + typeString(userId, riqi, "3") + "</div>"); 
        } 
      } 
      request.setAttribute("kqDuty", kqDuty);
      request.setAttribute("dateMap", map);
      Map<String, String> dutyMap = kqOut.getDutyMap(startDate, endDate, userId);
      Map<String, String> dutyShow = kqOut.getDutyShow(startDate, endDate, userId);
      request.setAttribute("dutyShow", dutyShow);
      request.setAttribute("dutyMap", dutyMap);
      String orgName = StaticParam.getOrgNameByOrgId(StaticParam.getOrgIdByEmpId(userId));
      request.setAttribute("orgName", orgName);
      request.setAttribute("start_date", startDate);
      request.setAttribute("end_date", endDate);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String typeString(String userId, String dateStr, String flag) {
    KqDutyOutBD kqOut = new KqDutyOutBD();
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "";
    if ("1".equals(flag)) {
      if (databaseType.indexOf("mysql") >= 0)
        sql = "SELECT waichuStart,waichuEnd FROM kq_waichu WHERE waichuEmp=" + userId + " AND " + 
          "(( waichuStart LIKE '%" + dateStr + "%' OR waichuEnd LIKE '%" + dateStr + "%')" + 
          "OR ('" + dateStr + "' BETWEEN waichuStart AND waichuEnd))"; 
      if (databaseType.indexOf("oracle") >= 0)
        sql = "SELECT waichuStart,waichuEnd FROM kq_waichu WHERE waichuEmp=" + userId + " AND " + 
          "(( to_char(waichustart,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%' " + 
          "OR to_char(waichuend,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%') " + 
          "or ('" + dateStr + "' between to_char(waichustart,'yyyy-mm-dd hh:mm:ss') and to_char(waichuend,'yyyy-mm-dd hh:mm:ss')))"; 
    } 
    if ("2".equals(flag)) {
      if (databaseType.indexOf("mysql") >= 0)
        sql = "SELECT qingjiaStart,qingjiaEnd FROM kq_qingjia WHERE qingjiaEmp=" + userId + " AND " + 
          "(( qingjiaStart LIKE '%" + dateStr + "%' OR qingjiaEnd LIKE '%" + dateStr + "%') " + 
          "OR ('" + dateStr + "' BETWEEN qingjiaStart AND qingjiaEnd))"; 
      if (databaseType.indexOf("oracle") >= 0)
        sql = "SELECT qingjiaStart,qingjiaEnd FROM kq_qingjia WHERE qingjiaEmp=" + userId + " AND " + 
          "(( to_char(qingjiastart,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%' " + 
          "OR to_char(qingjiaend,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%') " + 
          "or ('" + dateStr + "' between to_char(qingjiastart,'yyyy-mm-dd hh:mm:ss') and to_char(qingjiaend,'yyyy-mm-dd hh:mm:ss')))"; 
    } 
    if ("3".equals(flag)) {
      if (databaseType.indexOf("mysql") >= 0)
        sql = "SELECT chuchaiStart,chuchaiEnd FROM kq_chuchai WHERE chuchaiEmp=" + userId + " AND " + 
          "(( chuchaiStart LIKE '%" + dateStr + "%' OR chuchaiEnd LIKE '%" + dateStr + "%')" + 
          "OR ('" + dateStr + "' BETWEEN chuchaiStart AND chuchaiEnd))"; 
      if (databaseType.indexOf("oracle") >= 0)
        sql = "SELECT chuchaiStart,chuchaiEnd FROM kq_chuchai WHERE chuchaiEmp=" + userId + " AND " + 
          "(( to_char(chuchaistart,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%' " + 
          "OR to_char(chuchaiend,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%') " + 
          "or ('" + dateStr + "' between to_char(chuchaistart,'yyyy-mm-dd hh:mm:ss') and to_char(chuchaiend,'yyyy-mm-dd hh:mm:ss')))"; 
    } 
    return kqOut.dateShow(sql);
  }
}
