package com.js.oa.userdb.action;

import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.service.CustomDatabaseBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.sql.Page;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class TableAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) {
    TableActionForm rf = (TableActionForm)form;
    LogBD logBD = new LogBD();
    Date startDate = new Date();
    String moduleCode = "system_customdb_table";
    String moduleName = "自定义数据库";
    HttpSession session = req.getSession();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String operate = rf.getOperate();
    String sname = rf.getSname();
    String tableid = rf.getTableid();
    String tablename = rf.getTablename();
    String[] id = rf.getId();
    String domainid = rf.getDomainid();
    if (domainid == null || domainid.equals("null") || domainid.length() < 1)
      domainid = (req.getSession(true).getAttribute("domainId") == null) ? "0" : 
        req.getSession(true).getAttribute("domainId").toString(); 
    if (operate == null || operate.length() < 1)
      operate = "list"; 
    ActionForward forward = new ActionForward();
    CustomDatabaseBD bd = new CustomDatabaseBD();
    if (operate != null && operate.equals("list")) {
      try {
        String[][] modelList = bd.getModelInfo(domainid);
        req.setAttribute("modellist", (modelList == null) ? new String[0][15] : modelList);
        tableList(req, "", "");
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("batchdelete")) {
      try {
        moduleName = bd.getTableName(id);
        bd.batchDeleteTable(id);
        String[][] modelList = bd.getModelInfo(domainid);
        req.setAttribute("modellist", (modelList == null) ? new String[0][15] : modelList);
        tableList(req, "", "");
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("signledelete")) {
      try {
        moduleName = bd.getTableName(new String[] { tablename });
        bd.delSignleTable(tablename);
        String[][] modelList = bd.getModelInfo(domainid);
        req.setAttribute("modellist", (modelList == null) ? new String[0][15] : modelList);
        tableList(req, "", "");
        req.setAttribute("reload", "yes");
        forward = mapping.findForward("continue");
      } catch (Exception e) {
        e.printStackTrace();
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("edit")) {
      try {
        String[][] list = (String[][])null;
        list = bd.getFieldInfo(tableid, domainid);
        if (list == null)
          list = new String[0][15]; 
        req.setAttribute("recordCount", String.valueOf(list.length));
        req.setAttribute("maxPageItems", "15");
        req.setAttribute("fieldlist", list);
        rf.setOperate("list");
        forward = mapping.findForward("edit");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("field")) {
      try {
        forward = mapping.findForward("field");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } else if (operate != null && operate.equals("search")) {
      try {
        String[][] list = (String[][])null;
        String model = req.getParameter("model");
        req.setAttribute("ssname", sname);
        req.setAttribute("pageParameters", "operate,sname,model,mark,desc");
        tableList(req, sname, model);
        String[][] modelList = bd.getModelInfo(domainid);
        req.setAttribute("modellist", (modelList == null) ? new String[0][15] : modelList);
        forward = mapping.findForward("search");
      } catch (Exception e) {
        forward = mapping.findForward("failure");
      } 
    } 
    Date endDate = new Date();
    if (operate.equals("signledelete") || operate.equals("batchdelete"))
      logBD.log(req.getSession(true).getAttribute("userId").toString(), 
          req.getSession(true).getAttribute("userName").toString(), 
          req.getSession(true).getAttribute("orgName").toString(), moduleCode, "", startDate, 
          endDate, "3", moduleName, req.getRemoteAddr(), domainid); 
    operate = req.getParameter("operate");
    if ("copyData".equals(operate)) {
      String tableId = (req.getParameter("tableId") == null) ? "" : req.getParameter("tableId");
      bd.pasteTable(tableId, domainid, userId, orgId);
      forward = mapping.findForward("copyData");
    } 
    return forward;
  }
  
  private void tableList(HttpServletRequest request, String sname, String model) {
    HttpSession session = request.getSession();
    String domainId = session.getAttribute("domainId").toString();
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String mark = request.getParameter("mark");
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      if ("".equals(request.getParameter("pager.offset")) || "null".equals(request.getParameter("pager.offset"))) {
        offset = Integer.parseInt("0");
      } else {
        offset = Integer.parseInt(request.getParameter("pager.offset"));
      }  
    int currentPage = offset / pageSize + 1;
    String para = " a.table_id,a.table_desname,a.table_name,b.model_name,a.table_owner,a.table_date,a.table_code,a.table_filepath ";
    String from = " ttable a, tModel b ";
    String where = " where a.table_model=b.model_id and a.DOMAIN_ID=" + domainId;
    if (!"".equals(sname) && !"null".equals(sname))
      where = String.valueOf(where) + " and a.table_desname like '%" + sname + "%'"; 
    if (!"".equals(model) && !"null".equals(model))
      where = String.valueOf(where) + " and b.model_id=" + model; 
    ManagerService managerBD = new ManagerService();
    String managerWhere = managerBD.getRightFinalWhere(userId, orgId, "02*02*02", "a.createdOrg", "a.createdEmp");
    where = String.valueOf(where) + " and " + managerWhere;
    String desc = request.getParameter("desc");
    request.setAttribute("desc", desc);
    String sqlOrderDisc = "";
    if ("2".equals(desc)) {
      sqlOrderDisc = " desc";
    } else {
      sqlOrderDisc = " asc";
    } 
    if (mark != null && "1".equals(mark)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        where = String.valueOf(where) + " order by convert(a.table_desname using gbk) " + sqlOrderDisc;
      } else {
        where = String.valueOf(where) + " ORDER BY NLSSORT(a.table_desname, 'NLS_SORT=SCHINESE_PINYIN_M') " + sqlOrderDisc;
      } 
    } else {
      where = String.valueOf(where) + " order by a.table_id desc";
    } 
    System.out.println(where);
    Page page = new Page(para, from, where);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    int recordCount = page.getRecordCount();
    if (offset > 0 && offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      recordCount = page.getRecordCount();
      request.setAttribute("pager.offset", String.valueOf(offset));
      request.setAttribute("pager.realCurrent", String.valueOf(currentPage));
    } 
    request.setAttribute("tableList", list);
    request.setAttribute("maxPageItems", String.valueOf(pageSize));
    request.setAttribute("pageParameters", "operate,sname,model,mark,desc");
    request.setAttribute("recordCount", String.valueOf(page.getRecordCount()));
  }
}
