package com.js.oa.jsflow.service;

import com.js.oa.jsflow.bean.WorkFlowButtonEJBBean;
import com.js.oa.jsflow.bean.WorkFlowButtonEJBHome;
import com.js.oa.jsflow.vo.WorkLogVO;
import com.js.oa.jsflow.vo.WorkVO;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class WorkFlowButtonBD {
  private static Logger logger = Logger.getLogger(WorkFlowButtonBD.class
      .getName());
  
  public String undoWork(WorkVO workVO) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("undoWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to undoWork information :" + e.getMessage());
    } 
    return ret;
  }
  
  public boolean deleteWork(WorkVO workVO) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("deleteWork", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to deleteWork information :" + e.getMessage());
    } 
    return bl;
  }
  
  public boolean receiveWork(WorkVO workVO) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("receiveWork", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to receiveWork information :" + e.getMessage());
    } 
    return bl;
  }
  
  public List getReceiveActivityList(String tableId, String recordId, String userId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getReceiveActivityList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getReceiveActivityList information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public List getFrontActivityOper(WorkVO workVO) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getFrontActivityOper", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFrontActivityOper information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public boolean endWork(WorkVO workVO) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("endWork", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to endWork information :" + e.getMessage());
    } 
    return bl;
  }
  
  public boolean getOnlineUserByUserAccounts(String userAccounts) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userAccounts, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("getOnlineUserByUserAccounts", 
          pg.getParameters())).booleanValue();
    } catch (Exception e) {
      logger.error("error to getOnlineUserByUserAccounts information :" + 
          e.getMessage());
    } 
    return bl;
  }
  
  public boolean hasMoreDealFile(WorkVO workVO) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("hasMoreDealFile", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to hasMoreDealFile information :" + 
          e.getMessage());
    } 
    return bl;
  }
  
  public List getAllPassRoundUsers(WorkVO workVO) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAllPassRoundUsers", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllPassRoundUsers information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public String getCurTransactTypeByWorkId(String workId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getCurTransactTypeByWorkId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCurTransactTypeByWorkId information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String backToActivity(Map parameter) {
    String exeUser = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(parameter, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      exeUser = (String)ejbProxy.invoke("backToActivity", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to backToActivity information :" + e.getMessage());
    } 
    return exeUser;
  }
  
  public List getFlowedActivity(String tableId, String recordId, String stepCount) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(stepCount, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getFlowedActivity", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getFlowedActivity information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public Integer backToSubmitPerson(Map parameter) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(parameter, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (Integer)ejbProxy.invoke("backToSubmitPerson", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to backToSubmitPerson information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public boolean showUndoButton(WorkVO workVO) {
    boolean bl = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workVO, WorkVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      bl = ((Boolean)ejbProxy.invoke("showUndoButton", pg.getParameters()))
        .booleanValue();
    } catch (Exception e) {
      logger.error("error to showUndoButton information :" + e.getMessage());
    } 
    return bl;
  }
  
  public String getSaveFirstWorkUrl(String processId, String processName, String tableId, String recordId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(processName, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getSaveFirstWorkUrl", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSaveFirstWorkUrl information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public List getAllDealwithUsersByStatus(WorkVO workVO, String workStatus) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(workVO, WorkVO.class);
      pg.put(workStatus, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAllDealwithUsersByStatus", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllDealwithUsersByStatus information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public List getAllDealwithUsersByStatus2(WorkVO workVO, String workStatus) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(workVO, WorkVO.class);
      pg.put(workStatus, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAllDealwithUsersByStatus2", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllDealwithUsersByStatus information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public String getBackComment(String processId, String tableId, String recordId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getBackComment", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getBackComment information :" + e.getMessage());
    } 
    return ret;
  }
  
  public List getAllDealWithLog(String processId, String tableId, String recordId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAllDealWithLog", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllDealWithLog information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public void setDealWithLog(WorkLogVO workLogVO) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workLogVO, WorkLogVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("setDealWithLog", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setDealWithLog information :" + e.getMessage());
    } 
  }
  
  public String getFieldInfoByFieldId(String fieldId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fieldId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getFieldInfoByFieldId", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("error to getFieldInfoByFieldId information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String getFieldShowByFieldId(String fieldId) {
    return (new WorkFlowButtonEJBBean()).getFieldShowByFieldId(fieldId);
  }
  
  public String[] getModiCommentByCommField(String tableId, String recordId, String commField, String userId, String stepCount) {
    String[] ret = { "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      pg.put(userId, String.class);
      pg.put(stepCount, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String[])ejbProxy.invoke("getModiCommentByCommField", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getModiCommentByCommField information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String getCommentByCommField(String processId, String tableId, String recordId, String commField, String userId, String orgId, String isEdit, String hideModiComment) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(8);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      pg.put(userId, String.class);
      pg.put(orgId, String.class);
      pg.put(isEdit, String.class);
      pg.put(hideModiComment, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (String)ejbProxy.invoke("getCommentByCommField", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getCommentByCommField information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public String[] getCommentUserAndDateByCommField(String tableId, String recordId, String commField) {
    String[] ret = { "", "" };
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(commField, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String[])ejbProxy.invoke("getCommentUserAndDateByCommField", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error(
          "error to getCommentUserAndDateByCommField information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public void addPersonWork(String[] para, String[] user) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(para, String[].class);
      pg.put(user, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("addPersonWork", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to addPersonWork information :" + e.getMessage());
    } 
  }
  
  public Integer insertPassroundDealWith(Map para) {
    Integer result = Integer.valueOf("0");
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (Integer)ejbProxy.invoke("insertPassroundDealWith", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to insertPassroundDealWith information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public void setWFOnlineUser(Map para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("setWFOnlineUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setWFOnlineUser information :" + 
          e.getMessage());
    } 
  }
  
  public String[] getWFOnlineUser(Map para) {
    String[] ret = (String[])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String[])ejbProxy.invoke("getWFOnlineUser", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWFOnlineUser information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public void delWFOnlineUser(Map para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, Map.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("delWFOnlineUser", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to delWFOnlineUser information :" + 
          e.getMessage());
    } 
  }
  
  public Map getBackToPerson(String tableId, String recordId, String stepCount, String initStepCount, String workId) {
    Map result = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(stepCount, String.class);
      pg.put(initStepCount, String.class);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (Map)ejbProxy.invoke("getBackToPerson", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public List getLeaderByDutyLevelAndOrg(String userId, String dutyOper) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(userId, String.class);
      pg.put(dutyOper, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getLeaderByDutyLevelAndOrg", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getLeaderByDutyLevelAndOrg information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public String getDealTipsByWorkId(String workId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getDealTipsByWorkId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealTipsByWorkId information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String getDealTipsByLogId(String logId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(logId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getDealTipsByLogId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDealTipsByLogId information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public List getPressDealList() {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(0);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getPressDealList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getPressDealList information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public void setWorkTask(String workIds, String taskStatus) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(workIds, String.class);
      pg.put(taskStatus, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("setWorkTask", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setWorkTask information :" + e.getMessage());
    } 
  }
  
  public void tranAutoReturn(String[] para) {
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(para, String[].class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("tranAutoReturn", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to tranAutoReturn information :" + e.getMessage());
    } 
  }
  
  public void setWorkViewedDate(String workId, WorkLogVO workLogVO) {
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(workId, String.class);
      pg.put(workLogVO, WorkLogVO.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("setWorkViewedDate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setWorkViewedDate information :" + 
          e.getMessage());
    } 
  }
  
  public void setWorkViewedDate(String workId, WorkLogVO workLogVO, String lastStepCount) {
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(workId, String.class);
      pg.put(workLogVO, WorkLogVO.class);
      pg.put(lastStepCount, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("setWorkViewedDate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to setWorkViewedDate information :" + 
          e.getMessage());
    } 
  }
  
  public String getWorkViewedDate(String processId, String tableId, String recordId, String empId, String stepcount) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(empId, String.class);
      pg.put(stepcount, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getWorkViewedDate", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkViewedDate information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String getUserOrgId(String empId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getUserOrgId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserOrgId information :" + e.getMessage());
    } 
    return ret;
  }
  
  public List getAllRelationWork(String processId, String tableId, String recordId, String workId) {
    ArrayList alist = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(workId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      alist = (ArrayList)ejbProxy.invoke("getAllRelationWork", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getAllRelationWork information :" + 
          e.getMessage());
    } 
    return alist;
  }
  
  public void cleanWFOnlineUserInvalidate() {
    try {
      ParameterGenerator pg = new ParameterGenerator(0);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("cleanWFOnlineUserInvalidate", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to cleanWFOnlineUserInvalidate information :" + 
          e.getMessage());
    } 
  }
  
  public String getUserSideLineOrgId(String empId) {
    String ret = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(empId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ret = (String)ejbProxy.invoke("getUserSideLineOrgId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserSideLineOrgId information :" + 
          e.getMessage());
    } 
    return ret;
  }
  
  public String saveToDraft(String processId, String tableId, String recordId, String userId, String workTitle, String processName) {
    return saveToDraft(processId, tableId, recordId, userId, workTitle, processName, "-1");
  }
  
  public String saveToDraft(String processId, String tableId, String recordId, String userId, String workTitle, String processName, String relProjectId) {
    try {
      ParameterGenerator pg = new ParameterGenerator(7);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(userId, String.class);
      pg.put(workTitle, String.class);
      pg.put(processName, String.class);
      pg.put(relProjectId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("saveToDraft", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveToDraft information :" + e.getMessage());
    } 
    return null;
  }
  
  public String saveFirstComp(String processId, String tableId, String recordId, String userId, String workTitle, String processName) {
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(userId, String.class);
      pg.put(workTitle, String.class);
      pg.put(processName, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("saveFirstComp", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveToDraft information :" + e.getMessage());
    } 
    return null;
  }
  
  public String getSubmitPerson(String workId, String tableId, String recordId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(workId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (String)ejbProxy.invoke("getSubmitPerson", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveToDraft information :" + e.getMessage());
    } 
    return result;
  }
  
  public String getSubProcWorkId(String processId, String tableId, String recordId, String activityId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(activityId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (String)ejbProxy.invoke("getSubProcWorkId", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getSubProcWorkId(String processId, String tableId, String recordId) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (String)ejbProxy.invoke("getSubProcWorkId", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getSubProcWorkId information :" + e.getMessage());
    } 
    return result;
  }
  
  public String deleteFlowTempData(String processId, String tableId, String recordId, String flag) {
    String result = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(flag, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      result = (String)ejbProxy.invoke("deleteFlowTempData", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to deleteFlowTempData information :" + e.getMessage());
    } 
    return result;
  }
  
  public String setCurrentStep(String processId, String tableId, String recordId, String currentStep, String selUserId, String transactType) {
    String result = "-1";
    try {
      ParameterGenerator pg = new ParameterGenerator(6);
      pg.put(processId, String.class);
      pg.put(tableId, String.class);
      pg.put(recordId, String.class);
      pg.put(currentStep, String.class);
      pg.put(selUserId, String.class);
      pg.put(transactType, String.class);
      EJBProxy ejbProxy = new EJBProxy("WorkFlowButtonEJB", 
          "WorkFlowButtonEJBLocal", WorkFlowButtonEJBHome.class);
      ejbProxy.invoke("setCurrentStep", pg.getParameters());
      result = "0";
    } catch (Exception e) {
      logger.error("error to setCurrentStep information :" + e.getMessage());
    } 
    return result;
  }
  
  public List<String[]> getAllComment(String workId) {
    String sql = "SELECT d.ACTIVITYNAME,c.dealwithemployeecomment,c.dealwithtime,e.EMPNAME FROM JSF_WORK w JOIN jsf_dealwith d ON w.WORKTABLE_ID=d.DATABASETABLE_ID AND w.workRecord_id=d.DATABASERECORD_ID JOIN jsf_dealwithcomment c ON d.WF_DEALWITH_ID=c.WF_DEALWITH_ID JOIN org_employee e ON c.DEALWITHEMPLOYEE_ID=e.EMP_ID WHERE w.WF_WORK_ID=" + 
      
      workId + " ORDER BY w.WF_WORK_ID";
    return (new WorkFlowButtonEJBBean()).getAllComment(sql);
  }
  
  public String getMobilePageId(String pageId) {
    String mobilePageId = "";
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    String sql = "select t.mobile_page_id from TPAGE t where t.page_id=? ";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, pageId);
      rs = pstmt.executeQuery();
      if (rs.next())
        mobilePageId = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return mobilePageId;
  }
}
