package com.js.oa.scheme.workreport.action;

import com.js.oa.scheme.workreport.service.WorkReportTemplateBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkReportTemplateAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : Long.valueOf(session.getAttribute("domainId").toString());
    String curUserId = request.getSession(true).getAttribute("userId").toString();
    String curOrgId = request.getSession(true).getAttribute("orgId").toString();
    String orgIdString = request.getSession(true).getAttribute("orgIdString").toString();
    String curUserName = request.getSession(true).getAttribute("userName").toString();
    String curOrgName = request.getSession(true).getAttribute("orgName").toString();
    WorkReportTemplateActionForm workReportTemplateActionForm = (WorkReportTemplateActionForm)actionForm;
    WorkReportTemplateBD bd = new WorkReportTemplateBD();
    String action = request.getParameter("action");
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("add".equals(action)) {
      bd.add(curUserId, curOrgId, 
          request.getParameter("templateName"), 
          request.getParameter("action"), 
          curUserName, 
          request.getParameter("templateDescript"), 
          request.getParameter("userOrgGroup"), 
          request.getParameter("templateUseRange"), 
          request.getParameter("templateContent"), 
          domainId);
      if (!"false".equals(request.getAttribute("message")))
        workReportTemplateActionForm.reset(actionMapping, request); 
      return actionMapping.findForward("add");
    } 
    if ("load".equals(action)) {
      Map result = bd.load(request.getParameter("editId"), domainId);
      if (result.get("templateContent") != null) {
        workReportTemplateActionForm.setTemplateContent(result.get("templateContent").toString());
      } else {
        workReportTemplateActionForm.setTemplateContent("");
      } 
      if (result.get("templateDescript") != null) {
        workReportTemplateActionForm.setTemplateDescript(result.get("templateDescript").toString());
      } else {
        workReportTemplateActionForm.setTemplateDescript("");
      } 
      if (result.get("templateName") != null) {
        workReportTemplateActionForm.setTemplateName(result.get("templateName").toString());
      } else {
        workReportTemplateActionForm.setTemplateName("");
      } 
      if (result.get("templateUseRange") != null) {
        workReportTemplateActionForm.setTemplateUseRange(result.get("templateUseRange").toString());
      } else {
        workReportTemplateActionForm.setTemplateUseRange("");
      } 
      if (result.get("userOrgGroup") != null) {
        workReportTemplateActionForm.setUserOrgGroup(result.get("userOrgGroup").toString());
      } else {
        workReportTemplateActionForm.setUserOrgGroup("");
      } 
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      bd.update(request.getParameter("editId"), 
          request.getParameter("templateName"), 
          request.getParameter("action"), 
          request.getParameter("templateDescript"), 
          request.getParameter("userOrgGroup"), 
          request.getParameter("templateUseRange"), 
          request.getParameter("templateContent"), 
          domainId);
      return actionMapping.findForward("modi");
    } 
    if ("list".equals(action)) {
      ManagerService mdb = new ManagerService();
      String wherePara = mdb.getRightFinalWhere(curUserId, curOrgId, 
          orgIdString, "工作汇报-模板设置", "维护", "po.createdOrg", 
          "po.createdEmp");
      list(request, 
          " po.createdEmp=" + curUserId + ((
          wherePara != null && wherePara.length() > 0) ? (" or (" + wherePara + ")") : ""), 
          domainId);
      if (mdb.hasRightTypeName(curUserId, "工作汇报-模板设置", "维护")) {
        request.setAttribute("addRight", "1");
        request.setAttribute("modiRight", "1");
      } 
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, String wherePara, Long domainId) {
    try {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      Page page = new Page(
          " po.id,po.templateName ,po.templateUseRange,po.templateDescript,  po.createdEmpName, po.createdEmp , po.createdOrg ", 
          " com.js.oa.scheme.workreport.po.WorkReportTemplatePO po ", 
          " where " + ((wherePara != null && wherePara.length() > 0) ? wherePara : "") + (
          (domainId != null) ? (" and po.templateDomainId = " + domainId) : "") + 
          " order by po.id desc");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
