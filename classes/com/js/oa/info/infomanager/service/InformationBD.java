package com.js.oa.info.infomanager.service;

import com.js.oa.info.infomanager.bean.InformationEJBBean;
import com.js.oa.info.infomanager.bean.InformationEJBHome;
import com.js.oa.info.infomanager.po.InformationPO;
import com.js.system.manager.service.ManagerService;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class InformationBD {
  private static Logger logger = Logger.getLogger(InformationBD.class.getName());
  
  public Long add(InformationPO informationPO, String[] para, String[] assoInfo, String[] infoPicName, String[] infoPicSaveName, String[] infoAppendName, String[] infoAppendSaveName, String domainId, String corpId) {
    Long result = new Long(0L);
    try {
      ParameterGenerator pg = new ParameterGenerator(9);
      pg.put(informationPO, InformationPO.class);
      pg.put(para, String[].class);
      pg.put(assoInfo, String[].class);
      pg.put(infoPicName, String[].class);
      pg.put(infoPicSaveName, String[].class);
      pg.put(infoAppendName, String[].class);
      pg.put(infoAppendSaveName, String[].class);
      pg.put(domainId, String.class);
      pg.put(corpId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to add information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getSingleInfo(String informationId, String channelId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleInfo information :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getInformationIssueOrg(String informationId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getInformationIssueOrg", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOrgName information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getOrgName(String channelId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getOrgName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOrgName information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllOrgName(String flag) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(flag, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getAllOrgName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllOrgName information :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean informationStatus(String informationId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("informationStatus", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to getSingleInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean setInformationStatus(String informationId, String status) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(status, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setInformationStatus", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to setInformationStatus information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getchannleinfo(String informationId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getchannleinfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getchannleinfo information :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean setBrowser(String userId, String userName, String orgName, String informationId, String orgIdString) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      pg.put(informationId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setBrowser", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to setBrowser information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean setBrowserKits(String userId, String userName, String orgName, String informationId, String orgIdString, String domainId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      pg.put(informationId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setBrowserKits", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to setBrowser information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getBrowser(String informationId, String searchName) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(searchName, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getBrowser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getBrowser information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getHistoryVersion(String informationId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getHistoryVersion", pg.getParameters());
    } catch (Exception e) {
      logger.error("errot to getHistoryVersion information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public List getComment(String informationId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getComment information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getOrderedComment(String informationId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getOrderedComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getComment information :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean setComment(String userId, String userName, String orgName, String content, String informationId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      pg.put(content, String.class);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setComment information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean setComment(String userId, String userName, String orgName, String content, String informationId, String commentParentId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(orgName, String.class);
      pg.put(content, String.class);
      pg.put(informationId, String.class);
      pg.put(commentParentId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setComment information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean updateComment(String content, String commentId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(content, String.class);
      pg.put(commentId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("updateComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateComment information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean setKits(String informationId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("setKits", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to setKits information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean saveHistory(String informationId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("saveHistory", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to saveHistory information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean update(String informationId, String[] parameters, String[] assoInfo, String[] infoAppendName, String[] infoAppendSaveName, String[] infoPicName, String[] infoPicSaveName) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(informationId, String.class);
      pg.put(parameters, String[].class);
      pg.put(assoInfo, String[].class);
      pg.put(infoAppendName, String[].class);
      pg.put(infoAppendSaveName, String[].class);
      pg.put(infoPicName, String[].class);
      pg.put(infoPicSaveName, String[].class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("update", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean deleteAccessory(String informationId, String accessory) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(accessory, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("deleteAccessory", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to deleteAccessory information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public List getSingleHistInfo(String historyId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(historyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleHistInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleHistInfo information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public boolean commend(String[] batchId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(batchId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("commend", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to commend information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean removeCommend(String id) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("removeCommend", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to commend information :" + e.getMessage());
    } 
    return result;
  }
  
  public List batchDelete(String[] batchId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(batchId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (List)ejbProxy.invoke("batchDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to commend information :" + e.getMessage());
    } 
    return result;
  }
  
  public List allDelete(String channelId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (List)ejbProxy.invoke("allDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to commend information :" + e.getMessage());
    } 
    return result;
  }
  
  public List singleDelete(String informationId) {
    return singleDelete("", informationId);
  }
  
  public List singleDelete(String channelId, String informationId) {
    List result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(channelId, String.class);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (List)ejbProxy.invoke("singleDelete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to commend information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean transfer(String[] infoId, String channelId, String orchannelId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(infoId, String[].class);
      pg.put(channelId, String.class);
      pg.put(orchannelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("transfer", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to transfer information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getNew(String userId, String orgId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getNew", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNew information : " + e.getMessage());
    } 
    return list;
  }
  
  public String getContent(String informationId) {
    String content = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      content = (String)ejbProxy.invoke("getContent", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getContent information :" + e.getMessage());
    } 
    return content;
  }
  
  public String getUserViewCh(String userId, String orgId, String channelType, String userDefine) {
    String hSql = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      hSql = (String)ejbProxy.invoke("getUserViewCh", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh information :" + e.getMessage());
    } 
    return hSql;
  }
  
  public List getUserViewCh2(String userId, String orgId, String channelType, String userDefine) {
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getUserViewCh2", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh2 information :" + e.getMessage());
    } 
    return list;
  }
  
  public String getUserViewCh3(String userId, String orgId, String channelType, String userDefine) {
    String hSql = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      hSql = (String)ejbProxy.invoke("getUserViewCh3", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh information :" + e.getMessage());
    } 
    return hSql;
  }
  
  public String getManagedChannel(String userId, String orgId, String channelType, String userDefine) {
    String hSql = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      hSql = (String)ejbProxy.invoke("getManagedChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh information :" + e.getMessage());
    } 
    return hSql;
  }
  
  public String getAllInfoChannel(String userId, String orgId, String channelType, String userDefine) {
    String hSql = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      hSql = (String)ejbProxy.invoke("getManagedChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh information :" + e.getMessage());
    } 
    return hSql;
  }
  
  public List getinformation(String informationId) {
    List contentID = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      contentID = (List)ejbProxy.invoke("getinformation", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getContent information :" + e.getMessage());
    } 
    return contentID;
  }
  
  public List getinformationID(String informationId_same, String informationId, String domainId) {
    List contentID_same = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationId_same, String.class);
      pg.put(informationId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      contentID_same = (List)ejbProxy.invoke("getinformationID", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getContent information :" + e.getMessage());
    } 
    return contentID_same;
  }
  
  public String getInfoReader(String userId, String orgId, String orgIdString, String alias) {
    String reader = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(alias, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      reader = (String)ejbProxy.invoke("getInfoReader", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getContent information :" + e.getMessage());
    } 
    return reader;
  }
  
  public List getAssociateInfo(String orgId, String infoId, String userId, String orgIdString, String channelType, String userDefine) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(orgId, String.class);
      pg.put(infoId, String.class);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getAssociateInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAssociateInfo information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public void updateProcInfo(String infoId, List fieldValueList) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(infoId, String.class);
      pg.put(fieldValueList, List.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("updateProcInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateProcInfo information :" + e.getMessage());
    } 
  }
  
  public String getAccessoryType(String infoId) {
    String aType = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(infoId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      aType = (String)ejbProxy.invoke("getAccessoryType", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAccessoryType information :" + 
          e.getMessage());
    } 
    return aType;
  }
  
  public void save(InformationPO informationPO) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationPO, InformationPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
  }
  
  public List getNotBrowser(String informationId, String searchName, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationId, String.class);
      pg.put(searchName, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getNotBrowser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNotBrowser information :" + e.getMessage());
    } 
    return list;
  }
  
  public Object[] getAllBrowser(String informationId) {
    Object[] obj = (Object[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      obj = (Object[])ejbProxy.invoke("getAllBrowser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllBrowser information :" + e.getMessage());
    } 
    return obj;
  }
  
  public Integer getUserIssueInfoCount(String userId) {
    Integer count = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      count = (Integer)ejbProxy.invoke("getUserIssueInfoCount", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNotBrowser information :" + e.getMessage());
    } 
    return count;
  }
  
  public Integer setDossierStatus(String informationId, String dossierStatus) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(dossierStatus, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Integer)ejbProxy.invoke("setDossierStatus", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNotBrowser information :" + e.getMessage());
    } 
    return result;
  }
  
  public Boolean vindicateInfo(String userId, String orgId, String informationId) {
    Boolean result = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Boolean)ejbProxy.invoke("vindicateInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to vindicateInfo information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getInfoUserdefine(String informationId) {
    String userDefine = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      userDefine = (String)ejbProxy.invoke("getInfoUserdefine", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getInfoUserdefine information :" + 
          e.getMessage());
    } 
    return userDefine;
  }
  
  public Map getMustReadCount(String userIds) {
    Map<Object, Object> result = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Map<Object, Object>)ejbProxy.invoke("getMustReadCount", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getMustReadCount information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public List getMustReadInfo(String userIds, String domainId) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userIds, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (List)ejbProxy.invoke("getMustReadInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getMustReadInfo information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public Integer setOrderCode(String informationId, String channelType, String orderNum, String dateTime, String typeState, String active) {
    Integer result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(informationId, String.class);
      pg.put(channelType, String.class);
      pg.put(orderNum, String.class);
      pg.put(dateTime, String.class);
      pg.put(typeState, String.class);
      pg.put(active, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (Integer)ejbProxy.invoke("setOrderCode", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setOrderCode information :" + e.getMessage());
    } 
    return result;
  }
  
  public String[] getSingleEditor(String documentEditor, String informationIssuer, String year, String month) {
    String[] maplist = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(documentEditor, String.class);
      pg.put(informationIssuer, String.class);
      pg.put(year, String.class);
      pg.put(month, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      maplist = (String[])ejbProxy.invoke("getSingleEditor", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNotBrowser information :" + e.getMessage());
    } 
    return maplist;
  }
  
  public boolean channelCanView(String userId, String orgId, String channelType, String userDefine, String channelId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("channelCanView", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to channelCanView information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean channelCanView2(String userId, String orgId, String channelType, String userDefine, String channelId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("channelCanView2", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to channelCanView information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer delComment(String commentId) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(commentId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", "InformationEJBLocal", InformationEJBHome.class);
      result = (Integer)ejbProxy.invoke("delComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delComment information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getInformationModiIds(String channelId, String userId, String orgId, String orgIdString, String infoIds, List rightList) {
    String result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(channelId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(infoIds, String.class);
      pg.put(rightList, List.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("getInformationModiIds", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delComment information :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleteHistory(String historyId, String informationId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(historyId, String.class);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", "InformationEJBLocal", InformationEJBHome.class);
      result = (String)ejbProxy.invoke("deleteHistory", pg.getParameters());
    } catch (Exception ex) {
      logger.error("error to deleteHistory information :" + ex.getMessage());
    } 
    return result;
  }
  
  public List getAfficheList(String domainId, String userId, String orgId, String orgIdString) {
    String hasRight = "-1";
    String rightCode = "01*03*03";
    List<Object[]> rightScopeList = (new ManagerService()).getRightScope(userId, rightCode);
    if (rightScopeList != null && rightScopeList.size() > 0 && rightScopeList.get(0) != null) {
      Object[] obj = rightScopeList.get(0);
      if ("0".equals(obj[0].toString()))
        hasRight = "1"; 
    } 
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(hasRight, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (List)ejbProxy.invoke("getAfficheList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAfficheList information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public List getAllGroupByUserId(String userId) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = (List)ejbProxy.invoke("getAllGroupByUserId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllGroupByUserId information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public List getAssociateInfo(String orgId, String infoId, String userId, String orgIdString, String channelType, String userDefine, String channelStatusType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(orgId, String.class);
      pg.put(infoId, String.class);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      pg.put(channelStatusType, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getAssociateInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAssociateInfo information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public boolean checkReaded(String informationId, String userId) {
    boolean result = false;
    List list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      list = (List)ejbProxy.invoke("getBrowByEmpAndIfoId", pg.getParameters());
      if (list != null && list.size() > 0)
        result = true; 
    } catch (Exception e) {
      logger.error("error to channelCanView information :" + e.getMessage());
    } 
    return result;
  }
  
  public int getIssueNumOrg(String orgId) {
    int result = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = ((Integer)ejbProxy.invoke("getIssueNumOrg", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to channelCanView information :" + e.getMessage());
    } 
    return result;
  }
  
  public int getIssueNumPerson(String userId) {
    int result = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationEJB", 
          "InformationEJBLocal", InformationEJBHome.class);
      result = ((Integer)ejbProxy.invoke("getIssueNumPerson", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to channelCanView information :" + e.getMessage());
    } 
    return result;
  }
  
  public Integer getNumForOrg(String orgId, Long infoid, String[] rangeArr) throws Exception {
    Integer rs = Integer.valueOf("0");
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    rs = informationEJBBean.getNumForOrg(orgId, infoid, rangeArr);
    return rs;
  }
  
  public Integer getNumForGroup(String groupId, Long infoId) throws Exception {
    Integer rs = Integer.valueOf("0");
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    rs = informationEJBBean.getNumForGroup(groupId, infoId);
    return rs;
  }
  
  public List getBrowser1(String orgId, Long infoid) throws Exception {
    List browserlist = null;
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    browserlist = informationEJBBean.getBrowser1(orgId, infoid);
    return browserlist;
  }
  
  public List getNoBrowser1(String orgId, Long infoid) throws Exception {
    List browserlist = new ArrayList();
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    browserlist = informationEJBBean.getNoBrowser1(orgId, infoid);
    return browserlist;
  }
  
  public List getBrowserInGroup(String groupId, Long infoId) throws Exception {
    List browserlist = null;
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    browserlist = informationEJBBean.getBrowserInGroup(groupId, infoId);
    return browserlist;
  }
  
  public List getNoBrowserInGroup(String groupId, Long infoId) throws Exception {
    List browserlist = null;
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    browserlist = informationEJBBean.getNoBrowserInGroup(groupId, infoId);
    return browserlist;
  }
  
  public List getBrowserInUsers(String userIds, Long infoId) throws Exception {
    List browserlist = null;
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    browserlist = informationEJBBean.getBrowserInUsers(userIds, infoId);
    return browserlist;
  }
  
  public List getNoBrowserInUsers(String userIds, Long infoId) throws Exception {
    List browserlist = null;
    InformationEJBBean informationEJBBean = new InformationEJBBean();
    browserlist = informationEJBBean.getNoBrowserInUsers(userIds, infoId);
    return browserlist;
  }
  
  public boolean saveDocFile(List fileList, String empId) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.saveDocFile(fileList, empId);
  }
  
  public void recordReader(String userId, String userName, String orgId, String orgName, String orgIdString, String informationId) {
    InformationEJBBean bean = new InformationEJBBean();
    try {
      bean.setBrowser(userId, userName, orgName, informationId, orgIdString);
      bean.setKits(informationId);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public String getCanReadUserIds(String userId, String informationId) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getCanReadUserIds(userId, informationId);
  }
  
  public List getTopTime(String informationId, String typeState) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getTopTime(informationId, typeState);
  }
  
  public void resetOrderCode() {
    InformationEJBBean bean = new InformationEJBBean();
    bean.resetOrderCode();
  }
  
  public int getMaxOrderCodeByTypeState(String typeState) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getMaxOrderCodeByTypeState(typeState);
  }
  
  public String[] getMessageTitleAndUsers(String informationId, String reprocess) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getMessageTitleAndUsers(informationId, reprocess);
  }
  
  public String getProChannelId(String proId) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getProChannelId(proId);
  }
  
  public String getAllChildChannelIds(String parentId) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getAllChildChannelIds(parentId);
  }
  
  public String getInfoReaderNum(String informationId) {
    InformationEJBBean bean = new InformationEJBBean();
    return bean.getInfoReaderNum(informationId);
  }
  
  public InformationPO getInformationPoById(Long id) {
    return (new InformationEJBBean()).getInformationPoById(id);
  }
}
