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

public class FieldAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    FieldActionForm rf = (FieldActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String operate = rf.getOperate();
    String tableid = rf.getTableid();
    String tableDesName = bd.getTableDesName(tableid);
    String fieldname = rf.getFieldname();
    String[] id = req.getParameterValues("id");
    String tablename = rf.getTablename();
    Object domainid = req.getSession(true).getAttribute("domainId");
    if (domainid == null || domainid.equals("null"))
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : 
        req.getSession(true).getAttribute("domainId").toString(); 
    if (operate == null || operate.length() < 1)
      operate = "list"; 
    if (operate.equals("list"))
      try {
        String[][] list = bd.getFieldInfo(tableid, domainid.toString());
        if (list == null)
          list = new String[0][25]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("pageParameters", "tableid");
        req.setAttribute("fieldlist", list);
        req.setAttribute("pageParameters", "tableid,tablename");
        req.setAttribute("totField", bd.getTableTotField(tableid));
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("edit"))
      try {
        forward = mapping.findForward("edit");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("signledelete"))
      try {
        bd.delSignleField(tablename, fieldname);
        String[][] list = bd.getFieldInfo(tableid, domainid.toString());
        if (list == null)
          list = new String[0][25]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("fieldlist", list);
        req.setAttribute("pageParameters", "tableid,tablename");
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("batchdelete"))
      try {
        bd.batchDeleteField(tablename, id);
        String[][] list = bd.getFieldInfo(tableid, domainid.toString());
        if (list == null)
          list = new String[0][25]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("fieldlist", list);
        req.setAttribute("pageParameters", "tableid,tablename");
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("updateshow"))
      try {
        bd.updateFieldHide(id, tableid);
        String[][] list = bd.getFieldInfo(tableid, domainid.toString());
        if (list == null)
          list = new String[0][25]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("fieldlist", list);
        req.setAttribute("pageParameters", "tableid,tablename");
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    Date endDate = new Date();
    if (operate.equals("singledelete") || operate.equals("batchdelete"))
      logBD.log(req.getSession(true).getAttribute("userId").toString(), 
          req.getSession(true).getAttribute("userName").toString(), 
          req.getSession(true).getAttribute("orgName").toString(), 
          moduleCode, "", startDate, endDate, "2", tableDesName, req
          .getRemoteAddr(), req.getSession(true)
          .getAttribute("domainId").toString()); 
    return forward;
  }
}
