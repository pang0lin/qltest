package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.SendAssociatePO;
import com.js.doc.doc.po.SendFlowResavePO;
import com.js.doc.doc.po.SenddocumentUpdate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface SendFileEJBLocal extends EJBLocalObject {
  Long save(GovDocumentSendFilePO paramGovDocumentSendFilePO) throws Exception;
  
  Object[] getHeadInfo(String paramString) throws Exception;
  
  Object[] getSealInfo(String paramString) throws Exception;
  
  List getRedHeadList(String paramString) throws Exception;
  
  String getSendFileMaxNumber(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getSealList(String paramString) throws Exception;
  
  Long draft(String paramString, GovDocumentSendFilePO paramGovDocumentSendFilePO) throws Exception;
  
  GovDocumentSendFilePO load(String paramString) throws Exception;
  
  String getSendFileHead(String paramString) throws Exception;
  
  Long update(String paramString, GovDocumentSendFilePO paramGovDocumentSendFilePO) throws Exception;
  
  Map getNewInnerDocumentCount(String paramString) throws Exception;
  
  Integer send(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer send(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List getBrowerUser(String paramString, Integer paramInteger) throws Exception;
  
  Integer removeSendUser(String paramString) throws Exception;
  
  Map getDossierInfo(String paramString) throws Exception;
  
  Boolean isPigeonholed(String paramString) throws Exception;
  
  Integer setPigeonholed(String paramString) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  Map getDocWF(String paramString1, String paramString2) throws Exception;
  
  Integer delete(String paramString) throws Exception;
  
  void sendFileBoxDel(String paramString) throws Exception;
  
  String getSendMsgReceiver(String paramString) throws Exception;
  
  Integer setSendFileBrower(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  Long completeSendFile(String paramString) throws Exception;
  
  HashMap getUserOrg(String paramString) throws Exception;
  
  String getSendRtxReceiver(String paramString) throws Exception;
  
  Long saveResave(SendFlowResavePO paramSendFlowResavePO) throws Exception;
  
  Long updateResave(SendFlowResavePO paramSendFlowResavePO) throws Exception;
  
  String deleterResave(String paramString) throws Exception;
  
  List getAllResavePoByEmpIdType(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String deleterResaveByTwo(String paramString1, String paramString2) throws Exception;
  
  String monitorRedo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  Long saveSendAssociate(SendAssociatePO paramSendAssociatePO) throws Exception;
  
  Integer getSendAssociateNum(String paramString) throws Exception;
  
  List getSendAssociateList(String paramString) throws Exception;
  
  Integer getOutSeeNum(String paramString) throws Exception;
  
  Long saveSendUpdate(SenddocumentUpdate paramSenddocumentUpdate) throws Exception;
  
  List getAllSendDocumentUpdatePO(String paramString) throws Exception;
  
  List getWfTableInfoByTableId(String paramString) throws Exception;
  
  List getCommentByCommFiledList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getCommentByCommFiled(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  String updateWorkTitle(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
}
