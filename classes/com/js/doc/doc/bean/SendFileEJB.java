package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.SendAssociatePO;
import com.js.doc.doc.po.SendFlowResavePO;
import com.js.doc.doc.po.SenddocumentUpdate;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface SendFileEJB extends EJBObject {
  Long save(GovDocumentSendFilePO paramGovDocumentSendFilePO) throws Exception, RemoteException;
  
  Object[] getHeadInfo(String paramString) throws Exception, RemoteException;
  
  Object[] getSealInfo(String paramString) throws Exception, RemoteException;
  
  List getRedHeadList(String paramString) throws Exception, RemoteException;
  
  String getSendFileMaxNumber(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getSealList(String paramString) throws Exception, RemoteException;
  
  Long draft(String paramString, GovDocumentSendFilePO paramGovDocumentSendFilePO) throws Exception, RemoteException;
  
  GovDocumentSendFilePO load(String paramString) throws Exception, RemoteException;
  
  String getSendFileHead(String paramString) throws Exception, RemoteException;
  
  Long update(String paramString, GovDocumentSendFilePO paramGovDocumentSendFilePO) throws Exception, RemoteException;
  
  Map getNewInnerDocumentCount(String paramString) throws Exception, RemoteException;
  
  Integer send(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer send(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getBrowerUser(String paramString, Integer paramInteger) throws Exception, RemoteException;
  
  Integer removeSendUser(String paramString) throws Exception, RemoteException;
  
  Map getDossierInfo(String paramString) throws Exception, RemoteException;
  
  Boolean isPigeonholed(String paramString) throws Exception, RemoteException;
  
  Integer setPigeonholed(String paramString) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  Map getDocWF(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer delete(String paramString) throws Exception, RemoteException;
  
  void sendFileBoxDel(String paramString) throws Exception, RemoteException;
  
  String getSendMsgReceiver(String paramString) throws Exception, RemoteException;
  
  Integer setSendFileBrower(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  Long completeSendFile(String paramString) throws Exception, RemoteException;
  
  HashMap getUserOrg(String paramString) throws Exception;
  
  String getSendRtxReceiver(String paramString) throws Exception;
  
  Long saveResave(SendFlowResavePO paramSendFlowResavePO) throws Exception, RemoteException;
  
  Long updateResave(SendFlowResavePO paramSendFlowResavePO) throws Exception, RemoteException;
  
  String deleterResave(String paramString) throws Exception, RemoteException;
  
  List getAllResavePoByEmpIdType(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String deleterResaveByTwo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String monitorRedo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  Long saveSendAssociate(SendAssociatePO paramSendAssociatePO) throws Exception, RemoteException;
  
  Integer getSendAssociateNum(String paramString) throws Exception, RemoteException;
  
  List getSendAssociateList(String paramString) throws Exception, RemoteException;
  
  Integer getOutSeeNum(String paramString) throws Exception, RemoteException;
  
  Long saveSendUpdate(SenddocumentUpdate paramSenddocumentUpdate) throws Exception, RemoteException;
  
  List getAllSendDocumentUpdatePO(String paramString) throws Exception, RemoteException;
  
  List getWfTableInfoByTableId(String paramString) throws Exception, RemoteException;
  
  List getCommentByCommFiledList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getCommentByCommFiled(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws RemoteException, Exception;
  
  String updateWorkTitle(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
}
