package com.js.oa.crm.action;

import com.js.oa.crm.po.CstType;
import com.js.oa.crm.po.ProType;
import com.js.oa.crm.service.CstService;
import com.js.oa.crm.util.JDBCManager;
import com.js.util.page.Page;
import com.js.util.util.DateHelper;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class TaiZhaAction extends DispatchAction {
  private String SQLString(CstSellForm cstForm, String type, HttpServletRequest request) {
    StringBuffer sqlBuffer = new StringBuffer("");
    String cstName = request.getParameter("cstName");
    String proName = request.getParameter("proName");
    String chargeOrgId = request.getParameter("chargeOrgId");
    String chargeUserId = request.getParameter("chargeUserId");
    String startTime = request.getParameter("startTime");
    String endTime = request.getParameter("endTime");
    String cstState = request.getParameter("cstState");
    String productId = request.getParameter("productId");
    String hangYeType = request.getParameter("hangYeType");
    if (cstName != null && !cstName.equals(""))
      sqlBuffer.append(" and po.cstName like '%" + cstForm.getCstName() + "%' "); 
    if (proName != null && !proName.equals(""))
      sqlBuffer.append(" and po.proName like '%" + cstForm.getProName() + "%' "); 
    if (hangYeType != null && !hangYeType.equals("0"))
      sqlBuffer.append(" and po.hangYe='" + hangYeType + "'"); 
    if (chargeOrgId != null && !chargeOrgId.equals(""))
      sqlBuffer.append(" and po.chargeOrgId=" + cstForm.getChargeOrgId()); 
    if (chargeUserId != null && !chargeUserId.equals(""))
      sqlBuffer.append(" and po.chargeUserId=" + cstForm.getChargeUserId()); 
    if (cstState != null && !cstState.equals("0"))
      sqlBuffer.append(" and co.CType='" + cstState + "'"); 
    if (productId != null && !productId.equals("0"))
      sqlBuffer.append(" and po.productId='" + productId + "'"); 
    if (startTime != null && !startTime.equals("")) {
      String start = DateHelper.string2String(startTime.replaceAll("/", "-"), "yyyy-MM-dd");
      String end = DateHelper.string2String(endTime.replaceAll("/", "-"), "yyyy-MM-dd");
      sqlBuffer.append(" and (po.createTime between '" + start + " 00:00" + "' and '" + end + " 59:59" + "') ");
    } 
    return sqlBuffer.toString();
  }
  
  public ActionForward getHuiQList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cs = new CstService();
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    StringBuffer sql = new StringBuffer(" where po.cstId=co.id and (po.sendTime is null or po.sendTime='') ");
    sql.append(" and (po.zhiyeAgree=1 or po.zhiyeAgree is null or po.zhiyeAgree='') ");
    String orgId = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "台帐管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and po.chargeOrgId in(" + range + "," + orgId + ") ");
      } else {
        sql.append(" and po.chargeOrgId=" + orgId);
      }  
    List cstCTypeList = cs.getObjList(CstType.class, "0");
    List cstPTypeList = cs.getObjList(ProType.class, null);
    request.setAttribute("cstPTypeList", cstPTypeList);
    request.setAttribute("cstCTypeList", cstCTypeList);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    CstSellForm cstForm = (CstSellForm)form;
    sql.append(SQLString(cstForm, null, request));
    ownList(request, sql.toString());
    String export = request.getParameter("export");
    if (cstForm.getStartTime() != null && !cstForm.getStartTime().equals("null") && !cstForm.getStartTime().equals("")) {
      String start = DateHelper.string2String(cstForm.getStartTime().replaceAll("/", "-"), "yyyy-MM-dd");
      String end = DateHelper.string2String(cstForm.getEndTime().replaceAll("/", "-"), "yyyy-MM-dd");
      request.setAttribute("start", start);
      request.setAttribute("end", end);
    } 
    if (export == null || export.equals("null") || export.equals(""))
      return mapping.findForward("getHuiQList"); 
    return mapping.findForward("export");
  }
  
  public ActionForward getChuLList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cs = new CstService();
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    StringBuffer sql = new StringBuffer(" where po.cstId=co.id and po.zhiyeAgree=0 ");
    sql.append(" and ((po.sendTime is null or po.sendTime='')  ");
    sql.append(" or (po.inteCompTime is null or po.inteCompTime='' or po.sigendTime is null or po.sigendTime=''))");
    String orgId = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "台帐管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and po.chargeOrgId in(" + range + "," + orgId + ") ");
      } else {
        sql.append(" and po.chargeOrgId=" + orgId);
      }  
    List cstCTypeList = cs.getObjList(CstType.class, "0");
    List cstPTypeList = cs.getObjList(ProType.class, null);
    request.setAttribute("cstPTypeList", cstPTypeList);
    request.setAttribute("cstCTypeList", cstCTypeList);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    CstSellForm cstForm = (CstSellForm)form;
    sql.append(SQLString(cstForm, null, request));
    ownList(request, sql.toString());
    if (cstForm.getStartTime() != null && !cstForm.getStartTime().equals("null") && !cstForm.getStartTime().equals("")) {
      String start = DateHelper.string2String(cstForm.getStartTime().replaceAll("/", "-"), "yyyy-MM-dd");
      String end = DateHelper.string2String(cstForm.getEndTime().replaceAll("/", "-"), "yyyy-MM-dd");
      request.setAttribute("start", start);
      request.setAttribute("end", end);
    } 
    String export = request.getParameter("export");
    if (export == null || export.equals("null") || export.equals(""))
      return mapping.findForward("getChuLList"); 
    return mapping.findForward("export");
  }
  
  public ActionForward getWanCList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    CstService cs = new CstService();
    HttpSession session = request.getSession(true);
    String userId = String.valueOf(session.getAttribute("userId").toString());
    StringBuffer sql = new StringBuffer(" where po.cstId=co.id and (po.sendTime is not null and po.sendTime<>'') ");
    sql.append(" and po.sigendTime is not null and  po.sigendTime<>'' ");
    sql.append(" and po.zhiyeAgree=0 ");
    String orgId = session.getAttribute("orgId").toString();
    String range = JDBCManager.getDateRange(null, "台帐管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sql.append(" and po.chargeOrgId in(" + range + "," + orgId + ") ");
      } else {
        sql.append(" and po.chargeOrgId=" + orgId);
      }  
    List cstCTypeList = cs.getObjList(CstType.class, "0");
    List cstPTypeList = cs.getObjList(ProType.class, null);
    request.setAttribute("cstPTypeList", cstPTypeList);
    request.setAttribute("cstCTypeList", cstCTypeList);
    List cstHTypeList = cs.getObjList(CstType.class, "1");
    request.setAttribute("cstHTypeList", cstHTypeList);
    CstSellForm cstForm = (CstSellForm)form;
    sql.append(SQLString(cstForm, null, request));
    ownList(request, sql.toString());
    if (cstForm.getStartTime() != null && !cstForm.getStartTime().equals("null") && !cstForm.getStartTime().equals("")) {
      String start = DateHelper.string2String(cstForm.getStartTime().replaceAll("/", "-"), "yyyy-MM-dd");
      String end = DateHelper.string2String(cstForm.getEndTime().replaceAll("/", "-"), "yyyy-MM-dd");
      request.setAttribute("start", start);
      request.setAttribute("end", end);
    } 
    String export = request.getParameter("export");
    if (export == null || export.equals("null") || export.equals(""))
      return mapping.findForward("getWanCList"); 
    return mapping.findForward("export");
  }
  
  private void ownList(HttpServletRequest request, String wherePara) {
    Object object = (request.getSession(true).getAttribute("domainId") == null) ? "0" : request.getSession(true).getAttribute("domainId");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    try {
      String viewColumn = " po.id,po.proName,po.proId,po.cstName,po.cstId,po.cstProduct,po.productId,po.chargeOrg,po.chargeOrgId,po.floowUser,po.floowUserId,po.recePrice,po.cstPrice,po.compactNum,co.CTypeName,po.requestTime,po.sigendTime,po.chargeUser,po.chargeUserId ,po.unrecePrice,po.receInvoice,po.invoiceState,po.trustTime ,po.inteCompTime,po.acceptTime,po.sendTime,po.createTime,po.hangYeName, ";
      viewColumn = String.valueOf(viewColumn) + " po.proState,po.reportNum,po.reportNumIden";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      Page page = new Page(viewColumn, "CstSell po,Cst co ", wherePara);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("hkList", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "method,cstName,proName,chargeOrgId,chargeUserId,cstState,productId,startTime,endTime,hangYeType,chargeOrg,chargeUser");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public ActionForward exportXSL(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String cname = request.getParameter("cname");
    String chageId = request.getParameter("chageId");
    String proName = request.getParameter("proName");
    String chargeOrgId = request.getParameter("chargeOrgId");
    String productId = request.getParameter("productId");
    String cstState = request.getParameter("cstState");
    String start1 = request.getParameter("start");
    String end1 = request.getParameter("end");
    String export = request.getParameter("export");
    String hangYeType = request.getParameter("hangYeType");
    StringBuffer sqlBuffer = new StringBuffer();
    if (export.equals("huiq")) {
      sqlBuffer.append(" where po.cstId=co.id and (po.sendTime is null or po.sendTime='') ");
      sqlBuffer.append(" and (po.zhiyeAgree=1 or po.zhiyeAgree is null or po.zhiyeAgree='') ");
      request.setAttribute("xlsName", "会签项目列表");
    } else if (export.equals("chul")) {
      sqlBuffer.append(" where po.cstId=co.id and po.zhiyeAgree=0 ");
      sqlBuffer.append(" and ((po.sendTime is null or po.sendTime='')  ");
      sqlBuffer.append(" or (po.inteCompTime is null or po.inteCompTime='' or po.sigendTime is null or po.sigendTime=''))");
      request.setAttribute("xlsName", "执业中项目列表");
    } else if (export.equals("wanc")) {
      sqlBuffer.append(" where po.cstId=co.id and (po.sendTime is not null and po.sendTime<>'') ");
      sqlBuffer.append(" and po.sigendTime is not null and  po.sigendTime<>'' and (po.zhiyeAgree=0 ) ");
      request.setAttribute("xlsName", "完成项目列表");
    } 
    String orgId = session.getAttribute("orgId").toString();
    String userId = String.valueOf(session.getAttribute("userId").toString());
    String range = JDBCManager.getDateRange(null, "回款管理");
    boolean lw = JDBCManager.hasUserRange(userId, orgId, "例外管理");
    if (!lw)
      if (!range.equals("")) {
        sqlBuffer.append(" and po.chargeOrgId in(" + range + "," + orgId + ") ");
      } else {
        sqlBuffer.append(" and po.chargeOrgId=" + orgId);
      }  
    String start = DateHelper.string2String(start1.replaceAll("/", "-"), "yyyy-MM-dd");
    String end = DateHelper.string2String(end1.replaceAll("/", "-"), "yyyy-MM-dd");
    if (cname != null && !cname.equals("") && !cname.equals("null"))
      sqlBuffer.append(" and po.cstName like '%" + cname + "%' "); 
    if (hangYeType != null && !hangYeType.equals("0"))
      sqlBuffer.append(" and po.hangYe='" + hangYeType + "'"); 
    if (chageId != null && !chageId.equals("") && !chageId.equals("null"))
      sqlBuffer.append(" and po.chargeUserId=" + chageId); 
    if (proName != null && !proName.equals("") && !proName.equals("null"))
      sqlBuffer.append(" and po.proName like '%" + proName + "%' "); 
    if (chargeOrgId != null && !chargeOrgId.equals("") && !chargeOrgId.equals("null"))
      sqlBuffer.append(" and po.chargeOrgId=" + chargeOrgId); 
    if (cstState != null && !cstState.equals("0"))
      sqlBuffer.append(" and co.CType='" + cstState + "'"); 
    if (productId != null && !productId.equals("0"))
      sqlBuffer.append(" and po.productId='" + productId + "'"); 
    if (start1 != null && !start1.equals("") && !start1.equals("null"))
      sqlBuffer.append(" and ( po.createTime between '" + start + " 00:00" + "' and '" + end + " 59:59" + "' ) "); 
    String all = request.getParameter("all");
    if (all != null && all.equals("all")) {
      ownList1(request, sqlBuffer.toString());
    } else {
      ownList(request, sqlBuffer.toString());
    } 
    return mapping.findForward("export");
  }
  
  private void ownList1(HttpServletRequest request, String wherePara) {
    try {
      String viewColumn = " po.id,po.proName,po.proId,po.cstName,po.cstId,po.cstProduct,po.productId,po.chargeOrg,po.chargeOrgId,po.floowUser,po.floowUserId,po.recePrice,po.cstPrice,po.compactNum,co.CTypeName,po.requestTime,po.sigendTime,po.chargeUser,po.chargeUserId ,po.unrecePrice,po.receInvoice,po.invoiceState,po.trustTime ,po.inteCompTime,po.acceptTime,po.sendTime,po.createTime,po.hangYeName, ";
      viewColumn = String.valueOf(viewColumn) + " po.proState,po.reportNum,po.reportNumIden";
      wherePara = String.valueOf(wherePara) + " order by po.createTime desc ";
      String from = " CstSell po,Cst co ";
      CstService cs = new CstService();
      List list = cs.exportXSL(viewColumn, from, wherePara);
      request.setAttribute("hkList", list);
      request.setAttribute("pageParameters", "method,cstName,proName,chargeOrgId,chargeUserId,cstState,productId,startTime,endTime,hangYeType,chargeOrg");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
