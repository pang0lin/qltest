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

public class TableEdtAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    TableEdtActionForm rf = (TableEdtActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    String tablename = req.getParameter("fullName");
    String tablecode = rf.getTablecode();
    String tabledesname = rf.getTabledesname();
    String tablefilepath = "UpLoad/";
    String tablemodel = rf.getTablemodel();
    String domainId = (req.getSession(true).getAttribute("domainId") == null) ? "0" : req.getSession(true).getAttribute("domainId").toString();
    try {
      int success = bd.updateTable(tablename, tablecode, tabledesname, tablemodel, tablefilepath, domainId);
      if (success == 1) {
        forward = mapping.findForward("quit");
      } else if (success == -1) {
        forward = mapping.findForward("rename");
      } else {
        forward = mapping.findForward("failurecontinue");
      } 
    } catch (Exception e) {
      forward = mapping.findForward("failure");
    } 
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", startDate, endDate, "2", tabledesname, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
