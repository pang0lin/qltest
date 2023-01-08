package com.js.doc.doc.bean;

import com.js.doc.doc.po.GovTopicWordPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface GovTopicWordEJB extends EJBObject {
  String add(GovTopicWordPO paramGovTopicWordPO) throws Exception, RemoteException;
  
  String delBatch(String paramString) throws Exception, RemoteException;
  
  GovTopicWordPO load(String paramString) throws Exception, RemoteException;
  
  String update(String paramString, GovTopicWordPO paramGovTopicWordPO) throws Exception, RemoteException;
}
