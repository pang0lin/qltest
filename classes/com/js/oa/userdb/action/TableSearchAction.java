package com.js.oa.userdb.action;

import com.js.oa.userdb.service.CustomDatabaseBD;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TableSearchAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    TableSearchActionForm rf = (TableSearchActionForm)form;
    String sname = rf.getSname();
    Object domainid = req.getSession(true).getAttribute("domainId");
    if (domainid == null || domainid.equals("null"))
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : req.getSession(true).getAttribute("domainId").toString(); 
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    try {
      String[][] list = (String[][])null;
      list = bd.getTableInfo(req.getParameter("model"), sname, domainid.toString());
      if (list == null)
        list = new String[0][15]; 
      req.setAttribute("tablelist", list);
      req.setAttribute("recordCount", String.valueOf(list.length));
      req.setAttribute("maxPageItems", "15");
      req.setAttribute("ssname", sname);
      req.setAttribute("tablelist", list);
      forward = mapping.findForward("search");
    } catch (Exception e) {
      forward = mapping.findForward("failure");
    } 
    return forward;
  }
}
