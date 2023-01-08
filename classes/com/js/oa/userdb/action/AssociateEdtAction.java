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

public class AssociateEdtAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    AssociateEdtActionForm rf = (AssociateEdtActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String limitid = rf.getLimitid();
    String limitpryfield = rf.getLimitpryfield();
    String limitprytable = rf.getLimitprytable();
    String tableid = req.getParameter("tableid");
    String tableDesName = bd.getTableDesName(tableid);
    try {
      if (bd.updateAssociate(limitid, limitprytable, limitpryfield, req.getParameter("name")) > 0) {
        forward = mapping.findForward("quit");
      } else {
        return new ActionForward("/AssociateAction.do?operate=edit&lid=" + req.getParameter("limitid") + "&lfname=" + req.getParameter("name") + "&prytid=" + req.getParameter("prytid") + "&pryfid=" + req.getParameter("pryfid") + "&tableid=" + req.getParameter("tableid") + "&tablename=" + req.getParameter("tablename") + "&state=1");
      } 
    } catch (Exception e) {
      forward = mapping.findForward("failure");
    } 
    String ass = "";
    if (req.getParameter("assname") != null)
      ass = " 表修改关联子表：" + req.getParameter("assname").toString(); 
    logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", 
        startDate, new Date(), "2", String.valueOf(tableDesName) + ass, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    return forward;
  }
}
