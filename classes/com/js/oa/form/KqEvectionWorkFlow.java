package com.js.oa.form;

import com.js.oa.hr.kq.po.KqEvectionPO;
import com.js.oa.hr.kq.service.KqEvectionBD;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class KqEvectionWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    KqEvectionBD bd = new KqEvectionBD();
    Long eveId = new Long(-1L);
    try {
      eveId = bd.add(setPO(httpServletRequest));
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return eveId;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    String evectionId = httpServletRequest.getParameter("evectionid");
    KqEvectionBD kqEvectionBD = new KqEvectionBD();
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    try {
      kqEvectionPO = kqEvectionBD.searchById((new Long(evectionId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqEvectionPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqEvectionPO.setEndTime(calendar.getTime());
      kqEvectionPO.setPlace(httpServletRequest.getParameter("place"));
      kqEvectionPO.setCause(httpServletRequest.getParameter("cause"));
      kqEvectionBD.update(kqEvectionPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    String evectionId = httpServletRequest.getParameter("evectionid");
    KqEvectionBD kqEvectionBD = new KqEvectionBD();
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    try {
      kqEvectionPO = kqEvectionBD.searchById((new Long(evectionId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqEvectionPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqEvectionPO.setEndTime(calendar.getTime());
      kqEvectionPO.setPlace(httpServletRequest.getParameter("place"));
      kqEvectionPO.setCause(httpServletRequest.getParameter("cause"));
      kqEvectionPO.setStatus(100);
      kqEvectionBD.update(kqEvectionPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    String evectionId = httpServletRequest.getParameter("evectionid");
    KqEvectionBD kqEvectionBD = new KqEvectionBD();
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    try {
      kqEvectionPO = kqEvectionBD.searchById((new Long(evectionId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqEvectionPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqEvectionPO.setEndTime(calendar.getTime());
      kqEvectionPO.setPlace(httpServletRequest.getParameter("place"));
      kqEvectionPO.setCause(httpServletRequest.getParameter("cause"));
      kqEvectionPO.setStatus(-1);
      kqEvectionBD.update(kqEvectionPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  private KqEvectionPO setPO(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    Calendar calendar = Calendar.getInstance();
    String start_date = request.getParameter("start_date");
    String beginTime = request.getParameter("beginTime");
    String end_date = request.getParameter("end_date");
    String endTime = request.getParameter("endTime");
    calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
    kqEvectionPO.setStartTime(calendar.getTime());
    calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
    kqEvectionPO.setEndTime(calendar.getTime());
    kqEvectionPO.setUserId((new Long(session.getAttribute("userId").toString())).longValue());
    kqEvectionPO.setOrgId((new Long(session.getAttribute("orgId").toString())).longValue());
    kqEvectionPO.setProjectId(0L);
    kqEvectionPO.setIsreturn(0);
    kqEvectionPO.setUserName(session.getAttribute("userName").toString());
    kqEvectionPO.setOrgName(request.getSession().getAttribute("orgName").toString());
    kqEvectionPO.setStatus(0);
    kqEvectionPO.setCause(request.getParameter("cause"));
    kqEvectionPO.setPlace(request.getParameter("place"));
    return kqEvectionPO;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String evectionId = httpServletRequest.getParameter("evectionid");
    KqEvectionBD kqEvectionBD = new KqEvectionBD();
    KqEvectionPO kqEvectionPO = new KqEvectionPO();
    try {
      kqEvectionPO = kqEvectionBD.searchById((new Long(evectionId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqEvectionPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqEvectionPO.setEndTime(calendar.getTime());
      kqEvectionPO.setPlace(httpServletRequest.getParameter("place"));
      kqEvectionPO.setCause(httpServletRequest.getParameter("cause"));
      kqEvectionPO.setStatus(-2);
      kqEvectionBD.update(kqEvectionPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
