package com.js.oa.webmail.util;

import com.js.oa.webmail.po.WebMailAcc;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class str {
  private static final String chineseCharset = "UTF-8";
  
  private static final String unicodeCharset = "ISO8859_1";
  
  private static final String chineseGBK = "GBK";
  
  public static String convert88591ToGB(String strSrc) {
    try {
      return new String(strSrc.getBytes("ISO-8859-1"), "gb2312");
    } catch (UnsupportedEncodingException e) {
      return null;
    } 
  }
  
  public static String convertGBTo88591(String strSrc) {
    try {
      return new String(strSrc.getBytes("gb2312"), "ISO-8859-1");
    } catch (UnsupportedEncodingException e) {
      return null;
    } 
  }
  
  public static String convertToGBK(String strSrc) {
    try {
      return new String(strSrc.getBytes("ISO-8859-1"), "GBK");
    } catch (UnsupportedEncodingException e) {
      return null;
    } 
  }
  
  public static String convert(String strSrc) {
    try {
      return new String(strSrc.getBytes("gb2312"), "ISO-8859-1");
    } catch (UnsupportedEncodingException e) {
      return null;
    } 
  }
  
  public static String getChineseGBK() {
    return "GBK";
  }
  
  public static String getChineseCharset() {
    return "UTF-8";
  }
  
  public static String UnicodetoChinese(String input) {
    try {
      return new String(input.getBytes("ISO8859_1"), "GBK");
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static String ChinesetoUnicode(String input) {
    try {
      return new String(input.getBytes("UTF-8"), "ISO8859_1");
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public static String formatMailAddre(String mail) {
    StringBuffer sb = new StringBuffer();
    try {
      String[] names = mail.split(",");
      for (int i = 0; i < names.length; i++) {
        if (names[i].indexOf("<") > 0) {
          sb.append(String.valueOf(names[i].substring(names[i].indexOf("<") + 1, 
                  names[i].lastIndexOf(">"))) + 
              ",");
        } else {
          sb.append(String.valueOf(names[i]) + ",");
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    String c = sb.toString();
    if (c != null && !c.equals("")) {
      c = c.substring(0, sb.toString().lastIndexOf(","));
    } else {
      c = "";
    } 
    return c;
  }
  
  public static String containMailAddre(String a, Long userId) {
    String b = formatMailAddre(a);
    StringBuffer sb = new StringBuffer();
    try {
      String[] as = b.split(",");
      String[] arrayOfString1 = (String[])null;
      List<WebMailAcc> list = WebMailAccManager.getInstance().getMyAccList(userId);
      if (list != null && list.size() > 0) {
        arrayOfString1 = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
          WebMailAcc w = list.get(i);
          arrayOfString1[i] = w.getMailAccUser();
        } 
      } 
      if (arrayOfString1 != null && arrayOfString1.length > 0) {
        Map<Object, Object> keyMap = new HashMap<Object, Object>();
        int i;
        for (i = 0; i < as.length; i++)
          keyMap.put(as[i], String.valueOf(as[i]) + ","); 
        for (i = 0; i < arrayOfString1.length; i++) {
          if (keyMap.containsKey(arrayOfString1[i]))
            keyMap.remove(arrayOfString1[i]); 
        } 
        List<String> list1 = new ArrayList(keyMap.values());
        for (int j = 0; j < list1.size(); j++)
          sb.append(list1.get(j)); 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    String c = sb.toString();
    if (c != null && !c.equals("")) {
      c = c.substring(0, sb.toString().lastIndexOf(","));
    } else {
      c = "";
    } 
    return c;
  }
  
  public static void main(String[] args) {
    String a = "251549992@qq.com,ddd<xupei1317@126.com>,";
    System.out.println("----contain---->>>>" + formatMailAddre(a));
  }
  
  public static String get(String a) {
    String b = formatMailAddre(a);
    String[] as = b.split(",");
    String[] c = { "251549992@qq.com", "xupei1317@126.com" };
    Map<Object, Object> keyMap = new HashMap<Object, Object>();
    int i;
    for (i = 0; i < as.length; i++)
      keyMap.put(as[i], String.valueOf(as[i]) + ","); 
    for (i = 0; i < c.length; i++) {
      if (keyMap.containsKey(c[i]))
        keyMap.remove(c[i]); 
    } 
    List<String> list = new ArrayList(keyMap.values());
    StringBuffer sb = new StringBuffer();
    for (int j = 0; j < list.size(); j++)
      sb.append(list.get(j)); 
    return sb.toString();
  }
}
