package com.js.doc.doc.bean;

import com.js.doc.doc.po.BaseInfoPO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.po.SendDocumentSeqPO;
import com.js.doc.doc.po.SendDocumentUnitPO;
import com.js.doc.doc.po.SendDocumentWordPO;
import com.js.doc.doc.po.SenddocumentTopicalPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface SenddocumentEJB extends EJBObject {
  Object[] getSenddocumentBaseInfo(String paramString) throws Exception, RemoteException;
  
  Long saveBaseInfo(BaseInfoPO paramBaseInfoPO) throws Exception, RemoteException;
  
  String updateBaseInfo(BaseInfoPO paramBaseInfoPO) throws Exception, RemoteException;
  
  Long saveWordInfo(SendDocumentWordPO paramSendDocumentWordPO) throws Exception, RemoteException;
  
  SendDocumentWordPO loadSendDocumentWordPO(String paramString) throws Exception, RemoteException;
  
  String updateWordPO(String paramString, SendDocumentWordPO paramSendDocumentWordPO) throws Exception, RemoteException;
  
  String deleteWordPO(String paramString) throws Exception, RemoteException;
  
  List getNoNumWord(String paramString) throws Exception, RemoteException;
  
  String setWordPoNum(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Long saveNunInfo(SendDocumentNumPO paramSendDocumentNumPO) throws Exception, RemoteException;
  
  SendDocumentNumPO loadSendDocumentNumPO(String paramString) throws Exception, RemoteException;
  
  List getwordBynumId(String paramString) throws Exception, RemoteException;
  
  String removeWordPoNum(String paramString) throws Exception, RemoteException;
  
  String updateNumPO(String paramString, SendDocumentNumPO paramSendDocumentNumPO) throws Exception, RemoteException;
  
  String deleteNumPO(String paramString) throws Exception, RemoteException;
  
  Long SaveSeqPO(SendDocumentSeqPO paramSendDocumentSeqPO) throws Exception, RemoteException;
  
  String setWordPoSeq(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String updateSeqPO(String paramString, SendDocumentSeqPO paramSendDocumentSeqPO) throws Exception, RemoteException;
  
  SendDocumentSeqPO loadSendDocumentSeqPO(String paramString) throws Exception, RemoteException;
  
  List getwordBySeqId(String paramString) throws Exception, RemoteException;
  
  List getNoSeqWord(String paramString) throws Exception, RemoteException;
  
  String removeWordPoSeq(String paramString) throws Exception, RemoteException;
  
  String deleteSeqPO(String paramString) throws Exception, RemoteException;
  
  List getAllSendDocumentNumPO(String paramString) throws Exception, RemoteException;
  
  List getAllSendDocumentSeqPO(String paramString) throws Exception, RemoteException;
  
  List getTemplateList(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllUserTemplateList(String paramString) throws Exception, RemoteException;
  
  List getTemplateById(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getNunfigByNumId(String paramString) throws Exception, RemoteException;
  
  String setNumfigById(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getSeqfigBySeqID(String paramString) throws Exception, RemoteException;
  
  String setSeqfigBySeqId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllHaveTmep_Word(String paramString) throws Exception, RemoteException;
  
  String updateTopicalPO(SenddocumentTopicalPO paramSenddocumentTopicalPO) throws Exception, RemoteException;
  
  Long saveTopicalInfo(SenddocumentTopicalPO paramSenddocumentTopicalPO) throws Exception, RemoteException;
  
  SenddocumentTopicalPO loadSendTopicalPO(String paramString) throws Exception, RemoteException;
  
  String deleteTopicalPO(String paramString) throws Exception, RemoteException;
  
  String deleteUnitPO(String paramString) throws Exception, RemoteException;
  
  SendDocumentUnitPO loadSendUnitPO(String paramString) throws Exception, RemoteException;
  
  String updateUnitPO(SendDocumentUnitPO paramSendDocumentUnitPO) throws Exception, RemoteException;
  
  Long saveUnitInfo(SendDocumentUnitPO paramSendDocumentUnitPO) throws Exception, RemoteException;
  
  List getAllSendDocumentUnitPO(String paramString) throws Exception, RemoteException;
  
  List getAllHaveTemp_Num_Seq_Word(String paramString) throws Exception, RemoteException;
  
  List getWordByProceeIdAnd4Type(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getSendNumByNumClass(String paramString) throws Exception, RemoteException;
  
  String updateNumByPara(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getCommentByCommFiledList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getEmpIdsByTotal(String paramString) throws Exception, RemoteException;
  
  List getAllFealMenBymoduleId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getCanChooseTemplateList(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3) throws Exception, RemoteException;
  
  Integer getCanChooseTemplateListCount(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
}
