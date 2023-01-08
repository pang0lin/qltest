package com.js.oa.form.bob;

import com.js.oa.form.Workflow;
import com.js.util.util.DataSourceBase;
import com.js.util.util.IO2File;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class ShglUpdateWorkFlow extends Workflow {
  private Map<String, String[]> map = null;
  
  public String complete(HttpServletRequest request) {
    String result = super.complete(request);
    if ("success".equals(result)) {
      if (this.map == null)
        this.map = getTable(); 
      String processId = request.getParameter("processId");
      if (this.map.get(processId) != null) {
        String[] update = this.map.get(processId);
        String xiugaiMain = update[0];
        String xiugaiSub = update[2];
        String addMain = update[1];
        String addSub = update[3];
        String recordId = request.getParameter("recordId");
        Map<String, Map<String, String>> fieldMap = getTableField();
        Map<String, String> mainMap = fieldMap.get(xiugaiMain);
        String dataSql = "select bank_ID";
        for (String key : mainMap.keySet())
          dataSql = String.valueOf(dataSql) + "," + key; 
        dataSql = String.valueOf(dataSql) + " from " + xiugaiMain + " where " + xiugaiMain + "_id=" + recordId;
        DataSourceBase base = new DataSourceBase();
        String bank_ID = "";
        try {
          base.begin();
          String updateSql = "";
          ResultSet rs = base.executeQuery(dataSql);
          if (rs.next()) {
            bank_ID = (rs.getString("bank_ID") == null) ? "" : rs.getString("bank_ID");
            if (bank_ID.contains("@@$@@"))
              bank_ID = bank_ID.split("\\@\\@\\$\\@\\@")[1]; 
            if (!bank_ID.equals("")) {
              updateSql = "update " + addMain + " set bank_ID='" + bank_ID + "'";
              for (String key : mainMap.keySet())
                updateSql = String.valueOf(updateSql) + "," + (String)mainMap.get(key) + "='" + ((rs.getString(key) == null) ? "" : rs.getString(key)) + "'"; 
              updateSql = String.valueOf(updateSql) + " where bank_ID='" + bank_ID + "'";
            } 
          } 
          rs.close();
          if (!"".equals(updateSql)) {
            IO2File.printFile("北京银行特惠商户修改sql：" + updateSql, "特惠商户", 3);
            base.executeUpdate(updateSql);
          } 
          Long bank_thsh_id = Long.valueOf(0L);
          dataSql = "select bank_thsh_id from " + addMain + " where bank_ID='" + bank_ID + "'";
          IO2File.printFile("获得recordId：" + dataSql, "特惠商户", 3);
          rs = base.executeQuery(dataSql);
          if (rs.next())
            bank_thsh_id = Long.valueOf(rs.getLong(1)); 
          rs.close();
          Map<String, String> subMap = fieldMap.get(xiugaiSub);
          dataSql = "select " + xiugaiSub + "_owner," + xiugaiSub + "_date," + xiugaiSub + "_org," + xiugaiSub + "_foreignkey";
          for (String key : subMap.keySet())
            dataSql = String.valueOf(dataSql) + "," + key; 
          dataSql = String.valueOf(dataSql) + " from " + xiugaiSub + " where " + xiugaiSub + "_foreignkey=" + recordId;
          IO2File.printFile("删除子表中数据：delete from " + addSub + " where bank_thshxz_zb_foreignkey=" + bank_thsh_id, "特惠商户", 3);
          base.executeSQL("delete from " + addSub + " where bank_thshxz_zb_foreignkey=" + bank_thsh_id);
          List<String> sqlList = new ArrayList<String>();
          rs = base.executeQuery(dataSql);
          while (rs.next()) {
            String field = "insert into " + addSub + " (bank_thshxz_zb_id,bank_thshxz_zb_owner,bank_thshxz_zb_date,bank_thshxz_zb_org," + 
              "bank_thshxz_zb_foreignkey";
            String value = ") values(hibernate_sequence.nextval," + ((rs.getString(xiugaiSub + "_owner") == null) ? "" : rs.getString(xiugaiSub + "_owner")) + "," + 
              "sysdate," + ((rs.getString(xiugaiSub + "_org") == null) ? "" : rs.getString(xiugaiSub + "_org")) + "," + bank_thsh_id;
            for (String key : subMap.keySet()) {
              field = String.valueOf(field) + "," + (String)subMap.get(key);
              value = String.valueOf(value) + ",'" + ((rs.getString(key) == null) ? "" : rs.getString(key)) + "'";
            } 
            sqlList.add(String.valueOf(field) + value + ")");
          } 
          rs.close();
          for (int i = 0; i < sqlList.size(); i++) {
            IO2File.printFile("北京银行特惠商户子表修改sql：" + (String)sqlList.get(i), "特惠商户", 3);
            base.addBatch(sqlList.get(i));
          } 
          base.executeBatch();
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          base.end();
        } 
      } 
    } 
    return result;
  }
  
  private Map<String, String[]> getTable() {
    Map<String, String[]> map = (Map)new HashMap<String, String>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/bjyhtehui.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("fields");
      Element zonghang = node.getChild("zonghang");
      Element zMain = zonghang.getChild("main");
      Element zSub = zonghang.getChild("sub");
      map.put(zonghang.getAttributeValue("processId"), 
          new String[] { zMain.getAttributeValue("mainTableName"), 
            zMain.getAttributeValue("addMainTableName"), 
            zSub.getAttributeValue("subTableName"), 
            zSub.getAttributeValue("addSubTableName") });
      Element fenhang = node.getChild("fenhang");
      Element fMain = fenhang.getChild("main");
      Element fSub = fenhang.getChild("sub");
      map.put(fenhang.getAttributeValue("processId"), 
          new String[] { fMain.getAttributeValue("mainTableName"), 
            fMain.getAttributeValue("addMainTableName"), 
            fSub.getAttributeValue("subTableName"), 
            fSub.getAttributeValue("addSubTableName") });
      configFileInputStream.close();
    } catch (Exception ex) {
      System.out.println("请配置北京银行特惠商户数据库信息   /jsconfig/bjyhtehui.xml。");
      ex.printStackTrace();
    } 
    return map;
  }
  
  private Map<String, Map<String, String>> getTableField() {
    Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/bjyhtehui.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("fields");
      Element zonghang = node.getChild("zonghang");
      Element zMain = zonghang.getChild("main");
      List<Element> zmElement = zMain.getChildren();
      Map<String, String> zmMap = new HashMap<String, String>();
      for (int i = 0; i < zmElement.size(); i++) {
        Element element = zmElement.get(i);
        zmMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(zMain.getAttributeValue("mainTableName"), zmMap);
      Element zSub = zonghang.getChild("sub");
      List<Element> zsElement = zSub.getChildren();
      Map<String, String> zsMap = new HashMap<String, String>();
      for (int j = 0; j < zsElement.size(); j++) {
        Element element = zsElement.get(j);
        zsMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(zSub.getAttributeValue("subTableName"), zsMap);
      Element fenhang = node.getChild("fenhang");
      Element fMain = fenhang.getChild("main");
      List<Element> fmElement = fMain.getChildren();
      Map<String, String> fmMap = new HashMap<String, String>();
      for (int k = 0; k < fmElement.size(); k++) {
        Element element = fmElement.get(k);
        fmMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(fMain.getAttributeValue("mainTableName"), fmMap);
      Element fSub = fenhang.getChild("sub");
      List<Element> fsElement = fSub.getChildren();
      Map<String, String> fsMap = new HashMap<String, String>();
      for (int m = 0; m < fsElement.size(); m++) {
        Element element = fsElement.get(m);
        fsMap.put(element.getAttributeValue("updateTable"), element.getAttributeValue("addTable"));
      } 
      map.put(fSub.getAttributeValue("subTableName"), fsMap);
      configFileInputStream.close();
    } catch (Exception ex) {
      System.out.println("请配置北京银行特惠商户数据库信息   /jsconfig/bjyhtehui.xml。");
      ex.printStackTrace();
    } 
    return map;
  }
}
