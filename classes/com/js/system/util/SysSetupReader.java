package com.js.system.util;

import com.js.util.util.DataSourceBase;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SysSetupReader {
  private static SysSetupReader sysSetupReader;
  
  private static Map sysSetupMap = null;
  
  private static Map emailMap = new HashMap<Object, Object>();
  
  private static Map pwdPolicyMap = new HashMap<Object, Object>();
  
  private static String useSMS;
  
  private static String useRTX;
  
  private static String accessoryEncrypt;
  
  public static SysSetupReader getInstance() {
    if (sysSetupReader == null) {
      sysSetupReader = new SysSetupReader();
      sysSetupReader.init("0");
    } 
    return sysSetupReader;
  }
  
  public static SysSetupReader getInstance(String domainId) {
    if (sysSetupReader == null) {
      sysSetupReader = new SysSetupReader();
      sysSetupReader.init(domainId);
    } 
    return sysSetupReader;
  }
  
  public void init(String domainId) {
    Connection conn = null;
    Statement stmt = null;
    try {
      sysSetupMap = new HashMap<Object, Object>();
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT DOMAIN_SERVEROPTION,DOMAIN_WORKLOG,DOMAIN_MAILBOXSIZE,DOMAIN_NETDISKSIZE,LINKMAN_SCOPE FROM ORG_DOMAIN where domain_id=" + domainId);
      String serverOption = "";
      String workLog = "";
      String mailBoxSize = "";
      String netDiskSize = "";
      String linkmanScope = "";
      if (rs.next()) {
        serverOption = rs.getString(1);
        workLog = rs.getString(2);
        mailBoxSize = rs.getString("domain_mailboxsize");
        netDiskSize = rs.getString("domain_netdisksize");
        linkmanScope = rs.getString("LINKMAN_SCOPE");
      } 
      ResultSet rste = stmt.executeQuery("SELECT domain_email_state,domain_email_id FROM ORG_DOMAIN where domain_id=" + domainId);
      String st = "";
      if (rste.next())
        st = String.valueOf(rste.getString(1)) + ";" + rste.getString(2); 
      emailMap.put("STATE", rste.getString(1));
      ResultSet rst = stmt.executeQuery("select FROM_USER,PASS_WORD,BAK_STRING1,port,encryptionType from org_mail where MAIL_ID=" + new Long(st.split(";")[1]));
      if (rst.next()) {
        emailMap.put("FROM", rst.getString(1));
        emailMap.put("PWD", rst.getString(2));
        emailMap.put("SMTP", rst.getString(3));
        emailMap.put("PORT", Integer.valueOf(rst.getInt(4)));
        emailMap.put("ENCRYPTIONTYPE", rst.getString(5));
      } 
      ResultSet pwdRs = stmt.executeQuery("SELECT pwd_image,pwd_error,pwd_strong,pwd_key FROM ORG_DOMAIN where domain_id=" + domainId);
      if (pwdRs.next()) {
        pwdPolicyMap.put("PWDIMAGE", pwdRs.getString(1));
        pwdPolicyMap.put("PWDERROR", pwdRs.getString(2));
        pwdPolicyMap.put("PWDSTRONG", pwdRs.getString(3));
        pwdPolicyMap.put("PWDKEY", pwdRs.getString(4));
      } 
      rs.close();
      rste.close();
      rst.close();
      pwdRs.close();
      conn.close();
      Map<Object, Object> map = new HashMap<Object, Object>();
      map.put("附件上传", (new StringBuilder(String.valueOf(serverOption.charAt(0)))).toString());
      map.put("使用手写意见", (new StringBuilder(String.valueOf(serverOption.charAt(1)))).toString());
      map.put("短信开通", (new StringBuilder(String.valueOf(serverOption.charAt(2)))).toString());
      map.put("图形工作流", (new StringBuilder(String.valueOf(serverOption.charAt(3)))).toString());
      map.put("RTX在线感知", (new StringBuilder(String.valueOf(serverOption.charAt(4)))).toString());
      map.put("WORD编辑", (new StringBuilder(String.valueOf(serverOption.charAt(5)))).toString());
      map.put("与网站结合", (new StringBuilder(String.valueOf(serverOption.charAt(6)))).toString());
      map.put("电子签章", (new StringBuilder(String.valueOf(serverOption.charAt(7)))).toString());
      map.put("mailBoxSize", mailBoxSize);
      map.put("netDiskSize", netDiskSize);
      sysSetupMap.put("linkmanScope", linkmanScope);
      sysSetupMap.put(domainId, map);
      sysSetupMap.put("workLog", workLog);
    } catch (Exception e) {
      try {
        if (stmt != null)
          stmt.close(); 
        if (conn != null)
          conn.close(); 
      } catch (Exception ex) {
        ex.printStackTrace();
      } 
      e.printStackTrace();
    } 
  }
  
  public Map getSysSetupMap(String domainId) {
    init(domainId);
    return (Map)sysSetupMap.get(domainId);
  }
  
  public String isMultiDomain() {
    String account = null;
    int i = 0;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT domain_account FROM ORG_DOMAIN");
      while (rs.next()) {
        i++;
        account = rs.getString(1);
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    if (i > 1)
      return null; 
    return account;
  }
  
  public String getSystemOption(String domainId) {
    StringBuffer serverOption = new StringBuffer("111111111");
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(
          "SELECT DOMAIN_SERVEROPTION FROM ORG_DOMAIN where domain_id=" + domainId);
      if (rs.next())
        serverOption = new StringBuffer(rs.getString(1)); 
      rs.close();
      rs = stmt.executeQuery("select module_log,module_serial from sec_log_module where domain_id=" + domainId);
      while (rs.next())
        serverOption.append(",").append(rs.getString(1)).append(rs.getString(2)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return serverOption.toString();
  }
  
  public String getWorkLog(String domainId) {
    String workLog = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT DOMAIN_WORKLOG FROM ORG_DOMAIN where domain_id=" + domainId);
      if (rs.next())
        workLog = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return workLog;
  }
  
  public String getExportInner(String domainId) {
    String exportInner = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT export_inner FROM ORG_DOMAIN where domain_id=" + domainId);
      if (rs.next())
        exportInner = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return exportInner;
  }
  
  public String getKq(String domainId) {
    String exportInner = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT kq FROM ORG_DOMAIN where domain_id=" + domainId);
      if (rs.next())
        exportInner = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return exportInner;
  }
  
  public boolean updateSystemOption(String domainId, String options, String workLog, String mailBoxSize, String netDiskSize) {
    boolean result = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      if (options != null) {
        String[] optionArr = options.split(",");
        conn = ds.getConnection();
        stmt = conn.createStatement();
        stmt.executeUpdate("update ORG_DOMAIN set DOMAIN_SERVEROPTION='" + optionArr[0] + "',DOMAIN_WORKLOG=" + workLog + ",domain_mailboxsize=" + mailBoxSize + ",domain_netdisksize=" + netDiskSize + " where domain_id=" + domainId);
        String flag = "0";
        String serial = "";
        for (int i = 1; i < optionArr.length; i++) {
          flag = String.valueOf(optionArr[i].charAt(0));
          serial = optionArr[i].substring(1, optionArr[i].length());
          stmt.executeUpdate("update sec_log_module set module_log=" + flag + " where  parent_serial='" + serial + "' and domain_id=" + domainId);
        } 
        stmt.close();
        conn.close();
        result = true;
        init(domainId);
      } 
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    return result;
  }
  
  public void setServerOptionsHandWrite() {
    String handwrite = "0";
    String domainId = "0";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String options = "101011000";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select DOMAIN_SERVEROPTION from  ORG_DOMAIN  where domain_id=" + domainId);
      if (rs.next())
        options = rs.getString(1); 
      rs.close();
      System.out.println("options:" + options);
      if (options.length() > 7) {
        String temp = String.valueOf(options.substring(0, 1)) + handwrite + options.substring(2, options.length());
        stmt.executeUpdate("update ORG_DOMAIN set DOMAIN_SERVEROPTION='" + temp + "' where domain_id=" + domainId);
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public void setServerOptionsSignature() {
    String signature = "0";
    String domainId = "0";
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String options = "101011000";
    try {
      conn = (new DataSourceBase()).getDataSource().getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select DOMAIN_SERVEROPTION from  ORG_DOMAIN  where domain_id=" + domainId);
      if (rs.next())
        options = rs.getString(1); 
      rs.close();
      if (options.length() > 7) {
        String temp = String.valueOf(options.substring(0, 7)) + signature + options.substring(8, options.length());
        stmt.executeUpdate("update ORG_DOMAIN set DOMAIN_SERVEROPTION='" + temp + "' where domain_id=" + domainId);
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
      ex.printStackTrace();
    } 
  }
  
  public boolean updateSystemOption(String domainId, String options, String workLog, String mailBoxSize, String netDiskSize, String attachLimit, String attachLimitSize, String isUseEmail, String emailCount, String markPWD, String useRss, String exportInner, String kq, String pwd_strong, String pwd_init, String pwd_date, String pwdError, String pwdImage, String linkmanScope, String pwdKey, String filterLimit, String filterLimitValue) {
    boolean result = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      if (options != null) {
        String[] optionArr = options.split(",");
        conn = ds.getConnection();
        stmt = conn.createStatement();
        stmt.executeUpdate("update ORG_DOMAIN set DOMAIN_SERVEROPTION='" + optionArr[0] + "',DOMAIN_WORKLOG=" + workLog + "," + 
            "domain_mailboxsize=" + mailBoxSize + ",domain_netdisksize=" + netDiskSize + ",domain_attachlimit=" + attachLimit + 
            ",domain_attachLimitSize=" + attachLimitSize + ",domain_email_state=" + isUseEmail + ",domain_email_id=" + emailCount + 
            ",markpwd=" + markPWD + ",userss=" + useRss + ",export_inner=" + exportInner + ",kq=" + kq + ",pwd_strong='" + pwd_strong + 
            "',pwd_init='" + pwd_init + "',pwd_date='" + pwd_date + "',pwd_error='" + pwdError + "',pwd_image='" + pwdImage + 
            "',LINKMAN_SCOPE='" + linkmanScope + "',pwd_key='" + pwdKey + "',domain_filterlimitcontent='" + filterLimitValue + "', domain_filterlimit='" + filterLimit + "' where domain_id=" + domainId);
        rs = stmt.executeQuery("select FROM_USER,PASS_WORD,BAK_STRING1,port,ENCRYPTIONTYPE from org_mail where MAIL_ID=" + new Long(emailCount));
        if (rs.next()) {
          emailMap.put("FROM", rs.getString(1));
          emailMap.put("PWD", rs.getString(2));
          emailMap.put("SMTP", rs.getString(3));
          emailMap.put("PORT", Integer.valueOf(rs.getInt(4)));
          emailMap.put("ENCRYPTIONTYPE", rs.getString(5));
        } 
        emailMap.put("STATE", isUseEmail);
        String flag = "0";
        String serial = "";
        for (int i = 1; i < optionArr.length; i++) {
          flag = String.valueOf(optionArr[i].charAt(0));
          serial = optionArr[i].substring(1, optionArr[i].length());
          stmt.executeUpdate("update sec_log_module set module_log=" + flag + " where  parent_serial='" + serial + "' and domain_id=" + domainId);
        } 
        stmt.executeUpdate("update org_mail set BAK_STRING=" + isUseEmail + " where  MAIL_ID=" + new Long(emailCount));
        stmt.close();
        conn.close();
        result = true;
        init(domainId);
      } 
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    return result;
  }
  
  public String getMailBoxSize(String domainId) {
    String mailBoxSize = "";
    if (sysSetupMap == null || sysSetupMap.get(domainId) == null || ((Map)sysSetupMap.get(domainId)).get("mailBoxSize") == null)
      init(domainId); 
    mailBoxSize = ((Map)sysSetupMap.get(domainId)).get("mailBoxSize").toString();
    return mailBoxSize;
  }
  
  public String getNetDiskSize(String domainId) {
    String mailBoxSize = "";
    if (sysSetupMap == null || sysSetupMap.get(domainId) == null || ((Map)sysSetupMap.get(domainId)).get("netDiskSize") == null)
      init(domainId); 
    mailBoxSize = ((Map)sysSetupMap.get(domainId)).get("netDiskSize").toString();
    return mailBoxSize;
  }
  
  public boolean hasMsg(String domainId) {
    boolean hasMsg = false;
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && ((Map)sysSetupMap.get(domainId)).get("短信开通") != null && (
      (Map)sysSetupMap.get(domainId)).get("短信开通").toString().equals("1"))
      hasMsg = true; 
    return hasMsg;
  }
  
  public boolean hasEzWorkFlow(String domainId) {
    boolean hasEzWorkFlow = false;
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && ((Map)sysSetupMap.get(domainId)).get("图形工作流") != null && (
      (Map)sysSetupMap.get(domainId)).get("图形工作流").toString().equals("1"))
      hasEzWorkFlow = true; 
    return hasEzWorkFlow;
  }
  
  public boolean hasHandSign(String domainId) {
    boolean hasHandSign = false;
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && ((Map)sysSetupMap.get(domainId)).get("使用手写意见") != null && (
      (Map)sysSetupMap.get(domainId)).get("使用手写意见").toString().equals("1"))
      hasHandSign = true; 
    return hasHandSign;
  }
  
  public boolean hasWordEdit(String domainId) {
    boolean hasWordEdit = false;
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && ((Map)sysSetupMap.get(domainId)).get("WORD编辑") != null && (
      (Map)sysSetupMap.get(domainId)).get("WORD编辑").toString().equals("1"))
      hasWordEdit = true; 
    return hasWordEdit;
  }
  
  public boolean hasRtxOnline(String domainId) {
    boolean hasRtxOnline = false;
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && ((Map)sysSetupMap.get(domainId)).get("RTX在线感知") != null && (
      (Map)sysSetupMap.get(domainId)).get("RTX在线感知").toString().equals("1"))
      hasRtxOnline = true; 
    return hasRtxOnline;
  }
  
  public boolean rtxIsUsed() {
    if (useRTX == null)
      try {
        String path = System.getProperty("user.dir");
        String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        FileInputStream configFileInputStream = new FileInputStream(
            new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(configFileInputStream);
        Element root = doc.getRootElement();
        Element node = root.getChild("RtxServer");
        useRTX = node.getAttributeValue("use");
      } catch (Exception exception) {} 
    if (useRTX.equals("1"))
      return true; 
    return false;
  }
  
  public String emailType() {
    String emailTpye = "0";
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      FileInputStream configFileInputStream = new FileInputStream(
          new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("EmailType");
      emailTpye = node.getAttributeValue("type");
    } catch (Exception exception) {}
    return emailTpye;
  }
  
  public String noteYesOrNo() {
    if (useSMS == null)
      try {
        String path = System.getProperty("user.dir");
        String configFile = String.valueOf(path) + "/jsconfig/sysconfig.xml";
        FileInputStream configFileInputStream = new FileInputStream(
            new File(configFile));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(configFileInputStream);
        Element root = doc.getRootElement();
        Element node = root.getChild("Note");
        useSMS = node.getAttributeValue("type");
      } catch (Exception exception) {} 
    return useSMS;
  }
  
  public String getAttachLimit(String domainId) {
    String attachLimit = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT domain_attachLimit,domain_attachLimitSize FROM ORG_DOMAIN where domain_id=" + 
          domainId);
      if (rs.next())
        attachLimit = String.valueOf(rs.getString(1)) + rs.getString(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return attachLimit;
  }
  
  public String getFilterLimit(String domainId) {
    String filterLimit = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT domain_filterlimit,domain_filterlimitcontent FROM ORG_DOMAIN where domain_id=" + 
          domainId);
      if (rs.next())
        filterLimit = String.valueOf(rs.getString(2)) + rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return filterLimit;
  }
  
  public String getEmail(String domainId) {
    String email = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT domain_email_state,domain_email_id FROM ORG_DOMAIN where domain_id=" + 
          domainId);
      if (rs.next())
        email = String.valueOf(rs.getString(1)) + ";" + rs.getString(2); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return email;
  }
  
  public String getMarkPWD(String domainId) {
    String flag = "0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT markpwd,pwd_image,pwd_error FROM org_domain where domain_id=" + domainId);
      if (rs.next())
        flag = String.valueOf(rs.getString(1)) + ";" + rs.getString(2) + ";" + rs.getString(3); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public String getUseRss(String domainId) {
    String flag = "0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT userss FROM org_domain where domain_id=" + domainId);
      if (rs.next())
        flag = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public long getUserNetDiskSize(String empId) {
    long flag = 0L;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT NETDISKSIZE FROM org_employee where EMP_ID=" + empId);
      if (rs.next())
        flag = Long.valueOf(String.valueOf(rs.getInt(1))).longValue(); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public long getUseNetDiskSize(String empId) {
    long flag = 0L;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT   SUM(file_size) FROM   oa_netdisk_file where FILE_OWNID=" + empId);
      if (rs.next())
        flag = Long.valueOf(String.valueOf(rs.getInt(1))).longValue(); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return flag;
  }
  
  public boolean isUseEmail() {
    String useState = (String)emailMap.get("STATE");
    if (useState != null && useState.equals("1"))
      return true; 
    return false;
  }
  
  public String getEmailCount() {
    return (String)emailMap.get("FROM");
  }
  
  public String getEmailPWD() {
    return (String)emailMap.get("PWD");
  }
  
  public int getEmailPort() {
    return Integer.valueOf(emailMap.get("PORT").toString()).intValue();
  }
  
  public String getEncryptionType() {
    return (String)emailMap.get("ENCRYPTIONTYPE");
  }
  
  public String getEmailSMTP() {
    return (String)emailMap.get("SMTP");
  }
  
  public void modEmailMap(String c, String p, String s, int port, String encryptionType) {
    if (emailMap.containsValue(c)) {
      emailMap.put("FROM", c);
      emailMap.put("PWD", p);
      emailMap.put("SMTP", s);
      emailMap.put("PORT", Integer.valueOf(port));
      emailMap.put("ENCRYPTIONTYPE", encryptionType);
    } 
  }
  
  public void removeEmailMap(String c) {
    if (emailMap.containsValue(c))
      emailMap.clear(); 
  }
  
  public static String getPwdPolicyByImage() {
    String policyImage = "0";
    try {
      if (pwdPolicyMap.get("PWDIMAGE") != null && !((String)pwdPolicyMap.get("PWDIMAGE")).equals(""))
        policyImage = (String)pwdPolicyMap.get("PWDIMAGE"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return policyImage;
  }
  
  public static String getPwdPolicyByDate() {
    String policyDate = "0";
    try {
      if (pwdPolicyMap.get("PWDDATE") != null && !((String)pwdPolicyMap.get("PWDDATE")).equals(""))
        policyDate = (String)pwdPolicyMap.get("PWDDATE"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return policyDate;
  }
  
  public static String getPwdPolicyByError() {
    String policyError = "0";
    try {
      if (pwdPolicyMap.get("PWDERROR") != null && !((String)pwdPolicyMap.get("PWDERROR")).equals(""))
        policyError = (String)pwdPolicyMap.get("PWDERROR"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return policyError;
  }
  
  public static String getPwdPolicyByStrong() {
    String policyStrong = "0";
    try {
      if (pwdPolicyMap.get("PWDSTRONG") != null && !((String)pwdPolicyMap.get("PWDSTRONG")).equals(""))
        policyStrong = (String)pwdPolicyMap.get("PWDSTRONG"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return policyStrong;
  }
  
  public static String getPwdPolicyByKey() {
    String policyKey = "0";
    try {
      if (pwdPolicyMap.get("PWDKEY") != null && !((String)pwdPolicyMap.get("PWDKEY")).equals(""))
        policyKey = (String)pwdPolicyMap.get("PWDKEY"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return policyKey;
  }
  
  public static String getPwdStrongConfig(String domainId) {
    String strongConfig = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_strong from org_domain where domain_id=" + domainId);
      if (rs.next()) {
        strongConfig = rs.getString(1);
        if (!strongConfig.equals("0"))
          strongConfig = strongConfig.substring(1, strongConfig.length() - 1).replaceAll("\\$\\$", ";"); 
      } 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return strongConfig;
  }
  
  public static String getPwdDate(String domainId) {
    String pwd_date = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_date from org_domain where domain_id=" + domainId);
      if (rs.next())
        pwd_date = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return pwd_date;
  }
  
  public static String getPwdInit(String domainId) {
    String pwd_init = "";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_init from org_domain where domain_id=" + domainId);
      if (rs.next())
        pwd_init = rs.getString(1); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return pwd_init;
  }
  
  public static String getLinkmanScope(String domainId) {
    String flag = "";
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && sysSetupMap.get("linkmanScope") != null && !"".equals(sysSetupMap.get("linkmanScope")))
      flag = (String)sysSetupMap.get("linkmanScope"); 
    return flag;
  }
  
  public static String getAccessoryEncrypt(String domainId) {
    if (accessoryEncrypt == null) {
      DataSource ds = (new DataSourceBase()).getDataSource();
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      try {
        conn = ds.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery("select accessoryEncrypt from org_domain where domain_id=" + domainId);
        if (rs.next())
          accessoryEncrypt = rs.getString(1); 
        rs.close();
        stmt.close();
        conn.close();
      } catch (Exception ex) {
        if (conn != null)
          try {
            conn.close();
          } catch (Exception exception) {} 
      } 
    } 
    return accessoryEncrypt;
  }
  
  public static void setAccessoryEncrypt(String accessoryEncrypt, String domainId) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("update org_domain set accessoryEncrypt='" + accessoryEncrypt + "' where domain_id=" + domainId);
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    SysSetupReader.accessoryEncrypt = accessoryEncrypt;
  }
}
