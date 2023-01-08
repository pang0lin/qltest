package com.js.oa.routine.boardroom.bean;

import com.js.oa.routine.boardroom.po.EquipmentApplyPO;
import com.js.oa.routine.boardroom.po.EquipmentPO;
import com.js.oa.routine.boardroom.po.EquipmentTypePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface EquipmentEJB extends EJBObject {
  boolean addEquipmentType(EquipmentTypePO paramEquipmentTypePO) throws HibernateException, RemoteException;
  
  boolean deleteEquipmentType(Long paramLong) throws HibernateException, RemoteException;
  
  boolean deleteBatchEquipmentType(String paramString) throws HibernateException, RemoteException;
  
  EquipmentTypePO selectEquipmentType(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiEquipmentType(EquipmentTypePO paramEquipmentTypePO) throws HibernateException, RemoteException;
  
  List selectEquipmentType(String paramString) throws HibernateException, RemoteException;
  
  boolean addEquipment(EquipmentPO paramEquipmentPO, Long paramLong) throws HibernateException, RemoteException;
  
  boolean deleteEquipment(Long paramLong) throws HibernateException, RemoteException;
  
  boolean deleteBatchEquipment(String paramString) throws HibernateException, RemoteException;
  
  EquipmentPO selectEquipment(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiEquipment(EquipmentPO paramEquipmentPO, Long paramLong) throws HibernateException, RemoteException;
  
  Long addEquipmentApply(EquipmentApplyPO paramEquipmentApplyPO, Long paramLong) throws HibernateException, RemoteException;
  
  EquipmentApplyPO selectEquipmentApply(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiEquipmentApply(EquipmentApplyPO paramEquipmentApplyPO) throws HibernateException, RemoteException;
  
  boolean deleteEquipmentApply(Long paramLong) throws HibernateException, RemoteException;
  
  boolean restituteEquipmentApply(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean isRepeatName(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String[] getEquipmentInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String maintenanceEquipment(String paramString) throws HibernateException, RemoteException;
}
