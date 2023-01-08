package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.TrainhistoryVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeTrainhistoryEJB extends EJBObject {
  Boolean save(TrainhistoryVO paramTrainhistoryVO, Long paramLong) throws Exception, RemoteException;
  
  TrainhistoryVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(TrainhistoryVO paramTrainhistoryVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
