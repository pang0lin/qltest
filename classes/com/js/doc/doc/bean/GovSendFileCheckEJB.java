package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovSendFileCheckPO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface GovSendFileCheckEJB extends EJBObject {
  String add(GovSendFileCheckPO paramGovSendFileCheckPO) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  List load(String paramString) throws Exception, RemoteException;
  
  String update(GovSendFileCheckPO paramGovSendFileCheckPO, String paramString) throws Exception, RemoteException;
  
  String selfPostil(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  String add(GovSendFileCheckPO paramGovSendFileCheckPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  Map getDossierInfo(String paramString) throws Exception, RemoteException;
  
  Boolean isPigeonholed(String paramString) throws Exception, RemoteException;
  
  Integer setPigeonholed(String paramString) throws Exception, RemoteException;
}
