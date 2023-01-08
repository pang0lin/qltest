package com.js.oa.crm.action;

import com.js.oa.crm.po.CstType;
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

public class CstTypeAction extends DispatchAction {
  public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    CstService cstService = new CstService();
    String name = request.getParameter("name");
    String id = request.getParameter("id");
    String type = request.getParameter("type");
    String desc = request.getParameter("desc");
    int num = JDBCManager.checkName("cst_type_c", id, name, type);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    if (num == 0) {
      CstType cstType = new CstType();
      cstType.setName(name);
      cstType.setCreateUserId(userId);
      cstType.setCreateUserName(userName);
      cstType.setType(type);
      cstType.setCustomerDesc(desc);
      cstType.setCreateTime(DateHelper.date2String(new Date(), null));
      if (id.equals("") || id.equals("null"))
        cstService.create(cstType); 
      if (!id.equals("") && !id.equals("null")) {
        cstType.setId(Long.parseLong(id));
        cstService.update(cstType);
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
    cstService.remove("cst_type_c", id);
    return getCstTypeList(mapping, form, request, response);
  }
  
  public ActionForward proMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    CstType cstType = (CstType)cstService.cstProMod(CstType.class, id);
    request.setAttribute("cstType", cstType);
    return mapping.findForward("proMod");
  }
  
  public ActionForward getCstTypeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String flag = request.getParameter("flag");
    list(request, "where po.type=" + flag);
    if (flag.equals("0"))
      return mapping.findForward("getCstCTypeList"); 
    return mapping.findForward("getCstHTypeList");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.name,po.createUserId,po.createUserName,po.createTime,po.type ";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      Page page = new Page(viewColumn, "com.js.oa.crm.po.CstType po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("cstTypeList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,flag");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
