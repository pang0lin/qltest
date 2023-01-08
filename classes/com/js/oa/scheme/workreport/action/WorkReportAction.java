package com.js.oa.scheme.workreport.action;

import com.js.message.RealTimeUtil;
import com.js.oa.scheme.worklog.po.WorkLogPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.oa.scheme.workreport.service.WorkReportBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.Accessory;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class WorkReportAction extends Action {
  WorkReportBD bd = new WorkReportBD();
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession(true);
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String curUserId = request.getSession(true).getAttribute("userId")
      .toString();
    String curOrgId = request.getSession(true).getAttribute("orgId")
      .toString();
    String curOrgName = request.getSession(true).getAttribute("orgName")
      .toString();
    String orgIdString = request.getSession(true).getAttribute(
        "orgIdString").toString();
    String curUserName = request.getSession(true).getAttribute("userName")
      .toString();
    WorkReportActionForm workReportActionForm = 
      (WorkReportActionForm)actionForm;
    String action = request.getParameter("action");
    if ("add".equals(action)) {
      String[] para = { 
          "", "", "", "", "", "", "", "", "", "", 
          "", "" };
      WorkReportBD wrb = new WorkReportBD();
      EmployeeVO vo = wrb.getEmployeeByID(new Long(curUserId));
      String reportReader = workReportActionForm.getReportReader();
      String reportReadID = workReportActionForm.getReportReaderId();
      para[0] = reportReader;
      para[1] = reportReadID;
      para[2] = workReportActionForm.getReportRemark();
      para[3] = workReportActionForm.getTemplateId();
      para[4] = workReportActionForm.getReportType();
      para[5] = workReportActionForm.getReportTime();
      para[6] = domainId.toString();
      if (workReportActionForm.getReportType().equals("1")) {
        String tmp = workReportActionForm.getStartDate();
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
        tmp = workReportActionForm.getEndDate();
        String endDate = tmp.substring(0, 4);
        tmp = tmp.substring(tmp.indexOf("/") + 1, tmp.length());
        if (Integer.parseInt(tmp.substring(0, tmp.indexOf("/"))) < 10) {
          endDate = String.valueOf(endDate) + "0" + tmp.substring(0, tmp.indexOf("/"));
        } else {
          endDate = String.valueOf(endDate) + tmp.substring(0, tmp.indexOf("/"));
        } 
        tmp = tmp.substring(tmp.indexOf("/") + 1, tmp.length());
        if (Integer.parseInt(tmp) < 10) {
          endDate = String.valueOf(endDate) + "0" + tmp;
        } else {
          endDate = String.valueOf(endDate) + tmp;
        } 
        para[7] = String.valueOf(startDate) + 
          "-" + 
          endDate;
      } else {
        para[7] = String.valueOf(request.getParameter("selYear")) + 
          request.getParameter("selMonth");
      } 
      para[8] = vo.getEmpDuty();
      para[9] = (String)session.getAttribute("orgName");
      para[10] = curOrgId;
      para[11] = workReportActionForm.getSendType();
      if (this.bd.add(curUserId, curOrgId, curUserName, 
          request.getParameter("nextReport"), 
          request.getParameter("previousReport"), 
          workReportActionForm.getAccessoryName(), 
          workReportActionForm.getAccessorySaveName(), 
          para, workReportActionForm.getReportName()).booleanValue()) {
        request.setAttribute("success", "1");
        ManagerService mbd = new ManagerService();
        if (para[11].equals("1")) {
          String accounts = mbd.getEmployeesAccounts(
              workReportActionForm
              .getReportReaderId());
          RealTimeUtil api = new RealTimeUtil();
          String title = "[" + curUserName + "]";
          String reportType = workReportActionForm.getReportType();
          if ("0".equals(reportType)) {
            title = String.valueOf(title) + "每日工作汇报";
          } else if ("1".equals(reportType)) {
            title = String.valueOf(title) + "每周工作汇报";
          } else if ("2".equals(reportType)) {
            title = String.valueOf(title) + "半月工作汇报";
          } else if ("3".equals(reportType)) {
            title = String.valueOf(title) + "每月工作汇报";
          } else if ("4".equals(reportType)) {
            title = String.valueOf(title) + "每季工作汇报";
          } else if ("5".equals(reportType)) {
            title = String.valueOf(title) + "半年工作汇报";
          } else if ("6".equals(reportType)) {
            title = String.valueOf(title) + "每年工作汇报";
          } 
          Date date = new Date();
          title = String.valueOf(title) + "(" + (date.getMonth() + 1) + "-" + date.getDate() + 
            ")";
          api.sendNotify(accounts, "", "新工作汇报提醒", title, "", "0", "0");
        } 
        workReportActionForm.reset(actionMapping, request);
        String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
            orgIdString, 
            "po.templateUseEmp", 
            "po.templateUseOrg", 
            "po.templateUseGroup", 
            "po.createdEmp");
        request.setAttribute("templates", this.bd.see(wherePara, domainId));
        if (para[4].equals("1")) {
          action = "see";
        } else {
          action = "seemonth";
        } 
      } else {
        request.setAttribute("success", "0");
        request.setAttribute("workArea", para[7]);
        if (para[4].equals("1")) {
          action = "see";
        } else {
          action = "seemonth";
        } 
      } 
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
      if (queryText != null && !"".equals(queryText)) {
        where = String.valueOf(where) + " and ( po.previousReport like '%" + queryText + "%'";
        where = String.valueOf(where) + " or po.nextReport like '%" + queryText + "%' )";
      } 
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
      if (reportType != null && !"none".equals(reportType))
        where = String.valueOf(where) + " and po.reportType=" + reportType; 
      String wherePara = " where po.empId=" + userId + " and " + where;
      String orderby = 
        " order by po.hadRead ,po.reportTime desc,po.id desc ";
      if (request.getParameter("sortFirst") != null && 
        !"none".equals(request.getParameter("sortFirst")))
        if ("hbqj".equals(request.getParameter("sortFirst"))) {
          if ("1".equals(request.getParameter("hbqjDesc"))) {
            orderby = " order by po.reportCourse ";
          } else if ("2".equals(request.getParameter("hbqjDesc"))) {
            orderby = " order by po.reportCourse desc ";
          } 
        } else if ("txsj".equals(request.getParameter("sortFirst"))) {
          if ("1".equals(request.getParameter("txsjDesc"))) {
            orderby = " order by po.reportTime ";
          } else if ("2".equals(request.getParameter("txsjDesc"))) {
            orderby = " order by po.reportTime desc ";
          } 
        }  
      Page page = new Page(
          " po.id,po.reportType,po.reportTime,po.reportReader,po.reportReader,po.templateId,po.hadRead,po.reportCourse,po.sendType ", 
          " com.js.oa.scheme.workreport.po.WorkReportPO po  ", 
          String.valueOf(wherePara) + 
          " and po.reportDomainId = " + domainId + orderby);
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      list = (new WorkReportBD()).initList(list);
      String recordCount = String.valueOf(page.getRecordCount());
      String pageCount = String.valueOf(page.getPageCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", 
          "action,queryText,queryItem,startDate,endDate,reportType");
      if (reportType.equals("1"))
        return actionMapping.findForward("list"); 
      if (reportType.equals("3"))
        return actionMapping.findForward("listmonth"); 
    } 
    if ("load".equals(action)) {
      request.setAttribute("fromdesktop", request.getParameter("fromdesktop"));
      Map result = this.bd.load(request.getParameter("receiveRecordId"));
      request.setAttribute("reportTime", result.get("reportTime1"));
      request.setAttribute("saveName", result.get("accessorySaveTmpName"));
      request.setAttribute("fileName", result.get("accessoryTmpName"));
      request.setAttribute("sendType", result.get("sendType"));
      workReportActionForm.setEditId(request.getParameter("receiveRecordId"));
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
      } else {
        workReportActionForm.setReportTime("");
      } 
      if (result.get("sendType") != null) {
        workReportActionForm.setSendType(result.get("sendType")
            .toString());
      } else {
        workReportActionForm.setSendType("1");
      } 
      workReportActionForm.setReportDepart((result.get("reportDepart") == null) ? 
          "" : result.get("reportDepart")
          .toString());
      workReportActionForm.setReportJob((result.get("reportJob") == null) ? 
          "" : 
          result.get("reportJob").toString());
      workReportActionForm.setReportCourse(result.get("reportCourse")
          .toString());
      workReportActionForm.setReportEmpName(result.get("reportEmpName")
          .toString());
      String today = result.get("reportCourse").toString();
      if (request.getParameter("reportType1").equals("1")) {
        today = String.valueOf(today.substring(0, 4)) + "-" + 
          today.substring(4, 6) + "-" + 
          today.substring(6, 8);
        WorkLogBD wbd = new WorkLogBD();
        WorkLogPO wpo = null;
        String now = "";
        List<WorkLogPO> list = null;
        String content = "";
        if ("0".equals(SystemCommon.getReport())) {
          for (int i = 1; i < 8; i++) {
            content = "";
            if (i < 7) {
              now = getWeek(today, i);
            } else {
              now = getWeek(getNextDay(today, "7"), "0");
            } 
            list = wbd.getUserLogList(curUserId, now, "0");
            int j;
            for (j = 0; j < list.size(); j++) {
              wpo = list.get(j);
              content = String.valueOf(content) + "全天工作日志        " + ((wpo.getProjectName() == null) ? "默认" : wpo.getProjectName()) + 
                "\n" + wpo.getLogContent() + "\n";
            } 
            list = wbd.getUserLogList(curUserId, now, "1");
            for (j = 0; j < list.size(); j++) {
              wpo = list.get(j);
              content = String.valueOf(content) + wpo.getStartPeriod() + "-" + 
                wpo.getEndPeriod() + "        " + (
                (wpo.getProjectName() == null) ? "默认" : wpo.getProjectName()) + 
                "\n" + wpo.getLogContent() + "\n";
            } 
            request.setAttribute("worklog" + i, content);
            SimpleDateFormat format = new SimpleDateFormat(
                "yyyy年MM月dd日");
            Date d = strToDate(now);
            request.setAttribute("workday" + i, format.format(d));
          } 
        } else {
          for (int i = 1; i < 8; i++) {
            content = "";
            if (i < 7) {
              now = getWeek(today, i);
            } else {
              now = getWeek(getNextDay(today, "7"), "0");
            } 
            WorkReportPO wpo2 = wbd.getWorkReportPO(now, curUserId);
            content = (wpo2.getPreviousReport() == null) ? "" : wpo2.getPreviousReport();
            request.setAttribute("worklog" + i, content);
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
            Date d = strToDate(now);
            request.setAttribute("workday" + i, format.format(d));
          } 
        } 
      } else {
        String beginDate = String.valueOf(today.substring(0, 4)) + "-" + 
          today.substring(4, 6) + "-01";
        String thismonth = today;
        String tmpResult = "";
        for (int i = 0; i < 6; i++) {
          String weekbegin = getWeek(getNextDay(beginDate, i * 7), 
              "1").replaceAll("-", "");
          String weekend = getWeek(getNextDay(beginDate, (
                i + 1) * 7), 
              "0").replaceAll("-", "");
          if (weekbegin.indexOf(thismonth) == 0)
            tmpResult = String.valueOf(tmpResult) + weekbegin + "-" + weekend + ","; 
        } 
        request.setAttribute("reportCourse", thismonth);
        String[] tmp = tmpResult.split(",");
        request.setAttribute("weekCount", tmp.length);
        for (int j = 1; j <= tmp.length; j++) {
          String tmpCourse = tmp[j - 1];
          request.setAttribute("course" + j, tmpCourse);
          request.setAttribute("report" + j, 
              this.bd
              .getWorkReportContentByCourse(curUserId, 
                tmp[j - 1]));
        } 
      } 
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      if (request.getParameter("reportType1").equals("1"))
        return actionMapping.findForward("modi"); 
      return actionMapping.findForward("monthmodi");
    } 
    if ("update".equals(action)) {
      String[] para = { "", "", "", "", "", "", "" };
      String reportReader = workReportActionForm.getReportReader();
      String reportReadID = workReportActionForm.getReportReaderId();
      para[0] = reportReader;
      para[1] = reportReadID;
      para[2] = workReportActionForm.getReportRemark();
      para[3] = workReportActionForm.getTemplateId();
      para[4] = workReportActionForm.getReportType();
      para[5] = workReportActionForm.getReportTime();
      para[6] = workReportActionForm.getSendType();
      this.bd.update(request.getParameter("editId"), 
          curUserId, curOrgId, request.getParameter("nextReport"), 
          request.getParameter("previousReport"), 
          workReportActionForm.getAccessoryName(), 
          workReportActionForm.getAccessorySaveName(), 
          para);
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      request.setAttribute("update", "update");
      request.setAttribute("fromdesktop1", request.getParameter("fromdesktop"));
      if (para[4].equals("1"))
        return actionMapping.findForward("modi"); 
      return actionMapping.findForward("monthmodi");
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
      WorkReportBD wrb = new WorkReportBD();
      EmployeeVO vo = wrb.getEmployeeByID(new Long(curUserId));
      request.setAttribute("empduty", vo.getEmpDuty());
      request.setAttribute("leadername", vo.getEmpLeaderName());
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date date = new Date();
      String today = df.format(date);
      if (date.before(new Date(getWeek(today, "4").replaceAll("-", "/"))))
        today = getNextDay(today, "-7"); 
      request.setAttribute("thisMonday", 
          getWeek(today, "1"));
      request.setAttribute("thisSunday", 
          getWeek(getNextDay(today, "7"), "0"));
      WorkLogBD wbd = new WorkLogBD();
      WorkLogPO wpo = null;
      String now = "";
      List<WorkLogPO> list = null;
      String content = "";
      if ("0".equals(SystemCommon.getReport())) {
        for (int i = 1; i < 8; i++) {
          content = "";
          if (i < 7) {
            now = getWeek(today, i);
          } else {
            now = getWeek(getNextDay(today, "7"), "0");
          } 
          list = wbd.getUserLogList(curUserId, now, "0");
          int j;
          for (j = 0; j < list.size(); j++) {
            wpo = list.get(j);
            content = String.valueOf(content) + "全天工作日志        " + ((wpo.getProjectName() == null) ? "默认" : wpo.getProjectName()) + "\n" + 
              wpo.getLogContent() + "\n";
          } 
          list = wbd.getUserLogList(curUserId, now, "1");
          for (j = 0; j < list.size(); j++) {
            wpo = list.get(j);
            content = String.valueOf(content) + wpo.getStartPeriod() + "-" + wpo.getEndPeriod() + 
              "        " + (
              (wpo.getProjectName() == null) ? "默认" : wpo.getProjectName()) + 
              "\n" + wpo.getLogContent() + "\n";
          } 
          request.setAttribute("worklog" + i, content);
          SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
          Date d = strToDate(now);
          request.setAttribute("workday" + i, format.format(d));
        } 
      } else {
        for (int i = 1; i < 8; i++) {
          content = "";
          if (i < 7) {
            now = getWeek(today, i);
          } else {
            now = getWeek(getNextDay(today, "7"), "0");
          } 
          WorkReportPO wpo2 = wbd.getWorkReportPO(now, curUserId);
          content = (wpo2.getPreviousReport() == null) ? "" : wpo2.getPreviousReport();
          request.setAttribute("worklog" + i, content);
          SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
          Date d = strToDate(now);
          request.setAttribute("workday" + i, format.format(d));
        } 
      } 
      workReportActionForm.setReportReaderId(vo.getEmpLeaderId());
      workReportActionForm.setReportReader(vo.getEmpLeaderName());
      return actionMapping.findForward("add");
    } 
    if ("template".equals(action)) {
      String tempId = request.getParameter("tempId");
      String templateValue = this.bd.template(tempId, domainId);
      workReportActionForm.setPreviousReport((templateValue == null) ? "" : 
          templateValue);
      return actionMapping.findForward("template");
    } 
    if ("changedate".equals(action) || "changeWeek".equals(action)) {
      String today = request.getParameter("startdate");
      today = today.replaceAll("/", "-");
      if ("changeWeek".equals(action))
        today = getNextDay(today, request.getParameter("changeDay")); 
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      WorkReportBD wrb = new WorkReportBD();
      EmployeeVO vo = wrb.getEmployeeByID(new Long(curUserId));
      request.setAttribute("empduty", vo.getEmpDuty());
      request.setAttribute("leadername", vo.getEmpLeaderName());
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      request.setAttribute("thisMonday", 
          getWeek(today, "1"));
      request.setAttribute("thisSunday", 
          getWeek(getNextDay(today, "7"), "0"));
      WorkLogBD wbd = new WorkLogBD();
      WorkLogPO wpo = null;
      String now = "";
      List<WorkLogPO> list = null;
      String content = "";
      if ("0".equals(SystemCommon.getReport())) {
        for (int i = 1; i < 8; i++) {
          content = "";
          if (i < 7) {
            now = getWeek(today, i);
          } else {
            now = getWeek(getNextDay(today, "7"), "0");
          } 
          list = wbd.getUserLogList(curUserId, now, "0");
          int j;
          for (j = 0; j < list.size(); j++) {
            wpo = list.get(j);
            content = String.valueOf(content) + "全天工作日志        " + ((wpo.getProjectName() == null) ? "默认" : wpo.getProjectName()) + "\n" + 
              wpo.getLogContent() + "\n";
          } 
          list = wbd.getUserLogList(curUserId, now, "1");
          for (j = 0; j < list.size(); j++) {
            wpo = list.get(j);
            content = String.valueOf(content) + wpo.getStartPeriod() + "-" + wpo.getEndPeriod() + 
              "        " + (
              (wpo.getProjectName() == null) ? "默认" : wpo.getProjectName()) + 
              "\n" + wpo.getLogContent() + "\n";
          } 
          request.setAttribute("worklog" + i, content);
          SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
          Date d = strToDate(now);
          request.setAttribute("workday" + i, format.format(d));
        } 
      } else {
        for (int i = 1; i < 8; i++) {
          content = "";
          if (i < 7) {
            now = getWeek(today, i);
          } else {
            now = getWeek(getNextDay(today, "7"), "0");
          } 
          WorkReportPO wpo2 = wbd.getWorkReportPO(now, curUserId);
          content = (wpo2.getPreviousReport() == null) ? "" : wpo2.getPreviousReport();
          request.setAttribute("worklog" + i, content);
          SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
          Date d = strToDate(now);
          request.setAttribute("workday" + i, format.format(d));
        } 
      } 
      workReportActionForm.setReportReaderId(vo.getEmpLeaderId());
      workReportActionForm.setReportReader(vo.getEmpLeaderName());
      if (request.getParameter("reportReader") != null) {
        workReportActionForm.setReportReaderId(request.getParameter("reportReaderId").toString());
        workReportActionForm.setReportReader(request.getParameter("reportReader").toString());
      } 
      request.setAttribute("nextweek", "1");
      String[] accessoryName = workReportActionForm.getAccessoryName();
      String[] accessorySaveName = workReportActionForm.getAccessorySaveName();
      String str1 = "";
      String str2 = "";
      if (accessoryName != null)
        for (int i = 0; i < accessoryName.length; i++) {
          if (!str1.equals(""))
            str1 = String.valueOf(str1) + "|"; 
          if (!str2.equals(""))
            str2 = String.valueOf(str2) + "|"; 
          str1 = String.valueOf(str1) + accessoryName[i];
          str2 = String.valueOf(str2) + accessorySaveName[i];
        }  
      request.setAttribute("fileName", str1);
      request.setAttribute("saveName", str2);
      return actionMapping.findForward("add");
    } 
    if ("seemonth".equals(action) || "monthchange".equals(action)) {
      ManagerService mbd = new ManagerService();
      String wherePara = mbd.getScopeWhere(curUserId, curOrgId, 
          orgIdString, 
          "po.templateUseEmp", 
          "po.templateUseOrg", 
          "po.templateUseGroup", 
          "po.createdEmp");
      request.setAttribute("templates", this.bd.see(wherePara, domainId));
      WorkReportBD wrb = new WorkReportBD();
      EmployeeVO vo = wrb.getEmployeeByID(new Long(curUserId));
      request.setAttribute("empduty", vo.getEmpDuty());
      workReportActionForm.setReportReaderId(vo.getEmpLeaderId());
      workReportActionForm.setReportReader(vo.getEmpLeaderName());
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
      Date now = new Date();
      String beginDate = "";
      if ("seemonth".equals(action)) {
        if (now.before(new Date((String.valueOf(format.format(now)) + "-16").replaceAll("-", "/")))) {
          SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
          String tmpDay = getNextDay(format1.format(now), "-20");
          now = new Date(tmpDay.replaceAll("-", "/"));
        } 
        beginDate = String.valueOf(format.format(now)) + "-01";
      } else {
        beginDate = String.valueOf(request.getParameter("selYear")) + "-" + 
          request.getParameter("selMonth") + "-01";
      } 
      format = new SimpleDateFormat("yyyyMM");
      String thismonth = "";
      if ("seemonth".equals(action)) {
        thismonth = format.format(now);
      } else {
        thismonth = String.valueOf(request.getParameter("selYear")) + 
          request.getParameter("selMonth");
      } 
      String result = "";
      for (int i = 0; i < 6; i++) {
        String weekbegin = getWeek(getNextDay(beginDate, i * 7), 
            "1").replaceAll("-", "");
        String weekend = getWeek(getNextDay(beginDate, (i + 1) * 7), 
            "0").replaceAll("-", "");
        if (weekbegin.indexOf(thismonth) == 0)
          result = String.valueOf(result) + weekbegin + "-" + weekend + ","; 
      } 
      request.setAttribute("reportCourse", thismonth);
      String[] tmp = result.split(",");
      request.setAttribute("weekCount", tmp.length);
      for (int j = 1; j <= tmp.length; j++) {
        String tmpCourse = tmp[j - 1];
        request.setAttribute("course" + j, tmpCourse);
        request.setAttribute("report" + j, 
            this.bd
            .getWorkReportContentByCourse(curUserId, 
              tmp[j - 1]));
      } 
      return actionMapping.findForward("addmonth");
    } 
    if ("report".equals(action)) {
      String type = request.getParameter("reportFormsType");
      String year = request.getParameter("yeartime");
      ManagerService mbd = new ManagerService();
      if (type.equals("org")) {
        if (request.getParameter("org") != null && 
          !"".equals(request.getParameter("org"))) {
          String org = request.getParameter("org");
          String orgName = mbd.getNameBYId(org);
          org = org.substring(1, org.length() - 1);
          request.setAttribute("reportList", 
              this.bd.getReportData(year, org, 
                (String)domainId, curUserId, 
                mbd.getRightScope(curUserId, 
                  "04*02*01")));
          request.setAttribute("orgName", orgName);
        } else {
          request.setAttribute("reportList", 
              this.bd.getReportData(year, curOrgId, 
                (String)domainId, curUserId, 
                mbd.getRightScope(curUserId, 
                  "04*02*01")));
          request.setAttribute("orgName", curOrgName);
        } 
        request.setAttribute("year", year);
        return actionMapping.findForward("report_org");
      } 
      String orgId = "0";
      if (request.getParameter("org") != null && 
        !"".equals(request.getParameter("org"))) {
        orgId = request.getParameter("org");
        orgId = orgId.substring(1, orgId.length() - 1);
      } 
      request.setAttribute("reportList", 
          this.bd.getReportData(year, orgId, (String)domainId, 
            curUserId, 
            mbd.getRightScope(curUserId, "04*02*01")));
      request.setAttribute("year", year);
      return actionMapping.findForward("report_company");
    } 
    return actionMapping.findForward("list");
  }
  
  private static String getNextDay(String nowdate, String delay) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String mdate = "";
      Date d = strToDate(nowdate);
      long myTime = d.getTime() / 1000L + (
        Integer.parseInt(delay) * 24 * 60 * 60);
      d.setTime(myTime * 1000L);
      mdate = format.format(d);
      return mdate;
    } catch (Exception e) {
      return "";
    } 
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
  
  public static String getWeek(String sdate, String num) {
    Date dd = strToDate(sdate);
    Calendar c = Calendar.getInstance();
    c.setTime(dd);
    if (num.equals("1")) {
      c.set(7, 2);
    } else if (num.equals("2")) {
      c.set(7, 3);
    } else if (num.equals("3")) {
      c.set(7, 4);
    } else if (num.equals("4")) {
      c.set(7, 5);
    } else if (num.equals("5")) {
      c.set(7, 6);
    } else if (num.equals("6")) {
      c.set(7, 7);
    } else if (num.equals("0")) {
      c.set(7, 1);
    } 
    return (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
  }
}
