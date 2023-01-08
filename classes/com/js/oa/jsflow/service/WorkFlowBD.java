package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WorkFlowEJBHome;
import com.js.oa.jsflow.vo.ActivityVO;
import com.js.oa.jsflow.vo.ModuleVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

public class WorkFlowBD {
  private static Logger logger = Logger.getLogger(WorkFlowBD.class.getName());
  
  public List getAccessTable(ModuleVO moduleVO) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleVO, ModuleVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAccessTable", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAccessTable information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getSimpleField(String moduleId, String tableId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(moduleId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getSimpleField", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSimpleField information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getSimpleFieldByOrder(String moduleId, String tableId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(moduleId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getSimpleFieldByOrder", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSimpleField information :" + e.getMessage());
    } 
    return alist;
  }
  
  public long insertTable(String tableName, String fieldString, List valueList, String seqName) {
    Long id = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableName, String.class);
      pg.put(fieldString, String.class);
      pg.put(valueList, List.class);
      pg.put(seqName, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      id = (Long)ejbProxy.invoke("insertTable", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertTable information :" + e.getMessage());
    } 
    return id.longValue();
  }
  
  public long insertWorkAndSendMessage(String tableName, String fieldString, List valueList, List msg) {
    Long id = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableName, String.class);
      pg.put(fieldString, String.class);
      pg.put(valueList, List.class);
      pg.put(msg, List.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      id = (Long)ejbProxy.invoke("insertWorkAndSendMessage", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertTable information :" + e.getMessage());
      e.printStackTrace();
    } 
    return id.longValue();
  }
  
  public String copyProcInfo(String processId, String employeeId, String tTableId, String tRecordId) {
    String res = "-1";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(employeeId, String.class);
      pg.put(tTableId, String.class);
      pg.put(tRecordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      res = (String)ejbProxy.invoke("copyProcInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to copyProcInfo information :" + e.getMessage());
    } 
    return res;
  }
  
  public String getActivityURL(String moduleId) {
    String url = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      url = (String)ejbProxy.invoke("getActivityURL", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivityURL information :" + e.getMessage());
    } 
    return url;
  }
  
  public String getLeader(String userId) {
    String leader = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      leader = (String)ejbProxy.invoke("getLeader", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getLeader information :" + e.getMessage());
    } 
    return leader;
  }
  
  public String[] getPress(String activityId, String tableId, String recordId, String moduleId) {
    String[] str = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      str = (String[])ejbProxy.invoke("getPress", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPress inforamtion :" + e.getMessage());
    } 
    return str;
  }
  
  public List getAllNextActivity(String tableId, String recordId, String activityId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getAllNextActivity", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllNextActivity information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public List getFirstAllNextActivity(String activityId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getFirstAllNextActivity", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllNextActivity information :" + 
          e.getMessage());
    } 
    return list;
  }
  
  public String getImmoPO(String immoFieldId) {
    String immoFieldPO = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(immoFieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      immoFieldPO = (String)ejbProxy.invoke("getImmoPO", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getImmoPO information :" + e.getMessage());
    } 
    return immoFieldPO;
  }
  
  public List getRWList(String activityId, String tableId, String recordId, String moduleId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getRWList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getImmoPO information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getProtectList(String activityId, String tableId, String recordId, String moduleId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(activityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getProtectList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProtectList information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String getCommField(String tableId, String recordId, String activityId) {
    String field = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      field = (String)ejbProxy.invoke("getCommField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommField information :" + e.getMessage());
    } 
    return field;
  }
  
  public String getAllCommField(String procId, String tableId, String recordId) {
    String field = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(procId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      field = (String)ejbProxy.invoke("getAllCommField", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllCommField information :" + 
          e.getMessage());
    } 
    return field;
  }
  
  public List getActiviyCommList(String tableId, String recordId, String poField) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(poField, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getActiviyCommList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllCommField information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public void insertDealwithWithDate(String tableId, String recordId, String activityName, String activityId, String userId, String comment, String nextActivityName, String nextActivityId, String stepCount, String date, String commentField, String isStandForWork, String standForUserId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(13);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityName, String.class);
      pg.put(activityId, String.class);
      pg.put(userId, String.class);
      pg.put(comment, String.class);
      pg.put(nextActivityName, String.class);
      pg.put(nextActivityId, String.class);
      pg.put(stepCount, String.class);
      pg.put(date, String.class);
      pg.put(commentField, String.class);
      pg.put(isStandForWork, String.class);
      pg.put(standForUserId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("insertDealwithWithDate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertDealwithWithDate information :" + e.getMessage());
    } 
  }
  
  public long setSurveillance(WorkVO workVO) {
    Long survilId = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      survilId = (Long)ejbProxy.invoke("setSurveillance", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setSurveillance information :" + e.getMessage());
    } 
    return survilId.longValue();
  }
  
  public List getOffiDict(String userId, String domainId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getOffiDict", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOffiDict information :" + e.getMessage());
    } 
    return list;
  }
  
  public List getStandForUser(String[] userId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getStandForUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getStandForUser information :" + e.getMessage());
    } 
    return list;
  }
  
  public void insertDealWith(String[] para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("insertDealWith", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertDealWith information :" + e.getMessage());
    } 
  }
  
  public void operateWork(String[] para, String[] transactUser, String needPassRound, String[] passRoundUser) {
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(para, String[].class);
      pg.put(transactUser, String[].class);
      pg.put(needPassRound, String.class);
      pg.put(passRoundUser, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("operateWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertDealWith information :" + e.getMessage());
    } 
  }
  
  public String completeWork(String[] para, String workId) {
    String userLogin = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      userLogin = (String)ejbProxy.invoke("completeWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to completeWork information :" + e.getMessage());
    } 
    return userLogin;
  }
  
  public void insertPassRoundDeal(String[] para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("insertPassRoundDeal", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertPassRoundDeal information :" + e.getMessage());
    } 
  }
  
  public void operPassRoundWork(String[] para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("operPassRoundWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to operPassRoundWork information :" + e.getMessage());
    } 
  }
  
  public void transitionWork(String[] para, String[] user) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(user, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("transitionWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to transitionWork information :" + e.getMessage());
    } 
  }
  
  public List getOrg(String domainId) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getOrg", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOrg information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getRole(String domainId) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getRole", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRole information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String[] getRoleUser(String roleUserString, String submitPersonId) {
    String[] roleUser = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(roleUserString, String.class);
      pg.put(submitPersonId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      Object[] obj = ((List)ejbProxy.invoke("getRoleUser", pg.getParameters())).toArray();
      roleUser = new String[obj.length];
      for (int i = 0; i < obj.length; i++)
        roleUser[i] = (String)obj[i]; 
    } catch (Exception e) {
      logger.error("error to getRoleUser information :" + e.getMessage());
    } 
    return roleUser;
  }
  
  public String[] getActivityClass(String tableId, String recordId, String activityId) {
    String[] actiClass = { "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      actiClass = (String[])ejbProxy.invoke("getActivityClass", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRoleUser information :" + e.getMessage());
    } 
    return actiClass;
  }
  
  public String[] getActivityClass(String tableId, String recordId, String activityId, String subProcWorkId) {
    String[] actiClass = { "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      pg.put(subProcWorkId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      actiClass = (String[])ejbProxy.invoke("getActivityClass", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getRoleUser information :" + e.getMessage());
    } 
    return actiClass;
  }
  
  public long setSurveillance2(WorkVO workVO, String resubmitWorkId) {
    Long survilId = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(workVO, WorkVO.class);
      pg.put(resubmitWorkId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      survilId = (Long)ejbProxy.invoke("setSurveillance2", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setSurveillance information :" + e.getMessage());
    } 
    return survilId.longValue();
  }
  
  public void completeWorkDoc(String[] para, String workId, String docTitle) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(para, String[].class);
      pg.put(workId, String.class);
      pg.put(docTitle, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("completeWorkDoc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to completeWork information :" + e.getMessage());
    } 
  }
  
  public void operateWorkDoc(String[] para, String[] transactUser, String needPassRound, String[] passRoundUser, String docTitle) {
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(para, String[].class);
      pg.put(transactUser, String[].class);
      pg.put(needPassRound, String.class);
      pg.put(passRoundUser, String[].class);
      pg.put(docTitle, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("operateWorkDoc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertDealWith information :" + e.getMessage());
    } 
  }
  
  public void operPassRoundWorkDoc(String[] para, String docTitle) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(docTitle, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("operPassRoundWorkDoc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to operPassRoundWork information :" + e.getMessage());
    } 
  }
  
  public List getFirstNextActi(String activityId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getFirstNextActi", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFirstNextActi information :" + e.getMessage());
    } 
    return list;
  }
  
  public ActivityVO getFirstProcActiVO(String activityId) {
    ActivityVO activityVO = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      activityVO = (ActivityVO)ejbProxy.invoke("getFirstProcActiVO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFirstProcActiVO information :" + e.getMessage());
    } 
    return activityVO;
  }
  
  public ActivityVO getProceedActiVO(String tableId, String recordId, String activityId, String moduleId) {
    ActivityVO activityVO = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      activityVO = (ActivityVO)ejbProxy.invoke("getProceedActiVO", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProceedActiVO information :" + e.getMessage());
    } 
    return activityVO;
  }
  
  public List getWorkUserLogin(String tableId, String recordId, String processId) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getWorkUserLogin", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkUserLogin information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String getSingleModuleProcess(String moduleId) {
    String processId = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      processId = (String)ejbProxy.invoke("getSingleModuleProcess", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkUserLogin information :" + e.getMessage());
    } 
    return processId;
  }
  
  public ModuleVO getModule(Integer moduleId) {
    ModuleVO moduleVO = new ModuleVO();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, Integer.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      moduleVO = (ModuleVO)ejbProxy.invoke("getModule", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModule information :" + e.getMessage());
    } 
    return moduleVO;
  }
  
  public List getOperUserOrg(String tableId, String recordId) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getOperUserOrg", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getOperUserOrg information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getActiUserByActiName(String tableId, String recordId, String activityName) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityName, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getActiUserByActiName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActiUserByActiName information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getLeaderList(String userId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getLeaderList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getLeaderList information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String getUpActivityUser(String processId, String tableId, String recordId, String curActivityId) {
    String empId = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(curActivityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      empId = (String)ejbProxy.invoke("getUpActivityUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUpActivityUser information :" + e.getMessage());
    } 
    return empId;
  }
  
  public List<String[]> getActivityUsers(String processId, String tableId, String recordId, String curActivityId, String from) {
    List<String[]> users = (List)new ArrayList<String>();
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(curActivityId, String.class);
      pg.put(from, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      users = (List<String[]>)ejbProxy.invoke("getActivityUsers", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivityUsers information :" + e.getMessage());
    } 
    return users;
  }
  
  public String[] getLeaderListByOrgId(String orgId) {
    String[] roleUser = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(orgId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      Object[] obj = ((List)ejbProxy.invoke("getLeaderListByOrgId", pg.getParameters())).toArray();
      roleUser = new String[obj.length];
      for (int i = 0; i < obj.length; i++)
        roleUser[i] = (String)obj[i]; 
    } catch (Exception e) {
      logger.error("error to getLeaderListByOrgId information :" + e.getMessage());
    } 
    return roleUser;
  }
  
  public String getForceCancel(String activity, String tableId, String recordId) {
    String forceCancel = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(activity, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      forceCancel = (String)ejbProxy.invoke("getForceCancel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getForceCancel information :" + e.getMessage());
    } 
    return forceCancel;
  }
  
  public Integer forceCancel(String[] para) {
    Integer success = new Integer("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      success = (Integer)ejbProxy.invoke("forceCancel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to forceCancel information :" + e.getMessage());
    } 
    return success;
  }
  
  public Integer forceDel(String[] para) {
    Integer success = new Integer("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      success = (Integer)ejbProxy.invoke("forceDel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to forceDel information :" + e.getMessage());
    } 
    return success;
  }
  
  public void updateTable(List updateSqlList) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(updateSqlList, List.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("updateTable", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateTable information :" + e.getMessage());
    } 
  }
  
  public void workCancel(String[] para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("workCancel", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to workCancel information :" + e.getMessage());
    } 
  }
  
  public String getTableName(String tableId) {
    String tableName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      tableName = (String)ejbProxy.invoke("getTableName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getTableName information :" + e.getMessage());
    } finally {}
    return tableName;
  }
  
  public int insertFormRecord(String tableName, String fieldString, String valueString, List childTable, List childField, List childFieldValue) {
    int recordId = 0;
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(tableName, String.class);
      pg.put(fieldString, String.class);
      pg.put(valueString, String.class);
      pg.put(childTable, List.class);
      pg.put(childField, List.class);
      pg.put(childFieldValue, List.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      recordId = ((Integer)ejbProxy.invoke("insertFormRecord", pg.getParameters())).intValue();
    } catch (Exception e) {
      logger.error("error to insertFormRecord information :" + e.getMessage());
    } finally {}
    return recordId;
  }
  
  public void updateChildTable(String tableId, String recordId, String childStr) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(childStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("updateChildTable", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateChildTable information :" + e.getMessage());
      e.printStackTrace();
    } 
  }
  
  public String getAFieldValue(String tableName, String fieldId, String recordId) {
    String value = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableName, String.class);
      pg.put(fieldId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      value = (String)ejbProxy.invoke("getAFieldValue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAFieldValue information :" + e.getMessage());
    } finally {}
    return value;
  }
  
  public String[] getPress(String activityId, String tableName, String recordId) {
    String[] str = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(activityId, String.class);
      pg.put(tableName, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      str = (String[])ejbProxy.invoke("getPress", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPress inforamtion :" + e.getMessage());
    } finally {}
    return str;
  }
  
  public List getDealWithComment(String tableId, String recordId, String procType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(procType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getDealWithComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealWithComment information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getDealWithCommentNotBack(String tableId, String recordId, String procType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(procType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getDealWithCommentNotBack", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealWithCommentNotBack information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getDealWithCommentFordoc(String tableId, String recordId, String procType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(procType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getDealWithCommentFordoc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealWithCommentFordoc information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getDealWithCommentNotBackFordoc(String tableId, String recordId, String procType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(procType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getDealWithCommentNotBackFordoc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealWithCommentNotBackFordoc information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getAllowTransition(String tableId, String recordId, String activityId) {
    String s = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      s = (String)ejbProxy.invoke("getAllowTransition", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to operateWork getAllowTransition :" + e.getMessage());
    } finally {}
    return s;
  }
  
  public List getIdValue(String tableName, String fieldName) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableName, String.class);
      pg.put(fieldName, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getIdValue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getIdValue information :" + e.getMessage());
    } finally {}
    return alist;
  }
  
  public String getTransactType(String tableId, String recordId, String activityId) {
    String transactType = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      transactType = (String)ejbProxy.invoke("getTransactType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getTransactType information :" + e.getMessage());
    } 
    return transactType;
  }
  
  public void newRandomWork(String workId, String userId, String remindFieldValue) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(workId, String.class);
      pg.put(userId, String.class);
      pg.put(remindFieldValue, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      ejbProxy.invoke("newRandomWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to newRandomWork information :" + e.getMessage());
    } 
  }
  
  public String getActivityType(String activityid, String tableId, String recordId) {
    String activityType = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(activityid, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      activityType = (String)ejbProxy.invoke("getActivityType", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getActivityType information :" + e.getMessage());
    } finally {}
    return activityType;
  }
  
  public List getFormFields(String tableId) {
    List alist = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (List)ejbProxy.invoke("getFormFields", pg.getParameters());
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.error("error to getFormFields information :" + e.getMessage());
    } finally {}
    return alist;
  }
  
  public List getChildField(String tableId, String childFieldString) {
    ArrayList childFieldList = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(childFieldString, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      childFieldList = (ArrayList)ejbProxy.invoke("getChildField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChildField information :" + e.getMessage());
    } 
    return childFieldList;
  }
  
  public List getFieldAndValue(String tableId, String recordId, String pageId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(pageId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getFieldAndValue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFieldAndValue information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getChildTableValue(String childTable, List childField, String tableId, String recordId) {
    ArrayList childTableValue = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(childTable, String.class);
      pg.put(childField, List.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      childTableValue = (ArrayList)ejbProxy.invoke("getChildTableValue", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getChildTableValue information :" + e.getMessage());
    } 
    return childTableValue;
  }
  
  public List getDealProc(String tableId, String recordId, String procType) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(procType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getDealProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealProc information :" + e.getMessage());
    } 
    return list;
  }
  
  public String getNextStepUserName(String tableId, String recordId, String nextActivityId, String curStep) {
    String userName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(nextActivityId, String.class);
      pg.put(curStep, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      userName = (String)ejbProxy.invoke("getNextStepUserName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to updateChildTable information :" + e.getMessage());
    } 
    return userName;
  }
  
  public List getNextActivity(String tableId, String recordId, String activityId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getNextActivity", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNextActivity information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getFieldName(String fieldId) {
    String fieldName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      fieldName = (String)ejbProxy.invoke("getFieldName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFieldName information :" + e.getMessage());
    } 
    return fieldName;
  }
  
  public List getModuleProc(String moduleId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getModuleProc", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFieldName information :" + e.getMessage());
    } 
    return list;
  }
  
  public String getWriteControl(String processId, String moduleId) {
    String s = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(processId, String.class);
      pg.put(moduleId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      s = (String)ejbProxy.invoke("getWriteControl", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFieldName information :" + e.getMessage());
    } 
    return s;
  }
  
  public String getCancelReason(String workId) {
    String s = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      s = (String)ejbProxy.invoke("getCancelReason", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCancelReason information :" + e.getMessage());
    } 
    return s;
  }
  
  public String[] getCurCompleteWork(String tableId, String recordId) {
    String[] work = { "", "", "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      work = (String[])ejbProxy.invoke("getCurCompleteWork", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getCancelReason information :" + e.getMessage());
    } 
    return work;
  }
  
  public String insertCurFieldStr(String processId, String tTableId, String tRecordId, String curFieldStr) {
    String result = "-1";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(tTableId, String.class);
      pg.put(tRecordId, String.class);
      pg.put(curFieldStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      result = (String)ejbProxy.invoke("insertCurFieldStr", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertCurFieldStr information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getNextStepPassRoundUserName(String tableId, String recordId, String nextActivityId, String curStep) {
    String userName = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(nextActivityId, String.class);
      pg.put(curStep, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      userName = (String)ejbProxy.invoke("getNextStepPassRoundUserName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNextStepPassRoundUserName information :" + e.getMessage());
    } 
    return userName;
  }
  
  public List getRoleUserIDAndName(String roleUserString, String submitPersonId) {
    List roleUser = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(roleUserString, String.class);
      pg.put(submitPersonId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      roleUser = (List)ejbProxy.invoke("getRoleUserIDAndName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRoleUser information :" + e.getMessage());
    } 
    return roleUser;
  }
  
  public List getPositionUserIDAndName(String roleUserString, String submitPersonId) {
    List roleUser = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(roleUserString, String.class);
      pg.put(submitPersonId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      roleUser = (List)ejbProxy.invoke("getPositionUserIDAndName", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRoleUser information :" + e.getMessage());
    } 
    return roleUser;
  }
  
  public Boolean hasPrintRight(String processId, String userId, String orgId, String groupId) {
    Boolean hasRight = Boolean.FALSE;
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(groupId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      hasRight = (Boolean)ejbProxy.invoke("hasPrintRight", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getRoleUser information :" + e.getMessage());
    } 
    return hasRight;
  }
  
  public List getLeaderListByWorkID(String workId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getLeaderListByWorkID", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getLeaderList information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getSimpleField(String moduleId, String tableId, Set rwControlSet) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(moduleId, String.class);
      pg.put(tableId, String.class);
      pg.put(rwControlSet, Set.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getSimpleField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSimpleField information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getRelationSimpleField(String moduleId, String tableId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(moduleId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getRelationSimpleField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSimpleField information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getNotProtectField(String moduleId, String tableId, Set ptControlSet) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(moduleId, String.class);
      pg.put(tableId, String.class);
      pg.put(ptControlSet, Set.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getNotProtectField", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNotProtectField information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String[] getProcActiUser(String tableId, String recordId, String activityId) {
    String[] str = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      str = (String[])ejbProxy.invoke("getProcActiUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcActiUser information :" + e.getMessage());
    } finally {}
    return str;
  }
  
  public List getCandidate(String userIdStr, String groupIdStr) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userIdStr, String.class);
      pg.put(groupIdStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getCandidate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCandidate information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public List getCandidateSeq(String userIdStr, String userNameStr) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userIdStr, String.class);
      pg.put(userNameStr, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getCandidateSeq", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCandidate information :" + e.getMessage());
    } finally {}
    return list;
  }
  
  public String getNextActivityClass(String curActivityId, String tableId, String recordId, String domainId) {
    String res = "1";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(curActivityId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      res = (String)ejbProxy.invoke("getNextActivityClass", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNextActivityClass information :" + e.getMessage());
    } finally {}
    return res;
  }
  
  public String getNextDefaultActivityId(String fatherActivityId) {
    String res = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fatherActivityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      res = (String)ejbProxy.invoke("getNextDefaultActivityId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getNextActivityClass information :" + e.getMessage());
    } finally {}
    return res;
  }
  
  public int getWorkStatusByWorkId(String workId) {
    int res = -1;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      res = Integer.parseInt((String)ejbProxy.invoke("getWorkStatusByWorkId", pg.getParameters()));
    } catch (Exception e) {
      logger.error("error to getWorkStatusByWorkId information :" + e.getMessage());
    } finally {}
    return res;
  }
  
  public List getExportList(String viewSql, String fromSql, String whereSql) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(viewSql, String.class);
      pg.put(fromSql, String.class);
      pg.put(whereSql, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getExportList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getExportList information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String getHangup(String processId, String recordId, String tableId, String processName) {
    String flag = "0";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      pg.put(processName, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      flag = (String)ejbProxy.invoke("getHangup", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getHangup information :" + e.getMessage());
    } 
    return flag;
  }
  
  public List getWfPackageList(String domainId, String packagewhere) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(domainId, String.class);
      pg.put(packagewhere, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getWfPackageList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWfPackageList information :" + e.getMessage());
    } 
    return alist;
  }
  
  public List getAllFormInfo(String tableId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAllFormInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllFormInfo information :" + e.getMessage());
    } 
    return alist;
  }
  
  public String getProcessMainFormId(String processId) {
    String formId = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      formId = (String)ejbProxy.invoke("getProcessMainFormId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcessMainFormId information :" + e.getMessage());
    } 
    return formId;
  }
  
  public String getProcessFirstFormId(String processId) {
    String formId = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(processId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      formId = (String)ejbProxy.invoke("getProcessFirstFormId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getProcessFirstFormId information :" + e.getMessage());
    } 
    return formId;
  }
  
  public String getFormIdByActivityId(String activityId) {
    String formId = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      formId = (String)ejbProxy.invoke("getFormIdByActivityId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFormIdByActivityId information :" + e.getMessage());
    } 
    return formId;
  }
  
  public String getActivityDelayTime(String activityId, String processId, String recordId, String tableId) {
    String delayTime = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(activityId, String.class);
      pg.put(processId, String.class);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", 
          "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      delayTime = (String)ejbProxy.invoke("getActivityDelayTime", pg.getParameters());
      if ("null".equalsIgnoreCase(delayTime))
        delayTime = ""; 
    } catch (Exception e) {
      logger.error("error to getActivityDelayTime information :" + e.getMessage());
    } 
    return delayTime;
  }
  
  public List getDraftComment(String workid, String recordId, String tableId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(workid, String.class);
      pg.put(recordId, String.class);
      pg.put(tableId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowEJB", "WorkFlowEJBLocal", WorkFlowEJBHome.class);
      list = (List)ejbProxy.invoke("getDraftComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealWithCommentNotBack information :" + e.getMessage());
    } finally {}
    return list;
  }
}
