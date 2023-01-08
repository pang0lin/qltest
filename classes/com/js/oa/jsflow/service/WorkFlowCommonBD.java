package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WorkFlowCommonEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class WorkFlowCommonBD {
  private static Logger logger = Logger.getLogger(WorkFlowBD.class.getName());
  
  public List getFlowedActivity(String tableId, String recordId, String stepCount) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(stepCount, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getFlowedActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFlowedActivity information :" + e.getMessage());
    } 
    return alist;
  }
  
  public Map getCommField(String tableId, String recordId) {
    Map<Object, Object> commFieldMap = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      commFieldMap = (Map<Object, Object>)ejbProxy.invoke("getCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommField information :" + e.getMessage());
    } 
    return commFieldMap;
  }
  
  public Integer backToSubmitPerson(Map parameter) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(parameter, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("backToSubmitPerson", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to backToSubmitPerson information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer insertDealWith(Map para) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("insertDealWith", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertDealWith information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer insertTransDealWith(Map para) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("insertTransDealWith", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertTransDealWith information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer backToActivity(Map parameter) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(parameter, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("backToActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to backToActivity information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getFieldName(String fieldId, String moduleId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(fieldId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (String)ejbProxy.invoke("getFieldName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFieldName information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getProcessClassMethod(String processId) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map<Object, Object>)ejbProxy.invoke("getProcessClassMethod", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcessClassMethod information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getActivityClassMethod(String activityId) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map<Object, Object>)ejbProxy.invoke("getActivityClassMethod", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivityClassMethod information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getNoWriteField(String processId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (String)ejbProxy.invoke("getNoWriteField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoWriteField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getModuleDefaultMethod(String moduleId) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map<Object, Object>)ejbProxy.invoke("getModuleDefaultMethod", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModuleDefaultMethod information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getCurActivityWriteField(String activityId, String tableId, String recordId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (String)ejbProxy.invoke("getCurActivityWriteField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCurActivityWriteField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getCurActivityCommField(String activityId, String tableId, String recordId) {
    Map result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map)ejbProxy.invoke("getCurActivityCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCurActivityCommField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getCurActivityCommField(String activityId, String tableId, String recordId, String workId) {
    Map result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map)ejbProxy.invoke("getCurActivityCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCurActivityCommField information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (String)ejbProxy.invoke("getCommentByCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getWorkInfo(String moduleId, String recordId) {
    Map result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(moduleId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map)ejbProxy.invoke("getWorkInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getWorkInfo(String moduleId, String recordId, String formName) {
    Map result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(moduleId, String.class);
      pg.put(recordId, String.class);
      pg.put(formName, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map)ejbProxy.invoke("getWorkInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer completeWork(String[] para, String workId) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("completeWork", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public Long insertFormRecord(String tableName, List field, List fieldValue, List childTableList, List childFieldList, List childFieldValueList) {
    Long result = Long.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(tableName, String.class);
      pg.put(field, List.class);
      pg.put(fieldValue, List.class);
      pg.put(childTableList, List.class);
      pg.put(childFieldList, List.class);
      pg.put(childFieldValueList, List.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Long)ejbProxy.invoke("insertFormRecord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertFormRecord information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getModuleId(String immoFormId) {
    String result = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(immoFormId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (String)ejbProxy.invoke("getModuleId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModuleId information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer randomProcess(Map randomProcMap) {
    Integer result = Integer.valueOf("-1");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(randomProcMap, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("randomProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to randomProcess information :" + e.getMessage());
    } 
    return result;
  }
  
  public Long insertFormRecord(String tableName, List field, List fieldValue, List childTableList, List childFieldList, List childFieldValueList, String[] fieldType) {
    Long result = Long.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(tableName, String.class);
      pg.put(field, List.class);
      pg.put(fieldValue, List.class);
      pg.put(childTableList, List.class);
      pg.put(childFieldList, List.class);
      pg.put(childFieldValueList, List.class);
      pg.put(fieldType, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Long)ejbProxy.invoke("insertFormRecord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertFormRecord information :" + e.getMessage());
    } 
    return result;
  }
  
  public void updateFormRecord(Long recordId, String tableName, Long oldRecordId, String[] tables, String tableId) {
    Long result = Long.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(recordId, Long.class);
      pg.put(tableName, String.class);
      pg.put(oldRecordId, Long.class);
      pg.put(tables, String[].class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      ejbProxy.invoke("updateFormRecord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertFormRecord information :" + e.getMessage());
    } 
  }
  
  public Long updateFormRecord(String tableName, List field, List fieldValue, String recordId) {
    Long result = Long.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableName, String.class);
      pg.put(field, List.class);
      pg.put(fieldValue, List.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Long)ejbProxy.invoke("updateFormRecord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateFormRecord information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer insertPassroundDealWith(Map para) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("insertPassroundDealWith", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertDealWith information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getBackToPerson(String tableId, String recordId, String stepCount, String initStepCount, String workId) {
    Map result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(stepCount, String.class);
      pg.put(initStepCount, String.class);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map)ejbProxy.invoke("getBackToPerson", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getBackToPerson information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(isEdit, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (String)ejbProxy.invoke("getCommentByCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer updateCommentRange(String[] commentId, String[] rangeName, String[] rangeId) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(commentId, String[].class);
      pg.put(rangeName, String[].class);
      pg.put(rangeId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateCommentRange", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Boolean inCommentRange(String userId, String orgIdString, String commentRange) {
    Boolean result = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(commentRange, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Boolean)ejbProxy.invoke("inCommentRange", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to inCommentRange information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getRelatedProc(String workId) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (List)ejbProxy.invoke("getRelatedProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRelatedProc information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Integer transWorkflow(Map dealwithPara, String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(dealwithPara, Map.class);
      pg.put(para, String[].class);
      pg.put(transactUser, String[].class);
      pg.put(needPassRound, String.class);
      pg.put(passRoundUser, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("transWorkflow", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      logger.error("error to transWorkflow information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Integer transWorkflowButton(Map dealwithPara, String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(dealwithPara, Map.class);
      pg.put(para, String[].class);
      pg.put(transactUser, String[].class);
      pg.put(needPassRound, String.class);
      pg.put(passRoundUser, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("transWorkflowButton", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      logger.error("error to transWorkflowButton information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Integer updateToDraftButton(Map dealwithPara) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(dealwithPara, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateToDraftButton", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      logger.error("error to transWorkflowButton information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Integer transWorkflowBranch(Map dealwithPara, String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(dealwithPara, Map.class);
      pg.put(para, String[].class);
      pg.put(transactUser, String[].class);
      pg.put(needPassRound, String.class);
      pg.put(passRoundUser, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("transWorkflowBranch", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      logger.error("error to transWorkflowBranch information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public Integer endWorkflowButton(Map dealwithPara, String[] para) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(dealwithPara, Map.class);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("endWorkflowButton", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      logger.error("error to endWorkflowButton information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer selfSendWorkflowButton(String[] para, String[] passRoundUser) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(passRoundUser, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Integer)ejbProxy.invoke("selfSendWorkflowButton", pg.getParameters());
    } catch (Exception e) {
      result = Integer.valueOf("-1");
      logger.error("error to selfSendWorkflowButton information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getStandForUserByProcessAndOrg(String[] userId, String processId, String orgIdString) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String[].class);
      pg.put(processId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      list = (List)ejbProxy.invoke("getStandForUserByProcessAndOrg", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getStandForUserByProcessAndOrg information :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getCommentListByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(isEdit, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (List)ejbProxy.invoke("getCommentListByCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentListByCommField information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map getWorkInfoByTableID(String recordId, String tableId) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      result = (Map<Object, Object>)ejbProxy.invoke("getWorkInfoByTableID", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkInfoByTableID information :" + e.getMessage());
    } 
    return result;
  }
  
  public void deleteOldRecord(String tableId, String tableName, String recordId) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(tableName, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      ejbProxy.invoke("deleteOldRecord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteOldRecord information :" + e.getMessage());
    } 
  }
  
  public void deleteDraftRecord(String draftIds) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(draftIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      ejbProxy.invoke("deleteDraftRecord", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteOldRecord information :" + e.getMessage());
    } 
  }
  
  public String[] getWorkParallelInfo(String workId) {
    String[] info = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowCommonEJB", "WorkFlowCommonEJBLocal", WorkFlowCommonEJBHome.class);
      info = (String[])ejbProxy.invoke("getWorkParallelInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteOldRecord information :" + e.getMessage());
    } 
    return info;
  }
}
