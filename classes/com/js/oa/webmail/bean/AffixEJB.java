package com.js.oa.webmail.bean;

import com.js.oa.webmail.po.Affix;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJBObject;

public interface AffixEJB extends EJBObject {
  Affix getAffixByPath(String paramString) throws Exception, RemoteException;
  
  List getAffixList() throws Exception, RemoteException;
  
  void delMailAffix() throws Exception, RemoteException;
  
  List getMailAffixByMailId(String paramString) throws Exception, RemoteException;
  
  void createAttach(Collection paramCollection) throws Exception, RemoteException;
  
  List getAttachList() throws Exception, RemoteException;
}
