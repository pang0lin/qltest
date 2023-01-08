package com.js.oa.routine.boardroom.bean;

import com.js.oa.routine.boardroom.po.BdroomAppAccessoryPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyAssPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO;
import com.js.oa.routine.boardroom.po.BoardRoomDiscussionPO;
import com.js.oa.routine.boardroom.po.BoardRoomEquipmentPO;
import com.js.oa.routine.boardroom.po.BoardRoomPO;
import com.js.oa.routine.boardroom.po.BoardRoomRegularPO;
import com.js.oa.routine.boardroom.po.BoardroomExecuteStatusPO;
import com.js.oa.routine.boardroom.po.BoardroomMeetingTimePO;
import com.js.oa.routine.boardroom.po.BoardroomPersons;
import com.js.system.service.messages.RemindUtil;
import com.js.thread.BoardRoomTask;
import com.js.util.config.SystemCommon;
import com.js.util.hibernate.HibernateBase;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class BoardRoomEJBBean extends HibernateBase implements SessionBean {
  SessionContext sessionContext = null;
  
  public void ejbCreate() throws CreateException {}
  
  public void ejbRemove() {}
  
  public void ejbActivate() {}
  
  public void ejbPassivate() {}
  
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  
  public boolean updateBoardRoomApply(Long meetingId, String endTime) {
    boolean flag = false;
    String sql = "";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery("SELECT id,endlong,endtime FROM oa_boardroom_meetingtime WHERE id=" + meetingId);
      if (rs.next()) {
        Long endLong = Long.valueOf(rs.getLong(2) - rs.getLong(3) * 1000L + Long.valueOf(endTime).longValue() * 1000L);
        sql = "update OA_BOARDROOM_MEETINGTIME set endtime=" + endTime + ",endLong=" + endLong + " where id=" + rs.getLong("id");
      } 
      rs.close();
      int num = base.executeUpdate(sql);
      if (num > 0)
        flag = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    return flag;
  }
  
  public boolean addBoardroom(BoardRoomPO boardroomPO, Object[] para) throws HibernateException {
    boolean result = false;
    begin();
    try {
      this.session.save(boardroomPO);
      if (para[0] != null) {
        String[] equName = (String[])para[0];
        String[] equDescribe = (String[])para[1];
        for (int i = 0; i < equName.length; i++) {
          BoardRoomEquipmentPO bdEquPO = new BoardRoomEquipmentPO();
          bdEquPO.setEquName(equName[i]);
          bdEquPO.setEquDescribe(equDescribe[i]);
          bdEquPO.setBoardRoomPO(boardroomPO);
          this.session.save(bdEquPO);
        } 
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("addBoardroom EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteBoardroom(Long boardroomId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      BoardRoomPO boardroomPO = (BoardRoomPO)this.session.get(BoardRoomPO.class, 
          boardroomId);
      if (boardroomPO != null) {
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute(
            "DELETE FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=(select wf_immoform_id from jsf_immobilityform where wf_module_id=15)  AND  WORKRECORD_ID in (select boardroomapplyid from oa_boardroomapply where boardroomid=" + 
            
            boardroomId + ")");
        this.session.delete(
            "from com.js.oa.routine.boardroom.po.BoardRoomEquipmentPO po where po.boardRoomPO.boardroomId=" + 
            boardroomId);
        this.session.flush();
        this.session.delete(boardroomPO);
        this.session.flush();
        stmt.close();
        conn.close();
      } 
      result = true;
    } catch (HibernateException e) {
      System.out.println("deleteBoardroom EJB Exception:" + e.toString());
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteBatchBoardroom(String boardroomIds) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String[] idsArr = boardroomIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        stmt.execute(
            "DELETE FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=(select wf_immoform_id from jsf_immobilityform where wf_module_id=15)  AND WORKRECORD_ID in (select boardroomapplyid from oa_boardroomapply where boardroomid=" + 
            
            idsArr[i] + ")");
        this.session.delete(
            "from com.js.oa.routine.boardroom.po.BoardRoomEquipmentPO po where po.boardRoomPO.boardroomId=" + 
            idsArr[i]);
        this.session.flush();
        BoardRoomPO boardroomPO = (BoardRoomPO)this.session.load(
            BoardRoomPO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(boardroomPO);
      } 
      this.session.flush();
      result = true;
      stmt.close();
      conn.close();
    } catch (HibernateException e) {
      System.out.println("deleteBatchBoardroom EJB Exception:" + 
          e.toString());
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public BoardRoomPO selectBoardroom(Long boardroomId) throws HibernateException {
    BoardRoomPO boardroomPO = null;
    if (boardroomId.longValue() == 0L) {
      boardroomPO = new BoardRoomPO();
      return boardroomPO;
    } 
    begin();
    try {
      boardroomPO = (BoardRoomPO)this.session.load(BoardRoomPO.class, boardroomId);
    } catch (HibernateException e) {
      System.out.println("selectBoardroom EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return boardroomPO;
  }
  
  public boolean modiBoardroom(BoardRoomPO boardroomPO, Object[] para) throws HibernateException {
    boolean result = false;
    begin();
    try {
      BoardRoomPO boardroom = (BoardRoomPO)this.session.load(BoardRoomPO.class, 
          boardroomPO.getBoardroomId());
      boardroom.setBoardroomId(boardroomPO.getBoardroomId());
      boardroom.setName(boardroomPO.getName());
      boardroom.setDepict(boardroomPO.getDepict());
      boardroom.setLocation(boardroomPO.getLocation());
      boardroom.setCapacitance(boardroomPO.getCapacitance());
      boardroom.setBoardroomType(boardroomPO.getBoardroomType());
      boardroom.setCost(boardroomPO.getCost());
      boardroom.setEmphasis(boardroomPO.getEmphasis());
      boardroom.setLimit(boardroomPO.getLimit());
      boardroom.setScope(boardroomPO.getScope());
      boardroom.setScopeEmp(boardroomPO.getScopeEmp());
      boardroom.setScopeGroup(boardroomPO.getScopeGroup());
      boardroom.setScopeOrg(boardroomPO.getScopeOrg());
      boardroom.setRemark(boardroomPO.getRemark());
      boardroom.setDoorNumber(boardroomPO.getDoorNumber());
      boardroom.setIsVideo(boardroomPO.getIsVideo());
      boardroom.setMaxNumber(boardroomPO.getMaxNumber());
      boardroom.setWorkaddress(boardroomPO.getWorkaddress());
      boardroom.setManageOrg(boardroomPO.getManageOrg());
      boardroom.setManageOrgName(boardroomPO.getManageOrgName());
      boardroom.setBoardRoomLayout(boardroomPO.getBoardRoomLayout());
      boardroom.setApplyPhone(boardroomPO.getApplyPhone());
      boardroom.setReserveType(boardroomPO.getReserveType());
      boardroom.setChargeType(boardroomPO.getChargeType());
      boardroom.setCircleType(boardroomPO.getCircleType());
      boardroom.setUseScope(boardroomPO.getUseScope());
      boardroom.setUseScopeId(boardroomPO.getUseScopeId());
      boardroom.setBoardroomOrder(boardroomPO.getBoardroomOrder());
      Iterator<BoardRoomEquipmentPO> iter = boardroom.getBoardRoomEquipment().iterator();
      boardroom.setBoardRoomEquipment(null);
      while (iter.hasNext())
        this.session.delete(iter.next()); 
      if (para != null && para[0] != null) {
        String[] equName = (String[])para[0];
        String[] equDescribe = (String[])para[1];
        for (int i = 0; i < equName.length; i++) {
          BoardRoomEquipmentPO bdEquPO = new BoardRoomEquipmentPO();
          bdEquPO.setEquName(equName[i]);
          bdEquPO.setEquDescribe(equDescribe[i]);
          bdEquPO.setBoardRoomPO(boardroomPO);
          this.session.save(bdEquPO);
        } 
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public Long addBoardroomApply(BoardRoomApplyPO boardroomApplyPO, Long boardroomId, Long boardroomApplyTypeId) throws HibernateException {
    boardroomApplyPO.setMotif(boardroomApplyPO.getMotif());
    Long id = null;
    BoardRoomPO boardroom = null;
    BoardRoomApplyTypePO boardroomApplyType = null;
    begin();
    try {
      if (boardroomId.longValue() != 0L) {
        boardroom = (BoardRoomPO)this.session.load(BoardRoomPO.class, boardroomId);
        boardroomApplyPO.setBoardroom(boardroom);
      } 
      if (boardroomApplyTypeId != null && 
        boardroomApplyTypeId.longValue() != 0L) {
        boardroomApplyType = (BoardRoomApplyTypePO)this.session.load(
            BoardRoomApplyTypePO.class, boardroomApplyTypeId);
        boardroomApplyPO.setBdroomAppType(boardroomApplyType);
      } 
      if (boardroomApplyPO.getRegularPO() != null)
        this.session.save(boardroomApplyPO.getRegularPO()); 
      id = (Long)this.session.save(boardroomApplyPO);
      Set set = boardroomApplyPO.getBdRoomAppAccessory();
      HashSet hs = new HashSet();
      boardroomApplyPO.setBdRoomAppAccessory(hs);
      Iterator<BdroomAppAccessoryPO> iterator = set.iterator();
      BdroomAppAccessoryPO bdroomAppAccessoryPO = null;
      while (iterator != null && iterator.hasNext()) {
        bdroomAppAccessoryPO = iterator.next();
        bdroomAppAccessoryPO.setBoardRoomApply(boardroomApplyPO);
        boardroomApplyPO.getBdRoomAppAccessory().add(
            bdroomAppAccessoryPO);
        this.session.save(bdroomAppAccessoryPO);
      } 
      this.session.flush();
    } catch (HibernateException e) {
      id = new Long("-1");
      System.out.println("addBoardroomApply EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return id;
  }
  
  public boolean isExitMeetApplay(BoardRoomApplyPO boardroomApplyPO, Long boardroomId, Long boardroomApplyTypeId) {
    boolean flag = false;
    return flag;
  }
  
  public Map selectBoardroomApply(Long boardroomApplyId) throws HibernateException {
    BoardRoomApplyPO boardroomApplyPO = null;
    begin();
    Map<Object, Object> map = new HashMap<Object, Object>();
    try {
      boardroomApplyPO = (BoardRoomApplyPO)this.session.load(BoardRoomApplyPO.class, boardroomApplyId);
      Hibernate.initialize(boardroomApplyPO.getMeetingTime());
      Hibernate.initialize(boardroomApplyPO.getPersons());
      map.put("boardroomApplyPO", boardroomApplyPO);
      Query query = this.session.createQuery("select bdRoomAppAccessory.accessoryname,bdRoomAppAccessory.accessorysavename from  com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApply join boardRoomApply.bdRoomAppAccessory bdRoomAppAccessory where boardRoomApply.boardroomApplyId = " + 
          boardroomApplyId);
      map.put("accessory", query.list());
    } catch (Exception e) {
      System.out.println("selectBoardroomApply EJB Exception:" + e.toString());
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return map;
  }
  
  public boolean modiBoardroomApply(BoardRoomApplyPO boardroomApplyPO, Long boardroomApplyId, Long boardroomId, Long boardroomApplyType) throws HibernateException {
    return modiBoardroomApply(boardroomApplyPO, boardroomApplyId, boardroomId, boardroomApplyType, "modi");
  }
  
  public boolean modiBoardroomApply(BoardRoomApplyPO boardroomApplyPO, Long boardroomApplyId, Long boardroomId, Long boardroomApplyType, String flag) throws HibernateException {
    boolean result = false;
    String chuxiUser = "";
    String liexiUser = "";
    String url = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=@@boardroomApplyId@@&boardroomName=@@boardroomName@@&type=view&executeStatus=false";
    String motif = "";
    begin();
    try {
      BoardRoomApplyPO boardRoomApply = (BoardRoomApplyPO)this.session.load(BoardRoomApplyPO.class, 
          boardroomApplyPO.getBoardroomApplyId());
      BoardRoomPO boardroom = null;
      if (boardroomId.longValue() != 0L)
        boardroom = (BoardRoomPO)this.session.get(BoardRoomPO.class, boardroomId); 
      BoardRoomApplyTypePO boardroomApplyTypePO = 
        (BoardRoomApplyTypePO)this.session.get(BoardRoomApplyTypePO.class, boardroomApplyType);
      if (boardroom != null)
        boardRoomApply.setBoardroom(boardroom); 
      if (boardroomApplyTypePO != null)
        boardRoomApply.setBdroomAppType(boardroomApplyTypePO); 
      if (boardroomApplyPO.getRegularPO() != null) {
        BoardRoomRegularPO regularPO = boardroomApplyPO.getRegularPO();
        BoardRoomRegularPO rPo = (BoardRoomRegularPO)this.session.load(BoardRoomRegularPO.class, 
            regularPO.getRegularId());
        rPo.setMeetingCircle(regularPO.getMeetingCircle());
        rPo.setMeetingLength(regularPO.getMeetingLength());
        rPo.setMeetingDateBegin(regularPO.getMeetingDateBegin());
        rPo.setMeetingDateEnd(regularPO.getMeetingDateEnd());
        rPo.setEveryMeetingBegin(regularPO.getEveryMeetingBegin());
        rPo.setEveryMeetingEnd(regularPO.getEveryMeetingEnd());
        rPo.setEveryMeetingBeginTime(regularPO.getEveryMeetingBeginTime());
        rPo.setEveryMeetingEndTime(regularPO.getEveryMeetingEndTime());
        boardRoomApply.setRegularPO(rPo);
      } 
      boardRoomApply.setDepict(boardroomApplyPO.getDepict());
      boardRoomApply.setBoardroomCode(boardroomApplyPO.getBoardroomCode());
      boardRoomApply.setPersonNum(boardroomApplyPO.getPersonNum());
      boardRoomApply.setApplyOrgName(boardroomApplyPO.getApplyOrgName());
      boardRoomApply.setApplyOrg(boardroomApplyPO.getApplyOrg());
      boardRoomApply.setEmceeName(boardroomApplyPO.getEmceeName());
      boardRoomApply.setEmcee(boardroomApplyPO.getEmcee());
      boardRoomApply.setMotif(boardroomApplyPO.getMotif());
      boardRoomApply.setAttendeeLeader(boardroomApplyPO.getAttendeeLeader());
      boardRoomApply.setAttendeeLeaderId(boardroomApplyPO.getAttendeeLeaderId());
      boardRoomApply.setAttendee(boardroomApplyPO.getAttendee());
      boardRoomApply.setAttendeeEmpId(boardroomApplyPO.getAttendeeEmpId());
      boardRoomApply.setNonvoting(boardroomApplyPO.getNonvoting());
      boardRoomApply.setNonvotingEmpId(boardroomApplyPO.getNonvotingEmpId());
      boardRoomApply.setNotePerson(boardroomApplyPO.getNotePerson());
      boardRoomApply.setNotePersonId(boardroomApplyPO.getNotePersonId());
      boardRoomApply.setBoardLayout(boardroomApplyPO.getBoardLayout());
      this.session.delete("from com.js.oa.routine.boardroom.po.BoardroomMeetingTimePO po where po.apply.boardroomApplyId = " + boardroomApplyPO.getBoardroomApplyId());
      boardRoomApply.setMeetingTime(boardroomApplyPO.getMeetingTime());
      boardRoomApply.setFileNumber(boardroomApplyPO.getFileNumber());
      boardRoomApply.setSenderName(boardroomApplyPO.getSenderName());
      boardRoomApply.setStatus(boardroomApplyPO.getStatus());
      boardRoomApply.setBoardEquipment(boardroomApplyPO.getBoardEquipment());
      boardRoomApply.setPoints(boardroomApplyPO.getPoints());
      boardRoomApply.setLinkTelephone(boardroomApplyPO.getLinkTelephone());
      boardRoomApply.setApplyDate(boardroomApplyPO.getApplyDate());
      Set set = boardroomApplyPO.getBdRoomAppAccessory();
      HashSet hs = new HashSet();
      boardRoomApply.setBdRoomAppAccessory(hs);
      Iterator<BdroomAppAccessoryPO> iterator = set.iterator();
      BdroomAppAccessoryPO bdroomAppAccessoryPO = null;
      while (iterator != null && iterator.hasNext()) {
        bdroomAppAccessoryPO = iterator.next();
        bdroomAppAccessoryPO.setBoardRoomApply(boardRoomApply);
        boardRoomApply.getBdRoomAppAccessory().add(bdroomAppAccessoryPO);
        this.session.save(bdroomAppAccessoryPO);
      } 
      chuxiUser = String.valueOf(boardRoomApply.getEmcee()) + boardRoomApply.getAttendeeEmpId() + boardRoomApply.getAttendeeLeaderId() + 
        boardRoomApply.getNotePersonId() + "$" + boardRoomApply.getApplyEmp() + "$";
      liexiUser = boardRoomApply.getNonvotingEmpId();
      url = url.replace("@@boardroomApplyId@@", (CharSequence)boardRoomApply.getBoardroomApplyId())
        .replace("@@boardroomName@@", boardRoomApply.getBoardroom().getName());
      motif = boardRoomApply.getMotif();
      this.session.update(boardRoomApply);
      this.session.flush();
      result = true;
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    if (result && "modi".equals(flag))
      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        chuxiUser = chuxiUser.toLowerCase().replace("null", "").replace("$$", ",").replace("$", "");
        liexiUser = liexiUser.toLowerCase().replace("null", "").replace("$$", ",").replace("$", "");
        if (liexiUser.length() > 0)
          RemindUtil.sendMessageToUsers2("您列席的" + motif + "已被修改！", url, liexiUser, "Meeting", new Date(), 
              dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(0L), 1); 
        if (chuxiUser.length() > 0)
          RemindUtil.sendMessageToUsers2("您出席的" + motif + "已被修改！", url, chuxiUser, "Meeting", new Date(), 
              dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(0L), 1); 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return result;
  }
  
  public boolean deleteBoardroomApply(Long boardroomApplyId) throws HibernateException {
    return deleteBoardroomApply(boardroomApplyId, "");
  }
  
  public boolean deleteBoardroomApply(Long boardroomApplyId, String meetingTimeId) throws HibernateException {
    boolean result = false;
    String chuxiUser = "";
    String liexiUser = "";
    String url = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=@@boardroomApplyId@@&boardroomName=@@boardroomName@@&type=view&executeStatus=false";
    String motif = "";
    begin();
    try {
      if (this.session.load(BoardRoomApplyPO.class, boardroomApplyId) != null) {
        BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)this.session.load(BoardRoomApplyPO.class, boardroomApplyId);
        chuxiUser = String.valueOf(boardroomApplyPO.getEmcee()) + boardroomApplyPO.getAttendeeEmpId() + boardroomApplyPO.getAttendeeLeaderId() + 
          boardroomApplyPO.getNotePersonId() + "$" + boardroomApplyPO.getApplyEmp() + "$";
        liexiUser = boardroomApplyPO.getNonvotingEmpId();
        if (liexiUser == null)
          liexiUser = ""; 
        url = url.replace("@@boardroomApplyId@@", (CharSequence)boardroomApplyPO.getBoardroomApplyId())
          .replace("@@boardroomName@@", boardroomApplyPO.getBoardroom().getName());
        motif = boardroomApplyPO.getMotif();
        Connection conn = (new DataSourceBase()).getDataSource().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT id FROM OA_BOARDROOM_MEETINGTIME WHERE applyid=" + boardroomApplyId);
        int timeNum = 0;
        for (; rs.next(); timeNum++);
        rs.close();
        if (meetingTimeId.equals("") || timeNum == 1) {
          if (boardroomApplyPO != null) {
            this.session.delete(boardroomApplyPO);
            this.session.flush();
          } 
          stmt.execute("DELETE FROM JSDB.JSF_WORK WHERE WORKTABLE_ID=(select wf_immoform_id from jsf_immobilityform where wf_module_id=15)  AND WORKRECORD_ID = " + 
              boardroomApplyId);
        } else {
          rs = stmt.executeQuery("SELECT meetingtime,beginlong,endlong FROM OA_BOARDROOM_MEETINGTIME WHERE id=" + meetingTimeId);
          if (rs.next()) {
            String dateStr = (rs.getString(1) == null) ? "" : rs.getString(1).substring(0, 10);
            Long beginLong = Long.valueOf(rs.getLong(2));
            Long endLong = Long.valueOf(rs.getLong(3));
            SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
            motif = String.valueOf(motif) + "(" + dateStr + " " + hm.format(new Date(beginLong.longValue())) + "-" + hm.format(new Date(endLong.longValue())) + ")";
          } 
          stmt.execute("delete from OA_BOARDROOM_MEETINGTIME where id=" + meetingTimeId);
        } 
        stmt.close();
        conn.close();
      } 
      result = true;
    } catch (HibernateException e) {
      System.out.println("deleteBoardroomApply EJB Exception:" + 
          e.toString());
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    if (result)
      try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        chuxiUser = chuxiUser.toLowerCase().replace("null", "").replace("$$", ",").replace("$", "");
        liexiUser = liexiUser.toLowerCase().replace("null", "").replace("$$", ",").replace("$", "");
        if (liexiUser.length() > 0)
          RemindUtil.sendMessageToUsers2("您列席的" + motif + "已被删除！", url, liexiUser, "Meeting", new Date(), 
              dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(0L), 2); 
        if (chuxiUser.length() > 0)
          RemindUtil.sendMessageToUsers2("您出席的" + motif + "已被删除！", url, chuxiUser, "Meeting", new Date(), 
              dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(0L), 2); 
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return result;
  }
  
  public BoardRoomApplyPO getBoardRoomApplyPObyId(Long boardroomApplyId) throws HibernateException {
    begin();
    BoardRoomApplyPO boardRoomApplyPO = new BoardRoomApplyPO();
    try {
      boardRoomApplyPO = (BoardRoomApplyPO)this.session.load(BoardRoomApplyPO.class, boardroomApplyId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return boardRoomApplyPO;
  }
  
  public List lookStateBoardroomApply(Long boardroomId) throws HibernateException {
    List list = new ArrayList();
    Calendar now = Calendar.getInstance();
    Date nowDate = new Date();
    int beginYear = now.get(1);
    int beginMonth = now.get(2) + 1;
    int beginDate = now.get(5);
    String dataType = SystemCommon.getDatabaseType();
    try {
      begin();
      String hql = "";
      if (dataType.indexOf("mysql") >= 0) {
        hql = "select boardRoomApplyPO.destineDate,boardRoomApplyPO.startTime,boardRoomApplyPO.endTime from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO where (JSDB.FN_DATEPARTWEEK(JSDB.FN_STRTODATE('" + 
          beginYear + "/" + beginMonth + "/" + beginDate + "','S'))=JSDB.FN_DATEPARTWEEK(DESTINEDATE)) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) and boardRoomApplyPO.boardroom.boardroomId=" + 
          boardroomId;
      } else {
        hql = "select boardRoomApplyPO.destineDate,boardRoomApplyPO.startTime,boardRoomApplyPO.endTime from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO where (JSDB.FN_DATEPARTWEEK(JSDB.FN_STRTODATE('" + 
          beginYear + "/" + beginMonth + "/" + beginDate + "','S'))=JSDB.FN_DATEPARTWEEK(DESTINEDATE)) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) and boardRoomApplyPO.boardroom.boardroomId=" + 
          boardroomId;
      } 
      Query lookStateBoardroomApplyQuery = this.session.createQuery(hql);
      list = lookStateBoardroomApplyQuery.list();
    } catch (HibernateException e) {
      System.out.println("lookStateBoardroomApply EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public boolean approveBoardroomApply(Long boardroomApplyId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)this.session.load(
          BoardRoomApplyPO.class, boardroomApplyId);
      boardroomApplyPO.setStatus(new Integer(0));
      this.session.update(boardroomApplyPO);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("approveBoardroomApply EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean dissentBoardroomApply(Long boardroomApplyId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      BoardRoomApplyPO boardroomApplyPO = (BoardRoomApplyPO)this.session.load(
          BoardRoomApplyPO.class, boardroomApplyId);
      boardroomApplyPO.setStatus(new Integer(2));
      this.session.update(boardroomApplyPO);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("dissentBoardroomApply EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String isImpropriateTime(BoardRoomApplyPO boardroomApplyPO, Long boardroomId) throws HibernateException {
    String isImpropriateTime = "0";
    if (boardroomId.longValue() == 0L)
      return isImpropriateTime; 
    begin();
    try {
      Query query = null;
      BoardRoomPO po = (BoardRoomPO)this.session.load(BoardRoomPO.class, 
          boardroomId);
      String countView = "count(*)";
      if (po.getIsVideo().longValue() == 1L)
        countView = " sum(boardRoomApplyPO.points) "; 
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String destineDate = dateFormat.format(boardroomApplyPO.getDestineDate());
      String startTime = boardroomApplyPO.getStartTime();
      String endTime = boardroomApplyPO.getEndTime();
      StringBuffer sqlBuffer = new StringBuffer("");
      if (boardroomApplyPO.getBoardroomApplyId() != null && 
        !"".equals(boardroomApplyPO.getBoardroomApplyId())) {
        String dataType = SystemCommon.getDatabaseType();
        if (dataType.indexOf("mysql") >= 0) {
          sqlBuffer.append("select ").append(countView).append(" from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.meetingTime poo where poo.meetingDate = '")
            .append(destineDate)
            .append("' and ((poo.startTime>").append(startTime).append(" and poo.startTime<").append(endTime)
            .append(") or (poo.startTime<=").append(startTime).append(" and poo.endTime>=").append(endTime)
            .append(") or (poo.endTime>").append(startTime).append(" and poo.endTime<").append(endTime)
            .append(") or (poo.startTime>").append(startTime).append(" and poo.endTime<").append(endTime)
            .append(")) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) and boardRoomApplyPO.boardroom.boardroomId=")
            .append(boardroomId).append(" and boardRoomApplyPO.boardroomApplyId <>")
            .append(boardroomApplyPO.getBoardroomApplyId())
            .append(" and poo.status = 0 ");
        } else {
          sqlBuffer.append("select " + countView + " from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.meetingTime poo where poo.meetingDate = JSDB.FN_STRTODATE('" + 
              destineDate + 
              "','S') and ((poo.startTime>" + startTime + " and poo.startTime<" + endTime + 
              ") or (poo.startTime<=" + startTime + " and poo.endTime>=" + endTime + 
              ") or (poo.endTime>" + startTime + " and poo.endTime<" + endTime + 
              ") or (poo.startTime>" + startTime + " and poo.endTime<" + endTime + 
              ")) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) and boardRoomApplyPO.boardroom.boardroomId=" + 
              boardroomId + 
              " and boardRoomApplyPO.boardroomApplyId <>" + 
              boardroomApplyPO.getBoardroomApplyId() + 
              " and poo.status = 0 ");
        } 
        query = this.session.createQuery(sqlBuffer.toString());
      } else {
        String dataType = SystemCommon.getDatabaseType();
        String sql = "";
        if (dataType.indexOf("mysql") >= 0) {
          sqlBuffer.append("select " + countView + " from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.meetingTime poo where poo.meetingDate = '" + 
              destineDate + 
              "' and ((poo.startTime>" + startTime + " and poo.startTime<" + endTime + 
              ") or (poo.startTime<=" + startTime + " and poo.endTime>=" + endTime + 
              ") or (poo.endTime>" + startTime + " and poo.endTime<" + endTime + 
              ") or (poo.startTime>" + startTime + " and poo.endTime<" + endTime + 
              ")) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) and boardRoomApplyPO.boardroom.boardroomId=" + 
              boardroomId + 
              " and poo.status = 0 ");
        } else {
          sqlBuffer.append("select " + countView + " from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.meetingTime poo where poo.meetingDate = JSDB.FN_STRTODATE('" + 
              destineDate + 
              "','S') and ((poo.startTime>" + startTime + " and poo.startTime<" + endTime + 
              ") or (poo.startTime<=" + startTime + " and poo.endTime>=" + endTime + 
              ") or (poo.endTime>" + startTime + " and poo.endTime<" + endTime + 
              ") or (poo.startTime>" + startTime + " and poo.endTime<" + endTime + 
              ")) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) and boardRoomApplyPO.boardroom.boardroomId=" + 
              boardroomId + 
              " and poo.status = 0 ");
        } 
        query = this.session.createQuery(sqlBuffer.toString());
      } 
      List<E> list = query.list();
      if (list != null && list.get(0) != null)
        isImpropriateTime = list.get(0).toString(); 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
    } 
    return isImpropriateTime;
  }
  
  public String maintenanceBoardRoom(String scopeWhere) throws HibernateException {
    String maintenanceBoardRoomIds = "";
    begin();
    try {
      Query query = this.session.createQuery("select boardRoomPO.boardroomId from com.js.oa.routine.boardroom.po.BoardRoomPO boardRoomPO where " + 
          scopeWhere);
      List<String> list = query.list();
      for (int i = 0; i < list.size(); i++)
        maintenanceBoardRoomIds = String.valueOf(maintenanceBoardRoomIds) + list.get(i) + 
          ","; 
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return maintenanceBoardRoomIds;
  }
  
  public boolean addBoardroomAppType(BoardRoomApplyTypePO boardRoomAppTypePO) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery("select po.bdroomAppTypeId from com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO po  where po.type='" + 
          
          boardRoomAppTypePO.getType() + 
          "' and po.domainId=" + 
          boardRoomAppTypePO.getDomainId());
      if (query.iterate().hasNext()) {
        result = false;
      } else {
        this.session.save(boardRoomAppTypePO);
        this.session.flush();
        result = true;
      } 
    } catch (HibernateException e) {
      System.out.println("addBoardroomAppType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean deleteBoardroomAppType(Long bdroomAppTypeId) throws HibernateException {
    boolean result = false;
    begin();
    try {
      BoardRoomApplyTypePO boardRoomAppTypePO = 
        (BoardRoomApplyTypePO)this.session.load(BoardRoomApplyTypePO.class, 
          bdroomAppTypeId);
      this.session.delete(boardRoomAppTypePO);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("deleteBoardroomAppType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public void boardroomAppTypeDefault(String typeId) {
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      String sql = "UPDATE OA_BDROOMAPPTYPE SET typeDefault='0'";
      base.executeUpdate(sql);
      sql = "UPDATE OA_BDROOMAPPTYPE SET typeDefault='1' where bdroomapptypeId=" + typeId;
      base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public boolean deleteBatchBoardroomAppType(String bdroomAppTypeIds) throws HibernateException {
    boolean result = false;
    begin();
    try {
      String[] idsArr = bdroomAppTypeIds.split(",");
      for (int i = 0; i < idsArr.length; i++) {
        BoardRoomApplyTypePO boardRoomAppTypePO = 
          (BoardRoomApplyTypePO)this.session.load(BoardRoomApplyTypePO.class, 
            Long.valueOf(idsArr[i]));
        this.session.delete(boardRoomAppTypePO);
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("deleteBatchBoardroomAppType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public BoardRoomApplyTypePO selectBoardroomAppType(Long bdroomAppTypeId) throws HibernateException {
    BoardRoomApplyTypePO boardRoomAppTypePO = null;
    begin();
    try {
      boardRoomAppTypePO = (BoardRoomApplyTypePO)this.session.load(
          BoardRoomApplyTypePO.class, bdroomAppTypeId);
    } catch (HibernateException e) {
      System.out.println("selectBoardroomAppType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return boardRoomAppTypePO;
  }
  
  public boolean modiBoardroomAppType(BoardRoomApplyTypePO boardRoomAppTypePO) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery("select po.bdroomAppTypeId from com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO po  where po.type='" + 
          
          boardRoomAppTypePO.getType() + 
          "' and po.domainId=" + 
          boardRoomAppTypePO.getDomainId() + 
          " and po.bdroomAppTypeId<>" + 
          boardRoomAppTypePO
          .getBdroomAppTypeId());
      if (query.iterate().hasNext()) {
        result = false;
      } else {
        BoardRoomApplyTypePO po = (BoardRoomApplyTypePO)this.session.load(
            BoardRoomApplyTypePO.class, 
            boardRoomAppTypePO.getBdroomAppTypeId());
        po.setType(boardRoomAppTypePO.getType());
        po.setApplyType(boardRoomAppTypePO.getApplyType());
        po.setUsedScopeId(boardRoomAppTypePO.getUsedScopeId());
        po.setUsedScopeOrgId(boardRoomAppTypePO.getUsedScopeOrgId());
        po.setUsedScopeGroupId(boardRoomAppTypePO.getUsedScopeGroupId());
        po.setUsedScopeName(boardRoomAppTypePO.getUsedScopeName());
        po.setApplyFirst(boardRoomAppTypePO.getApplyFirst());
        this.session.update(po);
        this.session.flush();
        result = true;
      } 
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List selectBoardroomApplyType(String domainId) throws HibernateException {
    List list = new ArrayList();
    try {
      begin();
      Query selectBoardroomApplyTypeQuery = this.session.createQuery("select po.bdroomAppTypeId,po.type,po.applyType,po.typeDefault,po.usedScopeId,po.usedScopeOrgId,po.usedScopeGroupId,po.applyFirst from com.js.oa.routine.boardroom.po.BoardRoomApplyTypePO po where po.domainId=" + 
          
          domainId);
      list = selectBoardroomApplyTypeQuery.list();
    } catch (HibernateException e) {
      System.out.println("selectBoardroomApplyType EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public Map getWeekMeeting(Long userId, Calendar nowDate, String domainId, String orgId) throws Exception {
    begin();
    Map<Object, Object> weekMeeting = new HashMap<Object, Object>();
    try {
      StringTokenizer stt = new StringTokenizer(orgId, "$");
      String[] orgIdArr = new String[stt.countTokens()];
      int i = 0;
      while (stt.hasMoreTokens()) {
        orgIdArr[i] = stt.nextToken();
        i++;
      } 
      String databaseType = 
        SystemCommon.getDatabaseType();
      String viewSql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime,poo.id,poo.sortNum";
      String fromSql = "com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo";
      String whereSql = "";
      int dayOfWeek = nowDate.get(7);
      Calendar monday = Calendar.getInstance();
      monday.setTime(nowDate.getTime());
      monday.add(5, 2 - dayOfWeek);
      String mondaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        mondaySql = "where poo.status=0 and poo.meetingDate between '" + 
          monday.get(1) + "-" + (
          monday.get(2) + 1) + "-" + 
          monday.get(5) + 
          " 00:00:00' and '" + 
          monday.get(1) + "-" + (
          monday.get(2) + 1) + "-" + 
          monday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        mondaySql = 
          "where poo.status=0 and poo.meetingDate between JSDB.FN_STRTODATE('" + 
          monday.get(1) + "-" + (
          monday.get(2) + 1) + "-" + 
          monday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          monday.get(1) + "-" + (
          monday.get(2) + 1) + "-" + 
          monday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int p = 0; i < orgIdArr.length; p++) {
        if (orgIdArr[p] != null && orgIdArr[p].length() > 1)
          mondaySql = String.valueOf(mondaySql) + " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[p] + "*%' "; 
        if (p + 1 < orgIdArr.length && orgIdArr[p] != null && 
          orgIdArr[p].length() > 1)
          mondaySql = String.valueOf(mondaySql) + " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[p + 1] + "*%' "; 
      } 
      mondaySql = String.valueOf(mondaySql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%' or boardRoomApplyPO.notePersonId like '%$" + userId + "$%') " + 
        "and boardRoomApplyPO.status=0 and boardRoomPO.domainId=" + 
        domainId + " order by poo.startTime desc";
      Query mquery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + mondaySql);
      weekMeeting.put("monday", mquery.list());
      Calendar tuesday = Calendar.getInstance();
      tuesday.setTime(nowDate.getTime());
      tuesday.add(5, 3 - dayOfWeek);
      String tuesdaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        tuesdaySql = "where poo.meetingDate between '" + 
          tuesday.get(1) + "-" + (
          tuesday.get(2) + 1) + "-" + 
          tuesday.get(5) + 
          " 00:00:00' and '" + 
          tuesday.get(1) + "-" + (
          tuesday.get(2) + 1) + "-" + 
          tuesday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        tuesdaySql = 
          "where poo.meetingDate between JSDB.FN_STRTODATE('" + 
          tuesday.get(1) + "-" + (
          tuesday.get(2) + 1) + "-" + 
          tuesday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          tuesday.get(1) + "-" + (
          tuesday.get(2) + 1) + "-" + 
          tuesday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int j = 0; i < orgIdArr.length; j++) {
        if (orgIdArr[j] != null && orgIdArr[j].length() > 1)
          tuesdaySql = String.valueOf(tuesdaySql) + " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[j] + "*%' "; 
        if (j + 1 < orgIdArr.length && orgIdArr[j] != null && 
          orgIdArr[j].length() > 1)
          tuesdaySql = String.valueOf(tuesdaySql) + " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[j + 1] + "*%' "; 
      } 
      tuesdaySql = String.valueOf(tuesdaySql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%') and boardRoomApplyPO.status =0 and boardRoomPO.domainId=" + 
        domainId + "  order by poo.startTime desc";
      Query tuquery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + tuesdaySql);
      weekMeeting.put("tuesday", tuquery.list());
      Calendar wednesday = Calendar.getInstance();
      wednesday.setTime(nowDate.getTime());
      wednesday.add(5, 4 - dayOfWeek);
      String wednesdaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        wednesdaySql = "where poo.meetingDate between '" + 
          wednesday.get(1) + "-" + (
          wednesday.get(2) + 1) + "-" + 
          wednesday.get(5) + 
          " 00:00:00' and '" + 
          wednesday.get(1) + "-" + (
          wednesday.get(2) + 1) + "-" + 
          wednesday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        wednesdaySql = 
          "where poo.meetingDate between JSDB.FN_STRTODATE('" + 
          wednesday.get(1) + "-" + (
          wednesday.get(2) + 1) + "-" + 
          wednesday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          wednesday.get(1) + "-" + (
          wednesday.get(2) + 1) + "-" + 
          wednesday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int k = 0; i < orgIdArr.length; k++) {
        if (orgIdArr[k] != null && orgIdArr[k].length() > 1)
          wednesdaySql = String.valueOf(wednesdaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[k] + "*%' "; 
        if (k + 1 < orgIdArr.length && orgIdArr[k] != null && 
          orgIdArr[k].length() > 1)
          wednesdaySql = String.valueOf(wednesdaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[k + 1] + "*%' "; 
      } 
      wednesdaySql = String.valueOf(wednesdaySql) + "or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%') and boardRoomApplyPO.status =0 and boardRoomPO.domainId=" + 
        domainId + "  order by poo.startTime desc";
      Query wquery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + wednesdaySql);
      weekMeeting.put("wednesday", wquery.list());
      Calendar thursday = Calendar.getInstance();
      thursday.setTime(nowDate.getTime());
      thursday.add(5, 5 - dayOfWeek);
      String thursdaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        thursdaySql = "where poo.meetingDate between '" + 
          thursday.get(1) + "-" + (
          thursday.get(2) + 1) + "-" + 
          thursday.get(5) + 
          " 00:00:00' and '" + 
          thursday.get(1) + "-" + (
          thursday.get(2) + 1) + "-" + 
          thursday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        thursdaySql = 
          "where poo.meetingDate between JSDB.FN_STRTODATE('" + 
          thursday.get(1) + "-" + (
          thursday.get(2) + 1) + "-" + 
          thursday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          thursday.get(1) + "-" + (
          thursday.get(2) + 1) + "-" + 
          thursday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int m = 0; i < orgIdArr.length; m++) {
        if (orgIdArr[m] != null && orgIdArr[m].length() > 1)
          thursdaySql = String.valueOf(thursdaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[m] + "*%' "; 
        if (m + 1 < orgIdArr.length && orgIdArr[m] != null && 
          orgIdArr[m].length() > 1)
          thursdaySql = String.valueOf(thursdaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[m + 1] + "*%' "; 
      } 
      thursdaySql = String.valueOf(thursdaySql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%') and boardRoomApplyPO.status =0 and boardRoomPO.domainId=" + 
        domainId + "  order by poo.startTime desc";
      Query thquery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + thursdaySql);
      weekMeeting.put("thursday", thquery.list());
      Calendar friday = Calendar.getInstance();
      friday.setTime(nowDate.getTime());
      friday.add(5, 6 - dayOfWeek);
      String fridaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        fridaySql = "where poo.meetingDate between '" + 
          friday.get(1) + "-" + (
          friday.get(2) + 1) + "-" + 
          friday.get(5) + 
          " 00:00:00' and '" + 
          friday.get(1) + "-" + (
          friday.get(2) + 1) + "-" + 
          friday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        fridaySql = 
          "where poo.meetingDate between JSDB.FN_STRTODATE('" + 
          friday.get(1) + "-" + (
          friday.get(2) + 1) + "-" + 
          friday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          friday.get(1) + "-" + (
          friday.get(2) + 1) + "-" + 
          friday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int n = 0; i < orgIdArr.length; n++) {
        if (orgIdArr[n] != null && orgIdArr[n].length() > 1)
          fridaySql = String.valueOf(fridaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[n] + "*%' "; 
        if (n + 1 < orgIdArr.length && orgIdArr[n] != null && 
          orgIdArr[n].length() > 1)
          fridaySql = String.valueOf(fridaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[n + 1] + "*%' "; 
      } 
      fridaySql = String.valueOf(fridaySql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%') and boardRoomApplyPO.status =0 and boardRoomPO.domainId=" + 
        domainId + "  order by poo.startTime desc";
      Query fquery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + fridaySql);
      weekMeeting.put("friday", fquery.list());
      Calendar saturday = Calendar.getInstance();
      saturday.setTime(nowDate.getTime());
      saturday.add(5, 7 - dayOfWeek);
      String saturdaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        saturdaySql = "where poo.meetingDate between '" + 
          saturday.get(1) + "-" + (
          saturday.get(2) + 1) + "-" + 
          saturday.get(5) + 
          " 00:00:00' and '" + 
          saturday.get(1) + "-" + (
          saturday.get(2) + 1) + "-" + 
          saturday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        saturdaySql = 
          "where poo.meetingDate between JSDB.FN_STRTODATE('" + 
          saturday.get(1) + "-" + (
          saturday.get(2) + 1) + "-" + 
          saturday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          saturday.get(1) + "-" + (
          saturday.get(2) + 1) + "-" + 
          saturday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int i1 = 0; i < orgIdArr.length; i1++) {
        if (orgIdArr[i1] != null && orgIdArr[i1].length() > 1)
          saturdaySql = String.valueOf(saturdaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[i1] + "*%' "; 
        if (i1 + 1 < orgIdArr.length && orgIdArr[i1] != null && 
          orgIdArr[i1].length() > 1)
          saturdaySql = String.valueOf(saturdaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[i1 + 1] + "*%' "; 
      } 
      saturdaySql = String.valueOf(saturdaySql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%') and boardRoomApplyPO.status =0 and boardRoomPO.domainId=" + 
        domainId + "  order by poo.startTime desc";
      Query squery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + saturdaySql);
      weekMeeting.put("saturday", squery.list());
      Calendar sunday = Calendar.getInstance();
      sunday.setTime(nowDate.getTime());
      sunday.add(5, 8 - dayOfWeek);
      String sundaySql = "";
      if (databaseType.indexOf("mysql") >= 0) {
        sundaySql = "where poo.meetingDate between '" + 
          sunday.get(1) + "-" + (
          sunday.get(2) + 1) + "-" + 
          sunday.get(5) + 
          " 00:00:00' and '" + 
          sunday.get(1) + "-" + (
          sunday.get(2) + 1) + "-" + 
          sunday.get(5) + 
          " 23:59:59' and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } else {
        sundaySql = 
          "where poo.meetingDate between JSDB.FN_STRTODATE('" + 
          sunday.get(1) + "-" + (
          sunday.get(2) + 1) + "-" + 
          sunday.get(5) + 
          " 00:00:00','L') and JSDB.FN_STRTODATE('" + 
          sunday.get(1) + "-" + (
          sunday.get(2) + 1) + "-" + 
          sunday.get(5) + 
          " 23:59:59','L') and (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%" + 
          userId + "%' ";
      } 
      for (int i2 = 0; i < orgIdArr.length; i2++) {
        if (orgIdArr[i2] != null && orgIdArr[i2].length() > 1)
          sundaySql = String.valueOf(sundaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[i2] + "*%' "; 
        if (i2 + 1 < orgIdArr.length && orgIdArr[i2] != null && 
          orgIdArr[i2].length() > 1)
          sundaySql = String.valueOf(sundaySql) + 
            " or boardRoomApplyPO.attendeeEmpId like '%*" + 
            orgIdArr[i2 + 1] + "*%' "; 
      } 
      sundaySql = String.valueOf(sundaySql) + " or boardRoomApplyPO.attendeeLeaderId like '%$" + 
        userId + "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
        userId + 
        "$%') and boardRoomApplyPO.status =0 and boardRoomPO.domainId=" + 
        domainId + "  order by poo.startTime desc";
      Query suquery = this.session.createQuery(String.valueOf(viewSql) + " from " + fromSql + 
          " " + sundaySql);
      weekMeeting.put("sunday", suquery.list());
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return weekMeeting;
  }
  
  public List getTodayMeeting(Long userId) throws HibernateException {
    List list = new ArrayList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String destineDate = dateFormat.format(new Date());
    String dataType = SystemCommon.getDatabaseType();
    try {
      begin();
      String hql = "";
      if (dataType.indexOf("mysql") >= 0) {
        hql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo where (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeLeaderId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.nonvotingEmpId like '%" + 
          userId + "%') and boardRoomApplyPO.status =0 and poo.meetingDate = '" + 
          destineDate + 
          "' order by poo.startTime";
      } else {
        hql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo where (boardRoomApplyPO.emcee like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeEmpId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeLeaderId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.nonvotingEmpId like '%" + 
          userId + "%') and boardRoomApplyPO.status =0 and poo.meetingDate = JSDB.FN_STRTODATE('" + 
          destineDate + 
          "','S') order by poo.startTime";
      } 
      Query query = this.session.createQuery(hql);
      list = query.list();
    } catch (HibernateException e) {
      System.out.println("getTodayMeeting EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public List getNewMeeting(Long userId) throws HibernateException {
    List list = new ArrayList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String destineDate = dateFormat.format(new Date());
    try {
      begin();
      String dataType = 
        SystemCommon.getDatabaseType();
      String sql = "";
      if (dataType.indexOf("mysql") >= 0) {
        sql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo where (boardRoomApplyPO.emcee like '%$" + 
          userId + "$%' or boardRoomApplyPO.attendeeEmpId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeLeaderId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.nonvotingEmpId like '%$" + 
          userId + 
          "$%') and boardRoomApplyPO.status =0 and poo.meetingDate >='" + 
          destineDate + "' order by poo.meetingDate,poo.startTime";
      } else {
        sql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif,boardRoomPO.name,boardRoomPO.location,boardRoomApplyPO.emceeName,poo.meetingDate,poo.startTime,poo.endTime from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.boardroom boardRoomPO join boardRoomApplyPO.meetingTime poo where (boardRoomApplyPO.emcee like '%$" + 
          userId + "$%' or boardRoomApplyPO.attendeeEmpId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.attendeeLeaderId like '%$" + 
          userId + 
          "$%' or boardRoomApplyPO.nonvotingEmpId like '$%" + 
          userId + "$%') and boardRoomApplyPO.status =0 and poo.meetingDate >= JSDB.FN_STRTODATE('" + 
          destineDate + 
          "','S') order by poo.startTime";
      } 
      Query query = this.session.createQuery(sql);
      query.setFirstResult(0);
      query.setMaxResults(20);
      list = query.list();
    } catch (HibernateException e) {
      System.out.println("getNewMeeting EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public String getBoardRoomInfo(String voitureId, String searchDate) throws Exception {
    String returnValue = "";
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    String[][] info = getWeekRoomShow(voitureId, ymdFormat.parse(searchDate), 1, "1", "1", SystemCommon.getMeetingType()).get(0);
    if (info.length != 1 || !"".equals(info[0][0]))
      for (int i = 0; i < info.length; i++) {
        String[] timeStr = info[i][0].split("-");
        if (timeStr[0].startsWith("0"))
          timeStr[0] = timeStr[0].substring(1); 
        if (timeStr[1].startsWith("0"))
          timeStr[1] = timeStr[1].substring(1); 
        returnValue = String.valueOf(returnValue) + timeStr[0] + "-" + timeStr[1] + "||主题:" + info[i][1] + " 申请人:" + info[i][2] + " 联系电话：" + info[i][9] + "&" + info[i][8] + ";";
      }  
    return returnValue;
  }
  
  public String[] getEquipmentInfo(String voitureId, String searchDate) throws Exception {
    List list = new ArrayList();
    String[] voiturePlace = new String[288];
    for (int i = 0; i < voiturePlace.length; i++)
      voiturePlace[i] = "false"; 
    Iterator<Object[]> iter = null;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
    String dataType = SystemCommon.getDatabaseType();
    begin();
    try {
      Query query = null;
      if (dataType.indexOf("mysql") >= 0) {
        query = this.session.createQuery(
            "select po.startDate,po.endDate,po.startTime,po.endTime from com.js.oa.routine.boardroom.po.EquipmentApplyPO po where po.equipment.equipmentId =" + 
            voitureId + " and (timestamp('" + searchDate + "') between po.startDate and po.endDate) " + 
            " and po.status=2");
      } else {
        query = this.session.createQuery(
            "select po.startDate,po.endDate,po.startTime,po.endTime from com.js.oa.routine.boardroom.po.EquipmentApplyPO po where po.equipment.equipmentId =" + 
            voitureId + " and (JSDB.FN_STRTODATE('" + 
            searchDate + 
            "','S') between po.startDate and po.endDate) " + 
            " and po.status=2");
      } 
      list = query.list();
      iter = list.iterator();
      while (iter.hasNext()) {
        Object[] obj = iter.next();
        if (formatter.parse(searchDate).after(formatter.parse(obj[0]
              .toString())))
          if (formatter.parse(searchDate).before(formatter.parse(obj[
                  1]
                .toString()))) {
            for (int k = 0; k < voiturePlace.length; k++)
              voiturePlace[k] = "true"; 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                0].toString())))
          if (!formatter.parse(searchDate).equals(
              formatter.parse(obj[1].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 11; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                if (minute.equals("00")) {
                  minute = "0" + (Integer.parseInt(minute) + 5);
                } else {
                  int n = Integer.parseInt(minute) + 5;
                } 
                if (!formatter1.parse(myTime).before(formatter1
                    .parse(castBoardRoomTime(obj[2].toString()))))
                  voiturePlace[12 * k + m] = "true"; 
              } 
            } 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                1].toString())))
          if (!formatter.parse(searchDate).equals(
              formatter.parse(obj[
                  0].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 11; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                if (minute.equals("00")) {
                  minute = "0" + (Integer.parseInt(minute) + 5);
                } else {
                  int n = Integer.parseInt(minute) + 5;
                } 
                if (!formatter1.parse(myTime).after(formatter1
                    .parse(castBoardRoomTime(obj[3].toString()))))
                  if (!formatter1.parse(myTime).equals(formatter1
                      .parse(castBoardRoomTime(obj[3].toString()))))
                    voiturePlace[12 * k + m] = "true";  
              } 
            } 
            continue;
          }  
        if (formatter.parse(searchDate).equals(formatter.parse(
              obj[
                1].toString())))
          if (formatter.parse(searchDate).equals(formatter.parse(
                obj[
                  0].toString()))) {
            String minute = "00";
            for (int k = 0; k < 24; k++) {
              minute = "00";
              for (int m = 0; m <= 11; m++) {
                String myTime = String.valueOf(k) + ":" + minute;
                if (minute.equals("00")) {
                  minute = "0" + (Integer.parseInt(minute) + 5);
                } else {
                  int n = Integer.parseInt(minute) + 5;
                } 
                if (!formatter1.parse(myTime).before(formatter1
                    .parse(castBoardRoomTime(obj[2].toString()))))
                  if (!formatter1.parse(myTime).after(formatter1
                      .parse(castBoardRoomTime(obj[3].toString()))))
                    if (!formatter1.parse(myTime).equals(formatter1
                        .parse(castBoardRoomTime(obj[3].toString()))))
                      voiturePlace[12 * k + m] = "true";   
              } 
            } 
            continue;
          }  
        for (int j = 0; j < voiturePlace.length; j++)
          voiturePlace[j] = "false"; 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return voiturePlace;
  }
  
  private String castBoardRoomTime(String time) {
    if (time != null && time.length() > 0) {
      String retStr = "";
      int myTime = Integer.parseInt(time);
      retStr = String.valueOf(myTime / 3600) + ":";
      if (myTime % 3600 / 60 < 10) {
        retStr = String.valueOf(retStr) + "0" + (myTime % 3600 / 60);
      } else {
        retStr = String.valueOf(retStr) + (myTime % 3600 / 60);
      } 
      return retStr;
    } 
    return "";
  }
  
  public void updateBoardroomCancel(String workID) {
    Connection conn = null;
    Statement stmt = null;
    Statement stmt_update = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      stmt_update = conn.createStatement();
      stmt.executeUpdate("update oa_boardroomapply set status=-2 where boardroomapplyid=(select workrecord_id from jsf_work where wf_work_id='" + workID + "')");
      stmt_update.executeUpdate("update oa_boardroom_meetingtime set status=-2 where applyid=(select workrecord_id from jsf_work where wf_work_id='" + workID + "')");
      conn.commit();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        stmt.close();
        stmt_update.close();
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public List selectBoardroomByConditions(String userId, String personNum, String boardtime, String startTime, String endTime, String equipment, String domainId, String where) throws HibernateException {
    List list = new ArrayList();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String dataType = SystemCommon.getDatabaseType();
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String orgIds = "";
      String groupIds = "";
      rs = stmt.executeQuery("select org_id from ORG_ORGANIZATION_USER where emp_id =" + userId);
      if (rs.next())
        orgIds = String.valueOf(orgIds) + "*" + rs.getString(1) + "*"; 
      rs = null;
      rs = stmt.executeQuery("select SIDELINEORG from org_employee where emp_id =" + userId);
      if (rs.next() && 
        rs.getString(1) != null && rs.getString(1).length() > 0)
        orgIds = String.valueOf(orgIds) + rs.getString(1); 
      String org_sql = "";
      if (orgIds.length() > 0) {
        String[] tt = orgIds.substring(1, orgIds.length() - 1).split("\\*\\*");
        for (int i = 0; i < tt.length; i++)
          org_sql = String.valueOf(org_sql) + " or po.scopeOrg like '%*" + tt[i] + "*%' "; 
      } 
      rs = null;
      rs = stmt.executeQuery("select group_id from ORG_USER_GROUP where emp_id =" + userId);
      while (rs.next())
        groupIds = String.valueOf(groupIds) + "@" + rs.getString(1) + "@"; 
      String group_sql = "";
      if (groupIds.length() > 0) {
        String[] tt = groupIds.substring(1, groupIds.length() - 1).split("\\@\\@");
        for (int i = 0; i < tt.length; i++)
          group_sql = String.valueOf(group_sql) + " or po.scopeGroup like '%@" + tt[i] + "@%' "; 
      } 
      String sql = "";
      if (dataType.indexOf("mysql") >= 0) {
        sql = "select distinct boardRoomApplyPO.boardroom.boardroomId from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.meetingTime poo where poo.meetingDate = '" + 
          boardtime + 
          "' and ((poo.startTime<=" + 
          startTime + 
          " and poo.endTime>" + 
          startTime + 
          ") or (poo.startTime<" + 
          endTime + 
          " and poo.endTime>=" + 
          endTime + 
          ") or (poo.startTime>=" + 
          startTime + 
          " and poo.endTime<=" + 
          startTime + 
          ")) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) ";
      } else {
        sql = "select distinct boardRoomApplyPO.boardroom.boardroomId from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO join boardRoomApplyPO.meetingTime poo where poo.meetingDate = JSDB.FN_STRTODATE('" + 
          boardtime + 
          "','S') and ((poo.startTime<=" + 
          startTime + 
          " and poo.endTime>" + 
          startTime + 
          ") or (poo.startTime<" + 
          endTime + 
          " and poo.endTime>=" + 
          endTime + 
          ") or (poo.startTime>=" + 
          startTime + 
          " and poo.endTime<=" + 
          startTime + 
          ")) and (boardRoomApplyPO.status = 0 or boardRoomApplyPO.status = 1) ";
      } 
      Query query0 = this.session.createQuery(sql);
      List list0 = query0.list();
      String roomIds = "-1";
      if (list0 != null && list0.size() > 0)
        for (int i = 0; i < list0.size(); i++) {
          Object obj = list0.get(i);
          roomIds = String.valueOf(roomIds) + "," + obj;
        }  
      String hql = "select distinct po.boardroomId, po.name ";
      hql = String.valueOf(hql) + " from com.js.oa.routine.boardroom.po.BoardRoomPO po left join po.boardRoomEquipment poo left join po.boardRoomApply pooo ";
      hql = String.valueOf(hql) + where;
      if (personNum != null && !"".equals(personNum) && 
        !"null".equals(personNum))
        hql = String.valueOf(hql) + " and po.capacitance >= " + personNum + " "; 
      if (equipment != null && !"".equals(equipment) && 
        !"null".equals(equipment)) {
        String[] e = equipment.split(",");
        hql = String.valueOf(hql) + " and ( 1!=1 ";
        for (int i = 0; i < e.length; i++) {
          if (!"".equals(e[i]))
            hql = String.valueOf(hql) + " or poo.equName like '%" + e[i] + "%' "; 
        } 
        hql = String.valueOf(hql) + ")";
      } 
      hql = String.valueOf(hql) + " and po.boardroomId not in (" + roomIds + ") ";
      hql = String.valueOf(hql) + " order by po.boardroomId desc ";
      Query query = this.session.createQuery(hql);
      list = query.list();
    } catch (HibernateException e) {
      System.out.println("selectBoardroomByConditions EJB Exception:" + 
          e.toString());
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e) {
        e.printStackTrace();
      } 
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return list;
  }
  
  public boolean addBoardroomPersons(BoardroomPersons po) throws HibernateException {
    boolean result = false;
    begin();
    try {
      if ("100000".equals(po.getStatus())) {
        Query query = this.session.createQuery("select po from com.js.oa.routine.boardroom.po.BoardroomPersons po where po.empId=" + 
            po.getEmpId() + 
            " and po.apply.boardroomApplyId=" + 
            po.getApply()
            .getBoardroomApplyId() + 
            " and po.status='100000'");
        List list = query.list();
        if (list == null || list.size() == 0)
          this.session.save(po); 
      } else {
        this.session.save(po);
      } 
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("addBoardroomPersons EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean modityBoardroomPersons(BoardroomPersons po) throws HibernateException {
    boolean result = false;
    begin();
    try {
      this.session.update(po);
      this.session.flush();
      result = true;
    } catch (HibernateException e) {
      System.out.println("addBoardroomPersons EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public boolean modifyBoardroomMeetingTimePO(String meetingTimeId, String boardRoomApplyId, Integer endTime) throws HibernateException {
    boolean result = false;
    begin();
    try {
      Query query = this.session.createQuery(
          "select po from com.js.oa.routine.boardroom.po.BoardroomMeetingTimePO po  where po.id=" + 
          
          meetingTimeId + 
          " and po.apply.boardroomApplyId=" + 
          boardRoomApplyId + 
          " and po.status = 0");
      List<BoardroomMeetingTimePO> list = query.list();
      if (list != null && list.size() > 0) {
        BoardroomMeetingTimePO po = list.get(0);
        po.setEndTime(endTime.toString());
        po.setStatus(new Integer(1));
        this.session.update(po);
        this.session.flush();
        result = true;
      } 
    } catch (HibernateException e) {
      System.out.println(e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String[][] getBoardRoomInfo2(String voitureId, String searchDate, Integer type) throws Exception {
    String[][] ret = (String[][])null;
    String str = " and poo.startTime < 43200";
    if (type.intValue() == 1)
      str = " and (poo.startTime >= 43200 or (poo.startTime < 43200 and poo.endTime > 43200))"; 
    begin();
    String dataType = SystemCommon.getDatabaseType();
    try {
      String hql = "";
      if (dataType.indexOf("mysql") >= 0) {
        hql = "select distinct poo.meetingDate,po.motif,poo.startTime,poo.endTime,po.applyEmpName from com.js.oa.routine.boardroom.po.BoardRoomApplyPO po join po.meetingTime poo where po.boardroom.boardroomId =" + 
          voitureId + " and (timestamp('" + 
          searchDate + 
          "','S') between poo.meetingDate and poo.meetingDate) " + 
          " and poo.status=0 " + str + 
          " and po.status=0 order by poo.startTime";
      } else {
        hql = "select distinct poo.meetingDate,po.motif,poo.startTime,poo.endTime,po.applyEmpName from com.js.oa.routine.boardroom.po.BoardRoomApplyPO po join po.meetingTime poo where po.boardroom.boardroomId =" + 
          voitureId + " and (JSDB.FN_STRTODATE('" + 
          searchDate + 
          "','S') between poo.meetingDate and poo.meetingDate) " + 
          " and poo.status=0 " + str + 
          " and po.status=0 order by poo.startTime";
      } 
      Query query = this.session.createQuery(hql);
      List list = query.list();
      Iterator<Object[]> iter = list.iterator();
      if (list != null && list.size() > 0) {
        ret = new String[list.size()][4];
      } else {
        return null;
      } 
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    this.session.close();
    this.session = null;
    return ret;
  }
  
  public String getOrgIdsByUserId(String userId) throws Exception {
    String ret = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String orgIds = "";
      rs = stmt.executeQuery(
          "select orguser.org_id,org.orgidstring from ORG_ORGANIZATION_USER orguser left join org_ORGANIZATION org on orguser.org_id=org.org_id  where emp_id =" + userId);
      if (rs.next()) {
        orgIds = String.valueOf(orgIds) + "*" + rs.getString(1) + "*";
        String orgIdString = rs.getString(2);
        orgIdString = orgIdString.substring(0, orgIdString.length() - 1);
        String[] orgIdArray = orgIdString.split("\\$");
        for (int i = 0; i < orgIdArray.length; i++) {
          String orgIdPattern = orgIdArray[i];
          if (!orgIdPattern.startsWith("_"))
            orgIds = String.valueOf(orgIds) + "*" + orgIdPattern + "*"; 
        } 
      } 
      rs = null;
      rs = stmt.executeQuery(
          "select SIDELINEORG from org_employee where emp_id =" + userId);
      if (rs.next() && 
        rs.getString(1) != null && rs.getString(1).length() > 0)
        orgIds = String.valueOf(orgIds) + rs.getString(1); 
      ret = orgIds;
    } catch (Exception e) {
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ret;
  }
  
  public String getGroupIdsByUserId(String userId) throws Exception {
    String ret = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      begin();
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      String groupIds = "";
      rs = stmt.executeQuery(
          "select group_id from ORG_USER_GROUP where emp_id =" + 
          userId);
      while (rs.next())
        groupIds = String.valueOf(groupIds) + "@" + rs.getString(1) + "@"; 
      ret = groupIds;
    } catch (Exception e) {
      throw e;
    } finally {
      if (rs != null)
        rs.close(); 
      if (stmt != null)
        stmt.close(); 
      if (conn != null)
        conn.close(); 
      this.session.close();
      this.session = null;
      this.transaction = null;
    } 
    return ret;
  }
  
  public List getExecuteStatusList(Long applyId, Long meetingId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po from com.js.oa.routine.boardroom.po.BoardroomExecuteStatusPO po where po.applyId=" + applyId + " and po.meetingId=" + meetingId);
      result = query.list();
    } catch (HibernateException e) {
      System.out.println("getExecuteStatusList EJB Exception:" + 
          e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public String saveExecuteStatus(Long applyId, Long meetingId, List<BoardroomExecuteStatusPO> list, String description, String bespoIds) throws Exception {
    String result = "-1";
    begin();
    try {
      if (bespoIds != null)
        this.session.delete("from com.js.oa.routine.boardroom.po.BoardroomExecuteStatusPO po where po.applyId=" + 
            applyId + " and po.meetingId=" + meetingId + 
            " and po.id in (" + bespoIds + ")"); 
      if (description != null) {
        BoardroomMeetingTimePO bmtpo = (BoardroomMeetingTimePO)this.session
          .load(BoardroomMeetingTimePO.class, 
            meetingId);
        bmtpo.setDescriptions(description);
      } 
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          BoardroomExecuteStatusPO po = 
            list.get(i);
          this.session.save(po);
        }  
      this.session.flush();
      result = "1";
    } catch (HibernateException e) {
      System.out.println("saveExecuteStatus EJB Exception:" + e.toString());
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public List getboardRoomApplyAssPOList(Long applyId) throws Exception {
    List result = new ArrayList();
    begin();
    try {
      Query query = this.session.createQuery("select po from com.js.oa.routine.boardroom.po.BoardRoomApplyAssPO po where po.applyId=" + applyId);
      result = query.list();
    } catch (HibernateException e) {
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    return result;
  }
  
  public void addSummary(Long boardroomApplyId, String summary, String[] summarySaveName, String[] summaryName) throws HibernateException, SQLException {
    begin();
    try {
      Connection conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      stmt.execute("DELETE FROM JSDB.oa_boardroomapplyass WHERE apply_id =" + boardroomApplyId);
      stmt.close();
      conn.close();
      BoardRoomApplyPO boardRoomApply = (BoardRoomApplyPO)this.session.load(BoardRoomApplyPO.class, boardroomApplyId);
      boardRoomApply.setSummary(summary);
      this.session.update(boardRoomApply);
      if (summaryName != null && summaryName.length > 0)
        for (int i = 0; i < summaryName.length; i++) {
          BoardRoomApplyAssPO boardRoomApplyAssPO = new BoardRoomApplyAssPO();
          boardRoomApplyAssPO.setApplyId(boardroomApplyId.longValue());
          boardRoomApplyAssPO.setFileName(summaryName[i].toString());
          boardRoomApplyAssPO.setSavaName(summarySaveName[i].toString());
          this.session.save(boardRoomApplyAssPO);
        }  
      this.session.flush();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public List<Long[]> applyTime(String boardroomId, String boardroomApplyId) {
    return applyTime(boardroomId, boardroomApplyId, 0L, 0L);
  }
  
  public List<Long[]> applyTime(String boardroomId, String boardroomApplyId, long beginDate, long endDate) {
    String sql = "SELECT t.beginLong,t.endLong,a.boardroomId,a.BOARDROOMAPPLYID,a.motif FROM oa_boardroom_meetingtime t JOIN oa_boardroomapply a ON t.applyid=a.BOARDROOMAPPLYID WHERE t.status=0 AND (a.STATUS=0 OR a.STATUS=1)";
    if (!boardroomId.equals("-1"))
      sql = String.valueOf(sql) + " and a.boardroomId=" + boardroomId; 
    if (!boardroomApplyId.equals("-1"))
      sql = String.valueOf(sql) + " and a.BOARDROOMAPPLYID<>" + boardroomApplyId; 
    if (beginDate != 0L && endDate != 0L) {
      sql = String.valueOf(sql) + " and ((t.beginLong between " + beginDate + " and " + endDate + ") or (t.endLong between " + beginDate + " and " + endDate + ")" + 
        " or (" + beginDate + " between t.beginLong and t.endLong) or (" + endDate + " between t.beginLong and t.endLong)) ";
    } else if (beginDate != 0L && endDate == 0L) {
      sql = String.valueOf(sql) + " and (t.endLong>=" + beginDate + ")";
    } 
    List<Long[]> list = (List)new ArrayList<Long>();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        list.add(new Long[] { Long.valueOf(rs.getLong(1)), Long.valueOf(rs.getLong(2)), Long.valueOf(rs.getLong(3)), Long.valueOf(rs.getLong(4)) });
      } 
      base.end();
    } catch (Exception ex) {
      base.end();
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List<Long[]> applyTime1(String boardroomId, String boardroomApplyId, long beginDate, long endDate) {
    String sql = "SELECT t.beginLong,t.endLong,a.boardroomId,a.BOARDROOMAPPLYID,a.motif FROM oa_boardroom_meetingtime t JOIN oa_boardroomapply a ON t.applyid=a.BOARDROOMAPPLYID WHERE t.status=0 AND a.STATUS=1 ";
    if (!boardroomId.equals("-1"))
      sql = String.valueOf(sql) + " and a.boardroomId=" + boardroomId; 
    if (!boardroomApplyId.equals("-1"))
      sql = String.valueOf(sql) + " and a.BOARDROOMAPPLYID<>" + boardroomApplyId; 
    if (beginDate != 0L && endDate != 0L) {
      sql = String.valueOf(sql) + " and ((t.beginLong between " + beginDate + " and " + endDate + ") or (t.endLong between " + beginDate + " and " + endDate + ")" + 
        " or (" + beginDate + " between t.beginLong and t.endLong) or (" + endDate + " between t.beginLong and t.endLong)) ";
    } else if (beginDate != 0L && endDate == 0L) {
      sql = String.valueOf(sql) + " and (t.endLong>=" + beginDate + ")";
    } 
    List<Long[]> list = (List)new ArrayList<Long>();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        list.add(new Long[] { Long.valueOf(rs.getLong(1)), Long.valueOf(rs.getLong(2)), Long.valueOf(rs.getLong(3)), Long.valueOf(rs.getLong(4)) });
      } 
      base.end();
    } catch (Exception ex) {
      base.end();
      ex.printStackTrace();
    } 
    return list;
  }
  
  public List<Long[]> applyTime2(String boardroomId, String boardroomApplyId, long beginDate, long endDate) {
    String sql = "SELECT t.beginLong,t.endLong,a.boardroomId,a.BOARDROOMAPPLYID,a.motif FROM oa_boardroom_meetingtime t JOIN oa_boardroomapply a ON t.applyid=a.BOARDROOMAPPLYID WHERE t.status=0 AND a.STATUS=0 ";
    if (!boardroomId.equals("-1"))
      sql = String.valueOf(sql) + " and a.boardroomId=" + boardroomId; 
    if (!boardroomApplyId.equals("-1"))
      sql = String.valueOf(sql) + " and a.BOARDROOMAPPLYID<>" + boardroomApplyId; 
    if (beginDate != 0L && endDate != 0L) {
      sql = String.valueOf(sql) + " and ((t.beginLong between " + beginDate + " and " + endDate + ") or (t.endLong between " + beginDate + " and " + endDate + ")" + 
        " or (" + beginDate + " between t.beginLong and t.endLong) or (" + endDate + " between t.beginLong and t.endLong)) ";
    } else if (beginDate != 0L && endDate == 0L) {
      sql = String.valueOf(sql) + " and (t.endLong>=" + beginDate + ")";
    } 
    List<Long[]> list = (List)new ArrayList<Long>();
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        list.add(new Long[] { Long.valueOf(rs.getLong(1)), Long.valueOf(rs.getLong(2)), Long.valueOf(rs.getLong(3)), Long.valueOf(rs.getLong(4)) });
      } 
      base.end();
    } catch (Exception ex) {
      base.end();
      ex.printStackTrace();
    } 
    return list;
  }
  
  public Object[][] getBoardRoomSatus(String searchDate) throws Exception {
    return getBoardRoomSatus(searchDate, "", "3", "");
  }
  
  public Object[][] getBoardRoomSatus(String searchDate, String roomNum, String cond, String whereSQl) throws Exception {
    begin();
    List<Object[]> roomList = null;
    try {
      String where = "";
      if (!"".equals(roomNum)) {
        where = " where po.capacitance @cond@ " + roomNum;
        if ("1".equals(cond)) {
          where = where.replace("@cond@", ">");
        } else if ("2".equals(cond)) {
          where = where.replace("@cond@", "<");
        } else if ("3".equals(cond)) {
          where = where.replace("@cond@", "=");
        } else if ("4".equals(cond)) {
          where = where.replace("@cond@", ">=");
        } else if ("5".equals(cond)) {
          where = where.replace("@cond@", "<=");
        } 
      } 
      if (where != null && !"".equals(where)) {
        where = String.valueOf(where) + whereSQl.replace("where", "and");
      } else {
        where = whereSQl;
      } 
      String databaseType = SystemCommon.getDatabaseType();
      if ("mysql".equals(databaseType)) {
        where = String.valueOf(where) + " order by po.boardroomOrder asc , CONVERT(po.name USING gbk) desc";
      } else {
        where = String.valueOf(where) + " order by po.boardroomOrder asc , po.name desc";
      } 
      roomList = this.session.createQuery("select po.id,po.name,po.chargeType from com.js.oa.routine.boardroom.po.BoardRoomPO po" + where).list();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      this.session.close();
      this.session = null;
    } 
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<String[][]> returnObject = getWeekRoomShow("-1", ymdFormat.parse(searchDate), 1, "1", "1", SystemCommon.getMeetingType());
    String[][] info = returnObject.get((returnObject.size() <= 1) ? 0 : (returnObject.size() - 1));
    Object[][] returnValue = new Object[roomList.size()][8];
    Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
    int i;
    for (i = 0; i < info.length; i++) {
      String[] info_sub = info[i];
      List<String[]> list = null;
      if (map.get(info_sub[4]) == null) {
        list = (List)new ArrayList<String>();
      } else {
        list = map.get(info_sub[4]);
      } 
      list.add(info_sub);
      map.put(info_sub[4], list);
    } 
    for (i = 0; i < roomList.size(); i++) {
      Object[] obj = roomList.get(i);
      if (map.get(obj[1]) == null) {
        returnValue[i][0] = obj[0];
        returnValue[i][1] = obj[1];
        returnValue[i][2] = "";
        returnValue[i][3] = "";
        returnValue[i][4] = "";
        returnValue[i][5] = "";
        returnValue[i][6] = obj[2];
      } else {
        returnValue[i][0] = obj[0];
        returnValue[i][1] = obj[1];
        returnValue[i][6] = obj[2];
        List<String[]> list = map.get(obj[1]);
        if (list.size() > 0 && !"".equals(((String[])list.get(0))[0])) {
          String timeStrs = "";
          String titles = "";
          String applyNames = "";
          String status = "";
          String applyIds = "";
          for (int j = 0; j < list.size(); j++) {
            String[] info_sub = list.get(j);
            String[] timeStr = info_sub[0].split("-");
            if (timeStr[0].startsWith("0"))
              timeStr[0] = timeStr[0].substring(1); 
            if (timeStr[1].startsWith("0"))
              timeStr[1] = timeStr[1].substring(1); 
            timeStrs = String.valueOf(timeStrs) + timeStr[0] + "-" + timeStr[1] + ";";
            titles = String.valueOf(titles) + info_sub[1] + ";";
            applyNames = String.valueOf(applyNames) + "申请人：" + info_sub[2] + "   联系电话：" + info_sub[9] + ";";
            if ("0".equals(info_sub[8].toString()) && "0".equals(info_sub[7].toString())) {
              status = String.valueOf(status) + "会议申请已通过;";
            } else if ("0".equals(info_sub[8].toString()) && "1".equals(info_sub[7].toString())) {
              status = String.valueOf(status) + "会议已结束;";
            } else {
              status = String.valueOf(status) + "会议申请审核中;";
            } 
            applyIds = String.valueOf(applyIds) + info_sub[9] + ";";
          } 
          returnValue[i][2] = timeStrs;
          returnValue[i][3] = titles;
          returnValue[i][4] = applyNames;
          returnValue[i][5] = status;
          returnValue[i][7] = applyIds;
        } 
      } 
    } 
    return returnValue;
  }
  
  public List getMeetingList() throws Exception {
    List meetingList = null;
    try {
      begin();
      String hql = "select boardRoomApplyPO.boardroomApplyId,boardRoomApplyPO.motif from com.js.oa.routine.boardroom.po.BoardRoomApplyPO boardRoomApplyPO where boardRoomApplyPO.status=0 and boardRoomApplyPO.domainId=0 order by boardRoomApplyPO.boardroomApplyId";
      meetingList = this.session.createQuery(hql).list();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
    return meetingList;
  }
  
  public void saveDiscussion(BoardRoomDiscussionPO po) throws Exception {
    try {
      begin();
      this.session.save(po);
      this.session.flush();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      this.session.close();
      this.session = null;
    } 
  }
  
  public String[] boardroomUser(String applyId) {
    String sql = "SELECT a.emcee 主持人,a.attendeeempId 出席人,a.applyemp 预订者,a.attendeeleaderid 出席领导,a.nonvotingempId 会议列席人,a.NOTEPERSONID 会议记录人,a.motif 会议主题,r.NAME 会议室名称 FROM OA_BOARDROOMAPPLY a JOIN oa_boardRoom r ON r.BOARDROOMID=a.BOARDROOMID WHERE boardroomapplyId=" + 
      
      applyId;
    List<String[]> users = (new DataSourceUtil()).getListQuery(sql, "");
    return users.get(0);
  }
  
  public List<String[][]> getWeekRoomShow(String roomId, Date weekBeginDate, int dayNum) {
    return getWeekRoomShow(roomId, weekBeginDate, dayNum, "0", "0");
  }
  
  public List<String[][]> getWeekRoomShow(String roomId, Date weekBeginDate, int dayNum, String status, String roomStatus) {
    return getWeekRoomShow(roomId, weekBeginDate, dayNum, status, roomStatus, "0");
  }
  
  public List<String[][]> getWeekRoomShow(String roomId, Date weekBeginDate, int dayNum, String status, String roomStatus, String flag) {
    List<String[][]> list = (List)new ArrayList<String>();
    try {
      SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      SimpleDateFormat HmFormat = new SimpleDateFormat("HH:mm");
      String sql = "SELECT b.name,a.motif,a.APPLYEMPNAME,m.meetingtime,m.beginLong,m.endLong,a.emcee,m.status,a.status,a.LINKTELEPHONE,a.BOARDROOMAPPLYID FROM oa_boardroom b JOIN oa_boardroomapply a ON b.BOARDROOMID=a.BOARDROOMID JOIN oa_boardroom_meetingtime m ON a.BOARDROOMAPPLYID=m.applyid WHERE 1=1 ";
      if ("0".equals(roomStatus))
        sql = String.valueOf(sql) + " and m.status=0 "; 
      if ("0".equals(status)) {
        sql = String.valueOf(sql) + "AND a.STATUS=0 ";
      } else {
        sql = String.valueOf(sql) + "AND (a.STATUS=0 or a.STATUS=1 ) ";
      } 
      if (!"-1".equals(roomId))
        if (roomId.contains(",")) {
          sql = String.valueOf(sql) + "AND b.BOARDROOMID in (" + roomId + ")";
        } else {
          sql = String.valueOf(sql) + "AND b.BOARDROOMID=" + roomId;
        }  
      sql = String.valueOf(sql) + " and ((beginLong between @beginTime@ and @endTime@) or (endLong between @beginTime@ and @endTime@) or (@beginTime@ between beginLong and endLong) or (@endTime@ between beginLong and endLong)) ";
      sql = String.valueOf(sql) + "ORDER BY b.BOARDROOMID desc,beginlong";
      Calendar date = Calendar.getInstance();
      date.setTime(weekBeginDate);
      for (int i = 0; i < dayNum; i++) {
        String weekFlag = (new StringBuilder(String.valueOf(date.get(7)))).toString();
        String ymdDate = ymdFormat.format(date.getTime());
        Long beginTime = Long.valueOf(0L);
        Long endTime = Long.valueOf(0L);
        if ("0".equals(flag)) {
          beginTime = Long.valueOf(ymdhm.parse(String.valueOf(ymdDate) + " " + SystemCommon.getMeetingBeginShow()).getTime());
          endTime = Long.valueOf(ymdhm.parse(String.valueOf(ymdDate) + " " + SystemCommon.getMeetingEndShow()).getTime());
        } else {
          beginTime = Long.valueOf(date.getTime().getTime());
          endTime = Long.valueOf(date.getTime().getTime() + 86400000L);
        } 
        String exSql = sql.replace("@beginTime@", (CharSequence)beginTime).replace("@endTime@", (CharSequence)endTime);
        IO2File.printFile("会议室" + ("-1".equals(roomId) ? "(全部)" : ("(会议室Id：" + roomId + ")")) + "状态sql：" + exSql, "会议室状态");
        String[][] weekRoom = (new DataSourceBase()).queryArrayBySql(exSql);
        if (weekRoom == null || weekRoom.length == 0) {
          list.add(new String[][] { { "", "", "", ymdDate, "", "", weekFlag, "0", "0" } });
        } else {
          String[][] weekShow = new String[weekRoom.length][11];
          for (int j = 0; j < weekRoom.length; j++) {
            String[] weekR = weekRoom[j];
            weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
            weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
            weekShow[j][3] = ymdDate;
            weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
            weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
            weekShow[j][6] = weekFlag;
            weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
            weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
            weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
            weekShow[j][10] = "null".equals(weekR[9]) ? "" : weekR[10];
            Long meetingBegin = Long.valueOf(weekR[4]);
            Long meetingEnd = Long.valueOf(weekR[5]);
            if (meetingBegin.longValue() < beginTime.longValue())
              meetingBegin = beginTime; 
            if (meetingEnd.longValue() > endTime.longValue())
              meetingEnd = endTime; 
            String titleBegin = HmFormat.format(new Date(meetingBegin.longValue()));
            String titleEnd = HmFormat.format(new Date(meetingEnd.longValue()));
            if ("0".equals(flag)) {
              if (meetingBegin == beginTime && meetingEnd == endTime) {
                weekShow[j][0] = String.valueOf(SystemCommon.getMeetingBeginShow()) + "-" + SystemCommon.getMeetingEndShow();
              } else {
                if (meetingBegin == beginTime)
                  titleBegin = SystemCommon.getMeetingBeginShow(); 
                if (meetingEnd == endTime)
                  titleEnd = SystemCommon.getMeetingEndShow(); 
                weekShow[j][0] = String.valueOf(titleBegin) + "-" + titleEnd;
              } 
            } else {
              if ("00:00".equals(titleBegin) && "00:00".equals(titleEnd) && meetingEnd.longValue() - meetingBegin.longValue() == 86400000L)
                titleEnd = "24:00"; 
              weekShow[j][0] = String.valueOf(titleBegin) + "-" + titleEnd;
            } 
          } 
          list.add(weekShow);
        } 
        list = getDingqiMeeting(list, roomId, weekBeginDate, dayNum, status, roomStatus, flag);
        date.set(5, date.get(5) + 1);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public List<String[][]> getDingqiMeeting(List<String[][]> list, String roomId, Date weekBeginDate, int dayNum, String status, String roomStatus, String flag) {
    try {
      SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      SimpleDateFormat HmFormat = new SimpleDateFormat("HH:mm");
      String sql = "SELECT b.name,a.motif,a.APPLYEMPNAME,m.meetingDateBegin,m.meetingDateBegin,m.meetingDateEnd,a.emcee,a.status,a.status,a.LINKTELEPHONE,m.everyMeetingBeginTime,m.everyMeetingEndTime,m.everyMeetingBegin,m.everyMeetingEnd,m.meetingCircle FROM oa_boardroom b JOIN oa_boardroomapply a ON b.BOARDROOMID=a.BOARDROOMID JOIN oa_boardroomregular m ON a.regularId=m.regularId WHERE 1=1 ";
      if ("0".equals(status)) {
        sql = String.valueOf(sql) + "AND a.STATUS=0 ";
      } else {
        sql = String.valueOf(sql) + "AND (a.STATUS=0 or a.STATUS=1 ) ";
      } 
      if (!"-1".equals(roomId))
        if (roomId.contains(",")) {
          sql = String.valueOf(sql) + "AND b.BOARDROOMID in (" + roomId + ")";
        } else {
          sql = String.valueOf(sql) + "AND b.BOARDROOMID=" + roomId;
        }  
      String databaseType = SystemCommon.getDatabaseType();
      if ("mysql".equals(databaseType)) {
        sql = String.valueOf(sql) + " and ((@beginTime@ between DATE_ADD(meetingDateBegin,INTERVAL '1:1' MINUTE_SECOND) and DATE_ADD(meetingDateEnd,INTERVAL '1:1' MINUTE_SECOND)) or (@endTime@ between DATE_ADD(meetingDateBegin,INTERVAL '1:1' MINUTE_SECOND) and DATE_ADD(meetingDateEnd,INTERVAL '1:1' MINUTE_SECOND)) ) ";
      } else {
        sql = String.valueOf(sql) + " and ((to_date(@beginTime@,'yyyy-mm-dd hh24:mi:ss') between to_date(meetingDateBegin,'yyyy-mm-dd hh24:mi:ss') and to_date(meetingDateEnd,'yyyy-mm-dd hh24:mi:ss')) or (to_date(@endTime@,'yyyy-mm-dd hh24:mi:ss') between to_date(meetingDateBegin,'yyyy-mm-dd hh24:mi:ss') and to_date(meetingDateEnd,'yyyy-mm-dd hh24:mi:ss') )) ";
      } 
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(weekBeginDate);
      int curWeekDay = calendar.get(7) - 1;
      int curDate = calendar.get(5);
      int curMonth = calendar.get(2) + 1;
      String monthDate = String.valueOf(String.valueOf(curMonth)) + "-" + String.valueOf(curDate);
      sql = String.valueOf(sql) + " and ( (meetingCircle=1 and everyMeetingBegin=" + curWeekDay + ")";
      sql = String.valueOf(sql) + " or (meetingCircle=2 and everyMeetingBegin=" + curDate + ")";
      sql = String.valueOf(sql) + " or (meetingCircle=3 and everyMeetingBegin='" + monthDate + "') )";
      sql = String.valueOf(sql) + " ORDER BY b.BOARDROOMID desc";
      Calendar date = Calendar.getInstance();
      date.setTime(weekBeginDate);
      for (int i = 0; i < dayNum; i++) {
        Integer weekFlag = Integer.valueOf(date.get(7));
        String ymdDate = ymdhm.format(weekBeginDate);
        String beginTime = String.valueOf(ymdDate.substring(0, 10)) + " 00:00:00";
        String endTime = String.valueOf(ymdDate.substring(0, 10)) + " 23:59:59";
        String exSql = sql.replace("@beginTime@", "'" + beginTime + "'").replace("@endTime@", "'" + endTime + "'").replace("@beginnum@", (new StringBuilder(String.valueOf(dayNum))).toString());
        System.out.println("sql:" + exSql);
        String[][] weekRoom = (new DataSourceBase()).queryArrayBySql(exSql);
        if (weekRoom != null && weekRoom.length != 0) {
          String[][] weekShow = new String[weekRoom.length][10];
          for (int j = 0; j < weekRoom.length; j++) {
            String[] weekR = weekRoom[j];
            if ("1".equals((new StringBuilder(String.valueOf(weekR[14]))).toString())) {
              int weekbegin = Integer.parseInt(weekR[12]);
              int weekend = Integer.parseInt(weekR[13]);
              if (weekend - weekbegin == 0 && weekbegin == weekFlag.intValue() - 1) {
                weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                weekShow[j][3] = ymdDate;
                weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
              } 
              if (weekend > 0 && weekbegin >= weekFlag.intValue() - 1 && Math.abs(weekend - weekbegin) != 0) {
                weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                weekShow[j][3] = ymdDate;
                weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
              } 
              if (weekend == 0 && weekbegin >= weekFlag.intValue() - 1 && weekend - weekbegin < 0) {
                weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                weekShow[j][3] = ymdDate;
                weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
              } 
            } else if ("2".equals((new StringBuilder(String.valueOf(weekR[14]))).toString())) {
              int day = weekBeginDate.getDate();
              int weekbegin = Integer.parseInt(weekR[12]);
              int weekend = Integer.parseInt(weekR[13]);
              int temp = 1;
              if (weekend == 0 && weekbegin == day) {
                weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                weekShow[j][3] = ymdDate;
                weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
              } 
              if (weekend != 0 && weekbegin >= day && weekend >= day) {
                weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                weekShow[j][3] = ymdDate;
                weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                temp = weekend - weekbegin;
                weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
              } 
            } else if ("3".equals((new StringBuilder(String.valueOf(weekR[14]))).toString())) {
              Calendar a = Calendar.getInstance();
              String dm = String.valueOf(weekBeginDate.getMonth() + 1) + "-" + weekBeginDate.getDate();
              SimpleDateFormat md = new SimpleDateFormat("yyyy-MM-dd");
              int year = a.get(1);
              String weekbegin = (new StringBuilder(String.valueOf(weekR[12]))).toString();
              String weekend = (new StringBuilder(String.valueOf(weekR[13]))).toString();
              if ("0".equals(weekend) && dm.equals(weekbegin)) {
                weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                weekShow[j][3] = ymdDate;
                weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
              } 
              if (weekend.indexOf("-") > 0) {
                Long curtime = Long.valueOf(weekBeginDate.getTime());
                String YMDbegin = String.valueOf(year) + "-" + weekR[12];
                String YMDend = String.valueOf(year) + "-" + weekR[13];
                Date datebegin = md.parse(YMDbegin);
                Date dateend = md.parse(YMDend);
                Long timebegin = Long.valueOf(datebegin.getTime());
                Long timeend = Long.valueOf(dateend.getTime());
                if (curtime.longValue() >= timebegin.longValue() && curtime.longValue() <= timeend.longValue()) {
                  weekShow[j][1] = "null".equals(weekR[1]) ? "" : weekR[1];
                  weekShow[j][2] = "null".equals(weekR[2]) ? "" : weekR[2];
                  weekShow[j][3] = ymdDate;
                  weekShow[j][4] = "null".equals(weekR[0]) ? "" : weekR[0];
                  weekShow[j][5] = "null".equals(weekR[6]) ? "" : weekR[6];
                  weekShow[j][6] = String.valueOf(weekFlag.intValue() - 1);
                  weekShow[j][7] = "null".equals(weekR[7]) ? "" : weekR[7];
                  weekShow[j][8] = "null".equals(weekR[8]) ? "" : weekR[8];
                  weekShow[j][9] = "null".equals(weekR[9]) ? "" : weekR[9];
                  weekShow[j][0] = String.valueOf(getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[10]))).toString()))) + "-" + getTimeDingqi(Integer.parseInt((new StringBuilder(String.valueOf(weekR[11]))).toString()));
                } 
              } 
            } 
          } 
          if (list != null && list.size() > 0) {
            String[][] info = list.get(0);
            int strlen = info.length + weekShow.length;
            String[][] alist = new String[strlen][10];
            for (int k = 0; k < info.length; k++) {
              if (info[k][0] != null && !"".equals((new StringBuilder(String.valueOf(info[k][0]))).toString())) {
                alist[k][0] = info[k][0];
                alist[k][1] = info[k][1];
                alist[k][2] = info[k][2];
                alist[k][3] = info[k][3];
                alist[k][4] = info[k][4];
                alist[k][5] = info[k][5];
                alist[k][6] = info[k][6];
                alist[k][7] = info[k][7];
                alist[k][8] = info[k][8];
                alist[k][9] = info[k][9];
              } 
            } 
            if (weekShow != null && weekShow.length > 0)
              for (int m = 0; m < weekShow.length; m++) {
                if (weekShow[m][0] != null && !"".equals((new StringBuilder(String.valueOf(weekShow[m][0]))).toString())) {
                  alist[info.length + m][0] = weekShow[m][0];
                  alist[info.length + m][1] = weekShow[m][1];
                  alist[info.length + m][2] = weekShow[m][2];
                  alist[info.length + m][3] = weekShow[m][3];
                  alist[info.length + m][4] = weekShow[m][4];
                  alist[info.length + m][5] = weekShow[m][5];
                  alist[info.length + m][6] = weekShow[m][6];
                  alist[info.length + m][7] = weekShow[m][7];
                  alist[info.length + m][8] = weekShow[m][8];
                  alist[info.length + m][9] = weekShow[m][9];
                } 
              }  
            list.add(alist);
          } else if (weekShow != null && weekShow.length > 0) {
            list.add(weekShow);
          } 
        } 
        date.set(5, date.get(5) + 1);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getTimeDingqi(int time) {
    int hour = time / 60;
    int min = time - hour * 60;
    if (min < 10)
      return String.valueOf(hour) + ":0" + min; 
    return String.valueOf(hour) + ":" + min;
  }
  
  public List<Long[]> getRegularMeeting(String applyId, String boardroomId, String regularId) {
    Date nowDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String databaseType = SystemCommon.getDatabaseType();
    List<Long[]> meetings = (List)new ArrayList<Long>();
    List<Object[]> meetingList = new ArrayList();
    String sql = "SELECT a.motif,r.meetingCircle,r.meetingLength,r.meetingDateBegin,r.meetingDateEnd,r.everyMeetingBegin,r.everyMeetingEnd,r.everyMeetingBeginTime,r.everyMeetingEndTime,a.boardroomapplyId,a.boardroomId FROM oa_boardroomregular r JOIN oa_boardroomapply a ON r.regularId=a.regularId WHERE STATUS=0 and r.ifoprall=0 ";
    if (databaseType.contains("mysql")) {
      sql = String.valueOf(sql) + "AND (DATE_FORMAT('" + dateFormat.format(nowDate) + "','%Y-%m-%d') " + 
        "BETWEEN DATE_FORMAT(r.meetingDateBegin,'%Y-%m-%d') AND DATE_FORMAT(r.meetingDateEnd,'%Y-%m-%d'))";
    } else {
      sql = String.valueOf(sql) + "AND (to_date('" + dateFormat.format(nowDate) + "','yyyy-mm-dd') " + 
        "BETWEEN to_date(r.meetingDateBegin,'yyyy-mm-dd') AND to_date(r.meetingDateEnd,'yyyy-mm-dd'))";
    } 
    if (!"-1".equals(applyId))
      sql = String.valueOf(sql) + " and a.boardroomapplyId<>" + applyId; 
    if (!"-1".equals(boardroomId))
      sql = String.valueOf(sql) + " and a.boardroomId=" + boardroomId; 
    if (!"-1".equals(regularId))
      sql = String.valueOf(sql) + " and a.regularId<>" + boardroomId; 
    IO2File.printFile("获得定期会议所有的会议安排：" + sql, "会议室");
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = { 
            rs.getString(1), 
            Integer.valueOf(rs.getInt(2)), 
            Integer.valueOf(rs.getInt(3)), 
            rs.getString(4), 
            rs.getString(5), 
            rs.getString(6), 
            rs.getString(7), 
            Integer.valueOf(rs.getInt(8)), 
            Integer.valueOf(rs.getInt(9)), 
            Long.valueOf(rs.getLong(10)), 
            Long.valueOf(rs.getLong(11)) };
        meetingList.add(obj);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    for (int i = 0; i < meetingList.size(); i++) {
      Object[] obj = meetingList.get(i);
      List<Long[]> meetingSub = getRegularMeetingList(obj, nowDate);
      for (int m = 0; m < meetingSub.size(); m++)
        meetings.add(meetingSub.get(m)); 
    } 
    return meetings;
  }
  
  public List<Long[]> getRegularMeeting1(String applyId, String boardroomId, String regularId) {
    Date nowDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String databaseType = SystemCommon.getDatabaseType();
    List<Long[]> meetings = (List)new ArrayList<Long>();
    List<Object[]> meetingList = new ArrayList();
    String sql = "SELECT a.motif,r.meetingCircle,r.meetingLength,r.meetingDateBegin,r.meetingDateEnd,r.everyMeetingBegin,r.everyMeetingEnd,r.everyMeetingBeginTime,r.everyMeetingEndTime,a.boardroomapplyId,a.boardroomId FROM oa_boardroomregular r JOIN oa_boardroomapply a ON r.regularId=a.regularId WHERE STATUS=1 and r.ifoprall=0 ";
    if (databaseType.contains("mysql")) {
      sql = String.valueOf(sql) + "AND (DATE_FORMAT('" + dateFormat.format(nowDate) + "','%Y-%m-%d') " + 
        "BETWEEN DATE_FORMAT(r.meetingDateBegin,'%Y-%m-%d') AND DATE_FORMAT(r.meetingDateEnd,'%Y-%m-%d'))";
    } else {
      sql = String.valueOf(sql) + "AND (to_date('" + dateFormat.format(nowDate) + "','yyyy-mm-dd') " + 
        "BETWEEN to_date(r.meetingDateBegin,'yyyy-mm-dd') AND to_date(r.meetingDateEnd,'yyyy-mm-dd'))";
    } 
    if (!"-1".equals(applyId))
      sql = String.valueOf(sql) + " and a.boardroomapplyId<>" + applyId; 
    if (!"-1".equals(boardroomId))
      sql = String.valueOf(sql) + " and a.boardroomId=" + boardroomId; 
    if (!"-1".equals(regularId))
      sql = String.valueOf(sql) + " and a.regularId<>" + boardroomId; 
    IO2File.printFile("获得定期会议所有的会议安排：" + sql, "会议室");
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      while (rs.next()) {
        Object[] obj = { 
            rs.getString(1), 
            Integer.valueOf(rs.getInt(2)), 
            Integer.valueOf(rs.getInt(3)), 
            rs.getString(4), 
            rs.getString(5), 
            rs.getString(6), 
            rs.getString(7), 
            Integer.valueOf(rs.getInt(8)), 
            Integer.valueOf(rs.getInt(9)), 
            Long.valueOf(rs.getLong(10)), 
            Long.valueOf(rs.getLong(11)) };
        meetingList.add(obj);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    for (int i = 0; i < meetingList.size(); i++) {
      Object[] obj = meetingList.get(i);
      List<Long[]> meetingSub = getRegularMeetingList(obj, nowDate);
      for (int m = 0; m < meetingSub.size(); m++)
        meetings.add(meetingSub.get(m)); 
    } 
    return meetings;
  }
  
  public List<Long[]> getRegularMeetingList(Object[] obj, Date nowDate) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    BoardRoomTask boardRoomTask = new BoardRoomTask();
    List<Long[]> meetings = (List)new ArrayList<Long>();
    try {
      int anpai = (int)((dateFormat.parse((String)obj[4]).getTime() - dateFormat.parse((String)obj[3]).getTime()) / 86400000L);
      Long meetingCBegin = Long.valueOf(dateFormat.parse((String)obj[3]).getTime());
      Long meetingCEnd = Long.valueOf(dateFormat.parse((String)obj[4]).getTime());
      Date beginDate = boardRoomTask.getMeettingBeginDate(((Integer)obj[1]).intValue(), (String)obj[5], nowDate);
      while (beginDate.getTime() >= meetingCBegin.longValue() && beginDate.getTime() < meetingCEnd.longValue()) {
        Long beginLong = Long.valueOf(beginDate.getTime());
        Long endLong = Long.valueOf(beginDate.getTime());
        if ((beginDate.getTime() - nowDate.getTime()) / 86400000L <= anpai) {
          if (((Integer)obj[2]).intValue() == 1) {
            beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
            endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
          } else if (((Integer)obj[1]).intValue() == 1) {
            if (obj[5].equals(obj[6])) {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
              endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
            } else {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((String)obj[7]).longValue() * 60L * 1000L);
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(beginDate);
              while (Integer.valueOf((String)obj[6]).intValue() != calendar.get(7) - 1)
                calendar.set(5, calendar.get(5) + 1); 
              endLong = Long.valueOf(calendar.getTime().getTime() + Long.valueOf((String)obj[8]).longValue() * 60L * 1000L);
            } 
          } else if (((Integer)obj[1]).intValue() == 2) {
            if (obj[5].equals(obj[6])) {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
              endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
            } else {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((String)obj[7]).longValue() * 60L * 1000L);
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(beginDate);
              int maxDay = calendar.getActualMaximum(5);
              int endDay = Integer.valueOf((String)obj[6]).intValue();
              if (maxDay < endDay)
                endDay = maxDay; 
              while (endDay != calendar.get(5))
                calendar.set(5, calendar.get(5) + 1); 
              endLong = Long.valueOf(calendar.getTime().getTime() + Long.valueOf((String)obj[8]).longValue() * 60L * 1000L);
            } 
          } else if (obj[5].equals(obj[6])) {
            beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
            endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
          } else {
            beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((String)obj[7]).longValue() * 60L * 1000L);
            String[] monthDay = obj[6].split("-");
            int month = Integer.valueOf(monthDay[0]).intValue() - 1;
            int day = Integer.valueOf(monthDay[1]).intValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(beginDate);
            int monthNum = calendar.get(2);
            int dayNum = calendar.get(5);
            while (month != monthNum || day != dayNum) {
              calendar.set(5, calendar.get(5) + 1);
              monthNum = calendar.get(2);
              dayNum = calendar.get(5);
            } 
            endLong = Long.valueOf(calendar.getTime().getTime() + Long.valueOf((String)obj[8]).longValue() * 60L * 1000L);
          } 
          meetings.add(new Long[] { beginLong, endLong, (Long)obj[10], (Long)obj[9] });
        } 
        Date dateStr = dateFormat.parse(dateFormat.format(new Date(endLong.longValue() + 86400000L)));
        beginDate = boardRoomTask.getMeettingBeginDate(((Integer)obj[1]).intValue(), (String)obj[5], dateStr);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return meetings;
  }
  
  public boolean deleteRegularApply(String applyId) {
    boolean result = false;
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      String regularId = "-1";
      PreparedStatement pstmt = conn.prepareStatement("select regularid from oa_boardroomapply where boardroomapplyID=?");
      pstmt.setString(1, applyId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next())
        regularId = rs.getString(1); 
      rs.close();
      pstmt.close();
      List<String> list = new ArrayList<String>();
      pstmt = conn.prepareStatement("select boardroomapplyID from oa_boardroomapply where regularapplyid=? and ifregular=1");
      pstmt.setString(1, applyId);
      rs = pstmt.executeQuery();
      while (rs.next())
        list.add(rs.getString(1)); 
      rs.close();
      pstmt.close();
      Statement stmt = conn.createStatement();
      for (int i = 0; i < list.size(); i++) {
        stmt.executeUpdate("delete from OA_BOARDROOM_MEETINGTIME where applyid=" + (String)list.get(i));
        stmt.executeUpdate("delete from OA_BOARDROOM_PERSONS where applyid=" + (String)list.get(i));
        stmt.executeUpdate("delete from OA_BOARDROOM_EXECUTESTATUS where apply_id=" + (String)list.get(i));
        stmt.executeUpdate("delete from OA_BOARDROOM_DISCUSSION where applyId=" + (String)list.get(i));
        stmt.executeUpdate("delete from OA_BOARDROOMAPPLYASS where apply_id=" + (String)list.get(i));
      } 
      stmt.executeUpdate("delete from OA_BOARDROOMREGULAR where regularid=" + regularId);
      stmt.executeUpdate("delete from oa_boardroomapply where regularid=" + regularId);
      stmt.close();
      conn.close();
      result = true;
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    return result;
  }
  
  public String[] meetingE(String applyId) {
    String[] r = { "", "", "", "", "" };
    String sql = "select motif,applyempname,boardroomId,BOARDROOMAPPLYID from oa_boardroomapply where boardroomapplyid=" + applyId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        r[0] = rs.getString(1);
        r[1] = rs.getString(2);
        r[2] = rs.getString(3);
        r[3] = rs.getString(4);
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return r;
  }
}
