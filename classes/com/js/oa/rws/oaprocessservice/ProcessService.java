package com.js.oa.rws.oaprocessservice;

import com.js.oa.form.ClientInfoFromWeb;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class ProcessService {
  public String createNewProcess(String xml) {
    String result = "";
    String returnXml = "";
    String status = "1";
    String message = "数据插入失败";
    try {
      System.out.println("Xml:" + xml);
      Map map = readXML(xml);
      Map mapReal = getTableField(map);
      String xmlData = xmlData(mapReal);
      ClientInfoFromWeb cif = new ClientInfoFromWeb();
      result = cif.createNewProcess(xmlData);
      if (Long.valueOf(result).longValue() > 0L) {
        status = "0";
        message = "操作成功";
      } 
      returnXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Result><Status>" + 
        
        status + "</Status>" + 
        "<Message>" + message + "</Message>" + 
        "</Result>";
    } catch (Exception e) {
      returnXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><Result><Status>1</Status><Message>用户没有权限或系统出现异常</Message></Result>";
      e.printStackTrace();
    } 
    System.out.println("returnXml:" + returnXml);
    return returnXml;
  }
  
  private Map readXML(String xml) {
    List<String[]> fieldList = (List)new ArrayList<String>();
    List<String[]> subFieldList = (List)new ArrayList<String>();
    Map<Object, Object> map = new HashMap<Object, Object>();
    StringReader read = new StringReader(xml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    sb.setExpandEntities(false);
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      Element node = root.getChild("Process");
      map.put("processName", node.getAttributeValue("processName"));
      node = root.getChild("SubmitUser");
      if (node != null && 
        node.getAttributeValue("userName") != null)
        map.put("submitName", node.getAttributeValue("userName")); 
      node = root.getChild("tableType");
      map.put("tableType", node.getAttributeValue("typeValue"));
      String tableType = node.getAttributeValue("typeValue");
      node = root.getChild("Data");
      Element mainNode = node.getChild("mainTable");
      List<Element> list = mainNode.getChildren();
      for (int i = 0; i < list.size(); i++) {
        String[] fieldInfo = new String[2];
        Element field = list.get(i);
        Element fieldName = field.getChild("FieldName");
        Element fieldValue = field.getChild("FieldValue");
        if (fieldName != null && fieldValue != null) {
          fieldInfo[0] = fieldName.getValue();
          fieldInfo[1] = fieldValue.getValue();
          fieldList.add(fieldInfo);
        } 
      } 
      map.put("fieldList", fieldList);
      if ("tsbg".equals(tableType) || "htsp".equals(tableType)) {
        Element subNode = node.getChild("subTable");
        if (subNode != null) {
          List<Element> subList = subNode.getChildren();
          for (int j = 0; j < subList.size(); j++) {
            Element subField = subList.get(j);
            List<Element> subFeildList = subField.getChildren();
            for (int k = 0; k < subFeildList.size(); k++) {
              String[] subFieldInfo = new String[2];
              Element field = subFeildList.get(k);
              String subFieldName = field.getAttributeValue("FieldName");
              String subFieldValue = field.getText();
              subFieldInfo[0] = subFieldName;
              subFieldInfo[1] = subFieldValue;
              subFieldList.add(subFieldInfo);
            } 
          } 
          map.put("subFieldList", subFieldList);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return map;
  }
  
  private Map getTableField(Map map) {
    FileInputStream configFileInputStream = null;
    Map<Object, Object> mapList = new HashMap<Object, Object>();
    List<String[]> List = (List<String[]>)map.get("fieldList");
    String submitName = (String)map.get("submitName");
    String processName = (String)map.get("processName");
    String tableType = (String)map.get("tableType");
    List<String[]> fieldList = (List)new ArrayList<String>();
    List<String[]> subList = (List)new ArrayList<String>();
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/rwstablefield.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild(tableType);
      List<Element> list = node.getChildren();
      String bqField = "", oaField = "", fieldType = "";
      Map<String, String[]> mapArry = (Map)new HashMap<String, String>();
      for (int i = 0; i < list.size(); i++) {
        Element field = list.get(i);
        bqField = field.getAttributeValue("bqField");
        oaField = field.getAttributeValue("oaField");
        fieldType = field.getAttributeValue("fieldType");
        mapArry.put(bqField, new String[] { oaField, fieldType });
      } 
      for (int j = 0; j < List.size(); j++) {
        String[] fieldInfo1 = List.get(j);
        String fieldName = fieldInfo1[0];
        String[] fieldInfo2 = mapArry.get(fieldName);
        fieldList.add(new String[] { fieldInfo2[0], fieldInfo2[1], fieldInfo1[1] });
      } 
      mapList.put("list", fieldList);
      mapList.put("submitName", submitName);
      mapList.put("processName", processName);
      mapList.put("tableType", tableType);
      Map<String, String[]> subMap = (Map)new HashMap<String, String>();
      if ("tsbg".equals(tableType) || "htsp".equals(tableType)) {
        Element subnNode = null;
        if ("tsbg".equals(tableType))
          subnNode = root.getChild("subtable"); 
        if ("htsp".equals(tableType))
          subnNode = root.getChild("bqsubtable"); 
        if (subnNode != null) {
          List<Element> subListField = subnNode.getChildren();
          String subbqField = "", suboaField = "", subfieldType = "";
          for (int m = 0; m < subListField.size(); m++) {
            Element subField = subListField.get(m);
            subbqField = subField.getAttributeValue("bqField");
            suboaField = subField.getAttributeValue("oaField");
            subfieldType = subField.getAttributeValue("fieldType");
            subMap.put(subbqField, new String[] { suboaField, subfieldType });
          } 
          List<String[]> subFieldList = (List<String[]>)map.get("subFieldList");
          for (int k = 0; k < subFieldList.size(); k++) {
            String[] subFiledbq = subFieldList.get(k);
            String subFiledName = subFiledbq[0];
            String[] subFiled = subMap.get(subFiledName);
            subList.add(new String[] { subFiled[0], subFiled[1], subFiledbq[1] });
          } 
          mapList.put("subList", subList);
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return mapList;
  }
  
  private String xmlData(Map map) {
    List<String[]> list = (List<String[]>)map.get("list");
    String tableType = (String)map.get("tableType");
    String submitName = (String)map.get("submitName");
    String processName = (String)map.get("processName");
    String processId = "1326906";
    String tableId = "";
    String tableName = "";
    String auditstatus = "", field = "";
    String subXml = "";
    if ("tsbg".equals(tableType)) {
      processId = "2733604";
      tableName = "jst_3047";
      tableId = "1400943";
      field = "jst_3047_f4532";
    } 
    if ("htcz".equals(tableType)) {
      processId = "5517007";
      tableName = "jst_3133";
      tableId = "5555196";
      field = "jst_3133_f4529";
    } 
    if ("wqtfg".equals(tableType) && processName.indexOf("常规") > 0) {
      processId = "1921014";
      tableName = "jst_3064";
      tableId = "1920949";
      field = "jst_3064_f4526";
    } 
    if ("wqtfg".equals(tableType) && processName.indexOf("重要") > 0) {
      processId = "1326212";
      tableName = "jst_3064";
      tableId = "1326185";
      field = "jst_3064_f4526";
    } 
    if ("htsp".equals(tableType) && processName.indexOf("常规") > 0) {
      processId = "1326906";
      tableName = "jst_3069";
      tableId = "1326846";
      field = "jst_3069_f4523";
    } 
    if ("htsp".equals(tableType) && processName.indexOf("重要") > 0) {
      processId = "1326476";
      tableName = "jst_3069";
      tableId = "1326415";
      field = "jst_3069_f4523";
    } 
    auditstatus = "<field name=\"" + field + "\" type=\"varchar\">1</field>";
    StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    xml.append("<WorkFlow>");
    xml.append("<Process processId=\"" + processId + "\" />");
    xml.append("<UserName submitName=\"" + submitName + "\" receiveName=\"\" />");
    xml.append("<Data>");
    xml.append("<Table tableId=\"" + tableId + "\" tableName=\"" + tableName + "\">");
    xml.append("<Column>");
    for (int i = 0; i < list.size(); i++) {
      String[] fieldInfo = list.get(i);
      xml.append("<field name=\"" + fieldInfo[0] + "\" type=\"" + fieldInfo[1] + "\">" + fieldInfo[2] + "</field>");
    } 
    xml.append(auditstatus);
    xml.append("</Column>");
    xml.append("</Table>");
    String tablename = "jst_3085";
    String fieldName = "", fieldtype = "", fieldValue = "";
    List<String[]> subList = (List<String[]>)map.get("subList");
    if ("tsbg".equals(tableType) && subList != null)
      for (int j = 0, k = 0; j < subList.size(); j += 2) {
        String[] subField1 = subList.get(j);
        String[] subField2 = subList.get(j + 1);
        xml.append("<SubTable tableName=\"" + tablename + "\" type=\"" + k++ + "\">");
        xml.append("<Column>");
        xml.append("<field name=\"" + subField1[0] + "\" type=\"" + subField1[1] + "\">" + subField1[2] + "</field>");
        xml.append("<field name=\"" + subField2[0] + "\" type=\"" + subField2[1] + "\">" + subField2[2] + "</field>");
        xml.append("</Column>");
        xml.append("</SubTable>");
      }  
    if ("htsp".equals(tableType) && subList != null) {
      tablename = "jst_3137";
      for (int j = 0, k = 0; j < subList.size(); j += 7) {
        String[] subField1 = subList.get(j);
        String[] subField2 = subList.get(j + 1);
        String[] subField3 = subList.get(j + 2);
        String[] subField4 = subList.get(j + 3);
        String[] subField5 = subList.get(j + 4);
        String[] subField6 = subList.get(j + 5);
        String[] subField7 = subList.get(j + 6);
        xml.append("<SubTable tableName=\"" + tablename + "\" type=\"" + k++ + "\">");
        xml.append("<Column>");
        xml.append("<field name=\"" + subField1[0] + "\" type=\"" + subField1[1] + "\">" + subField1[2] + "</field>");
        xml.append("<field name=\"" + subField2[0] + "\" type=\"" + subField2[1] + "\">" + subField2[2] + "</field>");
        xml.append("<field name=\"" + subField3[0] + "\" type=\"" + subField3[1] + "\">" + subField3[2] + "</field>");
        xml.append("<field name=\"" + subField4[0] + "\" type=\"" + subField4[1] + "\">" + subField4[2] + "</field>");
        xml.append("<field name=\"" + subField5[0] + "\" type=\"" + subField5[1] + "\">" + subField5[2] + "</field>");
        xml.append("<field name=\"" + subField6[0] + "\" type=\"" + subField6[1] + "\">" + subField6[2] + "</field>");
        xml.append("<field name=\"" + subField7[0] + "\" type=\"" + subField7[1] + "\">" + subField7[2] + "</field>");
        xml.append("</Column>");
        xml.append("</SubTable>");
      } 
    } 
    xml.append("</Data>");
    xml.append("</WorkFlow>");
    System.out.println("xml:" + xml);
    return xml.toString();
  }
  
  public String getEmpId(String useraccount) {
    String sql = "select emp_id from org_employee where useraccounts=?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String empid = "";
    DataSourceBase base = new DataSourceBase();
    try {
      conn = base.getDataSource().getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, useraccount);
      rs = pstmt.executeQuery();
      while (rs.next())
        empid = rs.getString(1); 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (pstmt != null)
          pstmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return empid;
  }
}
