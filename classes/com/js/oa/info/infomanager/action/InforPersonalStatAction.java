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

public class InforPersonalStatAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    InforPersonalStatActionForm inforPersonalStatActionForm = (InforPersonalStatActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = session.getAttribute("domainId").toString();
    String corpId = session.getAttribute("corpId").toString();
    String action = httpServletRequest.getParameter("action");
    Date now = new Date();
    String viewSQL = " aaa.orgName,aaa.empName,aaa.monthIssueNum,aaa.accumulateNum ";
    String fromSQL = " com.js.oa.info.infomanager.po.InforPersonalStatPO aaa ";
    String whereSQL = " where aaa.domainId=" + domainId + " and aaa.statYear = " + (now.getYear() + 1900) + " and aaa.statMonth= " + (
      now.getMonth() + 1);
    if (action != null && action.equals("search"))
      whereSQL = " where aaa.domainId=" + domainId + " and aaa.statYear = " + inforPersonalStatActionForm.getStatYear() + " and " + 
        " aaa.statMonth = " + inforPersonalStatActionForm.getStatMonth() + " and " + 
        " aaa.empName like '%" + inforPersonalStatActionForm.getEmpName() + "%' "; 
    if (SystemCommon.getMultiDepart() == 1)
      whereSQL = String.valueOf(whereSQL) + " and aaa.corpId=" + corpId; 
    whereSQL = String.valueOf(whereSQL) + " order by aaa.monthIssueNum desc, aaa.orgIdString";
    list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    return actionMapping.findForward("success");
  }
  
  public void view(HttpServletRequest httpServletRequest) {
    String domainId = httpServletRequest.getSession(true).getAttribute("domainId").toString();
    Date now = new Date();
    String viewSQL = " aaa.orgName,aaa.empName,aaa.monthIssueNum,aaa.accumulateNum ";
    String fromSQL = " com.js.oa.info.infomanager.po.InforPersonalStatPO aaa ";
    String whereSQL = " where aaa.domainId=" + domainId + " and aaa.statYear = " + (now.getYear() + 1900) + " and aaa.statMonth= " + (
      now.getMonth() + 1) + " order by aaa.orgIdString ";
    list(httpServletRequest, viewSQL, fromSQL, whereSQL);
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
    httpServletRequest.setAttribute("inforPersonStat", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,statYear,statMonth,empName");
  }
}
