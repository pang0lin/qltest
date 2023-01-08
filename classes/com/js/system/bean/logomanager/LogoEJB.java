package com.js.system.bean.logomanager;

import com.js.system.vo.logomanager.LogoVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface LogoEJB extends EJBObject {
  List getLogoList() throws Exception, RemoteException;
  
  void modifyLogo(LogoVO paramLogoVO) throws Exception, RemoteException;
}
