package com.js.system.bean.usermanager;

import com.js.system.vo.usermanager.EmployeeVO;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJBObject;

public interface UserEJB extends EJBObject {
  Integer add(EmployeeVO paramEmployeeVO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString, String[] paramArrayOfString5) throws Exception, RemoteException;
  
  HashMap getUserRelativeInfo(Long paramLong, String paramString) throws Exception, RemoteException;
  
  String delete(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String disable(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String recover(String[] paramArrayOfString) throws Exception, RemoteException;
  
  boolean open(String[] paramArrayOfString) throws Exception, RemoteException;
  
  String getUserAccountByIds(String paramString) throws Exception, RemoteException;
  
  String getUserNameById(String paramString) throws Exception, RemoteException;
  
  List getUserIdByEmpName(String paramString) throws Exception, RemoteException;
  
  List getUserIdAndNameByEmpNumber(String paramString) throws Exception, RemoteException;
  
  String getUserAccountByEnglistName(String paramString) throws Exception, RemoteException;
  
  Integer update(EmployeeVO paramEmployeeVO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString, String[] paramArrayOfString5) throws Exception, RemoteException;
  
  void update(EmployeeVO paramEmployeeVO) throws Exception, RemoteException;
  
  List getUserInfo(Long paramLong) throws Exception, RemoteException;
  
  List getUserWorkClass(Long paramLong) throws Exception, RemoteException;
  
  List getUserCoordination(Long paramLong) throws Exception, RemoteException;
  
  String getUserDefaultDomain(String paramString) throws Exception, RemoteException;
  
  List getUserDomain(String paramString) throws Exception, RemoteException;
  
  boolean delete(String paramString) throws Exception, RemoteException;
  
  Integer getUserNum() throws Exception, RemoteException;
  
  Boolean getRtxLogin(String paramString) throws Exception, RemoteException;
  
  Integer getIsChangePwd(String paramString) throws Exception;
  
  Integer getNetDiskSize(String paramString) throws Exception;
  
  Integer getMailBoxSize(String paramString) throws Exception;
  
  String getSignature(String paramString) throws Exception;
  
  boolean updateRelationEmp(String paramString) throws Exception;
  
  boolean updateEmpTurnover(String paramString) throws Exception;
  
  boolean updateEmpTurnoverCoordination(String paramString) throws Exception;
  
  boolean updateSysmessage(String paramString) throws Exception;
}
