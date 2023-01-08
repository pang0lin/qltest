package com.js.doc.doc.action;

import com.js.doc.doc.po.GovCustomCheckedFieldPO;
import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.service.CovCustomBD;
import com.js.doc.doc.service.GovComeFileUnitBD;
import com.js.doc.doc.service.GovReceiveFileTypeBD;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.oa.jsflow.util.WorkflowCommon;
import com.js.system.manager.service.ManagerService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
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

public class GovReceiveFileLoadAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse httpServletResponse) {
    GovReceiveFileActionForm govReceiveFileActionForm = (GovReceiveFileActionForm)actionForm;
    String action = request.getParameter("action");
    if ("load".equals(action)) {
      load(request, govReceiveFileActionForm);
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
      String record = (request.getParameter("record") != null) ? request.getParameter("record").toString() : "";
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
      String url = "?search=" + search + "&from=" + from + "&dealwith=" + dealwith + "&workTitle=" + workTitle + "&activityName=" + activityName + "&submitPersonId=" + submitPersonId + "&submitPerson=" + submitPerson + "&work=" + work + "&workType=" + workType + "&activity=" + activity + "&table=" + table + "&record=" + record + "&processName=" + processName + "&workStatus=" + workStatus + "&submitTime=" + submitTime + "&processId=" + processId + "&stepCount=" + stepCount + "&isStandForWork=" + isStandForWork + "&standForUserId=" + standForUserId + "&standForUserName=" + standForUserName + "&initActivity=" + initActivity + "&initActivityName=" + initActivityName + "&submitPersonTime=" + submitPersonTime + "&tranType=" + tranType + "&tranFromPersonId=" + tranFromPersonId + "&processDeadlineDate=" + processDeadlineDate;
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
      if (object.equals("收文表")) {
        if (isPrint.equals("1"))
          return actionMapping.findForward("print"); 
        return actionMapping.findForward("modi");
      } 
      ActionForward forward = new ActionForward();
      if (isPrint.equals("1")) {
        url = "/doc/doc/" + table + "_1_print.jsp" + url;
      } else {
        url = "/doc/doc/" + table + "_1_modi.jsp" + url;
      } 
      forward.setPath(url);
      return forward;
    } 
    return actionMapping.findForward("modi");
  }
  
  private void load(HttpServletRequest httpServletRequest, GovReceiveFileActionForm govReceiveFileActionForm) {
    ReceiveFileBD receiveFileBD = new ReceiveFileBD();
    WorkflowCommon workflowCommon = new WorkflowCommon();
    int workStatus = (httpServletRequest.getParameter("workStatus") == null) ? 100 : Integer.parseInt(httpServletRequest.getParameter("workStatus"));
    String curModifyField = "";
    if (workStatus == 0 || workStatus == 2)
      curModifyField = workflowCommon.getCurActivityWriteField(httpServletRequest); 
    httpServletRequest.setAttribute("curModifyField", curModifyField);
    getReceiveFileType(httpServletRequest);
    getComeFileUnit(httpServletRequest);
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
    GovReceiveFilePO po = receiveFileBD.load((httpServletRequest.getParameter("record") == null) ? httpServletRequest.getParameter("editId") : httpServletRequest.getParameter("record"));
    if (po.getSendFileId() != null) {
      httpServletRequest.setAttribute("sendFileId", po.getSendFileId());
      httpServletRequest.setAttribute("sendFileLink", po.getSendFileLink());
      httpServletRequest.setAttribute("sendFileTitle", po.getSendFileTitle());
    } 
    if (po.getExchangeFileId() != null) {
      httpServletRequest.setAttribute("exchangeFileId", po.getExchangeFileId());
      httpServletRequest.setAttribute("exchangeFileLink", po.getExchangeFileLink());
      httpServletRequest.setAttribute("exchangeFileTitle", po.getExchangeFileTitle());
    } 
    httpServletRequest.setAttribute("lwh", po.getLwh());
    govReceiveFileActionForm.setReceiveFileDoComment(po.getReceiveFileDoComment());
    httpServletRequest.setAttribute("receiveFileDoComment", po.getReceiveFileDoComment());
    govReceiveFileActionForm.setReceiveFileDoDepart((new StringBuilder(String.valueOf(po.getReceiveFileDoDepart()))).toString());
    httpServletRequest.setAttribute("receiveFileDoDepart", po.getReceiveFileDoDepart());
    govReceiveFileActionForm.setReceiveFileDoDepartNm(po.getReceiveFileDoDepartNm());
    httpServletRequest.setAttribute("receiveFileDoDepartNm", po.getReceiveFileDoDepartNm());
    govReceiveFileActionForm.setReceiveFileFileNo((new StringBuilder(String.valueOf(po.getReceiveFileFileNo()))).toString());
    httpServletRequest.setAttribute("receiveFileFileNo", po.getReceiveFileFileNo());
    govReceiveFileActionForm.setReceiveFileFileNumber(po.getReceiveFileFileNumber());
    httpServletRequest.setAttribute("receiveFileFileNumber", po.getReceiveFileFileNumber());
    govReceiveFileActionForm.setReceiveFileLeaderComment(po.getReceiveFileLeaderComment());
    httpServletRequest.setAttribute("receiveFileLeaderComment", po.getReceiveFileLeaderComment());
    govReceiveFileActionForm.setReceiveFileLink(po.getReceiveFileLink());
    httpServletRequest.setAttribute("receiveFileLink", po.getReceiveFileLink());
    govReceiveFileActionForm.setReceiveFileMemo(po.getReceiveFileMemo());
    httpServletRequest.setAttribute("ReceiveFileMemo", po.getReceiveFileMemo());
    govReceiveFileActionForm.setReceiveFileQuantity((new StringBuilder(String.valueOf(po.getReceiveFileQuantity()))).toString());
    httpServletRequest.setAttribute("receiveFileQuantity", (new StringBuilder(String.valueOf(po.getReceiveFileQuantity()))).toString());
    govReceiveFileActionForm.setReceiveFileSafetyGrade(po.getReceiveFileSafetyGrade());
    httpServletRequest.setAttribute("receiveFileSafetyGrade", po.getReceiveFileSafetyGrade());
    govReceiveFileActionForm.setReceiveFileSendFileUnit(po.getReceiveFileSendFileUnit());
    httpServletRequest.setAttribute("receiveFileSendFileUnit", po.getReceiveFileSendFileUnit());
    govReceiveFileActionForm.setReceiveFileSendLeaderCheck(po.getReceiveFileSendLeaderCheck());
    httpServletRequest.setAttribute("receiveFileSendLeaderCheck", po.getReceiveFileSendLeaderCheck());
    govReceiveFileActionForm.setReceiveFileSendLeaderCheckNm(po.getReceiveFileSendLeaderCheckNm());
    httpServletRequest.setAttribute("receiveFileSendLeaderCheckNm", po.getReceiveFileSendLeaderCheckNm());
    govReceiveFileActionForm.setReceiveFileSendLeaderRead(po.getReceiveFileSendLeaderRead());
    httpServletRequest.setAttribute("receiveFileSendLeaderRead", po.getReceiveFileSendLeaderRead());
    govReceiveFileActionForm.setReceiveFileSendLeaderReaderNm(po.getReceiveFileSendLeaderReaderNm());
    httpServletRequest.setAttribute("receiveFileSendLeaderReaderNm", po.getReceiveFileSendLeaderReaderNm());
    govReceiveFileActionForm.setReceiveFileSettleComment(po.getReceiveFileSettleComment());
    httpServletRequest.setAttribute("receiveFileSettleComment", po.getReceiveFileSettleComment());
    govReceiveFileActionForm.setReceiveFileTitle(po.getReceiveFileTitle());
    httpServletRequest.setAttribute("receiveFileTitle", po.getReceiveFileTitle());
    govReceiveFileActionForm.setReceiveFileTogetherDoDepart(po.getReceiveFileTogetherDoDepart());
    httpServletRequest.setAttribute("receiveFileTogetherDoDepart", po.getReceiveFileTogetherDoDepart());
    govReceiveFileActionForm.setReceiveFileTogetherDoDepartNm(po.getReceiveFileTogetherDoDepartNm());
    httpServletRequest.setAttribute("receiveFileTogetherDoDepartNm", po.getReceiveFileTogetherDoDepartNm());
    govReceiveFileActionForm.setReceiveFileTransPerson(po.getReceiveFileTransPerson());
    httpServletRequest.setAttribute("receiveFileTransPerson", po.getReceiveFileTransPerson());
    govReceiveFileActionForm.setReceiveFileTransPersonNm(po.getReceiveFileTransPersonNm());
    httpServletRequest.setAttribute("receiveFileTransPersonNm", po.getReceiveFileTransPersonNm());
    govReceiveFileActionForm.setReceiveFileIsEnd((new StringBuilder(String.valueOf(po.getReceiveFileIsEnd()))).toString());
    httpServletRequest.setAttribute("receiveFileIsEnd", po.getReceiveFileIsEnd());
    govReceiveFileActionForm.setReceiveFileSettleLeaderComment(po.getReceiveFileSettleLeaderComment());
    httpServletRequest.setAttribute("receiveFileSettleLeaderComment", po.getReceiveFileSettleLeaderComment());
    govReceiveFileActionForm.setReceiveFileType(po.getReceiveFileType());
    httpServletRequest.setAttribute("receiveFileType", po.getReceiveFileType());
    govReceiveFileActionForm.setReceiveFileTransAuditComment(po.getReceiveFileTransAuditComment());
    httpServletRequest.setAttribute("receiveFileTransAuditComment", po.getReceiveFileTransAuditComment());
    govReceiveFileActionForm.setReceiveFileFileNoCount(po.getReceiveFileFileNoCount());
    httpServletRequest.setAttribute("receiveFileFileNoCount", po.getReceiveFileFileNoCount());
    govReceiveFileActionForm.setField1(po.getField1());
    httpServletRequest.setAttribute("field1", po.getField1());
    govReceiveFileActionForm.setField2(po.getField2());
    httpServletRequest.setAttribute("field2", po.getField2());
    govReceiveFileActionForm.setField3(po.getField3());
    httpServletRequest.setAttribute("field3", po.getField3());
    govReceiveFileActionForm.setField4(po.getField4());
    httpServletRequest.setAttribute("field4", po.getField4());
    govReceiveFileActionForm.setField5(po.getField5());
    httpServletRequest.setAttribute("field5", po.getField5());
    govReceiveFileActionForm.setField6(po.getField6());
    httpServletRequest.setAttribute("field6", po.getField6());
    govReceiveFileActionForm.setField7(po.getField7());
    httpServletRequest.setAttribute("field7", po.getField7());
    govReceiveFileActionForm.setField8(po.getField8());
    httpServletRequest.setAttribute("field8", po.getField8());
    govReceiveFileActionForm.setField9(po.getField9());
    httpServletRequest.setAttribute("field9", po.getField9());
    govReceiveFileActionForm.setField10(po.getField10());
    httpServletRequest.setAttribute("field10", po.getField10());
    govReceiveFileActionForm.setZjkySeq(po.getZjkySeq());
    httpServletRequest.setAttribute("zjkyseq", po.getZjkySeq());
    govReceiveFileActionForm.setZjkyType(po.getZjkyType());
    httpServletRequest.setAttribute("zjkyType", po.getZjkyType());
    govReceiveFileActionForm.setZjkykeepTerm(po.getZjkykeepTerm());
    httpServletRequest.setAttribute("zjkykeepTerm", po.getZjkykeepTerm());
    govReceiveFileActionForm.setReceiveFieldSelectMoreEmp(po.getReceiveFieldSelectMoreEmp());
    httpServletRequest.setAttribute("receiveFieldSelectMoreEmp", po.getReceiveFieldSelectMoreEmp());
    govReceiveFileActionForm.setField16(po.getField16());
    httpServletRequest.setAttribute("field16", po.getField16());
    govReceiveFileActionForm.setField17(po.getField17());
    httpServletRequest.setAttribute("field17", po.getField17());
    govReceiveFileActionForm.setField18(po.getField8());
    httpServletRequest.setAttribute("field18", po.getField18());
    govReceiveFileActionForm.setField19(po.getField19());
    httpServletRequest.setAttribute("field19", po.getField19());
    govReceiveFileActionForm.setField20(po.getField20());
    httpServletRequest.setAttribute("field20", po.getField20());
    govReceiveFileActionForm.setField21(po.getField21());
    httpServletRequest.setAttribute("field21", po.getField21());
    govReceiveFileActionForm.setField22(po.getField22());
    httpServletRequest.setAttribute("field22", po.getField22());
    httpServletRequest.setAttribute("myform", govReceiveFileActionForm);
    httpServletRequest.setAttribute("accessory1", po.getAccessoryName());
    httpServletRequest.setAttribute("accessorySave1", po.getAccessorySaveName());
    httpServletRequest.setAttribute("accessory2", po.getAccessoryNameFile());
    httpServletRequest.setAttribute("accessorySave2", po.getAccessorySaveNameFile());
    httpServletRequest.setAttribute("receiveDate", po.getReceiveFileReceiveDate());
    httpServletRequest.setAttribute("receiveFileReceiveDate", po.getReceiveFileReceiveDate());
    httpServletRequest.setAttribute("receiveStatus", po.getReceiveFileStatus());
    httpServletRequest.setAttribute("receiveTextField1", po.getReceiveTextField1());
    httpServletRequest.setAttribute("receiveTextField2", po.getReceiveTextField2());
    httpServletRequest.setAttribute("receiveDropDownSelect1", po.getReceiveDropDownSelect1());
    httpServletRequest.setAttribute("receiveDropDownSelect2", po.getReceiveDropDownSelect2());
    httpServletRequest.setAttribute("receiveMutliTextField1", po.getReceiveMutliTextField1());
    govReceiveFileActionForm.setReceiveAuthorName(po.getReceiveAuthorName());
    httpServletRequest.setAttribute("receiveAuthorName", po.getReceiveAuthorName());
    if (po.getSeqId() != null && !po.getSeqId().toString().equals("") && !po.getSeqId().toString().equals("null")) {
      httpServletRequest.setAttribute("seqId", po.getSeqId());
    } else {
      httpServletRequest.setAttribute("seqId", "");
    } 
    Calendar endDate = Calendar.getInstance();
    if (po.getReceiveFileEndDate() != null) {
      endDate.setTime(po.getReceiveFileEndDate());
      httpServletRequest.setAttribute("LimitTimeYear", (new StringBuilder(String.valueOf(endDate.get(1)))).toString());
      httpServletRequest.setAttribute("LimitTimeMonth", (new StringBuilder(String.valueOf(endDate.get(2) + 1))).toString());
      httpServletRequest.setAttribute("LimitTimeDate", (new StringBuilder(String.valueOf(endDate.get(5)))).toString());
    } else {
      httpServletRequest.setAttribute("LimitTimeYear", (new StringBuilder(String.valueOf(endDate.get(1)))).toString());
      httpServletRequest.setAttribute("LimitTimeMonth", "");
      httpServletRequest.setAttribute("LimitTimeDate", "");
    } 
    httpServletRequest.setAttribute("createdDate", po.getCreatedTime());
    if (po.getFileSendCheckId() != null) {
      httpServletRequest.setAttribute("fromFileSendCheckId", po.getFileSendCheckId());
      httpServletRequest.setAttribute("fromFileSendCheckLink", po.getFileSendCheckLink());
    } 
    setMapInfo(httpServletRequest);
  }
  
  private Iterator getYearItr() {
    List<Integer> itr = new ArrayList();
    Calendar now = new GregorianCalendar();
    int year = now.get(1);
    for (int i = -10; i <= 10; i++)
      itr.add(new Integer(year + i)); 
    return itr.iterator();
  }
  
  private void getReceiveFileType(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String scopeWhere = (new ManagerService()).getScopeFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        "po.userId", "po.orgId", "po.groupId");
    GovReceiveFileTypeBD govReceiveFileTypeBD = new GovReceiveFileTypeBD();
    List grftList = govReceiveFileTypeBD.govReceiveFileTypeList(scopeWhere);
    httpServletRequest.setAttribute("grftList", grftList);
  }
  
  private void getComeFileUnit(HttpServletRequest httpServletRequest) {
    GovComeFileUnitBD gcfbd = new GovComeFileUnitBD();
    String wherePara = "1=1";
    httpServletRequest.setAttribute("unitlist", gcfbd.getComeFileUnit(wherePara));
  }
  
  public void setMapInfo(HttpServletRequest request) {
    ReceivedocumentBD rbd = new ReceivedocumentBD();
    Object[] rObj = rbd.getReceivedocumentBaseInfo();
    Map<Object, Object> mapinfo = new HashMap<Object, Object>();
    if (rObj != null) {
      if (rObj[1] != null && !rObj[1].toString().equals("")) {
        String[] arrayOfString = rObj[1].toString().split(";");
        mapinfo.put("receiveFileType", arrayOfString);
      } else {
        mapinfo.put("receiveFileType", new Object[0]);
      } 
      if (rObj[2] != null && !rObj[2].toString().equals("")) {
        String[] arrayOfString = rObj[2].toString().split(";");
        mapinfo.put("pigeonholeType", arrayOfString);
      } else {
        mapinfo.put("pigeonholeType", new Object[0]);
      } 
      if (rObj[3] != null && !rObj[3].toString().equals("")) {
        String[] arrayOfString = rObj[3].toString().split(";");
        mapinfo.put("decumentKind", arrayOfString);
      } else {
        mapinfo.put("decumentKind", new Object[0]);
      } 
      if (rObj[4] != null && !rObj[4].toString().equals("")) {
        String[] arrayOfString = rObj[4].toString().split(";");
        mapinfo.put("receiveSecretLevel", arrayOfString);
      } else {
        mapinfo.put("receiveSecretLevel", new Object[0]);
      } 
      if (rObj[5] != null && !rObj[5].toString().equals("")) {
        String[] arrayOfString = rObj[5].toString().split(";");
        mapinfo.put("urgencyLevel", arrayOfString);
      } else {
        mapinfo.put("urgencyLevel", new Object[0]);
      } 
      if (rObj[6] != null && !rObj[6].toString().equals("")) {
        String[] arrayOfString = rObj[6].toString().split(";");
        mapinfo.put("keepTerm", arrayOfString);
      } else {
        mapinfo.put("keepTerm", new Object[0]);
      } 
      if (rObj[8] != null) {
        String[] arrayOfString = rObj[8].toString().split(";");
        mapinfo.put("receiveDropDownSelect1", arrayOfString);
      } else {
        mapinfo.put("receiveDropDownSelect1", new Object[0]);
      } 
      if (rObj[9] != null) {
        String[] arrayOfString = rObj[9].toString().split(";");
        mapinfo.put("receiveDropDownSelect2", arrayOfString);
      } else {
        mapinfo.put("receiveDropDownSelect2", new Object[0]);
      } 
    } 
    request.setAttribute("mapinfo", mapinfo);
  }
}
