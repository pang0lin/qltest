package com.js.oa.hntdxy;

import com.js.oa.hntdxy.bean.Information;
import com.js.oa.hntdxy.bean.TaskDeal;
import com.js.oa.hntdxy.xml.XStreamUtils;
import com.js.oa.portal.service.CustomDesktopBD;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class hntdCustomDesktopAction extends HttpServlet {
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    resp.setContentType("text/xml; charset=GBK");
    String url = req.getRequestURL().toString();
    url = url.substring(0, url.lastIndexOf("CustomDesktopAction"));
    String type = (req.getParameter("type") == null) ? "" : req.getParameter("type");
    System.out.println("类型：" + type);
    String userId = (req.getParameter("empid") == null) ? "-1" : req.getParameter("empid").toString();
    String domainId = (req.getParameter("domain_id") == null) ? "0" : req.getParameter("domain_id").toString();
    CustomDesktopBD customDesktopBD = new CustomDesktopBD();
    System.out.println("进入门户Action");
    if (type.equals("MyColumn")) {
      System.out.println("我的公告栏目");
      String channelId = "0";
      if (req.getParameter("channelId") != null)
        channelId = req.getParameter("channelId"); 
      System.out.println("我的公告栏目channelId" + channelId);
      String orgId = (req.getParameter("orgId") == null) ? "0" : req.getParameter("orgId").toString();
      String orgIdString = (req.getParameter("orgIdString") == null) ? "" : req.getParameter("orgIdString").toString();
      List listInfo = customDesktopBD.listInformation(channelId, userId, orgId, orgIdString, domainId);
      String operation = "inform";
      if ("100".equals(channelId))
        operation = "news"; 
      String xml = getInformAndNewslist(listInfo, req, operation);
      System.out.println("进入" + operation + "列表开始---");
      resp.getWriter().write(xml);
      resp.getWriter().flush();
      System.out.println("进入" + operation + "列表结束，并释放缓存------");
    } else if (type.equals("FileDealList")) {
      String fileType = req.getParameter("fileType");
      String category = req.getParameter("category");
      String includeCoop = req.getParameter("includeCoop");
      List list = (fileType == null) ? new ArrayList() : (new CustomDesktopBD()).listFileDealList(userId, domainId, fileType, category, includeCoop, 8);
      String xml = getdealDataxml(list);
      System.out.println("已进入待办列表---");
      resp.getWriter().write(xml);
      resp.getWriter().flush();
      System.out.println("进入待办列表结束，并释放缓存---");
    } 
  }
  
  public String getInformAndNewslist(List<Object[]> list, HttpServletRequest request, String operation) {
    String xml = "";
    String temp = "";
    String urlDetail = "";
    String hntdIP = SystemCommon.getHntdMainIP();
    String urlLogin = String.valueOf(hntdIP) + "/jsoa/hntdxy/index.jsp?";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    String time = "";
    List listaddress = new ArrayList();
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
  
  public List<String[]> getInformationaccessory(String informationid, HttpServletRequest request, String accessoryisimage) {
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
        address = getAccessoryAddress(rs.getString(3), request);
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
  
  public String getAccessoryAddress(String accessorysavename, HttpServletRequest request) {
    String address = "";
    String hntdIP = SystemCommon.getHntdIP();
    String year = "";
    year = accessorysavename.substring(0, accessorysavename.indexOf("_"));
    address = String.valueOf(hntdIP) + "/jsoa/upload/" + year + "/information/" + accessorysavename;
    return address;
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
}
