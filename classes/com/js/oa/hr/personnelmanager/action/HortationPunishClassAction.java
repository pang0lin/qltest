package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.HortationPunishPO;
import com.js.oa.hr.personnelmanager.service.HortationPunishClassBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HortationPunishClassAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HortationPunishClassActionForm hortationPunishClassActionForm = 
      (HortationPunishClassActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : 
      httpServletRequest.getParameter("flag");
    String tag = "view";
    if (flag.equals("view")) {
      tag = "view";
      view(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
    } else if (flag.equals("continue") || flag.equals("close")) {
      tag = "add";
      if (save(hortationPunishClassActionForm, httpServletRequest)) {
        if (flag.equals("continue")) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("delete")) {
      delete(httpServletRequest);
    } else if (flag.equals("modify")) {
      tag = "modify";
      getSingle(hortationPunishClassActionForm, httpServletRequest);
    } else if (flag.equals("update")) {
      tag = "modify";
      if (update(hortationPunishClassActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if (flag.equals("toview")) {
      tag = "toview";
      getSingle(hortationPunishClassActionForm, httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private boolean save(HortationPunishClassActionForm hortationPunishClassActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    HortationPunishPO hortationPunishPO = new HortationPunishPO();
    hortationPunishPO.setHp_name(hortationPunishClassActionForm.getHp_name());
    hortationPunishPO.setHp_type(hortationPunishClassActionForm.getHp_type());
    hortationPunishPO.setHp_apply(hortationPunishClassActionForm
        .getHp_apply());
    hortationPunishPO.setHp_dealwith(hortationPunishClassActionForm
        .getHp_dealwith());
    hortationPunishPO.setDomain(new Long(domain));
    hortationPunishPO.setHp_describe(hortationPunishClassActionForm
        .getHp_describe());
    return (new HortationPunishClassBD()).saveHortationPunish(
        hortationPunishPO).booleanValue();
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSql = 
      "aaa.id,aaa.hp_name,aaa.hp_type,aaa.hp_apply,aaa.hp_dealwith,aaa.hp_describe";
    String fromSql = 
      "com.js.oa.hr.personnelmanager.po.HortationPunishPO aaa";
    String whereSql = "where aaa.domain=" + domain + 
      " order by aaa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
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
      httpServletRequest.setAttribute("pager.realCurrent", (
          new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("hortationPunishList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new HortationPunishClassBD())
        .deleteHortationPunish(
          httpServletRequest.getParameter("hortationPunishId")));
    view(httpServletRequest);
  }
  
  private void getSingle(HortationPunishClassActionForm hortationPunishClassActionForm, HttpServletRequest httpServletRequest) {
    HortationPunishPO po = new HortationPunishPO();
    po = (new HortationPunishClassBD()).getSingleHortationPunish(
        new Long(httpServletRequest.getParameter("hortationPunishId")));
    hortationPunishClassActionForm.setHp_name(po.getHp_name());
    hortationPunishClassActionForm.setHp_type(po.getHp_type());
    hortationPunishClassActionForm.setHp_apply(po.getHp_apply());
    hortationPunishClassActionForm.setHp_dealwith(po.getHp_dealwith());
    hortationPunishClassActionForm.setHp_describe(po.getHp_describe());
  }
  
  private boolean update(HortationPunishClassActionForm hortationPunishClassActionForm, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domain = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    HortationPunishPO hortationPunishPO = new HortationPunishPO();
    hortationPunishPO.setHp_name(hortationPunishClassActionForm.getHp_name());
    hortationPunishPO.setHp_type(hortationPunishClassActionForm.getHp_type());
    hortationPunishPO.setHp_apply(hortationPunishClassActionForm
        .getHp_apply());
    hortationPunishPO.setHp_dealwith(hortationPunishClassActionForm
        .getHp_dealwith());
    hortationPunishPO.setHp_describe(hortationPunishClassActionForm
        .getHp_describe());
    hortationPunishPO.setDomain(new Long(domain));
    return (new HortationPunishClassBD()).updateHortationPunish(
        hortationPunishPO, new Long(httpServletRequest.getParameter(
            "hortationPunishId"))).booleanValue();
  }
}
