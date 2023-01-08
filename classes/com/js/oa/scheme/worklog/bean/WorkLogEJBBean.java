package com.js.oa.scheme.worklog.bean;

import com.js.oa.scheme.worklog.po.ProjectStepPO;
import com.js.oa.scheme.worklog.po.WorkLogCommentPO;
import com.js.oa.scheme.worklog.po.WorkLogPO;
import com.js.oa.scheme.worklog.po.WorkProjectClassPO;
import com.js.oa.scheme.worklog.po.WorkProjectPO;
import com.js.oa.scheme.worklog.po.WorkProjectTaskPO;
import com.js.oa.scheme.worklog.vo.ProjectStepVO;
import com.js.oa.scheme.worklog.vo.SimpleInfomationVO;
import com.js.oa.scheme.worklog.vo.WorkLogDO;
import com.js.oa.scheme.worklog.vo.WorkLogVO;
import com.js.oa.scheme.worklog.vo.WorkProjectClassVO;
import com.js.oa.scheme.worklog.vo.WorkProjectVO;
import com.js.oa.scheme.workreport.po.WorkReportPO;
import com.js.system.service.messages.RemindUtil;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.ConversionString;
import com.js.util.util.TransformObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;
import org.apache.log4j.Logger;

public class WorkLogEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  Logger logger = (Logger)Logger.getInstance(WorkLogEJBBean.class.getName());
  
  private static String databaseType = SystemCommon.getDatabaseType();
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean addProjectStep(ProjectStepPO projectStepPO, Long classId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      WorkProjectClassPO workProjectClassPO = 
        (WorkProjectClassPO)this.session.load(WorkProjectClassPO.class, classId);
      projectStepPO.setWorkProjectClass(workProjectClassPO);
      this.session.save(projectStepPO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("add ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public ProjectStepVO selectSingleProjectStep(Long id) throws HibernateException {
    ProjectStepVO projectStepVO = null;
    try {
      begin();
      ProjectStepPO projectStepPO = null;
      projectStepPO = (ProjectStepPO)this.session.load(ProjectStepPO.class, 
          id);
      Hibernate.initialize(projectStepPO);
      Hibernate.initialize(projectStepPO.getWorkProjectClass());
      projectStepVO = new ProjectStepVO();
      projectStepVO.conversionPO(projectStepPO);
    } catch (HibernateException ex) {
      System.out.println("select ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return projectStepVO;
  }
  
  public List selectAllProjectStep(Long classId, Long domainId) throws HibernateException {
    List<ProjectStepVO> list = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery(
          "from com.js.oa.scheme.worklog.po.ProjectStepPO ps where ps.workProjectClass.classId = " + 
          classId + (
          (domainId != null) ? (" and ps.stepDomainId = " + domainId) : 
          ""));
      List list1 = query.list();
      Iterator<ProjectStepPO> iterator = list1.iterator();
      ProjectStepPO projectStepPO = null;
      while (iterator != null && iterator.hasNext()) {
        projectStepPO = iterator.next();
        Hibernate.initialize(projectStepPO);
        Hibernate.initialize(projectStepPO.getWorkProjectClass());
        ProjectStepVO projectStepVO = new ProjectStepVO();
        projectStepVO.conversionPO(projectStepPO);
        list.add(projectStepVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select consistant type ProjectStep Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public boolean deleteProjectStep(Long id) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ProjectStepPO projectStepPO = (ProjectStepPO)this.session.load(
          ProjectStepPO.class, id);
      this.session.delete(projectStepPO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteBatchProjectStep(String ids) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ids = ids.substring(0, ids.length() - 1);
      this.session.delete(
          "from com.js.oa.scheme.worklog.po.ProjectStepPO ps where ps.stepId in (" + 
          ids + ")");
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteAllProjectStep(Long classId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      this.session.delete(
          "from com.js.oa.scheme.worklog.po.ProjectStepPO ps where ps.workProjectClass.classId = " + 
          classId);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean modifyProjectStep(ProjectStepVO projectStepVO) throws HibernateException {
    boolean result = false;
    try {
      begin();
      ProjectStepPO projectStepPO = (ProjectStepPO)this.session.load(
          ProjectStepPO.class, projectStepVO.getStepId());
      projectStepPO.setStepName(projectStepVO.getStepName());
      this.session.update(projectStepPO);
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("modify ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String projectStepNameIsExist(Long classId, String projectStepName, Long domainId) {
    String result = "no";
    try {
      begin();
      Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.ProjectStepPO projectStep join projectStep.workProjectClass workProjectClass where workProjectClass.classId =" + 
          
          classId + 
          " and projectStep.stepName = '" + 
          projectStepName + "'" + (
          (domainId != null) ? (
          " and projectStep.stepDomainId = " + 
          domainId) : ""));
      if (query.list().size() > 0)
        result = "yes"; 
    } catch (Exception ex) {
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("can not close session Exception: ", ex1);
      } finally {
        this.session = null;
        this.transaction = null;
      } 
    } 
    return result;
  }
  
  public Boolean addWorkLog(WorkLogVO workLogVO) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      saveWorkLog(workLogVO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add worklog Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void saveWorkLog(WorkLogVO workLogVO) throws HibernateException {
    WorkLogPO workLogPO = (WorkLogPO)TransformObject.getInstance()
      .transformObject(workLogVO, WorkLogPO.class);
    WorkProjectPO workProjectPO = (WorkProjectPO)this.session.load(
        WorkProjectPO.class, workLogVO.getProjectId());
    workLogPO.setWorkProject(workProjectPO);
    this.session.save(workLogPO);
  }
  
  public WorkLogVO selectSingleWorkLog(Long workLogId) throws HibernateException {
    WorkLogVO workLog = null;
    try {
      begin();
      WorkLogPO workLogPO = (WorkLogPO)this.session.load(WorkLogPO.class, 
          workLogId);
      Hibernate.initialize(workLogPO.getWorkProject());
      workLog = (WorkLogVO)TransformObject.getInstance().transformObject(
          workLogPO, WorkLogVO.class);
      workLog.setProjectId(workLogPO.getWorkProject().getProjectId());
      workLog.setProjectName(workLogPO.getWorkProject().getProjectName());
      Hibernate.initialize(workLogPO.getWorkProject().getWorkProjectClass());
      workLog.setClassId(workLogPO.getWorkProject().getWorkProjectClass()
          .getClassId());
    } catch (HibernateException ex) {
      System.out.println("select Single WorkLog Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return workLog;
  }
  
  public Boolean modifyWorkLog(WorkLogVO workLog) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] projectName = workLog.getProjectName().split(":");
      Long projectId = Long.valueOf(projectName[1]);
      workLog.setProjectId(projectId);
      WorkLogPO workLogPO = (WorkLogPO)this.session.load(WorkLogPO.class, 
          workLog.getLogId());
      workLogPO.setProjectStep(workLog.getProjectStep());
      workLogPO.setManHour(workLog.getManHour());
      WorkProjectPO workProject = (WorkProjectPO)this.session.load(
          WorkProjectPO.class, workLog.getProjectId());
      workLogPO.setWorkProject(workProject);
      workLogPO.setLogContent(workLog.getLogContent());
      workLogPO.setLogDate(workLog.getLogDate());
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("modify WorkLog Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean addWorkLog(String[] projectIds, String[] projectSteps, String[] manHours, String[] logContents, Long userId, Long userOrg, String logDate, String userName, Long domainId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      for (int i = 0; i < projectIds.length; i++) {
        WorkLogPO workLogPO = new WorkLogPO();
        workLogPO.setCreatedEmp(userId);
        workLogPO.setCreatedOrg(userOrg);
        workLogPO.setEmpName(userName);
        workLogPO.setLogContent(logContents[i]);
        workLogPO.setLogDate(new Date(logDate));
        workLogPO.setManHour(Float.valueOf(manHours[i]));
        workLogPO.setProjectStep(projectSteps[i]);
        workLogPO.setLogDomainId(domainId);
        String[] projectId = projectIds[i].split(":");
        WorkProjectPO workproject = (WorkProjectPO)this.session.load(
            WorkProjectPO.class, Long.valueOf(projectId[1]));
        workLogPO.setWorkProject(workproject);
        this.session.save(workLogPO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add Muti Work Log Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean addMultiWorkLog(List workLogVOs) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Iterator<WorkLogVO> iterator = workLogVOs.iterator();
      WorkLogVO workLogVO = null;
      while (iterator != null && iterator.hasNext()) {
        workLogVO = iterator.next();
        saveWorkLog(workLogVO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add Multi WorkLog Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteWorkLog(Long id) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      WorkLogPO workLogPO = (WorkLogPO)this.session.load(WorkLogPO.class, id);
      this.session.delete(workLogPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete WorkLog Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteBatchWorkLog(String ids) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.delete(
          "from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.logId in (" + 
          ids + ")");
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete WorkLog Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectProjectForWorkLog(Long userId, String orgIdString, Long domainId) throws HibernateException {
    List<WorkProjectVO> result = new ArrayList();
    try {
      begin();
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + 
          orgIdString + "' like concat('%$', org.orgId, '$%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$', JSDB.FN_INTTOSTR(org.orgId)),'$'), '" + 
          
          orgIdString + 
          "') >0 ";
      } else {
        tmpSql = "select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + 
          orgIdString + 
          "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$', JSDB.FN_INTTOSTR(org.orgId)),'$%')";
      } 
      Query orgQuery = this.session.createQuery(tmpSql);
      List<E> orgList = orgQuery.list();
      StringBuffer orgHql = new StringBuffer();
      String orgidtemp = "";
      for (int i = 0; i < orgList.size(); i++) {
        orgidtemp = orgList.get(i).toString();
        if (i + 1 < orgList.size()) {
          orgHql.append(" workProject.projectOrgRange like '%*" + 
              orgidtemp + "*%' or");
        } else if (i + 1 == orgList.size()) {
          orgHql.append(" workProject.projectOrgRange like '%*" + 
              orgidtemp + "*%'");
        } 
      } 
      orgidtemp = orgHql.toString();
      if (!orgidtemp.equals("")) {
        Query query = this.session.createQuery(
            "from com.js.oa.scheme.worklog.po.WorkProjectPO workProject where (" + 
            orgidtemp + 
            " or workProject.projectUserRange like '%$" + userId + 
            "$%') and (workProject.projectStatus <> 0)");
        List queryList = query.list();
        Iterator<WorkProjectPO> iterator = queryList.iterator();
        WorkProjectPO workProjectPO = null;
        WorkProjectVO workProjectVO = null;
        while (iterator != null && iterator.hasNext()) {
          workProjectPO = iterator.next();
          Hibernate.initialize(workProjectPO.getWorkProjectClass());
          workProjectVO = (WorkProjectVO)TransformObject.getInstance()
            .transformObject(workProjectPO, 
              WorkProjectVO.class);
          workProjectVO.setClassId(workProjectPO.getWorkProjectClass()
              .getClassId());
          result.add(workProjectVO);
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("select WorkProject For WorkLog Exception: " + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectProjectStepForWorkLog(Long classId, Long domainId) throws HibernateException {
    List<ProjectStepVO> result = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery(
          "from com.js.oa.scheme.worklog.po.ProjectStepPO projectStep where projectStep.workProjectClass.classId = " + 
          classId + (
          (domainId != null) ? (
          " and projectStep.stepDomainId = " + domainId) : ""));
      List queryList = query.list();
      Iterator<ProjectStepPO> iterator = queryList.iterator();
      ProjectStepPO projectStepPO = null;
      ProjectStepVO projectStepVO = null;
      while (iterator != null && iterator.hasNext()) {
        projectStepPO = iterator.next();
        projectStepVO = (ProjectStepVO)TransformObject.getInstance()
          .transformObject(projectStepPO, ProjectStepVO.class);
        result.add(projectStepVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select ProjectStep For WorkLog Exception: " + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectSelfWorkLog(Long userId, Long domainId) throws HibernateException {
    List<WorkLogVO> result = new ArrayList();
    try {
      begin();
      Query query = this.session.createQuery("select workLog,workProject.projectName from com.js.oa.scheme.worklog.po.WorkLogPO workLog join workLog.workProject workProject where workLog.createdEmp = " + 
          
          userId + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + 
          domainId) : "") + 
          " order by workLog.logDate desc");
      List<Object[]> queryList = query.list();
      for (int i = 0; i < queryList.size(); i++) {
        Object[] o = queryList.get(i);
        WorkLogPO workLogPO = null;
        WorkLogVO workLogVO = null;
        for (int j = 0; j < o.length; j++) {
          if (j == 0) {
            workLogPO = (WorkLogPO)o[j];
            workLogVO = (WorkLogVO)TransformObject.getInstance()
              .transformObject(workLogPO, WorkLogVO.class);
          } else if (j == 1) {
            workLogVO.setProjectName(o[j].toString());
            workLogVO.setSee(Boolean.TRUE);
            workLogVO.setMaintenance(Boolean.TRUE);
            Date now = new Date();
            if ((now.getTime() - workLogVO.getLogDate().getTime()) / 
              86400000L > 3L)
              workLogVO.setMaintenance(Boolean.FALSE); 
            result.add(workLogVO);
          } 
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println(ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List countProjectSimpleInformation(Long projectId, String stepName, String createdIds) throws HibernateException {
    List<SimpleInfomationVO> result = new ArrayList();
    try {
      begin();
      String hqlStr = 
        "select distinct workLog.projectStep from com.js.oa.scheme.workLog.po.WorkLogPO workLog ";
      String hqlConditions = makeCountInformationCondition(projectId, 
          stepName, createdIds);
      if (!hqlConditions.equals(""))
        hqlStr = String.valueOf(hqlStr) + " where " + hqlConditions; 
      hqlStr = String.valueOf(hqlStr) + 
        " order by workLog.projectStep desc";
      Query query = this.session.createQuery(hqlStr);
      List<Object[]> list = query.list();
      SimpleInfomationVO simpleInfomationVO = null;
      for (int i = 0; i < list.size(); i++) {
        Object[] o = list.get(i);
        simpleInfomationVO = new SimpleInfomationVO();
        simpleInfomationVO.setProjectStep(o[0].toString());
        hqlStr = "select count(workLog.manHour) from com.js.oa.scheme.workLog.po.WorkLogPO workLog  where " + 
          hqlConditions + 
          "and workLog.projectName = " + 
          simpleInfomationVO.getProjectStep();
        Query query1 = this.session.createQuery(hqlStr);
        List<Object[]> list1 = query1.list();
        Object[] manHourO = list1.get(0);
        simpleInfomationVO.setCountTime(Long.valueOf(manHourO[0]
              .toString()));
        result.add(simpleInfomationVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("count simple Information exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List countProjectDetailInformation(String projectIds, String stepName, String createdIds, Long userId, Long orgId, Long domainId) throws HibernateException {
    List<WorkLogVO> result = new ArrayList();
    try {
      begin();
      String hqlStr = 
        "from com.js.oa.scheme.worklog.po.WorkLogPO workLog ";
      String[] projectIdArray = projectIds.split(":");
      Long projectId = Long.valueOf(projectIdArray[1]);
      if ("0".equals(projectIdArray[1]))
        projectId = null; 
      String hqlConditions = makeCountInformationCondition1(projectId, 
          stepName, createdIds, userId, orgId);
      if (!hqlConditions.equals(""))
        hqlStr = String.valueOf(hqlStr) + " where " + hqlConditions + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + domainId) : ""); 
      hqlStr = String.valueOf(hqlStr) + 
        " order by workLog.projectStep,workLog.logDate desc";
      Query query = this.session.createQuery(hqlStr);
      List list = query.list();
      Iterator<WorkLogPO> iterator = list.iterator();
      WorkLogPO workLogPO = new WorkLogPO();
      WorkLogVO workLogVO = new WorkLogVO();
      Float projectStepTotalTime = new Float(0.0F);
      while (iterator != null && iterator.hasNext()) {
        workLogPO = iterator.next();
        if (workLogVO != null && 
          !((workLogPO.getProjectStep() == null) ? "" : workLogPO.getProjectStep()).equals(workLogVO.getProjectStep())) {
          projectStepTotalTime = getCountProjectStepTotalTime(
              projectIds, workLogPO.getProjectStep(), createdIds, 
              userId, orgId, domainId);
        } else if (workLogVO == null) {
          projectStepTotalTime = getCountProjectStepTotalTime(
              projectIds, workLogPO.getProjectStep(), createdIds, 
              userId, orgId, domainId);
        } 
        Hibernate.initialize(workLogPO.getWorkProject());
        workLogVO = (WorkLogVO)TransformObject.getInstance()
          .transformObject(workLogPO, WorkLogVO.class);
        workLogVO.setProjectStepTotalTime(projectStepTotalTime);
        if (workLogPO.getWorkProject() != null)
          workLogVO.setProjectName(workLogPO.getWorkProject()
              .getProjectName()); 
        result.add(workLogVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("count project information Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Float getCountDetailTotalTime(String projectIds, String stepName, String createdIds, Long userId, Long orgId, Long domainId) throws HibernateException {
    Float totalTime = new Float(0.0F);
    try {
      begin();
      Long projectId = Long.valueOf(projectIds.split(":")[1]);
      String hqlStr = projectDetailHqlGen(projectId, stepName, createdIds, 
          userId, orgId, domainId);
      hqlStr = hqlStr.substring(0, hqlStr.indexOf("order by"));
      Query query = this.session.createQuery(hqlStr);
      List list = query.list();
      if (!list.isEmpty()) {
        Object o = list.get(0);
        if (o != null)
          totalTime = Float.valueOf(o.toString()); 
      } 
    } catch (HibernateException ex) {
      System.out.println("get CountDetail Total Time Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return totalTime;
  }
  
  private Float getCountProjectStepTotalTime(String projectIds, String stepName, String createdIds, Long userId, Long orgId, Long domainId) throws HibernateException, NumberFormatException {
    Float totalTime = new Float(0.0F);
    Long projectId = Long.valueOf(projectIds.split(":")[1]);
    String hqlStr = projectDetailHqlGen(projectId, stepName, createdIds, 
        userId, orgId, domainId);
    hqlStr = hqlStr.substring(0, hqlStr.indexOf("order by"));
    Query query = this.session.createQuery(hqlStr);
    List list = query.list();
    Object o = list.get(0);
    totalTime = Float.valueOf(o.toString());
    return totalTime;
  }
  
  public String getProjectDetailMaxTime(String projectIds, String stepName, String createdId, Long userId, Long orgId, Long domainId) throws HibernateException {
    return getProjectDetailTime(projectIds, stepName, createdId, userId, 
        orgId, 1, domainId);
  }
  
  public String getProjectDetailMinTime(String projectIds, String stepName, String createdId, Long userId, Long orgId, Long domainId) throws HibernateException {
    return getProjectDetailTime(projectIds, stepName, createdId, userId, 
        orgId, 0, domainId);
  }
  
  private String getProjectDetailTime(String projectIds, String stepName, String createdIds, Long userId, Long orgId, int flag, Long domainId) throws HibernateException {
    String result = null;
    try {
      String hqlStr;
      begin();
      if (flag == 1) {
        hqlStr = 
          "select Max(workLog.logDate) from com.js.oa.scheme.worklog.po.WorkLogPO workLog ";
      } else {
        hqlStr = 
          "select Min(workLog.logDate) from com.js.oa.scheme.worklog.po.WorkLogPO workLog ";
      } 
      Long projectId = Long.valueOf(projectIds.split(":")[1]);
      if ("0".equals(projectIds.split(":")[1]))
        projectId = null; 
      String hqlConditions = makeCountInformationCondition1(projectId, 
          stepName, createdIds, userId, orgId);
      if (!hqlConditions.equals(""))
        hqlStr = String.valueOf(hqlStr) + " where " + hqlConditions + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + domainId) : ""); 
      Query query = this.session.createQuery(hqlStr);
      List list = query.list();
      Object o = list.get(0);
      if (o != null) {
        String time = o.toString();
        result = time.substring(0, 10);
      } 
    } catch (HibernateException ex) {
      System.out.print("get Project Detail Count Information Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Date StringToDateYYYYMMDDHHmmss(String s) throws ParseException {
    if (s != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", 
          Locale.CHINESE);
      Date date = formatter.parse(s);
      return date;
    } 
    return null;
  }
  
  private Date StringToDateYYYYMMDD(String s) throws ParseException {
    if (s != null) {
      DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", 
          Locale.CHINESE);
      Date date = formatter.parse(s);
      return date;
    } 
    return null;
  }
  
  private String projectDetailHqlGen(Long projectId, String stepName, String createdIds, Long userId, Long orgId, Long domainId) throws HibernateException {
    String hqlStr = 
      "select sum(workLog.manHour) from com.js.oa.scheme.worklog.po.WorkLogPO workLog ";
    String hqlConditions = makeCountInformationCondition1(projectId, 
        stepName, createdIds, userId, orgId);
    if (!hqlConditions.equals(""))
      hqlStr = String.valueOf(hqlStr) + " where " + hqlConditions + (
        (domainId != null) ? (
        " and workLog.logDomainId = " + domainId) : ""); 
    hqlStr = String.valueOf(hqlStr) + 
      " order by workLog.logDate,workLog.projectStep desc";
    return hqlStr;
  }
  
  private String makeCountInformationCondition(Long projectId, String stepName, String createdIds) throws HibernateException {
    String _ret = "";
    if (projectId != null && !projectId.equals(""))
      _ret = String.valueOf(_ret) + " (workLog.workProject.projectId = " + projectId + 
        ")"; 
    if (stepName != null && !stepName.equals(""))
      _ret = String.valueOf(_ret) + " and (workLog.projectStep = '" + stepName + "')"; 
    if (createdIds != null && !createdIds.equals("")) {
      String userIdString = (new ConversionString(createdIds))
        .getUserIdString();
      _ret = String.valueOf(_ret) + " and (workLog.createdEmp in (" + userIdString + "))";
    } 
    return _ret;
  }
  
  private String makeCountInformationCondition1(Long projectId, String stepName, String createdIds, Long userId, Long orgId) throws HibernateException {
    String _ret = "";
    if (projectId != null && !"0".equals(String.valueOf(projectId))) {
      _ret = String.valueOf(_ret) + " (workLog.workProject.projectId = " + projectId + 
        ")";
    } else {
      _ret = String.valueOf(_ret) + " (1=1)";
    } 
    if (stepName != null && !stepName.equals(""))
      _ret = String.valueOf(_ret) + " and (workLog.projectStep = '" + stepName + "')"; 
    if (createdIds != null && !createdIds.equals("")) {
      String[] userNameString = createdIds.split(",");
      String hql = "";
      for (int i = 0; i < userNameString.length; i++) {
        if (i + 1 < userNameString.length) {
          hql = String.valueOf(hql) + " (workLog.empName like '%" + userNameString[i] + 
            "%') and ";
        } else {
          hql = String.valueOf(hql) + " (workLog.empName like '%" + userNameString[i] + 
            "%')";
        } 
      } 
      if (!hql.equals(""))
        _ret = String.valueOf(_ret) + " and " + hql; 
    } 
    Query query = this.session.createQuery("select rightscope.rightScopeType,rightscope.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO rightscope where rightscope.employee.empId = " + 
        
        userId + " and rightscope.right.rightName = '统计' and rightscope.right.rightType = '工作日志-项目统计'");
    List<Object[]> list = query.list();
    String qxtj = "";
    if (list.size() > 0) {
      Query orgQuery = null;
      List<E> orgList;
      int i;
      String rightScopeScope, orgIdArr[];
      StringBuffer orgIdSelf;
      int j;
      Object[] rightObject = list.get(0);
      Integer rightScopeType = Integer.valueOf(rightObject[0]
          .toString());
      String orgIds = "";
      switch (rightScopeType.intValue()) {
        case 0:
          qxtj = "";
          break;
        case 1:
          qxtj = String.valueOf(qxtj) + " (workLog.createdEmp = " + userId + ")";
          break;
        case 3:
          qxtj = String.valueOf(qxtj) + " (workLog.createdOrg = " + orgId + ")";
          break;
        case 2:
          orgQuery = this.session.createQuery("select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
              
              orgId + "$%'");
          orgList = orgQuery.list();
          orgIds = "";
          for (i = 0; i < orgList.size(); i++)
            orgIds = String.valueOf(orgIds) + orgList.get(i).toString() + ","; 
          if (!orgIds.equals("")) {
            orgIds = orgIds.substring(0, orgIds.length() - 1);
            qxtj = String.valueOf(qxtj) + "(workLog.createdOrg in (" + orgIds + "))";
          } 
          break;
        case 4:
          rightScopeScope = rightObject[1].toString();
          orgIds = (new ConversionString(rightScopeScope))
            .getOrgIdString();
          orgIdArr = orgIds.split(",");
          orgIdSelf = new StringBuffer();
          for (j = 0; j < orgIdArr.length; j++) {
            Query org = this.session.createQuery("select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                
                orgIdArr[j] + 
                "$%'");
            List<E> orgl = org.list();
            orgIds = "";
            for (int k = 0; k < orgl.size(); k++)
              orgIds = String.valueOf(orgIds) + orgl.get(k).toString() + ","; 
            if (!orgIds.equals(""))
              if (j + 1 < orgIdArr.length) {
                orgIdSelf.append(orgIds);
              } else {
                orgIds = orgIds.substring(0, 
                    orgIds.length() - 1);
                orgIdSelf.append(orgIds);
              }  
          } 
          qxtj = String.valueOf(qxtj) + "(workLog.createdOrg in (" + orgIdSelf.toString() + 
            "))";
          break;
      } 
    } 
    if (!qxtj.equals(""))
      _ret = String.valueOf(_ret) + " and " + qxtj; 
    return _ret;
  }
  
  public List selectWorkLogByCondition(String searchederIds, String startTime, String endTime) throws HibernateException {
    List<WorkLogVO> result = new ArrayList();
    try {
      begin();
      String hql = "select workLog,workproject from com.js.oa.scheme.worklog.po.WorkLogPO workLog join worklog.projectId workproject";
      String sqlConditions = makeCondition(searchederIds, startTime, 
          endTime);
      if (sqlConditions != null && sqlConditions.equals(""))
        hql = String.valueOf(hql) + " where "; 
      hql = String.valueOf(hql) + 
        " order by workLog.logDate,workproject.projectName desc";
      Query query = this.session.createQuery(hql);
      List<Object[]> list = query.list();
      WorkLogPO workLogPO = null;
      WorkLogVO workLogVO = null;
      for (int i = 0; i < list.size(); i++) {
        Object[] o = list.get(i);
        for (int j = 0; j < o.length; j++) {
          if (j == 0) {
            workLogPO = (WorkLogPO)o[j];
            workLogVO = (WorkLogVO)TransformObject.getInstance()
              .transformObject(workLogPO, WorkLogVO.class);
          } else if (j == 1) {
            workLogVO.setProjectName(o[j].toString());
            result.add(workLogVO);
          } 
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("select WorkLog Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private String makeCondition(String searchederIds, String startTime, String endTime) throws HibernateException {
    ConversionString cs = new ConversionString(searchederIds);
    String userIdString = cs.getUserIdString();
    String orgIdString = cs.getOrgIdString();
    String groupIdString = cs.getGroupIdString();
    String hqlConditions = "";
    hqlConditions = makeUserIdStringCondition(hqlConditions, userIdString, 
        groupIdString);
    if (!hqlConditions.equals("")) {
      if (!orgIdString.equals(""))
        hqlConditions = " (workLog.createdOrg in (" + orgIdString + 
          ") or (" + hqlConditions + "))"; 
    } else if (!orgIdString.equals("")) {
      hqlConditions = " (workLog.createOrg in (" + orgIdString + 
        ")) ";
    } 
    if (!hqlConditions.equals("")) {
      if (startTime != null && !startTime.equals(""))
        if (databaseType.indexOf("mysql") >= 0) {
          hqlConditions = "(" + hqlConditions + 
            ") and (workLog.logDate >='" + 
            startTime + "')";
        } else {
          hqlConditions = "(" + hqlConditions + 
            ") and (workLog.logDate >=JSDB.FN_STRTODATE('" + 
            startTime + "','S'))";
        }  
    } else if (startTime != null && !startTime.equals("")) {
      if (databaseType.indexOf("mysql") >= 0) {
        hqlConditions = "(workLog.logDate >='" + startTime + "')";
      } else {
        hqlConditions = 
          "(workLog.logDate >=JSDB.FN_STRTODATE('" + 
          startTime + "','S'))";
      } 
    } 
    if (!hqlConditions.equals("")) {
      if (endTime != null && !endTime.equals(""))
        if (databaseType.indexOf("mysql") >= 0) {
          hqlConditions = "(" + hqlConditions + 
            ") and (workLog.logDate <='" + endTime + 
            "')";
        } else {
          hqlConditions = "(" + hqlConditions + 
            ") and (workLog.logDate <=JSDB.FN_STRTODATE('" + 
            endTime + 
            "','S'))";
        }  
    } else if (endTime != null && !endTime.equals("")) {
      if (databaseType.indexOf("mysql") >= 0) {
        hqlConditions = "(workLog.logDate >='" + endTime + "')";
      } else {
        hqlConditions = 
          "(workLog.logDate >=JSDB.FN_STRTODATE('" + 
          endTime + 
          "','S'))";
      } 
    } 
    return hqlConditions;
  }
  
  private Long workLogRight(Long userId) throws HibernateException {
    Long rightScopeId = null;
    Query query = this.session.createQuery(
        "select distinct rightscope.rightScopeId from com.js.system.vo.rolemanager.RightScopeVO rightscope join rightscope.right rights join rightscope.employee emp where (emp.empId =" + 
        
        userId + 
        ") and (rights.rightName ='查看') and (rights.rightType = '工作日志-日志查阅')");
    List list = query.list();
    if (list.size() > 0) {
      Object o = list.get(0);
      rightScopeId = Long.valueOf(o.toString());
    } 
    return rightScopeId;
  }
  
  private Long workProjectClassRight(Long userId) throws HibernateException {
    Long rightScopeId = null;
    Query query = this.session.createQuery(
        "select distinct rightscope.rightScopeId from com.js.system.vo.rolemanager.RightScopeVO rightscope join rightscope.right rights join rightscope.employee emp where (emp.empId =" + 
        
        userId + 
        ") and (rights.rightName ='维护') and (rights.rightType = '工作日志-项目分类')");
    List list = query.list();
    if (list.size() > 0) {
      Object o = list.get(0);
      rightScopeId = Long.valueOf(o.toString());
    } 
    return rightScopeId;
  }
  
  private Long workProjectRight(Long userId) throws HibernateException {
    Long rightScopeId = null;
    Query query = this.session.createQuery(
        "select distinct rightscope.rightScopeId from com.js.system.vo.rolemanager.RightScopeVO rightscope join rightscope.right rights join rightscope.employee emp where (emp.empId =" + 
        
        userId + 
        ") and (rights.rightName ='维护') and (rights.rightType = '工作日志-项目设置')");
    List list = query.list();
    if (list.size() > 0) {
      Object o = list.get(0);
      rightScopeId = Long.valueOf(o.toString());
    } 
    return rightScopeId;
  }
  
  public List searchWorkLog(Long userId, Long orgId, Long domainId) throws HibernateException {
    List<WorkLogVO> result = new ArrayList();
    try {
      begin();
      Long rightScopeId = workLogRight(userId);
      String hqlCondition = "";
      if (rightScopeId != null) {
        hqlCondition = makeSelfCondition(userId, orgId, rightScopeId);
        if (hqlCondition != null) {
          Query worklogQuery = this.session.createQuery("select workLog,workProject.projectName from com.js.oa.scheme.worklog.po.WorkLogPO workLog join workLog.workProject workProject " + (
              hqlCondition.equals("") ? " where " : (
              String.valueOf(hqlCondition) + " and ")) + (
              (domainId != null) ? (
              " workLog.logDomainId = " + domainId) : "") + 
              " order by workLog.logDate desc,workProject.projectName");
          List<Object[]> queryList = worklogQuery.list();
          for (int i = 0; i < queryList.size(); i++) {
            Object[] o = queryList.get(i);
            WorkLogPO workLogPO = null;
            WorkLogVO workLogVO = null;
            for (int j = 0; j < o.length; j++) {
              if (j == 0) {
                workLogPO = (WorkLogPO)o[j];
                Hibernate.initialize(workLogPO.getWorkProject());
                workLogVO = 
                  (WorkLogVO)TransformObject.getInstance()
                  .transformObject(workLogPO, 
                    WorkLogVO.class);
              } else if (j == 1) {
                workLogVO.setProjectId(workLogPO.getWorkProject()
                    .getProjectId());
                workLogVO.setProjectName(workLogPO
                    .getWorkProject().getProjectName());
                result.add(workLogVO);
              } 
            } 
          } 
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("select workLog Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private String makeConditionForWorkProjectClass(String hqlCondition, String userIdString, String groupIdString) throws NumberFormatException, HibernateException {
    if (!userIdString.equals("")) {
      if (!groupIdString.equals("")) {
        String[] groupIdArray = groupIdString.split(",");
        String groupUserIdString = "";
        for (int i = 0; i < groupIdArray.length; i++) {
          GroupVO groupVO = (GroupVO)this.session.load(
              GroupVO.class, 
              Long.valueOf(groupIdArray[i]));
          groupUserIdString = String.valueOf(groupUserIdString) + (
            
            new ConversionString(groupVO
              .getGroupUserString()))
            .getUserIdString() + ",";
        } 
        if (!groupUserIdString.equals("")) {
          hqlCondition = String.valueOf(hqlCondition) + 
            " workProjectClass.createdEmp in (" + 
            groupUserIdString + userIdString + ")";
        } else {
          hqlCondition = String.valueOf(hqlCondition) + 
            " workProjectClass.createdEmp in (" + 
            userIdString + ")";
        } 
      } else {
        hqlCondition = String.valueOf(hqlCondition) + 
          " workProjectClass.createdEmp in (" + 
          userIdString + ")";
      } 
    } else if (!groupIdString.equals("")) {
      String[] groupIdArray = groupIdString.split(",");
      String groupUserIdString = "";
      for (int i = 0; i < groupIdArray.length; i++) {
        GroupVO groupVO = (GroupVO)this.session.load(
            GroupVO.class, 
            Long.valueOf(groupIdArray[i]));
        groupUserIdString = String.valueOf(groupUserIdString) + (
          
          new ConversionString(groupVO
            .getGroupUserString()))
          .getUserIdString() + ",";
      } 
      if (!groupUserIdString.equals(""))
        hqlCondition = String.valueOf(hqlCondition) + 
          " workProjectClass.createdEmp in (" + 
          groupUserIdString + ")"; 
    } 
    return hqlCondition;
  }
  
  private String makeConditionForWorkProject(String hqlCondition, String userIdString, String groupIdString) throws NumberFormatException, HibernateException {
    if (!userIdString.equals("")) {
      if (!groupIdString.equals("")) {
        String[] groupIdArray = groupIdString.split(",");
        String groupUserIdString = "";
        for (int i = 0; i < groupIdArray.length; i++) {
          GroupVO groupVO = (GroupVO)this.session.load(
              GroupVO.class, 
              Long.valueOf(groupIdArray[i]));
          groupUserIdString = String.valueOf(groupUserIdString) + (
            
            new ConversionString(groupVO
              .getGroupUserString()))
            .getUserIdString() + ",";
        } 
        if (!groupUserIdString.equals("")) {
          hqlCondition = String.valueOf(hqlCondition) + 
            " workProject.createdEmp in (" + 
            groupUserIdString + userIdString + ")";
        } else {
          hqlCondition = String.valueOf(hqlCondition) + 
            " workProject.createdEmp in (" + 
            userIdString + ")";
        } 
      } else {
        hqlCondition = String.valueOf(hqlCondition) + " workProject.createdEmp in (" + 
          userIdString + ")";
      } 
    } else if (!groupIdString.equals("")) {
      String[] groupIdArray = groupIdString.split(",");
      String groupUserIdString = "";
      for (int i = 0; i < groupIdArray.length; i++) {
        GroupVO groupVO = (GroupVO)this.session.load(
            GroupVO.class, 
            Long.valueOf(groupIdArray[i]));
        groupUserIdString = String.valueOf(groupUserIdString) + (
          
          new ConversionString(groupVO
            .getGroupUserString()))
          .getUserIdString() + ",";
      } 
      if (!groupUserIdString.equals(""))
        hqlCondition = String.valueOf(hqlCondition) + 
          " workProject.createdEmp in (" + 
          groupUserIdString + ")"; 
    } 
    return hqlCondition;
  }
  
  private String makeUserIdStringCondition(String hqlCondition, String userIdString, String groupIdString) throws NumberFormatException, HibernateException {
    if (!userIdString.equals("")) {
      if (!groupIdString.equals("")) {
        String[] groupIdArray = groupIdString.split(",");
        String groupUserIdString = "";
        for (int i = 0; i < groupIdArray.length; i++) {
          GroupVO groupVO = (GroupVO)this.session.load(
              GroupVO.class, 
              Long.valueOf(groupIdArray[i]));
          groupUserIdString = String.valueOf(groupUserIdString) + (
            
            new ConversionString(groupVO
              .getGroupUserString()))
            .getUserIdString() + ",";
        } 
        if (!groupUserIdString.equals("")) {
          hqlCondition = String.valueOf(hqlCondition) + " workLog.createdEmp in (" + 
            groupUserIdString + userIdString + ")";
        } else {
          hqlCondition = String.valueOf(hqlCondition) + " workLog.createdEmp in (" + 
            userIdString + ")";
        } 
      } else {
        hqlCondition = String.valueOf(hqlCondition) + " workLog.createdEmp in (" + 
          userIdString + ")";
      } 
    } else if (!groupIdString.equals("")) {
      String[] groupIdArray = groupIdString.split(",");
      String groupUserIdString = "";
      for (int i = 0; i < groupIdArray.length; i++) {
        GroupVO groupVO = (GroupVO)this.session.load(
            GroupVO.class, 
            Long.valueOf(groupIdArray[i]));
        groupUserIdString = String.valueOf(groupUserIdString) + (
          
          new ConversionString(groupVO
            .getGroupUserString()))
          .getUserIdString() + ",";
      } 
      if (!groupUserIdString.equals(""))
        hqlCondition = String.valueOf(hqlCondition) + " workLog.createdEmp in (" + 
          groupUserIdString + ")"; 
    } 
    return hqlCondition;
  }
  
  private String makeWorkProjectClassCondition(Long userId, Long orgId, Long rightScopeId) throws NumberFormatException, HibernateException {
    Query q = this.session.createQuery("select distinct rightscope.rightScopeType,rightscope.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO rightscope where rightscope.rightScopeId = " + 
        
        rightScopeId);
    Object[] o = q.list().get(0);
    short rightScopeType = Short.parseShort(o[0].toString());
    String hqlCondition = "";
    if (rightScopeType == 0) {
      hqlCondition = "";
    } else if (rightScopeType == 1) {
      hqlCondition = "where (workProjectClass.createdEmp =" + userId + 
        ")";
    } else if (rightScopeType == 3) {
      hqlCondition = "where (workProjectClass.createdOrg =" + orgId + 
        ")";
    } else if (rightScopeType == 2) {
      Query query = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
          orgId + "$%'");
      List list = query.list();
      Iterator<Long> iterator = list.iterator();
      String orgIds = "";
      Long orgIditem = null;
      while (iterator != null && iterator.hasNext()) {
        orgIditem = iterator.next();
        orgIds = String.valueOf(orgIds) + orgIditem + ",";
      } 
      if (!orgIds.equals("")) {
        orgIds = orgIds.substring(0, orgIds.length() - 1);
        hqlCondition = "where (workProjectClass.createdOrg in (" + 
          orgIds + ")" + 
          ")";
      } 
    } else if (rightScopeType == 4) {
      String rightscopescope = o[1].toString();
      ConversionString cs = new ConversionString(rightscopescope);
      String userIdString = cs.getUserIdString();
      String orgIdString = cs.getOrgIdString();
      String groupIdString = cs.getGroupIdString();
      hqlCondition = makeConditionForWorkProjectClass(hqlCondition, 
          userIdString, groupIdString);
      if (!orgIdString.equals("")) {
        String[] orgIdArray = orgIdString.split(",");
        StringBuffer orgBuffer = new StringBuffer();
        for (int i = 0; i < orgIdArray.length; i++) {
          Query query = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
              orgIdArray[i] + "$%'");
          List list = query.list();
          Iterator<Long> iterator = list.iterator();
          StringBuffer orgIds = new StringBuffer();
          Long orgIditem = null;
          while (iterator != null && iterator.hasNext()) {
            orgIditem = iterator.next();
            orgIds.append(orgIditem);
            orgIds.append(",");
          } 
          orgBuffer.append(orgIds);
        } 
        orgIdString = orgBuffer.toString();
        if (orgIdString != null && !orgIdString.equals("")) {
          orgIdString = orgIdString.substring(0, 
              orgIdString.length() - 1);
        } else if (orgIdString == null) {
          orgIdString = "";
        } 
        if (!hqlCondition.equals("")) {
          if (!orgIdString.equals("")) {
            hqlCondition = "where (" + hqlCondition + 
              " or workProjectClass.createdOrg in (" + 
              orgIdString + ")" + 
              ")";
          } else {
            hqlCondition = "where" + hqlCondition;
          } 
        } else if (!orgIdString.equals("")) {
          hqlCondition = 
            "where (workProjectClass.createdOrg in (" + 
            orgIdString + "))";
        } else {
          hqlCondition = "";
        } 
      } else if (!hqlCondition.equals("")) {
        hqlCondition = "where (" + hqlCondition + ")";
      } 
    } 
    return hqlCondition;
  }
  
  private String makeWorkProjectCondition(Long userId, Long orgId, Long rightScopeId) throws NumberFormatException, HibernateException {
    Query q = this.session.createQuery("select distinct rightscope.rightScopeType,rightscope.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO rightscope where rightscope.rightScopeId = " + 
        
        rightScopeId);
    Object[] o = q.list().get(0);
    short rightScopeType = Short.parseShort(o[0].toString());
    String hqlCondition = "";
    if (rightScopeType == 0) {
      hqlCondition = "";
    } else if (rightScopeType == 1) {
      hqlCondition = "where (workProject.createdEmp =" + userId + ")";
    } else if (rightScopeType == 3) {
      hqlCondition = "where (workProject.createdOrg =" + orgId + ")";
    } else if (rightScopeType == 2) {
      Query query = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
          orgId + "$%'");
      List list = query.list();
      Iterator<Long> iterator = list.iterator();
      String orgIds = "";
      Long orgIditem = null;
      while (iterator != null && iterator.hasNext()) {
        orgIditem = iterator.next();
        orgIds = String.valueOf(orgIds) + orgIditem + ",";
      } 
      if (!orgIds.equals("")) {
        orgIds = orgIds.substring(0, orgIds.length() - 1);
        hqlCondition = "where (workProject.createdOrg in (" + 
          orgIds + "))";
      } 
    } else if (rightScopeType == 4) {
      String rightscopescope = o[1].toString();
      ConversionString cs = new ConversionString(rightscopescope);
      String userIdString = cs.getUserIdString();
      String orgIdString = cs.getOrgIdString();
      String groupIdString = cs.getGroupIdString();
      hqlCondition = makeConditionForWorkProject(hqlCondition, 
          userIdString, groupIdString);
      if (!orgIdString.equals("")) {
        String[] orgIdArray = orgIdString.split(",");
        StringBuffer orgBuffer = new StringBuffer();
        for (int i = 0; i < orgIdArray.length; i++) {
          Query query = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
              orgIdArray[i] + "$%'");
          List list = query.list();
          Iterator<Long> iterator = list.iterator();
          StringBuffer orgIds = new StringBuffer();
          Long orgIditem = null;
          while (iterator != null && iterator.hasNext()) {
            orgIditem = iterator.next();
            orgIds.append(orgIditem);
            orgIds.append(",");
          } 
          orgBuffer.append(orgIds);
        } 
        orgIdString = orgBuffer.toString();
        if (orgIdString != null && !orgIdString.equals("")) {
          orgIdString = orgIdString.substring(0, 
              orgIdString.length() - 1);
        } else if (orgIdString == null) {
          orgIdString = "";
        } 
        if (!hqlCondition.equals("")) {
          if (!orgIdString.equals("")) {
            hqlCondition = "where (" + hqlCondition + 
              " or workProject.createdOrg in (" + 
              orgIdString + "))";
          } else {
            hqlCondition = "where" + hqlCondition;
          } 
        } else if (!orgIdString.equals("")) {
          hqlCondition = "where (workProject.createdOrg in (" + 
            orgIdString + "))";
        } else {
          hqlCondition = "";
        } 
      } else if (!hqlCondition.equals("")) {
        hqlCondition = "where (" + hqlCondition + ")";
      } 
    } 
    return hqlCondition;
  }
  
  private String makeSelfCondition(Long userId, Long orgId, Long rightScopeId) throws NumberFormatException, HibernateException {
    Query q = this.session.createQuery("select distinct rightscope.rightScopeType,rightscope.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO rightscope where rightscope.rightScopeId = " + 
        
        rightScopeId);
    Object[] o = q.list().get(0);
    short rightScopeType = Short.parseShort(o[0].toString());
    String hqlCondition = "";
    if (rightScopeType == 0) {
      hqlCondition = "";
    } else if (rightScopeType == 1) {
      hqlCondition = "where (workLog.createdEmp =" + userId + ")";
    } else if (rightScopeType == 3) {
      hqlCondition = "where (workLog.createdOrg =" + orgId + ")";
    } else if (rightScopeType == 2) {
      Query query = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
          orgId + "$%'");
      List list = query.list();
      Iterator<Long> iterator = list.iterator();
      String orgIds = "";
      Long orgIditem = null;
      while (iterator != null && iterator.hasNext()) {
        orgIditem = iterator.next();
        orgIds = String.valueOf(orgIds) + orgIditem + ",";
      } 
      if (!orgIds.equals("")) {
        orgIds = orgIds.substring(0, orgIds.length() - 1);
        hqlCondition = "where (workLog.createdOrg in (" + 
          orgIds + "))";
      } 
    } else if (rightScopeType == 4) {
      String rightscopescope = o[1].toString();
      ConversionString cs = new ConversionString(rightscopescope);
      String userIdString = cs.getUserIdString();
      String orgIdString = cs.getOrgIdString();
      String groupIdString = cs.getGroupIdString();
      hqlCondition = makeUserIdStringCondition(hqlCondition, 
          userIdString, groupIdString);
      if (!orgIdString.equals("")) {
        String[] orgIdArray = orgIdString.split(",");
        StringBuffer orgBuffer = new StringBuffer();
        for (int i = 0; i < orgIdArray.length; i++) {
          Query query = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
              orgIdArray[i] + "$%'");
          List list = query.list();
          Iterator<Long> iterator = list.iterator();
          StringBuffer orgIds = new StringBuffer();
          Long orgIditem = null;
          while (iterator != null && iterator.hasNext()) {
            orgIditem = iterator.next();
            orgIds.append(orgIditem);
            orgIds.append(",");
          } 
          orgBuffer.append(orgIds);
        } 
        orgIdString = orgBuffer.toString();
        if (orgIdString != null && !orgIdString.equals("")) {
          orgIdString = orgIdString.substring(0, 
              orgIdString.length() - 1);
        } else if (orgIdString == null) {
          orgIdString = "";
        } 
        if (!hqlCondition.equals("")) {
          if (!orgIdString.equals("")) {
            hqlCondition = "where (" + hqlCondition + 
              " or workLog.createdOrg in (" + 
              orgIdString + "))";
          } else {
            hqlCondition = "where" + hqlCondition;
          } 
        } else if (!orgIdString.equals("")) {
          hqlCondition = "where (workLog.createdOrg in (" + 
            orgIdString + "))";
        } else {
          hqlCondition = "";
        } 
      } else if (!hqlCondition.equals("")) {
        hqlCondition = "where (" + hqlCondition + ")";
      } 
    } 
    return hqlCondition;
  }
  
  private String makeHQLCondition(Long userId, Long orgId, String searchederIds, String startTime, String endTime) throws HibernateException {
    Long rightScopeId = workLogRight(userId);
    String hqlCondition = null;
    if (rightScopeId != null) {
      hqlCondition = "";
      hqlCondition = makeSelfCondition(userId, orgId, rightScopeId);
      String condition = makeCondition(searchederIds, startTime, endTime);
      if (!hqlCondition.equals("")) {
        if (!condition.equals(""))
          hqlCondition = String.valueOf(hqlCondition) + " and (" + condition + ")"; 
      } else if (!condition.equals("")) {
        hqlCondition = " where (" + condition + ")";
      } 
    } 
    return hqlCondition;
  }
  
  public List searchWorkLog(Long userId, Long orgId, String searchederIds, String startTime, String endTime, Long domainId) throws HibernateException {
    List<WorkLogVO> result = new ArrayList();
    try {
      begin();
      String hqlCondition = makeHQLCondition(userId, orgId, searchederIds, 
          startTime, endTime);
      if (hqlCondition != null) {
        Query worklogQuery = this.session.createQuery("select workLog,workProject.projectName from com.js.oa.scheme.worklog.po.WorkLogPO workLog join workLog.workProject workProject " + (
            hqlCondition.equals("") ? " where " : (
            String.valueOf(hqlCondition) + " and ")) + (
            (domainId != null) ? (" workLog.logDomainId=" + domainId) : 
            "") + 
            " order by workLog.logDate desc,workProject.projectName");
        List<Object[]> queryList = worklogQuery.list();
        for (int i = 0; i < queryList.size(); i++) {
          Object[] o = queryList.get(i);
          WorkLogPO workLogPO = null;
          WorkLogVO workLogVO = null;
          for (int j = 0; j < o.length; j++) {
            if (j == 0) {
              workLogPO = (WorkLogPO)o[j];
              workLogVO = 
                (WorkLogVO)TransformObject.getInstance()
                .transformObject(workLogPO, 
                  WorkLogVO.class);
            } else if (j == 1) {
              workLogVO.setProjectName(o[j].toString());
              result.add(workLogVO);
            } 
          } 
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("search WorkLog Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String getProjectNameAndClassName(String projectIds, Long domainId) throws HibernateException {
    String result = null;
    try {
      begin();
      String[] projectClassId = projectIds.split(":");
      StringBuffer _ret = new StringBuffer("");
      if ("0".equals(projectClassId[0]))
        return _ret.toString(); 
      Long classId = Long.valueOf(projectClassId[0]);
      Long projectId = Long.valueOf(projectClassId[1]);
      WorkProjectPO workProject = (WorkProjectPO)this.session.load(
          WorkProjectPO.class, projectId);
      WorkProjectClassPO workProjectClass = (WorkProjectClassPO)this.session
        .load(WorkProjectClassPO.class, 
          classId);
      _ret.append(workProject.getProjectName());
      _ret.append("(");
    } catch (HibernateException ex) {
      System.out.println("get projectName and ClassName Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    this.session.close();
    this.transaction = null;
    this.session = null;
    return result;
  }
  
  public boolean addWorkProjectClass(WorkProjectClassPO workProjectClassPO) throws HibernateException {
    boolean result = false;
    try {
      begin();
      this.session.save(workProjectClassPO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("add WorkProjectClass Exception:" + 
          ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public WorkProjectClassVO selectWorkProjectClass(Long classId) throws HibernateException {
    WorkProjectClassVO workProjectClassVO = null;
    try {
      begin();
      WorkProjectClassPO workProjectClassPO = 
        (WorkProjectClassPO)this.session.load(WorkProjectClassPO.class, classId);
      workProjectClassVO = 
        (WorkProjectClassVO)TransformObject.getInstance().transformObject(
          workProjectClassPO, WorkProjectClassVO.class);
    } catch (HibernateException ex) {
      System.out.println("select WorkProjectClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return workProjectClassVO;
  }
  
  public List selectAllWorkProjectClass(Long userId, Long orgId, Long domainId) throws HibernateException {
    List<WorkProjectClassVO> result = new ArrayList();
    try {
      begin();
      Long rightScopeId = workProjectClassRight(userId);
      String hqlCondition = "";
      if (rightScopeId != null) {
        hqlCondition = makeWorkProjectClassCondition(userId, orgId, 
            rightScopeId);
        if (hqlCondition != null) {
          Query workProjectClassQuery = this.session.createQuery("select workProjectClass from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass " + (
              hqlCondition.equals("") ? " where " : (
              String.valueOf(hqlCondition) + " and ")) + (
              (domainId != null) ? (
              " workProjectClass.classDomainId = " + 
              domainId) : "") + 
              " order by workProjectClass.className");
          List queryList = workProjectClassQuery.list();
          Iterator<WorkProjectClassPO> iterator = queryList.iterator();
          WorkProjectClassPO workProjectClassPO = null;
          while (iterator != null && iterator.hasNext()) {
            workProjectClassPO = iterator.next();
            WorkProjectClassVO workProjectClassVO = 
              (WorkProjectClassVO)TransformObject.getInstance()
              .transformObject(workProjectClassPO, 
                WorkProjectClassVO.class);
            workProjectClassVO.setMaintenance(Boolean.TRUE);
            result.add(workProjectClassVO);
          } 
        } 
      } else {
        Query workProjectClassQuery = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass where (workProjectClass.createdEmp = " + 
            userId + (
            (domainId != null) ? (
            " and workProjectClass.classDomainId = " + domainId) : 
            "") + 
            ") order by workProjectClass.className");
        List queryList = workProjectClassQuery.list();
        Iterator<WorkProjectClassPO> iterator = queryList.iterator();
        WorkProjectClassPO workProjectClassPO = null;
        while (iterator != null && iterator.hasNext()) {
          workProjectClassPO = iterator.next();
          WorkProjectClassVO workProjectClassVO = 
            (WorkProjectClassVO)TransformObject.getInstance().transformObject(
              workProjectClassPO, WorkProjectClassVO.class);
          workProjectClassVO.setMaintenance(Boolean.TRUE);
          result.add(workProjectClassVO);
        } 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("select workLog Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteWorkProjectClass(Long classId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      result = deleteWorkProjectClassRecord(classId);
      if (result.booleanValue())
        this.session.flush(); 
    } catch (HibernateException ex) {
      System.out.println("delete WorkProjectClass Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Boolean deleteWorkProjectClassRecord(Long classId) throws HibernateException {
    Boolean result = new Boolean(false);
    WorkProjectClassPO workProjectClassPO = 
      (WorkProjectClassPO)this.session.load(WorkProjectClassPO.class, 
        classId);
    Hibernate.initialize(workProjectClassPO.getProjectSteps());
    Hibernate.initialize(workProjectClassPO.getWorkProjects());
    int projectStepSize = workProjectClassPO.getProjectSteps().size();
    int workProjectsSize = workProjectClassPO.getWorkProjects().size();
    if (projectStepSize > 0 || workProjectsSize > 0) {
      result = Boolean.FALSE;
    } else {
      this.session.delete(workProjectClassPO);
      result = Boolean.TRUE;
    } 
    return result;
  }
  
  public Boolean deleteBatchWorkProjectClass(String ids) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] idsArr = ids.split(",");
      boolean deleteFlag = false;
      for (int i = 0; i < idsArr.length; i++) {
        deleteFlag = deleteWorkProjectClassRecord(Long.valueOf(idsArr[i]))
          .booleanValue();
        if (!deleteFlag)
          break; 
      } 
      if (deleteFlag) {
        this.session.flush();
        result = Boolean.TRUE;
      } else {
        result = Boolean.FALSE;
      } 
    } catch (HibernateException ex) {
      System.out.println("delete Batch WorkProjectClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean modifyWorkProjectClass(WorkProjectClassVO workProjectClassVO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      WorkProjectClassPO workProjectClassPO = 
        (WorkProjectClassPO)this.session.load(WorkProjectClassPO.class, 
          workProjectClassVO.getClassId());
      workProjectClassPO.setClassName(workProjectClassVO.getClassName());
      workProjectClassPO.setClassOrgRange(workProjectClassVO
          .getClassOrgRange());
      workProjectClassPO.setClassUserRange(workProjectClassVO
          .getClassUserRange());
      workProjectClassPO.setClassGroupRange(workProjectClassVO
          .getClassGroupRange());
      workProjectClassPO.setClassRange(workProjectClassVO.getClassRange());
      this.session.update(workProjectClassPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("modify WorkProjectClass Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean addWorkProject(WorkProjectPO workProjectPO, Long classId) throws Exception {
    boolean result = false;
    try {
      begin();
      WorkProjectClassPO workProjectClassPO = 
        (WorkProjectClassPO)this.session.load(WorkProjectClassPO.class, classId);
      workProjectPO.setWorkProjectClass(workProjectClassPO);
      HashSet set = new HashSet();
      workProjectPO.setWorkLogs(set);
      this.session.save(workProjectPO);
      this.session.flush();
      result = true;
    } catch (Exception ex) {
      System.out.println("add WorkProject Exception:" + ex.getMessage());
      ex.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteWorkProject(Long id) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      result = deleteSingleWorkProject(id);
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("delete WorkProject Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Boolean deleteSingleWorkProject(Long id) throws HibernateException {
    Boolean result = new Boolean(false);
    WorkProjectPO workProjectPO = (WorkProjectPO)this.session.load(
        WorkProjectPO.class, id);
    Hibernate.initialize(workProjectPO.getWorkLogs());
    if (workProjectPO.getWorkLogs().size() < 1) {
      this.session.delete(workProjectPO);
      result = Boolean.TRUE;
    } 
    return result;
  }
  
  public Boolean deleteBatchWorkProject(String projectIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      String[] ids = projectIds.split(",");
      Boolean delFlag = new Boolean(false);
      for (int i = 0; i < ids.length; i++) {
        delFlag = deleteSingleWorkProject(new Long(ids[i]));
        if (!delFlag.booleanValue())
          break; 
      } 
      if (delFlag.booleanValue())
        this.session.flush(); 
      result = delFlag;
    } catch (HibernateException ex) {
      this.transaction.rollback();
      System.out.println("delete Batch WorkProject Exception:" + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean modifyWorkProject(WorkProjectVO workProjectVO) throws HibernateException {
    Boolean result = new Boolean(false);
    WorkProjectPO workProjectPO = new WorkProjectPO();
    try {
      begin();
      TransformObject transformObject = TransformObject.getInstance();
      workProjectPO = (WorkProjectPO)transformObject.transformObject(
          workProjectVO, WorkProjectPO.class);
      workProjectPO.setWorkProjectClass((WorkProjectClassPO)this.session.load(
            WorkProjectClassPO.class, workProjectVO.getClassId()));
      Hibernate.initialize(workProjectPO.getWorkLogs());
      this.session.update(workProjectPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("modify WorkProject Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectAllWorkProjectClass(Long userId, String orgIdString, Long domainId) {
    List<WorkProjectClassVO> list = new ArrayList();
    try {
      begin();
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + 
          orgIdString + 
          "' like concat('%$', org.orgId, '$%')";
      } else if (databaseType.indexOf("db2") >= 0) {
        tmpSql = "select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$',JSDB.FN_INTTOSTR(org.orgId)),'$'), '" + 
          
          orgIdString + "') > 0 ";
      } else {
        tmpSql = "select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where '" + 
          orgIdString + "' like JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('%$',JSDB.FN_INTTOSTR(org.orgId)),'$%')";
      } 
      Query orgQuery = this.session.createQuery(tmpSql);
      List<E> orgList = orgQuery.list();
      StringBuffer orgHql = new StringBuffer();
      String orgidtemp = "";
      for (int i = 0; i < orgList.size(); i++) {
        orgidtemp = orgList.get(i).toString();
        if (i + 1 < orgList.size()) {
          orgHql.append(" workProjectClass.classOrgRange like '%*" + 
              orgidtemp + "*%' or");
        } else if (i + 1 == orgList.size()) {
          orgHql.append(" workProjectClass.classOrgRange like '%*" + 
              orgidtemp + "*%'");
        } 
      } 
      orgidtemp = orgHql.toString();
      orgidtemp = String.valueOf(orgidtemp) + (
        (domainId != null) ? (
        " and workProjectClass.classDomainId = " + domainId) : "");
      Query groupQuery = this.session.createQuery(
          "from com.js.system.vo.groupmanager.GroupVO group1 join group1.employees emp where emp=" + 
          userId);
      String groupidtemp = "";
      if (groupQuery.list() != null) {
        List<E> groupList = groupQuery.list();
        StringBuffer groupHql = new StringBuffer();
        for (int j = 0; j < groupList.size(); j++) {
          groupidtemp = groupList.get(j).toString();
          if (j + 1 < groupList.size()) {
            groupHql.append(
                " workProjectClass.classGroupRange like '%@" + 
                groupidtemp + "@%' or ");
          } else if (j + 1 == groupList.size()) {
            groupHql.append(
                " workProjectClass.classGroupRange like '%@" + 
                groupidtemp + "@%'");
          } 
        } 
        groupidtemp = groupHql.toString();
      } 
      if (!orgidtemp.equals("")) {
        List<WorkProjectClassPO> queryList = new ArrayList();
        if (!groupidtemp.equals("")) {
          Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass where ((" + 
              orgidtemp + ") or (" + 
              groupidtemp + 
              ") or (workProjectClass.classUserRange like '%$" + 
              userId + "$%'))");
          queryList.addAll(query.list());
        } else {
          Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass where ((" + 
              orgidtemp + 
              ") or (workProjectClass.classUserRange like '%$" + 
              userId + "$%'))");
          queryList.addAll(query.list());
        } 
        WorkProjectClassPO workProjectClassPO = null;
        for (int j = 0; j < queryList.size(); j++) {
          workProjectClassPO = queryList.get(j);
          WorkProjectClassVO workProjectClassVO = 
            new WorkProjectClassVO();
          workProjectClassVO.conversionPO(workProjectClassPO);
          list.add(workProjectClassVO);
        } 
      } else if (!"".equals(groupidtemp)) {
        Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass where ((" + 
            groupidtemp + 
            ") or (workProjectClass.classUserRange like '%$" + 
            userId + "$%'))");
        List<WorkProjectClassPO> queryList = query.list();
        WorkProjectClassPO workProjectClassPO = null;
        for (int j = 0; j < queryList.size(); j++) {
          workProjectClassPO = queryList.get(j);
          WorkProjectClassVO workProjectClassVO = 
            new WorkProjectClassVO();
          workProjectClassVO.conversionPO(workProjectClassPO);
          list.add(workProjectClassVO);
        } 
      } else {
        Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass where workProjectClass.classUserRange like '%$" + 
            
            userId + "$%'");
        List<WorkProjectClassPO> queryList = query.list();
        WorkProjectClassPO workProjectClassPO = null;
        for (int j = 0; j < queryList.size(); j++) {
          workProjectClassPO = queryList.get(j);
          WorkProjectClassVO workProjectClassVO = 
            new WorkProjectClassVO();
          workProjectClassVO.conversionPO(workProjectClassPO);
          list.add(workProjectClassVO);
        } 
      } 
    } catch (HibernateException ex) {
      if (list != null)
        list.clear(); 
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        if (list != null)
          list.clear(); 
        throw new EJBException("can not close session!", ex1);
      } finally {
        this.session = null;
      } 
    } 
    return list;
  }
  
  public List selectAllWorkProject(Long userId, Long orgId, Long domainId, String where, String order) throws HibernateException {
    List<WorkProjectVO> result = new ArrayList();
    try {
      begin();
      Long rightScopeId = workProjectRight(userId);
      String hqlCondition = "";
      if (rightScopeId != null) {
        hqlCondition = makeWorkProjectCondition(userId, orgId, 
            rightScopeId);
        if (hqlCondition != null) {
          Query workProjectQuery = this.session.createQuery("select workProject,workProjectClass.className,workProjectClass.classId from com.js.oa.scheme.worklog.po.WorkProjectPO workProject join workProject.workProjectClass workProjectClass " + (
              hqlCondition.equals("") ? " where " : (
              String.valueOf(hqlCondition) + " and ")) + (
              (domainId != null) ? (
              " workProject.projectDomainId = " + domainId) : "") + 
              where + (
              (order.length() == 0) ? 
              " order by workProjectClass.className,workProject.projectStatus" : (
              " order by " + order)));
          List<Object[]> queryList = workProjectQuery.list();
          for (int i = 0; i < queryList.size(); i++) {
            Object[] o = queryList.get(i);
            WorkProjectPO workProjectPO = null;
            WorkProjectVO workProjectVO = null;
            for (int j = 0; j < o.length; j++) {
              Query emp = null;
              StringBuffer empName;
              List list = null;
              Object o1;
              switch (j) {
                case 0:
                  workProjectPO = (WorkProjectPO)o[j];
                  workProjectVO = 
                    (WorkProjectVO)TransformObject.getInstance().transformObject(
                      workProjectPO, WorkProjectVO.class);
                  workProjectVO.setMaintenance(Boolean.TRUE);
                  break;
                case 1:
                  workProjectVO.setClassName(o[j].toString());
                  break;
                case 2:
                  workProjectVO.setClassId(Long.valueOf(o[j]
                        .toString()));
                  emp = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
                      workProjectVO.getCreatedEmp());
                  empName = new StringBuffer();
                  list = emp.list();
                  o1 = list.get(0);
                  empName.append(o1.toString());
                  workProjectVO.setEmpName(empName.toString());
                  result.add(workProjectVO);
                  break;
              } 
            } 
          } 
        } 
      } else {
        Query workProjectQuery = this.session.createQuery("select workProject,workProjectClass.className,workProjectClass.classId from com.js.oa.scheme.worklog.po.WorkProjectPO workProject join workProject.workProjectClass workProjectClass where workProject.createdEmp = " + 
            userId + (
            (domainId != null) ? (
            " and workProject.projectDomainId = " + domainId) : "") + 
            where + 
            " order by workProjectClass.className,workProject.projectStatus");
        List<Object[]> queryList = workProjectQuery.list();
        for (int i = 0; i < queryList.size(); i++) {
          Object[] o = queryList.get(i);
          WorkProjectPO workProjectPO = null;
          WorkProjectVO workProjectVO = null;
          for (int j = 0; j < o.length; j++) {
            Query emp = null;
            StringBuffer empName;
            List list = null;
            Object o1;
            switch (j) {
              case 0:
                workProjectPO = (WorkProjectPO)o[j];
                workProjectVO = 
                  (WorkProjectVO)TransformObject.getInstance().transformObject(
                    workProjectPO, WorkProjectVO.class);
                workProjectVO.setMaintenance(Boolean.TRUE);
                break;
              case 1:
                workProjectVO.setClassName(o[j].toString());
                break;
              case 2:
                workProjectVO.setClassId(Long.valueOf(o[j].toString()));
                emp = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
                    workProjectVO.getCreatedEmp());
                empName = new StringBuffer();
                list = emp.list();
                o1 = list.get(0);
                empName.append(o1.toString());
                workProjectVO.setEmpName(empName.toString());
                result.add(workProjectVO);
                break;
            } 
          } 
        } 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("select WorkProject Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public WorkProjectVO selectSingleWorkProject(Long projectId, Long domainId) throws HibernateException {
    WorkProjectVO workProjectVO = null;
    try {
      begin();
      WorkProjectPO workProject = (WorkProjectPO)this.session.load(
          WorkProjectPO.class, projectId);
      workProjectVO = (WorkProjectVO)TransformObject.getInstance()
        .transformObject(workProject, WorkProjectVO.class);
      Query emp = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
          workProject.getCreatedEmp());
      StringBuffer empName = new StringBuffer();
      List list = emp.list();
      Object o = list.get(0);
      empName.append(o.toString());
      workProjectVO.setEmpName(empName.toString());
      Hibernate.initialize(workProject.getWorkProjectClass());
      workProjectVO.setClassId(workProject.getWorkProjectClass()
          .getClassId());
    } catch (HibernateException ex) {
      System.out.println("select Work project Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return workProjectVO;
  }
  
  public List searchWorkLog(Long userId, String startTime, String endTime, Long domainId) throws HibernateException {
    List<WorkLogVO> result = new ArrayList();
    try {
      begin();
      String tmpSql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select workLog,workProject.projectName from com.js.oa.scheme.worklog.po.WorkLogPO workLog join workLog.workProject workProject where workLog.createdEmp = " + 
          
          userId + 
          " and workLog.logDate >='" + 
          startTime + 
          "'  and workLog.logDate <= '" + 
          endTime + 
          "'" + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + 
          domainId) : "") + 
          " order by workLog.logDate desc";
      } else {
        tmpSql = "select workLog,workProject.projectName from com.js.oa.scheme.worklog.po.WorkLogPO workLog join workLog.workProject workProject where workLog.createdEmp = " + 
          
          userId + 
          " and workLog.logDate >=JSDB.FN_STRTODATE('" + 
          startTime + 
          "','S')" + 
          " and workLog.logDate <= JSDB.FN_STRTODATE('" + 
          endTime + 
          "','S')" + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + 
          domainId) : "") + 
          " order by workLog.logDate desc";
      } 
      Query query = this.session.createQuery(tmpSql);
      List<Object[]> queryList = query.list();
      for (int i = 0; i < queryList.size(); i++) {
        Object[] o = queryList.get(i);
        WorkLogPO workLogPO = null;
        WorkLogVO workLogVO = null;
        for (int j = 0; j < o.length; j++) {
          if (j == 0) {
            workLogPO = (WorkLogPO)o[j];
            workLogVO = (WorkLogVO)TransformObject.getInstance()
              .transformObject(workLogPO, WorkLogVO.class);
          } else if (j == 1) {
            workLogVO.setProjectName(o[j].toString());
            workLogVO.setMaintenance(Boolean.TRUE);
            Date now = new Date();
            if ((now.getTime() - workLogVO.getLogDate().getTime()) / 
              86400000L > 3L)
              workLogVO.setMaintenance(Boolean.FALSE); 
            result.add(workLogVO);
          } 
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println(ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectWorkProjectForProjectCount(Long userId, Long orgId, Long domainId) {
    List result = null;
    try {
      begin();
      Query query = this.session.createQuery("select rightscope.rightScopeType,rightscope.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO rightscope where rightscope.employee.empId = " + 
          
          userId + " and rightscope.right.rightName = '统计' and rightscope.right.rightType = '工作日志-项目统计'");
      List<Object[]> list = query.list();
      if (list.size() > 0) {
        Query orgQuery = null;
        List<E> orgList;
        int i;
        String rightScopeScope, orgIdArr[];
        StringBuffer orgIdSelf;
        int j;
        Object[] rightObject = list.get(0);
        Integer rightScopeType = Integer.valueOf(rightObject[0]
            .toString());
        String orgIds = "";
        switch (rightScopeType.intValue()) {
          case 0:
            result = getWorkProject(
                "where workProject.projectDomainId=" + domainId);
            break;
          case 1:
            result = getWorkProject("where workProject.createdEmp = " + 
                userId + (
                (domainId != null) ? (
                " and workProject.projectDomainId = " + 
                domainId) : 
                ""));
            break;
          case 3:
            result = getWorkProject("where workProject.createdOrg = " + 
                orgId + (
                (domainId != null) ? (
                " and workProject.projectDomainId = " + 
                domainId) : 
                ""));
            break;
          case 2:
            orgQuery = this.session.createQuery("select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                
                orgId + "$%'");
            orgList = orgQuery.list();
            orgIds = "";
            for (i = 0; i < orgList.size(); i++)
              orgIds = String.valueOf(orgIds) + orgList.get(i).toString() + ","; 
            if (!orgIds.equals("")) {
              orgIds = orgIds.substring(0, orgIds.length() - 1);
              result = getWorkProject(
                  "where workProject.createdOrg in (" + orgIds + 
                  ")" + (
                  (domainId != null) ? (
                  " and workProject.projectDomainId = " + 
                  domainId) : 
                  ""));
            } 
            break;
          case 4:
            rightScopeScope = rightObject[1].toString();
            orgIds = (new ConversionString(rightScopeScope))
              .getOrgIdString();
            orgIdArr = orgIds.split(",");
            orgIdSelf = new StringBuffer();
            for (j = 0; j < orgIdArr.length; j++) {
              Query org = this.session.createQuery("select distinct org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                  
                  orgIdArr[j] + 
                  "$%'");
              List<E> orgl = org.list();
              orgIds = "";
              for (int k = 0; k < orgl.size(); k++)
                orgIds = String.valueOf(orgIds) + orgl.get(k).toString() + ","; 
              if (!orgIds.equals(""))
                if (j + 1 < orgIdArr.length) {
                  orgIdSelf.append(orgIds);
                } else {
                  orgIds = orgIds.substring(0, 
                      orgIds.length() - 1);
                  orgIdSelf.append(orgIds);
                }  
            } 
            result = getWorkProject("where workProject.createdOrg in (" + 
                orgIdSelf.toString() + ")" + (
                (domainId != null) ? (
                " and workProject.projectDomainId = " + 
                domainId) : 
                ""));
            break;
        } 
      } 
    } catch (HibernateException ex) {
      if (result != null)
        result.clear(); 
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        if (result != null)
          result.clear(); 
        throw new EJBException("can not close session!", ex1);
      } finally {
        this.transaction = null;
        this.session = null;
      } 
    } 
    return result;
  }
  
  private List getWorkProject(String hqlCondition) {
    List<WorkProjectVO> result = new ArrayList();
    try {
      Query workprojectQuery = this.session.createQuery(
          "from com.js.oa.scheme.worklog.po.WorkProjectPO workProject " + 
          hqlCondition);
      List<WorkProjectPO> list = workprojectQuery.list();
      WorkProjectPO workprojectPO = null;
      for (int i = 0; i < list.size(); i++) {
        workprojectPO = list.get(i);
        Hibernate.initialize(workprojectPO.getWorkProjectClass());
        WorkProjectVO workproject = 
          (WorkProjectVO)TransformObject.getInstance().transformObject(
            workprojectPO, WorkProjectVO.class);
        workproject.setClassId(workprojectPO.getWorkProjectClass()
            .getClassId());
        workproject.setClassName(workprojectPO.getWorkProjectClass()
            .getClassName());
        result.add(workproject);
      } 
    } catch (HibernateException ex) {
      if (result != null)
        result.clear(); 
      throw new EJBException("Hibernate Exception: ", ex);
    } 
    return result;
  }
  
  public String selectProjectDate(String userName, String logDate, Long domainId) throws HibernateException {
    String projectManHour = null;
    try {
      begin();
      String tmpSql = "";
      String databaseType = 
        SystemCommon.getDatabaseType();
      if (databaseType.indexOf("mysql") >= 0) {
        tmpSql = "select SUM(workLog.manHour) from com.js.oa.scheme.worklog.po.WorkLogPO workLog where (workLog.empName = '" + 
          userName + 
          "') and (workLog.logDate = '" + logDate + 
          "')" + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + domainId) : "");
      } else {
        tmpSql = "select SUM(workLog.manHour) from com.js.oa.scheme.worklog.po.WorkLogPO workLog where (workLog.empName = '" + 
          userName + 
          "') and (workLog.logDate = JSDB.FN_STRTODATE('" + 
          logDate + 
          "','S'))" + (
          (domainId != null) ? (
          " and workLog.logDomainId = " + domainId) : "");
      } 
      Query query = this.session.createQuery(tmpSql);
      List<E> list = query.list();
      projectManHour = list.get(0).toString();
    } catch (HibernateException ex) {
      System.out.println("selectProjectDate Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return projectManHour;
  }
  
  public WorkLogDO getWorkLogDO(Long id) throws HibernateException {
    WorkLogDO workLogDO = null;
    try {
      begin();
      workLogDO = (WorkLogDO)this.session.load(WorkLogDO.class, id);
    } catch (HibernateException ex) {
      ex.printStackTrace();
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return workLogDO;
  }
  
  public String workProjectClassNameIsExist(String workProjectClassName, Long domainId) {
    String result = "no";
    try {
      begin();
      Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectClassPO workProjectClass where workProjectClass.className = '" + 
          
          workProjectClassName + "'" + (
          (domainId != null) ? (
          " and workProjectClass.classDomainId=" + 
          String.valueOf(domainId.intValue())) : 
          ""));
      if (query.list().size() > 0)
        result = "yes"; 
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("close session is failed!", ex1);
      } finally {
        this.session = null;
        this.transaction = null;
      } 
    } 
    return result;
  }
  
  public WorkReportPO getWorkReportPO(String reportDate, String userId) {
    WorkReportPO result = new WorkReportPO();
    try {
      begin();
      String Sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        Sql = "from com.js.oa.scheme.workreport.po.WorkReportPO po where po.reportTime='" + 
          reportDate + "' and po.empId=" + userId + " and po.reportType=0 and po.sendType<>0";
      } else {
        Sql = "from com.js.oa.scheme.workreport.po.WorkReportPO po where po.reportTime=JSDB.FN_STRTODATE('" + 
          
          reportDate + "','S') and po.empId=" + userId + " and po.reportType=0 and po.sendType<>0";
      } 
      Query query = this.session.createQuery(Sql);
      if (query.list().size() > 0)
        result = query.list().get(0); 
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("close session is failed!", ex1);
      } finally {
        this.session = null;
        this.transaction = null;
      } 
    } 
    return result;
  }
  
  public String workProjectIsExist(Long classId, String workProjectName, Long domainId) {
    String result = "no";
    try {
      begin();
      Query query = this.session.createQuery("from com.js.oa.scheme.worklog.po.WorkProjectPO workProject join workProject.workProjectClass workProjectClass where workProjectClass.classId = " + 
          
          classId + 
          " and workProject.projectName ='" + 
          workProjectName + "'" + (
          (domainId != null) ? (
          " and workProject.projectDomainId = " + 
          domainId) : ""));
      if (query.list().size() > 0)
        result = "yes"; 
    } catch (HibernateException ex) {
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("close session is failed!", ex1);
      } finally {
        this.session = null;
        this.transaction = null;
      } 
    } 
    return result;
  }
  
  public Boolean addWorkLog(WorkLogPO workLogPO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Long logId = (Long)this.session.save(workLogPO);
      EmployeeVO employeeVO = (EmployeeVO)this.session.get(EmployeeVO.class, workLogPO.getCreatedEmp());
      String attEmp = employeeVO.getEmpLeaderId();
      if (attEmp != null && !"".equals(attEmp)) {
        attEmp = attEmp.substring(1, attEmp.length() - 1);
        String[] attEmps = attEmp.split("\\$\\$");
        if (attEmps.length > 0) {
          String userIds = "";
          for (int i = 0; i < attEmps.length; i++)
            userIds = String.valueOf(userIds) + attEmps[i] + ","; 
          userIds = userIds.substring(0, userIds.length() - 1);
          Calendar tmp = Calendar.getInstance();
          tmp.set(2050, 12, 12);
          String url = "workLogAction.do?action=updateWorkLog&xiashu=1&logId=" + logId;
          String title = workLogPO.getLogContent();
          RemindUtil.sendMessageToUsers(title, url, userIds, "WorkLog", new Date(), tmp.getTime(), employeeVO.getEmpName(), logId);
        } 
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add worklog Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public WorkLogPO selectSingleWorkLogPO(Long workLogId) throws HibernateException {
    WorkLogPO workLogPO = null;
    try {
      begin();
      workLogPO = (WorkLogPO)this.session.load(WorkLogPO.class, workLogId);
      Set s = workLogPO.getWorklogComment();
      if (s != null && s.isEmpty()) {
        Iterator it = s.iterator();
        while (it.hasNext())
          it.next(); 
      } 
      workLogPO.setWorklogComment(s);
    } catch (HibernateException ex) {
      System.out.println("select Single WorkLog Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return workLogPO;
  }
  
  public Boolean modifyWorkLog(WorkLogPO workLog) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.update(workLog);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("modify WorkLog Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getUserLogList(String userId, String now, String logType) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String Sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.createdEmp=" + 
          userId + " and workLog.logDate='" + 
          now + "' and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } else {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.createdEmp=" + 
          userId + " and workLog.logDate=JSDB.FN_STRTODATE('" + 
          now + "','S') and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } 
      list = this.session.createQuery(Sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getUserLogListByUserName(String userName, String now, String logType) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String Sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.empName=" + 
          userName + " and workLog.logDate='" + 
          now + "' and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } else {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.empName=" + 
          userName + " and workLog.logDate=JSDB.FN_STRTODATE('" + 
          now + "','S') and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } 
      list = this.session.createQuery(Sql).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getDownEmployeeList(String userId) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      Long id = Long.valueOf(userId);
      list = this.session.createQuery(
          "select emp.empId,emp.empName,emp.empEmail,emp.empMobilePhone,emp.curStatus,emp.imId,org.orgIdString,emp.userAccounts,emp.userOnline,org.orgId,org.orgName from EmployeeVO emp join emp.organizations org where emp.empLeaderId like '%$" + 
          
          id.toString() + 
          "$%' and emp.userIsActive=1 and emp.userIsDeleted=0 and (emp.userAccounts != '' or emp.userAccounts is not null ) order by emp.empId desc ")
        .list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public String getWorkLogKeepDay(String domainId) throws Exception {
    String result = "0";
    begin();
    try {
      List<E> list = this.session.createQuery("select po.workLog from com.js.system.vo.organizationmanager.DomainVO po where po.id=" + 
          domainId).list();
      if (list.size() > 0)
        result = list.get(0).toString(); 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteWorkLogComment(Long commentid) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      WorkLogCommentPO workLogCommentPO = (WorkLogCommentPO)this.session.load(
          WorkLogCommentPO.class, commentid);
      this.session.delete(workLogCommentPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete WorkLog Exception:" + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean addWorkLogComment(WorkLogCommentPO workLogCommentPO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Long dateid = (Long)this.session.save(workLogCommentPO);
      Calendar tmp = Calendar.getInstance();
      tmp.set(2050, 12, 12);
      String url = "/jsoa/workLogAction.do?action=updateWorkLog&logId=" + workLogCommentPO.getWorklog().getLogId();
      String title = "领导评价了你的日报";
      RemindUtil.sendMessageToUsers(title, url, workLogCommentPO.getWorklog().getCreatedEmp().toString(), "WorkLogComment", new Date(), tmp.getTime(), workLogCommentPO.getCommentIssuerName(), dateid);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add worklog Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getDDUserLogList(String userId, String beginDate, String endDate, String logType) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      String Sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.createdEmp=" + 
          userId + " and workLog.logDate>='" + 
          beginDate + 
          "' and workLog.logDate<='" + 
          endDate + "' and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } else {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.createdEmp=" + 
          userId + " and workLog.logDate>=JSDB.FN_STRTODATE('" + 
          beginDate + 
          "','S') and workLog.logDate<=JSDB.FN_STRTODATE('" + 
          endDate + "','S') and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } 
      list = this.session.createQuery(Sql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getDDUserLogListByUserName(String userName, String beginDate, String endDate, String logType) throws HibernateException {
    List list = new ArrayList();
    begin();
    try {
      String Sql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.empName=" + 
          userName + " and workLog.logDate>='" + 
          beginDate + 
          "' and workLog.logDate<='" + 
          endDate + "' and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } else {
        Sql = "select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.empName=" + 
          userName + 
          " and workLog.logDate>=JSDB.FN_STRTODATE('" + 
          beginDate + 
          "','S') and workLog.logDate<=JSDB.FN_STRTODATE('" + 
          endDate + "','S') and workLog.logType=" + logType + 
          " order by workLog.startPeriod asc ";
      } 
      list = this.session.createQuery(Sql).list();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public Integer addWorkTask(WorkProjectTaskPO workTask, String taskOrderId, String radiobutton) throws HibernateException {
    Integer result = Integer.valueOf("0");
    begin();
    try {
      String task_code = workTask.getTask_code();
      Query query0 = null;
      List list0 = new ArrayList();
      query0 = this.session.createQuery(
          "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_code='" + 
          task_code + "'");
      list0 = query0.list();
      if (list0.size() != 0)
        return new Integer(1); 
      Long taskId = (Long)this.session.save(workTask);
      Query query = null;
      List<Object[]> list = null;
      int sort = 0;
      if (taskOrderId.equals("-1")) {
        sort = 500000;
      } else {
        query = this.session.createQuery(
            " select aaa.task_sort, aaa.task_fathercode, aaa.task_level from  from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_id = " + 
            
            taskOrderId);
        list = query.list();
        Object[] tmpObj = list.get(0);
        String taskSort = tmpObj[0].toString();
        String taskParentId = tmpObj[1].toString();
        String taskType = tmpObj[2].toString();
        if (radiobutton.equals("up")) {
          query = this.session.createQuery(
              " select max(aaa.task_sort) from  com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_level = " + 
              
              taskType + 
              " and aaa.task_fathercode = " + 
              taskParentId + 
              " and aaa.task_sort < " + taskSort);
          String maxChannelSort = "";
          list = query.list();
          if (list != null && list.size() > 0 && list.get(0) != null) {
            maxChannelSort = list.get(0).toString();
          } else {
            maxChannelSort = "0";
          } 
          if (maxChannelSort == null) {
            sort = Integer.parseInt(taskSort) - 10000;
          } else {
            sort = (Integer.parseInt(taskSort) + 
              Integer.parseInt(maxChannelSort)) / 2;
          } 
        } else {
          query = this.session.createQuery(
              " select min(aaa.task_sort) from  com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_level = " + 
              
              taskType + 
              " and aaa.task_fathercode = " + 
              taskParentId + 
              " and aaa.task_sort > " + taskSort);
          list = query.list();
          if (list == null || (list.size() == 1 && list.get(0) == null)) {
            sort = Integer.parseInt(taskSort) + 10000;
            if (sort > 1000000) {
              result = Integer.valueOf("-1");
              return result;
            } 
          } else {
            String minChannelSort = list.get(0).toString();
            sort = (Integer.parseInt(taskSort) + 
              Integer.parseInt(minChannelSort)) / 2;
          } 
        } 
      } 
      String taskLevel = "0";
      String taskIdString = "";
      String task_ParentId = workTask.getTask_fathercode();
      if (!"0".equals(task_ParentId)) {
        query = this.session.createQuery("select aaa.task_level,aaa.task_achieve from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa where aaa.task_id = " + 
            task_ParentId);
        list = query.list();
        Object[] obj = list.get(0);
        taskLevel = obj[0].toString();
        taskIdString = obj[1].toString();
      } 
      taskIdString = String.valueOf(taskIdString) + sort + "$" + taskId + 
        "$_";
      workTask.setTask_achieve(taskIdString);
      workTask.setTask_level(Integer.parseInt(taskLevel) + 1);
    } catch (HibernateException e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    this.session.close();
    this.session = null;
    this.transaction = null;
    return result;
  }
  
  public List listWorkTask() throws HibernateException {
    begin();
    List list = new ArrayList();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po");
      if (query != null)
        list = query.list(); 
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public Integer updateProjectTask(WorkProjectTaskPO workTask, String taskOrderId, String radiobutton) throws HibernateException {
    begin();
    int result = 2;
    String task_code = workTask.getTask_code();
    Query query0 = null;
    List list0 = new ArrayList();
    query0 = this.session.createQuery(
        "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_code='" + 
        task_code + "' and po.id<>" + workTask.getTask_id());
    list0 = query0.list();
    if (list0.size() != 0)
      return new Integer(1); 
    try {
      Query query = null;
      List<Object[]> list = null;
      int sort = 0;
      if (taskOrderId.equals("-1")) {
        sort = 500000;
      } else {
        query = this.session.createQuery(
            "select aaa.task_sort, aaa.task_fathercode, aaa.task_level from  from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_id = " + 
            
            taskOrderId);
        list = query.list();
        Object[] tmpObj = list.get(0);
        String orderTaskSort = tmpObj[0].toString();
        String orderTaskParentId = tmpObj[1].toString();
        String orderTaskType = tmpObj[2].toString();
        if (radiobutton.equals("up")) {
          query = this.session.createQuery(
              " select max(aaa.task_sort) from  com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_level = " + 
              
              orderTaskType + 
              " and aaa.task_fathercode = " + 
              orderTaskParentId + 
              " and aaa.task_sort < " + orderTaskSort);
          list = query.list();
          String maxChannelSort = "";
          if (list.get(0) != null) {
            maxChannelSort = list.get(0).toString();
          } else {
            maxChannelSort = "0";
          } 
          if (maxChannelSort.equals("0")) {
            sort = Integer.parseInt(orderTaskSort) - 10000;
          } else {
            sort = (Integer.parseInt(orderTaskSort) + 
              Integer.parseInt(maxChannelSort)) / 2;
          } 
        } else {
          query = this.session.createQuery(
              " select min(aaa.task_sort) from  com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_level = " + 
              
              orderTaskType + 
              " and aaa.task_fathercode = " + 
              orderTaskParentId + 
              " and aaa.task_sort < " + orderTaskSort);
          list = query.list();
          if (list != null && list.size() > 0 && list.get(0) != null) {
            String minChannelSort = list.get(0).toString();
            sort = (Integer.parseInt(orderTaskSort) + 
              Integer.parseInt(minChannelSort)) / 2;
          } else {
            sort = Integer.parseInt(orderTaskSort) + 10000;
          } 
        } 
      } 
      Long modifyChId = workTask.getTask_id();
      query = this.session.createQuery("select aaa.task_achieve,aaa.task_sort, aaa.task_fathercode, aaa.task_level from  from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa  where aaa.task_id = " + 
          
          modifyChId);
      list = query.list();
      Object[] obj = list.get(0);
      String beforeModiChIdStr = obj[0].toString();
      String beforeModiParentId = obj[2].toString();
      String beforeModiChSort = obj[1].toString();
      String beforeModiChLevel = obj[3].toString();
      String modifyedChPaId = workTask.getTask_fathercode()
        .toString();
      String modifyedChIdStr = "";
      int modifyedChLevel = 0;
      if (beforeModiParentId.equals(modifyedChPaId)) {
        int i = beforeModiChIdStr.indexOf(String.valueOf(beforeModiChSort) + "$" + 
            modifyChId + "$_");
        modifyedChIdStr = String.valueOf(beforeModiChIdStr.substring(0, i)) + sort + 
          "$" + modifyChId + "$_";
        query = this.session.createQuery("select aaa from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa where aaa.task_id <>" + 
            modifyChId + 
            " and aaa.task_achieve like '%$" + 
            modifyChId + "$%' ");
        list = query.list();
        WorkProjectTaskPO tmpPO = null;
        String tmpIdString = "";
        for (int j = 0; j < list.size(); j++) {
          tmpPO = (WorkProjectTaskPO)list.get(j);
          tmpIdString = tmpPO.getTask_achieve();
          tmpPO.setTask_achieve(String.valueOf(modifyedChIdStr) + 
              tmpIdString
              .substring(beforeModiChIdStr.length(), 
                tmpIdString.length()));
        } 
        workTask.setTask_sort(Long.valueOf((new StringBuilder(String.valueOf(sort))).toString()));
        workTask.setTask_achieve(modifyedChIdStr);
        workTask.setTask_level(Integer.parseInt(
              beforeModiChLevel));
        this.session.update(workTask);
      } else if (modifyedChPaId.equals("0")) {
        modifyedChLevel = 1;
        modifyedChIdStr = String.valueOf(sort) + "$" + modifyChId + "$_";
        query = this.session.createQuery("select aaa from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa where aaa.task_achieve like  '%" + 
            beforeModiChIdStr + 
            "%' and aaa.task_id <> " + 
            modifyChId);
        list = query.list();
        WorkProjectTaskPO tmpPO = null;
        String tmpIdString = "";
        int tmpLevel = 0;
        for (int i = 0; i < list.size(); i++) {
          tmpPO = (WorkProjectTaskPO)list.get(i);
          tmpIdString = tmpPO.getTask_achieve();
          tmpLevel = tmpPO.getTask_level();
          tmpPO.setTask_level(tmpLevel - 1);
          tmpPO.setTask_achieve(String.valueOf(modifyedChIdStr) + 
              tmpIdString
              .substring(beforeModiChIdStr
                .length()));
        } 
        workTask.setTask_sort(Long.valueOf((new StringBuilder(String.valueOf(sort))).toString()));
        workTask.setTask_achieve(modifyedChIdStr);
        workTask.setTask_level(1);
        this.session.update(workTask);
      } else {
        query = this.session.createQuery("select aaa.task_achieve,aaa.task_level from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa where aaa.task_id = " + 
            modifyedChPaId);
        list = query.list();
        Object[] tmpObj = list.get(0);
        String modifyedPaChIdString = tmpObj[0].toString();
        String modifyedPaChLevel = tmpObj[1].toString();
        modifyedChIdStr = String.valueOf(modifyedPaChIdString) + sort + "$" + 
          modifyChId + "$_";
        query = this.session.createQuery("select aaa from com.js.oa.scheme.worklog.po.WorkProjectTaskPO aaa where aaa.task_achieve like  '%" + 
            beforeModiChIdStr + 
            "%' and aaa.task_id <> " + 
            modifyChId);
        list = query.list();
        WorkProjectTaskPO tmpPO = null;
        String tmpIdString = "";
        int tmpLevel = 0;
        for (int i = 0; i < list.size(); i++) {
          tmpPO = (WorkProjectTaskPO)list.get(i);
          tmpIdString = tmpPO.getTask_achieve();
          tmpLevel = tmpPO.getTask_level();
          tmpPO.setTask_achieve(String.valueOf(modifyedChIdStr) + 
              tmpIdString
              .substring(beforeModiChIdStr
                .length()));
          if (Integer.parseInt(modifyedPaChLevel) + 1 > 
            Integer.parseInt(beforeModiChLevel)) {
            tmpPO.setTask_level(tmpLevel + 1);
          } else if (Integer.parseInt(modifyedPaChLevel) + 1 < 
            Integer.parseInt(beforeModiChLevel)) {
            tmpPO.setTask_level(tmpLevel - 1);
          } else {
            tmpPO.setTask_level(tmpLevel);
          } 
        } 
        workTask.setTask_sort(Long.valueOf((new StringBuilder(String.valueOf(sort))).toString()));
        workTask.setTask_achieve(modifyedChIdStr);
        workTask.setTask_level(Integer.parseInt(
              modifyedPaChLevel) + 1);
        this.session.update(workTask);
      } 
      this.session.flush();
      result = 0;
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return new Integer(result);
  }
  
  public List selectProjectTaskById(String id) throws HibernateException {
    begin();
    List list = new ArrayList();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_id=" + 
          id);
      if (query != null)
        list = query.list(); 
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectProjectTaskByCode(String id) throws HibernateException {
    begin();
    List list = new ArrayList();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_code='" + 
          id + "'");
      if (query != null)
        list = query.list(); 
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectTaskByProjectId(String project_id) throws Exception {
    List list = new ArrayList();
    begin();
    try {
      String sql = "select po.task_code,po.task_fathercode,po.task_name,po.task_description from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po join po.workProject workProject where workProject.projectId=" + 
        project_id;
      list = this.session.createQuery(sql).list();
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public List selectProjectById(Long project_id) throws Exception {
    List list = null;
    begin();
    try {
      String sql = "select po from com.js.oa.scheme.worklog.po.WorkProjectPO po where po.projectId=" + 
        project_id;
      list = this.session.createQuery(sql).list();
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
  
  public boolean deleteProjectWorkTask(WorkProjectTaskPO workProjectTaskPO) throws HibernateException {
    boolean result = false;
    try {
      begin();
      this.session.delete(workProjectTaskPO);
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectFatherTask(String fatherId) throws HibernateException {
    List result = new ArrayList();
    try {
      begin();
      String sql = "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_fathercode=" + 
        fatherId;
      result = this.session.createQuery(sql).list();
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectFatherTask(String fatherId, String taskCode) throws HibernateException {
    List result = new ArrayList();
    try {
      begin();
      String sql = "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_fathercode=" + 
        fatherId + " and po.task_code <> '" + taskCode + "'";
      result = this.session.createQuery(sql).list();
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectTaskByName(String name) throws HibernateException {
    List result = new ArrayList();
    try {
      begin();
      String sql = "select po from com.js.oa.scheme.worklog.po.WorkProjectTaskPO po where po.task_name=" + 
        name;
      result = this.session.createQuery(sql).list();
      this.session.flush();
    } catch (HibernateException ex) {
      System.out.println("delete ProjectStep Exception:" + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchWorkLogForPortal(String workwhere) throws HibernateException {
    List list = null;
    try {
      begin();
      list = this.session.createQuery("select workLog ,emp.empName from com.js.oa.scheme.worklog.po.WorkLogPO workLog ,com.js.system.vo.usermanager.EmployeeVO emp " + workwhere).list();
      this.session.flush();
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public String getByIds(Long id) throws HibernateException {
    String result = "N";
    try {
      begin();
      List list = this.session.createQuery("select workLog from com.js.oa.scheme.worklog.po.WorkLogPO workLog where workLog.logId =" + id).list();
      if (!list.isEmpty())
        result = "Y"; 
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String getManHour(String sql) throws HibernateException {
    String result = "0.0";
    try {
      begin();
      List<E> list = this.session.createQuery(sql).list();
      if (list.size() > 0)
        result = (list.get(0) == null) ? "0.0" : list.get(0).toString(); 
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
}
