package com.js.oa.form.pengchi;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class CreateProcessForPdcy {
  public static final String sourceName = "jdbc/pengchi";
  
  private static final String jdeConfigFileName = "/jsconfig/jde.xml";
  
  public List<Map<String, String>> getFieldValueForPdcy() {
    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
    String sql = "select PICYNO,CRPDTA.NTOD(PICSDJ) PICSDJ,PIDSC1,PJLITM,IMDSC1,IMDSC2,IMSRP2,DRDL01,MCDL01,NVL(PJTQOH,0)-NVL(PJTQCT,0) CYSL from PRODDTA.F4140@jdedblink ,PRODDTA.F4141@jdedblink,PRODDTA.F4101@jdedblink, PRODDTA.F0006@jdedblink,PRODCTL.F0005@jdedblink  where PICYCS='20' AND PICYNO=PJCYNO and NVL(PJTQOH,0)-NVL(PJTQCT,0) <>0 AND F4101.IMLITM=F4141.PJLITM AND DRSY='41' AND DRKY=IMSRP2  AND DRRT='S2' AND MCMCU=PJMCU";
    DataSource ds = (new DataSourceBase()).getDataSource("jdbc/pengchi");
    Connection conn = null;
    try {
      conn = ds.getConnection();
    } catch (SQLException e1) {
      e1.printStackTrace();
    } 
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String applyTime = sdf.format(new Date());
    try {
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        Map<String, String> result = new HashMap<String, String>();
        result.put("bh", rs.getString("PICYNO"));
        result.put("sqrq", applyTime);
        result.put("fbcs", rs.getString("MCDL01"));
        result.put("pdsj", rs.getString("PICSDJ").substring(0, 10));
        result.put("pdsm", rs.getString("PIDSC1"));
        result.put("pdlx", "");
        result.put("zdr", "");
        result.put("hl", rs.getString("DRDL01"));
        result.put("lh", rs.getString("PJLITM"));
        result.put("pm", rs.getString("IMDSC1"));
        result.put("gg", rs.getString("IMDSC2"));
        result.put("cysl", rs.getString("CYSL"));
        result.put("bz", "");
        datas.add(result);
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
    return datas;
  }
  
  public void startProcess() {
    List<Map<String, String>> ob = getFieldValueForPdcy();
    List<Map<String, String>> formBaseInfo = getBaseinfo();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String printTime = sdf.format(new Date());
    Map<String, String> info = new HashMap<String, String>();
    for (int i = 0; i < formBaseInfo.size(); i++) {
      if (((String)((Map)formBaseInfo.get(i)).get("formid")).equals("PDCY"))
        info = formBaseInfo.get(i); 
    } 
    if (ob != null && ob.size() > 0) {
      StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      String result = "-1";
      int num = 0;
      try {
        for (Map<String, String> obmap : ob) {
          if (num == 0) {
            xml.append("<WorkFlow>");
            xml.append("<Process processId=\"").append(info.get("processId")).append("\"/>");
            xml.append("<UserName  submitName=\"").append(info.get("submitName")).append("\"  receiveName=\"").append(info.get("receiveName")).append("\"/>");
            xml.append("<Data>");
            xml.append("<Table tableId=\"").append(info.get("tableId")).append("\" tableName=\"").append(info.get("tableName")).append("\">");
            xml.append("<Column>");
            xml.append("<field name=\"bh\" type=\"varchar\">").append(((String)obmap.get("bh")).trim()).append("</field>");
            xml.append("<field name=\"sqrq\" type=\"varchar\">").append(printTime).append("</field>");
            xml.append("<field name=\"fbcs\" type=\"varchar\">").append(((String)obmap.get("fbcs")).trim()).append("</field>");
            xml.append("<field name=\"pdsj\" type=\"varchar\">").append(((String)obmap.get("pdsj")).trim()).append("</field>");
            xml.append("<field name=\"pdlx\" type=\"varchar\">").append(((String)obmap.get("pdlx")).trim()).append("</field>");
            xml.append("<field name=\"pdsm\" type=\"varchar\">").append(((String)obmap.get("pdsm")).trim()).append("</field>");
            xml.append("<field name=\"zdr\" type=\"varchar\">").append(((String)obmap.get("zdr")).trim()).append("</field>");
            xml.append("</Column>");
            xml.append("</Table>");
          } 
          num++;
          xml.append("<SubTable tableName=\"").append(info.get("subTableName")).append("\" type=\"").append(String.valueOf(num) + "\">");
          xml.append("<Column>");
          xml.append("<field name=\"hl\" type=\"varchar\">").append(((String)obmap.get("hl")).trim()).append("</field>");
          xml.append("<field name=\"lh\" type=\"varchar\">").append(((String)obmap.get("lh")).trim()).append("</field>");
          xml.append("<field name=\"pm\" type=\"varchar\">").append(((String)obmap.get("pm")).trim()).append("</field>");
          xml.append("<field name=\"gg\" type=\"varchar\">").append(((String)obmap.get("gg")).trim()).append("</field>");
          xml.append("<field name=\"cysl\" type=\"varchar\">").append(((String)obmap.get("cysl")).trim()).append("</field>");
          xml.append("<field name=\"bz\" type=\"varchar\">").append(((String)obmap.get("bz")).trim()).append("</field>");
          xml.append("</Column>");
          xml.append("</SubTable>");
        } 
        xml.append("</Data>");
        xml.append("</WorkFlow>");
        result = (new ClientInfoFromWeb()).createNewProcess(xml.toString());
      } catch (Exception e) {
        e.printStackTrace();
      } 
      if (!result.equals("-1") && result.length() < 10) {
        System.out.println("创建盘点差异单成功！");
      } else if (result.equals("-1")) {
        System.out.println("创建盘点差异单失败！");
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
