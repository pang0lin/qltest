package com.js.oa.personalwork.setup.bean;

import com.js.oa.personalwork.setup.po.OptionSetPO;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

public interface OptionSetEJB extends EJBObject {
  OptionSetPO load(String paramString) throws Exception, RemoteException;
  
  String update(OptionSetPO paramOptionSetPO, String paramString) throws Exception, RemoteException;
}
