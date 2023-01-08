package com.js.oa.lcpsoa.client;

import java.io.IOException;
import java.util.TimerTask;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class LcpsoaTask extends TimerTask {
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
    Element node = doc.getRootElement().getChild("LcpsoaDatabase");
    if (node != null) {
      String setTime = node.getChild("setTime").getAttribute("value").getValue();
      String lcpsoaSwitch = node.getChild("LcpsoaSwitch").getAttribute("value").getValue();
      if ("1".equals(lcpsoaSwitch) && 
        !"".equals(setTime) && setTime != null) {
        int timeValue = Integer.parseInt(setTime);
        this.helpNum++;
        if (this.helpNum == timeValue) {
          LcpsoaService.getInstance();
          LcpsoaService.init();
          this.helpNum = 0;
        } 
      } 
    } else {
      System.out.println("请检查一下bin\\jsconfig下的sysconfig.xml是否缺少LcpsoaDatabase");
    } 
  }
}
