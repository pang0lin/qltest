package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.PackageVO;
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;

public interface WFPackageEJB extends EJBObject {
  PackageVO getPackage(Long paramLong) throws Exception, RemoteException;
  
  Boolean updatePackage(PackageVO paramPackageVO) throws Exception, RemoteException;
  
  Boolean addPackage(PackageVO paramPackageVO) throws Exception, RemoteException;
  
  void removePackage(String paramString) throws Exception, RemoteException;
  
  List getModulePackage(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getModuleProc(String paramString) throws RemoteException, Exception;
}
