package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationStockPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ExaminationStockEJB extends EJBObject {
  Boolean save(ExaminationStockPO paramExaminationStockPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  ExaminationStockPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(ExaminationStockPO paramExaminationStockPO, Object[] paramArrayOfObject, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
  
  Boolean deleteBatch(String paramString) throws Exception, RemoteException;
  
  Boolean move(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[] getStockArr(String paramString1, String paramString2, String paramString3, Long paramLong) throws Exception, RemoteException;
}
