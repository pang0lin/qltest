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
import java.util.List;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface WorkLogEJBLocal extends EJBLocalObject {
  boolean addWorkProjectClass(WorkProjectClassPO paramWorkProjectClassPO) throws HibernateException;
  
  List selectAllWorkProjectClass(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Boolean deleteWorkProjectClass(Long paramLong) throws HibernateException;
  
  Boolean deleteBatchWorkProjectClass(String paramString) throws HibernateException;
  
  Boolean modifyWorkProjectClass(WorkProjectClassVO paramWorkProjectClassVO) throws HibernateException;
  
  boolean addWorkProject(WorkProjectPO paramWorkProjectPO, Long paramLong) throws Exception;
  
  Boolean deleteWorkProject(Long paramLong) throws HibernateException;
  
  Boolean deleteBatchWorkProject(String paramString) throws HibernateException;
  
  Boolean modifyWorkProject(WorkProjectVO paramWorkProjectVO) throws HibernateException;
  
  List selectAllWorkProject(Long paramLong1, Long paramLong2, Long paramLong3, String paramString1, String paramString2) throws HibernateException;
  
  WorkProjectVO selectSingleWorkProject(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List searchWorkLog(Long paramLong1, String paramString1, String paramString2, Long paramLong2) throws HibernateException;
  
  List selectWorkProjectForProjectCount(Long paramLong1, Long paramLong2, Long paramLong3);
  
  String selectProjectDate(String paramString1, String paramString2, Long paramLong) throws HibernateException;
  
  WorkLogDO getWorkLogDO(Long paramLong) throws HibernateException;
  
  String workProjectClassNameIsExist(String paramString, Long paramLong);
  
  Boolean hasRight(Long paramLong, String paramString1, String paramString2) throws HibernateException;
  
  List getDDUserLogListByUserName(String paramString1, String paramString2, String paramString3, String paramString4) throws HibernateException;
  
  Integer addWorkTask(WorkProjectTaskPO paramWorkProjectTaskPO, String paramString1, String paramString2) throws HibernateException;
  
  List listWorkTask() throws HibernateException;
  
  Integer updateProjectTask(WorkProjectTaskPO paramWorkProjectTaskPO, String paramString1, String paramString2) throws HibernateException;
  
  List selectProjectTaskById(String paramString) throws HibernateException;
  
  List selectProjectTaskByCode(String paramString) throws HibernateException;
  
  List selectTaskByProjectId(String paramString) throws Exception;
  
  List selectProjectById(Long paramLong) throws Exception;
  
  boolean deleteProjectWorkTask(WorkProjectTaskPO paramWorkProjectTaskPO) throws HibernateException;
  
  List selectFatherTask(String paramString) throws HibernateException;
  
  List selectFatherTask(String paramString1, String paramString2) throws HibernateException;
  
  List selectTaskByName(String paramString) throws HibernateException;
  
  List getDDUserLogList(String paramString1, String paramString2, String paramString3, String paramString4) throws HibernateException;
  
  Boolean hasRight(Long paramLong, String paramString) throws HibernateException;
  
  boolean addProjectStep(ProjectStepPO paramProjectStepPO, Long paramLong) throws HibernateException;
  
  ProjectStepVO selectSingleProjectStep(Long paramLong) throws HibernateException;
  
  List selectAllProjectStep(Long paramLong1, Long paramLong2) throws HibernateException;
  
  boolean deleteProjectStep(Long paramLong) throws HibernateException;
  
  boolean deleteBatchProjectStep(String paramString) throws HibernateException;
  
  boolean deleteAllProjectStep(Long paramLong) throws HibernateException;
  
  boolean modifyProjectStep(ProjectStepVO paramProjectStepVO) throws HibernateException;
  
  String projectStepNameIsExist(Long paramLong1, String paramString, Long paramLong2);
  
  Boolean addWorkLog(WorkLogVO paramWorkLogVO) throws HibernateException;
  
  Boolean addWorkLog(String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Long paramLong1, Long paramLong2, String paramString1, String paramString2, Long paramLong3) throws HibernateException;
  
  Boolean addMultiWorkLog(List paramList) throws HibernateException;
  
  Boolean deleteWorkLog(Long paramLong) throws HibernateException;
  
  Boolean deleteBatchWorkLog(String paramString) throws HibernateException;
  
  List selectProjectForWorkLog(Long paramLong1, String paramString, Long paramLong2) throws HibernateException;
  
  List selectProjectStepForWorkLog(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectSelfWorkLog(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List countProjectSimpleInformation(Long paramLong, String paramString1, String paramString2) throws HibernateException;
  
  List countProjectDetailInformation(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Float getCountDetailTotalTime(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  String getProjectDetailMaxTime(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  String getProjectDetailMinTime(String paramString1, String paramString2, String paramString3, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  List selectWorkLogByCondition(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  List searchWorkLog(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  List searchWorkLog(Long paramLong1, Long paramLong2, String paramString1, String paramString2, String paramString3, Long paramLong3) throws HibernateException;
  
  WorkProjectClassVO selectWorkProjectClass(Long paramLong) throws HibernateException;
  
  WorkLogVO selectSingleWorkLog(Long paramLong) throws HibernateException;
  
  Boolean modifyWorkLog(WorkLogVO paramWorkLogVO) throws HibernateException;
  
  String getProjectNameAndClassName(String paramString, Long paramLong) throws HibernateException;
  
  List selectAllWorkProjectClass(Long paramLong1, String paramString, Long paramLong2);
  
  String workProjectIsExist(Long paramLong1, String paramString, Long paramLong2);
  
  Boolean addWorkLog(WorkLogPO paramWorkLogPO) throws HibernateException;
  
  WorkLogPO selectSingleWorkLogPO(Long paramLong) throws HibernateException;
  
  Boolean modifyWorkLog(WorkLogPO paramWorkLogPO) throws HibernateException;
  
  List getUserLogList(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getUserLogListByUserName(String paramString1, String paramString2, String paramString3) throws Exception;
  
  List getDownEmployeeList(String paramString) throws Exception;
  
  String getWorkLogKeepDay(String paramString) throws Exception;
  
  void begin() throws HibernateException;
  
  Long getTableId() throws HibernateException;
  
  Boolean deleteWorkLogComment(Long paramLong) throws HibernateException;
  
  Boolean addWorkLogComment(WorkLogCommentPO paramWorkLogCommentPO) throws HibernateException;
}
