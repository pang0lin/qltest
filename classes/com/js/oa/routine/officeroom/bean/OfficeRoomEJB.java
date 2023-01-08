package com.js.oa.routine.officeroom.bean;

import com.js.oa.routine.officeroom.po.OfficeBuildPO;
import com.js.oa.routine.officeroom.po.OfficePO;
import com.js.oa.routine.officeroom.po.OfficeUsePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface OfficeRoomEJB extends EJBObject {
  Boolean save(OfficeBuildPO paramOfficeBuildPO) throws Exception, RemoteException;
  
  OfficeBuildPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(OfficeBuildPO paramOfficeBuildPO, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(Long paramLong) throws Exception, RemoteException;
  
  Boolean officeSave(OfficePO paramOfficePO) throws Exception, RemoteException;
  
  OfficePO officeLoad(Long paramLong) throws Exception, RemoteException;
  
  Boolean officeUpdate(OfficePO paramOfficePO, Long paramLong) throws Exception, RemoteException;
  
  Boolean officeDelete(Long paramLong) throws Exception, RemoteException;
  
  List list(String paramString, Long paramLong) throws Exception, RemoteException;
  
  Boolean officeUseSave(OfficeUsePO paramOfficeUsePO) throws Exception, RemoteException;
  
  OfficeUsePO officeUseLoad(Long paramLong) throws Exception, RemoteException;
  
  Boolean officeUseUpdate(OfficeUsePO paramOfficeUsePO, Long paramLong) throws Exception, RemoteException;
  
  Boolean officeUseDelete(Long paramLong) throws Exception, RemoteException;
}
