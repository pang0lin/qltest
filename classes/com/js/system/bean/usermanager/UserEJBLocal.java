package com.js.system.bean.usermanager;

import com.js.system.vo.usermanager.EmployeeVO;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface UserEJBLocal extends EJBLocalObject {
  Integer add(EmployeeVO paramEmployeeVO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString, String[] paramArrayOfString5) throws Exception;
  
  HashMap getUserRelativeInfo(Long paramLong, String paramString) throws Exception;
  
  List getUserIdByEmpName(String paramString) throws Exception, RemoteException;
  
  List getUserIdAndNameByEmpNumber(String paramString) throws Exception, RemoteException;
  
  String delete(String[] paramArrayOfString) throws Exception;
  
  String disable(String[] paramArrayOfString) throws Exception;
  
  String recover(String[] paramArrayOfString) throws Exception;
  
  boolean open(String[] paramArrayOfString) throws Exception;
  
  String getUserAccountByIds(String paramString) throws Exception;
  
  String getUserNameById(String paramString) throws Exception;
  
  Integer update(EmployeeVO paramEmployeeVO, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, String paramString, String[] paramArrayOfString5) throws Exception;
  
  void update(EmployeeVO paramEmployeeVO) throws Exception;
  
  List getUserInfo(Long paramLong) throws Exception;
  
  List getUserWorkClass(Long paramLong) throws Exception, RemoteException;
  
  List getUserCoordination(Long paramLong) throws Exception, RemoteException;
  
  String getUserDefaultDomain(String paramString) throws Exception;
  
  List getUserDomain(String paramString) throws Exception;
  
  boolean delete(String paramString) throws Exception;
  
  Integer getUserNum() throws Exception;
  
  Boolean getRtxLogin(String paramString) throws Exception;
  
  Integer getIsChangePwd(String paramString) throws Exception;
  
  Integer getMailBoxSize(String paramString) throws Exception;
  
  Integer getNetDiskSize(String paramString) throws Exception;
  
  String getSignature(String paramString) throws Exception;
  
  boolean updateRelationEmp(String paramString) throws Exception;
  
  boolean updateEmpTurnover(String paramString) throws Exception;
  
  boolean updateEmpTurnoverCoordination(String paramString) throws Exception;
}
