package com.other;

import com.js.util.mail.Mail;
import com.js.util.mail.MailSender;
import com.js.util.util.PropertiesTrans;
import java.util.Date;

public class AutoThread extends Thread {
  public void run() {
    System.out.println("-----开始执行动作--------");
    System.out.println("===" + (new Date()).toGMTString() + "===");
    try {
      while (true) {
        StaticOtherParam.send();
        long second = Long.parseLong(PropertiesTrans.getValueByKey("second"));
        long min = Long.parseLong(PropertiesTrans.getValueByKey("min"));
        long hour = Long.parseLong(PropertiesTrans.getValueByKey("hour"));
        long des = Long.parseLong(PropertiesTrans.getValueByKey("des"));
        sleep(second * min * hour * des);
      } 
    } catch (InterruptedException e) {
      return;
    } 
  }
  
  public static void main(String[] args) {
    Mail mailVO = new Mail();
    String[] fileAffix = (String[])null;
    mailVO.setFileAffix(fileAffix);
    mailVO.setSubjectTitle("sdfsdf");
    mailVO.setBoby("请查看网站：http://www.jiusi.net");
    mailVO.setHtml(true);
    mailVO.setSendTo("xupei1317@126.com");
    String returnFlag = MailSender.autoSend(mailVO, "smtp.126.com", "aimenglinl@126.com", "jiusi.net", 25, "SSL");
    System.out.println("----returnFlag-->>>>>>>>>" + returnFlag);
  }
}
