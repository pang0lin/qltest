package com.js.oa.zky.bean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface ZkyMangerEJB extends EJBObject {
  List list(String paramString, Long paramLong) throws Exception, RemoteException;
}
