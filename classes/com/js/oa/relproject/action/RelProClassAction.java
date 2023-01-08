package com.js.oa.relproject.action;

import com.js.oa.relproject.bean.RelProClassBean;
import com.js.oa.relproject.po.RelProClassPO;
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

public class RelProClassAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgName = session.getAttribute("orgName").toString();
    RelProClassBean relProClassBean = new RelProClassBean();
    String action = request.getParameter("action");
    if ("add".equals(action)) {
      List<Object> list = relProClassBean.searchAll(Long.valueOf(-1L));
      request.setAttribute("list", list);
    } else {
      if ("save".equals(action)) {
        RelProClassPO relProClassPO = new RelProClassPO();
        relProClassPO.setCreateEmp(Long.valueOf(userId).longValue());
        relProClassPO.setCreateEmpName(userName);
        relProClassPO.setCreateOrg(Long.valueOf(orgId).longValue());
        relProClassPO.setCreateOrgName(orgName);
        relProClassPO.setName(request.getParameter("name"));
        relProClassBean.save(relProClassPO, request.getParameter("parentId"), request.getParameter("sortCode"), request.getParameter("insertSite"));
        request.setAttribute("contun", "yes");
        return mapping.findForward("add");
      } 
      if ("list".equals(action)) {
        String para = "po.id,po.name,po.classSortCode,po.parentId,po.createEmpName,po.level";
        String from = "com.js.oa.relproject.po.RelProClassPO po";
        ManagerService managerBD = new ManagerService();
        String relProClassRightWhere = managerBD.getRightFinalWhere(
            userId.toString(), 
            orgId.toString(), 
            "04*10*01", "po.createOrg", "po.createEmp");
        String where = " where " + relProClassRightWhere + " order by  po.classSort";
        list(request, para, from, where);
      } else if ("load".equals(action)) {
        String proClassId = request.getParameter("proClassId");
        RelProClassPO relProClassPO = relProClassBean.loadById(Long.valueOf(proClassId));
        request.setAttribute("po", relProClassPO);
        List<Object> list = relProClassBean.searchAll(Long.valueOf(proClassId));
        request.setAttribute("list", list);
      } else {
        if ("update".equals(action)) {
          String proClassId = request.getParameter("proClassId");
          RelProClassPO relProClassPO = relProClassBean.loadById(Long.valueOf(proClassId));
          relProClassPO.setName(request.getParameter("name"));
          relProClassBean.update(relProClassPO, request.getParameter("parentId"), request.getParameter("sortCode"), request.getParameter("insertSite"));
          request.setAttribute("contun", "yes");
          return mapping.findForward("add");
        } 
        if ("delete".equals(action)) {
          String proClassId = request.getParameter("proClassId");
          relProClassBean.del(Long.valueOf(proClassId));
          String url = "/jsoa/RelProClassAction.do?action=list";
          request.setAttribute("url", url);
          return mapping.findForward("forward");
        } 
      } 
    } 
    return mapping.findForward(action);
  }
  
  public void list(HttpServletRequest request, String viewSql, String fromSql, String whereSql) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("list", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
  }
}
