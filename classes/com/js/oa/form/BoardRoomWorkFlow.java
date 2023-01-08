package com.js.oa.form;

import com.js.oa.routine.boardroom.po.BdroomAppAccessoryPO;
import com.js.oa.routine.boardroom.po.BoardRoomApplyPO;
import com.js.oa.routine.boardroom.po.BoardRoomRegularPO;
import com.js.oa.routine.boardroom.po.BoardroomMeetingTimePO;
import com.js.oa.routine.boardroom.service.BoardRoomBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.service.messages.RemindUtil;
import com.js.thread.BoardRoomTask;
import com.js.util.util.ConversionString;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BoardRoomWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Long boardroomId = (httpServletRequest.getParameter("boardroomId") != null && 
      !"".equals(httpServletRequest.getParameter("boardroomId")) && 
      !"null".equals(httpServletRequest.getParameter("boardroomId"))) ? 
      Long.valueOf(httpServletRequest.getParameter("boardroomId")) : new Long(0L);
    String applyTypeId = httpServletRequest.getParameter("boardroomApplyType");
    if (applyTypeId == null || "".equals(applyTypeId) || "null".equals(applyTypeId))
      applyTypeId = "0"; 
    Long boardroomApplyTypeId = Long.valueOf(applyTypeId);
    boolean flag = false;
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Long Id = Long.valueOf(-1L);
    if (!flag) {
      if ("saveSubmit".equals(httpServletRequest.getParameter("saveType"))) {
        Id = boardRoomBD.addBoardroomApply(setPO(httpServletRequest, 1, "0"), boardroomId, boardroomApplyTypeId);
      } else {
        Id = boardRoomBD.addBoardroomApply(setPO(httpServletRequest, 1), boardroomId, boardroomApplyTypeId);
      } 
    } else {
      Id = Long.valueOf(0L);
    } 
    return Id;
  }
  
  public boolean isExitMeetApplay(HttpServletRequest httpServletRequest, Long boardroomId, Long boardroomApplyTypeId) {
    boolean flag = true;
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Date destineDate = new Date(httpServletRequest.getParameter("destineDate"));
    String destineDateBeginTime = httpServletRequest.getParameter("destineDateBeginTime");
    String destineDateEndTime = httpServletRequest.getParameter("destineDateEndTime");
    Date endDate = (httpServletRequest.getParameter("endDate") == null || "".equals(httpServletRequest.getParameter("endDate")) || 
      "null".equals(httpServletRequest.getParameter("endDate"))) ? 
      new Date(httpServletRequest.getParameter("destineDate")) : new Date(httpServletRequest.getParameter("endDate"));
    Long beginDateLong = Long.valueOf(destineDate.getTime() + Long.valueOf(destineDateBeginTime).longValue() * 1000L);
    Long endDateLong = Long.valueOf(endDate.getTime() + Long.valueOf(destineDateEndTime).longValue() * 1000L);
    String boardroomApplyId = (httpServletRequest.getParameter("boardroomApplyId") == null || "".equals(httpServletRequest.getParameter("boardroomApplyId")) || 
      "null".equals(httpServletRequest.getParameter("boardroomApplyId"))) ? "-1" : httpServletRequest.getParameter("boardroomApplyId");
    flag = boardRoomBD.applyTime((String)boardroomId, boardroomApplyId, beginDateLong, endDateLong);
    return flag;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId"));
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO = setPO(httpServletRequest, 0);
    boardroomApplyPO.setBoardroomApplyId(boardroomApplyId);
    Long boardroomId = new Long((httpServletRequest.getParameter("boardroomId") == null) ? "0" : httpServletRequest.getParameter("boardroomId"));
    Long boardroomApplyType = new Long((httpServletRequest.getParameter(
          "boardroomApplyType") == null) ? "0" : httpServletRequest.getParameter("boardroomApplyType"));
    String queryString = httpServletRequest.getQueryString();
    String flag = "flow";
    if (queryString.indexOf("modifyBoardroomApply") > -1)
      flag = "modi"; 
    boolean result = boardRoomBD.modiBoardroomApply(boardroomApplyPO, 
        boardroomApplyId, boardroomId, boardroomApplyType, flag);
    HttpSession session = httpServletRequest.getSession(true);
    if (!result)
      boardroomApplyId = Long.valueOf("-1"); 
    return boardroomApplyId;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomApplyId"));
    updateBoardRoomInfo(httpServletRequest.getParameter("boardroomId"));
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO = setPO(httpServletRequest, 0);
    boardroomApplyPO.setBoardroomApplyId(boardroomApplyId);
    boardroomApplyPO.setStatus(Integer.valueOf("3"));
    Long boardroomId = new Long((httpServletRequest.getParameter(
          "boardroomId") == null) ? "0" : 
        httpServletRequest.getParameter(
          "boardroomId"));
    Long boardroomApplyType = new Long((httpServletRequest.getParameter(
          "boardroomApplyType") == null) ? "0" : 
        httpServletRequest.getParameter(
          "boardroomApplyType"));
    boolean result = boardRoomBD.modiBoardroomApply(boardroomApplyPO, 
        boardroomApplyId, boardroomId, boardroomApplyType, "flow");
    if (result) {
      boardroomApplyId = boardroomApplyId;
    } else {
      boardroomApplyId = Long.valueOf("-1");
    } 
    return boardroomApplyId;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter("boardroomApplyId"));
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO = setPO(httpServletRequest, 0);
    boardroomApplyPO.setBoardroomApplyId(boardroomApplyId);
    boardroomApplyPO.setStatus(Integer.valueOf("0"));
    Long boardroomId = new Long((httpServletRequest.getParameter(
          "boardroomId") == null) ? "0" : httpServletRequest.getParameter("boardroomId"));
    Long boardroomApplyType = new Long((httpServletRequest.getParameter(
          "boardroomApplyType") == null) ? "0" : httpServletRequest.getParameter("boardroomApplyType"));
    boolean result = boardRoomBD.modiBoardroomApply(boardroomApplyPO, 
        boardroomApplyId, boardroomId, boardroomApplyType, "flow");
    if (!result)
      boardroomApplyId = Long.valueOf("-1"); 
    boardroomId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomId"));
    String receivers = String.valueOf(httpServletRequest.getParameter("emcee")) + 
      httpServletRequest.getParameter("attendeeEmpId") + (
      (httpServletRequest.getParameter("nonvotingEmpId") == null) ? "" : 
      httpServletRequest.getParameter("nonvotingEmpId")) + (
      (httpServletRequest.getParameter("attendeeLeaderId") == null) ? "" : 
      httpServletRequest.getParameter("attendeeLeaderId"));
    String boardRoomName = boardRoomBD.selectBoardroom(boardroomId).getName();
    String motif = httpServletRequest.getParameter("motif");
    String _mailsubject = "在" + boardRoomName + "召开" + motif;
    if (httpServletRequest.getParameter("typeFlag") != null && "1".equals(httpServletRequest.getParameter("typeFlag"))) {
      _mailsubject = "定期会议" + motif + "已审批完毕！";
    } else {
      SimpleDateFormat fomatter = new SimpleDateFormat("yy年MM月dd日");
      SimpleDateFormat fomatter1 = new SimpleDateFormat("yy年MM月dd日   HH:mm分");
      int destineDateBeginTime = Integer.parseInt(httpServletRequest.getParameter("destineDateBeginTime"));
      int destineDateEndTime = Integer.parseInt(httpServletRequest.getParameter("destineDateEndTime"));
      String tixing = fomatter.format(new Date(httpServletRequest.getParameter("destineDate")));
      _mailsubject = String.valueOf(tixing) + (destineDateBeginTime / 3600) + ":" + (
        destineDateBeginTime % 3600 / 60) + "-" + (
        destineDateEndTime / 3600) + ":" + (
        destineDateEndTime % 3600 / 60) + "在" + boardRoomName + 
        "召开" + motif;
    } 
    HttpSession session = httpServletRequest.getSession(true);
    String userIdStr = (boardroomApplyPO.getAttendeeEmpId() != null) ? boardroomApplyPO.getAttendeeEmpId() : "";
    userIdStr = userIdStr.replaceAll("\\$\\$", ",");
    userIdStr = userIdStr.replaceAll(",,", ",");
    userIdStr = userIdStr.replaceAll("\\$", ",");
    userIdStr = (userIdStr.length() > 1) ? userIdStr.substring(1, userIdStr.length() - 1) : "";
    String emcee = "$" + httpServletRequest.getParameter("emcee") + "$";
    String[] tmp0 = emcee.split("\\$\\$");
    String s0 = "";
    for (int i = 0; i < tmp0.length; i++)
      s0 = String.valueOf(s0) + tmp0[i] + ","; 
    String s1 = "";
    if (httpServletRequest.getParameter("nonvotingEmpId") != null && 
      !"".equals(httpServletRequest.getParameter("nonvotingEmpId")) && 
      !"null".equals(httpServletRequest.getParameter("nonvotingEmpId"))) {
      String nonvotingEmpId = "$" + httpServletRequest.getParameter("nonvotingEmpId") + "$";
      String[] tmp1 = nonvotingEmpId.split("\\$\\$");
      for (int k = 0; k < tmp1.length; k++)
        s1 = String.valueOf(s1) + tmp1[k] + ","; 
    } 
    String s2 = "";
    if (httpServletRequest.getParameter("attendeeLeaderId") != null && 
      !"".equals(httpServletRequest.getParameter("attendeeLeaderId")) && 
      !"null".equals(httpServletRequest.getParameter("attendeeLeaderId"))) {
      String attendeeLeaderId = "$" + httpServletRequest.getParameter(
          "attendeeLeaderId") + "$";
      String[] tmp2 = attendeeLeaderId.split("\\$\\$");
      for (int k = 0; k < tmp2.length; k++)
        s2 = String.valueOf(s2) + tmp2[k] + ","; 
    } 
    String s3 = "";
    if (httpServletRequest.getParameter("notePersonId") != null && 
      !"".equals(httpServletRequest.getParameter("notePersonId")) && 
      !"null".equalsIgnoreCase(httpServletRequest.getParameter("notePersonId"))) {
      String notePersonId = "$" + httpServletRequest.getParameter("notePersonId") + "$";
      String[] tmp2 = notePersonId.split("\\$\\$");
      for (int k = 0; k < tmp2.length; k++)
        s3 = String.valueOf(s3) + tmp2[k] + ","; 
    } 
    String _userIdStr = String.valueOf(userIdStr) + s0 + s1 + s2 + s3 + "-1";
    _userIdStr = _userIdStr.replaceAll(",,", ",");
    String[] _aaa = _userIdStr.split(",");
    String _bbb = ",";
    for (int j = 0; j < _aaa.length; j++) {
      if (!"".equals(_aaa[j]) && 
        _bbb.indexOf("," + _aaa[j] + ",") == -1)
        _bbb = String.valueOf(_bbb) + _aaa[j] + ","; 
    } 
    _userIdStr = _bbb.substring(1, _bbb.length() - 1);
    String link = "BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + httpServletRequest.getParameter(
        "boardroomApplyId") + "&boardroomName=" + boardRoomName + "&type=view&executeStatus=false";
    if (!"-1".equals(_userIdStr))
      RemindUtil.sendMessageToUsers(_mailsubject, link, _userIdStr, "Meeting", new Date(), new Date("2050/1/1")); 
    if (httpServletRequest.getParameter("typeFlag") != null && !"0".equals(httpServletRequest.getParameter("typeFlag")))
      try {
        BoardRoomTask task = new BoardRoomTask();
        task.meetingSet((String)boardroomApplyId, 0);
        System.out.println("定期会议申请完毕，安排会议！");
      } catch (Exception e) {
        e.printStackTrace();
      }  
    return boardroomApplyId;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    BoardRoomBD boardRoomBD = new BoardRoomBD();
    Long boardroomApplyId = Long.valueOf(httpServletRequest.getParameter(
          "boardroomApplyId"));
    boolean result = boardRoomBD.dissentBoardroomApply(boardroomApplyId);
    if (result) {
      boardroomApplyId = boardroomApplyId;
    } else {
      boardroomApplyId = Long.valueOf("-1");
    } 
    return boardroomApplyId;
  }
  
  private BoardRoomApplyPO setPO(HttpServletRequest httpServletRequest, int flag) {
    return setPO(httpServletRequest, flag, "-1");
  }
  
  private BoardRoomApplyPO setPO(HttpServletRequest httpServletRequest, int flag, String status) {
    HttpSession session = httpServletRequest.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    BoardRoomApplyPO boardroomApplyPO = new BoardRoomApplyPO();
    boardroomApplyPO.setDomainId(domainId);
    if (status.equals("-1")) {
      boardroomApplyPO.setStatus(new Integer(1));
    } else {
      boardroomApplyPO.setStatus(new Integer(0));
    } 
    String relProjectId = httpServletRequest.getParameter("relProjectId");
    if (relProjectId == null || "null".equals(relProjectId) || "".equals(relProjectId))
      relProjectId = "-1"; 
    boardroomApplyPO.setRelProjectId(Long.valueOf(Long.parseLong(relProjectId)));
    if (httpServletRequest.getParameter("status") != null && 
      "0".equals(httpServletRequest.getParameter("status")))
      boardroomApplyPO.setStatus(new Integer(0)); 
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (httpServletRequest.getParameter("ifRegular") != null && "1".equals(httpServletRequest.getParameter("ifRegular"))) {
      try {
        Set<BoardroomMeetingTimePO> timeSet = new HashSet();
        String mBeginDate = httpServletRequest.getParameter("mBeginDate");
        String startHour = httpServletRequest.getParameter("startHour");
        String startMinutes = httpServletRequest.getParameter("startMinutes");
        String endHour = httpServletRequest.getParameter("endHour");
        String endMinutes = httpServletRequest.getParameter("endMinutes");
        String mEndDate = httpServletRequest.getParameter("mEndDate");
        int meetingDay = 1;
        Date beginDate = dateTimeFormat.parse(String.valueOf(mBeginDate.replace("/", "-")) + " " + startHour + ":" + startMinutes + ":00");
        Date endDate = dateTimeFormat.parse(String.valueOf(mBeginDate.replace("/", "-")) + " " + endHour + ":" + endMinutes + ":00");
        if (mEndDate != null) {
          meetingDay = (int)((new Date(mEndDate)).getTime() - (new Date(mBeginDate)).getTime()) / 86400000;
          endDate = dateTimeFormat.parse(String.valueOf(mEndDate.replace("/", "-")) + " " + endHour + ":" + endMinutes + ":00");
        } 
        if (meetingDay == 0)
          meetingDay = 1; 
        BoardroomMeetingTimePO timePO = new BoardroomMeetingTimePO();
        timePO.setApply(boardroomApplyPO);
        timePO.setMeetingDate(new Date(mBeginDate));
        timePO.setStartTime(String.valueOf(Integer.parseInt(startHour) * 3600 + Integer.parseInt(startMinutes) * 60));
        timePO.setEndTime(String.valueOf(Integer.parseInt(endHour) * 3600 + Integer.parseInt(endMinutes) * 60));
        timePO.setSortNum(new Integer(0));
        timePO.setStatus(new Integer(0));
        timePO.setMeetingDay(meetingDay);
        timePO.setBeginLong(Long.valueOf(beginDate.getTime()));
        timePO.setEndLong(Long.valueOf(endDate.getTime()));
        timeSet.add(timePO);
        boardroomApplyPO.setIfRegular(Integer.valueOf(1));
        boardroomApplyPO.setMeetingTime(timeSet);
        boardroomApplyPO.setRegularPO(null);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else if (httpServletRequest.getParameter("typeFlag") != null && "1".equals(httpServletRequest.getParameter("typeFlag"))) {
      BoardRoomRegularPO regularPO = new BoardRoomRegularPO();
      regularPO.setBdroomAppType(Long.valueOf((httpServletRequest.getParameter("boardroomApplyType") == null) ? "0" : 
            httpServletRequest.getParameter("boardroomApplyType")));
      regularPO.setMeetingCircle(Integer.valueOf((httpServletRequest.getParameter("meetingCircle") == null) ? "0" : 
            httpServletRequest.getParameter("meetingCircle")));
      regularPO.setMeetingLength(Integer.valueOf((httpServletRequest.getParameter("meetingLength") == null) ? "0" : 
            httpServletRequest.getParameter("meetingLength")));
      regularPO.setTypeFlag(Integer.valueOf((httpServletRequest.getParameter("typeFlag") == null) ? "0" : 
            httpServletRequest.getParameter("typeFlag")));
      regularPO.setMeetingDateBegin((httpServletRequest.getParameter("meetingDateBegin") == null) ? "" : 
          httpServletRequest.getParameter("meetingDateBegin").replace("/", "-"));
      regularPO.setMeetingDateEnd((httpServletRequest.getParameter("meetingDateEnd") == null) ? "" : 
          httpServletRequest.getParameter("meetingDateEnd").replace("/", "-"));
      regularPO.setEveryMeetingBegin((httpServletRequest.getParameter("everyMeetingBegin") == null) ? "0" : 
          httpServletRequest.getParameter("everyMeetingBegin"));
      regularPO.setEveryMeetingEnd((httpServletRequest.getParameter("everyMeetingEnd") == null) ? "0" : 
          httpServletRequest.getParameter("everyMeetingEnd"));
      regularPO.setEveryMeetingBeginTime(Integer.valueOf((httpServletRequest.getParameter("everyMeetingBeginTime") == null) ? "0" : 
            httpServletRequest.getParameter("everyMeetingBeginTime")));
      regularPO.setEveryMeetingEndTime(Integer.valueOf((httpServletRequest.getParameter("everyMeetingEndTime") == null) ? "0" : 
            httpServletRequest.getParameter("everyMeetingEndTime")));
      if (httpServletRequest.getParameter("regularId") != null && !"".equals(httpServletRequest.getParameter("regularId")))
        regularPO.setRegularId(Long.valueOf(httpServletRequest.getParameter("regularId"))); 
      boardroomApplyPO.setRegularPO(regularPO);
      boardroomApplyPO.setMeetingTime(null);
      boardroomApplyPO.setIfRegular(Integer.valueOf(0));
    } else {
      try {
        Set<BoardroomMeetingTimePO> timeSet = new HashSet(0);
        String[] destineDate = httpServletRequest.getParameterValues("destineDate");
        String[] startHour = httpServletRequest.getParameterValues("startHour");
        String[] startMinutes = httpServletRequest.getParameterValues("startMinutes");
        String[] endHour = httpServletRequest.getParameterValues("endHour");
        String[] endMinutes = httpServletRequest.getParameterValues("endMinutes");
        int j = 0, b = 1;
        if (startHour.length == destineDate.length * 2)
          b = 2; 
        for (int i = 0; i < destineDate.length; i++) {
          if (i != 1) {
            int n = i * b;
            Date beginDate = dateTimeFormat.parse(String.valueOf(destineDate[i].replace("/", "-")) + " " + startHour[n] + ":" + startMinutes[n] + ":00");
            Date endDate = dateTimeFormat.parse(String.valueOf(destineDate[i].replace("/", "-")) + " " + endHour[n] + ":" + endMinutes[n] + ":00");
            BoardroomMeetingTimePO timePO = new BoardroomMeetingTimePO();
            timePO.setApply(boardroomApplyPO);
            timePO.setMeetingDate(new Date(destineDate[i]));
            timePO.setStartTime(String.valueOf(Integer.parseInt(startHour[n]) * 3600 + Integer.parseInt(startMinutes[n]) * 60));
            timePO.setEndTime(String.valueOf(Integer.parseInt(endHour[n]) * 3600 + Integer.parseInt(endMinutes[n]) * 60));
            timePO.setSortNum(new Integer(j));
            timePO.setStatus(new Integer(0));
            timePO.setBeginLong(Long.valueOf(beginDate.getTime()));
            timePO.setEndLong(Long.valueOf(endDate.getTime()));
            timePO.setMeetingDay(1);
            timeSet.add(timePO);
            j++;
          } 
        } 
        boardroomApplyPO.setIfRegular(Integer.valueOf(0));
        boardroomApplyPO.setMeetingTime(timeSet);
        boardroomApplyPO.setRegularPO(null);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    if (httpServletRequest.getParameter("personNum") != null && 
      !"".equals(httpServletRequest.getParameter("personNum").trim()) && 
      !"null".equals(httpServletRequest.getParameter("personNum")))
      boardroomApplyPO.setPersonNum(Long.valueOf(httpServletRequest.getParameter("personNum"))); 
    boardroomApplyPO.setDepict(httpServletRequest.getParameter("depict"));
    boardroomApplyPO.setApplyEmp(Long.valueOf(httpServletRequest.getParameter("applyEmp")));
    boardroomApplyPO.setApplyEmpName(httpServletRequest.getParameter("applyEmpName"));
    boardroomApplyPO.setApplyOrg(Long.valueOf(httpServletRequest.getParameter("applyOrg")));
    boardroomApplyPO.setApplyOrgName(httpServletRequest.getParameter("applyOrgName"));
    boardroomApplyPO.setEmcee(httpServletRequest.getParameter("emcee"));
    boardroomApplyPO.setEmceeName(httpServletRequest.getParameter("emceeName"));
    boardroomApplyPO.setMotif(httpServletRequest.getParameter("motif"));
    boardroomApplyPO.setBoardroomCode(httpServletRequest.getParameter("boardroomCode"));
    boardroomApplyPO.setAttendeeLeader(httpServletRequest.getParameter("attendeeLeader"));
    boardroomApplyPO.setAttendeeLeaderId(httpServletRequest.getParameter("attendeeLeaderId"));
    boardroomApplyPO.setAttendee(httpServletRequest.getParameter("attendee"));
    boardroomApplyPO.setBoardEquipment(httpServletRequest.getParameter("boardEquipment"));
    boardroomApplyPO.setPoints(new Long((httpServletRequest.getParameter(
            "points") == null || httpServletRequest.getParameter(
            "points").equals("")) ? "0" : httpServletRequest.getParameter("points")));
    if (httpServletRequest.getParameter("applyDate") != null && 
      !"".equals(httpServletRequest.getParameter("applyDate")) && 
      !"null".equals(httpServletRequest.getParameter("applyDate")))
      boardroomApplyPO.setApplyDate(new Date(httpServletRequest.getParameter("applyDate"))); 
    boardroomApplyPO.setLinkTelephone(httpServletRequest.getParameter(
          "linkTelephone"));
    if (flag == 1) {
      if (httpServletRequest.getParameter("attendeeEmpId") != null && 
        !httpServletRequest.getParameter("attendeeEmpId").equals("") && 
        !"null".equals(httpServletRequest.getParameter("attendeeEmpId"))) {
        ConversionString con = new ConversionString(httpServletRequest
            .getParameter("attendeeEmpId"));
        String userIdStr = String.valueOf(con.getUserIdString()) + ",";
        userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
        userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
        boardroomApplyPO.setAttendeeEmpId("$" + 
            userIdStr.replace(',', '$'));
        String temp = (boardroomApplyPO.getAttendeeEmpId() != null) ? boardroomApplyPO.getAttendeeEmpId() : "";
        temp = temp.replaceAll("\\$\\$", "\\$");
        temp = temp.replaceAll("\\$", "\\$\\$");
        temp = temp.replaceAll("\\$\\$\\$", "\\$");
        boardroomApplyPO.setAttendeeEmpId(temp.substring(1, 
              temp.length() - 1));
      } 
    } else if (httpServletRequest.getParameter("attendeeEmpId") != null && 
      !httpServletRequest.getParameter("attendeeEmpId").equals("") && 
      !"null".equals(httpServletRequest.getParameter("attendeeEmpId"))) {
      ConversionString con = new ConversionString(httpServletRequest
          .getParameter("attendeeEmpId"));
      String userIdStr = String.valueOf(con.getUserIdString()) + ",";
      userIdStr = String.valueOf(userIdStr) + getUserByGroup(con.getGroupIdString()) + ",";
      userIdStr = String.valueOf(userIdStr) + getUserByOrg(con.getOrgIdString());
      boardroomApplyPO.setAttendeeEmpId("$" + userIdStr.replace(',', '$'));
      String temp = (boardroomApplyPO.getAttendeeEmpId() != null) ? boardroomApplyPO.getAttendeeEmpId() : "";
      temp = temp.replaceAll("\\$\\$", "\\$");
      temp = temp.replaceAll("\\$", "\\$\\$");
      temp = temp.replaceAll("\\$\\$\\$", "\\$");
      boardroomApplyPO.setAttendeeEmpId(temp.substring(1, temp.length() - 1));
    } 
    boardroomApplyPO.setNonvoting(httpServletRequest.getParameter("nonvoting"));
    boardroomApplyPO.setNonvotingEmpId(httpServletRequest.getParameter("nonvotingEmpId"));
    boardroomApplyPO.setNotePerson(httpServletRequest.getParameter("notePerson"));
    boardroomApplyPO.setNotePersonId(httpServletRequest.getParameter("notePersonId"));
    boardroomApplyPO.setFileNumber(httpServletRequest.getParameter("fileNumber"));
    boardroomApplyPO.setSenderName(httpServletRequest.getParameter("senderName"));
    HashSet<BdroomAppAccessoryPO> Accessory = new HashSet();
    String[] fileName = httpServletRequest.getParameterValues(
        "boardroomFileName");
    String[] saveName = httpServletRequest.getParameterValues(
        "boardroomSaveName");
    if (fileName != null)
      for (int i = 0; i < fileName.length; i++) {
        if (!"".equals(fileName[i])) {
          BdroomAppAccessoryPO bdroomAppAccessoryPO = 
            new BdroomAppAccessoryPO();
          bdroomAppAccessoryPO.setAccessoryname(fileName[i]);
          bdroomAppAccessoryPO.setAccessorysavename(saveName[i]);
          Accessory.add(bdroomAppAccessoryPO);
        } 
      }  
    boardroomApplyPO.setBdRoomAppAccessory(Accessory);
    boardroomApplyPO.setBoardLayout(httpServletRequest.getParameter("boardLayout"));
    return boardroomApplyPO;
  }
  
  private String getUserByOrg(String orgIdStr) {
    String orgIds = "";
    if (orgIdStr == null || orgIdStr.length() < 1)
      return orgIds; 
    String[] orgIdArr = orgIdStr.split(",");
    DbOpt dbopt = null;
    ResultSet rs = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < orgIdArr.length; i++) {
        String orgCode = dbopt.executeQueryToStr(
            "select ORGIDSTRING from ORG_ORGANIZATION where ORG_ID=" + 
            orgIdArr[i]);
        rs = dbopt.executeQuery("select EMP_ID from ORG_ORGANIZATION_USER where ORG_ID in (select ORG_ID from ORG_ORGANIZATION where ORGIDSTRING like '%" + 
            orgCode + "%')");
        if (rs != null) {
          while (rs.next()) {
            Object empId = rs.getObject(1);
            if (empId != null && 
              orgIds.indexOf(empId.toString()) < 0)
              orgIds = String.valueOf(orgIds) + empId.toString() + ","; 
          } 
          rs.close();
        } 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } 
    return orgIds;
  }
  
  private String getUserByGroup(String groupIdStr) {
    String userStr = "";
    if (groupIdStr == null || groupIdStr.length() < 1)
      return userStr; 
    String[] groupIdArr = groupIdStr.split(",");
    DbOpt dbopt = null;
    try {
      dbopt = new DbOpt();
      for (int i = 0; i < groupIdArr.length; i++) {
        String empIdStr = dbopt.executeQueryToStr(
            "select GROUPUSERSTRING from ORG_GROUP where GROUP_ID=" + 
            groupIdArr[i]);
        if (empIdStr != null && empIdStr.length() > 1)
          userStr = String.valueOf(userStr) + empIdStr; 
      } 
      dbopt.close();
    } catch (Exception e) {
      try {
        dbopt.close();
      } catch (SQLException sQLException) {}
    } finally {
      if (userStr != null && userStr.length() > 1)
        userStr = userStr.substring(1, userStr.length() - 1).replaceAll("$$", ","); 
    } 
    return userStr;
  }
  
  private boolean updateBoardRoomInfo(String boardRoomId) {
    boolean res = false;
    if (boardRoomId != null && !"null".equals(boardRoomId) && !"".equals(boardRoomId)) {
      DbOpt dbopt = null;
      try {
        dbopt = new DbOpt();
        dbopt.executeUpdate("update oa_boardroom set doornumber=1 where boardroomid=" + boardRoomId);
        dbopt.close();
      } catch (Exception e) {
        try {
          dbopt.close();
        } catch (SQLException ex) {
          ex.printStackTrace();
        } 
        e.printStackTrace();
      } 
    } 
    return res;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    if (httpServletRequest.getParameter("boardroomApplyId") != null && 
      !"".equals(httpServletRequest.getParameter("boardroomApplyId"))) {
      BoardRoomBD boardRoomBD = new BoardRoomBD();
      Long boardroomApplyId = Long.valueOf(httpServletRequest
          .getParameter(
            "boardroomApplyId"));
      boardRoomBD.deleteBoardroomApply(boardroomApplyId);
    } 
  }
}
