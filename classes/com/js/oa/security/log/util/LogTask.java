package com.js.oa.security.log.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class LogTask extends TimerTask {
  private int helpNum = 0;
  
  public static void main(String[] args) {}
  
  public void run() {
    String path = System.getProperty("user.dir");
    String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    try {
      doc = builder.build(filePath);
    } catch (JDOMException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    Element node = doc.getRootElement().getChild("LogsearchService");
    if (node != null) {
      String LogearchSwitch = node.getChild("LogsearchSwitch").getAttribute("value").getValue();
      String AutoSync = node.getChild("AutoSync").getAttribute("staticTime").getValue();
      if ("1".equals(LogearchSwitch)) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        if (sdf.format(date).toString().equals(AutoSync))
          LogService.doMethod(); 
      } 
    } else {
      System.out.println("请检查一下bin\\jsconfig下的sysconfig.xml是否缺少LogsearchService");
    } 
  }
}
