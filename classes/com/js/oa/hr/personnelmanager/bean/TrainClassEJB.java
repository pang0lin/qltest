package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.TrainClassPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface TrainClassEJB extends EJBObject {
  Boolean save(TrainClassPO paramTrainClassPO) throws Exception, RemoteException;
  
  TrainClassPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(TrainClassPO paramTrainClassPO, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  List list(Long paramLong) throws Exception, RemoteException;
}
