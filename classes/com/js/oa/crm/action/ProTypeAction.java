package com.js.oa.crm.action;

import com.js.oa.crm.po.ProType;
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

public class ProTypeAction extends DispatchAction {
  public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    CstService cstService = new CstService();
    String name = request.getParameter("name");
    String id = request.getParameter("id");
    String price = request.getParameter("price");
    String productDesc = request.getParameter("desc");
    int num = JDBCManager.checkName("cst_type_p", id, name, null);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    if (num == 0) {
      ProType proType = new ProType();
      proType.setName(name);
      proType.setCreateUserId(userId);
      proType.setCreateUserName(userName);
      proType.setPrice(price);
      proType.setProductDesc(productDesc);
      proType.setCreateTime(DateHelper.date2String(new Date(), null));
      if (id.equals("") || id.equals("null"))
        cstService.create(proType); 
      if (!id.equals("") && !id.equals("null")) {
        proType.setId(Long.parseLong(id));
        cstService.update(proType);
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
    cstService.remove("cst_type_p", id);
    return getProTypeList(mapping, form, request, response);
  }
  
  public ActionForward proMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    ProType proType = (ProType)cstService.cstProMod(ProType.class, id);
    request.setAttribute("proType", proType);
    return mapping.findForward("proMod");
  }
  
  public ActionForward getProTypeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    list(request, "");
    return mapping.findForward("getProTypeList");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.name,po.price,po.productDesc,po.createUserId,po.createUserName,po.createTime ";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      Page page = new Page(viewColumn, "com.js.oa.crm.po.ProType po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("proTypeList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
