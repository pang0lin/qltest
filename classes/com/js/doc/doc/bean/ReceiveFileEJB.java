package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovReceiveFilePO;
import com.js.doc.doc.po.ReceiveAssociatePO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface ReceiveFileEJB extends EJBObject {
  String initNumber(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  Long save(GovReceiveFilePO paramGovReceiveFilePO) throws Exception, RemoteException;
  
  GovReceiveFilePO load(String paramString) throws Exception, RemoteException;
  
  Long update(GovReceiveFilePO paramGovReceiveFilePO, String paramString) throws Exception, RemoteException;
  
  Long completeReceiveFile(String paramString) throws Exception, RemoteException;
  
  Map getDossierInfo(String paramString) throws Exception, RemoteException;
  
  Boolean isPigeonholed(String paramString) throws Exception, RemoteException;
  
  Integer setPigeonholed(String paramString) throws Exception, RemoteException;
  
  Integer delete(String paramString) throws Exception, RemoteException;
  
  Long saveReceiveAssociate(ReceiveAssociatePO paramReceiveAssociatePO) throws Exception, RemoteException;
  
  Integer getReceiveAssociateNum(String paramString) throws Exception, RemoteException;
  
  List getRecieveAssociateList(String paramString) throws Exception, RemoteException;
  
  Integer delete2(String paramString) throws Exception;
}
