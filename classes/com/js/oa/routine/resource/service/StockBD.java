package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.StockEJBHome;
import com.js.oa.routine.resource.po.StockPO;
import com.js.util.page.Page;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class StockBD {
  private static Logger logger = Logger.getLogger(StockBD.class.getName());
  
  public Boolean save(StockPO stockPO) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockPO, StockPO.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public List getStockIDName(String domainid) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      list = (List)ejbProxy.invoke("getStockIDName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getStockIDName information :" + e.getMessage());
    } 
    return list;
  }
  
  public Boolean delete(String stockIds) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return success;
  }
  
  public String[] getSingleStock(String stockId) {
    String[] stock = { "", "", "", "", "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stock = (String[])ejbProxy.invoke("getSingleStock", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleStock information :" + e.getMessage());
    } 
    return stock;
  }
  
  public Boolean update(StockPO stockPO) {
    Boolean success = new Boolean(true);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockPO, StockPO.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return success;
  }
  
  public String getVindicate(String where) {
    String ids = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      ids = (String)ejbProxy.invoke("getVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getVindicate information :" + e.getMessage());
    } 
    return ids;
  }
  
  public List getUserManaStock(String userId, String domainid) {
    List stockList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stockList = (List)ejbProxy.invoke("getUserManaStock", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getUserManaStock information :" + 
          e.getMessage());
    } 
    return stockList;
  }
  
  public List getUserManaStockSong(String stockWhere, String domainid) {
    List stockList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(stockWhere, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stockList = (List)ejbProxy.invoke("getUserManaStockSong", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getUserManaStockSong information :" + 
          e.getMessage());
    } 
    return stockList;
  }
  
  public List getStockIDName(String domainid, String wherePara) {
    List stockList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainid, String.class);
      pg.put(wherePara, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stockList = (List)ejbProxy.invoke("getStockIDName", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getStockIDName information :" + 
          e.getMessage());
    } 
    return stockList;
  }
  
  public List getUserManaStock(String userId, String orgId, String domainid) {
    List stockList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stockList = (List)ejbProxy.invoke("getUserManaStock", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getUserManaStock information :" + 
          e.getMessage());
    } 
    return stockList;
  }
  
  public boolean isStockPut(String domainId, String userId) {
    boolean ret = false;
    Page page = new Page(
        "po.id", 
        "com.js.oa.routine.resource.po.StockPO po", 
        "where po.domainid=" + domainId + " and po.stockPut like '%$" + userId + "$%'");
    page.setPageSize(999999);
    page.setcurrentPage(1);
    List list = page.getResultList();
    if (list != null && list.size() > 0)
      ret = true; 
    return ret;
  }
  
  public List getWorkFlowStock(Long stockId) {
    List stockList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockId, Long.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stockList = (List)ejbProxy.invoke("getWorkFlowStock", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getWorkFlowStock information :" + 
          e.getMessage());
    } 
    return stockList;
  }
  
  public List getAllStock(String userId, String domainid) {
    List stockList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, String.class);
      pg.put(domainid, String.class);
      EJBProxy ejbProxy = new EJBProxy("StockEJB", 
          "StockEJBLocal", StockEJBHome.class);
      stockList = (List)ejbProxy.invoke("getAllStock", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getAllStock information :" + 
          e.getMessage());
    } 
    return stockList;
  }
}
