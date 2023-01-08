package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.GoodsTypeEJBHome;
import com.js.oa.routine.resource.po.GoodsTypePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class GoodsTypeBD {
  private static Logger logger = Logger.getLogger(GoodsTypeBD.class.getName());
  
  public Boolean save(GoodsTypePO goodsTypePO, String stockId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(goodsTypePO, GoodsTypePO.class);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public String[] getSingleGoodsType(String goodsTypeId) {
    String[] goodsType = { "", "", "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(goodsTypeId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      goodsType = (String[])ejbProxy.invoke("getSingleGoodsType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleGoodsType information :" + e.getMessage());
    } 
    return goodsType;
  }
  
  public Boolean update(GoodsTypePO goodsTypePO, String stockId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(goodsTypePO, GoodsTypePO.class);
      pg.put(stockId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return success;
  }
  
  public String delete(String goodsTypeId) {
    String success = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(goodsTypeId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      Boolean ret = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
      if (!ret.booleanValue())
        success = "删除失败！您选择的物品类别下有物品信息"; 
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return success;
  }
  
  public String getVindicate(String where) {
    String ids = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(where, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      ids = (String)ejbProxy.invoke("getVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getVindicate information :" + e.getMessage());
    } 
    return ids;
  }
  
  public List getUserManaGoodsTypeSub(String userId, String flag) {
    ArrayList goodsTypeList = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, String.class);
      pg.put(flag, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      goodsTypeList = (ArrayList)ejbProxy.invoke("getUserManaGoodsTypeSub", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserManaGoodsTypeSub information :" + e.getMessage());
    } 
    return goodsTypeList;
  }
  
  public List getUserManaGoodsType(String userId) {
    ArrayList goodsTypeList = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      goodsTypeList = (ArrayList)ejbProxy.invoke("getUserManaGoodsType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserManaGoodsType information :" + e.getMessage());
    } 
    return goodsTypeList;
  }
  
  public List getUserManaGoodsTypeParent(String userId) {
    List goodsTypeList = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsTypeEJB", "GoodsTypeEJBLocal", GoodsTypeEJBHome.class);
      goodsTypeList = (List)ejbProxy.invoke("getUserManaGoodsTypeParent", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserManaGoodsTypeParent information :" + e.getMessage());
    } 
    return goodsTypeList;
  }
}
