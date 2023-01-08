package com.js.oa.routine.boardroom.bean;

import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO;
import com.js.oa.routine.boardroom.po.BoardRoomPO;
import com.js.oa.routine.boardroom.po.BoardroomPersons;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBLocalObject;
import net.sf.hibernate.HibernateException;

public interface BoardRoomEJBLocal extends EJBLocalObject {
  boolean addBoardroom(BoardRoomPO paramBoardRoomPO, Object[] paramArrayOfObject) throws HibernateException;
  
  boolean deleteBoardroom(Long paramLong) throws HibernateException;
  
  boolean deleteBatchBoardroom(String paramString) throws HibernateException;
  
  BoardRoomPO selectBoardroom(Long paramLong) throws HibernateException;
  
  boolean modiBoardroom(BoardRoomPO paramBoardRoomPO, Object[] paramArrayOfObject) throws HibernateException;
  
  Long addBoardroomApply(BoardRoomApplyPO paramBoardRoomApplyPO, Long paramLong1, Long paramLong2) throws HibernateException;
  
  Map selectBoardroomApply(Long paramLong) throws HibernateException;
  
  boolean modiBoardroomApply(BoardRoomApplyPO paramBoardRoomApplyPO, Long paramLong1, Long paramLong2, Long paramLong3) throws HibernateException;
  
  boolean deleteBoardroomApply(Long paramLong) throws HibernateException;
  
  List lookStateBoardroomApply(Long paramLong) throws HibernateException;
  
  String isImpropriateTime(BoardRoomApplyPO paramBoardRoomApplyPO, Long paramLong) throws HibernateException;
  
  boolean approveBoardroomApply(Long paramLong) throws HibernateException;
  
  boolean dissentBoardroomApply(Long paramLong) throws HibernateException;
  
  String maintenanceBoardRoom(String paramString) throws HibernateException;
  
  boolean addBoardroomAppType(BoardRoomApplyTypePO paramBoardRoomApplyTypePO) throws HibernateException;
  
  boolean deleteBoardroomAppType(Long paramLong) throws HibernateException;
  
  boolean deleteBatchBoardroomAppType(String paramString) throws HibernateException;
  
  BoardRoomApplyTypePO selectBoardroomAppType(Long paramLong) throws HibernateException;
  
  boolean modiBoardroomAppType(BoardRoomApplyTypePO paramBoardRoomApplyTypePO) throws HibernateException;
  
  List selectBoardroomApplyType(String paramString) throws HibernateException;
  
  Map getWeekMeeting(Long paramLong, Calendar paramCalendar, String paramString1, String paramString2) throws Exception;
  
  List getTodayMeeting(Long paramLong) throws HibernateException;
  
  List getNewMeeting(Long paramLong) throws HibernateException;
  
  String[] getBoardRoomInfo(String paramString1, String paramString2) throws Exception;
  
  String[] getEquipmentInfo(String paramString1, String paramString2) throws Exception;
  
  List selectBoardroomByConditions(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws HibernateException;
  
  boolean addBoardroomPersons(BoardroomPersons paramBoardroomPersons) throws HibernateException;
  
  boolean modifyBoardroomMeetingTimePO(String paramString1, String paramString2, Integer paramInteger) throws HibernateException;
  
  String[][] getBoardRoomInfo2(String paramString1, String paramString2, Integer paramInteger) throws Exception;
  
  String getOrgIdsByUserId(String paramString) throws Exception;
  
  String getGroupIdsByUserId(String paramString) throws Exception;
  
  List getExecuteStatusList(Long paramLong1, Long paramLong2) throws Exception;
  
  String saveExecuteStatus(Long paramLong1, Long paramLong2, List paramList, String paramString1, String paramString2) throws Exception;
}
