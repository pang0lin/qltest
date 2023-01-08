package com.js.oa.scheme.worklog.bean;

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
import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface WorkLogEJB extends EJBObject {
  boolean addWorkProjectClass(WorkProjectClassPO paramWorkProjectClassPO) throws HibernateException, RemoteException;
  
  List selectAllWorkProjectClass(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Boolean deleteWorkProjectClass(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean deleteBatchWorkProjectClass(String paramString) throws HibernateException, RemoteException;
  
  Boolean modifyWorkProjectClass(WorkProjectClassVO paramWorkProjectClassVO) throws HibernateException, RemoteException;
  
  boolean addWorkProject(WorkProjectPO paramWorkProjectPO, Long paramLong) throws Exception, RemoteException;
  
  Boolean deleteWorkProject(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean deleteBatchWorkProject(String paramString) throws HibernateException, RemoteException;
  
  Boolean modifyWorkProject(WorkProjectVO paramWorkProjectVO) throws HibernateException, RemoteException;
  
  List selectAllWorkProject(Long paramLong1, Long paramLong2, Long paramLong3, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  WorkProjectVO selectSingleWorkProject(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List searchWorkLog(Long paramLong1, String paramString1, String paramString2, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectWorkProjectForProjectCount(Long paramLong1, Long paramLong2, Long paramLong3) throws RemoteException;
  
  String selectProjectDate(String paramString1, String paramString2, Long paramLong) throws HibernateException, RemoteException;
  
  WorkLogDO getWorkLogDO(Long paramLong) throws HibernateException, RemoteException;
  
  String workProjectClassNameIsExist(String paramString, Long paramLong) throws RemoteException;
  
  Boolean hasRight(Long paramLong, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List getDDUserLogListByUserName(String paramString1, String paramString2, String paramString3, String paramString4) throws HibernateException, RemoteException;
  
  Integer addWorkTask(WorkProjectTaskPO paramWorkProjectTaskPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List listWorkTask() throws HibernateException, RemoteException;
  
  Integer updateProjectTask(WorkProjectTaskPO paramWorkProjectTaskPO, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List selectProjectTaskById(String paramString) throws HibernateException, RemoteException;
  
  List selectProjectTaskByCode(String paramString) throws HibernateException, RemoteException;
  
  List selectTaskByProjectId(String paramString) throws Exception, RemoteException;
  
  List selectProjectById(Long paramLong) throws Exception, RemoteException;
  
  boolean deleteProjectWorkTask(WorkProjectTaskPO paramWorkProjectTaskPO) throws HibernateException, RemoteException;
  
  List selectFatherTask(String paramString) throws HibernateException, RemoteException;
  
  List selectFatherTask(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List selectTaskByName(String paramString) throws HibernateException, RemoteException;
  
  List getDDUserLogList(String paramString1, String paramString2, String paramString3, String paramString4) throws HibernateException, RemoteException;
  
  Boolean hasRight(Long paramLong, String paramString) throws HibernateException, RemoteException;
  
  boolean addProjectStep(ProjectStepPO paramProjectStepPO, Long paramLong) throws HibernateException, RemoteException;
  
  ProjectStepVO selectSingleProjectStep(Long paramLong) throws HibernateException, RemoteException;
  
  List selectAllProjectStep(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  boolean deleteProjectStep(Long paramLong) throws HibernateException, RemoteException;
  
  boolean deleteBatchProjectStep(String paramString) throws HibernateException, RemoteException;
  
  boolean deleteAllProjectStep(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modifyProjectStep(ProjectStepVO paramProjectStepVO) throws HibernateException, RemoteException;
  
  String projectStepNameIsExist(Long paramLong1, String paramString, Long paramLong2) throws RemoteException;
  
  Boolean addWorkLog(WorkLogVO paramWorkLogVO) throws HibernateException, RemoteException;
  
  Boolean addWorkLog(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3) throws HibernateException, RemoteException;
  
  Boolean addMultiWorkLog(List paramList) throws HibernateException, RemoteException;
  
  Boolean deleteWorkLog(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean deleteBatchWorkLog(String paramString) throws HibernateException, RemoteException;
  
  List selectProjectForWorkLog(Long paramLong1, String paramString, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectProjectStepForWorkLog(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectSelfWorkLog(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List countProjectSimpleInformation(Long paramLong, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List countProjectDetailInformation(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Float getCountDetailTotalTime(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  String getProjectDetailMaxTime(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  String getProjectDetailMinTime(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  List selectWorkLogByCondition(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  List searchWorkLog(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  List searchWorkLog(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, Long paramLong3) throws HibernateException, RemoteException;
  
  WorkProjectClassVO selectWorkProjectClass(Long paramLong) throws HibernateException, RemoteException;
  
  WorkLogVO selectSingleWorkLog(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modifyWorkLog(WorkLogVO paramWorkLogVO) throws HibernateException, RemoteException;
  
  String getProjectNameAndClassName(String paramString, Long paramLong) throws HibernateException, RemoteException;
  
  List selectAllWorkProjectClass(Long paramLong1, String paramString, Long paramLong2) throws RemoteException;
  
  String workProjectIsExist(Long paramLong1, String paramString, Long paramLong2) throws RemoteException;
  
  Boolean addWorkLog(WorkLogPO paramWorkLogPO) throws HibernateException, RemoteException;
  
  WorkLogPO selectSingleWorkLogPO(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modifyWorkLog(WorkLogPO paramWorkLogPO) throws HibernateException, RemoteException;
  
  List getUserLogList(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getUserLogListByUserName(String paramString1, String paramString2, String paramString3) throws Exception, RemoteException;
  
  List getDownEmployeeList(String paramString) throws Exception, RemoteException;
  
  String getWorkLogKeepDay(String paramString) throws Exception, RemoteException;
  
  void begin() throws HibernateException, RemoteException;
  
  Long getTableId() throws HibernateException, RemoteException;
  
  Boolean deleteWorkLogComment(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean addWorkLogComment(WorkLogCommentPO paramWorkLogCommentPO) throws HibernateException, RemoteException;
}
