package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.WorkVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;
import javax.ejb.EJBObject;

public interface WorkFlowEJB extends EJBObject {
  ModuleVO getModule(Integer paramInteger) throws Exception, RemoteException;
  
  List getAccessTable(ModuleVO paramModuleVO) throws Exception, RemoteException;
  
  List getSimpleField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Long insertTable(String paramString1, String paramString2, List paramList, String paramString3) throws Exception, RemoteException;
  
  String copyProcInfo(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getActivityURL(String paramString) throws Exception, RemoteException;
  
  String getLeader(String paramString) throws Exception, RemoteException;
  
  String[] getPress(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getAllNextActivity(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getFirstAllNextActivity(String paramString) throws Exception;
  
  String getImmoPO(String paramString) throws Exception, RemoteException;
  
  List getRWList(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getProtectList(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getCommField(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getAllCommField(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getActiviyCommList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void insertDealwithWithDate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13) throws Exception, RemoteException;
  
  Long setSurveillance(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  List getOffiDict(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getStandForUser(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void insertDealWith(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void operateWork(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception, RemoteException;
  
  String completeWork(String[] paramArrayOfString, String paramString) throws Exception, RemoteException;
  
  void insertPassRoundDeal(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void operPassRoundWork(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void transitionWork(String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  List getOrg(String paramString) throws Exception, RemoteException;
  
  List getRole(String paramString) throws Exception, RemoteException;
  
  List getRoleUser(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[] getActivityClass(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Long setSurveillance2(WorkVO paramWorkVO, String paramString) throws Exception, RemoteException;
  
  void operateWorkDoc(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String[] paramArrayOfString3, String paramString2) throws Exception, RemoteException;
  
  void completeWorkDoc(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception, RemoteException;
  
  void operPassRoundWorkDoc(String[] paramArrayOfString, String paramString) throws Exception, RemoteException;
  
  List getFirstNextActi(String paramString) throws Exception, RemoteException;
  
  ActivityVO getFirstProcActiVO(String paramString) throws Exception, RemoteException;
  
  ActivityVO getProceedActiVO(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getWorkUserLogin(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getSingleModuleProcess(String paramString) throws Exception, RemoteException;
  
  List getOperUserOrg(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getActiUserByActiName(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getLeaderList(String paramString) throws Exception, RemoteException;
  
  List getLeaderListByOrgId(String paramString) throws Exception, RemoteException;
  
  String getForceCancel(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer forceCancel(String[] paramArrayOfString) throws Exception, RemoteException;
  
  Integer forceDel(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void updateTable(List paramList) throws Exception, RemoteException;
  
  void workCancel(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String getTableName(String paramString) throws Exception, RemoteException;
  
  int insertFormRecord(String paramString1, String paramString2, String paramString3, List paramList1, List paramList2, List paramList3) throws Exception, RemoteException;
  
  void updateChildTable(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getAFieldValue(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String[] getPress(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getDealWithComment(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getDealWithCommentNotBack(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getAllowTransition(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getIdValue(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getTransactType(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void newRandomWork(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getActivityType(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getFormFields(String paramString) throws Exception, RemoteException;
  
  List getChildField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getFieldAndValue(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getChildTableValue(String paramString1, List paramList, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getDealProc(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getNextStepUserName(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getNextActivity(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getFieldName(String paramString) throws Exception, RemoteException;
  
  List getModuleProc(String paramString) throws Exception, RemoteException;
  
  String getWriteControl(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getCancelReason(String paramString) throws Exception, RemoteException;
  
  String[] getCurCompleteWork(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String insertCurFieldStr(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getNextStepPassRoundUserName(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getRoleUserIDAndName(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean hasPrintRight(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getLeaderListByWorkID(String paramString) throws Exception, RemoteException;
  
  List getSimpleField(String paramString1, String paramString2, Set paramSet) throws Exception, RemoteException;
  
  List getNotProtectField(String paramString1, String paramString2, Set paramSet) throws Exception, RemoteException;
  
  Long insertWorkAndSendMessage(String paramString1, String paramString2, List paramList1, List paramList2) throws Exception, RemoteException;
  
  String[] getProcActiUser(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getCandidate(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getNextActivityClass(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getNextDefaultActivityId(String paramString) throws Exception;
  
  List getCandidateSeq(String paramString1, String paramString2) throws Exception;
  
  List getRelationSimpleField(String paramString1, String paramString2) throws Exception;
  
  List getSimpleFieldByOrder(String paramString1, String paramString2) throws Exception;
  
  String getWorkStatusByWorkId(String paramString) throws Exception;
  
  List getPositionUserIDAndName(String paramString1, String paramString2) throws Exception;
  
  String getHangup(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getDealWithCommentFordoc(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getDealWithCommentNotBackFordoc(String paramString1, String paramString2, String paramString3) throws Exception;
}
