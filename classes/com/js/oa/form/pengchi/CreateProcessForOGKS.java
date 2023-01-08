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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreateProcessForOGKS {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  public List<Map<String, String>> getFieldValueForOG() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select THUKID,THKCOO,THDCTO,THSFXO,THRORN,THDOCO,THALPH,THVR01,THMLNM,THDL01,THADD1,THVR01,CRPDTA.NTOD(THTRDJ) THTRDJ,THANBY,THDL03,THDL04,THURAT,PNPTD,THDL11,A6CRCA from  PRODDTA.FE64319H@jdedblink,PRODDTA.F0401@jdedblink,PRODDTA.F0014@jdedblink where FE64319H.THAN8= F0401.A6AN8 and PNPTC=THPTC and THDCTO='OG' and THPOHP01=0 and THNXTR=' ' and THKCOO='00016'";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (Exception e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String updateSql = "";
    String ukids = "";
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("wxcs", rs.getString("THALPH"));
        result.put("lxr", rs.getString("THMLNM"));
        result.put("lxrdh", rs.getString("THDL01"));
        result.put("lxrcz", "");
        result.put("lxrdz", rs.getString("THADD1"));
        result.put("zdr", rs.getString("THVR01"));
        result.put("qxdh", rs.getString("THRORN"));
        result.put("sxdh", rs.getString("THDOCO"));
        result.put("rq", rs.getString("THTRDJ").substring(0, 10));
        result.put("cgy", rs.getString("THANBY"));
        result.put("cgydh", rs.getString("THDL03"));
        result.put("cgycz", rs.getString("THDL04"));
        result.put("bz", rs.getString("A6CRCA"));
        result.put("hjsl", "");
        result.put("zkje", "");
        result.put("zje", "");
        result.put("fkfs", rs.getString("PNPTD"));
        result.put("fplx", rs.getString("THDL11"));
        result.put("ukzj", rs.getString("THUKID").trim());
        result.put("ddlx", rs.getString("THDCTO").trim());
        result.put("ddgs", rs.getString("THKCOO").trim());
        result.put("ddh", rs.getString("THDOCO").trim());
        result.put("ddhz", rs.getString("THSFXO").trim());
        datas.add(result);
        ukids = String.valueOf(ukids) + rs.getString("THUKID").trim() + ",";
      } 
      rs.close();
      pstmt.close();
      SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
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
      String updateRemark = "OA修改状态为230,时间:" + sdf.format(new Date());
      if (ukids.length() > 0) {
        ukids = ukids.substring(0, ukids.length() - 1);
        String[] idArray = ukids.split(",");
        updateSql = "update PRODDTA.FE64319H@jdedblink set THNXTR='230',THPOHP01='0',THPOHP02='N',THUSER='OA',THUPMJ=?,THDL19=? where THUKID =?";
        pstmt = conn.prepareStatement(updateSql);
        conn.setAutoCommit(false);
        for (int i = 0; i < idArray.length; i++) {
          pstmt.setString(1, String.valueOf(year) + dayCount);
          pstmt.setString(2, updateRemark);
          pstmt.setString(3, idArray[i]);
          pstmt.addBatch();
        } 
        pstmt.executeBatch();
        conn.commit();
        System.out.println("------成功修改OG（昆山）单的读取状态--------------");
        conn.setAutoCommit(true);
        pstmt.close();
      } 
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
  
  public List<Map<String, String>> getFieldvalueForOGmx() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select PDASID,PDDL04,PDDS50,PDUOM,PDDL03,PDPRRC,PDUORG,PDDSPR,PDURAT,PDVR01,CRPDTA.NTOD(PDDRQJ) PDDRQJ,CRPDTA.NTOD(PDPDDJ) PDPDDJ,PDSGTXT,PDD200,PDDL02,PDUKID,PDDCTO,PDKCOO,PDDOCO,PDSFXO from PRODDTA.FE64319D@jdedblink where PDPOHP01=0 and PDKCOO='00016' and PDDCTO=?";
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
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, "OG");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", rs.getString("PDASID"));
        result.put("zcmc", rs.getString("PDDL04"));
        result.put("wxsx", rs.getString("PDDS50"));
        result.put("dw", rs.getString("PDDL03"));
        result.put("dj", String.valueOf(rs.getLong("PDPRRC") / 10000.0D));
        result.put("sl", String.valueOf(rs.getLong("PDUORG") / 10000.0D));
        result.put("zkl", "0");
        result.put("je", String.valueOf(rs.getLong("PDURAT") / 100.0D));
        result.put("yqrq", rs.getString("PDDRQJ").substring(0, 10));
        result.put("bxdqr", rs.getString("PDPDDJ").substring(0, 10));
        result.put("gzwtms", rs.getString("PDSGTXT"));
        result.put("wxjtsx", rs.getString("PDD200"));
        result.put("sxsffy", rs.getString("PDDL02"));
        result.put("sxbz", rs.getString("PDVR01"));
        result.put("ukzj", rs.getString("PDUKID").trim());
        result.put("ddlx", rs.getString("PDDCTO").trim());
        result.put("ddgs", rs.getString("PDKCOO").trim());
        result.put("ddh", rs.getString("PDDOCO").trim());
        result.put("ddhz", rs.getString("PDSFXO").trim());
        datas.add(result);
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
  
  public void startProcess() {
    List<Map<String, String>> og = getFieldValueForOG();
    List<Map<String, String>> ogmx = getFieldvalueForOGmx();
    List<Map<String, String>> formBaseInfo = getBaseinfo();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String printTime = sdf.format(new Date());
    Map<String, String> info = new HashMap<String, String>();
    for (int i = 0; i < formBaseInfo.size(); i++) {
      if (((String)((Map)formBaseInfo.get(i)).get("formid")).equals("OGKS"))
        info = formBaseInfo.get(i); 
    } 
    if (og != null && og.size() > 0 && ogmx != null && ogmx.size() > 0) {
      String result = "-1";
      for (Map<String, String> ogmap : og) {
        String ukid = ogmap.get("ukzj");
        String cgy = getOtherInfo(ogmap.get("cgy"), "cgy").get("cgy");
        Map<String, String> infomap = getOtherInfo(ogmap.get("cgy"), "lxfs");
        String cgydh = "";
        String cgycz = "";
        if (infomap != null && infomap.size() > 0) {
          if (infomap.get("cgydh") != null && !((String)infomap.get("cgydh")).equals(""))
            cgydh = infomap.get("cgydh"); 
          if (infomap.get("cgycz") != null && !((String)infomap.get("cgycz")).equals(""))
            cgycz = infomap.get("cgycz"); 
        } 
        String account = GetPersoninfo.getOaUseraccount(((String)ogmap.get("zdr")).trim());
        try {
          float totalNum = 0.0F;
          double totalCorrectNum = 0.0D;
          float totalMoney = 0.0F;
          for (Map<String, String> mxmap : ogmx) {
            if (((String)ogmap.get("ukzj")).equals(mxmap.get("ukzj")) && ((String)ogmap.get("ddlx")).equals(mxmap.get("ddlx")) && ((String)ogmap.get("ddgs")).equals(mxmap.get("ddgs")) && ((String)ogmap.get("ddh")).equals(mxmap.get("ddh")) && ((String)ogmap.get("ddhz")).equals(mxmap.get("ddhz"))) {
              totalNum += Float.valueOf(mxmap.get("sl")).floatValue();
              totalMoney += Float.valueOf(mxmap.get("je")).floatValue();
            } 
          } 
          totalCorrectNum = Math.round(totalNum * 1000.0D) / 1000.0D;
          String ddgs = ogmap.get("ddgs");
          String jsr = "";
          if (ddgs.equals("00016"))
            jsr = info.get("receiveName"); 
          StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
          xml.append("<WorkFlow>");
          xml.append("<Process processId=\"").append(info.get("processId")).append("\"/>");
          xml.append("<UserName  submitName=\"").append(account).append("\"  receiveName=\"").append(jsr).append("\"/>");
          xml.append("<Data>");
          xml.append("<Table tableId=\"").append(info.get("tableId")).append("\" tableName=\"").append(info.get("tableName")).append("\">");
          xml.append("<Column>");
          xml.append("<field name=\"dysj\" type=\"varchar\">").append(printTime).append("</field>");
          xml.append("<field name=\"qxdh\" type=\"varchar\">").append(((String)ogmap.get("qxdh")).trim()).append("</field>");
          xml.append("<field name=\"wxcs\" type=\"varchar\">").append(((String)ogmap.get("wxcs")).trim()).append("</field>");
          xml.append("<field name=\"sxdh\" type=\"varchar\">").append(((String)ogmap.get("sxdh")).trim()).append("</field>");
          xml.append("<field name=\"lxr\" type=\"varchar\">").append(((String)ogmap.get("lxr")).trim()).append("</field>");
          xml.append("<field name=\"rq\" type=\"varchar\">").append(((String)ogmap.get("rq")).trim()).append("</field>");
          xml.append("<field name=\"lxrdh\" type=\"varchar\">").append(((String)ogmap.get("lxrdh")).trim()).append("</field>");
          xml.append("<field name=\"cgy\" type=\"varchar\">").append(cgy).append("</field>");
          xml.append("<field name=\"cgydh\" type=\"varchar\">").append(cgydh).append("</field>");
          xml.append("<field name=\"lxrdz\" type=\"varchar\">").append(((String)ogmap.get("lxrdz")).trim()).append("</field>");
          xml.append("<field name=\"cgycz\" type=\"varchar\">").append(cgycz).append("</field>");
          xml.append("<field name=\"zdr\" type=\"varchar\">").append(((String)ogmap.get("zdr")).trim()).append("</field>");
          xml.append("<field name=\"bz\" type=\"varchar\">").append(((String)ogmap.get("bz")).trim()).append("</field>");
          xml.append("<field name=\"hjsl\" type=\"varchar\">").append(totalCorrectNum).append("</field>");
          xml.append("<field name=\"zkje\" type=\"varchar\">").append(((String)ogmap.get("zkje")).trim()).append("</field>");
          xml.append("<field name=\"zje\" type=\"varchar\">").append(totalMoney).append("</field>");
          xml.append("<field name=\"fkfs\" type=\"varchar\">").append(((String)ogmap.get("fkfs")).trim()).append("</field>");
          xml.append("<field name=\"fplx\" type=\"varchar\">").append(((String)ogmap.get("fplx")).trim()).append("</field>");
          xml.append("<field name=\"ukzj\" type=\"varchar\">").append(((String)ogmap.get("ukzj")).trim()).append("</field>");
          xml.append("<field name=\"ddlx\" type=\"varchar\">").append(((String)ogmap.get("ddlx")).trim()).append("</field>");
          xml.append("<field name=\"ddgs\" type=\"varchar\">").append(((String)ogmap.get("ddgs")).trim()).append("</field>");
          xml.append("<field name=\"ddh\" type=\"varchar\">").append(((String)ogmap.get("ddh")).trim()).append("</field>");
          xml.append("<field name=\"ddhz\" type=\"varchar\">").append(((String)ogmap.get("ddhz")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</Table>");
          int num = 0;
          for (Map<String, String> mxmap : ogmx) {
            if (((String)ogmap.get("ukzj")).equals(mxmap.get("ukzj")) && ((String)ogmap.get("ddlx")).equals(mxmap.get("ddlx")) && ((String)ogmap.get("ddgs")).equals(mxmap.get("ddgs")) && ((String)ogmap.get("ddh")).equals(mxmap.get("ddh")) && ((String)ogmap.get("ddhz")).equals(mxmap.get("ddhz"))) {
              num++;
              xml.append("<SubTable tableName=\"").append(info.get("subTableName")).append("\" type=\"").append(String.valueOf(num) + "\">");
              xml.append("<Column>");
              xml.append("<field name=\"zcbh\" type=\"varchar\">").append(((String)mxmap.get("zcbh")).trim()).append("</field>");
              xml.append("<field name=\"zcmc\" type=\"varchar\">").append(((String)mxmap.get("zcmc")).trim()).append("</field>");
              xml.append("<field name=\"wxsx\" type=\"varchar\">").append(((String)mxmap.get("wxsx")).trim()).append("</field>");
              xml.append("<field name=\"dw\" type=\"varchar\">").append(((String)mxmap.get("dw")).trim()).append("</field>");
              xml.append("<field name=\"dj\" type=\"varchar\">").append(((String)mxmap.get("dj")).trim()).append("</field>");
              xml.append("<field name=\"sl\" type=\"varchar\">").append(((String)mxmap.get("sl")).trim()).append("</field>");
              xml.append("<field name=\"zkl\" type=\"varchar\">").append(((String)mxmap.get("zkl")).trim()).append("</field>");
              xml.append("<field name=\"je\" type=\"varchar\">").append(((String)mxmap.get("je")).trim()).append("</field>");
              xml.append("<field name=\"yqrq\" type=\"varchar\">").append(((String)mxmap.get("yqrq")).trim()).append("</field>");
              xml.append("<field name=\"bxdqr\" type=\"varchar\">").append(((String)mxmap.get("bxdqr")).trim()).append("</field>");
              xml.append("<field name=\"ukzj\" type=\"varchar\">").append(((String)mxmap.get("ukzj")).trim()).append("</field>");
              xml.append("</Column>");
              xml.append("</SubTable>");
              xml.append("<SubTable tableName=\"").append(info.get("subTableName1")).append("\" type=\"").append(String.valueOf(num) + "\">");
              xml.append("<Column>");
              xml.append("<field name=\"gzwtms\" type=\"varchar\">").append(((String)mxmap.get("gzwtms")).trim()).append("</field>");
              xml.append("<field name=\"wxjtsx\" type=\"varchar\">").append(((String)mxmap.get("wxjtsx")).trim()).append("</field>");
              xml.append("<field name=\"sxsffy\" type=\"varchar\">").append(((String)mxmap.get("sxsffy")).trim()).append("</field>");
              xml.append("<field name=\"sxbz\" type=\"varchar\">").append(((String)mxmap.get("sxbz")).trim()).append("</field>");
              xml.append("</Column>");
              xml.append("</SubTable>");
            } 
          } 
          xml.append("</Data>");
          xml.append("</WorkFlow>");
          result = (new ClientInfoFromWeb()).createNewProcess(xml.toString());
        } catch (Exception e) {
          e.printStackTrace();
        } 
        if (!result.equals("-1") && result.length() < 10) {
          System.out.println("时间：[" + printTime + "] 创建送修单OG（昆山）成功！--单号是：" + (String)ogmap.get("ddh"));
          continue;
        } 
        if (result.equals("-1")) {
          System.out.println("时间：[" + printTime + "] 创建送修单OG（昆山）失败！--单号是：" + (String)ogmap.get("ddh"));
          String sql = "update PRODDTA.FE64319H@jdedblink set THNXTR=' ' where THUKID=?";
          DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
          PreparedStatement pstmt = null;
          Connection conn = null;
          try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ukid);
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
  
  public Map<String, String> getOtherInfo(String para, String flag) {
    Map<String, String> info = new HashMap<String, String>();
    String sql = " SELECT ABALPH FROM PRODDTA.F0101@jdedblink  LEFT JOIN PRODCTL.F0005@jdedblink ON DRSY='01' AND DRRT='23' AND ABAC23=DRKY  WHERE ABAN8=?";
    String sql1 = "SELECT WPAR1,WPPH1 FROM PRODDTA.F0115@jdedblink  INNER JOIN PRODCTL.F0005@jdedblink ON DRSY='01' AND DRRT='PH' AND TRIM(DRKY)=TRIM(WPPHTP) WHERE TRIM(WPPHTP)='TEL' AND WPAN8=?";
    String sql2 = "SELECT WPAR1,WPPH1 FROM PRODDTA.F0115@jdedblink  INNER JOIN PRODCTL.F0005@jdedblink ON DRSY='01' AND DRRT='PH' AND TRIM(DRKY)=TRIM(WPPHTP) WHERE TRIM(WPPHTP)='FAX' AND WPAN8=?";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      if (flag.equals("cgy")) {
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, para);
        rs = pstmt.executeQuery();
        while (rs.next())
          info.put("cgy", rs.getString("ABALPH").trim()); 
        rs.close();
        pstmt.close();
      } else if (flag.equals("lxfs")) {
        pstmt = conn.prepareStatement(sql1);
        pstmt.setString(1, para);
        rs = pstmt.executeQuery();
        String dh = "";
        while (rs.next()) {
          String str1 = rs.getString("WPAR1").trim();
          String str2 = rs.getString("WPPH1").trim();
          dh = String.valueOf(str1) + "-" + str2;
          info.put("cgydh", dh);
        } 
        rs.close();
        pstmt.close();
        pstmt = conn.prepareStatement(sql2);
        pstmt.setString(1, para);
        rs = pstmt.executeQuery();
        String cz = "";
        while (rs.next()) {
          String str1 = rs.getString("WPAR1").trim();
          String str2 = rs.getString("WPPH1").trim();
          cz = String.valueOf(str1) + "-" + str2;
          info.put("cgycz", cz);
        } 
        rs.close();
        pstmt.close();
      } 
      conn.close();
    } catch (Exception e1) {
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
        if (((String)nodeMap.get("formid")).equals("OGKS")) {
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
          nodeMap.put("receiveName", node.getAttributeValue("receiveName"));
          nodeMap.put("subTableName", node.getAttributeValue("subTableName"));
          nodeMap.put("subTableName1", node.getAttributeValue("subTableName1"));
          infos.add(nodeMap);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infos;
  }
}
