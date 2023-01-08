package com.js.oa.hr.kq.szgt.action;

import com.js.oa.hr.kq.szgt.service.HolidayUtil;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.sql.Page;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SKqHolidayStatAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    HttpSession session = httpServletRequest.getSession();
    if (action.equals("list")) {
      list(httpServletRequest, 15);
      tag = "list";
    } else if (action.equals("add")) {
      tag = "add";
    } else if ("export".equals(action)) {
      list(httpServletRequest, 99999);
      tag = "export";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest request, int pageSize) throws NumberFormatException, Exception {
    HttpSession session = request.getSession(true);
    String curOrgId = session.getAttribute("orgId").toString();
    String curUserId = session.getAttribute("userId").toString();
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String corpId = session.getAttribute("corpId").toString();
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String para = "oe.emp_id,oe.empname,oo.orgName,oe.empnumber,oe.empfiredate,oe.intoCompanyDate,oe.jobStatus,oe.zhuanzhengdate,oe.glbdef15 ";
    String from = "org_employee oe,org_organization_user oou,org_organization oo";
    String where = "where oe.emp_id=oou.emp_id and oou.org_id=oo.org_id";
    ManagerService service = new ManagerService();
    String whereSQL = service.getRightFinalWhere(curUserId, curOrgId, 
        "07*55*55", "oo.org_id", "oe.emp_id");
    where = String.valueOf(where) + " and oo.orgidstring like '%$" + corpId + "$%' and ((" + whereSQL + ") or oe.emp_id=" + curUserId + ") and oe.userisactive=1 and oe.userisdeleted=0";
    String userName = request.getParameter("userName");
    String searchOrgName = request.getParameter("searchOrgName");
    String searchOrgId = request.getParameter("searchOrgId");
    if (userName != null && !"".equals(userName))
      where = String.valueOf(where) + " and oe.empname like '%" + userName + "%'"; 
    if (searchOrgId != null && !"".equals(searchOrgId))
      where = String.valueOf(where) + " and oo.orgidstring like '%$" + searchOrgId + "$%'"; 
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List<String[]> list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    Calendar calendar = Calendar.getInstance();
    request.setAttribute("list", (new HolidayUtil()).getEmpHoliday(calendar, list, corpId));
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,searchOrgName,searchOrgId,userName");
  }
}
