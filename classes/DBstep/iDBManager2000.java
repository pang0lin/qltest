package DBstep;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class iDBManager2000 {
  private static String driver;
  
  private static String url;
  
  private static String userName;
  
  private static String password;
  
  public Connection Conn = null;
  
  public Statement Stmt = null;
  
  private void initPara() {
    try {
      String path = System.getProperty("user.dir");
      path = String.valueOf(path) + "/jsconfig/context.xml";
      FileInputStream configFileInputStream = new FileInputStream(new File(path));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      List<Element> resource = root.getChildren("Resource");
      if (resource != null)
        for (int i = 0; i < resource.size(); i++) {
          Element node = resource.get(i);
          String resourceName = node.getAttributeValue("name");
          if ("jdbc/jsdb".equals(resourceName)) {
            driver = node.getAttributeValue("driverClassName");
            url = node.getAttributeValue("url");
            userName = node.getAttributeValue("username");
            password = node.getAttributeValue("password");
            break;
          } 
        }  
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public boolean OpenConnection() {
    if (driver == null)
      initPara(); 
    boolean flag = true;
    try {
      Class.forName(driver).newInstance();
      this.Conn = DriverManager.getConnection(url, userName, password);
      this.Stmt = this.Conn.createStatement();
      flag = true;
    } catch (Exception exception) {
      System.out.println(exception.toString());
      flag = false;
    } 
    return flag;
  }
  
  public void CloseConnection() {
    try {
      this.Stmt.close();
      this.Conn.close();
    } catch (Exception exception) {
      try {
        this.Conn.close();
      } catch (Exception ex) {
        System.out.println("error close connection DBstep.iDBManager2000 CloseConnection");
      } 
      System.out.println(exception.toString());
    } 
  }
  
  public String GetDateTime() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String s = simpledateformat.format(calendar.getTime());
    return s;
  }
  
  public Date GetDate() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
    String s = simpledateformat.format(calendar.getTime());
    return Date.valueOf(s);
  }
  
  public int GetMaxID(String s, String s1) {
    int i = 0;
    boolean flag = true;
    String s2 = new String();
    s2 = "select max(" + s1 + ")+1 as MaxID from " + s;
    try {
      if (this.Conn != null)
        flag = this.Conn.isClosed(); 
      if (flag)
        OpenConnection(); 
      ResultSet resultset = ExecuteQuery(s2);
      if (resultset.next())
        i = resultset.getInt("MaxID"); 
      resultset.close();
      if (flag)
        CloseConnection(); 
    } catch (Exception exception) {
      System.out.println(exception.toString());
    } 
    return i;
  }
  
  public ResultSet ExecuteQuery(String s) {
    ResultSet resultset = null;
    try {
      resultset = this.Stmt.executeQuery(s);
    } catch (Exception exception) {
      System.out.println(exception.toString());
    } 
    return resultset;
  }
  
  public int ExecuteUpdate(String s) {
    int i = 0;
    try {
      i = this.Stmt.executeUpdate(s);
    } catch (Exception exception) {
      System.out.println(exception.toString());
    } 
    return i;
  }
  
  public String GetDateAsStr() {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
    String s = simpledateformat.format(calendar.getTime());
    return s;
  }
}
