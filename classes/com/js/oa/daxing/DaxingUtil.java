package com.js.oa.daxing;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class DaxingUtil {
  public String getMd5Code(String str) {
    MessageDigest messageDigest = null;
    try {
      messageDigest = MessageDigest.getInstance("MD5");
      messageDigest.reset();
      messageDigest.update(str.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException e) {
      System.out.println("NoSuchAlgorithmException caught!");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } 
    byte[] byteArray = messageDigest.digest();
    StringBuffer md5StrBuff = new StringBuffer();
    for (int i = 0; i < byteArray.length; i++) {
      if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
        md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
      } else {
        md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
      } 
    } 
    return md5StrBuff.toString();
  }
  
  public String getConfig(String nodeName) {
    FileInputStream configFileInputStream = null;
    String nodeValue = "";
    try {
      String path = System.getProperty("user.dir");
      String configFile = String.valueOf(path) + "\\jsconfig\\sysconfig.xml";
      configFileInputStream = new FileInputStream(new File(configFile));
      SAXBuilder builder = new SAXBuilder();
      Document doc = builder.build(configFileInputStream);
      Element root = doc.getRootElement();
      Element node = root.getChild("DaXingConfig");
      Element resultNode = node.getChild(nodeName);
      nodeValue = resultNode.getAttributeValue("value");
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return nodeValue;
  }
}
