package com.js.message.lava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class GKUtilClass {
  private static String server;
  
  private static int port;
  
  private static Socket socket = null;
  
  public GKUtilClass() {
    init();
  }
  
  private void init() {
    try {
      String path = System.getProperty("user.dir");
      String filePath = String.valueOf(path) + "/jsconfig/sysconfig.xml";
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(filePath);
      Element root = doc.getRootElement();
      Element node = root.getChild("RealTimeMessage");
      node = root.getChild("GKServer");
      server = node.getChildText("Server");
      port = Integer.parseInt(node.getChildText("port"));
      socket = new Socket(InetAddress.getByName(server), port);
    } catch (Exception ex) {
      ex.printStackTrace();
    } 
  }
  
  public void close() {
    if (socket != null)
      try {
        socket.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }  
  }
  
  public String sendRequest(String xml) {
    String res = "";
    try {
      byte[] cs = xml.getBytes("UTF-8");
      if (socket == null || socket.isClosed())
        init(); 
      String req = 
        
        "POST /api/gkmsapi  HTTP/1.1\r\nHost: " + 
        server + ":" + port + "\r\n" + 
        "Accept: text/javascript, text/html, application/xml, text/xml, */*\r\n" + 
        "Content-Type: application/binary; charset=UTF-8\r\n" + 
        "Content-Length: " + cs.length + "\r\n" + 
        "Connection: close\r\n" + 
        "Pragma: no-cache\r\n\r\n";
      OutputStream out = socket.getOutputStream();
      out.write(req.getBytes());
      out.write(cs);
      out.flush();
      BufferedReader input = new BufferedReader(new InputStreamReader(
            socket.getInputStream(), "UTF-8"));
      while (true) {
        String line = input.readLine();
        if (line == null)
          break; 
        res = String.valueOf(res) + line;
      } 
      out.close();
      input.close();
      int i = res.indexOf("<response");
      if (i >= 0)
        return res.substring(i); 
    } catch (Exception e) {
      System.out.println("---Error-sendRequest-");
      System.out.println("res:" + res);
      e.printStackTrace();
    } 
    return "";
  }
  
  public void writeLog(String str) {
    String s = new String();
    String s1 = new String();
    try {
      File f = new File("c:\\log.txt");
      if (!f.exists())
        f.createNewFile(); 
      BufferedReader input = new BufferedReader(new FileReader(f));
      while ((s = input.readLine()) != null)
        s1 = String.valueOf(s1) + s + "\n"; 
      input.close();
      s1 = String.valueOf(s1) + str;
      BufferedWriter output = new BufferedWriter(new FileWriter(f));
      output.write(s1);
      output.close();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public boolean getFlag(String str) {
    boolean flag = false;
    try {
      String ewXml = sendRequest(str);
      Document doc = DocumentHelper.parseText(ewXml);
      Element root = null;
      root = doc.getRootElement();
      Element nodeElement = root.element("result");
      Attribute attribute = nodeElement.attribute("code");
      if (attribute.getValue().equals("0"))
        flag = true; 
    } catch (Exception e) {
      System.out.println("---Error-getFlag-");
      e.printStackTrace();
    } 
    return flag;
  }
  
  public String getUserGID(String xml) {
    String gid = "";
    try {
      String ewXml = sendRequest(xml);
      Document doc = DocumentHelper.parseText(ewXml);
      Element root = null;
      root = doc.getRootElement();
      Element node = root.element("result");
      Attribute attribute = node.attribute("code");
      if (attribute.getValue().equals("0")) {
        node = root.element("message").element("users").element("user");
        gid = node.attribute("gid").getValue();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return gid;
  }
  
  public List getlistOrganization(Element root, List<Organization> list) {
    Element messageElement = root.element("message");
    Element ugsElement = messageElement.element("ugs");
    for (Iterator<Element> iter = ugsElement.elementIterator(); iter.hasNext(); ) {
      Element element = iter.next();
      Organization org = new Organization();
      for (Iterator<Attribute> i = element.attributeIterator(); i.hasNext(); ) {
        Attribute ugattribute = i.next();
        if (ugattribute.getName().equals("code")) {
          org.setCode(
              ugattribute.getValue());
          continue;
        } 
        if (ugattribute.getName().equals("name")) {
          org.setName(
              ugattribute.getValue());
          continue;
        } 
        if (ugattribute.getName().equals("parent_code")) {
          org
            .setParentcode(ugattribute.getValue());
          continue;
        } 
        if (ugattribute.getName().equals("sign")) {
          org.setSign(
              ugattribute.getValue());
          continue;
        } 
        if (ugattribute.getName().equals("location")) {
          org
            .setLocation(ugattribute.getValue());
          continue;
        } 
        if (ugattribute.getName().equals("email")) {
          org.setEmail(
              ugattribute.getValue());
          continue;
        } 
        if (ugattribute.getName().equals("remark"))
          org
            .setRemark(ugattribute.getValue()); 
      } 
      list.add(org);
    } 
    return list;
  }
  
  public List getOrgUserList(Element root, List<User> list) {
    Element messageElement = root.element("message");
    Element usersElement = messageElement.element("users");
    for (Iterator<Element> iter = usersElement.elementIterator(); iter.hasNext(); ) {
      Element element = iter.next();
      User user = new User();
      for (Iterator<Attribute> i = element.attributeIterator(); i.hasNext(); ) {
        Attribute userattribute = i.next();
        if (userattribute.getName().equals("account")) {
          user.setAccount(
              userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("gid")) {
          user.setGid(
              userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("zoneid")) {
          user
            .setZoneid(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("name")) {
          user
            .setName(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("display_name")) {
          user.setDisplayName(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("state")) {
          user
            .setState(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("sex")) {
          user.setSex(
              userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("birthday")) {
          user
            .setBirthday(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("email")) {
          user
            .setEmail(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("ug_name")) {
          user
            .setUgName(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("mobile")) {
          user
            .setMobile(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("office_tel")) {
          user
            .setOfficeTel(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("fax")) {
          user.setFax(
              userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("webaddress")) {
          user
            .setWebaddress(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("postcode")) {
          user
            .setPostcode(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("address")) {
          user
            .setAddress(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("position")) {
          user
            .setPosition(userattribute.getValue());
          continue;
        } 
        if (userattribute.getName().equals("remark"))
          user
            .setRemark(userattribute.getValue()); 
      } 
      list.add(user);
    } 
    return list;
  }
  
  public void setServer(String server) {
    GKUtilClass.server = server;
  }
  
  public void setPort(int port) {
    GKUtilClass.port = port;
  }
}
