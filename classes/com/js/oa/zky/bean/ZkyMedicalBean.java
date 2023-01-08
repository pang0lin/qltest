package com.js.oa.zky.bean;

import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZkyMedicalBean {
  public List<String[]> perInformation(String userId) {
    List<String[]> list = (List)new ArrayList<String>();
    String sql = "SELECT a.EMPNAME,b.ORGNAME,a.guid FROM org_employee a,org_organization b,org_organization_user c WHERE a.EMP_ID = c.EMP_ID AND c.ORG_ID=b.ORG_ID and a.EMP_ID=" + userId;
    String org = "", username = "", nid = "";
    DataSourceBase base = new DataSourceBase();
    Statement stat = null;
    Connection conn = null;
    ResultSet rs = null, rs2 = null;
    try {
      base.begin();
      conn = base.getDataSource("jdbc/zhky").getConnection();
      stat = conn.createStatement();
      rs = base.executeQuery(sql);
      rs2 = rs;
      if (rs.next()) {
        username = rs.getString(1);
        org = rs.getString(2);
        nid = rs.getString(3);
        if (nid != null && !"null".equals(nid) && !"".equals(nid)) {
          rs2 = stat.executeQuery("select patina from dbo.V_人员基本信息 where nid='" + nid + "'");
          if (rs2.next() && 
            rs2.getString(1).equals(username))
            nid = rs.getString(3); 
        } else {
          rs = stat.executeQuery("select count(*),max(nid) from dbo.V_人员基本信息 where patina='" + username + "'");
          if (rs.next() && 
            rs.getInt(1) == 1) {
            nid = rs.getString(2);
            if (nid != null && !"null".equalsIgnoreCase(nid) && !"".equals(nid))
              base.executeUpdate("update org_employee set guid='" + nid + "' where EMP_ID=" + userId); 
          } 
        } 
      } 
      base.end();
      list.add(new String[] { username, org, nid });
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } finally {
      try {
        conn.close();
        stat.close();
        rs.close();
        rs2.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public int medicalupdate(String empid, String num) {
    List<String> list = new ArrayList<String>();
    int nu = 0;
    String sql = "UPDATE org_employee SET guid='" + num + "' WHERE EMP_ID='" + empid + "'";
    DataSourceBase base = new DataSourceBase();
    try {
      base.begin();
      nu = base.executeUpdate(sql);
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } 
    return nu;
  }
  
  public List<String> perClass() {
    List<String> list = new ArrayList<String>();
    String sql = "select distinct patitypeid2 from dbo.V_人员基本信息";
    DataSourceBase base = new DataSourceBase();
    Statement stat = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      base.begin();
      conn = base.getDataSource("jdbc/zhky").getConnection();
      stat = conn.createStatement();
      rs = stat.executeQuery(sql);
      while (rs.next())
        list.add(rs.getString(1)); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } finally {
      try {
        conn.close();
        stat.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public Map<String, String> getSum(String medicalName, String userClass, String searchBeginDate, String searchEndDate, String checkbox) {
    Map<String, String> map = new HashMap<String, String>();
    String where = "";
    if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
      where = String.valueOf(where) + " and b.DrugName like '%" + medicalName + "%' "; 
    if (userClass != null && !"null".equals(userClass) && !"".equals(userClass))
      where = String.valueOf(where) + " and c.patitypeid2 like '%" + userClass + "%' "; 
    if (checkbox != null && !"null".equals(checkbox) && !"".equals(checkbox))
      where = String.valueOf(where) + " and a.InOutDate >='" + searchBeginDate + "' AND a.InOutDate <='" + searchEndDate + " 23:59:59'"; 
    String sql = "select c.patitypeid2,sum(b.lprice*b.quan) from dbo.V_发药主表 a left join dbo.V_发药子表 b on a.inoutid=b.inoutid left join dbo.V_人员基本信息 c on a.patiID=c.patiID";
    if (!"".equals(where))
      sql = String.valueOf(sql) + where; 
    sql = String.valueOf(sql) + " group by c.patitypeid2";
    DataSourceBase base = new DataSourceBase();
    Statement stat = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      base.begin();
      conn = base.getDataSource("jdbc/zhky").getConnection();
      stat = conn.createStatement();
      rs = stat.executeQuery(sql);
      while (rs.next())
        map.put(rs.getString(1), rs.getString(2)); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } finally {
      try {
        conn.close();
        stat.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return map;
  }
  
  public double getPerSum(String medicalName, String searchBeginDate, String searchEndDate, String checkbox, String nid) {
    String where = " where c.nid='" + nid + "'";
    double sum = 0.0D;
    if (medicalName != null && !"null".equals(medicalName) && !"".equals(medicalName))
      where = String.valueOf(where) + " and b.DrugName like '%" + medicalName + "%' "; 
    if (checkbox != null && !"null".equals(checkbox) && !"".equals(checkbox)) {
      where = String.valueOf(where) + " and a.InOutDate >='" + searchBeginDate + "' AND a.InOutDate <='" + searchEndDate + " 23:59:59'";
    } else {
      where = String.valueOf(where) + " and a.InOutDate >= DATEADD(yy, (DATEDIFF(yy,0,getdate()))-1, 0)";
    } 
    String sql = " select sum(b.quan*b.LPrice) score from dbo.V_发药主表 a left join dbo.V_发药子表 b on a.inoutid=b.inoutid left join dbo.V_人员基本信息 c on a.patiID=c.patiID left join dbo.V_药品单位 d on b.drugid=d.drugid";
    if (!"".equals(where))
      sql = String.valueOf(sql) + where; 
    DataSourceBase base = new DataSourceBase();
    Statement stat = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      base.begin();
      conn = base.getDataSource("jdbc/zhky").getConnection();
      stat = conn.createStatement();
      rs = stat.executeQuery(sql);
      while (rs.next())
        sum = rs.getDouble(1); 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } finally {
      try {
        conn.close();
        stat.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return sum;
  }
  
  public List<String[]> tongbuMedicalNum() {
    List<String[]> list = (List)new ArrayList<String>();
    String sql = "SELECT a.EMPNAME,b.ORGNAME,a.guid,a.EMP_ID,a.useraccounts FROM org_employee a,org_organization b,org_organization_user c WHERE a.EMP_ID = c.EMP_ID AND c.ORG_ID=b.ORG_ID ";
    String org = "", username = "", nid = "", empid = "";
    DataSourceBase base = new DataSourceBase();
    Statement stat = null, stat2 = null;
    Connection connLocal = null, conn = null;
    ResultSet rs = null, rs2 = null, rs3 = null;
    try {
      base.begin();
      connLocal = base.getDataSource().getConnection();
      conn = base.getDataSource("jdbc/zhky").getConnection();
      stat = connLocal.createStatement();
      rs = stat.executeQuery(sql);
      while (rs.next()) {
        username = rs.getString(1);
        org = rs.getString(2);
        nid = rs.getString(3);
        empid = rs.getString(4);
        String useraccount = rs.getString(5);
        if (nid != null && !"null".equals(nid) && !"".equals(nid)) {
          stat2 = conn.createStatement();
          rs2 = stat2.executeQuery("select patina from dbo.V_人员基本信息 where nid='" + nid + "'");
          if (rs2.next()) {
            if (rs2.getString(1).equals(username)) {
              nid = rs.getString(3);
              continue;
            } 
            list.add(new String[] { useraccount, username, org, nid });
          } 
          continue;
        } 
        stat2 = conn.createStatement();
        rs3 = stat2.executeQuery("select count(*),max(nid) from dbo.V_人员基本信息 where patina='" + username + "'");
        if (rs3.next()) {
          if (rs3.getInt(1) == 1) {
            nid = rs3.getString(2);
            if (nid != null && !"null".equalsIgnoreCase(nid) && !"".equals(nid))
              base.executeUpdate("update org_employee set guid='" + nid + "' where EMP_ID=" + empid); 
            continue;
          } 
          list.add(new String[] { useraccount, username, org, nid });
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        base.end();
        if (connLocal != null)
          connLocal.close(); 
        if (conn != null)
          conn.close(); 
        if (stat != null)
          stat.close(); 
        if (stat2 != null)
          stat2.close(); 
        if (rs != null)
          rs.close(); 
        if (rs2 != null)
          rs2.close(); 
        if (rs3 != null)
          rs3.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
  
  public List<String[]> exportList(String para, String from, String where, String exportType) {
    List<String[]> list = (List)new ArrayList<String>();
    String sql = "select " + para + " from " + from + where;
    String InOutID = "", InOutDate = "", username = "", StLine = "", DrugID = "", DrugName = "", DrugRegu = "", Quan = "", LPrice = "", score = "", drugunit = "", patitypeid2 = "", org = "", nid = "";
    DataSourceBase base = new DataSourceBase();
    Statement stat = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      base.begin();
      if ("maintain_list_export".equals(exportType)) {
        conn = base.getDataSource().getConnection();
      } else {
        conn = base.getDataSource("jdbc/zhky").getConnection();
      } 
      stat = conn.createStatement();
      rs = stat.executeQuery(sql);
      if ("perMedical_list_export".equals(exportType)) {
        while (rs.next()) {
          InOutID = rs.getString(1);
          InOutDate = rs.getString(2);
          username = rs.getString(3);
          StLine = rs.getString(4);
          DrugID = rs.getString(5);
          DrugName = rs.getString(6);
          DrugRegu = rs.getString(7);
          Quan = rs.getString(8);
          LPrice = rs.getString(9);
          score = rs.getString(10);
          list.add(new String[] { InOutID, InOutDate, username, StLine, DrugID, DrugName, DrugRegu, Quan, LPrice, score });
        } 
      } else if ("stock_list_export".equals(exportType)) {
        while (rs.next()) {
          DrugID = rs.getString(1);
          DrugName = rs.getString(2);
          DrugRegu = rs.getString(3);
          drugunit = rs.getString(4);
          Quan = rs.getString(5);
          list.add(new String[] { DrugID, DrugName, DrugRegu, drugunit, Quan });
        } 
      } else if ("payout_list_export".equals(exportType)) {
        while (rs.next()) {
          DrugID = rs.getString(1);
          DrugName = rs.getString(2);
          DrugRegu = rs.getString(3);
          drugunit = rs.getString(4);
          Quan = rs.getString(5);
          score = rs.getString(6);
          list.add(new String[] { DrugID, DrugName, DrugRegu, drugunit, Quan, score });
        } 
      } else if ("detail_list_export".equals(exportType)) {
        while (rs.next()) {
          DrugName = rs.getString(1);
          username = rs.getString(2);
          InOutDate = rs.getString(3);
          Quan = rs.getString(4);
          score = rs.getString(5);
          list.add(new String[] { DrugName, username, InOutDate, Quan, score });
        } 
      } else if ("stalical_list_export".equals(exportType)) {
        while (rs.next()) {
          DrugName = rs.getString(1);
          DrugRegu = rs.getString(2);
          patitypeid2 = rs.getString(3);
          Quan = rs.getString(4);
          score = rs.getString(5);
          list.add(new String[] { DrugName, DrugRegu, patitypeid2, Quan, score });
        } 
      } else if ("drugView_list_export".equals(exportType)) {
        while (rs.next()) {
          DrugName = rs.getString(1);
          DrugRegu = rs.getString(2);
          drugunit = rs.getString(3);
          list.add(new String[] { DrugName, DrugRegu, drugunit });
        } 
      } else if ("maintain_list_export".equals(exportType)) {
        while (rs.next()) {
          username = rs.getString(1);
          org = rs.getString(2);
          nid = rs.getString(3);
          list.add(new String[] { username, org, nid });
        } 
      } 
      base.end();
    } catch (Exception e) {
      base.end();
      e.printStackTrace();
    } finally {
      try {
        conn.close();
        stat.close();
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return list;
  }
}
