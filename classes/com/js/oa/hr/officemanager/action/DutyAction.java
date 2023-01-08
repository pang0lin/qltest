package com.js.oa.hr.officemanager.action;

import com.js.oa.hr.officemanager.po.DutyPO;
import com.js.oa.hr.officemanager.service.DutyBD;
import com.js.oa.hr.personnelmanager.bean.NewDutyEJBBean;
import com.js.oa.hr.personnelmanager.service.NewDutyBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DutyAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    DutyActionForm dutyActionForm = (DutyActionForm)actionForm;
    DutyBD dutyBD = new DutyBD();
    String action = httpServletRequest.getParameter("action");
    String fromSystem = httpServletRequest.getParameter("fromSystem");
    httpServletRequest.setAttribute("fromSystem", fromSystem);
    String tag = "";
    if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("view") || action.equals("search")) {
      tag = "view";
      list(actionMapping, actionForm, httpServletRequest, 
          httpServletResponse);
    } else if (action.equals("saveclose") || action.equals("savecontinue")) {
      DutyPO dutyPO = new DutyPO();
      String dutyName = dutyActionForm.getDutyName();
      String dutyLevel = dutyActionForm.getDutyLevel();
      dutyPO.setDutyName(dutyName.trim());
      dutyPO.setDutyNO(dutyActionForm.getDutyNO().trim());
      dutyPO.setDutyLevel(dutyLevel);
      dutyPO.setDutyDescribe(dutyActionForm.getDutyDescribe());
      dutyPO.setDomainId(domainId);
      if (session.getAttribute("sysManager").toString().indexOf("1") >= 0 && 
        httpServletRequest.getParameter("fromSystem") != null && 
        "1".equals(httpServletRequest.getParameter("fromSystem"))) {
        dutyPO.setCorpId(Long.valueOf(0L));
      } else {
        dutyPO.setCorpId(Long.valueOf(session.getAttribute("corpId").toString()));
      } 
      int result = (new NewDutyBD()).add(dutyPO);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
      } else {
        dutyActionForm.setDutyName("");
      } 
      dutyActionForm.setDutyLevel("");
      dutyActionForm.setDutyDescribe("");
      if (action.equals("saveclose")) {
        tag = "saveclose";
        httpServletRequest.setAttribute("shouldClose", "1");
      } else if (action.equals("savecontinue")) {
        tag = "savecontinue";
      } 
    } else if (action.equals("del")) {
      String[] ids = { "" };
      if (httpServletRequest.getParameterValues("batchDel") != null) {
        ids = httpServletRequest.getParameterValues("batchDel");
      } else {
        ids[0] = httpServletRequest.getParameter("id");
      } 
      (new NewDutyEJBBean()).updateDutyBeforDel(ids);
      (new NewDutyBD()).del(ids);
      tag = "view";
      list(actionMapping, actionForm, httpServletRequest, 
          httpServletResponse);
    } else if (action.equals("modify")) {
      tag = "add";
      DutyPO dutyPO = (new NewDutyBD()).getSingle(httpServletRequest
          .getParameter("dutyId"));
      dutyActionForm.setDutyNO(dutyPO.getDutyNO());
      dutyActionForm.setDutyName(dutyPO.getDutyName().trim());
      dutyActionForm.setDutyLevel(dutyPO.getDutyLevel());
      dutyActionForm.setDutyDescribe(dutyPO.getDutyDescribe());
    } else if (action.equals("update")) {
      tag = "add";
      DutyPO dutyPO = new DutyPO();
      dutyPO.setId(Long.parseLong(httpServletRequest.getParameter(
              "dutyId")));
      dutyPO.setDutyName(dutyActionForm.getDutyName().trim());
      dutyPO.setDutyLevel(dutyActionForm.getDutyLevel());
      dutyPO.setDutyDescribe(dutyActionForm.getDutyDescribe());
      dutyPO.setDutyNO(dutyActionForm.getDutyNO());
      int result = (new NewDutyBD()).update(dutyPO);
      if (result > 0) {
        httpServletRequest.setAttribute("opResult", 
            String.valueOf(result));
      } else {
        dutyActionForm.setDutyName("");
      } 
      httpServletRequest.setAttribute("shouldClose", "1");
    } else if (action.equals("view2")) {
      tag = action;
      view2(actionMapping, actionForm, httpServletRequest, 
          httpServletResponse);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String wherePara = "";
    if (httpServletRequest.getParameter("searchDutyName") != null && 
      !"".equals(httpServletRequest.getParameter("searchDutyName")) && 
      
      !"null".equals(httpServletRequest.getParameter("searchDutyName").toLowerCase()))
      wherePara = String.valueOf(wherePara) + " and po.dutyName like '%" + httpServletRequest.getParameter("searchDutyName") + "%'"; 
    if (httpServletRequest.getParameter("searchDutyLevel") != null && 
      !"".equals(httpServletRequest.getParameter("searchDutyLevel")) && 
      
      !"null".equals(httpServletRequest.getParameter("searchDutyLevel").toLowerCase()))
      wherePara = String.valueOf(wherePara) + " and po.dutyLevel='" + httpServletRequest.getParameter("searchDutyLevel") + "'"; 
    String hql = "";
    if (SystemCommon.getMultiDepart() == 1)
      if (session.getAttribute("sysManager").toString().indexOf("1") >= 0 && "admin".equals(session.getAttribute("userAccount").toString()) && 
        httpServletRequest.getParameter("fromSystem") != null && 
        httpServletRequest.getParameter("fromSystem").equals("1")) {
        hql = "po.corpId=0 and";
      } else {
        hql = "(po.corpId=0 or po.corpId=" + session.getAttribute("corpId") + ") and";
      }  
    Page page = new Page(
        "po.id,po.dutyName,po.dutyLevel,po.corpId,po.dutyNO", 
        "com.js.oa.hr.officemanager.po.DutyPO po", 
        " where " + hql + " po.domainId=" + domainId + wherePara + " order by po.dutyLevel,po.id desc ");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("dutyList", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchDutyName,searchDutyLevel,fromSystem");
  }
  
  private void view2(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String wherePara = " and po.dutyName = '" + httpServletRequest.getParameter("empDuty") + "'";
    Page page = new Page(
        "po", 
        "com.js.oa.hr.officemanager.po.DutyPO po", 
        " where po.domainId=" + domainId + wherePara + " order by po.dutyLevel,po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    httpServletRequest.setAttribute("dutyList", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,searchDutyName,searchDutyLevel,fromSystem");
  }
}
