package com.js.oa.scheme.event.bean;

import com.js.oa.scheme.event.vo.EventVO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EventEJBLocal extends EJBLocalObject {
  List getEventByDay(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Boolean addEvent(EventVO paramEventVO) throws HibernateException;
  
  List selectAllEvent(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  Integer getEventRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  EventVO selectSingleEvent(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Boolean deleteEvent(Long paramLong1, Long paramLong2) throws HibernateException;
  
  Boolean deleteBatchEvent(Long paramLong, String paramString) throws HibernateException;
  
  Boolean deleteAllEvent(Long paramLong) throws HibernateException;
  
  Boolean modifyEvent(EventVO paramEventVO) throws HibernateException;
  
  List selectEventByTime(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  List searchEvent(Long paramLong1, Integer paramInteger1, Integer paramInteger2, String paramString1, Long paramLong2, Long paramLong3, Long paramLong4, String paramString2, String paramString3, String paramString4) throws HibernateException;
  
  Integer getSearchEventRecordCount(Long paramLong1, String paramString1, Long paramLong2, Long paramLong3, Long paramLong4, String paramString2, String paramString3, String paramString4) throws HibernateException;
  
  List getPopRemindEvent(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Integer popedEvent(Long paramLong1, Long paramLong2) throws HibernateException;
  
  Map getAllRemindEvents(String paramString1, String paramString2, Long paramLong) throws HibernateException;
  
  List getDDEvents(Long paramLong1, String paramString1, String paramString2, Long paramLong2) throws HibernateException;
  
  List selectAllEmpEvent(String paramString, Integer paramInteger1, Integer paramInteger2, Long paramLong) throws HibernateException;
  
  Integer getEventUderlingRecordCount(String paramString, Long paramLong) throws HibernateException;
  
  List searchEventByName(Long paramLong1, Integer paramInteger1, Integer paramInteger2, String paramString1, Long paramLong2, Long paramLong3, Long paramLong4, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
  
  Integer getSearchEventRecordCountByName(Long paramLong1, String paramString1, Long paramLong2, Long paramLong3, Long paramLong4, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
  
  List getDeskEvent(Long paramLong1, Long paramLong2, Long paramLong3, String paramString1, String paramString2) throws HibernateException;
}
