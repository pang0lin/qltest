package com.js.oa.jsflow.bean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface WorkFlowCommonEJB extends EJBObject {
  List getFlowedActivity(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Map getCommField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer backToSubmitPerson(Map paramMap) throws Exception, RemoteException;
  
  Integer insertDealWith(Map paramMap) throws Exception, RemoteException;
  
  Integer insertTransDealWith(Map paramMap) throws Exception, RemoteException;
  
  Integer backToActivity(Map paramMap) throws Exception, RemoteException;
  
  String getFieldName(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map getProcessClassMethod(String paramString) throws Exception, RemoteException;
  
  Map getActivityClassMethod(String paramString) throws Exception, RemoteException;
  
  String getNoWriteField(String paramString) throws Exception, RemoteException;
  
  Map getModuleDefaultMethod(String paramString) throws Exception, RemoteException;
  
  String getCurActivityWriteField(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Map getCurActivityCommField(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Map getWorkInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map getWorkInfo(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer completeWork(String[] paramArrayOfString, String paramString) throws Exception, RemoteException;
  
  Long insertFormRecord(String paramString, List paramList1, List paramList2, List paramList3, List paramList4, List paramList5) throws Exception, RemoteException;
  
  String getModuleId(String paramString) throws Exception, RemoteException;
  
  Integer randomProcess(Map paramMap) throws Exception, RemoteException;
  
  Long insertFormRecord(String paramString, List paramList1, List paramList2, List paramList3, List paramList4, List paramList5, String[] paramArrayOfString) throws Exception, RemoteException;
  
  Long updateFormRecord(String paramString1, List paramList1, List paramList2, String paramString2) throws Exception, RemoteException;
  
  Integer insertPassroundDealWith(Map paramMap) throws Exception, RemoteException;
  
  Map getCurActivityCommField(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  Map getBackToPerson(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  String getCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  List getCommentListByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  Integer updateCommentRange(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  Boolean inCommentRange(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getRelatedProc(String paramString) throws Exception, RemoteException;
  
  Integer transWorkflow(Map paramMap, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  Integer transWorkflowButton(Map paramMap, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  Integer transWorkflowBranch(Map paramMap, String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  Integer selfSendWorkflowButton(String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  Integer endWorkflowButton(Map paramMap, String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getStandForUserByProcessAndOrg(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception, RemoteException;
  
  Map getWorkInfoByTableID(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void deleteOldRecord(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void deleteDraftRecord(String paramString) throws Exception;
  
  String[] getWorkParallelInfo(String paramString) throws Exception;
}
