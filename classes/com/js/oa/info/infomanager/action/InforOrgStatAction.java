package com.js.oa.info.infomanager.action;

import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InforOrgStatAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InforOrgStatActionForm inforOrgStatActionForm = (InforOrgStatActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    Date now = new Date();
    String domainId = session.getAttribute("domainId").toString();
    String action = httpServletRequest.getParameter("action");
    String viewSQL = " aaa.informationIssueOrg, count(*) ";
    String fromSQL = " com.js.oa.info.infomanager.po.InformationPO aaa ";
    String whereSQL = " where aaa.domainId=" + domainId;
    if (httpServletRequest.getParameter("orgName") != null)
      whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueOrg like '%" + httpServletRequest.getParameter("orgName") + "%' "; 
    if (httpServletRequest.getParameter("searchDate") != null) {
      String databaseType = SystemCommon.getDatabaseType();
      String searchBeginDate = httpServletRequest.getParameter("searchBeginDate");
      String searchEndDate = httpServletRequest.getParameter("searchEndDate");
      if (databaseType.indexOf("mysql") >= 0) {
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime between '" + searchBeginDate + "' and '" + searchEndDate + " 23:59:59' ";
      } else {
        whereSQL = String.valueOf(whereSQL) + " and aaa.informationIssueTime between JSDB.FN_STRTODATE('" + searchBeginDate + "','S') and JSDB.FN_STRTODATE('" + searchEndDate + " 23:59:59', '') ";
      } 
    } 
    if (SystemCommon.getMultiDepart() == 1)
      whereSQL = String.valueOf(whereSQL) + " and aaa.corpId=" + session.getAttribute("corpId").toString(); 
    whereSQL = String.valueOf(whereSQL) + " and  ( aaa.afficeHistoryDate  is null or  aaa.afficeHistoryDate = -1)  and (aaa.informationOrISODoc is null or aaa.informationOrISODoc='0' ) ";
    whereSQL = String.valueOf(whereSQL) + " group by aaa.informationIssueOrg order by aaa.informationIssueOrg";
    list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    return actionMapping.findForward("success");
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("inforOrgStat", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,orgName,searchDate,searchBeginDate,searchEndDate");
  }
}
