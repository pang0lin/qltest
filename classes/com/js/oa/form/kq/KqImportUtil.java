package com.js.oa.form.kq;

import com.js.oa.hr.kq.po.KqDutyOutPO;
import com.js.oa.hr.kq.service.KqDutyOutBD;
import com.js.system.util.StaticParam;
import com.js.util.util.DataSourceBase;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KqImportUtil {
  public String[][] getDutySet(String userId) {
    return getDutySet(userId, "");
  }
  
  public String[][] getDutySet(String userId, String flag) {
    String sql = "SELECT duty_time1,duty_time2,duty_time3,duty_time4,duty_time5,duty_time6,workday,dutyhour,ifPunch,minNum,minNumDown FROM kq_duty_set ";
    sql = String.valueOf(sql) + " where user_ids LIKE '%$" + userId + "$%' ";
    String groupId = StaticParam.getGroupIdByEmpId(userId);
    String orgId = StaticParam.getOrgIdByEmpId(userId);
    String orgIds = "";
    if (!"".equals(orgId)) {
      orgIds = StaticParam.getParentOrgIdsByOrgId(orgId);
      String[] org = orgIds.split(",");
      for (int i = 0; i < org.length; i++)
        sql = String.valueOf(sql) + " OR org_ids LIKE '%*" + org[i] + "*%' "; 
    } 
    if (!"".equals(groupId)) {
      String[] group = groupId.split(",");
      for (int i = 0; i < group.length; i++)
        sql = String.valueOf(sql) + " OR group_ids LIKE '%@" + group[i] + "@%' "; 
    } 
    sql = String.valueOf(sql) + " order by id";
    String weekSet = "0000000";
    List<String[]> list = (List)new ArrayList<String>();
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next() && 
        !"1111111".equals(weekSet)) {
        String[] duty = (String[])null;
        if ("".equals(flag)) {
          duty = new String[8];
        } else {
          duty = new String[11];
        } 
        duty[0] = rs.getString(1);
        duty[1] = rs.getString(2);
        duty[2] = rs.getString(3);
        duty[3] = rs.getString(4);
        duty[4] = rs.getString(5);
        duty[5] = rs.getString(6);
        duty[7] = rs.getString(8);
        if ("0000000".equals(weekSet)) {
          weekSet = rs.getString(7);
          duty[6] = weekSet;
        } else {
          char[] week = weekSet.toCharArray();
          char[] duty7 = rs.getString(7).toCharArray();
          for (int i = 0; i < week.length; i++) {
            if (week[i] != '1') {
              if (duty7[i] == '1')
                week[i] = '1'; 
            } else {
              duty7[i] = '0';
            } 
          } 
          duty[6] = new String(duty7);
          weekSet = new String(week);
        } 
        if (!"0000000".equals(duty[6]))
          list.add(duty); 
        if (!"".equals(flag)) {
          duty[8] = rs.getString(9);
          duty[9] = rs.getString(10);
          duty[10] = rs.getString(11);
        } 
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    if (list.size() > 0) {
      String[][] arrayOfString = (String[][])null;
      if ("".equals(flag)) {
        arrayOfString = new String[list.size()][8];
        for (int i = 0; i < list.size(); i++)
          arrayOfString[i] = list.get(i); 
      } else {
        arrayOfString = new String[list.size()][11];
        for (int i = 0; i < list.size(); i++)
          arrayOfString[i] = list.get(i); 
      } 
      return arrayOfString;
    } 
    String[][] returnDuty = (String[][])null;
    if ("".equals(flag)) {
      returnDuty = new String[][] { { "08:30", "17:30", "0", "0", "0", "0", "1111111", "1" } };
    } else {
      returnDuty = new String[][] { { 
            "08:30", "17:30", "0", "0", "0", "0", "1111111", "1", "1,0,0,1,0,0", 
            "60,60,60,60,60,60", 
            "60,60,60,60,60,60" } };
    } 
    return returnDuty;
  }
  
  public float getHour(String beginDate, String endDate, String flag, String userId) {
    float returnValue = 0.0F;
    String[][] dutySetAll = getDutySet(userId);
    try {
      PaiBanUtil util = new PaiBanUtil();
      util.getHour(dutySetAll, beginDate, endDate);
      String[] dateStrs = util.getDateStrs();
      String[] longStrs = util.getDateHour();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      for (int i = 0; longStrs != null && i < longStrs.length; i++) {
        if (!"0".equals(longStrs[i])) {
          returnValue += Float.valueOf(longStrs[i]).floatValue();
          if (!"5".equals(flag)) {
            KqDutyOutPO po = new KqDutyOutPO();
            po.setOutEmp(userId);
            po.setOutDate(format.parse(dateStrs[i]));
            po.setOutHour(Float.valueOf(longStrs[i]).floatValue());
            po.setOutType(flag);
            po.setOutInputDate(new Date());
            KqDutyOutBD outBD = new KqDutyOutBD();
            outBD.add(po);
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return returnValue;
  }
}
