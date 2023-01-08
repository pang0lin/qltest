package com.js.oa.hr.personnelmanager.bean;

import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBObject;

public interface EmpStatisticsEJB extends EJBObject {
  Map listEmpChange(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception, RemoteException;
  
  Map listEmpStruct(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception, RemoteException;
  
  Map listEmpCizhi(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception, RemoteException;
  
  Map listEmpZhuanzheng(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Integer paramInteger1, Integer paramInteger2) throws Exception, RemoteException;
}
