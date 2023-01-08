package com.js.oa.hr.finance.action;

import com.js.oa.hr.finance.service.FTableService;
import com.js.oa.hr.finance.service.FTaxService;
import com.js.util.config.SystemCommon;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FTaxAction extends Action {
  private static final String masCityService = null;
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    FSalaryActionForm form = (FSalaryActionForm)actionForm;
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    FTaxService services = new FTaxService();
    Long domainId = (session.getAttribute("domainId") != null) ? Long.valueOf(session.getAttribute("domainId").toString()) : 
      Long.valueOf("0");
    FTableService ftableService = new FTableService();
    if ("list".equals(action)) {
      try {
        String salaryFlag = (session.getAttribute("salaryFlag") == null) ? "" : session.getAttribute("salaryFlag").toString();
        String financeSalaryCheckPassWord = SystemCommon.getFinanceSalaryCheckPassWord();
        if (financeSalaryCheckPassWord != null && "0".equals(financeSalaryCheckPassWord)) {
          services.list(request);
        } else if (!"".equals(salaryFlag) && salaryFlag.length() == 20 && salaryFlag.indexOf("1") == 0) {
          String dateStr = salaryFlag.substring(1);
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          Date date = dateFormat.parse(dateStr);
          Calendar dateCalendar = Calendar.getInstance();
          dateCalendar.setTime(date);
          dateCalendar.set(12, dateCalendar.get(12) + 10);
          date = dateCalendar.getTime();
          if ((new Date()).before(date)) {
            services.list(request);
          } else {
            request.setAttribute("javaAction", "fTaxAction");
            request.setAttribute("action", "list");
            return actionMapping.findForward("forward");
          } 
        } else {
          request.setAttribute("javaAction", "fTaxAction");
          request.setAttribute("action", "list");
          return actionMapping.findForward("forward");
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward(action);
    } 
    if ("delete".equals(action)) {
      try {
        String ids = request.getParameter("ids");
        String deleteFlag = request.getParameter("deleteFlag");
        boolean b = false;
        if (ids != null && !"".equals(ids))
          try {
            b = services.delete(ids, deleteFlag);
          } catch (Exception e) {
            e.printStackTrace();
          }  
        String flag = null;
        if (b) {
          flag = "deletesuccess";
        } else {
          flag = "deleteerror";
        } 
        request.setAttribute("flag", flag);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("close");
    } 
    if ("forModi".equals(action)) {
      services.forUpdate(request);
      return actionMapping.findForward(action);
    } 
    if ("modi".equals(action)) {
      boolean re = services.modi(request);
      if (re) {
        request.setAttribute("flag", "updatesuccess");
      } else {
        request.setAttribute("flag", "updateerror");
      } 
      return actionMapping.findForward("close");
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
