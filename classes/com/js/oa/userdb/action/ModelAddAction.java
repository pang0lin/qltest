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

public class ModelAddAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    ModelAddActionForm rf = (ModelAddActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_class";
    String code = rf.getCode();
    String name = rf.getName();
    String remark = rf.getRemark();
    String user = rf.getUser();
    String operate = rf.getOperate();
    String domainid = rf.getDomainid();
    if (domainid == null || domainid.equals("null") || domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : req.getSession(true).getAttribute("domainId").toString(); 
    if (operate != null && operate.equals("continue")) {
      try {
        int success = bd.addModel(code, name, remark, user, domainid);
        if (success == -1)
          forward = mapping.findForward("rename"); 
        if (success < 1 && success != -1)
          forward = mapping.findForward("fail"); 
        if (success > 0)
          forward = mapping.findForward("continue"); 
      } catch (Exception e) {
        e.printStackTrace();
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("quit")) {
      try {
        int success = bd.addModel(code, name, remark, user, domainid);
        if (success == -1)
          forward = mapping.findForward("rename"); 
        if (success < 1 && success != -1)
          forward = mapping.findForward("fail"); 
        if (success > 0) {
          String[][] list = (String[][])null;
          list = bd.getModelInfo(domainid);
          req.setAttribute("modellist", list);
          forward = mapping.findForward("quit");
        } 
      } catch (Exception e) {
        e.printStackTrace();
        forward = mapping.findForward("failure");
      } 
    } else {
      try {
        String[][] list = (String[][])null;
        list = bd.getModelInfo(domainid);
        req.setAttribute("modellist", list);
        forward = mapping.findForward("quit");
      } catch (Exception e) {
        e.printStackTrace();
        forward = mapping.findForward("failure");
      } 
    } 
    Date endDate = new Date();
    logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", startDate, endDate, "1", name, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
