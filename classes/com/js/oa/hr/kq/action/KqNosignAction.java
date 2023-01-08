package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.po.KqNosignPO;
import com.js.oa.hr.kq.service.KqNosignBD;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class KqNosignAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NumberFormatException, Exception {
    String tag = "list";
    String action = (httpServletRequest.getParameter("action") == null) ? 
      "list" : httpServletRequest.getParameter("action");
    Long domainId = (httpServletRequest.getSession().getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession().getAttribute("domainId").toString());
    KqNosignBD kqNosignBD = new KqNosignBD();
    if (action.equals("list")) {
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("save")) {
      KqNosignPO kqNosignPO = new KqNosignPO();
      String saveType = httpServletRequest.getParameter("saveType");
      kqNosignPO.setDomainId(domainId.longValue());
      kqNosignPO.setNosignName(httpServletRequest.getParameter("nosignName"));
      kqNosignPO.setUserNames(httpServletRequest.getParameter("userName"));
      kqNosignPO.setUserIds(httpServletRequest.getParameter("userId"));
      String nosigntype = "";
      String nosigntype1 = (httpServletRequest.getParameter("nosigntype1") == null) ? "0" : httpServletRequest.getParameter("nosigntype1").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype1;
      String nosigntype2 = (httpServletRequest.getParameter("nosigntype2") == null) ? "0" : httpServletRequest.getParameter("nosigntype2").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype2;
      String nosigntype3 = (httpServletRequest.getParameter("nosigntype3") == null) ? "0" : httpServletRequest.getParameter("nosigntype3").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype3;
      String nosigntype4 = (httpServletRequest.getParameter("nosigntype4") == null) ? "0" : httpServletRequest.getParameter("nosigntype4").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype4;
      String nosigntype5 = (httpServletRequest.getParameter("nosigntype5") == null) ? "0" : httpServletRequest.getParameter("nosigntype5").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype5;
      String nosigntype6 = (httpServletRequest.getParameter("nosigntype6") == null) ? "0" : httpServletRequest.getParameter("nosigntype6").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype6;
      kqNosignPO.setNosignType(nosigntype);
      kqNosignBD.add(kqNosignPO);
      httpServletRequest.setAttribute("saveType", saveType);
      tag = "add";
    } else if (action.equals("del")) {
      String nosignid = httpServletRequest.getParameter("nosignid");
      kqNosignBD.del((new Long(nosignid)).longValue());
      list(httpServletRequest);
      tag = "list";
    } else if (action.equals("update")) {
      String nosignid = httpServletRequest.getParameter("nosignid");
      KqNosignPO kqNosignPO = new KqNosignPO();
      kqNosignPO = kqNosignBD.searchById((new Long(nosignid)).longValue());
      httpServletRequest.setAttribute("kqNosignPO", kqNosignPO);
      tag = "update";
    } else if (action.equals("updatesave")) {
      String nosignid = httpServletRequest.getParameter("nosignid");
      KqNosignPO kqNosignPO = new KqNosignPO();
      kqNosignPO = kqNosignBD.searchById((new Long(nosignid)).longValue());
      kqNosignPO.setNosignName(httpServletRequest.getParameter("nosignName"));
      kqNosignPO.setUserNames(httpServletRequest.getParameter("userName"));
      kqNosignPO.setUserIds(httpServletRequest.getParameter("userId"));
      String nosigntype = "";
      String nosigntype1 = (httpServletRequest.getParameter("nosigntype1") == null) ? "0" : httpServletRequest.getParameter("nosigntype1").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype1;
      String nosigntype2 = (httpServletRequest.getParameter("nosigntype2") == null) ? "0" : httpServletRequest.getParameter("nosigntype2").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype2;
      String nosigntype3 = (httpServletRequest.getParameter("nosigntype3") == null) ? "0" : httpServletRequest.getParameter("nosigntype3").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype3;
      String nosigntype4 = (httpServletRequest.getParameter("nosigntype4") == null) ? "0" : httpServletRequest.getParameter("nosigntype4").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype4;
      String nosigntype5 = (httpServletRequest.getParameter("nosigntype5") == null) ? "0" : httpServletRequest.getParameter("nosigntype5").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype5;
      String nosigntype6 = (httpServletRequest.getParameter("nosigntype6") == null) ? "0" : httpServletRequest.getParameter("nosigntype6").toString();
      nosigntype = String.valueOf(nosigntype) + nosigntype6;
      kqNosignPO.setNosignType(nosigntype);
      kqNosignBD.update(kqNosignPO);
      httpServletRequest.setAttribute("saveType", "saveandexit");
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
    String whereSql = " where po.domainId =" + domainId;
    Page page = new Page(
        " po.id,po.nosignName,po.userNames", 
        "com.js.oa.hr.kq.po.KqNosignPO po ", 
        whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("list", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", 
        "action");
  }
}
