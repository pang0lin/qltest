package com.js.oa.userdb.action;

import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    ShowActionForm rf = (ShowActionForm)form;
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    String tableid = req.getParameter("tableid");
    String operate = rf.getOperate();
    String fieldname = rf.getFieldname();
    String fieldlist = rf.getFieldlist();
    String fieldshow = rf.getFieldshow();
    String fieldwidth = rf.getFieldwidth();
    String fieldvalue = rf.getFieldvalue();
    String fieldtype = rf.getFieldtype();
    String fielddesname = rf.getFielddesname();
    String fieldId = req.getParameter("fieldId");
    String[] id = req.getParameterValues("id");
    HttpSession session = req.getSession(true);
    Object domainid = session.getAttribute("domainId");
    String userId = session.getAttribute("userId").toString();
    String userName = session.getAttribute("userName").toString();
    String orgName = session.getAttribute("orgName").toString();
    String domainId = session.getAttribute("domainId").toString();
    String ip = req.getRemoteAddr();
    Date now = new Date();
    String tableDesName = bd.getTableDesName(tableid);
    if (operate == null || "null".equals("operate") || operate.length() < 1)
      operate = "list"; 
    if (operate != null && operate.equals("list"))
      try {
        String[][] list = bd.getShow(tableid, domainid.toString());
        req.setCharacterEncoding("GB2312");
        req.setAttribute("showlist", list);
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
    if (operate != null && operate.equals("queryField"))
      try {
        String[][] list = bd.getQueryField(tableid);
        req.setCharacterEncoding("GBK");
        if (list == null)
          list = new String[0][2]; 
        req.setAttribute("querylist", list);
        String[][] list1 = bd.getMainFieldInfo(tableid);
        if (list1 == null)
          list1 = new String[0][3]; 
        req.setAttribute("fieldlist", list1);
        forward = mapping.findForward("queryField");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("totField"))
      try {
        int i = 0;
        String totField = bd.getTableTotField(tableid);
        req.setAttribute("totField", totField);
        String[][] list = bd.getTotField(tableid);
        req.setCharacterEncoding("GBK");
        if (list == null)
          list = new String[0][2]; 
        req.setAttribute("totlist", list);
        forward = mapping.findForward("totField");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("saveTotField"))
      try {
        String[] totField = req.getParameterValues("totField");
        if (totField != null) {
          StringBuffer sb = new StringBuffer();
          if (totField.length == 1) {
            sb.append(totField[0]);
          } else {
            for (int i = 0; i < totField.length; i++)
              sb.append(String.valueOf(totField[i]) + ","); 
          } 
          bd.saveTotField(tableid, sb.toString());
          req.setAttribute("totField", sb.toString());
        } else {
          bd.saveTotField(tableid, "");
          req.setAttribute("totField", "");
        } 
        String[][] list = bd.getTotField(tableid);
        req.setCharacterEncoding("GBK");
        if (list == null)
          list = new String[0][2]; 
        req.setAttribute("totlist", list);
        forward = mapping.findForward("totField");
        logBD.log(userId, userName, orgName, moduleCode, "", now, now, "2", tableDesName, ip, domainId);
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("associatetable")) {
      List<List> list = new ArrayList();
      List allList = new ArrayList();
      List oldList = new ArrayList();
      try {
        list = bd.getAssociateTable(tableid, domainid.toString());
        if (list != null && list.size() >= 2) {
          allList = list.get(0);
          oldList = list.get(1);
        } 
        req.setAttribute("querylist", oldList);
        req.setAttribute("fieldlist", allList);
        forward = mapping.findForward("associatetable");
        logBD.log(userId, userName, orgName, moduleCode, "", now, now, "2", tableDesName, ip, domainId);
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } 
    if (operate != null && operate.equals("batchupdate"))
      try {
        Boolean success = bd.batchUpdateShow(req.getParameterValues("id"), tableid);
        String[][] list = bd.getShow(tableid, domainid.toString());
        req.setCharacterEncoding("GB2312");
        req.setAttribute("showlist", list);
        forward = mapping.findForward("continue");
        logBD.log(userId, userName, orgName, moduleCode, "", now, now, "2", tableDesName, ip, domainId);
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("ALLUPDATE"))
      try {
        req.setAttribute("success", bd.batchUpdateShow(req.getParameterValues("id"), tableid));
        String[] fieldName = req.getParameterValues("fieldName");
        String[] fieldSeq = req.getParameterValues("fieldSeq");
        String[] fieldWidth = req.getParameterValues("fieldWidth");
        String[] fieldShow = req.getParameterValues("fieldShow");
        String[] fieldValue = req.getParameterValues("fieldvalue");
        if (fieldName != null)
          for (int i = 0; i < fieldName.length; i++)
            bd.setShow("", (
                fieldWidth[i] == null || fieldWidth[i].toUpperCase().equals("NULL") || 
                fieldWidth[i].length() < 1) ? "0" : fieldWidth[i], fieldShow[i], fieldValue[i], 
                fieldName[i], (
                fieldSeq[i] == null || fieldSeq[i].toUpperCase().equals("NULL") || 
                fieldSeq[i].length() < 1) ? "0" : fieldSeq[i], tableid);  
        String[][] list = bd.getShow(tableid, domainid.toString());
        req.setAttribute("showlist", list);
        forward = mapping.findForward("continue");
        logBD.log(userId, userName, orgName, moduleCode, "", now, now, "2", tableDesName, ip, domainId);
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("addQueryFieldContinue"))
      try {
        if (fieldId.length() > 1)
          fieldId = fieldId.substring(0, fieldId.length() - 1); 
        Boolean success = bd.addQueryField(fieldId, "", tableid);
        if (success.equals(Boolean.TRUE)) {
          req.setAttribute("success", "1");
        } else {
          req.setAttribute("success", "0");
        } 
        String[][] list = bd.getQueryField(tableid);
        req.setCharacterEncoding("GBK");
        if (list == null)
          list = new String[0][2]; 
        req.setAttribute("querylist", list);
        String[][] list1 = bd.getMainFieldInfo(tableid);
        if (list1 == null)
          list1 = new String[0][3]; 
        req.setAttribute("fieldlist", list1);
        if (success.equals(Boolean.TRUE))
          req.setAttribute("stat", "0"); 
        forward = mapping.findForward("queryField");
        logBD.log(userId, userName, orgName, moduleCode, "", now, now, "2", tableDesName, ip, domainId);
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    if (operate != null && operate.equals("addAssociateTableContinue"))
      try {
        if (fieldId.length() > 1)
          fieldId = fieldId.substring(0, fieldId.length() - 1); 
        Boolean success = bd.addAssociateTable(fieldId, "", tableid, domainid.toString());
        if (success.equals(Boolean.TRUE)) {
          req.setAttribute("success", "1");
        } else {
          req.setAttribute("success", "0");
        } 
        List<List> list = new ArrayList();
        List allList = new ArrayList();
        List oldList = new ArrayList();
        req.setCharacterEncoding("GBK");
        list = bd.getAssociateTable(tableid, domainid.toString());
        if (list != null && list.size() >= 2) {
          allList = list.get(0);
          oldList = list.get(1);
        } 
        req.setAttribute("querylist", oldList);
        req.setAttribute("fieldlist", allList);
        if (success.equals(Boolean.TRUE))
          req.setAttribute("stat", "0"); 
        forward = mapping.findForward("associatetable");
        logBD.log(userId, userName, orgName, moduleCode, "", now, now, "2", tableDesName, ip, domainId);
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      }  
    return forward;
  }
}
