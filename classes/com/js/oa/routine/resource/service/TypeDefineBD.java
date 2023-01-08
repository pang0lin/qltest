package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.bean.TypeDefineEJBHome;
import com.js.oa.routine.resource.po.TypeDefinePO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class TypeDefineBD {
  private static Logger logger = Logger.getLogger(TypeDefineBD.class.getName());
  
  public String save(TypeDefinePO po) {
    String result = "true";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(po, TypeDefinePO.class);
      EJBProxy ejbProxy = new EJBProxy("TypeDefineEJB", 
          "TypeDefineEJBLocal", TypeDefineEJBHome.class);
      result = (String)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  TypeDefineBD save information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public TypeDefinePO load(Long id) {
    TypeDefinePO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("TypeDefineEJB", 
          "TypeDefineEJBLocal", TypeDefineEJBHome.class);
      po = (TypeDefinePO)ejbProxy.invoke("load", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  TypeDefineBD load information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public Boolean update(TypeDefinePO po, Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(po, TypeDefinePO.class);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("TypeDefineEJB", 
          "TypeDefineEJBLocal", TypeDefineEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  TypeDefineBD update information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public List list(String mode, Long domainID) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(mode, String.class);
      pg.put(domainID, Long.class);
      EJBProxy ejbProxy = new EJBProxy("TypeDefineEJB", 
          "TypeDefineEJBLocal", TypeDefineEJBHome.class);
      list = (List)ejbProxy.invoke("list", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  TypeDefineBD list information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public Boolean delete(Long id) {
    Boolean ret = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, Long.class);
      EJBProxy ejbProxy = new EJBProxy("TypeDefineEJB", 
          "TypeDefineEJBLocal", TypeDefineEJBHome.class);
      ret = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to  TypeDefineBD delete information :" + 
          e.getMessage());
    } 
    return ret;
  }
}
