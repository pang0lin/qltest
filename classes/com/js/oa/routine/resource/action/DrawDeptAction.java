package com.js.oa.routine.resource.action;

import com.js.oa.routine.resource.po.DrawDeptPO;
import com.js.oa.routine.resource.service.DrawDeptBD;
import com.js.oa.routine.resource.service.StockBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.Page;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DrawDeptAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    DrawDeptActionForm drawDeptActionForm = (DrawDeptActionForm)actionForm;
    String flag = (httpServletRequest.getParameter("flag") == null) ? "view" : httpServletRequest.getParameter("flag").toString();
    String tag = "view";
    if (flag.equals("view") || flag.equals("search") || flag.equals("delete")) {
      httpServletRequest.setAttribute("vindicate", getVindicate(httpServletRequest));
      HttpSession httpSession = httpServletRequest.getSession(true);
      httpServletRequest.setAttribute("addRight", new Boolean((new ManagerService()).hasRightTypeName(httpSession.getAttribute("userId").toString(), "物品管理-领用单位", "新增")));
      getUserManaStock(httpServletRequest);
      tag = "view";
      if (flag.equals("delete"))
        delete(httpServletRequest); 
      if (flag.equals("search")) {
        search(httpServletRequest);
      } else {
        view(httpServletRequest);
      } 
    } else if (flag.equals("add") || flag.equals("close") || flag.equals("continue")) {
      tag = "add";
      if (flag.equals("add")) {
        getUserManaStock(httpServletRequest);
      } else if (save(drawDeptActionForm, httpServletRequest)) {
        if (flag.equals("close")) {
          httpServletRequest.setAttribute("close", "1");
        } else {
          httpServletRequest.setAttribute("close", "0");
          getUserManaStock(httpServletRequest);
        } 
      } else {
        httpServletRequest.setAttribute("close", "2");
        getUserManaStock(httpServletRequest);
      } 
    } else if (flag.equals("modify") || flag.equals("update")) {
      tag = "modify";
      if (flag.equals("modify")) {
        getUserManaStock(httpServletRequest);
        getSingleDept(drawDeptActionForm, httpServletRequest);
      } else if (update(drawDeptActionForm, httpServletRequest)) {
        httpServletRequest.setAttribute("close", "1");
      } else {
        httpServletRequest.setAttribute("close", "2");
        getUserManaStock(httpServletRequest);
        getSingleDept(drawDeptActionForm, httpServletRequest);
      } 
    } 
    return actionMapping.findForward(tag);
  }
  
  private void getUserManaStock(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    httpServletRequest.setAttribute("stockList", (new StockBD()).getUserManaStock(httpSession.getAttribute("userId").toString(), domainId));
  }
  
  private boolean save(DrawDeptActionForm drawDeptActionForm, HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    DrawDeptPO drawDeptPO = new DrawDeptPO();
    drawDeptPO.setName(drawDeptActionForm.getName());
    drawDeptPO.setRemark(drawDeptActionForm.getRemark());
    drawDeptPO.setCreatedEmp(new Long(httpSession.getAttribute("userId").toString()));
    drawDeptPO.setCreatedOrg(new Long(httpSession.getAttribute("orgId").toString()));
    String domainId = "";
    if (httpSession.getAttribute("domainId") != null && !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    if (!domainId.equals("") || domainId.length() > 0)
      drawDeptPO.setDomainid(Integer.parseInt(domainId)); 
    return (new DrawDeptBD()).save(drawDeptPO, drawDeptActionForm.getStock()).booleanValue();
  }
  
  private void view(HttpServletRequest httpServletRequest) {
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String viewSql = "aaa.id,aaa.name,bbb.stockName,aaa.remark";
    String fromSql = "com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb";
    String whereSql = " where aaa.domainid=" + domainId + " order by aaa.id desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list != null && list.size() == 0 && offset >= 15) {
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
    httpServletRequest.setAttribute("drawDeptList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,searchStock,searchName");
  }
  
  private void search(HttpServletRequest httpServletRequest) {
    String domainId = "";
    HttpSession httpSession = httpServletRequest.getSession(true);
    if (httpSession.getAttribute("domainId") != null && !httpSession.getAttribute("domainId").equals("null"))
      domainId = httpSession.getAttribute("domainId").toString(); 
    String searchName = httpServletRequest.getParameter("searchName");
    httpServletRequest.setAttribute("searchName", searchName);
    String searchStock = httpServletRequest.getParameter("searchStock");
    httpServletRequest.setAttribute("searchStock", searchStock);
    String viewSql = "aaa.id,aaa.name,bbb.stockName,aaa.remark";
    String fromSql = "com.js.oa.routine.resource.po.DrawDeptPO aaa join aaa.stock bbb";
    StringBuffer whereSql = new StringBuffer("where aaa.domainid=" + domainId);
    if (!searchStock.equals("0"))
      whereSql.append(" and bbb.id=" + searchStock); 
    whereSql.append(" and aaa.name like '%" + searchName + "%'");
    list(httpServletRequest, viewSql, fromSql, whereSql.toString());
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("deleteSuccess", (
        new DrawDeptBD()).delete(httpServletRequest.getParameter("drawDeptId")));
  }
  
  private void getSingleDept(DrawDeptActionForm drawDeptActionForm, HttpServletRequest httpServletRequest) {
    String drawDeptId = httpServletRequest.getParameter("drawDeptId");
    httpServletRequest.setAttribute("drawDeptId", drawDeptId);
    String[] drawDept = (new DrawDeptBD()).getSingleDept(drawDeptId);
    drawDeptActionForm.setName(drawDept[0]);
    httpServletRequest.setAttribute("stockId", drawDept[1]);
    drawDeptActionForm.setRemark(drawDept[2]);
  }
  
  private boolean update(DrawDeptActionForm drawDeptActionForm, HttpServletRequest httpServletRequest) {
    DrawDeptPO drawDeptPO = new DrawDeptPO();
    drawDeptPO.setName(drawDeptActionForm.getName());
    drawDeptPO.setRemark(drawDeptActionForm.getRemark());
    String drawDeptId = httpServletRequest.getParameter("drawDeptId");
    drawDeptPO.setId(new Long(drawDeptId));
    return (new DrawDeptBD()).update(drawDeptPO, drawDeptActionForm.getStock()).booleanValue();
  }
  
  private String getVindicate(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String where = (new ManagerService()).getRightFinalWhere(httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "物品管理-领用单位", "维护", "aaa.createdOrg", "aaa.createdEmp");
    return (new DrawDeptBD()).getVindicate(where);
  }
}
