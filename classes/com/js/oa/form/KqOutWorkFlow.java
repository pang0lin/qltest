package com.js.oa.form;

import com.js.oa.hr.kq.po.KqOutPO;
import com.js.oa.hr.kq.service.KqOutBD;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class KqOutWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    KqOutBD bd = new KqOutBD();
    Long eveId = new Long(-1L);
    try {
      eveId = bd.add(setPO(httpServletRequest));
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return eveId;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    String outId = httpServletRequest.getParameter("outid");
    KqOutBD kqOutBD = new KqOutBD();
    KqOutPO kqOutPO = new KqOutPO();
    try {
      kqOutPO = kqOutBD.searchById((new Long(outId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOutPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(start_date) + " " + endTime));
      kqOutPO.setEndTime(calendar.getTime());
      kqOutPO.setPlace(httpServletRequest.getParameter("place"));
      kqOutPO.setCause(httpServletRequest.getParameter("cause"));
      kqOutBD.update(kqOutPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    String outId = httpServletRequest.getParameter("outid");
    KqOutPO kqOutPO = new KqOutPO();
    KqOutBD kqOutBD = new KqOutBD();
    try {
      kqOutPO = kqOutBD.searchById((new Long(outId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOutPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(start_date) + " " + endTime));
      kqOutPO.setEndTime(calendar.getTime());
      kqOutPO.setPlace(httpServletRequest.getParameter("place"));
      kqOutPO.setCause(httpServletRequest.getParameter("cause"));
      kqOutPO.setStatus(100);
      kqOutBD.update(kqOutPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    String outId = httpServletRequest.getParameter("outid");
    KqOutPO kqOutPO = new KqOutPO();
    KqOutBD kqOutBD = new KqOutBD();
    try {
      kqOutPO = kqOutBD.searchById((new Long(outId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOutPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(start_date) + " " + endTime));
      kqOutPO.setEndTime(calendar.getTime());
      kqOutPO.setPlace(httpServletRequest.getParameter("place"));
      kqOutPO.setCause(httpServletRequest.getParameter("cause"));
      kqOutPO.setStatus(-1);
      kqOutBD.update(kqOutPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  private KqOutPO setPO(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    KqOutPO kqOutPO = new KqOutPO();
    Calendar calendar = Calendar.getInstance();
    String start_date = request.getParameter("start_date");
    String beginTime = request.getParameter("beginTime");
    String endTime = request.getParameter("endTime");
    calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
    kqOutPO.setStartTime(calendar.getTime());
    calendar.setTime(new Date(String.valueOf(start_date) + " " + endTime));
    kqOutPO.setEndTime(calendar.getTime());
    kqOutPO.setSignTime(new Date());
    kqOutPO.setUserId((new Long(session.getAttribute("userId").toString())).longValue());
    kqOutPO.setOrgId((new Long(session.getAttribute("orgId").toString())).longValue());
    kqOutPO.setProjectId(0L);
    kqOutPO.setUserName(session.getAttribute("userName").toString());
    kqOutPO.setOrgName(session.getAttribute("orgName").toString());
    kqOutPO.setStatus(0);
    kqOutPO.setCause(request.getParameter("cause"));
    kqOutPO.setPlace(request.getParameter("place"));
    return kqOutPO;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String outId = httpServletRequest.getParameter("outid");
    KqOutPO kqOutPO = new KqOutPO();
    KqOutBD kqOutBD = new KqOutBD();
    try {
      kqOutPO = kqOutBD.searchById((new Long(outId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOutPO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(start_date) + " " + endTime));
      kqOutPO.setEndTime(calendar.getTime());
      kqOutPO.setPlace(httpServletRequest.getParameter("place"));
      kqOutPO.setCause(httpServletRequest.getParameter("cause"));
      kqOutPO.setStatus(-2);
      kqOutBD.update(kqOutPO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
