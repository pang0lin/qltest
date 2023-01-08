package com.js.oa.personalwork.paper.action;

import com.js.oa.personalwork.paper.po.NotePaperPO;
import com.js.oa.personalwork.paper.service.NotePaperBD;
import com.js.util.page.Page;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class NotePaperAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    Long curUserId = new Long(session.getAttribute("userId").toString());
    String domainId = (session.getAttribute("domainId") == null) ? "0" : session.getAttribute("domainId").toString();
    NotePaperActionForm notePaperActionForm = (NotePaperActionForm)actionForm;
    NotePaperBD bd = new NotePaperBD();
    String action = request.getParameter("action");
    String NoteContent = request.getParameter("NoteContent");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
    if ("delBatch".equals(action)) {
      if (request.getParameter("ids") != null)
        bd.delBatch(request.getParameter("ids"), (String)curUserId); 
      action = "list";
    } 
    if ("add1".equals(action)) {
      bd.add("1", 
          NoteContent, 
          (String)curUserId, domainId);
      action = "list";
    } 
    if ("list".equals(action))
      list(request); 
    if ("add".equals(action)) {
      bd.add(notePaperActionForm.getNotePaperColor(), 
          notePaperActionForm.getNotePaperContent(), 
          (String)curUserId, domainId);
      action = "see";
    } 
    if ("modi1".equals(action)) {
      NotePaperPO po = bd.load(request.getParameter("editId"));
      notePaperActionForm.setEditId(request.getParameter("editId"));
      notePaperActionForm.setNotePaperContent(po.getNotePaperContent());
      Date date = po.getCreatedTime();
      request.setAttribute("creatTime", dateFormat.format(date));
      return actionMapping.findForward("add1");
    } 
    if ("modi2".equals(action)) {
      NotePaperPO po = bd.load(request.getParameter("editId"));
      notePaperActionForm.setEditId(request.getParameter("editId"));
      notePaperActionForm.setNotePaperContent(po.getNotePaperContent());
      Date date = po.getCreatedTime();
      request.setAttribute("creatTime", dateFormat.format(date));
      return actionMapping.findForward("add2");
    } 
    if ("modi3".equals(action)) {
      NotePaperPO po = bd.load(request.getParameter("editId"));
      notePaperActionForm.setEditId(request.getParameter("editId"));
      notePaperActionForm.setNotePaperContent(po.getNotePaperContent());
      Date date = po.getCreatedTime();
      request.setAttribute("creatTime", dateFormat.format(date));
      return actionMapping.findForward("add3");
    } 
    if ("modi4".equals(action)) {
      NotePaperPO po = bd.load(request.getParameter("editId"));
      notePaperActionForm.setEditId(request.getParameter("editId"));
      notePaperActionForm.setNotePaperContent(po.getNotePaperContent());
      Date date = po.getCreatedTime();
      request.setAttribute("creatTime", dateFormat.format(date));
      return actionMapping.findForward("add4");
    } 
    if ("update".equals(action)) {
      bd.update(notePaperActionForm.getEditId(), 
          notePaperActionForm.getNotePaperColor(), 
          notePaperActionForm.getNotePaperContent());
      notePaperActionForm.setDone("done");
      return actionMapping.findForward("add1");
    } 
    if ("see".equals(action)) {
      String color = bd.getColor(new Long((String)request.getSession(true).getAttribute("userId")));
      if ("1".equals(color))
        return actionMapping.findForward("add1"); 
      if ("2".equals(color))
        return actionMapping.findForward("add2"); 
      if ("3".equals(color))
        return actionMapping.findForward("add3"); 
      if ("4".equals(color))
        return actionMapping.findForward("add4"); 
      return actionMapping.findForward("add1");
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request) {
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    long userId = Long.parseLong(String.valueOf(request.getSession(true)
          .getAttribute("userId")));
    String where = " where po.empId=" + userId;
    Page page = new Page(
        " po.id,po.notePaperContent,po.notePaperColor,po.createdTime,po.updateTime ", 
        " com.js.oa.personalwork.paper.po.NotePaperPO po ", 
        String.valueOf(where) + 
        " order by po.id desc");
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    request.setAttribute("mylist", list);
    request.setAttribute("recordCount", recordCount);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "action");
  }
}
