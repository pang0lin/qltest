package com.js.oa.jsflow.action;

import com.js.oa.jsflow.service.ModuleBD;
import com.js.oa.jsflow.service.PackageBD;
import com.js.oa.jsflow.service.ProcessBD;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.PackageVO;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WFPackageAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WFPackageActionForm wFPackageActionForm = (WFPackageActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    String action = httpServletRequest.getParameter("action");
    if (action == null)
      action = "view"; 
    String tag = "view";
    int moduleId = 0;
    if (httpServletRequest.getParameter("moduleId") != null)
      moduleId = Integer.parseInt(httpServletRequest.getParameter("moduleId")); 
    httpServletRequest.setAttribute("moduleId", (new StringBuilder(String.valueOf(moduleId))).toString());
    if (action.equals("view")) {
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(moduleId);
      HttpSession httpSession = httpServletRequest.getSession(true);
      ManagerService managerBD = new ManagerService();
      httpServletRequest.setAttribute("canAdd", "1");
      tag = "view";
      list(httpServletRequest, moduleVO);
    } else if (action.equals("add")) {
      tag = "add";
      wFPackageActionForm.setOrderCode(1000);
    } else if (action.equals("continue") || action.equals("close")) {
      HttpSession httpSession = httpServletRequest.getSession(true);
      PackageVO packageVO = new PackageVO();
      packageVO.setCreatedEmp(Long.parseLong(httpSession.getAttribute("userId").toString()));
      packageVO.setCreatedOrg(Long.parseLong(httpSession.getAttribute("orgId").toString()));
      packageVO.setCreatedDate(new Date());
      packageVO.setDescription(wFPackageActionForm.getPackageDescription());
      packageVO.setName(wFPackageActionForm.getPackageName());
      packageVO.setModuleId(Integer.parseInt(httpServletRequest.getParameter("moduleId")));
      packageVO.setDomainId(session.getAttribute("domainId").toString());
      packageVO.setOrderCode(wFPackageActionForm.getOrderCode());
      PackageBD PackageBD = new PackageBD();
      boolean success = PackageBD.addPackage(packageVO);
      if (success) {
        if (action.equals("continue")) {
          tag = "add";
          wFPackageActionForm.setPackageName("");
          wFPackageActionForm.setPackageDescription("");
          wFPackageActionForm.setOrderCode(1000);
        } else {
          tag = "close";
        } 
      } else {
        tag = "addFail";
      } 
    } else if (action.equals("modify")) {
      tag = "modify";
      String wfPackageId = httpServletRequest.getParameter("id");
      wFPackageActionForm.setWfPackageId(wfPackageId);
      PackageBD packageBD = new PackageBD();
      PackageVO packageVO = packageBD.getPackage(Long.parseLong(wfPackageId));
      String packageName = packageVO.getName();
      String packageDescription = packageVO.getDescription();
      if (packageName == null)
        packageName = ""; 
      if (packageDescription == null)
        packageDescription = ""; 
      wFPackageActionForm.setPackageName(packageName);
      wFPackageActionForm.setPackageDescription(packageDescription);
      wFPackageActionForm.setOrderCode(packageVO.getOrderCode());
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
    } else if (action.equals("trueModify")) {
      PackageVO packageVO = new PackageVO();
      packageVO.setId(Long.parseLong(wFPackageActionForm.getWfPackageId()));
      packageVO.setName(wFPackageActionForm.getPackageName());
      packageVO.setDescription(wFPackageActionForm.getPackageDescription());
      packageVO.setOrderCode(wFPackageActionForm.getOrderCode());
      PackageBD packageBD = new PackageBD();
      if (packageBD.updatePackage(packageVO)) {
        tag = "close";
      } else {
        tag = "modifyFail";
      } 
      httpServletRequest.setAttribute("pager.offset", httpServletRequest.getParameter("pager.offset"));
    } else if (action.equals("singleDelete") || action.equals("batchDelete")) {
      ModuleBD moduleBD = new ModuleBD();
      ModuleVO moduleVO = moduleBD.getModule(moduleId);
      HttpSession httpSession = httpServletRequest.getSession(true);
      ManagerService managerBD = new ManagerService();
      if (managerBD.hasRight(httpSession.getAttribute("userId").toString(), String.valueOf(moduleVO.getPackRightType()) + "*02")) {
        httpServletRequest.setAttribute("canAdd", "1");
      } else {
        httpServletRequest.setAttribute("canAdd", "0");
      } 
      String deleteId = "0";
      if (action.equals("singleDelete")) {
        deleteId = httpServletRequest.getParameter("id");
      } else {
        String[] batchId = httpServletRequest.getParameterValues("batchId");
        if (batchId != null) {
          for (int i = 0; i < batchId.length; i++)
            deleteId = String.valueOf(deleteId) + batchId[i] + ","; 
          deleteId = deleteId.substring(0, deleteId.length() - 1);
        } 
      } 
      tag = "view";
      ProcessBD processBD = new ProcessBD();
      Boolean b = processBD.checkProcessByPackage(deleteId);
      if (b.booleanValue()) {
        httpServletRequest.setAttribute("deleteOrNot", "0");
      } else {
        PackageBD PackageBD = new PackageBD();
        PackageBD.removePackage(deleteId);
        httpServletRequest.setAttribute("deleteOrNot", "1");
      } 
      list(httpServletRequest, moduleVO);
    } 
    return actionMapping.findForward(tag);
  }
  
  public void list(HttpServletRequest httpServletRequest, ModuleVO moduleVO) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !httpServletRequest.getParameter("pager.offset").equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    HttpSession httpSession = httpServletRequest.getSession(true);
    ManagerService managerBD = new ManagerService();
    String whereSQL = "";
    if (moduleVO.isPackRight()) {
      String where = managerBD.getRightFinalWhere(httpSession.getAttribute("userId").toString(), 
          httpSession.getAttribute("orgId").toString(), 
          String.valueOf(moduleVO.getPackRightType()) + "*02", "aaa.createdOrg", 
          "aaa.createdEmp");
      whereSQL = " where ((" + where + ") or (aaa.createdEmp = " + 
        httpSession.getAttribute("userId").toString() + 
        ")) and aaa.moduleId = " + moduleVO.getId() + " and aaa.domainId=" + httpSession.getAttribute("domainId") + " order by aaa.orderCode,aaa.wfPackageId desc";
    } else {
      whereSQL = " where aaa.moduleId = " + moduleVO.getId() + " and aaa.domainId=" + httpSession.getAttribute("domainId") + " order by  aaa.orderCode,aaa.wfPackageId desc";
    } 
    String viewSQL = " aaa.wfPackageId,aaa.packageName,aaa.packageDescription,aaa.createdEmp,aaa.createdOrg,aaa.orderCode ";
    String fromSQL = " com.js.oa.jsflow.po.WFPackagePO aaa ";
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("packageList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("pageCount", pageCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,moduleId");
  }
}
