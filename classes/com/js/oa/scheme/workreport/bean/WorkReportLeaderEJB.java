package com.js.oa.scheme.workreport.bean;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface WorkReportLeaderEJB extends EJBObject {
  Vector load(String paramString1, String paramString2, String paramString3, Long paramLong) throws Exception, RemoteException;
  
  List loadContent(List paramList) throws Exception, RemoteException;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Long paramLong) throws Exception, RemoteException;
  
  void delBatch(String paramString) throws Exception, RemoteException;
  
  List getTopnNotReadReport(String paramString1, String paramString2, Integer paramInteger1, Integer paramInteger2) throws HibernateException, RemoteException;
  
  void addMonthReport(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, Long paramLong) throws Exception, RemoteException;
  
  List getTopnNotReadReport(String paramString1, String paramString2, Integer paramInteger) throws HibernateException, RemoteException;
  
  Boolean back(Long paramLong) throws HibernateException, RemoteException;
}
