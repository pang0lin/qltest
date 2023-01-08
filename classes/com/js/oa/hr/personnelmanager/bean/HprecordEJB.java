package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.HprecordPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface HprecordEJB extends EJBObject {
  boolean addHprecord(HprecordPO paramHprecordPO) throws Exception, RemoteException;
  
  boolean deleteHprecord(Long paramLong) throws Exception, RemoteException;
  
  boolean deleteBatchHprecord(String paramString) throws Exception, RemoteException;
  
  HprecordPO selectHprecordView(Long paramLong) throws HibernateException, RemoteException;
  
  boolean updateHprecord(HprecordPO paramHprecordPO) throws Exception, RemoteException;
  
  List selectHpName(String paramString) throws HibernateException, RemoteException;
}
