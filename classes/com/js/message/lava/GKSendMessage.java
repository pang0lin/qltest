package com.js.message.lava;

public class GKSendMessage {
  private GKUtilClass gKUtilClass = new GKUtilClass();
  
  public void close() {
    this.gKUtilClass.close();
  }
  
  public boolean sendMessage(String senderAccount, String senderGid, String senderZoneid, String receiverSize, String receiverGid, String receiverAccount, String receiverMobile, String text, String time) {
    boolean flag = false;
    String str = "<request type='sms' subtype='' msid=''>\r<message>\r<sender account='" + 
      
      senderAccount + "' gid='" + senderGid + "' zoneid='" + senderZoneid + "'/>\r" + 
      "<receivers size='" + receiverSize + "'>\r" + 
      "<receiver gid='" + receiverGid + "'/>\r" + 
      "<receiver account='" + receiverAccount + "' />\r" + 
      "<receiver mobile='" + receiverMobile + "'/>\r" + 
      "</receivers>\r" + 
      "<body>" + text + "</body>\r" + 
      "<time>" + time + "</time>\r" + 
      "</message>\r" + 
      "</request>\r";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean sendIM(String senderAccount, String ug_code, String receiverAccount, String text, String htmltext) {
    boolean flag = false;
    String str = 
      "<request type='im' subtype='' msid=''>\r<message type='normal'>\r<sender  account='" + 
      
      senderAccount + "'/>\r" + 
      "<receivers ug_code='" + ug_code + "'>\r" + 
      "<receiver account='" + receiverAccount + "'/>\r" + 
      "</receivers>\r" + 
      "<body>" + text + "</body>\r" + 
      "<htmlbody>" + htmltext + "</htmlbody>\r" + 
      "</message>\r" + 
      "</request>\r";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean sendSysIM(String receiverAccount, String ug_code, String text, String htmltext) {
    boolean flag = false;
    String str = 
      "<request type='im' subtype='' msid=''>\r<message type='sys'>\r<sender  account=''/>\r<receivers ug_code='" + 

      
      ug_code + "'>\r" + 
      "<receiver account='" + receiverAccount + "'/>\r" + 
      "</receivers>\r" + 
      "<body>" + text + "</body>\r" + 
      "<htmlbody>" + htmltext + "</htmlbody>\r" + 
      "</message>\r" + 
      "</request>\r";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean sendNotify(String receivers, String ug_code, String title, String text, String htmltext) {
    boolean rs = false;
    try {
      if (!"".equals(receivers) && receivers != null) {
        String[] receiversAccount = receivers.split(",");
        StringBuffer buffer = new StringBuffer();
        buffer.append("<request type='im' subtype='' msid=''>");
        buffer.append("<message type='sys'>");
        buffer.append("<sender  account=''/>");
        buffer.append("<receivers ug_code=''>");
        for (int i = 0; i < receiversAccount.length; i++)
          buffer.append("<receiver account='" + receiversAccount[i] + 
              "'/>"); 
        buffer.append("</receivers>");
        buffer.append("<body></body>");
        buffer.append("<htmlbody>" + text + "</htmlbody>");
        buffer.append("</message></request>");
        rs = this.gKUtilClass.getFlag(buffer.toString());
      } 
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
    return rs;
  }
}
