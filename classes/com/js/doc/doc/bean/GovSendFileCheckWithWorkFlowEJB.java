package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovSendFileCheckWithWorkFlowPO;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.EJBObject;

public interface GovSendFileCheckWithWorkFlowEJB extends EJBObject {
  Long save(GovSendFileCheckWithWorkFlowPO paramGovSendFileCheckWithWorkFlowPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  Long update(GovSendFileCheckWithWorkFlowPO paramGovSendFileCheckWithWorkFlowPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  ArrayList load(Long paramLong) throws Exception, RemoteException;
  
  void deleteBatch(String paramString) throws Exception, RemoteException;
  
  Map getDossierInfo(String paramString) throws Exception, RemoteException;
  
  Boolean isPigeonholed(String paramString) throws Exception, RemoteException;
  
  Integer setPigeonholed(String paramString) throws Exception, RemoteException;
  
  Long completeSendFileCheck(String paramString) throws Exception, RemoteException;
}
