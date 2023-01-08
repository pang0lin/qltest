package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.MyInfoPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface MyInfoEJB extends EJBObject {
  MyInfoPO load(String paramString) throws Exception, RemoteException;
  
  String update(MyInfoPO paramMyInfoPO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  String updatePass(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String loadRTXLogin(String paramString) throws Exception, RemoteException;
  
  Boolean updateRTXLogin(String paramString1, String paramString2) throws Exception, RemoteException;
  
  Integer skinSetup(String paramString1, String paramString2) throws Exception;
  
  Object[] getEmpStatus(String paramString) throws Exception;
  
  Integer setEmpStatus(String paramString1, String paramString2) throws Exception;
  
  Integer delEmpStatus(String paramString1, String paramString2) throws Exception;
  
  List getEmpStatusByEmpIdArr(String[] paramArrayOfString) throws Exception;
  
  List getEmpStatusByEmpIdStr(String paramString) throws Exception;
  
  List getEmpStatusByEmpOrgGrp(String paramString1, String paramString2, String paramString3) throws Exception;
  
  Integer saveEmpDefineStatus(String paramString1, String paramString2) throws Exception;
  
  Integer saveEmpNewStatus(String paramString1, String paramString2) throws Exception;
}
