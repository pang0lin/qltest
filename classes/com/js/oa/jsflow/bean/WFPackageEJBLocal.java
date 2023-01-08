package com.js.oa.jsflow.bean;

import com.js.oa.jsflow.vo.PackageVO;
import java.util.List;
import javax.ejb.EJBLocalObject;

public interface WFPackageEJBLocal extends EJBLocalObject {
  PackageVO getPackage(Long paramLong) throws Exception;
  
  Boolean updatePackage(PackageVO paramPackageVO) throws Exception;
  
  Boolean addPackage(PackageVO paramPackageVO) throws Exception;
  
  void removePackage(String paramString) throws Exception;
  
  List getModulePackage(String paramString1, String paramString2) throws Exception;
  
  List getModuleProc(String paramString) throws Exception;
}
