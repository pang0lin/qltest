package com.js.oa.hr.examination.action;

import com.js.oa.hr.examination.po.ExaminationStockPO;
import com.js.oa.hr.examination.service.ExaminationStockBD;
import com.js.oa.hr.examination.service.ExaminationTypeBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExaminationStockAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ExaminationStockActionForm examinationStockActionForm = 
      (ExaminationStockActionForm)actionForm;
    String action = httpServletRequest.getParameter("action");
    String saveType = httpServletRequest.getParameter("saveType");
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
    Long domainId = (httpServletRequest.getSession(true).getAttribute(
        "domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(httpServletRequest.getSession(true)
        .getAttribute("domainId").toString());
    String tag = "list";
    ExaminationStockBD bd = new ExaminationStockBD();
    if (action.equals("list")) {
      tag = "list";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
    } else if (action.equals("addStock")) {
      tag = "add";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      ExaminationStockPO po = new ExaminationStockPO();
      po.setCreatedEmp(new Long(curUserId));
      po.setCreatedOrg(new Long(orgId));
      po.setDomainId(domainId);
      po.setSign(examinationStockActionForm.getSign());
      po.setExaminationStyle(examinationStockActionForm
          .getExaminationStyle());
      po.setExaminationType(examinationStockActionForm.getExaminationType());
      po.setSubject(examinationStockActionForm.getSubject());
      po.setResult(examinationStockActionForm.getResult());
      String[] itemOption = (String[])null;
      String[] isResult = (String[])null;
      if (!po.getExaminationStyle().equals("3")) {
        itemOption = httpServletRequest.getParameterValues("itemOption");
        isResult = httpServletRequest.getParameterValues("isResult");
      } 
      if (itemOption == null)
        itemOption = new String[0]; 
      if (isResult == null)
        isResult = new String[0]; 
      Object[] para = { itemOption, isResult };
      boolean ret = bd.save(po, para);
      if (!ret)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        examinationStockActionForm.reset(actionMapping, 
            httpServletRequest);
        examinationStockActionForm.setSaveType("saveAndContinue");
      } else if ("saveAndExit".equals(saveType)) {
        examinationStockActionForm.reset(actionMapping, 
            httpServletRequest);
        examinationStockActionForm.setSaveType("saveAndExit");
      } 
    } else if (action.equals("modify") || action.equals("view")) {
      tag = action;
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      ExaminationStockPO po = new ExaminationStockPO();
      po = bd.load(new Long(httpServletRequest.getParameter("id")));
      examinationStockActionForm.setSubject(po.getSubject());
      examinationStockActionForm.setSign(po.getSign());
      examinationStockActionForm.setExaminationStyle(po
          .getExaminationStyle());
      examinationStockActionForm.setExaminationID(new Long(
            httpServletRequest.getParameter("id")));
      if (po.getExaminationStyle().equals("3")) {
        examinationStockActionForm.setResult(po.getResult());
      } else {
        httpServletRequest.setAttribute("examinationItem", 
            po.getExaminationItem());
      } 
      httpServletRequest.setAttribute("examinationStyle", 
          po.getExaminationStyle());
      httpServletRequest.setAttribute("examinationType", 
          po.getExaminationType());
      httpServletRequest.setAttribute("examinationSign", po.getSign());
    } else if (action.equals("update")) {
      tag = "modify";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      ExaminationStockPO po = new ExaminationStockPO();
      po.setExaminationType(examinationStockActionForm.getExaminationType());
      po.setSubject(examinationStockActionForm.getSubject());
      po.setResult(examinationStockActionForm.getResult());
      po.setSign(examinationStockActionForm.getSign());
      String[] itemOption = (String[])null;
      String[] isResult = (String[])null;
      String[] itemID = (String[])null;
      if (!examinationStockActionForm.getExaminationStyle().equals("3")) {
        itemOption = httpServletRequest.getParameterValues("itemOption");
        isResult = httpServletRequest.getParameterValues("isResult");
        itemID = httpServletRequest.getParameterValues("itemID");
      } 
      Object[] para = { itemOption, isResult, itemID };
      boolean ret = bd.update(po, para, 
          examinationStockActionForm.getExaminationID());
      if (!ret)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType))
        examinationStockActionForm.setSaveType("updateAndExit"); 
    } else if ("delete".equals(action)) {
      tag = "list";
      if (httpServletRequest.getParameter("id") != null && 
        !"".equals(httpServletRequest.getParameter("id")) && 
        !bd.delete(new Long(httpServletRequest.getParameter("id"))))
        httpServletRequest.getSession(true).setAttribute("delsuccess", "0"); 
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      list(httpServletRequest);
    } else if (action.equals("deleteBatch")) {
      tag = "list";
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids")) && 
        !bd.deleteBatch(httpServletRequest.getParameter("ids")))
        httpServletRequest.getSession(true).setAttribute("delsuccess", "0"); 
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      list(httpServletRequest);
    } else if (action.equals("move")) {
      tag = "list";
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids")))
        bd.move(httpServletRequest.getParameter("ids"), 
            httpServletRequest.getParameter("sign")); 
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      list(httpServletRequest);
    } else if (action.equals("selectStock")) {
      tag = "selectStock";
      httpServletRequest.setAttribute("typeList", (
          new ExaminationTypeBD())
          .getTypeList(domainId));
      selectStock(httpServletRequest);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest) {
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
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
    ManagerService mbd = new ManagerService();
    String whereSQL = mbd.getRightFinalWhere(curUserId, orgId, "07*40*02", 
        "po.createdOrg", 
        "po.createdEmp");
    String sign = "0";
    if (httpServletRequest.getParameter("sign") != null && 
      !"".equals(httpServletRequest.getParameter("sign")))
      sign = httpServletRequest.getParameter("sign"); 
    String searchStyle = httpServletRequest.getParameter("searchStyle");
    String searchType = httpServletRequest.getParameter("searchType");
    String searchSubject = httpServletRequest.getParameter("searchSubject");
    String searchSQL = "";
    if (searchStyle != null && !"0".equals(searchStyle) && 
      !"".equals(searchStyle) && !"null".equals(searchStyle))
      searchSQL = String.valueOf(searchSQL) + " and po.examinationStyle=" + searchStyle; 
    if (searchType != null && !"0".equals(searchType) && 
      !"".equals(searchType) && !"null".equals(searchType))
      searchSQL = String.valueOf(searchSQL) + " and po.examinationType=" + searchType; 
    if (searchSubject != null && 
      !"".equals(searchSubject) && !"null".equals(searchSubject))
      searchSQL = String.valueOf(searchSQL) + " and po.subject like '%" + searchSubject + "%' "; 
    String whereSql = " where po.examinationType=ppo.id and po.domainId=" + 
      domainId + " and " + whereSQL + " and po.sign=" + 
      sign + searchSQL;
    if (httpServletRequest.getParameter("styleOrder") != null && 
      !"".equals(httpServletRequest.getParameter("styleOrder")) && 
      !"null".equals(httpServletRequest.getParameter("styleOrder"))) {
      if (httpServletRequest.getParameter("styleOrder").equals("0")) {
        whereSql = String.valueOf(whereSql) + " order by po.examinationStyle ";
      } else {
        whereSql = String.valueOf(whereSql) + " order by po.examinationStyle desc ";
      } 
    } else if (httpServletRequest.getParameter("typeOrder") != null && 
      !"".equals(httpServletRequest.getParameter("typeOrder")) && 
      !"null".equals(httpServletRequest.getParameter("typeOrder"))) {
      if (httpServletRequest.getParameter("typeOrder").equals("0")) {
        whereSql = String.valueOf(whereSql) + " order by po.examinationType ";
      } else {
        whereSql = String.valueOf(whereSql) + " order by po.examinationType desc ";
      } 
    } else {
      whereSql = String.valueOf(whereSql) + " order by po.id desc";
    } 
    Page page = new Page(
        "po.examinationID,po.examinationType,ppo.typeName,po.examinationStyle,po.subject", 
        "com.js.oa.hr.examination.po.ExaminationStockPO po,com.js.oa.hr.examination.po.ExaminationTypePO ppo ", 
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
        "action,sign,searchStyle,searchType,searchSubject,styleOrder,typeOrder");
  }
  
  private void selectStock(HttpServletRequest httpServletRequest) {
    String orgId = httpServletRequest.getSession(true).getAttribute("orgId")
      .toString();
    String curUserId = httpServletRequest.getSession(true).getAttribute(
        "userId")
      .toString();
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
    ManagerService mbd = new ManagerService();
    String whereSQL = mbd.getRightFinalWhere(curUserId, orgId, "07*40*02", 
        "po.createdOrg", 
        "po.createdEmp");
    String signSQL = "";
    String sign = "0";
    if (httpServletRequest.getParameter("sign") != null && 
      !"".equals(httpServletRequest.getParameter("sign")) && 
      !"null".equals(httpServletRequest.getParameter("sign"))) {
      sign = httpServletRequest.getParameter("sign");
      signSQL = " and po.sign=" + sign;
    } 
    String searchStyle = httpServletRequest.getParameter("searchStyle");
    String searchType = httpServletRequest.getParameter("searchType");
    String searchSubject = httpServletRequest.getParameter("searchSubject");
    String searchSQL = "";
    if (searchStyle != null && !"0".equals(searchStyle) && 
      !"".equals(searchStyle) && !"null".equals(searchStyle))
      searchSQL = String.valueOf(searchSQL) + " and po.examinationStyle=" + searchStyle; 
    if (searchType != null && !"0".equals(searchType) && 
      !"".equals(searchType) && !"null".equals(searchType))
      searchSQL = String.valueOf(searchSQL) + " and po.examinationType=" + searchType; 
    if (searchSubject != null && 
      !"".equals(searchSubject) && !"null".equals(searchSubject))
      searchSQL = String.valueOf(searchSQL) + " and po.subject like '%" + searchSubject + "%' "; 
    String whereSql = " where po.examinationType=ppo.id and po.domainId=" + 
      domainId + " and " + whereSQL + signSQL + searchSQL;
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    Page page = new Page(
        "po.examinationID,po.examinationType,ppo.typeName,po.examinationStyle,po.subject", 
        "com.js.oa.hr.examination.po.ExaminationStockPO po,com.js.oa.hr.examination.po.ExaminationTypePO ppo ", 
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
        "action,sign,searchStyle,searchType,searchSubject,styleOrder,typeOrder,ids");
  }
}
