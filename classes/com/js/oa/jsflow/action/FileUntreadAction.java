package com.js.oa.jsflow.action;

import com.js.oa.jsflow.service.WorkFlowBD;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FileUntreadAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    FileDealWithActionForm fileDealWithActionForm = (FileDealWithActionForm)actionForm;
    HttpSession session = httpServletRequest.getSession(true);
    if (httpServletRequest.getParameter("orderBy") != null && !httpServletRequest.getParameter("orderBy").equals("null")) {
      httpServletRequest.setAttribute("orderBy", httpServletRequest.getParameter("orderBy"));
    } else {
      httpServletRequest.setAttribute("orderBy", "");
    } 
    if (httpServletRequest.getParameter("sortType") != null && !httpServletRequest.getParameter("sortType").equals("null")) {
      httpServletRequest.setAttribute("sortType", httpServletRequest.getParameter("sortType"));
    } else {
      httpServletRequest.setAttribute("sortType", "");
    } 
    String type = httpServletRequest.getParameter("type");
    if (type != null && type.equals("singleDel")) {
      String workId = httpServletRequest.getParameter("workId");
      String recordId = httpServletRequest.getParameter("recordId");
      String fileType = httpServletRequest.getParameter("fileType");
      List<String> sqlList = new ArrayList();
      sqlList.add("update JSF_WORK set workDelete = 1 where wf_work_id = " + workId);
      if (StringUtils.isNotEmpty(recordId)) {
        if (StringUtils.isNotEmpty(fileType) && "receiveFile".equals(fileType))
          sqlList.add("delete from doc_receivefile where RECEIVEFILE_ID=" + recordId); 
        if (StringUtils.isNotEmpty(fileType) && "sendFile".equals(fileType))
          sqlList.add("delete from doc_documentsendfile where DOCUMENTSENDFILE_ID=" + recordId); 
      } 
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workFlowBD.updateTable(sqlList);
    } 
    if (type != null && type.equals("batchDel")) {
      String[] workId = httpServletRequest.getParameterValues("batchId");
      List<String> sqlList = new ArrayList();
      for (int i = 0; i < workId.length; i++)
        sqlList.add("update JSF_WORK set workDelete = 1 where wf_work_id = " + workId[i]); 
      WorkFlowBD workFlowBD = new WorkFlowBD();
      workFlowBD.updateTable(sqlList);
    } 
    if (httpServletRequest.getParameter("flag") != null) {
      HttpSession httpSession = httpServletRequest.getSession(true);
      String orderBy = httpServletRequest.getParameter("orderBy");
      String sortType = httpServletRequest.getParameter("sortType");
      String tempSort = "";
      if (orderBy != null && !orderBy.equals("null") && orderBy != "")
        tempSort = " aaa." + orderBy + " " + sortType + " , "; 
      String workStatus = httpServletRequest.getParameter("workStatus");
      String workTitle = httpServletRequest.getParameter("workTitle");
      String viewSql = " aaa.workFileType, aaa.workTitle, aaa.workType,  aaa.workTableId, aaa.workRecordId, aaa.wfWorkId,  aaa.workCancelReason, aaa.workCreateDate, aaa.workSubmitPerson,  aaa.workProcessId, aaa.workMainLinkFile, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.submitOrg, aaa.workActivity, aaa.workCurStep";
      viewSql = String.valueOf(viewSql) + ",aaa.isSubProcWork,aaa.pareProcActiId,aaa.pareStepCount,aaa.pareTableId,aaa.pareRecordId,aaa.pareProcNextActiId";
      String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
      String dateWhere = "";
      if ("1".equals(httpServletRequest.getParameter("searchDate"))) {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          dateWhere = " and aaa.workCreateDate between '" + httpServletRequest.getParameter("searchBeginDate") + " 00:00:00' and '" + 
            httpServletRequest.getParameter("searchEndDate") + " 23:59:59' ";
        } else {
          dateWhere = " and aaa.workCreateDate between JSDB.FN_STRTODATE('" + httpServletRequest.getParameter("searchBeginDate") + " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
            httpServletRequest.getParameter("searchEndDate") + " 23:59:59','L') ";
        } 
      } 
      String whereSql = " where aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
        httpSession.getAttribute("userId").toString() + " and aaa.workTitle " + 
        " like '%" + workTitle + "%' and aaa.workStatus = " + workStatus + " and aaa.workDelete = 0 " + 
        dateWhere + " order by " + tempSort + " aaa.wfWorkId desc";
      if ("10".equals(workStatus))
        whereSql = " where aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
          httpSession.getAttribute("userId").toString() + " and aaa.workTitle " + 
          " like '%" + workTitle + "%' and aaa.workStatus in (-1,-2,1,100) and aaa.workDelete = 0 " + 
          dateWhere + " order by " + tempSort + " aaa.wfWorkId desc"; 
      list(httpServletRequest, viewSql, fromSql, whereSql);
    } else {
      view(httpServletRequest);
    } 
    return actionMapping.findForward("success");
  }
  
  public void view(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String orderBy = httpServletRequest.getParameter("orderBy");
    String sortType = httpServletRequest.getParameter("sortType");
    String workTitle = httpServletRequest.getParameter("workTitle");
    httpServletRequest.setAttribute("workTitle", workTitle);
    String tempSort = "";
    if (orderBy != null && !orderBy.equals("null") && orderBy != "")
      tempSort = " aaa." + orderBy + " " + sortType + " , "; 
    String workStatus = httpServletRequest.getParameter("workStatus");
    String viewSql = " aaa.workFileType, aaa.workTitle, aaa.workType,  aaa.workTableId, aaa.workRecordId, aaa.wfWorkId,  aaa.workCancelReason, aaa.workCreateDate, aaa.workSubmitPerson,  aaa.workProcessId, aaa.workMainLinkFile, aaa.workSubmitPerson, aaa.workSubmitTime, aaa.submitOrg, aaa.workActivity, aaa.workCurStep";
    viewSql = String.valueOf(viewSql) + ",aaa.isSubProcWork,aaa.pareProcActiId,aaa.pareStepCount,aaa.pareTableId,aaa.pareRecordId,aaa.pareProcNextActiId";
    String fromSql = " com.js.oa.jsflow.po.WFWorkPO aaa ";
    String whereSql = " where aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
      httpSession.getAttribute("userId").toString() + " and " + 
      " aaa.workStatus = " + workStatus + " and aaa.workDelete = 0";
    if ("10".equals(workStatus))
      whereSql = " where aaa.workStartFlag = 1 and aaa.wfCurEmployeeId = " + 
        httpSession.getAttribute("userId").toString() + " and " + 
        " aaa.workStatus in (-1,-2,1,100) and aaa.workDelete = 0 "; 
    if (workTitle != null && !"null".equals(workTitle) && !"".equals(workTitle))
      whereSql = String.valueOf(whereSql) + " and aaa.workTitle like '%" + workTitle + "%'"; 
    whereSql = String.valueOf(whereSql) + " order by " + tempSort + " aaa.wfWorkId desc";
    list(httpServletRequest, viewSql, fromSql, whereSql);
  }
  
  public void list(HttpServletRequest httpServletRequest, String viewSql, String fromSql, String whereSql) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    Page page = new Page(viewSql, fromSql, whereSql);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("workList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "flag,workStatus,workTitle,searchDate,searchBeginDate,searchEndDate,orderBy,sortType");
  }
}
