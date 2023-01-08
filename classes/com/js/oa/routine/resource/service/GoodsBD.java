package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.GoodsEJBHome;
import com.js.oa.routine.resource.po.GoodsPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class GoodsBD {
  private static Logger logger = Logger.getLogger(GoodsBD.class.getName());
  
  public Boolean save(GoodsPO goodsPO, String goodsTypeId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(goodsPO, GoodsPO.class);
      pg.put(goodsTypeId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsEJB", "GoodsEJBLocal", GoodsEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public String[] getSingleGoods(String goodsId) {
    String[] goods = { "", "", "", "", "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(goodsId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsEJB", "GoodsEJBLocal", GoodsEJBHome.class);
      goods = (String[])ejbProxy.invoke("getSingleGoods", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSingleGoods information :" + e.getMessage());
    } 
    return goods;
  }
  
  public Boolean update(GoodsPO goodsPO, String goodsTypeId) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(goodsPO, GoodsPO.class);
      pg.put(goodsTypeId, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsEJB", "GoodsEJBLocal", GoodsEJBHome.class);
      success = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean delete(String ids) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsEJB", "GoodsEJBLocal", GoodsEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean noused(String ids, String opt) {
    Boolean success = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ids, String.class);
      pg.put(opt, String.class);
      EJBProxy ejbProxy = new EJBProxy("GoodsEJB", "GoodsEJBLocal", GoodsEJBHome.class);
      success = (Boolean)ejbProxy.invoke("noused", pg.getParameters());
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
      EJBProxy ejbProxy = new EJBProxy("GoodsEJB", "GoodsEJBLocal", GoodsEJBHome.class);
      ids = (String)ejbProxy.invoke("getVindicate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return ids;
  }
}
