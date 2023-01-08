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

public class AssociateAddAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    AssociateAddActionForm rf = (AssociateAddActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String tablename = rf.getTablename();
    String tableid = rf.getTableid();
    String tableDesName = bd.getTableDesName(tableid);
    String limitfield = rf.getLimitfield();
    String limitprytable = rf.getLimitprytable();
    String limitpryfield = rf.getLimitpryfield();
    String user = rf.getUser();
    String operate = rf.getOperate();
    String domainid = rf.getDomainid();
    String name = req.getParameter("name");
    if (domainid == null || domainid.equals("null") || domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : req.getSession(true).getAttribute("domainId").toString(); 
    if (operate == null || "null".equals("operate") || operate.length() < 1)
      operate = "exit"; 
    if (operate != null && operate.equals("continue")) {
      String[][] list = (String[][])null;
      String[][] list1 = (String[][])null;
      String[][] list2 = (String[][])null;
      list = bd.getMainFieldInfo(tableid);
      if (list == null)
        list = new String[0][15]; 
      req.setAttribute("mainFieldList", list);
      list1 = bd.getPryTableInfo(domainid);
      if (list1 == null)
        list1 = new String[0][15]; 
      req.setAttribute("pryTableList", list1);
      list2 = bd.getPryFieldInfo(domainid);
      if (list2 == null)
        list2 = new String[0][15]; 
      req.setAttribute("pryFieldList", list2);
      try {
        int success = bd.addAssociate(name, tableid, limitfield, limitprytable, limitpryfield, user, domainid);
        if (success > 0) {
          forward = mapping.findForward("continue");
        } else {
          return new ActionForward("/AssociateAction.do?operate=add&tableid=" + 
              tableid + "&tablename=" + tablename + "&name=" + name + "&state=1");
        } 
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } 
    if (operate != null && operate.equals("quit"))
      try {
        int success = bd.addAssociate(name, tableid, limitfield, limitprytable, limitpryfield, user, domainid);
        if (success > 0) {
          forward = mapping.findForward("quit");
        } else {
          return new ActionForward("/AssociateAction.do?operate=add&tableid=" + tableid + "&tablename=" + tablename + "&name=" + name + "&state=1");
        } 
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("exit"))
      try {
        forward = mapping.findForward("quit");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    Date endDate = new Date();
    if (operate.equals("continue") || operate.equals("quit")) {
      String assname = "";
      if (req.getParameter("assname") != null)
        assname = "表增加关联子表：" + req.getParameter("assname").toString(); 
      logBD.log(req.getSession(true).getAttribute("userId").toString(), req.getSession(true).getAttribute("userName").toString(), req.getSession(true).getAttribute("orgName").toString(), 
          moduleCode, "", startDate, endDate, "2", String.valueOf(tableDesName) + assname, req.getRemoteAddr(), req.getSession(true).getAttribute("domainId").toString());
    } 
    return forward;
  }
}
