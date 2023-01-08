package com.js.oa.crm.action;

import com.js.oa.crm.po.Cst;
import com.js.oa.crm.po.CstLinkPerson;
import com.js.oa.crm.po.CstLinkThing;
import com.js.oa.crm.po.CstType;
import com.js.oa.crm.po.CstTypeRelat;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class CstAction extends DispatchAction {
  public ActionForward cstProAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cs = new CstService();
    List cstCTypeList = cs.getObjList(CstType.class, "0");
    request.setAttribute("cstCTypeList", cstCTypeList);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    return mapping.findForward("cstProAdd");
  }
  
  public ActionForward checkName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String name = request.getParameter("name");
    String num = JDBCManager.checkName("cst_c", "c_name='" + name.trim() + "' and create_user_id=" + userId);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    if (num == null) {
      outXML = XMLResult.getResultXML("0", "保存成功！");
    } else {
      outXML = XMLResult.getResultXML("1", "保存失败，该客户已经存在！");
    } 
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward delLinkMan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String id = request.getParameter("id");
    String action = request.getParameter("action");
    String table = "cst_link_p";
    if (action != null && action.equals("linkThing"))
      table = "cst_link_t"; 
    JDBCManager.removeByTableAndId(table, id);
    response.setContentType("text/xml;charset=GBK");
    response.setHeader("Cache-Control", "no-cache");
    PrintWriter out = response.getWriter();
    String outXML = "";
    outXML = XMLResult.getResultXML("0", "删除成功！");
    out.print(outXML);
    out.close();
    return null;
  }
  
  public ActionForward cstAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String eOrc = request.getParameter("eOrc");
    String p1 = request.getParameter("p1");
    String c1 = request.getParameter("c1");
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    String orgName = request.getSession(true).getAttribute("orgName").toString();
    String orgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    CstForm cstForm = (CstForm)form;
    Cst cst = new Cst();
    cst.setP1(p1);
    cst.setC1(c1);
    BeanUtils.copyProperties(cst, cstForm);
    cst.setCreateUserId(userId);
    cst.setCreateUser(userName);
    cst.setOrgId(orgId);
    cst.setOrgName(orgName);
    cst.setOrgIdString(orgIdString);
    cst.setCTypeName(JDBCManager.getNameByTableAndId("cst_type_c", cst.getCType()));
    cst.setCstStateName(JDBCManager.getNameByTableAndId("cst_type_c", cst.getCstState()));
    cst.setTrustTime(DateHelper.string2String(cstForm.getTrustTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setRequestTime(DateHelper.string2String(cstForm.getRequestTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setInteCompTime(DateHelper.string2String(cstForm.getInteCompTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setAcceptTime(DateHelper.string2String(cstForm.getAcceptTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setFloowUser(DateHelper.string2String(cstForm.getFloowUser().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setSendTime(DateHelper.string2String(cstForm.getSendTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setCreateTime(DateHelper.date2String(new Date(), null));
    CstService cstService = new CstService();
    CstTypeRelat cstRelat = new CstTypeRelat();
    cstService.create(cst);
    cstRelat.setCustomerId(cst.getId());
    cstRelat.setCustomerTypeId(Long.parseLong(cst.getCstState()));
    cstService.create(cstRelat);
    saveOrupdateLinkMan(request, cstService, String.valueOf(cst.getId()), null);
    return null;
  }
  
  public ActionForward cstRemove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    cstService.remove("cst_c", id);
    return getCstList(mapping, form, request, response);
  }
  
  public ActionForward cstProMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cstService = new CstService();
    String id = request.getParameter("id");
    String path = request.getParameter("path");
    Cst cst = (Cst)cstService.cstProMod(Cst.class, id);
    CstForm cstForm = (CstForm)form;
    BeanUtils.copyProperties(cstForm, cst);
    request.setAttribute("cst", cst);
    List cstCTypeList = cstService.getObjList(CstType.class, "0");
    List cstHTypeList = cstService.getObjList(CstType.class, "1");
    List linkManList = cstService.getObjList(CstLinkPerson.class, "where po.cstId=" + id, null);
    List linkThingList = cstService.getObjList(CstLinkThing.class, "where po.cstId=" + id, null);
    request.setAttribute("cstCTypeList", cstCTypeList);
    request.setAttribute("cstHTypeList", cstHTypeList);
    request.setAttribute("linkManList", linkManList);
    request.setAttribute("linkThingList", linkThingList);
    if (path == null || path.equals("") || path.equals("null"))
      return mapping.findForward("cstProMod"); 
    return mapping.findForward("detail");
  }
  
  public ActionForward cstMod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String userName = String.valueOf(session.getAttribute("userName").toString());
    String orgName = request.getSession(true).getAttribute("orgName").toString();
    String orgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String p1 = request.getParameter("p1");
    String c1 = request.getParameter("c1");
    CstForm cstForm = (CstForm)form;
    Cst cst = new Cst();
    cst.setP1(p1);
    cst.setC1(c1);
    BeanUtils.copyProperties(cst, cstForm);
    cst.setOrgId(orgId);
    cst.setOrgName(orgName);
    cst.setOrgIdString(orgIdString);
    cst.setCreateUserId(request.getParameter("createUserId"));
    cst.setCreateUser(request.getParameter("createUser"));
    cst.setCTypeName(JDBCManager.getNameByTableAndId("cst_type_c", cst.getCType()));
    cst.setCstStateName(JDBCManager.getNameByTableAndId("cst_type_c", cst.getCstState()));
    cst.setCreateTime(DateHelper.date2String(new Date(), null));
    cst.setTrustTime(DateHelper.string2String(cstForm.getTrustTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setRequestTime(DateHelper.string2String(cstForm.getRequestTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setInteCompTime(DateHelper.string2String(cstForm.getInteCompTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setAcceptTime(DateHelper.string2String(cstForm.getAcceptTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setFloowUser(DateHelper.string2String(cstForm.getFloowUser().replaceAll("/", "-"), "yyyy-MM-dd"));
    cst.setSendTime(DateHelper.string2String(cstForm.getSendTime().replaceAll("/", "-"), "yyyy-MM-dd"));
    CstService cstService = new CstService();
    cst.setId(cstForm.getId());
    cstService.update(cst);
    CstTypeRelat cstRelat = new CstTypeRelat();
    cstRelat.setCustomerId(cstForm.getId());
    cstRelat.setCustomerTypeId(Long.parseLong(cst.getCstState()));
    cstService.update(cstRelat);
    update(String.valueOf(cst.getId()), cst.getCName());
    String linkManId = request.getParameter("linkManId");
    String[] linkManIds = (String[])null;
    if (linkManId != null && !linkManId.equals("")) {
      linkManId = linkManId.substring(0, linkManId.length() - 1);
      linkManIds = linkManId.split(";");
    } 
    saveOrupdateLinkMan(request, cstService, String.valueOf(cst.getId()), linkManIds);
    return null;
  }
  
  private void update(String key, String value) {
    StringBuffer sqls = new StringBuffer();
    String sql = "update cst_s set cst_name='" + value + "' where cst_id=" + key;
    sqls.append(sql).append(";");
    JDBCManager.update(sqls.toString());
  }
  
  public ActionForward getCstList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    CstService cstService = new CstService();
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String orgId = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "客户管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and po.orgId in(" + range + "," + orgId + ") ");
      } else {
        sql.append(" and po.orgId=" + orgId);
      }  
    list(request, sql.toString());
    List cstCTypeList = cstService.getObjList(CstType.class, "0");
    List cstHTypeList = cstService.getObjList(CstType.class, "1");
    request.setAttribute("cstCTypeList", cstCTypeList);
    request.setAttribute("cstHTypeList", cstHTypeList);
    return mapping.findForward("getCstList");
  }
  
  public ActionForward sreachCstList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    CstService cstService = new CstService();
    CstForm cstForm = (CstForm)form;
    String CName = request.getParameter("CName");
    String chargeUserId = request.getParameter("chargeUserId");
    String keHuType = request.getParameter("keHuType");
    String hangYeType = request.getParameter("hangYeType");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    String start = DateHelper.string2String(startTime.replaceAll("/", "-"), "yyyy-MM-dd");
    String end = DateHelper.string2String(endTime.replaceAll("/", "-"), "yyyy-MM-dd");
    if (CName != null && !CName.equals("") && !CName.equals("null"))
      sql.append(" and po.CName like '%" + cstForm.getCName() + "%'"); 
    if (chargeUserId != null && !chargeUserId.equals("") && !chargeUserId.equals("null"))
      sql.append(" and po.chargeUserId=" + cstForm.getChargeUserId()); 
    if (keHuType != null && !keHuType.equals("0") && !keHuType.equals("null"))
      sql.append(" and po.CType='" + keHuType + "'"); 
    if (hangYeType != null && !hangYeType.equals("0") && !hangYeType.equals("null"))
      sql.append(" and po.cstState='" + hangYeType + "'"); 
    if (endTime != null)
      sql.append(" and ( po.createTime between '" + start + " 00:00" + "' and '" + end + " 59:59" + "' ) "); 
    String orgId = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "客户管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and po.orgId in(" + range + "," + orgId + ") ");
      } else {
        sql.append(" and po.orgId=" + orgId);
      }  
    list(request, sql.toString());
    List cstCTypeList = cstService.getObjList(CstType.class, "0");
    List cstHTypeList = cstService.getObjList(CstType.class, "1");
    request.setAttribute("cstCTypeList", cstCTypeList);
    request.setAttribute("cstHTypeList", cstHTypeList);
    cstForm.setChargeUserId(cstForm.getChargeUserId());
    cstForm.setChargeUser(cstForm.getChargeUser());
    request.setAttribute("start", start);
    request.setAttribute("end", end);
    return mapping.findForward("getCstList");
  }
  
  public ActionForward exportXSL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String cname = request.getParameter("cname");
    String type1 = request.getParameter("type1");
    String type2 = request.getParameter("type2");
    String start1 = request.getParameter("start");
    String end1 = request.getParameter("end");
    String chageId = request.getParameter("chageId");
    StringBuffer sql = new StringBuffer(" where 1=1 ");
    String start = DateHelper.string2String(start1.replaceAll("/", "-"), "yyyy-MM-dd");
    String end = DateHelper.string2String(end1.replaceAll("/", "-"), "yyyy-MM-dd");
    if (cname != null && !cname.equals("") && !cname.equals("null"))
      sql.append(" and po.CName like '%" + cname + "%'"); 
    if (chageId != null && !chageId.equals("") && !chageId.equals("null"))
      sql.append(" and po.chargeUserId=" + chageId); 
    if (type1 != null && !type1.equals("0") && !type1.equals("null"))
      sql.append(" and po.CType='" + type1 + "'"); 
    if (type2 != null && !type2.equals("0") && !type2.equals("null"))
      sql.append(" and po.cstState='" + type2 + "'"); 
    if (start1 != null && !start1.equals("") && !start1.equals("null"))
      sql.append(" and ( po.createTime between '" + start + " 00:00" + "' and '" + end + " 59:59" + "' ) "); 
    String orgId = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "客户管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and po.orgId in(" + range + "," + orgId + ") ");
      } else {
        sql.append(" and po.orgId=" + orgId);
      }  
    String all = request.getParameter("all");
    if (all != null && all.equals("all")) {
      list1(request, sql.toString());
    } else {
      list(request, sql.toString());
    } 
    request.setAttribute("xlsName", "客户列表");
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
      String viewColumn = " po.id,po.CName,po.CType,po.CTypeName,po.CTele,po.CEmail,po.CFax,po.CMobile, ";
      viewColumn = String.valueOf(viewColumn) + " po.CAddress,po.cstState,po.cstStateName,po.chargeUser,";
      viewColumn = String.valueOf(viewColumn) + " po.cstRequset,po.createUserId,po.createUser,po.createTime,po.p1,po.c1,po.cstDesc ";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      Page page = new Page(viewColumn, "com.js.oa.crm.po.Cst po ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("cstList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,startTime,endTime,CName,chargeUserId,keHuType,hangYeType,chargeUser");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void saveOrupdateLinkMan(HttpServletRequest request, CstService cstService, String cstId, String[] orangeId) {
    String[] names = request.getParameterValues("names");
    String[] zhiwu = request.getParameterValues("zhiwu");
    String[] tele = request.getParameterValues("tele");
    String[] email = request.getParameterValues("email");
    String[] tele1 = request.getParameterValues("tele1");
    String[] techang = request.getParameterValues("techang");
    String[] imports = request.getParameterValues("import");
    String[] time = request.getParameterValues("time");
    String[] desc = request.getParameterValues("desc");
    if (names != null && names.length > 0)
      for (int i = 0; i < names.length; i++) {
        CstLinkPerson cp = new CstLinkPerson();
        cp.setCstId(cstId);
        cp.setName(names[i]);
        cp.setZhiwu(zhiwu[i]);
        cp.setTele(tele[i]);
        cp.setEmail(email[i]);
        cp.setTele1(tele1[i]);
        cp.setTechang(techang[i]);
        cstService.create(cp);
      }  
    if (imports != null && imports.length > 0)
      for (int i = 0; i < imports.length; i++) {
        CstLinkThing cp = new CstLinkThing();
        cp.setCstId(cstId);
        cp.setDescThing(desc[i]);
        cp.setImportThing(imports[i]);
        cp.setTime(time[i]);
        cstService.create(cp);
      }  
    if (orangeId != null && orangeId.length > 0) {
      String[] names_ = request.getParameterValues("names_");
      String[] zhiwu_ = request.getParameterValues("zhiwu_");
      String[] tele_ = request.getParameterValues("tele_");
      String[] email_ = request.getParameterValues("email_");
      String[] tele1_ = request.getParameterValues("tele1_");
      String[] techang_ = request.getParameterValues("techang_");
      for (int i = 0; i < orangeId.length; i++) {
        CstLinkPerson cp = new CstLinkPerson();
        cp.setCstId(cstId);
        cp.setName(names_[i]);
        cp.setZhiwu(zhiwu_[i]);
        cp.setTele(tele_[i]);
        cp.setEmail(email_[i]);
        cp.setId(Long.parseLong(orangeId[i]));
        cp.setTele1(tele1_[i]);
        cp.setTechang(techang_[i]);
        cstService.update(cp);
      } 
    } 
    String linkThingId = request.getParameter("linkThingId");
    if (linkThingId != null && !linkThingId.equals("null") && !linkThingId.equals("")) {
      String[] imports_ = request.getParameterValues("import_");
      String[] time_ = request.getParameterValues("time_");
      String[] desc_ = request.getParameterValues("desc_");
      for (int i = 0; i < (linkThingId.split(";")).length; i++) {
        CstLinkThing cp = new CstLinkThing();
        cp.setCstId(cstId);
        cp.setDescThing(desc_[i]);
        cp.setImportThing(imports_[i]);
        cp.setTime(time_[i]);
        cp.setId(Long.parseLong(linkThingId.split(";")[i]));
        cstService.update(cp);
      } 
    } 
  }
  
  private void list1(HttpServletRequest request, String wherePara) {
    try {
      String viewColumn = " po.id,po.CName,po.CType,po.CTypeName,po.CTele,po.CEmail,po.CFax,po.CMobile, ";
      viewColumn = String.valueOf(viewColumn) + " po.CAddress,po.cstState,po.cstStateName,po.chargeUser,";
      viewColumn = String.valueOf(viewColumn) + " po.cstRequset,po.createUserId,po.createUser,po.createTime,po.p1,po.c1,po.cstDesc ";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      String poString = " com.js.oa.crm.po.Cst po ";
      CstService cs = new CstService();
      List list = cs.exportXSL(viewColumn, poString, wherePara);
      request.setAttribute("cstList", list);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
