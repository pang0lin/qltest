package com.js.oa.message.service;

import com.js.oa.message.bean.messageSettingHome;
import com.js.oa.message.po.MsLimitPO;
import com.js.oa.message.po.MsModelPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Map;
import org.apache.log4j.Logger;

public class messageSettingBD {
  private static Logger logger = Logger.getLogger(MessageBD.class.getName());
  
  public boolean judgePurviewMessage(String model, String domainId) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(model, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("judgePurviewMessage", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to judgePurviewMessage information :" + e.getMessage());
    } 
    return success;
  }
  
  public String getModleContents(String model, String title, String sendMen, String department, String domainId) {
    String contents = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(model, String.class);
      pg.put(title, String.class);
      pg.put(sendMen, String.class);
      pg.put(department, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      contents = (String)ejbProxy.invoke("getModleContents", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModleContents information :" + e.getMessage());
    } 
    return contents;
  }
  
  public boolean changeAllMembers(Map memberIdName, String memberId, String domainId) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(memberIdName, Map.class);
      pg.put(memberId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("changeAllMembers", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to changeAllMembers information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean whetherRepeaInMsLimit(String id) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("whetherRepeaInMsLimit", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to whetherRepeaInMsLimit information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean addLimitMount(MsLimitPO addMsLimitPO) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(addMsLimitPO, MsLimitPO.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = (Boolean)ejbProxy.invoke("addLimitMount", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to addLimitMount information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean changeLimitMount(MsLimitPO addMsLimitPO) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(addMsLimitPO, MsLimitPO.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("changeLimitMount", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to changeLimitMount information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean deletLimitMounts(String LimitMountIds) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(LimitMountIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("deletLimitMounts", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to deletLimitMounts information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean addModelSelf(MsModelPO modelName) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(modelName, MsModelPO.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("addModelSelf", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to addModelSelf information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean changeModelExist(MsModelPO[] modelSetting, String domainId) {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(modelSetting, MsModelPO[].class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("changeModelExist", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to changeModelExist information :" + e.getMessage());
    } 
    return success;
  }
  
  public boolean allNotExist() {
    boolean success = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(0);
      EJBProxy ejbProxy = new EJBProxy("messageSetting", "messageSettingLocal", messageSettingHome.class);
      success = ((Boolean)ejbProxy.invoke("allNotExist", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to changeModelExist information :" + e.getMessage());
    } 
    return success;
  }
}
