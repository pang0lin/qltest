package com.js.oa.hr.personnelmanager.action;

import com.js.oa.hr.personnelmanager.po.WorkAddressPO;
import com.js.oa.hr.personnelmanager.service.WorkAddressBD;
import com.js.oa.personalwork.person.service.PersonOwnBD;
import com.js.util.page.Page;
import com.js.util.util.StringSplit;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkAddressAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkAddressActionForm workAddressActionForm = 
      (WorkAddressActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "list" : 
      httpServletRequest.getParameter("flag");
    long userId = Long.parseLong(String.valueOf(httpServletRequest
          .getSession(true).getAttribute("userId")));
    String tag = "list";
    PersonOwnBD bd = new PersonOwnBD();
    if (flag.equals("list")) {
      tag = "list";
      view(httpServletRequest);
    } else if (flag.equals("add")) {
      tag = "add";
      Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), "0", 
          httpServletRequest.getSession(true)
          .getAttribute("domainId").toString());
      httpServletRequest.setAttribute("countries", vec.get(0));
    } else {
      if ("city".equals(flag)) {
        httpServletRequest.setAttribute("cities", 
            bd.city(httpServletRequest
              .getParameter("country")));
        return actionMapping.findForward("ifmcity");
      } 
      if ("county".equals(flag)) {
        String country = httpServletRequest.getParameter("country");
        String city = httpServletRequest.getParameter("city");
        httpServletRequest.setAttribute("counties", bd.county(country, city));
        return actionMapping.findForward("ifmcounty");
      } 
      if (flag.equals("close") || flag.equals("continue")) {
        tag = "add";
        if (save(workAddressActionForm, httpServletRequest)) {
          if (flag.equals("continue")) {
            httpServletRequest.setAttribute("close", "0");
          } else {
            httpServletRequest.setAttribute("close", "1");
          } 
        } else {
          httpServletRequest.setAttribute("close", "2");
        } 
        Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), "0", 
            httpServletRequest.getSession(true)
            .getAttribute("domainId").toString());
        httpServletRequest.setAttribute("countries", vec.get(0));
      } else if (flag.equals("modify")) {
        tag = "modify";
        load(workAddressActionForm, httpServletRequest);
        Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), "0", 
            httpServletRequest.getSession(true)
            .getAttribute("domainId").toString());
        httpServletRequest.setAttribute("countries", vec.get(0));
      } else if (flag.equals("view")) {
        tag = "view";
        load(workAddressActionForm, httpServletRequest);
      } else if (flag.equals("update")) {
        tag = "modify";
        if (update(workAddressActionForm, httpServletRequest)) {
          httpServletRequest.setAttribute("close", "1");
        } else {
          httpServletRequest.setAttribute("close", "2");
        } 
        Vector vec = bd.see((new StringBuilder(String.valueOf(userId))).toString(), "0", 
            httpServletRequest.getSession(true)
            .getAttribute("domainId").toString());
        httpServletRequest.setAttribute("countries", vec.get(0));
      } else if (flag.equals("delete")) {
        delete(httpServletRequest);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    String viewSql = 
      "po.id,po.workName,po.workAddress,po.workFax,po.workTelephone";
    String fromSql = 
      " com.js.oa.hr.personnelmanager.po.WorkAddressPO po ";
    String whereSql = " where po.domain=" + domainId + 
      " order by po.id desc ";
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
    httpServletRequest.setAttribute("workAddressList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
  
  private boolean save(WorkAddressActionForm workAddressActionForm, HttpServletRequest httpServletRequest) {
    boolean ret = false;
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? new Long(0L) : 
      new Long(session.getAttribute("domainId").toString());
    WorkAddressPO po = new WorkAddressPO();
    po.setWorkAddress(workAddressActionForm.getWorkAddress());
    po.setDomain(domainId);
    po.setWorkName(workAddressActionForm.getWorkName());
    po.setWorkCountry(workAddressActionForm.getLinkManCountry());
    po.setWorkState(workAddressActionForm.getLinkManState());
    po.setWorkCity(workAddressActionForm.getLinkManCounty());
    po.setWorkFax(workAddressActionForm.getWorkFax());
    po.setWorkTelephone(workAddressActionForm.getWorkTelephone());
    po.setAddrUserName(workAddressActionForm.getAddrUserName());
    String userOrgGroup = workAddressActionForm.getUserOrgGroup();
    po.setAddrUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
    po.setAddrUserOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
    po.setAddrUserGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
    ret = (new WorkAddressBD()).save(po);
    return ret;
  }
  
  private void load(WorkAddressActionForm workAddressActionForm, HttpServletRequest httpServletRequest) {
    Long id = new Long(httpServletRequest.getParameter("workAddressId"));
    WorkAddressPO po = (new WorkAddressBD()).load(id);
    workAddressActionForm.setId(po.getId());
    workAddressActionForm.setWorkName(po.getWorkName());
    workAddressActionForm.setWorkAddress(po.getWorkAddress());
    workAddressActionForm.setLinkManCountry(po.getWorkCountry());
    workAddressActionForm.setLinkManState(po.getWorkState());
    workAddressActionForm.setLinkManCounty(po.getWorkCity());
    workAddressActionForm.setWorkFax(po.getWorkFax());
    workAddressActionForm.setWorkTelephone(po.getWorkTelephone());
    workAddressActionForm.setAddrUserName(po.getAddrUserName());
    String userOrgGroup = "";
    if (po.getAddrUserId() != null)
      userOrgGroup = String.valueOf(userOrgGroup) + po.getAddrUserId(); 
    if (po.getAddrUserOrg() != null)
      userOrgGroup = String.valueOf(userOrgGroup) + po.getAddrUserOrg(); 
    if (po.getAddrUserGroup() != null)
      userOrgGroup = String.valueOf(userOrgGroup) + po.getAddrUserGroup(); 
    workAddressActionForm.setUserOrgGroup(userOrgGroup);
  }
  
  private boolean update(WorkAddressActionForm workAddressActionForm, HttpServletRequest httpServletRequest) {
    boolean ret = false;
    HttpSession session = httpServletRequest.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? new Long(0L) : 
      new Long(session.getAttribute("domainId").toString());
    WorkAddressPO po = new WorkAddressPO();
    po.setWorkAddress(workAddressActionForm.getWorkAddress());
    po.setDomain(domainId);
    po.setWorkName(workAddressActionForm.getWorkName());
    po.setWorkCountry(workAddressActionForm.getLinkManCountry());
    po.setWorkState(workAddressActionForm.getLinkManState());
    po.setWorkCity(workAddressActionForm.getLinkManCounty());
    po.setWorkFax(workAddressActionForm.getWorkFax());
    po.setWorkTelephone(workAddressActionForm.getWorkTelephone());
    po.setAddrUserName(workAddressActionForm.getAddrUserName());
    String userOrgGroup = workAddressActionForm.getUserOrgGroup();
    po.setAddrUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
    po.setAddrUserOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
    po.setAddrUserGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
    ret = (new WorkAddressBD()).update(po, workAddressActionForm.getId());
    return ret;
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", 
        new Boolean((new WorkAddressBD()).delete(
            httpServletRequest.getParameter("workAddressId"))));
    view(httpServletRequest);
  }
}
