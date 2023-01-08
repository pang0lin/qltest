package com.js.oa.scheme.taskcenter.bean;

import com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO;
import com.js.oa.scheme.taskcenter.po.TaskReportPO;
import com.js.oa.scheme.taskcenter.vo.RightScope;
import com.js.oa.scheme.taskcenter.vo.TaskClassVO;
import com.js.oa.scheme.taskcenter.vo.TaskReportVO;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface TaskEJBLocal extends EJBLocalObject {
  Boolean addTaskClass(TaskClassVO paramTaskClassVO) throws HibernateException;
  
  Boolean deleteTaskClass(Long paramLong) throws HibernateException;
  
  Boolean modifyTaskClass(TaskClassVO paramTaskClassVO) throws HibernateException;
  
  Boolean deleteAllTaskClass(Long paramLong) throws HibernateException;
  
  Boolean deleteBatchTaskClass(String paramString) throws HibernateException;
  
  TaskClassVO selectSingleTaskClass(Long paramLong) throws HibernateException;
  
  List selectAllTask(Long paramLong1, Long paramLong2, String paramString) throws HibernateException;
  
  List selectAllTaskClass(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  List selectAllTaskClass(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Long paramLong3) throws HibernateException;
  
  Integer getTaskClassRecordCount(String paramString) throws HibernateException;
  
  Integer getTaskClassRecordCount(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Boolean addTask(TaskVO paramTaskVO) throws Exception;
  
  Boolean modifyTask(TaskVO paramTaskVO) throws Exception;
  
  Boolean deleteTask(Long paramLong) throws HibernateException;
  
  Boolean cancelTask(Long paramLong1, Long paramLong2, String paramString) throws HibernateException;
  
  Boolean taskPass(Long paramLong1, Long paramLong2, Integer paramInteger, Long paramLong3) throws HibernateException;
  
  List selectPrincipalTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  Integer getPrincipalTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectCompleteTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  Integer getCompleteTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectJoinTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  Integer getJoinTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectAuditingTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  Integer getAuditingTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List searchTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Long paramLong2) throws HibernateException;
  
  Map selectSingleTask(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectSettleTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  List selectSettleCheckTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  List selectSettleFinishTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  Integer getSSTRecordCount(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException;
  
  Map selectTaskReport(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  Boolean hasRight(Long paramLong, String paramString) throws HibernateException;
  
  Boolean hasRight(Long paramLong, String paramString1, String paramString2) throws HibernateException;
  
  Integer getSearchTaskRecordCount(Long paramLong1, String[] paramArrayOfString, Integer paramInteger, Long paramLong2) throws HibernateException;
  
  Integer getSettleTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  Integer getSettleCheckTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  Integer getSettleFinishRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List searchSettleTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  Integer getSSCTRecordCount(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException;
  
  List searchSettleCheckTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  Integer getSSFRecordCount(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException;
  
  List searchSettleFinishTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  Boolean addTaskReport(TaskReportVO paramTaskReportVO, Integer paramInteger, String paramString, TaskReportPO paramTaskReportPO) throws HibernateException;
  
  Boolean addTaskReport(Long paramLong1, Long paramLong2, String paramString, Long paramLong3, TaskReportPO paramTaskReportPO) throws HibernateException;
  
  Boolean isPrincipal(Long paramLong1, Long paramLong2) throws HibernateException;
  
  TaskVO selectSettleSingleTask(Long paramLong1, Long paramLong2, Long paramLong3) throws Exception;
  
  String taskClassNameIsExist(String paramString, Long paramLong1, Long paramLong2) throws Exception;
  
  Boolean checkTask(Long paramLong1, String paramString1, Date paramDate, String paramString2, Long paramLong2, Integer paramInteger, Long paramLong3);
  
  RightScope getRightScope(Long paramLong1, Long paramLong2, String paramString1, String paramString2);
  
  Boolean addPeriodicityTask(TaskPeriodicityPO paramTaskPeriodicityPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  TaskPeriodicityPO selectPeriodicityTaskView(Long paramLong1, Long paramLong2) throws HibernateException;
  
  Boolean modifyPeriodicityTask(TaskPeriodicityPO paramTaskPeriodicityPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception;
  
  Boolean deletePeriodicityTask(Long paramLong) throws HibernateException;
  
  Integer getTaskPrincipalRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  Integer getTasktaskJoinedEmpRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  String selectUserIds(String paramString) throws HibernateException;
  
  List selectAllTask(Long paramLong1, Long paramLong2) throws HibernateException;
  
  boolean sendremind(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  boolean deleteremind(String paramString1, String paramString2, String paramString3) throws HibernateException;
  
  List selectSettleCheckTaskall(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  String checkhastype(String paramString) throws HibernateException;
  
  List getaccessory(String paramString) throws HibernateException;
  
  String getchildtasksta(String paramString) throws HibernateException;
  
  boolean saveHistory(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException;
  
  List getAccessory(String paramString1, String paramString2) throws Exception;
  
  List getHistoryInfo(String paramString) throws Exception;
  
  List selectSingleTaskHis(String paramString) throws HibernateException;
  
  Integer getCancelTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectCancelTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  Integer getCancelSettleRecordCount(Long paramLong1, Long paramLong2) throws HibernateException;
  
  List selectsettleCancelTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException;
  
  Integer getCancelSettleRC(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException;
  
  List searchCancelSettleTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException;
  
  boolean deleteremindall(String paramString1, String paramString2) throws HibernateException;
  
  List getSonsById(String paramString) throws Exception;
}
