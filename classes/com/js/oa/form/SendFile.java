package com.js.oa.form;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.ReceiveAssociatePO;
import com.js.doc.doc.po.SendFlowResavePO;
import com.js.doc.doc.po.SenddocumentUpdate;
import com.js.doc.doc.service.ReceiveFileBD;
import com.js.doc.doc.service.SendFileBD;
import com.js.doc.doc.service.SenddocumentBD;
import com.js.message.RealTimeUtil;
import com.js.oa.info.util.InfoArchives;
import com.js.oa.message.action.ModelSendMsg;
import com.js.oa.rws.service.TongBuService;
import com.js.oa.search.client.SearchService;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import com.js.util.util.ConversionString;
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
import javax.sql.DataSource;

public class SendFile {
  public Long save(HttpServletRequest httpServletRequest) {
    SendFileBD sendFileBD = new SendFileBD();
    Long sendFileId = null;
    if (httpServletRequest.getParameter("resubmitWorkId") != null && 
      
      !httpServletRequest.getParameter("resubmitWorkId").toString().equals(""))
      deleteSenddocument2(httpServletRequest); 
    if (httpServletRequest.getParameter("sendFileId") != null && 
      
      !httpServletRequest.getParameter("sendFileId").toString().equals("")) {
      String editId = httpServletRequest.getParameter("sendFileId");
      ReceiveFileBD receiveFileBD = new ReceiveFileBD();
      receiveFileBD.delete(editId);
    } 
    if (httpServletRequest.getParameter("sendFileId") != null && 
      !httpServletRequest.getParameter("sendFileId").toString().equals("")) {
      sendFileId = Long.valueOf(httpServletRequest.getParameter("sendFileId"));
      GovDocumentSendFilePO po = setPO(httpServletRequest);
      if (isDraft((String)sendFileId))
        po.setId(sendFileId.longValue()); 
      if (po.getId() == 0L) {
        sendFileId = sendFileBD.save(po);
      } else {
        sendFileBD.update(httpServletRequest.getParameter("sendFileId"), po);
      } 
      saveUpdate(httpServletRequest, po, sendFileId);
    } else {
      GovDocumentSendFilePO po = setPO(httpServletRequest);
      sendFileId = sendFileBD.save(po);
      saveUpdate(httpServletRequest, po, sendFileId);
    } 
    if (sendFileId != null && sendFileId.longValue() == -1L)
      httpServletRequest.setAttribute("repeat", "1"); 
    if (sendFileId != null && !sendFileId.toString().equals("-1") && 
      !sendFileId.toString().equals("-2")) {
      changeNumfig(httpServletRequest);
      changeSeqfig(httpServletRequest);
      if (httpServletRequest.getParameter("fromReceiveFileId") != null && 
        
        !httpServletRequest.getParameter("fromReceiveFileId").toString().equals("") && 
        
        !httpServletRequest.getParameter("fromReceiveFileId").toString().equals("null"))
        saveAssociate(sendFileId, httpServletRequest); 
    } 
    return sendFileId;
  }
  
