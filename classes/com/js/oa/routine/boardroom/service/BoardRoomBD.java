package com.js.oa.routine.boardroom.service;

import com.js.oa.routine.boardroom.bean.BoardRoomEJBBean;
import com.js.oa.routine.boardroom.bean.BoardRoomEJBHome;
import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO;
import com.js.oa.routine.boardroom.po.BoardRoomDiscussionPO;
import com.js.oa.routine.boardroom.po.BoardRoomPO;
import com.js.oa.routine.boardroom.po.BoardroomPersons;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.EJBProxy;
import com.js.util.util.ParameterGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.log4j.Logger;

public class BoardRoomBD {
  private static Logger logger = Logger.getLogger(BoardRoomBD.class.getName());
  
  public boolean updateMeetingTime(Long meetingId, String destineDateEndTime) {
    boolean flag = false;
    BoardRoomEJBBean boardRoomEJBBean = new BoardRoomEJBBean();
    flag = boardRoomEJBBean.updateBoardRoomApply(meetingId, destineDateEndTime);
    return flag;
  }
  
  public boolean addBoardroom(BoardRoomPO boardroomPO, Object[] para) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomPO, BoardRoomPO.class);
      pg.put(para, Object[].class);
      ejbProxy.invoke("addBoardroom", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addBoardroom BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBoardroom(Long boardroomId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomId, Long.class);
      ejbProxy.invoke("deleteBoardroom", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBoardroom BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean deleteBatchBoardroom(String boardroomIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomIds, String.class);
      ejbProxy.invoke("deleteBatchBoardroom", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchBoardroom BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public BoardRoomPO selectBoardroom(Long boardroomId) {
    BoardRoomPO boardroomPO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomId, Long.class);
      boardroomPO = (BoardRoomPO)ejbProxy.invoke("selectBoardroom", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectBoardroom BD Exception:" + 
          ex.getMessage());
    } finally {}
    return boardroomPO;
  }
  
  public boolean modiBoardroom(BoardRoomPO boardroomPO, Object[] para) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomPO, BoardRoomPO.class);
      pg.put(para, Object[].class);
      ejbProxy.invoke("modiBoardroom", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("modiBoardroom BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public Long addBoardroomApply(BoardRoomApplyPO boardroomApplyPO, Long boardroomId, Long boardroomApplyTypeId) {
    Long result = null;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomApplyPO, BoardRoomApplyPO.class);
      pg.put(boardroomId, "Long");
      pg.put(boardroomApplyTypeId, "Long");
      result = (Long)ejbProxy.invoke("addBoardroomApply", 
          pg.getParameters());
    } catch (Exception ex) {
      result = new Long("-1");
      System.out.println("addBoardroomApply BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public Map selectBoardroomApply(Long boardroomApplyId) {
    Map boardroomApply = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomApplyId, Long.class);
      boardroomApply = (Map)ejbProxy.invoke("selectBoardroomApply", 
          pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectBoardroomApply BD Exception:" + 
          ex.getMessage());
    } 
    return boardroomApply;
  }
  
  public boolean modiBoardroomApply(BoardRoomApplyPO boardroomApplyPO, Long boardroomApplyTypeId, Long boardroomId, Long boardroomApplyType) {
    return modiBoardroomApply(boardroomApplyPO, boardroomApplyTypeId, boardroomId, boardroomApplyType, "modi");
  }
  
  public boolean modiBoardroomApply(BoardRoomApplyPO boardroomApplyPO, Long boardroomApplyTypeId, Long boardroomId, Long boardroomApplyType, String flag) {
    boolean result = false;
    try {
      result = (new BoardRoomEJBBean()).modiBoardroomApply(boardroomApplyPO, boardroomApplyTypeId, boardroomId, boardroomApplyType, flag);
    } catch (Exception ex) {
      System.out.println("modiBoardroomApply BD Exception:" + ex.getMessage());
      ex.printStackTrace();
    } 
    return result;
  }
  
  public boolean deleteBoardroomApply(Long boardroomApplyId) {
    return deleteBoardroomApply(boardroomApplyId, "");
  }
  
  public boolean deleteBoardroomApply(Long boardroomApplyId, String meetingTimeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomApplyId, Long.class);
      pg.put(meetingTimeId, String.class);
      ejbProxy.invoke("deleteBoardroomApply", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBoardroomApply BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public List lookStateBoardroomApply(Long boardroomId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomId, Long.class);
      result = (List)ejbProxy.invoke(
          "lookStateBoardroomApply", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("lookStateBoardroomApplyBD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public String isImpropriateTime(BoardRoomApplyPO boardroomApplyPO, Long boardroomId) {
    String isImpropriateTime = "";
    ParameterGenerator pg = new ParameterGenerator(2);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomApplyPO, BoardRoomApplyPO.class);
      pg.put(boardroomId, "Long");
      isImpropriateTime = (String)ejbProxy.invoke(
          "isImpropriateTime", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("isImpropriateTimeBD Exception:" + 
          ex.getMessage());
    } 
    return isImpropriateTime;
  }
  
  public boolean applyTime(String boardroomId, String boardroomApplyId, Long beginDateLong, Long endDateLong) {
    return applyTime(boardroomId, boardroomApplyId, beginDateLong, endDateLong, "-1");
  }
  
  public boolean applyTime(String boardroomId, String boardroomApplyId, Long beginDateLong, Long endDateLong, String s) {
    List<Long[]> list = (new BoardRoomEJBBean()).applyTime(boardroomId, boardroomApplyId, beginDateLong.longValue(), endDateLong.longValue());
    boolean flag = false;
    flag = checkTime(list, endDateLong.longValue(), beginDateLong.longValue());
    if (!flag) {
      list = (new BoardRoomEJBBean()).getRegularMeeting(boardroomApplyId, boardroomId, s);
      flag = checkTime(list, endDateLong.longValue(), beginDateLong.longValue());
    } 
    return flag;
  }
  
  private boolean applyTime1(String boardroomId, String boardroomApplyId, Long beginDateLong, Long endDateLong, String s) {
    List<Long[]> list = (new BoardRoomEJBBean()).applyTime1(boardroomId, boardroomApplyId, beginDateLong.longValue(), endDateLong.longValue());
    boolean flag = false;
    flag = checkTime(list, endDateLong.longValue(), beginDateLong.longValue());
    if (!flag) {
      list = (new BoardRoomEJBBean()).getRegularMeeting1(boardroomApplyId, boardroomId, s);
      flag = checkTime(list, endDateLong.longValue(), beginDateLong.longValue());
    } 
    return flag;
  }
  
  private boolean applyTime2(String boardroomId, String boardroomApplyId, Long beginDateLong, Long endDateLong, String s) {
    List<Long[]> list = (new BoardRoomEJBBean()).applyTime2(boardroomId, boardroomApplyId, beginDateLong.longValue(), endDateLong.longValue());
    boolean flag = false;
    flag = checkTime(list, endDateLong.longValue(), beginDateLong.longValue());
    if (!flag) {
      list = (new BoardRoomEJBBean()).getRegularMeeting(boardroomApplyId, boardroomId, s);
      flag = checkTime(list, endDateLong.longValue(), beginDateLong.longValue());
    } 
    return flag;
  }
  
  public String applyTime3(String boardroomId, String boardroomApplyId, Long beginDateLong, Long endDateLong, String s) {
    boolean flag = applyTime2(boardroomId, boardroomApplyId, beginDateLong, endDateLong, s);
    if (flag)
      return "1"; 
    flag = applyTime1(boardroomId, boardroomApplyId, beginDateLong, endDateLong, s);
    if (flag)
      return "0"; 
    return "-1";
  }
  
  public boolean applyTime(Object[] applyInfo) {
    boolean flag = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date realBeginDate, nowDate = new Date();
      Date meetingBeginDate = dateFormat.parse((applyInfo[3] == null) ? "" : applyInfo[3].toString());
      if (meetingBeginDate.getTime() > nowDate.getTime()) {
        realBeginDate = meetingBeginDate;
      } else {
        realBeginDate = nowDate;
      } 
      List<Long[]> applyList = (new BoardRoomEJBBean()).getRegularMeetingList(applyInfo, realBeginDate);
      List<Long[]> list = (new BoardRoomEJBBean()).applyTime((String)applyInfo[10], (String)applyInfo[9], realBeginDate.getTime(), 0L);
      int i;
      for (i = 0; i < applyList.size(); i++) {
        Long[] applyLong = applyList.get(i);
        flag = checkTime(list, applyLong[1].longValue(), applyLong[0].longValue());
        if (flag)
          break; 
      } 
      if (!flag) {
        list = (new BoardRoomEJBBean()).getRegularMeeting((String)applyInfo[9], (String)applyInfo[10], (String)applyInfo[11]);
        for (i = 0; i < applyList.size(); i++) {
          Long[] applyLong = applyList.get(i);
          flag = checkTime(list, applyLong[1].longValue(), applyLong[0].longValue());
          if (flag)
            break; 
        } 
      } 
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return flag;
  }
  
  public String applyTime1(Object[] applyInfo) {
    boolean flag = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date realBeginDate, nowDate = new Date();
      Date meetingBeginDate = dateFormat.parse((applyInfo[3] == null) ? "" : applyInfo[3].toString());
      if (meetingBeginDate.getTime() > nowDate.getTime()) {
        realBeginDate = meetingBeginDate;
      } else {
        realBeginDate = nowDate;
      } 
      List<Long[]> applyList = (new BoardRoomEJBBean()).getRegularMeetingList(applyInfo, realBeginDate);
      List<Long[]> list = (new BoardRoomEJBBean()).applyTime2((String)applyInfo[10], (String)applyInfo[9], realBeginDate.getTime(), 0L);
      int i;
      for (i = 0; i < applyList.size(); i++) {
        Long[] applyLong = applyList.get(i);
        flag = checkTime(list, applyLong[1].longValue(), applyLong[0].longValue());
        if (flag)
          break; 
      } 
      if (!flag) {
        list = (new BoardRoomEJBBean()).getRegularMeeting((String)applyInfo[9], (String)applyInfo[10], (String)applyInfo[11]);
        for (i = 0; i < applyList.size(); i++) {
          Long[] applyLong = applyList.get(i);
          flag = checkTime(list, applyLong[1].longValue(), applyLong[0].longValue());
          if (flag)
            break; 
        } 
      } 
      if (flag)
        return "1"; 
      list = (new BoardRoomEJBBean()).applyTime1((String)applyInfo[10], (String)applyInfo[9], realBeginDate.getTime(), 0L);
      for (i = 0; i < applyList.size(); i++) {
        Long[] applyLong = applyList.get(i);
        flag = checkTime(list, applyLong[1].longValue(), applyLong[0].longValue());
        if (flag)
          break; 
      } 
      if (!flag) {
        list = (new BoardRoomEJBBean()).getRegularMeeting1((String)applyInfo[9], (String)applyInfo[10], (String)applyInfo[11]);
        for (i = 0; i < applyList.size(); i++) {
          Long[] applyLong = applyList.get(i);
          flag = checkTime(list, applyLong[1].longValue(), applyLong[0].longValue());
          if (flag)
            break; 
        } 
      } 
      if (flag)
        return "0"; 
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return "-1";
  }
  
  public boolean checkTime(List<Long[]> list, long endDateLong, long beginDateLong) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    boolean flag = false;
    if (list.size() <= 0) {
      flag = false;
    } else {
      for (int i = 0; i < list.size(); i++) {
        Long[] meetingTime = list.get(i);
        if (endDateLong <= meetingTime[0].longValue() || beginDateLong >= meetingTime[1].longValue() || (meetingTime[0].longValue() == beginDateLong && meetingTime[1].longValue() == endDateLong)) {
          flag = false;
        } else {
          String[] r = (new BoardRoomEJBBean()).meetingE((String)meetingTime[3]);
          flag = true;
          break;
        } 
      } 
    } 
    System.out.println("flag:" + System.currentTimeMillis() + "-----" + flag);
    return flag;
  }
  
  public boolean approveBoardroomApply(Long boardroomApplyId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomApplyId, Long.class);
      ejbProxy.invoke("approveBoardroomApply", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("approveBoardroomApply BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public boolean dissentBoardroomApply(Long boardroomApplyId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardroomApplyId, Long.class);
      ejbProxy.invoke("dissentBoardroomApply", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("dissentBoardroomApply BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public String maintenanceBoardRoom(String scopeWhere) {
    String maintenanceBoardRoomIds = "";
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(scopeWhere, String.class);
      maintenanceBoardRoomIds = (String)ejbProxy.invoke(
          "maintenanceBoardRoom", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("maintenanceBoardRoom BD Exception:" + 
          ex.getMessage());
    } 
    return maintenanceBoardRoomIds;
  }
  
  public boolean addBoardroomAppType(BoardRoomApplyTypePO boardRoomAppTypePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardRoomAppTypePO, BoardRoomApplyTypePO.class);
      result = ((Boolean)ejbProxy.invoke("addBoardroomAppType", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("addBoardroomAppType BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public boolean deleteBoardroomAppType(Long bdroomAppTypeId) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(bdroomAppTypeId, Long.class);
      ejbProxy.invoke("deleteBoardroomAppType", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBoardroomAppType BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public void boardroomAppTypeDefault(String bdroomAppTypeId) {
    (new BoardRoomEJBBean()).boardroomAppTypeDefault(bdroomAppTypeId);
  }
  
  public boolean deleteBatchBoardroomAppType(String bdroomAppTypeIds) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(bdroomAppTypeIds, String.class);
      ejbProxy.invoke("deleteBatchBoardroomAppType", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("deleteBatchBoardroomAppType BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public BoardRoomApplyTypePO selectBoardroomAppType(Long bdroomAppTypeId) {
    BoardRoomApplyTypePO boardRoomAppTypePO = null;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(bdroomAppTypeId, Long.class);
      boardRoomAppTypePO = (BoardRoomApplyTypePO)ejbProxy.invoke(
          "selectBoardroomAppType", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectBoardroomAppType BD Exception:" + 
          ex.getMessage());
    } 
    return boardRoomAppTypePO;
  }
  
  public boolean modiBoardroomAppType(BoardRoomApplyTypePO boardRoomAppTypePO) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(boardRoomAppTypePO, BoardRoomApplyTypePO.class);
      result = ((Boolean)ejbProxy.invoke("modiBoardroomAppType", pg.getParameters())).booleanValue();
    } catch (Exception ex) {
      System.out.println("modiBoardroomAppType BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public List selectBoardroomApplyType(String domainId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      pg.put(domainId, String.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      result = (List)ejbProxy.invoke(
          "selectBoardroomApplyType", pg.getParameters());
    } catch (Exception ex) {
      System.out.println("selectBoardroomApplyType BD Exception:" + 
          ex.getMessage());
    } 
    return result;
  }
  
  public Map getWeekMeeting(Long userId, Calendar nowDate, String domainId, String orgId) {
    Map<Object, Object> weekMeeting = new HashMap<Object, Object>();
    try {
      ParameterGenerator pg = new ParameterGenerator(4);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(userId, Long.class);
      pg.put(nowDate, Calendar.class);
      pg.put(domainId, String.class);
      pg.put(orgId, String.class);
      weekMeeting = (Map<Object, Object>)ejbProxy.invoke("getWeekMeeting", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return weekMeeting;
  }
  
  public List getTodayMeeting(Long userId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(userId, Long.class);
      result = (List)ejbProxy.invoke("getTodayMeeting", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public List getNewMeeting(Long userId) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(userId, Long.class);
      result = (List)ejbProxy.invoke("getNewMeeting", pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String getBoardRoomInfo(String voitureId, String searchDate) {
    String boardRoomPlace = "";
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitureId, String.class);
      pg.put(searchDate, String.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      boardRoomPlace = (String)ejbProxy.invoke("getBoardRoomInfo", pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return boardRoomPlace;
  }
  
  public String[] getEquipmentInfo(String voitureId, String searchDate) {
    String[] boardRoomPlace = new String[288];
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(voitureId, String.class);
      pg.put(searchDate, String.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      boardRoomPlace = (String[])ejbProxy.invoke("getEquipmentInfo", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return boardRoomPlace;
  }
  
  public Map getNewBoardNotyfyCount(String userID, String domainId) throws SQLException, Exception {
    int retInt = 0;
    String destineDate = "";
    Map<Object, Object> resultMap = new HashMap<Object, Object>();
    DbOpt opt = new DbOpt();
    try {
      destineDate = (new SimpleDateFormat("yyyy-MM-dd")).format(
          new Date()).toString();
      String sql = "";
      String dbType = SystemCommon.getDatabaseType();
      if ("oracle".equals(dbType)) {
        sql = 
          "SELECT COUNT(*) AS Expr1 FROM OA_BOARDROOMAPPLY WHERE (ATTENDEEEMPID LIKE '%" + 
          userID + "%') AND (domain_id = " + domainId + 
          ") AND (STATUS = 0) AND (DESTINEDATE = TO_DATE('" + 
          destineDate + 
          "', 'yyyy-MM-dd')) and (MOTIF LIKE '%*%')";
      } else if ("mssqlserver".equalsIgnoreCase(dbType)) {
        sql = 
          "SELECT COUNT(*) AS Expr1 FROM OA_BOARDROOMAPPLY WHERE (ATTENDEEEMPID LIKE '%" + 
          userID + "%') AND (domain_id = " + domainId + 
          ") AND (STATUS = 0) AND (DESTINEDATE = '" + destineDate + 
          "') and (MOTIF LIKE '%*%')";
      } else if ("mysql".equals(dbType)) {
        sql = 
          "SELECT COUNT(*) AS Expr1 FROM OA_BOARDROOMAPPLY WHERE (ATTENDEEEMPID LIKE '%" + 
          userID + "%') AND (domain_id = " + domainId + 
          ") AND (STATUS = 0) AND (DESTINEDATE = '" + destineDate + 
          "') and (MOTIF LIKE '%*%')";
      } else {
        sql = 
          "SELECT COUNT(*) AS Expr1 FROM OA_BOARDROOMAPPLY WHERE (ATTENDEEEMPID LIKE '%" + 
          userID + "%') AND (domain_id = " + domainId + 
          ") AND (STATUS = 0) AND (DESTINEDATE = '" + destineDate + 
          "') and (MOTIF LIKE '%*%')";
      } 
      String ret = opt.executeQueryToStr(sql);
      if (ret != null && ret.length() > 0)
        resultMap.put("meeting", ret); 
      opt.close();
    } catch (Exception ex) {
      opt.close();
      ex.printStackTrace();
    } 
    return resultMap;
  }
  
  public List selectBoardroomByConditions(String userId, String personNum, String boardtime, String startTime, String endTime, String equipment, String domainId, String where) {
    List result = new ArrayList();
    ParameterGenerator pg = new ParameterGenerator(8);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(userId, String.class);
      pg.put(personNum, String.class);
      pg.put(boardtime, String.class);
      pg.put(startTime, String.class);
      pg.put(endTime, String.class);
      pg.put(equipment, String.class);
      pg.put(domainId, String.class);
      pg.put(where, String.class);
      result = (List)ejbProxy.invoke("selectBoardroomByConditions", 
          pg.getParameters());
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public String scopeWhere(HttpServletRequest httpServletRequest, String rightType, String rightName) {
    HttpSession httpSession = httpServletRequest.getSession(true);
    return (new ManagerService()).getRightFinalWhere(
        httpSession.getAttribute("userId").toString(), 
        httpSession.getAttribute("orgId").toString(), 
        httpSession.getAttribute("orgIdString").toString(), 
        rightType, 
        rightName, 
        "boardRoomPO.createdOrg", 
        "boardRoomPO.createdEmp");
  }
  
  public boolean addBoardroomPersons(BoardroomPersons po) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(po, BoardroomPersons.class);
      ejbProxy.invoke("addBoardroomPersons", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("addBoardroomPersons BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modityBoardroomPersons(BoardroomPersons po) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(1);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(po, BoardroomPersons.class);
      ejbProxy.invoke("modityBoardroomPersons", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("modityBoardroomPersons BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public boolean modifyBoardroomMeetingTimePO(String meetingTimeId, String boardRoomApplyId, Integer endTime) {
    boolean result = false;
    ParameterGenerator pg = new ParameterGenerator(3);
    try {
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      pg.put(meetingTimeId, String.class);
      pg.put(boardRoomApplyId, String.class);
      pg.put(endTime, Integer.class);
      ejbProxy.invoke("modifyBoardroomMeetingTimePO", pg.getParameters());
      result = true;
    } catch (Exception ex) {
      System.out.println("modifyBoardroomMeetingTimePO BD Exception:" + 
          ex.getMessage());
    } finally {}
    return result;
  }
  
  public String[][] getBoardRoomInfo2(String voitureId, String searchDate, Integer type) {
    String[][] boardRoomPlace = (String[][])null;
    try {
      ParameterGenerator pg = new ParameterGenerator(3);
      pg.put(voitureId, String.class);
      pg.put(searchDate, String.class);
      pg.put(type, Integer.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      boardRoomPlace = (String[][])ejbProxy.invoke("getBoardRoomInfo2", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return boardRoomPlace;
  }
  
  public String getOrgIdsByUserId(String userId) {
    String ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      ret = (String)ejbProxy.invoke("getOrgIdsByUserId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public String getGroupIdsByUserId(String userId) {
    String ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(1);
      pg.put(userId, String.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      ret = (String)ejbProxy.invoke("getGroupIdsByUserId", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public List getExecuteStatusList(Long applyId, Long meetingId) {
    List ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(2);
      pg.put(applyId, Long.class);
      pg.put(meetingId, Long.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      ret = (List)ejbProxy.invoke("getExecuteStatusList", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to getExecuteStatusList information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public String saveExecuteStatus(Long applyId, Long meetingId, List list, String description, String bespoIds) {
    String ret = null;
    try {
      ParameterGenerator pg = new ParameterGenerator(5);
      pg.put(applyId, Long.class);
      pg.put(meetingId, Long.class);
      pg.put(list, List.class);
      pg.put(description, String.class);
      pg.put(bespoIds, String.class);
      EJBProxy ejbProxy = new EJBProxy("BoardRoomEJB", 
          "BoardRoomEJBLocal", BoardRoomEJBHome.class);
      ret = (String)ejbProxy.invoke("saveExecuteStatus", 
          pg.getParameters());
    } catch (Exception e) {
      logger.error("error to saveExecuteStatus information :" + e.getMessage());
      System.out.println(
          "-----------------------------------------------");
      e.printStackTrace();
      System.out.println(
          "-----------------------------------------------");
    } 
    return ret;
  }
  
  public BoardRoomApplyPO getBoardRoomApplyPObyId(Long boardroomApplyId) throws HibernateException {
    BoardRoomApplyPO boardRoomApplyPO = new BoardRoomApplyPO();
    BoardRoomEJBBean boardRoomEJBBean = new BoardRoomEJBBean();
    boardRoomApplyPO = boardRoomEJBBean.getBoardRoomApplyPObyId(boardroomApplyId);
    return boardRoomApplyPO;
  }
  
  public List getboardRoomApplyAssPOList(Long applyId) throws Exception {
    List result = new ArrayList();
    BoardRoomEJBBean boardRoomEJBBean = new BoardRoomEJBBean();
    result = boardRoomEJBBean.getboardRoomApplyAssPOList(applyId);
    return result;
  }
  
  public void addSummary(Long boardroomApplyId, String summary, String[] summarySaveName, String[] summaryName) throws HibernateException, SQLException {
    BoardRoomEJBBean boardRoomEJBBean = new BoardRoomEJBBean();
    boardRoomEJBBean.addSummary(boardroomApplyId, summary, summarySaveName, summaryName);
  }
  
  public Object[][] getBoardRoomSatus(String searchDate) {
    return getBoardRoomSatus(searchDate, "", "3", "");
  }
  
  public Object[][] getBoardRoomSatus(String searchDate, String roomNum, String cond, String where) {
    Object[][] obj = (Object[][])null;
    try {
      obj = (new BoardRoomEJBBean()).getBoardRoomSatus(searchDate, roomNum, cond, where);
    } catch (Exception e) {
      logger.error("error to load information :" + e.getMessage());
      System.out.println("-----------------------------------------------");
      e.printStackTrace();
      System.out.println("-----------------------------------------------");
    } 
    return obj;
  }
  
  public List getMeetingList() {
    try {
      return (new BoardRoomEJBBean()).getMeetingList();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public void saveDiscussion(BoardRoomDiscussionPO po) {
    try {
      (new BoardRoomEJBBean()).saveDiscussion(po);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String[] boradroomUser(String applyId) {
    return (new BoardRoomEJBBean()).boardroomUser(applyId);
  }
  
  public String[][] getRoomShow(Date weekBeginDate) {
    return getWeekRoomShow("-1", weekBeginDate, 1).get(0);
  }
  
  public String[][] getRoomShow(String roomId, Date beginDate) {
    return getWeekRoomShow(roomId, beginDate, 1).get(0);
  }
  
  public List<String[][]> getWeekRoomShow(Date beginDate) {
    return getWeekRoomShow("-1", beginDate, 7);
  }
  
  public List<String[][]> getWeekRoomShow(String roomId, Date beginDate) {
    return getWeekRoomShow(roomId, beginDate, 7);
  }
  
  public List<String[][]> getDateRoomShow(String roomId, Date beginDate, Date endDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(beginDate);
    int beginNum = c.get(5);
    c.setTime(endDate);
    int endNum = c.get(5);
    return getWeekRoomShow(roomId, beginDate, endNum - beginNum + 1);
  }
  
  public List<String[][]> getDateRoomShow(Date beginDate, Date endDate) {
    Calendar c = Calendar.getInstance();
    c.setTime(beginDate);
    int beginNum = c.get(5);
    c.setTime(endDate);
    int endNum = c.get(5);
    return getWeekRoomShow("-1", beginDate, endNum - beginNum + 1);
  }
  
  public List<String[][]> getWeekRoomShow(String roomId, Date weekBeginDate, int dayNum) {
    return (new BoardRoomEJBBean()).getWeekRoomShow(roomId, weekBeginDate, dayNum);
  }
  
  public boolean deleteRegularApply(String applyId) {
    return (new BoardRoomEJBBean()).deleteRegularApply(applyId);
  }
  
  public int getBoardRoomApply(String applyid) {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Connection conn = null;
    DataSourceBase base = new DataSourceBase();
    int count = 0;
    String sql = "SELECT COUNT(*) FROM OA_BOARDROOMAPPLY WHERE BOARDROOMAPPLYid=?";
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, applyid);
      rs = pstmt.executeQuery();
      if (rs.next())
        count = rs.getInt(1); 
      pstmt.close();
      rs.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (pstmt != null)
          pstmt.close(); 
        if (rs != null)
          rs.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return count;
  }
}
