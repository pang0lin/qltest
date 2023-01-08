package com.js.oa.scheme.event.bean;

import com.js.oa.scheme.event.vo.EventVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EventEJB extends EJBObject {
  List getEventByDay(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Boolean addEvent(EventVO paramEventVO) throws HibernateException, RemoteException;
  
  List selectAllEvent(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getEventRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  EventVO selectSingleEvent(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Boolean deleteEvent(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Boolean deleteBatchEvent(Long paramLong, String paramString) throws HibernateException, RemoteException;
  
  Boolean deleteAllEvent(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modifyEvent(EventVO paramEventVO) throws HibernateException, RemoteException;
  
  List selectEventByTime(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  List searchEvent(Long paramLong1, Integer paramInteger1, Integer paramInteger2, String paramString1, Long paramLong2, Long paramLong3, Long paramLong4, String paramString2, String paramString3, String paramString4) throws HibernateException, RemoteException;
  
  List searchEventByName(String paramString1, Integer paramInteger1, Integer paramInteger2, String paramString2, Long paramLong1, Long paramLong2, Long paramLong3, String paramString3, String paramString4, String paramString5, String paramString6) throws HibernateException, RemoteException;
  
  Integer getSearchEventRecordCount(Long paramLong1, String paramString1, Long paramLong2, Long paramLong3, Long paramLong4, String paramString2, String paramString3, String paramString4) throws HibernateException, RemoteException;
  
  Integer getSearchEventRecordCountByName(String paramString1, String paramString2, Long paramLong1, Long paramLong2, Long paramLong3, String paramString3, String paramString4, String paramString5, String paramString6) throws HibernateException, RemoteException;
  
  List getPopRemindEvent(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Integer popedEvent(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Map getAllRemindEvents(String paramString1, String paramString2, Long paramLong) throws HibernateException, RemoteException;
  
  List getDDEvents(Long paramLong1, String paramString1, String paramString2, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectAllEmpEvent(String paramString, Integer paramInteger1, Integer paramInteger2, Long paramLong) throws HibernateException, RemoteException;
  
  Integer getEventUderlingRecordCount(String paramString, Long paramLong) throws HibernateException, RemoteException;
  
  List getDeskEvent(Long paramLong1, Long paramLong2, Long paramLong3, String paramString1, String paramString2) throws HibernateException, RemoteException;
}
