package com.js.oa.hr.finance.util;

import com.js.oa.userdb.util.DbOpt;
import com.js.system.vo.usermanager.EmployeeOtherInfoVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.util.Date;

public class Rytb {
  static String ENDDA = "99991231";
  
  public static String addUser(EmployeeVO employee, EmployeeOtherInfoVO empOtherInfo) {
    JCoFunction function = null;
    String result = "";
    JCoDestination destination = SAPConn.connect();
    try {
      String currentDate = getDateString(null);
      String empId = empOtherInfo.getSap_ID();
      function = destination.getRepository().getFunction(
          "Z_RECEIVE_PERSON");
      JCoTable t_ZT0000 = function.getTableParameterList().getTable("ZT0000");
      t_ZT0000.appendRow();
      t_ZT0000.setValue("PERNR", empId);
      t_ZT0000.setValue("INFTY", "0000");
      t_ZT0000.setValue("ENDDA", ENDDA);
      t_ZT0000.setValue("BEGDA", currentDate);
      t_ZT0000.setValue("MASSN", "Z1");
      t_ZT0000.setValue("MASSG", "99");
      t_ZT0000.setValue("STAT2", 3);
      t_ZT0000.setValue("STAT3", 1);
      JCoTable t_IT_PERSON_OUT = function.getTableParameterList()
        .getTable("IT_PERSON_OUT");
      JCoTable t_IT_LOG = function.getTableParameterList().getTable(
          "IT_LOG");
      function.execute(destination);
      String log = "";
      for (int i = 0; i < t_IT_LOG.getNumRows(); i++) {
        t_IT_LOG.setRow(i);
        if (i > 0)
          log = String.valueOf(log) + "\n"; 
        log = String.valueOf(log) + t_IT_LOG.getString("ZMESS");
      } 
      if (!"".equals(log))
        result = log; 
      System.out.println("新增用户返回信息为：" + log);
    } catch (JCoException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static String updateUser(EmployeeVO employee, EmployeeOtherInfoVO empOtherInfo, EmployeeVO oldEmployee, EmployeeOtherInfoVO oldEmpOtherInfo) {
    JCoFunction function = null;
    String result = "";
    JCoDestination destination = SAPConn.connect();
    try {
      String currentDate = getDateString(null);
      String empId = empOtherInfo.getSap_ID();
      function = destination.getRepository().getFunction(
          "Z_UPDATE_PERSON");
      JCoTable t_ZT0000 = function.getTableParameterList().getTable(
          "ZT0000");
      t_ZT0000.appendRow();
      t_ZT0000.setValue("MASSN", "0000");
      t_ZT0000.setValue("PERNR", empId);
      t_ZT0000.setValue("ENDDA", ENDDA);
      t_ZT0000.setValue("BEGDA", currentDate);
      JCoTable t_IT_PERSON_OUT = function.getTableParameterList()
        .getTable("IT_PERSON_OUT");
      JCoTable t_IT_LOG = function.getTableParameterList().getTable(
          "IT_LOG");
      function.execute(destination);
      String log = "";
      for (int i = 0; i < t_IT_LOG.getNumRows(); i++) {
        t_IT_LOG.setRow(i);
        if (i > 0)
          log = String.valueOf(log) + "\n"; 
        log = String.valueOf(log) + t_IT_LOG.getString("ZMESS");
      } 
      if (!"".equals(log))
        result = log; 
      System.out.println("修改用户返回信息为：" + log);
    } catch (JCoException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  public static void main(String[] args) {
    System.out.println("xxx");
    EmployeeVO employee = new EmployeeVO();
    EmployeeVO oldEmployee = new EmployeeVO();
    employee.setEmpId(0L);
    EmployeeOtherInfoVO empOtherInfo = new EmployeeOtherInfoVO();
    EmployeeOtherInfoVO oldEmpOtherInfo = new EmployeeOtherInfoVO();
    empOtherInfo.setSap_ID("20000501");
    updateUser(employee, empOtherInfo, oldEmployee, oldEmpOtherInfo);
  }
  
  public static String getDateString(Date date) {
    String dateStr = "";
    if (date == null)
      date = new Date(); 
    String year = (new StringBuilder(String.valueOf(date.getYear() + 1900))).toString();
    String month = "0" + (date.getMonth() + 1);
    if (month.length() > 2)
      month = month.substring(month.length() - 2, month.length()); 
    String day = "0" + date.getDate();
    if (day.length() > 2)
      day = day.substring(day.length() - 2, day.length()); 
    dateStr = String.valueOf(year) + month + day;
    return dateStr;
  }
  
  public static String getStr(String str, int length) {
    String result = "";
    if (str != null && !"".equals(str))
      if (str.length() > length) {
        result = str.substring(0, length);
      } else {
        result = str;
      }  
    return result;
  }
  
  public static boolean noeEqu(String str1, String str2) {
    boolean equ = false;
    if (str1 == null && str2 == null) {
      equ = true;
    } else if (str1 != null && str1.equals(str2)) {
      equ = true;
    } else {
      equ = false;
    } 
    return !equ;
  }
  
  public static String getZwbm(String zwmc) {
    String result = "";
    if (zwmc != null && !"".equals(zwmc)) {
      String sql = "select dutyno from oa_duty where dutyname='" + zwmc + 
        "'";
      DbOpt db = new DbOpt();
      try {
        result = db.executeQueryToStr(sql);
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } 
    return result;
  }
}
