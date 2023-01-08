package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WFActivityEJBHome;
import com.js.oa.jsflow.po.WFActivityPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.List;
import org.apache.log4j.Logger;

public class ActivityBD {
  private static Logger logger = Logger.getLogger(ActivityBD.class.getName());
  
  public Long addWithoutCondition(String[] activityPara, String[] readField, String[] writeField, String[] protectField, String formId) {
    Long activityId = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(activityPara, String[].class);
      pg.put(readField, String[].class);
      pg.put(writeField, String[].class);
      pg.put(protectField, String[].class);
      pg.put(formId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      activityId = (Long)ejbProxy.invoke("addWithoutCondition", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to addWithoutCondition information :" + e.getMessage());
    } 
    return activityId;
  }
  
  public boolean updateWithoutCondition(String[] activityParameter, String[] readField, String[] writeField, String[] protectField, String activityId, String formId) {
    return updateWithoutCondition(activityParameter, readField, writeField, null, protectField, activityId, formId);
  }
  
  public boolean updateWithoutCondition(String[] activityParameter, String[] readField, String[] writeField, String[] noDisplayField, String[] protectField, String activityId, String formId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(activityParameter, String[].class);
      pg.put(readField, String[].class);
      pg.put(writeField, String[].class);
      pg.put(noDisplayField, String[].class);
      pg.put(protectField, String[].class);
      pg.put(activityId, String.class);
      pg.put(formId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      ejbProxy.invoke("updateWithoutCondition", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to updateWithoutCondition information :" + e.getMessage());
      e.printStackTrace();
    } 
    return result;
  }
  
  public boolean updateWithCondition(String[] activityParameter, String[] readField, String[] writeField, String[] protectField, String[] condition, String[] operate, String[] compareValue, String[] timeLimit, String[] motionTime, String activityId, String formId) {
    return updateWithCondition(activityParameter, readField, writeField, null, protectField, condition, operate, compareValue, timeLimit, motionTime, activityId, formId);
  }
  
  public boolean updateWithCondition(String[] activityParameter, String[] readField, String[] writeField, String[] noDisplayField, String[] protectField, String[] condition, String[] operate, String[] compareValue, String[] timeLimit, String[] motionTime, String activityId, String formId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(12);
      pg.put(activityParameter, String[].class);
      pg.put(readField, String[].class);
      pg.put(writeField, String[].class);
      pg.put(noDisplayField, String[].class);
      pg.put(protectField, String[].class);
      pg.put(condition, String[].class);
      pg.put(operate, String[].class);
      pg.put(compareValue, String[].class);
      pg.put(timeLimit, String[].class);
      pg.put(motionTime, String[].class);
      pg.put(activityId, String.class);
      pg.put(formId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      ejbProxy.invoke("updateWithCondition", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to updateWithCondition information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean addWithCondition(String[] activityPara, String[] readField, String[] writeField, String[] protectField, String[] condition, String[] operate, String[] compareValue, String[] timeLimit, String[] motionTime, String formId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(10);
      pg.put(activityPara, String[].class);
      pg.put(readField, String[].class);
      pg.put(writeField, String[].class);
      pg.put(protectField, String[].class);
      pg.put(condition, String[].class);
      pg.put(operate, String[].class);
      pg.put(compareValue, String[].class);
      pg.put(timeLimit, String[].class);
      pg.put(motionTime, String[].class);
      pg.put(formId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      ejbProxy.invoke("addWithCondition", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to addWithCondition information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public boolean remove(String activityIdString) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      ejbProxy.invoke("remove", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to remove information :" + e.getMessage());
    } 
    return result;
  }
  
  public boolean removeAll(String processId) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      ejbProxy.invoke("removeAll", pg.getParameters());
      result = true;
    } catch (Exception e) {
      logger.error("error to removeAll information :" + e.getMessage());
    } 
    return result;
  }
  
  public WFActivityPO getActivityInfo(String activityId) {
    WFActivityPO activityPO = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      activityPO = (WFActivityPO)ejbProxy.invoke("getActivityInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivityInfo information :" + e.getMessage());
      e.printStackTrace();
    } 
    return activityPO;
  }
  
  public List getFromActivity(String activityId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      list = (List)ejbProxy.invoke("getFromActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFromActivity information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getActivity(String processId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      list = (List)ejbProxy.invoke("getActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivity information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getToActivity(String activityId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      list = (List)ejbProxy.invoke("getToActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getToActivity information :" + e.getMessage());
    } 
    return list;
  }
  
  public Long setActivity(String[] activityIdValue, String[] conditionValue, String[] operateValue, String[] compareValue, String activityId, String[] Expression, String type) {
    Long result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(activityIdValue, String[].class);
      pg.put(conditionValue, String[].class);
      pg.put(operateValue, String[].class);
      pg.put(compareValue, String[].class);
      pg.put(activityId, String.class);
      pg.put(Expression, String[].class);
      pg.put(type, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      result = (Long)ejbProxy.invoke("setActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setActivity inforamtion :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Long setActivity(String[] activityIdValue, String[] conditionValue, String[] operateValue, String[] compareValue, String activityId, String[] Expression, String type, String defaultActivity) {
    Long result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(8);
      pg.put(activityIdValue, String[].class);
      pg.put(conditionValue, String[].class);
      pg.put(operateValue, String[].class);
      pg.put(compareValue, String[].class);
      pg.put(activityId, String.class);
      pg.put(Expression, String[].class);
      pg.put(type, String.class);
      pg.put(defaultActivity, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      result = (Long)ejbProxy.invoke("setActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setActivity inforamtion :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Long setStartActivity(String activityId, String startActivity) {
    Long result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(activityId, String.class);
      pg.put(startActivity, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      result = (Long)ejbProxy.invoke("setStartActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setStartActivity information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Long setSingelRelation(String fromActivity, String toActivity, String conditionField, String operate, String compareValue, String expression, String defaultActivity) throws Exception {
    Long result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(fromActivity, String.class);
      pg.put(toActivity, String.class);
      pg.put(conditionField, String.class);
      pg.put(operate, String.class);
      pg.put(compareValue, String.class);
      pg.put(expression, String.class);
      pg.put(defaultActivity, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      result = (Long)ejbProxy.invoke("setSingelRelation", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setStartActivity information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Boolean hasPrintRight(String activityId, String userId, String orgId, String groupId) {
    Boolean hasRight = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(activityId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(groupId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      hasRight = (Boolean)ejbProxy.invoke("hasPrintRight", pg.getParameters());
    } catch (Exception e) {
      logger.error("WFActivityEJB:error to hasPrintRight information :" + e.getMessage());
    } 
    return hasRight;
  }
  
  public List getUserActivityList(String processId, String tableId, String recordId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      list = (List)ejbProxy.invoke("getUserActivityList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserActivityList information :" + e.getMessage());
    } 
    return list;
  }
  
  public String getActivityHandSignType(String activityId) {
    String handSignType = "-1";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFActivityEJB", "WFActivityEJBLocal", WFActivityEJBHome.class);
      handSignType = (String)ejbProxy.invoke("getActivityHandSignType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivityHandSignType information :" + e.getMessage());
    } 
    return handSignType;
  }
}
