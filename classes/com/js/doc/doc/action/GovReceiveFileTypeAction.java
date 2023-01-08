package com.js.doc.doc.action;

import com.js.doc.doc.po.GovReceiveFileTypePO;
import com.js.doc.doc.service.GovReceiveFileTypeBD;
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

public class GovReceiveFileTypeAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    String info = null;
    String update_info = null;
    GovReceiveFileTypeActionForm govReceiveFileTypeActionForm = (GovReceiveFileTypeActionForm)actionForm;
    GovReceiveFileTypeBD bd = new GovReceiveFileTypeBD();
    String tag = "list";
    String action = (httpServletRequest.getParameter("actionFlag") == null) ? "list" : httpServletRequest.getParameter("actionFlag");
    if ("list".equals(action)) {
      tag = "list";
    } else if ("addwindow".equals(action)) {
      tag = "add";
    } else if ("add".equals(action)) {
      GovReceiveFileTypePO po = new GovReceiveFileTypePO();
      po.setReceiveFileTypeName(govReceiveFileTypeActionForm.getReceiveFileTypeName());
      po.setUserName(govReceiveFileTypeActionForm.getUserName());
      po.setUserId(govReceiveFileTypeActionForm.getUserId());
      po.setOrgId(govReceiveFileTypeActionForm.getOrgId());
      po.setGroupId(govReceiveFileTypeActionForm.getGroupId());
      po.setCreatedEmp(Long.valueOf(session.getAttribute("userId").toString()));
      po.setCreatedOrg(Long.valueOf(session.getAttribute("orgId").toString()));
      po.setDomainId(domainId);
      info = bd.govReceiveFileTypeAdd(po);
      httpServletRequest.setAttribute("info", info);
      tag = "add";
    } else if ("delBatch".equals(action)) {
      if (httpServletRequest.getParameter("ids") != null && !httpServletRequest.getParameter("ids").toString().equals("")) {
        String delinfo = "";
        delinfo = bd.govReceiveFileTypeDelBatch(httpServletRequest.getParameter("ids"));
        httpServletRequest.setAttribute("delinfo", delinfo);
      } 
      tag = "list";
    } else if ("modify".equals(action)) {
      List<GovReceiveFileTypePO> modifyReceiveFileTypeList = bd.govReceiveFileTypeModifylist(httpServletRequest.getParameter("id").toString());
      if (!modifyReceiveFileTypeList.isEmpty()) {
        GovReceiveFileTypePO po = modifyReceiveFileTypeList.get(0);
        govReceiveFileTypeActionForm.setId(po.getId().toString());
        govReceiveFileTypeActionForm.setReceiveFileTypeName(po.getReceiveFileTypeName());
        govReceiveFileTypeActionForm.setGroupId(po.getGroupId());
        govReceiveFileTypeActionForm.setOrgId(po.getOrgId());
        govReceiveFileTypeActionForm.setUserId(po.getUserId());
        govReceiveFileTypeActionForm.setUserName(po.getUserName());
      } 
      tag = "modify";
    } else if ("update".equals(action)) {
      GovReceiveFileTypePO paraPO = new GovReceiveFileTypePO();
      paraPO.setId(Long.valueOf(govReceiveFileTypeActionForm.getId()));
      paraPO.setReceiveFileTypeName(govReceiveFileTypeActionForm.getReceiveFileTypeName());
      paraPO.setUserName(govReceiveFileTypeActionForm.getUserName());
      paraPO.setUserId(govReceiveFileTypeActionForm.getUserId());
      paraPO.setOrgId(govReceiveFileTypeActionForm.getOrgId());
      paraPO.setGroupId(govReceiveFileTypeActionForm.getGroupId());
      paraPO.setDomainId(domainId);
      update_info = bd.govReceiveFileTypeUpdate(paraPO);
      httpServletRequest.setAttribute("update_info", update_info);
      tag = "modify";
    } else if ("delone".equals(action)) {
      if (httpServletRequest.getParameter("id") != null && !httpServletRequest.getParameter("id").toString().equals(""))
        bd.govReceiveFileTypeDel(httpServletRequest.getParameter("id").toString()); 
      tag = "list";
    } 
    if (tag.equals("list")) {
      String viewSQL = "po.id,po.receiveFileTypeName";
      String fromSQL = "com.js.doc.doc.po.GovReceiveFileTypePO po";
      String whereSQL = "where " + (new ManagerService()).getRightFinalWhere(session.getAttribute("userId").toString(), session.getAttribute("orgId").toString(), "03*06*01", "po.createdOrg", "po.createdEmp") + " and po.domainId=" + domainId;
      list(httpServletRequest, viewSQL, fromSQL, whereSQL);
    } 
    return actionMapping.findForward(tag);
  }
  
  private void list(HttpServletRequest httpServletRequest, String viewSQL, String fromSQL, String whereSQL) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && !httpServletRequest.getParameter("pager.offset").toString().equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
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
    httpServletRequest.setAttribute("govReceiveFileTypeList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "");
  }
}
