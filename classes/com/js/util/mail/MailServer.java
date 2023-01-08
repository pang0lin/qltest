package com.js.util.mail;

import com.js.oa.userdb.util.DbOpt;
import com.js.util.config.SystemCommon;
import java.io.File;
import java.net.HttpURLConnection;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailServer {
  private MimeMessage mimeMsg = null;
  
  private Session session = null;
  
  private Properties props = null;
  
  private String username;
  
  private String password;
  
  private String smtp = "";
  
  private String mailSubject = "";
  
  private String body = "";
  
  private boolean b_html = false;
  
  private String from;
  
  private String to;
  
  private String starttls = "false";
  
  private String fileAffi = "";
  
  private int port = 25;
  
  private Multipart mp = null;
  
  public MailServer() {
    setSmtpHost(MailConfig.getEmailSMTP());
    createMimeMessage();
    setNamePass(MailConfig.getEmailCount(), MailConfig.getEmailPWD());
    setFrom(MailConfig.getEmailCount());
    setNeedAuth(true);
  }
  
  public MailServer(String stmp, String username, String password, int port, String encryptionType) {
    setSmtpHost(stmp);
    createMimeMessage();
    setNamePass(username, password);
    setFrom(username);
    setNeedAuth(true);
    setPort(port);
    setEncryptionType(encryptionType);
  }
  
  public void setPort(int port) {
    this.port = port;
    if (this.port == 0)
      this.port = 25; 
  }
  
  public void setEncryptionType(String encryptionType) {
    if ("TLS".equalsIgnoreCase(encryptionType)) {
      this.props.put("mail.smtp.starttls.enable", "true");
      this.starttls = "true";
    } 
  }
  
  public void setSmtpHost(String hostName) {
    if (this.props == null)
      this.props = System.getProperties(); 
    this.props.put("mail.smtp.host", hostName);
    this.smtp = hostName;
  }
  
  public boolean createMimeMessage() {
    try {
      this.session = Session.getDefaultInstance(this.props, null);
    } catch (Exception e) {
      return false;
    } 
    try {
      this.mimeMsg = new MimeMessage(this.session);
      this.mp = (Multipart)new MimeMultipart();
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public void setNeedAuth(boolean need) {
    if (this.props == null)
      this.props = System.getProperties(); 
    if (need) {
      this.props.put("mail.smtp.auth", "true");
    } else {
      this.props.put("mail.smtp.auth", "false");
    } 
  }
  
  public void setNamePass(String name, String pass) {
    this.username = name;
    this.password = pass;
  }
  
  public boolean setSubject(String mailSubject) {
    this.mailSubject = mailSubject;
    try {
      this.mimeMsg.setSubject(mailSubject);
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean setBody(String mailBody, boolean b_html) {
    this.body = mailBody;
    this.b_html = b_html;
    try {
      MimeBodyPart mimeBodyPart = new MimeBodyPart();
      if (b_html) {
        mimeBodyPart.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody, "text/html;charset=UTF-8");
      } else {
        mimeBodyPart.setText(mailBody);
      } 
      this.mp.addBodyPart((BodyPart)mimeBodyPart);
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean addFileAffix(String[] filenames) {
    MimeBodyPart bp = null;
    FileDataSource fileds = null;
    File file = null;
    try {
      if (filenames != null && filenames.length >= 1) {
        int i;
        for (i = 0; i < filenames.length; i++) {
          String fileName = filenames[i].replaceAll("\\\\", "/");
          file = new File(fileName);
          if (file.exists()) {
            bp = new MimeBodyPart();
            fileds = new FileDataSource(fileName);
            bp.setDataHandler(new DataHandler((DataSource)fileds));
            bp.setFileName(MimeUtility.encodeWord(fileds.getName(), "UTF-8", null));
            if (fileds.getName().toLowerCase().endsWith(".png") || 
              fileds.getName().toLowerCase().endsWith(".jpg") || 
              fileds.getName().toLowerCase().endsWith(".jpeg") || 
              fileds.getName().toLowerCase().endsWith(".gif") || 
              fileds.getName().toLowerCase().endsWith(".bmp"))
              bp.setContentID(fileds.getName()); 
            this.mp.addBodyPart((BodyPart)bp);
          } else {
            return false;
          } 
        } 
        if (filenames != null && filenames.length >= 1)
          for (i = 0; i < filenames.length; i++) {
            String fileName = filenames[i];
            this.fileAffi = String.valueOf(this.fileAffi) + fileName + ";";
          }  
        if (this.fileAffi.endsWith(";"))
          this.fileAffi = this.fileAffi.substring(0, this.fileAffi.length() - 2); 
      } 
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean setFrom(String from) {
    this.from = from;
    try {
      this.mimeMsg.setFrom((Address)new InternetAddress(from));
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean setTo(String to) {
    this.to = to;
    if (to == null)
      return false; 
    try {
      this.mimeMsg.setRecipients(Message.RecipientType.TO, (Address[])InternetAddress.parse(to));
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean setCopyTo(String copyto) {
    if (copyto == null)
      return false; 
    try {
      this.mimeMsg.setRecipients(Message.RecipientType.CC, 
          (Address[])InternetAddress.parse(copyto));
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean setSecretTo(String scretto) {
    if (scretto == null)
      return false; 
    try {
      this.mimeMsg.setRecipients(Message.RecipientType.BCC, 
          (Address[])InternetAddress.parse(scretto));
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean sendout(String sendType) {
    try {
      this.mimeMsg.setContent(this.mp);
      this.mimeMsg.saveChanges();
      Session mailSession = Session.getInstance(this.props, null);
      Transport transport = mailSession.getTransport("smtp");
      if (sendType.equals("SCS")) {
        transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.TO));
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.CC));
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.BCC));
      } 
      if (sendType.equals("SC")) {
        transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.TO));
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.CC));
      } 
      if (sendType.equals("SS")) {
        transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.TO));
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.BCC));
      } 
      if (sendType.equals("CS")) {
        transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.TO));
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.BCC));
      } 
      if (sendType.equals("TO"))
        if (!SystemCommon.isUseMailServer()) {
          transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
          transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.TO));
        } else {
          HttpURLConnection httpURLConn = null;
          try {
            String databaseType = SystemCommon.getDatabaseType();
            DbOpt db = new DbOpt();
            String sql = "";
            if ("oracle".equalsIgnoreCase(databaseType)) {
              sql = "insert into sys_mailInfo(id,smtp,username,password,starttls,fromaccount,toaccount,port,mailsubject, mailbody,b_html,fileaffix,status)values(hibernate_sequence.nextval,'" + 
                this.smtp + "','" + this.username + 
                "','" + this.password + "','" + this.starttls + "','" + this.from + "','" + this.to + "','" + this.port + "','" + this.mailSubject + 
                "','" + this.body + "','" + this.b_html + "','" + this.fileAffi + "','0')";
            } else {
              sql = "insert into sys_mailInfo(smtp,username,password,starttls,fromaccount,toaccount,port,mailsubject, mailbody,b_html,fileaffix,status)values('" + 
                this.smtp + "','" + this.username + 
                "','" + this.password + "','" + this.starttls + "','" + this.from + "','" + this.to + "','" + this.port + "','" + this.mailSubject + 
                "','" + this.body + "','" + this.b_html + "','" + this.fileAffi + "','0')";
            } 
            try {
              db.executeUpdate(sql);
              db.close();
            } catch (Exception e) {
              db.close();
              e.printStackTrace();
            } 
          } catch (Exception e) {
            e.printStackTrace();
          } finally {
            if (httpURLConn != null)
              httpURLConn.disconnect(); 
          } 
        }  
      if (sendType.equals("CC")) {
        transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.CC));
      } 
      if (sendType.equals("BCC")) {
        transport.connect((String)this.props.get("mail.smtp.host"), this.port, this.username, this.password);
        transport.sendMessage((Message)this.mimeMsg, this.mimeMsg.getRecipients(Message.RecipientType.BCC));
      } 
      transport.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("777777777777777777:" + e.toString());
      return false;
    } 
  }
}
