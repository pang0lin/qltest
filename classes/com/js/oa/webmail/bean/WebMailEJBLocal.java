package com.js.oa.webmail.bean;

import com.js.oa.webmail.po.WebMail;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;

public interface WebMailEJBLocal extends EJBLocalObject {
  void createAll(Collection paramCollection1, Collection paramCollection2) throws Exception, RemoteException;
  
  void createAllUUID(Collection paramCollection) throws Exception, RemoteException;
  
  Map getSingleWebMail(String paramString) throws Exception, RemoteException;
  
  List getWebMailList() throws Exception, RemoteException;
  
  List getWebMailListByBoxId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getAllUUIDList() throws Exception, RemoteException;
  
  List getAllUUIDListById(String paramString) throws Exception, RemoteException;
  
  void modMailInfo(WebMail paramWebMail) throws Exception, RemoteException;
  
  void moveMail2_(String[] paramArrayOfString) throws Exception, RemoteException;
  
  List getWebMailList(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delMail(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delAll() throws Exception, RemoteException;
  
  Long createMail(WebMail paramWebMail) throws Exception, RemoteException;
  
  void updateMail(WebMail paramWebMail) throws Exception, RemoteException;
  
  Float getMailboxSize(Long paramLong) throws Exception, RemoteException;
}
