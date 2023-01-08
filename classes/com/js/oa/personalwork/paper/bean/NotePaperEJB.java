package com.js.oa.personalwork.paper.bean;

import com.js.oa.personalwork.paper.po.NotePaperPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface NotePaperEJB extends EJBObject {
  String getColor(Long paramLong) throws Exception, RemoteException;
  
  void delBatch(String paramString1, String paramString2) throws Exception, RemoteException;
  
  void add(String paramString1, String paramString2, String paramString3, String paramString4) throws Exception, RemoteException;
  
  void update(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  NotePaperPO load(String paramString) throws Exception, RemoteException;
}
