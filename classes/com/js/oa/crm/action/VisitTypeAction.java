package com.js.oa.crm.action;

import com.js.oa.crm.po.VisitType;
import com.js.oa.crm.service.CstService;
import com.js.oa.crm.util.JDBCManager;
import com.js.oa.crm.util.XMLResult;
import com.js.util.page.Page;
import com.js.util.util.DateHelper;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class VisitTypeAction extends DispatchAction {
  public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    CstService cstService = new CstService();
    String name = request.getParameter("name");
    String id = request.getParameter("id");
    String range = request.getParameter("range");
    String rangeId = request.getParameter("rangeId");
    String ranges = request.getParameter("ranges");
    String rangeIds = request.getParameter("rangeIds");
    int num = JDBCManager.checkName("cst_type_v", id, name, null);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    if (num == 0) {
      VisitType visitType = new VisitType();
      visitType.setName(name);
      visitType.setCreateUserId(userId);
      visitType.setCreateUserName(userName);
      visitType.setCreateTime(DateHelper.date2String(new Date(), null));
      if (id.equals("") || id.equals("null"))
        cstService.create(visitType); 
      if (!id.equals("") && !id.equals("null")) {
        visitType.setId(Long.parseLong(id));
        visitType.setUserRange(rangeId);
        visitType.setUserRangeName(range);
        visitType.setOrgRange(rangeIds);
        visitType.setOrgRangeName(ranges);
        cstService.update(visitType);
      } 
      outXML = XMLResult.getResultXML("0", "保存成功！");
    } else {
      outXML = XMLResult.getResultXML(String.valueOf(num), "保存失败，类别名称重复！");
    } 
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    cstService.remove("cst_type_v", id);
    return getVisitTypeList(mapping, form, request, response);
  }
  
  public ActionForward proMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    VisitType visitType = (VisitType)cstService.cstProMod(VisitType.class, id);
    request.setAttribute("visitType", visitType);
    return mapping.findForward("proMod");
  }
  
  public ActionForward getVisitTypeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    list(request, "");
    return mapping.findForward("getVisitTypeList");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.name,po.createUserId,po.createUserName,po.createTime,po.userRangeName,po.orgRangeName,po.userRange ";
      wherePara = String.valueOf(wherePara) + " order by po.id";
      Page page = new Page(viewColumn, "com.js.oa.crm.po.VisitType po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("cstTypeList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
