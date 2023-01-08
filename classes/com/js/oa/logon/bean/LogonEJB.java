package com.js.oa.logon.bean;

import java.rmi.RemoteException;
import java.util.HashMap;
import javax.ejb.EJBObject;

public interface LogonEJB extends EJBObject {
  HashMap logon(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws Exception, RemoteException;
}
