package com.js.oa.webservice.appservice;

import com.js.ldap.LDAP;
import com.js.ldap.LdapInterface;
import com.js.ldap.MSAD;
import com.js.ldap.MSADNoCert;
import com.js.ldap.OpenLDAP;
import com.js.util.config.SystemCommon;
import com.js.util.util.AES;
import com.js.util.util.BASE64;
import com.js.util.util.DataSourceBase;
import com.js.util.util.MD5;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class AuthService {
  private static Map<String, String> appInfo;
  
  public static String appServer = "";
  
  static {
    initAppInfo();
  }
  
  private static void initAppInfo() {
    appInfo = new HashMap<String, String>();
    FileInputStream configFileInputStream = null;
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/appInfo.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      builder.setExpandEntities(false);
      Document doc = builder.build(configFileInputStream);
      Element node = doc.getRootElement();
      node = node.getChild("WebApp");
      appServer = node.getChildText("AppServer");
      appInfo.put("AppServer", appServer);
      appInfo.put("AppName", node.getChildText("AppName"));
      appInfo.put("AppVersion", node.getChildText("AppVersion"));
      appInfo.put("UpdateDesc", node.getChildText("UpdateDesc"));
      appInfo.put("UpdateURL", node.getChildText("UpdateURL"));
      appInfo.put("ExpireTime", node.getChildText("ExpireTime"));
      appInfo.put("ServerPath", node.getChildText("ServerPath"));
      appInfo.put("Precision", node.getChildText("Precision"));
      appInfo.put("bgColor", node.getChildText("bgColor"));
      node = node.getChild("fieldsList");
      StringBuffer fieldsStr = new StringBuffer("<fields>");
      if (node != null) {
        List<Element> fields = node.getChildren();
        for (Element e : fields)
          fieldsStr.append("<field showName=\"" + e.getAttributeValue("showName") + "\" name=\"" + e.getAttributeValue("name") + 
              "\" mustFill=\"" + e.getAttributeValue("mustFill") + "\" maxLength=\"" + e.getAttributeValue("maxLength") + 
              "\" type=\"" + e.getAttributeValue("type") + "\" />"); 
      } 
      fieldsStr.append("</fields>");
      appInfo.put("fieldsStr", fieldsStr.toString());
      configFileInputStream.close();
    } catch (Exception err) {
      err.printStackTrace();
    } 
  }
  
  public String getAppLogInfo(String userInfo) {
    StringBuffer logInfo = new StringBuffer();
    logInfo.append("<?xml version='1.0' encoding='UTF-8'?>");
    logInfo.append("<data>");
    logInfo.append("<AppInfo>");
    logInfo.append("<AppName>").append(appInfo.get("AppName")).append("</AppName>");
    logInfo.append("<AppVersion>").append(appInfo.get("AppVersion")).append("</AppVersion>");
    logInfo.append("<UpdateURL>").append(appInfo.get("UpdateURL")).append("</UpdateURL>");
    logInfo.append("<UpdateDesc>").append(appInfo.get("UpdateDesc")).append("</UpdateDesc>");
    logInfo.append("<ExpireTime>").append(appInfo.get("ExpireTime")).append("</ExpireTime>");
    logInfo.append("<Precision>").append(appInfo.get("Precision")).append("</Precision>");
    logInfo.append("<bgColor>").append(appInfo.get("bgColor")).append("</bgColor>");
    logInfo.append("</AppInfo>");
    String errorType = "0";
    String errorMessage = "";
    String logURL = "";
    String userId = "";
    String userAccounts = "";
    String isLeader = "0";
    String empName = "";
    String[] account = getUserAccountAndPassword(userInfo);
    if (account[0] == null || "".equals(account[0])) {
      errorType = "1";
      errorMessage = "您输入的用户名不存在";
    } else {
      Connection conn = null;
      try {
        String userIsActive = "0", userPassword = "";
        boolean userExist = false;
        conn = (new DataSourceBase()).getDataSource().getConnection();
        String sql = "select userisactive,userpassword,emp_id,useraccounts,deptLeader,empname from org_employee where useraccounts=? and userisdeleted=0";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, account[0]);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
          userIsActive = rs.getString(1);
          userPassword = rs.getString(2);
          userId = rs.getString(3);
          userAccounts = rs.getString(4);
          isLeader = rs.getString(5);
          empName = rs.getString(6);
          userExist = true;
        } 
        rs.close();
        if (userExist) {
          if (!"1".equals(userIsActive)) {
            errorType = "2";
            errorMessage = "您的账户被禁用";
          } else if (passwordOK(account[0], account[1], userPassword)) {
            logURL = getLogURL(account[0]);
          } else {
            errorType = "3";
            errorMessage = "您输入的密码不正确";
          } 
        } else {
          errorType = "4";
          errorMessage = "用户名不存在";
        } 
        pstmt.close();
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
    } 
    logInfo.append("<LogResult>");
    logInfo.append("<LogURL>").append(logURL).append("</LogURL>");
    logInfo.append("<ErrorType>").append(errorType).append("</ErrorType>");
    logInfo.append("<ErrorMessage>").append(errorMessage).append("</ErrorMessage>");
    logInfo.append("<UserId>").append(String.valueOf(SystemCommon.getCustomerName()) + "_" + userId).append("</UserId>");
    logInfo.append("<UserAccounts>").append(userAccounts).append("</UserAccounts>");
    logInfo.append("<UserName>").append(empName).append("</UserName>");
    logInfo.append("<IsLeader>").append(isLeader).append("</IsLeader>");
    logInfo.append("<UserImg>").append(String.valueOf(appInfo.get("AppServer")) + "/jsoa/weixin/common/getUserAvatar.jsp?userId=" + userAccounts).append("</UserImg>");
    logInfo.append("</LogResult>");
    logInfo.append(appInfo.get("fieldsStr"));
    logInfo.append("</data>");
    return logInfo.toString();
  }
  
  private String[] getUserAccountAndPassword(String xml) {
    String[] account = { "", "" };
    StringReader read = new StringReader(xml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    sb.setExpandEntities(false);
    try {
      Document doc = sb.build(source);
      Element node = doc.getRootElement();
      account[0] = node.getChild("UserAccount").getText();
      String password = node.getChild("UserPassword").getText();
      account[1] = AES.decrypt2Str(password, "jiusi.net0123abc");
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    } 
    return account;
  }
  
  private boolean passwordOK(String userAccount, String input, String password) {
    int useLDAP = (new LDAP()).getUseLDAP();
    String rs = "-1";
    String openDS = (new OpenLDAP()).getLdapInfo().get("ldapUse");
    if ("1".equals(openDS)) {
      String openDSv = (new LdapInterface()).validateUser(userAccount, input);
      if (!"-1".equals(openDSv) && !"0".equals(openDSv))
        return true; 
    } else if (useLDAP == 1) {
      LDAP ldap = new MSAD();
      if (LDAP.getUseCert() == 0) {
        rs = (new MSADNoCert()).Authenticate(userAccount, input);
      } else {
        rs = ldap.Authenticate(userAccount, input);
      } 
      if ("0".equals(rs))
        return true; 
    } else if (password != null && password.length() > 30) {
      MD5 md5 = new MD5();
      input = md5.getMD5Code(input);
      if (md5.equals(input, password))
        return true; 
    } 
    return false;
  }
  
  private String getLogURL(String account) {
    String url = String.valueOf(appInfo.get("AppServer")) + "/jsoa/wap.do?method=wapLogin&amp;apptoken=";
    return String.valueOf(url) + BASE64.BASE64EncoderNoBR("account=" + account + "&time=" + (new Date()).getTime());
  }
  
  public String saveSign(String xml) {
    String result = "";
    System.out.println(xml);
    if (xml == null || "".equals(xml)) {
      result = "参数信息为空。";
    } else {
      CheckinInfo info = setSignInfo(xml);
      if (info.getUserId() == null || "".equals(info.getUserId())) {
        result = "用户名称为空。";
      } else if (info.getPosition() == null || "".equals(info.getPosition())) {
        result = "位置信息为空。";
      } else if (info.getJingdu() == null || "".equals(info.getJingdu())) {
        result = "经度信息为空。";
      } else if (info.getWeidu() == null || "".equals(info.getWeidu())) {
        result = "纬度信息为空。";
      } else if (info.getSignType() == null || "".equals(info.getSignType())) {
        result = "签到类型为空。";
      } else if (!"0".equals(info.getSignType()) && !"1".equals(info.getSignType())) {
        result = "签到类型错误。";
      } else if ("1".equals(info.getSignType()) && (
        info.getImgUrl() == null || "".equals(info.getImgUrl()))) {
        result = "照片信息为空。";
      } 
      if ("".equals(result)) {
        Connection conn = null;
        PreparedStatement ps = null;
        String databaseType = SystemCommon.getDatabaseType();
        try {
          conn = (new DataSourceBase()).getDataSource().getConnection();
          String sql = "";
          if ("0".equals(info.getSignType())) {
            if ("mysql".equals(databaseType)) {
              sql = "insert into kq_checkininfo(userid,checkintime,weidu,jingdu,position) values (?,now(),?,?,?)";
            } else {
              sql = "insert into kq_checkininfo values (hibernate_sequence.nextval,?,sysdate,?,?,?)";
            } 
          } else if ("1".equals(info.getSignType())) {
            if ("mysql".equals(databaseType)) {
              sql = "insert into kq_outsidecheckininfo(userid,checkintime,weidu,jingdu,position,imageurl,customName,customCompany,customAddress,reason,customType,customBz,ismark,rechecktime) values (?,now(),?,?,?,?,?,?,?,?,'',?,1,now())";
            } else {
              sql = "insert into kq_outsidecheckininfo values (hibernate_sequence.nextval,?,now(),?,?,?,?,?,?,?,?,'',?,1,now())";
            } 
          } 
          ps = conn.prepareStatement(sql);
          ps.setString(1, info.getUserId());
          ps.setString(2, info.getWeidu());
          ps.setString(3, info.getJingdu());
          ps.setString(4, info.getPosition());
          if ("1".equals(info.getSignType())) {
            ps.setString(5, info.getImgUrl());
            ps.setString(6, info.getCustomName());
            ps.setString(7, info.getCustomCompany());
            ps.setString(8, info.getCustomAddress());
            ps.setString(9, info.getReason());
            ps.setString(10, info.getCustomBz());
          } 
          if (ps.executeUpdate() > 0)
            result = "1"; 
        } catch (Exception e) {
          e.printStackTrace();
          result = e.getMessage();
        } finally {
          try {
            if (ps != null)
              ps.close(); 
            if (conn != null)
              conn.close(); 
          } catch (SQLException e) {
            e.printStackTrace();
            result = e.getMessage();
          } 
        } 
      } 
    } 
    return result;
  }
  
  private CheckinInfo setSignInfo(String xml) {
    CheckinInfo info = new CheckinInfo();
    StringReader read = new StringReader(xml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    sb.setExpandEntities(false);
    try {
      Document doc = sb.build(source);
      Element root = doc.getRootElement();
      Element node = root.getChild("userId");
      if (node != null)
        info.setUserId(node.getText()); 
      node = root.getChild("position");
      if (node != null)
        info.setPosition(node.getText()); 
      node = root.getChild("jingdu");
      if (node != null)
        info.setJingdu(node.getText()); 
      node = root.getChild("weidu");
      if (node != null)
        info.setWeidu(node.getText()); 
      node = root.getChild("signType");
      if (node != null)
        info.setSignType(node.getText()); 
      if ("1".equals(info.getSignType())) {
        node = root.getChild("imgUrl");
        if (node != null)
          info.setImgUrl(node.getText()); 
        node = root.getChild("customName");
        if (node != null)
          info.setCustomName(node.getText()); 
        node = root.getChild("customCompany");
        if (node != null)
          info.setCustomCompany(node.getText()); 
        node = root.getChild("customAddress");
        if (node != null)
          info.setCustomAddress(node.getText()); 
        node = root.getChild("reason");
        if (node != null)
          info.setReason(node.getText()); 
        node = root.getChild("customBz");
        if (node != null)
          info.setCustomBz(node.getText()); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return info;
  }
  
  public String saveImg(String userId, byte[] bytes) {
    String imgURL = "/jsoa/upload/weixinImage/";
    String imgName = String.valueOf(System.currentTimeMillis()) + "_" + userId + ".jpg";
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(String.valueOf(appInfo.get("ServerPath")) + imgURL + imgName);
      fos.write(bytes);
      fos.flush();
    } catch (Exception e) {
      e.printStackTrace();
      return "0";
    } finally {
      try {
        if (fos != null)
          fos.close(); 
      } catch (IOException e) {
        e.printStackTrace();
        return "0";
      } 
    } 
    return String.valueOf(imgURL) + imgName;
  }
  
  public String getCheckinCount(String xml) {
    String count = "0";
    System.out.println("xml:" + xml);
    StringReader read = new StringReader(xml);
    InputSource source = new InputSource(read);
    SAXBuilder sb = new SAXBuilder();
    sb.setExpandEntities(false);
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      Document doc = sb.build(source);
      Element node = doc.getRootElement();
      String userAccounts = node.getChild("userId").getText();
      String signType = node.getChild("signType").getText();
      String databaseType = SystemCommon.getDatabaseType();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      c.set(5, 1);
      c.set(10, 0);
      c.set(12, 0);
      c.set(13, 0);
      String startDate = String.valueOf(sdf.format(c.getTime())) + " 00:00:00";
      c.set(2, c.get(2) + 1);
      String endDate = String.valueOf(sdf.format(c.getTime())) + " 00:00:00";
      String sql = "";
      if ("0".equals(signType)) {
        if ("oracle".equals(databaseType)) {
          sql = "SELECT count(*) FROM kq_checkininfo WHERE checkintime BETWEEN to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') AND to_date('" + 
            endDate + "','yyyy-MM-ddhh24:mi:ss') and userid='" + userAccounts + "'";
        } else {
          sql = "SELECT count(*) FROM kq_checkininfo WHERE checkintime BETWEEN '" + startDate + "' AND '" + endDate + "' AND userid='" + userAccounts + "'";
        } 
      } else if ("1".equals(signType)) {
        if ("oracle".equals(databaseType)) {
          sql = "SELECT count(*) FROM kq_outsidecheckininfo WHERE rechecktime BETWEEN to_date('" + startDate + "','yyyy-MM-dd hh24:mi:ss') AND to_date('" + 
            endDate + "','yyyy-MM-ddhh24:mi:ss') and userid='" + userAccounts + "'";
        } else {
          sql = "SELECT count(*) FROM kq_outsidecheckininfo WHERE rechecktime BETWEEN '" + startDate + "' AND '" + endDate + "' AND userid='" + userAccounts + "'";
        } 
      } 
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      if (rs.next())
        count = rs.getString(1); 
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    } finally {
      try {
        if (rs != null)
          rs.close(); 
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (SQLException e) {
        e.printStackTrace();
      } 
    } 
    return count;
  }
}
