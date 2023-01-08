package com.other;

import com.js.util.mail.Mail;
import com.js.util.mail.MailSender;
import com.js.util.util.DataSourceBase;
import com.js.util.util.DateHelper;
import com.js.util.util.PropertiesTrans;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import javax.sql.DataSource;

public class StaticOtherParam {
  public static String getEmailString(int limited, boolean state) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer emailBuffer = new StringBuffer("");
    StringBuffer idBuffer = new StringBuffer("");
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT id,email_url FROM sys_client_email where DATEDIFF(curdate(),send_time)>30 or send_time is null or send_time='' order by id limit 0," + limited);
      while (rs.next()) {
        emailBuffer.append(rs.getString(2)).append(",");
        idBuffer.append(rs.getInt(1)).append(",");
      } 
      if (state && 
        !idBuffer.toString().equals("")) {
        Date date = new Date();
        stmt.executeUpdate("update sys_client_email set send_time='" + DateHelper.date2String(date, null) + "' where id in(" + idBuffer.substring(0, idBuffer.toString().length() - 1).toString() + ")");
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
    return emailBuffer.toString();
  }
  
  public static String getEmailCount() {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer emailBuffer = new StringBuffer("");
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT mail_id,bak_String1,from_user,pass_word,port,encryptionType FROM org_mail order by flag limit 0,1 ");
      if (rs.next())
        emailBuffer.append(rs.getString(2)).append(",").append(rs.getString(3)).append(",").append(rs.getString(4)).append(rs.getString(5)).append(",").append(rs.getString(6)); 
      stmt.executeUpdate("update org_mail set flag=flag+1 where mail_id=" + rs.getInt(1));
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return emailBuffer.substring(0, emailBuffer.length()).toString();
  }
  
  public static String getEmailText() {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer emailBuffer = new StringBuffer();
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery("SELECT title,content FROM sys_client_email_content");
      if (rs.next())
        emailBuffer.append(rs.getString(1)).append(",").append(rs.getString(2)); 
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return emailBuffer.toString();
  }
  
  public static String updateEmailText(String title, String text) {
    DataSource ds = (new DataSourceBase()).getDataSource();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    StringBuffer emailBuffer = new StringBuffer();
    try {
      conn = ds.getConnection();
      stmt = conn.createStatement();
      stmt.executeUpdate("update sys_client_email_content set title='" + title + "',content='" + text + "' ");
      rs.close();
      stmt.close();
      conn.close();
    } catch (Exception ex) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception exception) {} 
    } 
    return emailBuffer.toString();
  }
  
  public static void send() {
    String mailString = getEmailString(Integer.parseInt(PropertiesTrans.getValueByKey("desSendNum")), false);
    String mailCount = getEmailCount();
    String mailContent = getEmailText();
    try {
      if (!mailCount.equals("") && !mailString.equals("")) {
        Mail mailVO = new Mail();
        String[] fileAffix = (String[])null;
        mailVO.setFileAffix(fileAffix);
        mailVO.setSubjectTitle(mailContent.split(",")[0]);
        mailVO.setBoby(mailContent.substring(mailContent.indexOf(",") + 1, mailContent.length()));
        mailVO.setHtml(true);
        mailVO.setSendTo(mailString.substring(0, mailString.length() - 1));
        String returnFlag = MailSender.autoSend(mailVO, mailCount.split(",")[0], mailCount.split(",")[1], mailCount.split(",")[2], Integer.valueOf(mailCount.split(",")[3]).intValue(), mailCount.split(",")[4]);
        if (returnFlag.equals("6000")) {
          getEmailString(Integer.parseInt(PropertiesTrans.getValueByKey("desSendNum")), true);
        } else {
          System.out.println("-----------发送失败------------");
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
