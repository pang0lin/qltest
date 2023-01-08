package com.js.oa.form.pengchi;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreateProcessForGys {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  public List<Map<String, String>> getFieldValueForGys() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "SELECT SEECO,SELNID,CRPDTA.NTOD(SETRDJ) SETRDJ,SEAN8,SEDL02,ABALPH gysmc,ABAC21,A.DRDL01 gyslxsm,SEAC20,B.DRDL01 wlflsm,SEAC23,CRPDTA.NTOD(SESTRT)  SESTART,CRPDTA.NTOD(SEEND) SEEND,SEECV1,SEECV2,SEECV3,SEECV4,SEECV5,SEECV6,SEECV7,SEECV8,SEAMRC,SEFRGD,SEVSPC,SEDL01 from CRPDTA.FE64311 ,CRPDTA.F0101 ,CRPCTL.F0005  A,CRPCTL.F0005  B  where F0101.ABAN8=FE64311.SEAN8 and SEEV05 <> 'Y'  and  A.DRSY='01'  AND A.DRRT='21' AND TRIM(A.DRKY)=TRIM(SEAC21) and  B.DRSY='01'  AND B.DRRT='20' AND TRIM(B.DRKY)=TRIM(SEAC20) ";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String ecoid = "";
    String lnid = "";
    List<Map<String, String>> keyInfo = new ArrayList<Map<String, String>>();
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("bh", "");
        result.put("zdr", "");
        result.put("rq", rs.getString("SETRDJ").substring(0, 10));
        result.put("pgbh", rs.getString("SEECO"));
        result.put("hh", rs.getString("SELNID"));
        result.put("gysbm", rs.getString("SEAN8"));
        result.put("gysmc", rs.getString("GYSMC"));
        result.put("gyslx", rs.getString("ABAC21"));
        result.put("lxsm", rs.getString("gyslxsm"));
        result.put("wlfl", rs.getString("SEAC20"));
        result.put("flsm", rs.getString("wlflsm"));
        result.put("ppxh", rs.getString("SEAC23"));
        result.put("ppxhsm", "");
        result.put("pgkssj", rs.getString("SESTART").substring(0, 10));
        result.put("pgjssj", rs.getString("SEEND").substring(0, 10));
        result.put("jhcs", rs.getString("SEECV1"));
        result.put("jhyqcs", rs.getString("SEECV2"));
        result.put("jhdf", rs.getString("SEECV3"));
        result.put("cgjgdj", rs.getString("SEDL02"));
        result.put("thcs", rs.getString("SEECV5"));
        result.put("tccs", rs.getString("SEECV6"));
        result.put("pzdf", rs.getString("SEECV7"));
        result.put("fwdf", rs.getString("SEECV8"));
        result.put("zf", rs.getString("SEECV4"));
        result.put("pjjl", rs.getString("SEFRGD"));
        result.put("fpbl", String.valueOf(rs.getLong("SEVSPC") / 100.0D));
        result.put("bz", rs.getString("SEDL01"));
        datas.add(result);
        Map<String, String> keyMap = new HashMap<String, String>();
        ecoid = rs.getString("SEECO").trim();
        lnid = rs.getString("SELNID").trim();
        keyMap.put("ecoid", ecoid);
        keyMap.put("lnid", lnid);
        keyInfo.add(keyMap);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return datas;
  }
  
  public void startProcess(String gysbm) {
    List<Map<String, String>> gys = getFieldValueForGys();
    List<Map<String, String>> formBaseInfo = getBaseinfo();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String printTime = sdf.format(new Date());
    Map<String, String> info = new HashMap<String, String>();
    for (int i = 0; i < formBaseInfo.size(); i++) {
      if (((String)((Map)formBaseInfo.get(i)).get("formid")).equals("GYS"))
        info = formBaseInfo.get(i); 
    } 
    if (gys != null && gys.size() > 0) {
      String result = "-1";
      for (Map<String, String> gysmap : gys) {
        String pgbh = gysmap.get("pgbh");
        String hh = gysmap.get("hh");
        try {
          StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
          xml.append("<WorkFlow>");
          xml.append("<Process processId=\"").append(info.get("processId")).append("\"/>");
          xml.append("<UserName  submitName=\"").append(info.get("submitName")).append("\"  receiveName=\"").append(info.get("receiveName")).append("\"/>");
          xml.append("<Data>");
          xml.append("<Table tableId=\"").append(info.get("tableId")).append("\" tableName=\"").append(info.get("tableName")).append("\">");
          xml.append("<Column>");
          xml.append("<field name=\"dysj\" type=\"varchar\">").append(printTime).append("</field>");
          xml.append("<field name=\"zdr\" type=\"varchar\">").append(((String)gysmap.get("zdr")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</Table>");
          int num = 0;
          num++;
          xml.append("<SubTable tableName=\"").append(info.get("subTableName")).append("\" type=\"").append(String.valueOf(num) + "\">");
          xml.append("<Column>");
          xml.append("<field name=\"pgbh\" type=\"varchar\">").append(((String)gysmap.get("pgbh")).trim()).append("</field>");
          xml.append("<field name=\"hh\" type=\"varchar\">").append(((String)gysmap.get("hh")).trim()).append("</field>");
          xml.append("<field name=\"gysbm\" type=\"varchar\">").append(((String)gysmap.get("gysbm")).trim()).append("</field>");
          xml.append("<field name=\"gysmc\" type=\"varchar\">").append(((String)gysmap.get("gysmc")).trim()).append("</field>");
          xml.append("<field name=\"gyslx\" type=\"varchar\">").append(((String)gysmap.get("gyslx")).trim()).append("</field>");
          xml.append("<field name=\"lxsm\" type=\"varchar\">").append(((String)gysmap.get("lxsm")).trim()).append("</field>");
          xml.append("<field name=\"wlfl\" type=\"varchar\">").append(((String)gysmap.get("wlfl")).trim()).append("</field>");
          xml.append("<field name=\"flsm\" type=\"varchar\">").append(((String)gysmap.get("flsm")).trim()).append("</field>");
          xml.append("<field name=\"ppxh\" type=\"varchar\">").append(((String)gysmap.get("ppxh")).trim()).append("</field>");
          xml.append("<field name=\"ppxhsm\" type=\"varchar\">").append(((String)gysmap.get("ppxhsm")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</SubTable>");
          xml.append("<SubTable tableName=\"").append(info.get("subTableName1")).append("\" type=\"").append(String.valueOf(num) + "\">");
          xml.append("<Column>");
          xml.append("<field name=\"pgkssj\" type=\"varchar\">").append(((String)gysmap.get("pgkssj")).trim()).append("</field>");
          xml.append("<field name=\"pgjssj\" type=\"varchar\">").append(((String)gysmap.get("pgjssj")).trim()).append("</field>");
          xml.append("<field name=\"jhcs\" type=\"varchar\">").append(((String)gysmap.get("jhcs")).trim()).append("</field>");
          xml.append("<field name=\"jhyqcs\" type=\"varchar\">").append(((String)gysmap.get("jhyqcs")).trim()).append("</field>");
          xml.append("<field name=\"jhdf\" type=\"varchar\">").append(((String)gysmap.get("jhdf")).trim()).append("</field>");
          xml.append("<field name=\"cgjgdj\" type=\"varchar\">").append(((String)gysmap.get("cgjgdj")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</SubTable>");
          String fpbl = ((String)gysmap.get("fpbl")).trim();
          if (fpbl.endsWith(".0")) {
            fpbl = String.valueOf(fpbl.substring(0, fpbl.indexOf("."))) + "%";
          } else {
            fpbl = String.valueOf(fpbl) + "%";
          } 
          xml.append("<SubTable tableName=\"").append(info.get("subTableName2")).append("\" type=\"").append(String.valueOf(num) + "\">");
          xml.append("<Column>");
          xml.append("<field name=\"thcs\" type=\"varchar\">").append(((String)gysmap.get("thcs")).trim()).append("</field>");
          xml.append("<field name=\"tccs\" type=\"varchar\">").append(((String)gysmap.get("tccs")).trim()).append("</field>");
          xml.append("<field name=\"pzdf\" type=\"varchar\">").append(((String)gysmap.get("pzdf")).trim()).append("</field>");
          xml.append("<field name=\"fwdf\" type=\"varchar\">").append(((String)gysmap.get("fwdf")).trim()).append("</field>");
          xml.append("<field name=\"zf\" type=\"varchar\">").append(((String)gysmap.get("zf")).trim()).append("</field>");
          xml.append("<field name=\"pjjl\" type=\"varchar\">").append(((String)gysmap.get("pjjl")).trim()).append("</field>");
          xml.append("<field name=\"fpbl\" type=\"varchar\">").append(fpbl).append("</field>");
          xml.append("<field name=\"bz\" type=\"varchar\">").append(((String)gysmap.get("bz")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</SubTable>");
          xml.append("</Data>");
          xml.append("</WorkFlow>");
          result = (new ClientInfoFromWeb()).createNewProcess(xml.toString());
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (!result.equals("-1") && result.length() < 10) {
          System.out.println("创建供应商评级表流程成功！--供应商编码是：【" + ((String)gysmap.get("gysbm")).trim() + "】--时间：【" + printTime + "】");
          continue;
        } 
        if (result.equals("-1")) {
          System.out.println("创建供应商评级表流程失败！--时间：【" + printTime + "】");
          String sql = "update CRPDTA.FE64311  set SEEV05=' '  where SEECO='" + pgbh + "' and SELNID=?";
          DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
          PreparedStatement pstmt = null;
          Connection conn = null;
          try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hh);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
          } catch (Exception e1) {
            if (conn != null)
              try {
                conn.close();
              } catch (Exception err) {
                err.printStackTrace();
              }  
            e1.printStackTrace();
          } 
        } 
      } 
    } 
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
        if (((String)nodeMap.get("formid")).equals("GYS")) {
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
          nodeMap.put("submitName", node.getAttributeValue("submitName"));
          nodeMap.put("receiveName", node.getAttributeValue("receiveName"));
          nodeMap.put("subTableName", node.getAttributeValue("subTableName"));
          if (node.getAttributeValue("subTableName1") != null)
            nodeMap.put("subTableName1", node.getAttributeValue("subTableName1")); 
          if (node.getAttributeValue("subTableName2") != null)
            nodeMap.put("subTableName2", node.getAttributeValue("subTableName2")); 
          infos.add(nodeMap);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infos;
  }
}
