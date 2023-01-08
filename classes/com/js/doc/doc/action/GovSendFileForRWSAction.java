package com.js.doc.doc.action;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.GovTypeSetPO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.po.SendDocumentSeqPO;
import com.js.doc.doc.po.SendDocumentWordPO;
import com.js.doc.doc.service.GovTypeSetBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.message.RealTimeUtil;
import com.js.oa.archives.service.ArchivesBD;
import com.js.oa.form.SendFile;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.jsflow.service.PackageBD;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.search.client.SearchService;
import com.js.oa.security.log.service.LogBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.MessagesBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.util.config.SystemCommon;
import com.js.util.page.Page;
import com.js.util.util.ConversionString;
import com.js.util.util.DataSourceBase;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovSendFileForRWSAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovSendFileActionForm govSendFileActionForm = 
      (GovSendFileActionForm)actionForm;
    String action = request.getParameter("action");
    String status = "";
    if ("yuguidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "weiguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.yuguidang("DOC_DOCUMENTSENDFILE", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
      request.setAttribute("optMsg", "预归档成功！");
    } 
    if ("chexiaoguidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.quxiaoguidang("DOC_DOCUMENTSENDFILE", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
      request.setAttribute("optMsg", "驳回归档成功！");
    } 
    if ("guidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.guidang("DOC_DOCUMENTSENDFILE", recordId, request.getSession().getAttribute("userId").toString(), "");
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
      request.setAttribute("optMsg", "归档成功！");
    } 
    if ("tongyiguidang".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yuguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.tongyiguidang("DOC_DOCUMENTSENDFILE", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
    } 
    if ("guidangbukejian".equals(action)) {
      String recordId = request.getParameter("id");
      status = "yiguidang";
      WorkFlowEJBBeanForRWS ejb = new WorkFlowEJBBeanForRWS();
      try {
        ejb.guidangbukejian("DOC_DOCUMENTSENDFILE", recordId, request.getSession().getAttribute("userId").toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      action = "list";
    } 
    if ("see".equals(action)) {
      see(request, govSendFileActionForm);
      String receiveFileTitle = (request.getParameter("receiveFileTitle") != null) ? request.getParameter("receiveFileTitle").toString() : "";
      request.setAttribute("documentSendFileTitle", receiveFileTitle);
      String processId = (request.getParameter("processId") != null) ? request.getParameter("processId").toString() : "";
      String processName = (request.getParameter("processName") != null) ? request.getParameter("processName").toString() : "";
      String processType = (request.getParameter("processType") != null) ? request.getParameter("processType").toString() : "";
      String tableId = (request.getParameter("tableId") != null) ? request.getParameter("tableId").toString() : "";
      String remindField = (request.getParameter("remindField") != null) ? request.getParameter("remindField").toString() : "";
      SendFileBD sendFileBD = new SendFileBD();
      List<Object[]> tableInfoList = sendFileBD.getWfTableInfoByTableId(tableId);
      Object object = "";
      String url = "action=see&processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField;
      if (tableInfoList != null && tableInfoList.size() > 0) {
        Object[] tableInfoObj = tableInfoList.get(0);
        object = tableInfoObj[0];
      } 
      if (object.equals("发文表"))
        return actionMapping.findForward("add"); 
      ActionForward forward = new ActionForward();
      url = "/doc/doc/" + tableId + "_0_add.jsp?processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField + "&tableId=" + tableId;
      forward.setPath(url);
      return forward;
    } 
    if ("initNumber".equals(action)) {
      initNumber(request);
      intZjkySendWord(request);
      return actionMapping.findForward("initnumber");
    } 
    if ("initZjkySendWord".equals(action)) {
      intZjkySendWord(request);
      return actionMapping.findForward("zjkyinitnumber");
    } 
    if ("selRedHead".equals(action)) {
      selRedHead(request);
      return actionMapping.findForward("selRedHead");
    } 
    if ("draft".equals(action)) {
      draft(request);
      return actionMapping.findForward("sclose");
    } 
    if ("directSend".equals(action)) {
      directSend(request);
      return actionMapping.findForward("add");
    } 
    if ("supplySend".equals(action)) {
      supplySend(request);
      return actionMapping.findForward("modify");
    } 
    if ("draftModify".equals(action)) {
      draftLoad(request, govSendFileActionForm);
      String processId = (request.getParameter("processId") != null) ? request.getParameter("processId").toString() : "";
      String processName = (request.getParameter("processName") != null) ? request.getParameter("processName").toString() : "";
      String processType = (request.getParameter("processType") != null) ? request.getParameter("processType").toString() : "";
      String tableId = (request.getParameter("tableId") != null) ? request.getParameter("tableId").toString() : "";
      String remindField = (request.getParameter("remindField") != null) ? request.getParameter("remindField").toString() : "";
      SendFileBD sendFileBD = new SendFileBD();
      List<Object[]> tableInfoList = sendFileBD.getWfTableInfoByTableId(tableId);
      Object object = "";
      String url = "action=see&processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField;
      if (tableInfoList != null && tableInfoList.size() > 0) {
        Object[] tableInfoObj = tableInfoList.get(0);
        object = tableInfoObj[0];
      } 
      if (object.equals("发文表"))
        return actionMapping.findForward("add"); 
      ActionForward forward = new ActionForward();
      url = "/doc/doc/" + tableId + "_0_add.jsp?processId=" + processId + "&processName=" + processName + "&processType=" + processType + "&remindField=" + remindField + "&tableId=" + tableId;
      forward.setPath(url);
      return forward;
    } 
    if ("list".equals(action)) {
      list(request);
      return actionMapping.findForward("list");
    } 
    if ("export".equals(action)) {
      export(request);
      return actionMapping.findForward("export");
    } 
    if ("delete".equals(action)) {
      Date beginDate = new Date();
      HttpSession session = request.getSession(true);
      Object object = session.getAttribute("domainId");
      String deleTitle = request.getParameter("deleTitle");
      delete(request);
      Date endDate = new Date();
      LogBD bd = new LogBD();
      bd.log(session.getAttribute("userId").toString(), session.getAttribute("userName").toString(), session.getAttribute("orgName").toString(), "oa_gw_fw", "", beginDate, endDate, "3", deleTitle, session.getAttribute("userIP").toString(), (String)object);
      return actionMapping.findForward("list");
    } 
    if ("listLoad".equals(action)) {
      String gd = (String)request.getAttribute("gd");
      ActionForward forward = new ActionForward();
      forward.setPath(listLoad(request, httpServletResponse));
      request.setAttribute("gd", gd);
      return forward;
    } 
    if ("modify".equals(action)) {
      ActionForward forward = new ActionForward();
      forward.setPath(modify(request, httpServletResponse));
      return forward;
    } 
    if ("initSendNum".equals(action)) {
      getDocumentWord(request);
      return actionMapping.findForward("initnumber");
    } 
    if ("mailtransmit".equals(action)) {
      mailtransmit(request);
      if (request.getParameter("transmitType") != null && request.getParameter("transmitType").toString().equals("wordTransmit")) {
        request.setAttribute("supplySend", "1");
        return actionMapping.findForward("mailResult");
      } 
      ActionForward forward = new ActionForward();
      forward.setPath(listLoad(request, httpServletResponse));
      return forward;
    } 
    if ("sendToMy".equals(action)) {
      sendToMyReceive(request);
      return actionMapping.findForward("close");
    } 
    if ("update".equals(action)) {
      update(request, httpServletResponse);
      return actionMapping.findForward("close");
    } 
    if ("queryReadList".equals(action)) {
      String tag = readLilst(request);
      return actionMapping.findForward(tag);
    } 
    "queryNoReadList".equals(action);
    if ("sendAssociate".equals(action)) {
      sendAssociate(request);
      return actionMapping.findForward("sendAssociate");
    } 
    return actionMapping.findForward("list");
  }
  
  private void update(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    (new SendFile()).update(httpServletRequest);
  }
  
  private void delete(HttpServletRequest httpServletRequest) {
    String id = httpServletRequest.getParameter("id");
    (new SendFileBD()).delete(id);
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && id != null && ifActiveUpdateDelete != null && !"".equals(id) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.deleteIndex(id.toString(), "doc_documentsendfile");
    } 
    list(httpServletRequest);
  }
  
  private void export(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    ManagerService managerBD = new ManagerService();
    String wherePara = " po.createdEmp=" + httpSession.getAttribute("userId") + 
      " or (" + 
      managerBD.getRightFinalWhere(httpSession.getAttribute("userId")
        .toString(), 
        httpSession.getAttribute("orgId").toString(), 
        "03*01*02", "po.createdOrg", 
        "po.createdEmp") + ")";
    wherePara = String.valueOf(wherePara) + " or (" + 
      managerBD.getRightFinalWhere(httpSession.getAttribute("userId")
        .toString(), 
        httpSession.getAttribute("orgId").toString(), 
        "03*01*01", "po.createdOrg", 
        "po.createdEmp") + ")";
    PackageBD pbd = new PackageBD();
    Object[] ooo = pbd.getModuleProc("2").get(0);
    Object object = ooo[3];
    StringBuffer sb = new StringBuffer("");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String querySecret = request.getParameter("querySecret");
    String queryStatus = request.getParameter("queryStatus");
    String queryTransPersonName = request.getParameter("queryTransPersonName");
    String queryItem = request.getParameter("queryItem");
    String queryBeginDate = request.getParameter("queryBeginDate");
    String queryEndDate = request.getParameter("queryEndDate");
    String queryOrg = request.getParameter("queryOrg");
    String documentSendFileTopicWord = request.getParameter("documentSendFileTopicWord");
    String joinwhere = "1=1";
    String fromwhere = 
      "com.js.doc.doc.po.GovDocumentSendFilePO po ";
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.documentSendFileByteNumber like '%").append(
          queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.documentSendFileTitle like '%").append(
          queryTitle).append("%'"); 
    if (documentSendFileTopicWord != null && !"".equals(documentSendFileTopicWord))
      sb.append(" and po.documentSendFileTopicWord like '%").append(documentSendFileTopicWord).append("%'"); 
    if (queryOrg != null && !"".equals(queryOrg))
      sb.append(" and po.documentSendFileWriteOrg like '%").append(
          queryOrg.trim()).append("%'"); 
    if (querySecret != null && !"none".equals(querySecret))
      if (!"".equals(querySecret)) {
        sb.append(" and po.documentSendFileSecurityGrade = '").append(
            querySecret).append("'");
      } else {
        sb.append(" and po.documentSendFileSecurityGrade  is null");
      }  
    if (queryStatus != null && !"none".equals(queryStatus))
      if (!"".equals(queryStatus)) {
        sb.append(" and po.transactStatus = '").append(
            queryStatus).append("'");
      } else {
        sb.append(" and po.transactStatus  is null");
      }  
    if (queryTransPersonName != null && !"".equals(queryTransPersonName)) {
      sb.append(" and emp.empName like '%").append(queryTransPersonName)
        .append("%'");
      fromwhere = "com.js.doc.doc.po.GovDocumentSendFilePO po ,com.js.system.vo.usermanager.EmployeeVO emp , com.js.goa.workflow.po.wfDealWithCommentPO wfcomm join wfcomm.dealWith wfdw ";
      joinwhere = " wfcomm.dealWithEmployeeId=emp.empId and wfdw.databaseRecordId=po.id and wfdw.databaseTableId=  " + 
        object;
    } 
    if ("1".equals(queryItem)) {
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.createdTime between '")
          .append(
            queryBeginDate).append(" 00:00:00")
          .append("' and '").append(
            queryEndDate).append(" 23:59:59")
          .append("'").append(
            " or  po.createdTime between '")
          .append(queryEndDate).append(" 00:00:00").append(
            "' and '")
          .append(queryBeginDate).append(" 23:59:59").append(
            "' )");
      } else {
        sb.append(" and ( po.createdTime between JSDB.FN_STRTODATE('")
          .append(
            queryBeginDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('").append(
            queryEndDate).append(" 23:59:59")
          .append("','L')").append(
            " or  po.createdTime between JSDB.FN_STRTODATE('")
          .append(queryEndDate).append(" 00:00:00").append(
            "','L') and JSDB.FN_STRTODATE('")
          .append(queryBeginDate).append(" 23:59:59").append(
            "','L') )");
      } 
    } 
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
    sb.append(" and (").append(wherePara).append(")");
    try {
      Page page = new Page(" po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime, po.createdEmp, po.createdOrg, po.sendFileLink, po.thirdDossier,po.transactStatus,po.zjkySeq ", 
          fromwhere, 
          " where " + joinwhere + sb + " and po.domainId=" + domainId + 
          " order by po.id desc ");
      page.setPageSize(1000000);
      page.setcurrentPage(1);
      List list = page.getResultList();
      String recordCount = String.valueOf(page.getRecordCount());
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", "1000000");
      request.setAttribute("pageParameters", "action,queryItem,queryBeginDate,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,documentSendFileTopicWord,queryOrg");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    getGDRight(request);
    getDefendRight(request);
  }
  
  private void list(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    ManagerService managerBD = new ManagerService();
    String wherePara = " po.createdEmp=" + httpSession.getAttribute("userId") + 
      " or (" + 
      managerBD.getRightFinalWhere(httpSession.getAttribute("userId")
        .toString(), 
        httpSession.getAttribute("orgId").toString(), 
        "03*01*02", "po.createdOrg", 
        "po.createdEmp") + ")";
    wherePara = String.valueOf(wherePara) + " or (" + 
      managerBD.getRightFinalWhere(httpSession.getAttribute("userId")
        .toString(), 
        httpSession.getAttribute("orgId").toString(), 
        "03*01*01", "po.createdOrg", 
        "po.createdEmp") + ")";
    PackageBD pbd = new PackageBD();
    Object[] ooo = pbd.getModuleProc("2").get(0);
    Object object = ooo[3];
    int pageSize = 100;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    StringBuffer sb = new StringBuffer("");
    String queryNumber = request.getParameter("queryNumber");
    String queryTitle = request.getParameter("queryTitle");
    String documentSendFileTopicWord = request.getParameter("documentSendFileTopicWord");
    String querySecret = request.getParameter("querySecret");
    String queryStatus = request.getParameter("queryStatus");
    String queryTransPersonName = request.getParameter("queryTransPersonName");
    String queryItem = request.getParameter("queryItem");
    String queryBeginDate = request.getParameter("queryBeginDate");
    String queryEndDate = request.getParameter("queryEndDate");
    String queryOrg = request.getParameter("queryOrg");
    String joinwhere = "1=1";
    String fromwhere = 
      "com.js.doc.doc.po.GovDocumentSendFilePO po ,RWSWorkFlowStatusPO st";
    sb.append(" and st.tableName='DOC_DOCUMENTSENDFILE' and st.recordId=po.id ");
    if (queryNumber != null && !"".equals(queryNumber))
      sb.append(" and po.documentSendFileByteNumber like '%").append(
          queryNumber).append("%'"); 
    if (queryTitle != null && !"".equals(queryTitle))
      sb.append(" and po.documentSendFileTitle like '%").append(
          queryTitle).append("%'"); 
    if (documentSendFileTopicWord != null && !"".equals(documentSendFileTopicWord))
      sb.append(" and po.documentSendFileTopicWord like '%").append(documentSendFileTopicWord).append("%'"); 
    if (queryOrg != null && !"".equals(queryOrg))
      sb.append(" and po.documentSendFileWriteOrg like '%").append(
          queryOrg.trim()).append("%'"); 
    if (querySecret != null && !"none".equals(querySecret))
      if (!"".equals(querySecret)) {
        sb.append(" and po.documentSendFileSecurityGrade = '").append(
            querySecret).append("'");
      } else {
        sb.append(" and po.documentSendFileSecurityGrade  is null");
      }  
    if (queryStatus != null && !"none".equals(queryStatus))
      if (!"".equals(queryStatus)) {
        sb.append(" and po.transactStatus = '").append(
            queryStatus).append("'");
      } else {
        sb.append(" and po.transactStatus  is null");
      }  
    if (queryTransPersonName != null && !"".equals(queryTransPersonName)) {
      sb.append(" and emp.empName like '%").append(queryTransPersonName)
        .append("%'");
      fromwhere = "com.js.doc.doc.po.GovDocumentSendFilePO po ,com.js.system.vo.usermanager.EmployeeVO emp , com.js.goa.workflow.po.wfDealWithCommentPO wfcomm join wfcomm.dealWith wfdw ";
      joinwhere = " wfcomm.dealWithEmployeeId=emp.empId and wfdw.databaseRecordId=po.id and wfdw.databaseTableId=  " + 
        object;
    } 
    if ("1".equals(queryItem)) {
      String tmpSql = "";
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        sb.append(" and ( po.createdTime between '")
          .append(
            queryBeginDate).append(" 00:00:00")
          .append("' and '").append(
            queryEndDate).append(" 23:59:59")
          .append("'").append(
            " or  po.createdTime between '")
          .append(queryEndDate).append(" 00:00:00").append(
            "' and '")
          .append(queryBeginDate).append(" 23:59:59").append(
            "' )");
      } else {
        sb.append(" and ( po.createdTime between JSDB.FN_STRTODATE('")
          .append(
            queryBeginDate).append(" 00:00:00")
          .append("','L') and JSDB.FN_STRTODATE('").append(
            queryEndDate).append(" 23:59:59")
          .append("','L')").append(
            " or  po.createdTime between JSDB.FN_STRTODATE('")
          .append(queryEndDate).append(" 00:00:00").append(
            "','L') and JSDB.FN_STRTODATE('")
          .append(queryBeginDate).append(" 23:59:59").append(
            "','L') )");
      } 
    } 
    String status = request.getParameter("status");
    request.setAttribute("status", status);
    if ("weiguidang".equals(status)) {
      sb.append(" and (not exists(select st.id from RWSWorkFlowStatusPO st where st.tableName='DOC_DOCUMENTSENDFILE' and st.recordId=po.id) or  exists(select st2.id from com.js.doc.doc.po.RWSWorkFlowStatusPO st2 where st2.tableName='DOC_DOCUMENTSENDFILE' and st2.recordId=po.id and st2.status=2)) ");
    } else if ("yuguidang".equals(status)) {
      sb.append(" and  st.status = 0 ");
    } else if ("yiguidang".equals(status)) {
      sb.append(" and st.status=1 ");
    } 
    sb.append(" and (").append(wherePara).append(")");
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
    sb.append(" and (po.isDraft is null or po.isDraft<1) ");
    try {
      Page page = new Page(" po.id,po.documentSendFileByteNumber,po.documentSendFileTitle,po.documentSendFileWriteOrg,po.createdTime, po.createdEmp, po.createdOrg, po.sendFileLink, po.thirdDossier,po.transactStatus,po.sendFileText,po.sendFileDraft,po.accessoryName,po.accessorySaveName,po.tableId,st.yuguidang_time,st.guidang_time ", 
          fromwhere, 
          " where " + joinwhere + sb + " and po.domainId=" + domainId + 
          "  order by po.id desc ");
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
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,documentSendFileTopicWord,queryOrg,redHeadId,numType,numId,status");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    getGDRight(request);
    getDefendRight(request);
  }
  
  private Iterator getYearItr() {
    List<Integer> itr = new ArrayList();
    Calendar now = new GregorianCalendar();
    int year = now.get(1);
    for (int i = -10; i <= 10; i++)
      itr.add(new Integer(year + i)); 
    return itr.iterator();
  }
  
  private void draftLoad(HttpServletRequest httpServletRequest, GovSendFileActionForm govSendFileActionForm) {
    Iterator yearItr = getYearItr();
    httpServletRequest.setAttribute("yearItr", yearItr);
    httpServletRequest.setAttribute("seallist", new ArrayList());
    SendFileBD bd = new SendFileBD();
    String sendFileId = httpServletRequest.getParameter("sendFileId");
    GovDocumentSendFilePO po = bd.load(sendFileId);
    if (po.getSendFileRedHeadId() != 0L && 
      !"null".equals(po.getSendFileRedHeadId()) && 
      !"-1".equals((new StringBuilder(String.valueOf(po.getSendFileRedHeadId()))).toString()))
      httpServletRequest.setAttribute("seallist", 
          bd.getSealList((new StringBuilder(String.valueOf(po.getSendFileRedHeadId()))).toString())); 
    govSendFileActionForm.setDocumentSendFileWriteOrg(po
        .getDocumentSendFileWriteOrg());
    govSendFileActionForm.setDocumentSendFileTitle(po.getDocumentSendFileTitle()
        .replaceAll("<br>", "\n"));
    if (po.getDocumentSendFileTitle() != null && !po.getDocumentSendFileTitle().toString().equals(""))
      httpServletRequest.setAttribute("documentSendFileTitle", po.getDocumentSendFileTitle()); 
    govSendFileActionForm.setDocumentSendFileAssumePeople(po.getDocumentSendFileAssumePeople());
    httpServletRequest.setAttribute("documentSendFileAssumePeople", po.getDocumentSendFileAssumePeople());
    govSendFileActionForm.setDocumentSendFileAssumeUnit(po.getDocumentSendFileAssumeUnit());
    httpServletRequest.setAttribute("documentSendFileAssumeUnit", po.getDocumentSendFileAssumeUnit());
    govSendFileActionForm.setDocumentSendFileByteNumber(po.getDocumentSendFileByteNumber());
    httpServletRequest.setAttribute("documentSendFileByteNumber", po.getDocumentSendFileByteNumber());
    govSendFileActionForm.setDocumentSendFileCheckCommit(po.getDocumentSendFileCheckCommit());
    httpServletRequest.setAttribute("documentSendFileCheckCommit", po.getDocumentSendFileCheckCommit());
    govSendFileActionForm.setDocumentSendFileCheckDate(po.getDocumentSendFileCheckDate());
    httpServletRequest.setAttribute("documentSendFileCheckDat", po.getDocumentSendFileCheckDate());
    govSendFileActionForm.setDocumentSendFileCounterSign(po.getDocumentSendFileCounterSign());
    httpServletRequest.setAttribute("documentSendFileCounterSign", po.getDocumentSendFileCounterSign());
    govSendFileActionForm.setDocumentSendFileHead(po.getDocumentSendFileHead());
    httpServletRequest.setAttribute("documentSendFileHead", po.getDocumentSendFileHead());
    govSendFileActionForm.setDocumentSendFilePrintNumber((new StringBuilder(String.valueOf(po.getDocumentSendFilePrintNumber()))).toString());
    httpServletRequest.setAttribute("documentSendFilePrintNumber", po.getDocumentSendFilePrintNumber());
    govSendFileActionForm.setDocumentSendFileSecurityGrade(po.getDocumentSendFileSecurityGrade());
    httpServletRequest.setAttribute("documentSendFileSecurityGrad", po.getDocumentSendFileSecurityGrade());
    govSendFileActionForm.setDocumentSendFileSendFile(po.getDocumentSendFileSendFile());
    httpServletRequest.setAttribute("documentSendFileSendFile", po.getDocumentSendFileSendFile());
    httpServletRequest.setAttribute("sendFileDate", po.getDocumentSendFileDate());
    httpServletRequest.setAttribute("sendFileSendDate", po.getDocumentSendFileSendDate());
    govSendFileActionForm.setDocumentSendFileTopicWord(po.getDocumentSendFileTopicWord());
    httpServletRequest.setAttribute("documentSendFileTopicWord", po.getDocumentSendFileTopicWord());
    govSendFileActionForm.setDocumentSendFileWriteOrg(po.getDocumentSendFileWriteOrg());
    httpServletRequest.setAttribute("documentSendFileWriteOrg", po.getDocumentSendFileWriteOrg());
    govSendFileActionForm.setSendToId(String.valueOf(po.getSendToOrg()) + po.getSendToGroup() + po.getSendToEmp());
    httpServletRequest.setAttribute("sendToId", String.valueOf(po.getSendToOrg()) + po.getSendToGroup() + po.getSendToEmp());
    govSendFileActionForm.setSendToName(po.getSendToName());
    httpServletRequest.setAttribute("sendToName", po.getSendToName());
    govSendFileActionForm.setField1(po.getField1());
    httpServletRequest.setAttribute("field1", po.getField1());
    govSendFileActionForm.setField2(po.getField2());
    httpServletRequest.setAttribute("field2", po.getField2());
    govSendFileActionForm.setField3(po.getField3());
    httpServletRequest.setAttribute("field3", po.getField3());
    govSendFileActionForm.setField4(po.getField4());
    httpServletRequest.setAttribute("field4", po.getField4());
    govSendFileActionForm.setField5(po.getField5());
    httpServletRequest.setAttribute("field5", po.getField5());
    govSendFileActionForm.setField6(po.getField6());
    httpServletRequest.setAttribute("field6", po.getField6());
    govSendFileActionForm.setField7(po.getField7());
    httpServletRequest.setAttribute("field7", po.getField7());
    govSendFileActionForm.setField8(po.getField8());
    httpServletRequest.setAttribute("field8", po.getField8());
    govSendFileActionForm.setField9(po.getField9());
    httpServletRequest.setAttribute("field9", po.getField9());
    govSendFileActionForm.setField10(po.getField10());
    httpServletRequest.setAttribute("field10", po.getField10());
    httpServletRequest.setAttribute("content", po.getSendFileText());
    httpServletRequest.setAttribute("pn3Name", po.getSendToName());
    govSendFileActionForm.setSendFileAccessoryDesc(po.getSendFileAccessoryDesc());
    httpServletRequest.setAttribute("sendFileAccessoryDesc", po.getSendFileAccessoryDesc());
    govSendFileActionForm.setSendFileAgentDraft(po.getSendFileAgentDraft());
    httpServletRequest.setAttribute("sendFileAgentDraft", po.getSendFileAgentDraft());
    govSendFileActionForm.setSendFileDraft(po.getSendFileDraft());
    httpServletRequest.setAttribute("sendFileDraft", po.getSendFileDraft());
    govSendFileActionForm.setSendFileGrade(po.getSendFileGrade());
    httpServletRequest.setAttribute("sendFileGrade", po.getSendFileGrade());
    govSendFileActionForm.setSendFileMassDraft(po.getSendFileMassDraft());
    httpServletRequest.setAttribute("sendFileMassDraft", po.getSendFileMassDraft());
    govSendFileActionForm.setSendFilePrinter(po.getSendFilePrinter());
    httpServletRequest.setAttribute("sendFilePrinter", po.getSendFilePrinter());
    govSendFileActionForm.setSendFileProof(po.getSendFileProof());
    httpServletRequest.setAttribute("sendFileProof", po.getSendFileProof());
    govSendFileActionForm.setSendFileProveDraft(po.getSendFileProveDraft());
    httpServletRequest.setAttribute("sendFileProveDraft", po.getSendFileProveDraft());
    govSendFileActionForm.setSendFileReadComment(po.getSendFileReadComment());
    httpServletRequest.setAttribute("sendFileReadComment", po.getSendFileReadComment());
    govSendFileActionForm.setSendFileText(po.getSendFileText());
    httpServletRequest.setAttribute("sendFileText", po.getSendFileText());
    govSendFileActionForm.setReceiveFileIsFlowMode(po.getReceiveFileIsFlowMode());
    httpServletRequest.setAttribute("receiveFileIsFlowMode", po.getReceiveFileIsFlowMode());
    govSendFileActionForm.setSendFileRedHeadId(po.getSendFileRedHeadId());
    httpServletRequest.setAttribute("sendFileRedHeadId", po.getSendFileRedHeadId());
    govSendFileActionForm.setSendFileSealId(po.getSendFileSealId());
    httpServletRequest.setAttribute("sendFileSealId", po.getSendFileSealId());
    govSendFileActionForm.setSendFileLink(po.getSendFileLink());
    httpServletRequest.setAttribute("sendFileLink", po.getSendFileLink());
    govSendFileActionForm.setSendFileRedHeadPic(po.getSendFileRedHeadPic());
    httpServletRequest.setAttribute("sendFileRedHeadPic", po.getSendFileRedHeadPic());
    govSendFileActionForm.setSendFileSealPic(po.getSendFileSealPic());
    httpServletRequest.setAttribute("sendFileSealPic", po.getSendFileSealPic());
    httpServletRequest.setAttribute("sendFileTitle", po.getDocumentSendFileTitle());
    httpServletRequest.setAttribute("documentSendFileTitle", po.getDocumentSendFileTitle());
    govSendFileActionForm.setSendFileType(po.getSendFileType());
    httpServletRequest.setAttribute("sendFileType", po.getSendFileType());
    govSendFileActionForm.setOpenProperty(po.getOpenProperty());
    httpServletRequest.setAttribute("openProperty", po.getOpenProperty());
    httpServletRequest.setAttribute("signsendTime", po.getSignsendTime());
    httpServletRequest.setAttribute("accessoryName", po.getAccessoryName());
    httpServletRequest.setAttribute("accessorySaveName", po.getAccessorySaveName());
    httpServletRequest.setAttribute("contentAccName", po.getContentAccName());
    httpServletRequest.setAttribute("contentAccSaveName", po.getContentAccSaveName());
    httpServletRequest.setAttribute("sendStatus", po.getTransactStatus());
    govSendFileActionForm.setSendToType(po.getSendToType());
    httpServletRequest.setAttribute("sendToType", po.getSendToType());
    govSendFileActionForm.setDocumentWordType(po.getDocumentWordType());
    httpServletRequest.setAttribute("documentWordType", po.getDocumentWordType());
    govSendFileActionForm.setSendTextField1(po.getSendTextField1());
    httpServletRequest.setAttribute("sendTextField1", po.getSendTextField1());
    govSendFileActionForm.setSendTextField2(po.getSendTextField2());
    httpServletRequest.setAttribute("sendTextField2", po.getSendTextField2());
    govSendFileActionForm.setSendDropDownSelect1(po.getSendDropDownSelect1());
    httpServletRequest.setAttribute("sendDropDownSelect1", po.getSendDropDownSelect1());
    govSendFileActionForm.setSendDropDownSelect2(po.getSendDropDownSelect2());
    httpServletRequest.setAttribute("sendDropDownSelect2", po.getSendDropDownSelect2());
    govSendFileActionForm.setSendMutliTextField1(po.getSendMutliTextField1());
    httpServletRequest.setAttribute("sendMutliTextField1", po.getSendMutliTextField1());
    if (po.getSendToType().equals("0")) {
      govSendFileActionForm.setToPerson1(po.getMainToName());
      govSendFileActionForm.setToPerson2(po.getCopyToName());
    } else if (po.getSendToType().equals("1")) {
      govSendFileActionForm.setToPerson3(po.getMainToName());
      govSendFileActionForm.setToPerson4(po.getSendRoundName());
      govSendFileActionForm.setToPerson5(po.getSunderToName());
    } else {
      govSendFileActionForm.setToPerson6(po.getSunderToName());
    } 
    httpServletRequest.setAttribute("toPerson1", po.getMainToName());
    httpServletRequest.setAttribute("toPerson2", po.getCopyToName());
    govSendFileActionForm.setZjkySecrecyterm(po.getZjkySecrecyterm());
    httpServletRequest.setAttribute("zjkySecrecyterm", po.getZjkySecrecyterm());
    govSendFileActionForm.setZjkyContentLevel(po.getZjkyContentLevel());
    httpServletRequest.setAttribute("zjkyContentLevel", po.getZjkyContentLevel());
    if (po.getToPerson1Id() != null) {
      govSendFileActionForm.setToPerson1Id(po.getToPerson1Id());
      httpServletRequest.setAttribute("toPerson1Id", po.getToPerson1Id());
    } else {
      govSendFileActionForm.setToPerson1Id("");
    } 
    if (po.getToPerson2Id() != null) {
      govSendFileActionForm.setToPerson2Id(po.getToPerson2Id());
      httpServletRequest.setAttribute("toPerson2Id", po.getToPerson2Id());
    } else {
      govSendFileActionForm.setToPerson2Id("");
    } 
    if (po.getToPersonBaoId() != null) {
      govSendFileActionForm.setToPersonBaoId(po.getToPersonBaoId());
    } else {
      govSendFileActionForm.setToPersonBaoId("");
    } 
    if (po.getToPersonInnerId() != null) {
      govSendFileActionForm.setToPersonInnerId(po.getToPersonInnerId());
    } else {
      govSendFileActionForm.setToPersonInnerId("");
    } 
    if (po.getToPersonBao() != null) {
      govSendFileActionForm.setToPersonBao(po.getToPersonBao());
      httpServletRequest.setAttribute("toPersonBao", po.getToPersonBao());
    } 
    if (po.getToPersonInner() != null) {
      govSendFileActionForm.setToPersonInner(po.getToPersonInner());
      httpServletRequest.setAttribute("toPersonInner", po.getToPersonInner());
    } else {
      govSendFileActionForm.setToPersonInner("");
    } 
    govSendFileActionForm.setSendFilePoNumId((po.getSendFilePoNumId() == null) ? 
        "" : 
        po.getSendFilePoNumId().toString());
    httpServletRequest.setAttribute("SendFilePoNumId", (po.getSendFilePoNumId() != null) ? po.getSendFilePoNumId().toString() : "");
    govSendFileActionForm.setTemplateId((po.getSendFileTemId() == null) ? "" : 
        po.getSendFileTemId().toString());
    govSendFileActionForm.setZjkyWordId((po.getZjkyWordId() == null) ? "" : 
        po.getZjkyWordId().toString());
    String wordId = (po.getZjkyWordId() == null) ? "" : (String)po.getZjkyWordId();
    String wordName = (po.getSendFileDepartWord() == null) ? "" : po.getSendFileDepartWord();
    String tempId = (po.getSendFileTemId() == null) ? "" : po.getSendFileTemId().toString();
    if (wordId != null && !wordId.equals("")) {
      govSendFileActionForm.setSendFileDepartWord(String.valueOf(wordId) + ";" + wordName + ";" + tempId);
      httpServletRequest.setAttribute("sendFileDepartWord", String.valueOf(wordId) + ";" + wordName + ";" + tempId);
    } else {
      govSendFileActionForm.setSendFileDepartWord("");
    } 
    SenddocumentBD senddocumentBd = new SenddocumentBD();
    List<String[]> list = new ArrayList();
    String proceedId = httpServletRequest.getParameter("processId");
    if (proceedId != null && !proceedId.equals("")) {
      list = senddocumentBd.getWordByProceeIdAnd4Type(proceedId, "3", getscopeWhere(httpServletRequest));
    } else {
      String[] wordList = { wordId, wordName, tempId };
      list.add(wordList);
    } 
    httpServletRequest.setAttribute("wordlist", list);
    httpServletRequest.setAttribute("draftOpen", "1");
    httpServletRequest.setAttribute("sendToType", po.getSendToType());
  }
  
  private void getTypesetList(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String wherePara = (new ManagerService()).getScopeFinalWhere(httpSession.getAttribute(
          "userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "po.sendToUser", "po.sendToOrg", 
        "po.sendToGroup");
    wherePara = String.valueOf(wherePara) + " and po.domainId=" + domainId;
    httpServletRequest.setAttribute("typesetlist", (
        new GovTypeSetBD()).getTypeSet(wherePara));
  }
  
  private void see(HttpServletRequest httpServletRequest, GovSendFileActionForm govSendFileActionForm) {
    getDocumentWord(httpServletRequest);
    getDocumentWordList2(httpServletRequest);
    Iterator yearItr = getYearItr();
    httpServletRequest.setAttribute("yearItr", yearItr);
    httpServletRequest.setAttribute("seallist", new ArrayList());
    govSendFileActionForm.setDocumentSendFileWriteOrg(httpServletRequest.getSession(true).getAttribute("orgName").toString());
    govSendFileActionForm.setSendToType("0");
  }
  
  private void getRedHeadList(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String wherePara1 = (new ManagerService()).getScopeWhere(httpSession.getAttribute(
          "userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "po.headUser", "po.headUseOrg", 
        "po.headUseGroup", 
        "po.createdEmp");
    wherePara1 = String.valueOf(wherePara1) + " and po.domainId=" + domainId;
    httpServletRequest.setAttribute("redheadlist", (
        new SendFileBD()).getRedHeadList(wherePara1));
  }
  
  private void initNumber(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String sendFileCode = (new GovTypeSetBD()).getTypeNumber(httpServletRequest
        .getParameter("fileNumberId"));
    StringBuffer sendFileYear = new StringBuffer();
    if (httpServletRequest.getParameter("sendFileYear") != null && 
      !"".equals(httpServletRequest.getParameter("sendFileYear"))) {
      sendFileYear.append(httpServletRequest.getParameter("sendFileYear"));
    } else {
      sendFileYear.append((new Date()).getYear() + 1900);
    } 
    String sendFileMaxNumber = (new SendFileBD()).getSendFileMaxNumber(
        sendFileCode, sendFileYear.toString(), domainId);
    httpServletRequest.setAttribute("sendFileCode", sendFileCode);
    httpServletRequest.setAttribute("sendFileYear", sendFileYear.toString());
    httpServletRequest.setAttribute("sendFileMaxNumber", sendFileMaxNumber);
    GovTypeSetBD gtsBD = new GovTypeSetBD();
    GovTypeSetPO form = gtsBD.load(httpServletRequest.getParameter("fileNumberId"));
    httpServletRequest.setAttribute("redHeadId", form.getRedHeadId());
  }
  
  private void selRedHead(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("seallist", (
        new SendFileBD()).getSealList(
          httpServletRequest
          .getParameter("redHeadId")));
  }
  
  private void draft(HttpServletRequest httpServletRequest) {
    SendFile sendFile = new SendFile();
    sendFile.draft(httpServletRequest);
    httpServletRequest.setAttribute("draft", "1");
  }
  
  private void directSend(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    SendFile sendFile = new SendFile();
    Long sendFileId = sendFile.save(httpServletRequest);
    if (sendFileId != null && !sendFileId.toString().equals("-1")) {
      SendFileBD sendFileBD = new SendFileBD();
      sendFileBD.send(sendFileId.toString(), httpServletRequest.getParameter("sendToId"), domainId);
      sendFile.sendMessage(sendFileId.toString(), httpServletRequest);
      String userAccounts = sendFileBD.getSendRtxReceiver(httpServletRequest.getParameter("sendToId"));
      if (userAccounts.endsWith(","))
        userAccounts = userAccounts.substring(0, userAccounts.length() - 1); 
      String sendFileByteNumber = "";
      if (httpServletRequest.getParameter("field1") != null && 
        httpServletRequest.getParameter("field2") != null && 
        httpServletRequest.getParameter("field3") != null && 
        !"".equals(httpServletRequest.getParameter("field1")) && 
        !"".equals(httpServletRequest.getParameter("field2")) && 
        !"".equals(httpServletRequest.getParameter("field3")) && 
        !"null".equals(httpServletRequest.getParameter("field1")) && 
        !"null".equals(httpServletRequest.getParameter("field2")) && 
        !"null".equals(httpServletRequest.getParameter("field3")))
        sendFileByteNumber = String.valueOf(httpServletRequest.getParameter("field1")) + 
          "〔" + 
          httpServletRequest.getParameter("field2") + 
          "〕" + 
          httpServletRequest.getParameter("field3"); 
      Calendar now = Calendar.getInstance();
      String userIds = httpServletRequest.getParameter("sendToId");
      sendRTXRemind(userIds, "新收文提醒", String.valueOf(sendFileByteNumber) + "  " + httpServletRequest.getParameter("documentSendFileTitle") + "(" + (now.get(2) + 1) + "-" + now.get(5) + ")");
      String returnValue = (new ArchivesBD()).archivesPigeonholeSet("GWGL", (httpServletRequest.getSession(true).getAttribute("domainId") == null) ? "-1" : httpServletRequest.getSession(true).getAttribute("domainId").toString());
      if (!"".equals(returnValue) && !"-1".equals(returnValue))
        httpServletRequest.setAttribute("GWGD", "1"); 
      httpServletRequest.setAttribute("sendFileId", null);
      httpServletRequest.setAttribute("fileTitle", httpServletRequest.getParameter("documentSendFileTitle"));
    } else {
      httpServletRequest.setAttribute("repeat", "1");
    } 
    httpServletRequest.setAttribute("directSend", "1");
  }
  
  private void supplySend(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    SendFile sendFile = new SendFile();
    String sendFileId = httpServletRequest.getParameter("editId");
    if (sendFileId != null) {
      SendFileBD sendFileBD = new SendFileBD();
      sendFileBD.send(sendFileId.toString(), httpServletRequest.getParameter("sendToId2"), domainId);
      sendFile.sendSupplyMessage(sendFileId.toString(), httpServletRequest);
    } 
    httpServletRequest.setAttribute("supplySend", "1");
  }
  
  private String listLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String isPrint = (httpServletRequest.getParameter("isPrint") != null) ? httpServletRequest.getParameter("isPrint").toString() : "0";
    String tableId = (httpServletRequest.getParameter("tableId") != null) ? httpServletRequest.getParameter("tableId").toString() : "";
    Map workMap = wfcBD.getWorkInfoByTableID(httpServletRequest.getParameter("editId"), tableId);
    String activityName = (workMap.get("activityName") == null) ? "" : URLEncoder.encode(workMap.get("activityName").toString());
    String submitPerson = (workMap.get("submitPerson") == null) ? "" : URLEncoder.encode(workMap.get("submitPerson").toString());
    String processName = (workMap.get("processName") == null) ? "" : URLEncoder.encode(workMap.get("processName").toString());
    String standForUserName = (workMap.get("standForUserName") == null) ? "" : URLEncoder.encode(workMap.get("standForUserName").toString());
    String myFile = (httpServletRequest.getParameter("myFile") == null) ? "0" : httpServletRequest.getParameter("myFile").toString();
    String sendFileLink = 
      "/GovSendFileLoadForRWSAction.do?action=load&activityName=" + 
      activityName + 
      "&submitPersonId=" + workMap.get("submitPersonId") + "&submitPerson=" + 
      submitPerson + 
      "&work=" + workMap.get("work") + "&workType=" + workMap.get("workType") + 
      "&activity=" + 
      workMap.get("activity") + "&table=" + workMap.get("tableId") + 
      "&record=" + 
      httpServletRequest.getParameter("editId") + "&processName=" + 
      processName + 
      "&workStatus=1&submitTime=" + workMap.get("submitTime") + "&processId=" + 
      workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + "&isStandForWork=" + 
      workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + 
      "&standForUserName=" + standForUserName + "&myFile=" + myFile;
    return sendFileLink;
  }
  
  private String modify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    WorkFlowCommonBD wfcBD = new WorkFlowCommonBD();
    String isPrint = (httpServletRequest.getParameter("isPrint") != null) ? httpServletRequest.getParameter("isPrint").toString() : "0";
    String tableId = (httpServletRequest.getParameter("tableId") != null) ? httpServletRequest.getParameter("tableId").toString() : "";
    Map workMap = wfcBD.getWorkInfoByTableID(httpServletRequest.getParameter("id"), tableId);
    String activityName = (workMap.get("activityName") == null) ? "" : URLEncoder.encode(workMap.get("activityName").toString());
    String submitPerson = (workMap.get("submitPerson") == null) ? "" : URLEncoder.encode(workMap.get("submitPerson").toString());
    String processName = (workMap.get("processName") == null) ? "" : URLEncoder.encode(workMap.get("processName").toString());
    String standForUserName = (workMap.get("standForUserName") == null) ? "" : URLEncoder.encode(workMap.get("standForUserName").toString());
    String sendFileLink = 
      "/GovSendFileLoadAction.do?action=load&activityName=" + activityName + 
      "&submitPersonId=" + workMap.get("submitPersonId") + "&submitPerson=" + submitPerson + 
      "&work=" + workMap.get("work") + "&workType=" + workMap.get("workType") + 
      "&activity=" + 
      workMap.get("activity") + "&table=" + workMap.get("tableId") + 
      "&record=" + 
      httpServletRequest.getParameter("id") + "&processName=" + processName + 
      "&workStatus=1&submitTime=" + workMap.get("submitTime") + "&processId=" + 
      workMap.get("processId") + 
      "&stepCount=" + workMap.get("stepCount") + "&isStandForWork=" + 
      workMap.get("isStandForWork") + 
      "&standForUserId=" + workMap.get("standForUserId") + 
      "&standForUserName=" + standForUserName + "&isEdit=1&submitPersonTime=" + workMap.get("submitTime") + "&isPrint=" + isPrint;
    return sendFileLink;
  }
  
  private void getGDRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    List<Object[]> rightList = managerBD.getRightScope(userId, "03*15*01");
    if (rightList != null && rightList.size() > 0) {
      Object[] objRight = rightList.get(0);
      String scopeType = objRight[0].toString();
      String orgRange = "";
      if ("3".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange("*" + orgId + "*");
      } else if ("4".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange((String)objRight[1]);
      } 
      httpServletRequest.setAttribute("scopeType", scopeType);
      httpServletRequest.setAttribute("orgRange", "," + orgRange + ",");
    } 
  }
  
  private void getDefendRight(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String userId = httpSession.getAttribute("userId").toString();
    String orgId = httpSession.getAttribute("orgId").toString();
    ManagerService managerBD = new ManagerService();
    List<Object[]> rightList = managerBD.getRightScope(userId, "03*01*01");
    if (rightList != null && rightList.size() > 0) {
      Object[] objRight = rightList.get(0);
      String scopeType = objRight[0].toString();
      String orgRange = "";
      if ("3".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange("*" + orgId + "*");
      } else if ("4".equals(scopeType)) {
        orgRange = managerBD.getAllJuniorOrgIdByRange((String)objRight[1]);
      } 
      httpServletRequest.setAttribute("defendScopeType", scopeType);
      httpServletRequest.setAttribute("defendOrgRange", "," + orgRange + ",");
    } 
  }
  
  public String getUserByOrg(String orgIdStr) {
    String orgIds = "";
    if (orgIdStr == null || orgIdStr.length() < 1)
      return orgIds; 
    String[] orgIdArr = orgIdStr.split(",");
    DbOpt dbopt = null;
    ResultSet rs = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < orgIdArr.length; i++) {
        String orgCode = dbopt.executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
    } catch (Exception exception) {
    
    } finally {}
    try {
      dbopt.close();
    } catch (SQLException sQLException) {}
    return orgIds;
  }
  
  public String getUserByGroup(String groupIdStr) {
    String userStr = "";
    if (groupIdStr == null || groupIdStr.length() < 1)
      return userStr; 
    String[] groupIdArr = groupIdStr.split(",");
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < groupIdArr.length; i++) {
        String empIdStr = dbopt.executeQueryToStr("select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + groupIdArr[i]);
        if (empIdStr != null && empIdStr.length() > 1)
          userStr = String.valueOf(userStr) + empIdStr; 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
    if (userStr != null && userStr.length() > 1)
      userStr = userStr.substring(1, userStr.length() - 1).replaceAll("$$", ","); 
    return userStr;
  }
  
  private void sendRTXRemind(String userIds, String title, String content) {
    DataSource ds = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    ConversionString con = new ConversionString(userIds);
    String userIdStr = String.valueOf(con.getUserIdString()) + ",";
    userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
    userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
    StringBuffer allUserIdBuffer = new StringBuffer();
    userIdStr = userIdStr.replaceAll(",,", ",").replaceAll(",,", ",");
    while (userIdStr.startsWith(","))
      userIdStr = userIdStr.substring(1, userIdStr.length() - 1); 
    while (userIdStr.endsWith(","))
      userIdStr = userIdStr.substring(0, userIdStr.length() - 1); 
    try {
      ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(
          "select useraccounts from org_employee where emp_id in (" + 
          userIdStr + ")");
      allUserIdBuffer = new StringBuffer();
      while (rs.next())
        allUserIdBuffer.append(rs.getString(1)).append(","); 
      rs.next();
      stmt.close();
      conn.close();
      if (allUserIdBuffer.length() > 1)
        allUserIdBuffer = allUserIdBuffer.deleteCharAt(allUserIdBuffer
            .length() - 1); 
      RealTimeUtil util = new RealTimeUtil();
      util.sendNotify(allUserIdBuffer.toString(), title, content, "0", "0");
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (SQLException sQLException) {} 
      if (stmt != null)
        try {
          stmt.close();
        } catch (SQLException sQLException) {} 
      if (conn != null)
        try {
          conn.close();
        } catch (SQLException sQLException) {} 
    } 
  }
  
  private void getDocumentWord(HttpServletRequest request) {
    List<Object[]> endList = new ArrayList();
    int nowYear = (new Date()).getYear() + 1900;
    String numId = "";
    String sendWord = "";
    String temId = "";
    String num_f1 = "";
    String num_f2 = "";
    String num_f3 = "";
    String sendWordId = "";
    String seqId = "";
    String seqstr = "";
    SenddocumentBD bd = new SenddocumentBD();
    String proceedId = request.getParameter("processId");
    List<Object[]> list = new ArrayList();
    list = bd.getWordByProceeIdAnd4Type(proceedId, "3", getscopeWhere(request));
    if (list != null && list.size() > 0)
      for (int ii = 0; ii < list.size(); ii++) {
        Object object1, obj[] = list.get(ii);
        Object object2 = obj[1];
        Object object3 = obj[2];
        Object object4 = obj[0];
        if (obj[3] != null && !obj[3].toString().equals("") && 
          !obj[3].toString().equals("-1")) {
          int bitNum = Integer.parseInt(obj[9].toString());
          Object object = obj[10];
          Integer isYe = (Integer)obj[13];
          Integer numfig = (Integer)obj[12];
          int oldYear = Integer.parseInt(obj[14].toString());
          int initValue = Integer.parseInt((String)obj[15]);
          int numfigint = numfig.intValue() + 1;
          int nowYear_int = (new Date()).getYear() + 1900;
          if (object.equals("1") && 
            oldYear != nowYear_int) {
            numfigint = initValue;
            String[] para = { "", "" };
            para[0] = (String)obj[3];
            para[1] = (new StringBuilder(String.valueOf(initValue - 1))).toString();
            bd.updateNumByPara(para);
          } 
          String model = (String)obj[11];
          if (isYe != null && isYe.toString().equals("1")) {
            int nh = model.indexOf("[年度]");
            if (nh != -1)
              model = String.valueOf(model.substring(0, nh)) + nowYear + 
                model.substring(nh + 4); 
          } 
          int xh = model.indexOf("[序号]");
          if (xh != -1) {
            object1 = obj[3];
            num_f1 = String.valueOf(object2) + model.substring(0, xh);
            num_f2 = (new StringBuilder(String.valueOf(numfigint))).toString();
            int dd = num_f2.length();
            if (bitNum > dd) {
              String ling = "";
              for (int jj = 0; jj < bitNum - dd; jj++)
                ling = String.valueOf(ling) + "0"; 
              num_f1 = String.valueOf(num_f1) + ling;
            } 
            num_f3 = model.substring(xh + 4);
          } 
        } 
        String paraValue1 = String.valueOf(object4) + ";" + object2 + ";" + object1 + ";" + object3 + ";" + num_f1 + ";" + num_f2 + ";" + num_f3 + ";" + seqstr;
        Object object5 = object2;
        Object[] obj1 = { paraValue1, object5 };
        endList.add(obj1);
      }  
    request.setAttribute("wordlist", endList);
  }
  
  private void getDocumentWordList2(HttpServletRequest request) {
    SenddocumentBD bd = new SenddocumentBD();
    String proceedId = request.getParameter("processId");
    List list = new ArrayList();
    list = bd.getWordByProceeIdAnd4Type(proceedId, "3", getscopeWhere(request));
    request.setAttribute("wordlist", list);
  }
  
  public void setMapInfo(HttpServletRequest request) {
    Object[] ftObj = { "" };
    Object[] slObj = { "" };
    Object[] tlObj = { "" };
    Object[] clObj = { "" };
    Object[] ksObj = { "" };
    Object[] bqObj = { "" };
    SenddocumentBD bd = new SenddocumentBD();
    Object[] obj = bd.getSenddocumentBaseInfo();
    Map<Object, Object> mapinfo = new HashMap<Object, Object>();
    if (obj != null && obj.length > 0) {
      if (obj[2] != null) {
        String[] arrayOfString = obj[2].toString().split(";");
        mapinfo.put("fileType", arrayOfString);
      } 
      if (obj[4] != null) {
        String[] arrayOfString = obj[4].toString().split(";");
        mapinfo.put("secretLevel", arrayOfString);
      } 
      if (obj[5] != null) {
        String[] arrayOfString = obj[5].toString().split(";");
        mapinfo.put("transactLevel", arrayOfString);
      } 
      if (obj[1] != null) {
        String[] arrayOfString = obj[1].toString().split(";");
        mapinfo.put("contentLevel", arrayOfString);
      } 
      if (obj[3] != null) {
        String[] arrayOfString = obj[3].toString().split(";");
        mapinfo.put("keepSecretLevel", arrayOfString);
      } 
      if (obj[10] != null) {
        String[] arrayOfString = obj[10].toString().split(";");
        mapinfo.put("baseQueryLevel", arrayOfString);
      } 
    } 
    List list = new ArrayList();
    list = bd.getAllHaveTmep_Word();
    mapinfo.put("unitWord", list);
    request.setAttribute("mapinfo", mapinfo);
  }
  
  private void intZjkySendWord(HttpServletRequest request) {
    Long long_1, long_2;
    String changeNumType = (request.getParameter("changeNumType") == null) ? "modi" : request.getParameter("changeNumType");
    SenddocumentBD bd = new SenddocumentBD();
    SendDocumentWordPO po = bd.loadSendDocumentWordPO(request.getParameter("sendWordId"));
    SendDocumentNumPO numpo = null;
    SendDocumentSeqPO seqpo = null;
    String sendWord = (po.getWordName() == null) ? "" : po.getWordName();
    Long senddocumentId = po.getSendDocumentNumId();
    Long sendseqId = po.getSendDocumentSeqId();
    if (senddocumentId != null)
      numpo = bd.loadSendDocumentNumPO(senddocumentId.toString()); 
    if (sendseqId != null)
      seqpo = bd.loadSendDocumentSeqPO(sendseqId.toString()); 
    int nowYear = (new Date()).getYear() + 1900;
    String numId = "";
    String num_f1 = "";
    String num_f2 = "";
    String num_f3 = "";
    String seqId = "";
    String seqstr = "";
    String lastseqfig = "";
    int zeroNum = 0;
    int numweishu = 0;
    if (numpo != null) {
      int bitNum = numpo.getBitNum().intValue();
      String keyValue = numpo.getKeyValue();
      Integer isYe = numpo.getNumIsYear();
      Integer numfig = numpo.getNumfig();
      Integer integer1 = numpo.getNumIsWord();
      int oldYear = numpo.getOldYear();
      int initValue = numpo.getInitValue().intValue();
      numweishu = bitNum;
      int numfigint = numfig.intValue() + 1;
      int nowYear_int = (new Date()).getYear() + 1900;
      if (keyValue.equals("1") && 
        oldYear != nowYear_int) {
        numfigint = initValue;
        String[] para = { (String)numpo.getId(), (new StringBuilder(String.valueOf(initValue - 1))).toString() };
        bd.updateNumByPara(para);
      } 
      String model = numpo.getNumMode();
      if (isYe != null && isYe.toString().equals("1")) {
        int nh = model.indexOf("[年度]");
        if (nh != -1)
          model = String.valueOf(model.substring(0, nh)) + nowYear + 
            model.substring(nh + 4); 
      } 
      if (integer1 != null && integer1.toString().equals("3")) {
        int nh = model.indexOf("[机关代字]");
        if (nh != -1)
          model = String.valueOf(model.substring(0, nh)) + sendWord + 
            model.substring(nh + 6); 
      } 
      int xh = model.indexOf("[序号]");
      if (xh != -1) {
        long_1 = numpo.getId();
        num_f1 = model.substring(0, xh);
        num_f2 = (new StringBuilder(String.valueOf(numfigint))).toString();
        num_f3 = model.substring(xh + 4);
      } 
    } 
    if (seqpo != null && seqpo.getId() != null) {
      long_2 = seqpo.getId();
      Integer isyear = seqpo.getSeqIsYear();
      Integer seqfig = seqpo.getSeqfig();
      int seqfigint = seqfig.intValue() + 1;
      String sendSeqfig = (new StringBuilder(String.valueOf(seqfigint))).toString();
      lastseqfig = sendSeqfig;
      String seqmodel = seqpo.getSeqMode();
      int seqbitNum = seqpo.getSeqBitNum().intValue();
      if (isyear.toString().equals("1")) {
        int nh = seqmodel.indexOf("[年度]");
        if (nh != -1)
          seqmodel = String.valueOf(seqmodel.substring(0, nh)) + nowYear + seqmodel.substring(nh + 4); 
      } 
      int xh = seqmodel.indexOf("[顺序号]");
      if (xh != -1) {
        int dd = sendSeqfig.length();
        if (seqbitNum > dd) {
          String ling = "";
          for (int jj = 0; jj < seqbitNum - dd; jj++) {
            ling = String.valueOf(ling) + "0";
            zeroNum++;
          } 
          sendSeqfig = String.valueOf(ling) + sendSeqfig;
        } 
        seqmodel = String.valueOf(seqmodel.substring(0, xh)) + sendSeqfig + seqmodel.substring(xh + 5);
      } 
      seqstr = String.valueOf(seqstr) + seqmodel;
    } 
    Map<Object, Object> wordmap = new HashMap<Object, Object>();
    wordmap.put("numId", long_1);
    wordmap.put("num_f1", num_f1);
    wordmap.put("num_f2", num_f2);
    wordmap.put("num_f3", num_f3);
    wordmap.put("seqId", long_2);
    wordmap.put("seqstr", seqstr);
    wordmap.put("seqfig", lastseqfig);
    wordmap.put("changeNumType", changeNumType);
    wordmap.put("zeroNum", zeroNum);
    wordmap.put("numNum", numweishu);
    wordmap.put("initValue", numpo.getInitValue());
    request.setAttribute("wordmap", wordmap);
  }
  
  private void mailtransmit(HttpServletRequest request) {
    request.setAttribute("transmitSu", "1");
  }
  
  private String sendToMyReceive(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String editId = (httpServletRequest.getParameter("editId") == null) ? "" : httpServletRequest.getParameter("editId").toString();
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    String userName = (httpSession.getAttribute("userName") == null) ? "" : httpSession.getAttribute("userName").toString();
    String orgName = (httpSession.getAttribute("orgName") == null) ? "0" : httpSession.getAttribute("orgName").toString();
    SendFileBD sendfileBD = new SendFileBD();
    Integer result = sendfileBD.send(editId, httpServletRequest.getParameter("sendToId"), domainId, userName, orgName);
    httpServletRequest.setAttribute("toName", httpServletRequest.getParameter("toName"));
    sendSupplyMessage(editId, httpServletRequest);
    return String.valueOf(result);
  }
  
  private String readLilst(HttpServletRequest request) {
    String tableId = request.getParameter("tableId");
    String processId = request.getParameter("processId");
    String recordId = request.getParameter("recordId");
    String readType = request.getParameter("readType");
    String workstatus = "2";
    if (readType.equals("readlist"))
      workstatus = "102"; 
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 10;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "  com.js.oa.jsflow.po.WFWorkPO wk ,com.js.system.vo.usermanager.EmployeeVO AS user join user.organizations organization";
    String whereSql = "  wk.workRecordId= " + recordId + " and wk.workStatus= '" + workstatus + "' and wk.wfCurEmployeeId=user.empId";
    try {
      Page page = new Page(" user.empName ,organization.orgName,wk.workDoneWithDate ", 
          fromwhere, 
          " where " + whereSql + 
          "  order by wk.wfWorkId desc  ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 10) {
        offset -= 10;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,tableId,processId,recordId,readType");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return readType;
  }
  
  private void noReadList(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 10;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SendDocumentSeqPO po ";
    String whereSql = " 1=1";
    try {
      Page page = new Page("po.id, po.seqBitNum,po.seqFileType,po.seqFormat,po.seqInitValue,po.seqIsUse ,po.seqIsYear,po.seqMode,po.seqName,po.seqUnitName", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
      page.setPageSize(pageSize);
      page.setcurrentPage(currentPage);
      List list = page.getResultList();
      if (list != null && 
        list.size() == 0 && offset >= 10) {
        offset -= 10;
        currentPage = offset / pageSize + 1;
        page.setPageSize(pageSize);
        page.setcurrentPage(currentPage);
        list = page.getResultList();
        request.setAttribute("new.offset", (new StringBuilder(String.valueOf(offset))).toString());
        request.setAttribute("pager.realCurrent", (new StringBuilder(String.valueOf(currentPage))).toString());
      } 
      String recordCount = String.valueOf(page.getRecordCount());
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void list3(HttpServletRequest request) {
    HttpSession httpSession = request.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : httpSession.getAttribute("domainId").toString();
    int pageSize = 15;
    int offset = 0;
    if (request.getParameter("pager.offset") != null)
      offset = Integer.parseInt(request.getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    String fromwhere = 
      "com.js.doc.doc.po.SendDocumentSeqPO po ";
    String whereSql = " 1=1 ";
    if (request.getParameter("seqName") != null && !request.getParameter("seqName").toString().equals(""))
      whereSql = String.valueOf(whereSql) + " and po.seqName like '%" + request.getParameter("seqName") + "%' "; 
    try {
      Page page = new Page("po.id, po.seqBitNum,po.seqFileType,po.seqFormat,po.seqInitValue,po.seqIsUse ,po.seqIsYear,po.seqMode,po.seqName,po.seqUnitName", 
          fromwhere, 
          " where " + whereSql + 
          "  order by po.id desc ");
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
      page.setcurrentPage(currentPage);
      request.setAttribute("mylist", list);
      request.setAttribute("recordCount", recordCount);
      request.setAttribute("maxPageItems", String.valueOf(pageSize));
      request.setAttribute("pageParameters", "action,seqName,queryItem,queryBeginDate,queryStatus,queryEndDate,queryNumber,querySecret,queryTransPersonName,queryTitle,queryOrg,redHeadId");
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void sendAssociate(HttpServletRequest request) {
    String sendFileId = request.getParameter("sendFileId");
    SendFileBD bd = new SendFileBD();
    List list = bd.getSendAssociateList(sendFileId);
    request.setAttribute("mylist", list);
  }
  
  public void sendSupplyMessage(String recordId, HttpServletRequest request) {
    String tableId = request.getParameter("tableId");
    String sendToId = request.getParameter("sendToId");
    String[] userIdArr = (String[])null;
    String userIdSting = (new SendFileBD()).getSendMsgReceiver(sendToId);
    if (userIdSting != null) {
      userIdArr = userIdSting.split(",");
      String title = "您有新收文：" + request.getParameter("documentSendFileTitle");
      List<MessagesVO> msgList = new ArrayList();
      Date now = new Date();
      for (int i = 0; i < userIdArr.length; i++) {
        MessagesVO msgVO = new MessagesVO();
        msgVO.setMessage_date_begin(now);
        msgVO.setMessage_date_end(new Date("2050/1/1"));
        msgVO.setMessage_send_UserId(0L);
        msgVO.setMessage_send_UserName("系统");
        msgVO.setMessage_show(1);
        msgVO.setMessage_status(1);
        msgVO.setMessage_time(now);
        msgVO.setMessage_title(title);
        msgVO.setMessage_toUserId(Long.parseLong(userIdArr[i]));
        msgVO.setMessage_type("document");
        msgVO.setData_id(Long.valueOf(recordId).longValue());
        msgVO.setMessage_url("/jsoa/doc/doc/jump_recievefile.jsp?recordId=" + recordId + "&tableId=" + tableId);
        msgList.add(msgVO);
      } 
      MessagesBD messagesBD = new MessagesBD();
      try {
        messagesBD.messageArrayAdd(msgList);
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
    } 
  }
  
  private String getscopeWhere(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String userId = session.getAttribute("userId").toString();
    String orgId = session.getAttribute("orgId").toString();
    String orgIdString = session.getAttribute("orgIdString").toString();
    InformationBD informationBD1 = new InformationBD();
    List<String> groupList = new ArrayList();
    groupList = informationBD1.getAllGroupByUserId(userId);
    String readerWhere = " and (";
    readerWhere = String.valueOf(readerWhere) + "((po.receiveUser is null or po.receiveUser ='') and (po.receiveOrg is null or po.receiveOrg='') and ( po.receiveGroup is null or po.receiveGroup='') ) ";
    if (orgIdString != null && orgIdString.length() > 2) {
      String cStr = orgIdString.substring(1, orgIdString.length() - 1);
      cStr = cStr.replaceAll("\\$", ",");
      cStr = cStr.replaceAll(",,", ",");
      String[] gg1 = cStr.split(",");
      if (gg1 != null && gg1.length > 0)
        for (int i = 0; i < gg1.length; i++)
          readerWhere = String.valueOf(readerWhere) + " or po.receiveOrg like '%*" + 
            gg1[i] + "*%' ";  
    } 
    readerWhere = String.valueOf(readerWhere) + " or po.receiveUser like '%$" + userId + "$%' ";
    if (groupList != null && groupList.size() > 0)
      for (int i = 0; i < groupList.size(); i++) {
        String groupId = groupList.get(i);
        readerWhere = String.valueOf(readerWhere) + "  or  po.receiveGroup like '%@" + 
          groupId + "@%' ";
      }  
    readerWhere = String.valueOf(readerWhere) + " ) ";
    return readerWhere;
  }
}
