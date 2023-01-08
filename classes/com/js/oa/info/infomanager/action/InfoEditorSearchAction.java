package com.js.oa.info.infomanager.action;

import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InfoEditorSearchAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    List list = null;
    HttpSession session = httpServletRequest.getSession(true);
    String action = "";
    action = httpServletRequest.getParameter("action");
    String searchDate = httpServletRequest.getParameter("searchDate");
    String searchBeginDate = httpServletRequest.getParameter("searchBeginDate");
    httpServletRequest.setAttribute("searchBeginDate", searchBeginDate);
    String searchEndDate = httpServletRequest.getParameter("searchEndDate");
    httpServletRequest.setAttribute("searchEndDate", searchEndDate);
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    String domainId = httpSession.getAttribute("domainId").toString();
    String year = "";
    String month = "";
    Calendar now = Calendar.getInstance();
    int y = now.get(1);
    int m = now.get(2) + 1;
    year = String.valueOf(y);
    month = String.valueOf(m);
    if (!"".equals(httpServletRequest.getParameter("searchyear")) && httpServletRequest.getParameter("searchyear") != null)
      year = httpServletRequest.getParameter("searchyear"); 
    httpServletRequest.setAttribute("year", year);
    if (!"".equals(httpServletRequest.getParameter("searchmonth")) && httpServletRequest.getParameter("searchmonth") != null)
      month = httpServletRequest.getParameter("searchmonth"); 
    httpServletRequest.setAttribute("month", month);
    String informationAuthor = "info1.informationAuthor is not null";
    String documentEditor = "info1.documentEditor is not null";
    String condition = "";
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      condition = " year(info1.informationIssueTime)=" + year + " and MONTH(info1.informationIssueTime)=" + month;
    } else {
      condition = " JSDB.FN_DATENAME('YEAR', info1.informationIssueTime)=" + year + " and JSDB.FN_DATENAME('MONTH', info1.informationIssueTime)=" + month;
    } 
    if ("search".equals(action)) {
      String infoeditor = "2006";
      String infoeditorcomp = "8";
      if (httpServletRequest.getParameter("infoeditor") != null && !httpServletRequest.getParameter("infoeditor").equals("")) {
        infoeditor = httpServletRequest.getParameter("infoeditor");
        informationAuthor = "info1.informationAuthor like '%" + infoeditor + "%' ";
      } 
      if (httpServletRequest.getParameter("infoeditorcomp") != null && !httpServletRequest.getParameter("infoeditorcomp").equals("")) {
        infoeditorcomp = httpServletRequest.getParameter("infoeditorcomp");
        documentEditor = "info1.documentEditor like '%" + infoeditorcomp + "%' ";
      } 
    } 
    String viewSQL = "distinct info1.informationAuthor,info1.documentEditor ";
    String fromSQL = " from com.js.oa.info.infomanager.po.InformationPO  info1 where info1.domainId=" + domainId + " and " + informationAuthor + " and " + documentEditor + " and info1.informationStatus=0  and ( info1.afficeHistoryDate is null or info1.afficeHistoryDate =-1 )  and (info1.informationOrISODoc is null or info1.informationOrISODoc='0' ) and " + condition;
    String whereSQL = " ";
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    String tag = "success";
    httpServletRequest.setAttribute("searchList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "infoeditor,infoeditorcomp,searchDate,searchBeginDate,searchEndDate,searchtype,year,month");
    return actionMapping.findForward(tag);
  }
}
