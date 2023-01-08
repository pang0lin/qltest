package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.HortationPunishPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface HortationPunishEJB extends EJBObject {
  Boolean saveHortationPunish(HortationPunishPO paramHortationPunishPO) throws Exception, RemoteException;
  
  Boolean deleteHortationPunish(String paramString) throws Exception, RemoteException;
  
  HortationPunishPO getSingleHortationPunish(Long paramLong) throws Exception, RemoteException;
  
  Boolean updateHortationPunish(HortationPunishPO paramHortationPunishPO, Long paramLong) throws Exception, RemoteException;
  
  List getHortationPunishList(String paramString) throws Exception, RemoteException;
}
