package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface WorkFlowButtonEJBLocal extends EJBLocalObject {
  String undoWork(WorkVO paramWorkVO) throws Exception;
  
  Boolean receiveWork(WorkVO paramWorkVO) throws Exception;
  
  Boolean deleteWork(WorkVO paramWorkVO) throws Exception;
  
  List getReceiveActivityList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getFrontActivityOper(WorkVO paramWorkVO) throws Exception;
  
  Boolean endWork(WorkVO paramWorkVO) throws Exception;
  
  Boolean getOnlineUserByUserAccounts(String paramString) throws Exception;
  
  Boolean hasMoreDealFile(WorkVO paramWorkVO) throws Exception;
  
  String getCurTransactTypeByWorkId(String paramString) throws Exception;
  
  List getAllPassRoundUsers(WorkVO paramWorkVO) throws Exception;
  
  String backToActivity(Map paramMap) throws Exception;
  
  List getFlowedActivity(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer backToSubmitPerson(Map paramMap) throws Exception;
  
  Boolean showUndoButton(WorkVO paramWorkVO) throws Exception;
  
  String getSaveFirstWorkUrl(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  List getAllDealwithUsersByStatus(WorkVO paramWorkVO, String paramString) throws Exception;
  
  List getAllDealWithLog(String paramString1, String paramString2, String paramString3) throws Exception;
  
  void setDealWithLog(WorkLogVO paramWorkLogVO) throws Exception;
  
  String getBackComment(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getTableId2() throws Exception;
  
  String getFieldInfoByFieldId(String paramString) throws Exception;
  
  String[] getModiCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String[] getCommentUserAndDateByCommField(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getCommentByCommField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws Exception;
  
  void addPersonWork(String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  Integer insertPassroundDealWith(Map paramMap) throws Exception;
  
  void setWFOnlineUser(Map paramMap) throws Exception;
  
  String[] getWFOnlineUser(Map paramMap) throws Exception;
  
  void delWFOnlineUser(Map paramMap) throws Exception;
  
  Map getBackToPerson(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  List getLeaderByDutyLevelAndOrg(String paramString1, String paramString2) throws Exception;
  
  String getDealTipsByWorkId(String paramString) throws Exception;
  
  List getPressDealList() throws Exception;
  
  void setWorkTask(String paramString1, String paramString2) throws Exception;
  
  void tranAutoReturn(String[] paramArrayOfString) throws Exception;
  
  void setWorkViewedDate(String paramString, WorkLogVO paramWorkLogVO) throws Exception;
  
  void setWorkViewedDate(String paramString1, WorkLogVO paramWorkLogVO, String paramString2) throws Exception;
  
  String getWorkViewedDate(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  String getUserOrgId(String paramString) throws Exception;
  
  List getAllRelationWork(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getUserSideLineOrgId(String paramString) throws Exception;
  
  void cleanWFOnlineUserInvalidate() throws Exception;
  
  List getAllDealwithUsersByStatus2(WorkVO paramWorkVO, String paramString) throws Exception;
  
  String saveToDraft(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception;
  
  String getSubmitPerson(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getSubProcWorkId(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String deleteFlowTempData(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String getDealTipsByLogId(String paramString) throws Exception;
}
