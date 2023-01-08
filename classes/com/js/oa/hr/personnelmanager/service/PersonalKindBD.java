package com.js.oa.hr.personnelmanager.service;

import com.js.oa.hr.personnelmanager.bean.PersonalKindEJBHome;
import com.js.oa.hr.personnelmanager.po.PersonalKindPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class PersonalKindBD {
  private static Logger logger = Logger.getLogger(WorkAddressBD.class.getName());
  
  public boolean save(PersonalKindPO po) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, PersonalKindPO.class);
      ret = ((Boolean)ejbProxy.invoke("save", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("保存时出错:" + e);
    } 
    return ret;
  }
  
  public PersonalKindPO load(Long id) {
    PersonalKindPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      po = (PersonalKindPO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.info("加载时出错:" + e);
    } 
    return po;
  }
  
  public PersonalKindPO loadByName(String name) {
    PersonalKindPO po = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(name, String.class);
      po = (PersonalKindPO)ejbProxy.invoke("loadByName", pg.getParameters());
    } catch (Exception e) {
      logger.info("加载时出错:" + e);
    } 
    return po;
  }
  
  public boolean update(PersonalKindPO po, Long id) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, PersonalKindPO.class);
      pg.put(id, Long.class);
      ret = ((Boolean)ejbProxy.invoke("update", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("更新信息时出错:" + e);
    } 
    return ret;
  }
  
  public boolean delete(String ids) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ids, String.class);
      ret = ((Boolean)ejbProxy.invoke("delete", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("删除指定id号的记录时出错:" + e);
    } 
    return ret;
  }
  
  public List list() {
    List list = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      list = (List)ejbProxy.invoke("list", (Object[][])null);
    } catch (Exception e) {
      logger.info("获得下拉列表时出错:" + e);
    } 
    return list;
  }
  
  public boolean checkExistKind(Long kindId, String kindName) {
    boolean ret = false;
    try {
      EJBProxy ejbProxy = new EJBProxy("PersonalKindEJB", 
          "PersonalKindEJBLocal", PersonalKindEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(kindId, Long.class);
      pg.put(kindName, String.class);
      ret = ((Boolean)ejbProxy.invoke("checkExistKind", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.info("指定kindName的记录时出错:" + e);
    } 
    return ret;
  }
}