  public Long draft(HttpServletRequest httpServletRequest) {
    SendFileBD sendFileBD = new SendFileBD();
    GovDocumentSendFilePO po = setPO(httpServletRequest);
    po.setIsDraft(Integer.valueOf("1"));
    po.setTableId(Long.valueOf(httpServletRequest.getParameter("tableId")));
    po.setProcessId(Long.valueOf(httpServletRequest
          .getParameter("processId")));
    po.setProcessName(httpServletRequest.getParameter("processName"));
    po.setRemindField(httpServletRequest.getParameter("remindField"));
    po.setProcessType(Integer.valueOf(httpServletRequest
          .getParameter("processType")));
    boolean flag = false;
    if (httpServletRequest.getParameter("resubmitWorkId") != null && 
      
      !httpServletRequest.getParameter("resubmitWorkId").toString().equals(""))
      flag = true; 
    Long sendFileId = null;
    if (!flag && httpServletRequest.getParameter("sendFileId") != null && 
      
      !httpServletRequest.getParameter("sendFileId").toString().equals("")) {
      sendFileBD.draft(httpServletRequest.getParameter("sendFileId"), po);
      sendFileId = new Long(httpServletRequest.getParameter("sendFileId"));
      String editId = httpServletRequest.getParameter("sendFileId");
      ReceiveFileBD receiveFileBD = new ReceiveFileBD();
      receiveFileBD.delete2(editId);
    } else {
      sendFileId = sendFileBD.draft(null, po);
    } 
    if (sendFileId != null && !sendFileId.toString().equals(""))
      saveUpdate(httpServletRequest, po, sendFileId); 
    if (sendFileId != null && !sendFileId.toString().equals("-1") && 
      !sendFileId.toString().equals("-2")) {
      changeNumfig(httpServletRequest);
      changeSeqfig(httpServletRequest);
    } 
    httpServletRequest.setAttribute("sendFileId", sendFileId);
    return sendFileId;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    // Byte code:
    //   0: aload_1
    //   1: ldc 'editId'
    //   3: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   8: astore_2
    //   9: new com/js/doc/doc/service/SendFileBD
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: astore_3
    //   17: aload_0
    //   18: aload_1
    //   19: invokespecial setPO : (Ljavax/servlet/http/HttpServletRequest;)Lcom/js/doc/doc/po/GovDocumentSendFilePO;
    //   22: astore #4
    //   24: aload_2
    //   25: invokestatic getCreateEmpIdOrCreateOrg : (Ljava/lang/String;)Ljava/util/Map;
    //   28: astore #5
    //   30: aload #4
    //   32: new java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: aload #5
    //   41: ldc 'createdemp'
    //   43: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   48: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: invokevirtual toString : ()Ljava/lang/String;
    //   54: invokestatic parseLong : (Ljava/lang/String;)J
    //   57: invokevirtual setCreatedEmp : (J)V
    //   60: aload #4
    //   62: new java/lang/StringBuilder
    //   65: dup
    //   66: invokespecial <init> : ()V
    //   69: aload #5
    //   71: ldc 'createdorg'
    //   73: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   78: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   81: invokevirtual toString : ()Ljava/lang/String;
    //   84: invokestatic parseLong : (Ljava/lang/String;)J
    //   87: invokevirtual setCreatedOrg : (J)V
    //   90: aload_3
    //   91: aload_2
    //   92: aload #4
    //   94: invokevirtual update : (Ljava/lang/String;Lcom/js/doc/doc/po/GovDocumentSendFilePO;)Ljava/lang/Long;
    //   97: pop
    //   98: aload_1
    //   99: ldc 'wf_dealwithcomment_id'
    //   101: invokeinterface getParameterValues : (Ljava/lang/String;)[Ljava/lang/String;
    //   106: astore #6
    //   108: aload_1
    //   109: ldc 'rangeName'
    //   111: invokeinterface getParameterValues : (Ljava/lang/String;)[Ljava/lang/String;
    //   116: astore #7
    //   118: aload_1
    //   119: ldc 'rangeId'
    //   121: invokeinterface getParameterValues : (Ljava/lang/String;)[Ljava/lang/String;
    //   126: astore #8
    //   128: aload #6
    //   130: ifnull -> 178
    //   133: aload #6
    //   135: arraylength
    //   136: ifle -> 178
    //   139: aload #7
    //   141: ifnull -> 178
    //   144: aload #7
    //   146: arraylength
    //   147: ifle -> 178
    //   150: aload #8
    //   152: ifnull -> 178
    //   155: aload #8
    //   157: arraylength
    //   158: ifle -> 178
    //   161: new com/js/oa/jsflow/service/WorkFlowCommonBD
    //   164: dup
    //   165: invokespecial <init> : ()V
    //   168: aload #6
    //   170: aload #7
    //   172: aload #8
    //   174: invokevirtual updateCommentRange : ([Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Integer;
    //   177: pop
    //   178: aload_0
    //   179: aload_1
    //   180: aload #4
    //   182: aload_2
    //   183: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   186: invokevirtual saveUpdate : (Ljavax/servlet/http/HttpServletRequest;Lcom/js/doc/doc/po/GovDocumentSendFilePO;Ljava/lang/Long;)V
    //   189: aload_2
    //   190: ifnull -> 281
    //   193: aload_2
    //   194: invokevirtual toString : ()Ljava/lang/String;
    //   197: ldc '-1'
    //   199: invokevirtual equals : (Ljava/lang/Object;)Z
    //   202: ifne -> 281
    //   205: aload_2
    //   206: invokevirtual toString : ()Ljava/lang/String;
    //   209: ldc '-2'
    //   211: invokevirtual equals : (Ljava/lang/Object;)Z
    //   214: ifne -> 281
    //   217: aload_1
    //   218: ldc 'field3'
    //   220: invokeinterface getParameter : (Ljava/lang/String;)Ljava/lang/String;
    //   225: astore #9
    //   227: new java/util/Date
    //   230: dup
    //   231: invokespecial <init> : ()V
    //   234: invokevirtual getYear : ()I
    //   237: sipush #1900
    //   240: iadd
    //   241: istore #10
    //   243: new java/lang/StringBuilder
    //   246: dup
    //   247: invokespecial <init> : ()V
    //   250: iload #10
    //   252: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   255: invokevirtual toString : ()Ljava/lang/String;
    //   258: aload #9
    //   260: invokevirtual equals : (Ljava/lang/Object;)Z
    //   263: ifeq -> 276
    //   266: aload_0
    //   267: aload_1
    //   268: invokespecial changeNumfig : (Ljavax/servlet/http/HttpServletRequest;)V
    //   271: aload_0
    //   272: aload_1
    //   273: invokespecial changeSeqfig : (Ljavax/servlet/http/HttpServletRequest;)V
    //   276: aload_0
    //   277: aload_1
    //   278: invokespecial updateWorkTitle : (Ljavax/servlet/http/HttpServletRequest;)V
    //   281: aload_2
    //   282: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Long;
    //   285: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #165	-> 0
    //   #166	-> 9
    //   #167	-> 17
    //   #169	-> 24
    //   #170	-> 30
    //   #171	-> 60
    //   #174	-> 90
    //   #176	-> 98
    //   #177	-> 99
    //   #176	-> 106
    //   #178	-> 108
    //   #179	-> 118
    //   #180	-> 128
    //   #181	-> 139
    //   #182	-> 155
    //   #183	-> 161
    //   #184	-> 168
    //   #183	-> 174
    //   #186	-> 178
    //   #187	-> 189
    //   #188	-> 205
    //   #189	-> 217
    //   #190	-> 227
    //   #191	-> 243
    //   #192	-> 266
    //   #193	-> 271
    //   #195	-> 276
    //   #199	-> 281
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	286	0	this	Lcom/js/oa/form/SendFile;
    //   0	286	1	httpServletRequest	Ljavax/servlet/http/HttpServletRequest;
    //   9	277	2	editId	Ljava/lang/String;
    //   17	269	3	sendFileBD	Lcom/js/doc/doc/service/SendFileBD;
    //   24	262	4	po	Lcom/js/doc/doc/po/GovDocumentSendFilePO;
    //   30	256	5	map	Ljava/util/Map;
    //   108	178	6	wf_dealwithcomment_id	[Ljava/lang/String;
    //   118	168	7	rangeName	[Ljava/lang/String;
    //   128	158	8	rangeId	[Ljava/lang/String;
    //   227	54	9	field3	Ljava/lang/String;
    //   243	38	10	nowYear_int	I
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    GovDocumentSendFilePO po = new GovDocumentSendFilePO();
    String editId = httpServletRequest.getParameter("editId");
    SendFileBD sendFileBD = new SendFileBD();
    po = setPO(httpServletRequest);
    Map<String, String> map = getCreateEmpIdOrCreateOrg(editId);
    po.setCreatedEmp(Long.parseLong(map.get("createdemp")));
    po.setCreatedOrg(Long.parseLong(map.get("createdorg")));
    String type = "1";
    if (httpServletRequest.getParameter("type") != null && 
      !httpServletRequest.getParameter("type").toString().equals(
        ""))
      type = httpServletRequest.getParameter("type").toString(); 
    if (type.equals("0")) {
      po.setTransactStatus("2");
    } else {
      po.setTransactStatus("0");
    } 
    sendFileBD.update(editId, po);
    return Long.valueOf(editId);
  }
  
