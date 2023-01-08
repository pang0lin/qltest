package com.js.doc.doc.bean;

import com.js.doc.doc.po.BaseInfoPO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.po.SendDocumentSeqPO;
import com.js.doc.doc.po.SendDocumentUnitPO;
import com.js.doc.doc.po.SendDocumentWordPO;
import com.js.doc.doc.po.SenddocumentTopicalPO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface SenddocumentEJBLocal extends EJBLocalObject {
  Object[] getSenddocumentBaseInfo(String paramString) throws Exception;
  
  Long saveBaseInfo(BaseInfoPO paramBaseInfoPO) throws Exception;
  
  String updateBaseInfo(BaseInfoPO paramBaseInfoPO) throws Exception;
  
  Long saveWordInfo(SendDocumentWordPO paramSendDocumentWordPO) throws Exception;
  
  SendDocumentWordPO loadSendDocumentWordPO(String paramString) throws Exception;
  
  String updateWordPO(String paramString, SendDocumentWordPO paramSendDocumentWordPO) throws Exception;
  
  String deleteWordPO(String paramString) throws Exception;
  
  List getNoNumWord(String paramString) throws Exception;
  
  String setWordPoNum(String paramString1, String paramString2) throws Exception;
  
  Long saveNunInfo(SendDocumentNumPO paramSendDocumentNumPO) throws Exception;
  
  SendDocumentNumPO loadSendDocumentNumPO(String paramString) throws Exception;
  
  List getwordBynumId(String paramString) throws Exception;
  
  String removeWordPoNum(String paramString) throws Exception;
  
  String updateNumPO(String paramString, SendDocumentNumPO paramSendDocumentNumPO) throws Exception;
  
  String deleteNumPO(String paramString) throws Exception;
  
  Long SaveSeqPO(SendDocumentSeqPO paramSendDocumentSeqPO) throws Exception;
  
  String setWordPoSeq(String paramString1, String paramString2) throws Exception;
  
  String updateSeqPO(String paramString, SendDocumentSeqPO paramSendDocumentSeqPO) throws Exception;
  
  SendDocumentSeqPO loadSendDocumentSeqPO(String paramString) throws Exception;
  
  List getwordBySeqId(String paramString) throws Exception;
  
  List getNoSeqWord(String paramString) throws Exception;
  
  String removeWordPoSeq(String paramString) throws Exception;
  
  String deleteSeqPO(String paramString) throws Exception;
  
  List getAllSendDocumentNumPO(String paramString) throws Exception;
  
  List getAllSendDocumentSeqPO(String paramString) throws Exception;
  
  List getTemplateList(String paramString1, String paramString2) throws Exception;
  
  List getAllUserTemplateList(String paramString) throws Exception;
  
  List getTemplateById(String paramString1, String paramString2) throws Exception;
  
  String getNunfigByNumId(String paramString) throws Exception;
  
  String setNumfigById(String paramString1, String paramString2) throws Exception;
  
  String getSeqfigBySeqID(String paramString) throws Exception;
  
  String setSeqfigBySeqId(String paramString1, String paramString2) throws Exception;
  
  List getAllHaveTmep_Word(String paramString) throws Exception;
  
  String updateTopicalPO(SenddocumentTopicalPO paramSenddocumentTopicalPO) throws Exception;
  
  Long saveTopicalInfo(SenddocumentTopicalPO paramSenddocumentTopicalPO) throws Exception;
  
  SenddocumentTopicalPO loadSendTopicalPO(String paramString) throws Exception;
  
  String deleteTopicalPO(String paramString) throws Exception;
  
  String deleteUnitPO(String paramString) throws Exception;
  
  SendDocumentUnitPO loadSendUnitPO(String paramString) throws Exception;
  
  String updateUnitPO(SendDocumentUnitPO paramSendDocumentUnitPO) throws Exception;
  
  Long saveUnitInfo(SendDocumentUnitPO paramSendDocumentUnitPO) throws Exception;
  
  List getAllSendDocumentUnitPO(String paramString) throws Exception;
  
  List getAllHaveTemp_Num_Seq_Word(String paramString) throws Exception;
  
  List getWordByProceeIdAnd4Type(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getSendNumByNumClass(String paramString) throws Exception;
  
  String updateNumByPara(String[] paramArrayOfString) throws Exception;
  
  List getCommentByCommFiledList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getEmpIdsByTotal(String paramString) throws Exception;
  
  List getAllFealMenBymoduleId(String paramString1, String paramString2) throws Exception;
  
  List getCanChooseTemplateList(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2, String paramString3) throws Exception;
  
  Integer getCanChooseTemplateListCount(String paramString1, String paramString2, String paramString3) throws Exception;
}
