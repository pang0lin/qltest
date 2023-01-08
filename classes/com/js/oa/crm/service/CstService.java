package com.js.oa.crm.service;

import com.js.oa.crm.bean.CstEJBBean;
import com.js.oa.relproject.bean.RelProjectBean;
import com.js.oa.relproject.po.RelProjectPO;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class CstService {
  private static Logger logger = Logger.getLogger(CstService.class.getName());
  
  public void create(Object object) {
    CstEJBBean bean = new CstEJBBean();
    try {
      bean.create(object);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
  }
  
  public void createAll(List object) {
    CstEJBBean bean = new CstEJBBean();
    try {
      bean.createAll(object);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
  }
  
  public void remove(String table, String id) {
    CstEJBBean bean = new CstEJBBean();
    try {
      bean.remove(table, id);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
  }
  
  public Object cstProMod(Class clazz, String id) {
    CstEJBBean bean = new CstEJBBean();
    Object obj = null;
    try {
      obj = bean.getSingle(clazz, id);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return obj;
  }
  
  public Object getSingleInfoById(Class clazz, String id) {
    CstEJBBean bean = new CstEJBBean();
    Object obj = null;
    try {
      obj = bean.getSingle(clazz, id);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return obj;
  }
  
  public void update(Object object) {
    CstEJBBean bean = new CstEJBBean();
    try {
      bean.update(object);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
  }
  
  public List getCstList(String id) {
    CstEJBBean bean = new CstEJBBean();
    List list = null;
    try {
      list = bean.getCstListById(id);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getCstSellList(String id) {
    CstEJBBean bean = new CstEJBBean();
    List list = null;
    try {
      list = bean.getCstSellListById(id);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return list;
  }
  
  public List getObjList(Class clazz, String flag) {
    CstEJBBean bean = new CstEJBBean();
    List objList = null;
    try {
      objList = bean.getObjList(clazz, flag);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return objList;
  }
  
  public List getObjList(Class clazz, String where, String other) {
    CstEJBBean bean = new CstEJBBean();
    List objList = null;
    try {
      objList = bean.getObjList(clazz, where, other);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return objList;
  }
  
  public List getProjectObjList(String userId, String orgId, String orgIdString) {
    RelProjectBean bean = new RelProjectBean();
    List objList = null;
    try {
      objList = bean.getProjectFuze(userId, orgId, orgIdString);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return objList;
  }
  
  public RelProjectPO getSingleProjectObj(String projectId) {
    RelProjectBean bean = new RelProjectBean();
    RelProjectPO po = null;
    try {
      po = bean.getRelProjectPO(projectId);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return po;
  }
  
  public Map getProjectPepole(String projectId) {
    RelProjectBean bean = new RelProjectBean();
    Map map = null;
    try {
      map = bean.getProjectPepole(projectId);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return map;
  }
  
  public List exportXSL(String view, String from, String where) {
    CstEJBBean bean = new CstEJBBean();
    List list = null;
    try {
      list = bean.exportXSl(view, from, where);
    } catch (Exception e) {
      logger.error("Can not get crm's info:" + e.getMessage());
    } 
    return list;
  }
}
