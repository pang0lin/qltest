package com.js.oa.security.ip.service;

import com.js.oa.security.ip.bean.IPEJBHome;
import com.js.oa.security.ip.po.IPPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class IPBD {
  private static Logger logger = Logger.getLogger(IPBD.class.getName());
  
  public boolean add(IPPO ipPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(ipPO, IPPO.class);
      ejbProxy.invoke("add", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean audit(IPPO ipPO, String opreate, Long id, HttpServletRequest httpServletRequest) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(ipPO, IPPO.class);
      pg.put(opreate, String.class);
      pg.put(id, Long.class);
      pg.put(httpServletRequest, HttpServletRequest.class);
      ejbProxy.invoke("audit", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean autoAudit(IPPO ipPO, String opreate, Long id, HttpServletRequest httpServletRequest) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(ipPO, IPPO.class);
      pg.put(opreate, String.class);
      pg.put(id, Long.class);
      pg.put(httpServletRequest, HttpServletRequest.class);
      ejbProxy.invoke("autoAudit", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to add IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String delete(String id) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(id, String.class);
      result = (String)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to delete IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List selectSingle(String id) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", "IPEJBLocal", IPEJBHome.class);
      pg.put(id, String.class);
      result = (List)ejbProxy.invoke("selectSingle", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to   selectSingle information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modify(IPPO ipPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(ipPO, IPPO.class);
      ejbProxy.invoke("modify", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("Error to modify IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String delBatch(String checkboxIDS) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(checkboxIDS, String.class);
      result = (String)ejbProxy.invoke("delBatch", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to delBatch IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean delAll() {
    boolean result = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      ejbProxy.invoke("delAll", (Object[][])null);
      result = true;
    } catch (Exception e) {
      logger.error("Error to delAll IP information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String pass(String ids) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("IPEJB", 
          "IPEJBLocal", IPEJBHome.class);
      pg.put(ids, String.class);
      result = (String)ejbProxy.invoke("pass", pg.getParameters());
    } catch (Exception e) {
      logger.error("Error to delBatch IP information:" + e.getMessage());
    } finally {}
    return result;
  }
}
