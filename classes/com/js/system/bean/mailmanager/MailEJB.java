package com.js.system.bean.mailmanager;

import com.js.system.action.mailmanager.MailActionForm;
import com.js.system.vo.mailmanager.MailVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface MailEJB extends EJBObject {
  void add(MailVO paramMailVO) throws Exception, RemoteException;
  
  void del(String[] paramArrayOfString) throws Exception, RemoteException;
  
  void delAll() throws Exception, RemoteException;
  
  MailVO getSingleMail(String paramString) throws Exception, RemoteException;
  
  List getMailList() throws Exception, RemoteException;
  
  void modMail(MailVO paramMailVO) throws Exception, RemoteException;
  
  MailVO getSingleMailByFromUser(MailActionForm paramMailActionForm) throws Exception, RemoteException;
}
