package com.js.oa.message.service;

import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.service.ChatService;
import com.js.oa.hr.officemanager.service.EmployeeBD;
import com.js.system.service.messages.MessagesBD;
import com.js.system.service.usermanager.UserBD;
import com.js.system.vo.messages.MessagesVO;
import com.js.system.vo.usermanager.EmployeeVO;
import com.js.util.mail.Mail;
import com.js.util.mail.MailConfig;
import com.js.util.mail.MailSender;
import com.js.util.util.DataSourceBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.sql.DataSource;

public class MessageService {
  public static void main(String[] args) {
    System.out.println((new MessageService()).sendMessage("zhs", "zhs", "", ""));
  }
  
  public String sendChat(String to, String content) {
    String toID = "";
    String emp_Name = "";
    Connection conn = null;
    try {
      DataSource ds = (new DataSourceBase()).getDataSource();
      conn = ds.getConnection();
      PreparedStatement pstmt = conn.prepareStatement("select emp_id,empname from org_employee  where UserAccounts=?");
      pstmt.setString(1, to);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        toID = rs.getString(1);
        emp_Name = rs.getString(2);
      } 
      rs.close();
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      if (conn != null)
        try {
          conn.close();
        } catch (Exception err) {
          err.printStackTrace();
        }  
      e.printStackTrace();
    } 
    if ("".equals(toID)) {
      System.out.println("登录名为【" + to + "】的用户不存在");
      return "1";
    } 
    String chatToId = "$" + toID + "$";
    ChatPO chatPO = new ChatPO();
    chatPO.setChatContent(content);
    chatPO.setChatTime(new Date());
    chatPO.setSenderId("0");
    chatPO.setSenderName("系统提醒");
    chatPO.setChatTo(String.valueOf(emp_Name) + ",");
    chatPO.setChatAttachsize("0");
    ChatService chatService = new ChatService();
    if (chatService.sendChat(chatPO, null, null, chatToId))
      return "0"; 
    return "1";
  }
  
  public String sendWebMail(String to, String title, String content) {
    EmployeeBD bd = new EmployeeBD();
    String toID = bd.getEmpIdByAccounts(to);
    if ("".equals(toID)) {
      System.out.println("登录名为【" + to + "】的用户不存在");
      return "1";
    } 
    try {
      EmployeeVO employeeVO = new EmployeeVO();
      employeeVO = (new UserBD()).getEmpByid(new Long(toID));
      String emailUrl = employeeVO.getEmpEmail();
      if (emailUrl != null && !"".equals(emailUrl) && 
        !"null".equals(emailUrl)) {
        Mail wm = new Mail();
        wm.setSendTo(emailUrl);
        wm.setBoby(content);
        wm.setSubjectTitle(title);
        MailSender.send(wm, MailConfig.getEmailSMTP(), 
            MailConfig.getEmailCount(), MailConfig.getEmailPWD(), 
            MailConfig.getEmailPort(), 
            MailConfig.getEncryptionType());
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return "1";
    } 
    return "0";
  }
  
  public String sendSMS(String to, String content) {
    EmployeeBD bd = new EmployeeBD();
    String toID = bd.getEmpIdByAccounts(to);
    if ("".equals(toID)) {
      System.out.println("登录名为【" + to + "】的用户不存在");
      return "1";
    } 
    try {
      MessageBD messageSend = new MessageBD();
      messageSend.modelSendMsg((
          new Long(toID)).toString(), 
          content, 
          "", "0", "0", new Date(), Long.valueOf(0L));
    } catch (Exception e) {
      e.printStackTrace();
      return "1";
    } 
    return "0";
  }
  
  public String sendMessage(String to, String title, String sysType, String url) {
    try {
      EmployeeBD bd = new EmployeeBD();
      String toID = bd.getEmpIdByAccounts(to);
      if ("".equals(toID)) {
        System.out.println("登录名为【" + to + "】的用户不存在");
        return "1";
      } 
      Date now = new Date();
      MessagesVO msgVO = new MessagesVO();
      msgVO.setMessage_date_begin(now);
      msgVO.setMessage_date_end(new Date("2050/1/1"));
      msgVO.setMessage_send_UserId(0L);
      msgVO.setMessage_send_UserName("系统提醒");
      msgVO.setMessage_show(1);
      msgVO.setMessage_status(1);
      msgVO.setMessage_time(now);
      msgVO.setMessage_url(url);
      msgVO.setMessage_title(title);
      msgVO.setMessage_toUserId(Long.valueOf(toID).longValue());
      msgVO.setMessage_type(sysType);
      (new MessagesBD()).messageAdd(msgVO);
    } catch (Exception e) {
      e.printStackTrace();
      return "1";
    } 
    return "0";
  }
}
