package com.js.oa.form.pengchi;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreateProcessForGdzc {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  private List<String> bhlist = new ArrayList<String>();
  
  public List<String> getZcbh() {
    List<String> zcNumList = new ArrayList<String>();
    String sql = "select F1201.FANUMB numb from PRODDTA.F1201@jdedblink  WHERE F1201.FAEQST=' ' and FADSP='0' and  FAEFTB=111181 and F1201.FANUMB NOT IN (select  FE6OA06.FANUMB from PRODDTA.FE6OA06@jdedblink)";
    DataSource ds1 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds1.getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next())
        zcNumList.add(rs.getString("numb")); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return zcNumList;
  }
  
  public List<Map<String, String>> getFieldvalueForGdzcmx(String gdzcbh) {
    Calendar cal = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(cal.get(1)))).toString();
    year = "1" + year.substring(2);
    String yearNum = (new StringBuilder(String.valueOf(cal.get(1)))).toString().substring(2);
    int month = cal.get(2) + 1;
    List<Map<String, String>> datas1 = new ArrayList<Map<String, String>>();
    String sql = "SELECT MCDL01,FADL01,CRPDTA.NTOD(FADAJ) gmrq ,FADAJ ,FLADLM FROM PRODDTA.F1201@jdedblink,PRODDTA.F0006@jdedblink,PRODDTA.F1202@jdedblink WHERE FAMCU=MCMCU and FLNUMB=FANUMB and  FANUMB=?";
    List<Map<String, String>> datas2 = new ArrayList<Map<String, String>>();
    String sql1 = "select (select F0005.DRDL01 from  PRODCTL.F0005@jdedblink  where  F0005.DRSY='12' AND F0005.DRRT='C6' AND F0005.DRKY=FAACL6) as pp ,(select DRDL01  from PRODCTL.F0005@jdedblink  where F0005.DRSY='12' AND F0005.DRRT='23' AND F0005.DRKY=FAFA23)  as ggxh from PRODDTA.F1201@jdedblink where FANUMB=?";
    StringBuffer flan = new StringBuffer();
    int i = 1;
    while (i <= month) {
      String col = (i > 9) ? (new StringBuilder(String.valueOf(i))).toString() : ("0" + i);
      flan.append("FLAN").append(col).append(",");
      i++;
    } 
    String flanStr = flan.toString();
    flanStr = flanStr.substring(0, flanStr.length() - 1);
    int flanLen = (flanStr.split(",")).length;
    List<Map<String, String>> datas3 = new ArrayList<Map<String, String>>();
    List<Map<String, String>> datas4 = new ArrayList<Map<String, String>>();
    List<Map<String, String>> datas5 = new ArrayList<Map<String, String>>();
    List<Map<String, String>> datas6 = new ArrayList<Map<String, String>>();
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, gdzcbh);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", gdzcbh);
        result.put("bm", rs.getString("MCDL01").trim());
        result.put("zcmc", rs.getString("FADL01").trim());
        result.put("gmrq", rs.getString("gmrq").substring(0, 10));
        result.put("smys", rs.getString("FLADLM").trim());
        datas1.add(result);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    Connection conn1 = null;
    PreparedStatement pstmt1 = null;
    ResultSet rs1 = null;
    try {
      conn1 = ds.getConnection();
      pstmt1 = conn1.prepareStatement(sql1);
      pstmt1.setString(1, gdzcbh);
      rs1 = pstmt1.executeQuery();
      while (rs1.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", gdzcbh);
        result.put("pp", (rs1.getString(1) == null || rs1.getString(1).trim().equals("固定资产品牌")) ? "" : rs1.getString(1).trim());
        result.put("ggxh", (rs1.getString(2) == null || rs1.getString(2).trim().equals("固定资产型号")) ? "" : rs1.getString(2).trim());
        datas2.add(result);
      } 
      rs1.close();
      pstmt1.close();
      conn1.close();
    } catch (SQLException e) {
      if (conn1 != null)
        try {
          conn1.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    String sql3 = "SELECT FLAPYC from PRODDTA.F1202@jdedblink  where FLOBJ in (1601,1701) and FLFY=? and FLNUMB=?";
    String sql4 = "select " + flanStr + " zcyz from PRODDTA.F1202@jdedblink  where FLOBJ in (1601,1701) and FLFY=? and FLNUMB=?";
    String sql5 = "SELECT FLAPYC from PRODDTA.F1202@jdedblink  where FLOBJ=1602 and FLFY=? and FLNUMB=?";
    String sql6 = "select " + flanStr + " ljzj from PRODDTA.F1202@jdedblink  where FLOBJ=1602 and  FLFY=? and FLNUMB=?";
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs2 = null;
    try {
      conn2 = ds.getConnection();
      pstmt2 = conn2.prepareStatement(sql3);
      pstmt2.setString(1, yearNum);
      pstmt2.setString(2, gdzcbh);
      rs2 = pstmt2.executeQuery();
      while (rs2.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", gdzcbh);
        result.put("zcyzaypc", String.valueOf(rs2.getFloat(1) / 100.0D));
        datas3.add(result);
      } 
      rs2.close();
      pstmt2.close();
      pstmt2 = conn2.prepareStatement(sql4);
      pstmt2.setString(1, yearNum);
      pstmt2.setString(2, gdzcbh);
      rs2 = pstmt2.executeQuery();
      while (rs2.next()) {
        Map<String, String> result = new HashMap<String, String>();
        float sumFlan = 0.0F;
        result.put("zcbh", gdzcbh);
        for (int j = 1; j <= flanLen; j++)
          sumFlan = (float)(sumFlan + rs2.getFloat(j) / 100.0D); 
        result.put("zcyzan", String.valueOf(sumFlan));
        datas4.add(result);
      } 
      rs2.close();
      pstmt2.close();
      pstmt2 = conn2.prepareStatement(sql5);
      pstmt2.setString(1, yearNum);
      pstmt2.setString(2, gdzcbh);
      rs2 = pstmt2.executeQuery();
      while (rs2.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", gdzcbh);
        result.put("ljzjaypc", String.valueOf(rs2.getFloat(1) / 100.0D));
        datas5.add(result);
      } 
      rs2.close();
      pstmt2.close();
      pstmt2 = conn2.prepareStatement(sql6);
      pstmt2.setString(1, yearNum);
      pstmt2.setString(2, gdzcbh);
      rs2 = pstmt2.executeQuery();
      while (rs2.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", gdzcbh);
        float sumFlan = 0.0F;
        result.put("zcbh", gdzcbh);
        for (int j = 1; j <= flanLen; j++)
          sumFlan = (float)(sumFlan + rs2.getFloat(j) / 100.0D); 
        result.put("ljzjan", String.valueOf(sumFlan));
        datas6.add(result);
      } 
      rs2.close();
      pstmt2.close();
      conn2.close();
    } catch (SQLException e) {
      if (conn2 != null)
        try {
          conn2.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    if (datas1.size() > 0 && datas2.size() > 0 && datas3.size() > 0)
      for (Map<String, String> d1 : datas1) {
        Map<String, String> result = new HashMap<String, String>();
        for (Map<String, String> d2 : datas2) {
          if (((String)d1.get("zcbh")).equals(d2.get("zcbh"))) {
            result.put("zcbh", d1.get("zcbh"));
            result.put("bm", d1.get("bm"));
            result.put("zcmc", d1.get("zcmc"));
            result.put("gmrq", d1.get("gmrq"));
            result.put("smys", d1.get("smys"));
            result.put("ggxh", d2.get("ggxh"));
            result.put("pp", d2.get("pp"));
          } 
        } 
        for (Map<String, String> d3 : datas3) {
          if (((String)d1.get("zcbh")).equals(d3.get("zcbh")))
            result.put("zcyzaypc", d3.get("zcyzaypc")); 
        } 
        for (Map<String, String> d4 : datas4) {
          if (((String)d1.get("zcbh")).equals(d4.get("zcbh")))
            result.put("zcyzan", d4.get("zcyzan")); 
        } 
        for (Map<String, String> d5 : datas5) {
          if (((String)d1.get("zcbh")).equals(d5.get("zcbh")))
            result.put("ljzjaypc", d5.get("ljzjaypc")); 
        } 
        for (Map<String, String> d6 : datas6) {
          if (((String)d1.get("zcbh")).equals(d6.get("zcbh")))
            result.put("ljzjan", d6.get("ljzjan")); 
        } 
        datas.add(result);
      }  
    return datas;
  }
  
  public void startProcess(String bfbh) {
    List<Map<String, String>> gdzcmx = getFieldvalueForGdzcmx(bfbh);
    List<Map<String, String>> formBaseInfo = getBaseinfo();
    Map<String, String> info = new HashMap<String, String>();
    for (int i = 0; i < formBaseInfo.size(); i++) {
      if (((String)((Map)formBaseInfo.get(i)).get("formid")).equals("GDZC"))
        info = formBaseInfo.get(i); 
    } 
    if (this.bhlist != null && this.bhlist.size() > 0 && gdzcmx != null && gdzcmx.size() > 0)
      for (String bh : this.bhlist) {
        int num = 0;
        for (Map<String, String> mxmap : gdzcmx) {
          if (bh.equals(mxmap.get("zcbh"))) {
            String result = "-1";
            num++;
            try {
              String zcyz = (new StringBuilder(String.valueOf(Float.valueOf(mxmap.get("zcyzaypc")).floatValue() + Float.valueOf(mxmap.get("zcyzan")).floatValue()))).toString();
              String ljzj = (new StringBuilder(String.valueOf(Float.valueOf(mxmap.get("ljzjaypc")).floatValue() + Float.valueOf(mxmap.get("ljzjan")).floatValue()))).toString();
              String zmjz = (new StringBuilder(String.valueOf(Float.valueOf(zcyz).floatValue() - Float.valueOf(ljzj).floatValue()))).toString();
              StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
              xml.append("<WorkFlow>");
              xml.append("<Process processId=\"").append(info.get("processId")).append("\"/>");
              xml.append("<UserName  submitName=\"").append(info.get("submitName")).append("\"  receiveName=\"").append(info.get("receiveName")).append("\"/>");
              xml.append("<Data>");
              xml.append("<Table tableId=\"").append(info.get("tableId")).append("\" tableName=\"").append(info.get("tableName")).append("\">");
              xml.append("<Column>");
              xml.append("<field name=\"zcbh\" type=\"varchar\">").append(((String)mxmap.get("zcbh")).trim()).append("</field>");
              xml.append("</Column>");
              xml.append("</Table>");
              xml.append("<SubTable tableName=\"").append(info.get("subTableName")).append("\" type=\"").append(String.valueOf(num) + "\">");
              xml.append("<Column>");
              xml.append("<field name=\"zcbh\" type=\"varchar\">").append(mxmap.get("zcbh")).append("</field>");
              xml.append("<field name=\"zcmc\" type=\"varchar\">").append(mxmap.get("zcmc")).append("</field>");
              xml.append("<field name=\"ggxh\" type=\"varchar\">").append(mxmap.get("ggxh")).append("</field>");
              xml.append("<field name=\"pp\" type=\"varchar\">").append(mxmap.get("pp")).append("</field>");
              xml.append("<field name=\"zcyz\" type=\"varchar\">").append(zcyz).append("</field>");
              xml.append("<field name=\"gmrq\" type=\"varchar\">").append(mxmap.get("gmrq")).append("</field>");
              xml.append("<field name=\"ljzj\" type=\"varchar\">").append(ljzj).append("</field>");
              xml.append("<field name=\"synx\" type=\"varchar\">").append(mxmap.get("synx")).append("</field>");
              xml.append("<field name=\"zmjz\" type=\"varchar\">").append(zmjz).append("</field>");
              xml.append("</Column>");
              xml.append("</SubTable>");
              xml.append("</Data>");
              xml.append("</WorkFlow>");
              result = (new ClientInfoFromWeb()).createNewProcess(xml.toString());
            } catch (Exception ex) {
              ex.printStackTrace();
            } 
            if (!result.equals("-1") && result.length() < 10) {
              System.out.println("创建固定资产报废申请单成功！");
              insertTable(mxmap);
              continue;
            } 
            if (result.equals("-1"))
              System.out.println("固定资产报废申请单创建失败！"); 
          } 
        } 
      }  
  }
  
  public static int insertTable(Map<String, String> mapArg) {
    DataSource ds2 = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn2 = null;
    PreparedStatement pstmt2 = null;
    Calendar ca = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
    year = "1" + year.substring(2);
    String dayCount = (new StringBuilder(String.valueOf(ca.get(6)))).toString();
    String sql2 = "insert into PRODDTA.FE6OA06@jdedblink(FANUMB,FADL01,FADSC1,FADIVJ) values (?,?,?,?) ";
    try {
      conn2 = ds2.getConnection();
      pstmt2 = conn2.prepareStatement(sql2);
      pstmt2.setString(1, mapArg.get("zcbh"));
      pstmt2.setString(2, mapArg.get("zcmc"));
      pstmt2.setString(3, mapArg.get("username"));
      pstmt2.setString(4, String.valueOf(year) + dayCount);
      int resultInt = pstmt2.executeUpdate();
      if (resultInt > 0) {
        System.out.println("-----固定资产中间(FE6OA06)读取数据后回写成功-----");
      } else {
        System.out.println("-----固定资产中间(FE6OA06)读取数据后回写失败-----");
      } 
      pstmt2.close();
      conn2.close();
    } catch (SQLException e) {
      if (conn2 != null)
        try {
          conn2.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return 0;
  }
  
  public static Map<String, String> getInfo(String recordId, String useraccount) {
    Map<String, String> info = new HashMap<String, String>();
    String sql = "SELECT zcbh,zcmc FROM kf_gdzcbfmx WHERE kf_gdzcbfmx_FOREIGNKEY=?";
    String sql2 = "select empname from org_employee where useraccounts=?";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        info.put("zcbh", rs.getString("zcbh"));
        info.put("zcmc", rs.getString("zcmc"));
      } 
      rs.close();
      pstmt.close();
      pstmt = conn.prepareStatement(sql2);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
      while (rs.next())
        info.put("username", rs.getString("empname")); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e1) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      e1.printStackTrace();
    } 
    return info;
  }
  
  public List<Map<String, String>> getBaseinfo() {
    List<Map<String, String>> infos = new ArrayList<Map<String, String>>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/jde.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> formNodes = root.getChildren();
      for (int i = 0; i < formNodes.size(); i++) {
        Element node = formNodes.get(i);
        Map<String, String> nodeMap = new HashMap<String, String>();
        nodeMap.put("formid", node.getAttributeValue("id"));
        nodeMap.put("processId", node.getAttributeValue("processId"));
        nodeMap.put("tableId", node.getAttributeValue("tableId"));
        nodeMap.put("tableName", node.getAttributeValue("tableName"));
        nodeMap.put("submitName", node.getAttributeValue("submitName"));
        nodeMap.put("receiveName", node.getAttributeValue("receiveName"));
        nodeMap.put("subTableName", node.getAttributeValue("subTableName"));
        infos.add(nodeMap);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infos;
  }
}
