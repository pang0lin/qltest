package com.js.util.config;

import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class DocFieldMap {
  private static Map map = null;
  
  private static Map nameMap = null;
  
  private static Map receiveMap = null;
  
  private static Map receiveNameMap = null;
  
  public static Map getReceiveMap() {
    if (receiveMap == null)
      initReceiveMap(); 
    return receiveMap;
  }
  
  public static void initReceiveMap() {
    receiveMap = new HashMap<Object, Object>();
    receiveMap.put("receiveFileReceiveDate", "receiveFile_receivedate");
    receiveMap.put("createdDate", "createdTime");
    receiveMap.put("field5", "field5");
    receiveMap.put("field6", "field6");
    receiveMap.put("field7", "field7");
    receiveMap.put("field8", "field8");
    receiveMap.put("field10", "field10");
    receiveMap.put("receiveFileSendFileUnit", "receiveFile_SendFileUnit");
    receiveMap.put("receiveFileFileNumber", "receiveFile_FileNumber");
    receiveMap.put("receiveFileTitle", "receiveFile_Title");
    receiveMap.put("receiveFileSafetyGrade", "receiveFile_SafetyGrade");
    receiveMap.put("receiveFileQuantity", "receiveFile_Quantity");
    receiveMap.put("receiveFileDoComment", "receiveFile_DoComment");
    receiveMap.put("zjkySeq", "zjkySeq");
    receiveMap.put("zjkyType", "zjkyType");
    receiveMap.put("zjkykeepTerm", "zjkykeepTerm");
    receiveMap.put("receiveFileType", "receiveFileType");
    receiveMap.put("field4", "field4");
    receiveMap.put("receiveFileLeaderComment", "receiveFile_LeaderComment");
    receiveMap.put("receiveFileSettleLeaderComment", "receiveFileSettleLeaderComment");
    receiveMap.put("receiveFileSettleComment", "receiveFile_SettleComment");
    receiveMap.put("field9", "field9");
    receiveMap.put("receiveFileTransAuditComment", "receiveFileTransAuditComment");
    receiveMap.put("receiveFileMemo", "receiveFile_Memo");
    receiveMap.put("accessoryName1", "accessoryName");
    receiveMap.put("accessoryName2", "accessoryName_file");
    receiveMap.put("receiveTextField1", "receive_text_field1");
    receiveMap.put("receiveTextField2", "receive_text_field2");
    receiveMap.put("receiveDropDownSelect1", "receive_drop_down_select1");
    receiveMap.put("receiveDropDownSelect2", "receive_drop_down_select2");
    receiveMap.put("receiveMutliTextField1", "receive_mutli_text_field1");
  }
  
  public static Map getReceiveNameMap() {
    if (receiveNameMap == null)
      initReceiveNameMap(); 
    return receiveNameMap;
  }
  
  public static void initReceiveNameMap() {
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    receiveNameMap = new HashMap<Object, Object>();
    try {
      dataSourceBase.begin();
      String sql = "select gffName,gffdisplayName from form_initfield where govFormtype=1 order by id";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        receiveNameMap.put(rs.getString(1).toString(), rs.getString(2).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static Map getNameMap() {
    if (nameMap == null)
      initNameMap(); 
    return nameMap;
  }
  
  public static void initNameMap() {
    DataSourceBase dataSourceBase = new DataSourceBase();
    ResultSet rs = null;
    nameMap = new HashMap<Object, Object>();
    try {
      dataSourceBase.begin();
      String sql = "select gffName,gffdisplayName from form_initfield where govFormtype=0 order by id";
      rs = dataSourceBase.executeQuery(sql);
      while (rs.next())
        nameMap.put(rs.getString(1).toString(), rs.getString(2).toString()); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
        dataSourceBase.end();
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public static Map getSendDocMap() {
    if (map == null)
      initMap(); 
    return map;
  }
  
  public static void initMap() {
    map = new HashMap<Object, Object>();
    map.put("accessoryName", "accessoryName");
    map.put("sendFileAccessoryDesc", "sendFile_AccessayDesc");
    map.put("sendFileDepartWord", "sendFileDepartWord");
    map.put("documentSendFileByteNumber", "documentSendFile_ByteNumber");
    map.put("sendFileGrade", "sendFile_Grade");
    map.put("documentSendFileSecurityGrade", "documentSendFile_SecurityGrade");
    map.put("documentSendFileTitle", "documentSendFile_Title");
    map.put("documentSendFileTopicWord", "documentSendFile_TopicWord");
    map.put("zjkySeq", "zjkySeq");
    map.put("zjkySecrecyterm", "zjkySecrecyterm");
    map.put("zjkyContentLevel", "zjkyContentLevel");
    map.put("documentSendFileWriteOrg", "documentSendFile_WriteOrg");
    map.put("documentSendFileCounterSign", "documentSendFile_CounterSign");
    map.put("documentSendFileAssumePeople", "documentSendFile_AssumePeople");
    map.put("sendFileDraft", "sendFile_Draft");
    map.put("sendFileAgentDraft", "sendFile_AgentDraft");
    map.put("documentSendFileCheckDate", "documentSendFile_CheckDate");
    map.put("sendFilePrinter", "sendFile_Printer");
    map.put("sendFileProof", "sendFile_Proof");
    map.put("documentSendFileTime", "documentSendFile_Date");
    map.put("documentSendFileSendTime", "documentSendFile_SendDate");
    map.put("documentSendFilePrintNumber", "documentSendFile_PrintNumber");
    map.put("field9", "field9");
    map.put("field10", "field10");
    map.put("toPerson1", "maintoname");
    map.put("toPerson2", "copytoname");
    map.put("toPersonBao", "toPersonBao");
    map.put("toPersonInner", "toPersonInner");
    map.put("field4", "field4");
    map.put("field7", "field7");
    map.put("field8", "field8");
    map.put("documentSendFileAssumeUnit", "documentSendFile_AssumeUnit");
    map.put("sendFileMassDraft", "sendFile_MassDraft");
    map.put("sendFileProveDraft", "sendFile_ProveDraft");
    map.put("sendFileReadComment", "sendFile_ReadComment");
    map.put("documentSendFileCheckCommit", "documentSendFile_CheckCommit");
    map.put("documentSendFileSendFile", "documentSendFile_SendFile");
    map.put("signsendTime", "createdTime");
    map.put("sendTextField1", "send_text_field1");
    map.put("sendTextField2", "send_text_field2");
    map.put("sendDropDownSelect1", "send_drop_down_select1");
    map.put("sendDropDownSelect2", "send_drop_down_select2");
    map.put("sendMutliTextField1", "send_mutli_text_field1");
    map.put("openProperty", "openProperty");
  }
}
