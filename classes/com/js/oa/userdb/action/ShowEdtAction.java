package com.js.oa.userdb.action;

import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowEdtAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    ShowEdtActionForm rf = (ShowEdtActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "oa_systemmanager";
    String moduleName = "自定义数据库";
    String oprType = "2";
    String oprContent = "修改显示设置";
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String fieldname = rf.getFieldname();
    String fieldlist = rf.getFieldlist();
    String fieldshow = rf.getFieldshow();
    String fieldwidth = rf.getFieldwidth();
    String fieldvalue = rf.getFieldvalue();
    String fieldseq = req.getParameter("fieldseq");
    try {
      bd.setShow(fieldlist, fieldwidth, fieldshow, fieldvalue, fieldname, fieldseq, req.getParameter("tableid"));
      forward = mapping.findForward("quit");
    } catch (Exception e) {
      forward = mapping.findForward("failure");
    } 
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, oprType, oprContent, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
