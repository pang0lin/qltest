package com.js.oa.weixin.workReport;

import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.oa.weixin.rcap.WeiXinRcapAction;
import com.js.wap.WapAction;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeixinReportAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String action = request.getParameter("action");
    String repUser = request.getParameter("repUser");
    String repType = request.getParameter("repType");
    HttpSession session = request.getSession(true);
    String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
    request.setAttribute("appBh", "gzhb");
    if (keyword != null && !"".equals(keyword))
      keyword = URLDecoder.decode(keyword, "utf-8"); 
    String tag = "";
    if ("getWapRepList".equals(action))
      tag = getWapRepList(request, session, repUser, keyword); 
    if ("getWapWMRepList".equals(action))
      tag = getWapWMRepList(request, session, repUser, repType, keyword); 
    return mapping.findForward(tag);
  }
  
  private String getWapRepList(HttpServletRequest request, HttpSession session, String repUser, String keyword) throws Exception {
    String userId = session.getAttribute("userId").toString();
    if ("mine".equals(repUser)) {
      getWapReportListByDay(request, userId, "list", keyword);
    } else if ("other".equals(repUser)) {
      int empCount = Integer.parseInt((new WapAction()).getUnderEmpIdString(userId, false));
      if (empCount > 0) {
        String userIdStr = (new WeiXinRcapAction()).getEmpStr(userId);
        getWapReportListByDay(request, userIdStr, "", keyword);
      } 
    } else {
      System.out.println("repUser:" + repUser);
    } 
    return "showReportList";
  }
  
  private String getWapWMRepList(HttpServletRequest request, HttpSession session, String repUser, String repType, String keyword) throws Exception {
    String userId = session.getAttribute("userId").toString();
    Long domainId = (session.getAttribute("domainId") == null) ? Long.valueOf("0") : Long.valueOf(session.getAttribute("domainId").toString());
    getReportListByWM(request, userId, repType, "", repUser, domainId, keyword);
    request.setAttribute("repUser", repUser);
    request.setAttribute("repType", repType);
    return "showWMReportList";
  }
  
  private void getWapReportListByDay(HttpServletRequest request, String userIdStr, String indexTag, String keyword) {
    WeiXinBD cdb = new WeiXinBD();
    String showListAttName = null;
    String recordCountAttName = null;
    String where = null;
    HttpSession session = request.getSession(true);
    if (userIdStr.contains(",") || !session.getAttribute("userId").toString().equals(userIdStr)) {
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
    if (keyword != null && !"".equals(keyword)) {
      hql.append(" and (workLog.logContent like '%" + keyword + "%'");
      if (userIdStr.contains(","))
        hql.append(" or workLog.empName like '%" + keyword + "%'"); 
      hql.append(")");
    } 
    hql.append(" order by workLog.logDate desc,workLog.logId desc ");
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    int limited = "index".equals(indexTag) ? 5 : WapUtil.LIMITED;
    Map dayReportMap = cdb.getReportByDay(hql.toString(), beginIndex, limited);
    List showList = (List)dayReportMap.get("QUERY_LIST");
    int recordCount = ((Integer)dayReportMap.get("RECORD_COUNT")).intValue();
    request.setAttribute(showListAttName, showList);
    request.setAttribute(recordCountAttName, Integer.valueOf(recordCount));
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    request.setAttribute("keyword", keyword);
  }
  
  public void getReportListByWM(HttpServletRequest request, String userId, String reportType, String indexTag, String repUser, Long domainId, String keyword) {
    WeiXinBD cdb = new WeiXinBD();
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
      where = " where workReport.empId=" + userId + " and (workReport.reportType=" + reportType + ") and (workReport.reportDomainId=" + domainId + ") ";
      hql = new StringBuffer("SELECT  workReport  FROM com.js.oa.scheme.workreport.po.WorkReportPO workReport ");
      hql.append(where);
      if (keyword != null && !"".equals(keyword))
        hql.append(" and workReport.previousReport like '%" + keyword + "%' "); 
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
      hql.append(" and (workReport.previousReport like '%" + keyword + "%' or workReport.reportEmpName like '%" + keyword + "%') ");
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
    request.setAttribute("beginIndex", Integer.valueOf(beginIndex));
    request.setAttribute("keyword", keyword);
  }
}
