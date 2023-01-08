package com.js.oa.routine.boardroom.bean;

import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO;
import com.js.oa.routine.boardroom.po.BoardRoomPO;
import com.js.oa.routine.boardroom.po.BoardroomPersons;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;
import net.sf.hibernate.HibernateException;

public interface BoardRoomEJB extends EJBObject {
  boolean addBoardroom(BoardRoomPO paramBoardRoomPO, Object[] paramArrayOfObject) throws HibernateException, RemoteException;
  
  boolean deleteBoardroom(Long paramLong) throws HibernateException, RemoteException;
  
  boolean deleteBatchBoardroom(String paramString) throws HibernateException, RemoteException;
  
  BoardRoomPO selectBoardroom(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiBoardroom(BoardRoomPO paramBoardRoomPO, Object[] paramArrayOfObject) throws HibernateException, RemoteException;
  
  Long addBoardroomApply(BoardRoomApplyPO paramBoardRoomApplyPO, Long paramLong1, Long paramLong2) throws HibernateException, RemoteException;
  
  Map selectBoardroomApply(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiBoardroomApply(BoardRoomApplyPO paramBoardRoomApplyPO, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException, RemoteException;
  
  boolean deleteBoardroomApply(Long paramLong) throws HibernateException, RemoteException;
  
  List lookStateBoardroomApply(Long paramLong) throws HibernateException, RemoteException;
  
  String isImpropriateTime(BoardRoomApplyPO paramBoardRoomApplyPO, Long paramLong) throws HibernateException, RemoteException;
  
  boolean approveBoardroomApply(Long paramLong) throws HibernateException, RemoteException;
  
  boolean dissentBoardroomApply(Long paramLong) throws HibernateException, RemoteException;
  
  String maintenanceBoardRoom(String paramString) throws HibernateException, RemoteException;
  
  boolean addBoardroomAppType(BoardRoomApplyTypePO paramBoardRoomApplyTypePO) throws HibernateException, RemoteException;
  
  boolean deleteBoardroomAppType(Long paramLong) throws HibernateException, RemoteException;
  
  boolean deleteBatchBoardroomAppType(String paramString) throws HibernateException, RemoteException;
  
  BoardRoomApplyTypePO selectBoardroomAppType(Long paramLong) throws HibernateException, RemoteException;
  
  boolean modiBoardroomAppType(BoardRoomApplyTypePO paramBoardRoomApplyTypePO) throws HibernateException, RemoteException;
  
  List selectBoardroomApplyType(String paramString) throws HibernateException, RemoteException;
  
  Map getWeekMeeting(Long paramLong, Calendar paramCalendar, String paramString1, String paramString2) throws Exception, RemoteException;
  
  List getTodayMeeting(Long paramLong) throws HibernateException, RemoteException;
  
  List getNewMeeting(Long paramLong) throws HibernateException, RemoteException;
  
  String[] getBoardRoomInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  String[] getEquipmentInfo(String paramString1, String paramString2) throws Exception, RemoteException;
  
  List selectBoardroomByConditions(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws HibernateException, RemoteException;
  
  boolean addBoardroomPersons(BoardroomPersons paramBoardroomPersons) throws HibernateException, RemoteException;
  
  boolean modifyBoardroomMeetingTimePO(String paramString1, String paramString2, Integer paramInteger) throws HibernateException, RemoteException;
  
  String[][] getBoardRoomInfo2(String paramString1, String paramString2, Integer paramInteger) throws Exception, RemoteException;
  
  String getOrgIdsByUserId(String paramString) throws Exception, RemoteException;
  
  String getGroupIdsByUserId(String paramString) throws Exception, RemoteException;
  
  List getExecuteStatusList(Long paramLong1, Long paramLong2) throws Exception, RemoteException;
  
  String saveExecuteStatus(Long paramLong1, Long paramLong2, List paramList, String paramString1, String paramString2) throws Exception, RemoteException;
}
