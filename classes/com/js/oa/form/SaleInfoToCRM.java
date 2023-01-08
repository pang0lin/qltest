package com.js.oa.form;

import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

public class SaleInfoToCRM extends Workflow {
  public String updateDataToCRM(HttpServletRequest request) {
    String flag = request.getParameter("flag");
    if ("update".equals(flag))
      if ("0".equals(request.getParameter("jst_3260_f4935"))) {
        dataToCRM(request, 0, 1);
      } else if ("1".equals(request.getParameter("jst_3260_f4935"))) {
        dataToCRM(request, 1, 1);
      } else {
        dataToCRM(request, 0, 1);
        System.out.println("不写入到CRM中！");
      }  
    return update(request);
  }
  
  public void completeDataToCRM(HttpServletRequest request) {
    if ("0".equals(request.getParameter("jst_3260_f4935"))) {
      dataToCRM(request, 0, 0);
    } else if ("1".equals(request.getParameter("jst_3260_f4935"))) {
      dataToCRM(request, 1, 0);
    } else {
      System.out.println("不写入到CRM中！");
    } 
    complete(request);
  }
  
  public boolean dataToCRM(HttpServletRequest request, int crmType, int updateFlag) {
    String databaseType = SystemCommon.getDatabaseType();
    String info_Id = request.getParameter("Info_Id");
    DataSourceBase base = new DataSourceBase();
    ResultSet rs = null;
    boolean res = false;
    boolean flag = false;
    String countSql = "";
    if (crmType == 0) {
      countSql = "SELECT COUNT(*) FROM jst_3282 WHERE jst_3282_f4937='" + info_Id + "'";
    } else {
      countSql = "SELECT COUNT(*) FROM jst_3294 WHERE jst_3294_f4938='" + info_Id + "'";
    } 
    try {
      base.begin();
      rs = base.executeQuery(countSql);
      if (rs.next() && 
        rs.getInt(1) > 0)
        flag = true; 
      rs.close();
      base.end();
    } catch (Exception e1) {
      base.end();
      e1.printStackTrace();
    } 
    if (flag);
    if (!flag) {
      String hide_Field = "";
      String curUserId = request.getSession(true).getAttribute("userId").toString();
      String curUserName = request.getSession(true).getAttribute("userName").toString();
      String curOrgId = request.getSession(true).getAttribute("orgId").toString();
      curUserName = String.valueOf(curUserName) + ";" + curUserId;
      String fzrId = request.getParameter("jst_3260_f6457_Id");
      String fzrName = request.getParameter("jst_3260_f6457_Name");
      if (fzrId != null && !"".equals(fzrId) && !"null".equals(fzrId)) {
        curUserId = fzrId;
        curUserName = String.valueOf(fzrName) + ";" + fzrId;
        try {
          base.begin();
          rs = base.executeQuery("select org_id from org_organization_user where emp_id=" + fzrId);
          if (rs.next())
            curOrgId = rs.getString(1); 
          rs.close();
          base.end();
        } catch (Exception ex) {
          base.end();
          ex.printStackTrace();
        } 
      } 
      String custName = "";
      String custOASum = "";
      String custType = "";
      String custReqType = "";
      String custSupper = "";
      String linkManName = "";
      String linkManTitle = "";
      String linkManDepart = "";
      String linkManPhone = "";
      String linkManMobile = "";
      String linkManQQ = "";
      String infoDetail = "";
      String linkManMemo = "";
      String custArea = "";
      String pcNum = "";
      String webCond = "";
      String dataBase = "";
      String appSystem = "";
      String appSituation = "";
      String supplier = "";
      String ITNum = "";
      String techLevel = "";
      String oaExperience = "";
      String webSite = "";
      String linkEmail = "";
      String linkMsn = "";
      String custAddress = "";
      String custMemo = "";
      String custRelation = "";
      String infoPostDate = "";
      if (request.getParameter("jst_3260_f5569") != null) {
        infoPostDate = request.getParameter("jst_3260_f5569");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f5569,";
      } 
      if (request.getParameter("jst_3260_f4074") != null) {
        custName = request.getParameter("jst_3260_f4074");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4074,";
      } 
      if (request.getParameter("jst_3260_f4077") != null) {
        custReqType = request.getParameter("jst_3260_f4077");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4077,";
      } 
      if (request.getParameter("jst_3260_f4078") != null) {
        custType = request.getParameter("jst_3260_f4078");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4078,";
      } 
      if (request.getParameter("jst_3260_f4079") != null) {
        custSupper = request.getParameter("jst_3260_f4079");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4079,";
      } 
      if (request.getParameter("jst_3260_f4080") != null) {
        custOASum = request.getParameter("jst_3260_f4080");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4080,";
      } 
      if (request.getParameter("jst_3260_f4559") != null) {
        custMemo = request.getParameter("jst_3260_f4559");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4559,";
      } 
      if (request.getParameter("jst_3260_f4081") != null) {
        linkManName = request.getParameter("jst_3260_f4081");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4081,";
      } 
      if (request.getParameter("jst_3260_f4082") != null) {
        linkManTitle = request.getParameter("jst_3260_f4082");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4082,";
      } 
      if (request.getParameter("jst_3260_f4083") != null) {
        linkManDepart = request.getParameter("jst_3260_f4083");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4083,";
      } 
      if (request.getParameter("jst_3260_f4084") != null) {
        linkManPhone = request.getParameter("jst_3260_f4084");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4084,";
      } 
      if (request.getParameter("jst_3260_f4085") != null) {
        linkManMobile = request.getParameter("jst_3260_f4085");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4085,";
      } 
      if (request.getParameter("jst_3260_f4086") != null) {
        linkManQQ = request.getParameter("jst_3260_f4086");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4086,";
      } 
      if (request.getParameter("jst_3260_f4123") != null) {
        linkManMemo = request.getParameter("jst_3260_f4123");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4123,";
      } 
      if (request.getParameter("jst_3260_f4560") != null) {
        custArea = request.getParameter("jst_3260_f4560");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4560,";
      } 
      if (request.getParameter("jst_3260_f4121") != null) {
        infoDetail = request.getParameter("jst_3260_f4121");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4121,";
      } 
      if (request.getParameter("jst_3260_f4103") != null) {
        pcNum = request.getParameter("jst_3260_f4103");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4103,";
      } 
      if (request.getParameter("jst_3260_f4104") != null) {
        webCond = request.getParameter("jst_3260_f4104");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4104,";
      } 
      if (request.getParameter("jst_3260_f4105") != null) {
        dataBase = request.getParameter("jst_3260_f4105");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4105,";
      } 
      if (request.getParameter("jst_3260_f4106") != null) {
        appSystem = request.getParameter("jst_3260_f4106");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4106,";
      } 
      if (request.getParameter("jst_3260_f4107") != null) {
        appSituation = request.getParameter("jst_3260_f4107");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4107,";
      } 
      if (request.getParameter("jst_3260_f4108") != null) {
        supplier = request.getParameter("jst_3260_f4108");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4108,";
      } 
      if (request.getParameter("jst_3260_f4109") != null) {
        ITNum = request.getParameter("jst_3260_f4109");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4109,";
      } 
      if (request.getParameter("jst_3260_f4110") != null) {
        techLevel = request.getParameter("jst_3260_f4110");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4110,";
      } 
      if (request.getParameter("jst_3260_f4111") != null) {
        oaExperience = request.getParameter("jst_3260_f4111");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4111,";
      } 
      if (request.getParameter("jst_3260_f4932") != null) {
        webSite = request.getParameter("jst_3260_f4932");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4932,";
      } 
      if (request.getParameter("jst_3260_f4933") != null) {
        linkEmail = request.getParameter("jst_3260_f4933");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4933,";
      } 
      if (request.getParameter("jst_3260_f4934") != null) {
        linkMsn = request.getParameter("jst_3260_f4934");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4934,";
      } 
      if (request.getParameter("jst_3260_f4936") != null) {
        custAddress = request.getParameter("jst_3260_f4936");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4936,";
      } 
      if (request.getParameter("jst_3260_f4096") != null) {
        custRelation = request.getParameter("jst_3260_f4096");
      } else {
        hide_Field = String.valueOf(hide_Field) + "jst_3260_f4096,";
      } 
      if (hide_Field.length() > 0) {
        String infoSql = "select " + hide_Field.substring(0, hide_Field.length() - 1) + " from jst_3260 where jst_3260_id=" + info_Id;
        try {
          base.begin();
          rs = base.executeQuery(infoSql);
          if (rs.next()) {
            if (hide_Field.contains("jst_3260_f5569,"))
              infoPostDate = rs.getString("jst_3260_f5569"); 
            if (hide_Field.contains("jst_3260_f4074,"))
              custName = rs.getString("jst_3260_f4074"); 
            if (hide_Field.contains("jst_3260_f4077,"))
              custReqType = rs.getString("jst_3260_f4077"); 
            if (hide_Field.contains("jst_3260_f4078,"))
              custType = rs.getString("jst_3260_f4078"); 
            if (hide_Field.contains("jst_3260_f4079,"))
              custSupper = rs.getString("jst_3260_f4079"); 
            if (hide_Field.contains("jst_3260_f4080,"))
              custOASum = rs.getString("jst_3260_f4080"); 
            if (hide_Field.contains("jst_3260_f4559,"))
              custMemo = rs.getString("jst_3260_f4559"); 
            if (hide_Field.contains("jst_3260_f4081,"))
              linkManName = rs.getString("jst_3260_f4081"); 
            if (hide_Field.contains("jst_3260_f4082,"))
              linkManTitle = rs.getString("jst_3260_f4082"); 
            if (hide_Field.contains("jst_3260_f4083,"))
              linkManDepart = rs.getString("jst_3260_f4083"); 
            if (hide_Field.contains("jst_3260_f4084,"))
              linkManPhone = rs.getString("jst_3260_f4084"); 
            if (hide_Field.contains("jst_3260_f4085,"))
              linkManMobile = rs.getString("jst_3260_f4085"); 
            if (hide_Field.contains("jst_3260_f4086,"))
              linkManQQ = rs.getString("jst_3260_f4086"); 
            if (hide_Field.contains("jst_3260_f4123,"))
              linkManMemo = rs.getString("jst_3260_f4123"); 
            if (hide_Field.contains("jst_3260_f4560,"))
              custArea = rs.getString("jst_3260_f4560"); 
            if (hide_Field.contains("jst_3260_f4121,"))
              infoDetail = rs.getString("jst_3260_f4121"); 
            if (hide_Field.contains("jst_3260_f4103,"))
              pcNum = rs.getString("jst_3260_f4103"); 
            if (hide_Field.contains("jst_3260_f4104,"))
              webCond = rs.getString("jst_3260_f4104"); 
            if (hide_Field.contains("jst_3260_f4105,"))
              dataBase = rs.getString("jst_3260_f4105"); 
            if (hide_Field.contains("jst_3260_f4106,"))
              appSystem = rs.getString("jst_3260_f4106"); 
            if (hide_Field.contains("jst_3260_f4107,"))
              appSituation = rs.getString("jst_3260_f4107"); 
            if (hide_Field.contains("jst_3260_f4108,"))
              supplier = rs.getString("jst_3260_f4108"); 
            if (hide_Field.contains("jst_3260_f4109,"))
              ITNum = rs.getString("jst_3260_f4109"); 
            if (hide_Field.contains("jst_3260_f4110,"))
              techLevel = rs.getString("jst_3260_f4110"); 
            if (hide_Field.contains("jst_3260_f4111,"))
              oaExperience = rs.getString("jst_3260_f4111"); 
            if (hide_Field.contains("jst_3260_f4932,"))
              webSite = rs.getString("jst_3260_f4932"); 
            if (hide_Field.contains("jst_3260_f4933,"))
              linkEmail = rs.getString("jst_3260_f4933"); 
            if (hide_Field.contains("jst_3260_f4934,"))
              linkMsn = rs.getString("jst_3260_f4934"); 
            if (hide_Field.contains("jst_3260_f4936,"))
              custAddress = rs.getString("jst_3260_f4936"); 
            if (hide_Field.contains("jst_3260_f4096,"))
              custRelation = rs.getString("jst_3260_f4096"); 
          } 
          base.end();
        } catch (Exception e) {
          base.end();
          e.printStackTrace();
        } 
      } 
      try {
        Integer.valueOf(ITNum);
      } catch (Exception e) {
        ITNum = "0";
        System.out.println("IT人员数量未填写！");
      } 
      Connection conn = null;
      try {
        DataSourceBase dbase = new DataSourceBase();
        dbase.begin();
        String id = dbase.getTableId();
        dbase.end();
        StringBuffer sql = new StringBuffer(1024);
        sql.append("insert into ");
        if (crmType == 0) {
          sql.append("jst_3282 (");
          sql.append("jst_3282_id,jst_3282_owner,jst_3282_org,jst_3282_group,");
          sql.append("jst_3282_f4339,jst_3282_f4340,jst_3282_f4342,jst_3282_f4343,jst_3282_f4450,jst_3282_f4352,");
          sql.append("jst_3282_f4365,jst_3282_f4353,jst_3282_f4355,jst_3282_f4354,jst_3282_f4356,jst_3282_f4541,");
          sql.append("jst_3282_f4903,jst_3282_f4341,jst_3282_f4451,jst_3282_f4452,jst_3282_f4453,jst_3282_f4454,");
          sql.append("jst_3282_f4455,jst_3282_f4456,jst_3282_f4457,jst_3282_f4458,jst_3282_f4459,jst_3282_f4536,");
          sql.append("jst_3282_f4357,jst_3282_f4525,jst_3282_f4902,jst_3282_f4937,jst_3282_f4370,jst_3282_f4338,");
          sql.append("jst_3282_f4337,jst_3282_f4371,jst_3282_f4369)");
          sql.append("values(");
          sql.append("?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?)");
          conn = (new DataSourceBase()).getDataSource().getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql.toString());
          pstmt.setString(1, id);
          pstmt.setString(2, curUserId);
          pstmt.setString(3, curOrgId);
          pstmt.setString(4, "null");
          pstmt.setString(5, custName);
          pstmt.setString(6, custOASum);
          pstmt.setString(7, custType);
          pstmt.setString(8, custReqType);
          pstmt.setString(9, custSupper);
          pstmt.setString(10, linkManName);
          pstmt.setString(11, linkManTitle);
          pstmt.setString(12, linkManDepart);
          pstmt.setString(13, linkManPhone);
          pstmt.setString(14, linkManMobile);
          pstmt.setString(15, linkManQQ);
          pstmt.setString(16, infoDetail);
          pstmt.setString(17, linkManMemo);
          pstmt.setString(18, custArea);
          pstmt.setString(19, pcNum);
          pstmt.setString(20, webCond);
          pstmt.setString(21, dataBase);
          pstmt.setString(22, appSystem);
          pstmt.setString(23, appSituation);
          pstmt.setString(24, supplier);
          pstmt.setString(25, ITNum);
          pstmt.setString(26, techLevel);
          pstmt.setString(27, oaExperience);
          pstmt.setString(28, webSite);
          pstmt.setString(29, linkEmail);
          pstmt.setString(30, linkMsn);
          pstmt.setString(31, custAddress);
          pstmt.setString(32, info_Id);
          pstmt.setString(33, "0");
          pstmt.setString(34, curUserName);
          pstmt.setString(35, custMemo);
          pstmt.setString(36, custRelation);
          pstmt.setString(37, infoPostDate);
          pstmt.executeUpdate();
          pstmt.close();
          Statement stmt = conn.createStatement();
          if (databaseType.indexOf("mysql") >= 0)
            stmt.executeUpdate("update jst_3282 set jst_3282_date=now() where jst_3282_id=" + id); 
          if (databaseType.indexOf("oracle") >= 0)
            stmt.executeUpdate("update jst_3282 set jst_3282_date=sysdate() where jst_3282_id=" + id); 
          stmt.close();
        } else {
          sql.append("jst_3294 (");
          sql.append("jst_3294_id,jst_3294_owner,jst_3294_org,jst_3294_group,");
          sql.append("jst_3282_f4345,jst_3282_f4352,jst_3282_f4365,jst_3282_f4353,jst_3282_f4355,jst_3282_f4354,");
          sql.append("jst_3282_f4356,jst_3282_f4341,jst_3294_f4537,jst_3282_f4357,jst_3294_f4938,jst_3294_f4554,");
          sql.append("jst_3294_f4556,jst_3294_f4555)");
          sql.append("values(");
          sql.append("?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?,?,?,?,?,");
          sql.append("?,?)");
          conn = (new DataSourceBase()).getDataSource().getConnection();
          PreparedStatement pstmt = conn.prepareStatement(sql.toString());
          pstmt.setString(1, id);
          pstmt.setString(2, curUserId);
          pstmt.setString(3, curOrgId);
          pstmt.setString(4, "null");
          pstmt.setString(5, custName);
          pstmt.setString(6, linkManName);
          pstmt.setString(7, linkManTitle);
          pstmt.setString(8, linkManDepart);
          pstmt.setString(9, linkManPhone);
          pstmt.setString(10, linkManMobile);
          pstmt.setString(11, linkManQQ);
          pstmt.setString(12, custArea);
          pstmt.setString(13, webSite);
          pstmt.setString(14, linkEmail);
          pstmt.setString(15, info_Id);
          pstmt.setString(16, "0");
          pstmt.setString(17, curUserName);
          pstmt.setString(18, infoPostDate);
          pstmt.executeUpdate();
          pstmt.close();
          Statement stmt = conn.createStatement();
          if (databaseType.indexOf("mysql") >= 0)
            stmt.executeUpdate("update jst_3294 set jst_3294_date=now() where jst_3294_id=" + id); 
          if (databaseType.indexOf("oracle") >= 0)
            stmt.executeUpdate("update jst_3294 set jst_3294_date=sysdate() where jst_3294_id=" + id); 
          stmt.close();
        } 
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception err) {
            err.printStackTrace();
          }  
        ex.printStackTrace();
      } 
    } 
    return res;
  }
}
