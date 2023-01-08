package com.js.oa.security.ip.bean;

import com.js.oa.security.ip.po.IPPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;
import javax.servlet.http.HttpServletRequest;

public interface IPEJBLocal extends EJBLocalObject {
  boolean add(IPPO paramIPPO) throws Exception;
  
  boolean audit(IPPO paramIPPO, String paramString, Long paramLong, HttpServletRequest paramHttpServletRequest) throws Exception, RemoteException;
  
  String delete(String paramString) throws Exception;
  
  List selectSingle(String paramString) throws Exception;
  
  boolean modify(IPPO paramIPPO) throws Exception;
  
  String delBatch(String paramString) throws Exception;
  
  boolean delAll() throws Exception;
  
  String pass(String paramString) throws Exception;
}
