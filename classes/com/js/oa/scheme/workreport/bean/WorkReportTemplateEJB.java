package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBObject;

public interface WorkReportTemplateEJB extends EJBObject {
  void delBatch(String paramString) throws Exception, RemoteException;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, Long paramLong) throws Exception, RemoteException;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Long paramLong) throws Exception, RemoteException;
  
  Map load(String paramString, Long paramLong) throws Exception, RemoteException;
  
  Boolean hasWorkReportTemplate(String paramString1, String paramString2, String paramString3, Long paramLong) throws Exception, RemoteException;
}
