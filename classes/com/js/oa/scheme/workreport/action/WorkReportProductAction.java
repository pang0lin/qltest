package com.js.oa.scheme.workreport.action;

import com.js.oa.scheme.workreport.service.WorkReportBD;
import com.js.oa.scheme.workreport.service.WorkReportProductBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.util.StaticParam;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.Accessory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WorkReportProductAction extends Action {
  WorkReportProductBD bd = new WorkReportProductBD();
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String curUserId = request.getSession(true).getAttribute("userId")
      .toString();
    String curOrgId = request.getSession(true).getAttribute("orgId")
      .toString();
    String orgIdString = request.getSession(true).getAttribute(
        "orgIdString").toString();
    String curUserName = request.getSession(true).getAttribute("userName")
      .toString();
    WorkReportActionForm workReportActionForm = 
      (WorkReportActionForm)actionForm;
    String action = request.getParameter("action");
    if ("add".equals(action)) {
      WorkReportBD wrb = new WorkReportBD();
      EmployeeVO vo = wrb.getEmployeeByID(new Long(curUserId));
      String[] para = { 
          "", "", "", "", "", "", "", "", "", "", 
          "", "", "" };
      para[0] = workReportActionForm.getReportReader();
      para[1] = workReportActionForm.getReportReaderId();
      para[2] = workReportActionForm.getReportRemark();
      para[3] = workReportActionForm.getTemplateId();
      para[4] = workReportActionForm.getReportType();
      para[5] = workReportActionForm.getReportTime();
      para[6] = domainId.toString();
      String tmp = "";
      if (workReportActionForm.getReportTime() == null) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String datenew = format.format(new Date());
        tmp = datenew;
      } else {
        tmp = workReportActionForm.getReportTime();
      } 
      String startDate = tmp.substring(0, 4);
      tmp = tmp.substring(tmp.indexOf("/") + 1, tmp.length());
      if (Integer.parseInt(tmp.substring(0, tmp.indexOf("/"))) < 10) {
        startDate = String.valueOf(startDate) + "0" + tmp.substring(0, tmp.indexOf("/"));
      } else {
        startDate = String.valueOf(startDate) + tmp.substring(0, tmp.indexOf("/"));
      } 
      tmp = tmp.substring(tmp.indexOf("/") + 1, tmp.length());
      if (Integer.parseInt(tmp) < 10) {
        startDate = String.valueOf(startDate) + "0" + tmp;
      } else {
        startDate = String.valueOf(startDate) + tmp;
      } 
      para[7] = startDate;
      para[8] = vo.getEmpDuty();
      para[9] = (String)session.getAttribute("orgName");
      para[10] = curOrgId;
      para[11] = workReportActionForm.getSendType();
      para[12] = (request.getParameter("relproject") == null) ? "-1" : request.getParameter("relproject");
      Boolean ff = (new WorkReportBD()).add(curUserId, curOrgId, curUserName, 
          request.getParameter("nextReport"), 
          request.getParameter("previousReport"), 
          workReportActionForm.getAccessoryName(), 
          workReportActionForm.getAccessorySaveName(), 
          para, workReportActionForm.getReportName());
      ManagerService mbd = new ManagerService();
      request.setAttribute("yitijiao", ff);
      request.setAttribute("projectId", para[12]);
      workReportActionForm.reset(actionMapping, request);
      workReportActionForm.setReportReaderId(vo.getEmpLeaderId());
      workReportActionForm.setReportReader(vo.getEmpLeaderName());
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("ribao", (request.getParameter("ribao") == null) ? "" : request.getParameter("ribao"));
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      return actionMapping.findForward("add");
    } 
    if ("delBatch".equals(action)) {
      String retString = this.bd.delBatch(request.getParameter("ids"));
      String path = request.getRealPath("/");
      path = String.valueOf(path) + "/upload/workreport";
      Accessory.delAccessory(path, retString);
      action = "list";
    } 
    "delAll".equals(action);
    if ("list".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (request.getParameter("pager.offset") != null)
        offset = Integer.parseInt(request.getParameter("pager.offset")); 
      int currentPage = offset / pageSize + 1;
      long userId = Long.parseLong(String.valueOf(request.getSession(true)
            .getAttribute("userId")));
      String empID = request.getParameter("empID");
      if (empID != null && !"".equals(empID) && !"null".equals(empID)) {
        Long empIDValue = new Long(empID.toString());
        userId = empIDValue.longValue();
        request.setAttribute("showId", "show");
      } 
      String where = "1=1  ";
      String queryItem = request.getParameter("queryItem");
      String queryText = request.getParameter("queryText");
      String reportName = request.getParameter("reportName");
      if (queryText != null && !"".equals(queryText)) {
        where = String.valueOf(where) + " and ( po.previousReport like '%" + queryText + "%'";
        where = String.valueOf(where) + " or po.nextReport like '%" + queryText + "%' )";
      } 
      if (reportName != null && !"".equals(reportName))
        where = String.valueOf(where) + " and ( po.reportName like '%" + reportName + "%') "; 
      if (queryItem != null && queryItem.equals("1")) {
        String databaseType = 
          SystemCommon.getDatabaseType();
        if (databaseType.indexOf("mysql") >= 0) {
          where = String.valueOf(where) + " and ( po.reportTime between '" + 
            request.getParameter("startDate") + " 00:00:00" + 
            "' and '" + 
            request.getParameter("endDate") + " 23:59:59" + 
            "'";
          where = String.valueOf(where) + " or  po.reportTime between '" + 
            request.getParameter("endDate") + " 00:00:00" + 
            "' and '" + 
            request.getParameter("startDate") + " 23:59:59" + 
            "')";
        } else {
          where = String.valueOf(where) + 
            " and ( po.reportTime between JSDB.FN_STRTODATE('" + 
            request.getParameter("startDate") + " 00:00:00" + 
            "','L') and JSDB.FN_STRTODATE('" + 
            request.getParameter("endDate") + " 23:59:59" + 
            "','L')";
          where = String.valueOf(where) + 
            " or  po.reportTime between JSDB.FN_STRTODATE('" + 
            request.getParameter("endDate") + " 00:00:00" + 
            "','L') and JSDB.FN_STRTODATE('" + 
            request.getParameter("startDate") + " 23:59:59" + 
            "','L') )";
        } 
      } 
      String reportType = request.getParameter("reportType");
      if (reportType != null && !"none".equals(reportType)) {
        where = String.valueOf(where) + " and po.reportType=" + reportType;
      } else if (!"1".equals(SystemCommon.getReport())) {
        where = String.valueOf(where) + " and po.reportType not in (1,3) ";
      } else {
        where = String.valueOf(where) + " and po.reportType not in (0,1,3) ";
      } 
      String wherePara = " where po.empId=" + userId + " and " + where;
      String orderBy = " order by po.hadRead ,po.reportTime desc,po.id desc ";
      if ("1".equals(SystemCommon.getReport()))
        orderBy = " order by po.reportTime desc,po.id desc "; 
      Page page = new Page(
          
          " po.id,po.reportType,po.reportTime,po.reportReader,po.reportReader,po.templateId,po.hadRead,po.sendType,po.reportName,po.reportInputTime ", 
          " com.js.oa.scheme.workreport.po.WorkReportPO po  ", 
          String.valueOf(wherePara) + 
          " and po.reportDomainId = " + domainId + orderBy);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      list = (new WorkReportProductBD()).initList(list);
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", 
          "action,queryText,queryItem,startDate,endDate,reportType,reportName,ribao");
    } 
    if ("load".equals(action)) {
      Map result = this.bd.load(request.getParameter("receiveRecordId"));
      request.setAttribute("reportTime", result.get("reportTime1"));
      request.setAttribute("editId", request.getParameter("receiveRecordId"));
      request.setAttribute("saveName", result.get("accessorySaveTmpName"));
      request.setAttribute("fileName", result.get("accessoryTmpName"));
      request.setAttribute("relproject", result.get("relproject"));
      request.setAttribute("sendType", result.get("sendType"));
      if (result.get("nextReport") != null) {
        workReportActionForm.setNextReport(result.get("nextReport")
            .toString());
      } else {
        workReportActionForm.setNextReport("");
      } 
      if (result.get("previousReport") != null) {
        workReportActionForm.setPreviousReport(result.get(
              "previousReport").toString());
      } else {
        workReportActionForm.setPreviousReport("");
      } 
      if (result.get("reportName") != null) {
        workReportActionForm.setReportName(result.get(
              "reportName").toString());
      } else {
        workReportActionForm.setReportName("");
      } 
      if (result.get("reportReader") != null) {
        workReportActionForm.setReportReader(result.get("reportReader")
            .toString());
      } else {
        workReportActionForm.setReportReader("");
      } 
      if (result.get("reportReaderId") != null) {
        workReportActionForm.setReportReaderId(result.get(
              "reportReaderId").toString());
      } else {
        workReportActionForm.setReportReaderId("");
      } 
      if (result.get("reportRemark") != null) {
        workReportActionForm.setReportRemark(result.get("reportRemark")
            .toString());
      } else {
        workReportActionForm.setReportRemark("");
      } 
      if (result.get("reportType") != null) {
        workReportActionForm.setReportType(result.get("reportType")
            .toString());
      } else {
        workReportActionForm.setReportType("");
      } 
      if (result.get("templateId") != null) {
        workReportActionForm.setTemplateId(result.get("templateId")
            .toString());
      } else {
        workReportActionForm.setTemplateId("");
      } 
      if (result.get("reportTime") != null) {
        workReportActionForm.setReportTime(result.get("reportTime")
            .toString());
        request.setAttribute("reportTime", result.get("reportTime"));
      } else {
        workReportActionForm.setReportTime("");
      } 
      if (result.get("sendType") != null) {
        workReportActionForm.setSendType(result.get("sendType").toString());
      } else {
        workReportActionForm.setSendType("1");
      } 
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      return actionMapping.findForward("modi");
    } 
    if ("update".equals(action)) {
      String[] para = { "", "", "", "", "", "", "", "", "" };
      para[0] = workReportActionForm.getReportReader();
      para[1] = workReportActionForm.getReportReaderId();
      para[2] = workReportActionForm.getReportRemark();
      para[3] = workReportActionForm.getTemplateId();
      para[4] = workReportActionForm.getReportType();
      para[5] = workReportActionForm.getReportTime();
      para[6] = workReportActionForm.getSendType();
      para[7] = (request.getParameter("relproject") == null) ? "-1" : request.getParameter("relproject");
      String tmp = workReportActionForm.getReportTime();
      String startDate = tmp.substring(0, 4);
      tmp = tmp.substring(tmp.indexOf("/") + 1, tmp.length());
      if (Integer.parseInt(tmp.substring(0, tmp.indexOf("/"))) < 10) {
        startDate = String.valueOf(startDate) + "0" + tmp.substring(0, tmp.indexOf("/"));
      } else {
        startDate = String.valueOf(startDate) + tmp.substring(0, tmp.indexOf("/"));
      } 
      tmp = tmp.substring(tmp.indexOf("/") + 1, tmp.length());
      if (Integer.parseInt(tmp) < 10) {
        startDate = String.valueOf(startDate) + "0" + tmp;
      } else {
        startDate = String.valueOf(startDate) + tmp;
      } 
      para[8] = startDate;
      this.bd.update(request.getParameter("editId"), 
          curUserId, curOrgId, request.getParameter("nextReport"), 
          request.getParameter("previousReport"), 
          workReportActionForm.getAccessoryName(), 
          workReportActionForm.getAccessorySaveName(), 
          para, workReportActionForm.getReportName());
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      request.setAttribute("ribao", (request.getParameter("ribao") == null) ? "" : request.getParameter("ribao"));
      return actionMapping.findForward("modi");
    } 
    if ("see".equals(action)) {
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      String projectId = request.getParameter("projectId");
      request.setAttribute("projectId", projectId);
      WorkReportBD wrb = new WorkReportBD();
      EmployeeVO vo = wrb.getEmployeeByID(new Long(curUserId));
      String leaderId = "", leaderName = "", idAndNameStr = "";
      if (projectId != null && !projectId.equals(""))
        idAndNameStr = StaticParam.getProLeaderIdAndNameByProId(projectId); 
      if (idAndNameStr.equals(";") || idAndNameStr.equals("")) {
        leaderId = vo.getEmpLeaderId();
        leaderName = vo.getEmpLeaderName();
      } else {
        leaderId = idAndNameStr.split(";")[0];
        leaderName = idAndNameStr.split(";")[1];
      } 
      workReportActionForm.setReportReaderId(leaderId);
      workReportActionForm.setReportReader(leaderName);
      return actionMapping.findForward("add");
    } 
    if ("template".equals(action)) {
      String tempId = request.getParameter("tempId");
      String templateValue = this.bd.template(tempId, domainId);
      workReportActionForm.setPreviousReport((templateValue == null) ? "" : 
          templateValue);
      return actionMapping.findForward("template");
    } 
    return actionMapping.findForward("list");
  }
}
