package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.OfficalDictionPO;
import java.rmi.RemoteException;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface OfficalDictionEJB extends EJBObject {
  Vector list(Long paramLong, Integer paramInteger) throws Exception, RemoteException;
  
  String add(OfficalDictionPO paramOfficalDictionPO, Long paramLong, String paramString) throws Exception, RemoteException;
  
  String update(String paramString1, Byte paramByte, Long paramLong1, Long paramLong2, String paramString2) throws Exception, RemoteException;
  
  void delAll(Long paramLong) throws Exception, RemoteException;
  
  void delBatch(String paramString) throws Exception, RemoteException;
  
  OfficalDictionPO load(Long paramLong) throws Exception, RemoteException;
}
