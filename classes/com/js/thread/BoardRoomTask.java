package com.js.thread;

import com.js.oa.routine.boardroom.bean.BoardRoomEJBBean;
import com.js.oa.routine.boardroom.service.BoardRoomBD;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BoardRoomTask {
  private Date nowDate = new Date();
  
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  private String databaseType = SystemCommon.getDatabaseType();
  
  public void meetingSet() {
    meetingSet("");
  }
  
  public void meetingSet(String applyId) {
    meetingSet(applyId, 30);
  }
  
  public void meetingSet(String applyId, int anpai) {
    List<Object[]> meetingList = new ArrayList();
    boolean all = false;
    String sql = "SELECT a.motif,r.meetingCircle,r.meetingLength,r.meetingDateBegin,r.meetingDateEnd,r.everyMeetingBegin,r.everyMeetingEnd,r.everyMeetingBeginTime,r.everyMeetingEndTime,a.boardroomapplyId,a.boardroomId,r.regularId FROM oa_boardroomregular r JOIN oa_boardroomapply a ON r.regularId=a.regularId WHERE STATUS=0 and r.ifoprall=0 ";
    if (!"".equals(applyId))
      sql = String.valueOf(sql) + " and a.boardroomapplyId=" + applyId; 
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
            Long.valueOf(rs.getLong(11)), 
            Long.valueOf(rs.getLong(12)) };
        meetingList.add(obj);
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    try {
      for (int i = 0; i < meetingList.size(); i++) {
        Date realBeginDate = null;
        Object[] obj = meetingList.get(i);
        Date meetingBeginDate = this.dateFormat.parse((obj[3] == null) ? "" : obj[3].toString());
        Date meetingEndDate = this.dateFormat.parse((obj[4] == null) ? "" : obj[4].toString());
        long meetingCBegin = meetingBeginDate.getTime();
        long meetingCEnd = meetingEndDate.getTime();
        if (anpai == 0) {
          anpai = (int)((meetingCEnd - meetingCBegin) / 86400000L);
          all = true;
        } 
        if (meetingCBegin > this.nowDate.getTime()) {
          realBeginDate = meetingBeginDate;
        } else {
          realBeginDate = this.nowDate;
        } 
        Date beginDate = getMeettingBeginDate(((Integer)obj[1]).intValue(), (String)obj[5], realBeginDate);
        while (beginDate.getTime() >= meetingCBegin && beginDate.getTime() <= meetingCEnd) {
          Long beginLong = Long.valueOf(beginDate.getTime());
          Long endLong = Long.valueOf(beginDate.getTime());
          if ((beginDate.getTime() - realBeginDate.getTime()) / 86400000L <= anpai) {
            System.out.println(obj[0] + "  会议安排");
            if (((Integer)obj[2]).intValue() == 1) {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
              endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
              addMeetingApply(beginDate, obj, 1, beginLong.longValue(), endLong.longValue());
            } else if (((Integer)obj[1]).intValue() == 1) {
              if (obj[5].equals(obj[6])) {
                beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
                endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
                addMeetingApply(beginDate, obj, 1, beginLong.longValue(), endLong.longValue());
              } else {
                beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((String)obj[7]).longValue() * 60L * 1000L);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(beginDate);
                int dayNum = 1;
                while (Integer.valueOf((String)obj[6]).intValue() != calendar.get(7) - 1) {
                  calendar.set(5, calendar.get(5) + 1);
                  dayNum++;
                } 
                endLong = Long.valueOf(calendar.getTime().getTime() + Long.valueOf((String)obj[8]).longValue() * 60L * 1000L);
                addMeetingApply(beginDate, obj, dayNum, beginLong.longValue(), endLong.longValue());
              } 
            } else if (((Integer)obj[1]).intValue() == 2) {
              if (obj[5].equals(obj[6])) {
                beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
                endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
                addMeetingApply(beginDate, obj, 1, beginLong.longValue(), endLong.longValue());
              } else {
                beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((String)obj[7]).longValue() * 60L * 1000L);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(beginDate);
                int maxDay = calendar.getActualMaximum(5);
                int endDay = Integer.valueOf((String)obj[6]).intValue();
                if (maxDay < endDay)
                  endDay = maxDay; 
                int dayNum = 1;
                while (endDay != calendar.get(5)) {
                  calendar.set(5, calendar.get(5) + 1);
                  dayNum++;
                } 
                endLong = Long.valueOf(calendar.getTime().getTime() + Long.valueOf((String)obj[8]).longValue() * 60L * 1000L);
                addMeetingApply(beginDate, obj, dayNum, beginLong.longValue(), endLong.longValue());
              } 
            } else if (obj[5].equals(obj[6])) {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[7]).intValue() * 60 * 1000))).toString()).longValue());
              endLong = Long.valueOf(beginDate.getTime() + Long.valueOf((new StringBuilder(String.valueOf(((Integer)obj[8]).intValue() * 60 * 1000))).toString()).longValue());
              addMeetingApply(beginDate, obj, 1, beginLong.longValue(), endLong.longValue());
            } else {
              beginLong = Long.valueOf(beginDate.getTime() + Long.valueOf((String)obj[7]).longValue() * 60L * 1000L);
              int meetingNum = 1;
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
                meetingNum++;
              } 
              endLong = Long.valueOf(calendar.getTime().getTime() + Long.valueOf((String)obj[8]).longValue() * 60L * 1000L);
              addMeetingApply(beginDate, obj, meetingNum, beginLong.longValue(), endLong.longValue());
            } 
          } 
          Date dateStr = this.dateFormat.parse(this.dateFormat.format(new Date(endLong.longValue() + 86400000L)));
          System.out.println(this.dateFormat.format(dateStr));
          beginDate = getMeettingBeginDate(((Integer)obj[1]).intValue(), (String)obj[5], dateStr);
        } 
        if (all)
          oprAll((String)obj[11]); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public void addMeetingApply(Date meetingDate, Object[] regular, int dayNum, long beginLong, long endLong) {
    boolean ifInsert = true;
    String sql = "SELECT boardroomapplyId FROM oa_boardroomapply WHERE meetingTime=" + beginLong + " AND regularApplyId=" + regular[9];
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      if (rs.next())
        ifInsert = false; 
      rs.close();
      if (ifInsert) {
        System.out.println(regular[0] + "  会议开始时间：" + this.dateTimeFormat.format(new Date(beginLong)) + "  会议结束时间：" + this.dateTimeFormat.format(new Date(endLong)));
        if (this.databaseType.contains("mysql")) {
          sql = "insert into oa_boardroomapply (boardroomid,motif,emcee,emceename,agenda,attendee,attendeeempid,starttime,endtime,destinedate,applyemp,applyempname,applyorg,applyorgname,status,msg,depict,attendeeleader,attendeeleaderid,nonvoting,nonvotingempid,noteperson,sendername,filenumber,bdroomapptypeid,issynch,controlnumber,doornumber,domain_id,boardequipment,linktelephone,points,applydate,personnum,boardroomcode,relproject_id,summary,notepersonid,ifRegular,meetingTime,regularApplyId) select boardroomid,motif,emcee,emceename,agenda,attendee,attendeeempid,starttime,endtime,destinedate,applyemp,applyempname,applyorg,applyorgname,status,msg,depict,attendeeleader,attendeeleaderid,nonvoting,nonvotingempid,noteperson,sendername,filenumber,bdroomapptypeid,issynch,controlnumber,doornumber,domain_id,boardequipment,linktelephone,points,applydate,personnum,boardroomcode,relproject_id,summary,notepersonid,1," + 





            
            beginLong + "," + regular[9] + " from oa_boardroomapply where boardroomapplyid=" + regular[9];
        } else {
          sql = "insert into oa_boardroomapply (boardroomapplyid,boardroomid,motif,emcee,emceename,agenda,attendee,attendeeempid,starttime,endtime,destinedate,applyemp,applyempname,applyorg,applyorgname,status,msg,depict,attendeeleader,attendeeleaderid,nonvoting,nonvotingempid,noteperson,sendername,filenumber,bdroomapptypeid,issynch,controlnumber,doornumber,domain_id,boardequipment,linktelephone,points,applydate,personnum,boardroomcode,relproject_id,summary,notepersonid,ifRegular,meetingTime,regularApplyId) select hibernate_sequence.nextval,boardroomid,motif,emcee,emceename,agenda,attendee,attendeeempid,starttime,endtime,destinedate,applyemp,applyempname,applyorg,applyorgname,status,msg,depict,attendeeleader,attendeeleaderid,nonvoting,nonvotingempid,noteperson,sendername,filenumber,bdroomapptypeid,issynch,controlnumber,doornumber,domain_id,boardequipment,linktelephone,points,applydate,personnum,boardroomcode,relproject_id,summary,notepersonid,1," + 





            
            beginLong + "," + regular[9] + " from oa_boardroomapply where boardroomapplyid=" + regular[9];
        } 
        String maxId = "";
        base.executeUpdate(sql);
        sql = "select max(boardroomapplyid) from oa_boardroomapply";
        rs = base.executeQuery(sql);
        if (rs.next())
          maxId = rs.getString(1); 
        rs.close();
        if (this.databaseType.contains("mysql")) {
          sql = "insert into oa_boardroom_meetingtime (applyid,meetingtime,starttime,endtime,sortnum,status,descriptions,meetingDay,beginLong,endLong) values(" + 
            maxId + ",'" + this.dateFormat.format(meetingDate) + "'," + (((Integer)regular[7]).intValue() * 60) + "," + (((Integer)regular[8]).intValue() * 60) + ",0,0,''," + dayNum + "," + beginLong + "," + endLong + ")";
        } else {
          sql = "insert into oa_boardroom_meetingtime (id,applyid,meetingtime,starttime,endtime,sortnum,status,descriptions,meetingDay,beginLong,endLong) values(hibernate_sequence.nextval," + 
            maxId + ",to_date('" + this.dateFormat.format(meetingDate) + "','yyyy-mm-dd')" + 
            "," + (((Integer)regular[7]).intValue() * 60) + "," + (((Integer)regular[8]).intValue() * 60) + ",0,0,''," + dayNum + "," + beginLong + "," + endLong + ")";
        } 
        base.executeUpdate(sql);
        String meetingMax = "0";
        sql = "select max(id) from oa_boardroom_meetingtime";
        rs = base.executeQuery(sql);
        if (rs.next())
          meetingMax = rs.getString(1); 
        rs.close();
        String[] regulars = (new BoardRoomEJBBean()).boardroomUser((String)regular[9]);
        boolean flag = (new BoardRoomBD()).applyTime((String)regular[10], maxId, Long.valueOf(beginLong), Long.valueOf(endLong), (String)regular[11]);
        String url = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyView&boardroomApplyId=" + maxId + "&boardroomName=" + regulars[7] + 
          "&type=view&meetingId=" + meetingMax + "&executeStatus=false";
        String chuxiUser = String.valueOf(regulars[0]) + regulars[1] + "$" + regulars[2] + "$" + regulars[3] + regulars[5];
        String liexiUser = regulars[4];
        chuxiUser = chuxiUser.toLowerCase().replace("null", "").replace("$$", ",").replace("$", "");
        liexiUser = liexiUser.toLowerCase().replace("null", "").replace("$$", ",").replace("$", "");
        if (liexiUser.length() > 0)
          RemindUtil.sendMessageToUsers2("您列席的" + regular[0] + "已被安排！", url, liexiUser, "Meeting", new Date(beginLong - beginLong % 8640000L - 72000000L), 
              this.dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(maxId), 1, 1, 0); 
        if (chuxiUser.length() > 0)
          RemindUtil.sendMessageToUsers2("您出席的" + regular[0] + "已被安排！", url, chuxiUser, "Meeting", new Date(beginLong - beginLong % 8640000L - 72000000L), 
              this.dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(maxId), 1, 1, 0); 
        if (flag) {
          url = "/jsoa/BoardRoomAction.do?action=selectBoardroomApplyModify&boardroomApplyId=" + maxId + "&boardroomName=" + regulars[7] + "&type=view";
          String userIds = (String.valueOf(regulars[0]) + "$" + regulars[2] + "$" + regulars[5]).replace("null", "").replace("$$", ",").replace("$", "");
          if (userIds.length() > 0)
            RemindUtil.sendMessageToUsers2(regular[0] + "会议时间段有冲突，请及时调整时间！", url, userIds, "Meeting", new Date(beginLong - beginLong % 7200000L - 43200000L), 
                this.dateFormat.parse("2050-01-01"), "系统提醒", Long.valueOf(0L), 1, 1, 0); 
        } 
      } else {
        System.out.println(String.valueOf(this.dateTimeFormat.format(new Date(beginLong))) + " 到 " + this.dateTimeFormat.format(new Date(endLong)) + " 的会议 " + regular[0] + " 已安排，" + 
            "此次不再进行同步。");
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
  }
  
  public Date getMeettingBeginDate(int flag, String beginDate, Date nowDate) {
    try {
      Date returnDate = new Date();
      if (flag == 1) {
        returnDate = week(beginDate, nowDate);
      } else if (flag == 2) {
        returnDate = month(beginDate, nowDate);
      } else {
        returnDate = year(beginDate, nowDate);
      } 
      return this.dateFormat.parse(this.dateFormat.format(returnDate));
    } catch (ParseException e) {
      e.printStackTrace();
      return nowDate;
    } 
  }
  
  public Date week(String beginDate, Date nowDate) {
    int getweek = Integer.valueOf(beginDate).intValue() + 1;
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(nowDate);
    int weekNum = calendar.get(7);
    calendar.set(7, getweek);
    if (weekNum > getweek)
      calendar.set(5, calendar.get(5) + 7); 
    return calendar.getTime();
  }
  
  public Date month(String beginDate, Date nowDate) {
    int getMonth = Integer.valueOf(beginDate).intValue();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(nowDate);
    int dayNum = calendar.get(5);
    if (dayNum > getMonth) {
      int month = calendar.get(2);
      calendar.set(2, month + 1);
      calendar.set(5, 1);
      int maxDay = calendar.getActualMaximum(5);
      if (maxDay < getMonth)
        getMonth = maxDay; 
      calendar.set(5, getMonth);
    } else {
      int maxDay = calendar.getActualMaximum(5);
      if (maxDay < getMonth)
        getMonth = maxDay; 
      calendar.set(5, getMonth);
    } 
    return calendar.getTime();
  }
  
  public Date year(String beginDate, Date nowDate) {
    String[] monthDay = beginDate.split("-");
    int month = Integer.valueOf(monthDay[0]).intValue() - 1;
    int day = Integer.valueOf(monthDay[1]).intValue();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(nowDate);
    int monthNum = calendar.get(2);
    int dayNum = calendar.get(5);
    if (month > monthNum) {
      calendar.set(2, month);
      calendar.set(5, day);
    } else if (month == monthNum) {
      if (day >= dayNum) {
        calendar.set(5, day);
      } else {
        int year = calendar.get(1);
        calendar.set(1, year + 1);
        calendar.set(2, month);
        calendar.set(5, day);
      } 
    } else {
      int year = calendar.get(1);
      calendar.set(1, year + 1);
      calendar.set(2, month);
      calendar.set(5, day);
    } 
    return calendar.getTime();
  }
  
  public int oprAll(String regularId) {
    int num = 0;
    String sqlAll = "UPDATE oa_boardroomregular SET ifoprall=1 WHERE regularId=" + regularId;
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      num = base.executeUpdate(sqlAll);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return num;
  }
}
