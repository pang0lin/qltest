package com.js.oa.personalwork.person.bean;

import com.js.oa.personalwork.person.po.PersonPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJBObject;

public interface PersonOwnEJB extends EJBObject {
  Vector list(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws Exception, RemoteException;
  
  PersonPO load(String paramString) throws Exception, RemoteException;
  
  void add(PersonPO paramPersonPO, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws Exception, RemoteException;
  
  void delBatch(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void delAll(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void update(PersonPO paramPersonPO, String paramString) throws Exception, RemoteException;
  
  Vector see(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List city(String paramString) throws Exception, RemoteException;
  
  List county(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void delAll(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
}
