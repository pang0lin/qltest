package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WFProcessEJBHome;
import com.js.oa.jsflow.po.WFWorkFlowProcessPO;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ProcessBD {
  private static Logger logger = Logger.getLogger(ProcessBD.class.getName());
  
  public String removeProcess(String ProcessIdString) {
    String processName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(ProcessIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      processName = (String)ejbProxy.invoke("removeProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to removeProcess information :" + e.getMessage());
    } 
    return processName;
  }
  
  public boolean addProcess(WFWorkFlowProcessPO wfWorkFlowProcessPO, String packageId, String[] noWriteField, String mainFormId) {
    Boolean result = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(wfWorkFlowProcessPO, WFWorkFlowProcessPO.class);
      pg.put(packageId, String.class);
      pg.put(noWriteField, String[].class);
      pg.put(mainFormId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      result = (Boolean)ejbProxy.invoke("addProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to addProcess information :" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public List getNoWriteField(String processId) {
    ArrayList list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (ArrayList)ejbProxy.invoke("getNoWriteField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoWriteField information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getNoDisplayField(String processId) {
    ArrayList list = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (ArrayList)ejbProxy.invoke("getNoDisplayField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoWriteField information :" + e.getMessage());
    } 
    return list;
  }
  
  public String getFirstActivityWriteField(String processId, String moduleId) {
    String res = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(processId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      res = (String)ejbProxy.invoke("getFirstActivityWriteField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoWriteField information :" + e.getMessage());
    } 
    return res;
  }
  
  public List getProcInfo(String processId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getProcInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcInfo information :" + e.getMessage());
    } 
    return list;
  }
  
  public boolean updateProcess(String processId, String[] parameter, String[] noWriteField, String mainFormId) {
    Boolean result = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(parameter, String[].class);
      pg.put(noWriteField, String[].class);
      pg.put(mainFormId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      result = (Boolean)ejbProxy.invoke("updateProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateProcess information :" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public ActivityVO getFirstActivity(String processId) {
    ActivityVO activityVO = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      activityVO = (ActivityVO)ejbProxy.invoke("getFirstActivity", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getFirstActivity information :" + e.getMessage());
    } 
    return activityVO;
  }
  
  public List getUserDossProc(String userId, String orgIdString, String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getUserDossProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserProcess information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllDossProc(String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getAllDossProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllDossProc information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllProcess(String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getAllProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllProcess information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getAllProcessByModule(String moduleId, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(moduleId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getAllProcessByModule", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllProcessByModule information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getUserDossOperProc(String userId, String orgIdString, String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getUserDossOperProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserDossOperProc information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getUserDossViewOperAdminProc(String userId, String orgIdString, String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getUserDossViewOperAdminProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserDossViewOperAdminProc information :" + e.getMessage());
    } 
    return list;
  }
  
  public Boolean getUserDossExport(String userId, String orgIdString, String processId) {
    Boolean hasRight = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      hasRight = (Boolean)ejbProxy.invoke("getUserDossExport", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserDossExport information :" + e.getMessage());
    } 
    return hasRight;
  }
  
  public List getUserProcessList(String userId, String orgIdString, String moduleId, String workflowContent) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(moduleId, String.class);
      pg.put(workflowContent, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getUserProcessList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserProcess information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getUserProcess(String userId, String orgIdString, String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getUserProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserProcess information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getAllUserProcess(String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getAllUserProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllUserProcess information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getUserOftenProcess(String userId, String orgIdString, String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      list = (List)ejbProxy.invoke("getUserOftenProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserOftenProcess information :" + e.getMessage());
      e.printStackTrace();
    } finally {}
    return list;
  }
  
  public String userCanCreateFlow(String processId, String userId, String orgId, String orgIdString) {
    String ret = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(orgIdString, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      ret = (String)ejbProxy.invoke("userCanCreateFlow", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to userCanCreateFlow information :" + e.getMessage());
    } finally {}
    return ret;
  }
  
  public String getRemindField(String processId) {
    String remindField = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      remindField = (String)ejbProxy.invoke("getRemindField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRemindField information :" + e.getMessage());
    } 
    return remindField;
  }
  
  public String getNoWriteField(String processId, String processType) {
    String noWrite = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(processId, String.class);
      pg.put(processType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      noWrite = (String)ejbProxy.invoke("getNoWriteField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNoWriteField information :" + e.getMessage());
    } 
    return noWrite;
  }
  
  public Integer getCanCancel(String processId) {
    Integer canCancel = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      canCancel = (Integer)ejbProxy.invoke("getCanCancel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCanCancel information :" + e.getMessage());
    } 
    return canCancel;
  }
  
  public Integer getIsDossier(String processId) {
    Integer isDossier = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      isDossier = (Integer)ejbProxy.invoke("getIsDossier", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIsDossier information :" + e.getMessage());
    } 
    return isDossier;
  }
  
  public boolean copyProcess(String processId) {
    Boolean result = new Boolean(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      result = (Boolean)ejbProxy.invoke("copyProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to copyProcess information :" + e.getMessage());
    } 
    return result.booleanValue();
  }
  
  public String getDescription(String processId) {
    String description = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      description = (String)ejbProxy.invoke("getDescription", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDescription information :" + e.getMessage());
    } 
    return description;
  }
  
  public String updateFirstActivity(String processId, String activityId, String activityName, String nowriteField, String domainId) {
    return updateFirstActivity(processId, activityId, activityName, nowriteField, "", domainId);
  }
  
  public String updateFirstActivity(String processId, String activityId, String activityName, String nowriteField, String noDisplayField, String domainId) {
    String description = "-1";
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(processId, String.class);
      pg.put(activityId, String.class);
      pg.put(activityName, String.class);
      pg.put(nowriteField, String.class);
      pg.put(noDisplayField, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      description = (String)ejbProxy.invoke("updateFirstActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateFirstActivity information :" + e.getMessage());
    } 
    return description;
  }
  
  public boolean nameIsDuplicate(String processId, String processName, String processClass) {
    boolean res = true;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(processId, String.class);
      pg.put(processName, String.class);
      pg.put(processClass, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      res = ((Boolean)ejbProxy.invoke("nameIsDuplicate", pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to updateFirstActivity information :" + e.getMessage());
    } 
    return res;
  }
  
  public String getProcWhereSql(String userId, String orgIdString, String type) {
    String sql = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(orgIdString, String.class);
      pg.put(type, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      sql = (String)ejbProxy.invoke("getProcWhereSql", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcWhereSql information :" + e.getMessage());
    } 
    return sql;
  }
  
  public String getDeskTopFlowId(String userId) {
    String id = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      id = (String)ejbProxy.invoke("getDeskTopFlowId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcWhereSql information :" + e.getMessage());
    } 
    return id;
  }
  
  public String setProcessOnDeskTop(String userId, String processId, String type) {
    String id = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(userId, String.class);
      pg.put(processId, String.class);
      pg.put(type, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      id = (String)ejbProxy.invoke("setProcessOnDeskTop", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcWhereSql information :" + e.getMessage());
    } 
    return id;
  }
  
  public String endFlowInstance(String tableId, String recordId) {
    String result = "-1";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      ejbProxy.invoke("endFlowInstance", pg.getParameters());
      result = "0";
    } catch (Exception e) {
      logger.error("error to getProcWhereSql information :" + e.getMessage());
    } 
    return result;
  }
  
  public void hangupInstance(String processId, String flag, String tableId, String recordId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(flag, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      ejbProxy.invoke("hangupInstance", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcWhereSql information :" + e.getMessage());
    } 
  }
  
  public String changeProcessStatus(String processId, String flag) {
    String processName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(processId, String.class);
      pg.put(flag, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      processName = (String)ejbProxy.invoke("changeProcessStatus", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to changeProcessStatus information :" + e.getMessage());
    } 
    return processName;
  }
  
  public String copyFlow(String flows, String domainId, String userId, String orgId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(flows, String.class);
      pg.put(domainId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      ejbProxy.invoke("copyFlow", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to pasteForm information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public Boolean checkProcessByPackage(String packagesId) {
    Boolean b = Boolean.valueOf(false);
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(packagesId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      b = (Boolean)ejbProxy.invoke("checkProcessByPackage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to changeProcessStatus information :" + e.getMessage());
    } 
    return b;
  }
  
  public String[] getProcessDeadline(String processId) {
    String[] ret = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WFProcessEJB", "WFProcessEJBLocal", WFProcessEJBHome.class);
      ret = (String[])ejbProxy.invoke("getProcessDeadline", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to changeProcessStatus information :" + e.getMessage());
    } 
    return ret;
  }
}
