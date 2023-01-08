package com.js.oa.form;

import com.js.oa.hr.kq.po.KqOvertimePO;
import com.js.oa.hr.kq.po.KqOvertimeUserPO;
import com.js.oa.hr.kq.service.KqOvertimeBD;
import com.js.system.service.messages.RemindUtil;
import com.js.system.service.usermanager.UserBD;
import com.js.util.util.StringSplit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class KqOvertimeWorkFlow {
  public Long save(HttpServletRequest httpServletRequest) {
    KqOvertimeBD bd = new KqOvertimeBD();
    Long eveId = new Long(-1L);
    try {
      eveId = bd.add(setPO(httpServletRequest));
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return eveId;
  }
  
  public Long update(HttpServletRequest httpServletRequest) {
    String overtimeId = httpServletRequest.getParameter("overtimeid");
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    KqOvertimeBD kqOvertimeBD = new KqOvertimeBD();
    try {
      kqOvertimePO = kqOvertimeBD.searchById((new Long(overtimeId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOvertimePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqOvertimePO.setEndTime(calendar.getTime());
      String userOrgGroup = httpServletRequest.getParameter("userOrgGroup");
      kqOvertimePO.setAttendUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      kqOvertimePO.setAttendOrgId(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      kqOvertimePO.setAttendGroupId(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      kqOvertimePO.setProjectId(0L);
      kqOvertimePO.setCause(httpServletRequest.getParameter("cause"));
      kqOvertimePO.setAttendUserName(httpServletRequest.getParameter("attendUserName"));
      kqOvertimePO.setStatus(100);
      kqOvertimeBD.update(kqOvertimePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long back(HttpServletRequest httpServletRequest) {
    return null;
  }
  
  public Long complete(HttpServletRequest httpServletRequest) {
    String overtimeId = httpServletRequest.getParameter("overtimeid");
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    KqOvertimeBD kqOvertimeBD = new KqOvertimeBD();
    try {
      kqOvertimePO = kqOvertimeBD.searchById((new Long(overtimeId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOvertimePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqOvertimePO.setEndTime(calendar.getTime());
      String userOrgGroup = httpServletRequest.getParameter("userOrgGroup");
      kqOvertimePO.setAttendUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      kqOvertimePO.setAttendOrgId(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      kqOvertimePO.setAttendGroupId(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      kqOvertimePO.setProjectId(0L);
      kqOvertimePO.setCause(httpServletRequest.getParameter("cause"));
      kqOvertimePO.setAttendUserName(httpServletRequest.getParameter("attendUserName"));
      kqOvertimePO.setStatus(100);
      kqOvertimeBD.update(kqOvertimePO);
      UserBD userBD = new UserBD();
      String userIds = "";
      userIds = StringSplit.splitWith(userOrgGroup, "$", "*@");
      if (userIds != null && !"".equals(userIds)) {
        userIds = userIds.substring(1, userIds.length() - 1);
        userIds = userIds.replace("$$", ",");
        userIds = String.valueOf(userIds) + ",";
      } 
      String orgIds = "";
      orgIds = StringSplit.splitWith(userOrgGroup, "*", "@$");
      if (orgIds != null && !"".equals(orgIds)) {
        orgIds = orgIds.substring(1, orgIds.length() - 1);
        orgIds = orgIds.replace("**", ",");
        List<E> list = new ArrayList();
        list = userBD.selectEmpIdByOrgIds(orgIds);
        if (!list.isEmpty() && list.size() > 0)
          for (int i = 0; i < list.size(); i++)
            userIds = String.valueOf(userIds) + list.get(i).toString() + ",";  
      } 
      userIds = userIds.substring(0, userIds.length() - 1);
      List<Object[]> list1 = new ArrayList();
      list1 = userBD.selectEmpIdsAndOrgIds(userIds);
      if (!list1.isEmpty() && list1.size() > 0)
        for (int i = 0; i < list1.size(); i++) {
          Object[] obj = list1.get(i);
          KqOvertimeUserPO kqOvertimeUserPO = new KqOvertimeUserPO();
          kqOvertimeUserPO.setUserId((new Long(obj[0].toString())).longValue());
          kqOvertimeUserPO.setUserName(obj[1].toString());
          kqOvertimeUserPO.setOrgId((new Long(obj[2].toString())).longValue());
          kqOvertimeUserPO.setOvertimeId((new Long(overtimeId)).longValue());
          kqOvertimeBD.addKqOvertimeUser(kqOvertimeUserPO);
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
          String title = String.valueOf(dateFormat.format(new Date(String.valueOf(start_date) + " " + beginTime))) + "加班通知";
          String url = "KqOvertimeAction.do?action=detail&overtimeid=" + overtimeId;
          RemindUtil.sendMessageToUsers(title, url, obj[0].toString(), "KqOvertime", new Date(), calendar.getTime(), kqOvertimePO.getUserName(), new Long(overtimeId));
        }  
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  public Long untread(HttpServletRequest httpServletRequest) {
    String overtimeId = httpServletRequest.getParameter("overtimeid");
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    KqOvertimeBD kqOvertimeBD = new KqOvertimeBD();
    try {
      kqOvertimePO = kqOvertimeBD.searchById((new Long(overtimeId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOvertimePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqOvertimePO.setEndTime(calendar.getTime());
      String userOrgGroup = httpServletRequest.getParameter("userOrgGroup");
      kqOvertimePO.setAttendUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      kqOvertimePO.setAttendOrgId(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      kqOvertimePO.setAttendGroupId(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      kqOvertimePO.setProjectId(0L);
      kqOvertimePO.setCause(httpServletRequest.getParameter("cause"));
      kqOvertimePO.setAttendUserName(httpServletRequest.getParameter("attendUserName"));
      kqOvertimePO.setStatus(-1);
      kqOvertimeBD.update(kqOvertimePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return null;
  }
  
  private KqOvertimePO setPO(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    String domainId = (session.getAttribute("domainId") == null) ? "0" : 
      session.getAttribute("domainId").toString();
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    Calendar calendar = Calendar.getInstance();
    String start_date = request.getParameter("start_date");
    String beginTime = request.getParameter("beginTime");
    String end_date = request.getParameter("end_date");
    String endTime = request.getParameter("endTime");
    calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
    kqOvertimePO.setStartTime(calendar.getTime());
    calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
    kqOvertimePO.setEndTime(calendar.getTime());
    String userOrgGroup = request.getParameter("userOrgGroup");
    kqOvertimePO.setAttendUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
    kqOvertimePO.setAttendOrgId(StringSplit.splitWith(userOrgGroup, "*", "@$"));
    kqOvertimePO.setAttendGroupId(StringSplit.splitWith(userOrgGroup, "@", "$*"));
    kqOvertimePO.setUserId((new Long(session.getAttribute("userId").toString())).longValue());
    kqOvertimePO.setOrgId((new Long(session.getAttribute("orgId").toString())).longValue());
    kqOvertimePO.setOrgName(session.getAttribute("orgName").toString());
    kqOvertimePO.setProjectId(0L);
    kqOvertimePO.setUserName(session.getAttribute("userName").toString());
    kqOvertimePO.setStatus(0);
    kqOvertimePO.setCause(request.getParameter("cause"));
    kqOvertimePO.setAttendUserName(request.getParameter("attendUserName"));
    return kqOvertimePO;
  }
  
  public void delete(HttpServletRequest httpServletRequest) {
    String overtimeId = httpServletRequest.getParameter("overtimeid");
    KqOvertimePO kqOvertimePO = new KqOvertimePO();
    KqOvertimeBD kqOvertimeBD = new KqOvertimeBD();
    try {
      kqOvertimePO = kqOvertimeBD.searchById((new Long(overtimeId)).longValue());
      Calendar calendar = Calendar.getInstance();
      String start_date = httpServletRequest.getParameter("start_date");
      String beginTime = httpServletRequest.getParameter("beginTime");
      String end_date = httpServletRequest.getParameter("end_date");
      String endTime = httpServletRequest.getParameter("endTime");
      calendar.setTime(new Date(String.valueOf(start_date) + " " + beginTime));
      kqOvertimePO.setStartTime(calendar.getTime());
      calendar.setTime(new Date(String.valueOf(end_date) + " " + endTime));
      kqOvertimePO.setEndTime(calendar.getTime());
      String userOrgGroup = httpServletRequest.getParameter("userOrgGroup");
      kqOvertimePO.setAttendUserId(StringSplit.splitWith(userOrgGroup, "$", "*@"));
      kqOvertimePO.setAttendOrgId(StringSplit.splitWith(userOrgGroup, "*", "@$"));
      kqOvertimePO.setAttendGroupId(StringSplit.splitWith(userOrgGroup, "@", "$*"));
      kqOvertimePO.setProjectId(0L);
      kqOvertimePO.setCause(httpServletRequest.getParameter("cause"));
      kqOvertimePO.setAttendUserName(httpServletRequest.getParameter("attendUserName"));
      kqOvertimePO.setStatus(-2);
      kqOvertimeBD.update(kqOvertimePO);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
}
