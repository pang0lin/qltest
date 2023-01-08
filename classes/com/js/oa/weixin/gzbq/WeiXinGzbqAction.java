package com.js.oa.weixin.gzbq;

import com.js.oa.personalwork.paper.service.NotePaperBD;
import com.js.wap.service.WapNotePaperBD;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinGzbqAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String action = request.getParameter("action");
    String tag = "list";
    if ("list".equals(action)) {
      tag = notePaperList(request, session);
      if (request.getAttribute("closeWindow") != null)
        request.setAttribute("closeWindow", request.getAttribute("closeWindow")); 
    } 
    if ("content".equals(action))
      tag = noteContent(request); 
    if ("add".equals(action)) {
      tag = add(request, session);
      request.setAttribute("closeWindow", "1");
    } 
    if ("update".equals(action)) {
      tag = update(request, session);
      request.setAttribute("closeWindow", "1");
    } 
    if ("delete".equals(action)) {
      tag = delete(request, session);
      request.setAttribute("closeWindow", "1");
    } 
    request.setAttribute("action", tag);
    return mapping.findForward(tag);
  }
  
  private String notePaperList(HttpServletRequest request, HttpSession session) throws Exception {
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    long userId = Long.parseLong(String.valueOf(request.getSession(true).getAttribute("userId")));
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    String domainId = session.getAttribute("domainId").toString();
    String where = " where po.empId=" + userId + " and po.domainId=" + domainId + " ";
    if (keyword != null && !"".equals(keyword)) {
      keyword = URLDecoder.decode(keyword, "utf-8");
      where = String.valueOf(where) + " and po.notePaperContent like '%" + keyword + "%' ";
    } 
    String hql = "select po from com.js.oa.personalwork.paper.po.NotePaperPO po " + where + " order by po.id desc";
    Map map = (new WapNotePaperBD()).getNotePaperList(hql, beginIndex, limit);
    List list = (List)map.get("list");
    request.setAttribute("noteList", list);
    request.setAttribute("size", map.get("size"));
    request.setAttribute("keyword", keyword);
    return "list";
  }
  
  private String noteContent(HttpServletRequest request) throws Exception {
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    String noteId = request.getParameter("noteId").toString();
    String content = (new WapNotePaperBD()).getNoteContent(noteId);
    request.setAttribute("content", content);
    request.setAttribute("noteId", noteId);
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    return "info";
  }
  
  private String add(HttpServletRequest request, HttpSession session) throws Exception {
    String content = request.getParameter("contents").toString();
    content = URLDecoder.decode(content, "utf-8");
    String userId = String.valueOf(session.getAttribute("userId"));
    String domainId = String.valueOf(session.getAttribute("domainId"));
    (new NotePaperBD()).add("1", content, userId, domainId);
    return "load";
  }
  
  private String update(HttpServletRequest request, HttpSession session) throws Exception {
    String editId = request.getParameter("noteId").toString();
    String content = request.getParameter("contents").toString();
    content = URLDecoder.decode(content, "utf-8");
    (new NotePaperBD()).update(editId, "1", content);
    return "load";
  }
  
  private String delete(HttpServletRequest request, HttpSession session) throws Exception {
    String noteId = request.getParameter("noteId").toString();
    (new WapNotePaperBD()).delete(noteId);
    return "load";
  }
}
