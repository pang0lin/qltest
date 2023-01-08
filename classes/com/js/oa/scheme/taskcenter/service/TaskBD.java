package com.js.oa.scheme.taskcenter.service;

import com.js.oa.scheme.taskcenter.bean.TaskEJBBean;
import com.js.oa.scheme.taskcenter.bean.TaskEJBHome;
import com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO;
import com.js.oa.scheme.taskcenter.po.TaskReportPO;
import com.js.oa.scheme.taskcenter.vo.RightScope;
import com.js.oa.scheme.taskcenter.vo.TaskClassVO;
import com.js.oa.scheme.taskcenter.vo.TaskReportVO;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TaskBD {
  private Integer volume = new Integer(15);
  
  private int recordCount = 0;
  
  public boolean addTaskClass(TaskClassVO taskClassVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskClassVO, TaskClassVO.class);
      result = ((Boolean)ejbProxy.invoke("addTaskClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add TaskClass Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteTaskClass(Long classId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(classId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteTaskClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete TaskClass Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyTaskClass(TaskClassVO taskClassVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskClassVO, TaskClassVO.class);
      result = ((Boolean)ejbProxy.invoke("modifyTaskClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify TaskClass Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String taskClassNameIsExist(String className, Long classId, Long domainId) {
    String result = "no";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(className, "String");
      pg.put(classId, "Long");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("taskClassNameIsExist", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("modify TaskClass Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteAllTaskClass(Long userId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteAllTaskClass", (Object[][])null))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete All TaskClass Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchTaskClass(String classIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(classIds, "String");
      result = ((Boolean)ejbProxy.invoke("deleteBatchTaskClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete Batch TaskClass Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectAllTaskClass(Long domainId, Long userId, String orgIdString) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(domainId, "Long");
      pg.put(userId, "Long");
      pg.put(orgIdString, "String");
      result = (List)ejbProxy.invoke("selectAllTask", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All taskclass Exception：" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectAllTaskClass(Long userId, Long orgId, Integer currentPage, Integer volume, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectAllTaskClass", pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getTaskClassRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All taskclass Exception：" + ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public TaskClassVO selectSingleTaskClass(Long classId) {
    TaskClassVO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(classId, "Long");
      result = (TaskClassVO)ejbProxy.invoke("selectSingleTaskClass", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Single TaskClass Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectAllTaskClass(Long userId, Long orgId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectAllTaskClass", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All taskclass Exception：" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addTask(TaskVO task) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(task, TaskVO.class);
      result = ((Boolean)ejbProxy.invoke("addTask", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyTask(TaskVO task, String userName, String userId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(task, TaskVO.class);
      pg.put(userName, String.class);
      pg.put(userId, String.class);
      result = ((Boolean)ejbProxy.invoke("modifyTask", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteTask(Long taskId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteTask", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean cancelTask(Long taskId, Long domainId, String cancelReason) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskId, "Long");
      pg.put(domainId, "Long");
      pg.put(cancelReason, "String");
      result = ((Boolean)ejbProxy.invoke("cancelTask", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean taskPass(Long userId, Long taskId, Long domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      Integer status = new Integer(1);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      pg.put(status, "Integer");
      pg.put(domainId, "Long");
      result = ((Boolean)ejbProxy.invoke("taskPass", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean taskUnPass(Long userId, Long taskId, Long domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      Integer status = new Integer(2);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      pg.put(status, "Integer");
      pg.put(domainId, "Long");
      result = ((Boolean)ejbProxy.invoke("taskPass", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectCompleteTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectCompleteTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getCompleteTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Complete Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectPrincipalTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectPrincipalTask", pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getPrincipalTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectPrincipalTask(Long userId, Integer currentPage, Long domainId, String toUserId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      pg.put(toUserId, "String");
      result = (List)ejbProxy.invoke("selectPrincipalTask", pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      pg.put(toUserId, "String");
      ret = (Integer)ejbProxy.invoke("getPrincipalTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectCancelTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectCancelTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getCancelTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectsettleCancelTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectsettleCancelTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getCancelSettleRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectAuditingTask(Long userId, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectAuditingTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getAuditingTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Auditing Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectJoinTask(Long userId, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectJoinTask", pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getJoinTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Join Task Exception: " + ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List searchPrincipalTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer recordCount = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(0), "Integer");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchTask", pg.getParameters());
      pg = new ParameterGenerator(4);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(0), "Integer");
      pg.put(domainId, "Long");
      recordCount = (Integer)ejbProxy.invoke("getSearchTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(recordCount.intValue());
    return result;
  }
  
  public List searchPrincipalTask(Long userId, String[] parameters, Integer currentPage, Long domainId, String toUserId) {
    List result = null;
    Integer recordCount = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      result = (new TaskEJBBean()).searchTask(userId, parameters, Integer.valueOf(0), currentPage, this.volume, domainId, toUserId);
      pg = new ParameterGenerator(5);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(0), "Integer");
      pg.put(domainId, "Long");
      pg.put(toUserId, "String");
      recordCount = (Integer)ejbProxy.invoke("getSearchTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(recordCount.intValue());
    return result;
  }
  
  public List searchJoinTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer recordCount = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(1), "Integer");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchTask", pg.getParameters());
      pg = new ParameterGenerator(4);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(1), "Integer");
      pg.put(domainId, "Long");
      recordCount = (Integer)ejbProxy.invoke("getSearchTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Join Task Exception: " + ex.getMessage());
    } finally {}
    setRecordCount(recordCount.intValue());
    return result;
  }
  
  public List searchAuditingTask(Long userId, String searchContent, Integer currentPage, Long domainId) {
    List result = null;
    Integer recordCount = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(searchContent, "String");
      pg.put(new Integer(2), "Integer");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchTask", pg.getParameters());
      pg = new ParameterGenerator(4);
      pg.put(userId, "Long");
      pg.put(searchContent, "String");
      pg.put(new Integer(2), "Integer");
      pg.put(domainId, "Long");
      recordCount = (Integer)ejbProxy.invoke("getSearchTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Auditing Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(recordCount.intValue());
    return result;
  }
  
  public List selectSettleTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectSettleTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSettleTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Task Exception: " + ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectSettleCheckTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectSettleCheckTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSettleCheckTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Check Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectSettleCheckTaskall(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectSettleCheckTaskall", pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSettleCheckTaskRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Check Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List selectSettleFinishTask(Long userId, Integer currentPage, Long domainId, String type, String sortType) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      pg.put(type, "String");
      pg.put(sortType, "String");
      result = (List)ejbProxy.invoke("selectSettleFinishTask", 
          pg.getParameters());
      pg = new ParameterGenerator(2);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSettleFinishRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Finish Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List searchSettleTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchSettleTask", 
          pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSSTRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Task Exception: " + ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List searchSettleCheckTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchSettleCheckTask", 
          pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSSCTRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Check Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List searchSettleFinishTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchSettleFinishTask", 
          pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getSSFRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Finish Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List searchCompleteTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer recordCount = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(3), "Integer");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchTask", pg.getParameters());
      pg = new ParameterGenerator(4);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(3), "Integer");
      pg.put(domainId, "Long");
      recordCount = (Integer)ejbProxy.invoke("getSearchTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Join Task Exception: " + ex.getMessage());
    } finally {}
    setRecordCount(recordCount.intValue());
    return result;
  }
  
  public Map selectSingleTask(Long userId, Long taskId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      result = (Map)ejbProxy.invoke("selectSingleTask", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select single Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public TaskVO selectSettleSingleTask(Long userId, Long taskId, Long domainId) {
    TaskVO result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      pg.put(domainId, "Long");
      result = (TaskVO)ejbProxy.invoke("selectSettleSingleTask", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select single Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public Map selectTaskReport(Long userId, Long taskId, Long domainId) {
    Map result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      pg.put(domainId, "Long");
      result = (Map)ejbProxy.invoke("selectTaskReport", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select single Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addTaskReport(Long userId, Long taskId, String reportInfo, Long domainId, TaskReportPO taskReportPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      pg.put(reportInfo, "String");
      pg.put(domainId, "Long");
      pg.put(taskReportPO, TaskReportPO.class);
      result = ((Boolean)ejbProxy.invoke("addTaskReport", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add Task Report Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addTaskReport(TaskReportVO report, Integer status, String completeTime, TaskReportPO taskReportPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(report, TaskReportVO.class);
      pg.put(status, "Integer");
      pg.put(completeTime, "String");
      pg.put(taskReportPO, TaskReportPO.class);
      result = ((Boolean)ejbProxy.invoke("addTaskReport", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add Task Report Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean isPrincipal(Long userId, Long taskId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(taskId, "Long");
      result = ((Boolean)ejbProxy.invoke("isPrincipal", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("is Principal Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean checkTask(Long taskId, String checkOpinion, Date checkTime, String checkerName, Long checkerId, Integer finishRate, Long domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(7);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskId, "Long");
      pg.put(checkOpinion, "String");
      pg.put(checkTime, "Date");
      pg.put(checkerName, "String");
      pg.put(checkerId, "Long");
      pg.put(finishRate, "Integer");
      pg.put(domainId, "Long");
      result = ((Boolean)ejbProxy.invoke("checkTask", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("is Principal Exception: " + ex.getMessage());
    } finally {}
    pg = null;
    return result;
  }
  
  public boolean hasRight(Long userId, String rightType) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(rightType, "String");
      Boolean b = (Boolean)ejbProxy.invoke("hasRight", pg.getParameters());
      result = b.booleanValue();
    } catch (Exception ex) {
      System.out.println("delete has Right Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean hasRight(Long userId, String rightType, String rightName) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(rightType, "String");
      pg.put(rightName, "String");
      Boolean b = (Boolean)ejbProxy.invoke("hasRight", pg.getParameters());
      result = b.booleanValue();
    } catch (Exception ex) {
      System.out.println("delete has Right Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public RightScope getRightScope(Long userId, Long orgId, String rightType, String rightName) {
    RightScope result = null;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(rightType, "String");
      pg.put(rightName, "String");
      result = (RightScope)ejbProxy.invoke("getRightScope", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("get Right Scope Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addPeriodicityTask(TaskPeriodicityPO taskPeriodicityPO, String[] fileName, String[] saveName) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskPeriodicityPO, TaskPeriodicityPO.class);
      pg.put(fileName, String[].class);
      pg.put(saveName, String[].class);
      result = ((Boolean)ejbProxy.invoke("addPeriodicityTask", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("addPeriodicityTask Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public TaskPeriodicityPO selectPeriodicityTaskView(Long periodicityId, Long domainId) {
    TaskPeriodicityPO result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(periodicityId, "Long");
      pg.put(domainId, "Long");
      result = (TaskPeriodicityPO)ejbProxy.invoke("selectPeriodicityTaskView", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select single Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyPeriodicityTask(TaskPeriodicityPO taskPeriodicityPO, String[] fileName, String[] saveName) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskPeriodicityPO, TaskPeriodicityPO.class);
      pg.put(fileName, String[].class);
      pg.put(saveName, String[].class);
      result = ((Boolean)ejbProxy.invoke("modifyPeriodicityTask", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modifyPeriodicityTask Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deletePeriodicityTask(Long periodicityId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(periodicityId, "Long");
      result = ((Boolean)ejbProxy.invoke("deletePeriodicityTask", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("deletePeriodicityTask Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public Integer getTaskPrincipalRecordCount(Long userId, Long domainId) {
    Integer result = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      result = (Integer)ejbProxy.invoke("getTaskPrincipalRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("getTaskPrincipalRecordCount Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public Integer getTasktaskJoinedEmpRecordCount(Long userId, Long domainId) {
    Integer result = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      result = (Integer)ejbProxy.invoke("getTasktaskJoinedEmpRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("getTasktaskJoinedEmpRecordCount Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public Integer getSettleCheckTaskRecordCount(Long userId, Long domainId) {
    Integer result = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      result = (Integer)ejbProxy.invoke("getSettleCheckTaskRecordCount", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("getSettleCheckTaskRecordCount Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectAllTask(Long userId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectAllTask", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Join Task Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String selectUserIds(String orgId) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(orgId, "String");
      result = (String)ejbProxy.invoke("selectUserIds", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectUserIds Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteremind(String taskId, String userId, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskId, "String");
      pg.put(userId, "String");
      pg.put(domainId, "String");
      ejbProxy.invoke("deleteremind", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("selectUserIds Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteremindall(String taskId, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskId, "String");
      pg.put(domainId, "String");
      ejbProxy.invoke("deleteremindall", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("selectUserIds Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean sendremind(String taskId, String temps, String domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskId, "String");
      pg.put(temps, "String");
      pg.put(domainId, "String");
      ejbProxy.invoke("sendremind", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("selectUserIds Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String checkhastype(String classIdStr) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(classIdStr, "String");
      result = (String)ejbProxy.invoke("checkhastype", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("checkhastype Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getaccessory(String taskid) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskid, "String");
      result = (List)ejbProxy.invoke("getaccessory", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("getaccessory Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getchildtasksta(String taskid) {
    String result = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(taskid, "String");
      result = (String)ejbProxy.invoke("getchildtasksta", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectUserIds Exception: " + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean saveHistory(String taskid, String userId, String userName, String orgId, String orgName) {
    boolean result = false;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(taskid, String.class);
      pg.put(userId, String.class);
      pg.put(userName, String.class);
      pg.put(orgId, String.class);
      pg.put(orgName, String.class);
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      ejbProxy.invoke("saveHistory", pg.getParameters());
      result = true;
    } catch (Exception e) {
      System.out.println("error to saveHistory information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getAccessory(String taskhis, String taskid) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(taskhis, String.class);
      pg.put(taskid, String.class);
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      result = (List)ejbProxy.invoke("getAccessory", pg.getParameters());
    } catch (Exception e) {
      System.out.println("error to getAccessory information :" + e.getMessage());
    } finally {}
    return result;
  }
  
  public List getHistoryInfo(String taskid) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(taskid, String.class);
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      result = (List)ejbProxy.invoke("getHistoryInfo", pg.getParameters());
    } catch (Exception e) {
      System.out.println("error to getHistoryInfo information :" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public List selectSingleTaskHis(String taskid) {
    List result = new ArrayList();
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(taskid, String.class);
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      result = (List)ejbProxy.invoke("selectSingleTaskHis", pg.getParameters());
    } catch (Exception e) {
      System.out.println("error to getHistoryInfo information :" + 
          e.getMessage());
    } finally {}
    return result;
  }
  
  public List searchCancelTask(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer recordCount = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(4), "Integer");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchTask", pg.getParameters());
      pg = new ParameterGenerator(4);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(new Integer(4), "Integer");
      pg.put(domainId, "Long");
      recordCount = (Integer)ejbProxy.invoke("getSearchTaskRecordCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
    } finally {}
    setRecordCount(recordCount.intValue());
    return result;
  }
  
  public List searchCancel(Long userId, String[] parameters, Integer currentPage, Long domainId) {
    List result = null;
    Integer ret = new Integer(0);
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", "TaskEJBLocal", TaskEJBHome.class);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(currentPage, "Integer");
      pg.put(this.volume, "Integer");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchCancelSettleTask", pg.getParameters());
      pg = new ParameterGenerator(3);
      pg.put(userId, "Long");
      pg.put(parameters, "String[]");
      pg.put(domainId, "Long");
      ret = (Integer)ejbProxy.invoke("getCancelSettleRC", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Settle Task Exception: " + ex.getMessage());
    } finally {}
    setRecordCount(ret.intValue());
    return result;
  }
  
  public List getSonsById(String orgId) {
    List list = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      EJBProxy ejbProxy = new EJBProxy("TaskEJB", 
          "TaskEJBLocal", TaskEJBHome.class);
      pg.put(orgId, String.class);
      list = (List)ejbProxy.invoke("getSonsById", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public int getVolume() {
    return this.volume.intValue();
  }
  
  public void setVolume(int volume) {
    this.volume = new Integer(volume);
  }
  
  public int getRecordCount() {
    return this.recordCount;
  }
  
  public void setRecordCount(int value) {
    this.recordCount = value;
  }
  
  public static void main(String[] args) {
    TaskBD taskBD = new TaskBD();
  }
}
