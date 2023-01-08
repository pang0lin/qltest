package com.js.oa.jsflow.bean;

import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface WorkFlowCommonEJBLocal extends EJBLocalObject {
  List getFlowedActivity(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Map getCommField(String paramString1, String paramString2) throws Exception;
  
  Integer backToSubmitPerson(Map paramMap) throws Exception;
  
  Integer insertDealWith(Map paramMap) throws Exception;
  
  Integer insertTransDealWith(Map paramMap) throws Exception;
  
  Integer backToActivity(Map paramMap) throws Exception;
  
  String getFieldName(String paramString1, String paramString2) throws Exception;
  
  Map getProcessClassMethod(String paramString) throws Exception;
  
  Map getActivityClassMethod(String paramString) throws Exception;
  
  String getNoWriteField(String paramString) throws Exception;
  
  Map getModuleDefaultMethod(String paramString) throws Exception;
  
  String getCurActivityWriteField(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Map getCurActivityCommField(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Map getWorkInfo(String paramString1, String paramString2) throws Exception;
  
  Map getWorkInfo(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer completeWork(String[] paramArrayOfString, String paramString) throws Exception;
  
  Long insertFormRecord(String paramString, List paramList1, List paramList2, List paramList3, List paramList4, List paramList5) throws Exception;
  
  String getModuleId(String paramString) throws Exception;
  
  Integer randomProcess(Map paramMap) throws Exception;
  
  Long insertFormRecord(String paramString, List paramList1, List paramList2, List paramList3, List paramList4, List paramList5, String[] paramArrayOfString) throws Exception;
  
  Long updateFormRecord(String paramString1, List paramList1, List paramList2, String paramString2) throws Exception;
  
  Integer insertPassroundDealWith(Map paramMap) throws Exception;
  
  Map getCurActivityCommField(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  Map getBackToPerson(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String getCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  List getCommentListByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception;
  
  Integer updateCommentRange(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception;
  
  Boolean inCommentRange(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getRelatedProc(String paramString) throws Exception;
  
  Integer transWorkflow(Map paramMap, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception;
  
  Integer transWorkflowButton(Map paramMap, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception;
  
  Integer transWorkflowBranch(Map paramMap, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception;
  
  Integer selfSendWorkflowButton(String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  Integer endWorkflowButton(Map paramMap, String[] paramArrayOfString) throws Exception;
  
  List getStandForUserByProcessAndOrg(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception;
  
  Map getWorkInfoByTableID(String paramString1, String paramString2) throws Exception;
  
  void deleteOldRecord(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void deleteDraftRecord(String paramString) throws Exception;
  
  String[] getWorkParallelInfo(String paramString) throws Exception;
}
