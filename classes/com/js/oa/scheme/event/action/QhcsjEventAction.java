package com.js.oa.scheme.event.action;

import com.js.message.RealTimeUtil;
import com.js.oa.scheme.event.service.EventBD;
import com.js.oa.scheme.event.vo.DayData;
import com.js.oa.scheme.event.vo.EventVO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.system.manager.service.ManagerService;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.system.util.StaticParam;
import com.js.util.config.SystemCommon;
import com.js.util.util.ConversionString;
import com.js.util.util.FillBean;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.hibernate.HibernateException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class QhcsjEventAction extends Action {
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    HttpSession session = httpServletRequest.getSession(true);
    EventActionForm eventActionForm = (EventActionForm)actionForm;
    EventBD eventBD = new EventBD();
    String action = httpServletRequest.getParameter("action");
    String saveType = httpServletRequest.getParameter("saveType");
    Long userId = new Long(session.getAttribute("userId").toString());
    String empID = httpServletRequest.getParameter("empID");
    if (empID != null && !"".equals(empID) && !"null".equals(empID)) {
      Long empIDValue = new Long(empID.toString());
      userId = empIDValue;
      httpServletRequest.setAttribute("showId", "show");
    } 
    Long orgId = new Long(session.getAttribute("orgId").toString());
    String userName = session.getAttribute("userName").toString();
    String orgName = session.getAttribute("orgName").toString();
    Long domainId = (session.getAttribute("domainId") == null) ? 
      Long.valueOf("0") : 
      Long.valueOf(session.getAttribute("domainId").toString());
    if ("addEvent".equals(action)) {
      String searchContent = httpServletRequest.getParameter("searchContent");
      String toAction = httpServletRequest.getParameter("toAction");
      String relProject = httpServletRequest.getParameter("relProject");
      httpServletRequest.setAttribute("relProject", relProject);
      httpServletRequest.setAttribute("toAction", toAction);
      httpServletRequest.setAttribute("time", 
          httpServletRequest
          .getParameter("time"));
      String pagerOffset = httpServletRequest.getParameter("pager.offset");
      searchContent = "searchContent=" + searchContent + "&pager.offset=" + 
        pagerOffset + "&action=searchEvent";
      httpServletRequest.setAttribute("search", searchContent);
      EventVO eventVO = (EventVO)FillBean.transformOneToOne(
          eventActionForm, EventVO.class);
      Integer eventFullDay = eventActionForm.getEventFullDay();
      Integer eventRemind = eventActionForm.getEventRemind();
      Long eventBeginDate = new Long((new Date(httpServletRequest
            .getParameter("start_date"))).getTime());
      Long eventEndDate = new Long((new Date(httpServletRequest
            .getParameter("end_date")))
          .getTime());
      Integer eventBeginTime = new Integer(httpServletRequest
          .getParameter("eventBeginTime"));
      Integer eventEndTime = new Integer(httpServletRequest.getParameter(
            "eventEndTime"));
      Long eventRemindTime = Long.valueOf(httpServletRequest.getParameter(
            "eventRemindTime"));
      if (!eventFullDay.equals(new Integer(1))) {
        eventVO.setEventBeginTime(eventBeginTime);
        eventVO.setEventEndTime(eventEndTime);
      } 
      if (eventRemind.equals(new Integer(1))) {
        eventVO.setEventRemindTime(eventRemindTime);
        if (eventActionForm.getPopRemindMode().intValue() != 1 && 
          eventActionForm.getNoteRemindMode().intValue() != 1)
          eventVO.setPopRemindMode(new Integer(1)); 
      } else {
        eventVO.setEventRemindTime(new Long(0L));
        eventVO.setPopRemindMode(new Integer(0));
        eventVO.setNoteRemindMode(new Integer(0));
      } 
      eventVO.setEventEmpId(userId);
      eventVO.setEventEmpName(userName);
      eventVO.setOnTimeMode(new Integer(0));
      eventVO.setEventBeginDate(eventBeginDate);
      eventVO.setEventEndDate(eventEndDate);
      eventVO.setEventDomainId(domainId);
      eventVO.setRelProjectId(Long.valueOf(httpServletRequest.getParameter("relproject")));
      eventVO.setOpenEvent((httpServletRequest.getParameter("openEvent") == null) ? 
          "0" : httpServletRequest.getParameter("openEvent"));
      boolean result = eventBD.addEvent(eventVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        if (eventVO.getAttendEmp() != null && 
          !eventVO.getAttendEmp().trim().equals("")) {
          ManagerService mbd = new ManagerService();
          String receivers = mbd.getEmployeesAccounts((
              new ConversionString(eventVO.getAttendEmp()))
              .getUserIdString());
          if (!receivers.equals("")) {
            Date date = new Date();
            String title = "[" + userName + "]" + 
              eventVO.getEventTitle() + "(" + (
              date.getMonth() + 1) + "-" + 
              date.getDate() + ")";
            RealTimeUtil util = new RealTimeUtil();
            util.sendNotify(receivers, "新共享日历提醒", title, "0", "0");
          } 
        } 
        eventActionForm.reset(actionMapping, httpServletRequest);
        httpServletRequest.setAttribute("start", 
            httpServletRequest
            .getParameter("start"));
        httpServletRequest.setAttribute("end", 
            httpServletRequest
            .getParameter("end"));
        httpServletRequest.setAttribute("ymd", 
            httpServletRequest
            .getParameter("ymd"));
        return actionMapping.findForward("event_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        if (eventVO.getAttendEmp() != null && 
          !eventVO.getAttendEmp().trim().equals("")) {
          ManagerService mbd = new ManagerService();
          String receivers = mbd.getEmployeesAccounts((
              new ConversionString(eventVO.getAttendEmp()))
              .getUserIdString());
          if (!receivers.equals("")) {
            Date date = new Date();
            String title = "[" + userName + "]" + 
              eventVO.getEventTitle() + "(" + (
              date.getMonth() + 1) + "-" + 
              date.getDate() + ")";
            RealTimeUtil util = new RealTimeUtil();
            util.sendNotify(receivers, "新共享日历提醒", title, "0", "0");
          } 
        } 
        return actionMapping.findForward("event_saveAndExit");
      } 
    } 
    if ("selectAllEvent".equals(action)) {
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null)
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      eventBD.setVolume(pageSize);
      List list = null;
      String tag = "goto_AllEvent";
      if (httpServletRequest.getParameter("toUserId") != null) {
        String toUserId = httpServletRequest.getParameter("toUserId");
        list = eventBD.selectAllEvent(userId, 
            new Integer(currentPage), 
            domainId, toUserId);
        httpServletRequest.setAttribute("toUserId", toUserId);
        httpServletRequest.setAttribute("pageParameters", "action,toUserId");
        tag = "allEvent";
      } else {
        list = eventBD.selectAllEvent(userId, 
            new Integer(currentPage), 
            domainId);
        httpServletRequest.setAttribute("pageParameters", "action");
      } 
      String recordCount = String.valueOf(eventBD.getRecordCount());
      httpServletRequest.setAttribute("allEventList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("Year", 
          httpServletRequest
          .getParameter("Year"));
      httpServletRequest.setAttribute("Month", 
          httpServletRequest
          .getParameter("Month"));
      httpServletRequest.setAttribute("Day", 
          httpServletRequest
          .getParameter("Day"));
      return actionMapping.findForward(tag);
    } 
    if ("selectSingleEvent".equals(action)) {
      Long eventId = Long.valueOf(httpServletRequest.getParameter(
            "eventId"));
      EventVO eventVO = new EventVO();
      String re = eventBD.getByIds(eventId);
      if ("Y".equals(re)) {
        String isEdit = (httpServletRequest.getParameter("isEdit") == null) ? 
          "" : httpServletRequest.getParameter("isEdit");
        String evenEmpId = (httpServletRequest.getParameter("empId") == null) ? 
          "" : httpServletRequest.getParameter("empId");
        if (evenEmpId.length() != 0) {
          eventVO = eventBD.selectSingleEvent(eventId, 
              Long.valueOf(evenEmpId), 
              domainId);
          httpServletRequest.setAttribute("edit", "1");
        } else {
          eventVO = eventBD.selectSingleEvent(eventId, userId, 
              domainId);
        } 
        eventActionForm.setEventTitle(eventVO.getEventTitle());
        eventActionForm.setEventAddress(eventVO.getEventAddress());
        eventActionForm.setEventFullDay(eventVO.getEventFullDay());
        eventActionForm.setAttendEmp(eventVO.getAttendEmp());
        eventActionForm.setAttendName(eventVO.getAttendName());
        eventActionForm.setEventRemind(eventVO.getEventRemind());
        eventActionForm.setEventContent(eventVO.getEventContent());
        eventActionForm.setPopRemindMode(eventVO.getPopRemindMode());
        eventActionForm.setNoteRemindMode(eventVO.getNoteRemindMode());
        String eventBeginDate = eventVO.getEventBeginDateFormat();
        String eventEndDate = eventVO.getEventEndDateFormat();
        Integer eventBeginTime = eventVO.getEventBeginTime();
        Integer eventEndTime = eventVO.getEventEndTime();
        Long eventRemindTime = eventVO.getEventRemindTime();
        Boolean canModify = eventVO.getCanModify();
        httpServletRequest.setAttribute("eventBeginDate", eventBeginDate);
        httpServletRequest.setAttribute("eventEmpName", eventVO.getEventEmpName());
        httpServletRequest.setAttribute("eventEmpId", eventVO.getEventEmpId());
        httpServletRequest.setAttribute("eventEndDate", eventEndDate);
        httpServletRequest.setAttribute("eventBeginTime", eventBeginTime);
        httpServletRequest.setAttribute("eventEndTime", eventEndTime);
        httpServletRequest.setAttribute("eventRemindTime", eventRemindTime);
        httpServletRequest.setAttribute("canModify", canModify);
        httpServletRequest.setAttribute("proID", eventVO.getRelProjectId().toString());
        httpServletRequest.setAttribute("openEvent", eventVO.getOpenEvent());
      } 
      httpServletRequest.setAttribute("re", re);
      return actionMapping.findForward("goto_AllEventUpdate");
    } 
    if ("modifyEvent".equals(action)) {
      String fromdesktop = httpServletRequest.getParameter("fromdesktop");
      httpServletRequest.setAttribute("fromdesktop", fromdesktop);
      EventVO eventVO = (EventVO)FillBean.transformOneToOne(
          eventActionForm, EventVO.class);
      String searchContent = httpServletRequest.getParameter(
          "searchContent");
      String pagerOffset = httpServletRequest.getParameter("pager.offset");
      searchContent = "searchContent=" + searchContent + "&pager.offset=" + 
        pagerOffset + "&action=searchEvent";
      httpServletRequest.setAttribute("search", searchContent);
      Long eventId = new Long(httpServletRequest.getParameter("eventId"));
      Integer eventFullDay = eventActionForm.getEventFullDay();
      Integer eventRemind = eventActionForm.getEventRemind();
      Long eventBeginDate = new Long((new Date(httpServletRequest
            .getParameter("start_date"))).getTime());
      Long eventEndDate = new Long((new Date(httpServletRequest
            .getParameter("end_date")))
          .getTime());
      Integer eventBeginTime = new Integer(httpServletRequest
          .getParameter("eventBeginTime"));
      Integer eventEndTime = new Integer(httpServletRequest.getParameter(
            "eventEndTime"));
      Long eventRemindTime = Long.valueOf(httpServletRequest.getParameter(
            "eventRemindTime"));
      if (!eventFullDay.equals(new Integer(1))) {
        eventVO.setEventBeginTime(eventBeginTime);
        eventVO.setEventEndTime(eventEndTime);
      } 
      if (eventRemind.equals(new Integer(1))) {
        eventVO.setEventRemindTime(eventRemindTime);
        if (eventActionForm.getPopRemindMode().intValue() != 1 && 
          eventActionForm.getNoteRemindMode().intValue() != 1)
          eventVO.setPopRemindMode(new Integer(1)); 
      } else {
        eventVO.setPopRemindMode(new Integer(0));
        eventVO.setNoteRemindMode(new Integer(0));
      } 
      Long eventEmpId = userId;
      if (httpServletRequest.getParameter("eventEmpId") != null)
        eventEmpId = Long.valueOf(httpServletRequest.getParameter("eventEmpId")); 
      String eventEmpName = userName;
      if (httpServletRequest.getParameter("eventEmpName") != null)
        eventEmpName = httpServletRequest.getParameter("eventEmpName"); 
      eventVO.setEventEmpId(eventEmpId);
      eventVO.setEventEmpName(eventEmpName);
      eventVO.setOnTimeMode(new Integer(0));
      eventVO.setEventId(eventId);
      eventVO.setEventBeginDate(eventBeginDate);
      eventVO.setEventEndDate(eventEndDate);
      String proId = httpServletRequest.getParameter("relproject");
      eventVO.setRelProjectId(Long.valueOf(Long.parseLong(proId)));
      eventVO.setOpenEvent((httpServletRequest.getParameter("openEvent") == null) ? 
          "0" : httpServletRequest.getParameter("openEvent"));
      boolean result = eventBD.modifyEvent(eventVO);
      if (result && httpServletRequest.getParameter("tijiaoEmpId") != null) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        RemindUtil.sendMessageToUsers("您的日程已经被上级修改！", "/jsoa/eventAction.do?action=selectSingleEvent&isEdit=1&eventId=" + eventVO.getEventId(), 
            (String)eventEmpId, "Event", new Date(), format.parse("2050-01-01 23:59:59"));
      } 
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType)) {
        String re = eventBD.getByIds(eventId);
        httpServletRequest.setAttribute("eventBeginDate", 
            eventBeginDate);
        httpServletRequest.setAttribute("eventEndDate", eventEndDate);
        httpServletRequest.setAttribute("eventBeginTime", 
            eventBeginTime);
        httpServletRequest.setAttribute("eventEndTime", eventEndTime);
        httpServletRequest.setAttribute("eventRemindTime", 
            eventRemindTime);
        httpServletRequest.setAttribute("re", re);
        return actionMapping.findForward("event_updateAndExit");
      } 
    } 
    if ("deleteEvent".equals(action)) {
      String eventIdStr = httpServletRequest.getParameter("eventId");
      String searchContent = httpServletRequest.getParameter(
          "searchContent");
      if (eventIdStr != null && !"".equals(eventIdStr)) {
        Long eventId = Long.valueOf(httpServletRequest.getParameter(
              "eventId"));
        boolean result = eventBD.deleteEvent(userId, eventId);
        if (!result)
          return actionMapping.findForward("failure"); 
        view(httpServletRequest, userId, eventBD, searchContent, 
            domainId);
        return actionMapping.findForward("deleteEvent");
      } 
      view(httpServletRequest, userId, eventBD, searchContent, 
          domainId);
      return actionMapping.findForward("deleteEvent");
    } 
    if ("deleteBatchEvent".equals(action)) {
      String eventIds = httpServletRequest.getParameter("ids");
      String searchContent = httpServletRequest.getParameter(
          "searchContent");
      if (httpServletRequest.getParameter("toUserId") == null) {
        if (eventIds == null || "".equals(eventIds)) {
          view(httpServletRequest, userId, eventBD, searchContent, 
              domainId);
          return actionMapping.findForward("deleteEvent");
        } 
        boolean bool = eventBD.deleteBatchEvent(userId, eventIds);
        if (!bool)
          return actionMapping.findForward("failure"); 
        view(httpServletRequest, userId, eventBD, searchContent, 
            domainId);
        return actionMapping.findForward("deleteEvent");
      } 
      boolean result = false;
      if (eventIds != null && !"".equals(eventIds))
        result = eventBD.deleteBatchEvent(userId, eventIds); 
      ActionForward forward = new ActionForward();
      Date date = new Date();
      String year = (new StringBuilder(String.valueOf(date.getYear() + 1900))).toString();
      String month = (new StringBuilder(String.valueOf(date.getMonth() + 1))).toString();
      String day = (new StringBuilder(String.valueOf(date.getDay()))).toString();
      forward.setPath("/eventAction.do?action=selectAllEvent&toUserId=" + httpServletRequest.getParameter("toUserId") + 
          "&Year=" + year + "&Month=" + month + "&Day=" + day);
      return forward;
    } 
    if ("deleteAllEvent".equals(action)) {
      boolean result = eventBD.deleteAllEvent(userId);
      if (!result)
        return actionMapping.findForward("failute"); 
      try {
        httpServletResponse.sendRedirect(
            "/jsoa/eventAction.do?action=selectAllEvent");
      } catch (IOException ex) {
        System.err.println("IO Error(deleteAllEvent):" + 
            ex.getMessage());
      } 
    } 
    if ("searchEvent".equals(action)) {
      String start_date = httpServletRequest.getParameter("start_date");
      String end_date = httpServletRequest.getParameter("end_date");
      String eventType = httpServletRequest.getParameter("eventType");
      String isDate = httpServletRequest.getParameter("isDate");
      if (eventType == null || "".equals(eventType) || 
        "null".equals(eventType))
        eventType = null; 
      long start_time = 0L;
      long end_time = 0L;
      String _start_date = null;
      String _end_date = null;
      Date now = new Date();
      Calendar cal = Calendar.getInstance();
      if (start_date == null || "".equals(start_date) || 
        "null".equals(start_date)) {
        int y = now.getYear() + 1900;
        int m = now.getMonth() + 1;
        int d = now.getDate();
        start_date = String.valueOf(y) + "/" + m + "/" + d;
        _start_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + (
          (d < 10) ? ("0" + d) : d);
        start_time = (new Date(cal.get(1) - 1900, 
            cal.get(2), 
            cal.get(5)))
          .getTime();
      } else {
        int y = Integer.parseInt(start_date.substring(0, 
              start_date.indexOf("/")));
        int m = Integer.parseInt(start_date.substring(start_date
              .indexOf("/") + 1, start_date.lastIndexOf("/")));
        int d = Integer.parseInt(start_date.substring(start_date
              .lastIndexOf("/") + 1));
        _start_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + (
          (d < 10) ? ("0" + d) : d);
        cal.set(1, y);
        cal.set(2, m - 1);
        cal.set(5, d);
        start_time = (new Date(cal.get(1) - 1900, 
            cal.get(2), 
            cal.get(5)))
          .getTime();
      } 
      if (end_date == null || "".equals(end_date) || 
        "null".equals(end_date)) {
        int y = now.getYear() + 1900;
        int m = now.getMonth() + 1;
        int d = now.getDate();
        end_date = String.valueOf(y) + "/" + m + "/" + d;
        _end_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + (
          (d < 10) ? ("0" + d) : d);
        end_time = (new Date(cal.get(1) - 1900, 
            cal.get(2), 
            cal.get(5)))
          .getTime();
      } else {
        int y = Integer.parseInt(end_date.substring(0, 
              end_date.indexOf("/")));
        int m = Integer.parseInt(end_date.substring(end_date.indexOf(
                "/") + 1, end_date.lastIndexOf("/")));
        int d = Integer.parseInt(end_date.substring(end_date
              .lastIndexOf("/") + 1));
        _end_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + (
          (d < 10) ? ("0" + d) : d);
        cal.set(1, y);
        cal.set(2, m - 1);
        cal.set(5, d);
        end_time = (new Date(cal.get(1) - 1900, 
            cal.get(2), 
            cal.get(5)))
          .getTime();
      } 
      int pageSize = 15;
      int offset = 0;
      if (httpServletRequest.getParameter("pager.offset") != null && 
        !"null".equals(httpServletRequest.getParameter("pager.offset")))
        offset = Integer.parseInt(httpServletRequest.getParameter(
              "pager.offset")); 
      int currentPage = offset / pageSize + 1;
      eventBD.setVolume(pageSize);
      String eventContent = httpServletRequest.getParameter(
          "eventContent");
      String eventTitle = httpServletRequest.getParameter("searchContent");
      List list = null;
      String tag = "goto_AllEvent";
      if (httpServletRequest.getParameter("toUserId") != null) {
        String toUserId = httpServletRequest.getParameter("toUserId");
        if (!"1".equals(isDate)) {
          list = eventBD.searchEvent(userId, new Integer(currentPage), 
              eventContent, domainId, 
              (Long)null, 
              (Long)null, 
              (String)null, 
              (String)null, 
              eventType, eventTitle, toUserId);
        } else {
          list = eventBD.searchEvent(userId, new Integer(currentPage), 
              eventContent, domainId, 
              new Long(start_time), 
              new Long(end_time), _start_date, 
              _end_date, eventType, eventTitle, toUserId);
        } 
        httpServletRequest.setAttribute("toUserId", toUserId);
        httpServletRequest.setAttribute("pageParameters", 
            "action,toUserId,eventContent,searchContent,start_date,end_date,eventType,isDate");
        tag = "allEvent";
      } else {
        if (!"1".equals(isDate)) {
          list = eventBD.searchEvent(userId, new Integer(currentPage), 
              eventContent, domainId, 
              null, 
              null, 
              null, 
              null, 
              eventType, eventTitle);
        } else {
          list = eventBD.searchEvent(userId, new Integer(currentPage), 
              eventContent, domainId, 
              new Long(start_time), 
              new Long(end_time), _start_date, 
              _end_date, eventType, eventTitle);
        } 
        httpServletRequest.setAttribute("pageParameters", 
            "action,searchContent,eventContent,start_date,end_date,eventType,isDate");
      } 
      String recordCount = String.valueOf(eventBD.getRecordCount());
      httpServletRequest.setAttribute("allEventList", list);
      httpServletRequest.setAttribute("recordCount", recordCount);
      httpServletRequest.setAttribute("maxPageItems", 
          String.valueOf(pageSize));
      httpServletRequest.setAttribute("start_date", start_date);
      httpServletRequest.setAttribute("end_date", end_date);
      httpServletRequest.setAttribute("eventType", eventType);
      httpServletRequest.setAttribute("isDate", isDate);
      httpServletRequest.setAttribute("evenTitle", eventTitle);
      httpServletRequest.setAttribute("eventContent", eventContent);
      return actionMapping.findForward(tag);
    } 
    if ("addEventEcho".equals(action)) {
      String searchContent = httpServletRequest.getParameter(
          "searchContent");
      String pagerOffset = httpServletRequest.getParameter(
          "pager.offset");
      searchContent = "searchContent=" + searchContent + 
        "&pager.offset=" + pagerOffset + 
        "&action=searchEvent";
      httpServletRequest.setAttribute("search", searchContent);
      String toAction = httpServletRequest.getParameter("toAction");
      httpServletRequest.setAttribute("toAction", toAction);
      httpServletRequest.setAttribute("time", 
          httpServletRequest
          .getParameter("time"));
      EventVO eventVO = (EventVO)FillBean.transformOneToOne(
          eventActionForm, EventVO.class);
      Integer eventBeginTime = new Integer(httpServletRequest
          .getParameter(
            "hidden_eventBeginTime"));
      eventVO.setEventBeginTime(eventBeginTime);
      Integer eventEndTime = new Integer(httpServletRequest.getParameter(
            "hidden_eventEndTime"));
      eventVO.setEventEndTime(eventEndTime);
      Long eventLastTime = new Long(httpServletRequest.getParameter(
            "hidden_eventLastTime"));
      eventVO.setEventLastTime(eventLastTime);
      Integer onTimeMode = new Integer(httpServletRequest.getParameter(
            "onTimeMode"));
      eventVO.setOnTimeMode(onTimeMode);
      if (onTimeMode != null && onTimeMode.equals(new Integer(1))) {
        String day = httpServletRequest.getParameter("day");
        eventVO.setOnTimeContent(day);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(2))) {
        String week7 = httpServletRequest.getParameter("week7");
        String weekA = null;
        if ("7".equals(week7)) {
          weekA = "1";
        } else {
          weekA = "0";
        } 
        String week1 = httpServletRequest.getParameter("week1");
        String weekB = null;
        if ("1".equals(week1)) {
          weekB = String.valueOf(weekA) + "1";
        } else {
          weekB = String.valueOf(weekA) + "0";
        } 
        String week2 = httpServletRequest.getParameter("week2");
        String weekC = null;
        if ("2".equals(week2)) {
          weekC = String.valueOf(weekB) + "1";
        } else {
          weekC = String.valueOf(weekB) + "0";
        } 
        String week3 = httpServletRequest.getParameter("week3");
        String weekD = null;
        if ("3".equals(week3)) {
          weekD = String.valueOf(weekC) + "1";
        } else {
          weekD = String.valueOf(weekC) + "0";
        } 
        String week4 = httpServletRequest.getParameter("week4");
        String weekE = null;
        if ("4".equals(week4)) {
          weekE = String.valueOf(weekD) + "1";
        } else {
          weekE = String.valueOf(weekD) + "0";
        } 
        String week5 = httpServletRequest.getParameter("week5");
        String weekF = null;
        if ("5".equals(week5)) {
          weekF = String.valueOf(weekE) + "1";
        } else {
          weekF = String.valueOf(weekE) + "0";
        } 
        String week6 = httpServletRequest.getParameter("week6");
        String weekG = null;
        if ("6".equals(week6)) {
          weekG = String.valueOf(weekF) + "1";
        } else {
          weekG = String.valueOf(weekF) + "0";
        } 
        eventVO.setOnTimeContent(weekG);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(3))) {
        String month_day = httpServletRequest.getParameter("month_day");
        eventVO.setOnTimeContent(month_day);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(4))) {
        String year_month = httpServletRequest.getParameter(
            "year_month");
        String year_month_day = httpServletRequest.getParameter(
            "year_month_day");
        eventVO.setOnTimeContent(String.valueOf(year_month) + "$" + year_month_day);
      } 
      Integer echoMode = new Integer(httpServletRequest.getParameter(
            "echoMode"));
      eventVO.setEchoMode(echoMode);
      if (echoMode != null && echoMode.equals(new Integer(0))) {
        Date echoEndTime = new Date(httpServletRequest.getParameter(
              "end_date"));
        eventVO.setEchoEndTime(echoEndTime);
      } 
      if (echoMode != null && echoMode.equals(new Integer(1))) {
        Integer echoModeTime = Integer.valueOf(httpServletRequest
            .getParameter("echoModeTime"));
        eventVO.setEchoCounter(echoModeTime);
      } 
      Date echoBeginTime = new Date(httpServletRequest.getParameter(
            "start_date"));
      eventVO.setEchoBeginTime(echoBeginTime);
      String attendEmp = httpServletRequest.getParameter("attendEmp");
      eventVO.setAttendEmp(attendEmp);
      Long eventRemindTime = Long.valueOf(httpServletRequest.getParameter(
            "eventRemindTime"));
      Integer eventRemind = eventActionForm.getEventRemind();
      if (eventRemind.equals(new Integer(1))) {
        eventVO.setEventRemindTime(eventRemindTime);
        if (eventActionForm.getPopRemindMode().intValue() != 1 && 
          eventActionForm.getNoteRemindMode().intValue() != 1)
          eventVO.setPopRemindMode(new Integer(1)); 
      } else {
        eventVO.setPopRemindMode(new Integer(0));
        eventVO.setNoteRemindMode(new Integer(0));
      } 
      eventVO.setEventEmpId(userId);
      eventVO.setEventEmpName(userName);
      eventVO.setEventDomainId(domainId);
      String proId = httpServletRequest.getParameter("relproject");
      eventVO.setRelProjectId(new Long(proId));
      boolean result = eventBD.addEvent(eventVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("saveAndContinue".equals(saveType)) {
        if (eventVO.getAttendEmp() != null && 
          !eventVO.getAttendEmp().trim().equals("")) {
          ManagerService mbd = new ManagerService();
          String receivers = mbd.getEmployeesAccounts((
              new ConversionString(eventVO.getAttendEmp()))
              .getUserIdString());
          if (!receivers.equals("")) {
            Date date = new Date();
            String title = "[" + userName + "]" + 
              eventVO.getEventTitle() + "(" + (
              date.getMonth() + 1) + "-" + 
              date.getDate() + ")";
            RealTimeUtil util = new RealTimeUtil();
            util.sendNotify(receivers, "新共享日历提醒", title, "0", "0");
          } 
        } 
        eventActionForm.reset(actionMapping, httpServletRequest);
        httpServletRequest.setAttribute("start", 
            httpServletRequest
            .getParameter("start"));
        httpServletRequest.setAttribute("end", 
            httpServletRequest
            .getParameter("end"));
        httpServletRequest.setAttribute("ymd", 
            httpServletRequest
            .getParameter("ymd"));
        return actionMapping.findForward("event_echo_saveAndContinue");
      } 
      if ("saveAndExit".equals(saveType)) {
        if (eventVO.getAttendEmp() != null && 
          !eventVO.getAttendEmp().trim().equals("")) {
          ManagerService mbd = new ManagerService();
          String receivers = mbd.getEmployeesAccounts((
              new ConversionString(eventVO.getAttendEmp()))
              .getUserIdString());
          if (!receivers.equals("")) {
            Date date = new Date();
            String title = "[" + userName + "]" + 
              eventVO.getEventTitle() + "(" + (
              date.getMonth() + 1) + "-" + 
              date.getDate() + ")";
            RealTimeUtil util = new RealTimeUtil();
            util.sendNotify(receivers, "新共享日历提醒", title, "0", "0");
          } 
        } 
        return actionMapping.findForward("event_echo_saveAndExit");
      } 
    } 
    if ("modifyEventEcho".equals(action)) {
      EventVO eventVO = (EventVO)FillBean.transformOneToOne(
          eventActionForm, EventVO.class);
      Long eventId = new Long(httpServletRequest.getParameter("eventId"));
      eventVO.setEventId(eventId);
      Integer eventBeginTime = new Integer(httpServletRequest
          .getParameter(
            "hidden_eventBeginTime"));
      eventVO.setEventBeginTime(eventBeginTime);
      Integer eventEndTime = new Integer(httpServletRequest.getParameter(
            "hidden_eventEndTime"));
      eventVO.setEventEndTime(eventEndTime);
      Long eventLastTime = new Long(httpServletRequest.getParameter(
            "hidden_eventLastTime"));
      eventVO.setEventLastTime(eventLastTime);
      Integer onTimeMode = new Integer(httpServletRequest.getParameter(
            "onTimeMode"));
      eventVO.setOnTimeMode(onTimeMode);
      if (onTimeMode != null && onTimeMode.equals(new Integer(1))) {
        String day = httpServletRequest.getParameter("day");
        eventVO.setOnTimeContent(day);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(2))) {
        String week7 = httpServletRequest.getParameter("week7");
        String weekA = null;
        if ("7".equals(week7)) {
          weekA = "1";
        } else {
          weekA = "0";
        } 
        String week1 = httpServletRequest.getParameter("week1");
        String weekB = null;
        if ("1".equals(week1)) {
          weekB = String.valueOf(weekA) + "1";
        } else {
          weekB = String.valueOf(weekA) + "0";
        } 
        String week2 = httpServletRequest.getParameter("week2");
        String weekC = null;
        if ("2".equals(week2)) {
          weekC = String.valueOf(weekB) + "1";
        } else {
          weekC = String.valueOf(weekB) + "0";
        } 
        String week3 = httpServletRequest.getParameter("week3");
        String weekD = null;
        if ("3".equals(week3)) {
          weekD = String.valueOf(weekC) + "1";
        } else {
          weekD = String.valueOf(weekC) + "0";
        } 
        String week4 = httpServletRequest.getParameter("week4");
        String weekE = null;
        if ("4".equals(week4)) {
          weekE = String.valueOf(weekD) + "1";
        } else {
          weekE = String.valueOf(weekD) + "0";
        } 
        String week5 = httpServletRequest.getParameter("week5");
        String weekF = null;
        if ("5".equals(week5)) {
          weekF = String.valueOf(weekE) + "1";
        } else {
          weekF = String.valueOf(weekE) + "0";
        } 
        String week6 = httpServletRequest.getParameter("week6");
        String weekG = null;
        if ("6".equals(week6)) {
          weekG = String.valueOf(weekF) + "1";
        } else {
          weekG = String.valueOf(weekF) + "0";
        } 
        eventVO.setOnTimeContent(weekG);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(3))) {
        String month_day = httpServletRequest.getParameter("month_day");
        eventVO.setOnTimeContent(month_day);
      } 
      if (onTimeMode != null && onTimeMode.equals(new Integer(4))) {
        String year_month = httpServletRequest.getParameter(
            "year_month");
        String year_month_day = httpServletRequest.getParameter(
            "year_month_day");
        eventVO.setOnTimeContent(String.valueOf(year_month) + "$" + year_month_day);
      } 
      Integer echoMode = new Integer(httpServletRequest.getParameter(
            "echoMode"));
      eventVO.setEchoMode(echoMode);
      if (echoMode != null && echoMode.equals(new Integer(0))) {
        Date echoEndTime = new Date(httpServletRequest.getParameter(
              "end_date"));
        eventVO.setEchoEndTime(echoEndTime);
      } 
      if (echoMode != null && echoMode.equals(new Integer(1))) {
        Integer echoModeTime = Integer.valueOf(httpServletRequest
            .getParameter("echoModeTime"));
        eventVO.setEchoCounter(echoModeTime);
      } 
      String proId = httpServletRequest.getParameter("relproject");
      eventVO.setRelProjectId(Long.valueOf(Long.parseLong(proId)));
      Date echoBeginTime = new Date(httpServletRequest.getParameter(
            "start_date"));
      eventVO.setEchoBeginTime(echoBeginTime);
      String attendEmp = httpServletRequest.getParameter("attendEmp");
      eventVO.setAttendEmp(attendEmp);
      Long eventRemindTime = Long.valueOf(httpServletRequest.getParameter(
            "eventRemindTime"));
      Integer eventRemind = eventActionForm.getEventRemind();
      if (eventRemind.equals(new Integer(1))) {
        eventVO.setEventRemindTime(eventRemindTime);
        if (eventActionForm.getPopRemindMode().intValue() != 1 && 
          eventActionForm.getNoteRemindMode().intValue() != 1)
          eventVO.setPopRemindMode(new Integer(1)); 
      } else {
        eventVO.setPopRemindMode(new Integer(0));
        eventVO.setNoteRemindMode(new Integer(0));
      } 
      eventVO.setEventEmpId(userId);
      eventVO.setEventEmpName(userName);
      boolean result = eventBD.modifyEvent(eventVO);
      if (!result)
        return actionMapping.findForward("failure"); 
      if ("updateAndExit".equals(saveType))
        return actionMapping.findForward("event_echo_updateAndExit"); 
    } 
    if ("getEventByToday".equals(action)) {
      Date time = null;
      Date nowDate = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
      String nowdate = dateFormat.format(nowDate);
      String eventTitle = httpServletRequest.getParameter("eventTitle");
      String eventContent = httpServletRequest.getParameter("eventContent");
      try {
        time = dateFormat.parse(nowdate);
      } catch (ParseException parseException) {}
      Long newDate = new Long(time.getTime());
      List list = eventBD.getEventByDay(userId, newDate, domainId, eventTitle, eventContent);
      httpServletRequest.setAttribute("allEventTodayList", list);
      httpServletRequest.setAttribute("Year", 
          httpServletRequest
          .getParameter("Year"));
      httpServletRequest.setAttribute("Month", 
          httpServletRequest
          .getParameter("Month"));
      httpServletRequest.setAttribute("Day", 
          httpServletRequest
          .getParameter("Day"));
      httpServletRequest.setAttribute("evenTitle", eventTitle);
      httpServletRequest.setAttribute("eventContent", eventContent);
      return actionMapping.findForward("goto_AllEventToday");
    } 
    if ("getEventByDay".equals(action)) {
      String day = httpServletRequest.getParameter("Day");
      String month = httpServletRequest.getParameter("Month");
      String year = httpServletRequest.getParameter("Year");
      String eventTitle = httpServletRequest.getParameter("eventTitle");
      String eventContent = httpServletRequest.getParameter("eventContent");
      String nowdate = "";
      Date time = null;
      if (year != null && !"".equals(year)) {
        nowdate = String.valueOf(year) + "-" + month + "-" + day;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
          time = dateFormat.parse(nowdate);
        } catch (ParseException parseException) {}
      } else {
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowdate = dateFormat.format(nowDate);
        try {
          time = dateFormat.parse(nowdate);
        } catch (ParseException parseException) {}
      } 
      Long newDate = new Long(time.getTime());
      List list = eventBD.getEventByDay(userId, newDate, domainId, eventTitle, eventContent);
      Calendar prev = Calendar.getInstance();
      prev.setTime(strToDate(nowdate));
      prev.add(5, -1);
      String y = String.valueOf(prev.get(1));
      String m = String.valueOf(prev.get(2) + 1);
      String d = String.valueOf(prev.get(5));
      httpServletRequest.setAttribute("prevDay", 
          String.valueOf(y) + "-" + m + "-" + d);
      Calendar next = Calendar.getInstance();
      next.setTime(strToDate(nowdate));
      next.add(5, 1);
      y = String.valueOf(next.get(1));
      m = String.valueOf(next.get(2) + 1);
      d = String.valueOf(next.get(5));
      httpServletRequest.setAttribute("nextDay", 
          String.valueOf(y) + "-" + m + "-" + d);
      SimpleDateFormat sf0 = new SimpleDateFormat("yyyy-M-d");
      httpServletRequest.setAttribute("currentDay", 
          sf0.format(new Date())
          .toString());
      httpServletRequest.setAttribute("time", nowdate);
      httpServletRequest.setAttribute("allEventDayList", list);
      httpServletRequest.setAttribute("evenTitle", eventTitle);
      httpServletRequest.setAttribute("eventContent", eventContent);
      return actionMapping.findForward("goto_AllEventDay");
    } 
    if ("selectSingleEchoEvent".equals(action)) {
      Long eventId = Long.valueOf(httpServletRequest.getParameter(
            "eventId"));
      String isEdit = (httpServletRequest.getParameter("isEdit") == null) ? 
        "" : httpServletRequest.getParameter("isEdit");
      String evenEmpId = (httpServletRequest.getParameter("empId") == null) ? 
        "" : httpServletRequest.getParameter("empId");
      EventVO eventVO = new EventVO();
      if (evenEmpId.length() != 0) {
        eventVO = eventBD.selectSingleEvent(eventId, 
            Long.valueOf(evenEmpId), 
            domainId);
        httpServletRequest.setAttribute("edit", "1");
      } else {
        eventVO = eventBD.selectSingleEvent(eventId, userId, 
            domainId);
      } 
      eventActionForm.setEventTitle(eventVO.getEventTitle());
      eventActionForm.setEventAddress(eventVO.getEventAddress());
      eventActionForm.setAttendEmp(eventVO.getAttendEmp());
      eventActionForm.setAttendName(eventVO.getAttendName());
      eventActionForm.setEventRemind(eventVO.getEventRemind());
      eventActionForm.setEventContent(eventVO.getEventContent());
      eventActionForm.setPopRemindMode(eventVO.getPopRemindMode());
      eventActionForm.setNoteRemindMode(eventVO.getNoteRemindMode());
      Integer eventBeginTime = eventVO.getEventBeginTime();
      Integer eventEndTime = eventVO.getEventEndTime();
      Long eventLastTime = eventVO.getEventLastTime();
      Integer onTimeMode = eventVO.getOnTimeMode();
      String OnTimeContent = eventVO.getOnTimeContent();
      Date echoBeginTime = eventVO.getEchoBeginTime();
      Date echoEndTime = eventVO.getEchoEndTime();
      Long eventRemindTime = eventVO.getEventRemindTime();
      Boolean canModify = eventVO.getCanModify();
      String eventBeginTimeFormat = eventVO.getEventBeginTimeFormat();
      String eventEndTimeFormat = eventVO.getEventEndTimeFormat();
      String eventLastTimeFormat = eventVO.getEventLastTimeformat();
      httpServletRequest.setAttribute("echoMode", eventVO.getEchoMode());
      httpServletRequest.setAttribute("eventId", eventId);
      httpServletRequest.setAttribute("eventBeginTime", eventBeginTime);
      httpServletRequest.setAttribute("eventEndTime", eventEndTime);
      httpServletRequest.setAttribute("eventBeginTimeFormat", 
          eventBeginTimeFormat);
      httpServletRequest.setAttribute("eventEndTimeFormat", 
          eventEndTimeFormat);
      httpServletRequest.setAttribute("eventLastTime", eventLastTime);
      httpServletRequest.setAttribute("eventLastTimeFormat", 
          eventLastTimeFormat);
      httpServletRequest.setAttribute("onTimeMode", onTimeMode);
      httpServletRequest.setAttribute("onTimeContent", OnTimeContent);
      httpServletRequest.setAttribute("echoBeginTime", echoBeginTime);
      httpServletRequest.setAttribute("echoEndTime", echoEndTime);
      httpServletRequest.setAttribute("eventRemindTime", eventRemindTime);
      httpServletRequest.setAttribute("canModify", canModify);
      httpServletRequest.setAttribute("eventEmpName", eventVO.getEventEmpName());
      httpServletRequest.setAttribute("proID", eventVO.getRelProjectId().toString());
      return actionMapping.findForward("goto_AllEchoEventUpdate");
    } 
    if ("getThisWeek".equals(action)) {
      long currentDate = System.currentTimeMillis();
      if (httpServletRequest.getParameter("time") != null)
        currentDate = Long.parseLong(httpServletRequest.getParameter(
              "time").toString()); 
      String eventTitle = httpServletRequest.getParameter("eventTitle");
      String eventContent = httpServletRequest.getParameter("eventContent");
      DayData dayData = null;
      List<DayData> weekData = new ArrayList();
      GregorianCalendar calendar = new GregorianCalendar(Locale.CHINESE);
      calendar.setTimeInMillis(currentDate);
      if (calendar.get(7) == 1) {
        for (int i = 0; i < 7; i++) {
          dayData = new DayData();
          dayData.setDay(calendar.getTimeInMillis() - ((
              6 - i) * 3600 * 24 * 1000));
          List list = eventBD.getEventByDay(userId, 
              new Long(dayData.getDay()), domainId, eventTitle, eventContent);
          dayData.setEvents(list);
          weekData.add(dayData);
          httpServletRequest.setAttribute("logDay" + (i + 1), 
              String.valueOf(dayData.getYear()) + "-" + dayData.getMonth() + "-" + 
              dayData.getTian());
        } 
      } else {
        int i;
        for (i = 1; i < calendar.get(7); i++) {
          dayData = new DayData();
          dayData.setDay(calendar.getTimeInMillis() - ((
              calendar.get(7) - i + 1) * 
              3600 * 24 * 1000));
          List list = eventBD.getEventByDay(userId, 
              new Long(dayData.getDay()), domainId, eventTitle, eventContent);
          dayData.setEvents(list);
          weekData.add(dayData);
          httpServletRequest.setAttribute("logDay" + i, 
              String.valueOf(dayData.getYear()) + "-" + dayData.getMonth() + "-" + 
              dayData.getTian());
        } 
        for (i = calendar.get(7); i < 8; i++) {
          dayData = new DayData();
          dayData.setDay(calendar.getTimeInMillis() + ((
              i - calendar.get(7) + 1) * 
              3600 * 24 * 1000));
          List list = eventBD.getEventByDay(userId, 
              new Long(dayData.getDay()), domainId, eventTitle, eventContent);
          dayData.setEvents(list);
          weekData.add(dayData);
          httpServletRequest.setAttribute("logDay" + i, 
              String.valueOf(dayData.getYear()) + "-" + dayData.getMonth() + "-" + 
              dayData.getTian());
        } 
      } 
      Calendar prev = Calendar.getInstance();
      prev.setTimeInMillis(currentDate);
      prev.add(5, -7);
      String y = String.valueOf(prev.get(1));
      String m = String.valueOf(prev.get(2) + 1);
      String d = String.valueOf(prev.get(5));
      httpServletRequest.setAttribute("prevWeek", 
          String.valueOf(y) + "-" + m + "-" + d);
      httpServletRequest.setAttribute("prevTime", (
          new StringBuilder(String.valueOf(prev.getTimeInMillis()))).toString());
      Calendar next = Calendar.getInstance();
      next.setTimeInMillis(currentDate);
      next.add(5, 7);
      y = String.valueOf(next.get(1));
      m = String.valueOf(next.get(2) + 1);
      d = String.valueOf(next.get(5));
      httpServletRequest.setAttribute("nextWeek", 
          String.valueOf(y) + "-" + m + "-" + d);
      httpServletRequest.setAttribute("nextTime", (
          new StringBuilder(String.valueOf(next.getTimeInMillis()))).toString());
      httpServletRequest.setAttribute("weekData", weekData);
      httpServletRequest.setAttribute("Year", 
          httpServletRequest
          .getParameter("Year"));
      httpServletRequest.setAttribute("Month", 
          httpServletRequest
          .getParameter("Month"));
      httpServletRequest.setAttribute("Day", 
          httpServletRequest
          .getParameter("Day"));
      httpServletRequest.setAttribute("time", Long.toString(currentDate));
      httpServletRequest.setAttribute("evenTitle", eventTitle);
      httpServletRequest.setAttribute("eventContent", eventContent);
      return actionMapping.findForward("goto_ThisAllWeekEvent");
    } 
    if ("getMonthView".equals(action)) {
      long currentDate = System.currentTimeMillis();
      if (httpServletRequest.getParameter("time") != null)
        currentDate = Long.parseLong(httpServletRequest.getParameter(
              "time").toString()); 
      String day = httpServletRequest.getParameter("Day");
      String month = httpServletRequest.getParameter("Month");
      String year = httpServletRequest.getParameter("Year");
      if (year == null || "".equals(year)) {
        Date d = new Date();
        if (year == null || "".equals(year))
          year = String.valueOf(d.getYear() + 1900); 
        if (month == null || "".equals(month))
          month = String.valueOf(d.getMonth() + 1); 
        if (day == null || "".equals(day))
          day = String.valueOf(d.getDate()); 
      } 
      GregorianCalendar cal = new GregorianCalendar(Locale.CHINESE);
      cal.set(1, Integer.parseInt(year));
      cal.set(2, Integer.parseInt(month) - 1);
      cal.set(5, 1);
      int _weekOfFirstDay = cal.get(7);
      int currDate = cal.getActualMaximum(5);
      int _prev = 0;
      if (_weekOfFirstDay != 2) {
        if (_weekOfFirstDay == 1) {
          _prev = 6;
        } else {
          _prev = _weekOfFirstDay - 2;
        } 
        cal.add(5, -_prev);
      } 
      int minRow = 0;
      if ((_prev + currDate) % 7 == 0) {
        minRow = (_prev + currDate) / 7;
      } else {
        minRow = (_prev + currDate) / 7 + 1;
      } 
      int showAllDate = minRow * 7;
      DayData dayData = null;
      List monthData = new ArrayList();
      cal.setTimeInMillis((new Date(cal.get(1) - 1900, 
            cal.get(2), 
            cal.get(5)))
          .getTime());
      String evenTitle = httpServletRequest.getParameter("eventTitle");
      String eventContent = httpServletRequest.getParameter("eventContent");
      monthData = getDDEvents(httpServletRequest, userId, domainId, evenTitle, eventContent, 1);
      httpServletRequest.setAttribute("monthData", monthData);
      httpServletRequest.setAttribute("Year", year);
      httpServletRequest.setAttribute("Month", month);
      httpServletRequest.setAttribute("Day", day);
      GregorianCalendar cc = new GregorianCalendar(Locale.CHINESE);
      cc.set(1, Integer.parseInt(year));
      cc.set(2, Integer.parseInt(month) - 1);
      cc.set(5, Integer.parseInt(day));
      cc.setTimeInMillis((new Date(cc.get(1) - 1900, 
            cc.get(2), 
            cc.get(5))).getTime());
      httpServletRequest.setAttribute("time", 
          Long.toString(cc.getTimeInMillis()));
      httpServletRequest.setAttribute("evenTitle", evenTitle);
      httpServletRequest.setAttribute("eventContent", eventContent);
      return actionMapping.findForward("goto_TheMonthEvent");
    } 
    if ("underlingemp".equals(action)) {
      String type = httpServletRequest.getParameter("type");
      String suserId = (httpServletRequest.getParameter("suserId") == null || "".equals(httpServletRequest.getParameter("suserId"))) ? 
        "0" : httpServletRequest.getParameter("suserId").toString();
      httpServletRequest.setAttribute("suserId", suserId.equals("0") ? "" : suserId);
      httpServletRequest.setAttribute("suserName", StaticParam.getEmpNameByEmpId(suserId.equals("0") ? "-1" : suserId));
      WorkLogBD worklogBD = new WorkLogBD();
      List<Object[]> empList = SystemCommon.getCustomerName().equals("qhcsj") ? (
        new EventBD()).getRelationEmp((String)userId, (String)httpServletRequest.getSession().getAttribute("orgIdString")) : 
        worklogBD.getDownEmployeeList(userId.toString());
      httpServletRequest.setAttribute("empList", empList);
      if ("day".equals(type)) {
        String day = httpServletRequest.getParameter("Day");
        String month = httpServletRequest.getParameter("Month");
        String year = httpServletRequest.getParameter("Year");
        String eventTitle = httpServletRequest.getParameter("eventTitle");
        String eventContent = httpServletRequest.getParameter("eventContent");
        String nowdate = "";
        Date time = null;
        if (year != null && !"".equals(year)) {
          nowdate = String.valueOf(year) + "-" + month + "-" + day;
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          try {
            time = dateFormat.parse(nowdate);
          } catch (ParseException parseException) {}
        } else {
          Date nowDate = new Date();
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          nowdate = dateFormat.format(nowDate);
          try {
            time = dateFormat.parse(nowdate);
          } catch (ParseException parseException) {}
        } 
        Long newDate = new Long(time.getTime());
        List<EventVO> list = new ArrayList();
        if (suserId != null && !"".equals(suserId))
          if ("0".equals(suserId)) {
            UserBD userBD = new UserBD();
            List<Object[]> UnderList = (new EventBD()).getRelationEmp((String)userId, (String)httpServletRequest.getSession().getAttribute("orgIdString"));
            if (!UnderList.isEmpty())
              for (int j = 0; j < UnderList.size(); j++) {
                Object[] obj = UnderList.get(j);
                list.addAll(eventBD.getEventByDay(Long.valueOf(obj[0].toString()), newDate, domainId, eventTitle, eventContent, 1));
              }  
          } else {
            list = eventBD.getEventByDay(Long.valueOf(suserId), newDate, domainId, eventTitle, eventContent, 1);
          }  
        Calendar prev = Calendar.getInstance();
        prev.setTime(strToDate(nowdate));
        prev.add(5, -1);
        String y = String.valueOf(prev.get(1));
        String m = String.valueOf(prev.get(2) + 1);
        String d = String.valueOf(prev.get(5));
        httpServletRequest.setAttribute("prevDay", String.valueOf(y) + "-" + m + "-" + d);
        Calendar next = Calendar.getInstance();
        next.setTime(strToDate(nowdate));
        next.add(5, 1);
        y = String.valueOf(next.get(1));
        m = String.valueOf(next.get(2) + 1);
        d = String.valueOf(next.get(5));
        httpServletRequest.setAttribute("nextDay", String.valueOf(y) + "-" + m + "-" + d);
        SimpleDateFormat sf0 = new SimpleDateFormat("yyyy-M-d");
        httpServletRequest.setAttribute("currentDay", sf0.format(new Date()).toString());
        httpServletRequest.setAttribute("time", nowdate);
        List<EventVO> allList = new ArrayList();
        String ids = ",";
        for (int i = 0; i < list.size(); i++) {
          EventVO vo = list.get(i);
          if (!ids.contains("," + vo.getEventId() + ",")) {
            ids = String.valueOf(ids) + vo.getEventId() + ",";
            allList.add(vo);
          } 
        } 
        httpServletRequest.setAttribute("allEventDayList", allList);
        httpServletRequest.setAttribute("evenTitle", eventTitle);
        httpServletRequest.setAttribute("eventContent", eventContent);
        return actionMapping.findForward("qhcsj_AllEventDayUderling");
      } 
      if ("getMonthView".equals(type)) {
        List monthData = new ArrayList();
        String evenTitle = httpServletRequest.getParameter("eventTitle");
        String eventContent = httpServletRequest.getParameter("eventContent");
        if (suserId != null && !"".equals(suserId))
          monthData = getDDEvents(httpServletRequest, Long.valueOf(suserId), domainId, evenTitle, eventContent, 1); 
        httpServletRequest.setAttribute("monthData", monthData);
        httpServletRequest.setAttribute("evenTitle", evenTitle);
        httpServletRequest.setAttribute("eventContent", eventContent);
        return actionMapping.findForward("qhcsj_TheMonthEventUderling");
      } 
      if ("getThisWeek".equals(type)) {
        UserBD userBD = new UserBD();
        List<Object[]> UnderList = SystemCommon.getCustomerName().equals("qhcsj") ? (
          new EventBD()).getRelationEmp((String)userId, (String)httpServletRequest.getSession().getAttribute("orgIdString")) : 
          userBD.selectMyUnderling(String.valueOf(userId));
        long currentDate = System.currentTimeMillis();
        if (httpServletRequest.getParameter("time") != null)
          currentDate = Long.parseLong(httpServletRequest.getParameter("time").toString()); 
        String eventTitle = httpServletRequest.getParameter("eventTitle");
        String eventContent = httpServletRequest.getParameter("eventContent");
        DayData dayData = null;
        List<DayData> weekData = new ArrayList();
        GregorianCalendar calendar = new GregorianCalendar(Locale.CHINESE);
        calendar.setTimeInMillis(currentDate);
        if (calendar.get(7) == 1) {
          for (int i = 0; i < 7; i++) {
            dayData = new DayData();
            dayData.setDay(calendar.getTimeInMillis() - ((6 - i) * 3600 * 24 * 1000));
            List<EventVO> list = new ArrayList();
            if (suserId != null && !"".equals(suserId))
              if ("0".equals(suserId)) {
                if (!UnderList.isEmpty())
                  for (int j = 0; j < UnderList.size(); j++) {
                    Object[] obj = UnderList.get(j);
                    list.addAll(eventBD.getEventByDay(Long.valueOf(obj[0].toString()), 
                          new Long(dayData.getDay()), domainId, eventTitle, eventContent, 1));
                  }  
              } else {
                list = eventBD.getEventByDay(Long.valueOf(suserId), 
                    new Long(dayData.getDay()), domainId, eventTitle, eventContent, 1);
              }  
            List<EventVO> allList = new ArrayList();
            String ids = ",";
            for (int t = 0; t < list.size(); t++) {
              EventVO vo = list.get(t);
              if (!ids.contains("," + vo.getEventId() + ",")) {
                ids = String.valueOf(ids) + vo.getEventId() + ",";
                allList.add(vo);
              } 
            } 
            dayData.setEvents(allList);
            weekData.add(dayData);
            httpServletRequest.setAttribute("logDay" + (i + 1), 
                String.valueOf(dayData.getYear()) + "-" + dayData.getMonth() + "-" + dayData.getTian());
          } 
        } else {
          int i;
          for (i = 1; i < calendar.get(7); i++) {
            dayData = new DayData();
            dayData.setDay(calendar.getTimeInMillis() - ((calendar.get(7) - i + 1) * 3600 * 24 * 1000));
            List<EventVO> list = new ArrayList();
            if (suserId != null && !"".equals(suserId))
              if ("0".equals(suserId)) {
                if (!UnderList.isEmpty())
                  for (int j = 0; j < UnderList.size(); j++) {
                    Object[] obj = UnderList.get(j);
                    list.addAll(eventBD.getEventByDay(Long.valueOf(obj[0].toString()), 
                          new Long(dayData.getDay()), domainId, eventTitle, eventContent, 1));
                  }  
              } else {
                list = eventBD.getEventByDay(Long.valueOf(suserId), 
                    new Long(dayData.getDay()), domainId, eventTitle, eventContent, 1);
              }  
            List<EventVO> allList = new ArrayList();
            String ids = ",";
            for (int t = 0; t < list.size(); t++) {
              EventVO vo = list.get(t);
              if (!ids.contains("," + vo.getEventId() + ",")) {
                ids = String.valueOf(ids) + vo.getEventId() + ",";
                allList.add(vo);
              } 
            } 
            dayData.setEvents(allList);
            weekData.add(dayData);
            httpServletRequest.setAttribute("logDay" + i, String.valueOf(dayData.getYear()) + "-" + dayData.getMonth() + "-" + dayData.getTian());
          } 
          for (i = calendar.get(7); i < 8; i++) {
            dayData = new DayData();
            dayData.setDay(calendar.getTimeInMillis() + ((i - calendar.get(7) + 1) * 3600 * 24 * 1000));
            List<EventVO> list = new ArrayList();
            if (suserId != null && !"".equals(suserId))
              if ("0".equals(suserId)) {
                if (!UnderList.isEmpty())
                  for (int j = 0; j < UnderList.size(); j++) {
                    Object[] obj = UnderList.get(j);
                    list.addAll(eventBD.getEventByDay(Long.valueOf(obj[0].toString()), 
                          new Long(dayData.getDay()), domainId, eventTitle, eventContent, 1));
                  }  
              } else {
                list = eventBD.getEventByDay(Long.valueOf(suserId), 
                    new Long(dayData.getDay()), domainId, eventTitle, eventContent, 1);
              }  
            List<EventVO> allList = new ArrayList();
            String ids = ",";
            for (int t = 0; t < list.size(); t++) {
              EventVO vo = list.get(t);
              if (!ids.contains("," + vo.getEventId() + ",")) {
                ids = String.valueOf(ids) + vo.getEventId() + ",";
                allList.add(vo);
              } 
            } 
            dayData.setEvents(allList);
            weekData.add(dayData);
            httpServletRequest.setAttribute("logDay" + i, String.valueOf(dayData.getYear()) + "-" + dayData.getMonth() + "-" + dayData.getTian());
          } 
        } 
        Calendar prev = Calendar.getInstance();
        prev.setTimeInMillis(currentDate);
        prev.add(5, -7);
        String y = String.valueOf(prev.get(1));
        String m = String.valueOf(prev.get(2) + 1);
        String d = String.valueOf(prev.get(5));
        httpServletRequest.setAttribute("prevWeek", String.valueOf(y) + "-" + m + "-" + d);
        httpServletRequest.setAttribute("prevTime", (new StringBuilder(String.valueOf(prev.getTimeInMillis()))).toString());
        Calendar next = Calendar.getInstance();
        next.setTimeInMillis(currentDate);
        next.add(5, 7);
        y = String.valueOf(next.get(1));
        m = String.valueOf(next.get(2) + 1);
        d = String.valueOf(next.get(5));
        httpServletRequest.setAttribute("nextWeek", String.valueOf(y) + "-" + m + "-" + d);
        httpServletRequest.setAttribute("nextTime", (new StringBuilder(String.valueOf(next.getTimeInMillis()))).toString());
        httpServletRequest.setAttribute("weekData", weekData);
        httpServletRequest.setAttribute("Year", httpServletRequest.getParameter("Year"));
        httpServletRequest.setAttribute("Month", httpServletRequest.getParameter("Month"));
        httpServletRequest.setAttribute("Day", httpServletRequest.getParameter("Day"));
        httpServletRequest.setAttribute("time", Long.toString(currentDate));
        httpServletRequest.setAttribute("evenTitle", eventTitle);
        httpServletRequest.setAttribute("eventContent", eventContent);
        return actionMapping.findForward("qhcsj_ThisAllWeekEventUnderling");
      } 
      if ("selectAllEvent".equals(type)) {
        String start_date = httpServletRequest.getParameter("start_date");
        String end_date = httpServletRequest.getParameter("end_date");
        String eventType = httpServletRequest.getParameter("eventType");
        String isDate = httpServletRequest.getParameter("isDate");
        String eventTitle = httpServletRequest.getParameter("eventTitle");
        String empName = httpServletRequest.getParameter("empName");
        String eventContent = httpServletRequest.getParameter("eventContent");
        int pageSize = 15;
        int offset = 0;
        if (httpServletRequest.getParameter("pager.offset") != null)
          offset = Integer.parseInt(httpServletRequest.getParameter("pager.offset")); 
        int currentPage = offset / pageSize + 1;
        eventBD.setVolume(pageSize);
        List list = new ArrayList();
        String userIds = "-1";
        if (suserId != null && !"".equals(suserId)) {
          if (eventType == null || "".equals(eventType) || 
            "null".equals(eventType))
            eventType = null; 
          long start_time = 0L;
          long end_time = 0L;
          String _start_date = null;
          String _end_date = null;
          Date now = new Date();
          Calendar cal = Calendar.getInstance();
          if (start_date == null || "".equals(start_date) || 
            "null".equals(start_date)) {
            int y = now.getYear() + 1900;
            int m = now.getMonth() + 1;
            int d = now.getDate();
            start_date = String.valueOf(y) + "/" + m + "/" + d;
            _start_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + ((d < 10) ? ("0" + d) : d);
            start_time = (new Date(cal.get(1) - 1900, cal.get(2), 
                cal.get(5))).getTime();
          } else {
            int y = Integer.parseInt(start_date.substring(0, start_date.indexOf("/")));
            int m = Integer.parseInt(start_date.substring(start_date.indexOf("/") + 1, start_date.lastIndexOf("/")));
            int d = Integer.parseInt(start_date.substring(start_date.lastIndexOf("/") + 1));
            _start_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + ((d < 10) ? ("0" + d) : d);
            cal.set(1, y);
            cal.set(2, m - 1);
            cal.set(5, d);
            start_time = (new Date(cal.get(1) - 1900, cal.get(2), 
                cal.get(5))).getTime();
          } 
          if (end_date == null || "".equals(end_date) || 
            "null".equals(end_date)) {
            int y = now.getYear() + 1900;
            int m = now.getMonth() + 1;
            int d = now.getDate();
            end_date = String.valueOf(y) + "/" + m + "/" + d;
            _end_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + (
              (d < 10) ? ("0" + d) : d);
            end_time = (new Date(cal.get(1) - 1900, cal.get(2), 
                cal.get(5))).getTime();
          } else {
            int y = Integer.parseInt(end_date.substring(0, end_date.indexOf("/")));
            int m = Integer.parseInt(end_date.substring(end_date.indexOf("/") + 1, end_date.lastIndexOf("/")));
            int d = Integer.parseInt(end_date.substring(end_date.lastIndexOf("/") + 1));
            _end_date = String.valueOf(y) + "-" + ((m < 10) ? ("0" + m) : m) + "-" + (
              (d < 10) ? ("0" + d) : d);
            cal.set(1, y);
            cal.set(2, m - 1);
            cal.set(5, d);
            end_time = (new Date(cal.get(1) - 1900, cal.get(2), 
                cal.get(5))).getTime();
          } 
          eventBD.setVolume(pageSize);
          list = null;
          if (!"1".equals(isDate)) {
            if (empName == null || empName.equals("") || empName.equals("null")) {
              list = eventBD.searchEvent(Long.valueOf(suserId), 
                  new Integer(currentPage), eventContent, domainId, 
                  (Long)null, (Long)null, (String)null, (String)null, eventType, eventTitle, 1);
            } else {
              UserBD userDB = new UserBD();
              List<String[]> userList = (new EventBD()).getRelationEmp((String)userId, (String)httpServletRequest.getSession().getAttribute("orgIdString"));
              StringBuffer sb = new StringBuffer();
              if (userList != null && userList.size() > 0)
                for (int i = 0; i < userList.size(); i++) {
                  String[] ev = userList.get(i);
                  String evId = String.valueOf(ev[0]);
                  for (int j = 0; j < empList.size(); j++) {
                    Object[] obj = empList.get(j);
                    if (evId.equals(obj[0].toString()))
                      sb.append(String.valueOf(obj[0].toString()) + ","); 
                  } 
                }  
              if (sb.toString() != null && !sb.toString().equals("")) {
                String ids = sb.toString().substring(0, sb.toString().lastIndexOf(","));
                list = eventBD.searchEventByEmpName(ids, 
                    new Integer(currentPage), eventContent, domainId, 
                    null, null, null, null, eventType, eventTitle, empName, 1);
              } else {
                list = eventBD.searchEventByEmpName(suserId, 
                    new Integer(currentPage), eventContent, domainId, 
                    null, null, null, null, eventType, eventTitle, empName, 1);
              } 
            } 
          } else {
            list = eventBD.searchEvent(Long.valueOf(suserId), 
                new Integer(currentPage), 
                eventContent, domainId, 
                new Long(start_time), 
                new Long(end_time), _start_date, 
                _end_date, eventType, eventTitle, 1);
          } 
        } else {
          for (int i = 0; i < empList.size(); i++) {
            Object[] obj = empList.get(i);
            userIds = String.valueOf(userIds) + "," + obj[0].toString();
          } 
          list = eventBD.selectAllEmpEvent(userIds, new Integer(currentPage), domainId, eventTitle, eventContent, 1);
        } 
        String recordCount = String.valueOf(eventBD.getRecordCount());
        httpServletRequest.setAttribute("allEventList", list);
        httpServletRequest.setAttribute("recordCount", recordCount);
        httpServletRequest.setAttribute("maxPageItems", String.valueOf(pageSize));
        httpServletRequest.setAttribute("pageParameters", "action,type,suserId,start_date,end_date,eventType,isDate");
        httpServletRequest.setAttribute("Year", httpServletRequest.getParameter("Year"));
        httpServletRequest.setAttribute("Month", httpServletRequest.getParameter("Month"));
        httpServletRequest.setAttribute("Day", httpServletRequest.getParameter("Day"));
        httpServletRequest.setAttribute("start_date", start_date);
        httpServletRequest.setAttribute("end_date", end_date);
        httpServletRequest.setAttribute("eventType", eventType);
        httpServletRequest.setAttribute("isDate", isDate);
        httpServletRequest.setAttribute("evenTitle", eventTitle);
        httpServletRequest.setAttribute("eventContent", eventContent);
        return actionMapping.findForward("qhcsj_AllEventUnderling");
      } 
    } 
    throw new UnsupportedOperationException("Method perform() not yet implemented.");
  }
  
  private void view(HttpServletRequest httpServletRequest, Long userId, EventBD eventBD, String searchContent, Long domainId) {
    int pageSize = 15;
    int offset = 0;
    if (httpServletRequest.getParameter("pager.offset") != null)
      offset = Integer.parseInt(httpServletRequest
          .getParameter("pager.offset")); 
    int currentPage = offset / pageSize + 1;
    eventBD.setVolume(pageSize);
    List list = null;
    String eventTitle = httpServletRequest.getParameter("eventTitle");
    if (searchContent == null || "".equals(searchContent)) {
      list = eventBD.selectAllEvent(userId, 
          new Integer(currentPage), domainId);
      httpServletRequest.setAttribute("pageParameters", "action");
    } else {
      list = eventBD.searchEvent(userId, new Integer(currentPage), 
          searchContent, domainId, null, null, null, null, null, eventTitle);
      httpServletRequest.setAttribute("pageParameters", 
          "action,searchContent");
    } 
    int recordCount = eventBD.getRecordCount().intValue();
    if (offset >= recordCount) {
      offset = (recordCount - pageSize) / pageSize;
      currentPage = offset + 1;
      offset *= pageSize;
      if (searchContent == null || "".equals(searchContent)) {
        list = eventBD.selectAllEvent(userId, 
            new Integer(currentPage), 
            domainId);
        httpServletRequest.setAttribute("pageParameters", "action");
      } else {
        list = eventBD.searchEvent(userId, new Integer(currentPage), 
            searchContent, domainId, null, null, null, null, null, eventTitle);
        httpServletRequest.setAttribute("pageParameters", 
            "action,searchContent");
      } 
      recordCount = eventBD.getRecordCount().intValue();
      httpServletRequest.setAttribute("pager.offset", 
          String.valueOf(offset));
      httpServletRequest.setAttribute("pager.realCurrent", 
          String.valueOf(currentPage));
    } 
    httpServletRequest.setAttribute("allEventList", list);
    httpServletRequest.setAttribute("recordCount", 
        String.valueOf(recordCount));
    httpServletRequest.setAttribute("maxPageItems", 
        String.valueOf(pageSize));
  }
  
  private List getDDEvents(HttpServletRequest httpServletRequest, Long userId, Long domainId, String evenTitle, String eventContent, int flag) {
    String day = httpServletRequest.getParameter("Day");
    String month = httpServletRequest.getParameter("Month");
    String year = httpServletRequest.getParameter("Year");
    if (year == null || "".equals(year)) {
      Date d = new Date();
      if (year == null || "".equals(year))
        year = String.valueOf(d.getYear() + 1900); 
      if (month == null || "".equals(month))
        month = String.valueOf(d.getMonth() + 1); 
      if (day == null || "".equals(day))
        day = String.valueOf(d.getDate()); 
    } 
    GregorianCalendar cal = new GregorianCalendar(Locale.CHINESE);
    cal.set(1, Integer.parseInt(year));
    cal.set(2, Integer.parseInt(month) - 1);
    cal.set(5, 1);
    int _weekOfFirstDay = cal.get(7);
    int currDate = cal.getActualMaximum(5);
    int _prev = 0;
    if (_weekOfFirstDay != 2) {
      if (_weekOfFirstDay == 1) {
        _prev = 6;
      } else {
        _prev = _weekOfFirstDay - 2;
      } 
      cal.add(5, -_prev);
    } 
    int minRow = 0;
    if ((_prev + currDate) % 7 == 0) {
      minRow = (_prev + currDate) / 7;
    } else {
      minRow = (_prev + currDate) / 7 + 1;
    } 
    int showAllDate = minRow * 7;
    List monthData = new ArrayList();
    GregorianCalendar cal2 = new GregorianCalendar(Locale.CHINESE);
    cal2.setTimeInMillis((new Date(cal.get(1) - 1900, 
          cal.get(2), 
          cal.get(5))).getTime());
    long time = 0L;
    int begin_y = cal.get(1);
    int begin_m = cal.get(2);
    int begin_d = cal.get(5);
    String beginDate = String.valueOf(begin_y) + "-" + appendZero(begin_m + 1) + "-" + 
      appendZero(begin_d);
    cal.add(5, showAllDate - 1);
    int end_y = cal.get(1);
    int end_m = cal.get(2);
    int end_d = cal.get(5);
    String endDate = String.valueOf(end_y) + "-" + appendZero(end_m + 1) + "-" + 
      appendZero(end_d);
    EventBD bd = new EventBD();
    if (userId.longValue() == 0L) {
      UserBD userBD = new UserBD();
      try {
        Object object = httpServletRequest.getSession().getAttribute("userId");
        List<Object[]> UnderList = (new EventBD()).getRelationEmp((new StringBuilder(String.valueOf(object))).toString(), (String)httpServletRequest.getSession().getAttribute("orgIdString"));
        if (!UnderList.isEmpty())
          for (int i = 0; i < UnderList.size(); i++) {
            Object[] obj = UnderList.get(i);
            List result = bd.getDDEvents(Long.valueOf(obj[0].toString()), beginDate, endDate, domainId, evenTitle, eventContent, flag);
            setDDEvents(monthData, result, cal2, showAllDate);
          }  
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } else {
      List result = new ArrayList();
      try {
        result = bd.getDDEvents(userId, beginDate, endDate, domainId, evenTitle, eventContent, flag);
      } catch (HibernateException e) {
        e.printStackTrace();
      } 
      setDDEvents(monthData, result, cal2, showAllDate);
    } 
    httpServletRequest.setAttribute("Year", year);
    httpServletRequest.setAttribute("Month", month);
    httpServletRequest.setAttribute("Day", day);
    GregorianCalendar cc = new GregorianCalendar(Locale.CHINESE);
    cc.set(1, Integer.parseInt(year));
    cc.set(2, Integer.parseInt(month) - 1);
    cc.set(5, Integer.parseInt(day));
    cc.setTimeInMillis((new Date(cc.get(1) - 1900, cc.get(2), 
          cc.get(5))).getTime());
    httpServletRequest.setAttribute("time", Long.toString(cc.getTimeInMillis()));
    return monthData;
  }
  
  private void setDDEvents(List<DayData> datas, List<EventVO> result, Calendar cal2, int days) {
    long time = 0L;
    int size = datas.size();
    List<EventVO> list = null;
    DayData dayData = null;
    Calendar cal = Calendar.getInstance();
    cal.setTime(cal2.getTime());
    for (int i = 0; i < days; i++) {
      dayData = new DayData();
      list = new ArrayList();
      time = cal.getTimeInMillis();
      Date currentDate = cal.getTime();
      int y = cal.get(1);
      int m = cal.get(2);
      int d = cal.get(5);
      int w = cal.get(7);
      dayData.setDay(time);
      if (result != null && result.size() > 0)
        for (int j = 0; j < result.size(); j++) {
          EventVO vo = result.get(j);
          if (vo.getEventFullDay().intValue() == 1) {
            if (vo.getEventBeginDate().longValue() <= time && 
              vo.getEventEndDate().longValue() >= time)
              list.add(vo); 
          } else if (vo.getOnTimeMode().intValue() == 0) {
            if (vo.getEventBeginDate().longValue() <= time && 
              vo.getEventEndDate().longValue() >= time)
              list.add(vo); 
          } else if (vo.getOnTimeMode().intValue() == 1) {
            if ("0".equals(vo.getOnTimeContent())) {
              if (vo.getEchoBeginTime().compareTo(
                  currentDate) <= 0)
                if (vo.getEchoMode().intValue() == -1) {
                  list.add(vo);
                } else if (vo.getEchoMode().intValue() == 0) {
                  if (vo.getEchoEndTime().compareTo(
                      currentDate) >= 0)
                    list.add(vo); 
                } else if (vo.getEchoMode().intValue() > 0) {
                  if (vo.getEchoCounter().compareTo(
                      vo
                      .getEchoMode()) <= 0)
                    list.add(vo); 
                }  
            } else if ("1".equals(vo.getOnTimeContent()) && 
              vo.getEchoBeginTime().compareTo(
                currentDate) <= 0) {
              if (vo.getEchoMode().intValue() == -1) {
                if (w == 2 || w == 3 || w == 4 || 
                  w == 5 || w == 6)
                  list.add(vo); 
              } else if (vo.getEchoMode().intValue() == 0) {
                if (vo.getEchoEndTime().compareTo(
                    currentDate) >= 0)
                  if (w == 2 || w == 3 || w == 4 || 
                    w == 5 || w == 6)
                    list.add(vo);  
              } else if (vo.getEchoMode().intValue() > 0) {
                if (vo.getEchoCounter().compareTo(
                    vo
                    .getEchoMode()) <= 0)
                  if (w == 2 || w == 3 || w == 4 || 
                    w == 5 || w == 6)
                    list.add(vo);  
              } 
            } 
          } else if (vo.getOnTimeMode().intValue() == 2) {
            if (vo.getEchoMode().intValue() == -1) {
              if (vo.getEchoBeginTime().compareTo(
                  currentDate) <= 0)
                if (vo.getOnTimeContent().charAt(w - 1) == 
                  '1')
                  list.add(vo);  
            } else if (vo.getEchoMode().intValue() == 0) {
              if (vo.getEchoBeginTime().compareTo(
                  currentDate) <= 0)
                if (vo.getOnTimeContent().charAt(w - 1) == 
                  '1')
                  if (vo.getEchoEndTime().compareTo(
                      currentDate) >= 0)
                    list.add(vo);   
            } else if (vo.getEchoMode().intValue() > 0 && 
              vo.getEchoBeginTime().compareTo(
                currentDate) <= 0) {
              if (vo.getOnTimeContent().charAt(w - 1) == 
                '1')
                if (vo.getEchoCounter().compareTo(vo
                    .getEchoMode()) < 0)
                  list.add(vo);  
            } 
          } else if (vo.getOnTimeMode().intValue() == 3) {
            if (vo.getEchoMode().intValue() == -1) {
              if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
                Integer.parseInt(vo.getOnTimeContent()) == d) {
                list.add(vo);
              } else if (vo.getEchoBeginTime().compareTo(currentDate) <= 0) {
                if (vo.getOnTimeContent().equals("31")) {
                  switch (m) {
                    case 1:
                      if (isLeapYear(y)) {
                        if (d == 29)
                          list.add(vo); 
                        break;
                      } 
                      if (d == 28)
                        list.add(vo); 
                      break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                      if (d == 30)
                        list.add(vo); 
                      break;
                  } 
                } else if (vo.getOnTimeContent()
                  .equals(
                    "30")) {
                  if (m == 1)
                    if (isLeapYear(y)) {
                      if (d == 29)
                        list.add(vo); 
                    } else if (d == 28) {
                      list.add(vo);
                    }  
                } else if (vo.getOnTimeContent()
                  .equals(
                    "29")) {
                  if (m == 1 && 
                    !isLeapYear(y) && 
                    d == 28)
                    list.add(vo); 
                } 
              } 
            } else if (vo.getEchoMode().intValue() == 0) {
              if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
                Integer.parseInt(vo.getOnTimeContent()) == d && 
                vo.getEchoEndTime().compareTo(currentDate) >= 0) {
                list.add(vo);
              } else if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
                vo.getEchoEndTime().compareTo(currentDate) >= 0) {
                if (vo.getOnTimeContent().equals("31")) {
                  switch (m) {
                    case 1:
                      if (isLeapYear(y)) {
                        if (d == 29)
                          list.add(vo); 
                        break;
                      } 
                      if (d == 28)
                        list.add(vo); 
                      break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                      if (d == 30)
                        list.add(vo); 
                      break;
                  } 
                } else if (vo.getOnTimeContent()
                  .equals(
                    "30")) {
                  if (m == 1)
                    if (isLeapYear(y)) {
                      if (d == 29)
                        list.add(vo); 
                    } else if (d == 28) {
                      list.add(vo);
                    }  
                } else if (vo.getOnTimeContent()
                  .equals(
                    "29")) {
                  if (m == 1 && 
                    !isLeapYear(y) && 
                    d == 28)
                    list.add(vo); 
                } 
              } 
            } else if (vo.getEchoMode().intValue() > 0) {
              if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
                Integer.parseInt(vo.getOnTimeContent()) == d && 
                vo.getEchoMode().compareTo(vo.getEchoCounter()) > 0) {
                list.add(vo);
              } else if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && vo.getEchoMode().compareTo(vo.getEchoCounter()) > 0) {
                if (vo.getOnTimeContent().equals("31")) {
                  switch (m) {
                    case 1:
                      if (isLeapYear(y)) {
                        if (d == 29)
                          list.add(vo); 
                        break;
                      } 
                      if (d == 28)
                        list.add(vo); 
                      break;
                    case 3:
                    case 5:
                    case 8:
                    case 10:
                      if (d == 30)
                        list.add(vo); 
                      break;
                  } 
                } else if (vo.getOnTimeContent().equals("30")) {
                  if (m == 1)
                    if (isLeapYear(y)) {
                      if (d == 29)
                        list.add(vo); 
                    } else if (d == 28) {
                      list.add(vo);
                    }  
                } else if (vo.getOnTimeContent().equals("29") && 
                  m == 1 && 
                  !isLeapYear(y) && 
                  d == 28) {
                  list.add(vo);
                } 
              } 
            } 
          } else if (vo.getOnTimeMode().intValue() == 4) {
            String[] monthDay = vo.getOnTimeContent().split("\\$");
            if (vo.getEchoMode().intValue() == -1) {
              if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
                Integer.parseInt(monthDay[0]) == m + 1 && 
                Integer.parseInt(monthDay[1]) == d)
                list.add(vo); 
            } else if (vo.getEchoMode().intValue() == 0) {
              if (vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
                Integer.parseInt(monthDay[0]) == m + 1 && 
                Integer.parseInt(monthDay[1]) == d && 
                vo.getEchoEndTime().compareTo(currentDate) >= 0)
                list.add(vo); 
            } else if (vo.getEchoMode().intValue() > 0 && 
              vo.getEchoBeginTime().compareTo(currentDate) <= 0 && 
              Integer.parseInt(monthDay[0]) == m + 1 && 
              Integer.parseInt(monthDay[1]) == d && 
              vo.getEchoMode().compareTo(vo.getEchoCounter()) > 0) {
              list.add(vo);
            } 
          } 
        }  
      if (size > 0) {
        List<EventVO> dayData2 = ((DayData)datas.get(i)).getEvents();
        for (int j = 0; j < dayData2.size(); j++)
          list.add(dayData2.get(j)); 
        List<EventVO> allList = new ArrayList();
        String ids = ",";
        for (int t = 0; t < list.size(); t++) {
          EventVO vo = list.get(t);
          if (!ids.contains("," + vo.getEventId() + ",")) {
            ids = String.valueOf(ids) + vo.getEventId() + ",";
            allList.add(vo);
          } 
        } 
        dayData.setEvents(allList);
        datas.set(i, dayData);
      } else {
        dayData.setEvents(list);
        datas.add(dayData);
      } 
      cal.add(5, 1);
    } 
  }
  
  private String appendZero(int val) {
    if (val < 10)
      return "0" + val; 
    return String.valueOf(val);
  }
  
  private boolean isLeapYear(int year) {
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
      return true; 
    return false;
  }
  
  private static Date strToDate(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = formatter.parse(strDate, pos);
    return strtodate;
  }
}
