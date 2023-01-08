package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface WorkFlowButtonEJB extends EJBObject {
  String undoWork(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  Boolean receiveWork(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  Boolean deleteWork(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  List getReceiveActivityList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getFrontActivityOper(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  Boolean endWork(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  Boolean getOnlineUserByUserAccounts(String paramString) throws Exception, RemoteException;
  
  Boolean hasMoreDealFile(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  String getCurTransactTypeByWorkId(String paramString) throws Exception, RemoteException;
  
  List getAllPassRoundUsers(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  String backToActivity(Map paramMap) throws Exception, RemoteException;
  
  List getFlowedActivity(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer backToSubmitPerson(Map paramMap) throws Exception, RemoteException;
  
  Boolean showUndoButton(WorkVO paramWorkVO) throws Exception, RemoteException;
  
  String getSaveFirstWorkUrl(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getAllDealwithUsersByStatus(WorkVO paramWorkVO, String paramString) throws Exception, RemoteException;
  
  List getAllDealWithLog(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  void setDealWithLog(WorkLogVO paramWorkLogVO) throws Exception, RemoteException;
  
  String getBackComment(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getTableId2() throws Exception, RemoteException;
  
  String getFieldInfoByFieldId(String paramString) throws Exception, RemoteException;
  
  String[] getModiCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  String[] getCommentUserAndDateByCommField(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws Exception, RemoteException;
  
  void addPersonWork(String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  Integer insertPassroundDealWith(Map paramMap) throws Exception, RemoteException;
  
  void setWFOnlineUser(Map paramMap) throws Exception, RemoteException;
  
  String[] getWFOnlineUser(Map paramMap) throws Exception, RemoteException;
  
  void delWFOnlineUser(Map paramMap) throws Exception, RemoteException;
  
  Map getBackToPerson(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  List getLeaderByDutyLevelAndOrg(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String getDealTipsByWorkId(String paramString) throws Exception, RemoteException;
  
  List getPressDealList() throws Exception, RemoteException;
  
  void setWorkTask(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void tranAutoReturn(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void setWorkViewedDate(String paramString, WorkLogVO paramWorkLogVO) throws Exception, RemoteException;
  
  void setWorkViewedDate(String paramString1, WorkLogVO paramWorkLogVO, String paramString2) throws Exception;
  
  String getWorkViewedDate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
  
  String getUserOrgId(String paramString) throws Exception, RemoteException;
  
  List getAllRelationWork(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  String getUserSideLineOrgId(String paramString) throws Exception, RemoteException;
  
  void cleanWFOnlineUserInvalidate() throws Exception, RemoteException;
  
  List getAllDealwithUsersByStatus2(WorkVO paramWorkVO, String paramString) throws Exception;
  
  String saveToDraft(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  String getSubmitPerson(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getSubProcWorkId(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String deleteFlowTempData(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getDealTipsByLogId(String paramString) throws Exception;
}
