package com.js.oa.relproject.action;

import com.js.oa.relproject.bean.ProNoteBean;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.relproject.po.ProNotePO;
import com.js.util.page.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProNoteAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String operate = request.getParameter("flag");
    String projectId = request.getParameter("projectId");
    String tag = "add";
    if ("add".equals(operate)) {
      request.setAttribute("projectId", projectId);
    } else if ("save".equals(operate)) {
      tag = "add";
      ProNoteActionForm noteForm = (ProNoteActionForm)form;
      ProNotePO po = new ProNotePO();
      BeanUtils.copyProperties(po, noteForm);
      po.setEmpId(Long.valueOf(userId));
      po.setEmpName(userName);
      po.setSendTime(new Date());
      ProNoteBean bean = new ProNoteBean();
      bean.saveNote(po);
      request.setAttribute("operate", "saveClose");
      tag = "close";
    } else if ("list".equals(operate)) {
      tag = "list";
      String para = "po.id,po.content,po.sendTime,po.empId,po.empName";
      String from = "com.js.oa.relproject.po.ProNotePO po";
      String where = "where po.projectId=" + projectId;
      list(request, para, from, where);
    } else if ("load".equals(operate)) {
      tag = "load";
      String noteId = request.getParameter("noteId");
      ProNoteBean bean = new ProNoteBean();
      ProNotePO po = bean.load(Long.valueOf(noteId));
      request.setAttribute("notePO", po);
    } else if ("delete".equals(operate)) {
      String proIds = request.getParameter("ids");
      RelProjectBean bean = new RelProjectBean();
      if (proIds != null && proIds.length() > 0 && !"null".equals(proIds))
        bean.deleteProject(proIds); 
      tag = "list";
      String para = "po.id,po.content,po.sendTime,po.empId,po.empName";
      String from = "com.js.oa.relproject.po.ProNotePO po";
      String where = "where po.projectId=" + projectId;
      list(request, para, from, where);
    } else if ("update".equals(operate)) {
      ProNoteBean bean = new ProNoteBean();
      String noteId = request.getParameter("noteId");
      String content = request.getParameter("content");
      bean.saveProNote(noteId, content);
      ProNotePO po = new ProNotePO();
      po = bean.load(Long.valueOf(noteId));
      request.setAttribute("notePO", po);
      request.setAttribute("close", "1");
      tag = "load";
    } else if ("deleteNote".equals(operate)) {
      ProNoteBean bean = new ProNoteBean();
      String noteId = request.getParameter("noteId");
      bean.deleteProNote(noteId);
      tag = "close";
    } 
    return mapping.findForward(tag);
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
    request.setAttribute("pageParameters", "flag");
  }
}
