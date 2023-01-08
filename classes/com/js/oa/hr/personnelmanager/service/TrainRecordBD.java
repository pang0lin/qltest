package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.TrainRecordEJBHome;
import com.js.oa.hr.personnelmanager.po.TrainRecordPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class TrainRecordBD {
  private static Logger logger = Logger.getLogger(TrainRecordBD.class.getName());
  
  public boolean save(TrainRecordPO po) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainRecordEJB", 
          "TrainRecordEJBLocal", TrainRecordEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, TrainRecordPO.class);
      ret = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("保存培训记录时出错:" + e);
    } 
    return ret;
  }
  
  public boolean update(TrainRecordPO po, Long trainRecordID) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainRecordEJB", 
          "TrainRecordEJBLocal", TrainRecordEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, TrainRecordPO.class);
      pg.put(trainRecordID, Long.class);
      ret = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("更新一条培训记录时出错:" + e);
    } 
    return ret;
  }
  
  public TrainRecordPO load(Long trainRecordID) {
    TrainRecordPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainRecordEJB", 
          "TrainRecordEJBLocal", TrainRecordEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(trainRecordID, Long.class);
      po = (TrainRecordPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("根据ID号返回培训记录po对象时出错:" + e);
    } 
    return po;
  }
  
  public Boolean delete(String ids) {
    Boolean ret = Boolean.FALSE;
    try {
      EJBProxy ejbProxy = new EJBProxy("TrainRecordEJB", 
          "TrainRecordEJBLocal", TrainRecordEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      ret = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.info("删除培训记录时出错:" + e);
    } 
    return ret;
  }
}
