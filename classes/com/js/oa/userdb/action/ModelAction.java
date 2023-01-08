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

public class ModelAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    ModelActionForm rf = (ModelActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_class";
    String moduleName = "自定义数据库";
    String oprType = "";
    String oprContent = "";
    ActionForward forward = new ActionForward();
    String operate = rf.getOperate();
    String[] id = rf.getId();
    String modelid = rf.getModelid();
    String domainid = rf.getDomainid();
    if (domainid == null || domainid.equals("null") || domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : 
        req.getSession(true).getAttribute("domainId").toString(); 
    if (operate == null || operate.length() < 1)
      operate = "continue"; 
    CustomDatabaseBD bd = new CustomDatabaseBD();
    if (operate != null && operate.equals("continue")) {
      try {
        String[][] list = (String[][])null;
        list = bd.getModelInfo(domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("modellist", list);
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("delete")) {
      try {
        int success = 0;
        moduleName = bd.getModuleName(id);
        success = bd.batchDelete(id);
        String[][] list = (String[][])null;
        list = bd.getModelInfo(domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("modellist", list);
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("deletesignle")) {
      try {
        moduleName = bd.getModuleName(new String[] { modelid });
        bd.delSingleModel(modelid);
        String[][] list = (String[][])null;
        list = bd.getModelInfo(domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("modellist", list);
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("edit")) {
      try {
        forward = mapping.findForward("edit");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } 
    Date endDate = new Date();
    if (operate.equals("deletesignle") || operate.equals("delete"))
      logBD.log(req.getSession(true).getAttribute("userId").toString(), 
          req.getSession(true).getAttribute("userName").toString(), 
          req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", startDate, 
          endDate, "3", moduleName, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString()); 
    return forward;
  }
}
