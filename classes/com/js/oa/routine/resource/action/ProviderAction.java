package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.po.ProviderPO;
import com.js.oa.routine.resource.service.ProviderBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProviderAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ProviderActionForm providerActionForm = (ProviderActionForm)actionForm;
    String flag = httpServletRequest.getParameter("flag");
    String tag = "view";
    if ("view".equals(flag)) {
      tag = "view";
      view(httpServletRequest);
    } else if ("add".equals(flag)) {
      tag = "add";
    } else if ("continue".equals(flag) || "close".equals(flag)) {
      tag = "add";
      if (save(providerActionForm)) {
        if ("continue".equals(flag)) {
          httpServletRequest.setAttribute("close", "0");
        } else {
          httpServletRequest.setAttribute("close", "1");
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
      } 
    } else if ("modify".equals(flag)) {
      tag = "modify";
      String providerId = httpServletRequest.getParameter("providerId");
      if (providerId != null)
        getModifyProvider(providerId, providerActionForm); 
    } else if ("update".equals(flag)) {
      tag = "modify";
      String oldProviderId = httpServletRequest.getParameter(
          "oldProviderId");
      if (oldProviderId != null)
        if (update(oldProviderId, providerActionForm)) {
          httpServletRequest.setAttribute("close", "1");
        } else {
          httpServletRequest.setAttribute("close", "2");
        }  
    } else if ("delete".equals(flag)) {
      tag = "view";
      String providerIdString = httpServletRequest.getParameter(
          "providerId");
      if (providerIdString != null) {
        ProviderBD providerBD = new ProviderBD();
        httpServletRequest.setAttribute("deleteSuccess", 
            providerBD
            .delete(providerIdString));
      } 
      view(httpServletRequest);
    } else if ("search".equals(flag)) {
      tag = "view";
      search(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    String viewSql = 
      "aaa.id,aaa.name,aaa.contactMethod,aaa.contacter,aaa.accounts,aaa.makeOutInvoiceAddress,aaa.consignmentAddress";
    String fromSql = "com.js.oa.routine.resource.po.ProviderPO aaa";
    String whereSql = "order by aaa.id desc";
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
    if (list == null) {
      System.out.println("List is null!!!!!");
    } else if (list.size() == 0 && offset >= 15) {
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
    httpServletRequest.setAttribute("providerList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
  
  private boolean save(ProviderActionForm providerActionForm) {
    ProviderPO providerPO = new ProviderPO();
    providerPO.setAccounts(providerActionForm.getAccounts());
    providerPO.setConsignmentAddress(providerActionForm
        .getConsignmentAddress());
    providerPO.setContacter(providerActionForm.getContacter());
    providerPO.setContactMethod(providerActionForm.getContactMethod());
    providerPO.setId(providerActionForm.getId());
    providerPO.setMakeOutInvoiceAddress(providerActionForm
        .getMakeOutInvoiceAddress());
    providerPO.setName(providerActionForm.getName());
    ProviderBD providerBD = new ProviderBD();
    return providerBD.save(providerPO);
  }
  
  private boolean getModifyProvider(String providerId, ProviderActionForm providerActionForm) {
    ProviderBD providerBD = new ProviderBD();
    ProviderPO providerPO = providerBD.getModifyProvider(providerId);
    if (providerPO == null) {
      System.out.println("load providerPO is fail.");
      return false;
    } 
    providerActionForm.setAccounts(providerPO.getAccounts());
    providerActionForm.setConsignmentAddress(providerPO
        .getConsignmentAddress());
    providerActionForm.setContacter(providerPO.getContacter());
    providerActionForm.setContactMethod(providerPO
        .getContactMethod());
    providerActionForm.setId(providerPO.getId());
    providerActionForm.setMakeOutInvoiceAddress(providerPO
        .getMakeOutInvoiceAddress());
    providerActionForm.setName(providerPO.getName());
    return true;
  }
  
  private boolean update(String oldProviderId, ProviderActionForm providerActionForm) {
    ProviderBD providerBD = new ProviderBD();
    return providerBD.update(providerActionForm, oldProviderId);
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    String whereSql, providerName = httpServletRequest.getParameter(
        "searchProviderName");
    String viewSql = 
      "aaa.id,aaa.name,aaa.contactMethod,aaa.contacter,aaa.accounts,aaa.makeOutInvoiceAddress,aaa.consignmentAddress";
    String fromSql = "com.js.oa.routine.resource.po.ProviderPO aaa";
    if (providerName != null) {
      whereSql = "where aaa.name ='" + providerName + "'";
    } else {
      whereSql = "";
    } 
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
}
