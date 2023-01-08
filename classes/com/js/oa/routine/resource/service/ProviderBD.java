package com.js.oa.routine.resource.service;

import com.js.oa.routine.resource.action.ProviderActionForm;
import com.js.oa.routine.resource.bean.ProviderEJBHome;
import com.js.oa.routine.resource.po.ProviderPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class ProviderBD {
  private static Logger logger = Logger.getLogger(ProviderBD.class.getName());
  
  public boolean save(ProviderPO providerPO) {
    Boolean flag = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(providerPO, ProviderPO.class);
      EJBProxy ejbProxy = new EJBProxy("ProviderEJB", 
          "ProviderEJBLocal", ProviderEJBHome.class);
      flag = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to save information :" + e.getMessage());
    } 
    return flag.booleanValue();
  }
  
  public ProviderPO getModifyProvider(String providerId) {
    ProviderPO po = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(providerId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ProviderEJB", 
          "ProviderEJBLocal", ProviderEJBHome.class);
      po = (ProviderPO)ejbProxy.invoke("getModifyProvider", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModifyProvider information :" + 
          e.getMessage());
    } 
    return po;
  }
  
  public boolean update(ProviderActionForm providerActionForm, String oldProviderId) {
    Boolean flag = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(providerActionForm, ProviderActionForm.class);
      pg.put(oldProviderId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ProviderEJB", 
          "ProviderEJBLocal", ProviderEJBHome.class);
      flag = (Boolean)ejbProxy.invoke("update", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to update information :" + e.getMessage());
    } 
    return flag.booleanValue();
  }
  
  public Boolean delete(String providerIdString) {
    Boolean flag = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(providerIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("ProviderEJB", 
          "ProviderEJBLocal", ProviderEJBHome.class);
      flag = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delete information :" + e.getMessage());
    } 
    return flag;
  }
}
