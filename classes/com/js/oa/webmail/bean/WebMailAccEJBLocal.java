package com.js.oa.webmail.bean;

import com.js.oa.webmail.po.WebMailAcc;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface WebMailAccEJBLocal extends EJBLocalObject {
  void getMailAccList() throws Exception, RemoteException;
  
  List getMailAccListByUserAcco(Long paramLong, String paramString) throws Exception, RemoteException;
  
  void createMailAcc(WebMailAcc paramWebMailAcc) throws Exception, RemoteException;
  
  void modMailAcc(WebMailAcc paramWebMailAcc) throws Exception, RemoteException;
  
  void delMailAcc(String paramString, String[] paramArrayOfString) throws Exception, RemoteException;
  
  WebMailAcc getMailAccInfoById(Long paramLong) throws Exception, RemoteException;
  
  List getMailAccListByUserId(Long paramLong, String paramString) throws Exception, RemoteException;
}
