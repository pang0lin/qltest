package com.js.oa.hr.kq.szgt.action;

import com.js.oa.hr.kq.szgt.service.KQCaculate;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.sql.Page;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SKqReportAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    HttpSession session = httpServletRequest.getSession();
    String corpId = session.getAttribute("corpId").toString();
    String currentMonth = httpServletRequest.getParameter("currentMonth");
    if (currentMonth == null) {
      SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
      currentMonth = format.format(Calendar.getInstance().getTime());
    } 
    httpServletRequest.setAttribute("currentMonth", currentMonth);
    if (action.equals("list")) {
      if ("1".equals(httpServletRequest.getParameter("dataLock")))
        (new KQCaculate()).dataLock(corpId, currentMonth); 
      list(httpServletRequest, 15, currentMonth);
    } else if ("export".equals(action)) {
      list(httpServletRequest, 99999, currentMonth);
      tag = "excel";
    } else if ("caculate".equals(action)) {
      int year = Integer.parseInt(currentMonth.substring(0, 4));
      int month = Integer.parseInt(currentMonth.substring(4, 6));
      KQCaculate.executeCaculate(year, month, corpId);
      tag = "caculated";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request, int pageSize, String currentMonth) throws NumberFormatException, Exception {
    HttpSession session = request.getSession(true);
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String corpId = session.getAttribute("corpId").toString();
    int dataLockStatus = (new KQCaculate()).getDataLockStatus(corpId, currentMonth);
    request.setAttribute("dataLockStatus", String.valueOf(dataLockStatus));
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String para = "report_id,orgname,empName,duty_sum,nosign_sum,shouldduty,signonduty,businesstrip";
    para = String.valueOf(para) + ",overtime,leaveinlieu,annualleave,sickleave,workinjury,marriageleave,maternityleave,funeralleave";
    para = String.valueOf(para) + ",unpaidsick,unpaidleave,stopwork,absenteeism";
    para = String.valueOf(para) + ",annualremain,annualsum,unpaidsicksum,unpaidleavesum";
    String from = "skq_report skq";
    String where = " where report_month=" + currentMonth;
    ManagerService service = new ManagerService();
    if (service.hasRight(session.getAttribute("userId").toString(), "07*55*11"))
      request.setAttribute("canCaculate", "1"); 
    String whereSQL = service.getRightFinalWhere(curUserId, curOrgId, 
        "07*55*55", "oo.org_id", "oe.emp_id");
    where = String.valueOf(where) + " and exists (select oe.emp_id from org_employee oe,org_organization_user oou,org_organization oo where oe.emp_id=oou.emp_id and oe.userisactive=1 and oe.userisdeleted=0 and oou.org_id=oo.org_id and skq.emp_id=oe.emp_id and ((" + 
      whereSQL + ") or oe.emp_id=" + curUserId + ") and oo.orgidstring like '%$" + corpId + "$%'";
    String searchOrgName = request.getParameter("searchOrgName");
    String searchOrgId = request.getParameter("searchOrgId");
    if (searchOrgId != null && !"".equals(searchOrgId))
      where = String.valueOf(where) + " and oo.orgidstring like '%$" + searchOrgId + "$%'"; 
    where = String.valueOf(where) + ")";
    String userName = request.getParameter("userName");
    if (userName != null && !"".equals(userName))
      where = String.valueOf(where) + " and empname like '%" + userName + "%'"; 
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,currentMonth,userName,searchOrgId,searchOrgName");
  }
}
