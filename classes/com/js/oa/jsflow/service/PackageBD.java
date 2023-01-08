package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WFPackageEJBHome;
import com.js.oa.jsflow.vo.PackageVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PackageBD {
  private static Logger logger = Logger.getLogger(PackageBD.class.getName());
  
  public PackageVO getPackage(long packageId) {
    PackageVO packageVO = new PackageVO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(new Long(packageId), Long.class);
      EJBProxy ejbProxy = new EJBProxy("WFPackageEJB", "WFPackageEJBLocal", WFPackageEJBHome.class);
      packageVO = (PackageVO)ejbProxy.invoke("getPackage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPackage information :" + e.getMessage());
    } 
    return packageVO;
  }
  
  public boolean updatePackage(PackageVO packageVO) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(packageVO, PackageVO.class);
      EJBProxy ejbProxy = new EJBProxy("WFPackageEJB", "WFPackageEJBLocal", WFPackageEJBHome.class);
      success = ((Boolean)ejbProxy.invoke("updatePackage", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to updatePackage information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean addPackage(PackageVO packageVO) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(packageVO, PackageVO.class);
      EJBProxy ejbProxy = new EJBProxy("WFPackageEJB", "WFPackageEJBLocal", WFPackageEJBHome.class);
      success = ((Boolean)ejbProxy.invoke("addPackage", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to addPackage information :" + e.getMessage());
    } 
    return success;
  }
  
  public void removePackage(String packageIdString) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(packageIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFPackageEJB", "WFPackageEJBLocal", WFPackageEJBHome.class);
      ejbProxy.invoke("removePackage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to removePackage information :" + e.getMessage());
    } 
  }
  
  public List getModulePackage(String moduleId, String domainId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(moduleId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFPackageEJB", "WFPackageEJBLocal", WFPackageEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getModulePackage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to removePackage information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getModuleProc(String moduleId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFPackageEJB", "WFPackageEJBLocal", WFPackageEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getModuleProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModuleProc information :" + e.getMessage());
    } 
    return alist;
  }
}
