package com.js.oa.crm.action;

import com.js.oa.crm.po.CstFile;
import com.js.oa.crm.po.CstFileHis;
import com.js.oa.crm.po.CstSell;
import com.js.oa.crm.po.CstSellHis;
import com.js.oa.crm.po.CstType;
import com.js.oa.crm.po.NumType;
import com.js.oa.crm.po.ProType;
import com.js.oa.crm.service.CstService;
import com.js.oa.crm.util.JDBCManager;
import com.js.oa.crm.util.XMLResult;
import com.js.oa.relproject.po.RelProjectPO;
import com.js.util.page.Page;
import com.js.util.util.DateHelper;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CstSellAction extends DispatchAction {
  public ActionForward cstSellProAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstSellForm cstSellForm = (CstSellForm)form;
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String isChildPro = request.getParameter("isChildPro");
    CstService cs = new CstService();
    List proTypeList = cs.getObjList(ProType.class, null);
    List proNumList = cs.getObjList(NumType.class, "0");
    List repNumList = cs.getObjList(NumType.class, "1");
    request.setAttribute("proTypeList", proTypeList);
    request.setAttribute("proNumList", proNumList);
    request.setAttribute("repNumList", repNumList);
    String maxCode = JDBCManager.getIncream("max(order_code)", "cst_s");
    if (isChildPro.equals("true")) {
      String compa = request.getParameter("compa");
      String parentId = request.getParameter("id");
      String order = request.getParameter("order");
      String lever = request.getParameter("lever");
      String cstId = request.getParameter("cstid");
      String cstName = request.getParameter("cstname");
      cstSellForm.setParentId(parentId);
      cstSellForm.setParentProNum(compa);
      cstSellForm.setCstId(cstId);
      cstSellForm.setCstName(cstName);
      cstSellForm.setOrderCode((Integer.valueOf(maxCode).intValue() + 1));
      cstSellForm.setOrderString(String.valueOf(order) + "_" + cstSellForm.getOrderCode());
      cstSellForm.setLever(String.valueOf(Integer.valueOf(lever).intValue() + 1));
    } else {
      maxCode = (maxCode == null) ? "0" : maxCode;
      cstSellForm.setParentId("0");
      cstSellForm.setParentProNum("");
      cstSellForm.setOrderCode((Integer.valueOf(maxCode).intValue() + 1));
      cstSellForm.setOrderString("_" + (Integer.valueOf(maxCode).intValue() + 1));
      cstSellForm.setLever("0");
    } 
    return mapping.findForward("cstSellProAdd");
  }
  
  public ActionForward getSingleInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String proId = request.getParameter("proId");
    CstService cs = new CstService();
    RelProjectPO rep = cs.getSingleProjectObj(proId);
    Map map = cs.getProjectPepole(proId);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = XMLResult.getResultXML(rep, map);
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward getNumType(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String action = request.getParameter("action");
    CstService cs = new CstService();
    String incream = "-1";
    NumType numType = (NumType)cs.getSingleInfoById(NumType.class, id);
    incream = JDBCManager.get(" max(desc_incream),type ", " cst_incream ", " isauto=" + id);
    if (incream == null || incream.split(";")[0].equals("0")) {
      incream = "-1";
    } else {
      incream = incream.split(";")[0];
    } 
    String mun = null;
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = XMLResult.getResultXML(numType, incream, mun);
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward getCheck(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String imcr = request.getParameter("imcr");
    String head = request.getParameter("head");
    String rep = request.getParameter("rep");
    String re = request.getParameter("re");
    String action = request.getParameter("action");
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = XMLResult.getCheck(head, imcr, re, rep, action);
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward cstSellAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    String orgName = request.getSession(true).getAttribute("orgName").toString();
    String orgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String zhiyeAgree = request.getParameter("zhiye");
    String sigend = request.getParameter("sigendTime_box");
    String inte = request.getParameter("inteCompTime_box");
    String send = request.getParameter("sendTime_box");
    String trust = request.getParameter("trustTime_box");
    String requess = request.getParameter("requestTime_box");
    String accept = request.getParameter("acceptTime_box");
    CstSellForm cstSellForm = (CstSellForm)form;
    CstSell cstSell = new CstSell();
    BeanUtils.copyProperties(cstSell, cstSellForm);
    cstSell.setOrgId(orgId);
    cstSell.setOrgName(orgName);
    cstSell.setOrgIdString(orgIdString);
    cstSell.setZhiyeAgree(zhiyeAgree);
    cstSell.setCreateUserId(userId);
    cstSell.setCreateUser(userName);
    cstSell.setInvoiceState("0");
    cstSell.setCreateTime(DateHelper.date2String(new Date(), null));
    cstSell.setCstProduct(JDBCManager.getNameByTableAndId("cst_type_p", cstSell.getProductId()));
    cstSell.setProNumHead(JDBCManager.getNameByTableAndId("cst_type_n", cstSell.getProNumId()));
    cstSell.setReportNum(JDBCManager.getNameByTableAndId("cst_type_n", cstSell.getReportId()));
    if (trust != null && !trust.equals("null") && !trust.equals("")) {
      cstSell.setTrustTime(DateHelper.string2String(cstSellForm.getTrustTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setTrustTime("");
    } 
    if (requess != null && !requess.equals("null") && !requess.equals("")) {
      cstSell.setRequestTime(DateHelper.string2String(cstSellForm.getRequestTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setRequestTime("");
    } 
    if (accept != null && !accept.equals("null") && !accept.equals("")) {
      cstSell.setAcceptTime(DateHelper.string2String(cstSellForm.getAcceptTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setAcceptTime("");
    } 
    if (inte != null && !inte.equals("null") && !inte.equals("")) {
      cstSell.setInteCompTime(DateHelper.string2String(cstSellForm.getInteCompTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setInteCompTime("");
    } 
    if (send != null && !send.equals("null") && !send.equals("")) {
      cstSell.setSendTime(DateHelper.string2String(cstSellForm.getSendTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setSendTime("");
    } 
    if (sigend != null && !sigend.equals("null") && !sigend.equals("")) {
      cstSell.setSigendTime(DateHelper.string2String(cstSellForm.getSigendTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setSigendTime("");
    } 
    String idNaneStr = JDBCManager.getCstSell(cstSell.getCstId());
    cstSell.setHangYe(idNaneStr.split(";")[0]);
    cstSell.setHangYeName(idNaneStr.split(";")[1]);
    CstService cstService = new CstService();
    cstService.create(cstSell);
    if (cstSell.getProState().equals("2") && !cstSell.getReportNumIden().equals(""))
      JDBCManager.executeSQL(" insert into cst_incream set desc_incream='' "); 
    String proNumIncream = request.getParameter("proNumIncreamTemp");
    String reportNumIdenTemp = request.getParameter("reportNumIdenTemp");
    String isNum = request.getParameter("isNumId");
    String isReportNum = request.getParameter("isReportNum");
    if (proNumIncream.equals(cstSell.getProNumIncream()))
      JDBCManager.executeSQL(" insert into cst_incream set isauto='" + cstSell.getProNumId() + "',type='0',desc_incream=" + isNum); 
    if (reportNumIdenTemp.equals(cstSell.getReportNumIden()))
      JDBCManager.executeSQL(" insert into cst_incream set isauto='" + cstSell.getReportId() + "',type='0',desc_incream=" + isReportNum); 
    saveCstFile(request, cstService, String.valueOf(cstSell.getId()));
    return null;
  }
  
  public ActionForward cstRemove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    cstService.remove("cst_s", id);
    return getCstSellList(mapping, form, request, response);
  }
  
  public ActionForward cstSellHisRemove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    cstService.remove("cst_s_h", id);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    out.print("");
    out.close();
    return null;
  }
  
  public ActionForward delFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    cstService.remove("cst_file", id);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    out.print("");
    out.close();
    return null;
  }
  
  public ActionForward cstSellProMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    String path = request.getParameter("path");
    CstSell cstSell = (CstSell)cstService.cstProMod(CstSell.class, id);
    CstSellForm cstSellForm = (CstSellForm)form;
    BeanUtils.copyProperties(cstSellForm, cstSell);
    List proTypeList = cstService.getObjList(ProType.class, null);
    List proNumList = cstService.getObjList(NumType.class, "0");
    List repNumList = cstService.getObjList(NumType.class, "1");
    request.setAttribute("proTypeList", proTypeList);
    request.setAttribute("proNumList", proNumList);
    request.setAttribute("repNumList", repNumList);
    request.setAttribute("cstSell", cstSell);
    getCstFile(request, cstService, id);
    if (path == null || path.equals("") || path.equals("null"))
      return mapping.findForward("cstSellProMod"); 
    return mapping.findForward("detail");
  }
  
  public ActionForward showHisInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    String path = request.getParameter("path");
    CstSellHis cstSellHis = (CstSellHis)cstService.cstProMod(CstSellHis.class, id);
    CstSellForm cstSellForm = (CstSellForm)form;
    BeanUtils.copyProperties(cstSellForm, cstSellHis);
    List proTypeList = cstService.getObjList(ProType.class, null);
    List proNumList = cstService.getObjList(NumType.class, "0");
    List repNumList = cstService.getObjList(NumType.class, "1");
    request.setAttribute("proTypeList", proTypeList);
    request.setAttribute("proNumList", proNumList);
    request.setAttribute("repNumList", repNumList);
    request.setAttribute("cstSellHis", cstSellHis);
    String where = " where po.str1=" + id;
    List list = cstService.getObjList(CstFileHis.class, where, null);
    request.setAttribute("cstFileHisList", list);
    return mapping.findForward("showHisInfo");
  }
  
  public ActionForward cstSellMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    String orgName = request.getSession(true).getAttribute("orgName").toString();
    String orgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String zhiyeAgree = request.getParameter("zhiye");
    String sigend = request.getParameter("sigendTime_box");
    String inte = request.getParameter("inteCompTime_box");
    String send = request.getParameter("sendTime_box");
    String trust = request.getParameter("trustTime_box");
    String requess = request.getParameter("requestTime_box");
    String accept = request.getParameter("acceptTime_box");
    String proStateTemp = request.getParameter("proStateTemp");
    CstSellForm cstSellForm = (CstSellForm)form;
    CstSell cstSell = new CstSell();
    CstService cstService = new CstService();
    CstSell cs = (CstSell)cstService.getSingleInfoById(CstSell.class, String.valueOf(cstSellForm.getId()));
    saveHistory(cstService, cstSellForm.getId().longValue(), userId, userName);
    BeanUtils.copyProperties(cstSell, cstSellForm);
    if (zhiyeAgree != null) {
      cstSell.setZhiyeAgree(zhiyeAgree);
    } else {
      cstSell.setZhiyeAgree(cs.getZhiyeAgree());
    } 
    cstSell.setOrgId(orgId);
    cstSell.setOrgName(orgName);
    cstSell.setOrgIdString(orgIdString);
    cstSell.setCreateUserId(request.getParameter("createUserId"));
    cstSell.setCreateUser(request.getParameter("createUser"));
    cstSell.setCreateTime(cs.getCreateTime());
    cstSell.setCstProduct(JDBCManager.getNameByTableAndId("cst_type_p", cstSell.getProductId()));
    if (cstSell.getProNumId() != null && !cstSell.getProNumId().equals("null") && !cstSell.getProNumId().equals("")) {
      cstSell.setProNumHead(JDBCManager.getNameByTableAndId("cst_type_n", cstSell.getProNumId()));
    } else {
      cstSell.setProNumHead(cs.getProNumHead());
      cstSell.setProNumId(cs.getProNumId());
      cstSell.setProNumIncream(cs.getProNumIncream());
    } 
    if (cstSell.getReportId() != null && !cstSell.getReportId().equals("null") && !cstSell.getReportId().equals("")) {
      cstSell.setReportNum(JDBCManager.getNameByTableAndId("cst_type_n", cstSell.getReportId()));
    } else {
      cstSell.setReportNum(cs.getReportNum());
      cstSell.setReportId(cs.getReportId());
      cstSell.setReportNumIden(cs.getReportNumIden());
    } 
    cstSell.setId(cstSellForm.getId());
    cstSell.setProState(proStateTemp);
    cstSell.setInvoiceState((cstSellForm.getInvoiceState() == null) ? cs.getInvoiceState() : cstSellForm.getInvoiceState());
    cstSell.setZhiyeCenter((cstSellForm.getZhiyeCenter() == null) ? cs.getZhiyeCenter() : cstSellForm.getZhiyeCenter());
    cstSell.setRecePrice(new BigDecimal(request.getParameter("recePrice")));
    cstSell.setReceInvoice(cstSellForm.getReceInvoice());
    cstSell.setHangYe(cs.getHangYe());
    cstSell.setHangYeName(cs.getHangYeName());
    if (trust != null && !trust.equals("null") && !trust.equals("")) {
      cstSell.setTrustTime(DateHelper.string2String(cstSellForm.getTrustTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setTrustTime(cs.getTrustTime());
    } 
    if (requess != null && !requess.equals("null") && !requess.equals("")) {
      cstSell.setRequestTime(DateHelper.string2String(cstSellForm.getRequestTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setRequestTime(cs.getRequestTime());
    } 
    if (accept != null && !accept.equals("null") && !accept.equals("")) {
      cstSell.setAcceptTime(DateHelper.string2String(cstSellForm.getAcceptTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setAcceptTime(cs.getAcceptTime());
    } 
    if (inte != null && !inte.equals("null") && !inte.equals("")) {
      cstSell.setInteCompTime(DateHelper.string2String(cstSellForm.getInteCompTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setInteCompTime(cs.getInteCompTime());
    } 
    if (send != null && !send.equals("null") && !send.equals("")) {
      cstSell.setSendTime(DateHelper.string2String(cstSellForm.getSendTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setSendTime(cs.getSendTime());
    } 
    if (sigend != null && !sigend.equals("null") && !sigend.equals("")) {
      cstSell.setSigendTime(DateHelper.string2String(cstSellForm.getSigendTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    } else {
      cstSell.setSigendTime(cs.getSigendTime());
    } 
    cstService.update(cstSell);
    updateCstFile(request, cstService, String.valueOf(cstSell.getId()));
    String repNumIdenTemp = request.getParameter("repNumIdenTemp");
    if (cstSell.getProState().equals("2") && !cstSell.getReportNumIden().equals("") && 
      repNumIdenTemp.equals(cstSell.getReportNumIden())) {
      String count = JDBCManager.get(" count(*),type ", " cst_incream ", " desc_incream=" + cstSell.getReportNumIden() + " and isauto=" + cstSell.getReportId());
      if (count == null || count.split(";")[0].equals("0"))
        JDBCManager.executeSQL(" insert into cst_incream set isauto='" + cstSell.getReportId() + "',type='0',desc_incream=" + cstSell.getReportNumIden()); 
    } 
    return getCstSellList(mapping, form, request, response);
  }
  
  private void saveHistory(CstService cs, long value, String id, String name) throws Exception {
    CstSell cstSell = (CstSell)cs.getSingleInfoById(CstSell.class, String.valueOf(value));
    CstSellHis cstHis = new CstSellHis();
    BeanUtils.copyProperties(cstHis, cstSell);
    cstHis.setSeId(String.valueOf(value));
    cstHis.setModUser(name);
    cstHis.setModUserId(id);
    cstHis.setModTime(DateHelper.date2String(new Date(), null));
    cs.create(cstHis);
    String where = " where po.cstSellId=" + value;
    List<CstFile> list = cs.getObjList(CstFile.class, where, null);
    if (list != null && list.size() > 0)
      for (int i = 0; i < list.size(); i++) {
        CstFile cf = list.get(i);
        CstFileHis cfh = new CstFileHis();
        cfh.setCstFileId(String.valueOf(cf.getId()));
        cfh.setCstSellId(cf.getCstSellId());
        cfh.setFileName(cf.getFileName());
        cfh.setStr1(String.valueOf(cstHis.getId()));
        cfh.setStr2(cf.getStr2());
        cs.create(cfh);
      }  
  }
  
  public ActionForward getCstSellList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String orgId = session.getAttribute("orgId").toString();
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    String range = JDBCManager.getDateRange(null, "客户管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and (po.orgId in(" + range + "," + orgId + ") or po.caiWuId=" + userId + " or po.chargeUserId=" + userId + " or po.floowUserId=" + userId + ") ");
      } else {
        sql.append(" and (po.orgId=" + orgId + " or po.caiWuId=" + userId + " or po.chargeUserId=" + userId + " or po.floowUserId=" + userId + ") ");
      }  
    list(request, sql.toString(), false);
    CstService cs = new CstService();
    List proTypeList = cs.getObjList(ProType.class, null);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("proTypeList", proTypeList);
    request.setAttribute("cstHTypeList", cstHTypeList);
    request.setAttribute("ysfrom", "");
    request.setAttribute("ysTo", "");
    request.setAttribute("ssfrom", "");
    request.setAttribute("ssTo", "");
    return mapping.findForward("getCstSellList");
  }
  
  public ActionForward getCstSellSelfList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    list(request, " where po.cstId=" + id, false);
    CstService cs = new CstService();
    List proTypeList = cs.getObjList(ProType.class, null);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    request.setAttribute("proTypeList", proTypeList);
    return mapping.findForward("getCstSellSelfList");
  }
  
  public ActionForward sreachCstSellList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    CstSellForm csForm = (CstSellForm)form;
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String orgId = session.getAttribute("orgId").toString();
    String ysfrom = request.getParameter("ysFrom");
    String ysTo = request.getParameter("ysTo");
    String ssfrom = request.getParameter("ssFrom");
    String ssTo = request.getParameter("ssTo");
    String cstName = request.getParameter("cstName");
    String proName = request.getParameter("proName");
    String chargeUserId = request.getParameter("chargeUserId");
    String chargeOrgId = request.getParameter("chargeOrgId");
    String proState = request.getParameter("proState");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String hangYeType = request.getParameter("hangYeType");
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    String start = DateHelper.string2String(startTime.replaceAll("/", "-"), "yyyy-MM-dd");
    String end = DateHelper.string2String(endTime.replaceAll("/", "-"), "yyyy-MM-dd");
    if (cstName != null && !cstName.equals(""))
      sql.append(" and po.cstName like '%" + csForm.getCstName() + "%'"); 
    if (proName != null && !proName.equals(""))
      sql.append(" and po.proName like '%" + csForm.getProName() + "%' "); 
    if (hangYeType != null && !hangYeType.equals("0"))
      sql.append(" and po.hangYe='" + hangYeType + "'"); 
    if (chargeUserId != null && !chargeUserId.equals(""))
      sql.append(" and po.chargeUserId=" + csForm.getChargeUserId()); 
    if (chargeOrgId != null && !chargeOrgId.equals(""))
      sql.append(" and po.chargeOrgId=" + csForm.getChargeOrgId()); 
    if (proState != null && !proState.equals(""))
      sql.append(" and po.proState=" + csForm.getProState()); 
    if (ysfrom != null && !ysfrom.equals("") && !ysfrom.equals("null"))
      sql.append(" and po.unrecePrice>=" + ysfrom); 
    if (ysTo != null && !ysTo.equals("") && !ysTo.equals("null"))
      sql.append(" and po.unrecePrice<=" + ysTo); 
    if (ssfrom != null && !ssfrom.equals("") && !ssfrom.equals("null"))
      sql.append(" and po.recePrice>=" + ssfrom); 
    if (ssTo != null && !ssTo.equals("") && !ssTo.equals("null"))
      sql.append(" and po.recePrice<=" + ssTo); 
    if (csForm.getStartTime() != null && !csForm.getStartTime().equals(""))
      sql.append(" and ( po.createTime between '" + start + " 00:00" + "' and '" + end + " 59:59" + "' ) "); 
    String range = JDBCManager.getDateRange(null, "客户管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and (po.orgId in(" + range + "," + orgId + ") or po.caiWuId=" + userId + " or po.chargeUserId=" + userId + " or po.floowUserId=" + userId + ") ");
      } else {
        sql.append(" and (po.orgId=" + orgId + " or po.caiWuId=" + userId + " or po.chargeUserId=" + userId + " or po.floowUserId=" + userId + ") ");
      }  
    list(request, sql.toString(), true);
    csForm.setChargeUserId(csForm.getChargeUserId());
    csForm.setChargeUser(csForm.getChargeUser());
    csForm.setChargeOrgId(csForm.getChargeOrgId());
    csForm.setChargeOrg(csForm.getChargeOrg());
    request.setAttribute("start", start);
    request.setAttribute("end", end);
    request.setAttribute("ysfrom", (ysfrom != null && !ysfrom.equals("") && !ysfrom.equals("null")) ? ysfrom : "");
    request.setAttribute("ysTo", (ysTo != null && !ysTo.equals("") && !ysTo.equals("null")) ? ysTo : "");
    request.setAttribute("ssfrom", (ssfrom != null && !ssfrom.equals("") && !ssfrom.equals("null")) ? ssfrom : "");
    request.setAttribute("ssTo", (ssTo != null && !ssTo.equals("") && !ssTo.equals("null")) ? ssTo : "");
    CstService cs = new CstService();
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    return mapping.findForward("getCstSellList");
  }
  
  private void list(HttpServletRequest request, String wherePara, boolean flag) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " distinct po.id,po.proName,po.proNumHead,po.proNumIncream, po.cstName,po.chargeOrg,po.chargeUser, po.recePrice,po.unrecePrice, po.sigendTime,po.createTime,po.requestTime, ";
      viewColumn = String.valueOf(viewColumn) + " po.compactState,po.compactNum,po.trustTime,";
      viewColumn = String.valueOf(viewColumn) + " po.inteCompTime,po.acceptTime,po.floowUser,po.floowUserId,";
      viewColumn = String.valueOf(viewColumn) + " po.sendTime,po.proRequset,po.productId,po.invoiceState, ";
      viewColumn = String.valueOf(viewColumn) + " po.orderCode,po.orderString,po.lever,po.cstId,po.receInvoice,po.hangYeName,";
      viewColumn = String.valueOf(viewColumn) + " po.proState,po.reportNum,po.reportNumIden,po.createUserId";
      String poString = "CstSell po";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      Page page = new Page(viewColumn, poString, wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("cstSellList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      String params = "method,id,cstName";
      if (flag)
        params = String.valueOf(params) + ",ysFrom,ysTo,ssFrom,ssTo,startTime,endTime,proName,cstName,chargeUserId,chargeOrgId,invoiceState,hangYeType,proState,chargeOrg,chargeUser"; 
      request.setAttribute("pageParameters", params);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public ActionForward exportXSL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String cname = request.getParameter("cname");
    String pro = request.getParameter("pro");
    String chageId = request.getParameter("chageId");
    String orgId = request.getParameter("orgId");
    String start1 = request.getParameter("start");
    String end1 = request.getParameter("end");
    String ysfrom = request.getParameter("ysf");
    String ysTo = request.getParameter("yst");
    String ssfrom = request.getParameter("ssf");
    String ssTo = request.getParameter("sst");
    String state = request.getParameter("state");
    String hangYeType = request.getParameter("hangYeType");
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    String start = "";
    String end = "";
    if (start1 != null && !start1.equals("")) {
      start = DateHelper.string2String(start1.replaceAll("/", "-"), "yyyy-MM-dd");
      end = DateHelper.string2String(end1.replaceAll("/", "-"), "yyyy-MM-dd");
    } 
    if (pro != null && !pro.equals("") && !pro.equals("null"))
      sql.append(" and po.proName like '%" + pro + "%'"); 
    if (hangYeType != null && !hangYeType.equals("0"))
      sql.append(" and po.hangYe='" + hangYeType + "'"); 
    if (cname != null && !cname.equals("") && !cname.equals("null"))
      sql.append(" and po.cstName like '%" + cname + "%'"); 
    if (chageId != null && !chageId.equals("") && !chageId.equals("null"))
      sql.append(" and po.chargeUserId=" + chageId); 
    if (orgId != null && !orgId.equals("") && !orgId.equals("null"))
      sql.append(" and po.chargeOrgId=" + orgId); 
    if (ysfrom != null && !ysfrom.equals("") && !ysfrom.equals("null"))
      sql.append(" and po.unrecePrice>=" + ysfrom); 
    if (ysTo != null && !ysTo.equals("") && !ysTo.equals("null"))
      sql.append(" and po.unrecePrice<=" + ysTo); 
    if (ssfrom != null && !ssfrom.equals("") && !ssfrom.equals("null"))
      sql.append(" and po.recePrice>=" + ssfrom); 
    if (ssTo != null && !ssTo.equals("") && !ssTo.equals("null"))
      sql.append(" and po.recePrice<=" + ssTo); 
    if (state != null && !state.equals("") && !state.equals("null"))
      sql.append(" and po.proState=" + state); 
    if (start1 != null && !start1.equals(""))
      sql.append(" and ( po.createTime between '" + start + " 00:00" + "' and '" + end + " 59:59" + "' ) "); 
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String orgIds = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "客户管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and (po.orgId in(" + range + "," + orgIds + ") or po.caiWuId=" + userId + " or po.chargeUserId=" + userId + " or po.floowUserId=" + userId + ") ");
      } else {
        sql.append(" and (po.orgId=" + orgIds + " or po.caiWuId=" + userId + " or po.chargeUserId=" + userId + " or po.floowUserId=" + userId + ") ");
      }  
    String all = request.getParameter("all");
    if (all != null && all.equals("all")) {
      list(request, sql.toString());
    } else {
      list(request, sql.toString(), false);
    } 
    request.setAttribute("xlsName", "项目列表");
    return mapping.findForward("export");
  }
  
  public ActionForward getCstSellHisList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String action = request.getParameter("action");
    StringBuffer sql = new StringBuffer(" where po.seId=" + id);
    if (action != null && !action.equals("null") && !action.equals("") && action.equals("search")) {
      String modUser = request.getParameter("modUser");
      String startTime = request.getParameter("startTime");
      String endTime = request.getParameter("endTime");
      startTime = DateHelper.string2String(startTime.replaceAll("/", "-"), "yyyy-MM-dd");
      endTime = DateHelper.string2String(endTime.replaceAll("/", "-"), "yyyy-MM-dd");
      if (modUser != null && !modUser.equals(""))
        sql.append(" and po.modUser like '%" + modUser + "%'"); 
      sql.append(" and (po.modTime between '" + startTime + " 00:00' and '" + endTime + " 59:59') ");
    } 
    hisList(request, sql.toString(), false);
    return mapping.findForward("getCstSellHisList");
  }
  
  private void hisList(HttpServletRequest request, String wherePara, boolean flag) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.seId,po.proName,po.modUser,po.modTime,po.chargeUser,po.chargeOrg ";
      String poString = " CstSellHis po ";
      wherePara = String.valueOf(wherePara) + " order by po.modTime desc ";
      Page page = new Page(viewColumn, poString, wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("cstSellHisList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,id,proName");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void list(HttpServletRequest request, String wherePara) {
    try {
      String viewColumn = " distinct po.id,po.proName,po.proNumHead,po.proNumIncream, po.cstName,po.chargeOrg,po.chargeUser, po.recePrice,po.unrecePrice, po.sigendTime,po.createTime,po.requestTime, ";
      viewColumn = String.valueOf(viewColumn) + " po.compactState,po.compactNum,po.trustTime,";
      viewColumn = String.valueOf(viewColumn) + " po.inteCompTime,po.acceptTime,po.floowUser,po.floowUserId,";
      viewColumn = String.valueOf(viewColumn) + " po.sendTime,po.proRequset,po.productId,po.invoiceState, ";
      viewColumn = String.valueOf(viewColumn) + " po.orderCode,po.orderString,po.lever,po.cstId,po.receInvoice,po.hangYeName, ";
      viewColumn = String.valueOf(viewColumn) + " po.proState,po.reportNum,po.reportNumIden,po.createUserId";
      String poString = " CstSell po ";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      CstService cs = new CstService();
      List list = cs.exportXSL(viewColumn, poString, wherePara);
      request.setAttribute("cstSellList", list);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void saveCstFile(HttpServletRequest request, CstService cstService, String cstSellId) {
    try {
      String[] fileName = request.getParameterValues("filename");
      List cstFileList = null;
      if (fileName != null && fileName.length > 0) {
        cstFileList = new ArrayList();
        for (int i = 0; i < fileName.length; i++) {
          CstFile cf = new CstFile();
          cf.setCstSellId(cstSellId);
          cf.setFileName(fileName[i]);
          cstService.create(cf);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void updateCstFile(HttpServletRequest request, CstService cstService, String cstSellId) {
    try {
      String[] fileName = request.getParameterValues("filename");
      String[] fileName_ = request.getParameterValues("filename_");
      String fileId = request.getParameter("fileId");
      if (fileName != null && fileName.length > 0)
        for (int i = 0; i < fileName.length; i++) {
          CstFile cf = new CstFile();
          cf.setCstSellId(cstSellId);
          cf.setFileName(fileName[i]);
          cstService.create(cf);
        }  
      if (fileName_ != null && fileName_.length > 0 && fileId != null && !fileId.equals(""))
        for (int i = 0; i < fileName_.length; i++) {
          CstFile cf = new CstFile();
          cf.setId(Long.parseLong(fileId.split(";")[i]));
          cf.setCstSellId(cstSellId);
          cf.setFileName(fileName_[i]);
          cstService.update(cf);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void getCstFile(HttpServletRequest request, CstService cstService, String cstSellId) {
    try {
      String where = " where po.cstSellId=" + cstSellId;
      List list = cstService.getObjList(CstFile.class, where, null);
      request.setAttribute("fileList", list);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
