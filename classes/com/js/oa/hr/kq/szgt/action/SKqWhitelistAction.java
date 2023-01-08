package com.js.oa.hr.kq.szgt.action;

import com.js.oa.hr.kq.szgt.bean.SKqWhitelistBean;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SKqWhitelistAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    HttpSession session = httpServletRequest.getSession();
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("saveclose")) {
      String listTitle = httpServletRequest.getParameter("listtitle");
      String listuser = httpServletRequest.getParameter("listuser");
      String listuserId = httpServletRequest.getParameter("listuserId");
      String corpId = session.getAttribute("corpId").toString();
      SKqWhitelistBean bean = new SKqWhitelistBean();
      bean.saveWhitelist(listTitle, listuser, listuserId, corpId);
      httpServletRequest.setAttribute("saveSuccess", "1");
      tag = "add";
    } else if (action.equals("del")) {
      SKqWhitelistBean bean = new SKqWhitelistBean();
      bean.deleteWhitelist(httpServletRequest.getParameter("id"));
      list(httpServletRequest);
      tag = "list";
    } else if (action.equals("update")) {
      String[] list = (new SKqWhitelistBean()).getWhitelist(httpServletRequest.getParameter("id"));
      httpServletRequest.setAttribute("listtitle", list[0]);
      httpServletRequest.setAttribute("listuser", list[1]);
      httpServletRequest.setAttribute("listuserId", list[2]);
      tag = "update";
    } else if (action.equals("updatesave")) {
      String listId = httpServletRequest.getParameter("listId");
      String listTitle = httpServletRequest.getParameter("listtitle");
      String listuser = httpServletRequest.getParameter("listuser");
      String listuserId = httpServletRequest.getParameter("listuserId");
      SKqWhitelistBean bean = new SKqWhitelistBean();
      bean.updateWhitelist(listTitle, listuser, listuserId, listId);
      httpServletRequest.setAttribute("saveSuccess", "1");
      tag = "add";
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) throws NumberFormatException, Exception {
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter(
            "pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String para = "list_id,listtitle,listuser,listuserId";
    String from = "skq_whitelist";
    String where = "";
    if (SystemCommon.getMultiDepart() == 1)
      where = "where corp_id=" + httpServletRequest.getSession(true).getAttribute("corpId").toString(); 
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action");
  }
}
