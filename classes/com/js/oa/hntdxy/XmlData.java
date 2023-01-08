package com.js.oa.hntdxy;

import com.js.oa.hntdxy.bean.Information;
import com.js.oa.hntdxy.bean.InformationContent;
import com.js.oa.hntdxy.bean.TaskDeal;
import com.js.oa.hntdxy.xml.XStreamUtils;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XmlData {
  public String getDeaWithData(String userId, String domainId, String fileType, String category, String includeCoop) {
    String xml = "";
    List list = (fileType == null) ? new ArrayList() : (new CustomDesktopBD()).listFileDealList(userId, domainId, fileType, category, includeCoop, 8);
    xml = getdealDataxml(list);
    return xml;
  }
  
  public String getFormDataList(String channelId, String userId, String orgId, String orgIdString, String domainId) {
    String xml = "";
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    List listInfo = customDesktopBD.listInformation(channelId, userId, orgId, orgIdString, domainId);
    xml = getInformAndNewslist(listInfo);
    return xml;
  }
  
  public String getFormContentData(String inforid, String channelId) {
    String xml = "";
    List listInfo = getInformAndNews(inforid, channelId);
    xml = getInformAndNewscontent(listInfo);
    return xml;
  }
  
  public String getdealDataxml(List<Object[]> list) {
    String temp = "";
    String hntdIP = SystemCommon.getHntdMainIP();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String time = "";
    List<TaskDeal> objtemp = new ArrayList<TaskDeal>();
    if (list.size() > 0 && list != null) {
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        if (obj[5] != null && !"".equals(obj[5]) && !"null".equals(obj[5]))
          try {
            date = sdf.parse(obj[5].toString());
            time = sdf.format(date);
          } catch (ParseException e) {
            e.printStackTrace();
          }  
        TaskDeal td = new TaskDeal();
        td.setInfoId(obj[10].toString());
        td.setTitle(obj[2].toString());
        td.setCreatetime(time);
        td.setUrl(String.valueOf(hntdIP) + "/jsoa/hntdxy/index.jsp?operate=dbdp");
        objtemp.add(td);
      } 
      if (objtemp.size() != 0)
        return XStreamUtils.toXML(objtemp, "com.js.oa.hntdxy.bean.TaskDeal"); 
    } 
    return temp;
  }
  
  public String getInformAndNewslist(List<Object[]> list) {
    String temp = "";
    String urlDetail = "";
    String hntdIP = SystemCommon.getHntdMainIP();
    String urlLogin = String.valueOf(hntdIP) + "/jsoa/hntdxy/index.jsp?";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String time = "";
    List<Information> objtemp = new ArrayList<Information>();
    if (list.size() > 0 && list != null) {
      for (int i = 0; i < list.size(); i++) {
        Object[] obj = list.get(i);
        if (obj[17] != null && !"".equals(obj[17]) && !"null".equals(obj[17]))
          try {
            date = sdf.parse(obj[17].toString());
            time = sdf.format(date);
          } catch (ParseException e) {
            e.printStackTrace();
          }  
        Information info = new Information();
        info.setInfoId(obj[1].toString());
        info.setTitle(obj[2].toString());
        info.setCreatetime(time);
        urlDetail = "informationId=" + obj[1];
        info.setUrl(String.valueOf(urlLogin) + urlDetail);
        objtemp.add(info);
      } 
      if (objtemp.size() != 0)
        return XStreamUtils.toXML(objtemp, "com.js.oa.hntdxy.bean.Information"); 
    } 
    return temp;
  }
  
  public List getInformAndNews(String id, String channelId) {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<String> list = new ArrayList();
    String sql = "select a.INFORMATION_ID,a.INFORMATIONTITLE,a.INFORMATIONISSUETIME,a.INFORMATIONCONTENT from OA_INFORMATION a left join OA_INFORMATIONCHANNEL b on a.CHANNEL_ID=b.CHANNEL_ID";
    sql = String.valueOf(sql) + " where b.CHANNEL_ID=" + channelId + " and a.INFORMATION_ID=" + id;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        list.add(rs.getString(1));
        list.add(rs.getString(2));
        list.add(rs.getString(3));
        list.add(rs.getString(4));
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getInformAndNewscontent(List<E> list) {
    String temp = "";
    String time = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    List<String[]> listaddress = (List)new ArrayList<String>();
    List<InformationContent> objtemp = new ArrayList<InformationContent>();
    String address = "", addressstr = "", accStr = "";
    String accessoryname = "";
    if (list.size() > 0 && list != null) {
      if (list.get(2) != null && !"".equals(list.get(2)) && !"null".equals(list.get(2)))
        try {
          date = sdf.parse(list.get(2).toString());
          time = sdf.format(date);
        } catch (ParseException e) {
          e.printStackTrace();
        }  
      InformationContent infocontent = new InformationContent();
      infocontent.setInfoId(list.get(0).toString());
      infocontent.setTitle(list.get(1).toString());
      infocontent.setCreatetime(time);
      listaddress = getInformationaccessory(list.get(0).toString(), "1");
      if (listaddress.size() > 0 && listaddress != null) {
        Object[] addressobj = (Object[])listaddress.get(0);
        address = addressobj[0].toString();
        accessoryname = addressobj[1].toString();
        addressstr = "<img src=\"" + address + "\"/>";
      } 
      listaddress = getInformationaccessory(list.get(0).toString(), "0");
      if (listaddress.size() > 0 && listaddress != null)
        for (int j = 0; j < listaddress.size(); j++) {
          Object[] addressobj = (Object[])listaddress.get(j);
          address = addressobj[0].toString();
          accessoryname = addressobj[1].toString();
          accStr = String.valueOf(accStr) + "<a href=\"" + address + "\"target=\"_blank\">" + accessoryname + "</a>";
        }  
      String content = list.get(3).toString();
      if (content.indexOf("/jsoa/upload/html") >= 0)
        content = content.replaceAll("/jsoa/upload/html", "http://oa.hnrpc.com/jsoa/upload/html"); 
      infocontent.setContent(String.valueOf(content) + addressstr + accStr);
      objtemp.add(infocontent);
      if (objtemp.size() != 0)
        return XStreamUtils.toXML(objtemp, "com.js.oa.hntdxy.bean.InformationContent"); 
    } 
    return temp;
  }
  
  public List<String[]> getInformationaccessory(String informationid, String accessoryisimage) {
    List<String[]> list = (List)new ArrayList<String>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String address = "";
    String sql = "select accessoryisimage,accessoryname,accessorysavename from oa_informationaccessory where information_id=" + informationid + " and accessoryisimage=" + accessoryisimage;
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        address = getAccessoryAddress(rs.getString(3));
        list.add(new String[] { address, rs.getString(2) });
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e1) {
        e1.printStackTrace();
      } 
      e.printStackTrace();
    } 
    return list;
  }
  
  public String getAccessoryAddress(String accessorysavename) {
    String address = "";
    String hntdIP = SystemCommon.getHntdMainIP();
    String year = "";
    year = accessorysavename.substring(0, accessorysavename.indexOf("_"));
    address = String.valueOf(hntdIP) + "/jsoa/upload/" + year + "/information/" + accessorysavename;
    System.out.println("------" + address);
    return address;
  }
}
