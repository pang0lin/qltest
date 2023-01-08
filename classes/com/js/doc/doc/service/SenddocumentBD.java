package com.js.doc.doc.service;

import com.js.doc.doc.bean.SenddocumentEJBHome;
import com.js.doc.doc.po.BaseInfoPO;
import com.js.doc.doc.po.SendDocumentNumPO;
import com.js.doc.doc.po.SendDocumentSeqPO;
import com.js.doc.doc.po.SendDocumentUnitPO;
import com.js.doc.doc.po.SendDocumentWordPO;
import com.js.doc.doc.po.SenddocumentTopicalPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class SenddocumentBD {
  private static Logger logger = Logger.getLogger(SenddocumentBD.class.getName());
  
  private Integer volume = new Integer(15);
  
  private int recordCount = 0;
  
  public int getVolume() {
    return this.volume.intValue();
  }
  
  public void setVolume(int volume) {
    this.volume = new Integer(volume);
  }
  
  public int getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(int value) {
    this.recordCount = value;
  }
  
  public Object[] getSenddocumentBaseInfo() {
    ParameterGenerator pg = new ParameterGenerator(1);
    Object[] result = (Object[])null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      result = (Object[])ejbProxy.invoke("getSenddocumentBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSenddocumentBaseInfo:" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveBaseInfo(BaseInfoPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, BaseInfoPO.class);
      result = (Long)ejbProxy.invoke("saveBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save saveBaseInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateBaseInfo(BaseInfoPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, BaseInfoPO.class);
      result = (String)ejbProxy.invoke("updateBaseInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update updateBaseInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveWordInfo(SendDocumentWordPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SendDocumentWordPO.class);
      result = (Long)ejbProxy.invoke("saveWordInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save saveWordInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public SendDocumentWordPO loadSendDocumentWordPO(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    SendDocumentWordPO result = new SendDocumentWordPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(editId, String.class);
      result = (SendDocumentWordPO)ejbProxy.invoke("loadSendDocumentWordPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadSendDocumentWordPO information :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateWordPO(String sendFileId, SendDocumentWordPO po) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(po, SendDocumentWordPO.class);
      result = (String)ejbProxy.invoke("updateWordPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update updateWordPO :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleteWordPO(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (String)ejbProxy.invoke("deleteWordPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteWordPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public List getNoNumWord(String corpId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(corpId, String.class);
      list = (List)ejbProxy.invoke("getNoNumWord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoNumWord  :" + e.getMessage());
    } 
    return list;
  }
  
  public String setWordPoNum(String wordId, String numId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(wordId, String.class);
      pg.put(numId, String.class);
      result = (String)ejbProxy.invoke("setWordPoNum", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  setWordPoNum :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveNunInfo(SendDocumentNumPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SendDocumentNumPO.class);
      result = (Long)ejbProxy.invoke("saveNunInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save saveNunInfo :" + e.getMessage());
    } 
    return result;
  }
  
  public SendDocumentNumPO loadSendDocumentNumPO(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    SendDocumentNumPO result = new SendDocumentNumPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(editId, String.class);
      result = (SendDocumentNumPO)ejbProxy.invoke("loadSendDocumentNumPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadSendDocumentWordPO information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getwordBynumId(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List result = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(id, String.class);
      result = (List)ejbProxy.invoke("getwordBynumId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getwordBynumId  :" + e.getMessage());
    } 
    return result;
  }
  
  public String removeWordPoNum(String fileWordIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(fileWordIds, String.class);
      result = (String)ejbProxy.invoke("removeWordPoNum", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  removeWordPoNum :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateNumPO(String sendFileId, SendDocumentNumPO po) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(po, SendDocumentNumPO.class);
      result = (String)ejbProxy.invoke("updateNumPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update updateNumPO :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleteNumPO(String deleteIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(deleteIds, String.class);
      result = (String)ejbProxy.invoke("deleteNumPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteWordPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public Long SaveSeqPO(SendDocumentSeqPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SendDocumentSeqPO.class);
      result = (Long)ejbProxy.invoke("SaveSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save SaveSeqPO :" + e.getMessage());
    } 
    return result;
  }
  
  public String setWordPoSeq(String wordId, String seqId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(wordId, String.class);
      pg.put(seqId, String.class);
      result = (String)ejbProxy.invoke("setWordPoSeq", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  setWordPoSeq :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateSeqPO(String seqId, SendDocumentSeqPO po) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(seqId, String.class);
      pg.put(po, SendDocumentSeqPO.class);
      result = (String)ejbProxy.invoke("updateSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update updateSeqPO :" + e.getMessage());
    } 
    return result;
  }
  
  public String removeWordPoSeq(String fileWordIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(fileWordIds, String.class);
      result = (String)ejbProxy.invoke("removeWordPoSeq", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  removeWordPoSeq :" + e.getMessage());
    } 
    return result;
  }
  
  public SendDocumentSeqPO loadSendDocumentSeqPO(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    SendDocumentSeqPO result = new SendDocumentSeqPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(editId, String.class);
      result = (SendDocumentSeqPO)ejbProxy.invoke("loadSendDocumentSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadSendDocumentSeqPO information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getwordBySeqId(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List result = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(id, String.class);
      result = (List)ejbProxy.invoke("getwordBySeqId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getwordBySeqId  :" + e.getMessage());
    } 
    return result;
  }
  
  public List getNoSeqWord(String corpId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(corpId, String.class);
      list = (List)ejbProxy.invoke("getNoSeqWord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoSeqWord  :" + e.getMessage());
    } 
    return list;
  }
  
  public String deleteSeqPO(String deleteIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(deleteIds, String.class);
      result = (String)ejbProxy.invoke("deleteSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteSeqPO  :" + e.getMessage());
    } 
    return result;
  }
  
  public List getAllSendDocumentNumPO() {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      list = (List)ejbProxy.invoke("getAllSendDocumentNumPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllSendDocumentNumPO  :" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllSendDocumentSeqPO() {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      list = (List)ejbProxy.invoke("getAllSendDocumentSeqPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllSendDocumentSeqPO  :" + e.getMessage());
    } 
    return list;
  }
  
  public List getTemplateList(String domainId, String useTempIds) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(domainId, String.class);
      pg.put(useTempIds, String.class);
      list = (List)ejbProxy.invoke("getTemplateList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getTemplateList  :" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllUserTemplateList() {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      list = (List)ejbProxy.invoke("getAllUserTemplateList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllUserTemplateList  :" + e.getMessage());
    } 
    return list;
  }
  
  public List getTemplateById(String domainId, String Id) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(domainId, String.class);
      pg.put(Id, String.class);
      list = (List)ejbProxy.invoke("getTemplateById", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getTemplateById  :" + e.getMessage());
    } 
    return list;
  }
  
  public String getNunfigByNumId(String numId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(numId, String.class);
      result = (String)ejbProxy.invoke("getNunfigByNumId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNunfigByNumId  :" + e.getMessage());
    } 
    return result;
  }
  
  public String setNumfigById(String numId, String numfig) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(numId, String.class);
      pg.put(numfig, String.class);
      result = (String)ejbProxy.invoke("setNumfigById", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setNumfigById  :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSeqfigBySeqID(String seqId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(seqId, String.class);
      result = (String)ejbProxy.invoke("getSeqfigBySeqID", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSeqfigBySeqID  :" + e.getMessage());
    } 
    return result;
  }
  
  public String setSeqfigBySeqId(String seqId, String seqfig) {
    ParameterGenerator pg = new ParameterGenerator(2);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(seqId, String.class);
      pg.put(seqfig, String.class);
      result = (String)ejbProxy.invoke("setSeqfigBySeqId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setSeqfigBySeqId  :" + e.getMessage());
    } 
    return result;
  }
  
  public List getAllHaveTmep_Word() {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      list = (List)ejbProxy.invoke("getAllHaveTmep_Word", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllHaveTmep_Word  :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getAllHaveTemp_Num_Seq_Word() {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      list = (List)ejbProxy.invoke("getAllHaveTemp_Num_Seq_Word", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllHaveTmep_Word  :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public Long saveTopicalInfo(SenddocumentTopicalPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SenddocumentTopicalPO.class);
      result = (Long)ejbProxy.invoke("saveTopicalInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save saveTopicalInfo :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String updateTopicalPO(SenddocumentTopicalPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SenddocumentTopicalPO.class);
      result = (String)ejbProxy.invoke("updateTopicalPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error(" updateTopicalPO :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public SenddocumentTopicalPO loadSendTopicalPO(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    SenddocumentTopicalPO result = new SenddocumentTopicalPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(editId, String.class);
      result = (SenddocumentTopicalPO)ejbProxy.invoke("loadSendTopicalPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadSendTopicalPO :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String deleteTopicalPO(String deleteIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(deleteIds, String.class);
      result = (String)ejbProxy.invoke("deleteTopicalPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteTopicalPO  :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Long saveUnitInfo(SendDocumentUnitPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SendDocumentUnitPO.class);
      result = (Long)ejbProxy.invoke("saveUnitInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save saveUnitInfo :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String updateUnitPO(SendDocumentUnitPO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(po, SendDocumentUnitPO.class);
      result = (String)ejbProxy.invoke("updateUnitPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error(" updateUnitPO :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public SendDocumentUnitPO loadSendUnitPO(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    SendDocumentUnitPO result = new SendDocumentUnitPO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(editId, String.class);
      result = (SendDocumentUnitPO)ejbProxy.invoke("loadSendUnitPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadSendUnitPO :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public String deleteUnitPO(String deleteIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "-1";
    try {
      EJBProxy ejbProxy = new EJBProxy("SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(deleteIds, String.class);
      result = (String)ejbProxy.invoke("deleteUnitPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteUnitPO  :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public List getAllSendDocumentUnitPO() {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put("1", String.class);
      list = (List)ejbProxy.invoke("getAllSendDocumentUnitPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllSendDocumentUnitPO  :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getWordByProceeIdAnd4Type(String proceedId, String type, String whereSql) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(proceedId, String.class);
      pg.put(type, String.class);
      pg.put(whereSql, String.class);
      list = (List)ejbProxy.invoke("getWordByProceeIdAnd4Type", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWordByProceeIdAnd4Type  :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getSendNumByNumClass(String numClass) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(numClass, String.class);
      list = (List)ejbProxy.invoke("getSendNumByNumClass", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSendNumByNumClass  :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public String updateNumByPara(String[] para) {
    String dd = "0";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(para, String[].class);
      dd = (String)ejbProxy.invoke("updateNumByPara", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateNumByPara  :" + e.getMessage());
      e.printStackTrace();
    } 
    return dd;
  }
  
  public List getCommentByCommFiledList(String moduleId, String recordId, String commField) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(moduleId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      list = (List)ejbProxy.invoke("getCommentByCommFiledList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommFiledList  :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getEmpIdsByTotal(String userIds) {
    String empIds = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(userIds, String.class);
      empIds = (String)ejbProxy.invoke("getEmpIdsByTotal", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommFiledList  :" + e.getMessage());
      e.printStackTrace();
    } 
    return empIds;
  }
  
  public List getAllFealMenBymoduleId(String moduleId, String recordId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(moduleId, String.class);
      pg.put(recordId, String.class);
      list = (List)ejbProxy.invoke("getAllFealMenBymoduleId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllFealMenBymoduleId :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getCommentByCommFiledListForText(String moduleId, String recordId, String commField) {
    List<Object[]> list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    String dd = "";
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(moduleId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      list = (List)ejbProxy.invoke("getCommentByCommFiledList", pg.getParameters());
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          String setMen = "";
          String setTime = "";
          String comment = "";
          Object[] obj = list.get(i);
          Object object = obj[0];
          if (obj[1] != null && !obj[1].toString().equals("")) {
            setMen = String.valueOf(setMen) + obj[1] + "    ";
            Object object1 = obj[2];
            if (object1.indexOf(".") > 0)
              object1 = object1.substring(0, object1.indexOf(".")); 
            setTime = String.valueOf(setTime) + object1;
          } else if (obj[3] != null && !obj[3].toString().equals("")) {
            setMen = String.valueOf(setMen) + obj[4] + "    ";
            Object object1 = obj[2];
            if (object1.indexOf(".") > 0)
              object1 = object1.substring(0, object1.indexOf(".")); 
            setTime = String.valueOf(setTime) + object1;
          } 
          dd = String.valueOf(dd) + object + " \n " + "                        " + setMen + setTime + " \n";
        }  
    } catch (Exception e) {
      logger.error("error to getCommentByCommFiledList  :" + e.getMessage());
      e.printStackTrace();
    } 
    return dd;
  }
  
  public List getCanChooseTemplateList(String sqlStr, String fileName, Integer cruPage, Integer pageSize, String domainId) {
    List list = new ArrayList();
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy(
          "SenddocumentEJB", 
          "SenddocumentEJBLocal", SenddocumentEJBHome.class);
      pg.put(sqlStr, String.class);
      pg.put(fileName, String.class);
      pg.put(cruPage, Integer.class);
      pg.put(pageSize, Integer.class);
      pg.put(domainId, String.class);
      list = (List)ejbProxy.invoke("getCanChooseTemplateList", pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(sqlStr, String.class);
      pg.put(fileName, String.class);
      pg.put(domainId, String.class);
      ret = (Integer)ejbProxy.invoke("getCanChooseTemplateListCount", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllFealMenBymoduleId :" + e.getMessage());
      e.printStackTrace();
    } finally {}
    setRecordCount(ret.intValue());
    return list;
  }
}
