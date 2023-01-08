package com.js.oa.scheme.worklog.service;

import com.js.oa.scheme.util.ValueListHandler;
import com.js.oa.scheme.worklog.bean.WorkLogEJBBean;
import com.js.oa.scheme.worklog.bean.WorkLogEJBHome;
import com.js.oa.scheme.worklog.po.ProjectStepPO;
import com.js.oa.scheme.worklog.po.WorkLogCommentPO;
import com.js.oa.scheme.worklog.po.WorkLogPO;
import com.js.oa.scheme.worklog.po.WorkProjectClassPO;
import com.js.oa.scheme.worklog.po.WorkProjectPO;
import com.js.oa.scheme.worklog.po.WorkProjectTaskPO;
import com.js.oa.scheme.worklog.vo.ProjectStepVO;
import com.js.oa.scheme.worklog.vo.WorkLogDO;
import com.js.oa.scheme.worklog.vo.WorkLogVO;
import com.js.oa.scheme.worklog.vo.WorkProjectClassVO;
import com.js.oa.scheme.worklog.vo.WorkProjectVO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class WorkLogBD extends ValueListHandler {
  private static Logger logger = (Logger)Logger.getInstance(WorkLogBD.class
      .getName());
  
  public boolean addWorkProjectClass(WorkProjectClassPO workProjectClassPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workProjectClassPO, WorkProjectClassPO.class);
      ejbProxy.invoke("addWorkProjectClass", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("Add WorkProjectClass Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public WorkProjectClassVO selectWorkProjectClass(Long id) {
    WorkProjectClassVO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(id, "Long");
      result = (WorkProjectClassVO)ejbProxy.invoke(
          "selectWorkProjectClass", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select WorkProjectClass Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public void selectAllWorkProjectClass(Long userId, Long orgId, Long domainId) {
    List list = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      list = (List)ejbProxy.invoke("selectAllWorkProjectClass", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Select All WorkProjectClass Exception:" + 
          ex.getMessage());
    } finally {
      setList(list);
    } 
  }
  
  public boolean deleteWorkProjectClass(Long classId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classId, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteWorkProjectClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception exception) {}
    return result;
  }
  
  public boolean deleteBatchWorkProjectClass(String classIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classIds, "String");
      result = ((Boolean)ejbProxy.invoke("deleteBatchWorkProjectClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete Batch WorkProjectClass Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyWorkProjectClass(WorkProjectClassVO workProjectClassVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workProjectClassVO, WorkProjectClassVO.class);
      result = ((Boolean)ejbProxy.invoke("modifyWorkProjectClass", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify WorkProjectClass Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String workProjectClassNameIsExist(String workProjectClassName, Long domainId) {
    String result = "no";
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workProjectClassName, "String");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("workProjectClassNameIsExist", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("work Project Class Name Is Exist Exception: " + 
          ex.getMessage());
    } finally {}
    pg = null;
    return result;
  }
  
  public boolean addProjectStep(ProjectStepPO projectStepPO, Long classId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectStepPO, ProjectStepPO.class);
      pg.put(classId, "Long");
      ejbProxy.invoke("addProjectStep", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("add ProjectStep Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public ProjectStepVO selectSingleProjectStep(Long id) {
    ProjectStepVO projectStepVO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(id, "Long");
      projectStepVO = (ProjectStepVO)ejbProxy.invoke(
          "selectSingleProjectStep", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select ProjectStep Exception:" + ex.getMessage());
    } finally {}
    return projectStepVO;
  }
  
  public void selectAllProjectStep(Long classId, Long domainId) {
    List list = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classId, "Long");
      pg.put(domainId, "Long");
      list = (List)ejbProxy.invoke("selectAllProjectStep", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All ProjectStep Exception:" + 
          ex.getMessage());
    } finally {
      setList(list);
    } 
  }
  
  public boolean deleteProjectStep(Long id) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(id, "Long");
      ejbProxy.invoke("deleteProjectStep", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchProjectStep(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(ids, "String");
      ejbProxy.invoke("deleteBatchProjectStep", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delete Batch ProjectStep where classId = " + 
          ids + " Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteAllProjectStep(Long classId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classId, "Long");
      ejbProxy.invoke("deleteAllProjectStep", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delete All ProjectStep where classId = " + 
          classId + " Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyProjectStep(ProjectStepVO projectStepVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectStepVO, ProjectStepVO.class);
      ejbProxy.invoke("modifyProjectStep", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("modify ProjectStep Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public String projectStepNameIsExist(Long classId, String projectStepName, Long domainId) {
    String result = "no";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classId, "Long");
      pg.put(projectStepName, "String");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("projectStepNameIsExist", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("Project Step Name is Exist Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addWorkProject(WorkProjectPO workProjectPO, Long classId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workProjectPO, WorkProjectPO.class);
      pg.put(classId, "Long");
      ejbProxy.invoke("addWorkProject", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("add WorkProject Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteWorkProject(Long id) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(id, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteWorkProject", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception exception) {
    
    } finally {}
    return result;
  }
  
  public boolean deleteBatchWorkProject(String projectIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectIds, "String");
      result = ((Boolean)ejbProxy.invoke("deleteBatchWorkProject", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception exception) {
    
    } finally {}
    return result;
  }
  
  public boolean modifyWorkProject(WorkProjectVO workProjectVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workProjectVO, WorkProjectVO.class);
      result = ((Boolean)ejbProxy.invoke("modifyWorkProject", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modify WorkProject Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public void selectAllWorkProject(Long userId, Long orgId, Long domainId, String where, String order) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(5);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      pg.put(where, "String");
      pg.put(order, "String");
      result = (List)ejbProxy.invoke("selectAllWorkProject", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All WorkProject Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public WorkProjectVO selectSingleWorkProject(Long projectId, Long domainId) {
    WorkProjectVO result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectId, "Long");
      pg.put(domainId, "Long");
      result = (WorkProjectVO)ejbProxy.invoke("selectSingleWorkProject", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Single WorkProject Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String workProjectIsExist(Long classId, String workProjectName, Long domainId) {
    String result = "no";
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classId, "Long");
      pg.put(workProjectName, "String");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("workProjectIsExist", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("workProject Is Exist Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectAllWorkProjectClass(Long userId, String orgIdString, Long domainId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgIdString, "String");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectAllWorkProjectClass", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select All WorkProject Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addWorkLog(WorkLogVO workLogVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workLogVO, WorkLogVO.class);
      result = ((Boolean)ejbProxy.invoke("addWorkLog", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add WorkLog Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyWorkLog(WorkLogVO workLogVO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workLogVO, WorkLogVO.class);
      result = ((Boolean)ejbProxy.invoke("modifyWorkLog", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add WorkLog Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addWorkLog(String[] projectIds, String[] projectSteps, String[] manHours, String[] logContents, Long userId, Long userOrg, String logDate, String userName, Long domainId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(9);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectIds, String[].class);
      pg.put(projectSteps, String[].class);
      pg.put(manHours, String[].class);
      pg.put(logContents, String[].class);
      pg.put(userId, "Long");
      pg.put(userOrg, "Long");
      pg.put(logDate, "String");
      pg.put(userName, "String");
      pg.put(domainId, "Long");
      result = ((Boolean)ejbProxy.invoke("addWorkLog", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add Multi WorkLog Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addMultiWorkLog(List workLogVOs) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workLogVOs, List.class);
      result = ((Boolean)ejbProxy.invoke("addMultiWorkLog", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add Multi WorkLog Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteWorkLog(Long id) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(id, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteWorkLog", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete WorkLog Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchWorkLog(String ids) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(ids, "String");
      result = ((Boolean)ejbProxy.invoke("deleteBatchWorkLog", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete Batch WorkLog Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public WorkLogVO selectSingleWorkLog(Long logId) {
    WorkLogVO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(logId, "Long");
      result = (WorkLogVO)ejbProxy.invoke("selectSingleWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Single WorkLog Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectProjectForWorkLog(Long userId, String orgIdString, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgIdString, "String");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectProjectForWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Project For WorkLog Exception: " + 
          ex.getMessage());
    } finally {}
    setList(result);
    return result;
  }
  
  public List selectProjectStepForWorkLog(Long classId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(classId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectProjectStepForWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select ProjectStep For WorkLog Exception: " + 
          ex.getMessage());
    } finally {}
    setList(result);
    return result;
  }
  
  public void selectSelfWorkLog(Long userId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectSelfWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Self WorkLog Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public void countProjectSimpleInformation(Long projectId, String stepName, String createdId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectId, "Long");
      pg.put(stepName, "String");
      pg.put(createdId, "String");
      result = (List)ejbProxy.invoke("countProjectSimpleInformation", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("count Project Simple Information Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public void countProjectDetailInformation(String projectIds, String stepName, String createdId, Long userId, Long orgId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectIds, "String");
      pg.put(stepName, "String");
      pg.put(createdId, "String");
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("countProjectDetailInformation", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("count Project Detail Information Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public Float getCountDetailTotalTime(String projectIds, String stepName, String createdId, Long userId, Long orgId, Long domainId) {
    Float result = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectIds, "String");
      pg.put(stepName, "String");
      pg.put(createdId, "String");
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (Float)ejbProxy.invoke("getCountDetailTotalTime", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println(
          "get Count DetailTotal Time Information Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getProjectNameAndClassName(String projectIds, Long domainId) {
    String result = null;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectIds, "String");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("getProjectNameAndClassName", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("count Project Detail Information Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getProjectDetailMaxTime(String projectId, String stepName, String createdId, Long userId, Long orgId, Long domainId) {
    String result = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectId, "String");
      pg.put(stepName, "String");
      pg.put(createdId, "String");
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("getProjectDetailMaxTime", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("get Project Detail Max Time Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String getProjectDetailMinTime(String projectId, String stepName, String createdId, Long userId, Long orgId, Long domainId) {
    String result = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(projectId, "String");
      pg.put(stepName, "String");
      pg.put(createdId, "String");
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("getProjectDetailMinTime", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("get Project Detail Min Time Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public void selectWorkLogByCondition(String searchederIds, Integer isOverTime, Date startTime, Date endTime) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(searchederIds, "searchederIds");
      pg.put(isOverTime, "Integer");
      pg.put(startTime, "Date");
      pg.put(endTime, "Date");
      result = (List)ejbProxy.invoke("selectWorkLogByCondition", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select WorkLog By ConditionException: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public void searchWorkLog(Long userId, String startTime, String endTime, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(4);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(startTime, "String");
      pg.put(endTime, "String");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Project For WorkLog Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public void searchWorkLog(Long userId, Long orgId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Project For WorkLog Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public void searchWorkLog(Long userId, Long orgId, String searchederIds, String startTime, String endTime, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(6);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(searchederIds, "String");
      pg.put(startTime, "String");
      pg.put(endTime, "String");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("searchWorkLog", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select Project For WorkLog Exception: " + 
          ex.getMessage());
    } finally {
      setList(result);
    } 
  }
  
  public List selectWorkProjectForProjectCount(Long userId, Long orgId, Long domainId) {
    List result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, "Long");
      pg.put(orgId, "Long");
      pg.put(domainId, "Long");
      result = (List)ejbProxy.invoke("selectWorkProjectForProjectCount", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println(
          " select WorkProject For Project Count Exception: " + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean hasRight(Long userId, String rightType) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
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
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
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
  
  public WorkLogDO test(Long id) {
    WorkLogDO workLogDO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(id, "Long");
      workLogDO = (WorkLogDO)ejbProxy.invoke(
          "getWorkLogDO", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("select ProjectStep Exception:" + ex.getMessage());
    } finally {}
    return workLogDO;
  }
  
  public String selectProjectDate(String userName, String logDate, Long domainId) {
    String result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userName, "String");
      pg.put(logDate, "String");
      pg.put(domainId, "Long");
      result = (String)ejbProxy.invoke("selectProjectDate", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("addLog Date Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addWorkLog(WorkLogPO workLogPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workLogPO, WorkLogPO.class);
      result = ((Boolean)ejbProxy.invoke("addWorkLog", pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("addWorkLog Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public WorkLogPO selectSingleWorkLogPO(Long logId) {
    WorkLogPO result = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(logId, "Long");
      result = (WorkLogPO)ejbProxy.invoke("selectSingleWorkLogPO", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectSingleWorkLogPO Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyWorkLog(WorkLogPO workLog) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workLog, WorkLogPO.class);
      result = ((Boolean)ejbProxy.invoke("modifyWorkLog", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("modifyWorkLog Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getUserLogList(String userId, String now, String logType) {
    ParameterGenerator pg = new ParameterGenerator(3);
    List result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, String.class);
      pg.put(now, String.class);
      pg.put(logType, String.class);
      result = (List)ejbProxy.invoke("getUserLogList", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserLogList information :" + e.getMessage());
    } 
    return result;
  }
  
  public WorkReportPO getWorkReportPO(String reportDate, String userId) {
    WorkReportPO result = new WorkReportPO();
    try {
      result = (new WorkLogEJBBean()).getWorkReportPO(reportDate, userId);
    } catch (Exception e) {
      logger.error("error to getUserLogList information :" + e.getMessage());
    } 
    return result;
  }
  
  public List getUserLogListByUserName(String userName, String now, String logType) {
    ParameterGenerator pg = new ParameterGenerator(3);
    List result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userName, String.class);
      pg.put(now, String.class);
      pg.put(logType, String.class);
      result = (List)ejbProxy.invoke("getUserLogListByUserName", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getUserLogListByUserName information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public List getDownEmployeeList(String userId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    List result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, String.class);
      result = (List)ejbProxy.invoke("getDownEmployeeList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDownEmployeeList information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public String getWorkLogKeepDay(String domainId) {
    ParameterGenerator pg = new ParameterGenerator(1);
    String result = "0";
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      pg.put(domainId, String.class);
      result = (String)ejbProxy.invoke("getWorkLogKeepDay", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getWorkLogKeepDay information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public boolean deleteWorkLogComment(Long commentid) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(commentid, "Long");
      result = ((Boolean)ejbProxy.invoke("deleteWorkLogComment", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("delete WorkLogComment Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean addWorkLogComment(WorkLogCommentPO workLogCommentPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workLogCommentPO, WorkLogCommentPO.class);
      result = ((Boolean)ejbProxy.invoke("addWorkLogComment", 
          pg.getParameters()))
        .booleanValue();
    } catch (Exception ex) {
      System.out.println("add WorkLogComment Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List getDDUserLogList(String userId, String beginDate, String endDate, String logType) {
    ParameterGenerator pg = new ParameterGenerator(4);
    List result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userId, String.class);
      pg.put(beginDate, String.class);
      pg.put(endDate, String.class);
      pg.put(logType, String.class);
      result = (List)ejbProxy.invoke("getDDUserLogList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDDUserLogList information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public List getDDUserLogListByUserName(String userName, String beginDate, String endDate, String logType) {
    ParameterGenerator pg = new ParameterGenerator(4);
    List result = null;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(userName, String.class);
      pg.put(beginDate, String.class);
      pg.put(endDate, String.class);
      pg.put(logType, String.class);
      result = (List)ejbProxy.invoke("getDDUserLogListByUserName", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getDDUserLogListByUserName information :" + 
          e.getMessage());
    } 
    return result;
  }
  
  public int addWorkTask(WorkProjectTaskPO workTask, String taskOrderId, String radiobutton) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(workTask, WorkProjectTaskPO.class);
      pg.put(taskOrderId, String.class);
      pg.put(radiobutton, String.class);
      addResult = ((Integer)ejbProxy.invoke("addWorkTask", 
          pg.getParameters()))
        .intValue();
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return addResult;
  }
  
  public List listWorkTask() {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      list = (List)ejbProxy.invoke("listWorkTask", (Object[][])null);
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return list;
  }
  
  public List selectProjectTaskById(String id) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      list = (List)ejbProxy.invoke(
          "selectProjectTaskById", pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } finally {}
    return list;
  }
  
  public int updateProjectTask(WorkProjectTaskPO workTask, String taskOrderId, String radiobutton) {
    int addResult = 2;
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(workTask, WorkProjectTaskPO.class);
      pg.put(taskOrderId, String.class);
      pg.put(radiobutton, String.class);
      addResult = ((Integer)ejbProxy.invoke("updateProjectTask", 
          pg.getParameters()))
        .intValue();
    } catch (Exception e) {
      logger.info(e);
    } 
    return addResult;
  }
  
  public List selectTaskByProjectId(String project_id) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(project_id, String.class);
      list = (List)ejbProxy.invoke("selectTaskByProjectId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return list;
  }
  
  public List selectProjectById(Long project_id) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(project_id, Long.class);
      list = (List)ejbProxy.invoke("selectProjectById", 
          pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return list;
  }
  
  public boolean deleteProjectWorkTask(WorkProjectTaskPO workProjectTaskPO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", 
          WorkLogEJBHome.class);
      pg.put(workProjectTaskPO, WorkProjectTaskPO.class);
      ejbProxy.invoke("deleteProjectWorkTask", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {}
    return result;
  }
  
  public List selectFatherTask(String fatherId) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(fatherId, String.class);
      list = (List)ejbProxy.invoke("selectFatherTask", 
          pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return list;
  }
  
  public List selectFatherTask(String fatherId, String taskCode) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(fatherId, String.class);
      pg.put(taskCode, String.class);
      list = (List)ejbProxy.invoke("selectFatherTask", 
          pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return list;
  }
  
  public List selectTaskByName(String name) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(name, String.class);
      list = (List)ejbProxy.invoke("selectTaskByName", 
          pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return list;
  }
  
  public List selectProjectTaskByCode(String id) {
    List list = new ArrayList();
    try {
      EJBProxy ejbProxy = new EJBProxy("WorkLogEJB", 
          "WorkLogEJBLocal", WorkLogEJBHome.class);
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(id, String.class);
      list = (List)ejbProxy.invoke("selectProjectTaskByCode", 
          pg.getParameters());
    } catch (Exception e) {
      logger.info(e);
    } 
    return list;
  }
  
  public List searchWorkLogForPortal(String workwhere) throws HibernateException {
    List list = null;
    WorkLogEJBBean workLogEJBBean = new WorkLogEJBBean();
    list = workLogEJBBean.searchWorkLogForPortal(workwhere);
    return list;
  }
  
  public String getByIds(Long id) throws HibernateException {
    String result = "N";
    WorkLogEJBBean workLogEJBBean = new WorkLogEJBBean();
    result = workLogEJBBean.getByIds(id);
    return result;
  }
  
  public String getManHour(String sql) {
    WorkLogEJBBean workLogEJBBean = new WorkLogEJBBean();
    try {
      return workLogEJBBean.getManHour(sql);
    } catch (HibernateException e) {
      e.printStackTrace();
      return "0";
    } 
  }
  
  public static String compareDate(Date logDate, Date writeLogDate, boolean isJSOA) {
    long logTime = logDate.getTime();
    long writeTime = writeLogDate.getTime();
    long oneDay = 86400000L;
    String ans = "";
    if (writeTime - logTime < oneDay)
      ans = "按时"; 
    if (writeTime - logTime >= oneDay && writeTime - logTime < oneDay * 2L) {
      ans = "补交";
      if (isJSOA)
        ans = "扣款2元"; 
    } 
    if (writeTime - logTime >= oneDay * 2L && writeTime - logTime < oneDay * 3L) {
      ans = "3日内补交";
      if (isJSOA)
        ans = "扣款5元"; 
    } 
    if (writeTime - logTime >= oneDay * 3L) {
      ans = "<font color='red'>超期</font>";
      if (isJSOA)
        ans = "<font color='red'>扣款20元</font>"; 
    } 
    return ans;
  }
}
