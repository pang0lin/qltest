package com.js.doc.doc.service;

import com.js.doc.doc.bean.SendFileEJBHome;
import com.js.doc.doc.po.GovDocumentSendFilePO;
import com.js.doc.doc.po.SendAssociatePO;
import com.js.doc.doc.po.SendFlowResavePO;
import com.js.doc.doc.po.SenddocumentUpdate;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class SendFileBD {
  private static Logger logger = Logger.getLogger(SendFileBD.class.getName());
  
  public Long save(GovDocumentSendFilePO po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(po, GovDocumentSendFilePO.class);
      result = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public void sendFileBoxDel(String recordId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(recordId, String.class);
      ejbProxy.invoke("sendFileBoxDel", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public Object[] getHeadInfo(String redHeadId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Object[] result = (Object[])null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(redHeadId, String.class);
      result = (Object[])ejbProxy.invoke("getHeadInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getHeadInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public Object[] getSealInfo(String sealId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Object[] result = (Object[])null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sealId, String.class);
      result = (Object[])ejbProxy.invoke("getSealInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSealInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getRedHeadList(String wherePara) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List result = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(wherePara, String.class);
      result = (List)ejbProxy.invoke("getRedHeadList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRedHeadList information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSendFileMaxNumber(String sendFileCode, String sendFileYear, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileCode, String.class);
      pg.put(sendFileYear, String.class);
      pg.put(domainId, String.class);
      result = (String)ejbProxy.invoke("getSendFileMaxNumber", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSendFileMaxNumber information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getSealList(String redHeadId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List result = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(redHeadId, String.class);
      result = (List)ejbProxy.invoke("getSealList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSealList information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long draft(String sendFileId, GovDocumentSendFilePO po) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(po, GovDocumentSendFilePO.class);
      result = (Long)ejbProxy.invoke("draft", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to draft information :" + e.getMessage());
    } 
    return result;
  }
  
  public GovDocumentSendFilePO load(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovDocumentSendFilePO result = new GovDocumentSendFilePO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(editId, String.class);
      result = (GovDocumentSendFilePO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
    } 
    return result;
  }
  
  public GovDocumentSendFilePO get(String editId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    GovDocumentSendFilePO result = new GovDocumentSendFilePO();
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(editId, String.class);
      result = (GovDocumentSendFilePO)ejbProxy.invoke("get", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to get information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSendFileHead(String headId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(headId, String.class);
      result = (String)ejbProxy.invoke("getSendFileHead", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSendFileHead information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long update(String sendFileId, GovDocumentSendFilePO po) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(po, GovDocumentSendFilePO.class);
      result = (Long)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getNewInnerDocumentCount(String userIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(userIds, String.class);
      result = (Map)ejbProxy.invoke("getNewInnerDocumentCount", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNewInnerDocumentCount information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public Integer send(String sendFileId, String userIds, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(3);
    Integer result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(userIds, String.class);
      pg.put(domainId, String.class);
      result = (Integer)ejbProxy.invoke("send", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to send information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer send(String sendFileId, String userIds, String domainId, String userName, String orgName) {
    ParameterGenerator pg = new ParameterGenerator(5);
    Integer result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(userIds, String.class);
      pg.put(domainId, String.class);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      result = (Integer)ejbProxy.invoke("send", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to send information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer sendToMyReceiveCancel(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (Integer)ejbProxy.invoke("sendToMyReceiveCancel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to send information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer setSendFileBrower(String sendFileId, String userId, String userName, String orgName, String domainId) {
    ParameterGenerator pg = new ParameterGenerator(5);
    Integer result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      pg.put(domainId, String.class);
      result = (Integer)ejbProxy.invoke("setSendFileBrower", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setSendFileBrower information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getBrowerUser(String sendFileId, Integer type) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      pg.put(type, Integer.class);
      result = (List)ejbProxy.invoke("getBrowerUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getBrowerUser information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer removeSendUser(String sendUserId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendUserId, String.class);
      result = (Integer)ejbProxy.invoke("removeSendUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to removeSendUser information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer removeSendUser(String sendUserId, String userId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Integer result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendUserId, String.class);
      pg.put(userId, String.class);
      result = (Integer)ejbProxy.invoke("removeOneSendUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to removeSendUser information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getDossierInfo(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (Map)ejbProxy.invoke("getDossierInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDossierInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean isPigeonholed(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Boolean result = Boolean.FALSE;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (Boolean)ejbProxy.invoke("isPigeonholed", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to isPigeonholed information :" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public Integer setPigeonholed(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = Integer.valueOf("-1");
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (Integer)ejbProxy.invoke("setPigeonholed", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setPigeonholed information :" + e.getMessage());
    } 
    return result;
  }
  
  public String delBatch(String ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "";
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(ids, String.class);
      result = (String)ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delBatch information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getDocWF(String id, String moduleId) {
    ParameterGenerator pg = new ParameterGenerator(2);
    Map result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(id, String.class);
      pg.put(moduleId, String.class);
      result = (Map)ejbProxy.invoke("getDocWF", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDocWF information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer delete(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Integer result = Integer.valueOf("-1");
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (Integer)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSendMsgReceiver(String userIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(userIds, String.class);
      result = (String)ejbProxy.invoke("getSendMsgReceiver", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSendMsgReceiver information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long completeSendFile(String sendFileId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = (Long)ejbProxy.invoke("completeSendFile", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to completeSendFile information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public HashMap getUserOrg(String empId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    HashMap result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(empId, String.class);
      result = (HashMap)ejbProxy.invoke("getUserOrg", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserOrg information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSendRtxReceiver(String userIds) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(userIds, String.class);
      result = (String)ejbProxy.invoke("getSendRtxReceiver", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSendRtxReceiver information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveResave(SendFlowResavePO po) {
    Long result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(po, SendFlowResavePO.class);
      result = (Long)ejbProxy.invoke("saveResave", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveResave  :" + e.getMessage());
    } 
    return result;
  }
  
  public Long updateResave(SendFlowResavePO po) {
    Long result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(po, SendFlowResavePO.class);
      result = (Long)ejbProxy.invoke("updateResave", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateResave  :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleterResave(String id) {
    String result = "1";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(id, String.class);
      result = (String)ejbProxy.invoke("deleterResave", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleterResave  :" + e.getMessage());
    } 
    return result;
  }
  
  public List getAllResavePoByEmpIdType(String empId, String type, String sendId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(empId, String.class);
      pg.put(type, String.class);
      pg.put(sendId, String.class);
      list = (List)ejbProxy.invoke("getAllResavePoByEmpIdType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllResavePoByEmpIdType  :" + e.getMessage());
    } 
    return list;
  }
  
  public String deleterResaveByTwo(String sendId, String type) {
    String result = "1";
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendId, String.class);
      pg.put(type, String.class);
      result = (String)ejbProxy.invoke("deleterResaveByTwo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleterResaveByTwo  :" + e.getMessage());
    } 
    return result;
  }
  
  public String monitorRedo(String recordId, String tableId, String processId, String workId, String title, String cancelReason) {
    String result = "1";
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      pg.put(processId, String.class);
      pg.put(workId, String.class);
      pg.put(title, String.class);
      pg.put(cancelReason, String.class);
      result = (String)ejbProxy.invoke("monitorRedo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to monitorRedo  :" + e.getMessage());
    } 
    return result;
  }
  
  public Long saveSendAssociate(SendAssociatePO po) {
    Long result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(po, SendAssociatePO.class);
      result = (Long)ejbProxy.invoke("saveSendAssociate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveSendAssociate  :" + e.getMessage());
    } 
    return result;
  }
  
  public int getSendAssociateNum(String sendFileId) {
    int result = 0;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      result = ((Integer)ejbProxy.invoke("getSendAssociateNum", 
          pg.getParameters())).intValue();
    } catch (Exception ex) {
      System.out.println("Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getSendAssociateList(String sendFileId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      list = (List)ejbProxy.invoke("getSendAssociateList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSendAssociateList  :" + e.getMessage());
    } 
    return list;
  }
  
  public int getOutSeeNum(String userId) {
    int result = 0;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(userId, String.class);
      result = ((Integer)ejbProxy.invoke("getOutSeeNum", 
          pg.getParameters())).intValue();
    } catch (Exception ex) {
      System.out.println("Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public Long saveSendUpdate(SenddocumentUpdate po) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Long result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(po, SenddocumentUpdate.class);
      result = (Long)ejbProxy.invoke("saveSendUpdate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveSendUpdate  :" + e.getMessage());
    } 
    return result;
  }
  
  public List getAllSendDocumentUpdatePO(String sendFileId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(sendFileId, String.class);
      list = (List)ejbProxy.invoke("getAllSendDocumentUpdatePO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllSendDocumentUpdatePO  :" + e.getMessage());
    } 
    return list;
  }
  
  public List getWfTableInfoByTableId(String tableId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(tableId, String.class);
      list = (List)ejbProxy.invoke("getWfTableInfoByTableId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWfTableInfoByTableId  :" + e.getMessage());
    } 
    return list;
  }
  
  public List getCommentByCommFiledList(String moduleId, String recordId, String commField) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(moduleId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      list = (List)ejbProxy.invoke("getCommentByCommFiledList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommFiledList  :" + e.getMessage());
    } 
    return list;
  }
  
  public String getCommentByCommFiled(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) {
    String result = "1";
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(isEdit, String.class);
      result = (String)ejbProxy.invoke("getCommentByCommFiled", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommFiled  :" + e.getMessage());
    } 
    return result;
  }
  
  public String updateWorkTitle(String processId, String recordId, String tableId, String workTitle) {
    String result = "1";
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("SendFileEJB", 
          "SendFileEJBLocal", SendFileEJBHome.class);
      pg.put(processId, String.class);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      pg.put(workTitle, String.class);
      result = (String)ejbProxy.invoke("updateWorkTitle", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to updateWorkTitle  :" + e.getMessage());
    } 
    return result;
  }
}
