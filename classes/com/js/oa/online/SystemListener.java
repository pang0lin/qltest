package com.js.oa.online;

import com.js.oa.jsflow.uponline.UponlineThread;
import com.js.oa.security.log.service.LogBD;
import com.js.thread.FlowHandleThread;
import com.js.thread.MainThread;
import com.js.thread.TelecomReceiveThread;
import com.js.util.config.SystemCommon;
import com.js.util.util.DataSourceBase;
import com.js.util.util.JSFile;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jfree.chart.servlet.ChartDeleter;

public class SystemListener implements HttpSessionAttributeListener, ServletContextListener, HttpSessionListener {
  private static ServletContext context = null;
  
  public void sessionCreated(HttpSessionEvent se) {}
  
  public void sessionDestroyed(HttpSessionEvent se) {
    HttpSession session = se.getSession();
    DataSourceBase ds = new DataSourceBase();
    try {
      ds.begin();
      String userId = "0";
      ResultSet rs = ds.executeQuery("select user_id,user_ip,domain_id,user_name,user_account from sec_onlineuser where session_id='" + session.getId() + "'");
      if (rs.next()) {
        userId = rs.getString(1);
        String userIP = rs.getString(2);
        String domainId = rs.getString(3);
        String userName = rs.getString(4);
        String userAccount = rs.getString(5);
        String orgName = "";
        rs.close();
        if (userAccount != null && !userAccount.toLowerCase().equals("admin")) {
          rs = ds.executeQuery("select o.orgnamestring from org_organization o,org_organization_user ou,org_employee e where o.org_id=ou.org_id and ou.emp_id=e.emp_id and e.emp_id=" + 
              userId);
          if (rs.next())
            orgName = rs.getString(1); 
          rs.close();
        } 
        ds.executeUpdate("delete from jsf_onlineuser where useraccount='" + userAccount + "'");
        Date date = new Date();
        LogBD bd = new LogBD();
        bd.log(userId, userName, orgName, "oa_index", "首页", date, date, "4", "", userIP, domainId);
      } else {
        rs.close();
      } 
      ds.executeUpdate("delete from sec_onlineuser where session_id='" + session.getId() + "'");
      ds.executeUpdate("update org_employee set userOnline='0'  where EMP_ID =" + Long.parseLong(userId));
      deleteJFreeChartTempPicture(se);
    } catch (Exception ex) {
      System.out.println("记录删除在线用户信息失败");
      ex.printStackTrace();
    } finally {
      ds.end();
    } 
  }
  
  public void attributeAdded(HttpSessionBindingEvent e) {}
  
  public void attributeReplaced(HttpSessionBindingEvent e) {}
  
  public void attributeRemoved(HttpSessionBindingEvent e) {}
  
  public void contextDestroyed(ServletContextEvent sce) {
    MainThread mt = new MainThread();
    mt.cancel();
    FlowHandleThread ft = new FlowHandleThread();
    ft.cancel();
    UponlineThread ut = new UponlineThread();
    ut.cancel();
  }
  
  public void contextInitialized(ServletContextEvent sce) {
    try {
      ResourceBundle resource = ResourceBundle.getBundle("info");
      String str = resource.getString("AppPath");
      System.setProperty("user.dir", str);
      System.out.println("当前系统路径:" + str);
      System.out.println("如不正确请到/jsoa/WEB-INF/classes/info.properties 文件中修改 AppPath为正确的值！");
    } catch (Exception ex) {
      System.out.println("使用默认系统路径...");
    } 
    Connection conn = null;
    String curPath = System.getProperty("user.dir");
    String file = String.valueOf(curPath) + "/jsconfig/hibernate.property.xml";
    try {
      FileInputStream configFileInputStream = new FileInputStream(new File(file));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element node = doc.getRootElement().getChild("Hibernate");
      String server = node.getAttributeValue("server");
      if ("weblogic".equals(server) || "tongweb5".equals(server)) {
        DataSourceBase.setDataSourceName("jdbc/jsdb");
        DataSourceBase.setDataSourcePreName("");
      } else {
        DataSourceBase.setDataSourceName("java:comp/env/jdbc/jsdb");
        DataSourceBase.setDataSourcePreName("java:comp/env/");
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    String serverIP = "";
    try {
      serverIP = InetAddress.getLocalHost().getHostAddress();
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      Statement stmt = conn.createStatement();
      if (!"".equals(serverIP) && SystemCommon.getUseClusterServer() == 1) {
        stmt.executeUpdate("delete from sec_onlineuser where server_ip='" + serverIP + "'");
      } else {
        stmt.executeUpdate("delete from sec_onlineuser");
      } 
      stmt.executeUpdate("delete from jsf_onlineuser");
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      ex.printStackTrace();
    } 
    int currentYear = Calendar.getInstance().get(1);
    String path = sce.getServletContext().getRealPath("/upload/");
    JSFile jsFile = new JSFile();
    for (int i = 0; i < 4; i++)
      jsFile.copyUploadFolder(path, "0000", String.valueOf(i + currentYear)); 
    if (SystemCommon.getCustomerName().equals("landaedu"))
      (new TelecomReceiveThread()).start(); 
  }
  
  private void deleteJFreeChartTempPicture(HttpSessionEvent se) {
    ChartDeleter deleter = (ChartDeleter)se.getSession().getAttribute("JFreeChart_Deleter");
    se.getSession().removeAttribute("JFreeChart_Deleter");
    se.getSession().setAttribute("JFreeChart_Deleter", deleter);
  }
}
