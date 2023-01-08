package com.js.oa.userdb.action;

import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.oa.userdb.util.DbOpt;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TableAddAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    TableAddActionForm rf = (TableAddActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    String mark = (req.getParameter("tablename2") == null) ? "" : req.getParameter("tablename2").toString();
    String tablename = String.valueOf(mark) + "_" + rf.getTablename();
    String tablecode = rf.getTablecode();
    String tabledesname = rf.getTabledesname();
    String tablefilepath = "UpLoad/" + rf.getTablefilepath();
    String tableowner = rf.getTableowner();
    String operate = rf.getOperate();
    String modelid = rf.getModelid();
    String domainid = rf.getDomainid();
    if (domainid == null || domainid.equals("null") || domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : req.getSession(true).getAttribute("domainId").toString(); 
    String orgId = req.getSession(true).getAttribute("orgId").toString();
    String empId = req.getSession(true).getAttribute("empId").toString();
    if (operate != null && operate.equals("continue"))
      try {
        int success = bd.addTable(tablecode, tabledesname, tablename, modelid, tablefilepath, tableowner, domainid, empId, orgId);
        if (success == -1) {
          forward = mapping.findForward("rename");
        } else if (success == 0) {
          forward = mapping.findForward("failurecontinue");
        } else {
          forward = mapping.findForward("continue");
        } 
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("quit"))
      try {
        int success = bd.addTable(tablecode, tabledesname, tablename, modelid, tablefilepath, tableowner, domainid, empId, orgId);
        if (success == -1) {
          forward = mapping.findForward("rename");
        } else if (success == 0) {
          forward = mapping.findForward("failurecontinue");
        } else {
          forward = mapping.findForward("quit");
        } 
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("out"))
      try {
        int success = bd.addTable(tablecode, tabledesname, tablename, modelid, tablefilepath, tableowner, domainid, empId, orgId);
        DbOpt dbopt = new DbOpt();
        if (success == -1) {
          forward = mapping.findForward("outrename");
        } else if (success == 0) {
          forward = mapping.findForward("failurecontinue");
        } else {
          forward = mapping.findForward("out");
        } 
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", startDate, endDate, "1", tabledesname, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
