package com.js.oa.hr.finance.util;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class QuerySalary {
  public static String querySalary(String userNum, String year, String month) {
    JCoFunction function = null;
    JCoDestination destination = SAPConn.connect();
    try {
      function = destination.getRepository().getFunction("ZRFC_PYXX_READ_PAYROLL_RESULT");
      function.getImportParameterList().setValue("PERNR", userNum);
      function.getImportParameterList().setValue("GJAHR", year);
      function.getImportParameterList().setValue("MONAT", month);
      function.execute(destination);
      String result_log = function.getExportParameterList().getString("RESULT_LOG");
      System.out.println("调用返回信息--->" + result_log);
      String aaa = function.getExportParameterList().toXML();
      System.out.println(aaa);
      JCoTable salaryItem = function.getTableParameterList().getTable("ZT512T");
      System.out.println(salaryItem.getNumRows());
      int i;
      for (i = 0; i < salaryItem.getNumRows(); i++) {
        salaryItem.setRow(i);
        System.out.println(String.valueOf(salaryItem.getString("LGART")) + "=" + salaryItem.getString("LGTXT"));
      } 
      JCoTable salaryValue = function.getTableParameterList().getTable("PAYROLL_RESULT_RT");
      System.out.println("+++++++++++++++++");
      for (i = 0; i < salaryValue.getNumRows(); i++) {
        salaryValue.setRow(i);
        System.out.println(String.valueOf(salaryValue.getString("LGART")) + "=" + salaryValue.getFloat("BETRG"));
      } 
    } catch (JCoException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  public static void main(String[] args) {
    System.out.println("xxx");
    querySalary("20000917", "2014", "02");
  }
}
