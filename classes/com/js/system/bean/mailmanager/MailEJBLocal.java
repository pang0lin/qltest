package com.js.system.bean.mailmanager;

import com.js.system.vo.mailmanager.MailVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface MailEJBLocal extends EJBLocalObject {
  void add(MailVO paramMailVO) throws Exception, RemoteException;
  
  void del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delAll() throws Exception, RemoteException;
  
  MailVO getSingleMail(String paramString) throws Exception, RemoteException;
  
  List getMailList() throws Exception, RemoteException;
  
  MailVO getSingleMailByFromUser(String paramString) throws Exception, RemoteException;
}
