package com.js.doc.doc.bean;

import com.js.doc.doc.po.ReceiveBaseInfoPO;
import com.js.doc.doc.po.ReceiveFileSeqPO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ReceivedocumentEJB extends EJBObject {
  Object[] getReceivedocumentBaseInfo(String paramString) throws Exception, RemoteException;
  
  Long saveReceiveBaseInfo(ReceiveBaseInfoPO paramReceiveBaseInfoPO) throws Exception, RemoteException;
  
  String updateReceiveBaseInfo(ReceiveBaseInfoPO paramReceiveBaseInfoPO) throws Exception, RemoteException;
  
  Long saveRecSeqInfo(ReceiveFileSeqPO paramReceiveFileSeqPO) throws Exception, RemoteException;
  
  ReceiveFileSeqPO loadRecSeqPO(String paramString) throws Exception, RemoteException;
  
  String updateRecSeqPO(ReceiveFileSeqPO paramReceiveFileSeqPO) throws Exception, RemoteException;
  
  String deleteRecSeqPO(String paramString) throws Exception, RemoteException;
  
  List getRecSeqListByProceId(String paramString) throws Exception, RemoteException;
  
  List getRecSeqListByProceId(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getSeqPoListBySeqClass(String paramString) throws Exception, RemoteException;
}
