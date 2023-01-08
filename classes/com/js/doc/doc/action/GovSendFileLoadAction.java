package com.js.doc.doc.action;

import com.js.doc.doc.po.GovCustomCheckedFieldPO;
import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.service.CovCustomBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.oa.info.infomanager.service.InformationBD;
import com.js.oa.jsflow.bean.WorkFlowEJBBeanForRWS;
import com.js.oa.jsflow.po.WFActivityPO;
import com.js.oa.jsflow.service.ActivityBD;
import com.js.oa.jsflow.util.WorkflowCommon;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GovSendFileLoadAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovSendFileActionForm govSendFileActionForm = (GovSendFileActionForm)actionForm;
    String action = request.getParameter("action");
    HttpSession session = request.getSession();
    String send = request.getParameter("send");
    request.setAttribute("send", request.getParameter("send"));
    if ("load".equals(action)) {
      load(request, govSendFileActionForm);
      String isRWS = request.getParameter("isRWS");
      if (isRWS == null || "null".equalsIgnoreCase(isRWS))
        isRWS = ""; 
      if (session.getAttribute("mobile") != null && session.getAttribute("mobile").toString().equals("1"))
        return actionMapping.findForward("mobile_modi"); 
      if (request.getParameter("mobile") != null && request.getParameter("mobile").toString().equals("1"))
        return actionMapping.findForward("mobile_modi"); 
      String search = (request.getParameter("search") != null) ? request.getParameter("search").toString() : "";
      String from = (request.getParameter("from") != null) ? request.getParameter("from").toString() : "";
      String dealwith = (request.getParameter("dealwith") != null) ? request.getParameter("dealwith").toString() : "";
      String workTitle = (request.getParameter("workTitle") != null) ? request.getParameter("workTitle").toString() : "";
      String activityName = (request.getParameter("activityName") != null) ? request.getParameter("activityName").toString() : "";
      String submitPersonId = (request.getParameter("submitPersonId") != null) ? request.getParameter("submitPersonId").toString() : "";
      String submitPerson = (request.getParameter("submitPerson") != null) ? request.getParameter("submitPerson").toString() : "";
      String work = (request.getParameter("work") != null) ? request.getParameter("work").toString() : "";
      String workType = (request.getParameter("workType") != null) ? request.getParameter("workType").toString() : "";
      String activity = (request.getParameter("activity") != null) ? request.getParameter("activity").toString() : "";
      String table = (request.getParameter("table") != null) ? request.getParameter("table").toString() : "";
      ActivityBD activityBD = new ActivityBD();
      if (activity != null && !"".equals(activity) && !"0".equals(activity) && !"null".equals(activity)) {
        WFActivityPO activityPO = activityBD.getActivityInfo(activity);
        if (activityPO != null && 
          activityPO.getFormId() != null && !"".equals(activityPO.getFormId()))
          table = activityPO.getFormId(); 
      } 
      String record = (request.getParameter("record") != null) ? request.getParameter("record").toString() : "";
      if ("1".equals(isRWS)) {
        try {
          String tableName = "DOC_DOCUMENTSENDFILE";
          if (WorkFlowEJBBeanForRWS.canBeYuGuiDang(tableName, record)) {
            request.setAttribute("preGD", "1");
          } else {
            request.setAttribute("preGD", "0");
          } 
          if (WorkFlowEJBBeanForRWS.canBeGuiDang(tableName, record)) {
            request.setAttribute("canBeGuiDang", "1");
          } else {
            request.setAttribute("canBeGuiDang", "0");
          } 
          if (WorkFlowEJBBeanForRWS.canBeCheHuiGuiDang(tableName, record)) {
            request.setAttribute("canBeCheHuiGuiDang", "1");
          } else {
            request.setAttribute("canBeCheHuiGuiDang", "0");
          } 
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } else {
        request.setAttribute("preGD", "0");
        request.setAttribute("canBeGuiDang", "0");
        request.setAttribute("canBeCheHuiGuiDang", "0");
      } 
      String processName = (request.getParameter("processName") != null) ? request.getParameter("processName").toString() : "";
      String workStatus = (request.getParameter("workStatus") != null) ? request.getParameter("workStatus").toString() : "";
      String submitTime = (request.getParameter("submitTime") != null) ? request.getParameter("submitTime").toString() : "";
      String processId = (request.getParameter("processId") != null) ? request.getParameter("processId").toString() : "";
      String stepCount = (request.getParameter("stepCount") != null) ? request.getParameter("stepCount").toString() : "";
      String isStandForWork = (request.getParameter("isStandForWork") != null) ? request.getParameter("isStandForWork").toString() : "";
      String standForUserId = (request.getParameter("standForUserId") != null) ? request.getParameter("standForUserId").toString() : "";
      String standForUserName = (request.getParameter("standForUserName") != null) ? request.getParameter("standForUserName").toString() : "";
      String initActivity = (request.getParameter("initActivity") != null) ? request.getParameter("initActivity").toString() : "";
      String initActivityName = (request.getParameter("initActivityName") != null) ? request.getParameter("initActivityName").toString() : "";
      String submitPersonTime = (request.getParameter("submitPersonTime") != null) ? request.getParameter("submitPersonTime").toString() : "";
      String tranType = (request.getParameter("tranType") != null) ? request.getParameter("tranType").toString() : "";
      String tranFromPersonId = (request.getParameter("tranFromPersonId") != null) ? request.getParameter("tranFromPersonId").toString() : "";
      String processDeadlineDate = (request.getParameter("processDeadlineDate") != null) ? request.getParameter("processDeadlineDate").toString() : "";
      String isPrint = (request.getParameter("isPrint") != null) ? request.getParameter("isPrint").toString() : "";
      String url = "?search=" + search + "&from=" + from + "&dealwith=" + dealwith + "&workTitle=" + workTitle + "&activityName=" + activityName + "&submitPersonId=" + submitPersonId + "&submitPerson=" + submitPerson + "&work=" + work + "&workType=" + workType + "&activity=" + activity + "&table=" + table + "&record=" + record + "&processName=" + processName + "&workStatus=" + workStatus + "&submitTime=" + submitTime + "&processId=" + processId + "&stepCount=" + stepCount + "&isStandForWork=" + isStandForWork + "&standForUserId=" + standForUserId + "&standForUserName=" + standForUserName + "&initActivity=" + initActivity + "&initActivityName=" + initActivityName + "&submitPersonTime=" + submitPersonTime + "&tranType=" + tranType + "&tranFromPersonId=" + tranFromPersonId + "&processDeadlineDate=" + processDeadlineDate + "&send=" + send;
      request.setAttribute("url", url.substring(1));
      SendFileBD sendFileBD = new SendFileBD();
      List<Object[]> tableInfoList = sendFileBD.getWfTableInfoByTableId(table);
      Object object = "";
      if (tableInfoList != null && tableInfoList.size() > 0) {
        Object[] tableInfoObj = tableInfoList.get(0);
        object = tableInfoObj[0];
      } 
      CovCustomBD cbd = new CovCustomBD();
      String gffType = "0";
      if (isPrint.equals("1"))
        gffType = "1"; 
      List<GovCustomCheckedFieldPO> commentList = cbd.loadCheckFieldListByDisplayType(table, "10", gffType);
      if (commentList != null && commentList.size() > 0) {
        String[] commentName = new String[commentList.size()];
        for (int i = 0; i < commentList.size(); i++) {
          GovCustomCheckedFieldPO po = commentList.get(i);
          commentName[i] = po.getGffName();
        } 
        request.setAttribute("commentName", commentName);
      } 
      if (request.getParameter("print") != null)
        return actionMapping.findForward("prints"); 
      if (object.equals("发文表")) {
        if (isPrint.equals("1"))
          return actionMapping.findForward("print"); 
        return actionMapping.findForward("modi");
      } 
      ActionForward actionForward = new ActionForward();
      if (isPrint.equals("1")) {
        url = "/doc/doc/" + table + "_0_print.jsp" + url;
      } else {
        url = "/doc/doc/" + table + "_0_modi.jsp" + url;
      } 
      actionForward.setPath(url);
      return actionForward;
    } 
    ActionForward forward = new ActionForward();
    forward.setPath("/jsoa/doc/doc/doc_sendfile_modi.jsp");
    return forward;
  }
  
  private void load(HttpServletRequest httpServletRequest, GovSendFileActionForm govSendFileActionForm) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    SendFileBD sendFileBD = new SendFileBD();
    WorkflowCommon workflowCommon = new WorkflowCommon();
    String sendFileId = httpServletRequest.getParameter("record");
    if (sendFileId == null)
      sendFileId = httpServletRequest.getParameter("editId"); 
    GovDocumentSendFilePO po = sendFileBD.load(sendFileId);
    int workStatus = (httpServletRequest.getParameter("workStatus") == null) ? 100 : Integer.parseInt(httpServletRequest.getParameter("workStatus"));
    String curModifyField = "";
    if (workStatus == 0 || workStatus == 2)
      curModifyField = workflowCommon.getCurActivityWriteField(httpServletRequest); 
    httpServletRequest.setAttribute("curModifyField", curModifyField);
    Map commFieldMap = workflowCommon.getCurActivityCommField(httpServletRequest);
    String curCommField = "", curPassRoundCommField = "";
    if (commFieldMap != null) {
      if (commFieldMap.get("actiCommField") != null && 
        !commFieldMap.get("actiCommField").toString().equals("") && 
        !commFieldMap.get("actiCommField").toString().equals("-1") && 
        !commFieldMap.get("actiCommField").toString().toUpperCase().equals("NULL"))
        curCommField = commFieldMap.get("actiCommField").toString(); 
      if (commFieldMap.get("passRoundCommField") != null && 
        !commFieldMap.get("passRoundCommField").toString().equals("") && 
        !commFieldMap.get("passRoundCommField").toString().equals("-1") && 
        !commFieldMap.get("passRoundCommField").toString().toUpperCase().equals("NULL"))
        curPassRoundCommField = commFieldMap.get("passRoundCommField").toString(); 
    } 
    httpServletRequest.setAttribute("curCommField", curCommField);
    httpServletRequest.setAttribute("curPassRoundCommField", curPassRoundCommField);
    httpServletRequest.setAttribute("curProcCommField", workflowCommon.getCurProcCommField(httpServletRequest));
    httpServletRequest.setAttribute("yearItr", getYearItr());
    httpServletRequest.setAttribute("seallist", new ArrayList());
    if (po.getSendFileRedHeadId() != 0L && !"null".equals(po.getSendFileRedHeadId()) && 
      !"-1".equals((new StringBuilder(String.valueOf(po.getSendFileRedHeadId()))).toString()))
      httpServletRequest.setAttribute("seallist", sendFileBD.getSealList((new StringBuilder(String.valueOf(po.getSendFileRedHeadId()))).toString())); 
    govSendFileActionForm.setSendFilePoNumId((po.getSendFilePoNumId() == null) ? "" : po.getSendFilePoNumId().toString());
    govSendFileActionForm.setTemplateId((po.getSendFileTemId() == null) ? "" : po.getSendFileTemId().toString());
    govSendFileActionForm.setZjkyWordId((po.getZjkyWordId() == null) ? "" : po.getZjkyWordId().toString());
    httpServletRequest.setAttribute("zjkyWordId", (po.getZjkyWordId() != null) ? po.getZjkyWordId().toString() : "");
    govSendFileActionForm.setDocumentSendFileWriteOrg(po.getDocumentSendFileWriteOrg());
    httpServletRequest.setAttribute("documentSendFileWriteOrg", po.getDocumentSendFileWriteOrg());
    govSendFileActionForm.setDocumentSendFileTitle(po.getDocumentSendFileTitle());
    httpServletRequest.setAttribute("documentSendFileTitle", po.getDocumentSendFileTitle());
    govSendFileActionForm.setDocumentSendFileAssumePeople(po.getDocumentSendFileAssumePeople());
    httpServletRequest.setAttribute("documentSendFileAssumePeople", po.getDocumentSendFileAssumePeople());
    govSendFileActionForm.setDocumentSendFileByteNumber(po.getDocumentSendFileByteNumber());
    httpServletRequest.setAttribute("documentSendFileByteNumber", po.getDocumentSendFileByteNumber());
    govSendFileActionForm.setDocumentSendFileCheckDate(po.getDocumentSendFileCheckDate());
    httpServletRequest.setAttribute("documentSendFileCheckDate", po.getDocumentSendFileCheckDate());
    govSendFileActionForm.setDocumentSendFileCounterSign(po.getDocumentSendFileCounterSign());
    httpServletRequest.setAttribute("documentSendFileCounterSign", po.getDocumentSendFileCounterSign());
    govSendFileActionForm.setDocumentSendFileHead(po.getDocumentSendFileHead());
    govSendFileActionForm.setDocumentSendFilePrintNumber((new StringBuilder(String.valueOf(po.getDocumentSendFilePrintNumber()))).toString());
    httpServletRequest.setAttribute("documentSendFilePrintNumber", (new StringBuilder(String.valueOf(po.getDocumentSendFilePrintNumber()))).toString());
    govSendFileActionForm.setDocumentSendFileSecurityGrade(po.getDocumentSendFileSecurityGrade());
    httpServletRequest.setAttribute("documentSendFileSecurityGrade", (new StringBuilder(String.valueOf(po.getDocumentSendFileSecurityGrade()))).toString());
    govSendFileActionForm.setOpenProperty(po.getOpenProperty());
    httpServletRequest.setAttribute("openProperty", po.getOpenProperty());
    httpServletRequest.setAttribute("signsendTime", po.getSignsendTime());
    httpServletRequest.setAttribute("sendFileDate", po.getDocumentSendFileDate());
    httpServletRequest.setAttribute("sendFileSendDate", po.getDocumentSendFileSendDate());
    httpServletRequest.setAttribute("documentSendFileTime", po.getDocumentSendFileDate());
    httpServletRequest.setAttribute("documentSendFileSendTime", po.getDocumentSendFileSendDate());
    govSendFileActionForm.setDocumentSendFileTopicWord(po.getDocumentSendFileTopicWord());
    httpServletRequest.setAttribute("documentSendFileTopicWord", (new StringBuilder(String.valueOf(po.getDocumentSendFileTopicWord()))).toString());
    govSendFileActionForm.setDocumentSendFileWriteOrg(po.getDocumentSendFileWriteOrg());
    httpServletRequest.setAttribute("documentSendFileWriteOrg", (new StringBuilder(String.valueOf(po.getDocumentSendFileWriteOrg()))).toString());
    govSendFileActionForm.setSendToId(String.valueOf(po.getSendToOrg()) + po.getSendToGroup() + po.getSendToEmp());
    govSendFileActionForm.setSendToName(po.getSendToName());
    govSendFileActionForm.setSubmitFileType(po.getSubmitFileType());
    govSendFileActionForm.setOid(po.getOid());
    govSendFileActionForm.setField1(po.getField1());
    httpServletRequest.setAttribute("field1", (new StringBuilder(String.valueOf(po.getField1()))).toString());
    govSendFileActionForm.setSendFieldSelectMoreEmp(po.getSendFieldSelectMoreEmp());
    httpServletRequest.setAttribute("sendFieldSelectMoreEmp", po.getSendFieldSelectMoreEmp());
    govSendFileActionForm.setField2(po.getField2());
    httpServletRequest.setAttribute("field2", (new StringBuilder(String.valueOf(po.getField2()))).toString());
    govSendFileActionForm.setField3(po.getField3());
    httpServletRequest.setAttribute("field3", (new StringBuilder(String.valueOf(po.getField3()))).toString());
    govSendFileActionForm.setField4(po.getField4());
    httpServletRequest.setAttribute("field4", (new StringBuilder(String.valueOf(po.getField4()))).toString());
    govSendFileActionForm.setField5(po.getField5());
    httpServletRequest.setAttribute("field5", (new StringBuilder(String.valueOf(po.getField5()))).toString());
    govSendFileActionForm.setField6(po.getField6());
    httpServletRequest.setAttribute("field6", (new StringBuilder(String.valueOf(po.getField6()))).toString());
    govSendFileActionForm.setField7(po.getField7());
    httpServletRequest.setAttribute("field7", (new StringBuilder(String.valueOf(po.getField7()))).toString());
    govSendFileActionForm.setField8(po.getField8());
    httpServletRequest.setAttribute("field8", (new StringBuilder(String.valueOf(po.getField8()))).toString());
    govSendFileActionForm.setField9(po.getField9());
    httpServletRequest.setAttribute("field9", (new StringBuilder(String.valueOf(po.getField9()))).toString());
    govSendFileActionForm.setField10(po.getField10());
    httpServletRequest.setAttribute("field10", (new StringBuilder(String.valueOf(po.getField10()))).toString());
    if (po.getZjkySeq() == null) {
      govSendFileActionForm.setZjkySeq("");
    } else {
      govSendFileActionForm.setZjkySeq(po.getZjkySeq());
      httpServletRequest.setAttribute("zjkySeq", po.getZjkySeq());
    } 
    if (po.getZjkyContentLevel() != null && !"null".equals(po.getZjkyContentLevel())) {
      govSendFileActionForm.setZjkyContentLevel(po.getZjkyContentLevel());
      httpServletRequest.setAttribute("zjkyContentLevel", (new StringBuilder(String.valueOf(po.getZjkyContentLevel()))).toString());
    } else {
      govSendFileActionForm.setZjkyContentLevel("");
    } 
    if (po.getZjkySecrecyterm() != null && 
      !"null".equals(po.getZjkySecrecyterm())) {
      govSendFileActionForm.setZjkySecrecyterm(po.getZjkySecrecyterm());
      httpServletRequest.setAttribute("zjkySecrecyterm", (new StringBuilder(String.valueOf(po.getZjkySecrecyterm()))).toString());
    } else {
      govSendFileActionForm.setZjkySecrecyterm("");
    } 
    httpServletRequest.setAttribute("content", po.getSendFileText());
    if (po.getReceiveFileId() != null) {
      httpServletRequest.setAttribute("fromReceiveFileLink", po.getReceiveFileLink());
      httpServletRequest.setAttribute("fromReceiveFileId", po.getReceiveFileId());
    } 
    if (po.getFileSendCheckId() != null) {
      httpServletRequest.setAttribute("fromFileSendCheckId", po.getFileSendCheckId());
      httpServletRequest.setAttribute("fromFileSendCheckLink", po.getFileSendCheckLink());
    } 
    if (po.getSendFileDepartWord() != null && !po.getSendFileDepartWord().toString().equals(""))
      httpServletRequest.setAttribute("sendFileDeparatWord", po.getSendFileDepartWord()); 
    httpServletRequest.setAttribute("pn3Name", po.getSendToName());
    String sendToType = po.getSendToType();
    if (sendToType != null) {
      govSendFileActionForm.setSendToType(sendToType);
      if ("0".equals(sendToType)) {
        govSendFileActionForm.setToPerson1(po.getMainToName());
        govSendFileActionForm.setToPerson2(po.getCopyToName());
        httpServletRequest.setAttribute("toPerson1", po.getMainToName());
        httpServletRequest.setAttribute("toPerson2", po.getCopyToName());
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
    govSendFileActionForm.setSendFileAccessoryDesc(po.getSendFileAccessoryDesc());
    httpServletRequest.setAttribute("sendFileAccessoryDesc", po.getSendFileAccessoryDesc());
    govSendFileActionForm.setSendFileAgentDraft(po.getSendFileAgentDraft());
    httpServletRequest.setAttribute("sendFileAgentDraft", po.getSendFileAgentDraft());
    govSendFileActionForm.setSendFileDraft(po.getSendFileDraft());
    httpServletRequest.setAttribute("sendFileDraft", po.getSendFileDraft());
    govSendFileActionForm.setSendFileGrade(po.getSendFileGrade());
    httpServletRequest.setAttribute("sendFileGrade", po.getSendFileGrade());
    govSendFileActionForm.setSendFilePrinter(po.getSendFilePrinter());
    httpServletRequest.setAttribute("sendFilePrinter", po.getSendFilePrinter());
    govSendFileActionForm.setSendFileProof(po.getSendFileProof());
    httpServletRequest.setAttribute("sendFileProof", po.getSendFileProof());
    govSendFileActionForm.setSendFileText(po.getSendFileText());
    govSendFileActionForm.setReceiveFileIsFlowMode(po.getReceiveFileIsFlowMode());
    govSendFileActionForm.setSendFileRedHeadId(po.getSendFileRedHeadId());
    govSendFileActionForm.setSendFileSealId(po.getSendFileSealId());
    govSendFileActionForm.setSendFileLink(po.getSendFileLink());
    govSendFileActionForm.setSendFileRedHeadPic(po.getSendFileRedHeadPic());
    govSendFileActionForm.setSendFileSealPic(po.getSendFileSealPic());
    govSendFileActionForm.setSendFileType(po.getSendFileType());
    httpServletRequest.setAttribute("sendToType", sendToType);
    httpServletRequest.setAttribute("accessoryName", po.getAccessoryName());
    httpServletRequest.setAttribute("accessorySaveName", po.getAccessorySaveName());
    httpServletRequest.setAttribute("contentAccName", "正文" + po.getDocumentWordType());
    httpServletRequest.setAttribute("contentAccSaveName", String.valueOf(po.getGoldGridId()) + po.getDocumentWordType());
    httpServletRequest.setAttribute("documentCreateTime", po.getCreatedTime());
    httpServletRequest.setAttribute("sendStatus", po.getTransactStatus());
    govSendFileActionForm.setDocumentSendFileAssumeUnit(po.getDocumentSendFileAssumeUnit());
    govSendFileActionForm.setSendFileMassDraft(po.getSendFileMassDraft());
    govSendFileActionForm.setSendFileProveDraft(po.getSendFileProveDraft());
    govSendFileActionForm.setSendFileReadComment(po.getSendFileReadComment());
    govSendFileActionForm.setDocumentSendFileCheckCommit(po.getDocumentSendFileCheckCommit());
    govSendFileActionForm.setDocumentSendFileSendFile(po.getDocumentSendFileSendFile());
    govSendFileActionForm.setDocumentWordType(po.getDocumentWordType());
    httpServletRequest.setAttribute("documentSendFileAssumeUnit", po.getDocumentSendFileAssumeUnit());
    httpServletRequest.setAttribute("sendFileMassDraft", po.getSendFileMassDraft());
    httpServletRequest.setAttribute("sendFileProveDraft", po.getSendFileProveDraft());
    httpServletRequest.setAttribute("sendFileReadComment", po.getSendFileReadComment());
    httpServletRequest.setAttribute("documentSendFileCheckCommit", po.getDocumentSendFileCheckCommit());
    httpServletRequest.setAttribute("documentSendFileSendFile", po.getDocumentSendFileSendFile());
    govSendFileActionForm.setSendTextField1(po.getSendTextField1());
    govSendFileActionForm.setSendTextField2(po.getSendTextField2());
    govSendFileActionForm.setSendDropDownSelect1(po.getSendDropDownSelect1());
    govSendFileActionForm.setSendDropDownSelect2(po.getSendDropDownSelect2());
    govSendFileActionForm.setSendMutliTextField1(po.getSendMutliTextField1());
    httpServletRequest.setAttribute("sendTextField1", po.getSendTextField1());
    httpServletRequest.setAttribute("sendTextField2", po.getSendTextField2());
    httpServletRequest.setAttribute("sendDropDownSelect1", po.getSendDropDownSelect1());
    httpServletRequest.setAttribute("sendDropDownSelect2", po.getSendDropDownSelect2());
    httpServletRequest.setAttribute("sendMutliTextField1", po.getSendMutliTextField1());
    if (po.getToPerson1Id() != null) {
      govSendFileActionForm.setToPerson1Id(po.getToPerson1Id());
    } else {
      govSendFileActionForm.setToPerson1Id("");
    } 
    if (po.getToPerson2Id() != null) {
      govSendFileActionForm.setToPerson2Id(po.getToPerson2Id());
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
    String wordId = (po.getZjkyWordId() != null) ? (String)po.getZjkyWordId() : "";
    String wordName = (po.getSendFileDepartWord() != null) ? po.getSendFileDepartWord() : "";
    String tempId = (po.getSendFileTemId() != null) ? po.getSendFileTemId().toString() : "";
    if (wordId.indexOf(";") >= 0)
      wordId.substring(0, wordId.indexOf(";")); 
    if (wordId != null && !wordId.equals("")) {
      govSendFileActionForm.setSendFileDepartWord(String.valueOf(wordId) + ";" + wordName + ";" + tempId);
    } else {
      govSendFileActionForm.setSendFileDepartWord("");
    } 
    SenddocumentBD bd = new SenddocumentBD();
    List<String[]> list = new ArrayList();
    String proceedId = httpServletRequest.getParameter("processId");
    if (proceedId != null && !proceedId.equals("")) {
      list = bd.getWordByProceeIdAnd4Type(proceedId, "3", getscopeWhere(httpServletRequest));
    } else {
      String[] wordList = { wordId, wordName, tempId };
      list.add(wordList);
    } 
    httpServletRequest.setAttribute("unitWord", list);
    httpServletRequest.setAttribute("myform", govSendFileActionForm);
  }
  
  private Iterator getYearItr() {
    List<Integer> itr = new ArrayList();
    Calendar now = new GregorianCalendar();
    int year = now.get(1);
    for (int i = -10; i <= 10; i++)
      itr.add(new Integer(year + i)); 
    return itr.iterator();
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
    if (orgIdString != null && orgIdString.length() > 3) {
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
