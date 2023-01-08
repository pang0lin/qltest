package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.EmpRemindPO;
import com.js.oa.hr.personnelmanager.service.EmpRemindBD;
import com.js.util.page.Page;
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

public class EmpRemindAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    EmpRemindActionForm empRemindActionForm = (EmpRemindActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "list" : httpServletRequest.getParameter("flag");
    String tag = "list";
    String fromSystem = httpServletRequest.getParameter("fromSystem");
    httpServletRequest.setAttribute("fromSystem", fromSystem);
    if (flag.equals("list")) {
      tag = "list";
      listEmpRemind(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
    } else if (flag.equals("continue") || flag.equals("close")) {
      tag = "add";
      if (saveEmpRemind(empRemindActionForm, httpServletRequest)) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("delete")) {
      deleteEmpRemind(httpServletRequest);
    } else if (flag.equals("modify")) {
      tag = "modify";
      getSingleEmpRemind(empRemindActionForm, httpServletRequest);
    } else if (flag.equals("update")) {
      tag = "modify";
      if (updateEmpRemind(empRemindActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void listEmpRemind(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("empRemindList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "fromSystem");
  }
  
  private boolean updateEmpRemind(EmpRemindActionForm empRemindActionForm, HttpServletRequest httpServletRequest) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    EmpRemindPO empRemindPO = new EmpRemindPO();
    empRemindPO.setId(empRemindActionForm.getId());
    empRemindPO.setEmpId(empRemindActionForm.getEmpId());
    empRemindPO.setCreateId(empRemindActionForm.getCreateId());
    empRemindPO.setCreater(empRemindActionForm.getCreater());
    empRemindPO.setEmpName(empRemindActionForm.getEmpName());
    empRemindPO.setRemindTime(empRemindActionForm.getRemindTime().trim());
    empRemindPO.setRemindType(empRemindActionForm.getRemindType());
    empRemindPO.setSendToId(empRemindActionForm.getSendToId());
    empRemindPO.setSendToName(empRemindActionForm.getSendToName());
    empRemindPO.setCreateTime(simpleDateFormat.format(new Date()));
    empRemindPO.setState("1");
    return (new EmpRemindBD()).modify(empRemindPO).booleanValue();
  }
  
  private void getSingleEmpRemind(EmpRemindActionForm empRemindActionForm, HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    EmpRemindPO empRemindPO = (new EmpRemindBD()).load(Long.valueOf(Long.parseLong(httpServletRequest.getParameter("remindId"))));
    httpServletRequest.setAttribute("id", empRemindPO.getId());
    httpServletRequest.setAttribute("empId", empRemindPO.getEmpId());
    httpServletRequest.setAttribute("empName", empRemindPO.getEmpName());
    httpServletRequest.setAttribute("remindTime", empRemindPO.getRemindTime().trim());
    httpServletRequest.setAttribute("remindType", empRemindPO.getRemindType());
    httpServletRequest.setAttribute("sendToId", empRemindPO.getSendToId());
    httpServletRequest.setAttribute("sendToName", empRemindPO.getSendToName());
  }
  
  private void deleteEmpRemind(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new EmpRemindBD()).delete(httpServletRequest.getParameter("remindId")));
    listEmpRemind(httpServletRequest);
  }
  
  private boolean saveEmpRemind(EmpRemindActionForm empRemindActionForm, HttpServletRequest httpServletRequest) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    EmpRemindPO empRemindPO = new EmpRemindPO();
    empRemindPO.setEmpId(empRemindActionForm.getEmpId());
    empRemindPO.setCreateId(empRemindActionForm.getCreateId());
    empRemindPO.setCreater(empRemindActionForm.getCreater());
    empRemindPO.setEmpName(empRemindActionForm.getEmpName());
    empRemindPO.setRemindTime(empRemindActionForm.getRemindTime().trim());
    empRemindPO.setRemindType(empRemindActionForm.getRemindType());
    empRemindPO.setSendToId(empRemindActionForm.getSendToId());
    empRemindPO.setSendToName(empRemindActionForm.getSendToName());
    empRemindPO.setCreateTime(simpleDateFormat.format(new Date()));
    empRemindPO.setState("1");
    return (new EmpRemindBD()).save(empRemindPO).booleanValue();
  }
  
  private void listEmpRemind(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String createId = (session.getAttribute("userId") == null) ? "0" : session.getAttribute("userId").toString();
    String viewSql = " erpo.id, erpo.empName,erpo.remindTime,erpo.remindType,erpo.sendToName ";
    String fromSql = " com.js.oa.hr.personnelmanager.po.EmpRemindPO erpo ";
    String whereSql = " where erpo.createId=" + createId + " order by erpo.id desc";
    listEmpRemind(httpServletRequest, viewSql, fromSql, whereSql);
  }
}
