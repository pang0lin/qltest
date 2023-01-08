package com.js.oa.hr.examination.action;

import com.js.oa.hr.examination.po.ExaminationTypePO;
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

public class ExaminationTypeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    ExaminationTypeActionForm examinationTypeActionForm = 
      (ExaminationTypeActionForm)actionForm;
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
    httpServletRequest.setAttribute("examOnly", httpServletRequest.getParameter("examOnly"));
    String tag = "list";
    ExaminationTypeBD bd = new ExaminationTypeBD();
    if (action.equals("list")) {
      tag = "list";
      list(httpServletRequest);
    } else if (action.equals("add")) {
      tag = "add";
    } else if (action.equals("addExaminationType")) {
      ExaminationTypePO po = new ExaminationTypePO();
      po.setTypeName(examinationTypeActionForm.getTypeName());
      po.setCreatedEmp(new Long(curUserId));
      po.setCreatedOrg(new Long(orgId));
      po.setDomainID(domainId);
      boolean ret = bd.save(po);
      if (!ret)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        examinationTypeActionForm.reset(actionMapping, 
            httpServletRequest);
        examinationTypeActionForm.setSaveType("saveAndContinue");
        return actionMapping.findForward("add");
      } 
      if ("saveAndExit".equals(saveType)) {
        examinationTypeActionForm.reset(actionMapping, 
            httpServletRequest);
        examinationTypeActionForm.setSaveType("saveAndExit");
        return actionMapping.findForward("add");
      } 
    } else if (action.equals("modify")) {
      tag = "modify";
      Long id = new Long(httpServletRequest.getParameter("id"));
      ExaminationTypePO po = bd.load(id);
      examinationTypeActionForm.setId(po.getId());
      examinationTypeActionForm.setTypeName(po.getTypeName());
    } else if (action.equals("updateExaminationType")) {
      tag = "modify";
      ExaminationTypePO po = new ExaminationTypePO();
      po.setTypeName(examinationTypeActionForm.getTypeName());
      boolean ret = bd.update(po, examinationTypeActionForm.getId());
      examinationTypeActionForm.setSaveType("saveAndExit");
    } else if (action.equals("delete")) {
      tag = "list";
      boolean ret = true;
      if (httpServletRequest.getParameter("id") != null && 
        !"".equals(httpServletRequest.getParameter("id")))
        ret = bd.delete(new Long(httpServletRequest.getParameter("id"))); 
      if (!ret)
        httpServletRequest.setAttribute("delsuccess", "0"); 
      list(httpServletRequest);
    } else if (action.equals("deleteBatch")) {
      tag = "list";
      boolean ret = true;
      if (httpServletRequest.getParameter("ids") != null && 
        !"".equals(httpServletRequest.getParameter("ids")))
        ret = bd.delBatch(httpServletRequest.getParameter("ids")); 
      if (!ret)
        httpServletRequest.setAttribute("delsuccess", "0"); 
      list(httpServletRequest);
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
    String whereSql = " where po.domainID=" + domainId + " and " + whereSQL;
    whereSql = String.valueOf(whereSql) + " order by po.id desc";
    Page page = new Page(
        "po.id,po.typeName", 
        "com.js.oa.hr.examination.po.ExaminationTypePO po ", 
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
