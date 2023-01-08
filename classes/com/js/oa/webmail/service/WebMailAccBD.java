package com.js.oa.webmail.service;

import com.js.oa.webmail.bean.WebMailAccEJBHome;
import com.js.oa.webmail.po.WebMailAcc;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class WebMailAccBD {
  private static Logger logger = Logger.getLogger(WebMailBD.class.getName());
  
  public List getMailAccList() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      list = (List)ejbProxy.invoke("getMailAccList", (Object[][])null);
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public List getMailAccList(Long userId, String account) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      pg.put(userId, Long.class);
      pg.put(account, String.class);
      list = (List)ejbProxy.invoke("getMailAccListByUserAcco", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public List getMailAccListByUserId(Long userId, String flag) {
    ParameterGenerator pg = new ParameterGenerator(2);
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      pg.put(userId, Long.class);
      pg.put(flag, String.class);
      list = (List)ejbProxy.invoke("getMailAccListByUserId", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return list;
  }
  
  public void saveMailAcc(WebMailAcc wma) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      pg.put(wma, WebMailAcc.class);
      ejbProxy.invoke("createMailAcc", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public void removeMailAcc(String id, String[] ids) {
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      pg.put(id, String.class);
      pg.put(ids, String[].class);
      ejbProxy.invoke("delMailAcc", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
  
  public WebMailAcc getMailAccInfo(Long id) {
    ParameterGenerator pg = new ParameterGenerator(1);
    WebMailAcc wma = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      pg.put(id, Long.class);
      wma = (WebMailAcc)ejbProxy.invoke("getMailAccInfoById", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
    return wma;
  }
  
  public void ModMailAccInfo(WebMailAcc webMailAcc) {
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WebMailAccEJB", "WebMailAccEJBLocal", WebMailAccEJBHome.class);
      pg.put(webMailAcc, WebMailAcc.class);
      ejbProxy.invoke("modMailAcc", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to add Mail information:" + e.getMessage());
    } 
  }
}
