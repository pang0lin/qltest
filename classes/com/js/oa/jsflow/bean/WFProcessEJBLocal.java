package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.vo.ActivityVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface WFProcessEJBLocal extends EJBLocalObject {
  void removeProcess(String paramString) throws Exception;
  
  Boolean addProcess(WFWorkFlowProcessPO paramWFWorkFlowProcessPO, String paramString, String[] paramArrayOfString) throws Exception;
  
  List getNoWriteField(String paramString) throws Exception;
  
  List getProcInfo(String paramString) throws Exception;
  
  Boolean updateProcess(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  ActivityVO getFirstActivity(String paramString) throws Exception;
  
  String getProcWhereSql(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getUserProcess(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getUserProcessList(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getUserDossProc(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getAllDossProc(String paramString) throws Exception;
  
  List getAllProcess(String paramString) throws Exception;
  
  List getUserDossOperProc(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getUserDossViewOperAdminProc(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getRemindField(String paramString) throws Exception;
  
  String getNoWriteField(String paramString1, String paramString2) throws Exception;
  
  Integer getCanCancel(String paramString) throws Exception;
  
  Integer getIsDossier(String paramString) throws Exception;
  
  Boolean copyProcess(String paramString) throws Exception;
  
  String getDescription(String paramString) throws Exception;
  
  String updateFirstActivity(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception;
  
  Boolean nameIsDuplicate(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getAllProcessByModule(String paramString1, String paramString2) throws Exception;
  
  String userCanCreateFlow(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
  
  String setProcessOnDeskTop(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getDeskTopFlowId(String paramString) throws Exception;
  
  List getUserOftenProcess(String paramString1, String paramString2, String paramString3) throws Exception;
  
  String getFirstActivityWriteField(String paramString1, String paramString2) throws Exception;
  
  List getAllUserProcess(String paramString) throws Exception;
  
  String changeProcessStatus(String paramString1, String paramString2) throws Exception;
  
  Boolean checkProcessByPackage(String paramString) throws Exception;
}
