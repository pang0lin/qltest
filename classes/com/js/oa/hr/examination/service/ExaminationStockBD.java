package com.js.oa.hr.examination.service;

import com.js.oa.hr.examination.bean.ExaminationStockEJBHome;
import com.js.oa.hr.examination.po.ExaminationStockPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class ExaminationStockBD {
  private static Logger logger = Logger.getLogger(ExaminationStockBD.class
      .getName());
  
  public boolean save(ExaminationStockPO po, Object[] para) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ExaminationStockPO.class);
      pg.put(para, Object[].class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.save :" + e.getMessage());
    } 
    return ret;
  }
  
  public ExaminationStockPO load(Long id) {
    ExaminationStockPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      po = (ExaminationStockPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.load :" + e.getMessage());
    } 
    return po;
  }
  
  public boolean update(ExaminationStockPO po, Object[] para, Long examinationStockID) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(po, ExaminationStockPO.class);
      pg.put(para, Object[].class);
      pg.put(examinationStockID, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.update :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean delete(Long examinationID) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(examinationID, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("delete", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.delete :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean deleteBatch(String ids) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("deleteBatch", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.deleteBatch :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public boolean move(String ids, String sign) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(ids, String.class);
      pg.put(sign, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("move", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.move :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String[] getStockArr(String style, String type, String sign, Long domainId) {
    String[] stockArr = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(style, String.class);
      pg.put(type, String.class);
      pg.put(sign, String.class);
      pg.put(domainId, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationStockEJB", 
          "ExaminationStockEJBLocal", ExaminationStockEJBHome.class);
      stockArr = (String[])ejbProxy.invoke("getStockArr", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationStockBD.getStockArr :" + e.getMessage());
    } 
    return stockArr;
  }
}
