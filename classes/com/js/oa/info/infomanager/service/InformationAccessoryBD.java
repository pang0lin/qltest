package com.js.oa.info.infomanager.service;

import com.js.oa.info.infomanager.bean.InformationAccessoryEJBHome;
import com.js.oa.info.infomanager.po.InformationAccessoryPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class InformationAccessoryBD {
  private static Logger logger = Logger.getLogger(InformationBD.class.getName());
  
  public List getAccessory(String informationId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationAccessoryEJB", "InformationAccessoryEJBLocal", InformationAccessoryEJBHome.class);
      list = (List)ejbProxy.invoke("getAccessory", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAccessory information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public boolean updateAccessory(String informationId, InformationAccessoryPO informationAccessoryPO) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(informationId, String.class);
      pg.put(informationAccessoryPO, InformationAccessoryPO.class);
      EJBProxy ejbProxy = new EJBProxy("InformationAccessoryEJB", "InformationAccessoryEJBLocal", InformationAccessoryEJBHome.class);
      ejbProxy.invoke("updateAccessory", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateAccessory information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String getAccessoryFile(String accessoryId) {
    String saveName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(accessoryId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationAccessoryEJB", "InformationAccessoryEJBLocal", InformationAccessoryEJBHome.class);
      saveName = (String)ejbProxy.invoke("getAccessoryFile", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAccessoryFile information :" + e.getMessage());
    } finally {}
    return saveName;
  }
  
  public List getHistAccessory(String historyId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(historyId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationAccessoryEJB", "InformationAccessoryEJBLocal", InformationAccessoryEJBHome.class);
      list = (List)ejbProxy.invoke("getHistAccessory", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getHistAccessory information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getOneInfoPic(String informationId) {
    String saveName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(informationId, String.class);
      EJBProxy ejbProxy = new EJBProxy("InformationAccessoryEJB", "InformationAccessoryEJBLocal", InformationAccessoryEJBHome.class);
      saveName = (String)ejbProxy.invoke("getOneInfoPic", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOneInfoPic information :" + e.getMessage());
    } 
    return saveName;
  }
  
  public void updateAccessory(String informationId, String[] infoPicName, String[] infoPicSaveName, String[] infoAppendName, String[] infoAppendSaveName) {
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(informationId, String.class);
      pg.put(infoPicName, String[].class);
      pg.put(infoPicSaveName, String[].class);
      pg.put(infoAppendName, String[].class);
      pg.put(infoAppendSaveName, String[].class);
      EJBProxy ejbProxy = new EJBProxy("InformationAccessoryEJB", "InformationAccessoryEJBLocal", InformationAccessoryEJBHome.class);
      ejbProxy.invoke("updateAccessory", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateAccessory information :" + e.getMessage());
    } 
  }
}
