package com.js.oa.info.channelmanager.service;

import com.js.oa.info.channelmanager.bean.ChannelEJBBean;
import com.js.oa.info.channelmanager.bean.ChannelEJBHome;
import com.js.oa.info.channelmanager.po.DepartmentStylePO;
import com.js.oa.info.channelmanager.po.InformationChannelPO;
import com.js.oa.info.channelmanager.po.UserChannelPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class ChannelBD {
  private static Logger logger = Logger.getLogger(ChannelBD.class.getName());
  
  public List getAllChannel(String channelType, String domainId, String corpId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(channelType, String.class);
      pg.put(domainId, String.class);
      pg.put(corpId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getAllChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Long add(InformationChannelPO informationChannelPO, String channelOrderId, String radiobutton) {
    Long result = Long.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationChannelPO, InformationChannelPO.class);
      pg.put(channelOrderId, String.class);
      pg.put(radiobutton, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = (Long)ejbProxy.invoke("add", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to add channel information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getSingleChannel(String channelId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getSingleChannelName(String channelIdString) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getSingleChannelName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleChannelName information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean modify(InformationChannelPO informationChannelPO, String channelOrderId, String radiobutton) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(informationChannelPO, InformationChannelPO.class);
      pg.put(channelOrderId, String.class);
      pg.put(radiobutton, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("modify", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to modify channel information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getBrotherCh(String channelId, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(channelId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getBrotherCh", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getBrotherCh information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getPublicChannel(String channelType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getPublicChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPublicChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public InformationChannelPO loadChannel(String channelId) {
    InformationChannelPO informationChannelPO = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      informationChannelPO = (InformationChannelPO)ejbProxy.invoke("loadChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to loadChannel information :" + e.getMessage());
    } finally {}
    return informationChannelPO;
  }
  
  public Object[] getAccessory(String channelId) {
    Object[] obj = (Object[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      obj = (Object[])ejbProxy.invoke("getAccessory", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAccessory information :" + e.getMessage());
    } finally {}
    return obj;
  }
  
  public List getUserViewCh(String userId, String orgId, String channelType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getUserViewCh", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getCanIssue(String where) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getCanIssue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanIssue information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean canIssue(String userId, String orgId, String channelType) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("canIssue", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to canIssue information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean canVindicate(String userId, String orgId, String channelId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("canVindicate", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to canVindicate information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getCanVindicate(String userId, String orgId, String channelType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getCanVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanVindicate information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getOrgChannel(String orgId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getOrgChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOrgChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void addUserChannel(UserChannelPO po) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, UserChannelPO.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("addUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to addUserChannel information :" + e.getMessage());
    } 
  }
  
  public boolean deleteUserChannel(String userChannelId) {
    Boolean result = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userChannelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = (Boolean)ejbProxy.invoke("deleteUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteUserChannel inforamtion :" + e.getMessage());
    } finally {}
    return result.booleanValue();
  }
  
  public String getUserChannelName(String userChannelId) {
    String userChannelName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userChannelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      userChannelName = (String)ejbProxy.invoke("getUserChannelName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteUserChannel inforamtion :" + e.getMessage());
    } finally {}
    return userChannelName;
  }
  
  public String getChannelName(String userChannelId) {
    String channelName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userChannelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      channelName = (String)ejbProxy.invoke("getChannelName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelName inforamtion :" + e.getMessage());
    } finally {}
    return channelName;
  }
  
  public void updateUserChannel(UserChannelPO po) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, UserChannelPO.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("updateUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteUserChannel inforamtion :" + e.getMessage());
    } 
  }
  
  public List getUserChannel(String domainId, String flag) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, String.class);
      pg.put(flag, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getUserChannel(String domainId, String userId, String orgId, String orgIdString) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserChannel information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getChannelMenu(String rightWhere, String scopeWhere, String channelType, String domainId, String corpId, String sidelineCorpId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(rightWhere, String.class);
      pg.put(scopeWhere, String.class);
      pg.put(channelType, String.class);
      pg.put(domainId, String.class);
      pg.put(corpId, String.class);
      pg.put(sidelineCorpId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getChannelMenu", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelMenu information :" + e.getMessage());
      e.printStackTrace();
    } 
    return list;
  }
  
  public List getChannelMenu(String scopeWhere, String channelType, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(scopeWhere, String.class);
      pg.put(channelType, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getChannelMenu", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelMenu information :" + e.getMessage());
    } 
    return list;
  }
  
  public String[] getSortChannel(String channelId, String channelType) {
    String[] result = { "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(channelId, String.class);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = (String[])ejbProxy.invoke("getSortChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSortChannel information :" + e.getMessage());
    } 
    return result;
  }
  
  public void modifyByArray(String[] para, String channelOrderId, String radiobutton) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(para, String[].class);
      pg.put(channelOrderId, String.class);
      pg.put(radiobutton, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("modifyByArray", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to modifyByArray information :" + e.getMessage());
    } 
  }
  
  public boolean canOnDesktop() {
    Boolean canOnDesktop = new Boolean(false);
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      canOnDesktop = (Boolean)ejbProxy.invoke("canOnDesktop", (Object[][])null);
    } catch (Exception e) {
      logger.error("error to canOnDesktop information :" + e.getMessage());
    } 
    return canOnDesktop.booleanValue();
  }
  
  public List getChannelPosition() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getChannelPosition", (Object[][])null);
    } catch (Exception e) {
      logger.error("error to getChannelPosition information :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean departPageRight(String userId, String userOrg, String userOrgString, String orgId) {
    Boolean flag = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(userOrg, String.class);
      pg.put(userOrgString, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      flag = (Boolean)ejbProxy.invoke("departPageRight", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to departPageRight information :" + e.getMessage());
    } 
    return flag.booleanValue();
  }
  
  public DepartmentStylePO getDepaStyle(String styleId) {
    DepartmentStylePO stylePO = new DepartmentStylePO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put("2", String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      stylePO = (DepartmentStylePO)ejbProxy.invoke("getDepaStyle", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDepaStyle information :" + e.getMessage());
    } 
    return stylePO;
  }
  
  public Boolean canOnDepaDesk(String channelType) {
    Boolean flag = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      flag = (Boolean)ejbProxy.invoke("canOnDepaDesk", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to canOnDepaDesk information :" + e.getMessage());
    } 
    return flag;
  }
  
  public List getOtherPositionCh(String positionId, String userId, String orgId) {
    ArrayList chList = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(positionId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      chList = (ArrayList)ejbProxy.invoke("getOtherPositionCh", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to canOnDepaDesk information :" + e.getMessage());
    } 
    return chList;
  }
  
  public Long getChannelProcessId(String channelId) {
    Long processId = new Long(0L);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      processId = (Long)ejbProxy.invoke("getChannelProcessId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelProcessId information :" + e.getMessage());
    } 
    return processId;
  }
  
  public String getChannelProcessInfo(String channelId) {
    String info = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      info = (String)ejbProxy.invoke("getChannelProcessInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelProcessId information :" + e.getMessage());
    } 
    return info;
  }
  
  public List getAllCanIssueWithoutCheck(String userId, String orgId, String domainId, String test) {
    List chList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(domainId, String.class);
      pg.put(test, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      chList = (List)ejbProxy.invoke("getAllCanIssueWithoutCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllCanIssueWithoutCheck information :" + e.getMessage());
    } 
    return chList;
  }
  
  public boolean canVindicate(String userId, String orgId, String channelType, String channelId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(channelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("canVindicate", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to canVindicate information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getCanIssue(String where, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(where, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getCanIssue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanIssue information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAfficheCanIssue(String where, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(where, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getAfficheCanIssue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAfficheCanIssue information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllCanIssueWithoutCheck(String userId, String orgId, String userDefine) {
    List chList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(userDefine, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      chList = (List)ejbProxy.invoke("getAllCanIssueWithoutCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllCanIssueWithoutCheck information :" + e.getMessage());
    } 
    return chList;
  }
  
  public List getUserViewCh(String userId, String orgId, String channelType, String userDefine, String domainId, String corpId, String sidelineCorpId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      pg.put(domainId, String.class);
      pg.put(corpId, String.class);
      pg.put(sidelineCorpId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getUserViewCh", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserViewCh information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getProBindListByUserId(String userId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getProBindListByUserId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProBindList information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void addUserChannel(String userChannelName, String userChannelOrder, String domainId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userChannelName, String.class);
      pg.put(userChannelOrder, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("addUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to addUserChannel information :" + e.getMessage());
    } 
  }
  
  public void updateUserChannel(String userChannelId, String userChannelName, String userChannelOrder) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userChannelId, String.class);
      pg.put(userChannelName, String.class);
      pg.put(userChannelOrder, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("updateUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteUserChannel inforamtion :" + e.getMessage());
    } 
  }
  
  public UserChannelPO getUserChannel(String userChannelId) {
    UserChannelPO userChannelPO = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userChannelId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      userChannelPO = (UserChannelPO)ejbProxy.invoke("getUserChannel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteUserChannel inforamtion :" + e.getMessage());
    } finally {}
    return userChannelPO;
  }
  
  public boolean update(String informationId, String[] parameters, String[] assoInfo, String[] infoAppendName, String[] infoAppendSaveName, String[] infoPicName, String[] infoPicSaveName, Date historydate) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(8);
      pg.put(informationId, String.class);
      pg.put(parameters, String[].class);
      pg.put(assoInfo, String[].class);
      pg.put(infoAppendName, String[].class);
      pg.put(infoAppendSaveName, String[].class);
      pg.put(infoPicName, String[].class);
      pg.put(infoPicSaveName, String[].class);
      pg.put(historydate, Date.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      ejbProxy.invoke("update", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String hasChannelViewRight(String userId, String orgId, String channelType, String userDefine, String channelId, String domainId, String corpId, String sidelineCorpId) {
    String right = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(8);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(channelType, String.class);
      pg.put(userDefine, String.class);
      pg.put(channelId, String.class);
      pg.put(domainId, String.class);
      pg.put(corpId, String.class);
      pg.put(sidelineCorpId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      right = ejbProxy.invoke("hasChannelViewRight", pg.getParameters()).toString();
    } catch (Exception e) {
      logger.error("error to hasChannelViewRight information :" + e.getMessage());
    } finally {}
    return right;
  }
  
  public boolean isChannelManager(String channelId, String userId, String orgId, String orgIdString) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(channelId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      result = ((Boolean)ejbProxy.invoke("isChannelManager", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to isChannelManager information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getInformationProcessId(String informationId) {
    String rs = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      rs = (String)ejbProxy.invoke("getInformationProcessId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChannelProcessId information :" + e.getMessage());
    } 
    return rs;
  }
  
  public List getUserManageList(String userId, String orgId, String orgIdString, String channelType, String userDefine, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    pg.put(userId, String.class);
    pg.put(orgId, String.class);
    pg.put(orgIdString, String.class);
    pg.put(channelType, String.class);
    pg.put(userDefine, String.class);
    pg.put(domainId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getUserManageList", pg.getParameters());
    } catch (Exception ex) {
      logger.error("error to getUserManageList information :" + ex.getMessage());
    } 
    return list;
  }
  
  public List getChannelMenu_ByType(String rightWhere, String scopeWhere, String channelType, String domainId, String type) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(5);
    pg.put(rightWhere, String.class);
    pg.put(scopeWhere, String.class);
    pg.put(channelType, String.class);
    pg.put(domainId, String.class);
    pg.put(type, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getChannelMenu_ByType", pg.getParameters());
    } catch (Exception ex) {
      logger.error("error to getChannelMenu_ByType information :" + ex.getMessage());
    } 
    return list;
  }
  
  public List getAllChannel_ByType(String channelType, String domainId, String type) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    pg.put(channelType, String.class);
    pg.put(domainId, String.class);
    pg.put(type, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getAllChannel_ByType", pg.getParameters());
    } catch (Exception ex) {
      logger.error("error to getAllChannel_ByType information :" + ex.getMessage());
    } 
    return list;
  }
  
  public List getIsoCanIssue(String where, String domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    pg.put(where, String.class);
    pg.put(domainId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", 
          "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getIsoCanIssue", 
          pg.getParameters());
    } catch (Exception ex) {
      logger.error("error to getIsoCanIssue information :" + 
          ex.getMessage());
    } 
    return list;
  }
  
  public List getAllIsoChannel(String type) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(type, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", 
          "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getAllIsoChannel", 
          pg.getParameters());
    } catch (Exception ex) {
      logger.error("error to getAllIsoChannel information :" + 
          ex.getMessage());
    } 
    return list;
  }
  
  public List getBrotherCh_ByChannelStatusType(String channelId, String domainId, String channelStatusType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(channelId, String.class);
      pg.put(domainId, String.class);
      pg.put(channelStatusType, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", "ChannelEJBLocal", ChannelEJBHome.class);
      list = (List)ejbProxy.invoke("getBrotherCh_ByChannelStatusType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getBrotherCh_ByChannelStatusType information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public Object[] deleteInformation(String channelId, String type) {
    Object[] obj = (Object[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(channelId, String.class);
      pg.put(type, String.class);
      EJBProxy ejbProxy = new EJBProxy("ChannelEJB", 
          "ChannelEJBLocal", ChannelEJBHome.class);
      obj = (Object[])ejbProxy.invoke("deleteInformation", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAccessory information :" + e.getMessage());
    } finally {}
    return obj;
  }
  
  public String channelCanIssue(String where, String channelId) {
    ChannelEJBBean bean = new ChannelEJBBean();
    try {
      return bean.channelCanIssue(where, channelId);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public String getIsAllowView(String type, String channelId) {
    ChannelEJBBean bean = new ChannelEJBBean();
    try {
      return bean.getIsAllowView(type, channelId);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public String channelNeedFlow(String channelId) {
    ChannelEJBBean bean = new ChannelEJBBean();
    try {
      return bean.channelNeedFlow(channelId);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    } 
  }
  
  public String getParticipationProId(String userId) {
    ChannelEJBBean bean = new ChannelEJBBean();
    String returnValueString = "";
    try {
      returnValueString = bean.getParticipationProId(userId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnValueString;
  }
  
  public String[][] getChannelSimpleInfoByCorpId(String corpId, int flag) {
    ChannelEJBBean bean = new ChannelEJBBean();
    String[][] ret = (String[][])null;
    try {
      ret = bean.getChannelSimpleInfoByCorpId(corpId, flag);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return ret;
  }
  
  public List getChannelInfoList(String channelId, String curUserId) {
    ChannelEJBBean bean = new ChannelEJBBean();
    List infoList = null;
    try {
      infoList = bean.getChannelInfoList(channelId, curUserId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infoList;
  }
  
  public String canIssueChannel(String userId, String orgId, String channelId) {
    ChannelEJBBean bean = new ChannelEJBBean();
    String result = "-1";
    try {
      result = bean.canIssueChannel(userId, orgId, channelId);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
}
