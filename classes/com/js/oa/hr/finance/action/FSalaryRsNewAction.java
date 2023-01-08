package com.js.oa.hr.finance.action;

import com.js.oa.hr.finance.bean.FSalaryRsNewEJBBean;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FSalaryRsNewAction extends Action {
  String[] keys = null;
  
  String[] values = null;
  
  String message = "";
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    FSalaryRsNewActionForm form = (FSalaryRsNewActionForm)actionForm;
    String year = form.getYear();
    String month = form.getMonth();
    String action = request.getParameter("action");
    if (year == null || month == null || "".equals(year) || "".equals(month)) {
      Calendar c = Calendar.getInstance();
      c.add(2, -1);
      year = (new StringBuilder(String.valueOf(c.getTime().getYear() + 1900))).toString();
      month = (new StringBuilder(String.valueOf(c.getTime().getMonth() + 1))).toString();
      month = "0" + month;
      month = month.substring(month.length() - 2, month.length());
      form.setYear(year);
      form.setMonth(month);
    } 
    String tag = "";
    if ("view".equals(action)) {
      tag = "view";
      list(request);
    } else if ("showSalary".equals(action)) {
      tag = "showSalary";
      FSalaryRsNewEJBBean bean = new FSalaryRsNewEJBBean();
      String userId = session.getAttribute("userId").toString();
      Map map = bean.getSalaryInfo(userId, year, month);
      String result = map.get("result").toString();
      request.setAttribute("message", result);
      if ("".equals(result)) {
        request.setAttribute("org", map.get("org"));
        request.setAttribute("sfrq", map.get("sfrq"));
        request.setAttribute("sfgz", map.get("sfgz"));
        request.setAttribute("bz", map.get("bz"));
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("banks", map.get("banks"));
        request.setAttribute("lefts", map.get("lefts"));
        request.setAttribute("rights", map.get("rights"));
        request.setAttribute("userName", session.getAttribute("userName"));
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " 1=1";
    String viewSql = " po.year,po.month,po.userCount,(select max(empname) from org_employee where emp_id=po.importuser),to_char(importtime,'YYYY-MM-DD hh24:mi:ss'),bz";
    String fromSql = " temp_rsxc_pc po ";
    String orderBy = " year desc,month desc ";
    PageUtil page = new PageSqlUtil();
    List<Object> list = page.list(httpServletRequest, viewSql, fromSql, where, orderBy);
    httpServletRequest.setAttribute("pcList", list);
  }
}
