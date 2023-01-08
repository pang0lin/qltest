package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.TrainRecordPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface TrainRecordEJB extends EJBObject {
  Boolean save(TrainRecordPO paramTrainRecordPO) throws Exception, RemoteException;
  
  TrainRecordPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(TrainRecordPO paramTrainRecordPO, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
}
