package com.js.oa.oasysremind.bean;

import com.js.oa.message.service.MsManageBD;
import com.js.oa.userdb.util.DbOpt;
import com.js.system.bean.groupmanager.GroupEJBBean;
import com.js.system.bean.usermanager.UserEJBBean;
import com.js.system.service.messages.RemindUtil;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.StringSplit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RemindOnTime {
  public void doRemind() {
    String databaseType = SystemCommon.getDatabaseType();
    Connection conn = null;
    Statement stmt = null;
    try {
      String jdbcSql = "";
      MsManageBD msBD = new MsManageBD();
      List<Object[]> remindList = null;
      Date date = new Date();
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      String sql = "select po.remindId,po.remindTableId,po.remindTableCode,po.remindType,po.remindIndex,po.compareType, po.compareValue,po.remindTimeUnit,po.onTimeMode,po.remindWeek,po.remindMonthDay,po.remindYearMonth,po.remindYearMonthDay, po.remindDefQueryCondition,po.remindDefQueryOrder,po.remindObjId,po.remindTitle,po.remindUrlUser,po.remindUrlSys,po.remindSourceType,po.remindTypeClass,po.remindCoun from OASysRemindPO po where 1=1 ";
      if (databaseType.indexOf("mysql") >= 0) {
        sql = String.valueOf(sql) + "and po.effectiveBegin <='" + format.format(date) + " 00:00:00" + "' and po.effectiveEnd >='" + format.format(date) + " 00:00:00" + "'";
      } else {
        sql = String.valueOf(sql) + " and (po.effectiveBegin<= to_date('" + format.format(date) + " 00:00:00" + "','yyyy-mm-dd hh24:mi:ss' ))";
        sql = String.valueOf(sql) + " and(po.effectiveEnd>= to_date('" + format.format(date) + " 00:00:00" + "','yyyy-mm-dd hh24:mi:ss' ))";
      } 
      sql = String.valueOf(sql) + " order by po.remindId desc ";
      remindList = msBD.getListByYourSQL(sql);
      if (remindList != null)
        System.out.println("提醒设置一共设置了：" + remindList.size() + "条"); 
      if (remindList != null && remindList.size() > 0)
        for (int i = 0; i < remindList.size(); i++) {
          Date beginDate = new Date();
          Object[] obj = remindList.get(i);
          String remindTitle = "";
          String[] trArr = (String[])null;
          DataSourceBase ds = new DataSourceBase();
          conn = ds.getDataSource().getConnection();
          stmt = conn.createStatement();
          List<String> titleIndexList = new ArrayList();
          boolean ifRemind = false;
          String remindSourceType = (String)obj[19];
          String menuId = null;
          String tableId = null;
          String[] tableIdArr = obj[1].toString().split(",");
          menuId = tableIdArr[0];
          tableId = tableIdArr[1];
          jdbcSql = "select " + obj[2] + "_id";
          if (obj[4] != null && !"".equals(obj[4])) {
            jdbcSql = String.valueOf(jdbcSql) + "," + obj[4];
          } else {
            jdbcSql = String.valueOf(jdbcSql) + "," + obj[2] + "_id";
          } 
          if (obj[16] != null && !"".equals(obj[16])) {
            remindTitle = obj[16].toString();
            trArr = remindTitle.split("=1=");
            for (int j = 0; j < trArr.length; j++) {
              String tr = trArr[j].toString();
              if (tr != null && !"".equals(tr)) {
                String[] tdArr = tr.split("=:=");
                for (int k = 0; k < tdArr.length && tdArr.length >= 2; k++) {
                  if (k == 1 && tdArr[1] != null && !"".equals(tdArr[1])) {
                    jdbcSql = String.valueOf(jdbcSql) + "," + tdArr[1];
                    titleIndexList.add("=:=" + tdArr[1] + "=:=");
                  } 
                } 
              } 
            } 
          } 
          jdbcSql = String.valueOf(jdbcSql) + "," + obj[2] + "_owner";
          String counString = jdbcSql;
          jdbcSql = String.valueOf(jdbcSql) + " from " + obj[2] + " where 1=1 ";
          if (obj[3] != null && "1".equals(obj[3])) {
            if (obj[5] != null && "like".equals(obj[5])) {
              jdbcSql = String.valueOf(jdbcSql) + " and " + getIndexTypeByIndex((obj[4] == null) ? "" : obj[4].toString()) + " like '%" + obj[6] + "%' ";
            } else {
              jdbcSql = String.valueOf(jdbcSql) + " and " + getIndexTypeByIndex((obj[4] == null) ? "" : obj[4].toString()) + obj[5] + "'" + obj[6] + "'";
            } 
            ifRemind = true;
          } 
          if (obj[3] != null && "2".equals(obj[3])) {
            if (databaseType.indexOf("mysql") >= 0) {
              if ("day".equals(obj[7])) {
                if (">".equals(obj[5])) {
                  jdbcSql = String.valueOf(jdbcSql) + " and DATEDIFF(" + obj[4] + ",NOW())>" + (Integer.valueOf((String)obj[6]).intValue() - 1);
                  jdbcSql = String.valueOf(jdbcSql) + " and DATEDIFF(" + obj[4] + ",NOW())<" + (Integer.valueOf((String)obj[6]).intValue() + 1);
                } 
                if ("<".equals(obj[5])) {
                  jdbcSql = String.valueOf(jdbcSql) + " and DATEDIFF(NOW()," + obj[4] + ")>" + (Integer.valueOf((String)obj[6]).intValue() - 1);
                  jdbcSql = String.valueOf(jdbcSql) + " and DATEDIFF(NOW()," + obj[4] + ")<" + (Integer.valueOf((String)obj[6]).intValue() + 1);
                } 
              } 
            } else if ("day".equals(obj[7])) {
              if (">".equals(obj[5])) {
                jdbcSql = String.valueOf(jdbcSql) + " and (to_date(" + obj[4] + ",'yyyy-mm-dd hh24:mi:ss')-sysdate) >" + (Integer.valueOf((String)obj[6]).intValue() - 1);
                jdbcSql = String.valueOf(jdbcSql) + " and (to_date(" + obj[4] + ",'yyyy-mm-dd hh24:mi:ss')-sysdate) <" + (Integer.valueOf((String)obj[6]).intValue() + 1);
              } 
              if ("<".equals(obj[5])) {
                jdbcSql = String.valueOf(jdbcSql) + " and (sysdate-to_date(" + obj[4] + ",'yyyy-mm-dd hh24:mi:ss')) >" + (Integer.valueOf((String)obj[6]).intValue() - 1);
                jdbcSql = String.valueOf(jdbcSql) + " and (sysdate-to_date(" + obj[4] + ",'yyyy-mm-dd hh24:mi:ss')) <" + (Integer.valueOf((String)obj[6]).intValue() + 1);
              } 
            } 
            ifRemind = true;
          } 
          if (obj[3] != null && "3".equals(obj[3])) {
            if ("1".equals(obj[8])) {
              int weekDay = Calendar.getInstance().get(7);
              weekDay = (weekDay == 1) ? 7 : (weekDay - 1);
              if (weekDay == Integer.valueOf(weekFormat(obj[9].toString())).intValue())
                ifRemind = true; 
            } 
            if ("2".equals(obj[8])) {
              int day = Calendar.getInstance().get(5);
              if (day == Integer.valueOf(obj[10].toString()).intValue())
                ifRemind = true; 
            } 
            if ("3".equals(obj[8])) {
              int month = Calendar.getInstance().get(2);
              int day = Calendar.getInstance().get(5);
              if (month + 1 == Integer.valueOf(obj[11].toString()).intValue() && day == Integer.valueOf(obj[12].toString()).intValue())
                ifRemind = true; 
            } 
          } 
          if (obj[13] != null && !"".equals(obj[13]))
            jdbcSql = String.valueOf(jdbcSql) + " and " + obj[13]; 
          if (obj[14] != null && !"".equals(obj[14]))
            jdbcSql = String.valueOf(jdbcSql) + " " + obj[14]; 
          if (ifRemind && remindTitle != null && !"".equals(remindTitle)) {
            String userIds = StringSplit.splitWith((obj[15] == null) ? "" : obj[15].toString(), "$", "*@");
            String org = StringSplit.splitWith((obj[15] == null) ? "" : obj[15].toString(), "*", "@$");
            String group = StringSplit.splitWith((obj[15] == null) ? "" : obj[15].toString(), "@", "$*");
            if (userIds != null && userIds.length() > 0) {
              userIds = userIds.substring(1, userIds.length() - 1);
              userIds = String.valueOf(userIds.replace("$$", ",")) + ",";
            } 
            if (org != null && org.length() > 0) {
              org = org.substring(1, org.length() - 1);
              org = org.replace("**", ",");
            } 
            if (group != null && group.length() > 0) {
              group = group.substring(1, group.length() - 1);
              group = group.replace("@@", ",");
            } 
            List<Object[]> userList = null;
            if (org != null && !"".equals(org)) {
              UserEJBBean userBean = new UserEJBBean();
              userList = userBean.selectEmpIdByOrgIds(org);
              if (userList != null)
                for (int m = 0; m < userList.size(); m++) {
                  String userId = String.valueOf(userList.get(m));
                  userIds = String.valueOf(userIds) + "," + userId + ",";
                }  
            } 
            GroupEJBBean groupBean = new GroupEJBBean();
            if (group != null && !"".equals(group)) {
              String[] groupArr = group.split(",");
              for (int n = 0; n < groupArr.length; n++) {
                userList = groupBean.selectGroupUser(groupArr[n]);
                if (userList != null)
                  for (int m = 0; m < userList.size(); m++) {
                    Object[] objGroup = userList.get(m);
                    userIds = String.valueOf(userIds) + "," + objGroup[0] + ",";
                  }  
              } 
            } 
            String defineURL = (obj[17] != null) ? obj[17].toString() : "";
            String URL = "";
            String allUserIds = "";
            int len = ((trArr == null) ? 0 : trArr.length) + 2;
            ResultSet rs = stmt.executeQuery(jdbcSql);
            while (rs.next()) {
              allUserIds = userIds;
              String id = rs.getObject(1).toString();
              if ("1".equals(remindSourceType)) {
                DbOpt db = new DbOpt();
                String count = db.executeQueryToStr("select count(8) from jsf_p_draft where process_id=" + menuId + " and record_id=" + id);
                db.close();
                if (!"0".equals(count))
                  continue; 
                DbOpt db1 = new DbOpt();
                count = db1.executeQueryToStr("select count(8) from jsf_work where workprocess_id=" + menuId + " and workrecord_id=" + id + " and workstatus=-1");
                db1.close();
                if (!"0".equals(count))
                  continue; 
              } 
              if (obj[20] != null && 
                obj[20].toString().indexOf("2") >= 0) {
                String listUsers = msBD.getListUsers(obj[21].toString(), String.valueOf(obj[2].toString()) + " where " + obj[2].toString() + "_id=" + id);
                allUserIds = String.valueOf(allUserIds) + listUsers.replace(",,", ",");
              } 
              if (obj[3] != null && "2".equals(obj[3]) && "hour".equals(obj[7])) {
                double hour = Double.valueOf(obj[6].toString()).doubleValue();
                String sw = rs.getObject(2).toString();
                String compareDate = dateFormart(rs.getObject(2).toString());
                if ("error format date".equals(compareDate))
                  continue; 
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String now = format2.format(new Date());
                if (">".equals(obj[5])) {
                  double shijiancha = getHourByDate(now, compareDate);
                  boolean f = (shijiancha > hour - 24.0D && shijiancha < hour + 24.0D);
                  if (!f)
                    continue; 
                  Calendar a = Calendar.getInstance();
                  a.setTime(format2.parse(compareDate));
                  int s = (int)(hour * 60.0D * 60.0D);
                  a.add(13, -s);
                  beginDate = a.getTime();
                } 
                if ("<".equals(obj[5])) {
                  double shijiancha = getHourByDate(compareDate, now);
                  boolean f = (shijiancha > hour - 24.0D && shijiancha < hour + 24.0D);
                  if (!f)
                    continue; 
                  Calendar a = Calendar.getInstance();
                  a.setTime(format2.parse(compareDate));
                  int s = (int)(hour * 60.0D * 60.0D);
                  a.add(13, s);
                  beginDate = a.getTime();
                } 
              } 
              String tempRemindTitle = remindTitle;
              for (int le = 2; le < len; le++)
                tempRemindTitle = tempRemindTitle.replace(titleIndexList.get(le - 2).toString(), (rs.getObject(le + 1) == null) ? "" : rs.getObject(le + 1).toString()); 
              tempRemindTitle = tempRemindTitle.replace("=1=", "");
              if ("".equals(defineURL)) {
                URL = "/jsoa/oasysremind/remindMessage.jsp?remindTableId=" + obj[1] + "&tId=" + id + "&remindSourceType=" + obj[19];
              } else {
                URL = defineURL;
              } 
              if (obj[20] != null)
                if (obj[20].toString().indexOf("1") >= 0) {
                  String beginString = counString.replace("select", "");
                  String[] intStrings = beginString.replace(" ", "").split(",");
                  allUserIds = String.valueOf(allUserIds) + rs.getObject(intStrings.length);
                }  
              String userNew = ",";
              allUserIds = allUserIds.replace(",,", ",");
              String[] userIdStrings = allUserIds.split(",");
              for (int j = 0; j < userIdStrings.length; j++) {
                if (!userNew.contains("," + userIdStrings[j] + ","))
                  userNew = String.valueOf(userNew) + userIdStrings[j] + ","; 
              } 
              RemindUtil.sendMessageToUsers2(tempRemindTitle, URL, userNew.substring(1, userNew.length() - 1), "remind", beginDate, new Date("2050/1/1"), "系统", new Long(id), 1);
            } 
            rs.close();
            stmt.close();
            conn.close();
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception er) {
          er.printStackTrace();
        }  
    } 
  }
  
  public String weekFormat(String ch) {
    String num = "";
    if ("一".equals(ch))
      num = "1"; 
    if ("二".equals(ch))
      num = "2"; 
    if ("三".equals(ch))
      num = "3"; 
    if ("四".equals(ch))
      num = "4"; 
    if ("五".equals(ch))
      num = "5"; 
    if ("六".equals(ch))
      num = "6"; 
    if ("日".equals(ch))
      num = "0"; 
    return num;
  }
  
  public String dateFormart(String date) {
    try {
      SimpleDateFormat bartDateFormat = null;
      if (date.length() == 16)
        date = String.valueOf(date) + ":00"; 
      if (date.length() == 15)
        date = String.valueOf(date) + "0:00"; 
      if (date.indexOf("/") > 0) {
        bartDateFormat = new SimpleDateFormat("yyyy/MM/dd");
      } else if (date.indexOf("-") > 0 && date.indexOf(":") < 0) {
        bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      } else if (date.indexOf(":") > 0) {
        bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      } else {
        bartDateFormat = new SimpleDateFormat("yyyyMMdd");
      } 
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date datef = bartDateFormat.parse(date);
      date = format.format(datef);
    } catch (ParseException e) {
      System.out.println("如果时间格式错误，那么跳过这条数据，原因：如客户在定义表的时间某一字段类型日期格式，但后来改了表结构，改成了日期，那么老数据不是日期格式，所以要路过。");
      return date = "error format date";
    } 
    return date;
  }
  
  public double getHourByDate(String begin, String end) {
    Double hour = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date beginTime = sdf.parse(begin);
      Date endTime = sdf.parse(end);
      long interval = (endTime.getTime() - beginTime.getTime()) / 1000L;
      hour = Double.valueOf(interval / 3600.0D);
    } catch (ParseException e) {
      e.printStackTrace();
    } 
    return hour.doubleValue();
  }
  
  public String getIndexTypeByIndex(String index) throws Exception {
    String indexType = "";
    String databaseType = SystemCommon.getDatabaseType();
    String sql = "select po.fieldId,po.show.showId from com.js.oa.jsflow.po.TFieldPO po where po.fieldName='" + index + "'";
    MsManageBD msBD = new MsManageBD();
    List<Object[]> reList = msBD.getListByYourSQL(sql);
    if (reList != null && reList.size() > 0) {
      Object[] obj = reList.get(0);
      indexType = obj[1].toString();
    } 
    if ("107".equals(indexType) || "109".equals(indexType)) {
      if ("107".equals(indexType)) {
        if ("oracle".equals(databaseType))
          return "to_char(to_date(" + index + ",'yyyy-mm-dd'),'yyyy-mm-dd')"; 
        return " STR_TO_DATE(" + index + ", '%Y-%m-%d')";
      } 
      if ("109".equals(indexType)) {
        if ("oracle".equals(databaseType))
          return "to_char(to_date(" + index + ",'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')"; 
        return " STR_TO_DATE(" + index + ", '%Y-%m-%d %k:%i')";
      } 
    } 
    return index;
  }
}
