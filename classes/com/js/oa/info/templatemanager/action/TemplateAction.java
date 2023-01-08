package com.js.oa.info.templatemanager.action;

import com.js.oa.info.templatemanager.po.InformationTemplatePO;
import com.js.oa.info.templatemanager.service.TemplateBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import com.js.util.util.StringSplit;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TemplateAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    TemplateActionForm templateActionForm = (TemplateActionForm)actionForm;
    TemplateBD bd = new TemplateBD();
    String action = request.getParameter("action");
    String module = request.getParameter("module");
    HttpSession session = request.getSession(true);
    String curUserId = session.getAttribute("userId").toString();
    String curOrgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String domainId = session.getAttribute("domainId").toString();
    ManagerService mbd = new ManagerService();
    if ("add".equals(action)) {
      boolean addSuccess = save(request, templateActionForm);
      if (addSuccess) {
        templateActionForm.reset(actionMapping, request);
        return actionMapping.findForward("add");
      } 
      return actionMapping.findForward("addFalse");
    } 
    if ("modi".equals(action)) {
      if (update(request)) {
        request.setAttribute("module", request.getParameter("module"));
        return actionMapping.findForward("modi");
      } 
      return actionMapping.findForward("modiFalse");
    } 
    if ("load".equals(action)) {
      TemplateActionForm form = load(request);
      try {
        BeanUtils.copyProperties(templateActionForm, form);
      } catch (Exception e) {
        e.printStackTrace();
      } 
      return actionMapping.findForward("modi");
    } 
    if ("delBatch".equals(action)) {
      delBatch(request);
      action = "list";
    } else if ("delAll".equals(action)) {
      String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, "01*02*01", "po.createdOrg", "po.createdEmp");
      wherePara = " po.createdEmp=" + curUserId + " or ( " + wherePara + ")";
      bd.delAll(wherePara);
      action = "list";
    } 
    if ("list".equals(action)) {
      String wherePara = mbd.getRightFinalWhere(curUserId, curOrgId, "01*02*01", "po.createdOrg", "po.createdEmp");
      wherePara = " po.module=" + module + " and ((po.ispublic=1 and (po.createdEmp=" + curUserId + " or ( " + wherePara + "))) or (po.ispublic=0 and po.createdEmp=" + curUserId + "))";
      list(request, wherePara);
      String addRight = "0";
      if (mbd.hasRight(curUserId, "01*02*01"))
        addRight = "1"; 
      request.setAttribute("addRight", addRight);
    } else if ("judgeName".equals(action)) {
      String templateTitle = request.getParameter("templateTitle");
      String templateId = request.getParameter("templateId");
      request.setAttribute("judgeName", bd.judgeName(domainId, templateTitle, templateId));
      return actionMapping.findForward("judgeName");
    } 
    return actionMapping.findForward("list");
  }
  
  private boolean save(HttpServletRequest httpServletRequest, TemplateActionForm templateActionForm) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    InformationTemplatePO templatePO = new InformationTemplatePO();
    templatePO = (InformationTemplatePO)FillBean.transformOneToOne(templateActionForm, InformationTemplatePO.class);
    HttpSession httpSession = httpServletRequest.getSession(true);
    templatePO.setCreatedEmp(Long.parseLong(httpSession.getAttribute("userId").toString()));
    templatePO.setCreatedOrg(Long.parseLong(httpSession.getAttribute("orgId").toString()));
    String userOrgGroup = templateActionForm.getUserOrgGroup();
    templatePO.setUseEmp(StringSplit.splitWith(userOrgGroup, "$", "*@"));
    templatePO.setUseOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
    templatePO.setUseGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
    templatePO.setDomainId(domainId);
    templatePO.setIspublic(httpServletRequest.getParameter("ispublic"));
    return (new TemplateBD()).add(templatePO).booleanValue();
  }
  
  private TemplateActionForm load(HttpServletRequest httpServletRequest) {
    InformationTemplatePO templatePO = (new TemplateBD()).load(httpServletRequest.getParameter("id"));
    TemplateActionForm form = (TemplateActionForm)FillBean.transformOTO(templatePO, TemplateActionForm.class);
    form.setUserOrgGroup(String.valueOf(templatePO.getUseEmp()) + templatePO.getUseGroup() + templatePO.getUseOrg());
    httpServletRequest.setAttribute("isPublic", templatePO.getIspublic());
    return form;
  }
  
  private boolean update(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    InformationTemplatePO templatePO = new InformationTemplatePO();
    templatePO.setId(Long.parseLong(request.getParameter("id")));
    templatePO.setTemplateContent(request.getParameter("templateContent"));
    templatePO.setTemplateRemark(request.getParameter("templateRemark"));
    templatePO.setTemplateTitle(request.getParameter("templateTitle"));
    String userOrgGroup = request.getParameter("userOrgGroup");
    templatePO.setUseEmp(StringSplit.splitWith(userOrgGroup, "$", "*@"));
    templatePO.setUseOrg(StringSplit.splitWith(userOrgGroup, "*", "@$"));
    templatePO.setUseGroup(StringSplit.splitWith(userOrgGroup, "@", "$*"));
    templatePO.setUseRangeName(request.getParameter("useRangeName"));
    templatePO.setDomainId(domainId);
    templatePO.setIspublic(request.getParameter("ispublic"));
    templatePO.setModule(request.getParameter("module"));
    return (new TemplateBD()).update(templatePO).booleanValue();
  }
  
  private void delBatch(HttpServletRequest request) {
    (new TemplateBD()).delBatch(request.getParameter("ids"));
  }
  
  public void list(HttpServletRequest request, String wherePara) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String templateTitle = request.getParameter("templateTitle");
    String where = " where 1=1 ";
    if (templateTitle != null && !"".equals(templateTitle))
      where = String.valueOf(where) + " and po.templateTitle like '%" + templateTitle + "%'"; 
    where = String.valueOf(where) + " and ( " + wherePara + ") and po.domainId=" + domainId + " ";
    Page page = new Page(" po.id,po.useRangeName,po.templateTitle,po.templateRemark,po.templateContent", 
        " com.js.oa.info.templatemanager.po.InformationTemplatePO po ", 
        String.valueOf(where) + " order by po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = new ArrayList();
    list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action,templateTitle,module");
  }
}
