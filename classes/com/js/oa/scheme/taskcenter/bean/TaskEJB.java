package com.js.oa.scheme.taskcenter.bean;

import com.js.oa.scheme.taskcenter.po.TaskPeriodicityPO;
import com.js.oa.scheme.taskcenter.po.TaskReportPO;
import com.js.oa.scheme.taskcenter.vo.RightScope;
import com.js.oa.scheme.taskcenter.vo.TaskClassVO;
import com.js.oa.scheme.taskcenter.vo.TaskReportVO;
import com.js.oa.scheme.taskcenter.vo.TaskVO;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface TaskEJB extends EJBObject {
  Boolean deleteTaskClass(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean modifyTaskClass(TaskClassVO paramTaskClassVO) throws HibernateException, RemoteException;
  
  Boolean deleteAllTaskClass(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean deleteBatchTaskClass(String paramString) throws HibernateException, RemoteException;
  
  TaskClassVO selectSingleTaskClass(Long paramLong) throws HibernateException, RemoteException;
  
  List selectAllTask(Long paramLong1, Long paramLong2, String paramString) throws HibernateException, RemoteException;
  
  List selectAllTaskClass(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  List selectAllTaskClass(Long paramLong1, Long paramLong2, Integer paramInteger1, Integer paramInteger2, Long paramLong3) throws HibernateException, RemoteException;
  
  Integer getTaskClassRecordCount(String paramString) throws HibernateException, RemoteException;
  
  Integer getTaskClassRecordCount(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Boolean addTask(TaskVO paramTaskVO) throws Exception, RemoteException;
  
  Boolean modifyTask(TaskVO paramTaskVO, String paramString1, String paramString2) throws Exception, RemoteException;
  
  Boolean deleteTask(Long paramLong) throws HibernateException, RemoteException;
  
  Boolean cancelTask(Long paramLong1, Long paramLong2, String paramString) throws HibernateException, RemoteException;
  
  Boolean taskPass(Long paramLong1, Long paramLong2, Integer paramInteger, Long paramLong3) throws HibernateException, RemoteException;
  
  List selectPrincipalTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer getPrincipalTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectCompleteTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer getCompleteTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectJoinTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getJoinTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectAuditingTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getAuditingTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List searchTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Long paramLong2) throws HibernateException, RemoteException;
  
  Map selectSingleTask(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectSettleTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List selectSettleCheckTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List selectSettleFinishTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer getSSTRecordCount(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException, RemoteException;
  
  Map selectTaskReport(Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  Boolean hasRight(Long paramLong, String paramString) throws HibernateException, RemoteException;
  
  Boolean hasRight(Long paramLong, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer getSearchTaskRecordCount(Long paramLong1, String[] paramArrayOfString, Integer paramInteger, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getSettleTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getSettleCheckTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getSettleFinishRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List searchSettleTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getSSCTRecordCount(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException, RemoteException;
  
  List searchSettleCheckTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getSSFRecordCount(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException, RemoteException;
  
  List searchSettleFinishTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  Boolean addTaskReport(TaskReportVO paramTaskReportVO, Integer paramInteger, String paramString, TaskReportPO paramTaskReportPO) throws HibernateException, RemoteException;
  
  Boolean addTaskReport(Long paramLong1, Long paramLong2, String paramString, Long paramLong3, TaskReportPO paramTaskReportPO) throws HibernateException, RemoteException;
  
  Boolean isPrincipal(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  TaskVO selectSettleSingleTask(Long paramLong1, Long paramLong2, Long paramLong3) throws Exception, RemoteException;
  
  String taskClassNameIsExist(String paramString, Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  Boolean checkTask(Long paramLong1, String paramString1, Date paramDate, String paramString2, Long paramLong2, Integer paramInteger, Long paramLong3) throws RemoteException;
  
  RightScope getRightScope(Long paramLong1, Long paramLong2, String paramString1, String paramString2) throws RemoteException;
  
  Boolean addTaskClass(TaskClassVO paramTaskClassVO) throws HibernateException, RemoteException;
  
  Boolean addPeriodicityTask(TaskPeriodicityPO paramTaskPeriodicityPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  TaskPeriodicityPO selectPeriodicityTaskView(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Boolean modifyPeriodicityTask(TaskPeriodicityPO paramTaskPeriodicityPO, String[] paramArrayOfString1, String[] paramArrayOfString2) throws Exception, RemoteException;
  
  Boolean deletePeriodicityTask(Long paramLong) throws HibernateException, RemoteException;
  
  Integer getTaskPrincipalRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Integer getTasktaskJoinedEmpRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  String selectUserIds(String paramString) throws HibernateException, RemoteException;
  
  List selectAllTask(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  boolean sendremind(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  boolean deleteremind(String paramString1, String paramString2, String paramString3) throws HibernateException, RemoteException;
  
  List selectSettleCheckTaskall(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  String checkhastype(String paramString) throws HibernateException, RemoteException;
  
  List getaccessory(String paramString) throws HibernateException, RemoteException;
  
  String getchildtasksta(String paramString) throws HibernateException, RemoteException;
  
  boolean saveHistory(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws HibernateException, RemoteException;
  
  List getAccessory(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getHistoryInfo(String paramString) throws Exception, RemoteException;
  
  List selectSingleTaskHis(String paramString) throws HibernateException, RemoteException;
  
  Integer getCancelTaskRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectCancelTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer getCancelSettleRecordCount(Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  List selectsettleCancelTask(Long paramLong1, Integer paramInteger1, Integer paramInteger2, Long paramLong2, String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  Integer getCancelSettleRC(Long paramLong1, String[] paramArrayOfString, Long paramLong2) throws HibernateException, RemoteException;
  
  List searchCancelSettleTask(Long paramLong1, String[] paramArrayOfString, Integer paramInteger1, Integer paramInteger2, Long paramLong2) throws HibernateException, RemoteException;
  
  boolean deleteremindall(String paramString1, String paramString2) throws HibernateException, RemoteException;
  
  List getSonsById(String paramString) throws Exception, RemoteException;
}
