package com.js.oa.security.ip.bean;

import com.js.oa.security.ip.po.IPPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import javax.servlet.http.HttpServletRequest;

public interface IPEJB extends EJBObject {
  boolean add(IPPO paramIPPO) throws Exception, RemoteException;
  
  boolean audit(IPPO paramIPPO, String paramString, Long paramLong, HttpServletRequest paramHttpServletRequest) throws Exception, RemoteException;
  
  String delete(String paramString) throws Exception, RemoteException;
  
  List selectSingle(String paramString) throws Exception, RemoteException;
  
  boolean modify(IPPO paramIPPO) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  boolean delAll() throws Exception, RemoteException;
  
  String pass(String paramString) throws Exception, RemoteException;
}
