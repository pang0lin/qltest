package com.js.oa.hr.personnelmanager.bean;

import com.js.oa.hr.personnelmanager.po.WorkAddressPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface WorkAddressEJB extends EJBObject {
  Boolean save(WorkAddressPO paramWorkAddressPO) throws Exception, RemoteException;
  
  WorkAddressPO load(Long paramLong) throws Exception, RemoteException;
  
  Boolean update(WorkAddressPO paramWorkAddressPO, Long paramLong) throws Exception, RemoteException;
  
  Boolean delete(String paramString) throws Exception, RemoteException;
  
  List list(Long paramLong) throws Exception, RemoteException;
}
