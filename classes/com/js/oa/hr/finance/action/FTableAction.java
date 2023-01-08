package com.js.oa.hr.finance.action;

import com.js.oa.hr.finance.service.FTableService;
import com.js.util.page.Page;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FTableAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    FTableActionForm form = (FTableActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    FTableService ftableService = new FTableService();
    if ("tableList".equals(action)) {
      try {
        tableList(request);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("loadftableinfo".equals(action)) {
      try {
        String tableId = request.getParameter("tableName");
        if (request.getParameter("reload") != null)
          request.setAttribute("reload", "yes"); 
        String[][] tableInfo = ftableService.getSimpleTableInfo(tableId);
        String[][] fieldInfoList = ftableService.getAllFieldInfo(tableId);
        request.setAttribute("tableInfo", tableInfo);
        request.setAttribute("fieldInfoList", fieldInfoList);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("updateftableinfo".equals(action)) {
      try {
        boolean re = ftableService.updateFTableinfo(request);
        String tableName = request.getParameter("tableName");
        if (re) {
          request.setAttribute("flag", "1");
        } else {
          request.setAttribute("flag", "0");
        } 
        String opResult = request.getParameter("opResult");
        if ("saveonly".equals(opResult))
          return new ActionForward("/fTableAction.do?action=loadftableinfo&tableName=" + tableName); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("close");
    } 
    return actionMapping.findForward("taskList");
  }
  
  public void tableList(HttpServletRequest request) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.tableId,po.tableName,po.tableDesname,po.createdDate ";
    String table = " com.js.oa.hr.finance.po.FTable po ";
    String where = " where  1=1 ";
    String orderBy = " order by po.tableDesname desc ";
    page_ol = new Page(sqlHead, table, String.valueOf(where) + orderBy);
    page_ol.setPageSize(pageSize);
    page_ol.setcurrentPage(currentPage);
    List myList = page_ol.getResultList();
    int recordCount = page_ol.getRecordCount();
    if (offset_page >= recordCount) {
      offset_page = (recordCount - pageSize) / pageSize;
      currentPage = offset_page + 1;
      offset_page *= pageSize;
      page_ol.setcurrentPage(currentPage);
      myList = page_ol.getResultList();
      recordCount = page_ol.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset_page));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("recordCount", String.valueOf(recordCount));
    request.setAttribute("pageParameters", "action");
    request.setAttribute("myList", myList);
  }
  
  public Date timeFormat(String timeStr) throws ParseException {
    SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
    Date datef = bartDateFormat.parse(timeStr);
    timeStr = format.format(datef);
    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
    Date date = format2.parse(timeStr);
    return date;
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
