package com.js.oa.chinaLife.bean;

import com.js.oa.chinaLife.util.IsHoliday;
import com.js.util.page.util.PageSqlUtil;
import com.js.util.page.util.PageUtil;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DataSourceUtil;
import com.js.util.util.IO2File;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class KqShowBean {
  private SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
  
  public List<String[]> getKqShowData(HttpServletRequest request, String userId, String startDate, String endDate, String orgId, String type, int num) {
    List<String[]> resultData = (List)new ArrayList<String>();
    String viewSql = "k.kq_id,k.kq_userId,k.kq_userName,k.kq_userNum,k.kq_userOrg,k.kq_date,k.kq_sb,k.kq_sbType,k.kq_sbBuD,k.kq_sbbqContext,k.kq_xb,k.kq_xbType,k.kq_xbBuD,k.kq_xbbqContext,o.Orgname ";
    String fromSql = " rst_kq k join org_organization o on k.kq_userorg=o.org_id ";
    String where = " k.kq_date between '" + startDate + "' and '" + endDate + "'";
    if (userId.equals("") && orgId.equals(""))
      where = String.valueOf(where) + " and 1<>1"; 
    if (!"".equals(userId))
      where = String.valueOf(where) + " and k.kq_userId in (" + userId + ") "; 
    if (!"".equals(orgId)) {
      where = String.valueOf(where) + " and (k.kq_userOrg in (select org_id from org_organization where (1<>1 ";
      String[] orgIds = orgId.replace("**", ",").replace("*", "").split(",");
      for (int j = 0; j < orgIds.length; j++)
        where = String.valueOf(where) + " or (orgIdString like'%$" + orgIds[j] + "$%' )"; 
      where = String.valueOf(where) + ") and orgstatus=0 ))";
    } 
    if (!"".equals(type)) {
      where = String.valueOf(where) + "and (1<>1 ";
      if (type.contains("迟到"))
        where = String.valueOf(where) + " or k.kq_sbType=2"; 
      if (type.contains("早退"))
        where = String.valueOf(where) + " or k.kq_xbType=2"; 
      if (type.contains("缺勤"))
        where = String.valueOf(where) + " or k.kq_sbType=4 or k.kq_xbType=4"; 
      if (type.contains("请假"))
        where = String.valueOf(where) + " or k.kq_sbType=5 or k.kq_xbType=5"; 
      if (type.contains("补签"))
        where = String.valueOf(where) + " or k.kq_sbType=3 or k.kq_xbType=3"; 
      where = String.valueOf(where) + ")";
    } 
    String orderBy = " k.kq_userId,k.kq_date";
    PageUtil page = new PageSqlUtil(num);
    List<Object> kqData = page.list(request, viewSql, fromSql, where, orderBy);
    List<String[]> qJList = (List)new ArrayList<String>();
    for (int i = 0; i < kqData.size(); i++) {
      String[] kq = (String[])kqData.get(i);
      String[] result = new String[15];
      result[0] = kq[14];
      result[1] = kq[3];
      result[2] = kq[2];
      result[3] = kq[5];
      result[4] = "白班";
      result[5] = "";
      result[6] = "";
      result[7] = "";
      result[8] = "";
      result[9] = "";
      result[10] = "";
      result[11] = "";
      result[12] = "";
      result[13] = kq[7];
      result[14] = kq[11];
      if (!"6".equals(kq[7]) && !"5".equals(kq[7]) && isQingjia(kq[1], kq[5], "09:00")) {
        result[13] = "5";
        result[5] = (kq[6].equals("09:00") || "".equals(kq[6])) ? "请假" : kq[6];
        result[10] = "1";
        qJList.add(new String[] { result[1], result[3], "1", "5" });
      } else {
        if (IsHoliday.isHoliday(kq[5]).equals("0")) {
          kq[7] = "6";
          result[13] = "6";
          qJList.add(new String[] { result[1], result[3], "1", "6" });
        } 
        if ("1".equals(kq[7])) {
          result[5] = kq[6];
        } else if ("2".equals(kq[7])) {
          result[5] = kq[6];
          result[7] = "1";
        } else if ("3".equals(kq[7])) {
          result[5] = "补签";
          result[11] = "1";
          result[12] = kq[9];
        } else if ("4".equals(kq[7])) {
          result[5] = "缺勤";
          result[9] = "1";
        } else if ("5".equals(kq[7])) {
          result[5] = (kq[6].equals("09:00") || "".equals(kq[6])) ? "请假" : kq[6];
          result[10] = "1";
        } else if ("6".equals(kq[7])) {
          result[5] = "公休";
        } 
      } 
      if (!"6".equals(kq[11]) && !"5".equals(kq[11]) && isQingjia(kq[1], kq[5], "17:00")) {
        result[14] = "5";
        result[6] = (kq[10].equals("17:00") || "".equals(kq[10])) ? "请假" : kq[10];
        result[10] = "".equals(result[10]) ? "1" : (String)Integer.valueOf(Integer.valueOf(result[10]).intValue() + 1);
        qJList.add(new String[] { result[1], result[3], "2", "5" });
      } else {
        if (IsHoliday.isHoliday(kq[5]).equals("0")) {
          kq[11] = "6";
          result[14] = "6";
          qJList.add(new String[] { result[1], result[3], "2", "6" });
        } 
        if ("1".equals(kq[11])) {
          result[6] = kq[10];
        } else if ("2".equals(kq[11])) {
          result[6] = kq[10];
          result[8] = "1";
        } else if ("3".equals(kq[11])) {
          result[6] = "补签";
          result[11] = "".equals(result[11]) ? "1" : (String)Integer.valueOf(Integer.valueOf(result[11]).intValue() + 1);
          result[12] = "".equals(result[12]) ? kq[13] : (String.valueOf(result[12]) + "<br />" + kq[13]);
        } else if ("4".equals(kq[11])) {
          result[6] = "缺勤";
          result[9] = "".equals(result[9]) ? "1" : (String)Integer.valueOf(Integer.valueOf(result[9]).intValue() + 1);
        } else if ("5".equals(kq[11])) {
          result[6] = (kq[10].equals("17:00") || "".equals(kq[10])) ? "请假" : kq[10];
          result[10] = "".equals(result[10]) ? "1" : (String)Integer.valueOf(Integer.valueOf(result[10]).intValue() + 1);
        } else if ("6".equals(kq[11])) {
          result[6] = "公休";
        } 
      } 
      resultData.add(result);
    } 
    updateKq(qJList);
    return resultData;
  }
  
  public boolean isQingjia(String userId, String dateStr, String timeStr) {
    boolean isQj = false;
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd H:m:s");
    try {
      Long curLong = Long.valueOf(ymdhms.parse(String.valueOf(dateStr) + " " + timeStr + ":00").getTime());
      String sql = "select qj_type,qj_startdate,qj_endDate,qj_sjshour,qj_sjjhour,qj_shour,qj_jhour,qj_type from rst_qj where qj_userId=" + 
        userId + " and '" + dateStr + "' between qj_startdate and qj_endDate and (qj_isqj=1 or qj_isqj=0) ";
      List<String[]> qjList = (new DataSourceUtil()).getListQuery(sql, "");
      for (int i = 0; i < qjList.size(); i++) {
        String[] qj = qjList.get(i);
        Long sLong = Long.valueOf(0L);
        Long eLong = Long.valueOf(0L);
        if ("事假".equals(qj[0])) {
          sLong = Long.valueOf(ymdhms.parse(String.valueOf(qj[1]) + " " + qj[3] + ":00:00").getTime());
          eLong = Long.valueOf(ymdhms.parse(String.valueOf(qj[2]) + " " + qj[4] + ":00:00").getTime());
        } else {
          if (qj[5].equals("上午")) {
            sLong = Long.valueOf(ymdhms.parse(String.valueOf(qj[1]) + " 09:00:00").getTime());
          } else {
            sLong = Long.valueOf(ymdhms.parse(String.valueOf(qj[1]) + " 13:00:00").getTime());
          } 
          if (qj[6].equals("上午")) {
            eLong = Long.valueOf(ymdhms.parse(String.valueOf(qj[2]) + " 12:00:00").getTime());
          } else {
            eLong = Long.valueOf(ymdhms.parse(String.valueOf(qj[2]) + " 17:00:00").getTime());
          } 
        } 
        if (curLong.longValue() >= sLong.longValue() && curLong.longValue() <= eLong.longValue()) {
          isQj = true;
          break;
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return isQj;
  }
  
  public List<String[]> getKqMealData(HttpServletRequest request, String userId, String startDate, String endDate, String orgId, int num) {
    int tianshu = 0;
    List<Object> result = null;
    try {
      Date start = this.ymd.parse(startDate);
      Date end = this.ymd.parse(endDate);
      for (Long l = Long.valueOf(start.getTime()); l.longValue() <= end.getTime(); l = Long.valueOf(l.longValue() + 86400000L))
        tianshu += Integer.valueOf(IsHoliday.isHoliday(this.ymd.format(new Date(l.longValue())))).intValue(); 
      String where = "";
      if (!"".equals(userId))
        where = String.valueOf(where) + " and k.kq_userId in (" + userId + ")"; 
      if (!"".equals(orgId))
        where = String.valueOf(where) + " and k.kq_userorg in (select org_id from org_organization where orgIdString like '%$" + orgId + "$%' and orgstatus=0)"; 
      startDate = this.ymd.format(start);
      endDate = this.ymd.format(end);
      String viewSql = "o.Orgname,e.Empname,'0' a,'0' b," + tianshu + ",k.kq_userId ";
      String fromSql = "rst_kq k join org_organization o on k.kq_userorg=o.org_id join org_employee e on k.kq_userId=e.emp_id ";
      String whereSql = " o.orgidstring like '%$20461$%' and (k.kq_date between '" + startDate + "' and '" + endDate + "') " + where + " " + 
        "group by k.kq_userId,o.orgName,e.Empname,o.orgidString ";
      String orderBy = "o.orgidString desc";
      PageUtil page = new PageSqlUtil(num);
      result = page.list(request, viewSql, fromSql, whereSql, orderBy);
      Map<String, Integer[]> mealMap = (Map)new HashMap<String, Integer>();
      String sql = "select count(kq_id),kq_userId from rst_kq k where (kq_date between '" + 
        startDate + "' and '" + endDate + "') and kq_sbtype<>6 " + 
        "and kq_sb>'09:00' and kq_sb<='11:30' " + where + " group by kq_userId";
      IO2File.printFile("早餐缺勤补助：" + sql, "人寿考勤", 3);
      List<String[]> mealList = (new DataSourceUtil()).getListQuery(sql, "0");
      int i;
      for (i = 0; i < mealList.size(); i++) {
        String[] meal = mealList.get(i);
        Integer[] m = { Integer.valueOf(meal[0]), Integer.valueOf(0) };
        mealMap.put(meal[1], m);
      } 
      sql = "select count(kq_id),kq_userId from rst_kq k where (kq_date between '" + 
        startDate + "' and '" + endDate + "') and kq_sbtype<>6 " + 
        "and kq_sb>'11:30' " + where + " group by kq_userId";
      IO2File.printFile("早餐午餐缺勤补助：" + sql, "人寿考勤", 3);
      mealList = (new DataSourceUtil()).getListQuery(sql, "0");
      for (i = 0; i < mealList.size(); i++) {
        String[] meal = mealList.get(i);
        if (mealMap.get(meal[1]) == null) {
          Integer[] m = { Integer.valueOf(meal[0]), Integer.valueOf(meal[0]) };
          mealMap.put(meal[1], m);
        } else {
          Integer[] m = mealMap.get(meal[1]);
          m[0] = Integer.valueOf(m[0].intValue() + Integer.valueOf(meal[0]).intValue());
          m[1] = Integer.valueOf(meal[0]);
          mealMap.put(meal[1], m);
        } 
      } 
      for (i = 0; i < result.size(); i++) {
        String[] r = (String[])result.get(i);
        if (mealMap.get(r[5]) != null) {
          Integer[] m = mealMap.get(r[5]);
          r[2] = (String)m[0];
          r[3] = (String)m[1];
        } 
        result.set(i, r);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return (List)result;
  }
  
  public List<String[]> getKqQingData(HttpServletRequest request, String userId, String year, String month, String orgId, String type, int num) {
    String viewSql = "q.qj_year,o.Orgnamestring,'',q.Qj_Username,e.empName,q.qj_duty,q.qj_zj,q.qj_njall,q.qj_njsy,case when q.qj_type is null then '因公出差' else q.qj_type end,q.qj_hourLong,q.qj_startdate,q.qj_enddate,qj_isLijing,qj_isChujing,qj_cause，q.qj_sjshour,q.qj_sjjhour,q.qj_shour,q.qj_jhour, q.qj_isqj,q.qj_fqr,q.qj_userId ";
    String fromSql = "rst_qj q join org_organization o on q.qj_userorg=o.org_id left join org_employee e on e.emp_id=q.qj_fqr ";
    String where = "(q.qj_isqj=1 or q.qj_isqj=0)";
    if (userId.equals("") && orgId.equals(""))
      where = String.valueOf(where) + " and 1<>1"; 
    if (!"".equals(userId))
      where = String.valueOf(where) + " and (q.qj_userId in (" + userId + ") or q.qj_fqr in (" + userId + "))"; 
    if (!"".equals(year))
      where = String.valueOf(where) + " and q.qj_year='" + year + "年'"; 
    if (!"".equals(month))
      where = String.valueOf(where) + " and q.qj_month='" + month + "月份'"; 
    if (!"".equals(type)) {
      if (!"kqQing".equals(request.getParameter("action"))) {
        if ("产假".equals(type))
          type = "晚育假,独生子女假,计划生育假,产假,孕假,哺乳假"; 
        if ("其他".equals(type))
          type = "探亲假,丧假,婚假,补休"; 
      } 
      where = String.valueOf(where) + " and q.qj_type in ('" + type.replace(",", "','") + "')";
    } 
    if (!"".equals(orgId)) {
      where = String.valueOf(where) + " and (q.qj_userOrg in (select org_id from org_organization where 1<>1 ";
      String[] orgIds = orgId.replace("**", ",").replace("*", "").split(",");
      for (int j = 0; j < orgIds.length; j++)
        where = String.valueOf(where) + " or (orgIdString like'%$" + orgIds[j] + "$%' and orgstatus=0)"; 
      where = String.valueOf(where) + "))";
    } 
    String orderBy = "q.qj_id desc";
    PageUtil page = new PageSqlUtil(num);
    List<Object> qjList = page.list(request, viewSql, fromSql, where, orderBy);
    for (int i = 0; i < qjList.size(); i++) {
      String[] qj = (String[])qjList.get(i);
      String[] orgNameStr = getDept(qj[1]);
      qj[1] = orgNameStr[1];
      qj[2] = orgNameStr[2];
      qj[10] = (new StringBuilder(String.valueOf(Float.valueOf(qj[10]).floatValue() / 8.0F))).toString();
      if ("事假".equals(qj[9])) {
        qj[11] = String.valueOf(qj[11]) + " " + qj[16] + ":00";
        qj[12] = String.valueOf(qj[12]) + " " + qj[17] + ":00";
      } else {
        qj[11] = String.valueOf(qj[11]) + " " + qj[18];
        qj[12] = String.valueOf(qj[12]) + " " + qj[19];
      } 
      if (qj[22].equals(qj[21]))
        qj[4] = "无"; 
      qjList.set(i, qj);
    } 
    return (List)qjList;
  }
  
  public List<Map<String, String>> getBumenDate(String year, String month) {
    List<Map<String, String>> bumenDate = new ArrayList<Map<String, String>>();
    DataSourceUtil util = new DataSourceUtil();
    String sql = "select org_id,orgName from org_organization where org_id<100000 and orglevel=1 and orgstatus=0 order by orgidstring";
    List<String[]> orgList = util.getListQuery(sql, "0");
    for (int i = 0; i < orgList.size(); i++) {
      String[] org = orgList.get(i);
      sql = "select count(qj_hourlong),case when qj_type is null then '因公请假' ELSE qj_type end from rst_qj where (qj_isqj=1 or qj_isqj=0) and qj_year='" + year + "年' and qj_month='" + month + "月份' and qj_userOrg in " + 
        "(select org_id from org_organization where orgIdString like '%$" + org[0] + "$%' and orgstatus=0) group by qj_type";
      Map<String, String> map = new HashMap<String, String>();
      map.put("orgId", org[0]);
      map.put("orgName", org[1]);
      List<String[]> typeList = util.getListQuery(sql, "");
      for (int t = 0; t < typeList.size(); t++) {
        String[] type = typeList.get(t);
        map.put(type[1], type[0]);
      } 
      sql = "select count(kq_id), '迟到' from rst_kq where kq_sbtype=2 and kq_date like '%" + year + "-" + ((month.length() < 2) ? ("0" + month) : month) + "%' and kq_userOrg in " + 
        "(select org_id from org_organization where orgIdString like '%$" + org[0] + "$%' and orgstatus=0) ";
      IO2File.printFile("部门考勤统计（迟到）：" + sql, "部门考勤统计", 3);
      typeList = util.getListQuery(sql, "");
      map.put("迟到", ((String[])typeList.get(0))[0]);
      sql = "select count(kq_id), '早退' from rst_kq where kq_xbtype=2 and kq_date like '%" + year + "-" + ((month.length() < 2) ? ("0" + month) : month) + "%' and kq_userOrg in " + 
        "(select org_id from org_organization where orgIdString like '%$" + org[0] + "$%' and orgstatus=0) ";
      IO2File.printFile("部门考勤统计（早退）：" + sql, "部门考勤统计", 3);
      typeList = util.getListQuery(sql, "");
      sql = "select count(kq_id) from (select kq_id,kq_sb from rst_kq where kq_sbtype=4 and kq_date like '%" + year + "-" + ((month.length() < 2) ? ("0" + month) : month) + "%' and kq_userOrg in " + 
        "(select org_id from org_organization where orgIdString like '%$" + org[0] + "$%' and orgstatus=0) " + 
        "UNION " + 
        "select kq_id,kq_xb from rst_kq where kq_xbtype=4 and kq_date like '%" + year + "-" + ((month.length() < 2) ? ("0" + month) : month) + "%' " + 
        "and kq_date<>'" + this.ymd.format(new Date()) + "' and kq_userOrg in " + 
        "(select org_id from org_organization where orgIdString like '%$" + org[0] + "$%' and orgstatus=0)) ";
      IO2File.printFile("部门考勤统计（缺勤）：" + sql, "部门考勤统计", 3);
      typeList = util.getListQuery(sql, "");
      map.put("缺勤", ((String[])typeList.get(0))[0]);
      if (map.get("产假") == null)
        map.put("产假", "0"); 
      if (map.get("其他") == null)
        map.put("其他", "0"); 
      for (String key : map.keySet()) {
        if (",晚育假,独生子女假,计划生育假,孕假,哺乳假,".contains("," + key + ",")) {
          int n = Integer.valueOf(map.get("产假")).intValue() + Integer.valueOf(map.get(key)).intValue();
          map.put("产假", (new StringBuilder(String.valueOf(n))).toString());
          continue;
        } 
        if (",探亲假,丧假,婚假,补休,".contains("," + key + ",")) {
          int n = Integer.valueOf(map.get("其他")).intValue() + Integer.valueOf(map.get(key)).intValue();
          map.put("其他", (new StringBuilder(String.valueOf(n))).toString());
        } 
      } 
      bumenDate.add(map);
      typeList.clear();
    } 
    return bumenDate;
  }
  
  public List<Map<String, String>> getLingdaoDate(String year, String month) {
    List<Map<String, String>> lingdaoDate = new ArrayList<Map<String, String>>();
    DataSourceUtil util = new DataSourceUtil();
    String sql = "select e.empName,o.Orgname,e.emp_id,o.org_id from org_employee e join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id where e.empDutyLevel in (3,4) and e.userIsActive=1 and e.userIsDeleted=0";
    List<String[]> empList = util.getListQuery(sql, "0");
    for (int i = 0; i < empList.size(); i++) {
      String[] emp = empList.get(i);
      sql = "select count(qj_hourlong),case when qj_type is null then '因公请假' ELSE qj_type end from rst_qj where (qj_isqj=1 or qj_isqj=0) and qj_year='" + 
        year + "年' and qj_month='" + month + "月份' and qj_userId=" + emp[2] + " group by qj_type";
      Map<String, String> map = new HashMap<String, String>();
      map.put("orgId", emp[3]);
      map.put("orgName", emp[1]);
      map.put("empId", emp[2]);
      map.put("empName", emp[0]);
      List<String[]> typeList = util.getListQuery(sql, "");
      for (int t = 0; t < typeList.size(); t++) {
        String[] type = typeList.get(t);
        map.put(type[1], type[0]);
      } 
      sql = "select count(kq_id), '迟到' from rst_kq where kq_xbtype=2 and kq_date like '%" + year + "-" + ((month.length() < 2) ? ("0" + month) : month) + "%' " + 
        "and kq_userId=" + emp[2];
      typeList = util.getListQuery(sql, "");
      map.put("迟到", ((String[])typeList.get(0))[0]);
      if (map.get("产假") == null)
        map.put("产假", "0"); 
      if (map.get("其他") == null)
        map.put("其他", "0"); 
      for (String key : map.keySet()) {
        if (",晚育假,独生子女假,计划生育假,孕假,哺乳假,".contains("," + key + ",")) {
          int n = Integer.valueOf(map.get("产假")).intValue() + Integer.valueOf(map.get(key)).intValue();
          map.put("产假", (new StringBuilder(String.valueOf(n))).toString());
          continue;
        } 
        if (",探亲假,丧假,婚假,补休,".contains("," + key + ",")) {
          int n = Integer.valueOf(map.get("其他")).intValue() + Integer.valueOf(map.get(key)).intValue();
          map.put("其他", (new StringBuilder(String.valueOf(n))).toString());
        } 
      } 
      lingdaoDate.add(map);
      typeList.clear();
    } 
    return lingdaoDate;
  }
  
  public List<String[]> getBuqianData(HttpServletRequest request, String userId, String orgId, String type, String startDate, String endDate, int num) {
    String joinOn = "";
    String fields = "distinct b.rs_bqd_f3363,b.rs_bqd_f3359,b.rs_bqd_f3360,e.empName empName,b.rs_bqd_f3361,w.workstatus,w.wf_work_id,b.rs_bqd_id,rs_bqd_owner,rs_bqd_bqr,oe.empName oeName,b.rs_bqd_tjrq";
    if ("1".equals(type)) {
      fields = String.valueOf(fields) + ",d.id ";
      joinOn = String.valueOf(joinOn) + " LEFT outer JOIN jsf_work w ON b.rs_bqd_id= w.workrecord_id AND w.workprocess_id=126586 AND w.workstepcount=0 ";
      joinOn = String.valueOf(joinOn) + " LEFT OUTER JOIN JSF_P_DRAFT d ON b.rs_bqd_id=d.record_id AND d.process_id=126586 ";
    } else if ("2".equals(type)) {
      fields = String.valueOf(fields) + ",'' ";
      joinOn = String.valueOf(joinOn) + "JOIN jsf_work w ON b.rs_bqd_id= w.workrecord_id AND w.workprocess_id=126586 AND w.workstatus=0 AND w.wf_curemployee_id=" + userId;
    } else {
      fields = String.valueOf(fields) + ",'' ";
      joinOn = String.valueOf(joinOn) + " JOIN jsf_work w ON b.rs_bqd_id= w.workrecord_id AND w.workprocess_id=126586 AND w.workstepcount=0 ";
    } 
    String fromSql = " rs_bqd b JOIN org_employee e ON b.rs_bqd_bqr=e.emp_id join org_employee oe on oe.emp_id=b.rs_bqd_owner " + joinOn;
    String where = " 1=1 ";
    if (!"".equals(userId) && "".equals(type))
      where = String.valueOf(where) + "AND b.rs_bqd_owner=" + userId; 
    if (!"".equals(orgId) && "".equals(type)) {
      where = String.valueOf(where) + " and (b.rs_bqd_org in (select org_id from org_organization where 1<>1 ";
      String[] orgIds = orgId.replace("**", ",").replace("*", "").split(",");
      for (int i = 0; i < orgIds.length; i++)
        where = String.valueOf(where) + " or (orgIdString like'%$" + orgIds[i] + "$%' and orgstatus=0)"; 
      where = String.valueOf(where) + "))";
    } 
    if (!"".equals(startDate) && !"".equals(endDate)) {
      try {
        startDate = this.ymd.format(this.ymd.parse(startDate));
        endDate = this.ymd.format(this.ymd.parse(endDate));
      } catch (ParseException e) {
        e.printStackTrace();
      } 
      where = String.valueOf(where) + " and b.rs_bqd_tjrq between '" + startDate + "' and '" + endDate + "' ";
    } 
    String orderBy = " B.RS_BQD_ID DESC";
    PageUtil page = new PageSqlUtil(num);
    List<Object> bqList = page.list(request, fields, fromSql, where, orderBy);
    return (List)bqList;
  }
  
  public Map<String, List<String[]>> getKouxinMap(String userId, String year, String month) {
    Map<String, List<String[]>> rMap = new HashMap<String, List<String[]>>();
    DataSourceBase base = new DataSourceBase();
    String sql = "";
    String type = "病假,产假,晚育假,独生子女假,计划生育假,探亲假";
    boolean showShi = false;
    try {
      base.begin();
      sql = "SELECT SUM(qj_hourlong) FROM rst_qj WHERE (qj_isqj=0 OR qj_isqj=1) and qj_userId=" + 
        userId + " AND qj_type='事假' and qj_year='" + year + "年' " + 
        "GROUP BY qj_type,qj_userId";
      IO2File.printFile("获得" + year + "年事假总天数：" + sql, "扣薪统计", 3);
      ResultSet rs = base.executeQuery(sql);
      if (rs.next()) {
        Integer hour = Integer.valueOf(rs.getInt(1));
        if (hour.intValue() > 40)
          showShi = true; 
      } 
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      base.end();
    } 
    List<String[]> qjList = (List)new ArrayList<String>();
    sql = "SELECT o.orgNameString,'',q.qj_userName,q.qj_hourlong,q.qj_startdate,q.qj_enddate@select@ FROM rst_qj q JOIN org_organization o ON q.Qj_Userorg=o.org_id WHERE (q.qj_isqj=0 OR q.qj_isqj=1) and q.qj_userId=" + 
      
      userId + " and q.qj_hourlong>0 and q.qj_year='" + year + "年' @where@" + 
      "ORDER BY q.qj_type,q.qj_startdate";
    if (showShi) {
      String str = sql.replace("@select@", ",q.qj_isqj,q.qj_sjsHour,q.qj_sjjhour").replace("@where@", "and q.qj_type='事假' ");
      IO2File.printFile("获得" + year + "年扣薪数据（事假）：" + str, "扣薪统计", 3);
      qjList = (new DataSourceUtil()).getListQuery(str, "");
      for (int j = 0; j < qjList.size(); j++) {
        String[] qj = qjList.get(j);
        String[] orgName = getDept(qj[0]);
        qj[0] = orgName[1];
        qj[1] = orgName[2];
        qj[3] = (new StringBuilder(String.valueOf(Float.valueOf(qj[3]).floatValue() / 8.0F))).toString();
        qj[4] = String.valueOf(qj[4]) + " " + qj[7] + ":00";
        qj[5] = String.valueOf(qj[5]) + " " + qj[8] + ":00";
        if (rMap.get("事假") == null) {
          List<String[]> list = (List)new ArrayList<String>();
          list.add(qj);
          rMap.put("事假", list);
        } else {
          List<String[]> list = rMap.get("事假");
          list.add(qj);
          rMap.put("事假", list);
        } 
      } 
      qjList.clear();
    } 
    String eSql = sql.replace("@select@", ",q.qj_isqj,q.qj_type,q.qj_shour,q.qj_jhour")
      .replace("@where@", "and q.qj_type in ('" + type.replace(",", "','") + "') @month@");
    if (!month.equals("")) {
      eSql = eSql.replace("@month@", " and q.qj_month='" + month + "月份' ");
    } else {
      eSql = eSql.replace("@month@", " ");
    } 
    IO2File.printFile("获得" + year + "年" + month + "月扣薪数据（病假,产假,晚育假,独生子女假,计划生育假,探亲假）：" + eSql, "扣薪统计", 3);
    qjList = (new DataSourceUtil()).getListQuery(eSql, "");
    for (int i = 0; i < qjList.size(); i++) {
      String[] qj = qjList.get(i);
      String[] orgName = getDept(qj[0]);
      qj[0] = orgName[1];
      qj[1] = orgName[2];
      qj[3] = (new StringBuilder(String.valueOf(Float.valueOf(qj[3]).floatValue() / 8.0F))).toString();
      qj[4] = String.valueOf(qj[4]) + " " + qj[8];
      qj[5] = String.valueOf(qj[5]) + " " + qj[9];
      if (rMap.get(qj[7]) == null) {
        List<String[]> list = (List)new ArrayList<String>();
        list.add(qj);
        rMap.put(qj[7], list);
      } else {
        List<String[]> list = rMap.get(qj[7]);
        list.add(qj);
        rMap.put(qj[7], list);
      } 
    } 
    return rMap;
  }
  
  public List<String[]> qjType() {
    String sql = "SELECT base_value,base_key FROM tbase WHERE base_parent=21078";
    return (new DataSourceUtil()).getListQuery(sql, "");
  }
  
  public String[] ldString() {
    String ldId = "";
    String ldName = "";
    String sql = "select emp_id,empName from org_employee where empDutyLevel in (3,4) and userIsActive=1 and userIsDeleted=0";
    List<String[]> list = (new DataSourceUtil()).getListQuery(sql, "");
    for (int i = 0; i < list.size(); i++) {
      String[] ldInfo = list.get(i);
      ldId = String.valueOf(ldId) + "$" + ldInfo[0] + "$";
      ldName = String.valueOf(ldName) + ldInfo[1] + ",";
    } 
    return new String[] { ldId, ldName };
  }
  
  public void updateKq(List<String[]> qJList) {
    if (qJList.size() > 0) {
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        for (int i = 0; i < qJList.size(); i++) {
          String[] qj = qJList.get(i);
          if (qj[2].equals("1")) {
            base.addBatch("update rst_kq set kq_sbtype=" + qj[3] + " where kq_date='" + qj[1] + "' and kq_usernum='" + qj[0] + "' ");
          } else {
            base.addBatch("update rst_kq set kq_xbtype=" + qj[3] + " where kq_date='" + qj[1] + "' and kq_usernum='" + qj[0] + "' ");
          } 
        } 
        base.executeBatch();
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
  }
  
  public String[] getDept(String deptStr) {
    String[] r = { "", "", "" };
    if (deptStr.indexOf(".") > 0) {
      String[] strs = deptStr.split("\\.");
      for (int i = 0; i < strs.length; i++)
        r[i] = strs[i]; 
    } else {
      r[0] = deptStr;
    } 
    return r;
  }
  
  public String[] getUserArray(String userId, String orgId) {
    if (!userId.equals("") && userId.indexOf("$") < 0)
      userId = "$" + userId + "$"; 
    if (!orgId.equals("")) {
      String[] orgIds = orgId.replace("**", ",").replace("*", "").split(",");
      DataSourceBase base = new DataSourceBase();
      try {
        base.begin();
        String sql = "select e.emp_id from org_employee e join org_organization_user ou on e.emp_id=ou.emp_id join org_organization o on ou.org_id=o.org_id where e.userisactive=1 and e.userisdeleted=0 ";
        if (orgIds.length > 0 && !"".equals(orgIds[0])) {
          sql = String.valueOf(sql) + " and (1<>1 ";
          for (int i = 0; i < orgIds.length; i++)
            sql = String.valueOf(sql) + " or o.orgidstring like '%" + orgIds[i] + "%'"; 
          sql = String.valueOf(sql) + ")";
        } 
        sql = String.valueOf(sql) + " order by o.orgidstring,e.empdutylevel";
        ResultSet rs = base.executeQuery(sql);
        while (rs.next()) {
          if (!userId.contains("$" + rs.getString(1) + "$"))
            userId = String.valueOf(userId) + "$" + rs.getString(1) + "$"; 
        } 
        rs.close();
        base.end();
      } catch (Exception e) {
        base.end();
        e.printStackTrace();
      } 
    } 
    return userId.replace("$$", ",").replace("$", "").split(",");
  }
}
