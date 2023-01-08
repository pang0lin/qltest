package com.js.oa.audit.util;

import com.js.oa.userdb.util.RS;
import com.js.system.util.SysSetupReader;
import com.js.util.config.SystemCommon;
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

public class AuditSetupReader {
  private static AuditSetupReader sysSetupReader;
  
  private static Map sysSetupMap = null;
  
  private static Map emailMap = new HashMap<Object, Object>();
  
  private static Map pwdPolicyMap = new HashMap<Object, Object>();
  
  private static String useSMS;
  
  private static String useRTX;
  
  private static String logId = "0";
  
  public static AuditSetupReader getInstance(String log_id) {
    if (!"".equals(log_id))
      logId = log_id; 
    sysSetupReader = new AuditSetupReader();
    sysSetupReader.init("0");
    return sysSetupReader;
  }
  
  public static AuditSetupReader getInstance(String domainId, String log_id) {
    if (!"".equals(log_id))
      logId = log_id; 
    if (sysSetupReader == null) {
      sysSetupReader = new AuditSetupReader();
      sysSetupReader.init(domainId);
    } 
    return sysSetupReader;
  }
  
  public void init(String domainId) {
    Connection conn = null;
    Statement stmt = null;
    try {
      if (!"0".equals(logId)) {
        sysSetupMap = new HashMap<Object, Object>();
        DataSource ds = (new DataSourceBase()).getDataSource();
        conn = ds.getConnection();
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT DOMAIN_SERVEROPTION,DOMAIN_WORKLOG,DOMAIN_MAILBOXSIZE,DOMAIN_NETDISKSIZE,LINKMAN_SCOPE FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
        String serverOption = "111111111";
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
        ResultSet rste = stmt.executeQuery("SELECT domain_email_state,domain_email_id FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
        String st = "0;0";
        String state = "0";
        if (rste.next()) {
          st = String.valueOf(rste.getString(1)) + ";" + rste.getString(2);
          state = rste.getString(1);
        } 
        emailMap.put("STATE", state);
        ResultSet rst = stmt.executeQuery("select FROM_USER,PASS_WORD,BAK_STRING1,port,ENCRYPTIONTYPE from org_mail where MAIL_ID=" + new Long(st.split(";")[1]));
        if (rst.next()) {
          emailMap.put("FROM", rst.getString(1));
          emailMap.put("PWD", rst.getString(2));
          emailMap.put("SMTP", rst.getString(3));
          emailMap.put("PORT", Integer.valueOf(rst.getInt(4)));
          emailMap.put("ENCRYPTIONTYPE", rst.getString(5));
        } 
        ResultSet pwdRs = stmt.executeQuery("SELECT pwd_image,pwd_error,pwd_strong,pwd_key FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
      } 
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
      rs = stmt.executeQuery("SELECT domain_account FROM audit_switchlog where audit_log_id=" + logId);
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
          "SELECT DOMAIN_SERVEROPTION FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
      if (rs.next())
        serverOption = new StringBuffer(rs.getString(1)); 
      rs.close();
      rs = stmt.executeQuery("select module_log,module_serial from audit_log_module where domain_id=" + domainId);
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
      rs = stmt.executeQuery("SELECT DOMAIN_WORKLOG FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
      rs = stmt.executeQuery("SELECT export_inner FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
      rs = stmt.executeQuery("SELECT kq FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
        stmt.executeUpdate("update audit_switchlog set DOMAIN_SERVEROPTION='" + optionArr[0] + "',DOMAIN_WORKLOG=" + workLog + ",domain_mailboxsize=" + mailBoxSize + ",domain_netdisksize=" + netDiskSize + " where domain_id=" + domainId + " and audit_log_id=" + logId);
        String flag = "0";
        String serial = "";
        for (int i = 1; i < optionArr.length; i++) {
          flag = String.valueOf(optionArr[i].charAt(0));
          serial = optionArr[i].substring(1, optionArr[i].length());
          stmt.executeUpdate("update audit_log_module set module_log=" + flag + " where  parent_serial='" + serial + "' and domain_id=" + domainId);
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
  
  public String insertLog(String empId, String empName, String orgId, String autoAudit) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    String id = "";
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      String sql = "";
      if (databaseType.indexOf("oracle") >= 0) {
        rs = stmt.executeQuery("select hibernate_sequence.nextval from dual");
        if (rs.next())
          id = rs.getString(1); 
        if ("1".equals(autoAudit)) {
          sql = "insert into audit_log (log_id,submit_empid,submit_empname,submit_orgId,submit_time,audit_module,audit_status,isChecked,CHECK_TIME,CHECK_EMPID,CHECK_EMPNAME) values (" + 
            id + "," + empId + ",'" + empName + "'," + orgId + ",sysdate,1,1,1,sysdate,0,'系统自动审核')";
        } else {
          sql = "insert into audit_log (log_id,submit_empid,submit_empname,submit_orgId,submit_time,audit_module,audit_status,isChecked) values (" + 
            id + "," + empId + ",'" + empName + "'," + orgId + ",sysdate,1,0,0)";
        } 
        stmt.executeUpdate(sql);
      } else {
        if ("1".equals(autoAudit)) {
          sql = "insert into audit_log (submit_empid,submit_empname,submit_orgId,submit_time,audit_module,audit_status,isChecked,CHECK_TIME,CHECK_EMPID,CHECK_EMPNAME) values (" + 
            empId + ",'" + empName + "'," + orgId + ",now(),1,1,1,now(),0,'系统自动审核')";
        } else {
          sql = "insert into audit_log (submit_empid,submit_empname,submit_orgId,submit_time,audit_module,audit_status,isChecked) values (" + 
            empId + ",'" + empName + "'," + orgId + ",now(),1,0,0)";
        } 
        stmt.executeUpdate(sql);
        sql = "SELECT LAST_INSERT_ID()";
        rs = stmt.executeQuery(sql);
        if (rs.next())
          id = rs.getString(1); 
        rs.close();
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    return id;
  }
  
  public boolean updateSystemOption(String domainId, String options, String workLog, String mailBoxSize, String netDiskSize, String attachLimit, String attachLimitSize, String isUseEmail, String emailCount, String markPWD, String useRss, String exportInner, String kq, String pwd_strong, String pwd_init, String pwd_date, String pwdError, String pwdImage, String linkmanScope, String pwdKey, String filterLimt, String filterLimitValue) {
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
        String sql = "";
        String databaseType = SystemCommon.getDatabaseType();
        if (databaseType.indexOf("oracle") >= 0) {
          sql = "insert into audit_switchlog (SWITCH_ID,DOMAIN_SERVEROPTION,DOMAIN_WORKLOG,domain_mailboxsize,domain_netdisksize,domain_attachlimit,domain_attachLimitSize,domain_email_state,domain_email_id,markpwd,userss,export_inner,kq,pwd_strong,pwd_init,pwd_date,pwd_error,pwd_image,LINKMAN_SCOPE,pwd_key,domain_id,domain_filterlimitcontent,domain_filterlimit,options,audit_log_id) values (hibernate_sequence.nextval,'" + 

            
            optionArr[0] + "'," + workLog + "," + mailBoxSize + "," + netDiskSize + "," + attachLimit + "," + attachLimitSize + "," + 
            isUseEmail + "," + emailCount + "," + markPWD + "," + useRss + "," + exportInner + "," + kq + ",'" + pwd_strong + "','" + pwd_init + "','" + pwd_date + "','" + 
            pwdError + "','" + pwdImage + "','" + linkmanScope + "','" + pwdKey + "'," + domainId + "," + filterLimitValue + "," + filterLimt + ",'" + options + "'," + logId + "')";
        } else {
          sql = "insert into audit_switchlog (DOMAIN_SERVEROPTION,DOMAIN_WORKLOG,domain_mailboxsize,domain_netdisksize,domain_attachlimit,domain_attachLimitSize,domain_email_state,domain_email_id,markpwd,userss,export_inner,kq,pwd_strong,pwd_init,pwd_date,pwd_error,pwd_image,LINKMAN_SCOPE,pwd_key,domain_id,domain_filterlimitcontent,domain_filterlimit,options,audit_log_id) values ('" + 

            
            optionArr[0] + "'," + workLog + "," + mailBoxSize + "," + netDiskSize + "," + attachLimit + "," + attachLimitSize + "," + 
            isUseEmail + "," + emailCount + "," + markPWD + "," + useRss + "," + exportInner + "," + kq + ",'" + pwd_strong + "','" + pwd_init + "','" + pwd_date + "','" + 
            pwdError + "','" + pwdImage + "','" + linkmanScope + "','" + pwdKey + "'," + domainId + ",'" + filterLimitValue + "','" + filterLimt + "','" + options + "'," + logId + "')";
        } 
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        result = true;
        init(domainId);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    return result;
  }
  
  public void auditOption(String checked, String empId, String empName) {
    String sql = "select domain_id,options,DOMAIN_WORKLOG,domain_mailboxsize,domain_netdisksize,domain_attachlimit,domain_attachLimitSize,domain_email_state,domain_email_id,markpwd,userss,export_inner,kq,pwd_strong,pwd_init,pwd_date,pwd_error,pwd_image,LINKMAN_SCOPE,pwd_key,domain_filterlimit,domain_filterlimitcontent  from audit_switchlog where audit_log_id=" + 

      
      logId;
    DataSourceBase ds = new DataSourceBase();
    ResultSet rs = null;
    try {
      ds.begin();
      rs = ds.executeQuery(sql);
      String[][] audit = RS.toStrArr2(rs, 23);
      auditOption(audit[0][0], audit[0][1], audit[0][2], audit[0][3], audit[0][4], audit[0][5], audit[0][6], audit[0][7], 
          audit[0][8], audit[0][9], audit[0][10], audit[0][11], audit[0][12], audit[0][13], audit[0][14], audit[0][15], 
          audit[0][16], audit[0][17], audit[0][18], audit[0][19], audit[0][20], audit[0][21]);
      setLog(checked, empId, empName);
      rs.close();
      ds.end();
    } catch (Exception ex) {
      ds.end();
      ex.printStackTrace();
    } 
  }
  
  public boolean auditOption(String domainId, String options, String workLog, String mailBoxSize, String netDiskSize, String attachLimit, String attachLimitSize, String isUseEmail, String emailCount, String markPWD, String useRss, String exportInner, String kq, String pwd_strong, String pwd_init, String pwd_date, String pwdError, String pwdImage, String linkmanScope, String pwdKey) {
    return auditOption(domainId, options, workLog, 
        mailBoxSize, netDiskSize, attachLimit, attachLimitSize, 
        isUseEmail, emailCount, markPWD, useRss, exportInner, 
        kq, pwd_strong, pwd_init, pwd_date, pwdError, 
        pwdImage, linkmanScope, pwdKey, "0", "");
  }
  
  public boolean auditOption(String domainId, String options, String workLog, String mailBoxSize, String netDiskSize, String attachLimit, String attachLimitSize, String isUseEmail, String emailCount, String markPWD, String useRss, String exportInner, String kq, String pwd_strong, String pwd_init, String pwd_date, String pwdError, String pwdImage, String linkmanScope, String pwdKey, String filterlimit, String filterlimitcontent) {
    boolean result = false;
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      if (options != null) {
        conn = ds.getConnection();
        stmt = conn.createStatement();
        String[] optionArr = options.split(",");
        stmt.executeUpdate("update ORG_DOMAIN set DOMAIN_SERVEROPTION='" + optionArr[0] + "',DOMAIN_WORKLOG=" + workLog + "," + 
            "domain_mailboxsize=" + mailBoxSize + ",domain_netdisksize=" + netDiskSize + ",domain_attachlimit=" + attachLimit + 
            ",domain_attachLimitSize=" + attachLimitSize + ",domain_email_state=" + isUseEmail + ",domain_email_id=" + emailCount + 
            ",markpwd=" + markPWD + ",userss=" + useRss + ",export_inner=" + exportInner + ",kq=" + kq + ",pwd_strong='" + pwd_strong + 
            "',pwd_init='" + pwd_init + "',pwd_date='" + pwd_date + "',pwd_error='" + pwdError + "',pwd_image='" + pwdImage + 
            "',LINKMAN_SCOPE='" + linkmanScope + "',pwd_key='" + pwdKey + "',domain_filterlimit='" + filterlimit + "'," + 
            "domain_filterlimitcontent='" + filterlimitcontent + "' where domain_id=" + domainId);
        rs = stmt.executeQuery("select FROM_USER,PASS_WORD,BAK_STRING1,port from org_mail where MAIL_ID=" + new Long(emailCount));
        if (rs.next()) {
          emailMap.put("FROM", rs.getString(1));
          emailMap.put("PWD", rs.getString(2));
          emailMap.put("SMTP", rs.getString(3));
          emailMap.put("PORT", Integer.valueOf(rs.getInt(4)));
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
        SysSetupReader.getInstance().init(domainId);
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
    return result;
  }
  
  public void setLog(String checked, String empId, String empName) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      String databaseType = SystemCommon.getDatabaseType();
      if (databaseType.indexOf("oracle") >= 0) {
        stmt.executeUpdate("update audit_log set check_empId=" + empId + ",check_empname='" + empName + "'," + 
            "check_time=sysdate,ischecked=1,audit_status=" + checked + " where log_id=" + logId);
      } else {
        stmt.executeUpdate("update audit_log set check_empId=" + empId + ",check_empname='" + empName + "'," + 
            "check_time=now(),ischecked=1,audit_status=" + checked + " where log_id=" + logId);
      } 
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      ex.printStackTrace();
      if (conn != null)
        try {
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }  
    } 
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
      rs = stmt.executeQuery("SELECT domain_attachLimit,domain_attachLimitSize FROM audit_switchlog where domain_id=" + 
          domainId + " and audit_log_id=" + logId);
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
      rs = stmt.executeQuery("SELECT domain_filterlimit,domain_filterlimitcontent FROM audit_switchlog where domain_id=" + 
          domainId + " and audit_log_id=" + logId);
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
    String email = "0;0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT domain_email_state,domain_email_id FROM audit_switchlog where domain_id=" + 
          domainId + " and audit_log_id=" + logId);
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
    String flag = "0;0;0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT markpwd,pwd_image,pwd_error FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
      rs = stmt.executeQuery("SELECT userss FROM audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
  
  public String getEmailSMTP() {
    return (String)emailMap.get("SMTP");
  }
  
  public int getEmailPort() {
    return Integer.valueOf(emailMap.get("PORT").toString()).intValue();
  }
  
  public void modEmailMap(String c, String p, String s, int port) {
    if (emailMap.containsValue(c)) {
      emailMap.put("FROM", c);
      emailMap.put("PWD", p);
      emailMap.put("SMTP", s);
      emailMap.put("PORT", Integer.valueOf(port));
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
    String strongConfig = "0";
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("select pwd_strong from audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
      rs = stmt.executeQuery("select pwd_date from audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
      rs = stmt.executeQuery("select pwd_init from audit_switchlog where domain_id=" + domainId + " and audit_log_id=" + logId);
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
  
  public static boolean getLinkmanScope(String domainId) {
    boolean flag = true;
    if (sysSetupMap != null && sysSetupMap.get(domainId) != null && sysSetupMap.get("linkmanScope") != null && !"".equals(sysSetupMap.get("linkmanScope")))
      flag = ((String)sysSetupMap.get("linkmanScope")).equals("0"); 
    return flag;
  }
}
