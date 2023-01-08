package com.js.oa.crm.action;

import com.js.oa.crm.po.NumType;
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

public class NumTypeAction extends DispatchAction {
  public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    CstService cstService = new CstService();
    String name = request.getParameter("name");
    String id = request.getParameter("id");
    String initValue = request.getParameter("value");
    String initLong = request.getParameter("long");
    String numDesc = request.getParameter("desc");
    String flag = request.getParameter("flag");
    int num = JDBCManager.checkName("cst_type_n", id, name, flag);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    if (num == 0) {
      NumType numType = new NumType();
      numType.setName(name);
      numType.setCreateUserId(userId);
      numType.setCreateUserName(userName);
      numType.setType(flag);
      numType.setInitLong(Integer.parseInt(initLong));
      numType.setInitValue(Integer.parseInt(initValue));
      numType.setNumDesc(numDesc);
      numType.setCreateTime(DateHelper.date2String(new Date(), null));
      if (id.equals("") || id.equals("null"))
        cstService.create(numType); 
      if (!id.equals("") && !id.equals("null")) {
        numType.setId(Long.parseLong(id));
        cstService.update(numType);
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
    cstService.remove("cst_type_n", id);
    return getNumTypeList(mapping, form, request, response);
  }
  
  public ActionForward proMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    NumType numType = (NumType)cstService.cstProMod(NumType.class, id);
    request.setAttribute("numType", numType);
    return mapping.findForward("proMod");
  }
  
  public ActionForward getNumTypeList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String flag = request.getParameter("flag");
    list(request, " where po.type=" + flag);
    if (flag != null && !flag.equals("null") && flag.equals("0"))
      return mapping.findForward("getNumTypeList"); 
    request.setAttribute("flag", "1");
    return mapping.findForward("getReportNumTypeList");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.name,po.initValue,po.initLong,po.createUserId,po.createUserName,po.createTime ";
      Page page = new Page(viewColumn, "com.js.oa.crm.po.NumType po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("numTypeList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,flag");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
