package com.js.oa.form;

import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import com.js.doc.doc.po.SendAssociatePO;
import com.js.doc.doc.po.SendFlowResavePO;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.ReceivedocumentBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.oa.info.util.InfoArchives;
import com.js.oa.jsflow.service.WorkFlowCommonBD;
import com.js.oa.rws.service.TongBuService;
import com.js.oa.search.client.SearchService;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ReceiveFile {
  public Long save(HttpServletRequest httpServletRequest) {
    String fromSendFile = (httpServletRequest.getParameter("fromSendFile") != null) ? httpServletRequest.getParameter("fromSendFile").toString() : "";
    if (httpServletRequest.getParameter("resubmitWorkId") != null && 
      !httpServletRequest.getParameter("resubmitWorkId").toString().equals("") && !"1".equals(fromSendFile))
      delete(httpServletRequest); 
    String editId = httpServletRequest.getParameter("editId").toString();
    boolean flag = isDraft(editId);
    GovReceiveFilePO po = setPO(httpServletRequest);
    String isDraf = (httpServletRequest.getParameter("isDraf") == null) ? "" : httpServletRequest.getParameter("isDraf").toString();
    if (httpServletRequest.getParameter("resubmitWorkId") != null && 
      !httpServletRequest.getParameter("resubmitWorkId").toString().equals("") && 
      !"1".equals(fromSendFile) && po.getId() > 0L && 
      !"1".equals(isDraf) && !flag)
      po.setId(0L); 
    if (httpServletRequest.getParameter("pareRecordId") != null && !"0".equals(Boolean.valueOf((httpServletRequest.getParameter("pareRecordId") == null)))) {
      po.setIsSubFlow(Integer.valueOf(1));
    } else {
      po.setIsSubFlow(Integer.valueOf(0));
    } 
    Long result = (new ReceiveFileBD()).save(po);
    if (result != null && !result.toString().equals("") && Integer.parseInt((String)result) > 0) {
      changeSeqfig(httpServletRequest);
      String sendFileId = (httpServletRequest.getParameter("sendFileId") == null) ? "" : httpServletRequest.getParameter("sendFileId").toString();
      if (sendFileId != null && !sendFileId.equals(""))
        saveAssociate(result, httpServletRequest); 
    } 
    return result;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    GovReceiveFilePO po = setPO(httpServletRequest);
    String editId = httpServletRequest.getParameter("editId");
    Map<String, String> map = getCreateEmpIdOrCreateOrg(editId);
    po.setCreatedEmp(Long.parseLong(map.get("createdemp")));
    po.setCreatedOrg(Long.parseLong(map.get("createdorg")));
    if (httpServletRequest.getParameter("LimitTimeYear") != null && 
      !"".equals(httpServletRequest.getParameter("LimitTimeYear")) && 
      httpServletRequest.getParameter("LimitTimeMonth") != null && 
      !"".equals(httpServletRequest.getParameter("LimitTimeMonth")) && 
      httpServletRequest.getParameter("LimitTimeDate") != null && 
      !"".equals(httpServletRequest.getParameter("LimitTimeDate")))
      try {
        po.setReceiveFileEndDate(new Date(String.valueOf(httpServletRequest.getParameter("LimitTimeYear")) + "/" + httpServletRequest.getParameter("LimitTimeMonth") + "/" + httpServletRequest.getParameter("LimitTimeDate")));
      } catch (Exception exception) {} 
    po.setReceiveFileEndDate(new Date());
    String[] wf_dealwithcomment_id = httpServletRequest.getParameterValues("wf_dealwithcomment_id");
    String[] rangeName = httpServletRequest.getParameterValues("rangeName");
    String[] rangeId = httpServletRequest.getParameterValues("rangeId");
    if (wf_dealwithcomment_id != null && wf_dealwithcomment_id.length > 0 && rangeName != null && rangeName.length > 0 && rangeId != null && rangeId.length > 0)
      (new WorkFlowCommonBD()).updateCommentRange(wf_dealwithcomment_id, rangeName, rangeId); 
    GovReceiveFilePO oldPo = (new ReceiveFileBD()).load(httpServletRequest.getParameter("editId"));
    po.setIsSubFlow(oldPo.getIsSubFlow());
    Long result = (new ReceiveFileBD()).update(po, httpServletRequest.getParameter("editId"));
    if (result != null && !result.toString().equals("") && Integer.parseInt((String)result) > 0) {
      updateWorkTitle(httpServletRequest);
      if (httpServletRequest.getParameter("isChangeSeq") != null && httpServletRequest.getParameter("isChangeSeq").toString().equals("1"))
        changeSeqfig(httpServletRequest); 
    } 
    return result;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    String receiveFileId = httpServletRequest.getParameter("editId");
    if (SystemCommon.getArchiveToInfo() == 1) {
      String processId = httpServletRequest.getParameter("processId");
      infoArchives(processId, receiveFileId);
    } 
    System.out.println("ReceiveFile------------我要调用检索方法了！---------------------");
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && receiveFileId != null && ifActiveUpdateDelete != null && !"".equals(receiveFileId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.addIndex(receiveFileId.toString(), "doc_receivefile");
    } 
    if ("rws".equalsIgnoreCase(SystemCommon.getCustomerName())) {
      String recordId = receiveFileId;
      String tableId = "DOC_receivefile";
      TongBuService.yuguidang(tableId, recordId, httpServletRequest.getSession(true).getAttribute("userId").toString());
    } 
    return (new ReceiveFileBD()).completeReceiveFile(receiveFileId);
  }
  
  private GovReceiveFilePO setPO(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    GovReceiveFilePO po = new GovReceiveFilePO();
    if (httpServletRequest.getParameter("sendFileId") != null && !httpServletRequest.getParameter("sendFileId").toString().equals("")) {
      po.setSendFileId(Long.valueOf(httpServletRequest.getParameter("sendFileId")));
      po.setSendFileLink(httpServletRequest.getParameter("sendFileLink"));
      po.setSendFileTitle(httpServletRequest.getParameter("sendFileTitle"));
    } 
    if (httpServletRequest.getParameter("exchangeFileLink") != null) {
      po.setExchangeFileId(httpServletRequest.getParameter("exchangeFileId"));
      po.setExchangeFileLink(httpServletRequest.getParameter("exchangeFileLink"));
      po.setExchangeFileTitle(httpServletRequest.getParameter("exchangeFileTitle"));
    } 
    StringBuffer receiveFileFileNo = new StringBuffer();
    receiveFileFileNo.append(httpServletRequest.getParameter("field1"));
    receiveFileFileNo.append("[" + httpServletRequest.getParameter("field2") + "]");
    receiveFileFileNo.append(httpServletRequest.getParameter("field3"));
    po.setReceiveFileFileNo(receiveFileFileNo.toString());
    if (httpServletRequest.getParameter("field3") != null && !"".equals(httpServletRequest.getParameter("field3"))) {
      po.setReceiveFileFileNoCount(Long.parseLong(httpServletRequest.getParameter("field3")));
    } else {
      po.setReceiveFileFileNoCount(0L);
    } 
    if (httpServletRequest.getParameter("field2") != null && !"".equals(httpServletRequest.getParameter("field2"))) {
      po.setCreatedTimeYear(Long.parseLong(httpServletRequest.getParameter("field2")));
    } else {
      po.setCreatedTimeYear(0L);
    } 
    po.setReceiveFileType(httpServletRequest.getParameter("receiveFileType"));
    po.setCreatedEmp(Long.parseLong(httpSession.getAttribute("userId").toString()));
    po.setCreatedOrg(Long.parseLong(httpSession.getAttribute("orgId").toString()));
    po.setDomainId(httpSession.getAttribute("domainId").toString());
    String attachName1 = "";
    String[] attachNameArr1 = httpServletRequest.getParameterValues("accessoryName1");
    String attachSaveName1 = "";
    String[] attachSaveNameArr1 = httpServletRequest.getParameterValues("accessorySaveName1");
    String attachName2 = "";
    String[] attachNameArr2 = httpServletRequest.getParameterValues("accessoryName2");
    String attachSaveName2 = "";
    String[] attachSaveNameArr2 = httpServletRequest.getParameterValues("accessorySaveName2");
    int k = 0;
    int i;
    for (i = 0; attachSaveNameArr1 != null && i < attachSaveNameArr1.length; i++) {
      if (attachSaveNameArr1[i] != null && !"".equals(attachSaveNameArr1[i])) {
        k++;
        if (k != 1) {
          attachName1 = String.valueOf(attachName1) + "|";
          attachSaveName1 = String.valueOf(attachSaveName1) + "|";
        } 
        attachName1 = String.valueOf(attachName1) + attachNameArr1[i];
        attachSaveName1 = String.valueOf(attachSaveName1) + attachSaveNameArr1[i];
      } 
    } 
    po.setAccessoryName(attachName1);
    po.setAccessorySaveName(attachSaveName1);
    k = 0;
    for (i = 0; attachSaveNameArr2 != null && i < attachSaveNameArr2.length; i++) {
      if (attachSaveNameArr2[i] != null || !"".equals(attachSaveNameArr2[i])) {
        k++;
        if (k != 1) {
          attachName2 = String.valueOf(attachName2) + "|";
          attachSaveName2 = String.valueOf(attachSaveName2) + "|";
        } 
        attachName2 = String.valueOf(attachName2) + attachNameArr2[i];
        attachSaveName2 = String.valueOf(attachSaveName2) + attachSaveNameArr2[i];
      } 
    } 
    po.setAccessoryNameFile(attachName2);
    po.setAccessorySaveNameFile(attachSaveName2);
    if (httpServletRequest.getParameter("receiveFileReceiveDate") != null && !"2008/12/12".equals((new StringBuilder(String.valueOf(httpServletRequest.getParameter("receiveFileReceiveDate")))).toString())) {
      po.setReceiveFileReceiveDate(new Date(httpServletRequest.getParameter("receiveFileReceiveDate")));
    } else {
      po.setReceiveFileReceiveDate(new Date());
    } 
    if (httpServletRequest.getParameter("createdDate") != null && !"2008/12/12".equals((new StringBuilder(String.valueOf(httpServletRequest.getParameter("receiveFileReceiveDate")))).toString())) {
      po.setCreatedTime(new Date(httpServletRequest.getParameter("createdDate")));
    } else {
      po.setCreatedTime(new Date());
    } 
    po.setReceiveFieldSelectMoreEmp((httpServletRequest.getParameter("receiveFieldSelectMoreEmp") == null) ? 
        "" : httpServletRequest.getParameter("receiveFieldSelectMoreEmp"));
    po.setField1(httpServletRequest.getParameter("field1"));
    po.setField2(httpServletRequest.getParameter("field2"));
    po.setField3(httpServletRequest.getParameter("field3"));
    po.setField5(httpServletRequest.getParameter("field5"));
    po.setField6(httpServletRequest.getParameter("field6"));
    po.setField7(httpServletRequest.getParameter("field7"));
    po.setField8(httpServletRequest.getParameter("field8"));
    po.setField16(httpServletRequest.getParameter("field16"));
    po.setField17(httpServletRequest.getParameter("field17"));
    po.setField18(httpServletRequest.getParameter("field18"));
    po.setField19(httpServletRequest.getParameter("field19"));
    po.setField20(httpServletRequest.getParameter("field20"));
    po.setField21(httpServletRequest.getParameter("field21"));
    po.setField22(httpServletRequest.getParameter("field22"));
    po.setField23(httpServletRequest.getParameter("field23"));
    po.setField24(httpServletRequest.getParameter("field24"));
    po.setField25(httpServletRequest.getParameter("field25"));
    po.setField26(httpServletRequest.getParameter("field26"));
    po.setReceiveAuthorName(httpServletRequest.getParameter("receiveAuthorName"));
    po.setReceiveFileSendFileUnit(httpServletRequest.getParameter("receiveFileSendFileUnit"));
    po.setReceiveFileFileNumber(httpServletRequest.getParameter("receiveFileFileNumber"));
    po.setReceiveFileTitle(httpServletRequest.getParameter("receiveFileTitle"));
    po.setReceiveFileSafetyGrade(httpServletRequest.getParameter("receiveFileSafetyGrade"));
    if (httpServletRequest.getParameter("receiveFileQuantity") != null && !"".equals(httpServletRequest.getParameter("receiveFileQuantity")))
      po.setReceiveFileQuantity(Short.parseShort(httpServletRequest.getParameter("receiveFileQuantity"))); 
    po.setField4(httpServletRequest.getParameter("field4"));
    po.setReceiveFileDoComment(httpServletRequest.getParameter("receiveFileDoComment"));
    po.setField10(httpServletRequest.getParameter("field10"));
    if (httpServletRequest.getParameter("fromFileSendCheckId") != null) {
      po.setFileSendCheckId(httpServletRequest.getParameter("fromFileSendCheckId"));
      po.setFileSendCheckLink(httpServletRequest.getParameter("fromFileSendCheckLink"));
    } 
    if (httpServletRequest.getParameter("zjkySeq") != null)
      po.setZjkySeq(httpServletRequest.getParameter("zjkySeq")); 
    if (httpServletRequest.getParameter("zjkyType") != null)
      po.setZjkyType(httpServletRequest.getParameter("zjkyType")); 
    if (httpServletRequest.getParameter("zjkykeepTerm") != null)
      po.setZjkykeepTerm(httpServletRequest.getParameter("zjkykeepTerm")); 
    if (httpServletRequest.getParameter("seqId") != null && !httpServletRequest.getParameter("seqId").toString().equals(""))
      po.setSeqId(new Long(httpServletRequest.getParameter("seqId"))); 
    if (httpServletRequest.getParameter("editId") != null && 
      !httpServletRequest.getParameter("editId").toString().equals("")) {
      SendFlowResavePO savePo = new SendFlowResavePO();
      savePo.setFlowEmpId(new Long(httpSession.getAttribute("userId")
            .toString()));
      savePo.setSendId(new Long(httpServletRequest.getParameter("editId")));
      String deleteType = "0";
      String whichBatch = httpServletRequest.getParameter("whichBatch");
      if (httpServletRequest.getParameter("isInModify") != null && 
        httpServletRequest.getParameter("isInModify").toString().equals(
          "isInModify")) {
        if (whichBatch.equals("receiveFileLeaderComment")) {
          deleteType = "1";
          setResavePO(httpServletRequest, savePo, 
              "receiveFileLeaderComment");
        } 
        if (whichBatch.equals("receiveFileSettleLeaderComment")) {
          deleteType = "2";
          setResavePO(httpServletRequest, savePo, 
              "receiveFileSettleLeaderComment");
        } 
        if (whichBatch.equals("field9")) {
          deleteType = "3";
          setResavePO(httpServletRequest, savePo, "field9");
        } 
        if (whichBatch.equals("receiveFileSettleComment")) {
          deleteType = "5";
          setResavePO(httpServletRequest, savePo, "receiveFileSettleComment");
        } 
        if (whichBatch.equals("receiveFileTransAuditComment")) {
          deleteType = "6";
          setResavePO(httpServletRequest, savePo, "receiveFileTransAuditComment");
        } 
        if (whichBatch.equals("receiveFileMemo")) {
          deleteType = "7";
          setResavePO(httpServletRequest, savePo, "receiveFileMemo");
        } 
      } else if (httpServletRequest.getParameter("isInModify") == null || 
        
        !httpServletRequest.getParameter("isInModify").toString().equals("isEditModify")) {
        if (httpServletRequest.getParameter("resaveId") != null && 
          
          !httpServletRequest.getParameter("resaveId").toString().equals("")) {
          SendFileBD sendFileBD = new SendFileBD();
          sendFileBD.deleterResave(httpServletRequest
              .getParameter("resaveId"));
        } 
      } 
    } 
    if (httpServletRequest.getParameter("receiveStatus") != null && !httpServletRequest.getParameter("receiveStatus").toString().equals(""))
      po.setReceiveFileStatus(Byte.parseByte(httpServletRequest.getParameter("receiveStatus"))); 
    if (httpServletRequest.getParameter("tableId") != null && !httpServletRequest.getParameter("tableId").toString().equals(""))
      po.setTableId(new Long(httpServletRequest.getParameter("tableId"))); 
    if (httpServletRequest.getParameter("receiveTextField1") != null)
      po.setReceiveTextField1(httpServletRequest.getParameter("receiveTextField1")); 
    if (httpServletRequest.getParameter("receiveTextField2") != null)
      po.setReceiveTextField2(httpServletRequest.getParameter("receiveTextField2")); 
    if (httpServletRequest.getParameter("receiveDropDownSelect1") != null)
      po.setReceiveDropDownSelect1(httpServletRequest.getParameter("receiveDropDownSelect1")); 
    if (httpServletRequest.getParameter("receiveDropDownSelect2") != null)
      po.setReceiveDropDownSelect2(httpServletRequest.getParameter("receiveDropDownSelect2")); 
    if (httpServletRequest.getParameter("receiveMutliTextField1") != null)
      po.setReceiveMutliTextField1(httpServletRequest.getParameter("receiveMutliTextField1")); 
    if (httpServletRequest.getParameter("processId") != null)
      po.setProcessId(new Long(httpServletRequest.getParameter("processId"))); 
    String fromSendFile = (httpServletRequest.getParameter("fromSendFile") != null) ? httpServletRequest.getParameter("fromSendFile").toString() : "";
    if (httpServletRequest.getParameter("editId") != null && !"".equals(httpServletRequest.getParameter("editId"))) {
      long id = 0L;
      if (!"1".equals(fromSendFile)) {
        id = Long.parseLong(httpServletRequest.getParameter("editId"));
      } else {
        id = Long.parseLong("0");
      } 
      po.setId(id);
    } 
    String t = (new StringBuilder(String.valueOf((new Date()).getTime()))).toString();
    po.setUpdateTimeStr(t);
    return po;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    GovReceiveFilePO po = new GovReceiveFilePO();
    String editId = httpServletRequest.getParameter("editId");
    ReceiveFileBD receiveFileBD = new ReceiveFileBD();
    po = setPO(httpServletRequest);
    Map<String, String> map = getCreateEmpIdOrCreateOrg(editId);
    po.setCreatedEmp(Long.parseLong(map.get("createdemp")));
    po.setCreatedOrg(Long.parseLong(map.get("createdorg")));
    String type = "1";
    if (httpServletRequest.getParameter("type") != null && 
      !httpServletRequest.getParameter("type").toString().equals(""))
      type = httpServletRequest.getParameter("type").toString(); 
    if (type.equals("0")) {
      po.setReceiveFileStatus(Byte.parseByte("2"));
    } else {
      po.setReceiveFileStatus(Byte.parseByte("0"));
    } 
    receiveFileBD.update(po, editId);
    return Long.valueOf(editId);
  }
  
  public void changeSeqfig(HttpServletRequest request) {
    ReceivedocumentBD bd = new ReceivedocumentBD();
    if (request.getParameter("zjkySeq") != null && !request.getParameter("zjkySeq").toString().equals("") && 
      request.getParameter("seqId") != null && !request.getParameter("seqId").toString().equals("")) {
      ReceiveFileSeqPO po = bd.loadRecSeqPO(request.getParameter("seqId"));
      if (po != null && po.getId() != null) {
        int seqfig = po.getSeqfigR().intValue();
        seqfig++;
        po.setSeqfigR(new Long(seqfig));
        bd.updateRecSeqPO(po);
      } 
    } 
  }
  
  private void setResavePO(HttpServletRequest httpServletRequest, SendFlowResavePO savePo, String Type) {
    SendFileBD sendFileBD = new SendFileBD();
    if (httpServletRequest.getParameter("resaveId") != null && 
      !httpServletRequest.getParameter("resaveId").toString().equals("")) {
      savePo.setId(new Long(httpServletRequest.getParameter("resaveId")));
      savePo.setFlowContent(httpServletRequest.getParameter(Type));
      savePo.setFlowType(Type);
      Long result = sendFileBD.updateResave(savePo);
    } else {
      savePo.setFlowContent(httpServletRequest.getParameter(Type));
      savePo.setFlowType(Type);
      Long long_ = sendFileBD.saveResave(savePo);
    } 
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String editId = httpServletRequest.getParameter("editId");
    ReceiveFileBD receiveFileBD = new ReceiveFileBD();
    receiveFileBD.delete2(editId);
  }
  
  private void saveAssociate(Long result, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    SendFileBD sendFileBD = new SendFileBD();
    SendAssociatePO po = new SendAssociatePO();
    po.setDomainId(new Long((String)session.getAttribute("domainId")));
    po.setReceiveFileId(result);
    po.setSendFileId(new Long(httpServletRequest.getParameter("sendFileId")));
    po.setTransOrgId(new Long((String)session.getAttribute("orgId")));
    po.setTransUserId(new Long((String)session.getAttribute("userId")));
    sendFileBD.saveSendAssociate(po);
  }
  
  private void updateWorkTitle(HttpServletRequest request) {
    String isInModify = (request.getParameter("isInModify") == null) ? "" : request.getParameter("isInModify").toString();
    String processId = (request.getParameter("processId") == null) ? "null" : request.getParameter("processId");
    String table = (request.getParameter("table") == null) ? "null" : request.getParameter("table").toString();
    String editId = (request.getParameter("editId") == null) ? "null" : request.getParameter("editId").toString();
    String isTitleChange = (request.getParameter("isTitleChange") == null) ? "0" : request.getParameter("isTitleChange").toString();
    String workTitle = (request.getParameter("receiveFileTitle") == null) ? "" : request.getParameter("receiveFileTitle").toString();
    if (isTitleChange.equals("1") && !processId.equals("null") && !table.equals("null") && !editId.equals("null") && isInModify.equals("isInModify")) {
      SendFileBD bd = new SendFileBD();
      bd.updateWorkTitle(processId, editId, table, workTitle);
    } 
  }
  
  private void infoArchives(String processeId, String recordId) {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    List<String> list = new ArrayList<String>();
    boolean flag = false;
    try {
      base.begin();
      String sql = "SELECT infoChannelId,WORKFLOWPROCESSNAME FROM jsf_workflowprocess WHERE WF_WORKFLOWPROCESS_ID=" + processeId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        list.add((new StringBuilder(String.valueOf(rs.getString(2)))).toString());
        list.add(rs.getString(1));
        if (rs.getString(1) != null && !"".equals(rs.getString(1)) && !"null".equalsIgnoreCase(rs.getString(1)))
          flag = true; 
      } 
      rs.close();
      if (flag) {
        sql = "SELECT emp.empname,emp.emp_id,o.org_id,o.orgName,o.orgIdString,j.receivefile_sendfileunit,receivefile_filenumber,receivefile_title  FROM jsf_work w JOIN doc_receivefile j ON w.WORKRECORD_ID=j.receivefile_id  JOIN org_employee emp ON w.WF_CUREMPLOYEE_ID=emp.EMP_ID  JOIN org_organization_user e ON emp.emp_id=e.EMP_ID  JOIN org_organization o ON e.ORG_ID=o.org_id  WHERE w.WORKPROCESS_ID=" + 



          
          processeId + " AND w.WORKRECORD_ID=" + recordId + 
          " ORDER BY w.WF_WORK_ID";
        rs = base.executeQuery(sql);
        if (rs.next()) {
          list.add(rs.getString(1));
          list.add(rs.getString(2));
          list.add(rs.getString(3));
          list.add(rs.getString(4));
          list.add(rs.getString(5));
          String wh = rs.getString(7);
          if (wh != null && !"".equals(wh)) {
            list.set(0, "【" + wh + "】" + rs.getString(8));
          } else {
            list.set(0, rs.getString(8));
          } 
        } 
        rs.close();
        list.add(recordId);
        (new InfoArchives()).saveInfo(list, ((String)list.get(3)).toString(), "GWGL-SWGL");
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  private static Map<String, String> getCreateEmpIdOrCreateOrg(String editId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String createdemp = "";
    String createdorg = "";
    String sql = "select createdemp,createdorg from doc_receivefile t where receivefile_id=" + editId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());
      while (rs.next()) {
        createdemp = rs.getString("createdemp");
        createdorg = rs.getString("createdorg");
        map.put("createdemp", createdemp);
        map.put("createdorg", createdorg);
      } 
      stmt.close();
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return (Map)map;
  }
  
  private boolean isDraft(String editId) {
    if (editId == null || "".equals(editId))
      return false; 
    boolean flag = false;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String isDraft = "";
    String sql = "select IS_DRAF from doc_receiveFile  where receivefile_id=" + editId;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());
      while (rs.next()) {
        isDraft = rs.getString(1);
        if ("1".equals(isDraft))
          flag = true; 
      } 
      stmt.close();
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return flag;
  }
}
