package com.js.sms;

import GSMModem.GSMModem;
import com.js.sms.mac.ChinaMobile;
import com.js.sms.mac.JassionMac;
import com.js.util.util.PropertiesTrans;
import sms.ClientForFeixin;
import sms.ClientForGuangda;
import sms.MasSMS;

public class WXGSMModem {
  private GSMModem gsmModem = null;
  
  private String device;
  
  private String baud;
  
  private String sn;
  
  public boolean modemInit() {
    this.device = PropertiesTrans.getValueByKey("device");
    this.baud = PropertiesTrans.getValueByKey("baud");
    this.sn = PropertiesTrans.getValueByKey("sn");
    this.gsmModem = new GSMModem();
    boolean res = false;
    try {
      this.gsmModem.setDevice(this.device);
      this.gsmModem.setBaudrate(this.baud);
      this.gsmModem.setSn(this.sn);
      res = true;
    } catch (Exception ex) {
      System.out.println("请正确的设定通讯串口、波特率、注册码、接收号码等!");
    } 
    return res;
  }
  
  public boolean connect() {
    boolean res = false;
    try {
      res = this.gsmModem.GSMModemInit();
    } catch (Exception ex) {
      System.out.println("gsm modem connection error:" + this.gsmModem.GSMModemGetErrorMsg());
    } 
    return res;
  }
  
  public boolean release() {
    boolean res = false;
    try {
      this.gsmModem.GSMModemRelease();
      res = true;
    } catch (Exception ex) {
      System.out.println("gsm modem release error!");
    } 
    return res;
  }
  
  public boolean send(String[] receiver, String content, String sender) {
    boolean res = false;
    int messageNum = 0;
    if (receiver != null)
      messageNum = receiver.length; 
    while (messageNum > 0) {
      boolean state = false;
      if (!this.gsmModem.GSMModemIsConn()) {
        for (int i = 0; i < 5; i++) {
          if (this.gsmModem.GSMModemInit()) {
            state = true;
            break;
          } 
        } 
      } else {
        state = true;
      } 
      if (state) {
        if (content != null)
          this.gsmModem.GSMModemSMSsend(null, GSMModem.ENCodeing_GB2312, content, receiver[messageNum - 1], false); 
        res = true;
      } else {
        res = false;
        break;
      } 
      messageNum--;
    } 
    return res;
  }
  
  public boolean sendSinge(String receiver, String content, String sender) {
    String smsAPI = PropertiesTrans.getValueByKey("smsAPI");
    boolean flag = false;
    if (smsAPI.trim().equals("1")) {
      int sendState = JassionMac.getInstance().sendSM(receiver, content);
      if (sendState == 0)
        flag = true; 
    } else if (smsAPI.trim().equals("0")) {
      modemInit();
      flag = doSendSinge(receiver, content, sender);
      release();
    } else if (smsAPI.trim().equals("2")) {
      String state = ChinaMobile.sendShortMes(receiver, content);
      if (state.toLowerCase().indexOf("ok") >= 0)
        flag = true; 
    } else if (!smsAPI.trim().equals("4")) {
      if (smsAPI.trim().equals("5")) {
        ClientForFeixin client = new ClientForFeixin();
        String SendMsgResult = client.jinpanfeixin_sendMessage(receiver, content, sender);
        if ("true".equals(SendMsgResult)) {
          flag = true;
          System.out.println("发送飞信成功！");
        } else {
          flag = false;
          System.out.println("发送飞信失败！");
        } 
      } else if (smsAPI.trim().equals("7")) {
        ClientForGuangda guangda = new ClientForGuangda();
        boolean guangdaFlag = guangda.ClientForGuangdaSendMessage(receiver, content, sender);
        if (guangdaFlag) {
          flag = true;
          System.out.println("发送短信成功！");
        } else {
          flag = false;
          System.out.println("发送短信失败！");
        } 
      } 
    } 
    return flag;
  }
  
  public String royaMas(String receiver, String content) {
    return MasSMS.sendSMSReturn(receiver, content);
  }
  
  public String royoSendFlag(String requestIdentifier) {
    return MasSMS.getSmsDeliveryStatus(requestIdentifier);
  }
  
  public boolean doSendSinge(String receiver, String content, String sender) {
    boolean res = false;
    try {
      boolean state = false;
      if (!this.gsmModem.GSMModemIsConn()) {
        for (int i = 0; i < 5; i++) {
          if (this.gsmModem.GSMModemInit()) {
            state = true;
            break;
          } 
        } 
      } else {
        state = true;
      } 
      if (state) {
        if (content != null)
          if (content.length() <= 70) {
            this.gsmModem.GSMModemSMSsend(null, GSMModem.ENCodeing_GB2312, content, receiver, false);
          } else {
            int num = content.length() / 65;
            if (content.length() % 65 > 0)
              num++; 
            for (int i = 0; i < num; i++) {
              if (content.length() > 65) {
                temp = content.substring(0, 65);
                content = content.substring(65);
              } else {
                temp = content;
                content = "";
              } 
              String temp = "(" + (i + 1) + "/" + num + ")" + temp;
              this.gsmModem.GSMModemSMSsend(null, GSMModem.ENCodeing_GB2312, temp, receiver, false);
              Thread.sleep(800L);
            } 
          }  
        res = true;
      } else {
        res = false;
      } 
    } catch (Exception ex) {
      System.out.println("发送手机短信失败！");
      ex.printStackTrace();
    } 
    return res;
  }
  
  public String royaMas(String receiver, String content, String extendCode) {
    return MasSMS.sendSMSReturn(receiver, content, extendCode);
  }
}
