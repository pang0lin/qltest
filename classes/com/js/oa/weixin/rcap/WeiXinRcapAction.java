package com.js.oa.weixin.rcap;

import com.js.oa.crm.util.JDBCManager;
import com.js.oa.scheme.event.po.EventPO;
import com.js.oa.scheme.worklog.service.WorkLogBD;
import com.js.oa.weixin.common.service.WeiXinBD;
import com.js.wap.bean.WapBean;
import com.js.wap.util.WapUtil;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WeiXinRcapAction extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String method = request.getParameter("action");
    HttpSession session = request.getSession(true);
    String userId = (String)session.getAttribute("userId");
    if ("write".equals(method))
      return mapping.findForward("write"); 
    if ("list".equals(method)) {
      String schUser = request.getParameter("schUser");
      String keyword = (request.getParameter("keyword") != null) ? request.getParameter("keyword") : "";
      if (keyword != null && !"".equals(keyword))
        keyword = URLDecoder.decode(keyword, "utf-8"); 
      if ("mine".equals(schUser)) {
        getSchList(request, userId, "", false, keyword);
      } else if ("other".equals(schUser)) {
        String empsStr = getEmpStr(userId);
        if (!empsStr.equals(""))
          getSchList(request, empsStr, "", true, keyword); 
      } 
      return mapping.findForward("list");
    } 
    if ("rcapInfo".equals(method)) {
      rcapInfo(request);
      return mapping.findForward("rcapInfo");
    } 
    return null;
  }
  
  public String getEmpStr(String userId) {
    WorkLogBD worklogBD = new WorkLogBD();
    List<Object> empList = worklogBD.getDownEmployeeList(userId);
    StringBuffer empsStr = new StringBuffer("");
    Iterator<Object> it = empList.iterator();
    while (it.hasNext()) {
      Object[] objs = (Object[])it.next();
      empsStr.append(objs[0] + ",");
    } 
    if (empsStr.length() > 1)
      empsStr.deleteCharAt(empsStr.length() - 1); 
    return empsStr.toString();
  }
  
  public void getSchList(HttpServletRequest request, String userId, String tag, boolean flag, String keyword) {
    WeiXinBD cdb = new WeiXinBD();
    String listStr = "mySchList";
    String countStr = "mySchCount";
    String scheUser = "mine";
    int limited = WapUtil.LIMITED;
    Map schMap = null;
    int beginIndex = Integer.parseInt((request.getParameter("beginIndex") == null) ? "0" : request.getParameter("beginIndex"));
    if ("index".equals(tag))
      limited = 5; 
    if (!flag) {
      schMap = cdb.getEventListByEmpId(userId, "0", beginIndex, limited, keyword);
    } else {
      listStr = "otherSchList";
      countStr = "otherSchCount";
      scheUser = "other";
      schMap = cdb.getUnderEventList(userId, "0", beginIndex, limited, keyword);
    } 
    List<EventPO> schList = (List<EventPO>)schMap.get("QUERY_LIST");
    int secCount = ((Integer)schMap.get("RECORD_COUNT")).intValue();
    request.setAttribute("scheUser", scheUser);
    request.setAttribute(countStr, Integer.valueOf(secCount));
    request.setAttribute("keyword", keyword);
    request.setAttribute(listStr, schList);
  }
  
  private void rcapInfo(HttpServletRequest request) {
    try {
      Long eventId = Long.valueOf(request.getParameter("eventId"));
      String schUser = request.getParameter("schUser");
      EventPO event = (new WapBean()).getEventByEventId(eventId);
      Long eventRemindTime = event.getEventRemindTime();
      Long eventLastTime = event.getEventLastTime();
      String proId = String.valueOf(event.getRelProjectId());
      Integer eventBeginTime = event.getEventBeginTime();
      Integer eventEndTime = event.getEventEndTime();
      Integer onTimeMode = event.getOnTimeMode();
      String OnTimeContent = event.getOnTimeContent();
      Date echoBeginTime = event.getEchoBeginTime();
      Date echoEndTime = event.getEchoEndTime();
      String eventBeginTimeFormat = "";
      String eventEndTimeFormat = "";
      String eventLastTimeFormat = "";
      if (eventBeginTime != null)
        eventBeginTimeFormat = getEventBeginTimeFormat(Long.valueOf(Long.parseLong((String)eventBeginTime))); 
      if (eventEndTime != null)
        eventEndTimeFormat = getEventEndTimeFormat(Long.valueOf(Long.parseLong((String)eventEndTime))); 
      if (eventLastTime != null)
        eventLastTimeFormat = getEventLastTimeformat(eventLastTime); 
      String empIds = (event.getAttendEmp() == null || "".equals((new StringBuilder(String.valueOf(event.getAttendEmp()))).toString())) ? "" : event.getAttendEmp().substring(1, event.getAttendEmp().length() - 1).replace("$$", ",");
      request.setAttribute("userNames", event.getAttendName());
      request.setAttribute("userIds", empIds);
      request.setAttribute("echoMode", event.getEchoMode());
      request.setAttribute("eventId", eventId);
      request.setAttribute("eventBeginTime", eventBeginTime);
      request.setAttribute("eventEndTime", eventEndTime);
      request.setAttribute("eventBeginTimeFormat", eventBeginTimeFormat);
      request.setAttribute("eventEndTimeFormat", eventEndTimeFormat);
      request.setAttribute("eventLastTime", eventLastTime);
      request.setAttribute("eventLastTimeFormat", eventLastTimeFormat);
      request.setAttribute("onTimeMode", onTimeMode);
      request.setAttribute("onTimeContent", OnTimeContent);
      request.setAttribute("echoBeginTime", echoBeginTime);
      request.setAttribute("echoEndTime", echoEndTime);
      request.setAttribute("eventRemindTime", eventRemindTime);
      request.setAttribute("eventEmpName", event.getEventEmpName());
      request.setAttribute("proID", event.getRelProjectId().toString());
      request.setAttribute("schUser", schUser);
      if (request.getParameter("from") != null)
        request.setAttribute("from", request.getParameter("from")); 
      String relProName = null;
      if (proId != null && !"".equals(proId))
        relProName = JDBCManager.getProNameById(proId); 
      request.setAttribute("event", event);
      request.setAttribute("relProName", relProName);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public String getEventBeginTimeFormat(Long eventBeginTime) {
    StringBuffer result = new StringBuffer();
    int time = eventBeginTime.intValue();
    int hour = time / 3600;
    result.append(hour);
    result.append(":");
    int minute = (time - hour * 60 * 60) / 60;
    if (minute < 10)
      result.append('0'); 
    result.append(minute);
    return result.toString();
  }
  
  public String getEventEndTimeFormat(Long eventEndTime) {
    StringBuffer result = new StringBuffer();
    int time = eventEndTime.intValue();
    int hour = time / 3600;
    result.append(hour);
    result.append(":");
    int minute = (time - hour * 60 * 60) / 60;
    if (minute < 10)
      result.append('0'); 
    result.append(minute);
    return result.toString();
  }
  
  public String getEventLastTimeformat(Long eventLastTime) {
    StringBuffer result = new StringBuffer();
    int time = eventLastTime.intValue() / 1000;
    int day = time / 86400;
    int modday = time % 86400;
    int hour = time / 3600;
    int minute = (time - hour * 60 * 60) / 60;
    if (hour < 1) {
      result.append(minute);
      result.append("分钟");
    } else if (hour >= 1 && hour < 24) {
      result.append(hour);
      if (minute > 0) {
        result.append(".");
        result.append(minute);
      } 
      result.append("小时");
    } else if (modday == 0 && day > 0) {
      if (day < 7) {
        result.append(day);
        result.append("天");
      } 
      if (day == 7)
        result.append("1周"); 
      if (day == 14)
        result.append("2周"); 
    } 
    return result.toString();
  }
}
