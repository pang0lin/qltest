package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.BirthdayWishPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface BirthCardEJB extends EJBObject {
  void delBatch(String paramString) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  void add(BirthdayWishPO paramBirthdayWishPO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  BirthdayWishPO load(String paramString) throws Exception, RemoteException;
  
  void update(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) throws Exception, RemoteException;
}
