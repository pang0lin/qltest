package com.js.oa.hr.kq.util;

import com.js.oa.form.kq.KqImportUtil;
import com.js.oa.form.kq.PaiBanUtil;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.ExcelOperate;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportKqData {
  private String databaseType = SystemCommon.getDatabaseType();
  
  SimpleDateFormat riqiFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
  
  public String[] importData(String[] sqlInfo, String flag, String filePath) throws Exception {
    System.out.println("考勤同步时间：" + this.dateFormat.format(new Date()));
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    Statement stmt = null;
    String[] returnValue = new String[2];
    List<Object[]> list = new ArrayList();
    String com = "";
    String empId = "";
    String[][] dutySet = (String[][])null;
    String dateString = "";
    List<String[]> daKaList = (List)new ArrayList<String>();
    if ("excel".equals(flag)) {
      File file = new File(filePath);
      String[][] result = (String[][])null;
      String[][] resultExcel = ExcelOperate.getData(file, 4);
      int arrNum = (resultExcel[0]).length;
      if (arrNum == 3) {
        result = new String[resultExcel.length][4];
      } else {
        result = resultExcel;
      } 
      String[] paiBan = (String[])null;
      PaiBanUtil pUtil = new PaiBanUtil();
      KqImportUtil util = new KqImportUtil();
      String riqiStr = ",";
      int i;
      for (i = 0; i < resultExcel.length; i++) {
        String[] importExcel = resultExcel[i];
        if (arrNum == 3) {
          result[i][0] = importExcel[0];
          result[i][1] = importExcel[1];
          Date riqiDate = this.dateFormat.parse(importExcel[2].replace("/", "-"));
          result[i][2] = this.riqiFormat.format(riqiDate);
          result[i][3] = this.timeFormat.format(riqiDate);
        } 
        if (!riqiStr.contains("," + result[i][2] + ","))
          riqiStr = String.valueOf(riqiStr) + result[i][2] + ","; 
      } 
      inserInfoAllUser(riqiStr.substring(1, riqiStr.length() - 1));
      try {
        base.begin();
        for (i = 0; i < result.length; i++) {
          String[] infoStr = result[i];
          String bianhao = infoStr[0];
          String riqi = infoStr[2];
          long[] isOut = (long[])null;
          String[] insertSql = (String[])null;
          if (!bianhao.equals(com)) {
            com = bianhao;
            empId = getEmpIdByNum(bianhao);
            dutySet = util.getDutySet(empId, "0");
            paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi));
            isOut = isOut(riqi, empId);
            insertSql = insertInfo(empId, riqi, paiBan, isOut);
          } else if (!dateString.equals(riqi)) {
            paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi));
            isOut = isOut(riqi, empId);
            insertSql = insertInfo(empId, riqi, paiBan, isOut);
          } else {
            insertSql = insertInfo(empId, riqi, paiBan, isOut);
          } 
          dateString = riqi;
          for (int j = 0; j < insertSql.length; j++) {
            if (!"".equals(insertSql[j]))
              base.addBatch(insertSql[j]); 
          } 
          String shijian = infoStr[3];
          if (shijian != null && !"null".equals(shijian) && !"".equals(shijian)) {
            if (shijian.length() > 10)
              shijian = this.timeFormat.format(this.dateFormat.parse(shijian)); 
            String[] str = { riqi, shijian, bianhao, empId };
            daKaList.add(str);
            list.add(getKqType(riqi, empId, shijian, paiBan));
          } 
        } 
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        base.end();
      } 
    } else {
      String sql = sqlInfo[0];
      String dataB = sqlInfo[1];
      inserInfoAllUser(flag);
      int jsoaUse = SystemCommon.getJsoaUse();
      try {
        ResultSet rs = null;
        if ("system".equals(dataB) || "".equals(dataB)) {
          conn = base.getDataSource().getConnection();
        } else {
          conn = base.getDataSource(dataB).getConnection();
        } 
        if (jsoaUse == 0) {
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          String[] paiBan = (String[])null;
          PaiBanUtil pUtil = new PaiBanUtil();
          KqImportUtil util = new KqImportUtil();
          while (rs.next()) {
            if (rs.getString(3) != null && !"null".equals(rs.getString(3)) && !"".equals(rs.getString(3))) {
              String bianhao = rs.getString(1);
              String riqi = rs.getString(2);
              if (!bianhao.equals(com)) {
                com = bianhao;
                empId = getEmpIdByNum(bianhao);
                dutySet = util.getDutySet(empId, "0");
                paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi));
              } else if (!dateString.equals(riqi)) {
                paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi));
              } 
              dateString = riqi;
              String shijian = rs.getString(3);
              if (shijian.length() > 10)
                shijian = this.timeFormat.format(this.dateFormat.parse(shijian)); 
              String[] str = { riqi, shijian, bianhao, empId };
              daKaList.add(str);
              list.add(getKqType(riqi, empId, shijian, paiBan));
            } 
          } 
          rs.close();
          stmt.close();
          conn.close();
        } else {
          stmt = conn.createStatement();
          rs = stmt.executeQuery(sql);
          String[] paiBan = (String[])null;
          PaiBanUtil pUtil = new PaiBanUtil();
          KqImportUtil util = new KqImportUtil();
          while (rs.next()) {
            String bianhao = rs.getString(1);
            String riqi = rs.getString(2);
            if (!bianhao.equals(com)) {
              com = bianhao;
              empId = bianhao;
              dutySet = util.getDutySet(empId, "0");
              paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi));
            } else if (!dateString.equals(riqi)) {
              paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi));
            } 
            dateString = riqi;
            String shijian1 = rs.getString(3);
            String shijian2 = rs.getString(4);
            if (shijian1.length() > 10)
              shijian1 = this.timeFormat.format(this.dateFormat.parse(shijian1)); 
            if (shijian2.length() > 10)
              shijian2 = this.timeFormat.format(this.dateFormat.parse(shijian2)); 
            if (!"".equals(shijian1)) {
              String[] str1 = { riqi, shijian1, bianhao, empId };
              daKaList.add(str1);
            } 
            if (!shijian2.equals("")) {
              String[] str2 = { riqi, shijian2, bianhao, empId };
              daKaList.add(str2);
            } 
            list.add(getKqType(riqi, empId, shijian1, paiBan));
            if (!"".equals(shijian2))
              list.add(getKqType(riqi, empId, shijian2, paiBan)); 
          } 
          rs.close();
          stmt.close();
          conn.close();
        } 
      } catch (Exception e) {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
        e.printStackTrace();
      } 
    } 
    returnValue[0] = daKaList.size();
    inputPunch(daKaList);
    try {
      conn = base.getDataSource().getConnection();
      String pSql = "update kq_info set kq_infoTime=?,kq_infoType=?,kq_infoMinute=? where kq_infoEmpId=? and kq_infoDate=? and kq_infoNum=? and kq_infoType<>7";
      PreparedStatement pstmt = conn.prepareStatement(pSql);
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        pstmt.setString(1, obj[2].toString());
        pstmt.setInt(2, ((Integer)obj[3]).intValue());
        pstmt.setInt(3, ((Integer)obj[4]).intValue());
        pstmt.setString(4, obj[0].toString());
        pstmt.setString(5, obj[1].toString());
        pstmt.setInt(6, ((Integer)obj[5]).intValue());
        pstmt.addBatch();
      } 
      pstmt.executeBatch();
      pstmt.close();
      Map<String, String> ifUpdate = new HashMap<String, String>();
      String updateSql = "update kq_info set kq_infoType=? where kq_infoType=8 and kq_infoEmpId=? and kq_infoDate=? and  (kq_infoNum=? or kq_infoNum=? or kq_infoNum=?)";
      pstmt = conn.prepareStatement(updateSql);
      for (int j = 0; j < list.size(); j++) {
        Object[] obj = list.get(j);
        if (ifUpdate.get(obj[0] + "_" + obj[1]) == null) {
          pstmt.setInt(1, 5);
          pstmt.setString(2, obj[0].toString());
          pstmt.setString(3, obj[1].toString());
          pstmt.setInt(4, 1);
          pstmt.setInt(5, 3);
          pstmt.setInt(6, 5);
          pstmt.addBatch();
          pstmt.setInt(1, 4);
          pstmt.setString(2, obj[0].toString());
          pstmt.setString(3, obj[1].toString());
          pstmt.setInt(4, 2);
          pstmt.setInt(5, 4);
          pstmt.setInt(6, 6);
          pstmt.addBatch();
          ifUpdate.put(obj[0] + "_" + obj[1], "");
        } 
      } 
      pstmt.executeBatch();
      pstmt.close();
      conn.close();
      returnValue[1] = "true";
    } catch (SQLException e) {
      if (conn != null)
        conn.close(); 
      returnValue[1] = "false";
      e.printStackTrace();
    } 
    return returnValue;
  }
  
  public String getEmpIdByNum(String empNumber) {
    DataSourceBase base = new DataSourceBase();
    String empId = "";
    String sql = "SELECT emp_id FROM org_employee WHERE empnumber='" + empNumber + "' ";
    try {
      base.begin();
      ResultSet rs = null;
      rs = base.executeQuery(sql);
      if (rs.next()) {
        empId = rs.getString(1);
      } else {
        System.out.println("请在OA人事管理中设置对应的工号！查询的工号为：" + empNumber);
      } 
      rs.close();
      base.end();
    } catch (Exception e) {
      base.end();
      System.out.println("没有找到工号：" + empNumber + "对应的用户");
    } 
    return empId;
  }
  
  public int getSignTime() {
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    int gignTime = 0;
    String sql = "SELECT OFFSET FROM kq_signtime WHERE domain_id=0";
    try {
      base.begin();
      rs = base.executeQuery(sql);
      if (rs.next())
        gignTime = rs.getInt(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return gignTime;
  }
  
  public Object[] getKqType(String riqi, String empId, String shijian, String[] dutySet) {
    boolean flag = false;
    Object[] obj = new Object[6];
    obj[0] = empId;
    obj[1] = riqi;
    obj[2] = shijian;
    obj[3] = Integer.valueOf(4);
    obj[4] = Integer.valueOf(0);
    obj[5] = Integer.valueOf(0);
    int shijianTime = getTimeInt(shijian);
    String[] minNum = dutySet[9].split(",");
    String[] minNumDown = dutySet[10].split(",");
    String[] ifPunch = dutySet[8].split(",");
    for (int i = 0; i < 6; i++) {
      if (!"0".equals(dutySet[i]) && "1".equals(ifPunch[i])) {
        int time = getTimeInt(dutySet[i]);
        if (i % 2 == 0) {
          if (shijianTime >= time - Integer.valueOf(minNum[i]).intValue() && shijianTime <= time + Integer.valueOf(minNumDown[i]).intValue()) {
            if (shijianTime >= time - Integer.valueOf(minNum[i]).intValue() && shijianTime <= time) {
              obj[3] = Integer.valueOf(0);
              obj[4] = Integer.valueOf(0);
            } else if (shijianTime > time && shijianTime <= time + Integer.valueOf(minNumDown[i]).intValue()) {
              obj[3] = Integer.valueOf(2);
              obj[4] = Integer.valueOf(shijianTime - time);
            } 
            flag = true;
          } 
        } else if (shijianTime >= time - Integer.valueOf(minNumDown[i]).intValue() && shijianTime <= time + Integer.valueOf(minNum[i]).intValue()) {
          if (shijianTime >= time - Integer.valueOf(minNumDown[i]).intValue() && shijianTime < time) {
            obj[3] = Integer.valueOf(3);
            obj[4] = Integer.valueOf(time - shijianTime);
          } else if (shijianTime >= time && shijianTime <= time + Integer.valueOf(minNum[i]).intValue()) {
            obj[3] = Integer.valueOf(1);
            obj[4] = Integer.valueOf(0);
          } 
          flag = true;
        } 
        if (flag) {
          obj[5] = Integer.valueOf(i + 1);
          break;
        } 
      } 
    } 
    return obj;
  }
  
  public int getTimeInt(String timeHm) {
    timeHm = "".equals(timeHm) ? "0:0" : timeHm;
    String[] time = timeHm.split(":");
    int returnValue = Integer.valueOf(time[0].trim()).intValue() * 60 + Integer.valueOf(time[1].trim()).intValue();
    return returnValue;
  }
  
  public String[] insertInfo(String empId, String date, String[] dutySet, long[] isOut) {
    String[] insertSql = { "", "", "", "", "", "" };
    try {
      String[] ifPunch = dutySet[8].split(",");
      char[] dutyWeek = dutySet[6].toCharArray();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(this.riqiFormat.parse(date));
      if (dutySet != null && "1".equals((new StringBuilder(String.valueOf(dutyWeek[calendar.get(7) - 1]))).toString()))
        for (int i = 0; i < 6; i++) {
          String sql = "";
          if (this.databaseType.indexOf("mysql") >= 0)
            sql = "insert into kq_info (kq_infoEmpId,kq_infoDate,kq_infoMinute,kq_infoIf,kq_infoType,kq_infoNum,kq_infoTime) values ('" + 
              empId + "','" + date + "',0,1,"; 
          if (this.databaseType.indexOf("oracle") >= 0)
            sql = "insert into kq_info (kq_infoId,kq_infoEmpId,kq_infoDate,kq_infoMinute,kq_infoIf,kq_infoType,kq_infoNum,kq_infoTime) values (hibernate_sequence.nextval,'" + 
              empId + "','" + date + "',0,1,"; 
          if (!"0".equals(dutySet[i]) && "1".equals(ifPunch[i])) {
            if (isOut != null) {
              long punchLong = this.dateFormat.parse(String.valueOf(date) + " " + dutySet[i] + ":00").getTime();
              for (int j = 0; j < isOut.length; j += 2) {
                if (punchLong >= isOut[j] && punchLong <= isOut[j + 1]) {
                  sql = String.valueOf(sql) + "7,";
                  break;
                } 
              } 
              if (!sql.endsWith("7,"))
                sql = String.valueOf(sql) + "8,"; 
            } else {
              sql = String.valueOf(sql) + "8,";
            } 
            sql = String.valueOf(sql) + (i + 1) + ",'" + dutySet[i] + "')";
            insertSql[i] = sql;
          } 
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return insertSql;
  }
  
  public void inputPunch(List<String[]> list) {
    DataSourceBase base = new DataSourceBase();
    Connection conn = null;
    try {
      conn = base.getDataSource().getConnection();
      String pSql = "";
      if (this.databaseType.indexOf("mysql") >= 0) {
        pSql = "insert into kq_punch (punch_Date,punch_Time,punch_emp,punch_OAId) values (?,?,?,?)";
      } else {
        pSql = "insert into kq_punch (punch_id,punch_Date,punch_Time,punch_emp,punch_OAId) values (hibernate_sequence.nextval,?,?,?,?)";
      } 
      PreparedStatement pstmt = conn.prepareStatement(pSql);
      for (int i = 0; i < list.size(); i++) {
        String[] str = list.get(i);
        pstmt.setString(1, str[0]);
        pstmt.setString(2, str[1]);
        pstmt.setString(3, str[2]);
        pstmt.setString(4, str[3]);
        pstmt.addBatch();
      } 
      pstmt.executeBatch();
      pstmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
  }
  
  public void inserInfoAllUser(String flag) {
    long beginLong = (new Date()).getTime();
    String[] riqi = (String[])null;
    if ("".equals(flag)) {
      riqi = new String[] { this.riqiFormat.format(new Date((new Date()).getTime() - 86400000L)) };
    } else if (!"all".equals(flag)) {
      if ("m".equals(flag)) {
        String mStr = "";
        String mBegin = (new SimpleDateFormat("yyyy-MM")).format(new Date());
        Date nowDate = new Date((new Date()).getTime() - 86400000L);
        while (this.riqiFormat.format(nowDate).startsWith(mBegin)) {
          mStr = String.valueOf(this.riqiFormat.format(nowDate)) + "," + mStr;
          nowDate = new Date(nowDate.getTime() - 86400000L);
        } 
        riqi = mStr.split(",");
      } else if (flag.indexOf(",") > 0) {
        riqi = flag.split(",");
      } else if (flag.length() > 9) {
        riqi = new String[] { flag };
      } else {
        try {
          String mStr = "";
          String mBegin = flag;
          Date nowDate = this.riqiFormat.parse(String.valueOf(flag) + "-01");
          while (this.riqiFormat.format(nowDate).startsWith(mBegin)) {
            mStr = String.valueOf(this.riqiFormat.format(nowDate)) + "," + mStr;
            nowDate = new Date(nowDate.getTime() + 86400000L);
          } 
          riqi = mStr.split(",");
        } catch (Exception e) {
          e.printStackTrace();
        } 
      } 
    } 
    DataSourceBase base = new DataSourceBase();
    PaiBanUtil pUtil = new PaiBanUtil();
    String[][] dutySet = (String[][])null;
    String[] paiBan = (String[])null;
    KqImportUtil util = new KqImportUtil();
    long[] isOut = (long[])null;
    try {
      String[] empIds = (String[])null;
      String sql = "SELECT emp_id FROM org_employee WHERE userisdeleted=0 AND userisactive=1 AND emp_id <>-99 AND emp_id <>0 ORDER BY emp_id";
      base.begin();
      ResultSet rs = base.executeQuery(sql);
      String empIdStr = "";
      while (rs.next())
        empIdStr = String.valueOf(empIdStr) + rs.getString(1) + ","; 
      empIds = empIdStr.split(",");
      for (int j = 0; j < empIds.length; j++) {
        dutySet = util.getDutySet(empIds[j], "0");
        for (int i = 0; i < riqi.length; i++) {
          paiBan = pUtil.getPaiBan(dutySet, this.riqiFormat.parse(riqi[i]));
          isOut = isOut(riqi[i], empIds[j]);
          String[] insertSql = insertInfo(empIds[j], riqi[i], paiBan, isOut);
          for (int k = 0; k < insertSql.length; k++) {
            if (!"".equals(insertSql[k]))
              base.addBatch(insertSql[k]); 
          } 
        } 
        base.executeBatch();
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    System.out.println("插入数据耗时：" + (((new Date()).getTime() - beginLong) / 1000L) + "s");
  }
  
  public long[] isOut(String dateStr, String empId) {
    String sql = "";
    long[] outDateTime = (long[])null;
    String dateTimeStr = "";
    String databaseType = SystemCommon.getDatabaseType();
    if (databaseType.indexOf("mysql") >= 0) {
      sql = String.valueOf(sql) + "SELECT qingjiaStart timebegint,qingjiaEnd timeend FROM kq_qingjia WHERE (('" + dateStr + "' BETWEEN qingjiaStart AND qingjiaEnd ) " + 
        "OR (qingjiaStart LIKE '%" + dateStr + "%') OR (qingjiaEnd LIKE '%" + dateStr + "%')) AND qingjiaEmp='" + empId + "'";
      sql = String.valueOf(sql) + " UNION SELECT waichuStart timebegint,waichuEnd timeend FROM kq_waichu WHERE (('" + dateStr + "' BETWEEN waichuStart AND waichuEnd ) " + 
        "OR (waichuStart LIKE '%" + dateStr + "%') OR (waichuEnd LIKE '%" + dateStr + "%')) AND waichuEmp='" + empId + "'";
      sql = String.valueOf(sql) + " UNION SELECT chuchaiStart timebegint,chuchaiEnd timeend FROM kq_chuchai WHERE (('" + dateStr + "' BETWEEN chuchaiStart AND chuchaiEnd ) " + 
        "OR (chuchaiStart LIKE '%" + dateStr + "%') OR (chuchaiEnd LIKE '%" + dateStr + "%')) AND chuchaiEmp='" + empId + "'";
    } else {
      sql = String.valueOf(sql) + "SELECT qingjiaStart timebegint,qingjiaEnd timeend FROM kq_qingjia WHERE (('" + dateStr + "' BETWEEN to_char(qingjiaStart,'yyyy-mm-dd hh:mm:ss') " + 
        "AND to_char(qingjiaEnd,'yyyy-mm-dd hh:mm:ss') ) OR (to_char(qingjiaStart,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%') " + 
        "OR (to_char(qingjiaEnd,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%')) AND qingjiaEmp='" + empId + "'";
      sql = String.valueOf(sql) + " UNION SELECT waichuStart timebegint,waichuEnd timeend FROM kq_waichu WHERE (('" + dateStr + "' BETWEEN to_char(waichuStart,'yyyy-mm-dd hh:mm:ss') " + 
        "AND to_char(waichuEnd,'yyyy-mm-dd hh:mm:ss') ) OR (to_char(waichuStart,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%') " + 
        "OR (to_char(waichuEnd,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%')) AND waichuEmp='" + empId + "'";
      sql = String.valueOf(sql) + " UNION SELECT chuchaiStart timebegint,chuchaiEnd timeend FROM kq_chuchai WHERE ('" + dateStr + "' BETWEEN to_char(chuchaiStart,'yyyy-mm-dd hh:mm:ss') " + 
        "AND to_char(chuchaiEnd,'yyyy-mm-dd hh:mm:ss') ) OR ((to_char(chuchaiStart,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%') " + 
        "OR (to_char(chuchaiEnd,'yyyy-mm-dd hh:mm:ss') LIKE '%" + dateStr + "%')) AND chuchaiEmp='" + empId + "'";
    } 
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    try {
      base.begin();
      rs = base.executeQuery(sql);
      while (rs.next())
        dateTimeStr = String.valueOf(dateTimeStr) + rs.getString(1) + "~" + rs.getString(2) + ";"; 
      rs.close();
      base.end();
      if (!"".equals(dateTimeStr)) {
        String[] dateTimes = dateTimeStr.split(";");
        outDateTime = new long[dateTimes.length * 2];
        for (int i = 0; i < dateTimes.length; i++) {
          outDateTime[i * 2] = this.dateFormat.parse(dateTimes[i].split("~")[0]).getTime();
          outDateTime[i * 2 + 1] = this.dateFormat.parse(dateTimes[i].split("~")[1]).getTime();
        } 
      } 
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return outDateTime;
  }
}
