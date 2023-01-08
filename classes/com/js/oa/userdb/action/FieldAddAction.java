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

public class FieldAddAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    FieldAddActionForm rf = (FieldAddActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String tableid = rf.getTableid();
    String tableDesName = bd.getTableDesName(tableid);
    String tablename = rf.getTablename();
    String modelid = rf.getModelid();
    String fieldcode = rf.getFieldcode();
    String fielddesname = rf.getFielddesname();
    String fieldname = rf.getFieldname();
    String[] desname = fielddesname.split(";");
    String[] sysname = fieldname.split(";");
    String fieldtype = rf.getFieldtype();
    String typename = rf.getTypename();
    String fieldlen = rf.getFieldlen();
    String fieldnull = rf.getFieldnull();
    String fieldindex = rf.getFieldindex();
    String fieldupdata = rf.getFieldupdata();
    String operate = rf.getOperate();
    String fielddefault = rf.getFielddefault();
    String fielddes = rf.getFielddes();
    String fieldonly = rf.getFieldonly();
    String user = rf.getUser();
    String domainid = rf.getDomainid();
    String queryField = req.getParameter("queryField");
    String totField = req.getParameter("totField");
    String mark = (req.getParameter("fieldname2") == null) ? "" : req.getParameter("fieldname2").toString();
    if (domainid == null || domainid.equals("null") || 
      domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? 
        "0" : 
        req.getSession(true).getAttribute("domainId").toString(); 
    String desnames = "";
    String sysnames = "";
    for (int di = 0; di < desname.length - 1; di++)
      desnames = String.valueOf(desnames) + "'" + desname[di] + "' ,"; 
    desnames = String.valueOf(desnames) + "'" + desname[desname.length - 1] + "'";
    for (int si = 0; si < sysname.length - 1; si++)
      sysnames = String.valueOf(sysnames) + "'" + mark + "_" + sysname[si] + "' ,"; 
    sysnames = String.valueOf(sysnames) + "'" + mark + "_" + sysname[sysname.length - 1] + "'";
    String rsuss = bd.checkSame(desnames, sysnames, tableid);
    if (rsuss.equals("1")) {
      if (operate != null && operate.equals("continue")) {
        int result = 0;
        String errorname = "";
        forward = mapping.findForward("continue");
        try {
          for (int ii = 0; ii < desname.length; ii++) {
            fielddesname = desname[ii];
            fieldname = String.valueOf(mark) + "_" + sysname[ii];
            int success = bd.addField(fieldtype, tableid, tablename, 
                modelid, fielddesname, 
                fieldcode, fieldname, 
                typename, fieldlen, fieldnull, 
                fielddefault, fielddes, 
                fieldonly, fieldindex, 
                fieldupdata, user, queryField, totField, domainid, "");
            if (success != 1) {
              result++;
              errorname = String.valueOf(errorname) + fielddesname + ";";
            } 
          } 
          if (result != 0) {
            req.setAttribute("errorname", errorname);
            forward = mapping.findForward("failurecontinue");
          } 
        } catch (Exception e) {
          e.printStackTrace();
          forward = mapping.findForward("failure");
        } 
      } else if (operate != null && operate.equals("quit")) {
        int result = 0;
        String errorname = "";
        forward = mapping.findForward("quit");
        try {
          for (int ii = 0; ii < desname.length; ii++) {
            fielddesname = desname[ii];
            fieldname = String.valueOf(mark) + "_" + sysname[ii];
            int success = bd.addField(fieldtype, tableid, 
                tablename, modelid, fielddesname, fieldcode, 
                fieldname, typename, fieldlen, fieldnull, 
                fielddefault, fielddes, fieldonly, 
                fieldindex, fieldupdata, user, queryField, totField, domainid, "");
            if (success != 1) {
              result++;
              errorname = String.valueOf(errorname) + fielddesname + ";";
            } 
          } 
          if (result != 0) {
            req.setAttribute("errorname", errorname);
            forward = mapping.findForward("failurecontinue");
          } 
        } catch (Exception e) {
          e.printStackTrace();
          forward = mapping.findForward("failure");
        } 
      } else {
        try {
          forward = mapping.findForward("quit");
        } catch (Exception e) {
          e.printStackTrace();
          forward = mapping.findForward("failure");
        } 
      } 
    } else {
      forward = mapping.findForward("rename");
    } 
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), 
        req.getSession(true).getAttribute("userName").toString(), 
        req.getSession(true).getAttribute("orgName").toString(), 
        moduleCode, "", startDate, endDate, "2", 
        tableDesName, req.getRemoteAddr(), 
        req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
