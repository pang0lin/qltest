package com.js.oa.menu.service;

import com.js.oa.search.client.SearchService;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShortCutBD {
  private Connection conn = null;
  
  private String dbType;
  
  public void init() {
    try {
      this.conn = (new DataSourceBase()).getDataSource().getConnection();
      this.dbType = SystemCommon.getDatabaseType();
    } catch (Exception ex) {
      System.out.println("ShortCutBD.java -- init err!");
    } 
  }
  
  public List getPortalMenuUser(String userId) {
    List<String[]> portalList = new ArrayList();
    try {
      Statement stmt = this.conn.createStatement();
      ResultSet rs = stmt.executeQuery("select opu.id,opu.portal_id,pu.menuname from oa_portal_user opu,oa_portal pu where opu.portal_id=pu.id and opu.emp_id='" + userId + "'  ");
      while (rs.next()) {
        String[] obj = new String[3];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        portalList.add(obj);
      } 
      rs.close();
      stmt.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return portalList;
  }
  
  public List getShortChutMenu(String userId) {
    List<String[]> shortCutList = new ArrayList();
    String idStr = "";
    try {
      Statement stmt = this.conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT id,menu_name FROM menu_short WHERE menu_type=0 AND showtype=0 AND id NOT IN(SELECT menu_id FROM menu_short_user WHERE menu_type=0 AND user_id=" + userId + ") ORDER BY menu_order");
      while (rs.next()) {
        String[] obj = new String[2];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        shortCutList.add(obj);
        idStr = String.valueOf(idStr) + obj[0] + ",";
      } 
      rs.close();
      stmt.close();
      stmt = this.conn.createStatement();
      rs = stmt.executeQuery("select count(*) from menu_short a,menu_short_user b where a.id=b.menu_id and a.menu_type=0  and b.user_id=" + userId + " order by b.menu_order");
      int count = 0;
      if (rs.next())
        count = rs.getInt(1); 
      rs.close();
      stmt.close();
      if (count > 0) {
        stmt = this.conn.createStatement();
        rs = stmt.executeQuery("SELECT id,menu_name FROM menu_short WHERE menu_type=0 AND showtype=1 AND id NOT IN(SELECT menu_id FROM menu_short_user WHERE menu_type=0 AND user_id=" + userId + ") ORDER BY menu_order");
        while (rs.next()) {
          String[] obj = new String[2];
          obj[0] = rs.getString(1);
          obj[1] = rs.getString(2);
          if (idStr.indexOf(obj[0]) < 0)
            shortCutList.add(obj); 
        } 
        rs.close();
        stmt.close();
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return shortCutList;
  }
  
  public List getShortChutMenuChecked(String userId) {
    List<String[]> shortCutList = new ArrayList();
    try {
      Statement stmt = this.conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT a.id,a.menu_name FROM menu_short a,menu_short_user b WHERE a.id = b.menu_id AND a.menu_type = 0 AND b.user_id = " + userId + " ORDER BY b.menu_order ");
      while (rs.next()) {
        String[] obj = new String[2];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        shortCutList.add(obj);
      } 
      rs.close();
      stmt.close();
      if (shortCutList.size() < 1) {
        stmt = this.conn.createStatement();
        rs = stmt.executeQuery("SELECT id,menu_name FROM menu_short WHERE menu_type=0  AND showtype=1 ORDER BY menu_order");
        while (rs.next()) {
          String[] obj = new String[2];
          obj[0] = rs.getString(1);
          obj[1] = rs.getString(2);
          shortCutList.add(obj);
        } 
        rs.close();
        stmt.close();
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return shortCutList;
  }
  
  public List getToolsMenu(String userId) {
    List<String[]> toolsList = new ArrayList();
    try {
      Statement stmt = this.conn.createStatement();
      SearchService.getInstance();
      String iSearchSwitch = SearchService.getiSearchSwitch();
      String wh = "";
      if (iSearchSwitch == null || "".equals(iSearchSwitch) || !"1".equals(iSearchSwitch))
        wh = " and id!=106"; 
      ResultSet rs = stmt.executeQuery("select id,menu_name from menu_short where menu_type=1 " + wh + " and id not in(select menu_id from menu_short_user where menu_type=1 and user_id=" + userId + ") order by menu_order");
      while (rs.next()) {
        String[] obj = new String[2];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        toolsList.add(obj);
      } 
      rs.close();
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return toolsList;
  }
  
  public List getToolsMenuUser(String userId) {
    List<String[]> toolsList = new ArrayList();
    try {
      Statement stmt = this.conn.createStatement();
      SearchService.getInstance();
      String iSearchSwitch = SearchService.getiSearchSwitch();
      String wh = "";
      if (iSearchSwitch == null || "".equals(iSearchSwitch) || !"1".equals(iSearchSwitch))
        wh = " and a.id!=106"; 
      ResultSet rs = stmt.executeQuery("select a.id,a.menu_name from menu_short a,menu_short_user b where a.id=b.menu_id and a.menu_type=1 and b.user_id=" + userId + wh + "  order by b.menu_order");
      while (rs.next()) {
        String[] obj = new String[2];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        toolsList.add(obj);
      } 
      rs.close();
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return toolsList;
  }
  
  public List getShortCutMenuUser(String userId) {
    List<String[]> shortCutList = new ArrayList();
    try {
      Statement stmt = this.conn.createStatement();
      ResultSet rs = stmt.executeQuery("select a.id,a.menu_name from menu_short a,menu_short_user b where a.id=b.menu_id and a.menu_type=0 and b.user_id=" + userId + " order by b.menu_order");
      while (rs.next()) {
        String[] obj = new String[2];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        shortCutList.add(obj);
      } 
      rs.close();
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return shortCutList;
  }
  
  public boolean updateUserMenu(String userId, String[] shortCutMenu, String[] toolsMenu, String portalMenu) {
    boolean res = false;
    try {
      Statement stmt = this.conn.createStatement();
      if (shortCutMenu != null) {
        stmt.executeUpdate("delete from menu_short_user where menu_type=0 and user_id=" + userId);
        for (int i = 0; i < shortCutMenu.length; i++) {
          StringBuffer buffer = new StringBuffer();
          if ("oracle".equals(this.dbType)) {
            buffer.append("insert into menu_short_user(id,menu_id,user_id,menu_type,menu_order) values(hibernate_sequence.nextval,");
          } else if ("mysql".equals(this.dbType)) {
            buffer.append("insert into menu_short_user(menu_id,user_id,menu_type,menu_order) values(");
          } 
          buffer.append(shortCutMenu[i]).append(",")
            .append(userId).append(",0,")
            .append(i).append(")");
          stmt.addBatch(buffer.toString());
        } 
        stmt.executeBatch();
        stmt.clearBatch();
      } 
      if (toolsMenu != null) {
        stmt.executeUpdate("delete from menu_short_user where menu_type=1 and user_id=" + userId);
        for (int i = 0; i < toolsMenu.length; i++) {
          StringBuffer buffer = new StringBuffer();
          if ("oracle".equals(this.dbType)) {
            buffer.append("insert into menu_short_user(id,menu_id,user_id,menu_type,menu_order) values(hibernate_sequence.nextval,");
          } else if ("mysql".equals(this.dbType)) {
            buffer.append("insert into menu_short_user(menu_id,user_id,menu_type,menu_order) values(");
          } 
          buffer.append(toolsMenu[i]).append(",")
            .append(userId).append(",1,")
            .append(i).append(")");
          stmt.addBatch(buffer.toString());
        } 
        stmt.executeBatch();
        stmt.clearBatch();
      } 
      if (portalMenu != null && !portalMenu.equals("") && !portalMenu.equals("null")) {
        stmt.executeUpdate("delete from oa_portal_user where emp_id=" + userId);
        StringBuffer buffer = new StringBuffer();
        if ("oracle".equals(this.dbType)) {
          buffer.append("insert into oa_portal_user(id,emp_id,portal_id) values(hibernate_sequence.nextval,");
        } else if ("mysql".equals(this.dbType)) {
          buffer.append("insert into oa_portal_user(emp_id,portal_id) values(");
        } 
        buffer.append(userId).append(",")
          .append(portalMenu).append(")");
        stmt.addBatch(buffer.toString());
        stmt.executeBatch();
        stmt.clearBatch();
      } else {
        stmt.executeUpdate("delete from oa_portal_user where emp_id=" + userId);
        stmt.executeBatch();
        stmt.clearBatch();
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return res;
  }
  
  public String getPortalByUserId(String userId) {
    String portalId = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      init();
      stmt = this.conn.createStatement();
      rs = stmt.executeQuery("select portal_id from oa_portal_user where emp_id=" + userId);
      if (rs.next())
        portalId = rs.getString(1); 
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
      close();
    } 
    return portalId;
  }
  
  public List getLeftMenu(String userId, int flag) {
    List<String[]> menuList = new ArrayList();
    try {
      Statement stmt = this.conn.createStatement();
      ResultSet rs = stmt.executeQuery("select a.id,a.menu_name,a.url_type,a.menu_url,a.menu_img from menu_short a,menu_short_user b where a.id=b.menu_id and a.menu_type=" + flag + "  and b.user_id=" + userId + " order by b.menu_order");
      while (rs.next()) {
        String[] obj = new String[5];
        obj[0] = rs.getString(1);
        obj[1] = rs.getString(2);
        obj[2] = rs.getString(3);
        obj[3] = rs.getString(4);
        obj[4] = rs.getString(5);
        menuList.add(obj);
      } 
      rs.close();
      if (menuList.size() < 1) {
        rs = stmt.executeQuery("select id,menu_name,url_type,menu_url,menu_img from menu_short where menu_type=" + flag + "  and showtype=1 order by menu_order");
        int i = 0;
        while (rs.next()) {
          i++;
          String[] obj = new String[5];
          obj[0] = rs.getString(1);
          obj[1] = rs.getString(2);
          obj[2] = rs.getString(3);
          obj[3] = rs.getString(4);
          obj[4] = rs.getString(5);
          menuList.add(obj);
          if ((flag == 0) ? (
            i >= 6) : (
            
            i >= 4))
            break; 
        } 
        rs.close();
      } 
      stmt.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return menuList;
  }
  
  public void close() {
    try {
      if (this.conn != null)
        this.conn.close(); 
    } catch (Exception ex) {
      System.out.println("ShortCutBD.java --关闭数据库失败");
    } 
  }
}