  public Integer complete(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : 
      httpSession.getAttribute("domainId").toString();
    String editId = httpServletRequest.getParameter("editId");
    SendFileBD sendFileBD = new SendFileBD();
    sendFileBD.completeSendFile(editId);
    String userIds = httpServletRequest.getParameter("sendToId");
    String docSendFileByteNum = (httpServletRequest
      .getParameter("documentSendFileByteNumber") == null) ? (
      String.valueOf(httpServletRequest.getParameter("field1")) + 
      httpServletRequest.getParameter("field2") + 
      httpServletRequest.getParameter("field3")) : 
      httpServletRequest.getParameter("documentSendFileByteNumber");
    String docTile = httpServletRequest
      .getParameter("documentSendFileTitle");
    String sendTime = httpServletRequest
      .getParameter("documentSendFileSendTime");
    if (SystemCommon.getArchiveToInfo() == 1) {
      String processId = httpServletRequest.getParameter("processId");
      infoArchives(processId, editId);
    } 
    SearchService.getInstance();
    String ifActiveUpdateDelete = SearchService.getIfActiveUpdateDelete();
    SearchService.getInstance();
    String isearchSwitch = SearchService.getiSearchSwitch();
    if ("1".equals(isearchSwitch) && editId != null && ifActiveUpdateDelete != null && !"".equals(editId) && !"".equals(ifActiveUpdateDelete) && !"no".equals(ifActiveUpdateDelete)) {
      SearchService.getInstance();
      SearchService.addIndex(editId.toString(), "doc_documentsendfile");
    } 
    if ("rws".equalsIgnoreCase(SystemCommon.getCustomerName())) {
      String recordId = editId;
      String tableId = "DOC_DOCUMENTSENDFILE";
      TongBuService.yuguidang(tableId, recordId, httpServletRequest.getSession(true).getAttribute("userId").toString());
    } 
    return new Integer(1);
  }
  
