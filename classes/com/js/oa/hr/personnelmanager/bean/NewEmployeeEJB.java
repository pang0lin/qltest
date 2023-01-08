package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.EmployeeVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface NewEmployeeEJB extends EJBObject {
  List selectSingle(Long paramLong) throws Exception, RemoteException;
  
  Integer add(EmployeeVO paramEmployeeVO, String paramString) throws Exception, RemoteException;
  
  List postTitle(String paramString) throws Exception, RemoteException;
  
  Integer update(EmployeeVO paramEmployeeVO, String paramString, Long paramLong) throws Exception, RemoteException;
  
  List getMaturityAlertSettings(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean saveMaturityAlertSettings(String paramString1, String[][] paramArrayOfString, String paramString2) throws Exception, RemoteException;
  
  String getMaturityAlertSettingsValue(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Integer getLogCountByUserId(String paramString) throws Exception, RemoteException;
  
  Integer getLogCountByOrgId(String paramString) throws Exception, RemoteException;
}
