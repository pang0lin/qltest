package com.js.oa.jsflow.util;

import com.js.oa.eform.bean.CustomFormEJBBean;
import com.js.oa.userdb.util.DbOpt;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

public class WorkflowSYHRData {
  public void insertSYHRData(HttpServletRequest request) throws SQLException {
    HttpSession session = request.getSession(true);
    String tableId = request.getParameter("tableId");
    CustomFormEJBBean bean = new CustomFormEJBBean();
    if ("1303062".equals(tableId)) {
      String FlowID = "";
      String empNO = request.getParameter("jst_3105_f4429_temp");
      String empName = request.getParameter("jst_3105_f4430");
      String dimissionDate = request.getParameter("jst_3105_f4438");
      String infoId = request.getParameter("Info_Id");
      String Page_Id = request.getParameter("Page_Id");
      String smalldatetime = getSmalldatetime(request);
      DbOpt dbopt = null;
      String fieldValue = "";
      try {
        dbopt = new DbOpt();
        fieldValue = CustomFormEJBBean.getValue("jst_3105_f4439", infoId, Page_Id, dbopt);
        FlowID = CustomFormEJBBean.getValue("jst_3105_f4428", infoId, Page_Id, dbopt);
      } catch (Exception e) {
        dbopt.close();
        e.printStackTrace();
      } finally {
        dbopt.close();
      } 
      String dimissionType = fieldValue.replace(",", "");
      String dimissionReason = request.getParameter("jst_3105_f4440");
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      temp_str = sdf.format(dt);
      String createTime = temp_str;
      String updateTime = temp_str;
      int RWFlag = 0;
      DataSource base = (new DataSourceBase()).getDataSource("hydb");
      Statement stmt = null;
      Connection con = null;
      try {
        con = base.getConnection();
        String sql = "insert into temp_dimissionApply (ID,FlowID,empNO,empName,dimissionDate,dimissionType,dimissionReason,createTime,updateTime,RWFlag,smalldatetime)";
        sql = String.valueOf(sql) + "values(hibernate_sequence.nextval,'" + FlowID + "','" + empNO + "','" + empName + "',JSDB.FN_STRTODATE('" + dimissionDate + "',''),'" + dimissionType + "','" + dimissionReason + "',JSDB.FN_STRTODATE('" + createTime + "',''),JSDB.FN_STRTODATE('" + updateTime + "','')," + RWFlag + ",JSDB.FN_STRTODATE('" + smalldatetime + "',''))";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
      } catch (Exception e) {
        if (stmt != null)
          try {
            stmt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else if ("1304437".equals(tableId)) {
      String FlowID = "";
      String empNO = request.getParameter("jst_3106_f4470_temp");
      String empName = request.getParameter("jst_3106_f4471");
      String dimissionDate = request.getParameter("jst_3106_f4479");
      String infoId = request.getParameter("Info_Id");
      String Page_Id = request.getParameter("Page_Id");
      String smalldatetime = getSmalldatetime(request);
      DbOpt dbopt = null;
      String fieldValue = "";
      try {
        dbopt = new DbOpt();
        fieldValue = CustomFormEJBBean.getValue("jst_3106_f4480", infoId, Page_Id, dbopt);
        FlowID = CustomFormEJBBean.getValue("jst_3106_f4469", infoId, Page_Id, dbopt);
      } catch (Exception e) {
        dbopt.close();
        e.printStackTrace();
      } finally {
        dbopt.close();
      } 
      String dimissionType = fieldValue;
      String dimissionReason = request.getParameter("jst_3106_f4481");
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      temp_str = sdf.format(dt);
      String createTime = temp_str;
      String updateTime = temp_str;
      int RWFlag = 0;
      DataSource base = (new DataSourceBase()).getDataSource("hydb");
      Statement stmt = null;
      Connection con = null;
      try {
        con = base.getConnection();
        String sql = "insert into temp_dimissionApply (ID,FlowID,empNO,empName,dimissionDate,dimissionType,dimissionReason,createTime,updateTime,RWFlag,smalldatetime)";
        sql = String.valueOf(sql) + "values(hibernate_sequence.nextval,'" + FlowID + "','" + empNO + "','" + empName + "',JSDB.FN_STRTODATE('" + dimissionDate + "',''),'" + dimissionType + "','" + dimissionReason + "',JSDB.FN_STRTODATE('" + createTime + "',''),JSDB.FN_STRTODATE('" + updateTime + "','')," + RWFlag + ",JSDB.FN_STRTODATE('" + smalldatetime + "',''))";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
      } catch (Exception e) {
        if (stmt != null)
          try {
            stmt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else if ("1308232".equals(tableId) || "1308575".equals(tableId)) {
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      temp_str = sdf.format(dt);
      String FlowID = "";
      String infoId = request.getParameter("Info_Id");
      String Page_Id = request.getParameter("Page_Id");
      DbOpt dbopt = null;
      try {
        dbopt = new DbOpt();
        FlowID = CustomFormEJBBean.getValue("jst_3111_f4546", infoId, Page_Id, dbopt);
      } catch (Exception e) {
        dbopt.close();
        e.printStackTrace();
      } finally {
        dbopt.close();
      } 
      String[] empNOStr = request.getParameterValues("jst_3112_f4562_temp");
      String[] empName = request.getParameterValues("jst_3112_f4563");
      String unCheckDate = (request.getParameter("jst_3111_f4551") == null) ? "" : request.getParameter("jst_3111_f4551");
      String checkInTime1 = String.valueOf(unCheckDate) + " " + request.getParameter("jst_3111_f4552");
      String checkOutTime1 = String.valueOf(unCheckDate) + " " + request.getParameter("jst_3111_f4555");
      String checkInTime2 = String.valueOf(unCheckDate) + " " + request.getParameter("jst_3111_f4553");
      String checkOutTime2 = String.valueOf(unCheckDate) + " " + request.getParameter("jst_3111_f4556");
      String checkInTime3 = String.valueOf(unCheckDate) + " " + request.getParameter("jst_3111_f4554");
      String checkOutTime3 = String.valueOf(unCheckDate) + " " + request.getParameter("jst_3111_f4557");
      String smalldatetime = getSmalldatetime(request);
      temp_str = sdf.format(dt);
      String createTime = temp_str;
      String updateTime = temp_str;
      int RWFlag = 0;
      DataSource base = (new DataSourceBase()).getDataSource("hydb");
      Statement stmt = null;
      Connection con = null;
      try {
        con = base.getConnection();
        for (int i = 0; i < empNOStr.length; i++) {
          String sql = "insert into temp_unCheckApply (ID,FlowID,empNO,empName,unCheckDate,checkInTime1,checkOutTime1,checkInTime2,checkOutTime2,checkInTime3,checkOutTime3,createTime,updateTime,RWFlag,smalldatetime)";
          sql = String.valueOf(sql) + "values(hibernate_sequence.nextval,'" + FlowID + "','" + empNOStr[i] + "','" + empName[i] + "',JSDB.FN_STRTODATE('" + unCheckDate + "',''),JSDB.FN_STRTODATE('" + checkInTime1 + "',''),JSDB.FN_STRTODATE('" + checkOutTime1 + "',''),JSDB.FN_STRTODATE('" + checkInTime2 + "',''),JSDB.FN_STRTODATE('" + checkOutTime2 + "',''),JSDB.FN_STRTODATE('" + checkInTime3 + "',''),JSDB.FN_STRTODATE('" + checkOutTime3 + "',''),JSDB.FN_STRTODATE('" + createTime + "',''),JSDB.FN_STRTODATE('" + updateTime + "','')," + RWFlag + ",JSDB.FN_STRTODATE('" + smalldatetime + "',''))";
          stmt = con.createStatement();
          stmt.executeUpdate(sql);
        } 
        stmt.close();
        con.close();
      } catch (Exception e) {
        if (stmt != null)
          try {
            stmt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else if ("1305348".equals(tableId)) {
      String FlowID = request.getParameter("jst_3107_f4505");
      String smalldatetime = getSmalldatetime(request);
      String[] empNO = request.getParameterValues("jst_3108_f4516_temp");
      String[] empNOStr = new String[empNO.length];
      String empNomber = "";
      for (int i = 0; i < empNO.length; i++) {
        if (!"".equals(empNomber) && !empNomber.contains((new StringBuilder(String.valueOf(empNO[i]))).toString())) {
          empNOStr[i] = empNO[i];
        } else if ("".equals(empNomber)) {
          empNOStr[i] = empNO[i];
        } 
        empNomber = String.valueOf(empNomber) + empNO[i] + ",";
      } 
      String[] empNameStr = request.getParameterValues("jst_3108_f4517");
      String[] empName = new String[empNO.length];
      String empNa = "";
      for (int j = 0; j < empNameStr.length; j++) {
        if (!"".equals(empNa) && !empNa.contains((new StringBuilder(String.valueOf(empNameStr[j]))).toString())) {
          empName[j] = empNameStr[j];
        } else if ("".equals(empNa)) {
          empName[j] = empNameStr[j];
        } 
        empNa = String.valueOf(empNa) + empNameStr[j] + ",";
      } 
      String infoId = request.getParameter("Info_Id");
      String Page_Id = request.getParameter("Page_Id");
      DbOpt dbopt = null;
      String fieldValue = "";
      try {
        dbopt = new DbOpt();
        fieldValue = CustomFormEJBBean.getValue("jst_3107_f4510", infoId, Page_Id, dbopt);
        FlowID = CustomFormEJBBean.getValue("jst_3107_f4505", infoId, Page_Id, dbopt);
      } catch (Exception e) {
        dbopt.close();
        e.printStackTrace();
      } finally {
        dbopt.close();
      } 
      String type = fieldValue;
      String startTime = String.valueOf(request.getParameter("jst_3107_f4592")) + " " + (
        (request.getParameter("jst_3107_f4592hours") == null) ? "00" : request.getParameter("jst_3107_f4592hours")) + ":" + (
        (request.getParameter("jst_3107_f4592minutes") == null) ? "00" : request.getParameter("jst_3107_f4592minutes")) + ":" + (
        (request.getParameter("jst_3107_f4592second") == null) ? "00" : request.getParameter("jst_3107_f4592second"));
      String endTime = String.valueOf(request.getParameter("jst_3107_f4593")) + " " + (
        (request.getParameter("jst_3107_f4593hours") == null) ? "00" : request.getParameter("jst_3107_f4593hours")) + ":" + (
        (request.getParameter("jst_3107_f4593minutes") == null) ? "00" : request.getParameter("jst_3107_f4593minutes")) + ":" + (
        (request.getParameter("jst_3107_f4593second") == null) ? "00" : request.getParameter("jst_3107_f4593second"));
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      temp_str = sdf.format(dt);
      String createTime = temp_str;
      String updateTime = temp_str;
      int RWFlag = 0;
      DataSource base = (new DataSourceBase()).getDataSource("hydb");
      Statement stmt = null;
      Connection con = null;
      try {
        con = base.getConnection();
        for (int k = 0; k < empNOStr.length; k++) {
          if (empNOStr[k] != null && empName[k] != null) {
            String sql = "insert into temp_attendanceApply (ID,FlowID,empNO,empName,type,startTime,endTime,createTime,updateTime,RWFlag,smalldatetime)";
            sql = String.valueOf(sql) + "values(hibernate_sequence.nextval,'" + FlowID + "','" + empNOStr[k] + "','" + empName[k] + "','" + type + "',JSDB.FN_STRTODATE('" + startTime + "',''),JSDB.FN_STRTODATE('" + endTime + "',''),JSDB.FN_STRTODATE('" + createTime + "',''),JSDB.FN_STRTODATE('" + updateTime + "','')," + RWFlag + ",JSDB.FN_STRTODATE('" + smalldatetime + "',''))";
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
          } 
        } 
        stmt.close();
        con.close();
      } catch (Exception e) {
        if (stmt != null)
          try {
            stmt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else if ("1307817".equals(tableId)) {
      String FlowID = request.getParameter("jst_3110_f4523");
      String empNO = request.getParameter("jst_3110_f4524_temp");
      String empName = request.getParameter("jst_3110_f4525");
      String infoId = request.getParameter("Info_Id");
      String Page_Id = request.getParameter("Page_Id");
      String smalldatetime = getSmalldatetime(request);
      DbOpt dbopt = null;
      String fieldValue = "";
      String salaryTypeValue = "";
      String amountValue = "";
      try {
        dbopt = new DbOpt();
        fieldValue = CustomFormEJBBean.getValue("jst_3110_f4533", infoId, Page_Id, dbopt);
        salaryTypeValue = CustomFormEJBBean.getValue("jst_3110_f4534", infoId, Page_Id, dbopt);
        amountValue = CustomFormEJBBean.getValue("jst_3110_f4535", infoId, Page_Id, dbopt);
        FlowID = CustomFormEJBBean.getValue("jst_3110_f4523", infoId, Page_Id, dbopt);
      } catch (Exception e) {
        dbopt.close();
        e.printStackTrace();
      } finally {
        dbopt.close();
      } 
      String type = fieldValue;
      String applyTime = request.getParameter("jst_3110_f4536");
      String salaryType = salaryTypeValue;
      if (amountValue == null || "".equals(amountValue))
        amountValue = "0"; 
      double amount = Double.parseDouble(amountValue);
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      temp_str = sdf.format(dt);
      String createTime = temp_str;
      String updateTime = temp_str;
      int RWFlag = 0;
      DataSource base = (new DataSourceBase()).getDataSource("hydb");
      Statement stmt = null;
      Connection con = null;
      try {
        con = base.getConnection();
        String sql = "insert into temp_transferApply (ID,FlowID,empNO,empName,type,applyTime,salaryType,amount,createTime,updateTime,RWFlag,smalldatetime)";
        sql = String.valueOf(sql) + "values(hibernate_sequence.nextval,'" + FlowID + "','" + empNO + "','" + empName + "','" + type + "',JSDB.FN_STRTODATE('" + applyTime + "',''),'" + salaryType + "'," + amountValue + ",JSDB.FN_STRTODATE('" + createTime + "',''),JSDB.FN_STRTODATE('" + updateTime + "','')," + RWFlag + ",JSDB.FN_STRTODATE('" + smalldatetime + "',''))";
        stmt = con.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        con.close();
      } catch (Exception e) {
        if (stmt != null)
          try {
            stmt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } else if ("1348108".equals(tableId)) {
      String FlowID = request.getParameter("jst_3113_f4570");
      String infoId = request.getParameter("Info_Id");
      String Page_Id = request.getParameter("Page_Id");
      String smalldatetime = getSmalldatetime(request);
      DbOpt dbopt = null;
      String fieldValue = "";
      String salaryTypeValue = "";
      String amountValue = "";
      try {
        dbopt = new DbOpt();
        FlowID = CustomFormEJBBean.getValue("jst_3113_f4570", infoId, Page_Id, dbopt);
      } catch (Exception e) {
        dbopt.close();
        e.printStackTrace();
      } finally {
        dbopt.close();
      } 
      String[] empNO = request.getParameterValues("jst_3114_f4577_temp");
      String[] empNOStr = new String[empNO.length];
      String empNomber = "";
      for (int i = 0; i < empNO.length; i++) {
        if (!"".equals(empNomber) && !empNomber.contains((new StringBuilder(String.valueOf(empNO[i]))).toString())) {
          empNOStr[i] = empNO[i];
        } else if ("".equals(empNomber)) {
          empNOStr[i] = empNO[i];
        } 
        empNomber = String.valueOf(empNomber) + empNO[i] + ",";
      } 
      String[] empNameStr = request.getParameterValues("jst_3114_f4578");
      String[] empName = new String[empNO.length];
      String empNa = "";
      for (int j = 0; j < empNameStr.length; j++) {
        if (!"".equals(empNa) && !empNa.contains((new StringBuilder(String.valueOf(empNameStr[j]))).toString())) {
          empName[j] = empNameStr[j];
        } else if ("".equals(empNa)) {
          empName[j] = empNameStr[j];
        } 
        empNa = String.valueOf(empNa) + empNameStr[j] + ",";
      } 
      String oldDutyNum = request.getParameter("jst_3113_f4590");
      String newDutyNum = request.getParameter("jst_3113_f4591");
      String startTime = request.getParameter("jst_3113_f4588");
      String endTime = request.getParameter("jst_3113_f4589");
      String temp_str = "";
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      temp_str = sdf.format(dt);
      String createTime = temp_str;
      String updateTime = temp_str;
      int RWFlag = 0;
      DataSource base = (new DataSourceBase()).getDataSource("hydb");
      Statement stmt = null;
      Connection con = null;
      try {
        con = base.getConnection();
        for (int k = 0; k < empNOStr.length; k++) {
          if (empNOStr[k] != null && empName[k] != null) {
            String sql = "insert into temp_emptransferApplay (ID,FlowID,empNO,empName,oldDutyNum,newDutyNum,startTime,endTime,createTime,updateTime,RWFlag,smalldatetime)";
            sql = String.valueOf(sql) + "values(hibernate_sequence.nextval,'" + FlowID + "','" + empNOStr[k] + "','" + empName[k] + "','" + oldDutyNum + "','" + newDutyNum + "',JSDB.FN_STRTODATE('" + startTime + "',''),JSDB.FN_STRTODATE('" + endTime + "',''),JSDB.FN_STRTODATE('" + createTime + "',''),JSDB.FN_STRTODATE('" + updateTime + "','')," + RWFlag + ",JSDB.FN_STRTODATE('" + smalldatetime + "',''))";
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
          } 
        } 
        stmt.close();
        con.close();
      } catch (Exception e) {
        if (stmt != null)
          try {
            stmt.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        if (con != null)
          try {
            con.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        e.printStackTrace();
      } 
    } 
  }
  
  public String getSmalldatetime(HttpServletRequest request) {
    String dateTime = "";
    String worktable_id = request.getParameter("tableId");
    String workrecord_id = request.getParameter("recordId");
    String workprocess_Id = request.getParameter("processId");
    DbOpt db = new DbOpt();
    try {
      String sql = "SELECT WORKSUBMITTIME FROM jsf_work WHERE WORKTABLE_ID=" + worktable_id + " and WORKPROCESS_ID=" + workprocess_Id + " " + 
        "and WORKRECORD_ID=" + workrecord_id + " and WORKSTATUS=100 ";
      dateTime = db.executeQueryToStr(sql);
      db.close();
    } catch (Exception e) {
      if (db != null)
        try {
          db.close();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }  
    } 
    if (dateTime != null && !"".endsWith(dateTime)) {
      dateTime = dateTime.substring(0, dateTime.length() - 2);
    } else {
      Date dt = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String temp_str = sdf.format(dt);
      dateTime = temp_str;
    } 
    return dateTime;
  }
}
