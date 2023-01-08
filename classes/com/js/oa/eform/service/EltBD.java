package com.js.oa.eform.service;

import com.js.oa.eform.bean.EltEJBHome;
import com.js.oa.eform.po.TEltPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class EltBD {
  private static Logger logger = Logger.getLogger(EltBD.class.getName());
  
  public Boolean save(TEltPO eltPO, String pageId, String areaId) {
    Boolean success = new Boolean(true);
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(eltPO, TEltPO.class);
      pg.put(pageId, String.class);
      pg.put(areaId, String.class);
      EJBProxy ejbProxy = new EJBProxy("EltEJB", "EltEJBLocal", EltEJBHome.class);
      success = (Boolean)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("EltEJB error to save information :" + e.getMessage());
    } 
    return success;
  }
  
  public Boolean delete(String pageId) {
    Boolean success = new Boolean(true);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("EltEJB", "EltEJBLocal", EltEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("EltEJB error to delete information :" + e.getMessage());
    } 
    return success;
  }
}
