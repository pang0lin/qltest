package com.js.wap.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WapStringTool {
  public static Object nullStrFilter(Object obj) {
    if (obj == null)
      return ""; 
    return obj;
  }
  
  public static String cutString(String str) {
    if (str != null) {
      int length = str.length();
      return (length > 10) ? (String.valueOf(str.substring(0, 10)) + "...") : str;
    } 
    return "";
  }
  
  public static String getForwardStr(String version, String colorStr) {
    if ("3G".equals(version))
      colorStr = String.valueOf(colorStr) + "_3g"; 
    return colorStr;
  }
  
  public static String Html2Text(String inputString) {
    String htmlStr = inputString;
    String textStr = "";
    try {
      String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
      String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
      String regEx_html = "<[^>]+>";
      Pattern p_script = Pattern.compile(regEx_script, 2);
      Matcher m_script = p_script.matcher(htmlStr);
      htmlStr = m_script.replaceAll("");
      Pattern p_style = Pattern.compile(regEx_style, 2);
      Matcher m_style = p_style.matcher(htmlStr);
      htmlStr = m_style.replaceAll("");
      Pattern p_html = Pattern.compile(regEx_html, 2);
      Matcher m_html = p_html.matcher(htmlStr);
      htmlStr = m_html.replaceAll("");
      textStr = htmlStr;
    } catch (Exception e) {
      System.err.println("Html2Text: " + e.getMessage());
    } 
    return textStr;
  }
}
