package com.js.oa.routine.boardroom.bean;

import com.js.oa.routine.boardroom.po.EquipmentApplyPO;
import com.js.oa.routine.boardroom.po.EquipmentPO;
import com.js.oa.routine.boardroom.po.EquipmentTypePO;
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface EquipmentEJBLocal extends EJBLocalObject {
  boolean addEquipmentType(EquipmentTypePO paramEquipmentTypePO) throws HibernateException;
  
  boolean deleteEquipmentType(Long paramLong) throws HibernateException;
  
  boolean deleteBatchEquipmentType(String paramString) throws HibernateException;
  
  EquipmentTypePO selectEquipmentType(Long paramLong) throws HibernateException;
  
  boolean modiEquipmentType(EquipmentTypePO paramEquipmentTypePO) throws HibernateException;
  
  List selectEquipmentType(String paramString) throws HibernateException;
  
  boolean addEquipment(EquipmentPO paramEquipmentPO, Long paramLong) throws HibernateException;
  
  boolean deleteEquipment(Long paramLong) throws HibernateException;
  
  boolean deleteBatchEquipment(String paramString) throws HibernateException;
  
  EquipmentPO selectEquipment(Long paramLong) throws HibernateException;
  
  boolean modiEquipment(EquipmentPO paramEquipmentPO, Long paramLong) throws HibernateException;
  
  Long addEquipmentApply(EquipmentApplyPO paramEquipmentApplyPO, Long paramLong) throws HibernateException;
  
  EquipmentApplyPO selectEquipmentApply(Long paramLong) throws HibernateException;
  
  boolean modiEquipmentApply(EquipmentApplyPO paramEquipmentApplyPO) throws HibernateException;
  
  boolean deleteEquipmentApply(Long paramLong) throws HibernateException;
  
  boolean restituteEquipmentApply(Long paramLong) throws HibernateException;
  
  Boolean isRepeatName(String paramString1, String paramString2) throws HibernateException;
  
  String[] getEquipmentInfo(String paramString1, String paramString2) throws Exception;
  
  String maintenanceEquipment(String paramString) throws HibernateException;
}
