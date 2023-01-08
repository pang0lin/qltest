package com.js.oa.webservice;

import com.js.system.service.messages.RemindUtil;
import com.js.system.util.StaticParam;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class Message_Service {
  public String getMessage(String xml) {
    String returnXml = 
      "<return><status>$status$</status><success>$success$</success><fail>$fail$</fail><description>$description$</description><Id>$id$</Id></return>";
    String dataXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data>";
    String rxml = "";
    String flag = "0";
    String id = "";
    try {
      List<String[]> itemList = readXml(xml);
      for (String[] item : itemList) {
        String title = item[1];
        id = item[4];
        if (item[2] != null && !"".equals(item[2])) {
          String success = "";
          String fail = "";
          String from = (item[0] == null || item[0].equals("")) ? "系统提醒" : item[0];
          String[] toUsers = item[2].split(",");
          String url = item[3];
          byte b;
          int i;
          String[] arrayOfString1;
          for (i = (arrayOfString1 = toUsers).length, b = 0; b < i; ) {
            String account = arrayOfString1[b];
            if (account != null && !"".equals(account)) {
              String toID = StaticParam.getEmpIdByAccount(account);
              if (!"".equals(toID)) {
                success = String.valueOf(success) + account + ",";
                RemindUtil.sendMessageToUsers2(title, url, toID, "Chat", new Date(), new Date("2050/1/1"), from, Long.valueOf(0L), 1);
                flag = "1";
              } else {
                fail = String.valueOf(fail) + account + ",";
                System.out.println("账号为【" + account + "】的用户不存在");
              } 
            } 
            b++;
          } 
          rxml = String.valueOf(rxml) + returnXml.replace("$status$", flag)
            .replace("$success$", success)
            .replace("$fail$", fail)
            .replace("$description$", "【" + title + "】处理完成")
            .replace("$id$", id);
          continue;
        } 
        rxml = String.valueOf(rxml) + returnXml.replace("$status$", flag)
          .replace("$success$", "")
          .replace("$fail$", "")
          .replace("$description$", "【" + title + "】未找到提醒人员账号")
          .replace("$id$", id);
      } 
      dataXml = String.valueOf(dataXml) + rxml;
    } catch (Exception e) {
      e.printStackTrace();
      dataXml = String.valueOf(dataXml) + returnXml.replace("$status$", "0")
        .replace("$success$", "")
        .replace("$fail$", "")
        .replace("$description$", "参数解析错误")
        .replace("$id$", id);
    } 
    return String.valueOf(dataXml) + "</Data>";
  }
  
  private List<String[]> readXml(String xml) throws Exception {
    List<String[]> list = (List)new ArrayList<String>();
    SAXBuilder builder = new SAXBuilder();
    builder.setExpandEntities(false);
    Document doc = null;
    doc = builder.build(new InputSource(new StringReader(xml)));
    Element person = doc.getRootElement();
    List<Element> itemList = person.getChildren("item");
    for (int i = 0; i < itemList.size(); i++) {
      Element item = itemList.get(i);
      list.add(new String[] { item.getChildText("From"), 
            item.getChildText("Title"), 
            item.getChildText("User"), 
            item.getChildText("URL"), 
            item.getChildText("Id") });
    } 
    return list;
  }
}
