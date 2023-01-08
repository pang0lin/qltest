package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.TrainClassEJBHome;
import com.js.oa.hr.personnelmanager.po.TrainClassPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class TrainClassBD {
  private static Logger logger = Logger.getLogger(TrainClassBD.class.getName());
  
  public boolean save(TrainClassPO po) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainClassEJB", "TrainClassEJBLocal", TrainClassEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, TrainClassPO.class);
      ret = ((Boolean)ejbProxy.invoke("save", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("保存培训种类设置时出错:" + e);
    } 
    return ret;
  }
  
  public TrainClassPO load(Long id) {
    TrainClassPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainClassEJB", "TrainClassEJBLocal", TrainClassEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      po = (TrainClassPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID获取培训种类PO时出错:" + e);
    } 
    return po;
  }
  
  public Boolean update(TrainClassPO po, Long id) {
    Boolean ret = Boolean.FALSE;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainClassEJB", "TrainClassEJBLocal", TrainClassEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, TrainClassPO.class);
      pg.put(id, Long.class);
      ret = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.info("修改培训种类时出错:" + e);
    } 
    return ret;
  }
  
  public Boolean delete(String ids) {
    Boolean ret = Boolean.FALSE;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainClassEJB", "TrainClassEJBLocal", TrainClassEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      ret = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.info("删除指定id号的培训种类时出错:" + e);
    } 
    return ret;
  }
  
  public List list(Long domainID) {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainClassEJB", "TrainClassEJBLocal", TrainClassEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainID, Long.class);
      list = (List)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      logger.info("获取指定域所有的培训种类列表时出错:" + e);
    } 
    return list;
  }
}
