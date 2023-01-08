package com.js.oa.eform.service;

import com.js.oa.eform.bean.AreaEJBHome;
import com.js.oa.eform.po.TAreaPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import org.apache.log4j.Logger;

public class AreaBD {
  private static Logger logger = Logger.getLogger(AreaBD.class.getName());
  
  public Long save(TAreaPO areaPO, String pageId, String areaTypeId) {
    Long areaId = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(areaPO, TAreaPO.class);
      pg.put(pageId, String.class);
      pg.put(areaTypeId, String.class);
      EJBProxy ejbProxy = new EJBProxy("AreaEJB", "AreaEJBLocal", AreaEJBHome.class);
      areaId = (Long)ejbProxy.invoke("save", pg.getParameters());
    } catch (Exception e) {
      logger.error("AreaEJB error to save information :" + e.getMessage());
    } 
    return areaId;
  }
  
  public Boolean delete(String pageId) {
    Boolean success = new Boolean(true);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("AreaEJB", "AreaEJBLocal", AreaEJBHome.class);
      success = (Boolean)ejbProxy.invoke("delete", pg.getParameters());
    } catch (Exception e) {
      logger.error("AreaEJB error to delete information :" + e.getMessage());
    } 
    return success;
  }
}
