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

public class ModelEdtAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    ModelEdtActionForm rf = (ModelEdtActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_class";
    String modelcode = rf.getModelcode();
    String modelname = rf.getModelname();
    String modeldis = rf.getModeldis();
    String modelid = rf.getModelid();
    try {
      bd.updateModel(modelid, modelname, modelcode, modeldis);
      forward = mapping.findForward("quit");
    } catch (Exception e) {
      e.printStackTrace();
      forward = mapping.findForward("failure");
    } 
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), 
        req.getSession(true).getAttribute("userName").toString(), 
        req.getSession(true).getAttribute("orgName").toString(), 
        moduleCode, "", startDate, endDate, "2", 
        modelname, req.getRemoteAddr(), 
        req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
