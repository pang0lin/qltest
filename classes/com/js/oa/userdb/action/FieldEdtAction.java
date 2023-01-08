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

public class FieldEdtAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    FieldEdtActionForm rf = (FieldEdtActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String fieldname = rf.getFieldname();
    String fielddesname = rf.getFielddesname();
    String fieldcode = rf.getFieldcode();
    String fieldnull = rf.getFieldnull();
    String fielddefault = rf.getFielddefault();
    String fielddes = rf.getFielddes();
    String fieldindex = rf.getFieldindex();
    String fieldupdata = rf.getFieldupdata();
    String fieldlen = rf.getFieldlen();
    String tableid = rf.getTableid();
    String queryField = req.getParameter("queryField");
    String totField = req.getParameter("totField");
    String tableDesName = bd.getTableDesName(tableid);
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    try {
      int success = bd.updateField(fieldname, fieldnull, fielddefault, fielddes, fieldindex, fieldupdata, fielddesname, fieldcode, fieldlen, tableid, req.getParameter("fieldonly"), queryField, totField);
      if (success == 1) {
        forward = mapping.findForward("quit");
      } else if (success == -1) {
        forward = mapping.findForward("rename");
      } else {
        forward = mapping.findForward("failurecontinue");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      forward = mapping.findForward("failure");
    } 
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", startDate, endDate, "2", tableDesName, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
