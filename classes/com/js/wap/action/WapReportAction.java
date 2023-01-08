package com.js.wap.action;

import com.js.wap.WapAction;
import com.js.wap.service.WapBD;
import com.js.wap.util.WapStringTool;
import com.js.wap.util.WapUtil;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class WapReportAction extends DispatchAction {
  public ActionForward showReportIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String version = (String)session.getAttribute("wapVersion");
    String userId = (String)session.getAttribute("userId");
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    int empCount = Integer.parseInt((new WapAction()).getUnderEmpIdString(userId, false));
    String userIdStr = null;
    getWapReportListByDay(request, userId, "index");
    getReportListByWM(request, userId, "1", "index", "mine", domainId);
    getReportListByWM(request, userId, "3", "index", "mine", domainId);
    if (empCount > 0) {
      userIdStr = (new WapSchemeAction()).getEmpStr(userId);
      getWapReportListByDay(request, userIdStr, "index");
    } 
    getReportListByWM(request, userId, "1", "index", "other", domainId);
    getReportListByWM(request, userId, "3", "index", "other", domainId);
    return mapping.findForward(WapStringTool.getForwardStr(version, "showReportIndex"));
  }
  
  public ActionForward getWapRepList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String version = (String)session.getAttribute("wapVersion");
    String userId = (String)session.getAttribute("userId");
    String repUser = request.getParameter("repUser");
    if ("mine".equals(repUser)) {
      getWapReportListByDay(request, userId, "list");
    } else if ("other".equals(repUser)) {
      int empCount = Integer.parseInt((new WapAction()).getUnderEmpIdString(userId, false));
      if (empCount > 0) {
        String userIdStr = (new WapSchemeAction()).getEmpStr(userId);
        getWapReportListByDay(request, userIdStr, "");
      } 
    } else {
      System.out.println("repUser:" + repUser);
    } 
    return mapping.findForward(WapStringTool.getForwardStr(version, "showReportList"));
  }
  
  public ActionForward getWapWMRepList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession(true);
    String version = (String)session.getAttribute("wapVersion");
    String userId = (String)session.getAttribute("userId");
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    String repUser = request.getParameter("repUser");
    String repType = request.getParameter("repType");
    getReportListByWM(request, userId, repType, "", repUser, domainId);
    request.setAttribute("repUser", repUser);
    request.setAttribute("repType", repType);
    return mapping.findForward(WapStringTool.getForwardStr(version, "showWMReportList"));
  }
  
  public void getWapReportListByDay(HttpServletRequest request, String userIdStr, String indexTag) {
    WapBD cdb = new WapBD();
    String showListAttName = null;
    String recordCountAttName = null;
    String where = null;
    if (userIdStr.contains(",")) {
      showListAttName = "unReportList";
      recordCountAttName = "unRecordCount";
      where = " in(" + userIdStr + ")";
    } else {
      showListAttName = "myReportList";
      recordCountAttName = "myRecordCount";
      where = "=" + userIdStr;
    } 
    StringBuffer hql = new StringBuffer("SELECT  workLog  FROM com.js.oa.scheme.worklog.po.WorkLogPO workLog  ");
    hql.append(" where workLog.createdEmp" + where);
    hql.append(" order by workLog.logDate desc,workLog.logId desc ");
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limited = "index".equals(indexTag) ? 5 : WapUtil.LIMITED;
    Map dayReportMap = cdb.getReportByDay(hql.toString(), beginIndex, limited);
    List showList = (List)dayReportMap.get("QUERY_LIST");
    int recordCount = ((Integer)dayReportMap.get("RECORD_COUNT")).intValue();
    request.setAttribute(showListAttName, showList);
    request.setAttribute(recordCountAttName, Integer.valueOf(recordCount));
  }
  
  public void getReportListByWM(HttpServletRequest request, String userId, String reportType, String indexTag, String repUser, Long domainId) {
    WapBD cdb = new WapBD();
    String listStr = "";
    String countStr = "";
    StringBuffer hql = null;
    String where = null;
    if ("mine".equals(repUser)) {
      if ("1".equals(reportType)) {
        listStr = "mineWeekReportList";
        countStr = "mineWeekReportCount";
      } else if ("3".equals(reportType)) {
        listStr = "mineMonthReportList";
        countStr = "mineMonthReportCount";
      } else {
        System.out.println("reportType:" + reportType);
      } 
      where = " where workReport.empId=" + userId + " and(workReport.reportType=" + reportType + ") and(workReport.reportDomainId=" + domainId + ")";
      hql = new StringBuffer("SELECT  workReport  FROM com.js.oa.scheme.workreport.po.WorkReportPO workReport ");
      hql.append(where);
      hql.append(" order by workReport.hadRead ,workReport.reportTime desc, workReport.id desc ");
    } else if ("other".equals(repUser)) {
      if ("1".equals(reportType)) {
        listStr = "otherWeekReportList";
        countStr = "otherWeekReportCount";
      } else if ("3".equals(reportType)) {
        listStr = "otherMonthReportList";
        countStr = "otherMonthReportCount";
      } else {
        System.out.println("reportType:" + reportType);
      } 
      where = " and leader.empId=" + userId + " and (workReport.reportType=" + reportType + ") and (workReport.reportDomainId=" + domainId + ")";
      hql = new StringBuffer("SELECT leader FROM com.js.oa.scheme.workreport.po.WorkReportLeaderPO leader join leader.report workReport where (1=1)");
      hql.append(where);
      hql.append(" order by workReport.reportCourse desc , workReport.reportTime desc ");
    } else {
      System.out.println("repUser:" + repUser);
    } 
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limited = "index".equals(indexTag) ? 5 : WapUtil.LIMITED;
    Map wmReportMap = cdb.getReportByWeek(hql.toString(), beginIndex, limited);
    List showList = (List)wmReportMap.get("QUERY_LIST");
    int recordCount = ((Integer)wmReportMap.get("RECORD_COUNT")).intValue();
    request.setAttribute(listStr, showList);
    request.setAttribute(countStr, Integer.valueOf(recordCount));
  }
}
