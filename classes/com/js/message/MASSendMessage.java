package com.js.message;

import com.mascloud.model.MoModel;
import com.mascloud.model.StatusReportModel;
import com.mascloud.model.SubmitReportModel;
import com.mascloud.sdkclient.Client;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import org.apache.log4j.Logger;

public class MASSendMessage {
  private static Logger logger = Logger.getLogger(MASSendMessage.class.getName());
  
  Client client = null;
  
  public MASSendMessage() {
    try {
      ResourceBundle resource = ResourceBundle.getBundle("info");
      String MASuserPasswd = resource.getString("MASuserPasswd");
      String MASecName = new String(resource.getString("MASecName").getBytes("ISO-8859-1"), "GBK");
      String MASuserName = resource.getString("MASuserName");
      String MASURL = resource.getString("MASURL");
      this.client = Client.getInstance();
      this.client.login(MASURL, MASuserName, MASuserPasswd, MASecName);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public int getResult() {
    return 1;
  }
  
  public int sengMess(String receiver, String content) {
    int endMes = -1;
    try {
      int sendResult = this.client.sendDSMS(new String[] { receiver }, content, "", 1, "GyP1mIr6", UUID.randomUUID().toString(), true);
      if (sendResult == 1) {
        logger.info("向" + receiver + "短信发送成功！推送结果为" + sendResult);
        System.out.println("向" + receiver + "短信发送成功！推送结果为" + sendResult);
        endMes = 1;
      } else {
        logger.info("向" + receiver + errorMsg(sendResult));
        System.out.println("向" + receiver + errorMsg(sendResult));
        endMes = 0;
      } 
      Thread t1 = new Thread() {
          public void run() {
            while (true) {
              List<SubmitReportModel> list = MASSendMessage.this.client.getSubmitReport();
              try {
                Thread.sleep(2000L);
              } catch (InterruptedException e) {
                e.printStackTrace();
              } 
            } 
          }
        };
      t1.start();
      Thread t2 = new Thread() {
          public void run() {
            while (true) {
              List<StatusReportModel> StatusReportlist = MASSendMessage.this.client.getReport();
              try {
                Thread.sleep(2000L);
              } catch (InterruptedException e) {
                e.printStackTrace();
              } 
            } 
          }
        };
      t2.start();
      Thread t3 = new Thread() {
          public void run() {
            while (true) {
              List<MoModel> lis = MASSendMessage.this.client.getMO();
              try {
                Thread.sleep(2000L);
              } catch (InterruptedException e) {
                e.printStackTrace();
              } 
            } 
          }
        };
      t3.start();
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return endMes;
  }
  
  private String errorMsg(int errorNo) {
    if (101 == errorNo)
      return "短信发送失败：短信内容为空"; 
    if (102 == errorNo)
      return "短信发送失败：号码数组为空"; 
    if (103 == errorNo)
      return "短信发送失败：号码数组为空数组"; 
    if (104 == errorNo)
      return "短信发送失败：批次短信的号码中存在非法号码， SDK带有号码的验证处理。"; 
    if (105 == errorNo)
      return "短信发送失败：未进行身份认证或认证失败，用户请确认输入的用户名，密码和企业名是否正确。"; 
    if (106 == errorNo)
      return "短信发送失败：网关签名为空，用户需要填写网关签名编号"; 
    if (107 == errorNo)
      return "短信发送失败：其它错误"; 
    if (108 == errorNo)
      return "短信发送失败：JMS异常，需要联系移动集团维护人员"; 
    if (109 == errorNo)
      return "短信发送失败：批次短信号码中存在重复号码"; 
    if (110 == errorNo)
      return "短信发送失败：发送的号码在一分钟之内全部内容（包括，号码，模板ID,模板内容都相同）都重复发送）"; 
    if (111 == errorNo)
      return "短信发送失败：扩展码错误,扩展码只能是15位以内数字或空字符串"; 
    return "短信发送失败：签名错误或普通短信不允许使用模板短信的签名编码";
  }
}
