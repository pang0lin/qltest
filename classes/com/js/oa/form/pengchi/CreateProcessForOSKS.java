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

public class CreateProcessForOSKS {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  public List<Map<String, String>> getFieldValueForOS() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select THUKID,THKCOO,THDCTO,THSFXO,THDOCO,CRPDTA.NTOD(THTRDJ) THTRDJ,THDL07,THVR01,THRMK from  PRODDTA.FE64319H@jdedblink where THDCTO='OS' and THNXTR=' ' and THPOHP01=0  and THKCOO='00016'";
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
        result.put("djbh", rs.getString("THDOCO"));
        result.put("ddrq", rs.getString("THTRDJ").substring(0, 10));
        result.put("sqbm", rs.getString("THDL07"));
        result.put("sqr", rs.getString("THVR01"));
        result.put("bz", rs.getString("THRMK"));
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
      String updateRemark = "OA修改状态为110,时间:" + sdf.format(new Date());
      if (ukids.length() > 0) {
        ukids = ukids.substring(0, ukids.length() - 1);
        String[] idArray = ukids.split(",");
        updateSql = "update PRODDTA.FE64319H@jdedblink set THNXTR='110',THPOHP01='0',THPOHP02='N',THUSER='OA',THUPMJ=?,THDL19=? where THUKID =?";
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
        System.out.println("------成功修改请修OS单的读取状态--------------");
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
  
  public List<Map<String, String>> getFieldvalueForOSmx() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select PDASID,PDDL04,PDDL01,PDACL2,PDDS50,PDDL03,PDUOM,PDUORG,PDVR01,CRPDTA.NTOD(PDDRQJ) PDDRQJ,PDURAT,PDUKID,PDDCTO,PDKCOO,PDDOCO,PDSFXO from PRODDTA.FE64319D@jdedblink where PDPOHP01=0 and PDKCOO='00016' and PDDCTO=?";
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
      pstmt.setString(1, "OS");
      rs = pstmt.executeQuery();
      while (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("zcbh", rs.getString("PDASID"));
        result.put("sbmc", rs.getString("PDDL04"));
        result.put("sbxh", rs.getString("PDDL01"));
        result.put("sbpj", rs.getString("PDACL2"));
        result.put("wxsx", rs.getString("PDDS50"));
        result.put("dw", rs.getString("PDDL03"));
        result.put("sl", String.valueOf(rs.getLong("PDUORG") / 10000.0D));
        result.put("yqrq", rs.getString("PDDRQJ").substring(0, 10));
        result.put("bz", rs.getString("PDVR01"));
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
    List<Map<String, String>> os = getFieldValueForOS();
    List<Map<String, String>> osmx = getFieldvalueForOSmx();
    List<Map<String, String>> formBaseInfo = getBaseinfo();
    Map<String, String> info = new HashMap<String, String>();
    for (int i = 0; i < formBaseInfo.size(); i++) {
      if (((String)((Map)formBaseInfo.get(i)).get("formid")).equals("OSKS"))
        info = formBaseInfo.get(i); 
    } 
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String printTime = sdf.format(new Date());
    if (os != null && os.size() > 0 && osmx != null && osmx.size() > 0) {
      String result = "-1";
      for (Map<String, String> osmap : os) {
        String ukid = osmap.get("ukzj");
        try {
          String account = GetPersoninfo.getOaUseraccount(((String)osmap.get("sqr")).trim());
          String jsr = GetPersoninfo.getDepartmentHead(account);
          StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
          xml.append("<WorkFlow>");
          xml.append("<Process processId=\"").append(info.get("processId")).append("\"/>");
          xml.append("<UserName  submitName=\"").append(account).append("\"  receiveName=\"").append(jsr).append("\"/>");
          xml.append("<Data>");
          xml.append("<Table tableId=\"").append(info.get("tableId")).append("\" tableName=\"").append(info.get("tableName")).append("\">");
          xml.append("<Column>");
          xml.append("<field name=\"djbh\" type=\"varchar\">").append(((String)osmap.get("djbh")).trim()).append("</field>");
          xml.append("<field name=\"ddrq\" type=\"varchar\">").append(((String)osmap.get("ddrq")).trim()).append("</field>");
          xml.append("<field name=\"sqbm\" type=\"varchar\">").append(((String)osmap.get("sqbm")).trim()).append("</field>");
          xml.append("<field name=\"sqr\" type=\"varchar\">").append(((String)osmap.get("sqr")).trim()).append("</field>");
          xml.append("<field name=\"bz\" type=\"varchar\">").append(((String)osmap.get("bz")).trim()).append("</field>");
          xml.append("<field name=\"dysj\" type=\"varchar\">").append(printTime).append("</field>");
          xml.append("<field name=\"ukzj\" type=\"varchar\">").append(((String)osmap.get("ukzj")).trim()).append("</field>");
          xml.append("<field name=\"ddlx\" type=\"varchar\">").append(((String)osmap.get("ddlx")).trim()).append("</field>");
          xml.append("<field name=\"ddgs\" type=\"varchar\">").append(((String)osmap.get("ddgs")).trim()).append("</field>");
          xml.append("<field name=\"ddh\" type=\"varchar\">").append(((String)osmap.get("ddh")).trim()).append("</field>");
          xml.append("<field name=\"ddhz\" type=\"varchar\">").append(((String)osmap.get("ddhz")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</Table>");
          int num = 0;
          for (Map<String, String> mxmap : osmx) {
            if (((String)osmap.get("ukzj")).equals(mxmap.get("ukzj")) && ((String)osmap.get("ddlx")).equals(mxmap.get("ddlx")) && ((String)osmap.get("ddgs")).equals(mxmap.get("ddgs")) && ((String)osmap.get("ddh")).equals(mxmap.get("ddh")) && ((String)osmap.get("ddhz")).equals(mxmap.get("ddhz"))) {
              num++;
              xml.append("<SubTable tableName=\"").append(info.get("subTableName")).append("\" type=\"").append(String.valueOf(num) + "\">");
              xml.append("<Column>");
              xml.append("<field name=\"zcbh\" type=\"varchar\">").append(((String)mxmap.get("zcbh")).trim()).append("</field>");
              xml.append("<field name=\"sbmc\" type=\"varchar\">").append(((String)mxmap.get("sbmc")).trim()).append("</field>");
              xml.append("<field name=\"sbxh\" type=\"varchar\">").append(((String)mxmap.get("sbxh")).trim()).append("</field>");
              xml.append("<field name=\"sbpj\" type=\"varchar\">").append(((String)mxmap.get("sbpj")).trim()).append("</field>");
              xml.append("<field name=\"wxsx\" type=\"varchar\">").append(((String)mxmap.get("wxsx")).trim()).append("</field>");
              xml.append("<field name=\"dw\" type=\"varchar\">").append(((String)mxmap.get("dw")).trim()).append("</field>");
              xml.append("<field name=\"sl\" type=\"varchar\">").append(((String)mxmap.get("sl")).trim()).append("</field>");
              xml.append("<field name=\"yqrq\" type=\"varchar\">").append(((String)mxmap.get("yqrq")).trim()).append("</field>");
              xml.append("<field name=\"bz\" type=\"varchar\">").append(((String)mxmap.get("bz")).trim()).append("</field>");
              xml.append("<field name=\"ukzj\" type=\"varchar\">").append(((String)mxmap.get("ukzj")).trim()).append("</field>");
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
          System.out.println("时间：[" + printTime + "]昆山-创建请修单OS成功！--单号是：" + (String)osmap.get("ddh"));
          continue;
        } 
        if (result.equals("-1")) {
          System.out.println("时间：[" + printTime + "]昆山-创建请修单OS失败！--单号是：" + (String)osmap.get("ddh"));
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
        if (((String)nodeMap.get("formid")).equals("OSKS")) {
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
          nodeMap.put("subTableName", node.getAttributeValue("subTableName"));
          infos.add(nodeMap);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return infos;
  }
}
