package com.js.oa.hr.personnelmanager.bean;

import com.js.system.vo.usermanager.CompetenceVO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface EmployeeCompetenceEJB extends EJBObject {
  Boolean save(CompetenceVO paramCompetenceVO, Long paramLong) throws Exception, RemoteException;
  
  CompetenceVO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(CompetenceVO paramCompetenceVO, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
}
