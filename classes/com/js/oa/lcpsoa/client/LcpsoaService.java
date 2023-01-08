package com.js.oa.lcpsoa.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class LcpsoaService {
  private static LcpsoaService instance;
  
  public static LcpsoaService getInstance() {
    if (instance == null) {
      init();
      instance = new LcpsoaService();
    } 
    return instance;
  }
  
  public static void init() {
    String driverName = "";
    String dbUser = "";
    String password = "";
    String dbUrl = "";
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element node = doc.getRootElement().getChild("LcpsoaDatabase");
      driverName = node.getChild("driverName").getAttribute("value").getValue();
      dbUser = node.getChild("dbUser").getAttribute("value").getValue();
      password = node.getChild("password").getAttribute("value").getValue();
      dbUrl = node.getChild("dbUrl").getAttribute("value").getValue();
    } catch (Exception e) {
      System.out.println("请检查一下bin\\jsconfig下的sysconfig.xml是否缺少LcpsoaDatabase");
      e.printStackTrace();
    } 
    doMethod(driverName, dbUser, password, dbUrl);
  }
  
  public static Connection getConnection(String driverName, String dbUser, String password, String dbUrl) {
    try {
      Class.forName(driverName);
      Connection conn = DriverManager.getConnection(dbUrl, dbUser, password);
      System.out.println("数据库已连接！");
      return conn;
    } catch (Exception e) {
      System.out.println("获取数据库中间表【lcpsoa】连接参数是出现异常！");
      e.printStackTrace();
      return null;
    } 
  }
  
  public static void doMethod(String driverName, String dbUser, String password, String dbUrl) {
    Connection con = getConnection(driverName, dbUser, password, dbUrl);
    String sql = "select * from lcpsoa where f_flag=0";
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = con.createStatement();
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("获取数据库中间表【lcpsoa】连接创建Statement出现异常！");
    } 
    try {
      rs = stmt.executeQuery(sql);
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("从中间表【lcpsoa】获取数据时出现异常！");
    } 
    LcpsoaClient lcpsoaClient = new LcpsoaClient();
    List<Object[]> list = new ArrayList();
    try {
      while (rs.next()) {
        Object[] obj = new Object[8];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        obj[5] = rs.getString(6);
        obj[6] = rs.getString(7);
        obj[7] = rs.getString(8);
        list.add(obj);
      } 
      String sqlUpdateFlag = "";
      boolean flag = false;
      for (int j = 0; j < list.size(); j++) {
        Object[] obj = list.get(j);
        if ("BM".equals(obj[0].toString().toUpperCase())) {
          if ("M".equals(obj[3].toString().toUpperCase())) {
            try {
              flag = lcpsoaClient.updateIndexOrg(obj);
            } catch (Exception e) {
              e.printStackTrace();
              System.out.println("部门信息修改时出现相应的错误！！！");
            } 
          } else if ("D".equals(obj[3].toString().toUpperCase())) {
            try {
              flag = lcpsoaClient.deleteIndexOrg(obj[2].toString());
            } catch (Exception e) {
              e.printStackTrace();
              System.out.println("部门信息删除时出现相应的错误！！！");
            } 
          } 
        } else if ("RY".equals(obj[0].toString().toUpperCase())) {
          if ("M".equals(obj[3].toString().toUpperCase())) {
            flag = lcpsoaClient.updateIndexUser(obj);
          } else if ("D".equals(obj[3].toString().toUpperCase())) {
            try {
              flag = lcpsoaClient.deleteIndexUser(obj[2].toString());
            } catch (Exception e) {
              e.printStackTrace();
              System.out.println("人员信息删除时出现相应的错误！！！");
            } 
          } 
        } 
        if (flag) {
          sqlUpdateFlag = "update lcpsoa set f_flag=1 where f_ywlx='" + obj[0].toString() + "' and f_bdsj='" + obj[1].toString() + "' and f_zj='" + obj[2].toString() + "' ";
          stmt.executeUpdate(sqlUpdateFlag);
        } 
      } 
      stmt.close();
      rs.close();
      con.close();
    } catch (SQLException e) {
      if (con != null)
        try {
          con.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
  }
}
