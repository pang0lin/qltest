package com.js.oa.box.action;

import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class BoxAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    request.setCharacterEncoding("gbk");
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String wherePara = "";
    String action = request.getParameter("action");
    if (action.equals("search")) {
      String netAddressName = request.getParameter("netAddressName");
      wherePara = "where po.userID=" + userId + " and po.Name like '%" + netAddressName + "%' order by po.id desc";
      list(request, wherePara);
      return mapping.findForward("success");
    } 
    if (action.equals("getAll")) {
      wherePara = "where po.userID=" + userId + "  order by po.id desc";
      list(request, wherePara);
      return mapping.findForward("success");
    } 
    if (action.equals("getshow")) {
      wherePara = "where po.userID=" + userId + "  order by po.id desc";
      list(request, wherePara);
      return mapping.findForward("show");
    } 
    return mapping.findForward("success");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      Page page = new Page("po.id,po.netStr,po.userID,po.saveImg,po.synopsis,po.Name", 
          "com.js.oa.box.po.BoxPO po ", 
          wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
