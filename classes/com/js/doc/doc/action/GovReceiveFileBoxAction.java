package com.js.doc.doc.action;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.doc.doc.util.DateToStringCN;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ParameterFilter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovReceiveFileBoxAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    if (!ParameterFilter.isNumber(request.getParameter("pager.offset")) || 
      !ParameterFilter.checkParameter(request.getParameter("viewType")) || 
      !ParameterFilter.checkParameter(request.getParameter("isEdit")) || 
      !ParameterFilter.checkParameter(request.getParameter("canEdit")))
      try {
        return new ActionForward("/public/jsp/inputerror.jsp");
      } catch (Exception exception) {} 
    String action = request.getParameter("action");
    GovSendFileActionForm govSendFileActionForm = 
      (GovSendFileActionForm)actionForm;
    if ("list".equals(action)) {
      if (request.getParameter("tag") != null && "notRead".equals(request.getParameter("tag").toString()))
        request.setAttribute("tag", "notRead"); 
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("load".equals(action)) {
      ActionForward forward = new ActionForward();
      forward.setPath(modify(request, httpServletResponse));
      return forward;
    } 
    if ("loadFile".equals(action)) {
      if (request.getParameter("transmitType") != null && !request.getParameter("transmitType").equals(""))
        request.setAttribute("transmitType", request.getParameter("transmitType").toString()); 
      return actionMapping.findForward(load(request, govSendFileActionForm));
    } 
    if ("userinfo".equals(action) || "userSearch".equals(action)) {
      userinfo(request);
      return actionMapping.findForward("userinfo");
    } 
    if ("delBatch".equals(action)) {
      delete(request);
      return actionMapping.findForward("list");
    } 
    return actionMapping.findForward("list");
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSesison = request.getSession(true);
    ManagerService mbd = new ManagerService();
    Object object1 = request.getSession(true).getAttribute("userId");
    Object object2 = request.getSession(true).getAttribute("orgId");
    Object object3 = request.getSession(true).getAttribute("orgIdString");
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("1=1 ");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String querySecret = request.getParameter("querySecret");
    String queryOrg = request.getParameter("queryOrg");
    String queryItem = request.getParameter("queryItem");
    String queryBeginDate = request.getParameter("queryBeginDate");
    String queryEndDate = request.getParameter("queryEndDate");
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.documentSendFileByteNumber like '%").append(
          queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.documentSendFileTitle like '%").append(queryTitle)
        .append("%'"); 
    if (querySecret != null && !"none".equals(querySecret))
      sb.append(" and po.documentSendFileSecurityGrade = '").append(querySecret)
        .append("'"); 
    if (queryOrg != null && !"none".equals(queryOrg) && !"null".equals(queryOrg))
      sb.append(" and po.documentSendFileWriteOrg like '%").append(queryOrg)
        .append("%'"); 
    String databaseType = SystemCommon.getDatabaseType();
    if ("1".equals(queryItem))
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.createdTime between '").append(
            queryBeginDate).append(" 00:00:00").append("' and '")
          .append(queryEndDate).append(" 23:59:59").append("'")
          .append(" or  po.createdTime between '").append(
            queryEndDate).append(" 00:00:00").append(
            "' and '").append(queryBeginDate).append(
            " 23:59:59").append("' )");
      } else {
        sb
          .append(
            " and ( po.createdTime between JSDB.FN_STRTODATE('")
          .append(queryBeginDate)
          .append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('")
          .append(queryEndDate)
          .append(" 23:59:59")
          .append("','L')")
          .append(" or  po.createdTime between JSDB.FN_STRTODATE('")
          .append(queryEndDate).append(" 00:00:00").append(
            "','L') and JSDB.FN_STRTODATE('").append(
            queryBeginDate).append(" 23:59:59").append(
            "','L') )");
      }  
    sb.append(" and ( sendFileUser.outSeeType is null or sendFileUser.outSeeType <> '1') ");
    String findScope = mbd.getRightFinalWhere((String)object1, (String)object2, (String)object3, 
        "公文管理_收文员", "查看", "sendFileUser.orgId", 
        "sendFileUser.empId");
    findScope = String.valueOf(findScope) + " and sendFileUser.orgId is not null ";
    if (request.getParameter("tag") != null && "notRead".equals(request.getParameter("tag").toString())) {
      String findSql = "sendFileUser.empId=" + object1;
      findSql = String.valueOf(findSql) + " or ( " + findScope + " and (select count(userpo2.id) from com.js.doc.doc.po.GovSendFileUserPO userpo2 where userpo2.empId= " + 
        object1 + " and userpo2.outSeeType='1')=0) ";
      sb.append(" and (").append(findSql).append(")");
      sb.append(" and ( sendFileUser.isReaded is null or sendFileUser.isReaded <> 1 )");
    } else {
      String findSql = "sendFileUser.empId=" + object1;
      findSql = String.valueOf(findSql) + " or ( " + findScope + " )";
      sb.append(" and (").append(findSql).append(")");
    } 
    if (request.getParameter("redHeadId") != null && !request.getParameter("redHeadId").toUpperCase().trim().equals("NULL"))
      sb.append(" and ( po.documentSendFileHead='" + request.getParameter("redHeadId") + "') "); 
    if (request.getParameter("numId") != null && !request.getParameter("numId").toString().equals("")) {
      sb.append(" and ( po.sendFilePoNumId= " + request.getParameter("numId") + " )");
    } else if (request.getParameter("numType") != null && !request.getParameter("numType").toString().equals("")) {
      List<SendDocumentNumPO> numList = (new SenddocumentBD()).getSendNumByNumClass(request.getParameter("numType"));
      String sqlStr = "";
      if (numList != null && numList.size() > 0)
        for (int i = 0; i < numList.size(); i++) {
          SendDocumentNumPO po = numList.get(i);
          sqlStr = String.valueOf(sqlStr) + po.getId() + ",";
        }  
      if (sqlStr != null && sqlStr.length() > 1) {
        sqlStr = sqlStr.substring(0, sqlStr.length() - 1);
        sb.append(" and ( po.sendFilePoNumId in (" + sqlStr + " ) )");
      } 
    } 
    try {
      Page page = new Page(" distinct po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime,po.createdEmp,po.createdOrg,po.sendFileLink,po.sendFileOverSee,sendFileUser.id,po.goldGridId,sendFileUser.isReaded ,sendFileUser.outSeeType,sendFileUser.orgId,po.documentWordType,po.tableId", 
          " com.js.doc.doc.po.GovDocumentSendFilePO po join po.sendFileUser sendFileUser", 
          " where " + sb + " and po.sendFileOverSee<>2" + " and po.domainId=" + domainId + 
          " order by po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 15) {
        offset -= 15;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,queryItem,queryBeginDate,queryEndDate,queryNumber,querySecret,queryTitle,queryOrg,redHeadId,tag");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private String load(HttpServletRequest httpServletRequest, GovSendFileActionForm govSendFileActionForm) {
    HttpSession httpSesison = httpServletRequest.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    String recordId = httpServletRequest.getParameter("editId");
    if (recordId == null || recordId.equals(""))
      recordId = httpServletRequest.getParameter("record"); 
    SendFileBD bd = new SendFileBD();
    GovDocumentSendFilePO po = bd.load(recordId);
    govSendFileActionForm.setDocumentSendFileSecurityGrade(po
        .getDocumentSendFileSecurityGrade());
    govSendFileActionForm.setSendFileGrade(po.getSendFileGrade());
    govSendFileActionForm.setSendFileRedHeadPic(po.getSendFileRedHeadPic());
    govSendFileActionForm.setDocumentSendFileByteNumber(po
        .getDocumentSendFileByteNumber());
    govSendFileActionForm.setSendFileType(po.getSendFileType());
    govSendFileActionForm.setDocumentSendFileTitle(po.getDocumentSendFileTitle());
    govSendFileActionForm.setSendFileText(po.getSendFileText());
    if (po.getHtmlContent() != null && !po.getHtmlContent().equals(""))
      httpServletRequest.setAttribute("htmlContent", po.getHtmlContent()); 
    govSendFileActionForm.setSendFileSealPic(po.getSendFileSealPic());
    DateToStringCN dateToStringCN = new DateToStringCN();
    String dateString = DateToStringCN.convertObjectToYearMonthDateCN(po
        .getDocumentSendFileSendDate());
    httpServletRequest.setAttribute("dateString", dateString);
    httpServletRequest.setAttribute("accessoryName", po.getAccessoryName());
    httpServletRequest.setAttribute("accessorySaveName", 
        po.getAccessorySaveName());
    httpServletRequest.setAttribute("contentAccName", "正文" + po.getDocumentWordType());
    httpServletRequest.setAttribute("contentAccSaveName", String.valueOf(po.getGoldGridId()) + po.getDocumentWordType());
    govSendFileActionForm.setDocumentSendFileTopicWord(po
        .getDocumentSendFileTopicWord());
    String sendToType = po.getSendToType();
    if (sendToType != null) {
      govSendFileActionForm.setSendToType(sendToType);
      if ("0".equals(sendToType)) {
        govSendFileActionForm.setToPerson1(po.getMainToName());
        govSendFileActionForm.setToPerson2(po.getCopyToName());
      } else if ("1".equals(sendToType)) {
        govSendFileActionForm.setToPerson3(po.getMainToName());
        govSendFileActionForm.setToPerson4(po.getSendRoundName());
        govSendFileActionForm.setToPerson5(po.getSunderToName());
      } else if ("2".equals(sendToType)) {
        govSendFileActionForm.setToPerson6(po.getSunderToName());
      } 
    } else {
      govSendFileActionForm.setSendToType("0");
    } 
    govSendFileActionForm.setDocumentSendFileWriteOrg(po
        .getDocumentSendFileWriteOrg());
    govSendFileActionForm.setDocumentSendFileAssumePeople(po
        .getDocumentSendFileAssumePeople());
    govSendFileActionForm.setDocumentSendFileAssumeUnit(po
        .getDocumentSendFileAssumeUnit());
    govSendFileActionForm.setDocumentSendFileCheckCommit(po
        .getDocumentSendFileCheckCommit());
    govSendFileActionForm.setDocumentSendFileCheckDate(po
        .getDocumentSendFileCheckDate());
    govSendFileActionForm.setDocumentSendFileCounterSign(po
        .getDocumentSendFileCounterSign());
    govSendFileActionForm.setDocumentSendFileHead(po.getDocumentSendFileHead());
    govSendFileActionForm.setDocumentSendFilePrintNumber((new StringBuilder(
          String.valueOf(po.getDocumentSendFilePrintNumber()))).toString());
    govSendFileActionForm.setDocumentSendFileSendFile(po
        .getDocumentSendFileSendFile());
    httpServletRequest.setAttribute("sendFileDate", po.getDocumentSendFileDate());
    httpServletRequest.setAttribute("sendFileSendDate", 
        po.getDocumentSendFileSendDate());
    govSendFileActionForm.setDocumentSendFileWriteOrg(po
        .getDocumentSendFileWriteOrg());
    httpServletRequest.setAttribute("sendFileWriteOrg", 
        po.getDocumentSendFileWriteOrg());
    httpServletRequest.setAttribute("sendFileTopicWord", 
        po.getDocumentSendFileTopicWord());
    govSendFileActionForm.setSendToId(String.valueOf(po.getSendToOrg()) + po.getSendToGroup() + 
        po.getSendToEmp());
    govSendFileActionForm.setSendToName(po.getSendToName());
    govSendFileActionForm.setSendFileAccessoryDesc(po.getSendFileAccessoryDesc());
    govSendFileActionForm.setSendFileAgentDraft(po.getSendFileAgentDraft());
    govSendFileActionForm.setSendFileDraft(po.getSendFileDraft());
    govSendFileActionForm.setSendFileMassDraft(po.getSendFileMassDraft());
    govSendFileActionForm.setSendFilePrinter(po.getSendFilePrinter());
    govSendFileActionForm.setSendFileProof(po.getSendFileProof());
    govSendFileActionForm.setSendFileProveDraft(po.getSendFileProveDraft());
    govSendFileActionForm.setSendFileReadComment(po.getSendFileReadComment());
    govSendFileActionForm.setReceiveFileIsFlowMode(po.getReceiveFileIsFlowMode());
    govSendFileActionForm.setSendFileRedHeadId(po.getSendFileRedHeadId());
    govSendFileActionForm.setSendFileSealId(po.getSendFileSealId());
    govSendFileActionForm.setDocumentSendFileSecurityGrade(po
        .getDocumentSendFileSecurityGrade());
    govSendFileActionForm.setDocumentSendFileByteNumber((po.getDocumentSendFileByteNumber() == null) ? "" : po.getDocumentSendFileByteNumber());
    govSendFileActionForm.setZjkySeq((po.getZjkySeq() == null) ? "" : po.getZjkySeq());
    govSendFileActionForm.setDocumentWordType(po.getDocumentWordType());
    httpServletRequest.setAttribute("sendToType", sendToType);
    httpServletRequest.setAttribute("myform", govSendFileActionForm);
    httpServletRequest.setAttribute("queryStr", httpServletRequest.getQueryString());
    HttpSession httpSession = httpServletRequest.getSession(true);
    bd.setSendFileBrower(recordId, 
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("userName").toString(), 
        httpSession.getAttribute("orgName").toString(), 
        domainId);
    String sendFileType = (po.getSendFileType() == null) ? "0" : 
      po.getSendFileType();
    if (sendFileType.equals("2"))
      return "modiTele"; 
    return "modi";
  }
  
  private void userinfo(HttpServletRequest httpServletRequest) {
    String recordId = httpServletRequest.getParameter("editId");
    String showType = httpServletRequest.getParameter("type");
    Integer type = Integer.valueOf("0");
    if (showType.equals("showNotRead"))
      type = Integer.valueOf("1"); 
    httpServletRequest.setAttribute("showType", type);
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null && 
      !httpServletRequest.getParameter("pager.offset").equals("null"))
      offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String viewSQL = " PO.userName,PO.orgName,PO.readDate ";
    String fromSQL = " com.js.doc.doc.po.GovSendFileUserPO PO ";
    String whereSQL = "where PO.sendFile.id=" + recordId + " and isReaded=1 ";
    if (showType.equals("showNotRead")) {
      viewSQL = " PO.userName,PO.orgName ";
      fromSQL = " com.js.doc.doc.po.GovSendFileUserPO PO,  com.js.system.vo.usermanager.EmployeeVO emp";
      whereSQL = "where PO.empId=emp.empId and emp.userIsActive<>0 and emp.userIsDeleted<>1 and PO.sendFile.id=" + recordId + " and ( PO.isReaded is null or PO.isReaded  = 0 ) ";
    } 
    if (httpServletRequest.getParameter("userName") != null)
      whereSQL = String.valueOf(whereSQL) + " and PO.userName like '%" + httpServletRequest.getParameter("userName") + "%'"; 
    Page page = new Page(viewSQL, fromSQL, whereSQL);
    page.setPageSize(pageSize);
    page.setcurrentPage(currentPage);
    List list = page.getResultList();
    if (list.size() == 0 && offset >= 15) {
      offset -= 15;
      currentPage = offset / pageSize + 1;
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      list = page.getResultList();
      httpServletRequest.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
      httpServletRequest.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
    } 
    String recordCount = String.valueOf(page.getRecordCount());
    String pageCount = String.valueOf(page.getPageCount());
    httpServletRequest.setAttribute("uList", list);
    httpServletRequest.setAttribute("recordCount", recordCount);
    httpServletRequest.setAttribute("pageCount", pageCount);
    httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
    httpServletRequest.setAttribute("pageParameters", "action,type,editId,userName");
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    HttpSession httpSesison = httpServletRequest.getSession(true);
    String domainId = (httpSesison.getAttribute("domainId") == null) ? "0" : httpSesison.getAttribute("domainId").toString();
    String sendFileUserId = httpServletRequest.getParameter("sendFileUserId");
    SendFileBD bd = new SendFileBD();
    if (httpServletRequest.getParameter("num") == null) {
      bd.removeSendUser(sendFileUserId);
    } else {
      bd.removeSendUser(sendFileUserId, httpSesison.getAttribute("userId").toString());
    } 
    String recordId = httpServletRequest.getParameter("editId");
    HttpSession httpSession = httpServletRequest.getSession(true);
    bd.setSendFileBrower(recordId, 
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("userName").toString(), 
        httpSession.getAttribute("orgName").toString(), 
        domainId);
    list(httpServletRequest);
  }
  
  private String modify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String tableId = "";
    if (httpServletRequest.getParameter("tableId") != null) {
      tableId = httpServletRequest.getParameter("tableId");
    } else {
      tableId = (httpServletRequest.getParameter("table") != null) ? httpServletRequest.getParameter("table").toString() : "";
    } 
    Map workMap = wfcBD.getWorkInfoByTableID(httpServletRequest.getParameter("editId"), tableId);
    String activityName = (workMap.get("activityName") == null) ? "" : URLEncoder.encode(workMap.get("activityName").toString());
    String submitPerson = (workMap.get("submitPerson") == null) ? "" : URLEncoder.encode(workMap.get("submitPerson").toString());
    String processName = (workMap.get("processName") == null) ? "" : URLEncoder.encode(workMap.get("processName").toString());
    String standForUserName = (workMap.get("standForUserName") == null) ? "" : URLEncoder.encode(workMap.get("standForUserName").toString());
    String canEdit = (httpServletRequest.getParameter("canEdit") == null) ? "0" : httpServletRequest.getParameter("canEdit").toString();
    String isEdit = (httpServletRequest.getParameter("isEdit") == null) ? "0" : httpServletRequest.getParameter("isEdit").toString();
    String viewType = (httpServletRequest.getParameter("viewType") == null) ? "0" : httpServletRequest.getParameter("viewType").toString();
    String pageroffset = (httpServletRequest.getParameter("pager.offset") == null) ? "0" : httpServletRequest.getParameter("pager.offset").toString();
    String showReceiveFile = (httpServletRequest.getParameter("showReceiveFile") == null) ? "0" : httpServletRequest.getParameter("showReceiveFile").toString();
    String myFile = (httpServletRequest.getParameter("myFile") == null) ? "0" : httpServletRequest.getParameter("myFile").toString();
    String transmitType = (httpServletRequest.getParameter("transmitType") == null) ? "0" : httpServletRequest.getParameter("transmitType").toString();
    String sendFileLink = "/GovReceiveFileBoxAction.do?action=loadFile&editId=" + httpServletRequest.getParameter("editId") + "&canEdit=" + 
      canEdit + "&isEdit=" + isEdit + "&viewType=" + viewType + "&pager.offset=" + pageroffset + "&showReceiveFile=" + showReceiveFile + 
      "&myFile=" + myFile + "&transmitType=" + transmitType;
    sendFileLink = String.valueOf(sendFileLink) + "&activityName=" + activityName + 
      "&submitPersonId=" + workMap.get("submitPersonId") + 
      "&submitPerson=" + submitPerson + 
      "&work=" + workMap.get("work") + "&workType=" + 
      workMap.get("workType") + 
      "&activity=" + 
      workMap.get("activity") + "&table=" + 
      workMap.get("tableId") + 
      "&record=" + 
      httpServletRequest.getParameter("editId") + 
      "&processName=" + 
      processName + 
      "&workStatus=1&submitTime=" + 
      workMap.get("submitTime") + 
      "&processId=" + 
      workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + 
      "&isStandForWork=" + 
      workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + 
      "&standForUserName=" + standForUserName + 
      "&isEdit=1&submitPersonTime=" + 
      workMap.get("submitTime");
    return sendFileLink;
  }
}