  private static Map<String, String> getCreateEmpIdOrCreateOrg(String editId) {
    Map<Object, Object> map = new HashMap<Object, Object>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String createdemp = "";
    String createdorg = "";
    String sql = "select createdemp,createdorg from  doc_documentsendfile where documentsendfile_id=" + editId;
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
      rs.close();
      stmt.close();
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
  
  private GovDocumentSendFilePO setPO(HttpServletRequest httpServletRequest) {
    SendFileBD sendFileBD = new SendFileBD();
    GovDocumentSendFilePO po = new GovDocumentSendFilePO();
    if (httpServletRequest.getParameter("content") != null && 
      !httpServletRequest.getParameter("content").equals("")) {
      po.setSendFileText(httpServletRequest.getParameter("content"));
      po.setGoldGridId(httpServletRequest.getParameter("content"));
    } 
    StringBuffer attachName = new StringBuffer();
    String[] attachNameArr = httpServletRequest.getParameterValues("accessoryName");
    StringBuffer attachSaveName = new StringBuffer();
    String[] attachSaveNameArr = httpServletRequest.getParameterValues("accessorySaveName");
    int k = 0;
    for (int i = 0; attachSaveNameArr != null && 
      i < attachSaveNameArr.length; i++) {
      if (attachSaveNameArr[i] != null && !"".equals(attachSaveNameArr[i])) {
        k++;
        if (k != 1) {
          attachName.append("|");
          attachSaveName.append("|");
        } 
        attachName.append(attachNameArr[i]);
        attachSaveName.append(attachSaveNameArr[i]);
      } 
    } 
    po.setAccessoryName(attachName.toString());
    po.setAccessorySaveName(attachSaveName.toString());
    po.setContentAccName((httpServletRequest.getParameter("contentAccName") == null) ? "" : 
        httpServletRequest.getParameter("contentAccName").toString());
    po.setContentAccSaveName((httpServletRequest.getParameter("contentAccSaveName") == null) ? "" : 
        httpServletRequest.getParameter("contentAccSaveName").toString());
    po.setSendFileAccessoryDesc(httpServletRequest.getParameter("sendFileAccessoryDesc"));
    po.setSendFileType(httpServletRequest.getParameter("sendFileType"));
    po.setDocumentSendFileHead(httpServletRequest.getParameter("documentSendFileHead"));
    String sendFileDepartWord = "";
    if (httpServletRequest.getParameter("sendFileDepartWord") != null && 
      !httpServletRequest.getParameter("sendFileDepartWord").toString().equals("") && 
      !httpServletRequest.getParameter("sendFileDepartWord").toString().equals("null")) {
      String temWord = httpServletRequest.getParameter("sendFileDepartWord").toString();
      String[] wordObjs = temWord.split(";");
      po.setZjkyWordId(new Long(wordObjs[0]));
      if (wordObjs.length > 1) {
        sendFileDepartWord = wordObjs[1];
        po.setSendFileDepartWord(sendFileDepartWord);
      } 
    } 
    po.setSubmitFileType((httpServletRequest.getParameter("submitFileType") == null) ? "" : httpServletRequest.getParameter("submitFileType"));
    po.setOid((httpServletRequest.getParameter("oid") == null) ? "" : httpServletRequest.getParameter("oid"));
    if (httpServletRequest.getParameter("templateId") != null && 
      !httpServletRequest.getParameter("templateId").toString().equals(""))
      po.setSendFileTemId(httpServletRequest.getParameter("templateId")); 
    if (httpServletRequest.getParameter("sendFilePoNumId") != null && 
      !httpServletRequest.getParameter("sendFilePoNumId").toString().equals("") && 
      httpServletRequest.getParameter("field2") != null && 
      !"".equals(httpServletRequest.getParameter("field2")) && 
      !"null".equals(httpServletRequest.getParameter("field2"))) {
      po.setSendFilePoNumId(new Long(httpServletRequest.getParameter("sendFilePoNumId")));
      po.setDocumentSendFileByteNumber((httpServletRequest.getParameter("documentSendFileByteNumber") == null) ? "" : 
          httpServletRequest.getParameter("documentSendFileByteNumber"));
    } else {
      po.setDocumentSendFileByteNumber("");
    } 
    if (httpServletRequest.getParameter("sendFileRedHeadId") != null && 
      !"".equals(httpServletRequest.getParameter("sendFileRedHeadId").trim()) && 
      !"null".equals(httpServletRequest.getParameter("sendFileRedHeadId").trim()) && 
      !"0".equals(httpServletRequest.getParameter("sendFileRedHeadId").trim()))
      po.setSendFileRedHeadId(Long.parseLong(httpServletRequest.getParameter("sendFileRedHeadId"))); 
    if (httpServletRequest.getParameter("sendFileSealId") != null && 
      !"".equals(httpServletRequest.getParameter("sendFileSealId").trim()) && 
      !"null".equals(httpServletRequest.getParameter("sendFileSealId").trim())) {
      po.setSendFileSealId(Long.parseLong(httpServletRequest.getParameter("sendFileSealId")));
      if (!"-1".equals(httpServletRequest.getParameter("sendFileSealId"))) {
        Object[] obj = sendFileBD.getSealInfo(httpServletRequest.getParameter("sendFileSealId"));
        po.setSendFileSeal((String)obj[0]);
        po.setSendFileSealPic((String)obj[1]);
      } 
    } 
    po.setSendFileGrade(httpServletRequest.getParameter("sendFileGrade"));
    po.setDocumentSendFileSecurityGrade(httpServletRequest.getParameter("documentSendFileSecurityGrade"));
    po.setDocumentSendFileTitle(httpServletRequest.getParameter("documentSendFileTitle"));
    po.setDocumentSendFileTopicWord(httpServletRequest.getParameter("documentSendFileTopicWord"));
    if (httpServletRequest.getParameter("zjkySeq") != null)
      po.setZjkySeq(httpServletRequest.getParameter("zjkySeq").toString()); 
    po.setZjkySecrecyterm((httpServletRequest.getParameter("zjkySecrecyterm") == null) ? "" : 
        httpServletRequest.getParameter("zjkySecrecyterm").toString());
    po.setZjkyContentLevel((httpServletRequest.getParameter("zjkyContentLevel") == null) ? "" : 
        httpServletRequest.getParameter("zjkyContentLevel").toString());
    po.setSendToType(httpServletRequest.getParameter("sendToType"));
    if ("0".equals(httpServletRequest.getParameter("sendToType"))) {
      po.setMainToName(httpServletRequest.getParameter("toPerson1"));
      po.setCopyToName(httpServletRequest.getParameter("toPerson2"));
    } else if ("1".equals(httpServletRequest.getParameter("sendToType"))) {
      po.setMainToName(httpServletRequest.getParameter("toPerson3"));
      po.setSendRoundName(httpServletRequest.getParameter("toPerson4"));
      po.setSunderToName(httpServletRequest.getParameter("toPerson5"));
    } else if ("2".equals(httpServletRequest.getParameter("sendToType"))) {
      po.setSunderToName(httpServletRequest.getParameter("toPerson6"));
    } 
    po.setDocumentSendFileWriteOrg(httpServletRequest.getParameter("documentSendFileWriteOrg"));
    po.setDocumentSendFileCounterSign(httpServletRequest.getParameter("documentSendFileCounterSign"));
    po.setDocumentSendFileAssumePeople(httpServletRequest.getParameter("documentSendFileAssumePeople"));
    po.setSendFileDraft(httpServletRequest.getParameter("sendFileDraft"));
    po.setSendFileAgentDraft(httpServletRequest.getParameter("sendFileAgentDraft"));
    po.setDocumentSendFileCheckDate(httpServletRequest.getParameter("documentSendFileCheckDate"));
    po.setSendFilePrinter(httpServletRequest.getParameter("sendFilePrinter"));
    po.setSendFileProof(httpServletRequest.getParameter("sendFileProof"));
    if (httpServletRequest.getParameter("documentSendFileTime") != null)
      po.setDocumentSendFileDate(new Date(httpServletRequest.getParameter("documentSendFileTime"))); 
    po.setDocumentSendFilePrintNumber((httpServletRequest
        .getParameter("documentSendFilePrintNumber") == null || 
        "".equals(httpServletRequest.getParameter("documentSendFilePrintNumber"))) ? 0 : 
        Integer.parseInt(httpServletRequest.getParameter("documentSendFilePrintNumber")));
    if (httpServletRequest.getParameter("documentSendFileSendTime") != null)
      po.setDocumentSendFileSendDate(new Date(httpServletRequest.getParameter("documentSendFileSendTime"))); 
    po.setField1(httpServletRequest.getParameter("field1"));
    if (httpServletRequest.getParameter("field2") != null && 
      !httpServletRequest.getParameter("field2").equals(""))
      po.setField2(httpServletRequest.getParameter("field2")); 
    if (httpServletRequest.getParameter("field3") != null && 
      !httpServletRequest.getParameter("field3").equals(""))
      po.setField3((httpServletRequest.getParameter("field3") == null) ? "" : 
          httpServletRequest.getParameter("field3")); 
    po.setSendFieldSelectMoreEmp((httpServletRequest.getParameter("sendFieldSelectMoreEmp") == null) ? "" : 
        httpServletRequest.getParameter("sendFieldSelectMoreEmp"));
    po.setField4(httpServletRequest.getParameter("field4"));
    po.setField5(httpServletRequest.getParameter("field5"));
    po.setField6(httpServletRequest.getParameter("field6"));
    po.setField7(httpServletRequest.getParameter("field7"));
    po.setField8(httpServletRequest.getParameter("field8"));
    po.setField9(httpServletRequest.getParameter("field9"));
    po.setField10(httpServletRequest.getParameter("field10"));
    po.setDocumentWordType(httpServletRequest.getParameter("documentWordType"));
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    po.setCreatedEmp(Long.parseLong(session.getAttribute("userId").toString()));
    po.setCreatedOrg(Long.parseLong(session.getAttribute("orgId").toString()));
    po.setDomainId(domainId);
    if (httpServletRequest.getParameter("fromReceiveFileId") != null) {
      po.setReceiveFileId(Long.valueOf(httpServletRequest
            .getParameter("fromReceiveFileId")));
      po.setReceiveFileLink(httpServletRequest
          .getParameter("fromReceiveFileLink"));
    } 
    po.setCreatedTime(new Date());
    if (httpServletRequest.getParameter("documentCreateTime") != null && 
      !httpServletRequest.getParameter("documentCreateTime").toString().equals(""))
      po.setCreatedTime(new Date(httpServletRequest.getParameter("documentCreateTime"))); 
    if (httpServletRequest.getParameter("sendStatus") != null && 
      !httpServletRequest.getParameter("sendStatus").toString().equals("")) {
      po.setTransactStatus(httpServletRequest.getParameter("sendStatus"));
    } else {
      po.setTransactStatus("0");
    } 
    if (httpServletRequest.getParameter("fromFileSendCheckId") != null) {
      po.setFileSendCheckId(httpServletRequest.getParameter("fromFileSendCheckId"));
      po.setFileSendCheckLink(httpServletRequest.getParameter("fromFileSendCheckLink"));
    } 
    if (httpServletRequest.getParameter("toPerson1Id") != null && 
      !httpServletRequest.getParameter("toPerson1Id").toString().equals("")) {
      po.setToPerson1Id(httpServletRequest.getParameter("toPerson1Id"));
    } else {
      po.setToPerson1Id("");
    } 
    if (httpServletRequest.getParameter("toPerson2Id") != null && 
      !httpServletRequest.getParameter("toPerson2Id").toString().equals("")) {
      po.setToPerson2Id(httpServletRequest.getParameter("toPerson2Id"));
    } else {
      po.setToPerson2Id("");
    } 
    if (httpServletRequest.getParameter("toPersonBaoId") != null && 
      !httpServletRequest.getParameter("toPersonBaoId").toString().equals("")) {
      po.setToPersonBaoId(httpServletRequest.getParameter("toPersonBaoId"));
    } else {
      po.setToPersonBaoId("");
    } 
    if (httpServletRequest.getParameter("toPersonInnerId") != null && 
      !httpServletRequest.getParameter("toPersonInnerId").toString().equals("")) {
      po.setToPersonInnerId(httpServletRequest.getParameter("toPersonInnerId"));
    } else {
      po.setToPersonInnerId("");
    } 
    if (httpServletRequest.getParameter("toPersonBao") != null) {
      po.setToPersonBao(httpServletRequest.getParameter("toPersonBao"));
    } else {
      po.setToPersonBao("");
    } 
    if (httpServletRequest.getParameter("toPersonInner") != null) {
      po.setToPersonInner(httpServletRequest.getParameter("toPersonInner"));
    } else {
      po.setToPersonInner("");
    } 
    if (httpServletRequest.getParameter("editId") != null && 
      !httpServletRequest.getParameter("editId").toString().equals("")) {
      SendFlowResavePO savePo = new SendFlowResavePO();
      savePo.setFlowEmpId(new Long(session.getAttribute("userId").toString()));
      savePo.setSendId(new Long(httpServletRequest.getParameter("editId")));
      String whichBatch = httpServletRequest.getParameter("whichBatch");
      if (httpServletRequest.getParameter("isInModify") != null && 
        httpServletRequest.getParameter("isInModify").toString().equals("isInModify")) {
        if (whichBatch.equals("documentSendFileAssumeUnit"))
          setResavePO(httpServletRequest, savePo, "documentSendFileAssumeUnit"); 
        if (whichBatch.equals("sendFileMassDraft"))
          setResavePO(httpServletRequest, savePo, "sendFileMassDraft"); 
        if (whichBatch.equals("sendFileProveDraft"))
          setResavePO(httpServletRequest, savePo, "sendFileProveDraft"); 
        if (whichBatch.equals("sendFileReadComment"))
          setResavePO(httpServletRequest, savePo, "sendFileReadComment"); 
        if (whichBatch.equals("documentSendFileCheckCommit"))
          setResavePO(httpServletRequest, savePo, "documentSendFileCheckCommit"); 
        if (whichBatch.equals("documentSendFileSendFile"))
          setResavePO(httpServletRequest, savePo, "documentSendFileSendFile"); 
        if (whichBatch.equals("leader7"))
          setResavePO(httpServletRequest, savePo, "leader7"); 
      } else if (httpServletRequest.getParameter("isInModify") == null || 
        !httpServletRequest.getParameter("isInModify").toString().equals("isEditModify")) {
        if (httpServletRequest.getParameter("resaveId") != null && 
          !httpServletRequest.getParameter("resaveId").toString().equals(""))
          sendFileBD.deleterResave(httpServletRequest.getParameter("resaveId")); 
      } 
    } 
    if (httpServletRequest.getParameter("signsendTime") != null)
      po.setSignsendTime(new Date(httpServletRequest.getParameter("signsendTime"))); 
    if (httpServletRequest.getParameter("openProperty") != null)
      po.setOpenProperty(httpServletRequest.getParameter("openProperty")); 
    if (httpServletRequest.getParameter("sendTextField1") != null)
      po.setSendTextField1(httpServletRequest.getParameter("sendTextField1")); 
    if (httpServletRequest.getParameter("sendTextField2") != null)
      po.setSendTextField2(httpServletRequest.getParameter("sendTextField2")); 
    if (httpServletRequest.getParameter("sendDropDownSelect1") != null)
      po.setSendDropDownSelect1(httpServletRequest.getParameter("sendDropDownSelect1")); 
    if (httpServletRequest.getParameter("sendDropDownSelect2") != null)
      po.setSendDropDownSelect2(httpServletRequest.getParameter("sendDropDownSelect2")); 
    if (httpServletRequest.getParameter("sendMutliTextField1") != null)
      po.setSendMutliTextField1(httpServletRequest.getParameter("sendMutliTextField1")); 
    po.setTableId(Long.valueOf(httpServletRequest.getParameter("tableId")));
    return po;
  }
  
  public void sendMessage(String recordId, HttpServletRequest request) {
    String sendFileNeedSendMsg = (new StringBuilder(
        String.valueOf(request.getParameter("sendFileNeedSendMsg")))).toString();
    if ("1".equals(sendFileNeedSendMsg)) {
      String sendToId = request.getParameter("sendToId");
      String[] userIdArr = (String[])null;
      String userIdSting = (new SendFileBD()).getSendMsgReceiver(sendToId);
      if (userIdSting != null)
        userIdArr = userIdSting.split(","); 
      String model = "发文管理";
      String telephone = "";
      String fileSign = "GWGL_SF_" + recordId;
      String title = "你有一份新的发文：" + 
        request.getParameter("documentSendFileTitle") + "，请查办。";
      ModelSendMsg msg = new ModelSendMsg();
      if (userIdArr != null)
        for (int i = 0; i < userIdArr.length; i++)
          msg.sendFileSystemMsg(model, title, userIdArr[i], 
              telephone, fileSign, request);  
      msg = null;
    } 
  }
  
  public void sendSupplyMessage(String recordId, HttpServletRequest request) {
    String sendFileNeedSendMsg = (new StringBuilder(
        String.valueOf(request.getParameter("sendFileNeedSendMsg2")))).toString();
    if ("1".equals(sendFileNeedSendMsg)) {
      String sendToId = request.getParameter("sendToId2");
      String[] userIdArr = (String[])null;
      String userIdSting = (new SendFileBD()).getSendMsgReceiver(sendToId);
      if (userIdSting != null)
        userIdArr = userIdSting.split(","); 
      String model = "发文管理";
      String telephone = "";
      String fileSign = "GWGL_SF_" + recordId;
      String title = "您有一份新的收文：" + 
        request.getParameter("documentSendFileTitle") + "，请查办。";
      ModelSendMsg msg = new ModelSendMsg();
      if (userIdArr != null)
        for (int i = 0; i < userIdArr.length; i++)
          msg.sendFileSystemMsg(model, title, userIdArr[i], 
              telephone, fileSign, request);  
      msg = null;
    } 
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
      rs = stmt
        .executeQuery("select useraccounts from org_employee where emp_id in (" + 
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
      util.sendNotify(allUserIdBuffer.toString(), title, content, "0", 
          "0");
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
        String orgCode = dbopt
          .executeQueryToStr("select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
            orgIdArr[i]);
        rs = dbopt
          .executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
            orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && 
              orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {}
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
        String empIdStr = dbopt
          .executeQueryToStr("select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + 
            groupIdArr[i]);
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
      userStr = userStr.substring(1, userStr.length() - 1)
        .replaceAll("$$", ","); 
    return userStr;
  }
  
  private void changeNumfig(HttpServletRequest request) {
    String numId = (request.getParameter("sendFilePoNumId") == null) ? "" : 
      request.getParameter("sendFilePoNumId").toString();
    if (numId != null && !numId.trim().equals("")) {
      String field2 = (request.getParameter("field2") == null) ? "" : 
        request.getParameter("field2").toString();
      SenddocumentBD bd = new SenddocumentBD();
      bd.setNumfigById(numId, field2);
    } 
  }
  
  private void changeSeqfig(HttpServletRequest request) {
    String seqId = (request.getParameter("sendSeqId") == null) ? "" : request
      .getParameter("sendSeqId").toString();
    if (seqId != null && !seqId.trim().equals("")) {
      String field2 = (request.getParameter("sendSeqfig") == null) ? "" : 
        request.getParameter("sendSeqfig").toString();
      SenddocumentBD bd = new SenddocumentBD();
      bd.setSeqfigBySeqId(seqId, field2);
    } 
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : 
      httpSession.getAttribute("domainId").toString();
    String editId = httpServletRequest.getParameter("editId");
    SendFileBD sendFileBD = new SendFileBD();
    sendFileBD.delete(editId);
  }
  
  public void deleteSenddocument2(HttpServletRequest httpServletRequest) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    String domainId = (httpSession.getAttribute("domainId") == null) ? "0" : 
      httpSession.getAttribute("domainId").toString();
    String editId = httpServletRequest.getParameter("editId");
    SendFileBD sendFileBD = new SendFileBD();
    sendFileBD.delete(editId);
  }
  
  public void saveUpdate(HttpServletRequest httpServletRequest, GovDocumentSendFilePO po, Long sendFileId) {
    Date date = new Date();
    SendFileBD sendFileBD = new SendFileBD();
    Object object1 = httpServletRequest.getSession(true).getAttribute("userId");
    Object object2 = httpServletRequest.getSession(true).getAttribute("orgId");
    Object object3 = httpServletRequest.getSession(true).getAttribute("userName");
    Object object4 = httpServletRequest.getSession(true).getAttribute("orgName");
    SenddocumentUpdate updatePo = new SenddocumentUpdate();
    updatePo.setSendFileId(sendFileId);
    updatePo.setUpdateEmpId(new Long((String)object1));
    updatePo.setUpdateEmpName((String)object3);
    updatePo.setUpdateOrgId(new Long((String)object2));
    updatePo.setUpdateOrgName((String)object4);
    updatePo.setUpdateTime((new Date()).toLocaleString());
    if (httpServletRequest.getParameter("oldTitle") != null && httpServletRequest.getParameter("oldTitle").toString().equals("1")) {
      updatePo.setSendTitle(po.getDocumentSendFileTitle());
      updatePo.setSendMainTo("1");
      sendFileBD.saveSendUpdate(updatePo);
    } 
    if (httpServletRequest.getParameter("oldToPerson1") != null && httpServletRequest.getParameter("oldToPerson1").toString().equals("2")) {
      updatePo.setSendTitle(po.getMainToName());
      updatePo.setSendMainTo("2");
      sendFileBD.saveSendUpdate(updatePo);
    } 
    if (httpServletRequest.getParameter("oldToPerson2") != null && httpServletRequest.getParameter("oldToPerson2").toString().equals("3")) {
      updatePo.setSendTitle(po.getCopyToName());
      updatePo.setSendMainTo("3");
      sendFileBD.saveSendUpdate(updatePo);
    } 
    if (httpServletRequest.getParameter("oldToInnner") != null && httpServletRequest.getParameter("oldToInnner").toString().equals("4")) {
      updatePo.setSendTitle(po.getToPersonInner());
      updatePo.setSendMainTo("4");
      sendFileBD.saveSendUpdate(updatePo);
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
  
  private void saveAssociate(Long result, HttpServletRequest httpServletRequest) {
    HttpSession session = httpServletRequest.getSession(true);
    ReceiveFileBD receiveFileBD = new ReceiveFileBD();
    ReceiveAssociatePO po = new ReceiveAssociatePO();
    po.setDomainId(new Long((String)session.getAttribute("domainId")));
    po.setSendFileId(result);
    po.setReceiveFileId(new Long(httpServletRequest.getParameter("fromReceiveFileId")));
    po.setTransOrgId(new Long((String)session.getAttribute("orgId")));
    po.setTransUserId(new Long((String)session.getAttribute("userId")));
    receiveFileBD.saveReceiveAssociate(po);
  }
  
  private void updateWorkTitle(HttpServletRequest request) {
    String isInModify = (request.getParameter("isInModify") == null) ? "" : request.getParameter("isInModify").toString();
    String processId = (request.getParameter("processId") == null) ? "null" : request.getParameter("processId");
    String table = (request.getParameter("table") == null) ? "null" : request.getParameter("table").toString();
    String editId = (request.getParameter("editId") == null) ? "null" : request.getParameter("editId").toString();
    String oldTitle = (request.getParameter("oldTitle") == null) ? "0" : request.getParameter("oldTitle").toString();
    String workTitle = (request.getParameter("documentSendFileTitle") == null) ? "" : request.getParameter("documentSendFileTitle").toString();
    if (oldTitle.equals("1") && !processId.equals("null") && !table.equals("null") && !editId.equals("null") && isInModify.equals("isInModify")) {
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
      int sendFile = 0;
      base.begin();
      String sql = "SELECT infoChannelId,WORKFLOWPROCESSNAME,sendFileType FROM jsf_workflowprocess WHERE WF_WORKFLOWPROCESS_ID=" + 
        processeId;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        list.add((new StringBuilder(String.valueOf(rs.getString(2)))).toString());
        list.add(rs.getString(1));
        sendFile = rs.getInt(3);
        if (rs.getString(1) != null && !"".equals(rs.getString(1)) && !"null".equalsIgnoreCase(rs.getString(1)))
          flag = true; 
      } 
      rs.close();
      if (flag) {
        sql = "SELECT emp.empname,emp.emp_id,o.org_id,o.orgName,o.orgIdString,j.documentsendfile_title,DOCUMENTSENDFILE_BYTENUMBER  FROM jsf_work w JOIN doc_documentsendfile j ON w.WORKRECORD_ID=j.documentsendfile_id  JOIN org_employee emp ON w.WF_CUREMPLOYEE_ID=emp.EMP_ID  JOIN org_organization_user e ON emp.emp_id=e.EMP_ID  JOIN org_organization o ON e.ORG_ID=o.org_id  where w.WORKPROCESS_ID=" + 



          
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
          String title = rs.getString(6);
          if (wh != null && !"".equals(wh))
            title = "【" + wh + "】" + title; 
          list.set(0, title);
        } 
        rs.close();
        list.add(recordId);
        if (sendFile == 0) {
          (new InfoArchives()).saveInfo(list, ((String)list.get(3)).toString(), "GWGL-FWGL");
        } else {
          (new InfoArchives()).saveInfo(list, ((String)list.get(3)).toString(), "GWGL-DOC");
        } 
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  private boolean isDraft(String editId) {
    boolean flag = false;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String isDraft = "";
    String sql = "select SENDFILE_ISDRAFT from DOC_DOCUMENTSENDFILE  where DOCUMENTSENDFILE_ID=" + editId;
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
