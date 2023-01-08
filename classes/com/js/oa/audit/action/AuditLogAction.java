package com.js.oa.audit.action;

import com.js.oa.message.service.MsManageBD;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AuditLogAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String action = request.getParameter("action");
    if ("yiShenHeList".equals(action)) {
      yiShenHeList(request);
      return actionMapping.findForward("yiShenHeList");
    } 
    if ("daiShenHeList".equals(action)) {
      daiShenHeList(request);
      return actionMapping.findForward("daiShenHeList");
    } 
    return actionMapping.findForward("bbs_forward");
  }
  
  public void yiShenHeList(HttpServletRequest request) {
    String wherePara = "";
    String submitEmpname = request.getParameter("submitEmpname");
    String submitOrgName = request.getParameter("submitOrgName");
    String checkEmpname = request.getParameter("checkEmpname");
    String start_date = request.getParameter("start_date");
    String end_date = request.getParameter("end_date");
    String auditModule = request.getParameter("auditModule");
    String searchDate = request.getParameter("searchDate");
    if (start_date != null && !"".equals(start_date))
      start_date = dateFormart(start_date); 
    if (end_date != null && !"".equals(end_date))
      try {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        Date datef = bartDateFormat.parse(end_date);
        end_date = format.format(datef);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(datef);
        calendar.add(5, 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        end_date = dateFormat.format(calendar.getTime());
      } catch (ParseException e) {
        e.printStackTrace();
      }  
    if (submitEmpname != null && !"".equals(submitEmpname))
      wherePara = String.valueOf(wherePara) + " and adt.submitEmpname like '%" + submitEmpname + "%' "; 
    if (submitOrgName != null && !"".equals(submitOrgName))
      wherePara = String.valueOf(wherePara) + " and org.orgName like '%" + submitOrgName + "%' "; 
    if (checkEmpname != null && !"".equals(checkEmpname))
      wherePara = String.valueOf(wherePara) + " and adt.checkEmpname like '%" + checkEmpname + "%' "; 
    if (auditModule != null && !"".equals(auditModule))
      wherePara = String.valueOf(wherePara) + " and adl.moduleName like '%" + auditModule + "%' "; 
    String databaseType = SystemCommon.getDatabaseType();
    if (start_date != null && !"".equals(start_date) && "1".equals(searchDate))
      if ("oracle".equals(databaseType)) {
        wherePara = String.valueOf(wherePara) + " and adt.checkTime >=to_date('" + start_date + "','yyyy-MM-dd HH24:mi:ss')";
      } else {
        wherePara = String.valueOf(wherePara) + " and adt.checkTime > '" + start_date + "'";
      }  
    if (end_date != null && !"".equals(end_date) && "1".equals(searchDate))
      if ("oracle".equals(databaseType)) {
        wherePara = String.valueOf(wherePara) + " and adt.checkTime <=to_date('" + end_date + "','yyyy-MM-dd HH24:mi:ss')";
      } else {
        wherePara = String.valueOf(wherePara) + " and adt.checkTime <='" + end_date + "'";
      }  
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " where adt.submitOrgid=org.orgId and adt.auditModule=adl.moduleId and adt.ischecked=1 ";
    HttpSession session = request.getSession();
    String userId = session.getAttribute("userId").toString();
    if (!"-99".equals(userId))
      where = String.valueOf(where) + " and adt.checkEmpid=" + userId; 
    where = String.valueOf(where) + wherePara;
    Page page = new Page(" adt.logId,adt.submitEmpname,org.orgName,adt.submitTime,adt.checkEmpname,adl.moduleName,adt.auditStatus,adl.moduleId,adl.url,adt.auditStatus,adt.checkTime", 
        " com.js.oa.audit.po.AuditLog adt,com.js.system.vo.organizationmanager.OrganizationVO org,com.js.oa.audit.po.AuditModule adl ", 
        String.valueOf(where) + " order by adt.submitTime desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("submitEmpname", submitEmpname);
    request.setAttribute("submitOrgName", submitOrgName);
    request.setAttribute("auditModule", auditModule);
    request.setAttribute("checkEmpname", checkEmpname);
    request.setAttribute("start_date", start_date);
    request.setAttribute("end_date", end_date);
    request.setAttribute("searchDate", searchDate);
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,submitEmpname,submitOrgName,auditModule,checkEmpname,start_date,end_date,searchDate");
  }
  
  public void daiShenHeList(HttpServletRequest request) {
    String wherePara = "";
    String submitEmpname = request.getParameter("submitEmpname");
    String submitOrgName = request.getParameter("submitOrgName");
    String checkEmpname = request.getParameter("checkEmpname");
    String start_date = request.getParameter("start_date");
    String end_date = request.getParameter("end_date");
    String auditModule = request.getParameter("auditModule");
    String searchDate = request.getParameter("searchDate");
    if (start_date != null && !"".equals(start_date))
      start_date = dateFormart(start_date); 
    if (end_date != null && !"".equals(end_date))
      try {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        Date datef = bartDateFormat.parse(end_date);
        end_date = format.format(datef);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(datef);
        calendar.add(5, 1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        end_date = dateFormat.format(calendar.getTime());
      } catch (ParseException e) {
        e.printStackTrace();
      }  
    if (submitEmpname != null && !"".equals(submitEmpname))
      wherePara = String.valueOf(wherePara) + " and adt.submitEmpname like '%" + submitEmpname + "%' "; 
    if (submitOrgName != null && !"".equals(submitOrgName))
      wherePara = String.valueOf(wherePara) + " and org.orgName like '%" + submitOrgName + "%' "; 
    if (checkEmpname != null && !"".equals(checkEmpname))
      wherePara = String.valueOf(wherePara) + " and adt.checkEmpname like '%" + checkEmpname + "%' "; 
    if (auditModule != null && !"".equals(auditModule))
      wherePara = String.valueOf(wherePara) + " and adl.moduleName like '%" + auditModule + "%' "; 
    String databaseType = SystemCommon.getDatabaseType();
    if (start_date != null && !"".equals(start_date) && "1".equals(searchDate))
      if ("oracle".equals(databaseType)) {
        wherePara = String.valueOf(wherePara) + " and adt.submitTime >=to_date('" + start_date + "','yyyy-MM-dd HH24:mi:ss')";
      } else {
        wherePara = String.valueOf(wherePara) + " and adt.submitTime >='" + start_date + "'";
      }  
    if (end_date != null && !"".equals(end_date) && "1".equals(searchDate))
      if ("oracle".equals(databaseType)) {
        wherePara = String.valueOf(wherePara) + " and adt.submitTime <=to_date('" + end_date + "','yyyy-MM-dd HH24:mi:ss')";
      } else {
        wherePara = String.valueOf(wherePara) + " and adt.submitTime <='" + end_date + "'";
      }  
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String where = " where adt.submitOrgid=org.orgId and adt.auditModule=adl.moduleId and adt.ischecked=0 ";
    HttpSession session = request.getSession();
    String urerId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    MsManageBD msBD = new MsManageBD();
    OrganizationBD orgBD = new OrganizationBD();
    String sql = "select po.managerId,po.rightScopeType,po.rightScopeScope from com.js.oa.audit.po.AuditManager po where po.empId=" + urerId;
    List<Object[]> yourList = null;
    try {
      yourList = msBD.getListByYourSQL(sql);
      if (yourList != null && yourList.size() != 0) {
        Object[] obj = yourList.get(0);
        if ("0".equals((obj[1] == null) ? "" : obj[1].toString()))
          where = String.valueOf(where) + " and adt.submitEmpid=" + urerId; 
        if ("1".equals((obj[1] == null) ? "" : obj[1].toString()))
          where = String.valueOf(where) + " and adt.submitOrgid=" + orgId; 
        if ("2".equals((obj[1] == null) ? "" : obj[1].toString()))
          where = String.valueOf(where) + " and adt.submitOrgid in (" + orgBD.getAllSubOrgIdByOrgId(orgId) + ") "; 
        if ("4".equals((obj[1] == null) ? "" : obj[1].toString()) && obj[2] != null && !"".equals(obj[2])) {
          String rightScopeScope = obj[2].toString();
          rightScopeScope = rightScopeScope.substring(1, rightScopeScope.length() - 1).replace("**", ",");
          where = String.valueOf(where) + " and adt.submitOrgid in (" + rightScopeScope + ") ";
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    where = String.valueOf(where) + wherePara;
    Page page = new Page(" adt.logId,adt.submitEmpname,org.orgName,adt.submitTime,adt.checkEmpname,adl.moduleName,adt.auditStatus,adl.moduleId,adl.url ", 
        " com.js.oa.audit.po.AuditLog adt,com.js.system.vo.organizationmanager.OrganizationVO org,com.js.oa.audit.po.AuditModule adl ", 
        String.valueOf(where) + " order by adt.submitTime desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("submitEmpname", submitEmpname);
    request.setAttribute("submitOrgName", submitOrgName);
    request.setAttribute("auditModule", auditModule);
    request.setAttribute("checkEmpname", checkEmpname);
    request.setAttribute("start_date", start_date);
    request.setAttribute("end_date", end_date);
    request.setAttribute("searchDate", searchDate);
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,submitEmpname,submitOrgName,auditModule,checkEmpname,start_date,end_date,searchDate");
  }
  
  public String dateFormart(String date) {
    try {
      SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return date;
  }
}
