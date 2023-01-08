package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.vo.ActivityVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface WFProcessEJB extends EJBObject {
  void removeProcess(String paramString) throws Exception, RemoteException;
  
  Boolean addProcess(WFWorkFlowProcessPO paramWFWorkFlowProcessPO, String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getNoWriteField(String paramString) throws Exception, RemoteException;
  
  List getProcInfo(String paramString) throws Exception, RemoteException;
  
  Boolean updateProcess(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  ActivityVO getFirstActivity(String paramString) throws Exception, RemoteException;
  
  String getProcWhereSql(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getUserProcess(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getUserProcessList(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  List getUserDossProc(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getAllDossProc(String paramString) throws Exception, RemoteException;
  
  List getAllProcess(String paramString) throws Exception, RemoteException;
  
  List getUserDossOperProc(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getUserDossViewOperAdminProc(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String getRemindField(String paramString) throws Exception, RemoteException;
  
  String getNoWriteField(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer getCanCancel(String paramString) throws Exception, RemoteException;
  
  Integer getIsDossier(String paramString) throws Exception, RemoteException;
  
  Boolean copyProcess(String paramString) throws Exception, RemoteException;
  
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
  
  void hangupInstance(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception;
}
