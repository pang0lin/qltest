package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.IntoOutStockEJBHome;
import com.js.oa.routine.resource.po.CsMasterPO;
import com.js.oa.routine.resource.po.PtMasterPO;
import com.js.oa.routine.resource.po.SsMasterPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class IntoOutStockBD {
  private static Logger logger = Logger.getLogger(IntoOutStockBD.class.getName());
  
  public Map save(PtMasterPO ptMasterPO, Object[] ptDetail) {
    Map<Object, Object> success = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ptMasterPO, PtMasterPO.class);
      pg.put(ptDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Map<Object, Object>)ejbProxy.invoke("saveIntoStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveIntoStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Map getIntoStock(String ptMasterId) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ptMasterId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      hashMap = (HashMap<Object, Object>)ejbProxy.invoke("getIntoStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIntoStock information :" + e.getMessage());
    } 
    return hashMap;
  }
  
  public Boolean updateIntoStock(PtMasterPO ptMasterPO, Object[] ptDetail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ptMasterPO, PtMasterPO.class);
      pg.put(ptDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("updateIntoStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateIntoStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean unCheckIntoStock(String ptMasterId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ptMasterId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("unCheckIntoStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to unCheckIntoStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean deleteIntoStock(String ids) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("deleteIntoStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteIntoStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Long saveOutStock(SsMasterPO ssMasterPO, Object[] detail) {
    Long success = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ssMasterPO, SsMasterPO.class);
      pg.put(detail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Long)ejbProxy.invoke("saveOutStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveOutStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Long saveDirectOutStock(SsMasterPO ssMasterPO, Object[] detail) {
    Long success = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ssMasterPO, SsMasterPO.class);
      pg.put(detail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Long)ejbProxy.invoke("saveDirectOutStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveOutStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Map getOutStock(String ssMasterId) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ssMasterId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      hashMap = (HashMap<Object, Object>)ejbProxy.invoke("getOutStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOutStock information :" + e.getMessage());
    } 
    return hashMap;
  }
  
  public Boolean updateOutStock(SsMasterPO ssMasterPO, Object[] ssDetail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ssMasterPO, SsMasterPO.class);
      pg.put(ssDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("updateOutStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateOutStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean updateBackOutStockAmnout(SsMasterPO ssMasterPO, Object[] ssDetail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ssMasterPO, SsMasterPO.class);
      pg.put(ssDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("updateBackOutStockAmnout", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateOutStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean updateOutStockAmnout(SsMasterPO ssMasterPO, Object[] ssDetail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ssMasterPO, SsMasterPO.class);
      pg.put(ssDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("updateOutStockAmnout", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateOutStockAmnout information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean uncheckOutStock(String ssMasterId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ssMasterId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("uncheckOutStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to uncheckOutStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean deleteOutStock(String ids, String tableId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ids, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("deleteOutStock", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteOutStock information :" + e.getMessage());
    } 
    return success;
  }
  
  public List getStockGoods(String stockId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getStockGoods", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getStockGoods information :" + e.getMessage());
    } 
    return alist;
  }
  
  public Boolean saveStockCheck(CsMasterPO csMasterPO, Object[] detail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(csMasterPO, CsMasterPO.class);
      pg.put(detail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("saveStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveStockCheck information :" + e.getMessage());
    } 
    return success;
  }
  
  public Map getStockCheck(String csMasterId) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(csMasterId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      hashMap = (HashMap<Object, Object>)ejbProxy.invoke("getStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getStockCheck information :" + e.getMessage());
    } 
    return hashMap;
  }
  
  public Boolean updateStockCheck(CsMasterPO csMasterPO, Object[] csDetail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(csMasterPO, CsMasterPO.class);
      pg.put(csDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("updateStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateStockCheck information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean uncheckStockCheck(String csMasterId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(csMasterId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("uncheckStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to uncheckStockCheck information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean deleteStockCheck(String ids) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("deleteStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteStockCheck information :" + e.getMessage());
    } 
    return success;
  }
  
  public String getGoodsAmount(String id, String ck) {
    String returnValue = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(ck, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      returnValue = (String)ejbProxy.invoke("getGoodsAmount", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getGoodsAmount information :" + e.getMessage());
    } 
    return returnValue;
  }
  
  public List getWFProcessInfoByStockId1(String stockId) {
    List alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      alist = (List)ejbProxy.invoke("getWFProcessInfoByStockId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWFProcessInfoByStockId information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getWFProcessInfoByStockId(String stockId, String type) {
    List alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(stockId, String.class);
      pg.put(type, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      alist = (List)ejbProxy.invoke("getWFProcessInfoByStockId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWFProcessInfoByStockId information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String getStockCharger(String stockId) {
    String success = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (String)ejbProxy.invoke("getStockCharger", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getStockCharger information :" + e.getMessage());
    } 
    return success;
  }
  
  public Integer updateOutStockCheck(String id, String checkStr) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(checkStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateOutStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateOutStockCheck information :" + e.getMessage());
    } 
    return result;
  }
  
  public Map updateOutFlag(String ids) {
    Map<Object, Object> ret = new HashMap<Object, Object>(0);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      ret = (Map<Object, Object>)ejbProxy.invoke("updateOutFlag", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateOutFlag information :" + e.getMessage());
    } 
    return ret;
  }
  
  public Boolean updateIntoStockMoney(PtMasterPO ptMasterPO, Object[] ptDetail) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ptMasterPO, PtMasterPO.class);
      pg.put(ptDetail, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      success = (Boolean)ejbProxy.invoke("updateIntoStockMoney", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateIntoStockMoney information :" + e.getMessage());
    } 
    return success;
  }
  
  public Integer updateIntoStockCheck(String id, String checkStr) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(id, String.class);
      pg.put(checkStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("IntoOutStockEJB", "IntoOutStockEJBLocal", IntoOutStockEJBHome.class);
      result = (Integer)ejbProxy.invoke("updateIntoStockCheck", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateIntoStockCheck information :" + e.getMessage());
    } 
    return result;
  }
}
