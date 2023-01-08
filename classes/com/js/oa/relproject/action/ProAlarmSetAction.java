package com.js.oa.relproject.action;

import com.js.oa.relproject.bean.ProAlarmSetBean;
import com.js.oa.relproject.po.ProAlarmSet;
import com.js.util.page.Page;
import com.js.util.util.FillBean;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProAlarmSetAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm actionform, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    String action = request.getParameter("action");
    ProAlarmSetActionForm form = (ProAlarmSetActionForm)actionform;
    ProAlarmSetBean bean = new ProAlarmSetBean();
    if (action.equals("alarmSetList")) {
      alarmSetList(request);
      return mapping.findForward(action);
    } 
    if (action.equals("alarmSetForAdd")) {
      form.setAlarmEnable("1");
      return mapping.findForward(action);
    } 
    if (action.equals("alarmSetAdd")) {
      ProAlarmSet po = (ProAlarmSet)FillBean.transformOTO(form, ProAlarmSet.class);
      String flag = null;
      Long id = bean.saveAlarmSet(po);
      if (id != null) {
        flag = "addsuccess";
      } else {
        flag = "adderror";
      } 
      request.setAttribute("flag", flag);
      return mapping.findForward("close");
    } 
    if (action.equals("alarmSetForModi")) {
      String id = request.getParameter("id");
      ProAlarmSet po = bean.loadAlarmSet(Long.valueOf(id));
      POtoForm(form, po);
      request.setAttribute("alarmColor", po.getAlarmColor());
      return mapping.findForward(action);
    } 
    if (action.equals("alarmSetModi")) {
      ProAlarmSet po = (ProAlarmSet)FillBean.transformOTO(form, ProAlarmSet.class);
      boolean b = bean.updateAlarmSet(po);
      String flag = null;
      if (b) {
        flag = "updatesuccess";
      } else {
        flag = "updateerror";
      } 
      request.setAttribute("flag", flag);
      return mapping.findForward("close");
    } 
    if (action.equals("alarmSetDel")) {
      String ids = request.getParameter("ids");
      boolean b = false;
      if (ids != null && !"".equals(ids))
        try {
          b = bean.deleteAlarmSet(ids);
        } catch (Exception e) {
          e.printStackTrace();
        }  
      String flag = null;
      if (b) {
        flag = "deletesuccess";
      } else {
        flag = "deleteerror";
      } 
      request.setAttribute("flag", flag);
      alarmSetList(request);
      return mapping.findForward("alarmSetList");
    } 
    return mapping.findForward(action);
  }
  
  public void alarmSetList(HttpServletRequest request) {
    String viewSql = "p.alarmId,p.alarmName,p.alarmDays,p.alarmColor,p.alarmEnable";
    String fromSql = "com.js.oa.relproject.po.ProAlarmSet p";
    String whereSql = "where 1=1";
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
  
  public void POtoForm(ProAlarmSetActionForm from, ProAlarmSet po) {
    from.setAlarmId(po.getAlarmId());
    from.setAlarmName(po.getAlarmName());
    from.setAlarmDays(po.getAlarmDays());
    from.setAlarmColor(po.getAlarmColor());
    from.setAlarmEnable(po.getAlarmEnable());
  }
}
