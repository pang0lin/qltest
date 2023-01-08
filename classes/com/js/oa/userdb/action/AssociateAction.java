package com.js.oa.userdb.action;

import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AssociateAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    AssociateActionForm rf = (AssociateActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String tablename = rf.getTablename();
    String operate = req.getParameter("operate");
    String tableid = rf.getTableid();
    String tableDesName = bd.getTableDesName(tableid);
    String[] id = req.getParameterValues("id");
    String limitid = rf.getLimitid();
    String domainid = rf.getDomainid();
    if (domainid == null || domainid.equals("null") || domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : req.getSession(true).getAttribute("domainId").toString(); 
    if (operate == null || "null".equals("operate") || operate.length() < 1)
      operate = "list"; 
    if (operate != null && operate.equals("list"))
      try {
        String[][] list = bd.getAssociateInfo(tableid, domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("pageParameters", "tableid");
        req.setAttribute("associatelist", list);
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("edit"))
      try {
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
        forward = mapping.findForward("edit");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("add"))
      try {
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
        forward = mapping.findForward("add");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("signledelete")) {
      String tString = "关联 ";
      if (req.getParameter("assname") != null)
        tString = String.valueOf(tString) + req.getParameter("assname").toString(); 
      associatelog(req, moduleCode, "", "3", tString);
      try {
        bd.delSignleAssociate(limitid);
        String[][] list = bd.getAssociateInfo(tableid, domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("associatelist", list);
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } 
    if (operate != null && operate.equals("batchdelete"))
      try {
        bd.batchDelAssociate(id);
        String[][] list = bd.getAssociateInfo(tableid, domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("associatelist", list);
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    Date endDate = new Date();
    if (operate.equals("singledelete") || operate.equals("batchdelete"))
      associatelog(req, moduleCode, "", "2", tableDesName); 
    return forward;
  }
  
  public void associatelog(HttpServletRequest httpServletRequest, String moduleCode, String moduleName, String oprType, String oprContent) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    Date endDate = new Date();
    HttpSession sess = httpServletRequest.getSession(true);
    logBD.log(sess.getAttribute("userId").toString(), sess.getAttribute("userName").toString(), 
        sess.getAttribute("orgName").toString(), moduleCode, moduleName, startDate, endDate, 
        oprType, oprContent, httpServletRequest.getRemoteAddr(), sess.getAttribute("domainId").toString());
  }
}
