package com.js.wap.action;

import com.js.oa.personalwork.paper.service.NotePaperBD;
import com.js.wap.service.WapNotePaperBD;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WapNotePaperAction extends DispatchAction {
  public ActionForward notePaperList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limit = WapUtil.LIMITED;
    long userId = Long.parseLong(String.valueOf(request.getSession(true)
          .getAttribute("userId")));
    String domainId = request.getSession(true).getAttribute("domainId").toString();
    String where = " where po.empId=" + userId + " and po.domainId=" + domainId;
    String hql = "select po from com.js.oa.personalwork.paper.po.NotePaperPO po " + 
      where + " order by po.id desc";
    Map map = (new WapNotePaperBD()).getNotePaperList(hql, beginIndex, limit);
    List list = (List)map.get("list");
    request.setAttribute("noteList", list);
    request.setAttribute("size", map.get("size"));
    return mapping.findForward("notePaperList_3g");
  }
  
  public ActionForward notePaper(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    String noteId = request.getParameter("noteId").toString();
    String content = (new WapNotePaperBD()).getNoteContent(noteId);
    request.setAttribute("content", content);
    request.setAttribute("noteId", noteId);
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    return mapping.findForward("notePaper_3g");
  }
  
  public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String editId = request.getParameter("noteId").toString();
    String content = request.getParameter("contents").toString();
    content = URLDecoder.decode(content, "utf-8");
    (new NotePaperBD()).update(editId, "1", content);
    return notePaperList(mapping, form, request, response);
  }
  
  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String noteId = request.getParameter("noteId").toString();
    (new WapNotePaperBD()).delete(noteId);
    return notePaperList(mapping, form, request, response);
  }
  
  public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String content = request.getParameter("contents").toString();
    content = URLDecoder.decode(content, "utf-8");
    String userId = String.valueOf(request.getSession(true)
        .getAttribute("userId"));
    String domainId = String.valueOf(request.getSession(true)
        .getAttribute("domainId"));
    (new NotePaperBD()).add("1", content, userId, domainId);
    return notePaperList(mapping, form, request, response);
  }
}
