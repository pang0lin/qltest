package com.js.oa.hr.examination.service;

import com.js.oa.hr.examination.bean.ExaminationTypeEJBHome;
import com.js.oa.hr.examination.po.ExaminationTypePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class ExaminationTypeBD {
  private static Logger logger = Logger.getLogger(ExaminationTypeBD.class
      .getName());
  
  public boolean save(ExaminationTypePO po) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, ExaminationTypePO.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationTypeEJB", 
          "ExaminationTypeEJBLocal", ExaminationTypeEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationTypeBD.save :" + e.getMessage());
    } 
    return ret;
  }
  
  public ExaminationTypePO load(Long id) {
    ExaminationTypePO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationTypeEJB", 
          "ExaminationTypeEJBLocal", ExaminationTypeEJBHome.class);
      po = (ExaminationTypePO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationTypeBD.load :" + e.getMessage());
    } 
    return po;
  }
  
  public boolean update(ExaminationTypePO po, Long id) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, ExaminationTypePO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationTypeEJB", 
          "ExaminationTypeEJBLocal", ExaminationTypeEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationTypeBD.update :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean delete(Long id) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationTypeEJB", 
          "ExaminationTypeEJBLocal", ExaminationTypeEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("delete", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationTypeBD.delete :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean delBatch(String ids) {
    boolean ret = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationTypeEJB", 
          "ExaminationTypeEJBLocal", ExaminationTypeEJBHome.class);
      ret = ((Boolean)ejbProxy.invoke("delBatch", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to ExaminationTypeBD.delBatch :" + e.getMessage());
    } 
    return ret;
  }
  
  public List getTypeList(Long domainID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainID, Long.class);
      EJBProxy ejbProxy = new EJBProxy("ExaminationTypeEJB", 
          "ExaminationTypeEJBLocal", ExaminationTypeEJBHome.class);
      list = (List)ejbProxy.invoke("getTypeList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to ExaminationTypeBD.getTypeList :" + e.getMessage());
    } 
    return list;
  }
}
