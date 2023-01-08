package com.js.oa.scheme.taskcenter.bean;

import com.js.oa.scheme.taskcenter.po.TaskAccessoryPO;
import com.js.oa.scheme.taskcenter.po.TaskClassPO;
import com.js.oa.scheme.taskcenter.po.TaskExecPO;
import com.js.oa.scheme.taskcenter.po.TaskHistoryAccessoryPO;
import com.js.oa.scheme.taskcenter.po.TaskHistoryPO;
import com.js.oa.scheme.taskcenter.po.TaskPO;
import com.js.oa.scheme.taskcenter.po.TaskPeriodicityAccessoryPO;
import com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO;
import com.js.oa.scheme.taskcenter.po.TaskRemindPO;
import com.js.oa.scheme.taskcenter.po.TaskReportAccessoryPO;
import com.js.oa.scheme.taskcenter.po.TaskReportPO;
import com.js.oa.scheme.taskcenter.service.TaskBD;
import com.js.oa.scheme.taskcenter.vo.RightScope;
import com.js.oa.scheme.taskcenter.vo.TaskClassVO;
import com.js.oa.scheme.taskcenter.vo.TaskReportVO;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import com.js.oa.scheme.util.ConversionString;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.groupmanager.GroupVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import com.js.util.util.TransformObject;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.LockMode;
import net.sf.hibernate.Query;
import org.apache.log4j.Logger;

public class TaskEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  Logger logger = (Logger)Logger.getInstance(TaskEJBBean.class.getName());
  
  private static String databaseType = SystemCommon.getDatabaseType();
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public Boolean addTaskClass(TaskClassVO taskClassVO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskClassPO taskClassPO = (TaskClassPO)TransformObject.getInstance()
        .transformObject(taskClassVO, TaskClassPO.class);
      this.session.save(taskClassPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add TaskClass Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteTaskClass(Long classId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskClassPO taskClassPO = (TaskClassPO)this.session.load(TaskClassPO.class, 
          classId);
      this.session.delete(taskClassPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete TaskClass Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean modifyTaskClass(TaskClassVO taskClassVO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskClassPO taskClassPO = (TaskClassPO)this.session.load(TaskClassPO.class, 
          taskClassVO.getClassId());
      taskClassPO.setClassName(taskClassVO.getClassName());
      taskClassPO.setUsedScopeName(taskClassVO.getUsedScopeName());
      taskClassPO.setUsedScopeId(taskClassVO.getUsedScopeId());
      taskClassPO.setUsedScopeOrgId(taskClassVO.getUsedScopeOrgId());
      taskClassPO.setUsedScopeGroupId(taskClassVO.getUsedScopeGroupId());
      taskClassPO.setOrderCode(taskClassVO.getOrderCode());
      this.session.update(taskClassPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("modify TaskClass Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String taskClassNameIsExist(String className, Long classId, Long domainId) throws Exception {
    String result = "no";
    begin();
    try {
      Query query = null;
      if (classId != null) {
        query = this.session.createQuery(
            "from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.className = '" + 
            
            className + "' and taskClass.classId<>" + classId + (
            (domainId != null) ? (
            " and taskClass.classDomainId = " + domainId) : 
            ""));
      } else {
        query = this.session.createQuery(
            "from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.className = '" + 
            
            className + "'" + (
            (domainId != null) ? (
            " and taskClass.classDomainId = " + domainId) : 
            ""));
      } 
      if (query.list().size() > 0)
        result = "yes"; 
    } catch (Exception ex) {
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public Boolean deleteAllTaskClass(Long userId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      this.session.delete(
          "from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.createdEmp = " + 
          userId);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete All taskClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deleteBatchTaskClass(String classIds) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      if (classIds != null && !classIds.equals("")) {
        classIds = classIds.substring(0, classIds.length() - 1);
        this.session.delete(
            "from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.classId in (" + 
            classIds + ")");
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("delete Batch TaskClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public TaskClassVO selectSingleTaskClass(Long classId) throws HibernateException {
    TaskClassVO taskClass = null;
    try {
      begin();
      TaskClassPO taskClassPO = (TaskClassPO)this.session.load(TaskClassPO.class, 
          classId);
      taskClass = (TaskClassVO)TransformObject.getInstance()
        .transformObject(taskClassPO, TaskClassVO.class);
      Query emp = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
          taskClassPO.getCreatedEmp());
      StringBuffer empName = new StringBuffer();
      List list = emp.list();
      Object o = list.get(0);
      empName.append(o.toString());
      taskClass.setEmpName(empName.toString());
    } catch (HibernateException ex) {
      System.out.println("select single task class Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return taskClass;
  }
  
  public List selectAllTask(Long domainId, Long userId, String orgIdString) throws HibernateException {
    List<TaskClassVO> result = new ArrayList();
    try {
      String where = " (taskClass.usedScopeName is null or taskClass.usedScopeName = '' ";
      where = String.valueOf(where) + " or taskClass.usedScopeId like '%$" + userId + "$%' ";
      where = String.valueOf(where) + " or taskClass.createdEmp = " + userId + " ";
      String[] orgIds = orgIdString.split("\\$");
      String _orgIds = "-1";
      if (orgIds != null && orgIds.length > 0)
        for (int i = 0; i < orgIds.length; i++) {
          if (orgIds[i] != null && orgIds[i].indexOf("_") == -1)
            _orgIds = String.valueOf(_orgIds) + "," + orgIds[i]; 
        }  
      String[] ___org = _orgIds.split(",");
      if (___org != null && ___org.length > 0)
        for (int i = 0; i < ___org.length; i++) {
          if (___org[i] != null && ___org[i].length() > 0)
            where = String.valueOf(where) + " or taskClass.usedScopeOrgId like '%*" + 
              ___org[i] + "*%' "; 
        }  
      where = String.valueOf(where) + ") ";
      begin();
      TaskClassPO taskClassPO = null;
      TaskClassVO taskClassVO = null;
      Query query = this.session.createQuery("select distinct taskClass from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where " + where + (
          (domainId != null) ? (
          " and taskClass.classDomainId = " + 
          domainId) : "") + " order by taskClass.orderCode");
      List resultSet = query.list();
      Iterator<TaskClassPO> it = resultSet.iterator();
      while (it != null && it.hasNext()) {
        taskClassPO = it.next();
        taskClassVO = (TaskClassVO)TransformObject.getInstance()
          .transformObject(taskClassPO, TaskClassVO.class);
        result.add(taskClassVO);
      } 
    } catch (HibernateException ex) {
      System.out.println("select All TaskClass Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectAllTaskClass(Long userId, Long orgId, Long domainId) throws HibernateException {
    List<TaskClassVO> result = new ArrayList();
    try {
      begin();
      Long rightScopeId = taskClassRight(userId);
      String hqlCondition = "";
      if (rightScopeId != null) {
        hqlCondition = makeTaskClassCondition(userId, orgId, 
            rightScopeId);
        String retStr = "select taskClass from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass " + (
          (hqlCondition.length() > 0) ? (
          String.valueOf(hqlCondition) + (
          (domainId != null) ? (
          " and taskClass.classDomainId = " + domainId) : 
          "")) : (
          (domainId != null) ? (
          " where taskClass.classDomainId = " + 
          domainId) : "")) + 
          " order by taskClass.orderCode,taskClass.className";
        Query taskClassQuery = this.session.createQuery(retStr);
        List queryList = taskClassQuery.list();
        Iterator<TaskClassPO> iterator = queryList.iterator();
        TaskClassPO taskClassPO = null;
        while (iterator != null && iterator.hasNext()) {
          taskClassPO = iterator.next();
          TaskClassVO taskClassVO = 
            (TaskClassVO)TransformObject.getInstance()
            .transformObject(taskClassPO, 
              TaskClassVO.class);
          taskClassVO.setMaintenance(Boolean.TRUE);
          result.add(taskClassVO);
        } 
      } else {
        Query taskClassQuery = this.session.createQuery("select taskClass from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.createdEmp = " + 
            userId + 
            " order by taskClass.orderCode,taskClass.className");
        List queryList = taskClassQuery.list();
        Iterator<TaskClassPO> iterator = queryList.iterator();
        TaskClassPO taskClassPO = null;
        while (iterator != null && iterator.hasNext()) {
          taskClassPO = iterator.next();
          TaskClassVO taskClassVO = 
            (TaskClassVO)TransformObject.getInstance()
            .transformObject(
              taskClassPO, TaskClassVO.class);
          taskClassVO.setMaintenance(Boolean.TRUE);
          result.add(taskClassVO);
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
  
  private Long taskClassRight(Long userId) throws HibernateException {
    Long rightScopeId = null;
    Query query = this.session.createQuery(
        "select distinct rightscope.rightScopeId from com.js.system.vo.rolemanager.RightScopeVO rightscope join rightscope.right rights join rightscope.employee emp where (emp.empId =" + 
        
        userId + 
        ") and (rights.rightName ='维护') and (rights.rightType = '任务中心-任务分类')");
    List list = query.list();
    if (list.size() > 0) {
      Object o = list.get(0);
      rightScopeId = Long.valueOf(o.toString());
    } 
    return rightScopeId;
  }
  
  private String makeTaskClassCondition(Long userId, Long orgId, Long rightScopeId) throws NumberFormatException, HibernateException {
    Query q = this.session.createQuery("select distinct rightscope.rightScopeType,rightscope.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO rightscope where rightscope.rightScopeId = " + 
        
        rightScopeId);
    Object[] o = q.list().get(0);
    short rightScopeType = Short.parseShort(o[0].toString());
    String hqlCondition = "";
    if (rightScopeType == 0) {
      hqlCondition = "";
    } else if (rightScopeType == 1) {
      hqlCondition = "where (taskClass.createdEmp =" + userId + 
        ")";
    } else if (rightScopeType == 3) {
      hqlCondition = "where (taskClass.createdOrg =" + orgId + ")";
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
        hqlCondition = "where (taskClass.createdOrg in (" + 
          orgIds + "))";
      } 
    } else if (rightScopeType == 4) {
      String rightscopescope = o[1].toString();
      ConversionString cs = new ConversionString(rightscopescope);
      String userIdString = cs.getUserIdString();
      String orgIdString = cs.getOrgIdString();
      String groupIdString = cs.getGroupIdString();
      hqlCondition = makeConditionForTaskClass(hqlCondition, 
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
              " or taskClass.createdOrg in (" + 
              orgIdString + "))";
          } else {
            hqlCondition = "where" + hqlCondition;
          } 
        } else if (!orgIdString.equals("")) {
          hqlCondition = 
            "where (taskClass.createdOrg in (" + 
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
  
  private String makeConditionForTaskClass(String hqlCondition, String userIdString, String groupIdString) throws NumberFormatException, HibernateException {
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
            " taskClass.createdEmp in (" + 
            groupUserIdString + userIdString + ")";
        } else {
          hqlCondition = String.valueOf(hqlCondition) + 
            " taskClass.createdEmp in (" + 
            userIdString + ")";
        } 
      } else {
        hqlCondition = String.valueOf(hqlCondition) + 
          " taskClass.createdEmp in (" + 
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
          " taskClass.createdEmp in (" + 
          groupUserIdString + ")"; 
    } 
    return hqlCondition;
  }
  
  public List selectAllTaskClass(Long userId, Long orgId, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskClassVO> result = new ArrayList();
    try {
      begin();
      Long rightScopeId = taskClassRight(userId);
      String hqlCondition = "";
      if (rightScopeId != null) {
        hqlCondition = makeTaskClassCondition(userId, orgId, 
            rightScopeId);
        String retStr = "select taskClass from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass " + (
          (hqlCondition.length() > 0) ? (
          String.valueOf(hqlCondition) + (
          (domainId != null) ? (
          " and taskClass.classDomainId = " + domainId) : 
          "")) : (
          (domainId != null) ? (
          " where taskClass.classDomainId = " + 
          domainId) : "")) + 
          " order by taskClass.orderCode,taskClass.className";
        Query taskClassQuery = this.session.createQuery(retStr);
        taskClassQuery.setFirstResult((currentPage.intValue() - 1) * 
            volume.intValue());
        taskClassQuery.setMaxResults(volume.intValue());
        List queryList = taskClassQuery.list();
        Iterator<TaskClassPO> iterator = queryList.iterator();
        TaskClassPO taskClassPO = null;
        while (iterator != null && iterator.hasNext()) {
          taskClassPO = iterator.next();
          TaskClassVO taskClassVO = 
            (TaskClassVO)TransformObject.getInstance().transformObject(
              taskClassPO, TaskClassVO.class);
          Query emp = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
              taskClassPO.getCreatedEmp());
          StringBuffer empName = new StringBuffer();
          List list = emp.list();
          if (list != null && list.size() > 0) {
            Object o = list.get(0);
            empName.append(o.toString());
            taskClassVO.setEmpName(empName.toString());
            taskClassVO.setMaintenance(Boolean.TRUE);
            result.add(taskClassVO);
          } 
        } 
      } else {
        Query taskClassQuery = this.session.createQuery("select taskClass from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.createdEmp = " + 
            userId + (
            (domainId != null) ? (
            " and taskClass.classDomainId = " + domainId) : 
            "") + 
            " order by taskClass.orderCode,taskClass.className");
        taskClassQuery.setFirstResult((currentPage.intValue() - 1) * 
            volume.intValue());
        taskClassQuery.setMaxResults(volume.intValue());
        List queryList = taskClassQuery.list();
        Iterator<TaskClassPO> iterator = queryList.iterator();
        TaskClassPO taskClassPO = null;
        while (iterator != null && iterator.hasNext()) {
          taskClassPO = iterator.next();
          TaskClassVO taskClassVO = 
            (TaskClassVO)TransformObject.getInstance().transformObject(
              taskClassPO, TaskClassVO.class);
          Query emp = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
              taskClassPO.getCreatedEmp());
          StringBuffer empName = new StringBuffer();
          List list = emp.list();
          Object o = list.get(0);
          empName.append(o.toString());
          taskClassVO.setEmpName(empName.toString());
          taskClassVO.setMaintenance(Boolean.TRUE);
          result.add(taskClassVO);
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
  
  public Integer getTaskClassRecordCount(String hqlStr) throws HibernateException {
    return this.session.iterate("select count(*) from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where " + 
        hqlStr).next();
  }
  
  public Integer getTaskClassRecordCount(Long userId, Long orgId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      Long rightScopeId = taskClassRight(userId);
      String hqlCondition = "";
      if (rightScopeId != null) {
        hqlCondition = makeTaskClassCondition(userId, orgId, 
            rightScopeId);
        if (hqlCondition != null) {
          String retStr = "select count(taskClass) from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass " + (
            (hqlCondition.length() > 0) ? (
            String.valueOf(hqlCondition) + (
            (domainId != null) ? (
            " and taskClass.classDomainId = " + 
            domainId) : 
            "")) : (
            (domainId != null) ? (
            " where taskClass.classDomainId = " + 
            domainId) : ""));
          result = this.session.iterate(retStr).next();
        } 
      } else {
        result = this.session.iterate("select count(taskClass) from com.js.oa.scheme.taskcenter.po.TaskClassPO taskClass where taskClass.createdEmp = " + 
            
            userId)
          .next();
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
  
  public Boolean addTask(TaskVO task) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      if (task.getTaskPrincipal() == null || 
        task.getTaskPrincipal().equals(""))
        task.setTaskPrincipal(task.getCreatedEmp()); 
      String idStrings = "$0";
      TaskPO taskPOO = task.getTaskPO();
      TaskPO taskPO = (TaskPO)TransformObject.getInstance()
        .transformObject(task, TaskPO.class);
      if (taskPO.getTaskParentId() != null && taskPO.getTaskParentId().longValue() != 0L) {
        TaskPO parentPo = (TaskPO)this.session.load(TaskPO.class, taskPO.getTaskParentId());
        idStrings = parentPo.getTaskIdStrings();
      } 
      taskPO.setMeetingId(taskPOO.getMeetingId());
      taskPO.setTaskHasPass(new Integer(1));
      taskPO.setTaskHasJunior(new Integer(0));
      taskPO.setTaskOrderCode(new Integer(0));
      taskPO.setTaskFinishRate(new Integer(0));
      Long taskId = (Long)this.session.save(taskPO);
      taskPO.setTaskIdStrings(String.valueOf(idStrings) + "$" + taskId);
      String userids = "";
      String useridss = "";
      if (taskPO.getTaskPrincipal() != null)
        userids = String.valueOf(userids) + taskPO.getTaskPrincipal() + ","; 
      if (taskPO.getTaskChecker() != null)
        userids = String.valueOf(userids) + taskPO.getTaskChecker() + ","; 
      UserBD userBD = new UserBD();
      if (taskPO.getTaskJoinedEmp() != null) {
        String empid = StringSplit.splitWith(taskPO.getTaskJoinedEmp(), "$", "*@");
        if (empid != null && !"".equals(empid)) {
          empid = empid.substring(1, empid.length() - 1);
          empid = empid.replace("$$", ",");
          userids = String.valueOf(userids) + empid + ",";
        } 
        String orgIds = "";
        orgIds = StringSplit.splitWith(taskPO.getTaskJoinedEmp(), "*", "@$");
        if (orgIds != null && !"".equals(orgIds)) {
          orgIds = orgIds.substring(1, orgIds.length() - 1);
          orgIds = orgIds.replace("**", ",");
          List<E> list = new ArrayList();
          list = userBD.selectEmpIdByOrgIds(orgIds);
          if (!list.isEmpty() && list.size() > 0)
            for (int i = 0; i < list.size(); i++)
              userids = String.valueOf(userids) + list.get(i).toString() + ",";  
        } 
      } 
      if (!"".equals(userids))
        userids = userids.substring(0, userids.trim().length() - 1); 
      List<Object[]> list1 = new ArrayList();
      list1 = userBD.selectEmpIdsAndOrgIds(userids);
      if (!list1.isEmpty() && list1.size() > 0)
        for (int i = 0; i < list1.size(); i++) {
          Object[] obj = list1.get(i);
          if (!obj[0].toString().equals(String.valueOf(taskPO.getCreatedEmp())))
            useridss = String.valueOf(useridss) + obj[0].toString() + ","; 
        }  
      if (!"".equals(useridss)) {
        useridss = useridss.substring(0, useridss.trim().length() - 1);
        String title = String.valueOf(taskPO.getCreatedEmpName()) + "安排了" + taskPO.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskId + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskPO.getTaskEndTime());
        calendar.set(11, 23);
        calendar.set(12, 59);
        RemindUtil.sendMessageToUsers(title, url, useridss, "NewTask", new Date(), calendar.getTime(), taskPO.getCreatedEmpName(), taskId);
      } 
      if (taskPOO != null) {
        Set set = taskPOO.getTaskAccessory();
        HashSet hs = new HashSet();
        taskPO.setTaskAccessory(hs);
        Iterator<TaskAccessoryPO> iterator = set.iterator();
        TaskAccessoryPO taskAccessoryPO = null;
        while (iterator != null && iterator.hasNext()) {
          taskAccessoryPO = iterator.next();
          taskAccessoryPO.setTask(taskPO);
          taskPO.getTaskAccessory().add(taskAccessoryPO);
          this.session.save(taskAccessoryPO);
        } 
      } 
      if (task.getParentIdString().equals("-1")) {
        taskPO.setTaskBaseId(taskPO.getTaskId());
        taskPO.setParentIdString("$" + taskPO.getTaskId() + "$");
        taskPO.setTaskParentId(new Long(0L));
        addTaskList(taskPO);
      } else {
        TaskPO parentTask = (TaskPO)this.session.load(TaskPO.class, 
            taskPO.getTaskParentId());
        taskPO.setTaskBaseId(parentTask.getTaskBaseId());
        taskPO.setTaskParentId(parentTask.getTaskId());
        taskPO.setParentIdString(parentTask.getParentIdString());
        parentTask.setTaskHasJunior(new Integer(parentTask
              .getTaskHasJunior().intValue() + 1));
        taskPO.setTaskOrderCode(new Integer(parentTask.getTaskOrderCode()
              .intValue() + 1));
        if (taskPO.getCreatedEmp().equals(parentTask.getTaskPrincipal())) {
          if (taskPO.getIsArranged().intValue() == 0) {
            addTaskExec(taskPO, taskPO.getTaskPrincipal());
          } else {
            addTaskList(taskPO);
          } 
        } else {
          addTaskList(taskPO);
        } 
      } 
      String temppric = "0";
      if (task.getTaskPrincipal() == null || 
        task.getTaskPrincipal().equals("")) {
        temppric = task.getCreatedEmp().toString();
      } else {
        temppric = task.getTaskPrincipal().toString();
      } 
      String temps = taskPO.getTaskJoineOrg();
      if (!"".equals(temps))
        if (",".equals(temps.substring(temps.length() - 1, temps.length()))) {
          temps = String.valueOf(temps) + temppric;
        } else {
          temps = String.valueOf(temps) + "," + temppric;
        }  
      if (!"".equals(temps)) {
        String domainId = taskPO.getTaskDomainId().toString();
        String[] tmp = temps.split(",");
        for (int i = 0; i < tmp.length; i++) {
          TaskRemindPO taskremindpo = new TaskRemindPO();
          taskremindpo.setEmpId(Long.valueOf(tmp[i]));
          taskremindpo.setTaskId(taskId);
          taskremindpo.setRemindDomainId(Long.valueOf(domainId));
          this.session.save(taskremindpo);
        } 
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception ex) {
      System.out.println("add Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void addTaskList(TaskPO taskPO) throws NumberFormatException, HibernateException {
    addMultiTaskExecs(taskPO);
    addTaskExec(taskPO, taskPO.getTaskPrincipal());
  }
  
  private void addMultiTaskExecs(TaskPO taskPO) throws HibernateException, NumberFormatException {
    if (taskPO.getTaskJoinedEmp() != null && 
      !taskPO.getTaskJoinedEmp().equals("")) {
      String joineMember = (new ConversionString(taskPO.getTaskJoinedEmp()))
        .getUserIdString();
      String userId = taskPO.getTaskJoineOrg();
      if (joineMember != null && !"".equals(joineMember) && userId != null && 
        !"".equals(userId))
        joineMember = String.valueOf(joineMember) + "," + userId; 
      if ("".equals(joineMember) && userId != null && !"".equals(userId))
        joineMember = userId; 
      HashMap<Object, Object> hm = new HashMap<Object, Object>();
      if (joineMember != null && !"".equals(joineMember)) {
        String[] memberIds = joineMember.split(",");
        for (int i = 0; i < memberIds.length; i++) {
          if (!"".equals(memberIds[i])) {
            Long memberId = Long.valueOf(memberIds[i]);
            if (!hm.containsKey(memberId))
              if (!memberId.equals(taskPO.getTaskPrincipal())) {
                addTaskExec(taskPO, memberId);
                hm.put(memberId, "");
              }  
          } 
        } 
      } 
      hm.clear();
    } 
  }
  
  private void addTaskExec(TaskPO taskPO, Long memberId) throws HibernateException {
    TaskExecPO taskExec = new TaskExecPO();
    Set set = new HashSet();
    taskExec.setTask(taskPO);
    taskExec.setExecEmpId(memberId);
    if (memberId.equals(taskPO.getTaskPrincipal())) {
      taskExec.setIsPrincipal(new Integer(1));
    } else {
      taskExec.setIsPrincipal(new Integer(0));
    } 
    taskExec.setTaskReports(set);
    this.session.save(taskExec);
  }
  
  public Boolean modifyTask(TaskVO task, String userName, String userId) throws Exception {
    Boolean result = new Boolean(false);
    String taskId = task.getTaskId().toString();
    try {
      begin();
      TaskPO taskPO = (TaskPO)this.session.load(TaskPO.class, task.getTaskId());
      updateTask(task, taskPO);
      String userids = "";
      String useridss = "";
      if (taskPO.getTaskPrincipal() != null)
        userids = String.valueOf(userids) + taskPO.getTaskPrincipal() + ","; 
      if (taskPO.getTaskChecker() != null)
        userids = String.valueOf(userids) + taskPO.getTaskChecker() + ","; 
      UserBD userBD = new UserBD();
      if (taskPO.getTaskJoinedEmp() != null) {
        String empid = StringSplit.splitWith(taskPO.getTaskJoinedEmp(), "$", "*@");
        if (empid != null && !"".equals(empid)) {
          empid = empid.substring(1, empid.length() - 1);
          empid = empid.replace("$$", ",");
          userids = String.valueOf(userids) + empid + ",";
        } 
        String orgIds = "";
        orgIds = StringSplit.splitWith(taskPO.getTaskJoinedEmp(), "*", "@$");
        if (orgIds != null && !"".equals(orgIds)) {
          orgIds = orgIds.substring(1, orgIds.length() - 1);
          orgIds = orgIds.replace("**", ",");
          List<E> list = new ArrayList();
          list = userBD.selectEmpIdByOrgIds(orgIds);
          if (!list.isEmpty() && list.size() > 0)
            for (int i = 0; i < list.size(); i++)
              userids = String.valueOf(userids) + list.get(i).toString() + ",";  
        } 
      } 
      if (!"".equals(userids))
        userids = userids.substring(0, userids.trim().length() - 1); 
      List<Object[]> list1 = new ArrayList();
      list1 = userBD.selectEmpIdsAndOrgIds(userids);
      if (!list1.isEmpty() && list1.size() > 0)
        for (int i = 0; i < list1.size(); i++) {
          Object[] obj = list1.get(i);
          useridss = String.valueOf(useridss) + obj[0].toString() + ",";
        }  
      if (useridss.indexOf(taskPO.getCreatedEmp() + ",") == -1)
        useridss = String.valueOf(useridss) + taskPO.getCreatedEmp() + ","; 
      if (useridss.indexOf(String.valueOf(userId) + ",") >= 0)
        useridss = useridss.replaceAll(String.valueOf(userId) + ",", ""); 
      if (!"".equals(useridss)) {
        useridss = useridss.substring(0, useridss.trim().length() - 1);
        String title = String.valueOf(userName) + "修订了" + taskPO.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskId + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskPO.getTaskEndTime());
        calendar.set(11, 23);
        calendar.set(12, 59);
        RemindUtil.sendMessageToUsers(title, url, useridss, "NewTask", new Date(), calendar.getTime(), taskPO.getCreatedEmpName(), new Long(taskId));
      } 
      TaskPO taskPOO = task.getTaskPO();
      taskPO.setRelProjectId(task.getRelProjectId());
      String temps = taskPO.getTaskJoineOrg();
      if (temps != null && !"".equals(temps)) {
        String tmps = temps.substring(temps.length() - 1, temps.length());
        if (",".equals(tmps))
          temps = temps.substring(0, temps.length() - 1); 
      } 
      if (!"".equals(temps))
        this.session.delete(
            "from com.js.oa.scheme.taskcenter.po.TaskPO aaa  where aaa.taskId in (" + 
            temps + ")"); 
      Iterator<TaskAccessoryPO> it = this.session.createQuery("from com.js.oa.scheme.taskcenter.po.TaskAccessoryPO accessory where accessory.task.taskId=" + 
          task.getTaskId()).iterate();
      while (it.hasNext()) {
        TaskAccessoryPO taskAccessory = it.next();
        this.session.delete(taskAccessory);
      } 
      Set<TaskAccessoryPO> accessorySet = taskPOO.getTaskAccessory();
      Set<TaskAccessoryPO> setTemp = new HashSet();
      it = accessorySet.iterator();
      while (it.hasNext()) {
        TaskAccessoryPO taskAccessory = it.next();
        this.session.save(taskAccessory);
        setTemp.add(taskAccessory);
      } 
      taskPO.setTaskAccessory(setTemp);
      Set taskExecs = taskPO.getTaskExecs();
      if (taskExecs != null && taskExecs.size() > 0) {
        Iterator<TaskExecPO> itor = taskExecs.iterator();
        while (itor.hasNext()) {
          TaskExecPO taskExec = itor.next();
          taskExec.setTask(taskPO);
          taskExec.setExecEmpId(taskPO.getTaskPrincipal());
          taskExec.setIsPrincipal(new Integer(1));
          Set set = new HashSet();
          taskExec.setTaskReports(set);
          this.session.saveOrUpdate(taskExec);
        } 
      } 
      if (taskPO.getTaskJoinedEmp() != null) {
        String desStr = (new ConversionString(task.getTaskJoinedEmp()))
          .getUserIdString();
        if (!desStr.equals(""))
          addMultiTaskExecs(taskPO); 
      } 
      temps = taskPO.getTaskJoineOrg();
      if (temps != null && !"".equals(temps)) {
        String domainId = taskPO.getTaskDomainId().toString();
        String[] tmp = temps.split(",");
        int y = 0;
        for (int i = 0; i < tmp.length; i++) {
          TaskExecPO taskExecPO = new TaskExecPO();
          taskExecPO.setExecEmpId(Long.valueOf(tmp[i]));
          taskExecPO.setTask(taskPO);
          if (taskPO.getTaskPrincipal().toString().equals(tmp[i]))
            y = 1; 
          taskExecPO.setIsPrincipal(new Integer(y));
          taskExecPO.setTeDomainId(Long.valueOf(domainId));
          this.session.save(taskExecPO);
        } 
      } 
      if (temps != null && !"".equals(temps)) {
        String domainId = taskPO.getTaskDomainId().toString();
        String[] tmp = temps.split(",");
        for (int i = 0; i < tmp.length; i++) {
          TaskRemindPO taskremindpo = new TaskRemindPO();
          taskremindpo.setEmpId(Long.valueOf(tmp[i]));
          taskremindpo.setTaskId(new Long(taskId));
          taskremindpo.setRemindDomainId(Long.valueOf(domainId));
          this.session.save(taskremindpo);
        } 
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception ex) {
      System.out.println("modify Task Exception: " + ex.getMessage());
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private void deleteTaskExec(Long taskId) throws HibernateException {
    this.session.delete(
        "from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec where taskExec.task.taskId =" + 
        taskId);
  }
  
  private void deleteTaskExec(Long taskId, Long empId) throws HibernateException {
    this.session.delete(
        "from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec where taskExec.task.taskId =" + 
        taskId + 
        " and taskExec.execEmpId = " + empId);
  }
  
  private void updateTask(TaskVO task, TaskPO taskPO) throws HibernateException {
    taskPO.setMeetingId(task.getTaskPO().getMeetingId());
    if (task.getCreatedEmp() != null && 
      !task.getCreatedEmp().equals(taskPO.getCreatedEmp()))
      taskPO.setCreatedEmp(task.getCreatedEmp()); 
    if (task.getCreatedOrg() != null && 
      !task.getCreatedOrg().equals(taskPO.getCreatedOrg()))
      taskPO.setCreatedOrg(task.getCreatedOrg()); 
    if (task.getTaskAwakeTime() != null && 
      !task.getTaskAwakeTime().equals(taskPO.getTaskAwakeTime()))
      taskPO.setTaskAwakeTime(task.getTaskAwakeTime()); 
    if (task.getTaskBaseId() != null && 
      !task.getTaskBaseId().equals(taskPO.getTaskBaseId()))
      taskPO.setTaskBaseId(task.getTaskBaseId()); 
    if (task.getTaskBeginTime() != null && 
      !task.getTaskBeginTime().equals(taskPO.getTaskBeginTime()))
      taskPO.setTaskBeginTime(task.getTaskBeginTime()); 
    if (task.getTaskChecker() != null && 
      !task.getTaskChecker().equals(taskPO.getTaskChecker()) && 
      taskPO.getTaskHasPass().equals(new Integer(1))) {
      deleteTaskExec(taskPO.getTaskId(), taskPO.getTaskChecker());
      taskPO.setTaskChecker(task.getTaskChecker());
      taskPO.setTaskCheckerName(task.getTaskCheckerName());
    } 
    if (task.getTaskCreatedTime() != null && 
      !task.getTaskCreatedTime().equals(taskPO.getTaskCreatedTime()))
      taskPO.setTaskCreatedTime(task.getTaskCreatedTime()); 
    if (task.getTaskEndTime() != null && 
      !task.getTaskEndTime().equals(taskPO.getTaskEndTime()))
      taskPO.setTaskEndTime(task.getTaskEndTime()); 
    if (taskPO.getIsArranged().intValue() != 0 && task.getTaskJoinedEmp() != null) {
      if (!task.getTaskJoinedEmp().equals(taskPO.getTaskJoinedEmp())) {
        taskPO.setTaskJoinedEmp(task.getTaskJoinedEmp());
        taskPO.setTaskJoinedEmpName(task.getTaskJoinedEmpName());
      } 
      if (!task.getTaskJoineOrg().equals(taskPO.getTaskJoineOrg())) {
        String joineOrgId = (task.getTaskJoinedEmp() == null) ? "0" : (
          new ConversionString(task.getTaskJoinedEmp()))
          .getOrgIdString();
        TaskBD taskBD = new TaskBD();
        String taskJoineOrg = taskBD.selectUserIds(joineOrgId);
        taskPO.setTaskJoineOrg(taskJoineOrg);
        taskPO.setTaskJoineOrg(task.getTaskJoineOrg());
        taskPO.setTaskJoinOrgName(task.getTaskJoinOrgName());
      } 
    } 
    if (task.getTaskPrincipal() != null && 
      !task.getTaskPrincipal().equals(taskPO.getTaskPrincipal())) {
      deleteTaskExec(taskPO.getTaskId(), taskPO.getTaskPrincipal());
      this.session.flush();
      taskPO.setTaskPrincipal(task.getTaskPrincipal());
      taskPO.setTaskPrincipalName(task.getTaskPrincipalName());
      addTaskExec(taskPO, taskPO.getTaskPrincipal());
    } 
    if (task.getTaskPriority() != null && 
      !task.getTaskPriority().equals(taskPO.getTaskPriority()))
      taskPO.setTaskPriority(task.getTaskPriority()); 
    if (task.getTaskSn() != null && 
      !task.getTaskSn().equals(taskPO.getTaskSn()))
      taskPO.setTaskSn(task.getTaskSn()); 
    if (task.getTaskStatus() != null && 
      !task.getTaskStatus().equals(taskPO.getTaskStatus())) {
      taskPO.setTaskStatus(task.getTaskStatus());
      if (task.getTaskStatus().intValue() == 2)
        taskPO.setTaskFinishRate(new Integer(100)); 
    } 
    if (task.getTaskTitle() != null && 
      !task.getTaskTitle().equals(taskPO.getTaskTitle()))
      taskPO.setTaskTitle(task.getTaskTitle()); 
    if (task.getTaskType() != null && 
      !task.getTaskType().equals(taskPO.getTaskType()))
      taskPO.setTaskType(task.getTaskType()); 
    if (task.getTaskDescription() != null && 
      !task.getTaskDescription().equals(taskPO.getTaskDescription()))
      taskPO.setTaskDescription(task.getTaskDescription()); 
    taskPO.setTaskAppend(task.getTaskAppend());
  }
  
  private Boolean checkTaskJoinEmp(String srcStr, String desStr) {
    Boolean result = new Boolean(true);
    String[] srcStrIds = srcStr.split(",");
    String[] desStrIds = desStr.split(",");
    HashMap<Object, Object> map = new HashMap<Object, Object>();
    if (srcStrIds.length > desStrIds.length) {
      int i;
      for (i = 0; i < desStrIds.length; i++)
        map.put(desStrIds[i], Boolean.FALSE); 
      for (i = 0; i < srcStrIds.length; i++) {
        if (!map.containsKey(srcStrIds[i])) {
          result = Boolean.FALSE;
          break;
        } 
      } 
    } else {
      int i;
      for (i = 0; i < srcStrIds.length; i++)
        map.put(srcStrIds[i], Boolean.FALSE); 
      for (i = 0; i < desStrIds.length; i++) {
        if (!map.containsKey(desStrIds[i])) {
          result = Boolean.FALSE;
          break;
        } 
      } 
    } 
    return result;
  }
  
  public Boolean deleteTask(Long taskId) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      Query taskQuery = this.session.createQuery("select task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
          taskId + 
          "$%' or task.taskId=" + 
          taskId + 
          
          " or (task.taskBaseId = " + taskId + " and task.taskParentId != 0) or task.taskParentId = " + taskId + " or task.taskParentId in (select task0.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task0 where task0.taskParentId = " + taskId + ")");
      List taskList = taskQuery.list();
      Iterator<TaskPO> iterator = taskList.iterator();
      TaskPO taskPO1 = (TaskPO)this.session.load(TaskPO.class, taskId);
      EmployeeVO EmployeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, taskPO1.getTaskPrincipal());
      String userids = "";
      String useridss = "";
      if (taskPO1.getTaskPrincipal() != null)
        userids = String.valueOf(userids) + taskPO1.getTaskPrincipal() + ","; 
      if (taskPO1.getTaskChecker() != null)
        userids = String.valueOf(userids) + taskPO1.getTaskChecker() + ","; 
      UserBD userBD = new UserBD();
      if (taskPO1.getTaskJoinedEmp() != null) {
        String empid = StringSplit.splitWith(taskPO1.getTaskJoinedEmp(), "$", "*@");
        if (empid != null && !"".equals(empid)) {
          empid = empid.substring(1, empid.length() - 1);
          empid = empid.replace("$$", ",");
          userids = String.valueOf(userids) + empid + ",";
        } 
        String orgIds = "";
        orgIds = StringSplit.splitWith(taskPO1.getTaskJoinedEmp(), "*", "@$");
        if (orgIds != null && !"".equals(orgIds)) {
          orgIds = orgIds.substring(1, orgIds.length() - 1);
          orgIds = orgIds.replace("**", ",");
          List<E> list3 = new ArrayList();
          list3 = userBD.selectEmpIdByOrgIds(orgIds);
          if (!list3.isEmpty() && list3.size() > 0)
            for (int i = 0; i < list3.size(); i++)
              userids = String.valueOf(userids) + list3.get(i).toString() + ",";  
        } 
      } 
      if (!"".equals(userids))
        userids = userids.substring(0, userids.trim().length() - 1); 
      List<Object[]> list4 = new ArrayList();
      list4 = userBD.selectEmpIdsAndOrgIds(userids);
      if (!list4.isEmpty() && list4.size() > 0)
        for (int i = 0; i < list4.size(); i++) {
          Object[] obj = list4.get(i);
          if (!obj[0].toString().equals(String.valueOf(String.valueOf(taskPO1.getTaskPrincipal()))))
            useridss = String.valueOf(useridss) + obj[0].toString() + ","; 
        }  
      if (!"".equals(useridss)) {
        useridss = useridss.substring(0, useridss.trim().length() - 1);
        String title = String.valueOf(EmployeeVO.getEmpName()) + "删除了" + taskPO1.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskId + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2050, 12, 12);
        RemindUtil.sendMessageToUsersNoURL(title, url, useridss, "TaskDel", new Date(), calendar.getTime());
        MessagesBD messagesBD = new MessagesBD();
        messagesBD.changeTaskStatus(String.valueOf(taskId), "");
      } 
      TaskPO taskPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskPO = iterator.next();
        Hibernate.initialize(taskPO.getTaskExecs());
        Connection conn = null;
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          Statement stmt = conn.createStatement();
          stmt.executeUpdate("delete from oa_taskreportaccessory where report_id in(select report_id from oa_taskreport where task_id=" + taskId + ")");
          stmt.executeUpdate("delete from oa_taskreport where task_id=" + taskId);
          stmt.close();
          conn.close();
        } catch (Exception err) {
          if (conn != null)
            try {
              conn.close();
            } catch (Exception exx) {
              exx.printStackTrace();
            }  
          err.printStackTrace();
        } 
        Query query2 = this.session.createQuery(
            "select history from com.js.oa.scheme.taskcenter.po.TaskHistoryPO history where history.taskId=" + taskId);
        List list2 = query2.list();
        Iterator<TaskHistoryPO> iter2 = list2.iterator();
        while (iter2 != null && iter2.hasNext()) {
          TaskHistoryPO po = iter2.next();
          this.session.delete(po);
        } 
        Query query3 = this.session.createQuery(
            "select remind from com.js.oa.scheme.taskcenter.po.TaskRemindPO remind where remind.taskId=" + 
            taskId);
        List list3 = query3.list();
        Iterator<TaskRemindPO> iter3 = list3.iterator();
        while (iter3 != null && iter3.hasNext()) {
          TaskRemindPO po = iter3.next();
          this.session.delete(po);
        } 
        Set s1 = taskPO.getTaskExecs();
        if (s1 != null && s1.size() > 0) {
          Iterator<TaskExecPO> it1 = s1.iterator();
          while (it1.hasNext()) {
            TaskExecPO p1 = it1.next();
            this.session.delete(p1);
          } 
        } 
        this.session.delete(taskPO);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean cancelTask(Long taskId, Long domainId, String cancelReason) throws Exception {
    Boolean result = new Boolean(false);
    try {
      begin();
      Query query = this.session.createQuery(
          "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId = " + 
          taskId + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      List list = query.list();
      if (list == null || list.size() == 0)
        query = this.session.createQuery(
            "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId = " + 
            taskId + 
            " or task.taskParentId = " + taskId + " or task.taskParentId in (select task0.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task0 where task0.taskParentId = " + taskId + "))" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : "")); 
      list = query.list();
      Iterator<TaskPO> iterator = list.iterator();
      TaskPO task = null;
      while (iterator != null && iterator.hasNext()) {
        task = iterator.next();
        task.setTaskStatus(new Integer(4));
        task.setTaskCancelReason(cancelReason);
        this.session.update(task);
      } 
      TaskPO taskPO = (TaskPO)this.session.load(TaskPO.class, taskId);
      String userids = "";
      String useridss = "";
      if (taskPO.getTaskPrincipal() != null)
        userids = String.valueOf(userids) + taskPO.getTaskPrincipal() + ","; 
      if (taskPO.getTaskChecker() != null)
        userids = String.valueOf(userids) + taskPO.getTaskChecker() + ","; 
      UserBD userBD = new UserBD();
      if (taskPO.getTaskJoinedEmp() != null) {
        String empid = StringSplit.splitWith(taskPO.getTaskJoinedEmp(), "$", "*@");
        if (empid != null && !"".equals(empid)) {
          empid = empid.substring(1, empid.length() - 1);
          empid = empid.replace("$$", ",");
          userids = String.valueOf(userids) + empid + ",";
        } 
        String orgIds = "";
        orgIds = StringSplit.splitWith(taskPO.getTaskJoinedEmp(), "*", "@$");
        if (orgIds != null && !"".equals(orgIds)) {
          orgIds = orgIds.substring(1, orgIds.length() - 1);
          orgIds = orgIds.replace("**", ",");
          List<E> list2 = new ArrayList();
          list2 = userBD.selectEmpIdByOrgIds(orgIds);
          if (!list2.isEmpty() && list2.size() > 0)
            for (int i = 0; i < list2.size(); i++)
              userids = String.valueOf(userids) + list2.get(i).toString() + ",";  
        } 
      } 
      if (!"".equals(userids))
        userids = userids.substring(0, userids.trim().length() - 1); 
      List<Object[]> list1 = new ArrayList();
      list1 = userBD.selectEmpIdsAndOrgIds(userids);
      if (!list1.isEmpty() && list1.size() > 0)
        for (int i = 0; i < list1.size(); i++) {
          Object[] obj = list1.get(i);
          if (!obj[0].toString().equals(String.valueOf(taskPO.getCreatedEmp())))
            useridss = String.valueOf(useridss) + obj[0].toString() + ","; 
        }  
      if (!"".equals(useridss)) {
        useridss = useridss.substring(0, useridss.trim().length() - 1);
        String title = String.valueOf(taskPO.getCreatedEmpName()) + "取消了" + taskPO.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskId + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2050, 12, 12);
        RemindUtil.sendMessageToUsers(title, url, useridss, "TaskCancel", new Date(), calendar.getTime(), taskPO.getCreatedEmpName(), taskId);
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("cancel Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean taskPass(Long userId, Long taskId, Integer status, Long domainId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      Query query = this.session.createQuery("select task from com.js.oa.scheme.taskcenter.po.TaskPO task join task.taskExecs taskExecs  where task.taskId = " + 
          taskId + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : 
          ""));
      TaskPO task = query.iterate().next();
      task.setTaskHasPass(status);
      if (status.intValue() == 1) {
        addMultiTaskExecs(task);
        if (!task.getTaskPrincipal().equals(task.getCreatedEmp()))
          addTaskExec(task, task.getTaskPrincipal()); 
      } else {
        task.setTaskStatus(new Integer(4));
        TaskPO parent = (TaskPO)this.session.load(TaskPO.class, 
            task.getTaskParentId());
        parent.setTaskHasJunior(new Integer(parent.getTaskHasJunior()
              .intValue() - 1));
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("task Pass Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectPrincipalTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    return selectPrincipalTask(userId, currentPage, volume, domainId, type, sortType, (String)null);
  }
  
  public List selectPrincipalTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType, String toUserId) throws HibernateException {
    // Byte code:
    //   0: ldc ''
    //   2: astore #8
    //   4: new java/util/ArrayList
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: astore #9
    //   13: aload_0
    //   14: invokevirtual begin : ()V
    //   17: ldc ''
    //   19: astore #10
    //   21: aload #7
    //   23: ifnull -> 848
    //   26: new java/lang/StringBuffer
    //   29: dup
    //   30: new java/lang/StringBuilder
    //   33: dup
    //   34: ldc_w 'select distinct po from com.js.oa.scheme.taskcenter.po.TaskPO po where po.createdEmp='
    //   37: invokespecial <init> : (Ljava/lang/String;)V
    //   40: aload_1
    //   41: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   44: ldc_w ' and po.taskDomainId='
    //   47: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   50: aload #4
    //   52: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   55: invokevirtual toString : ()Ljava/lang/String;
    //   58: invokespecial <init> : (Ljava/lang/String;)V
    //   61: astore #11
    //   63: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   66: ldc_w 'mysql'
    //   69: invokevirtual indexOf : (Ljava/lang/String;)I
    //   72: iflt -> 108
    //   75: aload #11
    //   77: new java/lang/StringBuilder
    //   80: dup
    //   81: ldc_w ' and (concat('$',replace(po.taskJoineOrg,',','$$'),'$') like '%$'
    //   84: invokespecial <init> : (Ljava/lang/String;)V
    //   87: aload #7
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: ldc_w '$%' '
    //   95: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: invokevirtual toString : ()Ljava/lang/String;
    //   101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   104: pop
    //   105: goto -> 138
    //   108: aload #11
    //   110: new java/lang/StringBuilder
    //   113: dup
    //   114: ldc_w ' and (JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$',JSDB.FN_replace(po.taskJoineOrg,',','$$')),'$') like '%$'
    //   117: invokespecial <init> : (Ljava/lang/String;)V
    //   120: aload #7
    //   122: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: ldc_w '$%' '
    //   128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual toString : ()Ljava/lang/String;
    //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   137: pop
    //   138: aload #11
    //   140: new java/lang/StringBuilder
    //   143: dup
    //   144: ldc_w ' or po.taskJoinedEmp like '%$'
    //   147: invokespecial <init> : (Ljava/lang/String;)V
    //   150: aload #7
    //   152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: ldc_w '$%' or po.taskPrincipal='
    //   158: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   161: aload #7
    //   163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: ldc_w ' or po.taskChecker='
    //   169: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: aload #7
    //   174: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   177: ldc_w ' )'
    //   180: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   183: invokevirtual toString : ()Ljava/lang/String;
    //   186: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   189: pop
    //   190: aload #5
    //   192: ldc ''
    //   194: invokevirtual equals : (Ljava/lang/Object;)Z
    //   197: ifeq -> 222
    //   200: ldc ''
    //   202: aload #6
    //   204: invokevirtual equals : (Ljava/lang/Object;)Z
    //   207: ifeq -> 222
    //   210: aload #11
    //   212: ldc_w ' order by po.taskCreatedTime desc '
    //   215: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   218: pop
    //   219: goto -> 257
    //   222: aload #11
    //   224: new java/lang/StringBuilder
    //   227: dup
    //   228: ldc_w ' order by po.'
    //   231: invokespecial <init> : (Ljava/lang/String;)V
    //   234: aload #5
    //   236: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: ldc_w ' '
    //   242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   245: aload #6
    //   247: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   250: invokevirtual toString : ()Ljava/lang/String;
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   256: pop
    //   257: aload #11
    //   259: invokevirtual toString : ()Ljava/lang/String;
    //   262: astore #8
    //   264: aload_0
    //   265: getfield session : Lnet/sf/hibernate/Session;
    //   268: aload #8
    //   270: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   275: astore #12
    //   277: aload #12
    //   279: aload_2
    //   280: invokevirtual intValue : ()I
    //   283: iconst_1
    //   284: isub
    //   285: aload_3
    //   286: invokevirtual intValue : ()I
    //   289: imul
    //   290: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   295: pop
    //   296: aload #12
    //   298: aload_3
    //   299: invokevirtual intValue : ()I
    //   302: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   307: pop
    //   308: aload #12
    //   310: invokeinterface list : ()Ljava/util/List;
    //   315: invokeinterface iterator : ()Ljava/util/Iterator;
    //   320: astore #13
    //   322: aconst_null
    //   323: astore #14
    //   325: goto -> 830
    //   328: aload #13
    //   330: invokeinterface next : ()Ljava/lang/Object;
    //   335: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   338: astore #14
    //   340: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   343: aload #14
    //   345: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   348: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   351: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   354: astore #15
    //   356: aload #15
    //   358: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   361: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   364: aload #15
    //   366: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   369: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   372: aload #15
    //   374: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   377: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   380: aload #15
    //   382: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   385: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   388: aload #14
    //   390: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   393: aload_1
    //   394: invokevirtual equals : (Ljava/lang/Object;)Z
    //   397: ifne -> 412
    //   400: aload #14
    //   402: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   405: aload_1
    //   406: invokevirtual equals : (Ljava/lang/Object;)Z
    //   409: ifeq -> 428
    //   412: aload #15
    //   414: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   417: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   420: aload #15
    //   422: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   425: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   428: aload #15
    //   430: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   433: new java/lang/Integer
    //   436: dup
    //   437: iconst_1
    //   438: invokespecial <init> : (I)V
    //   441: invokevirtual equals : (Ljava/lang/Object;)Z
    //   444: ifeq -> 479
    //   447: aload #14
    //   449: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   452: aload_1
    //   453: invokevirtual equals : (Ljava/lang/Object;)Z
    //   456: ifne -> 471
    //   459: aload #14
    //   461: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   464: aload_1
    //   465: invokevirtual equals : (Ljava/lang/Object;)Z
    //   468: ifeq -> 479
    //   471: aload #15
    //   473: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   476: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   479: aload #14
    //   481: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   484: ifnull -> 521
    //   487: aload #14
    //   489: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   492: new java/lang/StringBuilder
    //   495: dup
    //   496: ldc_w '$'
    //   499: invokespecial <init> : (Ljava/lang/String;)V
    //   502: aload_1
    //   503: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   506: ldc_w '$'
    //   509: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   512: invokevirtual toString : ()Ljava/lang/String;
    //   515: invokevirtual indexOf : (Ljava/lang/String;)I
    //   518: ifge -> 600
    //   521: aload #14
    //   523: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   526: ifnull -> 588
    //   529: new java/lang/StringBuilder
    //   532: dup
    //   533: ldc_w ','
    //   536: invokespecial <init> : (Ljava/lang/String;)V
    //   539: aload #14
    //   541: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   544: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: ldc_w ','
    //   550: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   553: invokevirtual toString : ()Ljava/lang/String;
    //   556: new java/lang/StringBuilder
    //   559: dup
    //   560: ldc_w ','
    //   563: invokespecial <init> : (Ljava/lang/String;)V
    //   566: aload_1
    //   567: invokevirtual toString : ()Ljava/lang/String;
    //   570: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   573: ldc_w ','
    //   576: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   579: invokevirtual toString : ()Ljava/lang/String;
    //   582: invokevirtual indexOf : (Ljava/lang/String;)I
    //   585: ifge -> 600
    //   588: aload #14
    //   590: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   593: aload_1
    //   594: invokevirtual equals : (Ljava/lang/Object;)Z
    //   597: ifeq -> 625
    //   600: ldc_w '1'
    //   603: aload #14
    //   605: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   608: invokevirtual toString : ()Ljava/lang/String;
    //   611: invokevirtual equals : (Ljava/lang/Object;)Z
    //   614: ifeq -> 625
    //   617: aload #15
    //   619: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   622: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   625: aload #15
    //   627: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   630: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   633: aload #14
    //   635: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   638: aload_1
    //   639: invokevirtual equals : (Ljava/lang/Object;)Z
    //   642: ifeq -> 653
    //   645: aload #15
    //   647: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   650: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   653: aload #15
    //   655: ldc_w '0'
    //   658: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   661: aload #14
    //   663: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   666: ifnull -> 682
    //   669: ldc ''
    //   671: aload #14
    //   673: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   676: invokevirtual equals : (Ljava/lang/Object;)Z
    //   679: ifeq -> 703
    //   682: aload #14
    //   684: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   687: ifnull -> 820
    //   690: ldc ''
    //   692: aload #14
    //   694: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   697: invokevirtual equals : (Ljava/lang/Object;)Z
    //   700: ifne -> 820
    //   703: aload #14
    //   705: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   708: ifnull -> 745
    //   711: aload #14
    //   713: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   716: new java/lang/StringBuilder
    //   719: dup
    //   720: ldc_w '$'
    //   723: invokespecial <init> : (Ljava/lang/String;)V
    //   726: aload_1
    //   727: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   730: ldc_w '$'
    //   733: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   736: invokevirtual toString : ()Ljava/lang/String;
    //   739: invokevirtual indexOf : (Ljava/lang/String;)I
    //   742: ifge -> 812
    //   745: aload #14
    //   747: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   750: ifnull -> 820
    //   753: new java/lang/StringBuilder
    //   756: dup
    //   757: ldc_w ','
    //   760: invokespecial <init> : (Ljava/lang/String;)V
    //   763: aload #14
    //   765: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   768: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   771: ldc_w ','
    //   774: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   777: invokevirtual toString : ()Ljava/lang/String;
    //   780: new java/lang/StringBuilder
    //   783: dup
    //   784: ldc_w ','
    //   787: invokespecial <init> : (Ljava/lang/String;)V
    //   790: aload_1
    //   791: invokevirtual toString : ()Ljava/lang/String;
    //   794: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   797: ldc_w ','
    //   800: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   803: invokevirtual toString : ()Ljava/lang/String;
    //   806: invokevirtual indexOf : (Ljava/lang/String;)I
    //   809: iflt -> 820
    //   812: aload #15
    //   814: ldc_w '1'
    //   817: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   820: aload #9
    //   822: aload #15
    //   824: invokeinterface add : (Ljava/lang/Object;)Z
    //   829: pop
    //   830: aload #13
    //   832: ifnull -> 2226
    //   835: aload #13
    //   837: invokeinterface hasNext : ()Z
    //   842: ifne -> 328
    //   845: goto -> 2226
    //   848: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   851: ldc_w 'db2'
    //   854: invokevirtual indexOf : (Ljava/lang/String;)I
    //   857: iflt -> 919
    //   860: new java/lang/StringBuilder
    //   863: dup
    //   864: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   867: invokespecial <init> : (Ljava/lang/String;)V
    //   870: aload_1
    //   871: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   874: ldc_w ' or task.taskJoinedEmp like '%$'
    //   877: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   880: aload_1
    //   881: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   884: ldc_w '$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   887: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   890: aload_1
    //   891: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   894: ldc_w ',' )>0 '
    //   897: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   900: ldc_w ') and task.taskDomainId = '
    //   903: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   906: aload #4
    //   908: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   911: invokevirtual toString : ()Ljava/lang/String;
    //   914: astore #10
    //   916: goto -> 1046
    //   919: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   922: ldc_w 'mysql'
    //   925: invokevirtual indexOf : (Ljava/lang/String;)I
    //   928: iflt -> 990
    //   931: new java/lang/StringBuilder
    //   934: dup
    //   935: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   938: invokespecial <init> : (Ljava/lang/String;)V
    //   941: aload_1
    //   942: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   945: ldc_w ' or task.taskJoinedEmp like '%$'
    //   948: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   951: aload_1
    //   952: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   955: ldc_w '$%' or   concat(',', task.taskJoineOrg,',') like  '%,'
    //   958: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   961: aload_1
    //   962: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   965: ldc_w ',%''
    //   968: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   971: ldc_w ') and task.taskDomainId = '
    //   974: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   977: aload #4
    //   979: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   982: invokevirtual toString : ()Ljava/lang/String;
    //   985: astore #10
    //   987: goto -> 1046
    //   990: new java/lang/StringBuilder
    //   993: dup
    //   994: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   997: invokespecial <init> : (Ljava/lang/String;)V
    //   1000: aload_1
    //   1001: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1004: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1007: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1010: aload_1
    //   1011: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1014: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   1017: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1020: aload_1
    //   1021: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1024: ldc_w ',%''
    //   1027: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1030: ldc_w ') and task.taskDomainId = '
    //   1033: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1036: aload #4
    //   1038: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1041: invokevirtual toString : ()Ljava/lang/String;
    //   1044: astore #10
    //   1046: ldc ''
    //   1048: aload #5
    //   1050: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1053: ifne -> 1066
    //   1056: ldc ''
    //   1058: aload #6
    //   1060: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1063: ifeq -> 1306
    //   1066: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1069: ldc_w 'db2'
    //   1072: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1075: iflt -> 1154
    //   1078: new java/lang/StringBuilder
    //   1081: dup
    //   1082: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   1085: invokespecial <init> : (Ljava/lang/String;)V
    //   1088: aload #10
    //   1090: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1093: ldc_w ')or(task.taskPrincipal = '
    //   1096: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1099: aload_1
    //   1100: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1103: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1109: aload_1
    //   1110: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1113: ldc_w '$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   1116: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1119: aload_1
    //   1120: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1123: ldc_w ',' )>0  ) '
    //   1126: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1129: ldc_w ') and task.taskFinishRate<100 and task.taskStatus<>4 and task.taskDomainId ='
    //   1132: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1135: aload #4
    //   1137: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1140: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   1143: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1146: invokevirtual toString : ()Ljava/lang/String;
    //   1149: astore #8
    //   1151: goto -> 1585
    //   1154: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1157: ldc_w 'mysql'
    //   1160: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1163: iflt -> 1236
    //   1166: new java/lang/StringBuilder
    //   1169: dup
    //   1170: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   1173: invokespecial <init> : (Ljava/lang/String;)V
    //   1176: aload #10
    //   1178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1181: ldc_w ')or(task.taskPrincipal = '
    //   1184: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1187: aload_1
    //   1188: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1191: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1194: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1197: aload_1
    //   1198: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1201: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   1204: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1207: aload_1
    //   1208: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1211: ldc_w ',%' ) ) and task.taskFinishRate<100 and task.taskStatus<>4 and task.taskDomainId ='
    //   1214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1217: aload #4
    //   1219: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1222: ldc_w ' order by task.stickie desc,task.parentIdString desc,task.taskCreatedTime'
    //   1225: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1228: invokevirtual toString : ()Ljava/lang/String;
    //   1231: astore #8
    //   1233: goto -> 1585
    //   1236: new java/lang/StringBuilder
    //   1239: dup
    //   1240: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   1243: invokespecial <init> : (Ljava/lang/String;)V
    //   1246: aload #10
    //   1248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1251: ldc_w ')or(task.taskPrincipal = '
    //   1254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1257: aload_1
    //   1258: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1261: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1264: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1267: aload_1
    //   1268: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1271: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   1274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1277: aload_1
    //   1278: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1281: ldc_w ',%' ) ) and task.taskFinishRate<100 and task.taskStatus<>4 and task.taskDomainId ='
    //   1284: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1287: aload #4
    //   1289: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1292: ldc_w ' order by task.stickie desc, task.parentIdString desc,task.taskCreatedTime'
    //   1295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1298: invokevirtual toString : ()Ljava/lang/String;
    //   1301: astore #8
    //   1303: goto -> 1585
    //   1306: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1309: ldc_w 'db2'
    //   1312: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1315: iflt -> 1404
    //   1318: new java/lang/StringBuilder
    //   1321: dup
    //   1322: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   1325: invokespecial <init> : (Ljava/lang/String;)V
    //   1328: aload #10
    //   1330: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1333: ldc_w ')or(task.taskPrincipal = '
    //   1336: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1339: aload_1
    //   1340: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1343: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1346: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1349: aload_1
    //   1350: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1353: ldc_w '$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   1356: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1359: aload_1
    //   1360: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1363: ldc_w ',' )>0   ) ) and task.taskFinishRate<100 and task.taskStatus<>4 and task.taskDomainId ='
    //   1366: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1369: aload #4
    //   1371: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1374: ldc_w ' order by task.'
    //   1377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1380: aload #5
    //   1382: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1385: ldc_w ' '
    //   1388: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1391: aload #6
    //   1393: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1396: invokevirtual toString : ()Ljava/lang/String;
    //   1399: astore #8
    //   1401: goto -> 1585
    //   1404: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1407: ldc_w 'mysql'
    //   1410: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1413: iflt -> 1502
    //   1416: new java/lang/StringBuilder
    //   1419: dup
    //   1420: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   1423: invokespecial <init> : (Ljava/lang/String;)V
    //   1426: aload #10
    //   1428: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1431: ldc_w ')or(task.taskPrincipal = '
    //   1434: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1437: aload_1
    //   1438: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1441: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1444: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1447: aload_1
    //   1448: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1451: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   1454: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1457: aload_1
    //   1458: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1461: ldc_w ',%' ) ) and task.taskFinishRate<100 and task.taskStatus<>4 and task.taskDomainId ='
    //   1464: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1467: aload #4
    //   1469: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1472: ldc_w ' order by task.'
    //   1475: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1478: aload #5
    //   1480: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1483: ldc_w ' '
    //   1486: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1489: aload #6
    //   1491: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1494: invokevirtual toString : ()Ljava/lang/String;
    //   1497: astore #8
    //   1499: goto -> 1585
    //   1502: new java/lang/StringBuilder
    //   1505: dup
    //   1506: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   1509: invokespecial <init> : (Ljava/lang/String;)V
    //   1512: aload #10
    //   1514: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1517: ldc_w ')or(task.taskPrincipal = '
    //   1520: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1523: aload_1
    //   1524: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1527: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1530: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1533: aload_1
    //   1534: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1537: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   1540: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1543: aload_1
    //   1544: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1547: ldc_w ',%' ) ) and task.taskFinishRate<100 and task.taskStatus<>4 and task.taskDomainId ='
    //   1550: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1553: aload #4
    //   1555: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1558: ldc_w ' order by task.'
    //   1561: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1564: aload #5
    //   1566: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1569: ldc_w ' '
    //   1572: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1575: aload #6
    //   1577: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1580: invokevirtual toString : ()Ljava/lang/String;
    //   1583: astore #8
    //   1585: aload_0
    //   1586: getfield session : Lnet/sf/hibernate/Session;
    //   1589: aload #8
    //   1591: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1596: astore #11
    //   1598: aload #11
    //   1600: aload_2
    //   1601: invokevirtual intValue : ()I
    //   1604: iconst_1
    //   1605: isub
    //   1606: aload_3
    //   1607: invokevirtual intValue : ()I
    //   1610: imul
    //   1611: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   1616: pop
    //   1617: aload #11
    //   1619: aload_3
    //   1620: invokevirtual intValue : ()I
    //   1623: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   1628: pop
    //   1629: aload #11
    //   1631: invokeinterface list : ()Ljava/util/List;
    //   1636: invokeinterface iterator : ()Ljava/util/Iterator;
    //   1641: astore #12
    //   1643: aconst_null
    //   1644: astore #13
    //   1646: goto -> 2151
    //   1649: aload #12
    //   1651: invokeinterface next : ()Ljava/lang/Object;
    //   1656: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   1659: astore #13
    //   1661: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1664: aload #13
    //   1666: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   1669: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1672: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   1675: astore #14
    //   1677: aload #14
    //   1679: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1682: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   1685: aload #14
    //   1687: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1690: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1693: aload #14
    //   1695: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1698: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1701: aload #14
    //   1703: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1706: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   1709: aload #13
    //   1711: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1714: aload_1
    //   1715: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1718: ifne -> 1733
    //   1721: aload #13
    //   1723: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1726: aload_1
    //   1727: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1730: ifeq -> 1749
    //   1733: aload #14
    //   1735: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1738: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1741: aload #14
    //   1743: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1746: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   1749: aload #14
    //   1751: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   1754: new java/lang/Integer
    //   1757: dup
    //   1758: iconst_1
    //   1759: invokespecial <init> : (I)V
    //   1762: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1765: ifeq -> 1800
    //   1768: aload #13
    //   1770: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1773: aload_1
    //   1774: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1777: ifne -> 1792
    //   1780: aload #13
    //   1782: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1785: aload_1
    //   1786: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1789: ifeq -> 1800
    //   1792: aload #14
    //   1794: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1797: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   1800: aload #13
    //   1802: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1805: ifnull -> 1842
    //   1808: aload #13
    //   1810: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1813: new java/lang/StringBuilder
    //   1816: dup
    //   1817: ldc_w '$'
    //   1820: invokespecial <init> : (Ljava/lang/String;)V
    //   1823: aload_1
    //   1824: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1827: ldc_w '$'
    //   1830: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1833: invokevirtual toString : ()Ljava/lang/String;
    //   1836: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1839: ifge -> 1921
    //   1842: aload #13
    //   1844: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1847: ifnull -> 1909
    //   1850: new java/lang/StringBuilder
    //   1853: dup
    //   1854: ldc_w ','
    //   1857: invokespecial <init> : (Ljava/lang/String;)V
    //   1860: aload #13
    //   1862: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1865: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1868: ldc_w ','
    //   1871: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1874: invokevirtual toString : ()Ljava/lang/String;
    //   1877: new java/lang/StringBuilder
    //   1880: dup
    //   1881: ldc_w ','
    //   1884: invokespecial <init> : (Ljava/lang/String;)V
    //   1887: aload_1
    //   1888: invokevirtual toString : ()Ljava/lang/String;
    //   1891: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1894: ldc_w ','
    //   1897: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1900: invokevirtual toString : ()Ljava/lang/String;
    //   1903: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1906: ifge -> 1921
    //   1909: aload #13
    //   1911: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1914: aload_1
    //   1915: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1918: ifeq -> 1946
    //   1921: ldc_w '1'
    //   1924: aload #13
    //   1926: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   1929: invokevirtual toString : ()Ljava/lang/String;
    //   1932: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1935: ifeq -> 1946
    //   1938: aload #14
    //   1940: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1943: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1946: aload #14
    //   1948: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1951: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1954: aload #13
    //   1956: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1959: aload_1
    //   1960: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1963: ifeq -> 1974
    //   1966: aload #14
    //   1968: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1971: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1974: aload #14
    //   1976: ldc_w '0'
    //   1979: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   1982: aload #13
    //   1984: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1987: ifnull -> 2003
    //   1990: ldc ''
    //   1992: aload #13
    //   1994: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1997: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2000: ifeq -> 2024
    //   2003: aload #13
    //   2005: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2008: ifnull -> 2141
    //   2011: ldc ''
    //   2013: aload #13
    //   2015: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2018: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2021: ifne -> 2141
    //   2024: aload #13
    //   2026: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   2029: ifnull -> 2066
    //   2032: aload #13
    //   2034: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   2037: new java/lang/StringBuilder
    //   2040: dup
    //   2041: ldc_w '$'
    //   2044: invokespecial <init> : (Ljava/lang/String;)V
    //   2047: aload_1
    //   2048: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2051: ldc_w '$'
    //   2054: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2057: invokevirtual toString : ()Ljava/lang/String;
    //   2060: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2063: ifge -> 2133
    //   2066: aload #13
    //   2068: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2071: ifnull -> 2141
    //   2074: new java/lang/StringBuilder
    //   2077: dup
    //   2078: ldc_w ','
    //   2081: invokespecial <init> : (Ljava/lang/String;)V
    //   2084: aload #13
    //   2086: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2089: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2092: ldc_w ','
    //   2095: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2098: invokevirtual toString : ()Ljava/lang/String;
    //   2101: new java/lang/StringBuilder
    //   2104: dup
    //   2105: ldc_w ','
    //   2108: invokespecial <init> : (Ljava/lang/String;)V
    //   2111: aload_1
    //   2112: invokevirtual toString : ()Ljava/lang/String;
    //   2115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2118: ldc_w ','
    //   2121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2124: invokevirtual toString : ()Ljava/lang/String;
    //   2127: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2130: iflt -> 2141
    //   2133: aload #14
    //   2135: ldc_w '1'
    //   2138: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   2141: aload #9
    //   2143: aload #14
    //   2145: invokeinterface add : (Ljava/lang/Object;)Z
    //   2150: pop
    //   2151: aload #12
    //   2153: ifnull -> 2226
    //   2156: aload #12
    //   2158: invokeinterface hasNext : ()Z
    //   2163: ifne -> 1649
    //   2166: goto -> 2226
    //   2169: astore #10
    //   2171: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   2174: new java/lang/StringBuilder
    //   2177: dup
    //   2178: ldc_w 'select Principal Task Exception: '
    //   2181: invokespecial <init> : (Ljava/lang/String;)V
    //   2184: aload #10
    //   2186: invokevirtual getMessage : ()Ljava/lang/String;
    //   2189: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2192: invokevirtual toString : ()Ljava/lang/String;
    //   2195: invokevirtual println : (Ljava/lang/String;)V
    //   2198: aload #10
    //   2200: athrow
    //   2201: astore #16
    //   2203: aload_0
    //   2204: getfield session : Lnet/sf/hibernate/Session;
    //   2207: invokeinterface close : ()Ljava/sql/Connection;
    //   2212: pop
    //   2213: aload_0
    //   2214: aconst_null
    //   2215: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   2218: aload_0
    //   2219: aconst_null
    //   2220: putfield session : Lnet/sf/hibernate/Session;
    //   2223: aload #16
    //   2225: athrow
    //   2226: aload_0
    //   2227: getfield session : Lnet/sf/hibernate/Session;
    //   2230: invokeinterface close : ()Ljava/sql/Connection;
    //   2235: pop
    //   2236: aload_0
    //   2237: aconst_null
    //   2238: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   2241: aload_0
    //   2242: aconst_null
    //   2243: putfield session : Lnet/sf/hibernate/Session;
    //   2246: aload #9
    //   2248: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #2026	-> 0
    //   #2027	-> 4
    //   #2029	-> 13
    //   #2031	-> 17
    //   #2032	-> 21
    //   #2033	-> 26
    //   #2034	-> 63
    //   #2035	-> 75
    //   #2038	-> 108
    //   #2041	-> 138
    //   #2042	-> 190
    //   #2043	-> 210
    //   #2045	-> 222
    //   #2047	-> 257
    //   #2048	-> 264
    //   #2049	-> 277
    //   #2050	-> 285
    //   #2049	-> 290
    //   #2051	-> 296
    //   #2052	-> 308
    //   #2053	-> 322
    //   #2054	-> 325
    //   #2055	-> 328
    //   #2056	-> 340
    //   #2057	-> 343
    //   #2056	-> 351
    //   #2058	-> 356
    //   #2059	-> 364
    //   #2060	-> 372
    //   #2061	-> 380
    //   #2063	-> 388
    //   #2064	-> 400
    //   #2065	-> 412
    //   #2066	-> 420
    //   #2069	-> 428
    //   #2070	-> 447
    //   #2071	-> 471
    //   #2075	-> 479
    //   #2076	-> 487
    //   #2078	-> 521
    //   #2079	-> 529
    //   #2081	-> 588
    //   #2082	-> 600
    //   #2083	-> 617
    //   #2086	-> 625
    //   #2087	-> 633
    //   #2088	-> 645
    //   #2090	-> 653
    //   #2091	-> 661
    //   #2092	-> 669
    //   #2093	-> 682
    //   #2094	-> 690
    //   #2095	-> 703
    //   #2096	-> 711
    //   #2097	-> 713
    //   #2098	-> 745
    //   #2099	-> 753
    //   #2101	-> 812
    //   #2104	-> 820
    //   #2054	-> 830
    //   #2107	-> 848
    //   #2109	-> 860
    //   #2110	-> 870
    //   #2111	-> 874
    //   #2112	-> 880
    //   #2113	-> 884
    //   #2114	-> 900
    //   #2109	-> 911
    //   #2116	-> 919
    //   #2118	-> 931
    //   #2119	-> 941
    //   #2120	-> 945
    //   #2121	-> 951
    //   #2122	-> 955
    //   #2123	-> 971
    //   #2118	-> 982
    //   #2126	-> 990
    //   #2127	-> 1000
    //   #2128	-> 1004
    //   #2129	-> 1010
    //   #2130	-> 1014
    //   #2131	-> 1030
    //   #2126	-> 1041
    //   #2136	-> 1046
    //   #2138	-> 1066
    //   #2140	-> 1078
    //   #2142	-> 1088
    //   #2143	-> 1093
    //   #2144	-> 1103
    //   #2145	-> 1113
    //   #2146	-> 1129
    //   #2147	-> 1135
    //   #2148	-> 1140
    //   #2140	-> 1146
    //   #2150	-> 1154
    //   #2152	-> 1166
    //   #2154	-> 1176
    //   #2155	-> 1181
    //   #2156	-> 1191
    //   #2157	-> 1201
    //   #2158	-> 1217
    //   #2159	-> 1222
    //   #2152	-> 1228
    //   #2163	-> 1236
    //   #2165	-> 1246
    //   #2166	-> 1251
    //   #2167	-> 1261
    //   #2168	-> 1271
    //   #2169	-> 1287
    //   #2170	-> 1292
    //   #2163	-> 1298
    //   #2174	-> 1306
    //   #2176	-> 1318
    //   #2178	-> 1328
    //   #2179	-> 1333
    //   #2180	-> 1343
    //   #2181	-> 1353
    //   #2182	-> 1369
    //   #2183	-> 1391
    //   #2176	-> 1396
    //   #2185	-> 1404
    //   #2187	-> 1416
    //   #2189	-> 1426
    //   #2190	-> 1431
    //   #2191	-> 1441
    //   #2192	-> 1451
    //   #2193	-> 1467
    //   #2194	-> 1489
    //   #2187	-> 1494
    //   #2198	-> 1502
    //   #2200	-> 1512
    //   #2201	-> 1517
    //   #2202	-> 1527
    //   #2203	-> 1537
    //   #2204	-> 1553
    //   #2205	-> 1575
    //   #2198	-> 1580
    //   #2209	-> 1585
    //   #2210	-> 1598
    //   #2211	-> 1606
    //   #2210	-> 1611
    //   #2212	-> 1617
    //   #2213	-> 1629
    //   #2214	-> 1643
    //   #2215	-> 1646
    //   #2216	-> 1649
    //   #2217	-> 1661
    //   #2218	-> 1664
    //   #2217	-> 1672
    //   #2219	-> 1677
    //   #2220	-> 1685
    //   #2221	-> 1693
    //   #2222	-> 1701
    //   #2224	-> 1709
    //   #2225	-> 1721
    //   #2226	-> 1733
    //   #2227	-> 1741
    //   #2230	-> 1749
    //   #2231	-> 1768
    //   #2232	-> 1792
    //   #2236	-> 1800
    //   #2237	-> 1808
    //   #2239	-> 1842
    //   #2240	-> 1850
    //   #2242	-> 1909
    //   #2243	-> 1921
    //   #2244	-> 1938
    //   #2247	-> 1946
    //   #2248	-> 1954
    //   #2249	-> 1966
    //   #2251	-> 1974
    //   #2252	-> 1982
    //   #2253	-> 1990
    //   #2254	-> 2003
    //   #2255	-> 2011
    //   #2256	-> 2024
    //   #2257	-> 2032
    //   #2258	-> 2034
    //   #2259	-> 2066
    //   #2260	-> 2074
    //   #2262	-> 2133
    //   #2265	-> 2141
    //   #2215	-> 2151
    //   #2269	-> 2169
    //   #2270	-> 2171
    //   #2271	-> 2184
    //   #2270	-> 2195
    //   #2272	-> 2198
    //   #2273	-> 2201
    //   #2274	-> 2203
    //   #2275	-> 2213
    //   #2276	-> 2218
    //   #2278	-> 2223
    //   #2274	-> 2226
    //   #2275	-> 2236
    //   #2276	-> 2241
    //   #2279	-> 2246
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	2249	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	2249	1	userId	Ljava/lang/Long;
    //   0	2249	2	currentPage	Ljava/lang/Integer;
    //   0	2249	3	volume	Ljava/lang/Integer;
    //   0	2249	4	domainId	Ljava/lang/Long;
    //   0	2249	5	type	Ljava/lang/String;
    //   0	2249	6	sortType	Ljava/lang/String;
    //   0	2249	7	toUserId	Ljava/lang/String;
    //   4	2245	8	strsql	Ljava/lang/String;
    //   13	2236	9	result	Ljava/util/List;
    //   21	2148	10	Sql1	Ljava/lang/String;
    //   63	785	11	hql	Ljava/lang/StringBuffer;
    //   277	571	12	query	Lnet/sf/hibernate/Query;
    //   322	526	13	iterator	Ljava/util/Iterator;
    //   325	523	14	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   356	474	15	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   1598	568	11	query	Lnet/sf/hibernate/Query;
    //   1643	523	12	iterator	Ljava/util/Iterator;
    //   1646	520	13	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   1677	474	14	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   2171	30	10	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   13	2166	2169	net/sf/hibernate/HibernateException
    //   13	2201	2201	finally
  }
  
  public Integer getPrincipalTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    return getPrincipalTaskRecordCount(userId, domainId, (String)null);
  }
  
  public Integer getPrincipalTaskRecordCount(Long userId, Long domainId, String toUserId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String Sql2 = "";
      String Sql1 = "";
      if (toUserId != null) {
        StringBuffer hql = new StringBuffer("select distinct po.taskId from com.js.oa.scheme.taskcenter.po.TaskPO po where po.createdEmp=" + userId + " and po.taskDomainId=" + domainId);
        if (databaseType.indexOf("mysql") >= 0) {
          hql.append(" and (concat('$',replace(po.taskJoineOrg,',','$$'),'$') like '%$" + toUserId + "$%' ");
        } else {
          hql.append(" and (JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$',JSDB.FN_replace(po.taskJoineOrg,',','$$')),'$') like '%$" + toUserId + "$%' ");
        } 
        hql.append(" or po.taskJoinedEmp like '%$" + toUserId + "$%' or po.taskPrincipal=" + toUserId + " or po.taskChecker=" + toUserId + " ) ");
        hql.append(" order by po.taskCreatedTime desc ");
        Sql2 = hql.toString();
        Query query = this.session.createQuery(Sql2);
        result = Integer.valueOf(query.list().get(0).toString());
      } else {
        if (databaseType.indexOf("db2") >= 0) {
          Sql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4))  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = " + 
            userId + 
            " or task.taskJoinedEmp like '%$" + 
            userId + 
            "$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0  " + 
            " ) and task.taskDomainId = " + domainId;
        } else if (databaseType.indexOf("mysql") >= 0) {
          Sql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4))  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = " + 
            userId + 
            " or task.taskJoinedEmp like '%$" + 
            userId + 
            "$%' or  concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%' " + 
            " ) and task.taskDomainId = " + domainId;
        } else {
          Sql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4))  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = " + 
            userId + 
            " or task.taskJoinedEmp like '%$" + 
            userId + 
            "$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' " + 
            " ) and task.taskDomainId = " + domainId;
        } 
        if (databaseType.indexOf("db2") >= 0) {
          Sql2 = "select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in (" + 
            
            Sql1 + 
            ") or (task.taskPrincipal = " + 
            userId + 
            " or task.taskJoinedEmp like '%$" + 
            userId + 
            "$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0  )" + 
            ") and task.taskFinishRate<100 and task.taskStatus<>4" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : "");
        } else if (databaseType.indexOf("mysql") >= 0) {
          Sql2 = "select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in (" + 
            
            Sql1 + 
            ") or (task.taskPrincipal = " + 
            userId + 
            " or task.taskJoinedEmp like '%$" + 
            userId + 
            "$%' or  concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%'   )" + 
            ") and task.taskFinishRate<100 and task.taskStatus<>4" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : "");
        } else {
          Sql2 = "select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in (" + 
            
            Sql1 + 
            ") or (task.taskPrincipal = " + 
            userId + 
            " or task.taskJoinedEmp like '%$" + 
            userId + 
            "$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%'   )" + 
            ") and task.taskFinishRate<100 and task.taskStatus<>4" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : "");
        } 
        Query query = this.session.createQuery(Sql2);
        result = Integer.valueOf(query.list().get(0).toString());
      } 
    } catch (HibernateException ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectCompleteTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String strsql = "";
    StringBuffer taskIds = new StringBuffer();
    try {
      begin();
      Query joinTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskJoinedEmp like '%" + 
          
          userId + "%'" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      Iterator<E> iterator = joinTask.iterate();
      String taskid = "";
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
            taskid + "%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      Query principalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal = " + 
          
          userId + (
          
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      iterator = principalTask.iterate();
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
            taskid + "%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      String taskId = taskIds.toString();
      if (taskId != null && !taskId.trim().equals("")) {
        taskId = taskId.substring(0, taskId.length() - 1);
        if ("".equals(type) || "".equals(sortType)) {
          if (databaseType.indexOf("db2") >= 0) {
            strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
              taskId + ") and (task.createdEmp=" + userId + 
              " or task.taskPrincipal = " + userId + 
              " or task.taskJoinedEmp like '%$" + userId + 
              "$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0    ) and task.taskStatus=2 and task.taskDomainId =" + 
              domainId + 
              " order by task.parentIdString desc,task.taskCreatedTime";
          } else if (databaseType.indexOf("mysql") >= 0) {
            strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
              taskId + ") and (task.createdEmp=" + userId + 
              " or task.taskPrincipal = " + userId + 
              " or task.taskJoinedEmp like '%$" + userId + 
              "$%' or concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%'  )  and task.taskStatus=2 and task.taskDomainId =" + 
              domainId + 
              " order by task.parentIdString desc,task.taskCreatedTime";
          } else {
            strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
              taskId + ") and (task.createdEmp=" + userId + 
              " or task.taskPrincipal = " + userId + 
              " or task.taskJoinedEmp like '%$" + userId + 
              "$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%'  ) and task.taskStatus=2 and task.taskDomainId =" + 
              domainId + 
              " order by task.parentIdString desc,task.taskCreatedTime";
          } 
        } else if (databaseType.indexOf("db2") >= 0) {
          strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and (task.createdEmp=" + userId + 
            " or task.taskPrincipal = " + userId + 
            " or task.taskJoinedEmp like '%$" + userId + 
            "$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0   ) and task.taskStatus=2 and task.taskDomainId =" + 
            domainId + " order by task." + type + " " + 
            sortType;
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and (task.createdEmp=" + userId + 
            " or task.taskPrincipal = " + userId + 
            " or task.taskJoinedEmp like '%$" + userId + 
            "$%' or  concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%'  ) and task.taskStatus=2 and task.taskDomainId =" + 
            domainId + " order by task." + type + " " + 
            sortType;
        } else {
          strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and (task.createdEmp=" + userId + 
            " or task.taskPrincipal = " + userId + 
            " or task.taskJoinedEmp like '%$" + userId + 
            "$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%'  ) and task.taskStatus=2 and task.taskDomainId =" + 
            domainId + " order by task." + type + " " + 
            sortType;
        } 
        Query query = this.session.createQuery(strsql);
        query.setFirstResult((currentPage.intValue() - 1) * 
            volume.intValue());
        query.setMaxResults(volume.intValue());
        iterator = query.list().iterator();
        TaskPO taskPO = null;
        int ii = 0;
        while (iterator != null && iterator.hasNext()) {
          ii++;
          taskPO = (TaskPO)iterator.next();
          TaskVO task = (TaskVO)TransformObject.getInstance()
            .transformObject(taskPO, TaskVO.class);
          task.setCanCancel(Boolean.FALSE);
          task.setCanDelete(Boolean.FALSE);
          task.setCanReport(Boolean.FALSE);
          task.setMaintenance(Boolean.FALSE);
          task.setTaskTypeShow("0");
          if (taskPO.getTaskJoinedEmp() != null && 
            !"".equals(taskPO.getTaskJoinedEmp())) {
            if (taskPO.getTaskJoinedEmp().indexOf("$" + userId + 
                "$") >= 0)
              task.setTaskTypeShow("1"); 
          } else if (taskPO.getTaskJoineOrg() != null && 
            !"".equals(taskPO.getTaskJoineOrg()) && (
            "," + taskPO.getTaskJoineOrg() + ",").indexOf("," + userId.toString() + ",") >= 0) {
            task.setTaskTypeShow("1");
          } 
          result.add(task);
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("select Complete Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getCompleteTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      StringBuffer taskIds = new StringBuffer();
      Query joinTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskJoinedEmp like '%" + 
          
          userId + "%'" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      Iterator<E> iterator = joinTask.iterate();
      String taskid = "";
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
            taskid + "%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      Query principalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal = " + 
          
          userId + (
          
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      iterator = principalTask.iterate();
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
            taskid + "%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      String taskId = taskIds.toString();
      if (taskId != null && !taskId.trim().equals("")) {
        taskId = taskId.substring(0, taskId.length() - 1);
        result = this.session.iterate("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in(" + 
            taskId + 
            ") and task.taskStatus=2" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + 
            domainId) : "")).next();
      } 
    } catch (HibernateException ex) {
      System.out.println("select Complete Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectJoinTask(Long userId, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    try {
      begin();
      StringBuffer taskIds = new StringBuffer();
      String Sql1 = "";
      if (databaseType.indexOf("db2") >= 0) {
        Sql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0 ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : "");
      } else if (databaseType.indexOf("mysql") >= 0) {
        Sql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or  concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%' ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : "");
      } else {
        Sql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' )  " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "");
      } 
      Query joinQuery = this.session.createQuery(Sql1);
      Iterator<E> iterator = joinQuery.iterate();
      String taskid = "";
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
            taskid + "$%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      Query childPrincipalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal =" + 
          userId + 
          " and task.taskParentId<>0" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      iterator = childPrincipalTask.iterate();
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
            taskid + "$%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      String taskId = taskIds.toString();
      if (taskId != null && !taskId.trim().equals("")) {
        taskId = taskId.substring(0, taskId.length() - 1);
        Query q = this.session.createQuery("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and not task.taskStatus in (2,4) and task.taskFinishRate<100 and ((task.taskHasPass<>0)or(task.taskHasPass=0 and task.createdEmp =" + 
            userId + 
            ")) " + (
            (domainId != null) ? (
            " and task.taskDomainId = " + 
            domainId) : 
            "") + 
            " order by task.parentIdString desc");
        q.setFirstResult((currentPage.intValue() - 1) * 
            volume.intValue());
        q.setMaxResults(volume.intValue());
        iterator = q.list().iterator();
        TaskPO taskPO = null;
        while (iterator != null && iterator.hasNext()) {
          taskPO = (TaskPO)iterator.next();
          TaskVO task = (TaskVO)TransformObject.getInstance()
            .transformObject(taskPO, TaskVO.class);
          task.setCanCancel(Boolean.FALSE);
          task.setCanDelete(Boolean.FALSE);
          task.setCanReport(Boolean.FALSE);
          task.setMaintenance(Boolean.FALSE);
          if (task.getTaskStatus().equals(new Integer(0))) {
            task.setMaintenance(Boolean.TRUE);
            if (taskPO.getCreatedEmp().equals(userId))
              task.setCanDelete(Boolean.TRUE); 
          } 
          if (taskPO.getCreatedEmp().equals(userId) || (
            taskPO.getTaskJoinedEmp() != null && 
            taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0) || 
            taskPO.getTaskPrincipal().equals(userId))
            task.setCanReport(Boolean.TRUE); 
          task.setCanNew(Boolean.FALSE);
          if ((taskPO.getTaskJoinedEmp() != null && 
            taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0) || 
            taskPO.getCreatedEmp().equals(userId) || 
            taskPO.getTaskPrincipal().equals(userId))
            task.setCanNew(Boolean.TRUE); 
          result.add(task);
        } 
      } 
    } catch (HibernateException ex) {
      System.out.println("select Join Task Exception: " + ex.getMessage());
      throw new EJBException(ex);
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getJoinTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      StringBuffer taskIds = new StringBuffer();
      Query joinQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskJoinedEmp like '%" + 
          
          userId + "%'" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      Iterator<E> iterator = joinQuery.iterate();
      String taskid = "";
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
            taskid + "%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      Query childPrincipalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal =" + 
          userId + 
          " and task.taskParentId<>0" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      iterator = childPrincipalTask.iterate();
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
            taskid + "%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      String taskId = taskIds.toString();
      if (taskId != null && !taskId.trim().equals("")) {
        taskId = taskId.substring(0, taskId.length() - 1);
        result = this.session.iterate("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and not task.taskStatus in (2,4) and task.taskFinishRate<100 and ((task.taskHasPass<>0)or(task.taskHasPass=0 and task.createdEmp =" + 
            userId + "))" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + 
            domainId) : "")).next();
      } 
    } catch (HibernateException ex) {
      System.out.println("select Joined Task  record count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectAuditingTask(Long userId, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task join task.taskExecs taskExecs where taskExecs.isPrincipal = 1 and taskExecs.execEmpId = " + 
        
        userId + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      Query query = this.session.createQuery("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskParentId in (" + 
          
          tempHql + 
          ") and task.taskHasPass = 0  " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "") + 
          " order by task.parentIdString");
      query.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      query.setMaxResults(volume.intValue());
      List list = query.list();
      Iterator<TaskPO> iterator = list.iterator();
      TaskPO taskPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskPO = iterator.next();
        TaskVO task = (TaskVO)TransformObject.getInstance()
          .transformObject(taskPO, TaskVO.class);
        task.setCanCancel(Boolean.FALSE);
        task.setCanDelete(Boolean.FALSE);
        task.setCanReport(Boolean.FALSE);
        task.setMaintenance(Boolean.FALSE);
        if (task.getTaskStatus().equals(new Integer(0)))
          task.setMaintenance(Boolean.TRUE); 
        result.add(task);
      } 
    } catch (HibernateException ex) {
      System.out.println("select Auditing Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getAuditingTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String tempSql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task join task.taskExecs taskExecs where taskExecs.isPrincipal = 1 and taskExecs.execEmpId = " + 
        
        userId + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      Query query = this.session.createQuery("select distinct count(*) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskParentId in (" + 
          
          tempSql + 
          ") and task.taskHasPass = 0" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("select Complete Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchTask(Long userId, String[] parameters, Integer status, Integer currentPage, Integer volume, Long domainId) throws Exception {
    return searchTask(userId, parameters, status, currentPage, volume, domainId, (String)null);
  }
  
  public List searchTask(Long userId, String[] parameters, Integer status, Integer currentPage, Integer volume, Long domainId, String toUserId) throws Exception {
    List result = new ArrayList();
    try {
      begin();
      int searchStatus = status.intValue();
      switch (searchStatus) {
        case 0:
          result = searchPrincipalTask(userId, parameters, currentPage, 
              volume, domainId, toUserId);
          break;
        case 1:
          result = searchJoinTask(userId, parameters, currentPage, 
              volume, domainId);
          break;
        case 2:
          result = searchAuditingTask(userId, parameters, currentPage, 
              volume, domainId);
          break;
        case 3:
          result = searchCompleteTask(userId, parameters, currentPage, 
              volume, domainId);
          break;
        case 4:
          result = searchCancelTask(userId, parameters, currentPage, 
              volume, domainId);
          break;
      } 
    } catch (HibernateException ex) {
      System.out.println("search Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getSearchTaskRecordCount(Long userId, String[] parameters, Integer status, Long domainId) throws HibernateException {
    return getSearchTaskRecordCount(userId, parameters, status, domainId, (String)null);
  }
  
  public Integer getSearchTaskRecordCount(Long userId, String[] parameters, Integer status, Long domainId, String toUserId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      int searchStatus = status.intValue();
      switch (searchStatus) {
        case 0:
          result = getSPTRecordCount(userId, parameters, domainId, toUserId);
          break;
        case 1:
          result = getSearchJoinTaskRecordCount(userId, parameters, 
              domainId);
          break;
        case 2:
          result = getSearchAuditingTaskRecordCount(userId, parameters, 
              domainId);
          break;
        case 3:
          result = getSearchCompleteTaskRecordCount(userId, parameters, 
              domainId);
          break;
        case 4:
          result = getSearchCancelTaskRecordCount(userId, parameters, 
              domainId);
          break;
      } 
    } catch (HibernateException ex) {
      System.out.println("search Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private Integer getSearchCompleteTaskRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    StringBuffer taskIds = new StringBuffer();
    Query joinTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskJoinedEmp like '%" + 
        
        userId + "%'" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : ""));
    Iterator<E> iterator = joinTask.iterate();
    String taskid = "";
    while (iterator != null && iterator.hasNext()) {
      taskid = iterator.next().toString();
      taskIds.append(taskid);
      taskIds.append(",");
      Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
          taskid + "%'" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      Iterator<E> iterator1 = joinChildTaskQuery.iterate();
      String taskChildId = "";
      while (iterator1 != null && iterator1.hasNext()) {
        taskChildId = iterator1.next().toString();
        taskIds.append(taskChildId);
        taskIds.append(",");
      } 
    } 
    Query principalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal = " + 
        
        userId + 
        " and task.isArranged <>1" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : ""));
    iterator = principalTask.iterate();
    while (iterator != null && iterator.hasNext()) {
      taskid = iterator.next().toString();
      taskIds.append(taskid);
      taskIds.append(",");
      Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
          taskid + "%'" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      Iterator<E> iterator1 = joinChildTaskQuery.iterate();
      String taskChildId = "";
      while (iterator1 != null && iterator1.hasNext()) {
        taskChildId = iterator1.next().toString();
        taskIds.append(taskChildId);
        taskIds.append(",");
      } 
    } 
    String taskId = taskIds.toString();
    String strsql = "";
    if (taskId != null && !taskId.trim().equals("")) {
      taskId = taskId.substring(0, taskId.length() - 1);
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + "' and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + " and task.taskEndTime between '" + 
            parameters[6].toString() + "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      String strsql1 = "";
      if ("".equals(parameters[8].toString())) {
        strsql1 = " order by task.parentIdString";
      } else {
        strsql1 = " order by task." + parameters[8].toString() + " " + 
          parameters[9].toString();
      } 
      result = this.session.iterate("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in(" + 
          taskId + 
          ") and task.taskStatus in (2,4) and (" + 
          strsql + 
          ") and task.taskDomainId = " + 
          domainId).next();
    } 
    return result;
  }
  
  private Integer getSearchAuditingTaskRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task join task.taskExecs taskExecs where taskExecs.isPrincipal = 1 and taskExecs.execEmpId = " + 
      
      userId + (
      (domainId != null) ? (
      " and task.taskDomainId = " + domainId) : 
      "");
    String strsql = "";
    if (!"".equals(parameters[0].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskTitle like '%" + parameters[0].toString() + 
          "%'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
          parameters[0].toString() + "%'";
      }  
    if (!"".equals(parameters[1].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskFinishRate=" + parameters[1].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + parameters[1].toString();
      }  
    if (!"".equals(parameters[2].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskType='" + parameters[2].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString();
      }  
    if (!"".equals(parameters[3].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskPriority=" + parameters[3].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskPriority=" + parameters[3].toString();
      }  
    if (!"".equals(parameters[4].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskStatus=" + parameters[4].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
      }  
    if (!"".equals(parameters[5].toString()) && 
      "1".equals(parameters[5].toString())) {
      if (!"".equals(strsql))
        strsql = String.valueOf(strsql) + " and "; 
      if (databaseType.indexOf("mysql") >= 0) {
        strsql = String.valueOf(strsql) + 
          " task.taskEndTime between '" + 
          parameters[6].toString() + 
          "'  and '" + 
          parameters[7].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + 
          " task.taskEndTime between JSDB.FN_STRTODATE('" + 
          parameters[6].toString() + 
          "','S')  and JSDB.FN_STRTODATE('" + 
          parameters[7].toString() + "','S')";
      } 
    } 
    result = this.session.iterate("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskParentId in (" + 
        
        tempHql + 
        ") and task.taskHasPass = 0 and (" + 
        strsql + ")" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "")).next();
    return result;
  }
  
  private Integer getSearchJoinTaskRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException, NumberFormatException {
    Integer result = new Integer(0);
    StringBuffer taskIds = new StringBuffer();
    Query joinQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskJoinedEmp like '%" + 
        
        userId + "%'" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : ""));
    Iterator<E> iterator = joinQuery.iterate();
    String taskid = "";
    while (iterator != null && iterator.hasNext()) {
      taskid = iterator.next().toString();
      taskIds.append(taskid);
      taskIds.append(",");
      Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
          taskid + "%'" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      Iterator<E> iterator1 = joinChildTaskQuery.iterate();
      String taskChildId = "";
      while (iterator1 != null && iterator1.hasNext()) {
        taskChildId = iterator1.next().toString();
        taskIds.append(taskChildId);
        taskIds.append(",");
      } 
    } 
    Query childPrincipalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal =" + 
        userId + 
        " and task.taskParentId<>0" + (
        (domainId != null) ? (" and task.taskDomainId = " + domainId) : ""));
    iterator = childPrincipalTask.iterate();
    while (iterator != null && iterator.hasNext()) {
      taskid = iterator.next().toString();
      taskIds.append(taskid);
      taskIds.append(",");
      Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%" + 
          taskid + "%'" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      Iterator<E> iterator1 = joinChildTaskQuery.iterate();
      String taskChildId = "";
      while (iterator1 != null && iterator1.hasNext()) {
        taskChildId = iterator1.next().toString();
        taskIds.append(taskChildId);
        taskIds.append(",");
      } 
    } 
    String taskId = taskIds.toString();
    String strsql = "";
    if (taskId != null && !taskId.trim().equals("")) {
      taskId = taskId.substring(0, taskId.length() - 1);
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString();
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString())) {
        if (!"".equals(strsql))
          strsql = String.valueOf(strsql) + " and "; 
        if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        } 
      } 
      result = this.session.iterate("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
          
          taskId + ") and not task.taskStatus in (2,4) and task.taskFinishRate<100 and ((task.taskHasPass<>0)or(task.taskHasPass=0 and task.createdEmp =" + 
          userId + 
          ")) and (" + strsql + ")" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "")).next();
    } 
    return result;
  }
  
  private Integer getSPTRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    return getSPTRecordCount(userId, parameters, domainId, (String)null);
  }
  
  private Integer getSPTRecordCount(Long userId, String[] parameters, Long domainId, String toUserId) throws HibernateException {
    Integer result = new Integer(0);
    StringBuffer hql = new StringBuffer("");
    if (toUserId == null) {
      hql.append("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and task.taskParentId =0 and task.taskPrincipal = " + 
          userId + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : 
          ""));
    } else {
      hql = new StringBuffer("select distinct po.taskId from com.js.oa.scheme.taskcenter.po.TaskPO po where po.createdEmp=" + userId + " and po.taskDomainId=" + domainId);
      if (databaseType.indexOf("mysql") >= 0) {
        hql.append(" and (concat('$',replace(po.taskJoineOrg,',','$$'),'$') like '%$" + toUserId + "$%' ");
      } else {
        hql.append(" and (JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$',JSDB.FN_replace(po.taskJoineOrg,',','$$')),'$') like '%$" + toUserId + "$%' ");
      } 
      hql.append(" or po.taskJoinedEmp like '%$" + toUserId + "$%' or po.taskPrincipal=" + toUserId + " or po.taskChecker=" + toUserId + " ) ");
      hql.append(" order by po.taskCreatedTime desc ");
    } 
    String strsql = "";
    if (!"".equals(parameters[0].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskTitle like '%" + parameters[0].toString() + 
          "%'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
          parameters[0].toString() + "%'";
      }  
    if (!"".equals(parameters[1].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskFinishRate=" + parameters[1].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
          parameters[1].toString();
      }  
    if (!"".equals(parameters[2].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskType='" + parameters[2].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
          "'";
      }  
    if (!"".equals(parameters[3].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskPriority=" + parameters[3].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
          parameters[3].toString();
      }  
    if (!"".equals(parameters[4].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskStatus=" + parameters[4].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
      }  
    if (!"".equals(parameters[10].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskJoineOrg like '%," + parameters[10].toString() + ",%' ";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskJoineOrg '%," + parameters[10].toString() + ",% ";
      }  
    if (!"".equals(parameters[11].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskJoinedEmp like '%" + parameters[11].toString() + "%' ";
      } else {
        strsql = String.valueOf(strsql) + "and task.taskJoinedEmp like '%" + parameters[11].toString() + "%' ";
      }  
    if (!"".equals(parameters[5].toString()) && 
      "1".equals(parameters[5].toString())) {
      String dataType = 
        SystemCommon.getDatabaseType();
      if (dataType.indexOf("mysql") >= 0) {
        if ("".equals(strsql)) {
          strsql = 
            "task.taskEndTime between '" + 
            parameters[6].toString() + 
            "' and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between '" + 
            parameters[6].toString() + 
            "' and '" + 
            parameters[7].toString() + "' ";
        } 
      } else {
        if (!"".equals(strsql))
          strsql = String.valueOf(strsql) + " and "; 
        if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        } 
      } 
    } 
    if ("".equals(strsql))
      strsql = "1=1"; 
    String taskId = "";
    if (toUserId != null)
      taskId = " and task.taskId in (" + hql + ")"; 
    Query query = this.session.createQuery("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
        
        hql + 
        ")" + taskId + " and (task.taskFinishRate<100) and (" + 
        strsql + ")" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        ""));
    result = Integer.valueOf(query.list().get(0).toString());
    return result;
  }
  
  private List searchCompleteTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #6
    //   9: new java/lang/StringBuffer
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: astore #7
    //   18: aload_0
    //   19: invokevirtual begin : ()V
    //   22: aload_0
    //   23: getfield session : Lnet/sf/hibernate/Session;
    //   26: new java/lang/StringBuilder
    //   29: dup
    //   30: ldc_w 'select task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.taskBaseId in(select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec join taskExec.task task where (task.taskStatus=2 or task.taskStatus=4) and task.taskOrderCode=0 and taskExec.isPrincipal=1 and taskExec.execEmpId='
    //   33: invokespecial <init> : (Ljava/lang/String;)V
    //   36: aload_1
    //   37: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   40: ldc_w ')'
    //   43: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: aload #5
    //   48: ifnull -> 72
    //   51: new java/lang/StringBuilder
    //   54: dup
    //   55: ldc_w ' and task1.taskDomainId = '
    //   58: invokespecial <init> : (Ljava/lang/String;)V
    //   61: aload #5
    //   63: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   66: invokevirtual toString : ()Ljava/lang/String;
    //   69: goto -> 74
    //   72: ldc ''
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: invokevirtual toString : ()Ljava/lang/String;
    //   80: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   85: astore #9
    //   87: aload #9
    //   89: invokeinterface iterate : ()Ljava/util/Iterator;
    //   94: astore #8
    //   96: goto -> 118
    //   99: aload #7
    //   101: aload #8
    //   103: invokeinterface next : ()Ljava/lang/Object;
    //   108: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   111: ldc_w ','
    //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   117: pop
    //   118: aload #8
    //   120: invokeinterface hasNext : ()Z
    //   125: ifne -> 99
    //   128: new java/lang/StringBuffer
    //   131: dup
    //   132: invokespecial <init> : ()V
    //   135: astore #10
    //   137: aload_0
    //   138: getfield session : Lnet/sf/hibernate/Session;
    //   141: new java/lang/StringBuilder
    //   144: dup
    //   145: ldc_w 'select distinct task2.taskBaseId from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec join taskExec.task task2 where (task2.taskStatus=2 or task2.taskStatus=4) and task2.taskOrderCode<>0 and taskExec.isPrincipal=1 and taskExec.execEmpId='
    //   148: invokespecial <init> : (Ljava/lang/String;)V
    //   151: aload_1
    //   152: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   155: aload #5
    //   157: ifnull -> 181
    //   160: new java/lang/StringBuilder
    //   163: dup
    //   164: ldc_w ' and taskExec.teDomainId = '
    //   167: invokespecial <init> : (Ljava/lang/String;)V
    //   170: aload #5
    //   172: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   175: invokevirtual toString : ()Ljava/lang/String;
    //   178: goto -> 183
    //   181: ldc ''
    //   183: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: invokevirtual toString : ()Ljava/lang/String;
    //   189: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   194: astore #9
    //   196: aload #9
    //   198: invokeinterface iterate : ()Ljava/util/Iterator;
    //   203: astore #8
    //   205: goto -> 227
    //   208: aload #10
    //   210: aload #8
    //   212: invokeinterface next : ()Ljava/lang/Object;
    //   217: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   220: ldc_w ','
    //   223: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   226: pop
    //   227: aload #8
    //   229: invokeinterface hasNext : ()Z
    //   234: ifne -> 208
    //   237: aload #10
    //   239: ldc_w '-1'
    //   242: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   245: pop
    //   246: aload_0
    //   247: getfield session : Lnet/sf/hibernate/Session;
    //   250: new java/lang/StringBuilder
    //   253: dup
    //   254: ldc_w 'select task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in(select task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where (task1.taskStatus=2 or task1.taskStatus=4) and task1.taskId in ('
    //   257: invokespecial <init> : (Ljava/lang/String;)V
    //   260: aload #10
    //   262: invokevirtual toString : ()Ljava/lang/String;
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: ldc_w '))'
    //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: aload #5
    //   276: ifnull -> 300
    //   279: new java/lang/StringBuilder
    //   282: dup
    //   283: ldc_w ' and task.taskDomainId = '
    //   286: invokespecial <init> : (Ljava/lang/String;)V
    //   289: aload #5
    //   291: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   294: invokevirtual toString : ()Ljava/lang/String;
    //   297: goto -> 302
    //   300: ldc ''
    //   302: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   305: invokevirtual toString : ()Ljava/lang/String;
    //   308: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   313: astore #9
    //   315: aload #9
    //   317: invokeinterface iterate : ()Ljava/util/Iterator;
    //   322: astore #8
    //   324: goto -> 346
    //   327: aload #7
    //   329: aload #8
    //   331: invokeinterface next : ()Ljava/lang/Object;
    //   336: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   339: ldc_w ','
    //   342: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   345: pop
    //   346: aload #8
    //   348: invokeinterface hasNext : ()Z
    //   353: ifne -> 327
    //   356: aload_0
    //   357: getfield session : Lnet/sf/hibernate/Session;
    //   360: new java/lang/StringBuilder
    //   363: dup
    //   364: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec join taskExec.task task where (task.taskStatus=2 or task.taskStatus=4) and task.taskOrderCode<>0 and taskExec.isPrincipal=1 and taskExec.execEmpId='
    //   367: invokespecial <init> : (Ljava/lang/String;)V
    //   370: aload_1
    //   371: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   374: ldc_w ' and task.taskBaseId in('
    //   377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: ldc_w 'select task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where (task1.taskStatus<>2 and task1.taskStatus<>4) and task1.taskId in ('
    //   383: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   386: aload #10
    //   388: invokevirtual toString : ()Ljava/lang/String;
    //   391: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: ldc_w '))'
    //   397: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   400: aload #5
    //   402: ifnull -> 426
    //   405: new java/lang/StringBuilder
    //   408: dup
    //   409: ldc_w ' and taskExec.teDomainId = '
    //   412: invokespecial <init> : (Ljava/lang/String;)V
    //   415: aload #5
    //   417: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   420: invokevirtual toString : ()Ljava/lang/String;
    //   423: goto -> 428
    //   426: ldc ''
    //   428: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   431: invokevirtual toString : ()Ljava/lang/String;
    //   434: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   439: astore #9
    //   441: aload #9
    //   443: invokeinterface iterate : ()Ljava/util/Iterator;
    //   448: astore #8
    //   450: goto -> 472
    //   453: aload #7
    //   455: aload #8
    //   457: invokeinterface next : ()Ljava/lang/Object;
    //   462: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   465: ldc_w ','
    //   468: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   471: pop
    //   472: aload #8
    //   474: invokeinterface hasNext : ()Z
    //   479: ifne -> 453
    //   482: aload_0
    //   483: getfield session : Lnet/sf/hibernate/Session;
    //   486: new java/lang/StringBuilder
    //   489: dup
    //   490: ldc_w 'select distinct task.taskBaseId from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec join taskExec.task task where (task.taskStatus=2 or task.taskStatus=4) and task.taskOrderCode=0 and taskExec.isPrincipal=0 and taskExec.execEmpId='
    //   493: invokespecial <init> : (Ljava/lang/String;)V
    //   496: aload_1
    //   497: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   500: aload #5
    //   502: ifnull -> 526
    //   505: new java/lang/StringBuilder
    //   508: dup
    //   509: ldc_w ' and taskExec.teDomainId = '
    //   512: invokespecial <init> : (Ljava/lang/String;)V
    //   515: aload #5
    //   517: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   520: invokevirtual toString : ()Ljava/lang/String;
    //   523: goto -> 528
    //   526: ldc ''
    //   528: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   531: invokevirtual toString : ()Ljava/lang/String;
    //   534: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   539: astore #9
    //   541: aload #9
    //   543: invokeinterface iterate : ()Ljava/util/Iterator;
    //   548: astore #8
    //   550: new java/lang/StringBuffer
    //   553: dup
    //   554: invokespecial <init> : ()V
    //   557: astore #10
    //   559: goto -> 581
    //   562: aload #10
    //   564: aload #8
    //   566: invokeinterface next : ()Ljava/lang/Object;
    //   571: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   574: ldc_w ','
    //   577: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   580: pop
    //   581: aload #8
    //   583: invokeinterface hasNext : ()Z
    //   588: ifne -> 562
    //   591: aload #10
    //   593: ldc_w '-1'
    //   596: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   599: pop
    //   600: aload_0
    //   601: getfield session : Lnet/sf/hibernate/Session;
    //   604: new java/lang/StringBuilder
    //   607: dup
    //   608: ldc_w 'select task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (select task1.taskId from com.js.oa.scheme.taskcenter.po.TaskExecPO taskExec join taskExec.task task1 where (task1.taskStatus=2 or task1.taskStatus=4) and task1.taskOrderCode=0 and task1.createdEmp<>'
    //   611: invokespecial <init> : (Ljava/lang/String;)V
    //   614: aload_1
    //   615: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   618: ldc_w ' and taskExec.isPrincipal=0 and taskExec.execEmpId='
    //   621: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   624: aload_1
    //   625: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   628: ldc_w ')'
    //   631: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   634: aload #5
    //   636: ifnull -> 660
    //   639: new java/lang/StringBuilder
    //   642: dup
    //   643: ldc_w ' and task.taskDomainId = '
    //   646: invokespecial <init> : (Ljava/lang/String;)V
    //   649: aload #5
    //   651: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   654: invokevirtual toString : ()Ljava/lang/String;
    //   657: goto -> 662
    //   660: ldc ''
    //   662: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   665: invokevirtual toString : ()Ljava/lang/String;
    //   668: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   673: astore #9
    //   675: aload #9
    //   677: invokeinterface iterate : ()Ljava/util/Iterator;
    //   682: astore #8
    //   684: goto -> 706
    //   687: aload #7
    //   689: aload #8
    //   691: invokeinterface next : ()Ljava/lang/Object;
    //   696: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   699: ldc_w ','
    //   702: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   705: pop
    //   706: aload #8
    //   708: invokeinterface hasNext : ()Z
    //   713: ifne -> 687
    //   716: aload_0
    //   717: getfield session : Lnet/sf/hibernate/Session;
    //   720: new java/lang/StringBuilder
    //   723: dup
    //   724: ldc_w 'select task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (select task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where (task1.taskStatus=2 or task1.taskStatus=4) and task1.createdEmp<>'
    //   727: invokespecial <init> : (Ljava/lang/String;)V
    //   730: aload_1
    //   731: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   734: ldc_w ' and task1.taskId in('
    //   737: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   740: aload #10
    //   742: invokevirtual toString : ()Ljava/lang/String;
    //   745: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   748: ldc_w '))'
    //   751: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   754: aload #5
    //   756: ifnull -> 780
    //   759: new java/lang/StringBuilder
    //   762: dup
    //   763: ldc_w ' and task.taskDomainId = '
    //   766: invokespecial <init> : (Ljava/lang/String;)V
    //   769: aload #5
    //   771: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   774: invokevirtual toString : ()Ljava/lang/String;
    //   777: goto -> 782
    //   780: ldc ''
    //   782: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   785: invokevirtual toString : ()Ljava/lang/String;
    //   788: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   793: astore #9
    //   795: aload #9
    //   797: invokeinterface iterate : ()Ljava/util/Iterator;
    //   802: astore #8
    //   804: goto -> 826
    //   807: aload #7
    //   809: aload #8
    //   811: invokeinterface next : ()Ljava/lang/Object;
    //   816: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   819: ldc_w ','
    //   822: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   825: pop
    //   826: aload #8
    //   828: invokeinterface hasNext : ()Z
    //   833: ifne -> 807
    //   836: aload_0
    //   837: getfield session : Lnet/sf/hibernate/Session;
    //   840: new java/lang/StringBuilder
    //   843: dup
    //   844: ldc_w 'select task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (select task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where (task1.taskStatus<>2 and task1.taskStatus<>4) and task1.taskId in('
    //   847: invokespecial <init> : (Ljava/lang/String;)V
    //   850: aload #10
    //   852: invokevirtual toString : ()Ljava/lang/String;
    //   855: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   858: ldc_w '))'
    //   861: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   864: aload #5
    //   866: ifnull -> 890
    //   869: new java/lang/StringBuilder
    //   872: dup
    //   873: ldc_w ' and task.taskDomainId = '
    //   876: invokespecial <init> : (Ljava/lang/String;)V
    //   879: aload #5
    //   881: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   884: invokevirtual toString : ()Ljava/lang/String;
    //   887: goto -> 892
    //   890: ldc ''
    //   892: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   895: invokevirtual toString : ()Ljava/lang/String;
    //   898: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   903: astore #9
    //   905: aload #9
    //   907: invokeinterface iterate : ()Ljava/util/Iterator;
    //   912: astore #8
    //   914: goto -> 936
    //   917: aload #7
    //   919: aload #8
    //   921: invokeinterface next : ()Ljava/lang/Object;
    //   926: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuffer;
    //   929: ldc_w ','
    //   932: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   935: pop
    //   936: aload #8
    //   938: invokeinterface hasNext : ()Z
    //   943: ifne -> 917
    //   946: aload #7
    //   948: invokevirtual toString : ()Ljava/lang/String;
    //   951: astore #11
    //   953: ldc ''
    //   955: astore #12
    //   957: ldc ''
    //   959: aload_2
    //   960: iconst_0
    //   961: aaload
    //   962: invokevirtual toString : ()Ljava/lang/String;
    //   965: invokevirtual equals : (Ljava/lang/Object;)Z
    //   968: ifne -> 1052
    //   971: ldc ''
    //   973: aload #12
    //   975: invokevirtual equals : (Ljava/lang/Object;)Z
    //   978: ifeq -> 1014
    //   981: new java/lang/StringBuilder
    //   984: dup
    //   985: ldc_w 'task.taskTitle like '%'
    //   988: invokespecial <init> : (Ljava/lang/String;)V
    //   991: aload_2
    //   992: iconst_0
    //   993: aaload
    //   994: invokevirtual toString : ()Ljava/lang/String;
    //   997: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1000: ldc_w '%''
    //   1003: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1006: invokevirtual toString : ()Ljava/lang/String;
    //   1009: astore #12
    //   1011: goto -> 1052
    //   1014: new java/lang/StringBuilder
    //   1017: dup
    //   1018: aload #12
    //   1020: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1023: invokespecial <init> : (Ljava/lang/String;)V
    //   1026: ldc_w ' and task.taskTitle like '%'
    //   1029: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1032: aload_2
    //   1033: iconst_0
    //   1034: aaload
    //   1035: invokevirtual toString : ()Ljava/lang/String;
    //   1038: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1041: ldc_w '%''
    //   1044: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1047: invokevirtual toString : ()Ljava/lang/String;
    //   1050: astore #12
    //   1052: ldc ''
    //   1054: aload_2
    //   1055: iconst_1
    //   1056: aaload
    //   1057: invokevirtual toString : ()Ljava/lang/String;
    //   1060: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1063: ifne -> 1135
    //   1066: ldc ''
    //   1068: aload #12
    //   1070: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1073: ifeq -> 1103
    //   1076: new java/lang/StringBuilder
    //   1079: dup
    //   1080: ldc_w 'task.taskFinishRate='
    //   1083: invokespecial <init> : (Ljava/lang/String;)V
    //   1086: aload_2
    //   1087: iconst_1
    //   1088: aaload
    //   1089: invokevirtual toString : ()Ljava/lang/String;
    //   1092: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1095: invokevirtual toString : ()Ljava/lang/String;
    //   1098: astore #12
    //   1100: goto -> 1135
    //   1103: new java/lang/StringBuilder
    //   1106: dup
    //   1107: aload #12
    //   1109: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1112: invokespecial <init> : (Ljava/lang/String;)V
    //   1115: ldc_w ' and task.taskFinishRate='
    //   1118: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1121: aload_2
    //   1122: iconst_1
    //   1123: aaload
    //   1124: invokevirtual toString : ()Ljava/lang/String;
    //   1127: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1130: invokevirtual toString : ()Ljava/lang/String;
    //   1133: astore #12
    //   1135: ldc ''
    //   1137: aload_2
    //   1138: iconst_2
    //   1139: aaload
    //   1140: invokevirtual toString : ()Ljava/lang/String;
    //   1143: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1146: ifne -> 1228
    //   1149: ldc ''
    //   1151: aload #12
    //   1153: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1156: ifeq -> 1191
    //   1159: new java/lang/StringBuilder
    //   1162: dup
    //   1163: ldc_w 'task.taskType=''
    //   1166: invokespecial <init> : (Ljava/lang/String;)V
    //   1169: aload_2
    //   1170: iconst_2
    //   1171: aaload
    //   1172: invokevirtual toString : ()Ljava/lang/String;
    //   1175: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1178: ldc '''
    //   1180: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1183: invokevirtual toString : ()Ljava/lang/String;
    //   1186: astore #12
    //   1188: goto -> 1228
    //   1191: new java/lang/StringBuilder
    //   1194: dup
    //   1195: aload #12
    //   1197: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1200: invokespecial <init> : (Ljava/lang/String;)V
    //   1203: ldc_w ' and task.taskType=''
    //   1206: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1209: aload_2
    //   1210: iconst_2
    //   1211: aaload
    //   1212: invokevirtual toString : ()Ljava/lang/String;
    //   1215: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1218: ldc '''
    //   1220: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1223: invokevirtual toString : ()Ljava/lang/String;
    //   1226: astore #12
    //   1228: ldc ''
    //   1230: aload_2
    //   1231: iconst_3
    //   1232: aaload
    //   1233: invokevirtual toString : ()Ljava/lang/String;
    //   1236: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1239: ifne -> 1311
    //   1242: ldc ''
    //   1244: aload #12
    //   1246: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1249: ifeq -> 1279
    //   1252: new java/lang/StringBuilder
    //   1255: dup
    //   1256: ldc_w 'task.taskPriority='
    //   1259: invokespecial <init> : (Ljava/lang/String;)V
    //   1262: aload_2
    //   1263: iconst_3
    //   1264: aaload
    //   1265: invokevirtual toString : ()Ljava/lang/String;
    //   1268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1271: invokevirtual toString : ()Ljava/lang/String;
    //   1274: astore #12
    //   1276: goto -> 1311
    //   1279: new java/lang/StringBuilder
    //   1282: dup
    //   1283: aload #12
    //   1285: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1288: invokespecial <init> : (Ljava/lang/String;)V
    //   1291: ldc_w ' and task.taskPriority='
    //   1294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1297: aload_2
    //   1298: iconst_3
    //   1299: aaload
    //   1300: invokevirtual toString : ()Ljava/lang/String;
    //   1303: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1306: invokevirtual toString : ()Ljava/lang/String;
    //   1309: astore #12
    //   1311: ldc ''
    //   1313: aload_2
    //   1314: iconst_4
    //   1315: aaload
    //   1316: invokevirtual toString : ()Ljava/lang/String;
    //   1319: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1322: ifne -> 1394
    //   1325: ldc ''
    //   1327: aload #12
    //   1329: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1332: ifeq -> 1362
    //   1335: new java/lang/StringBuilder
    //   1338: dup
    //   1339: ldc_w 'task.taskStatus='
    //   1342: invokespecial <init> : (Ljava/lang/String;)V
    //   1345: aload_2
    //   1346: iconst_4
    //   1347: aaload
    //   1348: invokevirtual toString : ()Ljava/lang/String;
    //   1351: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1354: invokevirtual toString : ()Ljava/lang/String;
    //   1357: astore #12
    //   1359: goto -> 1394
    //   1362: new java/lang/StringBuilder
    //   1365: dup
    //   1366: aload #12
    //   1368: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1371: invokespecial <init> : (Ljava/lang/String;)V
    //   1374: ldc_w ' and task.taskStatus='
    //   1377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1380: aload_2
    //   1381: iconst_4
    //   1382: aaload
    //   1383: invokevirtual toString : ()Ljava/lang/String;
    //   1386: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1389: invokevirtual toString : ()Ljava/lang/String;
    //   1392: astore #12
    //   1394: ldc ''
    //   1396: aload_2
    //   1397: iconst_5
    //   1398: aaload
    //   1399: invokevirtual toString : ()Ljava/lang/String;
    //   1402: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1405: ifne -> 1580
    //   1408: ldc_w '1'
    //   1411: aload_2
    //   1412: iconst_5
    //   1413: aaload
    //   1414: invokevirtual toString : ()Ljava/lang/String;
    //   1417: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1420: ifeq -> 1580
    //   1423: ldc ''
    //   1425: aload #12
    //   1427: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1430: ifne -> 1456
    //   1433: new java/lang/StringBuilder
    //   1436: dup
    //   1437: aload #12
    //   1439: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1442: invokespecial <init> : (Ljava/lang/String;)V
    //   1445: ldc_w ' and  '
    //   1448: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1451: invokevirtual toString : ()Ljava/lang/String;
    //   1454: astore #12
    //   1456: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1459: ldc_w 'mysql'
    //   1462: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1465: iflt -> 1525
    //   1468: new java/lang/StringBuilder
    //   1471: dup
    //   1472: aload #12
    //   1474: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1477: invokespecial <init> : (Ljava/lang/String;)V
    //   1480: ldc_w ' task.taskEndTime between ''
    //   1483: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1486: aload_2
    //   1487: bipush #6
    //   1489: aaload
    //   1490: invokevirtual toString : ()Ljava/lang/String;
    //   1493: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1496: ldc_w ''  and ''
    //   1499: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1502: aload_2
    //   1503: bipush #7
    //   1505: aaload
    //   1506: invokevirtual toString : ()Ljava/lang/String;
    //   1509: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1512: ldc '''
    //   1514: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1517: invokevirtual toString : ()Ljava/lang/String;
    //   1520: astore #12
    //   1522: goto -> 1580
    //   1525: new java/lang/StringBuilder
    //   1528: dup
    //   1529: aload #12
    //   1531: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1534: invokespecial <init> : (Ljava/lang/String;)V
    //   1537: ldc_w ' task.taskEndTime between JSDB.FN_STRTODATE(''
    //   1540: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1543: aload_2
    //   1544: bipush #6
    //   1546: aaload
    //   1547: invokevirtual toString : ()Ljava/lang/String;
    //   1550: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1553: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   1556: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1559: aload_2
    //   1560: bipush #7
    //   1562: aaload
    //   1563: invokevirtual toString : ()Ljava/lang/String;
    //   1566: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1569: ldc_w '','S')'
    //   1572: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1575: invokevirtual toString : ()Ljava/lang/String;
    //   1578: astore #12
    //   1580: ldc ''
    //   1582: aload #12
    //   1584: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1587: ifeq -> 1595
    //   1590: ldc_w '1=1'
    //   1593: astore #12
    //   1595: ldc ''
    //   1597: astore #13
    //   1599: ldc ''
    //   1601: aload_2
    //   1602: bipush #8
    //   1604: aaload
    //   1605: invokevirtual toString : ()Ljava/lang/String;
    //   1608: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1611: ifeq -> 1622
    //   1614: ldc_w ' order by task.taskId'
    //   1617: astore #13
    //   1619: goto -> 1663
    //   1622: new java/lang/StringBuilder
    //   1625: dup
    //   1626: ldc_w ' order by task.'
    //   1629: invokespecial <init> : (Ljava/lang/String;)V
    //   1632: aload_2
    //   1633: bipush #8
    //   1635: aaload
    //   1636: invokevirtual toString : ()Ljava/lang/String;
    //   1639: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1642: ldc_w ' '
    //   1645: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1648: aload_2
    //   1649: bipush #9
    //   1651: aaload
    //   1652: invokevirtual toString : ()Ljava/lang/String;
    //   1655: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1658: invokevirtual toString : ()Ljava/lang/String;
    //   1661: astore #13
    //   1663: aload #11
    //   1665: ifnull -> 2297
    //   1668: aload #11
    //   1670: invokevirtual trim : ()Ljava/lang/String;
    //   1673: ldc ''
    //   1675: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1678: ifne -> 2297
    //   1681: aload #11
    //   1683: iconst_0
    //   1684: aload #11
    //   1686: invokevirtual length : ()I
    //   1689: iconst_1
    //   1690: isub
    //   1691: invokevirtual substring : (II)Ljava/lang/String;
    //   1694: astore #11
    //   1696: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1699: ldc_w 'db2'
    //   1702: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1705: iflt -> 1798
    //   1708: new java/lang/StringBuilder
    //   1711: dup
    //   1712: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in ('
    //   1715: invokespecial <init> : (Ljava/lang/String;)V
    //   1718: aload #11
    //   1720: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1723: ldc_w ') and (task.createdEmp='
    //   1726: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1729: aload_1
    //   1730: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1733: ldc_w ' or task.taskPrincipal = '
    //   1736: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1739: aload_1
    //   1740: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1743: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1746: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1749: aload_1
    //   1750: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1753: ldc_w '$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   1756: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1759: aload_1
    //   1760: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1763: ldc_w ',' )>0    ) and ('
    //   1766: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1769: aload #12
    //   1771: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1774: ldc_w ') and task.taskFinishRate=100 and task.taskDomainId ='
    //   1777: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1780: aload #5
    //   1782: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1785: aload #13
    //   1787: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1790: invokevirtual toString : ()Ljava/lang/String;
    //   1793: astore #12
    //   1795: goto -> 1987
    //   1798: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   1801: ldc_w 'mysql'
    //   1804: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1807: iflt -> 1900
    //   1810: new java/lang/StringBuilder
    //   1813: dup
    //   1814: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in ('
    //   1817: invokespecial <init> : (Ljava/lang/String;)V
    //   1820: aload #11
    //   1822: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1825: ldc_w ') and (task.createdEmp='
    //   1828: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1831: aload_1
    //   1832: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1835: ldc_w ' or task.taskPrincipal = '
    //   1838: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1841: aload_1
    //   1842: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1845: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1848: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1851: aload_1
    //   1852: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1855: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   1858: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1861: aload_1
    //   1862: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1865: ldc_w ',%'  ) and ('
    //   1868: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1871: aload #12
    //   1873: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1876: ldc_w ') and task.taskFinishRate=100 and task.taskDomainId ='
    //   1879: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1882: aload #5
    //   1884: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1887: aload #13
    //   1889: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1892: invokevirtual toString : ()Ljava/lang/String;
    //   1895: astore #12
    //   1897: goto -> 1987
    //   1900: new java/lang/StringBuilder
    //   1903: dup
    //   1904: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in ('
    //   1907: invokespecial <init> : (Ljava/lang/String;)V
    //   1910: aload #11
    //   1912: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1915: ldc_w ') and (task.createdEmp='
    //   1918: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1921: aload_1
    //   1922: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1925: ldc_w ' or task.taskPrincipal = '
    //   1928: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1931: aload_1
    //   1932: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1935: ldc_w ' or task.taskJoinedEmp like '%$'
    //   1938: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1941: aload_1
    //   1942: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1945: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   1948: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1951: aload_1
    //   1952: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1955: ldc_w ',%'  ) and ('
    //   1958: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1961: aload #12
    //   1963: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1966: ldc_w ') and task.taskFinishRate=100 and task.taskDomainId ='
    //   1969: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1972: aload #5
    //   1974: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1977: aload #13
    //   1979: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1982: invokevirtual toString : ()Ljava/lang/String;
    //   1985: astore #12
    //   1987: aload_0
    //   1988: getfield session : Lnet/sf/hibernate/Session;
    //   1991: aload #12
    //   1993: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1998: astore #14
    //   2000: aload #14
    //   2002: aload_3
    //   2003: invokevirtual intValue : ()I
    //   2006: iconst_1
    //   2007: isub
    //   2008: aload #4
    //   2010: invokevirtual intValue : ()I
    //   2013: imul
    //   2014: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   2019: pop
    //   2020: aload #14
    //   2022: aload #4
    //   2024: invokevirtual intValue : ()I
    //   2027: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   2032: pop
    //   2033: aload #14
    //   2035: invokeinterface list : ()Ljava/util/List;
    //   2040: invokeinterface iterator : ()Ljava/util/Iterator;
    //   2045: astore #8
    //   2047: aconst_null
    //   2048: astore #15
    //   2050: goto -> 2274
    //   2053: aload #8
    //   2055: invokeinterface next : ()Ljava/lang/Object;
    //   2060: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   2063: astore #15
    //   2065: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   2068: aload #15
    //   2070: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   2073: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   2076: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   2079: astore #16
    //   2081: aload #16
    //   2083: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2086: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   2089: aload #16
    //   2091: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2094: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   2097: aload #16
    //   2099: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2102: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   2105: aload #16
    //   2107: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   2110: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   2113: aload #16
    //   2115: ldc_w '0'
    //   2118: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   2121: aload #15
    //   2123: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   2126: ifnull -> 2142
    //   2129: ldc ''
    //   2131: aload #15
    //   2133: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   2136: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2139: ifeq -> 2163
    //   2142: aload #15
    //   2144: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2147: ifnull -> 2264
    //   2150: ldc ''
    //   2152: aload #15
    //   2154: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2157: invokevirtual equals : (Ljava/lang/Object;)Z
    //   2160: ifne -> 2264
    //   2163: aload #15
    //   2165: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   2168: new java/lang/StringBuilder
    //   2171: dup
    //   2172: ldc_w '$'
    //   2175: invokespecial <init> : (Ljava/lang/String;)V
    //   2178: aload_1
    //   2179: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2182: ldc_w '$'
    //   2185: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2188: invokevirtual toString : ()Ljava/lang/String;
    //   2191: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2194: ifge -> 2256
    //   2197: new java/lang/StringBuilder
    //   2200: dup
    //   2201: ldc_w ','
    //   2204: invokespecial <init> : (Ljava/lang/String;)V
    //   2207: aload #15
    //   2209: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   2212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2215: ldc_w ','
    //   2218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2221: invokevirtual toString : ()Ljava/lang/String;
    //   2224: new java/lang/StringBuilder
    //   2227: dup
    //   2228: ldc_w ','
    //   2231: invokespecial <init> : (Ljava/lang/String;)V
    //   2234: aload_1
    //   2235: invokevirtual toString : ()Ljava/lang/String;
    //   2238: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2241: ldc_w ','
    //   2244: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2247: invokevirtual toString : ()Ljava/lang/String;
    //   2250: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2253: iflt -> 2264
    //   2256: aload #16
    //   2258: ldc_w '1'
    //   2261: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   2264: aload #6
    //   2266: aload #16
    //   2268: invokeinterface add : (Ljava/lang/Object;)Z
    //   2273: pop
    //   2274: aload #8
    //   2276: ifnull -> 2297
    //   2279: aload #8
    //   2281: invokeinterface hasNext : ()Z
    //   2286: ifne -> 2053
    //   2289: goto -> 2297
    //   2292: astore #9
    //   2294: aload #9
    //   2296: athrow
    //   2297: aload #6
    //   2299: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #3979	-> 0
    //   #3981	-> 9
    //   #3984	-> 18
    //   #3997	-> 22
    //   #3999	-> 36
    //   #4000	-> 46
    //   #4001	-> 51
    //   #4002	-> 61
    //   #4001	-> 66
    //   #4002	-> 72
    //   #4000	-> 74
    //   #3997	-> 80
    //   #4003	-> 87
    //   #4004	-> 96
    //   #4005	-> 99
    //   #4004	-> 118
    //   #4009	-> 128
    //   #4010	-> 137
    //   #4011	-> 151
    //   #4012	-> 155
    //   #4013	-> 160
    //   #4014	-> 170
    //   #4013	-> 175
    //   #4014	-> 181
    //   #4012	-> 183
    //   #4010	-> 189
    //   #4015	-> 196
    //   #4016	-> 205
    //   #4017	-> 208
    //   #4016	-> 227
    //   #4019	-> 237
    //   #4022	-> 246
    //   #4024	-> 260
    //   #4025	-> 274
    //   #4026	-> 279
    //   #4027	-> 289
    //   #4026	-> 294
    //   #4027	-> 300
    //   #4025	-> 302
    //   #4022	-> 308
    //   #4028	-> 315
    //   #4029	-> 324
    //   #4030	-> 327
    //   #4029	-> 346
    //   #4034	-> 356
    //   #4035	-> 370
    //   #4036	-> 374
    //   #4037	-> 380
    //   #4038	-> 386
    //   #4039	-> 400
    //   #4040	-> 405
    //   #4041	-> 415
    //   #4040	-> 420
    //   #4041	-> 426
    //   #4039	-> 428
    //   #4034	-> 434
    //   #4042	-> 441
    //   #4043	-> 450
    //   #4044	-> 453
    //   #4043	-> 472
    //   #4049	-> 482
    //   #4050	-> 496
    //   #4051	-> 500
    //   #4052	-> 505
    //   #4053	-> 515
    //   #4052	-> 520
    //   #4053	-> 526
    //   #4051	-> 528
    //   #4049	-> 534
    //   #4054	-> 541
    //   #4055	-> 550
    //   #4056	-> 559
    //   #4057	-> 562
    //   #4056	-> 581
    //   #4059	-> 591
    //   #4062	-> 600
    //   #4064	-> 614
    //   #4065	-> 618
    //   #4066	-> 624
    //   #4067	-> 634
    //   #4068	-> 639
    //   #4069	-> 649
    //   #4068	-> 654
    //   #4069	-> 660
    //   #4067	-> 662
    //   #4062	-> 668
    //   #4070	-> 675
    //   #4071	-> 684
    //   #4072	-> 687
    //   #4071	-> 706
    //   #4077	-> 716
    //   #4079	-> 730
    //   #4080	-> 740
    //   #4081	-> 754
    //   #4082	-> 759
    //   #4083	-> 769
    //   #4082	-> 774
    //   #4083	-> 780
    //   #4081	-> 782
    //   #4077	-> 788
    //   #4084	-> 795
    //   #4085	-> 804
    //   #4086	-> 807
    //   #4085	-> 826
    //   #4091	-> 836
    //   #4093	-> 850
    //   #4094	-> 864
    //   #4095	-> 869
    //   #4096	-> 879
    //   #4095	-> 884
    //   #4096	-> 890
    //   #4094	-> 892
    //   #4091	-> 898
    //   #4097	-> 905
    //   #4098	-> 914
    //   #4099	-> 917
    //   #4098	-> 936
    //   #4102	-> 946
    //   #4103	-> 953
    //   #4104	-> 957
    //   #4105	-> 971
    //   #4106	-> 981
    //   #4107	-> 1000
    //   #4106	-> 1006
    //   #4109	-> 1014
    //   #4110	-> 1032
    //   #4109	-> 1047
    //   #4113	-> 1052
    //   #4114	-> 1066
    //   #4115	-> 1076
    //   #4118	-> 1103
    //   #4119	-> 1121
    //   #4118	-> 1130
    //   #4124	-> 1135
    //   #4125	-> 1149
    //   #4126	-> 1159
    //   #4128	-> 1191
    //   #4129	-> 1218
    //   #4128	-> 1223
    //   #4132	-> 1228
    //   #4133	-> 1242
    //   #4134	-> 1252
    //   #4137	-> 1279
    //   #4138	-> 1297
    //   #4137	-> 1306
    //   #4142	-> 1311
    //   #4143	-> 1325
    //   #4144	-> 1335
    //   #4147	-> 1362
    //   #4151	-> 1394
    //   #4152	-> 1408
    //   #4154	-> 1423
    //   #4156	-> 1456
    //   #4158	-> 1468
    //   #4159	-> 1480
    //   #4160	-> 1486
    //   #4161	-> 1496
    //   #4162	-> 1502
    //   #4158	-> 1517
    //   #4166	-> 1525
    //   #4167	-> 1537
    //   #4168	-> 1543
    //   #4169	-> 1553
    //   #4170	-> 1559
    //   #4166	-> 1575
    //   #4176	-> 1580
    //   #4177	-> 1590
    //   #4180	-> 1595
    //   #4181	-> 1599
    //   #4182	-> 1614
    //   #4184	-> 1622
    //   #4185	-> 1648
    //   #4184	-> 1658
    //   #4188	-> 1663
    //   #4189	-> 1681
    //   #4191	-> 1696
    //   #4193	-> 1708
    //   #4194	-> 1718
    //   #4195	-> 1733
    //   #4196	-> 1743
    //   #4197	-> 1753
    //   #4198	-> 1774
    //   #4199	-> 1780
    //   #4193	-> 1790
    //   #4201	-> 1798
    //   #4203	-> 1810
    //   #4204	-> 1820
    //   #4205	-> 1835
    //   #4206	-> 1845
    //   #4207	-> 1855
    //   #4208	-> 1871
    //   #4209	-> 1876
    //   #4210	-> 1882
    //   #4203	-> 1892
    //   #4214	-> 1900
    //   #4215	-> 1910
    //   #4216	-> 1925
    //   #4217	-> 1935
    //   #4218	-> 1945
    //   #4219	-> 1961
    //   #4220	-> 1966
    //   #4221	-> 1972
    //   #4214	-> 1982
    //   #4227	-> 1987
    //   #4228	-> 2000
    //   #4229	-> 2008
    //   #4228	-> 2014
    //   #4230	-> 2020
    //   #4231	-> 2033
    //   #4232	-> 2047
    //   #4234	-> 2050
    //   #4235	-> 2053
    //   #4236	-> 2065
    //   #4237	-> 2068
    //   #4236	-> 2076
    //   #4238	-> 2081
    //   #4239	-> 2089
    //   #4240	-> 2097
    //   #4241	-> 2105
    //   #4243	-> 2113
    //   #4244	-> 2121
    //   #4245	-> 2129
    //   #4246	-> 2142
    //   #4247	-> 2150
    //   #4248	-> 2163
    //   #4249	-> 2182
    //   #4248	-> 2191
    //   #4250	-> 2197
    //   #4252	-> 2256
    //   #4256	-> 2264
    //   #4234	-> 2274
    //   #4260	-> 2292
    //   #4262	-> 2294
    //   #4268	-> 2297
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	2300	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	2300	1	userId	Ljava/lang/Long;
    //   0	2300	2	parameters	[Ljava/lang/String;
    //   0	2300	3	currentPage	Ljava/lang/Integer;
    //   0	2300	4	volume	Ljava/lang/Integer;
    //   0	2300	5	domainId	Ljava/lang/Long;
    //   9	2291	6	result	Ljava/util/List;
    //   18	2282	7	taskIds	Ljava/lang/StringBuffer;
    //   96	2196	8	iterator	Ljava/util/Iterator;
    //   2297	3	8	iterator	Ljava/util/Iterator;
    //   87	2205	9	myFinishTask	Lnet/sf/hibernate/Query;
    //   137	2155	10	buffer	Ljava/lang/StringBuffer;
    //   953	1339	11	taskId	Ljava/lang/String;
    //   957	1335	12	strsql	Ljava/lang/String;
    //   1599	693	13	strsql1	Ljava/lang/String;
    //   2000	289	14	query	Lnet/sf/hibernate/Query;
    //   2050	239	15	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   2081	193	16	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   2294	3	9	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   18	2289	2292	net/sf/hibernate/HibernateException
  }
  
  private List searchAuditingTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task join task.taskExecs taskExecs where taskExecs.isPrincipal = 1 and taskExecs.execEmpId = " + 
      
      userId + (
      (domainId != null) ? (
      " and task.taskDomainId = " + domainId) : 
      "");
    String strsql = "";
    if (!"".equals(parameters[0].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskTitle like '%" + parameters[0].toString() + 
          "%'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
          parameters[0].toString() + "%'";
      }  
    if (!"".equals(parameters[1].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskFinishRate=" + parameters[1].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + parameters[1].toString();
      }  
    if (!"".equals(parameters[2].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskType='" + parameters[2].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString();
      }  
    if (!"".equals(parameters[3].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskPriority=" + parameters[3].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskPriority=" + parameters[3].toString();
      }  
    if (!"".equals(parameters[4].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskStatus=" + parameters[4].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
      }  
    if (!"".equals(parameters[5].toString()) && 
      "1".equals(parameters[5].toString())) {
      if (!"".equals(strsql))
        strsql = String.valueOf(strsql) + " and "; 
      if (databaseType.indexOf("mysql") >= 0) {
        strsql = String.valueOf(strsql) + 
          " task.taskEndTime between '" + 
          parameters[6].toString() + 
          "'  and '" + 
          parameters[7].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + 
          " task.taskEndTime between JSDB.FN_STRTODATE('" + 
          parameters[6].toString() + 
          "','S')  and JSDB.FN_STRTODATE('" + 
          parameters[7].toString() + "','S')";
      } 
    } 
    Query query = this.session.createQuery("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskParentId in (" + 
        
        tempHql + 
        ") and task.taskHasPass = 0 and (" + 
        strsql + ") " + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "") + 
        " order by task.parentIdString");
    query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
    query.setMaxResults(volume.intValue());
    List list = query.list();
    Iterator<TaskPO> iterator = list.iterator();
    TaskPO taskPO = null;
    while (iterator != null && iterator.hasNext()) {
      taskPO = iterator.next();
      TaskVO task = (TaskVO)TransformObject.getInstance()
        .transformObject(taskPO, TaskVO.class);
      task.setCanCancel(Boolean.FALSE);
      task.setCanDelete(Boolean.FALSE);
      task.setCanReport(Boolean.FALSE);
      task.setMaintenance(Boolean.FALSE);
      if (task.getTaskStatus().equals(new Integer(0)))
        task.setMaintenance(Boolean.TRUE); 
      result.add(task);
    } 
    return result;
  }
  
  private List searchJoinTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String strsql = "";
    if (!"".equals(parameters[0].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskTitle like '%" + parameters[0].toString() + 
          "%'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
          parameters[0].toString() + "%'";
      }  
    if (!"".equals(parameters[1].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskFinishRate=" + parameters[1].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + parameters[1].toString();
      }  
    if (!"".equals(parameters[2].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskType='" + parameters[2].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
          "'";
      }  
    if (!"".equals(parameters[3].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskPriority=" + parameters[3].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskPriority=" + parameters[3].toString();
      }  
    if (!"".equals(parameters[4].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskStatus=" + parameters[4].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskStatus='" + parameters[4].toString();
      }  
    if (!"".equals(parameters[5].toString()) && 
      "1".equals(parameters[5].toString())) {
      if (!"".equals(strsql))
        strsql = String.valueOf(strsql) + " and "; 
      if (databaseType.indexOf("mysql") >= 0) {
        strsql = String.valueOf(strsql) + 
          " task.taskEndTime between '" + 
          parameters[6].toString() + 
          "'  and '" + 
          parameters[7].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + 
          " task.taskEndTime between JSDB.FN_STRTODATE('" + 
          parameters[6].toString() + 
          "','S')  and JSDB.FN_STRTODATE('" + 
          parameters[7].toString() + "','S')";
      } 
    } 
    if ("".equals(strsql))
      strsql = "1=1"; 
    String strsql1 = "";
    if ("".equals(parameters[8].toString())) {
      strsql1 = " order by task.parentIdString";
    } else {
      strsql1 = " order by task." + parameters[8].toString() + " " + 
        parameters[9].toString();
    } 
    String Sql = "";
    if (databaseType.indexOf("db2") >= 0) {
      Sql = 
        "select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp=" + 
        userId + 
        " or task.taskPrincipal = " + userId + 
        " or task.taskJoinedEmp like '%$" + userId + 
        "$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0  )" + 
        " and task.taskFinishRate = 100 and task.taskStatus = 1  and (" + 
        strsql + ") and task.taskDomainId=" + domainId + " " + 
        strsql1;
    } else if (databaseType.indexOf("mysql") >= 0) {
      Sql = 
        "select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp=" + 
        userId + 
        " or task.taskPrincipal = " + userId + 
        " or task.taskJoinedEmp like '%$" + userId + 
        "$%' or concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%'  )" + 
        " and task.taskFinishRate = 100 and task.taskStatus = 1  and (" + 
        strsql + ") and task.taskDomainId=" + domainId + " " + 
        strsql1;
    } else {
      Sql = 
        "select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp=" + 
        userId + 
        " or task.taskPrincipal = " + userId + 
        " or task.taskJoinedEmp like '%$" + userId + 
        "$%' or JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' )" + 
        " and task.taskFinishRate = 100 and task.taskStatus = 1  and (" + 
        strsql + ") and task.taskDomainId=" + domainId + " " + 
        strsql1;
    } 
    Query query = this.session.createQuery(Sql);
    query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
    query.setMaxResults(volume.intValue());
    List list = query.list();
    Iterator<TaskPO> iterator = list.iterator();
    TaskPO taskPO = null;
    while (iterator != null && iterator.hasNext()) {
      taskPO = iterator.next();
      TaskVO task = (TaskVO)TransformObject.getInstance()
        .transformObject(taskPO, TaskVO.class);
      task.setTaskTypeShow("0");
      if (((taskPO.getTaskJoinedEmp() != null && 
        !"".equals(taskPO.getTaskJoinedEmp())) || (
        taskPO.getTaskJoineOrg() != null && 
        !"".equals(taskPO.getTaskJoineOrg()))) && (
        taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0 || (
        "," + taskPO.getTaskJoineOrg() + ",").indexOf("," + userId.toString() + ",") >= 0))
        task.setTaskTypeShow("1"); 
      result.add(task);
    } 
    return result;
  }
  
  private List searchPrincipalTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws Exception {
    return searchPrincipalTask(userId, parameters, currentPage, volume, domainId, (String)null);
  }
  
  public List searchPrincipalTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId, String toUserId) throws Exception {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #7
    //   9: ldc ''
    //   11: astore #8
    //   13: aload #6
    //   15: ifnull -> 201
    //   18: new java/lang/StringBuffer
    //   21: dup
    //   22: new java/lang/StringBuilder
    //   25: dup
    //   26: ldc_w 'select distinct po.taskId from com.js.oa.scheme.taskcenter.po.TaskPO po where po.createdEmp='
    //   29: invokespecial <init> : (Ljava/lang/String;)V
    //   32: aload_1
    //   33: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   36: ldc_w ' and po.taskDomainId='
    //   39: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: aload #5
    //   44: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   47: invokevirtual toString : ()Ljava/lang/String;
    //   50: invokespecial <init> : (Ljava/lang/String;)V
    //   53: astore #9
    //   55: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   58: ldc_w 'mysql'
    //   61: invokevirtual indexOf : (Ljava/lang/String;)I
    //   64: iflt -> 100
    //   67: aload #9
    //   69: new java/lang/StringBuilder
    //   72: dup
    //   73: ldc_w ' and (concat('$',replace(po.taskJoineOrg,',','$$'),'$') like '%$'
    //   76: invokespecial <init> : (Ljava/lang/String;)V
    //   79: aload #6
    //   81: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: ldc_w '$%' '
    //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: invokevirtual toString : ()Ljava/lang/String;
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   96: pop
    //   97: goto -> 130
    //   100: aload #9
    //   102: new java/lang/StringBuilder
    //   105: dup
    //   106: ldc_w ' and (JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR('$',JSDB.FN_replace(po.taskJoineOrg,',','$$')),'$') like '%$'
    //   109: invokespecial <init> : (Ljava/lang/String;)V
    //   112: aload #6
    //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: ldc_w '$%' '
    //   120: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: invokevirtual toString : ()Ljava/lang/String;
    //   126: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   129: pop
    //   130: aload #9
    //   132: new java/lang/StringBuilder
    //   135: dup
    //   136: ldc_w ' or po.taskJoinedEmp like '%$'
    //   139: invokespecial <init> : (Ljava/lang/String;)V
    //   142: aload #6
    //   144: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   147: ldc_w '$%' or po.taskPrincipal='
    //   150: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: aload #6
    //   155: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   158: ldc_w ' or po.taskChecker='
    //   161: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: aload #6
    //   166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   169: ldc_w ' ) '
    //   172: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: invokevirtual toString : ()Ljava/lang/String;
    //   178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   181: pop
    //   182: aload #9
    //   184: ldc_w ' order by po.taskCreatedTime desc '
    //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   190: pop
    //   191: aload #9
    //   193: invokevirtual toString : ()Ljava/lang/String;
    //   196: astore #8
    //   198: goto -> 381
    //   201: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   204: ldc_w 'db2'
    //   207: invokevirtual indexOf : (Ljava/lang/String;)I
    //   210: iflt -> 266
    //   213: new java/lang/StringBuilder
    //   216: dup
    //   217: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4))  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   220: invokespecial <init> : (Ljava/lang/String;)V
    //   223: aload_1
    //   224: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   227: ldc_w ' or task.taskJoinedEmp like '%$'
    //   230: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   233: aload_1
    //   234: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   237: ldc_w '$%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   240: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: aload_1
    //   244: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   247: ldc_w ',' )>0   ) and task.taskDomainId = '
    //   250: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: aload #5
    //   255: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   258: invokevirtual toString : ()Ljava/lang/String;
    //   261: astore #8
    //   263: goto -> 381
    //   266: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   269: ldc_w 'mysql'
    //   272: invokevirtual indexOf : (Ljava/lang/String;)I
    //   275: iflt -> 331
    //   278: new java/lang/StringBuilder
    //   281: dup
    //   282: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4))  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   285: invokespecial <init> : (Ljava/lang/String;)V
    //   288: aload_1
    //   289: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   292: ldc_w ' or task.taskJoinedEmp like '%$'
    //   295: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: aload_1
    //   299: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   302: ldc_w '$%' or concat(',', task.taskJoineOrg,',') like  '%,'
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: aload_1
    //   309: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   312: ldc_w ',%'  ) and task.taskDomainId = '
    //   315: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   318: aload #5
    //   320: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   323: invokevirtual toString : ()Ljava/lang/String;
    //   326: astore #8
    //   328: goto -> 381
    //   331: new java/lang/StringBuilder
    //   334: dup
    //   335: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4))  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   338: invokespecial <init> : (Ljava/lang/String;)V
    //   341: aload_1
    //   342: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   345: ldc_w ' or task.taskJoinedEmp like '%$'
    //   348: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   351: aload_1
    //   352: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   355: ldc_w '$%' or JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   358: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   361: aload_1
    //   362: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   365: ldc_w ',%'  ) and task.taskDomainId = '
    //   368: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   371: aload #5
    //   373: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   376: invokevirtual toString : ()Ljava/lang/String;
    //   379: astore #8
    //   381: ldc ''
    //   383: astore #9
    //   385: ldc ''
    //   387: aload_2
    //   388: iconst_0
    //   389: aaload
    //   390: invokevirtual toString : ()Ljava/lang/String;
    //   393: invokevirtual equals : (Ljava/lang/Object;)Z
    //   396: ifne -> 480
    //   399: ldc ''
    //   401: aload #9
    //   403: invokevirtual equals : (Ljava/lang/Object;)Z
    //   406: ifeq -> 442
    //   409: new java/lang/StringBuilder
    //   412: dup
    //   413: ldc_w 'task.taskTitle like '%'
    //   416: invokespecial <init> : (Ljava/lang/String;)V
    //   419: aload_2
    //   420: iconst_0
    //   421: aaload
    //   422: invokevirtual toString : ()Ljava/lang/String;
    //   425: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   428: ldc_w '%''
    //   431: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   434: invokevirtual toString : ()Ljava/lang/String;
    //   437: astore #9
    //   439: goto -> 480
    //   442: new java/lang/StringBuilder
    //   445: dup
    //   446: aload #9
    //   448: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   451: invokespecial <init> : (Ljava/lang/String;)V
    //   454: ldc_w ' and task.taskTitle like '%'
    //   457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   460: aload_2
    //   461: iconst_0
    //   462: aaload
    //   463: invokevirtual toString : ()Ljava/lang/String;
    //   466: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   469: ldc_w '%''
    //   472: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   475: invokevirtual toString : ()Ljava/lang/String;
    //   478: astore #9
    //   480: ldc ''
    //   482: aload_2
    //   483: iconst_1
    //   484: aaload
    //   485: invokevirtual toString : ()Ljava/lang/String;
    //   488: invokevirtual equals : (Ljava/lang/Object;)Z
    //   491: ifne -> 563
    //   494: ldc ''
    //   496: aload #9
    //   498: invokevirtual equals : (Ljava/lang/Object;)Z
    //   501: ifeq -> 531
    //   504: new java/lang/StringBuilder
    //   507: dup
    //   508: ldc_w 'task.taskFinishRate='
    //   511: invokespecial <init> : (Ljava/lang/String;)V
    //   514: aload_2
    //   515: iconst_1
    //   516: aaload
    //   517: invokevirtual toString : ()Ljava/lang/String;
    //   520: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   523: invokevirtual toString : ()Ljava/lang/String;
    //   526: astore #9
    //   528: goto -> 563
    //   531: new java/lang/StringBuilder
    //   534: dup
    //   535: aload #9
    //   537: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   540: invokespecial <init> : (Ljava/lang/String;)V
    //   543: ldc_w ' and task.taskFinishRate='
    //   546: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   549: aload_2
    //   550: iconst_1
    //   551: aaload
    //   552: invokevirtual toString : ()Ljava/lang/String;
    //   555: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   558: invokevirtual toString : ()Ljava/lang/String;
    //   561: astore #9
    //   563: ldc ''
    //   565: aload_2
    //   566: iconst_2
    //   567: aaload
    //   568: invokevirtual toString : ()Ljava/lang/String;
    //   571: invokevirtual equals : (Ljava/lang/Object;)Z
    //   574: ifne -> 656
    //   577: ldc ''
    //   579: aload #9
    //   581: invokevirtual equals : (Ljava/lang/Object;)Z
    //   584: ifeq -> 619
    //   587: new java/lang/StringBuilder
    //   590: dup
    //   591: ldc_w 'task.taskType=''
    //   594: invokespecial <init> : (Ljava/lang/String;)V
    //   597: aload_2
    //   598: iconst_2
    //   599: aaload
    //   600: invokevirtual toString : ()Ljava/lang/String;
    //   603: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   606: ldc '''
    //   608: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   611: invokevirtual toString : ()Ljava/lang/String;
    //   614: astore #9
    //   616: goto -> 656
    //   619: new java/lang/StringBuilder
    //   622: dup
    //   623: aload #9
    //   625: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   628: invokespecial <init> : (Ljava/lang/String;)V
    //   631: ldc_w ' and task.taskType=''
    //   634: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   637: aload_2
    //   638: iconst_2
    //   639: aaload
    //   640: invokevirtual toString : ()Ljava/lang/String;
    //   643: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   646: ldc '''
    //   648: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   651: invokevirtual toString : ()Ljava/lang/String;
    //   654: astore #9
    //   656: ldc ''
    //   658: aload_2
    //   659: iconst_3
    //   660: aaload
    //   661: invokevirtual toString : ()Ljava/lang/String;
    //   664: invokevirtual equals : (Ljava/lang/Object;)Z
    //   667: ifne -> 739
    //   670: ldc ''
    //   672: aload #9
    //   674: invokevirtual equals : (Ljava/lang/Object;)Z
    //   677: ifeq -> 707
    //   680: new java/lang/StringBuilder
    //   683: dup
    //   684: ldc_w 'task.taskPriority='
    //   687: invokespecial <init> : (Ljava/lang/String;)V
    //   690: aload_2
    //   691: iconst_3
    //   692: aaload
    //   693: invokevirtual toString : ()Ljava/lang/String;
    //   696: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   699: invokevirtual toString : ()Ljava/lang/String;
    //   702: astore #9
    //   704: goto -> 739
    //   707: new java/lang/StringBuilder
    //   710: dup
    //   711: aload #9
    //   713: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   716: invokespecial <init> : (Ljava/lang/String;)V
    //   719: ldc_w ' and task.taskPriority='
    //   722: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   725: aload_2
    //   726: iconst_3
    //   727: aaload
    //   728: invokevirtual toString : ()Ljava/lang/String;
    //   731: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   734: invokevirtual toString : ()Ljava/lang/String;
    //   737: astore #9
    //   739: ldc ''
    //   741: aload_2
    //   742: iconst_4
    //   743: aaload
    //   744: invokevirtual toString : ()Ljava/lang/String;
    //   747: invokevirtual equals : (Ljava/lang/Object;)Z
    //   750: ifne -> 822
    //   753: ldc ''
    //   755: aload #9
    //   757: invokevirtual equals : (Ljava/lang/Object;)Z
    //   760: ifeq -> 790
    //   763: new java/lang/StringBuilder
    //   766: dup
    //   767: ldc_w 'task.taskStatus='
    //   770: invokespecial <init> : (Ljava/lang/String;)V
    //   773: aload_2
    //   774: iconst_4
    //   775: aaload
    //   776: invokevirtual toString : ()Ljava/lang/String;
    //   779: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   782: invokevirtual toString : ()Ljava/lang/String;
    //   785: astore #9
    //   787: goto -> 822
    //   790: new java/lang/StringBuilder
    //   793: dup
    //   794: aload #9
    //   796: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   799: invokespecial <init> : (Ljava/lang/String;)V
    //   802: ldc_w ' and task.taskStatus='
    //   805: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   808: aload_2
    //   809: iconst_4
    //   810: aaload
    //   811: invokevirtual toString : ()Ljava/lang/String;
    //   814: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   817: invokevirtual toString : ()Ljava/lang/String;
    //   820: astore #9
    //   822: ldc ''
    //   824: aload_2
    //   825: bipush #10
    //   827: aaload
    //   828: invokevirtual toString : ()Ljava/lang/String;
    //   831: invokevirtual equals : (Ljava/lang/Object;)Z
    //   834: ifne -> 920
    //   837: ldc ''
    //   839: aload #9
    //   841: invokevirtual equals : (Ljava/lang/Object;)Z
    //   844: ifeq -> 881
    //   847: new java/lang/StringBuilder
    //   850: dup
    //   851: ldc_w 'task.taskJoineOrg like '%,'
    //   854: invokespecial <init> : (Ljava/lang/String;)V
    //   857: aload_2
    //   858: bipush #10
    //   860: aaload
    //   861: invokevirtual toString : ()Ljava/lang/String;
    //   864: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   867: ldc_w ',%' '
    //   870: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   873: invokevirtual toString : ()Ljava/lang/String;
    //   876: astore #9
    //   878: goto -> 920
    //   881: new java/lang/StringBuilder
    //   884: dup
    //   885: aload #9
    //   887: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   890: invokespecial <init> : (Ljava/lang/String;)V
    //   893: ldc_w ' and task.taskJoineOrg '%,'
    //   896: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   899: aload_2
    //   900: bipush #10
    //   902: aaload
    //   903: invokevirtual toString : ()Ljava/lang/String;
    //   906: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   909: ldc_w ',% '
    //   912: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   915: invokevirtual toString : ()Ljava/lang/String;
    //   918: astore #9
    //   920: ldc ''
    //   922: aload_2
    //   923: bipush #11
    //   925: aaload
    //   926: invokevirtual toString : ()Ljava/lang/String;
    //   929: invokevirtual equals : (Ljava/lang/Object;)Z
    //   932: ifne -> 1018
    //   935: ldc ''
    //   937: aload #9
    //   939: invokevirtual equals : (Ljava/lang/Object;)Z
    //   942: ifeq -> 979
    //   945: new java/lang/StringBuilder
    //   948: dup
    //   949: ldc_w 'task.taskJoinedEmp like '%'
    //   952: invokespecial <init> : (Ljava/lang/String;)V
    //   955: aload_2
    //   956: bipush #11
    //   958: aaload
    //   959: invokevirtual toString : ()Ljava/lang/String;
    //   962: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   965: ldc_w '%' '
    //   968: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   971: invokevirtual toString : ()Ljava/lang/String;
    //   974: astore #9
    //   976: goto -> 1018
    //   979: new java/lang/StringBuilder
    //   982: dup
    //   983: aload #9
    //   985: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   988: invokespecial <init> : (Ljava/lang/String;)V
    //   991: ldc_w 'and task.taskJoinedEmp like '%'
    //   994: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   997: aload_2
    //   998: bipush #11
    //   1000: aaload
    //   1001: invokevirtual toString : ()Ljava/lang/String;
    //   1004: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1007: ldc_w '%' '
    //   1010: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1013: invokevirtual toString : ()Ljava/lang/String;
    //   1016: astore #9
    //   1018: ldc ''
    //   1020: aload_2
    //   1021: iconst_5
    //   1022: aaload
    //   1023: invokevirtual toString : ()Ljava/lang/String;
    //   1026: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1029: ifne -> 1294
    //   1032: ldc_w '1'
    //   1035: aload_2
    //   1036: iconst_5
    //   1037: aaload
    //   1038: invokevirtual toString : ()Ljava/lang/String;
    //   1041: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1044: ifeq -> 1294
    //   1047: invokestatic getDatabaseType : ()Ljava/lang/String;
    //   1050: astore #10
    //   1052: aload #10
    //   1054: ldc_w 'mysql'
    //   1057: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1060: iflt -> 1179
    //   1063: ldc ''
    //   1065: aload #9
    //   1067: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1070: ifeq -> 1122
    //   1073: new java/lang/StringBuilder
    //   1076: dup
    //   1077: ldc_w 'task.taskEndTime between ''
    //   1080: invokespecial <init> : (Ljava/lang/String;)V
    //   1083: aload_2
    //   1084: bipush #6
    //   1086: aaload
    //   1087: invokevirtual toString : ()Ljava/lang/String;
    //   1090: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1093: ldc_w ''  and ''
    //   1096: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1099: aload_2
    //   1100: bipush #7
    //   1102: aaload
    //   1103: invokevirtual toString : ()Ljava/lang/String;
    //   1106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1109: ldc '''
    //   1111: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1114: invokevirtual toString : ()Ljava/lang/String;
    //   1117: astore #9
    //   1119: goto -> 1294
    //   1122: new java/lang/StringBuilder
    //   1125: dup
    //   1126: aload #9
    //   1128: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1131: invokespecial <init> : (Ljava/lang/String;)V
    //   1134: ldc_w ' and task.taskEndTime between ''
    //   1137: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1140: aload_2
    //   1141: bipush #6
    //   1143: aaload
    //   1144: invokevirtual toString : ()Ljava/lang/String;
    //   1147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1150: ldc_w ''  and ''
    //   1153: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1156: aload_2
    //   1157: bipush #7
    //   1159: aaload
    //   1160: invokevirtual toString : ()Ljava/lang/String;
    //   1163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1166: ldc '''
    //   1168: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1171: invokevirtual toString : ()Ljava/lang/String;
    //   1174: astore #9
    //   1176: goto -> 1294
    //   1179: ldc ''
    //   1181: aload #9
    //   1183: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1186: ifeq -> 1239
    //   1189: new java/lang/StringBuilder
    //   1192: dup
    //   1193: ldc_w 'task.taskEndTime between JSDB.FN_STRTODATE(''
    //   1196: invokespecial <init> : (Ljava/lang/String;)V
    //   1199: aload_2
    //   1200: bipush #6
    //   1202: aaload
    //   1203: invokevirtual toString : ()Ljava/lang/String;
    //   1206: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1209: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   1212: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1215: aload_2
    //   1216: bipush #7
    //   1218: aaload
    //   1219: invokevirtual toString : ()Ljava/lang/String;
    //   1222: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1225: ldc_w '','S')'
    //   1228: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1231: invokevirtual toString : ()Ljava/lang/String;
    //   1234: astore #9
    //   1236: goto -> 1294
    //   1239: new java/lang/StringBuilder
    //   1242: dup
    //   1243: aload #9
    //   1245: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1248: invokespecial <init> : (Ljava/lang/String;)V
    //   1251: ldc_w ' and task.taskEndTime between JSDB.FN_STRTODATE(''
    //   1254: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1257: aload_2
    //   1258: bipush #6
    //   1260: aaload
    //   1261: invokevirtual toString : ()Ljava/lang/String;
    //   1264: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1267: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   1270: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1273: aload_2
    //   1274: bipush #7
    //   1276: aaload
    //   1277: invokevirtual toString : ()Ljava/lang/String;
    //   1280: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1283: ldc_w '','S')'
    //   1286: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1289: invokevirtual toString : ()Ljava/lang/String;
    //   1292: astore #9
    //   1294: ldc ''
    //   1296: aload #9
    //   1298: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1301: ifeq -> 1309
    //   1304: ldc_w '1=1'
    //   1307: astore #9
    //   1309: ldc ''
    //   1311: astore #10
    //   1313: ldc ''
    //   1315: aload_2
    //   1316: bipush #8
    //   1318: aaload
    //   1319: invokevirtual toString : ()Ljava/lang/String;
    //   1322: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1325: ifeq -> 1336
    //   1328: ldc_w ' order by task.parentIdString'
    //   1331: astore #10
    //   1333: goto -> 1377
    //   1336: new java/lang/StringBuilder
    //   1339: dup
    //   1340: ldc_w ' order by task.'
    //   1343: invokespecial <init> : (Ljava/lang/String;)V
    //   1346: aload_2
    //   1347: bipush #8
    //   1349: aaload
    //   1350: invokevirtual toString : ()Ljava/lang/String;
    //   1353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1356: ldc_w ' '
    //   1359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1362: aload_2
    //   1363: bipush #9
    //   1365: aaload
    //   1366: invokevirtual toString : ()Ljava/lang/String;
    //   1369: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1372: invokevirtual toString : ()Ljava/lang/String;
    //   1375: astore #10
    //   1377: ldc ''
    //   1379: astore #11
    //   1381: aload #6
    //   1383: ifnull -> 1412
    //   1386: new java/lang/StringBuilder
    //   1389: dup
    //   1390: ldc_w ' and task.taskId in ('
    //   1393: invokespecial <init> : (Ljava/lang/String;)V
    //   1396: aload #8
    //   1398: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1401: ldc_w ') '
    //   1404: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1407: invokevirtual toString : ()Ljava/lang/String;
    //   1410: astore #11
    //   1412: aload_0
    //   1413: getfield session : Lnet/sf/hibernate/Session;
    //   1416: new java/lang/StringBuilder
    //   1419: dup
    //   1420: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in ('
    //   1423: invokespecial <init> : (Ljava/lang/String;)V
    //   1426: aload #8
    //   1428: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1431: ldc_w ')'
    //   1434: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1437: aload #11
    //   1439: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1442: ldc_w 'and task.taskFinishRate<100 and ('
    //   1445: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1448: aload #9
    //   1450: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1453: ldc_w ') and task.taskDomainId = '
    //   1456: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1459: aload #5
    //   1461: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1464: aload #10
    //   1466: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1469: invokevirtual toString : ()Ljava/lang/String;
    //   1472: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   1477: astore #12
    //   1479: aload #12
    //   1481: aload_3
    //   1482: invokevirtual intValue : ()I
    //   1485: iconst_1
    //   1486: isub
    //   1487: aload #4
    //   1489: invokevirtual intValue : ()I
    //   1492: imul
    //   1493: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   1498: pop
    //   1499: aload #12
    //   1501: aload #4
    //   1503: invokevirtual intValue : ()I
    //   1506: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   1511: pop
    //   1512: aload #12
    //   1514: invokeinterface list : ()Ljava/util/List;
    //   1519: invokeinterface iterator : ()Ljava/util/Iterator;
    //   1524: astore #13
    //   1526: aconst_null
    //   1527: astore #14
    //   1529: goto -> 2034
    //   1532: aload #13
    //   1534: invokeinterface next : ()Ljava/lang/Object;
    //   1539: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   1542: astore #14
    //   1544: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   1547: aload #14
    //   1549: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   1552: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   1555: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   1558: astore #15
    //   1560: aload #15
    //   1562: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1565: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   1568: aload #15
    //   1570: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1573: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1576: aload #15
    //   1578: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1581: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1584: aload #15
    //   1586: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1589: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   1592: aload #14
    //   1594: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1597: aload_1
    //   1598: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1601: ifne -> 1616
    //   1604: aload #14
    //   1606: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1609: aload_1
    //   1610: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1613: ifeq -> 1632
    //   1616: aload #15
    //   1618: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1621: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1624: aload #15
    //   1626: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1629: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   1632: aload #15
    //   1634: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   1637: new java/lang/Integer
    //   1640: dup
    //   1641: iconst_1
    //   1642: invokespecial <init> : (I)V
    //   1645: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1648: ifeq -> 1671
    //   1651: aload #14
    //   1653: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1656: aload_1
    //   1657: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1660: ifeq -> 1671
    //   1663: aload #15
    //   1665: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1668: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   1671: aload #14
    //   1673: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1676: aload_1
    //   1677: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1680: ifne -> 1804
    //   1683: aload #14
    //   1685: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1688: ifnull -> 1725
    //   1691: aload #14
    //   1693: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1696: new java/lang/StringBuilder
    //   1699: dup
    //   1700: ldc_w '$'
    //   1703: invokespecial <init> : (Ljava/lang/String;)V
    //   1706: aload_1
    //   1707: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1710: ldc_w '$'
    //   1713: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1716: invokevirtual toString : ()Ljava/lang/String;
    //   1719: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1722: ifge -> 1804
    //   1725: aload #14
    //   1727: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1730: ifnull -> 1792
    //   1733: new java/lang/StringBuilder
    //   1736: dup
    //   1737: ldc_w ','
    //   1740: invokespecial <init> : (Ljava/lang/String;)V
    //   1743: aload #14
    //   1745: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1748: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1751: ldc_w ','
    //   1754: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1757: invokevirtual toString : ()Ljava/lang/String;
    //   1760: new java/lang/StringBuilder
    //   1763: dup
    //   1764: ldc_w ','
    //   1767: invokespecial <init> : (Ljava/lang/String;)V
    //   1770: aload_1
    //   1771: invokevirtual toString : ()Ljava/lang/String;
    //   1774: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1777: ldc_w ','
    //   1780: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1783: invokevirtual toString : ()Ljava/lang/String;
    //   1786: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1789: ifge -> 1804
    //   1792: aload #14
    //   1794: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1797: aload_1
    //   1798: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1801: ifeq -> 1829
    //   1804: ldc_w '1'
    //   1807: aload #14
    //   1809: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   1812: invokevirtual toString : ()Ljava/lang/String;
    //   1815: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1818: ifeq -> 1829
    //   1821: aload #15
    //   1823: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1826: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1829: aload #15
    //   1831: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1834: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1837: aload #14
    //   1839: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1842: aload_1
    //   1843: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1846: ifeq -> 1857
    //   1849: aload #15
    //   1851: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1854: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1857: aload #15
    //   1859: ldc_w '0'
    //   1862: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   1865: aload #14
    //   1867: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1870: ifnull -> 1886
    //   1873: ldc ''
    //   1875: aload #14
    //   1877: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1880: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1883: ifeq -> 1907
    //   1886: aload #14
    //   1888: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1891: ifnull -> 2024
    //   1894: ldc ''
    //   1896: aload #14
    //   1898: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1901: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1904: ifne -> 2024
    //   1907: aload #14
    //   1909: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1912: ifnull -> 1949
    //   1915: aload #14
    //   1917: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1920: new java/lang/StringBuilder
    //   1923: dup
    //   1924: ldc_w '$'
    //   1927: invokespecial <init> : (Ljava/lang/String;)V
    //   1930: aload_1
    //   1931: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1934: ldc_w '$'
    //   1937: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1940: invokevirtual toString : ()Ljava/lang/String;
    //   1943: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1946: ifge -> 2016
    //   1949: aload #14
    //   1951: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1954: ifnull -> 2024
    //   1957: new java/lang/StringBuilder
    //   1960: dup
    //   1961: ldc_w ','
    //   1964: invokespecial <init> : (Ljava/lang/String;)V
    //   1967: aload #14
    //   1969: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1972: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1975: ldc_w ','
    //   1978: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1981: invokevirtual toString : ()Ljava/lang/String;
    //   1984: new java/lang/StringBuilder
    //   1987: dup
    //   1988: ldc_w ','
    //   1991: invokespecial <init> : (Ljava/lang/String;)V
    //   1994: aload_1
    //   1995: invokevirtual toString : ()Ljava/lang/String;
    //   1998: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2001: ldc_w ','
    //   2004: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2007: invokevirtual toString : ()Ljava/lang/String;
    //   2010: invokevirtual indexOf : (Ljava/lang/String;)I
    //   2013: iflt -> 2024
    //   2016: aload #15
    //   2018: ldc_w '1'
    //   2021: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   2024: aload #7
    //   2026: aload #15
    //   2028: invokeinterface add : (Ljava/lang/Object;)Z
    //   2033: pop
    //   2034: aload #13
    //   2036: ifnull -> 2049
    //   2039: aload #13
    //   2041: invokeinterface hasNext : ()Z
    //   2046: ifne -> 1532
    //   2049: aload #7
    //   2051: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #4603	-> 0
    //   #4606	-> 9
    //   #4607	-> 13
    //   #4608	-> 18
    //   #4609	-> 55
    //   #4610	-> 67
    //   #4613	-> 100
    //   #4616	-> 130
    //   #4617	-> 182
    //   #4618	-> 191
    //   #4621	-> 201
    //   #4623	-> 213
    //   #4624	-> 223
    //   #4625	-> 227
    //   #4626	-> 233
    //   #4627	-> 237
    //   #4628	-> 253
    //   #4623	-> 258
    //   #4630	-> 266
    //   #4632	-> 278
    //   #4633	-> 288
    //   #4634	-> 292
    //   #4635	-> 298
    //   #4636	-> 302
    //   #4637	-> 318
    //   #4632	-> 323
    //   #4641	-> 331
    //   #4642	-> 341
    //   #4643	-> 345
    //   #4644	-> 351
    //   #4645	-> 355
    //   #4646	-> 371
    //   #4641	-> 376
    //   #4652	-> 381
    //   #4653	-> 385
    //   #4654	-> 399
    //   #4655	-> 409
    //   #4656	-> 428
    //   #4655	-> 434
    //   #4658	-> 442
    //   #4659	-> 460
    //   #4658	-> 475
    //   #4662	-> 480
    //   #4663	-> 494
    //   #4664	-> 504
    //   #4667	-> 531
    //   #4668	-> 549
    //   #4667	-> 558
    //   #4672	-> 563
    //   #4673	-> 577
    //   #4674	-> 587
    //   #4676	-> 619
    //   #4677	-> 646
    //   #4676	-> 651
    //   #4680	-> 656
    //   #4681	-> 670
    //   #4682	-> 680
    //   #4685	-> 707
    //   #4686	-> 725
    //   #4685	-> 734
    //   #4689	-> 739
    //   #4690	-> 753
    //   #4691	-> 763
    //   #4694	-> 790
    //   #4698	-> 822
    //   #4699	-> 837
    //   #4700	-> 847
    //   #4702	-> 881
    //   #4705	-> 920
    //   #4706	-> 935
    //   #4707	-> 945
    //   #4709	-> 979
    //   #4712	-> 1018
    //   #4713	-> 1032
    //   #4715	-> 1047
    //   #4714	-> 1050
    //   #4716	-> 1052
    //   #4717	-> 1063
    //   #4719	-> 1073
    //   #4720	-> 1083
    //   #4721	-> 1093
    //   #4722	-> 1099
    //   #4719	-> 1114
    //   #4718	-> 1117
    //   #4724	-> 1122
    //   #4725	-> 1134
    //   #4726	-> 1140
    //   #4727	-> 1150
    //   #4728	-> 1156
    //   #4724	-> 1171
    //   #4731	-> 1179
    //   #4733	-> 1189
    //   #4734	-> 1199
    //   #4735	-> 1209
    //   #4736	-> 1215
    //   #4733	-> 1231
    //   #4732	-> 1234
    //   #4738	-> 1239
    //   #4739	-> 1251
    //   #4740	-> 1257
    //   #4741	-> 1267
    //   #4742	-> 1273
    //   #4738	-> 1289
    //   #4747	-> 1294
    //   #4748	-> 1304
    //   #4751	-> 1309
    //   #4752	-> 1313
    //   #4753	-> 1328
    //   #4755	-> 1336
    //   #4756	-> 1362
    //   #4755	-> 1372
    //   #4758	-> 1377
    //   #4759	-> 1381
    //   #4760	-> 1386
    //   #4762	-> 1412
    //   #4764	-> 1426
    //   #4765	-> 1431
    //   #4766	-> 1448
    //   #4767	-> 1453
    //   #4768	-> 1459
    //   #4762	-> 1472
    //   #4769	-> 1479
    //   #4770	-> 1499
    //   #4771	-> 1512
    //   #4772	-> 1526
    //   #4816	-> 1529
    //   #4817	-> 1532
    //   #4818	-> 1544
    //   #4819	-> 1547
    //   #4818	-> 1555
    //   #4820	-> 1560
    //   #4821	-> 1568
    //   #4822	-> 1576
    //   #4823	-> 1584
    //   #4826	-> 1592
    //   #4827	-> 1604
    //   #4828	-> 1616
    //   #4829	-> 1624
    //   #4832	-> 1632
    //   #4833	-> 1651
    //   #4834	-> 1663
    //   #4837	-> 1671
    //   #4838	-> 1683
    //   #4839	-> 1691
    //   #4841	-> 1725
    //   #4842	-> 1733
    //   #4844	-> 1792
    //   #4845	-> 1804
    //   #4846	-> 1821
    //   #4849	-> 1829
    //   #4850	-> 1837
    //   #4851	-> 1849
    //   #4853	-> 1857
    //   #4854	-> 1865
    //   #4855	-> 1873
    //   #4856	-> 1886
    //   #4857	-> 1894
    //   #4858	-> 1907
    //   #4859	-> 1915
    //   #4860	-> 1917
    //   #4861	-> 1949
    //   #4862	-> 1957
    //   #4864	-> 2016
    //   #4867	-> 2024
    //   #4816	-> 2034
    //   #4871	-> 2049
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	2052	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	2052	1	userId	Ljava/lang/Long;
    //   0	2052	2	parameters	[Ljava/lang/String;
    //   0	2052	3	currentPage	Ljava/lang/Integer;
    //   0	2052	4	volume	Ljava/lang/Integer;
    //   0	2052	5	domainId	Ljava/lang/Long;
    //   0	2052	6	toUserId	Ljava/lang/String;
    //   9	2043	7	result	Ljava/util/List;
    //   13	2039	8	Sql	Ljava/lang/String;
    //   55	146	9	hql	Ljava/lang/StringBuffer;
    //   385	1667	9	strsql	Ljava/lang/String;
    //   1052	242	10	dataType	Ljava/lang/String;
    //   1313	739	10	strsql1	Ljava/lang/String;
    //   1381	671	11	taskId	Ljava/lang/String;
    //   1479	573	12	query	Lnet/sf/hibernate/Query;
    //   1526	526	13	iterator	Ljava/util/Iterator;
    //   1529	523	14	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   1560	474	15	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
  }
  
  public Map selectSingleTask(Long userId, Long taskId) throws HibernateException {
    Map<Object, Object> map = new HashMap<Object, Object>();
    TaskVO taskVO = null;
    try {
      begin();
      TaskPO taskPO = (TaskPO)this.session.load(TaskPO.class, taskId);
      taskVO = (TaskVO)TransformObject.getInstance().transformObject(
          taskPO, TaskVO.class);
      taskVO.setTaskPO(taskPO);
      taskVO.setMaintenance(Boolean.FALSE);
      Long parentPrincipal = new Long(0L);
      if (!taskPO.getTaskParentId().equals(new Long(0L))) {
        TaskPO parent = (TaskPO)this.session.load(TaskPO.class, 
            taskPO.getTaskBaseId());
        parentPrincipal = parent.getTaskPrincipal();
      } 
      if (userId.equals(taskVO.getCreatedEmp()) || (
        !parentPrincipal.equals(new Long(0L)) && 
        userId.equals(parentPrincipal)) || 
        userId.equals(taskVO.getTaskPrincipal()))
        taskVO.setMaintenance(Boolean.TRUE); 
      map.put("taskVO", taskVO);
      Query query = this.session.createQuery("select accessory.accessoryname,accessory.accessorysavename from com.js.oa.scheme.taskcenter.po.TaskPO taskPO join taskPO.taskAccessory accessory where taskPO.taskId = " + 
          taskId);
      map.put("accessory", query.list());
    } catch (HibernateException ex) {
      System.out.println("select Single Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return map;
  }
  
  public TaskVO selectSettleSingleTask(Long userId, Long taskId, Long domainId) throws Exception {
    TaskVO result = null;
    try {
      begin();
      TaskPO taskPO = (TaskPO)this.session.load(TaskPO.class, taskId);
      result = (TaskVO)TransformObject.getInstance().transformObject(
          taskPO, TaskVO.class);
      result.setTaskPO(taskPO);
      result.setMaintenance(Boolean.FALSE);
      Long parentPrincipal = new Long(0L);
      if (!taskPO.getTaskParentId().equals(new Long(0L))) {
        TaskPO parent = (TaskPO)this.session.load(TaskPO.class, 
            taskPO.getTaskParentId());
        parentPrincipal = parent.getTaskPrincipal();
      } 
      if (userId.equals(result.getCreatedEmp()) && 
        result.getTaskStatus().equals(new Integer(0)))
        result.setMaintenance(Boolean.TRUE); 
      if (userId.equals(result.getCreatedEmp()) && 
        result.getTaskHasPass().equals(new Integer(0)))
        result.setMaintenance(Boolean.TRUE); 
      if (result.getTaskHasPass().equals(new Integer(0)) || 
        result.getTaskStatus().equals(new Long(2L)))
        result.setMaintenance(Boolean.FALSE); 
    } catch (HibernateException ex) {
      System.out.println("select Single Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Map selectTaskReport(Long userId, Long taskId, Long domainId) throws HibernateException {
    List<TaskReportVO> list = new ArrayList();
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      begin();
      Query query = this.session.createQuery("select taskReport.checkerId,taskReport.finishRate,taskReport.reportTime,taskReport.reportInfo from com.js.oa.scheme.taskcenter.po.TaskReportPO taskReport where taskReport.taskId in (" + 
          taskId + 
          ")  order by taskReport.reportTime");
      Iterator<Object[]> iterator = query.iterate();
      while (iterator != null && iterator.hasNext()) {
        TaskReportVO taskReportVO = new TaskReportVO();
        Object[] obj = iterator.next();
        String checkerId = obj[0].toString();
        taskReportVO.setEmpId(Long.valueOf(checkerId));
        taskReportVO.setEmpName("匿名用户");
        taskReportVO.setFinishRate((obj[1] == null) ? null : Integer.valueOf(obj[1].toString()));
        taskReportVO.setReportTime((Date)obj[2]);
        taskReportVO.setReportInfo((obj[3] == null) ? "" : obj[3].toString());
        if (checkerId != null) {
          Query empNameQuery = this.session.createQuery("select emp.empName from com.js.system.vo.usermanager.EmployeeVO emp where emp.empId =" + 
              checkerId);
          taskReportVO.setEmpName(empNameQuery.list().get(0).toString());
        } 
        list.add(taskReportVO);
        map.put("taskReportList", list);
      } 
      query = this.session.createQuery("select accessory.accessoryname,accessory.accessorysavename from com.js.oa.scheme.taskcenter.po.TaskReportPO taskReportPO join taskReportPO.taskReportAccessory accessory where taskReportPO.taskId = " + 
          taskId);
      map.put("reportaccessory", query.list());
    } catch (HibernateException ex) {
      System.out.println("select Task Report and Checker Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return map;
  }
  
  public Integer getSettleTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      result = this.session.iterate("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp = " + 
          
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))) or" + 
          "(task.taskBaseId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp =" + 
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4))))" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""))
        .next();
    } catch (HibernateException ex) {
      System.out.println("get Settle Task Record Count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectSettleTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String strsql = "";
    try {
      begin();
      if ("".equals(type) || "".equals(sortType)) {
        strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where ((task.taskId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp = " + 
          
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))) or" + 
          "(task.taskBaseId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp =" + 
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))))" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : "") + 
          " order by task.anpaiStickie desc,task.parentIdString desc,task.taskCreatedTime ";
      } else {
        strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where ((task.taskId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp = " + 
          
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))) or" + 
          "(task.taskBaseId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp =" + 
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))))" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : "") + 
          " order by task." + type + " " + sortType;
      } 
      Query query = this.session.createQuery(strsql);
      query.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      query.setMaxResults(volume.intValue());
      Iterator<TaskPO> iterator = query.list().iterator();
      TaskPO taskPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskPO = iterator.next();
        TaskVO task = (TaskVO)TransformObject.getInstance()
          .transformObject(taskPO, TaskVO.class);
        task.setCanCancel(Boolean.FALSE);
        task.setMaintenance(Boolean.TRUE);
        if (task.getTaskOrderCode().intValue() == 0 && 
          task.getTaskStatus().equals(new Integer(1)))
          task.setCanCancel(Boolean.TRUE); 
        task.setTaskTypeShow("0");
        if (taskPO.getTaskJoinedEmp() != null && 
          !"".equals(taskPO.getTaskJoinedEmp()) && 
          taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0)
          task.setTaskTypeShow("1"); 
        if (taskPO.getTaskJoineOrg() != null && 
          !"".equals(taskPO.getTaskJoineOrg()) && (
          "," + taskPO.getTaskJoineOrg() + ",").indexOf("," + userId.toString() + ",") >= 0)
          task.setTaskTypeShow("1"); 
        result.add(task);
      } 
    } catch (HibernateException ex) {
      System.out.println("select Settle Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getSettleCheckTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String strsql = "";
      if (databaseType.indexOf("db2") >= 0) {
        strsql = "select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp=" + 
          userId + 
          " or task.taskPrincipal = " + userId + 
          " or task.taskJoinedEmp like '%$" + userId + 
          "$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0   )" + 
          " and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId=" + 
          domainId;
      } else if (databaseType.indexOf("mysql") >= 0) {
        strsql = "select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp=" + 
          userId + 
          " or task.taskPrincipal = " + userId + 
          " or task.taskJoinedEmp like '%$" + userId + 
          "$%' or  concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%' )" + 
          " and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId=" + 
          domainId;
      } else {
        strsql = "select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp=" + 
          userId + 
          " or task.taskPrincipal = " + userId + 
          " or task.taskJoinedEmp like '%$" + userId + 
          "$%' or JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' )" + 
          " and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId=" + 
          domainId;
      } 
      Query query = this.session.createQuery(strsql);
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("get settle check task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectSettleCheckTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #7
    //   9: ldc ''
    //   11: astore #8
    //   13: aload_0
    //   14: invokevirtual begin : ()V
    //   17: ldc ''
    //   19: aload #5
    //   21: invokevirtual equals : (Ljava/lang/Object;)Z
    //   24: ifne -> 37
    //   27: ldc ''
    //   29: aload #6
    //   31: invokevirtual equals : (Ljava/lang/Object;)Z
    //   34: ifeq -> 70
    //   37: new java/lang/StringBuilder
    //   40: dup
    //   41: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskChecker ='
    //   44: invokespecial <init> : (Ljava/lang/String;)V
    //   47: aload_1
    //   48: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   51: ldc_w ') and task.taskFinishRate = 100 and task.taskStatus = 1 and task.taskDomainId = '
    //   54: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: aload #4
    //   59: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   62: invokevirtual toString : ()Ljava/lang/String;
    //   65: astore #8
    //   67: goto -> 122
    //   70: new java/lang/StringBuilder
    //   73: dup
    //   74: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskChecker ='
    //   77: invokespecial <init> : (Ljava/lang/String;)V
    //   80: aload_1
    //   81: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   84: ldc_w ') and task.taskFinishRate = 100 and task.taskStatus = 1 and task.taskDomainId = '
    //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: aload #4
    //   92: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   95: ldc_w ' order by task.'
    //   98: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: aload #5
    //   103: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: ldc_w ' '
    //   109: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: aload #6
    //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: invokevirtual toString : ()Ljava/lang/String;
    //   120: astore #8
    //   122: aload_0
    //   123: getfield session : Lnet/sf/hibernate/Session;
    //   126: aload #8
    //   128: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   133: astore #9
    //   135: aload #9
    //   137: aload_2
    //   138: invokevirtual intValue : ()I
    //   141: iconst_1
    //   142: isub
    //   143: aload_3
    //   144: invokevirtual intValue : ()I
    //   147: imul
    //   148: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   153: pop
    //   154: aload #9
    //   156: aload_3
    //   157: invokevirtual intValue : ()I
    //   160: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   165: pop
    //   166: aload #9
    //   168: invokeinterface list : ()Ljava/util/List;
    //   173: astore #10
    //   175: aload #10
    //   177: invokeinterface iterator : ()Ljava/util/Iterator;
    //   182: astore #11
    //   184: aconst_null
    //   185: astore #12
    //   187: goto -> 379
    //   190: aload #11
    //   192: invokeinterface next : ()Ljava/lang/Object;
    //   197: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   200: astore #12
    //   202: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   205: aload #12
    //   207: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   210: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   213: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   216: astore #13
    //   218: aload #13
    //   220: ldc_w '0'
    //   223: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   226: aload #12
    //   228: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   231: ifnull -> 281
    //   234: ldc ''
    //   236: aload #12
    //   238: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   241: invokevirtual equals : (Ljava/lang/Object;)Z
    //   244: ifne -> 281
    //   247: aload #12
    //   249: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   252: new java/lang/StringBuilder
    //   255: dup
    //   256: ldc_w '$'
    //   259: invokespecial <init> : (Ljava/lang/String;)V
    //   262: aload_1
    //   263: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   266: ldc_w '$'
    //   269: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   272: invokevirtual toString : ()Ljava/lang/String;
    //   275: invokevirtual indexOf : (Ljava/lang/String;)I
    //   278: ifge -> 361
    //   281: aload #12
    //   283: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   286: ifnull -> 369
    //   289: ldc ''
    //   291: aload #12
    //   293: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   296: invokevirtual equals : (Ljava/lang/Object;)Z
    //   299: ifne -> 369
    //   302: new java/lang/StringBuilder
    //   305: dup
    //   306: ldc_w ','
    //   309: invokespecial <init> : (Ljava/lang/String;)V
    //   312: aload #12
    //   314: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   317: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   320: ldc_w ','
    //   323: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   326: invokevirtual toString : ()Ljava/lang/String;
    //   329: new java/lang/StringBuilder
    //   332: dup
    //   333: ldc_w ','
    //   336: invokespecial <init> : (Ljava/lang/String;)V
    //   339: aload_1
    //   340: invokevirtual toString : ()Ljava/lang/String;
    //   343: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   346: ldc_w ','
    //   349: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   352: invokevirtual toString : ()Ljava/lang/String;
    //   355: invokevirtual indexOf : (Ljava/lang/String;)I
    //   358: iflt -> 369
    //   361: aload #13
    //   363: ldc_w '1'
    //   366: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   369: aload #7
    //   371: aload #13
    //   373: invokeinterface add : (Ljava/lang/Object;)Z
    //   378: pop
    //   379: aload #11
    //   381: ifnull -> 454
    //   384: aload #11
    //   386: invokeinterface hasNext : ()Z
    //   391: ifne -> 190
    //   394: goto -> 454
    //   397: astore #9
    //   399: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   402: new java/lang/StringBuilder
    //   405: dup
    //   406: ldc_w 'Select Settle Check Task Exception: '
    //   409: invokespecial <init> : (Ljava/lang/String;)V
    //   412: aload #9
    //   414: invokevirtual getMessage : ()Ljava/lang/String;
    //   417: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   420: invokevirtual toString : ()Ljava/lang/String;
    //   423: invokevirtual println : (Ljava/lang/String;)V
    //   426: aload #9
    //   428: athrow
    //   429: astore #14
    //   431: aload_0
    //   432: getfield session : Lnet/sf/hibernate/Session;
    //   435: invokeinterface close : ()Ljava/sql/Connection;
    //   440: pop
    //   441: aload_0
    //   442: aconst_null
    //   443: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   446: aload_0
    //   447: aconst_null
    //   448: putfield session : Lnet/sf/hibernate/Session;
    //   451: aload #14
    //   453: athrow
    //   454: aload_0
    //   455: getfield session : Lnet/sf/hibernate/Session;
    //   458: invokeinterface close : ()Ljava/sql/Connection;
    //   463: pop
    //   464: aload_0
    //   465: aconst_null
    //   466: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   469: aload_0
    //   470: aconst_null
    //   471: putfield session : Lnet/sf/hibernate/Session;
    //   474: aload #7
    //   476: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #5306	-> 0
    //   #5307	-> 9
    //   #5309	-> 13
    //   #5310	-> 17
    //   #5318	-> 37
    //   #5319	-> 47
    //   #5320	-> 51
    //   #5321	-> 57
    //   #5318	-> 62
    //   #5317	-> 65
    //   #5330	-> 70
    //   #5331	-> 80
    //   #5332	-> 84
    //   #5333	-> 90
    //   #5330	-> 117
    //   #5329	-> 120
    //   #5337	-> 122
    //   #5338	-> 135
    //   #5339	-> 154
    //   #5340	-> 166
    //   #5341	-> 175
    //   #5342	-> 184
    //   #5343	-> 187
    //   #5344	-> 190
    //   #5345	-> 202
    //   #5346	-> 205
    //   #5345	-> 213
    //   #5348	-> 218
    //   #5349	-> 226
    //   #5350	-> 234
    //   #5351	-> 247
    //   #5353	-> 281
    //   #5354	-> 289
    //   #5355	-> 302
    //   #5361	-> 361
    //   #5365	-> 369
    //   #5343	-> 379
    //   #5367	-> 397
    //   #5368	-> 399
    //   #5369	-> 412
    //   #5368	-> 423
    //   #5370	-> 426
    //   #5371	-> 429
    //   #5372	-> 431
    //   #5373	-> 441
    //   #5374	-> 446
    //   #5376	-> 451
    //   #5372	-> 454
    //   #5373	-> 464
    //   #5374	-> 469
    //   #5377	-> 474
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	477	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	477	1	userId	Ljava/lang/Long;
    //   0	477	2	currentPage	Ljava/lang/Integer;
    //   0	477	3	volume	Ljava/lang/Integer;
    //   0	477	4	domainId	Ljava/lang/Long;
    //   0	477	5	type	Ljava/lang/String;
    //   0	477	6	sortType	Ljava/lang/String;
    //   9	468	7	result	Ljava/util/List;
    //   13	464	8	strsql	Ljava/lang/String;
    //   135	262	9	query	Lnet/sf/hibernate/Query;
    //   175	222	10	list	Ljava/util/List;
    //   184	213	11	iterator	Ljava/util/Iterator;
    //   187	210	12	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   218	161	13	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   399	30	9	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   13	394	397	net/sf/hibernate/HibernateException
    //   13	429	429	finally
  }
  
  public List selectSettleCheckTaskall(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #7
    //   9: ldc ''
    //   11: astore #8
    //   13: aload_0
    //   14: invokevirtual begin : ()V
    //   17: ldc ''
    //   19: aload #5
    //   21: invokevirtual equals : (Ljava/lang/Object;)Z
    //   24: ifne -> 37
    //   27: ldc ''
    //   29: aload #6
    //   31: invokevirtual equals : (Ljava/lang/Object;)Z
    //   34: ifeq -> 316
    //   37: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   40: ldc_w 'db2'
    //   43: invokevirtual indexOf : (Ljava/lang/String;)I
    //   46: iflt -> 134
    //   49: new java/lang/StringBuilder
    //   52: dup
    //   53: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp='
    //   56: invokespecial <init> : (Ljava/lang/String;)V
    //   59: aload_1
    //   60: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   63: ldc_w ' or task.taskPrincipal = '
    //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: aload_1
    //   70: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   73: ldc_w ' or task.taskJoinedEmp like '%$'
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: aload_1
    //   80: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   83: ldc_w '$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   86: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   89: aload_1
    //   90: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   93: ldc_w ',' )>0  ) '
    //   96: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: ldc_w ' and task.taskChecker != '
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: aload_1
    //   106: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   109: ldc_w ' and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId='
    //   112: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   115: aload #4
    //   117: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   120: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: invokevirtual toString : ()Ljava/lang/String;
    //   129: astore #8
    //   131: goto -> 658
    //   134: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   137: ldc_w 'mysql'
    //   140: invokevirtual indexOf : (Ljava/lang/String;)I
    //   143: iflt -> 231
    //   146: new java/lang/StringBuilder
    //   149: dup
    //   150: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp='
    //   153: invokespecial <init> : (Ljava/lang/String;)V
    //   156: aload_1
    //   157: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   160: ldc_w ' or task.taskPrincipal = '
    //   163: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   166: aload_1
    //   167: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   170: ldc_w ' or task.taskJoinedEmp like '%$'
    //   173: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   176: aload_1
    //   177: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   180: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   183: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   186: aload_1
    //   187: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   190: ldc_w ',%'  ) '
    //   193: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: ldc_w ' and task.taskChecker != '
    //   199: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: aload_1
    //   203: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   206: ldc_w ' and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId='
    //   209: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   212: aload #4
    //   214: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   217: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   220: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   223: invokevirtual toString : ()Ljava/lang/String;
    //   226: astore #8
    //   228: goto -> 658
    //   231: new java/lang/StringBuilder
    //   234: dup
    //   235: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp='
    //   238: invokespecial <init> : (Ljava/lang/String;)V
    //   241: aload_1
    //   242: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   245: ldc_w ' or task.taskPrincipal = '
    //   248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: aload_1
    //   252: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   255: ldc_w ' or task.taskJoinedEmp like '%$'
    //   258: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: aload_1
    //   262: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   265: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: aload_1
    //   272: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   275: ldc_w ',%'   ) '
    //   278: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: ldc_w ' and task.taskChecker != '
    //   284: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   287: aload_1
    //   288: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   291: ldc_w ' and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId='
    //   294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   297: aload #4
    //   299: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   302: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: invokevirtual toString : ()Ljava/lang/String;
    //   311: astore #8
    //   313: goto -> 658
    //   316: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   319: ldc_w 'db2'
    //   322: invokevirtual indexOf : (Ljava/lang/String;)I
    //   325: iflt -> 435
    //   328: new java/lang/StringBuilder
    //   331: dup
    //   332: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp='
    //   335: invokespecial <init> : (Ljava/lang/String;)V
    //   338: aload_1
    //   339: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   342: ldc_w ' or task.taskPrincipal = '
    //   345: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: aload_1
    //   349: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   352: ldc_w ' or task.taskJoinedEmp like '%$'
    //   355: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: aload_1
    //   359: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   362: ldc_w '$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   365: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   368: aload_1
    //   369: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   372: ldc_w ',' )>0   )'
    //   375: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   378: ldc_w ' and task.taskChecker != '
    //   381: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: aload_1
    //   385: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   388: ldc_w ' and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId='
    //   391: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: aload #4
    //   396: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   399: ldc_w ' order by task.'
    //   402: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   405: aload #5
    //   407: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   410: ldc_w ' '
    //   413: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: aload #6
    //   418: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   421: ldc_w ' '
    //   424: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   427: invokevirtual toString : ()Ljava/lang/String;
    //   430: astore #8
    //   432: goto -> 658
    //   435: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   438: ldc_w 'mysql'
    //   441: invokevirtual indexOf : (Ljava/lang/String;)I
    //   444: iflt -> 554
    //   447: new java/lang/StringBuilder
    //   450: dup
    //   451: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp='
    //   454: invokespecial <init> : (Ljava/lang/String;)V
    //   457: aload_1
    //   458: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   461: ldc_w ' or task.taskPrincipal = '
    //   464: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   467: aload_1
    //   468: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   471: ldc_w ' or task.taskJoinedEmp like '%$'
    //   474: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   477: aload_1
    //   478: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   481: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   484: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   487: aload_1
    //   488: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   491: ldc_w ',%'   )'
    //   494: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   497: ldc_w ' and task.taskChecker != '
    //   500: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   503: aload_1
    //   504: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   507: ldc_w ' and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId='
    //   510: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   513: aload #4
    //   515: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   518: ldc_w ' order by task.'
    //   521: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   524: aload #5
    //   526: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   529: ldc_w ' '
    //   532: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   535: aload #6
    //   537: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   540: ldc_w ' '
    //   543: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   546: invokevirtual toString : ()Ljava/lang/String;
    //   549: astore #8
    //   551: goto -> 658
    //   554: new java/lang/StringBuilder
    //   557: dup
    //   558: ldc_w 'select task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp='
    //   561: invokespecial <init> : (Ljava/lang/String;)V
    //   564: aload_1
    //   565: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   568: ldc_w ' or task.taskPrincipal = '
    //   571: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   574: aload_1
    //   575: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   578: ldc_w ' or task.taskJoinedEmp like '%$'
    //   581: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   584: aload_1
    //   585: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   588: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   591: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: aload_1
    //   595: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   598: ldc_w ',%'   )'
    //   601: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   604: ldc_w ' and task.taskChecker != '
    //   607: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   610: aload_1
    //   611: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   614: ldc_w ' and task.taskFinishRate = 100 and task.taskStatus = 1  and task.taskDomainId='
    //   617: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   620: aload #4
    //   622: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   625: ldc_w ' order by task.'
    //   628: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   631: aload #5
    //   633: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   636: ldc_w ' '
    //   639: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   642: aload #6
    //   644: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   647: ldc_w ' '
    //   650: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   653: invokevirtual toString : ()Ljava/lang/String;
    //   656: astore #8
    //   658: aload_0
    //   659: getfield session : Lnet/sf/hibernate/Session;
    //   662: aload #8
    //   664: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   669: astore #9
    //   671: aload #9
    //   673: aload_2
    //   674: invokevirtual intValue : ()I
    //   677: iconst_1
    //   678: isub
    //   679: aload_3
    //   680: invokevirtual intValue : ()I
    //   683: imul
    //   684: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   689: pop
    //   690: aload #9
    //   692: aload_3
    //   693: invokevirtual intValue : ()I
    //   696: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   701: pop
    //   702: aload #9
    //   704: invokeinterface list : ()Ljava/util/List;
    //   709: astore #10
    //   711: aload #10
    //   713: invokeinterface iterator : ()Ljava/util/Iterator;
    //   718: astore #11
    //   720: aconst_null
    //   721: astore #12
    //   723: goto -> 915
    //   726: aload #11
    //   728: invokeinterface next : ()Ljava/lang/Object;
    //   733: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   736: astore #12
    //   738: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   741: aload #12
    //   743: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   746: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   749: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   752: astore #13
    //   754: aload #13
    //   756: ldc_w '0'
    //   759: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   762: aload #12
    //   764: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   767: ifnull -> 817
    //   770: ldc ''
    //   772: aload #12
    //   774: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   777: invokevirtual equals : (Ljava/lang/Object;)Z
    //   780: ifne -> 817
    //   783: aload #12
    //   785: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   788: new java/lang/StringBuilder
    //   791: dup
    //   792: ldc_w '$'
    //   795: invokespecial <init> : (Ljava/lang/String;)V
    //   798: aload_1
    //   799: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   802: ldc_w '$'
    //   805: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   808: invokevirtual toString : ()Ljava/lang/String;
    //   811: invokevirtual indexOf : (Ljava/lang/String;)I
    //   814: ifge -> 897
    //   817: aload #12
    //   819: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   822: ifnull -> 905
    //   825: ldc ''
    //   827: aload #12
    //   829: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   832: invokevirtual equals : (Ljava/lang/Object;)Z
    //   835: ifne -> 905
    //   838: new java/lang/StringBuilder
    //   841: dup
    //   842: ldc_w ','
    //   845: invokespecial <init> : (Ljava/lang/String;)V
    //   848: aload #12
    //   850: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   853: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   856: ldc_w ','
    //   859: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   862: invokevirtual toString : ()Ljava/lang/String;
    //   865: new java/lang/StringBuilder
    //   868: dup
    //   869: ldc_w ','
    //   872: invokespecial <init> : (Ljava/lang/String;)V
    //   875: aload_1
    //   876: invokevirtual toString : ()Ljava/lang/String;
    //   879: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   882: ldc_w ','
    //   885: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   888: invokevirtual toString : ()Ljava/lang/String;
    //   891: invokevirtual indexOf : (Ljava/lang/String;)I
    //   894: iflt -> 905
    //   897: aload #13
    //   899: ldc_w '1'
    //   902: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   905: aload #7
    //   907: aload #13
    //   909: invokeinterface add : (Ljava/lang/Object;)Z
    //   914: pop
    //   915: aload #11
    //   917: ifnull -> 990
    //   920: aload #11
    //   922: invokeinterface hasNext : ()Z
    //   927: ifne -> 726
    //   930: goto -> 990
    //   933: astore #9
    //   935: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   938: new java/lang/StringBuilder
    //   941: dup
    //   942: ldc_w '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Exception: '
    //   945: invokespecial <init> : (Ljava/lang/String;)V
    //   948: aload #9
    //   950: invokevirtual getMessage : ()Ljava/lang/String;
    //   953: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   956: invokevirtual toString : ()Ljava/lang/String;
    //   959: invokevirtual println : (Ljava/lang/String;)V
    //   962: aload #9
    //   964: athrow
    //   965: astore #14
    //   967: aload_0
    //   968: getfield session : Lnet/sf/hibernate/Session;
    //   971: invokeinterface close : ()Ljava/sql/Connection;
    //   976: pop
    //   977: aload_0
    //   978: aconst_null
    //   979: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   982: aload_0
    //   983: aconst_null
    //   984: putfield session : Lnet/sf/hibernate/Session;
    //   987: aload #14
    //   989: athrow
    //   990: aload_0
    //   991: getfield session : Lnet/sf/hibernate/Session;
    //   994: invokeinterface close : ()Ljava/sql/Connection;
    //   999: pop
    //   1000: aload_0
    //   1001: aconst_null
    //   1002: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1005: aload_0
    //   1006: aconst_null
    //   1007: putfield session : Lnet/sf/hibernate/Session;
    //   1010: aload #7
    //   1012: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #5395	-> 0
    //   #5396	-> 9
    //   #5398	-> 13
    //   #5399	-> 17
    //   #5401	-> 37
    //   #5404	-> 49
    //   #5405	-> 59
    //   #5406	-> 63
    //   #5407	-> 73
    //   #5408	-> 83
    //   #5410	-> 99
    //   #5413	-> 109
    //   #5414	-> 115
    //   #5415	-> 120
    //   #5404	-> 126
    //   #5403	-> 129
    //   #5417	-> 134
    //   #5420	-> 146
    //   #5421	-> 156
    //   #5422	-> 160
    //   #5423	-> 170
    //   #5424	-> 180
    //   #5426	-> 196
    //   #5429	-> 206
    //   #5430	-> 212
    //   #5431	-> 217
    //   #5420	-> 223
    //   #5419	-> 226
    //   #5436	-> 231
    //   #5437	-> 241
    //   #5438	-> 245
    //   #5439	-> 255
    //   #5440	-> 265
    //   #5442	-> 281
    //   #5445	-> 291
    //   #5446	-> 297
    //   #5447	-> 302
    //   #5436	-> 308
    //   #5435	-> 311
    //   #5453	-> 316
    //   #5456	-> 328
    //   #5457	-> 338
    //   #5458	-> 342
    //   #5459	-> 352
    //   #5460	-> 362
    //   #5462	-> 378
    //   #5465	-> 388
    //   #5466	-> 394
    //   #5467	-> 416
    //   #5468	-> 421
    //   #5456	-> 427
    //   #5455	-> 430
    //   #5470	-> 435
    //   #5473	-> 447
    //   #5474	-> 457
    //   #5475	-> 461
    //   #5476	-> 471
    //   #5477	-> 481
    //   #5479	-> 497
    //   #5482	-> 507
    //   #5483	-> 513
    //   #5484	-> 535
    //   #5485	-> 540
    //   #5473	-> 546
    //   #5472	-> 549
    //   #5490	-> 554
    //   #5491	-> 564
    //   #5492	-> 568
    //   #5493	-> 578
    //   #5494	-> 588
    //   #5496	-> 604
    //   #5499	-> 614
    //   #5500	-> 620
    //   #5501	-> 642
    //   #5502	-> 647
    //   #5490	-> 653
    //   #5489	-> 656
    //   #5507	-> 658
    //   #5509	-> 671
    //   #5510	-> 690
    //   #5511	-> 702
    //   #5512	-> 711
    //   #5513	-> 720
    //   #5514	-> 723
    //   #5515	-> 726
    //   #5516	-> 738
    //   #5517	-> 741
    //   #5516	-> 749
    //   #5519	-> 754
    //   #5520	-> 762
    //   #5521	-> 770
    //   #5522	-> 783
    //   #5524	-> 817
    //   #5525	-> 825
    //   #5526	-> 838
    //   #5532	-> 897
    //   #5535	-> 905
    //   #5514	-> 915
    //   #5537	-> 933
    //   #5538	-> 935
    //   #5539	-> 938
    //   #5540	-> 948
    //   #5539	-> 956
    //   #5538	-> 959
    //   #5541	-> 962
    //   #5542	-> 965
    //   #5543	-> 967
    //   #5544	-> 977
    //   #5545	-> 982
    //   #5547	-> 987
    //   #5543	-> 990
    //   #5544	-> 1000
    //   #5545	-> 1005
    //   #5548	-> 1010
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	1013	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	1013	1	userId	Ljava/lang/Long;
    //   0	1013	2	currentPage	Ljava/lang/Integer;
    //   0	1013	3	volume	Ljava/lang/Integer;
    //   0	1013	4	domainId	Ljava/lang/Long;
    //   0	1013	5	type	Ljava/lang/String;
    //   0	1013	6	sortType	Ljava/lang/String;
    //   9	1004	7	result	Ljava/util/List;
    //   13	1000	8	strsql	Ljava/lang/String;
    //   671	262	9	query	Lnet/sf/hibernate/Query;
    //   711	222	10	list	Ljava/util/List;
    //   720	213	11	iterator	Ljava/util/Iterator;
    //   723	210	12	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   754	161	13	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   935	30	9	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   13	930	933	net/sf/hibernate/HibernateException
    //   13	965	965	finally
  }
  
  public Integer getSettleFinishRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp = " + 
        userId + 
        " or task.taskChecker = " + userId + 
        ") and task.isArranged =1" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      Query query = this.session.createQuery("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId in(" + 
          
          tempHql + 
          ")  or (task.createdEmp = " + 
          userId + 
          " or task.taskChecker = " + 
          userId + 
          ")) and task.taskStatus=2" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("get settle finish record count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List selectSettleFinishTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String strsql = "";
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp = " + 
        userId + 
        " or task.taskChecker = " + userId + 
        ") and task.isArranged = 1" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      if ("".equals(type) || "".equals(sortType)) {
        strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId in (" + 
          
          tempHql + 
          ") or (task.createdEmp = " + userId + 
          " or task.taskChecker = " + userId + 
          ")) and task.taskStatus=2 " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "") + 
          " order by task.parentIdString desc,task.taskCreatedTime";
      } else {
        strsql = "select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId in (" + 
          
          tempHql + 
          ") or (task.createdEmp = " + userId + 
          " or task.taskChecker = " + userId + 
          ")) and task.taskStatus=2 " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "") + 
          " order by task." + type + " " + sortType;
      } 
      Query query = this.session.createQuery(strsql);
      query.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      query.setMaxResults(volume.intValue());
      List taskList = query.list();
      Iterator<TaskPO> iterator = taskList.iterator();
      TaskPO taskPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskPO = iterator.next();
        TaskVO task = (TaskVO)TransformObject.getInstance()
          .transformObject(taskPO, TaskVO.class);
        task.setTaskTypeShow("0");
        if (taskPO.getTaskJoinedEmp() != null && 
          !"".equals(taskPO.getTaskJoinedEmp())) {
          if (taskPO.getTaskJoinedEmp().indexOf("$" + userId + 
              "$") >= 0)
            task.setTaskTypeShow("1"); 
        } else if (taskPO.getTaskJoineOrg() != null && 
          !"".equals(taskPO.getTaskJoineOrg()) && (
          "," + taskPO.getTaskJoineOrg() + ",").indexOf("," + userId.toString() + ",") >= 0) {
          task.setTaskTypeShow("1");
        } 
        result.add(task);
      } 
    } catch (HibernateException ex) {
      System.out.println("select Settle Finish Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getSSTRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    String strsql = "";
    try {
      begin();
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[11].toString()))
        if ("".equals(strsql)) {
          StringTokenizer st = new StringTokenizer(
              parameters[11].toString(), "$");
          strsql = " ( user.empId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        } else {
          StringTokenizer st = new StringTokenizer(
              parameters[11].toString(), "$");
          strsql = String.valueOf(strsql) + "  and ( user.empId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        }  
      if (!"".equals(parameters[10].toString()))
        if ("".equals(strsql)) {
          StringTokenizer st = new StringTokenizer(parameters[10].toString(), 
              "*");
          strsql = " ( org.orgId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + 
              st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        } else {
          StringTokenizer st = new StringTokenizer(parameters[10].toString(), 
              "*");
          strsql = String.valueOf(strsql) + "  and ( org.orgId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + 
              st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        }  
      if (!"".equals(parameters[12].toString()))
        strsql = String.valueOf(strsql) + " and " + parameters[12].toString(); 
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + 
              "'  and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      result = this.session.iterate("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO  task,com.js.system.vo.usermanager.EmployeeVO user join user.organizations org where task.taskPrincipal= user.empId  and ((task.taskId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp = " + 
          
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))) or" + 
          "(task.taskBaseId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp =" + 
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))))and (" + 
          strsql + 
          ") and task.taskDomainId = " + 
          domainId).next();
    } catch (HibernateException ex) {
      System.out.println(
          "get Search Settle Task Record Count Exception: " + 
          ex.getMessage());
      throw new EJBException(ex);
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchSettleTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String strsql = "";
    try {
      begin();
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[11].toString()))
        if ("".equals(strsql)) {
          StringTokenizer st = new StringTokenizer(
              parameters[11].toString(), "$");
          strsql = " ( user.empId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        } else {
          StringTokenizer st = new StringTokenizer(
              parameters[11].toString(), "$");
          strsql = String.valueOf(strsql) + "  and ( user.empId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        }  
      if (!"".equals(parameters[10].toString()))
        if ("".equals(strsql)) {
          StringTokenizer st = new StringTokenizer(parameters[10].toString(), 
              "*");
          strsql = " ( org.orgId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + 
              st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        } else {
          StringTokenizer st = new StringTokenizer(parameters[10].toString(), 
              "*");
          strsql = String.valueOf(strsql) + "  and ( org.orgId in (";
          while (st.hasMoreTokens())
            strsql = String.valueOf(strsql) + 
              st.nextToken() + ","; 
          strsql = String.valueOf(strsql.substring(0, strsql.length() - 1)) + 
            ")) ";
        }  
      if (!"".equals(parameters[12].toString()))
        strsql = String.valueOf(strsql) + " and " + parameters[12].toString(); 
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + 
              "'  and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      String strsql1 = "";
      if ("".equals(parameters[8].toString())) {
        strsql1 = " order by task.taskIdStrings";
      } else {
        strsql1 = " order by task." + parameters[8].toString() + " " + 
          parameters[9].toString() + " ,task.taskIdStrings";
      } 
      Query query = this.session.createQuery("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO  task,com.js.system.vo.usermanager.EmployeeVO user join user.organizations org where task.taskPrincipal= user.empId and ((task.taskId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp = " + 
          
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))) or" + 
          "(task.taskBaseId in (select distinct task1.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task1 where task1.createdEmp =" + 
          userId + " and task1.isArranged = 1 and task1.taskFinishRate<100 and not (task.taskStatus  in (2,4)))))and (" + 
          strsql + 
          ") and task.taskDomainId = " + 
          domainId + strsql1);
      query.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      query.setMaxResults(volume.intValue());
      Iterator<TaskPO> iterator = query.list().iterator();
      TaskPO taskPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskPO = iterator.next();
        TaskVO task = (TaskVO)TransformObject.getInstance()
          .transformObject(taskPO, TaskVO.class);
        task.setCanCancel(Boolean.FALSE);
        task.setMaintenance(Boolean.TRUE);
        if (task.getTaskOrderCode().intValue() == 0 && 
          task.getTaskStatus().equals(new Integer(1)))
          task.setCanCancel(Boolean.TRUE); 
        task.setTaskTypeShow("0");
        if (taskPO.getTaskJoinedEmp() != null && 
          !"".equals(taskPO.getTaskJoinedEmp()) && 
          taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0)
          task.setTaskTypeShow("1"); 
        if (taskPO.getTaskJoineOrg() != null && 
          !"".equals(taskPO.getTaskJoineOrg()) && (
          "," + taskPO.getTaskJoineOrg() + ",").indexOf("," + userId.toString() + ",") >= 0)
          task.setTaskTypeShow("1"); 
        result.add(task);
      } 
    } catch (HibernateException ex) {
      System.out.println("select Settle Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getSSCTRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    String strsql = "";
    try {
      begin();
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + 
              "'  and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      Query query = this.session.createQuery(
          "select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskChecker =" + 
          userId + 
          " and task.taskFinishRate = 100 and task.taskStatus =1)and (" + 
          strsql + ")" + 
          " and task.taskDomainId = " + domainId);
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("get settle check task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchSettleCheckTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #6
    //   9: ldc ''
    //   11: astore #7
    //   13: aload_0
    //   14: invokevirtual begin : ()V
    //   17: ldc ''
    //   19: aload_2
    //   20: iconst_0
    //   21: aaload
    //   22: invokevirtual toString : ()Ljava/lang/String;
    //   25: invokevirtual equals : (Ljava/lang/Object;)Z
    //   28: ifne -> 112
    //   31: ldc ''
    //   33: aload #7
    //   35: invokevirtual equals : (Ljava/lang/Object;)Z
    //   38: ifeq -> 74
    //   41: new java/lang/StringBuilder
    //   44: dup
    //   45: ldc_w 'task.taskTitle like '%'
    //   48: invokespecial <init> : (Ljava/lang/String;)V
    //   51: aload_2
    //   52: iconst_0
    //   53: aaload
    //   54: invokevirtual toString : ()Ljava/lang/String;
    //   57: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: ldc_w '%''
    //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: invokevirtual toString : ()Ljava/lang/String;
    //   69: astore #7
    //   71: goto -> 112
    //   74: new java/lang/StringBuilder
    //   77: dup
    //   78: aload #7
    //   80: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   83: invokespecial <init> : (Ljava/lang/String;)V
    //   86: ldc_w ' and task.taskTitle like '%'
    //   89: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: aload_2
    //   93: iconst_0
    //   94: aaload
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: ldc_w '%''
    //   104: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: invokevirtual toString : ()Ljava/lang/String;
    //   110: astore #7
    //   112: ldc ''
    //   114: aload_2
    //   115: iconst_1
    //   116: aaload
    //   117: invokevirtual toString : ()Ljava/lang/String;
    //   120: invokevirtual equals : (Ljava/lang/Object;)Z
    //   123: ifne -> 195
    //   126: ldc ''
    //   128: aload #7
    //   130: invokevirtual equals : (Ljava/lang/Object;)Z
    //   133: ifeq -> 163
    //   136: new java/lang/StringBuilder
    //   139: dup
    //   140: ldc_w 'task.taskFinishRate='
    //   143: invokespecial <init> : (Ljava/lang/String;)V
    //   146: aload_2
    //   147: iconst_1
    //   148: aaload
    //   149: invokevirtual toString : ()Ljava/lang/String;
    //   152: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: invokevirtual toString : ()Ljava/lang/String;
    //   158: astore #7
    //   160: goto -> 195
    //   163: new java/lang/StringBuilder
    //   166: dup
    //   167: aload #7
    //   169: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   172: invokespecial <init> : (Ljava/lang/String;)V
    //   175: ldc_w ' and task.taskFinishRate='
    //   178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: aload_2
    //   182: iconst_1
    //   183: aaload
    //   184: invokevirtual toString : ()Ljava/lang/String;
    //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   190: invokevirtual toString : ()Ljava/lang/String;
    //   193: astore #7
    //   195: ldc ''
    //   197: aload_2
    //   198: iconst_2
    //   199: aaload
    //   200: invokevirtual toString : ()Ljava/lang/String;
    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
    //   206: ifne -> 288
    //   209: ldc ''
    //   211: aload #7
    //   213: invokevirtual equals : (Ljava/lang/Object;)Z
    //   216: ifeq -> 251
    //   219: new java/lang/StringBuilder
    //   222: dup
    //   223: ldc_w 'task.taskType=''
    //   226: invokespecial <init> : (Ljava/lang/String;)V
    //   229: aload_2
    //   230: iconst_2
    //   231: aaload
    //   232: invokevirtual toString : ()Ljava/lang/String;
    //   235: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: ldc '''
    //   240: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: invokevirtual toString : ()Ljava/lang/String;
    //   246: astore #7
    //   248: goto -> 288
    //   251: new java/lang/StringBuilder
    //   254: dup
    //   255: aload #7
    //   257: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   260: invokespecial <init> : (Ljava/lang/String;)V
    //   263: ldc_w ' and task.taskType=''
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: aload_2
    //   270: iconst_2
    //   271: aaload
    //   272: invokevirtual toString : ()Ljava/lang/String;
    //   275: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: ldc '''
    //   280: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   283: invokevirtual toString : ()Ljava/lang/String;
    //   286: astore #7
    //   288: ldc ''
    //   290: aload_2
    //   291: iconst_3
    //   292: aaload
    //   293: invokevirtual toString : ()Ljava/lang/String;
    //   296: invokevirtual equals : (Ljava/lang/Object;)Z
    //   299: ifne -> 371
    //   302: ldc ''
    //   304: aload #7
    //   306: invokevirtual equals : (Ljava/lang/Object;)Z
    //   309: ifeq -> 339
    //   312: new java/lang/StringBuilder
    //   315: dup
    //   316: ldc_w 'task.taskPriority='
    //   319: invokespecial <init> : (Ljava/lang/String;)V
    //   322: aload_2
    //   323: iconst_3
    //   324: aaload
    //   325: invokevirtual toString : ()Ljava/lang/String;
    //   328: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: invokevirtual toString : ()Ljava/lang/String;
    //   334: astore #7
    //   336: goto -> 371
    //   339: new java/lang/StringBuilder
    //   342: dup
    //   343: aload #7
    //   345: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   348: invokespecial <init> : (Ljava/lang/String;)V
    //   351: ldc_w ' and task.taskPriority='
    //   354: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   357: aload_2
    //   358: iconst_3
    //   359: aaload
    //   360: invokevirtual toString : ()Ljava/lang/String;
    //   363: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: invokevirtual toString : ()Ljava/lang/String;
    //   369: astore #7
    //   371: ldc ''
    //   373: aload_2
    //   374: iconst_4
    //   375: aaload
    //   376: invokevirtual toString : ()Ljava/lang/String;
    //   379: invokevirtual equals : (Ljava/lang/Object;)Z
    //   382: ifne -> 454
    //   385: ldc ''
    //   387: aload #7
    //   389: invokevirtual equals : (Ljava/lang/Object;)Z
    //   392: ifeq -> 422
    //   395: new java/lang/StringBuilder
    //   398: dup
    //   399: ldc_w 'task.taskStatus='
    //   402: invokespecial <init> : (Ljava/lang/String;)V
    //   405: aload_2
    //   406: iconst_4
    //   407: aaload
    //   408: invokevirtual toString : ()Ljava/lang/String;
    //   411: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   414: invokevirtual toString : ()Ljava/lang/String;
    //   417: astore #7
    //   419: goto -> 454
    //   422: new java/lang/StringBuilder
    //   425: dup
    //   426: aload #7
    //   428: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   431: invokespecial <init> : (Ljava/lang/String;)V
    //   434: ldc_w ' and task.taskStatus='
    //   437: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   440: aload_2
    //   441: iconst_4
    //   442: aaload
    //   443: invokevirtual toString : ()Ljava/lang/String;
    //   446: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   449: invokevirtual toString : ()Ljava/lang/String;
    //   452: astore #7
    //   454: ldc ''
    //   456: aload_2
    //   457: iconst_5
    //   458: aaload
    //   459: invokevirtual toString : ()Ljava/lang/String;
    //   462: invokevirtual equals : (Ljava/lang/Object;)Z
    //   465: ifne -> 728
    //   468: ldc_w '1'
    //   471: aload_2
    //   472: iconst_5
    //   473: aaload
    //   474: invokevirtual toString : ()Ljava/lang/String;
    //   477: invokevirtual equals : (Ljava/lang/Object;)Z
    //   480: ifeq -> 728
    //   483: ldc ''
    //   485: aload #7
    //   487: invokevirtual equals : (Ljava/lang/Object;)Z
    //   490: ifeq -> 604
    //   493: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   496: ldc_w 'mysql'
    //   499: invokevirtual indexOf : (Ljava/lang/String;)I
    //   502: iflt -> 554
    //   505: new java/lang/StringBuilder
    //   508: dup
    //   509: ldc_w 'task.taskEndTime between ''
    //   512: invokespecial <init> : (Ljava/lang/String;)V
    //   515: aload_2
    //   516: bipush #6
    //   518: aaload
    //   519: invokevirtual toString : ()Ljava/lang/String;
    //   522: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   525: ldc_w ''  and ''
    //   528: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   531: aload_2
    //   532: bipush #7
    //   534: aaload
    //   535: invokevirtual toString : ()Ljava/lang/String;
    //   538: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   541: ldc '''
    //   543: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   546: invokevirtual toString : ()Ljava/lang/String;
    //   549: astore #7
    //   551: goto -> 728
    //   554: new java/lang/StringBuilder
    //   557: dup
    //   558: ldc_w 'task.taskEndTime between JSDB.FN_STRTODATE(''
    //   561: invokespecial <init> : (Ljava/lang/String;)V
    //   564: aload_2
    //   565: bipush #6
    //   567: aaload
    //   568: invokevirtual toString : ()Ljava/lang/String;
    //   571: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   574: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   577: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   580: aload_2
    //   581: bipush #7
    //   583: aaload
    //   584: invokevirtual toString : ()Ljava/lang/String;
    //   587: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   590: ldc_w '','S')'
    //   593: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: invokevirtual toString : ()Ljava/lang/String;
    //   599: astore #7
    //   601: goto -> 728
    //   604: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   607: ldc_w 'mysql'
    //   610: invokevirtual indexOf : (Ljava/lang/String;)I
    //   613: iflt -> 673
    //   616: new java/lang/StringBuilder
    //   619: dup
    //   620: aload #7
    //   622: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   625: invokespecial <init> : (Ljava/lang/String;)V
    //   628: ldc_w ' and task.taskEndTime between ''
    //   631: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   634: aload_2
    //   635: bipush #6
    //   637: aaload
    //   638: invokevirtual toString : ()Ljava/lang/String;
    //   641: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   644: ldc_w ''  and ''
    //   647: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   650: aload_2
    //   651: bipush #7
    //   653: aaload
    //   654: invokevirtual toString : ()Ljava/lang/String;
    //   657: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   660: ldc '''
    //   662: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   665: invokevirtual toString : ()Ljava/lang/String;
    //   668: astore #7
    //   670: goto -> 728
    //   673: new java/lang/StringBuilder
    //   676: dup
    //   677: aload #7
    //   679: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   682: invokespecial <init> : (Ljava/lang/String;)V
    //   685: ldc_w ' and task.taskEndTime between JSDB.FN_STRTODATE(''
    //   688: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   691: aload_2
    //   692: bipush #6
    //   694: aaload
    //   695: invokevirtual toString : ()Ljava/lang/String;
    //   698: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   701: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   704: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   707: aload_2
    //   708: bipush #7
    //   710: aaload
    //   711: invokevirtual toString : ()Ljava/lang/String;
    //   714: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   717: ldc_w '','S')'
    //   720: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   723: invokevirtual toString : ()Ljava/lang/String;
    //   726: astore #7
    //   728: ldc ''
    //   730: aload #7
    //   732: invokevirtual equals : (Ljava/lang/Object;)Z
    //   735: ifeq -> 743
    //   738: ldc_w '1=1'
    //   741: astore #7
    //   743: ldc ''
    //   745: astore #8
    //   747: ldc ''
    //   749: aload_2
    //   750: bipush #8
    //   752: aaload
    //   753: invokevirtual toString : ()Ljava/lang/String;
    //   756: invokevirtual equals : (Ljava/lang/Object;)Z
    //   759: ifeq -> 770
    //   762: ldc_w ' order by task.parentIdString'
    //   765: astore #8
    //   767: goto -> 811
    //   770: new java/lang/StringBuilder
    //   773: dup
    //   774: ldc_w ' order by task.'
    //   777: invokespecial <init> : (Ljava/lang/String;)V
    //   780: aload_2
    //   781: bipush #8
    //   783: aaload
    //   784: invokevirtual toString : ()Ljava/lang/String;
    //   787: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   790: ldc_w ' '
    //   793: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   796: aload_2
    //   797: bipush #9
    //   799: aaload
    //   800: invokevirtual toString : ()Ljava/lang/String;
    //   803: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   806: invokevirtual toString : ()Ljava/lang/String;
    //   809: astore #8
    //   811: aload_0
    //   812: getfield session : Lnet/sf/hibernate/Session;
    //   815: new java/lang/StringBuilder
    //   818: dup
    //   819: ldc_w 'from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskChecker ='
    //   822: invokespecial <init> : (Ljava/lang/String;)V
    //   825: aload_1
    //   826: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   829: ldc_w '   or task.createdEmp='
    //   832: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   835: aload_1
    //   836: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   839: ldc_w ') and task.taskFinishRate = 100 and task.taskStatus =1 and ('
    //   842: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   845: aload #7
    //   847: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   850: ldc_w ')'
    //   853: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   856: ldc_w ' and task.taskDomainId = '
    //   859: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   862: aload #5
    //   864: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   867: aload #8
    //   869: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   872: invokevirtual toString : ()Ljava/lang/String;
    //   875: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   880: astore #9
    //   882: aload #9
    //   884: aload_3
    //   885: invokevirtual intValue : ()I
    //   888: iconst_1
    //   889: isub
    //   890: aload #4
    //   892: invokevirtual intValue : ()I
    //   895: imul
    //   896: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   901: pop
    //   902: aload #9
    //   904: aload #4
    //   906: invokevirtual intValue : ()I
    //   909: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   914: pop
    //   915: aload #9
    //   917: invokeinterface list : ()Ljava/util/List;
    //   922: astore #10
    //   924: aload #10
    //   926: invokeinterface iterator : ()Ljava/util/Iterator;
    //   931: astore #11
    //   933: aconst_null
    //   934: astore #12
    //   936: goto -> 1128
    //   939: aload #11
    //   941: invokeinterface next : ()Ljava/lang/Object;
    //   946: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   949: astore #12
    //   951: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   954: aload #12
    //   956: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   959: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   962: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   965: astore #13
    //   967: aload #13
    //   969: ldc_w '0'
    //   972: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   975: aload #12
    //   977: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   980: ifnull -> 996
    //   983: ldc ''
    //   985: aload #12
    //   987: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   990: invokevirtual equals : (Ljava/lang/Object;)Z
    //   993: ifeq -> 1017
    //   996: aload #12
    //   998: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1001: ifnull -> 1118
    //   1004: ldc ''
    //   1006: aload #12
    //   1008: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1011: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1014: ifne -> 1118
    //   1017: aload #12
    //   1019: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1022: new java/lang/StringBuilder
    //   1025: dup
    //   1026: ldc_w '$'
    //   1029: invokespecial <init> : (Ljava/lang/String;)V
    //   1032: aload_1
    //   1033: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1036: ldc_w '$'
    //   1039: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1042: invokevirtual toString : ()Ljava/lang/String;
    //   1045: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1048: ifge -> 1110
    //   1051: new java/lang/StringBuilder
    //   1054: dup
    //   1055: ldc_w ','
    //   1058: invokespecial <init> : (Ljava/lang/String;)V
    //   1061: aload #12
    //   1063: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1066: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1069: ldc_w ','
    //   1072: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1075: invokevirtual toString : ()Ljava/lang/String;
    //   1078: new java/lang/StringBuilder
    //   1081: dup
    //   1082: ldc_w ','
    //   1085: invokespecial <init> : (Ljava/lang/String;)V
    //   1088: aload_1
    //   1089: invokevirtual toString : ()Ljava/lang/String;
    //   1092: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1095: ldc_w ','
    //   1098: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1101: invokevirtual toString : ()Ljava/lang/String;
    //   1104: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1107: iflt -> 1118
    //   1110: aload #13
    //   1112: ldc_w '1'
    //   1115: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   1118: aload #6
    //   1120: aload #13
    //   1122: invokeinterface add : (Ljava/lang/Object;)Z
    //   1127: pop
    //   1128: aload #11
    //   1130: ifnull -> 1203
    //   1133: aload #11
    //   1135: invokeinterface hasNext : ()Z
    //   1140: ifne -> 939
    //   1143: goto -> 1203
    //   1146: astore #8
    //   1148: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1151: new java/lang/StringBuilder
    //   1154: dup
    //   1155: ldc_w 'Select Settle Check Task Exception: '
    //   1158: invokespecial <init> : (Ljava/lang/String;)V
    //   1161: aload #8
    //   1163: invokevirtual getMessage : ()Ljava/lang/String;
    //   1166: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1169: invokevirtual toString : ()Ljava/lang/String;
    //   1172: invokevirtual println : (Ljava/lang/String;)V
    //   1175: aload #8
    //   1177: athrow
    //   1178: astore #14
    //   1180: aload_0
    //   1181: getfield session : Lnet/sf/hibernate/Session;
    //   1184: invokeinterface close : ()Ljava/sql/Connection;
    //   1189: pop
    //   1190: aload_0
    //   1191: aconst_null
    //   1192: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1195: aload_0
    //   1196: aconst_null
    //   1197: putfield session : Lnet/sf/hibernate/Session;
    //   1200: aload #14
    //   1202: athrow
    //   1203: aload_0
    //   1204: getfield session : Lnet/sf/hibernate/Session;
    //   1207: invokeinterface close : ()Ljava/sql/Connection;
    //   1212: pop
    //   1213: aload_0
    //   1214: aconst_null
    //   1215: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1218: aload_0
    //   1219: aconst_null
    //   1220: putfield session : Lnet/sf/hibernate/Session;
    //   1223: aload #6
    //   1225: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #6274	-> 0
    //   #6275	-> 9
    //   #6277	-> 13
    //   #6278	-> 17
    //   #6279	-> 31
    //   #6280	-> 41
    //   #6281	-> 60
    //   #6280	-> 66
    //   #6283	-> 74
    //   #6284	-> 92
    //   #6283	-> 107
    //   #6287	-> 112
    //   #6288	-> 126
    //   #6289	-> 136
    //   #6292	-> 163
    //   #6293	-> 181
    //   #6292	-> 190
    //   #6297	-> 195
    //   #6298	-> 209
    //   #6299	-> 219
    //   #6301	-> 251
    //   #6302	-> 278
    //   #6301	-> 283
    //   #6305	-> 288
    //   #6306	-> 302
    //   #6307	-> 312
    //   #6310	-> 339
    //   #6311	-> 357
    //   #6310	-> 366
    //   #6314	-> 371
    //   #6315	-> 385
    //   #6316	-> 395
    //   #6319	-> 422
    //   #6323	-> 454
    //   #6324	-> 468
    //   #6325	-> 483
    //   #6327	-> 493
    //   #6329	-> 505
    //   #6330	-> 515
    //   #6331	-> 525
    //   #6332	-> 531
    //   #6329	-> 546
    //   #6337	-> 554
    //   #6338	-> 564
    //   #6339	-> 574
    //   #6340	-> 580
    //   #6337	-> 596
    //   #6336	-> 599
    //   #6347	-> 604
    //   #6349	-> 616
    //   #6350	-> 628
    //   #6351	-> 634
    //   #6352	-> 644
    //   #6353	-> 650
    //   #6349	-> 665
    //   #6357	-> 673
    //   #6358	-> 685
    //   #6359	-> 691
    //   #6360	-> 701
    //   #6361	-> 707
    //   #6357	-> 723
    //   #6369	-> 728
    //   #6370	-> 738
    //   #6372	-> 743
    //   #6373	-> 747
    //   #6374	-> 762
    //   #6376	-> 770
    //   #6377	-> 796
    //   #6376	-> 806
    //   #6380	-> 811
    //   #6381	-> 815
    //   #6382	-> 825
    //   #6383	-> 829
    //   #6384	-> 839
    //   #6385	-> 845
    //   #6386	-> 856
    //   #6381	-> 872
    //   #6380	-> 875
    //   #6387	-> 882
    //   #6388	-> 902
    //   #6389	-> 915
    //   #6390	-> 924
    //   #6391	-> 933
    //   #6392	-> 936
    //   #6393	-> 939
    //   #6394	-> 951
    //   #6395	-> 954
    //   #6394	-> 962
    //   #6396	-> 967
    //   #6397	-> 975
    //   #6398	-> 983
    //   #6399	-> 996
    //   #6400	-> 1004
    //   #6401	-> 1017
    //   #6403	-> 1051
    //   #6405	-> 1110
    //   #6409	-> 1118
    //   #6392	-> 1128
    //   #6411	-> 1146
    //   #6412	-> 1148
    //   #6413	-> 1161
    //   #6412	-> 1172
    //   #6414	-> 1175
    //   #6415	-> 1178
    //   #6416	-> 1180
    //   #6417	-> 1190
    //   #6418	-> 1195
    //   #6420	-> 1200
    //   #6416	-> 1203
    //   #6417	-> 1213
    //   #6418	-> 1218
    //   #6421	-> 1223
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	1226	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	1226	1	userId	Ljava/lang/Long;
    //   0	1226	2	parameters	[Ljava/lang/String;
    //   0	1226	3	currentPage	Ljava/lang/Integer;
    //   0	1226	4	volume	Ljava/lang/Integer;
    //   0	1226	5	domainId	Ljava/lang/Long;
    //   9	1217	6	result	Ljava/util/List;
    //   13	1213	7	strsql	Ljava/lang/String;
    //   747	399	8	strsql1	Ljava/lang/String;
    //   882	264	9	query	Lnet/sf/hibernate/Query;
    //   924	222	10	list	Ljava/util/List;
    //   933	213	11	iterator	Ljava/util/Iterator;
    //   936	210	12	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   967	161	13	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   1148	30	8	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   13	1143	1146	net/sf/hibernate/HibernateException
    //   13	1178	1178	finally
  }
  
  public Integer getSSFRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    String strsql = "";
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp = " + 
        userId + 
        ") and task.isArranged = 1" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + 
              "'  and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      Query query = this.session.createQuery("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId in (" + 
          
          tempHql + 
          ") or (task.createdEmp = " + 
          userId + 
          " or task.taskChecker = " + 
          userId + 
          ")) and task.taskStatus=2 and (" + 
          strsql + ")" + 
          " and task.taskDomainId = " + 
          domainId);
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println(
          "get search settle finish record count Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchSettleFinishTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String strsql = "";
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.createdEmp = " + 
        userId + 
        " or task.taskChecker = " + userId + 
        ") and task.isArranged = 1" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
        }  
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + 
              "'  and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      String strsql1 = "";
      if ("".equals(parameters[8].toString())) {
        strsql1 = " order by task.parentIdString";
      } else {
        strsql1 = " order by task." + parameters[8].toString() + " " + 
          parameters[9].toString();
      } 
      Query query = this.session.createQuery("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskId in (" + 
          
          tempHql + 
          ") or (task.createdEmp = " + 
          userId + 
          " or task.taskChecker = " + 
          userId + 
          ")) and task.taskStatus=2 and (" + 
          strsql + ") " + 
          " and task.taskDomainId = " + 
          domainId + strsql1);
      query.setFirstResult((currentPage.intValue() - 1) * 
          volume.intValue());
      query.setMaxResults(volume.intValue());
      List taskList = query.list();
      Iterator<TaskPO> iterator = taskList.iterator();
      TaskPO taskPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskPO = iterator.next();
        TaskVO task = (TaskVO)TransformObject.getInstance()
          .transformObject(taskPO, TaskVO.class);
        task.setTaskTypeShow("0");
        if (taskPO.getTaskJoinedEmp() != null && 
          !"".equals(taskPO.getTaskJoinedEmp())) {
          if (taskPO.getTaskJoinedEmp().indexOf("$" + userId + 
              "$") >= 0)
            task.setTaskTypeShow("1"); 
        } else if (taskPO.getTaskJoineOrg() != null && 
          !"".equals(taskPO.getTaskJoineOrg()) && (
          "," + taskPO.getTaskJoineOrg() + ",").indexOf("," + userId.toString() + ",") >= 0) {
          task.setTaskTypeShow("1");
        } 
        result.add(task);
      } 
    } catch (HibernateException ex) {
      System.out.println("select Settle Finish Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private TaskExecPO getTaskExec(Long userId, Long taskId) throws HibernateException {
    TaskExecPO result = null;
    Query query = this.session.createQuery("select distinct taskexec from com.js.oa.scheme.taskcenter.po.TaskExecPO taskexec where taskexec.task.taskId =" + 
        
        taskId + " and taskexec.execEmpId = " + 
        userId);
    result = query.list().get(0);
    return result;
  }
  
  private void reportTask(Long taskId, Long userId, Integer status, Integer finishRate, String realEndTime) throws HibernateException {
    TaskPO task = (TaskPO)this.session.load(TaskPO.class, taskId);
    task.setTaskStatus(status);
    task.setTaskFinishRate(finishRate);
    if (!realEndTime.equals(""))
      task.setTaskRealEndTime(new Date(realEndTime)); 
    if (task.getTaskStatus().equals(new Integer(1)) && 
      task.getTaskFinishRate().equals(new Integer(100)))
      if (task.getTaskChecker().longValue() != 0L && 
        !task.getTaskChecker().equals(userId) && 
        !task.getCreatedEmp().equals(task.getTaskChecker()) && 
        !task.getTaskPrincipal().equals(task.getTaskChecker())) {
        addTaskExec(task, task.getTaskChecker());
        if (task.getTaskChecker().longValue() == 0L)
          task.setTaskStatus(new Integer(2)); 
      } else if (userId.equals(task.getTaskChecker())) {
        task.setTaskStatus(new Integer(2));
      } else if (task.getTaskChecker().longValue() == 0L) {
        task.setTaskStatus(new Integer(2));
      }  
    this.session.update(task);
  }
  
  public Boolean addTaskReport(TaskReportVO taskReport, Integer status, String completeTime, TaskReportPO taskReportPOO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskReportPO taskReportPO = 
        (TaskReportPO)TransformObject.getInstance().transformObject(
          taskReport, TaskReportPO.class);
      TaskExecPO taskExec = getTaskExec(taskReport.getEmpId(), 
          taskReport.getTaskId());
      if (taskExec.getIsPrincipal().equals(new Integer(1)))
        reportTask(taskReport.getTaskId(), taskReport.getEmpId(), 
            status, taskReport.getFinishRate(), completeTime); 
      taskReportPO.setTaskExec(taskExec);
      taskReportPO.setCheckerId(taskReport.getEmpId());
      taskReportPO.setReportTime(new Date());
      this.session.save(taskReportPO);
      TaskPO taskPO = (TaskPO)this.session.get(TaskPO.class, taskReport.getTaskId());
      EmployeeVO EmployeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, taskReport.getEmpId());
      String userids = "";
      if (taskPO.getTaskPrincipal() != null && !String.valueOf(taskPO.getTaskPrincipal()).equals(String.valueOf(taskReport.getEmpId())))
        userids = String.valueOf(userids) + taskPO.getTaskPrincipal() + ","; 
      if (taskPO.getTaskChecker() != null && !String.valueOf(taskPO.getTaskChecker()).equals(String.valueOf(taskReport.getEmpId())))
        userids = String.valueOf(userids) + taskPO.getTaskChecker() + ","; 
      if (!"".equals(userids)) {
        userids = userids.substring(0, userids.trim().length() - 1);
        String title = String.valueOf(EmployeeVO.getEmpName()) + "汇报了" + taskPO.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskReport.getTaskId() + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskPO.getTaskEndTime());
        calendar.set(11, 23);
        calendar.set(12, 59);
        RemindUtil.sendMessageToUsers(title, url, userids, "TaskReport", new Date(), calendar.getTime(), EmployeeVO.getEmpName(), taskReport.getTaskId());
      } 
      Set set = taskReportPOO.getTaskReportAccessory();
      Set<TaskReportAccessoryPO> set1 = new HashSet();
      HashSet hs = new HashSet();
      taskReportPO.setTaskReportAccessory(hs);
      Iterator<TaskReportAccessoryPO> iterator = set.iterator();
      TaskReportAccessoryPO taskReportAccessoryPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskReportAccessoryPO = iterator.next();
        taskReportAccessoryPO.setTaskReport(taskReportPO);
        this.session.save(taskReportAccessoryPO);
        set1.add(taskReportAccessoryPO);
      } 
      taskReportPO.setTaskReportAccessory(set1);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add Task Report Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean addTaskReport(Long userId, Long taskId, String reportInfo, Long domainId, TaskReportPO taskReportPO) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskReportPO reportPO = new TaskReportPO();
      TaskExecPO taskExec = getTaskExec(userId, taskId);
      reportPO.setReportInfo(reportInfo);
      reportPO.setTaskExec(taskExec);
      reportPO.setCheckerId(userId);
      reportPO.setTaskId(taskId);
      reportPO.setTrDomainId(domainId);
      reportPO.setReportTime(new Date());
      this.session.save(reportPO);
      TaskPO taskPO = (TaskPO)this.session.get(TaskPO.class, taskId);
      EmployeeVO EmployeeVO = (EmployeeVO)this.session.load(EmployeeVO.class, userId);
      String userids = "";
      if (taskPO.getTaskPrincipal() != null && !String.valueOf(taskPO.getTaskPrincipal()).equals(String.valueOf(userId)))
        userids = String.valueOf(userids) + taskPO.getTaskPrincipal() + ","; 
      if (taskPO.getTaskChecker() != null && !String.valueOf(taskPO.getTaskChecker()).equals(String.valueOf(userId)))
        userids = String.valueOf(userids) + taskPO.getTaskChecker() + ","; 
      if (!"".equals(userids)) {
        userids = userids.substring(0, userids.trim().length() - 1);
        String title = String.valueOf(EmployeeVO.getEmpName()) + "汇报了" + taskPO.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + taskId + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskPO.getTaskEndTime());
        calendar.set(11, 23);
        calendar.set(12, 59);
        RemindUtil.sendMessageToUsers(title, url, userids, "TaskReport", new Date(), calendar.getTime(), EmployeeVO.getEmpName(), taskId);
      } 
      Set set = taskReportPO.getTaskReportAccessory();
      Set<TaskReportAccessoryPO> set1 = new HashSet();
      HashSet hs = new HashSet();
      reportPO.setTaskReportAccessory(hs);
      Iterator<TaskReportAccessoryPO> iterator = set.iterator();
      TaskReportAccessoryPO taskReportAccessoryPO = null;
      while (iterator != null && iterator.hasNext()) {
        taskReportAccessoryPO = iterator.next();
        taskReportAccessoryPO.setTaskReport(reportPO);
        this.session.save(taskReportAccessoryPO);
        set1.add(taskReportAccessoryPO);
      } 
      reportPO.setTaskReportAccessory(set1);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("add Task Report Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean isPrincipal(Long userId, Long taskId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskPO task = (TaskPO)this.session.load(TaskPO.class, taskId);
      if (userId.equals(task.getTaskPrincipal()))
        result = Boolean.TRUE; 
    } catch (HibernateException ex) {
      System.out.println("Is Principal Exception: " + ex.getMessage());
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean checkTask(Long taskId, String checkOpinion, Date checkTime, String checkerName, Long checkerId, Integer finishRate, Long domainId) {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskPO task = (TaskPO)this.session.load(TaskPO.class, taskId);
      TaskReportPO reportPO = new TaskReportPO();
      TaskExecPO taskExec = getTaskExec(task.getTaskPrincipal(), taskId);
      reportPO.setReportInfo(checkOpinion);
      reportPO.setTaskExec(taskExec);
      reportPO.setCheckerId(checkerId);
      reportPO.setTaskId(taskId);
      reportPO.setReportTime(checkTime);
      reportPO.setCheckerName(checkerName);
      reportPO.setFinishRate(finishRate);
      reportPO.setTrDomainId(domainId);
      this.session.save(reportPO);
      String userids = "";
      if (task.getTaskPrincipal() != null && !String.valueOf(task.getTaskPrincipal()).equals(String.valueOf(checkerId)))
        userids = String.valueOf(userids) + task.getTaskPrincipal() + ","; 
      if (!"".equals(userids)) {
        userids = userids.substring(0, userids.trim().length() - 1);
        String title = String.valueOf(checkerName) + "考核了" + task.getTaskTitle() + "任务";
        String url = "/jsoa/taskAction.do?action=selectSingleTask&cansees=1&taskId=" + task.getTaskId() + "&isArranged=1";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2050, 12, 12);
        RemindUtil.sendMessageToUsers(title, url, userids, "TaskKaoheResult", new Date(), calendar.getTime(), task.getTaskCheckerName(), task.getTaskId());
      } 
      if (!finishRate.equals(new Integer(100))) {
        task = (TaskPO)this.session.load(TaskPO.class, taskId);
        task.setTaskFinishRate(finishRate);
      } else {
        task = (TaskPO)this.session.load(TaskPO.class, taskId);
        task.setTaskStatus(new Integer(2));
      } 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("can't close session Exception:", ex1);
      } finally {
        this.transaction = null;
        this.session = null;
      } 
    } 
    return result;
  }
  
  public RightScope getRightScope(Long userId, Long orgId, String rightType, String rightName) {
    RightScope result = null;
    try {
      begin();
      Query query = this.session.createQuery("select bbb.empId,ccc.rightType,ccc.rightName,aaa.rightScopeType,aaa.rightScopeScope from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          userId + " and ccc.rightType = '" + 
          rightType + 
          "' and ccc.rightName = '" + 
          rightName + "'");
      List<Object[]> list = query.list();
      if (list.size() > 0) {
        result = new RightScope();
        result.setScopeType(-1);
        Object[] o = list.get(0);
        if (o[3] != null) {
          int scopeType = Integer.valueOf(o[3].toString()).intValue();
          result.setScopeType(scopeType);
          if (scopeType == 0) {
            result.setScope("*0*");
          } else if (scopeType == 2) {
            Query query1 = this.session.createQuery("select org.orgId from com.js.system.vo.organizationmanager.OrganizationVO org where org.orgIdString like '%$" + 
                orgId + "$%'");
            List list1 = query1.list();
            Iterator<Long> iterator = list1.iterator();
            StringBuffer orgIds = new StringBuffer();
            Long orgIditem = null;
            while (iterator != null && iterator.hasNext()) {
              orgIditem = iterator.next();
              orgIds.append("*");
              orgIds.append(orgIditem);
              orgIds.append("*");
            } 
            result.setScope(orgIds.toString());
          } else if (scopeType == 3) {
            result.setScope("*" + orgId + "*");
          } else if (scopeType == 4 && 
            o[4] != null) {
            result.setScope(o[4].toString());
          } 
        } 
      } 
    } catch (HibernateException ex) {
      throw new EJBException("Hibernate Exception: ", ex);
    } finally {
      try {
        this.session.close();
      } catch (HibernateException ex1) {
        throw new EJBException("Can not close session!", ex1);
      } 
    } 
    return result;
  }
  
  public Boolean addPeriodicityTask(TaskPeriodicityPO taskPeriodicityPO, String[] fileName, String[] saveName) throws Exception {
    Boolean result = new Boolean(false);
    Long domainId = taskPeriodicityPO.getDomainId();
    try {
      begin();
      if (taskPeriodicityPO.getTaskPrincipal() == null || 
        taskPeriodicityPO.getTaskPrincipal().equals(""))
        taskPeriodicityPO.setTaskPrincipal(taskPeriodicityPO
            .getCreatedEmp()); 
      HashSet<TaskPeriodicityAccessoryPO> Accessory = new HashSet();
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            TaskPeriodicityAccessoryPO taskexecAccessoryPO = 
              new TaskPeriodicityAccessoryPO();
            taskexecAccessoryPO.setAccessoryName(fileName[i]);
            taskexecAccessoryPO.setAccessorySaveName(saveName[i]);
            taskexecAccessoryPO.setDomainId(domainId);
            this.session.save(taskexecAccessoryPO);
            Accessory.add(taskexecAccessoryPO);
          } 
        }  
      taskPeriodicityPO.setTaskAccessory(Accessory);
      this.session.save(taskPeriodicityPO);
      this.session.flush();
      result = Boolean.TRUE;
    } catch (Exception ex) {
      System.out.println("add Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public TaskPeriodicityPO selectPeriodicityTaskView(Long periodicityId, Long domainId) throws HibernateException {
    TaskPeriodicityPO result = null;
    try {
      begin();
      result = (TaskPeriodicityPO)this.session.load(TaskPeriodicityPO.class, 
          periodicityId);
    } catch (HibernateException ex) {
      System.out.println("selectPeriodicityTaskView Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean modifyPeriodicityTask(TaskPeriodicityPO taskPeriodicityPO, String[] fileName, String[] saveName) throws Exception {
    Boolean result = new Boolean(false);
    String ids = taskPeriodicityPO.getPeriodicityId().toString();
    String domainId = taskPeriodicityPO.getDomainId().toString();
    try {
      begin();
      TaskPeriodicityPO taskPeriodicity = (TaskPeriodicityPO)this.session
        .load(TaskPeriodicityPO.class, 
          taskPeriodicityPO.getPeriodicityId());
      taskPeriodicity.setDomainId(taskPeriodicityPO.getDomainId());
      taskPeriodicity.setOnTimeContent(taskPeriodicityPO.getOnTimeContent());
      taskPeriodicity.setOnTimeMode(taskPeriodicityPO.getOnTimeMode());
      taskPeriodicity.setTaskChecker(taskPeriodicityPO.getTaskChecker());
      taskPeriodicity.setTaskCheckerName(taskPeriodicityPO
          .getTaskCheckerName());
      taskPeriodicity.setTaskDescription(taskPeriodicityPO
          .getTaskDescription());
      taskPeriodicity.setTaskJoinedEmp(taskPeriodicityPO.getTaskJoinedEmp());
      taskPeriodicity.setTaskJoinedEmpName(taskPeriodicityPO
          .getTaskJoinedEmpName());
      taskPeriodicity.setTaskJoineOrg(taskPeriodicityPO.getTaskJoineOrg());
      taskPeriodicity.setTaskJoinOrgName(taskPeriodicityPO
          .getTaskJoinOrgName());
      taskPeriodicity.setTaskPrincipal(taskPeriodicityPO.getTaskPrincipal());
      taskPeriodicity.setTaskPrincipalName(taskPeriodicityPO
          .getTaskPrincipalName());
      taskPeriodicity.setTaskPriority(taskPeriodicityPO.getTaskPriority());
      taskPeriodicity.setTaskTitle(taskPeriodicityPO.getTaskTitle());
      taskPeriodicity.setTaskType(taskPeriodicityPO.getTaskType());
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute(
          "delete from oa_taskperiodicityaccessory where PERIODICITYID =" + 
          ids);
      if (fileName != null)
        for (int i = 0; i < fileName.length; i++) {
          if (!"".equals(fileName[i])) {
            TaskPeriodicityAccessoryPO taskexecAccessoryPO = 
              new TaskPeriodicityAccessoryPO();
            taskexecAccessoryPO.setAccessoryName(fileName[i]);
            taskexecAccessoryPO.setAccessorySaveName(saveName[i]);
            taskexecAccessoryPO.setDomainId(new Long(domainId));
            taskexecAccessoryPO.setPeriodicity(taskPeriodicity);
            this.session.save(taskexecAccessoryPO);
          } 
        }  
      this.session.flush();
      stmt.close();
      conn.close();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("modifyPeriodicityTask Exception: " + ex);
      ex.printStackTrace();
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean deletePeriodicityTask(Long periodicityId) throws HibernateException {
    Boolean result = new Boolean(false);
    try {
      begin();
      TaskPeriodicityPO taskPeriodicityPO = (TaskPeriodicityPO)this.session
        .get(TaskPeriodicityPO.class, 
          periodicityId);
      if (taskPeriodicityPO != null)
        this.session.delete(taskPeriodicityPO); 
      this.session.flush();
      result = Boolean.TRUE;
    } catch (HibernateException ex) {
      System.out.println("deletePeriodicityTask Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Integer getTaskPrincipalRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer results = new Integer(0);
    List result = new ArrayList();
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and task.taskParentId =0 and task.taskPrincipal = " + 
        userId + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      Query query = this.session.createQuery("select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
          
          tempHql + 
          ") and task.taskFinishRate<100 " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : "") + 
          " order by task.taskId desc");
      results = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return results;
  }
  
  public Integer getTasktaskJoinedEmpRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer results = new Integer(0);
    List result = new ArrayList();
    try {
      begin();
      StringBuffer taskIds = new StringBuffer();
      String Sql = "";
      if (databaseType.indexOf("db2") >= 0) {
        Sql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0 ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + domainId) : "");
      } else if (databaseType.indexOf("mysql") >= 0) {
        Sql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%' ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "");
      } else {
        Sql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskJoinedEmp like '%" + 
          
          userId + 
          "%' or JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "");
      } 
      Query joinQuery = this.session.createQuery(Sql);
      Iterator<E> iterator = joinQuery.iterate();
      String taskid = "";
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
            taskid + "$%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      Query childPrincipalTask = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskPrincipal =" + 
          userId + 
          " and task.taskParentId<>0" + (
          (domainId != null) ? (" and task.taskDomainId = " + domainId) : 
          ""));
      iterator = childPrincipalTask.iterate();
      while (iterator != null && iterator.hasNext()) {
        taskid = iterator.next().toString();
        taskIds.append(taskid);
        taskIds.append(",");
        Query joinChildTaskQuery = this.session.createQuery("select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.parentIdString like '%$" + 
            taskid + "$%'" + (
            (domainId != null) ? (
            " and task.taskDomainId = " + domainId) : ""));
        Iterator<E> iterator1 = joinChildTaskQuery.iterate();
        String taskChildId = "";
        while (iterator1 != null && iterator1.hasNext()) {
          taskChildId = iterator1.next().toString();
          taskIds.append(taskChildId);
          taskIds.append(",");
        } 
      } 
      String taskId = taskIds.toString();
      if (taskId != null && !taskId.trim().equals("")) {
        taskId = taskId.substring(0, taskId.length() - 1);
        Query q = this.session.createQuery("select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskId in (" + 
            taskId + ") and not task.taskStatus in (2,4) and task.taskFinishRate<100 and ((task.taskHasPass<>0)or(task.taskHasPass=0 and task.createdEmp =" + 
            userId + 
            ")) " + (
            (domainId != null) ? (
            " and task.taskDomainId = " + 
            domainId) : 
            "") + 
            " order by task.parentIdString desc");
        results = Integer.valueOf(q.list().get(0).toString());
      } 
    } catch (HibernateException ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return results;
  }
  
  public String selectUserIds(String orgId) throws HibernateException {
    String userIds = "";
    String whereSql = "";
    StringTokenizer st = new StringTokenizer(orgId, ",");
    whereSql = String.valueOf(whereSql) + " and (";
    while (st.hasMoreTokens())
      whereSql = String.valueOf(whereSql) + " org.orgIdString like '%$" + st.nextToken() + 
        "$%' or "; 
    if (whereSql.equals(" and (")) {
      whereSql = "";
    } else {
      whereSql = String.valueOf(whereSql.substring(0, whereSql.length() - 3)) + ") ";
    } 
    try {
      begin();
      Query query = this.session.createQuery("select empPO.empId from com.js.system.vo.usermanager.EmployeeVO empPO join empPO.organizations org where 1=1 " + 
          whereSql);
      if (query != null) {
        List<String> list = query.list();
        for (int i = 0; i < list.size(); i++)
          userIds = String.valueOf(userIds) + list.get(i) + ","; 
        if (userIds != "")
          userIds = userIds.substring(0, userIds.length() - 1); 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("selectUserIds EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return userIds;
  }
  
  public List selectAllTask(Long userId, Long domainId) throws HibernateException {
    List result = new ArrayList();
    try {
      begin();
      String strsql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and task.taskFinishRate<100 and task.taskParentId =0 and task.taskPrincipal = " + 
        userId + (
        (domainId != null) ? (
        " and task.taskDomainId = " + domainId) : 
        "");
      Query q = this.session.createQuery(strsql);
      q.setMaxResults(20);
      q.setFirstResult(0);
      String taskIds = "";
      List list = q.list();
      if (list != null && list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          Object o = list.get(i);
          taskIds = String.valueOf(taskIds) + o.toString() + ",";
        } 
        taskIds = taskIds.substring(0, taskIds.length() - 1);
      } 
      String strsql1 = "";
      if (databaseType.indexOf("db2") >= 0) {
        strsql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and (task.taskJoinedEmp like '%" + 
          
          userId + "%' or locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0  ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "");
      } else if (databaseType.indexOf("mysql") >= 0) {
        strsql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and (task.taskJoinedEmp like '%" + 
          
          userId + "%' or concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%' )  " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "");
      } else {
        strsql1 = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2,4)) and (task.taskJoinedEmp like '%" + 
          
          userId + "%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' ) " + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : "");
      } 
      Query q1 = this.session.createQuery(strsql1);
      q1.setMaxResults(20);
      q1.setFirstResult(0);
      String taskIds1 = "";
      List list1 = q1.list();
      if (list1 != null && list1.size() > 0) {
        for (int i = 0; i < list1.size(); i++) {
          Object o = list1.get(i);
          taskIds1 = String.valueOf(taskIds1) + o.toString() + ",";
        } 
        taskIds1 = taskIds1.substring(0, taskIds1.length() - 1);
      } 
      if (taskIds.equals(""))
        taskIds = "-1"; 
      if (taskIds1.equals(""))
        taskIds1 = "-1"; 
      String sql = "";
      sql = "select distinct task.taskId,task.taskTitle,task.isArranged from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
        taskIds + ") or task.taskBaseId in (" + taskIds1 + 
        ")and task.taskFinishRate<100 and task.taskDomainId =" + 
        domainId + " order by task.taskId desc";
      Query query = this.session.createQuery(sql);
      query.setMaxResults(20);
      query.setFirstResult(0);
      result = query.list();
    } catch (HibernateException ex) {
      System.out.println("select Settle Finish Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean sendremind(String taskId, String temps, String domainId) throws HibernateException {
    boolean result = false;
    try {
      begin();
      TaskRemindPO taskremindpo = new TaskRemindPO();
      String[] tmp = temps.split(",");
      for (int i = 0; i < tmp.length; i++) {
        taskremindpo.setEmpId(Long.valueOf(tmp[i]));
        taskremindpo.setTaskId(Long.valueOf(taskId));
        taskremindpo.setRemindDomainId(Long.valueOf(domainId));
        this.session.save(taskremindpo);
      } 
      result = true;
    } catch (HibernateException ex) {
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteremind(String taskId, String userId, String domainId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.empId from com.js.oa.scheme.taskcenter.po.TaskRemindPO aaa where aaa.empId=" + 
          userId + " and aaa.taskId=" + 
          taskId + 
          " and aaa.remindDomainId=" + 
          domainId);
      List list = query.list();
      if (list != null && list.size() == 1)
        this.session.delete("from com.js.oa.scheme.taskcenter.po.TaskRemindPO aaa where aaa.empId=" + 
            userId + " and aaa.taskId=" + taskId + 
            " and aaa.remindDomainId=" + domainId); 
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("checkhastype EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public boolean deleteremindall(String taskId, String domainId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.empId from com.js.oa.scheme.taskcenter.po.TaskRemindPO aaa where aaa.taskId=" + 
          taskId + 
          " and aaa.remindDomainId=" + 
          domainId);
      List list = query.list();
      if (list != null && list.size() > 0)
        this.session.delete("from com.js.oa.scheme.taskcenter.po.TaskRemindPO aaa where aaa.taskId=" + 
            taskId + " and aaa.remindDomainId=" + domainId); 
      this.session.flush();
      result = true;
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("checkhastype EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
    } 
    return result;
  }
  
  public String checkhastype(String orgId) throws HibernateException {
    String result = "0";
    String strsql = "";
    strsql = "select count(task.taskId) from com.js.oa.scheme.taskcenter.po.TaskPO task,com.js.oa.scheme.taskcenter.po.TaskClassPO taskclass where task.taskType=taskclass.className and taskclass.classId=" + 
      orgId;
    try {
      begin();
      Query query = this.session.createQuery(strsql);
      if (query != null) {
        List<E> list = query.list();
        if (list.size() > 0)
          result = list.get(0).toString(); 
      } 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("checkhastype EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List getaccessory(String taskid) throws HibernateException {
    List result = new ArrayList();
    String strsql = "";
    strsql = "select accessory.accessoryName,accessory.accessorySaveName from com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO task join task.taskAccessory accessory where task.periodicityId =" + 
      
      taskid;
    try {
      begin();
      Query query = this.session.createQuery(strsql);
      result = query.list();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("checkhastype EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public String getchildtasksta(String taskid) throws HibernateException {
    List<E> list = new ArrayList();
    String result = "1";
    String strsql = "select task.taskFinishRate from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskParentId=" + 
      taskid + 
      " and task.taskStatus<>4 and task.taskFinishRate<100";
    try {
      begin();
      Query query = this.session.createQuery(strsql);
      list = query.list();
      if (list != null && list.size() > 0 && 
        "100".equals(list.get(0).toString()))
        result = "0"; 
      if (list.size() == 0)
        result = "0"; 
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("checkhastype EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public boolean saveHistory(String taskid, String userId, String userName, String orgId, String orgName) throws HibernateException {
    boolean result = false;
    begin();
    try {
      TaskPO taskPO = (TaskPO)this.session.load(TaskPO.class, new Long(taskid), 
          LockMode.UPGRADE);
      TaskHistoryPO taskhisPO = new TaskHistoryPO();
      taskhisPO.setCreatedEmp(taskPO.getCreatedEmp());
      taskhisPO.setCreatedEmpName(taskPO.getCreatedEmpName());
      taskhisPO.setCreatedOrg(taskPO.getCreatedOrg());
      taskhisPO.setCreatedOrgName(taskPO.getCreatedOrgName());
      taskhisPO.setIsArranged(taskPO.getIsArranged());
      taskhisPO.setParentIdString(taskPO.getParentIdString());
      taskhisPO.setTaskAppend(taskPO.getTaskAppend());
      taskhisPO.setTaskAwakeTime(taskPO.getTaskAwakeTime());
      taskhisPO.setTaskBaseId(taskPO.getTaskBaseId());
      taskhisPO.setTaskBeginTime(taskPO.getTaskBeginTime());
      taskhisPO.setTaskChecker(taskPO.getTaskChecker());
      taskhisPO.setTaskCheckerName(taskPO.getTaskCheckerName());
      taskhisPO.setTaskCreatedTime(taskPO.getTaskCreatedTime());
      taskhisPO.setTaskDescription(taskPO.getTaskDescription());
      taskhisPO.setTaskDomainId(taskPO.getTaskDomainId());
      taskhisPO.setTaskEndTime(taskPO.getTaskEndTime());
      taskhisPO.setTaskFinishRate(taskPO.getTaskFinishRate());
      taskhisPO.setTaskHasJunior(taskPO.getTaskHasJunior());
      taskhisPO.setTaskId(new Long(taskid));
      taskhisPO.setTaskJoinedEmp(taskPO.getTaskJoinedEmp());
      taskhisPO.setTaskJoinedEmpName(taskPO.getTaskJoinedEmpName());
      taskhisPO.setTaskJoineOrg(taskPO.getTaskJoineOrg());
      taskhisPO.setTaskJoinOrgName(taskPO.getTaskJoinOrgName());
      taskhisPO.setTaskOrderCode(taskPO.getTaskOrderCode());
      taskhisPO.setTaskParentId(taskPO.getTaskParentId());
      taskhisPO.setTaskPrincipal(taskPO.getTaskPrincipal());
      taskhisPO.setTaskPrincipalName(taskPO.getTaskPrincipalName());
      taskhisPO.setTaskPriority(taskPO.getTaskPriority());
      taskhisPO.setTaskRealEndTime(taskPO.getTaskRealEndTime());
      taskhisPO.setTaskRemind(taskPO.getTaskRemind());
      taskhisPO.setTaskSn(taskPO.getTaskSn());
      taskhisPO.setTaskStatus(taskPO.getTaskStatus());
      taskhisPO.setTaskTitle(taskPO.getTaskTitle());
      taskhisPO.setTaskType(taskPO.getTaskType());
      taskhisPO.setTaskModiOrgId(orgId);
      taskhisPO.setTaskModiOrgName(orgName);
      taskhisPO.setTaskModiTime(new Date());
      taskhisPO.setTaskModiUser(userName);
      taskhisPO.setTaskModiUserId(userId);
      this.session.save(taskhisPO);
      Query query = this.session.createQuery(
          " select aaa.accessoryname,aaa.accessorysavename,aaa.domain_id  from com.js.oa.scheme.taskcenter.po.TaskAccessoryPO aaa  join aaa.task bbb where bbb.taskId = " + 
          
          taskid);
      List<Object[]> list = query.list();
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        TaskHistoryAccessoryPO taskhisaccessoryPO = 
          new TaskHistoryAccessoryPO();
        taskhisaccessoryPO.setAccessoryName(obj[0].toString());
        taskhisaccessoryPO.setAccessorySaveName(obj[1].toString());
        taskhisaccessoryPO.setDomainId(new Long(obj[2].toString()));
        taskhisaccessoryPO.setInformationHistory(taskhisPO);
        this.session.save(taskhisaccessoryPO);
      } 
      this.session.flush();
    } catch (HibernateException ex) {
      ex.printStackTrace();
      System.out.println("saveHistory EJB Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getAccessory(String taskhis, String taskid) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.accessoryName,aaa.accessorySaveName from com.js.oa.scheme.taskcenter.po.TaskHistoryPO taskPO join taskPO.inforHistoryAccessory aaa where taskPO.taskId = " + 
          
          taskid + " and taskPO.taskhisId=" + taskhis);
      list = query.list();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("getAccessory EJB Exception: " + e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List getHistoryInfo(String taskid) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery(
          "select aaa.taskhisId,aaa.taskModiTime,aaa.taskModiUser,aaa.taskModiOrgName from com.js.oa.scheme.taskcenter.po.TaskHistoryPO aaa  where aaa.taskId = " + 
          
          taskid);
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return list;
  }
  
  public List selectSingleTaskHis(String taskhisId) throws HibernateException {
    List list = new ArrayList();
    String strsql = "";
    try {
      begin();
      strsql = "select aaa.taskTitle,aaa.taskPrincipalName,aaa.taskPrincipal,aaa.taskCheckerName,aaa.taskChecker,aaa.taskPriority,aaa.taskStatus,aaa.taskJoinedEmpName,aaa.taskJoinedEmp,aaa.taskJoinOrgName,aaa.taskJoineOrg,aaa.taskDescription,aaa.taskAwakeTime,aaa.taskType,aaa.taskBeginTime,aaa.taskEndTime,aaa.isArranged,aaa.taskAppend from com.js.oa.scheme.taskcenter.po.TaskHistoryPO aaa where aaa.taskhisId=" + 


        
        taskhisId;
      Query query = this.session.createQuery(strsql);
      list = query.list();
    } catch (HibernateException ex) {
      System.out.println("select Single Task Exception: " + ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return list;
  }
  
  public List selectCancelTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    // Byte code:
    //   0: ldc ''
    //   2: astore #7
    //   4: new java/util/ArrayList
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: astore #8
    //   13: aload_0
    //   14: invokevirtual begin : ()V
    //   17: ldc ''
    //   19: astore #9
    //   21: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   24: ldc_w 'db2'
    //   27: invokevirtual indexOf : (Ljava/lang/String;)I
    //   30: iflt -> 86
    //   33: new java/lang/StringBuilder
    //   36: dup
    //   37: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   40: invokespecial <init> : (Ljava/lang/String;)V
    //   43: aload_1
    //   44: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   47: ldc_w ' or task.taskJoinedEmp like '%$'
    //   50: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: aload_1
    //   54: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   57: ldc_w '$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   60: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: aload_1
    //   64: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   67: ldc_w ',' )>0  ) and task.taskDomainId = '
    //   70: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: aload #4
    //   75: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   78: invokevirtual toString : ()Ljava/lang/String;
    //   81: astore #9
    //   83: goto -> 201
    //   86: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   89: ldc_w 'mysql'
    //   92: invokevirtual indexOf : (Ljava/lang/String;)I
    //   95: iflt -> 151
    //   98: new java/lang/StringBuilder
    //   101: dup
    //   102: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   105: invokespecial <init> : (Ljava/lang/String;)V
    //   108: aload_1
    //   109: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   112: ldc_w ' or task.taskJoinedEmp like '%$'
    //   115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: aload_1
    //   119: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   122: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   125: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload_1
    //   129: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   132: ldc_w ',%'  ) and task.taskDomainId = '
    //   135: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: aload #4
    //   140: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   143: invokevirtual toString : ()Ljava/lang/String;
    //   146: astore #9
    //   148: goto -> 201
    //   151: new java/lang/StringBuilder
    //   154: dup
    //   155: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4  and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = '
    //   158: invokespecial <init> : (Ljava/lang/String;)V
    //   161: aload_1
    //   162: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   165: ldc_w ' or task.taskJoinedEmp like '%$'
    //   168: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   171: aload_1
    //   172: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   175: ldc_w '$%' or   JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   178: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   181: aload_1
    //   182: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   185: ldc_w ',%'  ) and task.taskDomainId = '
    //   188: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   191: aload #4
    //   193: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   196: invokevirtual toString : ()Ljava/lang/String;
    //   199: astore #9
    //   201: ldc ''
    //   203: aload #5
    //   205: invokevirtual equals : (Ljava/lang/Object;)Z
    //   208: ifne -> 221
    //   211: ldc ''
    //   213: aload #6
    //   215: invokevirtual equals : (Ljava/lang/Object;)Z
    //   218: ifeq -> 455
    //   221: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   224: ldc_w 'db2'
    //   227: invokevirtual indexOf : (Ljava/lang/String;)I
    //   230: iflt -> 303
    //   233: new java/lang/StringBuilder
    //   236: dup
    //   237: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   240: invokespecial <init> : (Ljava/lang/String;)V
    //   243: aload #9
    //   245: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: ldc_w ')or(task.taskPrincipal = '
    //   251: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   254: aload_1
    //   255: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   258: ldc_w ' or task.taskJoinedEmp like '%$'
    //   261: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: aload_1
    //   265: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   268: ldc_w '$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   271: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: aload_1
    //   275: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   278: ldc_w ',' )>0  ) ) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   281: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: aload #4
    //   286: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   289: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   292: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: invokevirtual toString : ()Ljava/lang/String;
    //   298: astore #7
    //   300: goto -> 734
    //   303: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   306: ldc_w 'mysql'
    //   309: invokevirtual indexOf : (Ljava/lang/String;)I
    //   312: iflt -> 385
    //   315: new java/lang/StringBuilder
    //   318: dup
    //   319: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   322: invokespecial <init> : (Ljava/lang/String;)V
    //   325: aload #9
    //   327: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: ldc_w ')or(task.taskPrincipal = '
    //   333: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   336: aload_1
    //   337: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   340: ldc_w ' or task.taskJoinedEmp like '%$'
    //   343: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   346: aload_1
    //   347: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   350: ldc_w '$%' or  concat(',', task.taskJoineOrg,',') like  '%,'
    //   353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: aload_1
    //   357: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   360: ldc_w ',%' )  ) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   363: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   366: aload #4
    //   368: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   371: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   374: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   377: invokevirtual toString : ()Ljava/lang/String;
    //   380: astore #7
    //   382: goto -> 734
    //   385: new java/lang/StringBuilder
    //   388: dup
    //   389: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   392: invokespecial <init> : (Ljava/lang/String;)V
    //   395: aload #9
    //   397: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   400: ldc_w ')or(task.taskPrincipal = '
    //   403: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   406: aload_1
    //   407: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   410: ldc_w ' or task.taskJoinedEmp like '%$'
    //   413: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   416: aload_1
    //   417: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   420: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   423: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   426: aload_1
    //   427: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   430: ldc_w ',%'  ) ) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   433: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   436: aload #4
    //   438: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   441: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   444: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   447: invokevirtual toString : ()Ljava/lang/String;
    //   450: astore #7
    //   452: goto -> 734
    //   455: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   458: ldc_w 'db2'
    //   461: invokevirtual indexOf : (Ljava/lang/String;)I
    //   464: iflt -> 553
    //   467: new java/lang/StringBuilder
    //   470: dup
    //   471: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   474: invokespecial <init> : (Ljava/lang/String;)V
    //   477: aload #9
    //   479: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   482: ldc_w ')or(task.taskPrincipal = '
    //   485: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: aload_1
    //   489: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   492: ldc_w ' or task.taskJoinedEmp like '%$'
    //   495: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: aload_1
    //   499: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   502: ldc_w '$%' or  locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),','
    //   505: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   508: aload_1
    //   509: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   512: ldc_w ',' )>0  )  ) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   515: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   518: aload #4
    //   520: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   523: ldc_w ' order by task.'
    //   526: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   529: aload #5
    //   531: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   534: ldc_w ' '
    //   537: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   540: aload #6
    //   542: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   545: invokevirtual toString : ()Ljava/lang/String;
    //   548: astore #7
    //   550: goto -> 734
    //   553: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   556: ldc_w 'mysql'
    //   559: invokevirtual indexOf : (Ljava/lang/String;)I
    //   562: iflt -> 651
    //   565: new java/lang/StringBuilder
    //   568: dup
    //   569: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   572: invokespecial <init> : (Ljava/lang/String;)V
    //   575: aload #9
    //   577: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   580: ldc_w ')or(task.taskPrincipal = '
    //   583: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   586: aload_1
    //   587: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   590: ldc_w ' or task.taskJoinedEmp like '%$'
    //   593: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: aload_1
    //   597: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   600: ldc_w '$%' or concat(',', task.taskJoineOrg,',') like  '%,'
    //   603: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   606: aload_1
    //   607: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   610: ldc_w ',%' )) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   613: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   616: aload #4
    //   618: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   621: ldc_w ' order by task.'
    //   624: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   627: aload #5
    //   629: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   632: ldc_w ' '
    //   635: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   638: aload #6
    //   640: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   643: invokevirtual toString : ()Ljava/lang/String;
    //   646: astore #7
    //   648: goto -> 734
    //   651: new java/lang/StringBuilder
    //   654: dup
    //   655: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   658: invokespecial <init> : (Ljava/lang/String;)V
    //   661: aload #9
    //   663: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   666: ldc_w ')or(task.taskPrincipal = '
    //   669: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   672: aload_1
    //   673: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   676: ldc_w ' or task.taskJoinedEmp like '%$'
    //   679: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   682: aload_1
    //   683: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   686: ldc_w '$%' or  JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%,'
    //   689: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   692: aload_1
    //   693: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   696: ldc_w ',%' ) ) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   699: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   702: aload #4
    //   704: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   707: ldc_w ' order by task.'
    //   710: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   713: aload #5
    //   715: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   718: ldc_w ' '
    //   721: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   724: aload #6
    //   726: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   729: invokevirtual toString : ()Ljava/lang/String;
    //   732: astore #7
    //   734: aload_0
    //   735: getfield session : Lnet/sf/hibernate/Session;
    //   738: aload #7
    //   740: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   745: astore #10
    //   747: aload #10
    //   749: aload_2
    //   750: invokevirtual intValue : ()I
    //   753: iconst_1
    //   754: isub
    //   755: aload_3
    //   756: invokevirtual intValue : ()I
    //   759: imul
    //   760: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   765: pop
    //   766: aload #10
    //   768: aload_3
    //   769: invokevirtual intValue : ()I
    //   772: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   777: pop
    //   778: aload #10
    //   780: invokeinterface list : ()Ljava/util/List;
    //   785: invokeinterface iterator : ()Ljava/util/Iterator;
    //   790: astore #11
    //   792: aconst_null
    //   793: astore #12
    //   795: goto -> 1330
    //   798: aload #11
    //   800: invokeinterface next : ()Ljava/lang/Object;
    //   805: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   808: astore #12
    //   810: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   813: aload #12
    //   815: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   818: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   821: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   824: astore #13
    //   826: aload #13
    //   828: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   831: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   834: aload #13
    //   836: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   839: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   842: aload #13
    //   844: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   847: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   850: aload #13
    //   852: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   855: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   858: aload #13
    //   860: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   863: new java/lang/Integer
    //   866: dup
    //   867: iconst_0
    //   868: invokespecial <init> : (I)V
    //   871: invokevirtual equals : (Ljava/lang/Object;)Z
    //   874: ifeq -> 908
    //   877: aload #12
    //   879: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   882: aload_1
    //   883: invokevirtual equals : (Ljava/lang/Object;)Z
    //   886: ifeq -> 928
    //   889: aload #13
    //   891: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   894: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   897: aload #13
    //   899: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   902: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   905: goto -> 928
    //   908: aload #13
    //   910: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   913: invokevirtual intValue : ()I
    //   916: iconst_3
    //   917: if_icmpne -> 928
    //   920: aload #13
    //   922: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   925: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   928: aload #13
    //   930: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   933: new java/lang/Integer
    //   936: dup
    //   937: iconst_1
    //   938: invokespecial <init> : (I)V
    //   941: invokevirtual equals : (Ljava/lang/Object;)Z
    //   944: ifeq -> 967
    //   947: aload #12
    //   949: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   952: aload_1
    //   953: invokevirtual equals : (Ljava/lang/Object;)Z
    //   956: ifeq -> 967
    //   959: aload #13
    //   961: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   964: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   967: aload #12
    //   969: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   972: aload_1
    //   973: invokevirtual equals : (Ljava/lang/Object;)Z
    //   976: ifne -> 1100
    //   979: aload #12
    //   981: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   984: ifnull -> 1021
    //   987: aload #12
    //   989: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   992: new java/lang/StringBuilder
    //   995: dup
    //   996: ldc_w '$'
    //   999: invokespecial <init> : (Ljava/lang/String;)V
    //   1002: aload_1
    //   1003: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1006: ldc_w '$'
    //   1009: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1012: invokevirtual toString : ()Ljava/lang/String;
    //   1015: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1018: ifge -> 1100
    //   1021: aload #12
    //   1023: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1026: ifnull -> 1088
    //   1029: new java/lang/StringBuilder
    //   1032: dup
    //   1033: ldc_w ','
    //   1036: invokespecial <init> : (Ljava/lang/String;)V
    //   1039: aload #12
    //   1041: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1044: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1047: ldc_w ','
    //   1050: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1053: invokevirtual toString : ()Ljava/lang/String;
    //   1056: new java/lang/StringBuilder
    //   1059: dup
    //   1060: ldc_w ','
    //   1063: invokespecial <init> : (Ljava/lang/String;)V
    //   1066: aload_1
    //   1067: invokevirtual toString : ()Ljava/lang/String;
    //   1070: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1073: ldc_w ','
    //   1076: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1079: invokevirtual toString : ()Ljava/lang/String;
    //   1082: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1085: ifge -> 1100
    //   1088: aload #12
    //   1090: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1093: aload_1
    //   1094: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1097: ifeq -> 1125
    //   1100: ldc_w '1'
    //   1103: aload #12
    //   1105: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   1108: invokevirtual toString : ()Ljava/lang/String;
    //   1111: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1114: ifeq -> 1125
    //   1117: aload #13
    //   1119: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1122: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1125: aload #13
    //   1127: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1130: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1133: aload #12
    //   1135: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1138: aload_1
    //   1139: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1142: ifeq -> 1153
    //   1145: aload #13
    //   1147: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1150: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1153: aload #13
    //   1155: ldc_w '0'
    //   1158: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   1161: aload #12
    //   1163: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1166: ifnull -> 1182
    //   1169: ldc ''
    //   1171: aload #12
    //   1173: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1176: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1179: ifeq -> 1203
    //   1182: aload #12
    //   1184: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1187: ifnull -> 1320
    //   1190: ldc ''
    //   1192: aload #12
    //   1194: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1197: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1200: ifne -> 1320
    //   1203: aload #12
    //   1205: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1208: ifnull -> 1245
    //   1211: aload #12
    //   1213: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1216: new java/lang/StringBuilder
    //   1219: dup
    //   1220: ldc_w '$'
    //   1223: invokespecial <init> : (Ljava/lang/String;)V
    //   1226: aload_1
    //   1227: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1230: ldc_w '$'
    //   1233: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1236: invokevirtual toString : ()Ljava/lang/String;
    //   1239: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1242: ifge -> 1312
    //   1245: aload #12
    //   1247: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1250: ifnull -> 1320
    //   1253: new java/lang/StringBuilder
    //   1256: dup
    //   1257: ldc_w ','
    //   1260: invokespecial <init> : (Ljava/lang/String;)V
    //   1263: aload #12
    //   1265: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   1268: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1271: ldc_w ','
    //   1274: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1277: invokevirtual toString : ()Ljava/lang/String;
    //   1280: new java/lang/StringBuilder
    //   1283: dup
    //   1284: ldc_w ','
    //   1287: invokespecial <init> : (Ljava/lang/String;)V
    //   1290: aload_1
    //   1291: invokevirtual toString : ()Ljava/lang/String;
    //   1294: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1297: ldc_w ','
    //   1300: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1303: invokevirtual toString : ()Ljava/lang/String;
    //   1306: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1309: iflt -> 1320
    //   1312: aload #13
    //   1314: ldc_w '1'
    //   1317: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   1320: aload #8
    //   1322: aload #13
    //   1324: invokeinterface add : (Ljava/lang/Object;)Z
    //   1329: pop
    //   1330: aload #11
    //   1332: ifnull -> 1405
    //   1335: aload #11
    //   1337: invokeinterface hasNext : ()Z
    //   1342: ifne -> 798
    //   1345: goto -> 1405
    //   1348: astore #9
    //   1350: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1353: new java/lang/StringBuilder
    //   1356: dup
    //   1357: ldc_w 'select Principal Task Exception: '
    //   1360: invokespecial <init> : (Ljava/lang/String;)V
    //   1363: aload #9
    //   1365: invokevirtual getMessage : ()Ljava/lang/String;
    //   1368: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1371: invokevirtual toString : ()Ljava/lang/String;
    //   1374: invokevirtual println : (Ljava/lang/String;)V
    //   1377: aload #9
    //   1379: athrow
    //   1380: astore #14
    //   1382: aload_0
    //   1383: getfield session : Lnet/sf/hibernate/Session;
    //   1386: invokeinterface close : ()Ljava/sql/Connection;
    //   1391: pop
    //   1392: aload_0
    //   1393: aconst_null
    //   1394: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1397: aload_0
    //   1398: aconst_null
    //   1399: putfield session : Lnet/sf/hibernate/Session;
    //   1402: aload #14
    //   1404: athrow
    //   1405: aload_0
    //   1406: getfield session : Lnet/sf/hibernate/Session;
    //   1409: invokeinterface close : ()Ljava/sql/Connection;
    //   1414: pop
    //   1415: aload_0
    //   1416: aconst_null
    //   1417: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1420: aload_0
    //   1421: aconst_null
    //   1422: putfield session : Lnet/sf/hibernate/Session;
    //   1425: aload #8
    //   1427: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8036	-> 0
    //   #8037	-> 4
    //   #8039	-> 13
    //   #8040	-> 17
    //   #8041	-> 21
    //   #8043	-> 33
    //   #8044	-> 43
    //   #8045	-> 47
    //   #8046	-> 53
    //   #8047	-> 57
    //   #8048	-> 73
    //   #8043	-> 78
    //   #8050	-> 86
    //   #8052	-> 98
    //   #8053	-> 108
    //   #8054	-> 112
    //   #8055	-> 118
    //   #8056	-> 122
    //   #8057	-> 138
    //   #8052	-> 143
    //   #8061	-> 151
    //   #8062	-> 161
    //   #8063	-> 165
    //   #8064	-> 171
    //   #8065	-> 175
    //   #8066	-> 191
    //   #8061	-> 196
    //   #8071	-> 201
    //   #8073	-> 221
    //   #8075	-> 233
    //   #8077	-> 243
    //   #8078	-> 248
    //   #8079	-> 258
    //   #8080	-> 268
    //   #8081	-> 284
    //   #8082	-> 289
    //   #8075	-> 295
    //   #8084	-> 303
    //   #8086	-> 315
    //   #8088	-> 325
    //   #8089	-> 330
    //   #8090	-> 340
    //   #8091	-> 350
    //   #8092	-> 366
    //   #8093	-> 371
    //   #8086	-> 377
    //   #8097	-> 385
    //   #8099	-> 395
    //   #8100	-> 400
    //   #8101	-> 410
    //   #8102	-> 420
    //   #8103	-> 436
    //   #8104	-> 441
    //   #8097	-> 447
    //   #8110	-> 455
    //   #8112	-> 467
    //   #8114	-> 477
    //   #8115	-> 482
    //   #8116	-> 492
    //   #8117	-> 502
    //   #8118	-> 518
    //   #8119	-> 540
    //   #8112	-> 545
    //   #8121	-> 553
    //   #8123	-> 565
    //   #8125	-> 575
    //   #8126	-> 580
    //   #8127	-> 590
    //   #8128	-> 600
    //   #8129	-> 616
    //   #8130	-> 638
    //   #8123	-> 643
    //   #8134	-> 651
    //   #8136	-> 661
    //   #8137	-> 666
    //   #8138	-> 676
    //   #8139	-> 686
    //   #8140	-> 702
    //   #8141	-> 724
    //   #8134	-> 729
    //   #8147	-> 734
    //   #8148	-> 747
    //   #8149	-> 755
    //   #8148	-> 760
    //   #8150	-> 766
    //   #8151	-> 778
    //   #8152	-> 792
    //   #8153	-> 795
    //   #8154	-> 798
    //   #8155	-> 810
    //   #8156	-> 813
    //   #8155	-> 821
    //   #8157	-> 826
    //   #8158	-> 834
    //   #8159	-> 842
    //   #8160	-> 850
    //   #8162	-> 858
    //   #8163	-> 877
    //   #8164	-> 889
    //   #8165	-> 897
    //   #8167	-> 908
    //   #8168	-> 920
    //   #8171	-> 928
    //   #8172	-> 947
    //   #8173	-> 959
    //   #8176	-> 967
    //   #8177	-> 979
    //   #8178	-> 987
    //   #8180	-> 1021
    //   #8181	-> 1029
    //   #8183	-> 1088
    //   #8184	-> 1100
    //   #8185	-> 1117
    //   #8188	-> 1125
    //   #8189	-> 1133
    //   #8190	-> 1145
    //   #8192	-> 1153
    //   #8193	-> 1161
    //   #8194	-> 1169
    //   #8195	-> 1182
    //   #8196	-> 1190
    //   #8197	-> 1203
    //   #8198	-> 1211
    //   #8199	-> 1213
    //   #8200	-> 1245
    //   #8201	-> 1253
    //   #8203	-> 1312
    //   #8206	-> 1320
    //   #8153	-> 1330
    //   #8209	-> 1348
    //   #8210	-> 1350
    //   #8211	-> 1363
    //   #8210	-> 1374
    //   #8212	-> 1377
    //   #8213	-> 1380
    //   #8214	-> 1382
    //   #8215	-> 1392
    //   #8216	-> 1397
    //   #8218	-> 1402
    //   #8214	-> 1405
    //   #8215	-> 1415
    //   #8216	-> 1420
    //   #8219	-> 1425
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	1428	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	1428	1	userId	Ljava/lang/Long;
    //   0	1428	2	currentPage	Ljava/lang/Integer;
    //   0	1428	3	volume	Ljava/lang/Integer;
    //   0	1428	4	domainId	Ljava/lang/Long;
    //   0	1428	5	type	Ljava/lang/String;
    //   0	1428	6	sortType	Ljava/lang/String;
    //   4	1424	7	strsql	Ljava/lang/String;
    //   13	1415	8	result	Ljava/util/List;
    //   21	1327	9	Sql	Ljava/lang/String;
    //   747	601	10	query	Lnet/sf/hibernate/Query;
    //   792	556	11	iterator	Ljava/util/Iterator;
    //   795	553	12	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   826	504	13	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   1350	30	9	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   13	1345	1348	net/sf/hibernate/HibernateException
    //   13	1380	1380	finally
  }
  
  public Integer getCancelTaskRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where not(task.taskStatus in(2)) and task.taskFinishRate<100 and task.taskParentId =0 and task.taskPrincipal = " + 
        userId + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      Query query = this.session.createQuery("select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
          
          tempHql + 
          ") and task.taskFinishRate<100 and task.taskStatus=4" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  private List searchCancelTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    List<TaskVO> result = new ArrayList();
    String Sql = "";
    if (databaseType.indexOf("db2") >= 0) {
      Sql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = " + 
        userId + 
        " or task.taskJoinedEmp like '%$" + 
        userId + 
        "$%' or   locate(JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ','),'," + userId + ",' )>0   ) and task.taskDomainId = " + 
        domainId;
    } else if (databaseType.indexOf("mysql") >= 0) {
      Sql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = " + 
        userId + 
        " or task.taskJoinedEmp like '%$" + 
        userId + 
        "$%' or   concat(',', task.taskJoineOrg,',') like  '%," + userId + ",%'  ) and task.taskDomainId = " + 
        domainId;
    } else {
      Sql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskParentId =0 and task.taskFinishRate<100 and (task.taskPrincipal = " + 
        userId + 
        " or task.taskJoinedEmp like '%$" + 
        userId + 
        "$%' or   JSDB.FN_LINKCHAR(JSDB.FN_LINKCHAR(',', task.taskJoineOrg), ',')  like  '%," + userId + ",%' ) and task.taskDomainId = " + 
        domainId;
    } 
    String strsql = "";
    if (!"".equals(parameters[0].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskTitle like '%" + parameters[0].toString() + 
          "%'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
          parameters[0].toString() + "%'";
      }  
    if (!"".equals(parameters[1].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskFinishRate=" + parameters[1].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
          parameters[1].toString();
      }  
    if (!"".equals(parameters[2].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskType='" + parameters[2].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
          "'";
      }  
    if (!"".equals(parameters[3].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskPriority=" + parameters[3].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
          parameters[3].toString();
      }  
    if (!"".equals(parameters[4].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskStatus=" + parameters[4].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
      }  
    if (!"".equals(parameters[5].toString()) && 
      "1".equals(parameters[5].toString()))
      if ("".equals(strsql)) {
        if (databaseType.indexOf("mysql") >= 0) {
          strsql = "task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = 
            "task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        } 
      } else if (databaseType.indexOf("mysql") >= 0) {
        strsql = String.valueOf(strsql) + 
          " and task.taskEndTime between '" + 
          parameters[6].toString() + 
          "'  and '" + 
          parameters[7].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + 
          " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
          parameters[6].toString() + 
          "','S')  and JSDB.FN_STRTODATE('" + 
          parameters[7].toString() + "','S')";
      }  
    if ("".equals(strsql))
      strsql = "1=1"; 
    String strsql1 = "";
    if ("".equals(parameters[8].toString())) {
      strsql1 = " order by task.parentIdString";
    } else {
      strsql1 = " order by task." + parameters[8].toString() + " " + 
        parameters[9].toString();
    } 
    Query query = this.session.createQuery("select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
        
        Sql + 
        ")and task.taskFinishRate<100 and task.taskStatus=4 and (" + 
        strsql + 
        ") and task.taskDomainId = " + 
        domainId + strsql1);
    query.setFirstResult((currentPage.intValue() - 1) * volume.intValue());
    query.setMaxResults(volume.intValue());
    Iterator<TaskPO> iterator = query.list().iterator();
    TaskPO taskPO = null;
    while (iterator != null && iterator.hasNext()) {
      taskPO = iterator.next();
      TaskVO task = (TaskVO)TransformObject.getInstance()
        .transformObject(taskPO, TaskVO.class);
      task.setCanCancel(Boolean.FALSE);
      task.setCanDelete(Boolean.FALSE);
      task.setCanReport(Boolean.FALSE);
      task.setMaintenance(Boolean.FALSE);
      if (task.getTaskStatus().equals(new Integer(0))) {
        task.setMaintenance(Boolean.TRUE);
        if (taskPO.getCreatedEmp().equals(userId))
          task.setCanDelete(Boolean.TRUE); 
      } 
      if (taskPO.getCreatedEmp().equals(userId) || (
        taskPO.getTaskJoinedEmp() != null && 
        taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0) || 
        taskPO.getTaskPrincipal().equals(userId))
        task.setCanReport(Boolean.TRUE); 
      task.setCanNew(Boolean.FALSE);
      if ((taskPO.getTaskJoinedEmp() != null && 
        taskPO.getTaskJoinedEmp().indexOf("$" + userId + "$") >= 0) || 
        taskPO.getCreatedEmp().equals(userId) || 
        taskPO.getTaskPrincipal().equals(userId))
        task.setCanNew(Boolean.TRUE); 
      result.add(task);
    } 
    return result;
  }
  
  private Integer getSearchCancelTaskRecordCount(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskFinishRate<100 and task.taskParentId =0 and task.taskPrincipal = " + 
      userId + (
      (domainId != null) ? (
      " and task.taskDomainId = " + domainId) : 
      "");
    String strsql = "";
    if (!"".equals(parameters[0].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskTitle like '%" + parameters[0].toString() + 
          "%'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
          parameters[0].toString() + "%'";
      }  
    if (!"".equals(parameters[1].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskFinishRate=" + parameters[1].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
          parameters[1].toString();
      }  
    if (!"".equals(parameters[2].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskType='" + parameters[2].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + " and task.taskType='" + parameters[2].toString() + 
          "'";
      }  
    if (!"".equals(parameters[3].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskPriority=" + parameters[3].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
          parameters[3].toString();
      }  
    if (!"".equals(parameters[4].toString()))
      if ("".equals(strsql)) {
        strsql = "task.taskStatus=" + parameters[4].toString();
      } else {
        strsql = String.valueOf(strsql) + " and task.taskStatus=" + parameters[4].toString();
      }  
    if (!"".equals(parameters[5].toString()) && 
      "1".equals(parameters[5].toString()))
      if ("".equals(strsql)) {
        if (databaseType.indexOf("mysql") >= 0) {
          strsql = "task.taskEndTime between '" + 
            parameters[6].toString() + 
            "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = 
            "task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        } 
      } else if (databaseType.indexOf("mysql") >= 0) {
        strsql = String.valueOf(strsql) + 
          " and task.taskEndTime between '" + 
          parameters[6].toString() + 
          "'  and '" + 
          parameters[7].toString() + "'";
      } else {
        strsql = String.valueOf(strsql) + 
          " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
          parameters[6].toString() + 
          "','S')  and JSDB.FN_STRTODATE('" + 
          parameters[7].toString() + "','S')";
      }  
    if ("".equals(strsql))
      strsql = "1=1"; 
    Query query = this.session.createQuery("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
        
        tempHql + 
        ") and (task.taskFinishRate<100) and task.taskStatus=4 and (" + 
        strsql + ")" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        ""));
    result = Integer.valueOf(query.list().get(0).toString());
    return result;
  }
  
  public List selectsettleCancelTask(Long userId, Integer currentPage, Integer volume, Long domainId, String type, String sortType) throws HibernateException {
    // Byte code:
    //   0: ldc ''
    //   2: astore #7
    //   4: new java/util/ArrayList
    //   7: dup
    //   8: invokespecial <init> : ()V
    //   11: astore #8
    //   13: aload_0
    //   14: invokevirtual begin : ()V
    //   17: new java/lang/StringBuilder
    //   20: dup
    //   21: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4  and task.taskParentId =0 and task.taskFinishRate<100 and (task.createdEmp = '
    //   24: invokespecial <init> : (Ljava/lang/String;)V
    //   27: aload_1
    //   28: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   31: ldc_w ') and task.taskDomainId = '
    //   34: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: aload #4
    //   39: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   42: invokevirtual toString : ()Ljava/lang/String;
    //   45: astore #9
    //   47: ldc ''
    //   49: aload #5
    //   51: invokevirtual equals : (Ljava/lang/Object;)Z
    //   54: ifne -> 67
    //   57: ldc ''
    //   59: aload #6
    //   61: invokevirtual equals : (Ljava/lang/Object;)Z
    //   64: ifeq -> 117
    //   67: new java/lang/StringBuilder
    //   70: dup
    //   71: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   74: invokespecial <init> : (Ljava/lang/String;)V
    //   77: aload #9
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ')or(task.createdEmp = '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_1
    //   89: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   92: ldc_w ')) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   95: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: aload #4
    //   100: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   103: ldc_w ' order by task.parentIdString desc,task.taskCreatedTime'
    //   106: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: invokevirtual toString : ()Ljava/lang/String;
    //   112: astore #7
    //   114: goto -> 180
    //   117: new java/lang/StringBuilder
    //   120: dup
    //   121: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where (task.taskBaseId in ('
    //   124: invokespecial <init> : (Ljava/lang/String;)V
    //   127: aload #9
    //   129: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: ldc_w ')or(task.createdEmp = '
    //   135: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   138: aload_1
    //   139: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   142: ldc_w ')) and task.taskFinishRate<100 and task.taskStatus=4 and task.taskDomainId ='
    //   145: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   148: aload #4
    //   150: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   153: ldc_w ' order by task.'
    //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: aload #5
    //   161: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   164: ldc_w ' '
    //   167: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   170: aload #6
    //   172: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   175: invokevirtual toString : ()Ljava/lang/String;
    //   178: astore #7
    //   180: aload_0
    //   181: getfield session : Lnet/sf/hibernate/Session;
    //   184: aload #7
    //   186: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   191: astore #10
    //   193: aload #10
    //   195: aload_2
    //   196: invokevirtual intValue : ()I
    //   199: iconst_1
    //   200: isub
    //   201: aload_3
    //   202: invokevirtual intValue : ()I
    //   205: imul
    //   206: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   211: pop
    //   212: aload #10
    //   214: aload_3
    //   215: invokevirtual intValue : ()I
    //   218: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   223: pop
    //   224: aload #10
    //   226: invokeinterface list : ()Ljava/util/List;
    //   231: invokeinterface iterator : ()Ljava/util/Iterator;
    //   236: astore #11
    //   238: aconst_null
    //   239: astore #12
    //   241: goto -> 776
    //   244: aload #11
    //   246: invokeinterface next : ()Ljava/lang/Object;
    //   251: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   254: astore #12
    //   256: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   259: aload #12
    //   261: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   264: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   267: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   270: astore #13
    //   272: aload #13
    //   274: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   277: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   280: aload #13
    //   282: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   285: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   288: aload #13
    //   290: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   293: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   296: aload #13
    //   298: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   301: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   304: aload #13
    //   306: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   309: new java/lang/Integer
    //   312: dup
    //   313: iconst_0
    //   314: invokespecial <init> : (I)V
    //   317: invokevirtual equals : (Ljava/lang/Object;)Z
    //   320: ifeq -> 354
    //   323: aload #12
    //   325: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   328: aload_1
    //   329: invokevirtual equals : (Ljava/lang/Object;)Z
    //   332: ifeq -> 374
    //   335: aload #13
    //   337: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   340: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   343: aload #13
    //   345: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   348: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   351: goto -> 374
    //   354: aload #13
    //   356: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   359: invokevirtual intValue : ()I
    //   362: iconst_3
    //   363: if_icmpne -> 374
    //   366: aload #13
    //   368: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   371: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   374: aload #13
    //   376: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   379: new java/lang/Integer
    //   382: dup
    //   383: iconst_1
    //   384: invokespecial <init> : (I)V
    //   387: invokevirtual equals : (Ljava/lang/Object;)Z
    //   390: ifeq -> 413
    //   393: aload #12
    //   395: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   398: aload_1
    //   399: invokevirtual equals : (Ljava/lang/Object;)Z
    //   402: ifeq -> 413
    //   405: aload #13
    //   407: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   410: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   413: aload #12
    //   415: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   418: aload_1
    //   419: invokevirtual equals : (Ljava/lang/Object;)Z
    //   422: ifne -> 546
    //   425: aload #12
    //   427: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   430: ifnull -> 467
    //   433: aload #12
    //   435: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   438: new java/lang/StringBuilder
    //   441: dup
    //   442: ldc_w '$'
    //   445: invokespecial <init> : (Ljava/lang/String;)V
    //   448: aload_1
    //   449: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   452: ldc_w '$'
    //   455: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   458: invokevirtual toString : ()Ljava/lang/String;
    //   461: invokevirtual indexOf : (Ljava/lang/String;)I
    //   464: ifge -> 546
    //   467: aload #12
    //   469: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   472: ifnull -> 534
    //   475: new java/lang/StringBuilder
    //   478: dup
    //   479: ldc_w ','
    //   482: invokespecial <init> : (Ljava/lang/String;)V
    //   485: aload #12
    //   487: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   490: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   493: ldc_w ','
    //   496: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   499: invokevirtual toString : ()Ljava/lang/String;
    //   502: new java/lang/StringBuilder
    //   505: dup
    //   506: ldc_w ','
    //   509: invokespecial <init> : (Ljava/lang/String;)V
    //   512: aload_1
    //   513: invokevirtual toString : ()Ljava/lang/String;
    //   516: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   519: ldc_w ','
    //   522: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   525: invokevirtual toString : ()Ljava/lang/String;
    //   528: invokevirtual indexOf : (Ljava/lang/String;)I
    //   531: ifge -> 546
    //   534: aload #12
    //   536: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   539: aload_1
    //   540: invokevirtual equals : (Ljava/lang/Object;)Z
    //   543: ifeq -> 571
    //   546: ldc_w '1'
    //   549: aload #12
    //   551: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   554: invokevirtual toString : ()Ljava/lang/String;
    //   557: invokevirtual equals : (Ljava/lang/Object;)Z
    //   560: ifeq -> 571
    //   563: aload #13
    //   565: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   568: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   571: aload #13
    //   573: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   576: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   579: aload #12
    //   581: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   584: aload_1
    //   585: invokevirtual equals : (Ljava/lang/Object;)Z
    //   588: ifeq -> 599
    //   591: aload #13
    //   593: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   596: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   599: aload #13
    //   601: ldc_w '0'
    //   604: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   607: aload #12
    //   609: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   612: ifnull -> 628
    //   615: ldc ''
    //   617: aload #12
    //   619: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   622: invokevirtual equals : (Ljava/lang/Object;)Z
    //   625: ifeq -> 649
    //   628: aload #12
    //   630: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   633: ifnull -> 766
    //   636: ldc ''
    //   638: aload #12
    //   640: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   643: invokevirtual equals : (Ljava/lang/Object;)Z
    //   646: ifne -> 766
    //   649: aload #12
    //   651: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   654: ifnull -> 691
    //   657: aload #12
    //   659: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   662: new java/lang/StringBuilder
    //   665: dup
    //   666: ldc_w '$'
    //   669: invokespecial <init> : (Ljava/lang/String;)V
    //   672: aload_1
    //   673: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   676: ldc_w '$'
    //   679: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   682: invokevirtual toString : ()Ljava/lang/String;
    //   685: invokevirtual indexOf : (Ljava/lang/String;)I
    //   688: ifge -> 758
    //   691: aload #12
    //   693: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   696: ifnull -> 766
    //   699: new java/lang/StringBuilder
    //   702: dup
    //   703: ldc_w ','
    //   706: invokespecial <init> : (Ljava/lang/String;)V
    //   709: aload #12
    //   711: invokevirtual getTaskJoineOrg : ()Ljava/lang/String;
    //   714: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   717: ldc_w ','
    //   720: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   723: invokevirtual toString : ()Ljava/lang/String;
    //   726: new java/lang/StringBuilder
    //   729: dup
    //   730: ldc_w ','
    //   733: invokespecial <init> : (Ljava/lang/String;)V
    //   736: aload_1
    //   737: invokevirtual toString : ()Ljava/lang/String;
    //   740: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   743: ldc_w ','
    //   746: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   749: invokevirtual toString : ()Ljava/lang/String;
    //   752: invokevirtual indexOf : (Ljava/lang/String;)I
    //   755: iflt -> 766
    //   758: aload #13
    //   760: ldc_w '1'
    //   763: invokevirtual setTaskTypeShow : (Ljava/lang/String;)V
    //   766: aload #8
    //   768: aload #13
    //   770: invokeinterface add : (Ljava/lang/Object;)Z
    //   775: pop
    //   776: aload #11
    //   778: ifnull -> 851
    //   781: aload #11
    //   783: invokeinterface hasNext : ()Z
    //   788: ifne -> 244
    //   791: goto -> 851
    //   794: astore #9
    //   796: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   799: new java/lang/StringBuilder
    //   802: dup
    //   803: ldc_w 'select Principal Task Exception: '
    //   806: invokespecial <init> : (Ljava/lang/String;)V
    //   809: aload #9
    //   811: invokevirtual getMessage : ()Ljava/lang/String;
    //   814: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   817: invokevirtual toString : ()Ljava/lang/String;
    //   820: invokevirtual println : (Ljava/lang/String;)V
    //   823: aload #9
    //   825: athrow
    //   826: astore #14
    //   828: aload_0
    //   829: getfield session : Lnet/sf/hibernate/Session;
    //   832: invokeinterface close : ()Ljava/sql/Connection;
    //   837: pop
    //   838: aload_0
    //   839: aconst_null
    //   840: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   843: aload_0
    //   844: aconst_null
    //   845: putfield session : Lnet/sf/hibernate/Session;
    //   848: aload #14
    //   850: athrow
    //   851: aload_0
    //   852: getfield session : Lnet/sf/hibernate/Session;
    //   855: invokeinterface close : ()Ljava/sql/Connection;
    //   860: pop
    //   861: aload_0
    //   862: aconst_null
    //   863: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   866: aload_0
    //   867: aconst_null
    //   868: putfield session : Lnet/sf/hibernate/Session;
    //   871: aload #8
    //   873: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8590	-> 0
    //   #8591	-> 4
    //   #8593	-> 13
    //   #8594	-> 17
    //   #8595	-> 27
    //   #8596	-> 31
    //   #8597	-> 37
    //   #8594	-> 42
    //   #8599	-> 47
    //   #8600	-> 67
    //   #8602	-> 77
    //   #8603	-> 82
    //   #8604	-> 92
    //   #8605	-> 98
    //   #8606	-> 103
    //   #8600	-> 109
    //   #8608	-> 117
    //   #8610	-> 127
    //   #8611	-> 132
    //   #8612	-> 142
    //   #8613	-> 148
    //   #8614	-> 170
    //   #8608	-> 175
    //   #8618	-> 180
    //   #8619	-> 193
    //   #8620	-> 201
    //   #8619	-> 206
    //   #8621	-> 212
    //   #8622	-> 224
    //   #8623	-> 238
    //   #8624	-> 241
    //   #8625	-> 244
    //   #8626	-> 256
    //   #8627	-> 259
    //   #8626	-> 267
    //   #8628	-> 272
    //   #8629	-> 280
    //   #8630	-> 288
    //   #8631	-> 296
    //   #8633	-> 304
    //   #8634	-> 323
    //   #8635	-> 335
    //   #8636	-> 343
    //   #8638	-> 354
    //   #8639	-> 366
    //   #8642	-> 374
    //   #8643	-> 393
    //   #8644	-> 405
    //   #8647	-> 413
    //   #8648	-> 425
    //   #8649	-> 433
    //   #8651	-> 467
    //   #8652	-> 475
    //   #8654	-> 534
    //   #8655	-> 546
    //   #8656	-> 563
    //   #8659	-> 571
    //   #8660	-> 579
    //   #8661	-> 591
    //   #8663	-> 599
    //   #8664	-> 607
    //   #8665	-> 615
    //   #8666	-> 628
    //   #8667	-> 636
    //   #8668	-> 649
    //   #8669	-> 657
    //   #8670	-> 659
    //   #8671	-> 691
    //   #8672	-> 699
    //   #8674	-> 758
    //   #8677	-> 766
    //   #8624	-> 776
    //   #8680	-> 794
    //   #8681	-> 796
    //   #8682	-> 809
    //   #8681	-> 820
    //   #8683	-> 823
    //   #8684	-> 826
    //   #8685	-> 828
    //   #8686	-> 838
    //   #8687	-> 843
    //   #8689	-> 848
    //   #8685	-> 851
    //   #8686	-> 861
    //   #8687	-> 866
    //   #8690	-> 871
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	874	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	874	1	userId	Ljava/lang/Long;
    //   0	874	2	currentPage	Ljava/lang/Integer;
    //   0	874	3	volume	Ljava/lang/Integer;
    //   0	874	4	domainId	Ljava/lang/Long;
    //   0	874	5	type	Ljava/lang/String;
    //   0	874	6	sortType	Ljava/lang/String;
    //   4	870	7	strsql	Ljava/lang/String;
    //   13	861	8	result	Ljava/util/List;
    //   47	747	9	tempHql	Ljava/lang/String;
    //   193	601	10	query	Lnet/sf/hibernate/Query;
    //   238	556	11	iterator	Ljava/util/Iterator;
    //   241	553	12	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   272	504	13	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   796	30	9	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   13	791	794	net/sf/hibernate/HibernateException
    //   13	826	826	finally
  }
  
  public Integer getCancelSettleRecordCount(Long userId, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskFinishRate<100 and task.taskParentId =0 and task.createdEmp = " + 
        userId + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      Query query = this.session.createQuery("select count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
          
          tempHql + 
          ") and task.taskFinishRate<100 and task.taskStatus=4" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : ""));
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("select Principal Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public List searchCancelSettleTask(Long userId, String[] parameters, Integer currentPage, Integer volume, Long domainId) throws HibernateException {
    // Byte code:
    //   0: new java/util/ArrayList
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #6
    //   9: aload_0
    //   10: invokevirtual begin : ()V
    //   13: new java/lang/StringBuilder
    //   16: dup
    //   17: ldc_w 'select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskParentId =0 and task.taskFinishRate<100 and (task.createdEmp = '
    //   20: invokespecial <init> : (Ljava/lang/String;)V
    //   23: aload_1
    //   24: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   27: ldc_w ') and task.taskDomainId = '
    //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: aload #5
    //   35: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   38: invokevirtual toString : ()Ljava/lang/String;
    //   41: astore #7
    //   43: ldc ''
    //   45: astore #8
    //   47: ldc ''
    //   49: aload_2
    //   50: iconst_0
    //   51: aaload
    //   52: invokevirtual toString : ()Ljava/lang/String;
    //   55: invokevirtual equals : (Ljava/lang/Object;)Z
    //   58: ifne -> 142
    //   61: ldc ''
    //   63: aload #8
    //   65: invokevirtual equals : (Ljava/lang/Object;)Z
    //   68: ifeq -> 104
    //   71: new java/lang/StringBuilder
    //   74: dup
    //   75: ldc_w 'task.taskTitle like '%'
    //   78: invokespecial <init> : (Ljava/lang/String;)V
    //   81: aload_2
    //   82: iconst_0
    //   83: aaload
    //   84: invokevirtual toString : ()Ljava/lang/String;
    //   87: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   90: ldc_w '%''
    //   93: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   96: invokevirtual toString : ()Ljava/lang/String;
    //   99: astore #8
    //   101: goto -> 142
    //   104: new java/lang/StringBuilder
    //   107: dup
    //   108: aload #8
    //   110: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   113: invokespecial <init> : (Ljava/lang/String;)V
    //   116: ldc_w ' and task.taskTitle like '%'
    //   119: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: aload_2
    //   123: iconst_0
    //   124: aaload
    //   125: invokevirtual toString : ()Ljava/lang/String;
    //   128: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: ldc_w '%''
    //   134: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: invokevirtual toString : ()Ljava/lang/String;
    //   140: astore #8
    //   142: ldc ''
    //   144: aload_2
    //   145: iconst_1
    //   146: aaload
    //   147: invokevirtual toString : ()Ljava/lang/String;
    //   150: invokevirtual equals : (Ljava/lang/Object;)Z
    //   153: ifne -> 225
    //   156: ldc ''
    //   158: aload #8
    //   160: invokevirtual equals : (Ljava/lang/Object;)Z
    //   163: ifeq -> 193
    //   166: new java/lang/StringBuilder
    //   169: dup
    //   170: ldc_w 'task.taskFinishRate='
    //   173: invokespecial <init> : (Ljava/lang/String;)V
    //   176: aload_2
    //   177: iconst_1
    //   178: aaload
    //   179: invokevirtual toString : ()Ljava/lang/String;
    //   182: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: invokevirtual toString : ()Ljava/lang/String;
    //   188: astore #8
    //   190: goto -> 225
    //   193: new java/lang/StringBuilder
    //   196: dup
    //   197: aload #8
    //   199: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   202: invokespecial <init> : (Ljava/lang/String;)V
    //   205: ldc_w ' and task.taskFinishRate='
    //   208: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: aload_2
    //   212: iconst_1
    //   213: aaload
    //   214: invokevirtual toString : ()Ljava/lang/String;
    //   217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: invokevirtual toString : ()Ljava/lang/String;
    //   223: astore #8
    //   225: ldc ''
    //   227: aload_2
    //   228: iconst_2
    //   229: aaload
    //   230: invokevirtual toString : ()Ljava/lang/String;
    //   233: invokevirtual equals : (Ljava/lang/Object;)Z
    //   236: ifne -> 318
    //   239: ldc ''
    //   241: aload #8
    //   243: invokevirtual equals : (Ljava/lang/Object;)Z
    //   246: ifeq -> 281
    //   249: new java/lang/StringBuilder
    //   252: dup
    //   253: ldc_w 'task.taskType=''
    //   256: invokespecial <init> : (Ljava/lang/String;)V
    //   259: aload_2
    //   260: iconst_2
    //   261: aaload
    //   262: invokevirtual toString : ()Ljava/lang/String;
    //   265: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   268: ldc '''
    //   270: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   273: invokevirtual toString : ()Ljava/lang/String;
    //   276: astore #8
    //   278: goto -> 318
    //   281: new java/lang/StringBuilder
    //   284: dup
    //   285: aload #8
    //   287: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   290: invokespecial <init> : (Ljava/lang/String;)V
    //   293: ldc_w ' and task.taskType=''
    //   296: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   299: aload_2
    //   300: iconst_2
    //   301: aaload
    //   302: invokevirtual toString : ()Ljava/lang/String;
    //   305: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: ldc '''
    //   310: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   313: invokevirtual toString : ()Ljava/lang/String;
    //   316: astore #8
    //   318: ldc ''
    //   320: aload_2
    //   321: iconst_3
    //   322: aaload
    //   323: invokevirtual toString : ()Ljava/lang/String;
    //   326: invokevirtual equals : (Ljava/lang/Object;)Z
    //   329: ifne -> 401
    //   332: ldc ''
    //   334: aload #8
    //   336: invokevirtual equals : (Ljava/lang/Object;)Z
    //   339: ifeq -> 369
    //   342: new java/lang/StringBuilder
    //   345: dup
    //   346: ldc_w 'task.taskPriority='
    //   349: invokespecial <init> : (Ljava/lang/String;)V
    //   352: aload_2
    //   353: iconst_3
    //   354: aaload
    //   355: invokevirtual toString : ()Ljava/lang/String;
    //   358: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   361: invokevirtual toString : ()Ljava/lang/String;
    //   364: astore #8
    //   366: goto -> 401
    //   369: new java/lang/StringBuilder
    //   372: dup
    //   373: aload #8
    //   375: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   378: invokespecial <init> : (Ljava/lang/String;)V
    //   381: ldc_w ' and task.taskPriority='
    //   384: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   387: aload_2
    //   388: iconst_3
    //   389: aaload
    //   390: invokevirtual toString : ()Ljava/lang/String;
    //   393: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   396: invokevirtual toString : ()Ljava/lang/String;
    //   399: astore #8
    //   401: ldc ''
    //   403: aload_2
    //   404: iconst_4
    //   405: aaload
    //   406: invokevirtual toString : ()Ljava/lang/String;
    //   409: invokevirtual equals : (Ljava/lang/Object;)Z
    //   412: ifne -> 484
    //   415: ldc ''
    //   417: aload #8
    //   419: invokevirtual equals : (Ljava/lang/Object;)Z
    //   422: ifeq -> 452
    //   425: new java/lang/StringBuilder
    //   428: dup
    //   429: ldc_w 'task.taskStatus='
    //   432: invokespecial <init> : (Ljava/lang/String;)V
    //   435: aload_2
    //   436: iconst_4
    //   437: aaload
    //   438: invokevirtual toString : ()Ljava/lang/String;
    //   441: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   444: invokevirtual toString : ()Ljava/lang/String;
    //   447: astore #8
    //   449: goto -> 484
    //   452: new java/lang/StringBuilder
    //   455: dup
    //   456: aload #8
    //   458: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   461: invokespecial <init> : (Ljava/lang/String;)V
    //   464: ldc_w ' and task.taskStatus='
    //   467: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: aload_2
    //   471: iconst_4
    //   472: aaload
    //   473: invokevirtual toString : ()Ljava/lang/String;
    //   476: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   479: invokevirtual toString : ()Ljava/lang/String;
    //   482: astore #8
    //   484: ldc ''
    //   486: aload_2
    //   487: iconst_5
    //   488: aaload
    //   489: invokevirtual toString : ()Ljava/lang/String;
    //   492: invokevirtual equals : (Ljava/lang/Object;)Z
    //   495: ifne -> 758
    //   498: ldc_w '1'
    //   501: aload_2
    //   502: iconst_5
    //   503: aaload
    //   504: invokevirtual toString : ()Ljava/lang/String;
    //   507: invokevirtual equals : (Ljava/lang/Object;)Z
    //   510: ifeq -> 758
    //   513: ldc ''
    //   515: aload #8
    //   517: invokevirtual equals : (Ljava/lang/Object;)Z
    //   520: ifeq -> 634
    //   523: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   526: ldc_w 'mysql'
    //   529: invokevirtual indexOf : (Ljava/lang/String;)I
    //   532: iflt -> 584
    //   535: new java/lang/StringBuilder
    //   538: dup
    //   539: ldc_w 'task.taskEndTime between ''
    //   542: invokespecial <init> : (Ljava/lang/String;)V
    //   545: aload_2
    //   546: bipush #6
    //   548: aaload
    //   549: invokevirtual toString : ()Ljava/lang/String;
    //   552: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   555: ldc_w '' and ''
    //   558: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   561: aload_2
    //   562: bipush #7
    //   564: aaload
    //   565: invokevirtual toString : ()Ljava/lang/String;
    //   568: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   571: ldc '''
    //   573: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   576: invokevirtual toString : ()Ljava/lang/String;
    //   579: astore #8
    //   581: goto -> 758
    //   584: new java/lang/StringBuilder
    //   587: dup
    //   588: ldc_w 'task.taskEndTime between JSDB.FN_STRTODATE(''
    //   591: invokespecial <init> : (Ljava/lang/String;)V
    //   594: aload_2
    //   595: bipush #6
    //   597: aaload
    //   598: invokevirtual toString : ()Ljava/lang/String;
    //   601: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   604: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   607: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   610: aload_2
    //   611: bipush #7
    //   613: aaload
    //   614: invokevirtual toString : ()Ljava/lang/String;
    //   617: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   620: ldc_w '','S')'
    //   623: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   626: invokevirtual toString : ()Ljava/lang/String;
    //   629: astore #8
    //   631: goto -> 758
    //   634: getstatic com/js/oa/scheme/taskcenter/bean/TaskEJBBean.databaseType : Ljava/lang/String;
    //   637: ldc_w 'mysql'
    //   640: invokevirtual indexOf : (Ljava/lang/String;)I
    //   643: iflt -> 703
    //   646: new java/lang/StringBuilder
    //   649: dup
    //   650: aload #8
    //   652: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   655: invokespecial <init> : (Ljava/lang/String;)V
    //   658: ldc_w ' and task.taskEndTime between ''
    //   661: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   664: aload_2
    //   665: bipush #6
    //   667: aaload
    //   668: invokevirtual toString : ()Ljava/lang/String;
    //   671: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   674: ldc_w ''  and ''
    //   677: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   680: aload_2
    //   681: bipush #7
    //   683: aaload
    //   684: invokevirtual toString : ()Ljava/lang/String;
    //   687: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   690: ldc '''
    //   692: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   695: invokevirtual toString : ()Ljava/lang/String;
    //   698: astore #8
    //   700: goto -> 758
    //   703: new java/lang/StringBuilder
    //   706: dup
    //   707: aload #8
    //   709: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   712: invokespecial <init> : (Ljava/lang/String;)V
    //   715: ldc_w ' and task.taskEndTime between JSDB.FN_STRTODATE(''
    //   718: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   721: aload_2
    //   722: bipush #6
    //   724: aaload
    //   725: invokevirtual toString : ()Ljava/lang/String;
    //   728: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   731: ldc_w '','S')  and JSDB.FN_STRTODATE(''
    //   734: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   737: aload_2
    //   738: bipush #7
    //   740: aaload
    //   741: invokevirtual toString : ()Ljava/lang/String;
    //   744: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   747: ldc_w '','S')'
    //   750: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   753: invokevirtual toString : ()Ljava/lang/String;
    //   756: astore #8
    //   758: ldc ''
    //   760: aload #8
    //   762: invokevirtual equals : (Ljava/lang/Object;)Z
    //   765: ifeq -> 773
    //   768: ldc_w '1=1'
    //   771: astore #8
    //   773: ldc ''
    //   775: astore #9
    //   777: ldc ''
    //   779: aload_2
    //   780: bipush #8
    //   782: aaload
    //   783: invokevirtual toString : ()Ljava/lang/String;
    //   786: invokevirtual equals : (Ljava/lang/Object;)Z
    //   789: ifeq -> 800
    //   792: ldc_w ' order by task.parentIdString'
    //   795: astore #9
    //   797: goto -> 841
    //   800: new java/lang/StringBuilder
    //   803: dup
    //   804: ldc_w ' order by task.'
    //   807: invokespecial <init> : (Ljava/lang/String;)V
    //   810: aload_2
    //   811: bipush #8
    //   813: aaload
    //   814: invokevirtual toString : ()Ljava/lang/String;
    //   817: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   820: ldc_w ' '
    //   823: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   826: aload_2
    //   827: bipush #9
    //   829: aaload
    //   830: invokevirtual toString : ()Ljava/lang/String;
    //   833: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   836: invokevirtual toString : ()Ljava/lang/String;
    //   839: astore #9
    //   841: aload_0
    //   842: getfield session : Lnet/sf/hibernate/Session;
    //   845: new java/lang/StringBuilder
    //   848: dup
    //   849: ldc_w 'select distinct task from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in ('
    //   852: invokespecial <init> : (Ljava/lang/String;)V
    //   855: aload #7
    //   857: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   860: ldc_w ')and task.taskFinishRate<100 and task.taskStatus=4 and ('
    //   863: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   866: aload #8
    //   868: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   871: ldc_w ') and task.taskDomainId = '
    //   874: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   877: aload #5
    //   879: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   882: aload #9
    //   884: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   887: invokevirtual toString : ()Ljava/lang/String;
    //   890: invokeinterface createQuery : (Ljava/lang/String;)Lnet/sf/hibernate/Query;
    //   895: astore #10
    //   897: aload #10
    //   899: aload_3
    //   900: invokevirtual intValue : ()I
    //   903: iconst_1
    //   904: isub
    //   905: aload #4
    //   907: invokevirtual intValue : ()I
    //   910: imul
    //   911: invokeinterface setFirstResult : (I)Lnet/sf/hibernate/Query;
    //   916: pop
    //   917: aload #10
    //   919: aload #4
    //   921: invokevirtual intValue : ()I
    //   924: invokeinterface setMaxResults : (I)Lnet/sf/hibernate/Query;
    //   929: pop
    //   930: aload #10
    //   932: invokeinterface list : ()Ljava/util/List;
    //   937: invokeinterface iterator : ()Ljava/util/Iterator;
    //   942: astore #11
    //   944: aconst_null
    //   945: astore #12
    //   947: goto -> 1223
    //   950: aload #11
    //   952: invokeinterface next : ()Ljava/lang/Object;
    //   957: checkcast com/js/oa/scheme/taskcenter/po/TaskPO
    //   960: astore #12
    //   962: invokestatic getInstance : ()Lcom/js/util/util/TransformObject;
    //   965: aload #12
    //   967: ldc_w com/js/oa/scheme/taskcenter/vo/TaskVO
    //   970: invokevirtual transformObject : (Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   973: checkcast com/js/oa/scheme/taskcenter/vo/TaskVO
    //   976: astore #13
    //   978: aload #13
    //   980: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   983: invokevirtual setCanCancel : (Ljava/lang/Boolean;)V
    //   986: aload #13
    //   988: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   991: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   994: aload #13
    //   996: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   999: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1002: aload #13
    //   1004: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1007: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   1010: aload #13
    //   1012: invokevirtual getTaskStatus : ()Ljava/lang/Integer;
    //   1015: new java/lang/Integer
    //   1018: dup
    //   1019: iconst_0
    //   1020: invokespecial <init> : (I)V
    //   1023: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1026: ifeq -> 1057
    //   1029: aload #13
    //   1031: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1034: invokevirtual setMaintenance : (Ljava/lang/Boolean;)V
    //   1037: aload #12
    //   1039: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1042: aload_1
    //   1043: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1046: ifeq -> 1057
    //   1049: aload #13
    //   1051: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1054: invokevirtual setCanDelete : (Ljava/lang/Boolean;)V
    //   1057: aload #12
    //   1059: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1062: aload_1
    //   1063: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1066: ifne -> 1123
    //   1069: aload #12
    //   1071: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1074: ifnull -> 1111
    //   1077: aload #12
    //   1079: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1082: new java/lang/StringBuilder
    //   1085: dup
    //   1086: ldc_w '$'
    //   1089: invokespecial <init> : (Ljava/lang/String;)V
    //   1092: aload_1
    //   1093: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1096: ldc_w '$'
    //   1099: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1102: invokevirtual toString : ()Ljava/lang/String;
    //   1105: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1108: ifge -> 1123
    //   1111: aload #12
    //   1113: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1116: aload_1
    //   1117: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1120: ifeq -> 1131
    //   1123: aload #13
    //   1125: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1128: invokevirtual setCanReport : (Ljava/lang/Boolean;)V
    //   1131: aload #13
    //   1133: getstatic java/lang/Boolean.FALSE : Ljava/lang/Boolean;
    //   1136: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1139: aload #12
    //   1141: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1144: ifnull -> 1181
    //   1147: aload #12
    //   1149: invokevirtual getTaskJoinedEmp : ()Ljava/lang/String;
    //   1152: new java/lang/StringBuilder
    //   1155: dup
    //   1156: ldc_w '$'
    //   1159: invokespecial <init> : (Ljava/lang/String;)V
    //   1162: aload_1
    //   1163: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1166: ldc_w '$'
    //   1169: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1172: invokevirtual toString : ()Ljava/lang/String;
    //   1175: invokevirtual indexOf : (Ljava/lang/String;)I
    //   1178: ifge -> 1205
    //   1181: aload #12
    //   1183: invokevirtual getCreatedEmp : ()Ljava/lang/Long;
    //   1186: aload_1
    //   1187: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1190: ifne -> 1205
    //   1193: aload #12
    //   1195: invokevirtual getTaskPrincipal : ()Ljava/lang/Long;
    //   1198: aload_1
    //   1199: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1202: ifeq -> 1213
    //   1205: aload #13
    //   1207: getstatic java/lang/Boolean.TRUE : Ljava/lang/Boolean;
    //   1210: invokevirtual setCanNew : (Ljava/lang/Boolean;)V
    //   1213: aload #6
    //   1215: aload #13
    //   1217: invokeinterface add : (Ljava/lang/Object;)Z
    //   1222: pop
    //   1223: aload #11
    //   1225: ifnull -> 1298
    //   1228: aload #11
    //   1230: invokeinterface hasNext : ()Z
    //   1235: ifne -> 950
    //   1238: goto -> 1298
    //   1241: astore #7
    //   1243: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   1246: new java/lang/StringBuilder
    //   1249: dup
    //   1250: ldc_w 'Select Settle Check Task Exception: '
    //   1253: invokespecial <init> : (Ljava/lang/String;)V
    //   1256: aload #7
    //   1258: invokevirtual getMessage : ()Ljava/lang/String;
    //   1261: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1264: invokevirtual toString : ()Ljava/lang/String;
    //   1267: invokevirtual println : (Ljava/lang/String;)V
    //   1270: aload #7
    //   1272: athrow
    //   1273: astore #14
    //   1275: aload_0
    //   1276: getfield session : Lnet/sf/hibernate/Session;
    //   1279: invokeinterface close : ()Ljava/sql/Connection;
    //   1284: pop
    //   1285: aload_0
    //   1286: aconst_null
    //   1287: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1290: aload_0
    //   1291: aconst_null
    //   1292: putfield session : Lnet/sf/hibernate/Session;
    //   1295: aload #14
    //   1297: athrow
    //   1298: aload_0
    //   1299: getfield session : Lnet/sf/hibernate/Session;
    //   1302: invokeinterface close : ()Ljava/sql/Connection;
    //   1307: pop
    //   1308: aload_0
    //   1309: aconst_null
    //   1310: putfield transaction : Lnet/sf/hibernate/Transaction;
    //   1313: aload_0
    //   1314: aconst_null
    //   1315: putfield session : Lnet/sf/hibernate/Session;
    //   1318: aload #6
    //   1320: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #8731	-> 0
    //   #8734	-> 9
    //   #8735	-> 13
    //   #8736	-> 23
    //   #8737	-> 27
    //   #8738	-> 33
    //   #8735	-> 38
    //   #8740	-> 43
    //   #8741	-> 47
    //   #8742	-> 61
    //   #8743	-> 71
    //   #8744	-> 81
    //   #8745	-> 90
    //   #8743	-> 96
    //   #8747	-> 104
    //   #8748	-> 122
    //   #8747	-> 137
    //   #8751	-> 142
    //   #8752	-> 156
    //   #8753	-> 166
    //   #8754	-> 176
    //   #8753	-> 185
    //   #8757	-> 193
    //   #8758	-> 211
    //   #8757	-> 220
    //   #8762	-> 225
    //   #8763	-> 239
    //   #8764	-> 249
    //   #8765	-> 268
    //   #8764	-> 273
    //   #8767	-> 281
    //   #8768	-> 299
    //   #8769	-> 308
    //   #8767	-> 313
    //   #8772	-> 318
    //   #8773	-> 332
    //   #8774	-> 342
    //   #8777	-> 369
    //   #8778	-> 387
    //   #8777	-> 396
    //   #8781	-> 401
    //   #8782	-> 415
    //   #8783	-> 425
    //   #8786	-> 452
    //   #8787	-> 470
    //   #8786	-> 479
    //   #8791	-> 484
    //   #8792	-> 498
    //   #8808	-> 513
    //   #8809	-> 523
    //   #8811	-> 535
    //   #8812	-> 545
    //   #8813	-> 561
    //   #8811	-> 576
    //   #8818	-> 584
    //   #8819	-> 594
    //   #8820	-> 604
    //   #8821	-> 610
    //   #8818	-> 626
    //   #8817	-> 629
    //   #8827	-> 634
    //   #8829	-> 646
    //   #8830	-> 664
    //   #8831	-> 680
    //   #8829	-> 695
    //   #8835	-> 703
    //   #8836	-> 715
    //   #8837	-> 721
    //   #8838	-> 731
    //   #8839	-> 737
    //   #8835	-> 753
    //   #8847	-> 758
    //   #8848	-> 768
    //   #8851	-> 773
    //   #8852	-> 777
    //   #8853	-> 792
    //   #8855	-> 800
    //   #8856	-> 820
    //   #8857	-> 826
    //   #8855	-> 836
    //   #8859	-> 841
    //   #8861	-> 855
    //   #8862	-> 860
    //   #8863	-> 866
    //   #8864	-> 871
    //   #8865	-> 877
    //   #8859	-> 890
    //   #8866	-> 897
    //   #8867	-> 905
    //   #8866	-> 911
    //   #8868	-> 917
    //   #8869	-> 930
    //   #8870	-> 944
    //   #8871	-> 947
    //   #8872	-> 950
    //   #8873	-> 962
    //   #8874	-> 965
    //   #8873	-> 973
    //   #8875	-> 978
    //   #8876	-> 986
    //   #8877	-> 994
    //   #8878	-> 1002
    //   #8879	-> 1010
    //   #8880	-> 1029
    //   #8881	-> 1037
    //   #8882	-> 1049
    //   #8885	-> 1057
    //   #8886	-> 1069
    //   #8887	-> 1077
    //   #8889	-> 1111
    //   #8890	-> 1123
    //   #8892	-> 1131
    //   #8893	-> 1139
    //   #8894	-> 1147
    //   #8896	-> 1181
    //   #8897	-> 1193
    //   #8898	-> 1205
    //   #8900	-> 1213
    //   #8871	-> 1223
    //   #8903	-> 1241
    //   #8904	-> 1243
    //   #8905	-> 1256
    //   #8904	-> 1267
    //   #8906	-> 1270
    //   #8907	-> 1273
    //   #8908	-> 1275
    //   #8909	-> 1285
    //   #8910	-> 1290
    //   #8912	-> 1295
    //   #8908	-> 1298
    //   #8909	-> 1308
    //   #8910	-> 1313
    //   #8913	-> 1318
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	1321	0	this	Lcom/js/oa/scheme/taskcenter/bean/TaskEJBBean;
    //   0	1321	1	userId	Ljava/lang/Long;
    //   0	1321	2	parameters	[Ljava/lang/String;
    //   0	1321	3	currentPage	Ljava/lang/Integer;
    //   0	1321	4	volume	Ljava/lang/Integer;
    //   0	1321	5	domainId	Ljava/lang/Long;
    //   9	1312	6	result	Ljava/util/List;
    //   43	1198	7	tempHql	Ljava/lang/String;
    //   47	1194	8	strsql	Ljava/lang/String;
    //   777	464	9	strsql1	Ljava/lang/String;
    //   897	344	10	query	Lnet/sf/hibernate/Query;
    //   944	297	11	iterator	Ljava/util/Iterator;
    //   947	294	12	taskPO	Lcom/js/oa/scheme/taskcenter/po/TaskPO;
    //   978	245	13	task	Lcom/js/oa/scheme/taskcenter/vo/TaskVO;
    //   1243	30	7	ex	Lnet/sf/hibernate/HibernateException;
    // Exception table:
    //   from	to	target	type
    //   9	1238	1241	net/sf/hibernate/HibernateException
    //   9	1273	1273	finally
  }
  
  public Integer getCancelSettleRC(Long userId, String[] parameters, Long domainId) throws HibernateException {
    Integer result = new Integer(0);
    try {
      begin();
      String tempHql = "select distinct task.taskId from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskStatus=4 and task.taskFinishRate<100 and task.taskParentId =0 and (task.createdEmp like '%" + 
        userId + "%')" + (
        (domainId != null) ? (
        " and task.taskDomainId = " + 
        domainId) : 
        "");
      String strsql = "";
      if (!"".equals(parameters[0].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskTitle like '%" + 
            parameters[0].toString() + 
            "%'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskTitle like '%" + 
            parameters[0].toString() + "%'";
        }  
      if (!"".equals(parameters[1].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskFinishRate=" + 
            parameters[1].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskFinishRate=" + 
            parameters[1].toString();
        }  
      if (!"".equals(parameters[2].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskType='" + parameters[2].toString() + 
            "'";
        } else {
          strsql = String.valueOf(strsql) + " and task.taskType='" + 
            parameters[2].toString() + 
            "'";
        }  
      if (!"".equals(parameters[3].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskPriority=" + parameters[3].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskPriority=" + 
            parameters[3].toString();
        }  
      if (!"".equals(parameters[4].toString()))
        if ("".equals(strsql)) {
          strsql = "task.taskStatus=" + parameters[4].toString();
        } else {
          strsql = String.valueOf(strsql) + " and task.taskStatus=" + 
            parameters[4].toString();
        }  
      if (!"".equals(parameters[5].toString()) && 
        "1".equals(parameters[5].toString()))
        if ("".equals(strsql)) {
          if (databaseType.indexOf("mysql") >= 0) {
            strsql = "task.taskEndTime between '" + 
              parameters[6].toString() + "' and '" + 
              parameters[7].toString() + "'";
          } else {
            strsql = 
              "task.taskEndTime between JSDB.FN_STRTODATE('" + 
              parameters[6].toString() + 
              "','S')  and JSDB.FN_STRTODATE('" + 
              parameters[7].toString() + "','S')";
          } 
        } else if (databaseType.indexOf("mysql") >= 0) {
          strsql = String.valueOf(strsql) + " and task.taskEndTime between '" + 
            parameters[6].toString() + "'  and '" + 
            parameters[7].toString() + "'";
        } else {
          strsql = String.valueOf(strsql) + 
            " and task.taskEndTime between JSDB.FN_STRTODATE('" + 
            parameters[6].toString() + 
            "','S')  and JSDB.FN_STRTODATE('" + 
            parameters[7].toString() + "','S')";
        }  
      if ("".equals(strsql))
        strsql = "1=1"; 
      Query query = this.session.createQuery("select distinct count(task) from com.js.oa.scheme.taskcenter.po.TaskPO task where task.taskBaseId in (" + 
          
          tempHql + 
          ") and (task.taskFinishRate<100) and task.taskStatus=4 and (" + 
          strsql + ")" + (
          (domainId != null) ? (
          " and task.taskDomainId = " + 
          domainId) : 
          ""));
      result = Integer.valueOf(query.list().get(0).toString());
    } catch (HibernateException ex) {
      System.out.println("Select Settle Check Task Exception: " + 
          ex.getMessage());
      throw ex;
    } finally {
      this.session.close();
      this.transaction = null;
      this.session = null;
    } 
    return result;
  }
  
  public Boolean hasRight(Long userId, String rightType, String rightName) throws HibernateException {
    Boolean result = new Boolean(false);
    begin();
    try {
      Query query = this.session.createQuery("select count(aaa.rightScopeId) from com.js.system.vo.rolemanager.RightScopeVO aaa join aaa.employee bbb join aaa.right ccc where bbb.empId = " + 
          userId + 
          " and ccc.rightType = '" + 
          rightType + 
          "' and ccc.rightName = '" + 
          rightName + "'");
      int count = ((Integer)query.iterate().next()).intValue();
      if (count > 0)
        result = Boolean.TRUE; 
    } catch (HibernateException e) {
      System.out.println(e.getMessage());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return result;
  }
  
  public List getSonsById(String orgId) throws Exception {
    List list = null;
    begin();
    try {
      Query query = this.session.createQuery("select aaa.orgId from com.js.system.vo.organizationmanager.OrganizationVO aaa where aaa.orgIdString like '%$" + 
          orgId + "$%' and aaa.orgId <> '" + 
          orgId + "'");
      list = query.list();
    } catch (Exception e) {
      throw e;
    } finally {
      this.session.close();
    } 
    return list;
  }
}
