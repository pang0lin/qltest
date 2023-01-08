package com.js.oa.crm.action;

import com.js.oa.crm.po.CstSell;
import com.js.oa.crm.po.CstType;
import com.js.oa.crm.po.CstVisit;
import com.js.oa.crm.po.VisitType;
import com.js.oa.crm.service.CstService;
import com.js.oa.crm.util.XMLResult;
import com.js.util.page.Page;
import com.js.util.util.DateHelper;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CstVisitAction extends DispatchAction {
  public ActionForward cstVisitProAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cs = new CstService();
    String id = request.getParameter("id");
    if (id != null && !id.equals("") && !id.equals("null"))
      request.setAttribute("cstSell", cs.getSingleInfoById(CstSell.class, id)); 
    return mapping.findForward("cstVisitProAdd");
  }
  
  public ActionForward getCstSellInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cs = new CstService();
    String id = request.getParameter("id");
    CstSell cstSell = (CstSell)cs.getSingleInfoById(CstSell.class, id);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    if (cstSell != null) {
      outXML = XMLResult.getResultXML(cstSell, "1", "成功读取数据！");
    } else {
      outXML = XMLResult.getResultXML(cstSell, "0", "读取失败：数据不存在！");
    } 
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward cstVisitAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    String min = request.getParameter("min");
    String hour = request.getParameter("hour");
    CstVisitForm cstVisitForm = (CstVisitForm)form;
    CstVisit cstVisit = new CstVisit();
    BeanUtils.copyProperties(cstVisit, cstVisitForm);
    cstVisit.setCreateUserId(userId);
    cstVisit.setCreateTime(DateHelper.date2String(new Date(), null));
    String time = cstVisit.getVisitTime();
    String year = time.substring(0, time.indexOf("/"));
    String month = time.substring(time.indexOf("/") + 1, time.lastIndexOf("/"));
    String day = time.substring(time.lastIndexOf("/") + 1, time.length());
    if (month.length() == 1)
      month = "0" + month; 
    if (day.length() == 1)
      day = "0" + day; 
    cstVisit.setVisitTime(String.valueOf(year) + "-" + month + "-" + day + " " + hour + ":" + min);
    CstService cstService = new CstService();
    cstService.create(cstVisit);
    return null;
  }
  
  public ActionForward cstRemove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    cstService.remove("cst_v", id);
    return getCstVisitList(mapping, form, request, response);
  }
  
  public ActionForward cstVisitProMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    CstVisit cstVisit = (CstVisit)cstService.cstProMod(CstVisit.class, id);
    CstVisitForm cstVisitForm = (CstVisitForm)form;
    BeanUtils.copyProperties(cstVisitForm, cstVisit);
    request.setAttribute("cstSellVisit", cstVisit);
    request.setAttribute("cstSell", cstService.getSingleInfoById(CstSell.class, cstVisit.getVisitDesc()));
    request.setAttribute("cstVisitTime", cstVisit.getVisitTime());
    return mapping.findForward("cstVisitProMod");
  }
  
  public ActionForward cstVisitMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String min = request.getParameter("min");
    String hour = request.getParameter("hour");
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    CstService cstService = new CstService();
    CstVisit cstVisit = new CstVisit();
    CstVisitForm cstVisitForm = (CstVisitForm)form;
    BeanUtils.copyProperties(cstVisit, cstVisitForm);
    String time = cstVisit.getVisitTime();
    String year = time.substring(0, time.indexOf("/"));
    String month = time.substring(time.indexOf("/") + 1, time.lastIndexOf("/"));
    String day = time.substring(time.lastIndexOf("/") + 1, time.length());
    if (month.length() == 1)
      month = "0" + month; 
    if (day.length() == 1)
      day = "0" + day; 
    cstVisit.setCreateUserId(userId);
    cstVisit.setCreateTime(DateHelper.date2String(new Date(), null));
    cstVisit.setId(cstVisitForm.getId());
    cstVisit.setVisitTime(String.valueOf(year) + "-" + month + "-" + day + " " + hour + ":" + min);
    cstService.update(cstVisit);
    return null;
  }
  
  public ActionForward getCstVisitList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    list(request, " where po.createUserId='" + userId + "'");
    CstService cs = new CstService();
    List cstVTypeList = cs.getObjList(VisitType.class, null);
    request.setAttribute("cstVTypeList", cstVTypeList);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    return mapping.findForward("getCstVisitList");
  }
  
  public ActionForward sreachCstVisitList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    String cstName = request.getParameter("custmerName");
    String proName = request.getParameter("proName");
    String chargeUserId = request.getParameter("chargeUserId");
    String visitTitle = request.getParameter("visitTitle");
    String chargeUser = request.getParameter("chargeUser");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String hangYeName = request.getParameter("hangYeName");
    String start = DateHelper.string2String(startTime.replaceAll("/", "-"), "yyyy-MM-dd");
    String end = DateHelper.string2String(endTime.replaceAll("/", "-"), "yyyy-MM-dd");
    if (cstName != null && !cstName.equals(""))
      sql.append(" and po.cstName like '%" + cstName + "%'"); 
    if (proName != null && !proName.equals(""))
      sql.append(" and po.visitTypeName like'%" + proName + "%'"); 
    if (chargeUserId != null && !chargeUserId.equals(""))
      sql.append(" and po.createUserId=" + chargeUserId); 
    if (visitTitle != null && !visitTitle.equals("0"))
      sql.append(" and po.visitTitle='" + visitTitle + "'"); 
    if (hangYeName != null && !hangYeName.equals("0"))
      sql.append(" and po.hangYe='" + hangYeName + "'"); 
    if (startTime != null && !startTime.equals(""))
      sql.append(" and (po.visitTime  between '" + start + " 00:00" + "' and '" + end + " 59:59" + "' )"); 
    request.setAttribute("cstName", cstName);
    request.setAttribute("proName", proName);
    request.setAttribute("chargeUserId", chargeUserId);
    request.setAttribute("chargeUser", (chargeUserId == null || chargeUserId.equals("")) ? "" : chargeUser);
    request.setAttribute("visitTitle", visitTitle);
    request.setAttribute("startTime", start);
    request.setAttribute("endTime", end);
    String export = request.getParameter("export");
    CstService cs = new CstService();
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    if (export == null || export.equals("") || export.equals("null")) {
      list(request, sql.toString());
      return mapping.findForward("getCstVisitList");
    } 
    String all = request.getParameter("all");
    if (all != null && all.equals("all")) {
      list1(request, sql.toString());
    } else {
      list(request, sql.toString());
    } 
    request.setAttribute("xlsName", "回访列表");
    return mapping.findForward("export");
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.cstName,po.visitType,po.visitTitle,po.createUserName,po.visitTime,po.createTime,po.visitTypeName,po.createUserName,po.visitTime,po.visitContent,po.visitFeedBack,po.hangYeName";
      Page page = new Page(viewColumn, "CstVisit po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("cstVisitList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,custmerName,proName,chargeUserId,visitTitle,chargeUser,startTime,endTime,hangYeName,chargeOrg");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void list1(HttpServletRequest request, String wherePara) {
    try {
      String viewColumn = " po.id,po.cstName,po.visitType,po.visitTitle,po.createUserName,po.visitTime,po.createTime,po.visitTypeName,po.createUserName,po.visitTime,po.visitContent,po.visitFeedBack,po.hangYeName";
      String from = " CstVisit po ";
      CstService cs = new CstService();
      List list = cs.exportXSL(viewColumn, from, wherePara);
      request.setAttribute("cstVisitList", list);
      request.setAttribute("pageParameters", "method,custmerName,proName,chargeUserId,visitTitle,chargeUser,startTime,endTime,hangYeName,chargeOrg");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
