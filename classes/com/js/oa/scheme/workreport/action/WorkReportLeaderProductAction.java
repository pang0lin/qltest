package com.js.oa.scheme.workreport.action;

import com.js.oa.scheme.workreport.service.WorkReportLeaderProductBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.organizationmanager.OrganizationBD;
import com.js.util.config.SysConfig;
import com.js.util.config.SystemCommon;
import com.js.util.page.simple.Page;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkReportLeaderProductAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    Long curUserId = new Long(request.getSession(true).getAttribute(
          "userId").toString());
    Long curOrgId = new Long(request.getSession(true).getAttribute("orgId")
        .toString());
    String curUserName = request.getSession(true).getAttribute("userName")
      .toString();
    String curOrgName = request.getSession(true).getAttribute("orgName")
      .toString();
    WorkReportLeaderActionForm workReportLeaderActionForm = 
      (WorkReportLeaderActionForm)actionForm;
    WorkReportLeaderProductBD bd = new WorkReportLeaderProductBD();
    String action = request.getParameter("action");
    if ("add".equals(action)) {
      String flag = request.getParameter("flag");
      if ("1".equals(flag)) {
        String reportId = bd.getReportId(request.getParameter("receiveRecordId"));
        bd.setReport(reportId, request.getParameter("reportName"), request.getParameter("previousReport"));
        action = "load";
      } else {
        bd.add(curUserName, 
            request.getParameter("receiveRecordId"), 
            (String)curUserId, 
            workReportLeaderActionForm.getPostilContent(), 
            workReportLeaderActionForm.getUsersId(), 
            workReportLeaderActionForm.getUsersName(), 
            domainId);
        workReportLeaderActionForm.reset(actionMapping, request);
        action = "load";
      } 
    } 
    if ("load".equals(action)) {
      Vector vec = bd.load(request.getParameter("sendRecordId"), 
          request.getParameter("receiveRecordId"), 
          request.getParameter("status"), domainId);
      request.setAttribute("listReport", vec.get(0));
      request.setAttribute("listPostil", vec.get(1));
      return actionMapping.findForward("modi");
    } 
    if ("delBatch".equals(action)) {
      bd.delBatch(request.getParameter("ids"));
      action = "list";
    } 
    if ("list".equals(action)) {
      list(request, domainId);
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view")))
        return actionMapping.findForward("view"); 
    } 
    return actionMapping.findForward("list");
  }
  
  public void list(HttpServletRequest request, Long domainId) {
    try {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      long userId = Long.parseLong(String.valueOf(request.getSession(true)
            .getAttribute("userId")));
      String orgId = request.getSession(true).getAttribute("orgId")
        .toString();
      String where = "1=1  ";
      String queryItem = request.getParameter("queryItem");
      String queryText = request.getParameter("queryText");
      String reportEmpId = request.getParameter("reportEmpId");
      String queryName = request.getParameter("queryName");
      String dataType = SysConfig.getDatabaseType();
      String orderBy = "";
      if (queryText != null && !"".equals(queryText))
        if ("oracle".equals(dataType)) {
          where = String.valueOf(where) + " and ( dbms_lob.instr(poo.previousReport,'" + 
            queryText + "',1,1)>0 ";
          where = String.valueOf(where) + " or dbms_lob.instr(poo.nextReport,'" + queryText + 
            "',1,1)>0 )";
        } else {
          where = String.valueOf(where) + " and poo.previousReport like '%" + queryText + "%'";
          where = String.valueOf(where) + " or poo.nextReport like '%" + queryText + "%'";
        }  
      if (queryName != null && !"".equals(queryName))
        if ("mysql".equals(dataType)) {
          where = String.valueOf(where) + " and '" + queryName + "' like concat('%',poo.reportEmpName,'%') ";
        } else {
          where = String.valueOf(where) + " and '" + queryName + "' like '%'||poo.reportEmpName||'%' ";
        }  
      if (queryItem != null && queryItem.equals("1"))
        if ("mysql".equals(dataType)) {
          where = String.valueOf(where) + " and ( poo.reportTime between '" + 
            request.getParameter("startDate") + " 00:00:00" + 
            "' and '" + 
            request.getParameter("endDate") + " 23:59:59" + 
            "'";
          where = String.valueOf(where) + " or  poo.reportTime between '" + 
            request.getParameter("endDate") + " 00:00:00" + 
            "' and '" + 
            request.getParameter("startDate") + " 23:59:59" + 
            "' )";
        } else {
          where = String.valueOf(where) + 
            " and ( poo.reportTime between JSDB.FN_STRTODATE('" + 
            request.getParameter("startDate") + " 00:00:00" + 
            "','L') and JSDB.FN_STRTODATE('" + 
            request.getParameter("endDate") + " 23:59:59" + 
            "','L')";
          where = String.valueOf(where) + 
            " or  poo.reportTime between JSDB.FN_STRTODATE('" + 
            request.getParameter("endDate") + " 00:00:00" + 
            "','L') and JSDB.FN_STRTODATE('" + 
            request.getParameter("startDate") + " 23:59:59" + 
            "','L') )";
        }  
      String reportOrgId = (request.getParameter("reportDepartId") == null) ? "" : request.getParameter("reportDepartId");
      if (!"".equals(reportOrgId))
        where = String.valueOf(where) + " and poo.reportOrgID in (" + reportOrgId.replaceAll("\\*\\*", ",").replaceAll("\\*", "") + ")"; 
      String reportType = request.getParameter("reportType");
      if (reportType != null && !"none".equals(reportType)) {
        where = String.valueOf(where) + " and poo.reportType=" + reportType;
      } else if (!"1".equals(SystemCommon.getReport())) {
        where = String.valueOf(where) + " and poo.reportType not in (1,3) ";
      } else {
        where = String.valueOf(where) + " and poo.reportType not in (0,1,3) ";
      } 
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view"))) {
        ManagerService bd = new ManagerService();
        List<Object[]> rightlist = bd.getRightScope(userId, "04*02*01");
        if (rightlist != null && rightlist.size() > 0) {
          Object[] obj = rightlist.get(0);
          if (obj[0] != null && obj[0].toString().equals("3")) {
            where = String.valueOf(where) + " and poo.reportOrgID=" + orgId;
          } else if (obj[0] != null && obj[0].toString().equals("2")) {
            String ids = "";
            OrganizationBD obd = new OrganizationBD();
            List<E> list1 = obd.getSons(orgId);
            if (list1 != null && list1.size() != 0) {
              for (int j = 0; j < list1.size(); j++)
                ids = String.valueOf(ids) + list1.get(j).toString() + ","; 
              ids = String.valueOf(ids) + orgId + ",";
              ids = ids.substring(0, ids.trim().length() - 1);
              where = String.valueOf(where) + " and (" + 
                convertStr(ids, "poo.reportOrgID") + ")";
            } else {
              where = String.valueOf(where) + " and poo.reportOrgID=" + orgId;
            } 
          } else if (obj[0] != null && obj[0].toString().equals("4")) {
            String rang = obj[1].toString();
            rang = rang.substring(1, rang.length() - 1);
            String[] tmp = rang.split("\\*\\*");
            if (tmp.length > 0) {
              String ids = "";
              for (int i = 0; i < tmp.length; i++) {
                ids = String.valueOf(ids) + tmp[i] + ",";
                OrganizationBD obd = new OrganizationBD();
                List<E> list1 = obd.getSons(tmp[i]);
                for (int j = 0; j < list1.size(); j++)
                  ids = String.valueOf(ids) + list1.get(j).toString() + ","; 
              } 
              ids = ids.substring(0, 
                  ids.trim().length() - 1);
              where = String.valueOf(where) + " and (" + convertStr(ids, "poo.reportOrgID") + ")";
            } 
          } 
        } else {
          where = String.valueOf(where) + " and poo.reportOrgID=9999999 ";
        } 
      } 
      Page page = null;
      if (request.getParameter("view") != null && 
        "1".equals(request.getParameter("view"))) {
        orderBy = " order by poo.reportTime desc";
        if (request.getParameter("orderBy") != null && !"".equals(request.getParameter("orderBy")))
          if ("xingming".equals(request.getParameter("orderBy"))) {
            if ("desc".equals(request.getParameter("sortType"))) {
              if ("mysql".equals(dataType)) {
                orderBy = " order by CONVERT(poo.reportEmpName USING gbk) desc,poo.reportTime desc";
              } else {
                orderBy = " order by poo.reportEmpName desc,poo.reportTime desc";
              } 
            } else if ("mysql".equals(dataType)) {
              orderBy = " order by CONVERT(poo.reportEmpName USING gbk),poo.reportTime desc";
            } else {
              orderBy = " order by poo.reportEmpName,poo.reportTime desc";
            } 
          } else if ("desc".equals(request.getParameter("sortType"))) {
            orderBy = " order by poo.reportOrgID desc,poo.reportTime desc";
          } else {
            orderBy = " order by poo.reportOrgID,poo.reportTime desc";
          }  
        page = new Page(
            " poo.id,poo.reportTime,poo.reportEmpName,poo.reportType,poo.reportReader,poo.empId,poo.reportDepart,poo.reportInputTime,poo.reportName", 
            " com.js.oa.scheme.workreport.po.WorkReportPO poo ", 
            " where " + (
            (domainId != null) ? (" poo.reportDomainId = " + domainId) : 
            "") + 
            " and " + where + " and poo.sendType<>0 " + orderBy);
      } else {
        orderBy = " order by poo.reportTime desc ,po.hadRead, poo.id desc";
        if (request.getParameter("orderBy") != null && !"".equals(request.getParameter("orderBy")))
          if ("xingming".equals(request.getParameter("orderBy"))) {
            if ("desc".equals(request.getParameter("sortType"))) {
              if ("mysql".equals(dataType)) {
                orderBy = " order by CONVERT(poo.reportEmpName USING gbk) desc,poo.reportTime desc ,po.hadRead, poo.id desc";
              } else {
                orderBy = " order by poo.reportEmpName desc,poo.reportTime desc ,po.hadRead, poo.id desc";
              } 
            } else if ("mysql".equals(dataType)) {
              orderBy = " order by CONVERT(poo.reportEmpName USING gbk),poo.reportTime desc ,po.hadRead, poo.id desc";
            } else {
              orderBy = " order by poo.reportEmpName,poo.reportTime desc ,po.hadRead, poo.id desc";
            } 
          } else if ("desc".equals(request.getParameter("sortType"))) {
            orderBy = " order by poo.reportOrgID desc,poo.reportTime desc ,po.hadRead, poo.id desc";
          } else {
            orderBy = " order by poo.reportOrgID,poo.reportTime desc ,po.hadRead, poo.id desc";
          }  
        page = new Page(
            " po.id,po.hadRead,poo.reportTime,poo.reportEmpName,poo.reportType,poo.reportReader,poo.empId,poo.reportDepart,poo.reportInputTime,poo.reportName", 
            " com.js.oa.scheme.workreport.po.WorkReportLeaderPO po join po.report poo  ", 
            " where po.empId=" + userId + (
            (domainId != null) ? (" and po.rlDomainId = " + domainId) : 
            "") + 
            " and " + where + orderBy);
      } 
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", 
          "action,queryText,queryItem,ribao,startDate,sortType,orderBy,endDate,queryName,view,reportType,reportDepartId,reportEmpId,reportOrgId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private String convertStr(String strIds, String fields) {
    StringBuffer where = new StringBuffer();
    String[] tmp = strIds.split(",");
    int max = 500;
    if (tmp.length > max) {
      int t = (tmp.length % max == 0) ? (
        tmp.length / max) : (
        tmp.length / max + 1);
      for (int i = 0; i < t; i++) {
        if (i == 0) {
          where.append(String.valueOf(fields) + " in (-1");
          for (int j = 0; j < max; j++) {
            if (i * max + j < 
              tmp.length)
              where.append(",")
                .append(
                  tmp[i * max + j]); 
          } 
          where.append(")");
        } else {
          where.append(
              " or " + fields + " in (-1");
          for (int j = 0; j < max; j++) {
            if (i * max + j < 
              tmp.length)
              where.append(",")
                .append(
                  tmp[i * max + j]); 
          } 
          where.append(")");
        } 
      } 
    } else {
      where.append(String.valueOf(fields) + " in (");
      where.append(strIds);
      where.append(")");
    } 
    return where.toString();
  }
}
