package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.WorkAddressEJBHome;
import com.js.oa.hr.personnelmanager.po.WorkAddressPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class WorkAddressBD {
  private static Logger logger = Logger.getLogger(WorkAddressBD.class.getName());
  
  public boolean save(WorkAddressPO po) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAddressEJB", "WorkAddressEJBLocal", WorkAddressEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, WorkAddressPO.class);
      ret = ((Boolean)ejbProxy.invoke("save", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("保存办公地点设置时出错:" + e);
    } 
    return ret;
  }
  
  public WorkAddressPO load(Long id) {
    WorkAddressPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAddressEJB", "WorkAddressEJBLocal", WorkAddressEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      po = (WorkAddressPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("保存办公地点设置时出错:" + e);
    } 
    return po;
  }
  
  public WorkAddressPO loadByName(String Name) {
    WorkAddressPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAddressEJB", "WorkAddressEJBLocal", WorkAddressEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(Name, String.class);
      po = (WorkAddressPO)ejbProxy.invoke("loadByName", pg.getParameters());
    } catch (Exception e) {
      logger.info("保存办公地点设置时出错:" + e);
    } 
    return po;
  }
  
  public boolean update(WorkAddressPO po, Long id) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAddressEJB", "WorkAddressEJBLocal", WorkAddressEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, WorkAddressPO.class);
      pg.put(id, Long.class);
      ret = ((Boolean)ejbProxy.invoke("update", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("更新一条办公地址设置信息时出错:" + e);
    } 
    return ret;
  }
  
  public boolean delete(String ids) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAddressEJB", "WorkAddressEJBLocal", WorkAddressEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      ret = ((Boolean)ejbProxy.invoke("delete", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.info("删除指定id号的记录时出错:" + e);
    } 
    return ret;
  }
  
  public List list(Long domainID) {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkAddressEJB", "WorkAddressEJBLocal", WorkAddressEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainID, Long.class);
      list = (List)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      logger.info("获得指定域的办公地址下拉列表时出错:" + e);
    } 
    return list;
  }
}
