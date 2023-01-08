package com.js.oa.form;

import com.js.oa.hr.kq.po.KqLeavePO;
import com.js.oa.hr.kq.service.KqLeaveBD;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class KqLeaveWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    KqLeaveBD leave = new KqLeaveBD();
    Long leaveId = new Long(-1L);
    try {
      leaveId = leave.add(setPO(httpServletRequest));
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return leaveId;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    String leaveid = httpServletRequest.getParameter("leaveId");
    KqLeaveBD kqLeaveBD = new KqLeaveBD();
    KqLeavePO kqLeavePO = new KqLeavePO();
    try {
      kqLeavePO = kqLeaveBD.searchById((new Long(leaveid)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqLeavePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqLeavePO.setEndTime(calendar.getTime());
      kqLeavePO.setLeaveType(Integer.parseInt(httpServletRequest.getParameter("leaveType")));
      kqLeavePO.setCause(httpServletRequest.getParameter("cause"));
      kqLeaveBD.update(kqLeavePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    String leaveid = httpServletRequest.getParameter("leaveId");
    KqLeaveBD kqLeaveBD = new KqLeaveBD();
    KqLeavePO kqLeavePO = new KqLeavePO();
    try {
      kqLeavePO = kqLeaveBD.searchById((new Long(leaveid)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqLeavePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqLeavePO.setEndTime(calendar.getTime());
      kqLeavePO.setLeaveType(Integer.parseInt(httpServletRequest.getParameter("leaveType")));
      kqLeavePO.setCause(httpServletRequest.getParameter("cause"));
      kqLeavePO.setStatus(100);
      kqLeaveBD.update(kqLeavePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    String leaveid = httpServletRequest.getParameter("leaveId");
    KqLeaveBD kqLeaveBD = new KqLeaveBD();
    KqLeavePO kqLeavePO = new KqLeavePO();
    try {
      kqLeavePO = kqLeaveBD.searchById((new Long(leaveid)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqLeavePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqLeavePO.setEndTime(calendar.getTime());
      kqLeavePO.setLeaveType(Integer.parseInt(httpServletRequest.getParameter("leaveType")));
      kqLeavePO.setCause(httpServletRequest.getParameter("cause"));
      kqLeavePO.setStatus(-1);
      kqLeaveBD.update(kqLeavePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  private KqLeavePO setPO(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    KqLeavePO kqLeavePO = new KqLeavePO();
    Calendar calendar = Calendar.getInstance();
    String start_date = request.getParameter("start_date");
    String beginTime = request.getParameter("beginTime");
    String end_date = request.getParameter("end_date");
    String endTime = request.getParameter("endTime");
    calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
    kqLeavePO.setStartTime(calendar.getTime());
    calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
    kqLeavePO.setEndTime(calendar.getTime());
    kqLeavePO.setUserId((new Long(session.getAttribute("userId").toString())).longValue());
    kqLeavePO.setOrgId((new Long(session.getAttribute("orgId").toString())).longValue());
    kqLeavePO.setUserName(session.getAttribute("userName").toString());
    kqLeavePO.setOrgName(session.getAttribute("orgName").toString());
    kqLeavePO.setStatus(0);
    kqLeavePO.setCause(request.getParameter("cause"));
    kqLeavePO.setLeaveType(Integer.parseInt(request.getParameter("leaveType")));
    return kqLeavePO;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String leaveid = httpServletRequest.getParameter("leaveId");
    KqLeaveBD kqLeaveBD = new KqLeaveBD();
    KqLeavePO kqLeavePO = new KqLeavePO();
    try {
      kqLeavePO = kqLeaveBD.searchById((new Long(leaveid)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqLeavePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqLeavePO.setEndTime(calendar.getTime());
      kqLeavePO.setLeaveType(Integer.parseInt(httpServletRequest.getParameter("leaveType")));
      kqLeavePO.setCause(httpServletRequest.getParameter("cause"));
      kqLeavePO.setStatus(-2);
      kqLeaveBD.update(kqLeavePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
