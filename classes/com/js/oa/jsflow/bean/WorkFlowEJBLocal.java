package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.WorkVO;
import java.util.List;
import java.util.Set;
import javax.ejb.EJBLocalObject;

public interface WorkFlowEJBLocal extends EJBLocalObject {
  ModuleVO getModule(Integer paramInteger) throws Exception;
  
  List getAccessTable(ModuleVO paramModuleVO) throws Exception;
  
  List getSimpleField(String paramString1, String paramString2) throws Exception;
  
  Long insertTable(String paramString1, String paramString2, List paramList, String paramString3) throws Exception;
  
  String copyProcInfo(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getActivityURL(String paramString) throws Exception;
  
  String getLeader(String paramString) throws Exception;
  
  String[] getPress(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getAllNextActivity(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getFirstAllNextActivity(String paramString) throws Exception;
  
  String getImmoPO(String paramString) throws Exception;
  
  List getRWList(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getProtectList(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getCommField(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getAllCommField(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getActiviyCommList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void insertDealwithWithDate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13) throws Exception;
  
  Long setSurveillance(WorkVO paramWorkVO) throws Exception;
  
  List getOffiDict(String paramString1, String paramString2) throws Exception;
  
  List getStandForUser(String[] paramArrayOfString) throws Exception;
  
  void insertDealWith(String[] paramArrayOfString) throws Exception;
  
  void operateWork(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString, String[] paramArrayOfString3) throws Exception;
  
  String completeWork(String[] paramArrayOfString, String paramString) throws Exception;
  
  void insertPassRoundDeal(String[] paramArrayOfString) throws Exception;
  
  void operPassRoundWork(String[] paramArrayOfString) throws Exception;
  
  void transitionWork(String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  List getOrg(String paramString) throws Exception;
  
  List getRole(String paramString) throws Exception;
  
  List getRoleUser(String paramString1, String paramString2) throws Exception;
  
  String[] getActivityClass(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Long setSurveillance2(WorkVO paramWorkVO, String paramString) throws Exception;
  
  void operateWorkDoc(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString1, String[] paramArrayOfString3, String paramString2) throws Exception;
  
  void completeWorkDoc(String[] paramArrayOfString, String paramString1, String paramString2) throws Exception;
  
  void operPassRoundWorkDoc(String[] paramArrayOfString, String paramString) throws Exception;
  
  List getFirstNextActi(String paramString) throws Exception;
  
  ActivityVO getFirstProcActiVO(String paramString) throws Exception;
  
  ActivityVO getProceedActiVO(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getWorkUserLogin(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getSingleModuleProcess(String paramString) throws Exception;
  
  List getOperUserOrg(String paramString1, String paramString2) throws Exception;
  
  List getActiUserByActiName(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getLeaderList(String paramString) throws Exception;
  
  List getLeaderListByOrgId(String paramString) throws Exception;
  
  String getForceCancel(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer forceCancel(String[] paramArrayOfString) throws Exception;
  
  Integer forceDel(String[] paramArrayOfString) throws Exception;
  
  void updateTable(List paramList) throws Exception;
  
  void workCancel(String[] paramArrayOfString) throws Exception;
  
  String getTableName(String paramString) throws Exception;
  
  int insertFormRecord(String paramString1, String paramString2, String paramString3, List paramList1, List paramList2, List paramList3) throws Exception;
  
  void updateChildTable(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getAFieldValue(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String[] getPress(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getDealWithComment(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getDealWithCommentNotBack(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getAllowTransition(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getIdValue(String paramString1, String paramString2) throws Exception;
  
  String getTransactType(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void newRandomWork(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getActivityType(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getFormFields(String paramString) throws Exception;
  
  List getChildField(String paramString1, String paramString2) throws Exception;
  
  List getFieldAndValue(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getChildTableValue(String paramString1, List paramList, String paramString2, String paramString3) throws Exception;
  
  List getDealProc(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getNextStepUserName(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getNextActivity(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getFieldName(String paramString) throws Exception;
  
  List getModuleProc(String paramString) throws Exception;
  
  String getWriteControl(String paramString1, String paramString2) throws Exception;
  
  String getCancelReason(String paramString) throws Exception;
  
  String[] getCurCompleteWork(String paramString1, String paramString2) throws Exception;
  
  String insertCurFieldStr(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getNextStepPassRoundUserName(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getRoleUserIDAndName(String paramString1, String paramString2) throws Exception;
  
  Boolean hasPrintRight(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getLeaderListByWorkID(String paramString) throws Exception;
  
  List getSimpleField(String paramString1, String paramString2, Set paramSet) throws Exception;
  
  List getNotProtectField(String paramString1, String paramString2, Set paramSet) throws Exception;
  
  Long insertWorkAndSendMessage(String paramString1, String paramString2, List paramList1, List paramList2) throws Exception;
  
  String[] getProcActiUser(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getCandidate(String paramString1, String paramString2) throws Exception;
  
  String getNextActivityClass(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getNextDefaultActivityId(String paramString) throws Exception;
  
  List getCandidateSeq(String paramString1, String paramString2) throws Exception;
  
  List getRelationSimpleField(String paramString1, String paramString2) throws Exception;
  
  List getSimpleFieldByOrder(String paramString1, String paramString2) throws Exception;
  
  String getWorkStatusByWorkId(String paramString) throws Exception;
  
  List getPositionUserIDAndName(String paramString1, String paramString2) throws Exception;
  
  List getDealWithCommentFordoc(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getDealWithCommentNotBackFordoc(String paramString1, String paramString2, String paramString3) throws Exception;
}
