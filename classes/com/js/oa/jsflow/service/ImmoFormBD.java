package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.ImmoFormWorkFlowEJBHome;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.Map;
import org.apache.log4j.Logger;

public class ImmoFormBD {
  private static Logger logger = Logger.getLogger(ImmoFormBD.class.getName());
  
  public String[] getCommPO(String activityId, String tableId, String recordId) {
    String[] str = { "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ImmoFormWorkFlowEJB", "ImmoFormWorkFlowEJBLocal", ImmoFormWorkFlowEJBHome.class);
      str = (String[])ejbProxy.invoke("getCommPO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommPO information :" + e.getMessage());
    } finally {}
    return str;
  }
  
  public Map getComment(String processId, String tableId, String recordId) {
    Map commentMap = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ImmoFormWorkFlowEJB", "ImmoFormWorkFlowEJBLocal", ImmoFormWorkFlowEJBHome.class);
      commentMap = (Map)ejbProxy.invoke("getComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getComment information :" + e.getMessage());
    } finally {}
    return commentMap;
  }
  
  public String getImmoFormRealName(String immoFormId) {
    String immoFormRealName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(immoFormId, String.class);
      EJBProxy ejbProxy = new EJBProxy("ImmoFormWorkFlowEJB", "ImmoFormWorkFlowEJBLocal", ImmoFormWorkFlowEJBHome.class);
      immoFormRealName = (String)ejbProxy.invoke("getImmoFormRealName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getImmoFormRealName information :" + e.getMessage());
    } finally {}
    return immoFormRealName;
  }
}
