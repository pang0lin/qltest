package com.js.oa.portal.service;

import com.js.oa.bbs.service.ForumClassBD;
import com.js.system.manager.service.ManagerService;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class PortalService {
  public List getSubChannel(String channelId) {
    List<String[]> list = new ArrayList();
    Connection conn = null;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select channel_id,channelname from oa_informationchannel where channelparentid=" + Long.valueOf(channelId);
      sql = String.valueOf(sql) + " order by channelsort";
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        list.add(new String[] { rs.getString(1), rs.getString(2) });
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    return list;
  }
  
  public List getForumInfo(String userId, String orgId, String orgIdString) {
    String classId = "0";
    ManagerService mbd = new ManagerService();
    ForumClassBD cbd = new ForumClassBD();
    String where = " 1=1";
    String more = "/jsoa/ForumAction.do?action=list%26classId=none";
    where = mbd.getScopeFinalWhere(userId, orgId, orgIdString, "po.classUserId", "po.classUserOrg", "po.classUserGroup");
    where = " (1<>1 " + cbd.getClassIdString(userId, "po.forumClass.id", where) + " or po.forumClass.classUserId is null)";
    where = String.valueOf(where) + " and po.domainId=0";
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    Map map = customDesktopBD.listMyForum(where);
    List listInfo = (List)map.get("forum");
    return listInfo;
  }
  
  public String getTitleStr(String title, int length) {
    int num = 0;
    char[] characters = title.toCharArray();
    String returnValue = "";
    int i = 0;
    while (num <= length && num < (title.getBytes()).length) {
      num++;
      returnValue = String.valueOf(returnValue) + characters[i];
      i++;
    } 
    if ((title.getBytes()).length > length)
      returnValue = (new StringBuilder(String.valueOf(returnValue))).toString(); 
    return returnValue;
  }
  
  public Map getPortalURL() {
    Map<Object, Object> map = new HashMap<Object, Object>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/shensiportal.xml";
      configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("commonmenu");
      List<Element> modules = node.getChildren("module");
      if (modules != null && modules.size() > 0) {
        List<String[]> list = new ArrayList();
        for (int i = 0; i < modules.size(); i++) {
          node = modules.get(i);
          String name = node.getChildText("name");
          String image = node.getChildText("image");
          String url = node.getChildText("url");
          list.add(new String[] { name, image, url });
        } 
        map.put("commonmenu", list);
      } 
      node = root.getChild("empself");
      modules = node.getChildren("module");
      if (modules != null && modules.size() > 0) {
        List<String[]> list = new ArrayList();
        for (int i = 0; i < modules.size(); i++) {
          node = modules.get(i);
          String name = node.getChildText("name");
          String image = node.getChildText("image");
          String url = node.getChildText("url");
          list.add(new String[] { name, image, url });
        } 
        map.put("empself", list);
      } 
      node = root.getChild("productservice");
      List<Element> moduleRows = node.getChildren("modulerow");
      if (moduleRows != null && moduleRows.size() > 0) {
        List<String[][]> list = new ArrayList();
        for (int i = 0; i < moduleRows.size(); i++) {
          modules = ((Element)moduleRows.get(i)).getChildren("module");
          String[][] menuArr = new String[modules.size()][3];
          for (int j = 0; j < modules.size(); j++) {
            node = modules.get(j);
            String name = node.getChildText("name");
            String image = node.getChildText("image");
            String url = node.getChildText("url");
            menuArr[j][0] = name;
            menuArr[j][1] = image;
            menuArr[j][2] = url;
          } 
          list.add(menuArr);
        } 
        map.put("productservice", list);
      } 
      configFileInputStream.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
    return map;
  }
  
  public String getTopChannelId2(String channelId) {
    String topChannelId = "0";
    String topChannelName = "0";
    Connection conn = null;
    String idString = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select channelidstring from oa_informationchannel where channel_id=" + Long.valueOf(channelId);
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        idString = rs.getString(1); 
      rs.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    String[] split = idString.split("[$]");
    return split[1];
  }
  
  public String[] getTopChannelId(String channelId) {
    String topChannelId = "0";
    String topChannelName = "0";
    Connection conn = null;
    String idString = "";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      Statement stmt = conn.createStatement();
      String sql = "select channelparentid from oa_informationchannel where channel_id=" + Long.valueOf(channelId);
      ResultSet rs = stmt.executeQuery(sql);
      if (rs.next())
        idString = rs.getString(1); 
      rs.close();
      String databaseType = SystemCommon.getDatabaseType();
      if (Integer.parseInt(idString) > 0) {
        sql = "select channel_id,channelname from oa_informationchannel where  channel_id=" + idString;
        rs = stmt.executeQuery(sql);
        if (rs.next()) {
          topChannelId = rs.getString(1);
          topChannelName = rs.getString(2);
        } 
        rs.close();
        stmt.close();
        conn.close();
      } 
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } finally {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
    } 
    String[] channelInfo = { topChannelId, topChannelName };
    return channelInfo;
  }
}
