package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationTypePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ExaminationTypeEJB extends EJBObject {
  Boolean save(ExaminationTypePO paramExaminationTypePO) throws Exception, RemoteException;
  
  ExaminationTypePO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(ExaminationTypePO paramExaminationTypePO, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
  
  Boolean delBatch(String paramString) throws Exception, RemoteException;
  
  List getTypeList(Long paramLong) throws Exception, RemoteException;
}
