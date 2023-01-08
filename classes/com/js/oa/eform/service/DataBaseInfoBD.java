package com.js.oa.eform.service;

import com.js.oa.eform.bean.DataBaseInfoEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class DataBaseInfoBD {
  private static Logger logger = Logger.getLogger(DataBaseInfoBD.class.getName());
  
  public String[][] getTableInfo(String domainId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DataBaseInfoEJB", "DataBaseInfoEJBLocal", DataBaseInfoEJBHome.class);
      result = (String[][])ejbProxy.invoke("getTableInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("DataBaseInfoEJB error on getCode information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getTableInfoByRange(String domainId, String userId, String orgId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DataBaseInfoEJB", "DataBaseInfoEJBLocal", DataBaseInfoEJBHome.class);
      result = (String[][])ejbProxy.invoke("getTableInfoByRange", pg.getParameters());
    } catch (Exception e) {
      logger.error("DataBaseInfoEJB error on getCode information:" + e.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getFieldInfo(String tableId) {
    String[][] result = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("DataBaseInfoEJB", "DataBaseInfoEJBLocal", DataBaseInfoEJBHome.class);
      result = (String[][])ejbProxy.invoke("getFieldInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("DataBaseInfoEJB error on getCode information:" + e.getMessage());
    } finally {}
    return result;
  }
}
