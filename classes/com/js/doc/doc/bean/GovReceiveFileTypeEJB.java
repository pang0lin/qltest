package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovReceiveFileTypePO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface GovReceiveFileTypeEJB extends EJBObject {
  String govReceiveFileTypeAdd(GovReceiveFileTypePO paramGovReceiveFileTypePO) throws Exception, RemoteException;
  
  String govReceiveFileTypeDelBatch(String paramString) throws Exception, RemoteException;
  
  String govReceiveFileTypeUpdate(GovReceiveFileTypePO paramGovReceiveFileTypePO) throws Exception, RemoteException;
  
  List govReceiveFileTypeModifylist(String paramString) throws Exception, RemoteException;
  
  String govReceiveFileTypeDel(String paramString) throws Exception, RemoteException;
  
  List govReceiveFileTypeList() throws Exception, RemoteException;
  
  List govReceiveFileTypeList(String paramString) throws Exception, RemoteException;
}
