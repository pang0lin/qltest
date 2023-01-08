package com.js.oa.hr.kq.action;

import com.js.oa.hr.kq.service.WxKqBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.page.util.PageSqlUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WxKqAction extends Action {
  SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
  
  SimpleDateFormat ymd = new SimpleDateFormat("yyyy-M-d");
  
  public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ParseException {
    String action = request.getParameter("action");
    HttpSession session = request.getSession();
    Object object1 = session.getAttribute("userId");
    Object object2 = session.getAttribute("orgId");
    if ("day".equals(action)) {
      String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
      String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
      String numberId = (request.getParameter("numberId") == null) ? "" : request.getParameter("numberId");
      String date = "";
      if (request.getParameter("searchDate") != null && request.getParameter("searchDate").equals("1")) {
        date = request.getParameter("date").replace("/", "-");
        request.setAttribute("searchDate", "1");
      } else if (request.getParameter("goDate") != null) {
        date = request.getParameter("goDate");
      } 
      if (date == null || "".equals(date))
        date = this.ymd.format(new Date()); 
      long flag = getFlag(date);
      String select = "empName,work_empNumber,o.Orgnamestring,work_begin,work_end,work_status,work_length,w.work_id,e.emp_id,work_holiday ";
      String from = " wx_atdwork w join org_employee e on w.work_empId=e.emp_id join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id ";
      String where = " work_day=" + flag;
      if (!"".equals(numberId))
        where = String.valueOf(where) + " and e.empnumber='" + numberId + "'"; 
      if (!userId.equals("")) {
        userId = userId.replace("$$", ",").replace("$", "");
        where = String.valueOf(where) + " and w.work_empId in (" + userId + ")";
      } 
      if (!orgId.equals("")) {
        where = String.valueOf(where) + " and (";
        orgId = orgId.replace("**", ",").replace("*", "");
        String[] orgIds = orgId.split(",");
        for (int i = 0; i < orgIds.length; i++)
          where = String.valueOf(where) + " o.orgIdString like '%$" + orgIds[i] + "$%' " + ((i == orgIds.length - 1) ? "" : "or"); 
        where = String.valueOf(where) + ")";
      } 
      String whereTmp = " 1==1 ";
      if (request.getParameter("leader") == null || !"1".equals(request.getParameter("leader"))) {
        ManagerService managerBD = new ManagerService();
        whereTmp = managerBD.getRightWhere((String)object1, (String)object2, "07*04*04", "o.org_id", "o.emp_id");
      } else {
        whereTmp = "e.empLeaderId like '%$" + object1 + "$%'";
      } 
      where = String.valueOf(where) + " and " + whereTmp;
      String orderBy = " w.work_empNumber";
      if (request.getParameter("export") != null) {
        PageSqlUtil util = new PageSqlUtil(10000);
        List<Object> list = util.list(request, select, from, where, orderBy);
        String[] head = { "姓名", "工号", "部门", "上班打卡", "下班打卡", "说明", "工时（小时）" };
        String[] zhuangtai = { "正常上班", "正常下班", "迟到", "早退", "上班未打卡", "下班未打卡" };
        request.setAttribute("head", head);
        for (int i = 0; i < list.size(); i++) {
          Object[] single = (Object[])list.get(i);
          String[] zhuang = single[5].split(",");
          single[5] = String.valueOf(zhuangtai[Integer.valueOf(zhuang[0]).intValue()]) + "/" + zhuangtai[Integer.valueOf(zhuang[1]).intValue()];
          single[6] = (new StringBuilder(String.valueOf(myRound((Float.valueOf((String)single[6]).floatValue() / 3600.0F), 2)))).toString();
        } 
        request.setAttribute("data", list);
        request.setAttribute("title", "玩蟹科技考勤导出(" + date + ")");
        action = "export";
      } else {
        PageSqlUtil util = new PageSqlUtil();
        List<Object> list = util.list(request, select, from, where, orderBy);
        request.setAttribute("list", list);
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", (request.getParameter("userName") == null) ? "" : request.getParameter("userName"));
        request.setAttribute("orgName", (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName"));
        request.setAttribute("date", date);
      } 
    } else if ("week".equals(action) || "month".equals(action) || "jidu".equals(action)) {
      SimpleDateFormat ymdHan = new SimpleDateFormat("yyyy年M月d日");
      String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
      String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
      String date = "";
      if (request.getParameter("searchDate") != null && request.getParameter("searchDate").equals("1")) {
        date = request.getParameter("date").replace("/", "-");
        request.setAttribute("searchDate", "1");
      } else if (request.getParameter("goDate") != null) {
        date = request.getParameter("goDate");
      } 
      if (date == null || "".equals(date))
        date = this.ymd.format(new Date()); 
      Date[] dates = getBeginAndEndDate(action, this.ymd.parse(date));
      Date first = dates[0];
      Date last = dates[1];
      if (request.getParameter("export") != null) {
        List<Object> list = list(first, last, request, 10000);
        String[] head = { "姓名", "工号", "部门", "迟到", "上班未打卡", "早退", "下班未打卡", "工时(小时)" };
        request.setAttribute("head", head);
        List<String[]> dataList = (List)new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
          Object[] single = (Object[])list.get(i);
          String[] data = { (String)single[1], (String)single[0], (String)single[3], (String)single[4], (String)single[5], (String)single[6], 
              (String)single[7], (new StringBuilder(String.valueOf(myRound((Float.valueOf((String)single[8]).floatValue() / 3600.0F), 2)))).toString() };
          dataList.add(data);
        } 
        request.setAttribute("data", dataList);
        request.setAttribute("title", "玩蟹科技考勤导出(" + ("week".equals(action) ? "周" : ("month".equals(action) ? "月" : "季度")) + ")");
        action = "export";
      } else {
        request.setAttribute("list", list(first, last, request, 15));
        String lastDate = this.ymd.format(new Date(first.getTime() - 86400000L));
        String nextDate = this.ymd.format(new Date(last.getTime() + 86400000L));
        request.setAttribute("lastDate", lastDate);
        request.setAttribute("nextDate", nextDate);
        request.setAttribute("first", ymdHan.format(first));
        request.setAttribute("last", ymdHan.format(last));
        request.setAttribute("userId", userId);
        request.setAttribute("orgId", orgId);
        request.setAttribute("userName", (request.getParameter("userName") == null) ? "" : request.getParameter("userName"));
        request.setAttribute("orgName", (request.getParameter("orgName") == null) ? "" : request.getParameter("orgName"));
        request.setAttribute("date", date);
        action = "wmj";
      } 
    } else if ("more".equals(action)) {
      String empNum = request.getParameter("empNum");
      String date = request.getParameter("date");
      long flag = getFlag(date);
      WxKqBD bd = new WxKqBD();
      Map<String, String> map = bd.getKqMap(empNum, flag);
      request.setAttribute("map", map);
      String empId = request.getParameter("empId");
      String[] empInfo = bd.getEmpInfo(empId);
      request.setAttribute("empName", empInfo[0]);
      request.setAttribute("empNumber", empInfo[1]);
      request.setAttribute("empOrg", empInfo[2]);
    } else if ("wmjDay".equals(action)) {
      String flag = request.getParameter("flag");
      SimpleDateFormat ymdHan = new SimpleDateFormat("yyyy年M月d日");
      String userId = (request.getParameter("empId") == null) ? "0" : request.getParameter("empId");
      String see = request.getParameter("see");
      String date = request.getParameter("date");
      if (date == null || "".equals(date))
        date = ymdHan.format(new Date()); 
      Date[] dates = getBeginAndEndDate(flag, ymdHan.parse(date));
      Date first = dates[0];
      Date last = dates[1];
      String select = "empName,work_empNumber,o.Orgnamestring,work_begin,work_end,work_status,work_length,w.work_id,e.emp_id,work_holiday,work_date";
      String from = "wx_atdwork w join org_employee e on w.work_empId=e.emp_id join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id";
      String where = "work_day>" + (first.getTime() / 86400000L) + " and work_day<=" + (last.getTime() / 86400000L + 1L) + " and work_empId=" + userId;
      if ("normal".equals(see)) {
        where = String.valueOf(where) + " and work_status ='0,1' and work_holiday=0";
      } else if ("abnormal".equals(see)) {
        where = String.valueOf(where) + " and work_status <>'0,1' and work_holiday=0";
      } else if ("holiday".equals(see)) {
        where = String.valueOf(where) + " and work_holiday=1";
      } 
      String orderBy = " work_day ";
      List<Object> list = (new PageSqlUtil()).list(request, select, from, where, orderBy);
      request.setAttribute("list", list);
      WxKqBD bd = new WxKqBD();
      String[] empInfo = bd.getEmpInfo(userId);
      request.setAttribute("userName", empInfo[0]);
      request.setAttribute("userNum", empInfo[1]);
      request.setAttribute("userOrg", empInfo[2]);
      request.setAttribute("userId", empInfo[3]);
    } else if ("holidayList".equals(action)) {
      PageSqlUtil util = new PageSqlUtil();
      List<Object> list = util.list(request, "id,holiday_name,begin_date,end_date,memo,type", "kq_holiday", 
          "", "begin_date desc,id");
      request.setAttribute("list", list);
    } else if ("addHoliday".equals(action)) {
      String holidayName = request.getParameter("holidayName");
      String start_date = request.getParameter("start_date");
      String beginTime = request.getParameter("beginTime");
      String end_date = request.getParameter("end_date");
      String endTime = request.getParameter("endTime");
      String type = request.getParameter("type");
      String memo = request.getParameter("memo");
      WxKqBD bd = new WxKqBD();
      bd.addHoliday(new String[] { holidayName, String.valueOf(start_date.replace("/", "-")) + " " + beginTime + ":00", String.valueOf(end_date.replace("/", "-")) + " " + endTime + ":00", 
            type, memo });
      request.setAttribute("flag", action);
      action = "close";
    } else if ("updateHoliday".equals(action)) {
      String holidayName = request.getParameter("holidayName");
      String start_date = request.getParameter("start_date");
      String beginTime = request.getParameter("beginTime");
      String end_date = request.getParameter("end_date");
      String endTime = request.getParameter("endTime");
      String type = request.getParameter("type");
      String memo = request.getParameter("memo");
      String holidayId = request.getParameter("holidayId");
      WxKqBD bd = new WxKqBD();
      bd.upadteHoliday(new String[] { holidayName, String.valueOf(start_date.replace("/", "-")) + " " + beginTime + ":00", String.valueOf(end_date.replace("/", "-")) + " " + endTime + ":00", 
            type, memo, holidayId });
      request.setAttribute("flag", action);
      action = "close";
    } else if ("holidayUpdate".equals(action)) {
      String holidayId = request.getParameter("holidayId");
      WxKqBD bd = new WxKqBD();
      String[] holiday = bd.getHoliday(holidayId);
      request.setAttribute("holiday", holiday);
    } else if ("deleleHoliday".equals(action)) {
      String holidayId = request.getParameter("holidayId");
      WxKqBD bd = new WxKqBD();
      bd.deleteHoliday(holidayId);
      request.setAttribute("flag", action);
      action = "close";
    } 
    return actionMapping.findForward(action);
  }
  
  private List<Object> list(Date beginDate, Date endDate, HttpServletRequest request, int showNum) {
    HttpSession session = request.getSession();
    Object object1 = session.getAttribute("userId");
    Object object2 = session.getAttribute("orgId");
    String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId");
    String orgId = (request.getParameter("orgId") == null) ? "" : request.getParameter("orgId");
    String numberId = (request.getParameter("numberId") == null) ? "" : request.getParameter("numberId");
    String select = "distinct w.work_empNumber,e.empName,e.emp_Id,o.Orgnamestring,'' a,'' b,'' c,'' d,'' e,'' f";
    String from = " wx_atdwork w join org_employee e on w.work_empId=e.emp_id join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id ";
    String where = "w.work_day between " + getFlag(this.ymd.format(beginDate)) + " and " + getFlag(this.ymd.format(endDate));
    if (!"".equals(numberId))
      where = String.valueOf(where) + " and e.empnumber='" + numberId + "'"; 
    if (!userId.equals("")) {
      userId = userId.replace("$$", ",").replace("$", "");
      where = String.valueOf(where) + " and w.work_empId in (" + userId + ")";
    } 
    if (!orgId.equals("")) {
      where = String.valueOf(where) + " and (";
      orgId = orgId.replace("**", ",").replace("*", "");
      String[] orgIds = orgId.split(",");
      for (int j = 0; j < orgIds.length; j++)
        where = String.valueOf(where) + " o.orgIdString like '%$" + orgIds[j] + "$%' " + ((j == orgIds.length - 1) ? "" : "or"); 
      where = String.valueOf(where) + ")";
    } 
    String whereTmp = " 1==1 ";
    if (request.getParameter("leader") == null || !"1".equals(request.getParameter("leader"))) {
      ManagerService managerBD = new ManagerService();
      whereTmp = managerBD.getRightWhere((String)object1, (String)object2, "07*04*04", "o.org_id", "o.emp_id");
    } else {
      whereTmp = "e.empLeaderId like '%$" + object1 + "$%'";
    } 
    where = String.valueOf(where) + " and " + whereTmp;
    String orderBy = "w.work_empNumber";
    PageSqlUtil util = new PageSqlUtil(showNum);
    List<Object> list = util.list(request, select, from, where, orderBy);
    String empNum = "1=1";
    if (list.size() > 0) {
      empNum = "(";
      for (int j = 0; j < list.size(); j++) {
        Object[] single = (Object[])list.get(j);
        empNum = String.valueOf(empNum) + " work_empNumber='" + single[0] + "' " + ((j == list.size() - 1) ? "" : "or") + " ";
      } 
      empNum = String.valueOf(empNum) + ")";
    } 
    String sql = "select work_empNumber,work_status,work_length,work_date,work_holiday,work_empId from wx_atdwork where " + empNum + " " + 
      "and work_day between " + getFlag(this.ymd.format(beginDate)) + " and " + getFlag(this.ymd.format(endDate)) + " order by work_empNumber,work_day";
    WxKqBD bd = new WxKqBD();
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    List<String[]> dataList = bd.getDataList(sql);
    int i;
    for (i = 0; i < dataList.size(); i++) {
      String[] single = dataList.get(i);
      String[] mapStrs = (String[])null;
      if (map.get(single[0]) == null) {
        mapStrs = new String[] { "0", "0", "0", "0", "0", "0" };
      } else {
        mapStrs = map.get(single[0]);
      } 
      if (single[1].contains("2") && "0".equals(single[4]))
        mapStrs[0] = (new StringBuilder(String.valueOf(Integer.valueOf(mapStrs[0]).intValue() + 1))).toString(); 
      if (single[1].contains("4") && "0".equals(single[4]))
        mapStrs[1] = (new StringBuilder(String.valueOf(Integer.valueOf(mapStrs[1]).intValue() + 1))).toString(); 
      if (single[1].contains("3") && "0".equals(single[4]))
        mapStrs[2] = (new StringBuilder(String.valueOf(Integer.valueOf(mapStrs[2]).intValue() + 1))).toString(); 
      if (single[1].contains("5") && "0".equals(single[4]))
        mapStrs[3] = (new StringBuilder(String.valueOf(Integer.valueOf(mapStrs[3]).intValue() + 1))).toString(); 
      mapStrs[4] = (new StringBuilder(String.valueOf(Integer.valueOf(mapStrs[4]).intValue() + Integer.valueOf(single[2]).intValue()))).toString();
      mapStrs[5] = single[5];
      map.put(single[0], mapStrs);
    } 
    for (i = 0; i < list.size(); i++) {
      Object[] single = (Object[])list.get(i);
      String[] mapStr = map.get(single[0]);
      for (int j = 0; j < mapStr.length; j++)
        single[j + 4] = mapStr[j]; 
    } 
    return list;
  }
  
  private long getFlag(String dateStr) {
    try {
      Long nowLong = Long.valueOf(this.ymdhms.parse(String.valueOf(dateStr) + " 08:00:00").getTime());
      return nowLong.longValue() / 86400000L;
    } catch (Exception e) {
      e.printStackTrace();
      return 0L;
    } 
  }
  
  private double myRound(double number, int index) {
    double temp = Math.pow(10.0D, index);
    return Math.round(number * temp) / temp;
  }
  
  public Date[] getBeginAndEndDate(String flag, Date date) {
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    Date first = c.getTime();
    Date last = c.getTime();
    try {
      if ("week".equals(flag)) {
        c.setFirstDayOfWeek(2);
        c.set(7, c.getFirstDayOfWeek());
        first = c.getTime();
        c.set(7, c.getFirstDayOfWeek() + 6);
        last = c.getTime();
      } else if ("month".equals(flag)) {
        c.set(5, c.getActualMinimum(5));
        first = c.getTime();
        c.set(5, c.getActualMaximum(5));
        last = c.getTime();
      } else {
        int year = c.get(1);
        int month = c.get(2) + 1;
        if (month == 1 || month == 2 || month == 3) {
          first = this.ymd.parse(String.valueOf(year) + "-1-1");
          last = this.ymd.parse(String.valueOf(year) + "-3-31");
        } else if (month == 4 || month == 5 || month == 6) {
          first = this.ymd.parse(String.valueOf(year) + "-4-1");
          last = this.ymd.parse(String.valueOf(year) + "-6-30");
        } else if (month == 7 || month == 8 || month == 9) {
          first = this.ymd.parse(String.valueOf(year) + "-7-1");
          last = this.ymd.parse(String.valueOf(year) + "-9-30");
        } else {
          first = this.ymd.parse(String.valueOf(year) + "-10-1");
          last = this.ymd.parse(String.valueOf(year) + "-12-31");
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return new Date[] { first, last };
  }
}
