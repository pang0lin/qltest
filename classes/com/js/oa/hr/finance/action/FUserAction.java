package com.js.oa.hr.finance.action;

import com.js.oa.hr.finance.service.FUserService;
import com.js.oa.security.log.service.LogBD;
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

public class FUserAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "";
    String moduleName = "";
    String oprType = "";
    String oprContent = "";
    String ipAddress = request.getRemoteAddr();
    String domainIdStr = (session.getAttribute("domainId").toString() != null) ? session.getAttribute("domainId").toString() : "";
    String action = request.getParameter("action");
    FUserActionForm form = (FUserActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgName = session.getAttribute("orgName").toString();
    FUserService fUserService = new FUserService();
    if ("updatePass".equals(action)) {
      try {
        form.setEmpId(new Long(userId));
        form.setEmpName(userName);
        form.setOrgId(new Long(orgId));
        form.setOrgName(orgName);
        int re = fUserService.updatePass(request, form);
        String message = "修改成功！";
        if (re == 0)
          message = "修改失败！"; 
        if (2 == re)
          message = "原密码错误！"; 
        if (3 == re)
          message = "其他错误！"; 
        request.setAttribute("fmessage", message);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      Date endDate = new Date();
      moduleCode = "oa_personalwork";
      moduleName = "个人设置-密码设置";
      oprType = "2";
      oprContent = "修改工资密码";
      logBD.log(userId, userName, orgName, moduleCode, moduleName, startDate, endDate, oprType, oprContent, ipAddress, domainIdStr);
      return actionMapping.findForward(action);
    } 
    if ("searchUsers".equals(action)) {
      listUser(request);
      return actionMapping.findForward(action);
    } 
    if ("userList".equals(action)) {
      listUser(request);
      return actionMapping.findForward(action);
    } 
    if ("resetPwd".equals(action)) {
      String message = "重置失败！";
      boolean flag = fUserService.updateResetPwd(request);
      if (flag)
        message = "重置成功！"; 
      listUser(request);
      request.setAttribute("message", message);
      return actionMapping.findForward(action);
    } 
    if ("forMod".equals(action))
      return actionMapping.findForward(action); 
    if ("modPwd".equals(action)) {
      String message = "修改失败！";
      boolean flag = fUserService.updateResetPwd(request);
      if (flag)
        message = "修改成功！"; 
      listUser(request);
      request.setAttribute("message", message);
      return actionMapping.findForward(action);
    } 
    return actionMapping.findForward("taskList");
  }
  
  public void listUser(HttpServletRequest request) {
    String content = request.getParameter("empName");
    int offset_page = 0;
    if (request.getParameter("pager.offset") != null && !"".equals(request.getParameter("pager.offset")))
      offset_page = Integer.parseInt(request.getParameter("pager.offset")); 
    int pageSize = 15;
    int currentPage = offset_page / pageSize + 1;
    Page page_ol = null;
    String sqlHead = " po.id,po.empId,po.empName,po.orgName";
    String table = " com.js.oa.hr.finance.po.FUser po ";
    String where = "";
    if (!"".equals(content) && content != null)
      where = " where po.empName like '%" + content + "%'  "; 
    String orderBy = " order by po.id desc ";
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
    request.setAttribute("listUsers", myList);
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
