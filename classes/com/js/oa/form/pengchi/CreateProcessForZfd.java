package com.js.oa.form.pengchi;

import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreateProcessForZfd {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  public List<Map<String, String>> getFieldValueForZf(String gysbm, String zfdh) {
    List<Map<String, String>> zfdatas = new ArrayList<Map<String, String>>();
    String zfsql = "select  PIKCO,PIDCT,PIDOC,PIAN8,PIALPH,PIAG,PIDL01,PIPTC,PNPTD,PIGLC,CRPDTA.NTOD(PIDDJ) PIDDJ,PIAAP from PRODDTA.FE6OA05@jdedblink ,PRODDTA.F0014@jdedblink  WHERE PIPTC=PNPTC AND PIPST='H' and  PIAN8=? and PIDOC in (" + 
      zfdh + ")";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    try {
      pstmt = conn.prepareStatement(zfsql);
      pstmt.setString(1, gysbm);
      rs = pstmt.executeQuery();
      DecimalFormat df = new DecimalFormat("0.00");
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        if (rs.getString("PIGLC").trim().equals("PREP")) {
          result.put("sfyf", "是");
        } else {
          result.put("sfyf", "否");
        } 
        result.put("gs", rs.getString("PIKCO").trim());
        result.put("dct", rs.getString("PIDCT").trim());
        result.put("doc", rs.getString("PIDOC").trim());
        result.put("zfdh", String.valueOf(rs.getString("PIDCT").trim()) + rs.getString("PIDOC").trim());
        result.put("gysdm", rs.getString("PIAN8").trim());
        result.put("gysmc", rs.getString("PIALPH").trim());
        result.put("zfje", df.format(rs.getLong("PIAG") / 100.0D));
        result.put("zffs", rs.getString("PIDL01").trim());
        result.put("fktk", rs.getString("PNPTD").trim());
        result.put("dqr", rs.getString("PIDDJ").substring(0, 10));
        result.put("wjje", df.format(rs.getLong("PIAAP") / 100.0D));
        zfdatas.add(result);
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
    return zfdatas;
  }
  
  public List<Map<String, String>> getFieldValueForCg(String gysbm, String ddh) {
    List<Map<String, String>> cgdatas = new ArrayList<Map<String, String>>();
    String cgsql = "select PRKCO,PRDCT,PRDOC,PRVINV,PRDCTO,PRDOCO,PRLNID,PRLITM,PRUREC,PRPRRC,CRPDTA.NTOD(PRRCDJ) PRRCDJ,PRUOM,IMDSC1,IMDSC2 ,TATXR1 from PRODDTA.F43121@jdedblink,PRODDTA.FE6OA05@jdedblink,PRODDTA.F4101@jdedblink,PRODDTA.F4008@jdedblink WHERE FE6OA05.PIKCO=F43121.PRKCO and PIDCT=PRDCT and FE6OA05.PIDOC=F43121.PRDOC AND  PIPST='H' AND PIAN8=? and PRDOC in (" + 
      ddh + ") and  F43121.PRLITM=F4101.IMLITM  and F4008.TATXA1=F43121.PRTXA1";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(cgsql);
      pstmt.setString(1, gysbm);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("gs", rs.getString("PRKCO").trim());
        result.put("dct", rs.getString("PRDCT").trim());
        result.put("doc", rs.getString("PRDOC").trim());
        result.put("fph", rs.getString("PRVINV"));
        result.put("ddlx", rs.getString("PRDCTO"));
        result.put("ddh", rs.getString("PRDOCO"));
        result.put("lh", rs.getString("PRLITM"));
        result.put("ysrq", rs.getString("PRRCDJ").substring(0, 10));
        result.put("dw", rs.getString("PRUOM"));
        result.put("lp", rs.getString("IMDSC1"));
        result.put("gg", rs.getString("IMDSC2"));
        result.put("sl", String.valueOf(rs.getLong("PRUREC") / 10000.0D));
        result.put("ddhang", String.valueOf(rs.getLong("PRLNID") / 1000L));
        double ysdj = rs.getLong("PRPRRC") / 10000.0D * (1.0D + rs.getLong("TATXR1") / 100000.0D);
        float sl = Float.valueOf(result.get("sl")).floatValue();
        double dj = Math.round(ysdj * 10000.0D) / 10000.0D;
        result.put("dj", String.valueOf(dj));
        double je = Math.round(sl * dj * 100.0D) / 100.0D;
        result.put("je", String.valueOf(je));
        cgdatas.add(result);
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
    return cgdatas;
  }
  
  public List<Map<String, String>> getFieldValueForYfk(String gysbm, String ddh) {
    List<Map<String, String>> cgdatas = new ArrayList<Map<String, String>>();
    String cgsql = "select  PIKCO,PIDCT,PIDOC,PDDCTO,PDDOCO,PDLITM ,PDUREC,PDLNID,PDPRRC,PDUOM,IMDSC1,IMDSC2,TATXR1 from PRODDTA.FE6OA05@jdedblink ,PRODDTA.F0411@jdedblink ,PRODDTA.F4311@jdedblink ,PRODDTA.F4101@jdedblink ,PRODDTA.F4008@jdedblink  WHERE PIKCO=RPKCO AND PIDOC=RPDOC AND PIDCT=RPDCT AND PIGLC='PREP' AND RPPKCO=PDKCOO AND RPPO=PDDOCO AND RPPDCT=PDDCTO AND PDLITM=IMLITM AND F4008.TATXA1=F4311.PDTXA1 AND  PIPST='H' AND PIAN8=? and PIDOC in (" + 
      ddh + ") ";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      pstmt = conn.prepareStatement(cgsql);
      pstmt.setString(1, gysbm);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("gs", rs.getString("PIKCO").trim());
        result.put("dct", rs.getString("PIDCT").trim());
        result.put("doc", rs.getString("PIDOC").trim());
        result.put("fph", "");
        result.put("ddlx", rs.getString("PDDCTO"));
        result.put("ddh", rs.getString("PDDOCO"));
        result.put("lh", rs.getString("PDLITM"));
        result.put("ysrq", "");
        result.put("dw", rs.getString("PDUOM"));
        result.put("lp", rs.getString("IMDSC1"));
        result.put("gg", rs.getString("IMDSC2"));
        result.put("sl", String.valueOf(rs.getLong("PDUREC") / 10000.0D));
        result.put("ddhang", String.valueOf(rs.getLong("PDLNID") / 1000L));
        double ysdj = rs.getLong("PRPRRC") / 10000.0D * (1.0D + rs.getLong("TATXR1") / 100000.0D);
        float sl = Float.valueOf(result.get("sl")).floatValue();
        double dj = Math.round(ysdj * 10000.0D) / 10000.0D;
        result.put("dj", String.valueOf(dj));
        double je = Math.round(sl * dj * 100.0D) / 100.0D;
        result.put("je", String.valueOf(je));
        cgdatas.add(result);
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
    return cgdatas;
  }
  
  public static void updateTable(List<Map<String, String>> para) {
    Calendar ca = Calendar.getInstance();
    String year = (new StringBuilder(String.valueOf(ca.get(1)))).toString();
    year = "1" + year.substring(2);
    int today = ca.get(6);
    String dayCount = "";
    if (today < 10) {
      dayCount = "00" + today;
    } else if (today >= 10 && today < 100) {
      dayCount = "0" + today;
    } else {
      dayCount = (new StringBuilder(String.valueOf(today))).toString();
    } 
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    PreparedStatement pstmt = null;
    Connection conn = null;
    if (para != null && para.size() > 0)
      try {
        String sql = "update PRODDTA.FE6OA05@jdedblink set PIPST='#',PIAURDT2=? where PIKCO=? and PIDCT=? and PIDOC=?";
        conn = ds.getConnection();
        pstmt = conn.prepareStatement(sql);
        conn.setAutoCommit(false);
        for (Map<String, String> zf : para) {
          pstmt.setString(1, String.valueOf(year) + dayCount);
          pstmt.setString(2, zf.get("gs"));
          pstmt.setString(3, zf.get("dct"));
          pstmt.setString(4, zf.get("doc"));
          pstmt.addBatch();
        } 
        int[] num = pstmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        pstmt.close();
        if (num.length == para.size()) {
          System.out.println("-----支付单中间(FE6OA05)数据读取成功-----共" + num.length + "条");
        } else {
          System.out.println("---------支付单(FE6OA05)读取-----------" + num.length + "条");
        } 
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
        nodeMap.put("subTableName1", node.getAttributeValue("subTableName1"));
        infos.add(nodeMap);
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infos;
  }
}
