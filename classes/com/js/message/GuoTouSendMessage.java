package com.js.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class GuoTouSendMessage {
  private static Logger logger = Logger.getLogger(GuoTouSendMessage.class.getName());
  
  private String SENDER = "sdgzkg";
  
  private String PASSWORD = "sdgzkg@2017";
  
  public int sendMessProxy(String receiver, String content) {
    Calendar c = Calendar.getInstance();
    int hour = c.get(11);
    if (hour >= 8 && hour <= 20)
      return sendMessGuotou(receiver, content); 
    return sendMessGuotou(receiver, content);
  }
  
  private int sendMessGuotou(String receiver, String content) {
    int result = -1;
    try {
      String smsInfo = "";
      String userid = "";
      String extno = "";
      URL url = new URL("http://sms2.snailsoft.cn/sms.aspx");
      HttpURLConnection connection = (HttpURLConnection)url.openConnection();
      connection.setDoOutput(true);
      OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "utf-8");
      String SENDTIME = "";
      content = (new StringBuilder(String.valueOf(content))).toString();
      content = URLEncoder.encode(content, "UTF-8");
      smsInfo = "action=send&userid=" + 
        userid + 
        "&account=" + this.SENDER + 
        "&password=" + this.PASSWORD + 
        "&mobile=" + receiver + 
        "&content=" + content + 
        "&sendTime=" + SENDTIME + 
        "&extno=" + extno;
      out.write(smsInfo);
      out.flush();
      out.close();
      InputStream in = connection.getInputStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      result = resultanalysis(reader, receiver);
      in.close();
      reader.close();
      connection.disconnect();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return result;
  }
  
  private int resultanalysis(BufferedReader res, String receiver) {
    int endMsg = -1;
    try {
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(res);
      Element root = doc.getRootElement();
      Element node = root.getChild("returnsms");
      String result = node.getChild("returnstatus").getValue();
      String message = node.getChild("message").getValue();
      String remainpoint = node.getChild("remainpoint").getValue();
      String taskID = node.getChild("taskID").getValue();
      if (result.toLowerCase().equals("success")) {
        logger.info("向" + receiver + "发送短信成功，状态码为" + result);
        System.out.println("向" + receiver + "发送短信成功,本次任务序列：" + taskID + ",剩余余额：" + remainpoint);
        endMsg = 1;
      } else {
        logger.error("向" + receiver + "发送短信失败,相关错误描述：" + message);
        System.err.println("向" + receiver + "发送短信失败,相关错误描述：" + message);
        endMsg = 0;
      } 
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return endMsg;
  }
}
