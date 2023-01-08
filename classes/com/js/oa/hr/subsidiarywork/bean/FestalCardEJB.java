package com.js.oa.hr.subsidiarywork.bean;

import com.js.oa.hr.subsidiarywork.po.FestivalSetPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface FestalCardEJB extends EJBObject {
  void delBatch(String paramString) throws Exception, RemoteException;
  
  void delAll(String paramString) throws Exception, RemoteException;
  
  void add(FestivalSetPO paramFestivalSetPO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  FestivalSetPO load(String paramString) throws Exception, RemoteException;
  
  void update(FestivalSetPO paramFestivalSetPO, String paramString1, String paramString2) throws Exception, RemoteException;
}
