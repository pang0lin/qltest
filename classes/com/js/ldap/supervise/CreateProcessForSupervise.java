package com.js.ldap.supervise;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class CreateProcessForSupervise {
  public static String dataCombination(List<Map<String, String>> list, String type, String userAccount) {
    String result = "";
    int cnt = 0;
    Map<String, String> baseInfoMap = getBaseinfo(type);
    String databaseType = SystemCommon.getDatabaseType();
    try {
      for (int i = list.size() - 1; i >= 0; i--) {
        Map<String, String> map = list.get(i);
        StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        xml.append("<WorkFlow>");
        xml.append("<Process processId=\"").append(baseInfoMap.get("processId")).append("\"/>");
        xml.append("<UserName submitName=\"").append(userAccount).append("\"  receiveName=\"").append(userAccount).append("\"/>");
        xml.append("<Data>");
        xml.append("<Table tableId=\"").append(baseInfoMap.get("tableId")).append("\" tableName=\"").append(baseInfoMap.get("tableName")).append("\">");
        xml.append("<Column>");
        if ("1".equals(type)) {
          if (databaseType.indexOf("mysql") >= 0) {
            xml.append("<field name=\"month\" type=\"varchar\">").append(((String)map.get("dbyf")).trim()).append("</field>");
            xml.append("<field name=\"gzrw\" type=\"varchar\">").append(((String)map.get("gzrw")).trim()).append("</field>");
            xml.append("<field name=\"zrld\" type=\"varchar\">").append(((String)map.get("zrld")).trim()).append("</field>");
            xml.append("<field name=\"zrbm\" type=\"varchar\">").append(((String)map.get("zrbm")).trim()).append("</field>");
            xml.append("<field name=\"jzqk\" type=\"varchar\">").append(((String)map.get("wcqk")).trim()).append("</field>");
            xml.append("<field name=\"jysm\" type=\"varchar\">").append(((String)map.get("jysm")).trim()).append("</field>");
            xml.append("<field name=\"xh\" type=\"varchar\">").append(Integer.parseInt(map.get("xh"))).append("</field>");
          } else if (databaseType.indexOf("oracle") >= 0) {
            xml.append("<field name=\"month\" type=\"varchar2\">").append(((String)map.get("dbyf")).trim()).append("</field>");
            xml.append("<field name=\"gzrw\" type=\"varchar2\">").append(((String)map.get("gzrw")).trim()).append("</field>");
            xml.append("<field name=\"zrld\" type=\"varchar2\">").append(((String)map.get("zrld")).trim()).append("</field>");
            xml.append("<field name=\"zrbm\" type=\"varchar2\">").append(((String)map.get("zrbm")).trim()).append("</field>");
            xml.append("<field name=\"jzqk\" type=\"varchar2\">").append(((String)map.get("wcqk")).trim()).append("</field>");
            xml.append("<field name=\"jysm\" type=\"varchar2\">").append(((String)map.get("jysm")).trim()).append("</field>");
            xml.append("<field name=\"xh\" type=\"varchar2\">").append(Integer.parseInt(map.get("xh"))).append("</field>");
          } 
        } else if ("2".equals(type)) {
          if (databaseType.indexOf("mysql") >= 0) {
            xml.append("<field name=\"year\" type=\"varchar\">").append(((String)map.get("dbnf")).trim()).append("</field>");
            xml.append("<field name=\"gzrw\" type=\"varchar\">").append(((String)map.get("gzrw")).trim()).append("</field>");
            xml.append("<field name=\"fzld\" type=\"varchar\">").append(((String)map.get("fzld")).trim()).append("</field>");
            xml.append("<field name=\"zrdw\" type=\"varchar\">").append(((String)map.get("zrdw")).trim()).append("</field>");
            xml.append("<field name=\"jzqk\" type=\"varchar\">").append(((String)map.get("jzqk")).trim()).append("</field>");
            xml.append("<field name=\"wcsj\" type=\"varchar\">").append(((String)map.get("wcsj")).trim()).append("</field>");
            xml.append("<field name=\"phdw\" type=\"varchar\">").append(((String)map.get("phdw")).trim()).append("</field>");
          } else if (databaseType.indexOf("oracle") >= 0) {
            xml.append("<field name=\"year\" type=\"varchar2\">").append(((String)map.get("dbnf")).trim()).append("</field>");
            xml.append("<field name=\"gzrw\" type=\"varchar2\">").append(((String)map.get("gzrw")).trim()).append("</field>");
            xml.append("<field name=\"fzld\" type=\"varchar2\">").append(((String)map.get("fzld")).trim()).append("</field>");
            xml.append("<field name=\"zrdw\" type=\"varchar2\">").append(((String)map.get("zrdw")).trim()).append("</field>");
            xml.append("<field name=\"jzqk\" type=\"varchar2\">").append(((String)map.get("jzqk")).trim()).append("</field>");
            xml.append("<field name=\"wcsj\" type=\"varchar2\">").append(((String)map.get("wcsj")).trim()).append("</field>");
            xml.append("<field name=\"phdw\" type=\"varchar2\">").append(((String)map.get("phdw")).trim()).append("</field>");
          } 
        } else if ("3".equals(type)) {
          if (databaseType.indexOf("mysql") >= 0) {
            xml.append("<field name=\"phdw\" type=\"varchar\">").append(((String)map.get("phdw")).trim()).append("</field>");
            xml.append("<field name=\"zrdw\" type=\"varchar\">").append(((String)map.get("zrdw")).trim()).append("</field>");
            xml.append("<field name=\"dbsx\" type=\"varchar\">").append(((String)map.get("dbsx")).trim()).append("</field>");
            xml.append("<field name=\"gzyq\" type=\"varchar\">").append(((String)map.get("gzyq")).trim()).append("</field>");
            xml.append("<field name=\"wcsx\" type=\"varchar\">").append(((String)map.get("wcsx")).trim()).append("</field>");
            xml.append("<field name=\"lsqk\" type=\"varchar\">").append(((String)map.get("lsqk")).trim()).append("</field>");
            xml.append("<field name=\"bljg\" type=\"varchar\">").append(((String)map.get("bljg")).trim()).append("</field>");
            xml.append("<field name=\"sx\" type=\"varchar\">").append(((String)map.get("sx")).trim()).append("</field>");
          } else if (databaseType.indexOf("oracle") >= 0) {
            xml.append("<field name=\"phdw\" type=\"varchar2\">").append(((String)map.get("phdw")).trim()).append("</field>");
            xml.append("<field name=\"zrdw\" type=\"varchar2\">").append(((String)map.get("zrdw")).trim()).append("</field>");
            xml.append("<field name=\"dbsx\" type=\"varchar2\">").append(((String)map.get("dbsx")).trim()).append("</field>");
            xml.append("<field name=\"gzyq\" type=\"varchar2\">").append(((String)map.get("gzyq")).trim()).append("</field>");
            xml.append("<field name=\"wcsx\" type=\"varchar2\">").append(((String)map.get("wcsx")).trim()).append("</field>");
            xml.append("<field name=\"lsqk\" type=\"varchar2\">").append(((String)map.get("lsqk")).trim()).append("</field>");
            xml.append("<field name=\"bljg\" type=\"varchar2\">").append(((String)map.get("bljg")).trim()).append("</field>");
            xml.append("<field name=\"sx\" type=\"varchar2\">").append(((String)map.get("sx")).trim()).append("</field>");
          } 
        } 
        xml.append("</Column>");
        xml.append("</Table>");
        xml.append("</Data>");
        xml.append("</WorkFlow>");
        String fk = (new ClientInfoFromWeb()).createNewProcess(xml.toString());
        if (fk.length() > 1)
          cnt++; 
      } 
      String flag = "";
      if ("1".equals(type)) {
        flag = "月度";
      } else if ("2".equals(type)) {
        flag = "年度";
      } else if ("3".equals(type)) {
        flag = "任务分解";
      } 
      result = "共获取到有效" + flag + "督办" + list.size() + "条，已成功导入" + cnt + "条，请到办理中心-接收办理中查阅。";
    } catch (Exception e) {
      String flag = "";
      if ("1".equals(type)) {
        flag = "月度";
      } else if ("2".equals(type)) {
        flag = "年度";
      } else if ("3".equals(type)) {
        flag = "任务分解";
      } 
      result = "共获取到有效" + flag + "督办" + list.size() + "条，目前已成功导入" + cnt + "条，<br>";
      result = String.valueOf(result) + "因导入出现问题造成后续的文件未成功导入，请按照模板文件格式重新整理上传。<br>";
      if (cnt > 0)
        result = String.valueOf(result) + "已成功导入的" + cnt + "条督办信息请到办理中心-接收办理中查阅。"; 
      e.printStackTrace();
    } 
    return result;
  }
  
  private static Map<String, String> getBaseinfo(String type) {
    Map<String, String> nodeMap = new HashMap<String, String>();
    FileInputStream configFileInputStream = null;
    String flowInfoFile = "/jsconfig/lzuedu/supervise.xml";
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + flowInfoFile;
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> formNodes = root.getChildren();
      for (int i = 0; i < formNodes.size(); i++) {
        Element node = formNodes.get(i);
        if ("1".equals(type) && "supervisemonth".equals(node.getAttributeValue("id"))) {
          nodeMap.put("formid", node.getAttributeValue("id"));
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
        } else if ("2".equals(type) && "superviseyear".equals(node.getAttributeValue("id"))) {
          nodeMap.put("formid", node.getAttributeValue("id"));
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
        } else if ("3".equals(type) && "supervisetask".equals(node.getAttributeValue("id"))) {
          nodeMap.put("formid", node.getAttributeValue("id"));
          nodeMap.put("processId", node.getAttributeValue("processId"));
          nodeMap.put("tableId", node.getAttributeValue("tableId"));
          nodeMap.put("tableName", node.getAttributeValue("tableName"));
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return nodeMap;
  }
  
  public static List<String[]> getYearDelaySendList() {
    List<String[]> list = (List)new ArrayList<String>();
    Map<String, String> baseMap = getBaseinfo("2");
    String processId = baseMap.get("processId");
    String sql = "select WF_ACTIVITY_ID,ACTIVITYNAME,activitydelaySend from jsf_activity where WF_WORKFLOWPROCESS_ID=? AND ACTIVITYCLASS=1";
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, processId);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        String[] temp = new String[3];
        temp[0] = rs.getString("WF_ACTIVITY_ID");
        temp[1] = rs.getString("ACTIVITYNAME");
        temp[2] = (rs.getString("activitydelaySend") == null) ? "" : rs.getString("activitydelaySend");
        list.add(temp);
      } 
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
    return list;
  }
  
  public static String saveSuperviseDelayData(String data) {
    String result = "0";
    String[] datas = data.split("\\$\\$");
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement("update jsf_activity set activitydelaySend=? where WF_ACTIVITY_ID=?");
      conn.setAutoCommit(false);
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = datas).length, b = 0; b < i; ) {
        String item = arrayOfString[b];
        String[] items = item.split("#");
        pstmt.setString(1, items[1]);
        pstmt.setString(2, items[0]);
        pstmt.addBatch();
        b++;
      } 
      pstmt.executeBatch();
      conn.commit();
      conn.setAutoCommit(true);
      pstmt.close();
      conn.close();
      result = "1";
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e1) {
          e1.printStackTrace();
        }  
      e.printStackTrace();
    } 
    return result;
  }
  
  public static void updateSuperviseDualData(String recordId) {
    Map<String, String> map = getBaseinfo("2");
    String table = map.get("tableName");
    DataSourceBase db = new DataSourceBase();
    DataSource ds = db.getDataSource();
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      String sql = "update " + table + " set jzqk='完成' where " + table + "_id =?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, recordId);
      pstmt.execute();
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
  }
}
