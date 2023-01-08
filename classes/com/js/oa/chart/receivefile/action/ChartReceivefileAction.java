package com.js.oa.chart.receivefile.action;

import com.js.oa.chart.receivefile.bean.ChartReceivefileEJBBean;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ChartReceivefileAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    String yearMonth = request.getParameter("yearMonth");
    request.setAttribute("yearMonth", yearMonth);
    ChartReceivefileEJBBean chartBean = new ChartReceivefileEJBBean();
    if ("list".equals(action)) {
      request.setAttribute("processList", chartBean.getReceiveProcess());
      flowStatusData(request, 15, "02*05*01");
      return actionMapping.findForward(action);
    } 
    if ("detail".equals(action))
      detailList(request); 
    return actionMapping.findForward(action);
  }
  
  private void detailList(HttpServletRequest request) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String orgId = request.getParameter("orgId");
    String statProcessId = request.getParameter("statProcessId");
    String para = "dr.receivefile_title,dr.receivefile_filenumber,dr.receivefile_sendfileunit,dr.receivefile_receivedate,ds.expect_donetime,ds.donetime";
    String from = "doc_receivefile_stat ds join doc_receivefile dr on ds.record_id=dr.receivefile_id left join org_organization_user o on ds.emp_id=o.emp_id";
    String where = " where ds.isoverdue=1 and file_status=100 and o.org_id=" + orgId;
    if (!"".equals(statProcessId))
      where = String.valueOf(where) + " and ds.process_id=" + statProcessId; 
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "orgId,statProcessId,action,orgName,orderBy,sortType,searchTime,oprStartTime,oprEndTime,flowStatusType,userName");
  }
  
  private void flowStatusData(HttpServletRequest request, int page_size, String rightCode) {
    HttpSession session = request.getSession(true);
    String orgId = session.getAttribute("orgId").toString();
    String userId = session.getAttribute("userId").toString();
    String orgName = request.getParameter("orgName");
    String userName = request.getParameter("userName");
    String orderBy = request.getParameter("orderBy");
    String sortType = request.getParameter("sortType");
    String searchReceiveTime = request.getParameter("searchReceiveTime");
    String receiveTimeBegin = request.getParameter("receiveTimeBegin");
    String receiveTimeEnd = request.getParameter("receiveTimeEnd");
    String searchLimitTime = request.getParameter("searchLimitTime");
    String limitTimeBegin = request.getParameter("limitTimeBegin");
    String limitTimeEnd = request.getParameter("limitTimeEnd");
    String flowStatusType = request.getParameter("flowStatusType");
    String statProcessId = request.getParameter("statProcessId");
    request.setAttribute("flowStatusType", flowStatusType);
    String databaseType = SystemCommon.getDatabaseType();
    String where = "";
    if (searchReceiveTime != null && "1".equals(searchReceiveTime)) {
      if (receiveTimeBegin != null && !"".equals(receiveTimeBegin))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and ds.receive_date >=to_date('" + dateFormart(receiveTimeBegin, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and ds.receive_date >='" + dateFormart(receiveTimeBegin, "yyyy/MM/dd") + "'";
        }  
      if (receiveTimeEnd != null && !"".equals(receiveTimeEnd))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and ds.receive_date <=to_date('" + dateFormart(receiveTimeEnd, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and ds.receive_date <='" + dateFormart(receiveTimeEnd, "yyyy/MM/dd") + "'";
        }  
    } 
    if (searchLimitTime != null && "1".equals(searchLimitTime)) {
      if (limitTimeBegin != null && !"".equals(limitTimeBegin))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and ds.expect_donetime >=to_date('" + dateFormart(limitTimeBegin, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and ds.expect_donetime >='" + dateFormart(limitTimeBegin, "yyyy/MM/dd") + "'";
        }  
      if (limitTimeEnd != null && !"".equals(limitTimeEnd))
        if ("oracle".equals(databaseType)) {
          where = String.valueOf(where) + " and ds.expect_donetime <=to_date('" + dateFormart(limitTimeEnd, "yyyy/MM/dd") + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
          where = String.valueOf(where) + " and ds.expect_donetime <='" + dateFormart(limitTimeEnd, "yyyy/MM/dd") + "'";
        }  
    } 
    String itemIds = (request.getParameter("itemIds") == null) ? "" : request.getParameter("itemIds");
    request.setAttribute("sortType", sortType);
    request.setAttribute("orderBy", orderBy);
    if (orderBy != null && sortType != null)
      orderBy = String.valueOf(orderBy) + " " + sortType; 
    int pageSize = page_size;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    int recordCount = 0;
    Map<String, Object> result = null;
    List list = null;
    ChartReceivefileEJBBean b = new ChartReceivefileEJBBean();
    try {
      result = b.getFlowStatusData(orgId, userId, rightCode, Integer.valueOf(pageSize), new Integer(currentPage), orgName, orderBy, where, flowStatusType, userName, itemIds, statProcessId);
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    list = (List)result.get("reList");
    List reCountList = (List)result.get("reCountList");
    try {
      recordCount = ((Integer)result.get("recordCount")).intValue();
    } catch (Exception e) {
      list = new ArrayList();
      recordCount = 0;
    } 
    String url = "";
    Enumeration<String> pNames = request.getParameterNames();
    while (pNames.hasMoreElements()) {
      String name = pNames.nextElement();
      if (!name.equals("pager.offset") && !name.equals("action")) {
        String value = (request.getParameter(name) == null) ? "" : request.getParameter(name);
        url = String.valueOf(url) + "&" + name + "=" + value;
      } 
    } 
    request.setAttribute("urlPara", url.replace("/", "-"));
    request.setAttribute("list", list);
    request.setAttribute("reCountList", reCountList);
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "statProcessId,action,orgName,orderBy,sortType,searchReceiveTime,receiveTimeBegin,receiveTimeEnd,searchLimitTime,limitTimeBegin,limitTimeEnd,flowStatusType,userName");
  }
  
  public String dateFormart(String date, String frmtStr) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(frmtStr);
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
