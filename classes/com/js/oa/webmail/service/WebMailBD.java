package com.js.oa.webmail.service;

import com.js.oa.webmail.bean.WebMailEJBHome;
import com.js.oa.webmail.po.WebMail;
import com.js.oa.webmail.util.UniqueCode;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class WebMailBD {
  private static Logger logger = Logger.getLogger(WebMailBD.class.getName());
  
  public void createAllPOPMail(List webMailList, List affix) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      pg.put(webMailList, Collection.class);
      pg.put(affix, Collection.class);
      ejbProxy.invoke("createAll", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public void createAllUUID(List uuidList) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      pg.put(uuidList, Collection.class);
      ejbProxy.invoke("createAllUUID", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public List getAllUUIDList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      list = (List)ejbProxy.invoke("getAllUUIDList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllUUIDListById(String userId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    pg.put(userId, String.class);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      list = (List)ejbProxy.invoke("getAllUUIDListById", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public Long createMail(WebMail wm) {
    Long mailInfoId = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      pg.put(wm, WebMail.class);
      mailInfoId = (Long)ejbProxy.invoke("createMail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return mailInfoId;
  }
  
  public void updateMail(WebMail wm) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      pg.put(wm, WebMail.class);
      ejbProxy.invoke("updateMail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public Map getSingleWebMailInfo(String id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Map map = null;
    try {
      pg.put(id, String.class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      map = (Map)ejbProxy.invoke("getSingleWebMail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return map;
  }
  
  public List getWebMailList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      list = (List)ejbProxy.invoke("getWebMailList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getWebMailListByBoxId(String boxid, String mailIds) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      pg.put(boxid, String.class);
      pg.put(mailIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      list = (List)ejbProxy.invoke("getWebMailListByBoxId", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getWebMailList(String[] ids) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(ids, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      list = (List)ejbProxy.invoke("getWebMailList", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } finally {}
    return list;
  }
  
  public void modMailState(WebMail wm) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(wm, WebMail.class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      ejbProxy.invoke("modMailInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void moveMail2_(List list) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(list, Collection.class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      ejbProxy.invoke("moveMail2_", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void delMail(String[] ids) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(ids, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      ejbProxy.invoke("delMail", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public void delAll() {
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      ejbProxy.invoke("delAll", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
  }
  
  public Float getMailboxSize(Long userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    Float result = Float.valueOf("0");
    try {
      pg.put(userId, Long.class);
      EJBProxy ejbProxy = new EJBProxy("WebMailEJB", "WebMailEJBLocal", WebMailEJBHome.class);
      result = (Float)ejbProxy.invoke("getMailboxSize", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to select Mail information:" + e.getMessage());
    } 
    return result;
  }
  
  public String setFileByContent(String content, String tempPath) {
    String result = "";
    Calendar cal = Calendar.getInstance();
    String year = String.valueOf(cal.get(1));
    if (content.length() >= 0) {
      String path = "/upload/" + year + "/webmail/" + UniqueCode.generate() + ".jiusi";
      String saveFullPath = String.valueOf(tempPath) + path;
      String savePath = String.valueOf(tempPath) + "/upload/" + year + "/webmail";
      try {
        if (!(new File(savePath)).isDirectory()) {
          File tempFile = new File(savePath);
          tempFile.mkdirs();
        } 
        FileWriter f = new FileWriter(saveFullPath);
        f.write(content);
        f.flush();
        f.close();
        result = path;
      } catch (Exception e) {
        logger.error("Error to save Email Content to File" + e.getMessage());
      } 
    } else {
      result = content;
    } 
    return result;
  }
}
