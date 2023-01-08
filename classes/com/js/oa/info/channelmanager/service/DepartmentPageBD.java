package com.js.oa.info.channelmanager.service;

import com.js.oa.info.channelmanager.bean.DepartmentPageEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class DepartmentPageBD {
  private static Logger logger = Logger.getLogger(DepartmentPageBD.class.getName());
  
  public List getTopChannel(String orgId, String userId, String channelType) {
    ArrayList list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      list = (ArrayList)ejbProxy.invoke("getTopChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllChannel information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getLeftChTree(String topChId, String orgId, String userId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(topChId, String.class);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getLeftChTree", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllChannel information :" + e.getMessage());
    } 
    return alist;
  }
  
  public void ModiDepaStyle(String styleId, String orgId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(styleId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      ejbProxy.invoke("ModiDepaStyle", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllChannel information :" + e.getMessage());
    } 
  }
  
  public List departmentDeskop(String viewChStr, String readerWhere) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(viewChStr, String.class);
      pg.put(readerWhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("departmentDeskop", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to departmentDeskop information :" + e.getMessage());
    } 
    return alist;
  }
  
  public void setDepartFlag(String informationId, String gg, String flagValue) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationId, String.class);
      pg.put(gg, String.class);
      pg.put(flagValue, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      ejbProxy.invoke("setDepartFlag", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to departmentDeskop information :" + e.getMessage());
    } 
  }
  
  public List getNewAnno(String channelType, String infoWhere) {
    List anno = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(channelType, String.class);
      pg.put(infoWhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      anno = (List)ejbProxy.invoke("getNewAnno", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNewAnno information :" + e.getMessage());
    } 
    return anno;
  }
  
  public List getPhotoInfo(String channelType, String infoWhere) {
    List photoInfo = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(channelType, String.class);
      pg.put(infoWhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      photoInfo = (List)ejbProxy.invoke("getPhotoInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPhotoInfo information :" + e.getMessage());
    } 
    return photoInfo;
  }
  
  public void updateBanner(String styleId, String saveName, String orgId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(styleId, String.class);
      pg.put(saveName, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      ejbProxy.invoke("updateBanner", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateBanner information :" + e.getMessage());
    } 
  }
  
  public List getMostNewInfo(String viewChStr, String orgIdString, String userId) {
    ArrayList mostNewInfo = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(viewChStr, String.class);
      pg.put(orgIdString, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      mostNewInfo = (ArrayList)ejbProxy.invoke("getMostNewInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPhotoInfo information :" + e.getMessage());
    } 
    return mostNewInfo;
  }
  
  public String getOrgBanner(String orgId) {
    String orgBanner = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      orgBanner = (String)ejbProxy.invoke("getOrgBanner", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPhotoInfo information :" + e.getMessage());
    } 
    return orgBanner;
  }
  
  public Map departHomepage(String viewChStr, String readerWhere) {
    Map map = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(viewChStr, String.class);
      pg.put(readerWhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      map = (Map)ejbProxy.invoke("departHomepage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPhotoInfo information :" + e.getMessage());
    } 
    return map;
  }
  
  public List getCanVindicate(String where, String userId, String orgId, String domainId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(where, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getCanVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanVindicate information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getCanVindicate(String where, String userId, String orgId, String domainId, String infoOrDepartmentType) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(where, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(domainId, String.class);
      pg.put(infoOrDepartmentType, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getCanVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanVindicate information :" + e.getMessage());
    } 
    return alist;
  }
  
  public Boolean deptVindicateInfo(String userId, String userOrg, String curOrgId) {
    Boolean result = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(userOrg, String.class);
      pg.put(curOrgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      result = (Boolean)ejbProxy.invoke("deptVindicateInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deptVindicateInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getAllChannel(String orgId, String userId, String channelType) {
    ArrayList list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(orgId, String.class);
      pg.put(userId, String.class);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("DepartmentPageEJB", "DepartmentPageEJBLocal", DepartmentPageEJBHome.class);
      list = (ArrayList)ejbProxy.invoke("getAllChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllChannel information :" + e.getMessage());
    } 
    return list;
  }
}
