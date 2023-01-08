package com.js.oa.hr.examination.bean;

import com.js.oa.hr.examination.po.ExaminationSelfTestPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface ExaminationSelfTestEJB extends EJBObject {
  Long save(ExaminationSelfTestPO paramExaminationSelfTestPO, Object[] paramArrayOfObject) throws Exception, RemoteException;
  
  String[] result(Long paramLong) throws Exception, RemoteException;
  
  ExaminationSelfTestPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
  
  Boolean deleteBatch(String paramString) throws Exception, RemoteException;
}
