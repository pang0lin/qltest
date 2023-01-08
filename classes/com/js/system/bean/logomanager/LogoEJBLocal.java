package com.js.system.bean.logomanager;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface LogoEJBLocal extends EJBLocalObject {
  List getLogoList() throws Exception, RemoteException;
}
