package com.js.util.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class TranDataFromOtherDataBaseToOA {
  public static TranDataBaseConfig dataConfig = null;
  
  public boolean getDataFromOtherDataBase() {
    readConfig();
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date nowTime = new Date();
    System.out.println("数据同步开始：" + ymdhms.format(nowTime));
    long begingLong = nowTime.getTime();
    int onceNum = 1000, allNum = 0;
    boolean flag = false;
    List<String[]> list = (List)new ArrayList<String>();
    if (dataConfig.getUse().equals("1")) {
      String sql = "select ";
      List<String[]> fieldList = dataConfig.getFieldList();
      int columnCount = fieldList.size();
      for (String[] field : fieldList) {
        if (!field[0].equals("")) {
          sql = String.valueOf(sql) + field[0] + ",";
          continue;
        } 
        sql = String.valueOf(sql) + "'',";
      } 
      sql = sql.substring(0, sql.length() - 1);
      sql = String.valueOf(sql) + " from " + dataConfig.getFromTable();
      sql = String.valueOf(sql) + " " + replaceString(dataConfig.getWhere());
      sql = String.valueOf(sql) + " " + dataConfig.getOrderBy();
      System.out.println("取值sql：" + sql);
      DataSourceBase base = new DataSourceBase();
      Connection conn = null;
      int num = 0;
      try {
        if (dataConfig.getFromBaseName().equals("system")) {
          conn = base.getDataSource().getConnection();
        } else {
          conn = base.getDataSource(dataConfig.getFromBaseName()).getConnection();
        } 
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while (rs.next()) {
          String[] rsValue = new String[columnCount];
          for (int i = 0; i < columnCount; i++)
            rsValue[i] = (new StringBuilder(String.valueOf(rs.getString(i + 1)))).toString(); 
          allNum++;
          list.add(rsValue);
          if ((list.size() + 1) % onceNum == 0) {
            insertIntoOADataBase(list, num);
            System.out.println("同步第" + (num * onceNum + 1) + "条到第" + ((num + 1) * onceNum) + "条");
            num++;
            list.clear();
          } 
        } 
        insertIntoOADataBase(list, num);
        System.out.println("同步第" + (num * onceNum) + "条到第" + allNum + "条");
        list.clear();
        rs.close();
        flag = true;
      } catch (Exception e) {
        flag = false;
        e.printStackTrace();
      } finally {
        try {
          if (conn != null)
            conn.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
      System.out.println("共同步" + allNum + "条数据。耗时：" + (((new Date()).getTime() - begingLong) / 1000L) + "秒");
    } 
    return flag;
  }
  
  public boolean insertIntoOADataBase(List<String[]> dataList, int num) {
    boolean flag = false;
    if (dataConfig.getUse().equals("1") && dataList.size() > 0) {
      String sql = "insert into " + dataConfig.getToTable() + " (";
      List<String[]> fieldList = dataConfig.getFieldList();
      for (String[] field : fieldList) {
        if (!field[1].equals(""))
          sql = String.valueOf(sql) + field[1] + ","; 
      } 
      sql = String.valueOf(sql.substring(0, sql.length() - 1)) + ") values (";
      DataSourceBase base = new DataSourceBase();
      Connection conn = null;
      try {
        if (dataConfig.getToBaseName().equals("system")) {
          conn = base.getDataSource().getConnection();
        } else {
          conn = base.getDataSource(dataConfig.getToBaseName()).getConnection();
        } 
        Statement stat = conn.createStatement();
        if (num == 0)
          stat.executeUpdate("delete from " + dataConfig.getToTable()); 
        for (int d = 0; d < dataList.size(); d++) {
          String executeSql = sql;
          String[] dataSingle = dataList.get(d);
          for (int i = 0; i < fieldList.size(); i++) {
            String[] fieldInfo = fieldList.get(i);
            if (",number,int,float,bigint,double,".contains(fieldInfo[2].toLowerCase())) {
              if ("".equals(fieldInfo[3])) {
                executeSql = String.valueOf(executeSql) + dataSingle[i] + ",";
              } else {
                executeSql = String.valueOf(executeSql) + fieldInfo[3] + ",";
              } 
            } else if (",varchar,varchar2,".contains(fieldInfo[2].toLowerCase())) {
              if ("".equals(fieldInfo[3])) {
                executeSql = String.valueOf(executeSql) + "'" + dataSingle[i] + "',";
              } else {
                executeSql = String.valueOf(executeSql) + fieldInfo[3] + ",";
              } 
            } else if ("mysql".equalsIgnoreCase(dataConfig.getToBaseType())) {
              if ("".equals(fieldInfo[3])) {
                executeSql = String.valueOf(executeSql) + "'" + dataSingle[i] + "',";
              } else {
                executeSql = String.valueOf(executeSql) + fieldInfo[3] + ",";
              } 
            } else if ("".equals(fieldInfo[3])) {
              executeSql = String.valueOf(executeSql) + "to_date('" + dataSingle[i] + "','yyyy-mm-dd hh24:mi:ss'),";
            } else {
              executeSql = String.valueOf(executeSql) + fieldInfo[3] + ",";
            } 
          } 
          stat.addBatch(String.valueOf(executeSql.substring(0, executeSql.length() - 1)) + ")");
          if (d == 0)
            System.out.println("同步sql实例：" + executeSql.substring(0, executeSql.length() - 1) + ")"); 
        } 
        stat.executeBatch();
        stat.close();
        flag = true;
      } catch (Exception e) {
        flag = false;
        e.printStackTrace();
      } finally {
        dataList.clear();
        try {
          if (conn != null)
            conn.close(); 
        } catch (SQLException e) {
          e.printStackTrace();
        } 
      } 
    } 
    if (dataList.size() == 0)
      flag = false; 
    return flag;
  }
  
  public String replaceString(String param) {
    try {
      String[] paras = param.split("\\@\\@");
      for (int z = 1; z < paras.length; z += 2) {
        String pageValue = "";
        if (paras[z].startsWith("*")) {
          if ("*year".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getYear() + 1900))).toString();
          } else if ("*month".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getMonth() + 1))).toString();
          } else if ("*day".equalsIgnoreCase(paras[z])) {
            pageValue = (new StringBuilder(String.valueOf((new Date()).getDate()))).toString();
          } else if (paras[z].startsWith("*format")) {
            SimpleDateFormat format = new SimpleDateFormat(paras[z].substring(7));
            pageValue = format.format(new Date());
          } else {
            pageValue = paras[z].substring(1);
          } 
        } else {
          pageValue = paras[z];
        } 
        param = param.replace("@@" + paras[z] + "@@", pageValue);
      } 
    } catch (Exception e) {
      System.out.println("请检查参数格式：" + param);
    } 
    return param;
  }
  
  public TranDataBaseConfig readConfig() {
    if (dataConfig == null) {
      dataConfig = new TranDataBaseConfig();
      FileInputStream configFileInputStream = null;
      try {
        String path = System.getProperty("user.dir");
        String configFile = String.valueOf(path) + "/jsconfig/tranData.xml";
        configFileInputStream = new FileInputStream(new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(configFileInputStream);
        Element root = doc.getRootElement();
        dataConfig.setUse(root.getAttributeValue("use"));
        dataConfig.setTranTime(root.getChildText("tranTime"));
        Element node = root.getChild("dataSet");
        dataConfig.setFromBaseName(node.getAttributeValue("fromBaseName"));
        dataConfig.setToBaseName(node.getAttributeValue("toBaseName"));
        dataConfig.setToBaseType(node.getAttributeValue("toBaseType"));
        dataConfig.setFromTable(node.getChildText("fromTable"));
        dataConfig.setToTable(node.getChildText("toTable"));
        dataConfig.setWhere(node.getChildText("where"));
        dataConfig.setOrderBy(node.getChildText("orderBy"));
        List<String[]> fieldList = (List)new ArrayList<String>();
        List<Element> fieldEle = node.getChildren("field");
        for (int i = 0; i < fieldEle.size(); i++) {
          Element fieldnode = fieldEle.get(i);
          String[] field = { fieldnode.getChildText("from"), 
              fieldnode.getChildText("to"), 
              fieldnode.getChildText("toType"), 
              (fieldnode.getChild("to").getAttribute("default") == null) ? "" : 
              fieldnode.getChild("to").getAttributeValue("default") };
          fieldList.add(field);
        } 
        dataConfig.setFieldList(fieldList);
      } catch (FileNotFoundException e) {
        System.out.println("是否配置数据同步，如果配置，请检查 /bin/jsconfig/tranData.xml 是否存在！");
        dataConfig.setUse("0");
      } catch (Exception e) {
        System.out.println("请检查 /bin/jsconfig/tranData.xml 内容是否正确！");
      } 
      return dataConfig;
    } 
    return dataConfig;
  }
}
