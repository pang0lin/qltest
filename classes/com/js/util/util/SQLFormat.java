package com.js.util.util;

public class SQLFormat {
  public static String replace(String source, String oldString, String newString) {
    StringBuffer output = new StringBuffer();
    int lengthOfSource = source.length();
    int lengthOfOld = oldString.length();
    int posStart = 0;
    int pos;
    while ((pos = source.indexOf(oldString, posStart)) >= 0) {
      output.append(source.substring(posStart, pos));
      output.append(newString);
      posStart = pos + lengthOfOld;
    } 
    if (posStart < lengthOfSource)
      output.append(source.substring(posStart)); 
    return output.toString();
  }
  
  public static String toJavascriptVar(String str) {
    if (str == null)
      return null; 
    String html = replace(str, "\\", "\\\\");
    html = replace(html, "\r", "\\r");
    html = replace(html, "\n", "\\n");
    html = replace(html, "'", "\\'");
    html = replace(html, "\"", "\\\"");
    return html;
  }
  
  public static String toHtmlInput(String str) {
    if (str == null)
      return null; 
    String html = new String(str);
    html = replace(html, "&", "&amp;");
    html = replace(html, "\"", "&quot;");
    html = replace(html, "<", "&lt;");
    html = replace(html, ">", "&gt;");
    return html;
  }
  
  public static String toHtml(String str) {
    if (str == null)
      return null; 
    String html = new String(str);
    html = toHtmlInput(html);
    html = replace(html, "\r\n", "<br>");
    html = replace(html, "\r", "<br>");
    html = replace(html, "\n", "<br>");
    html = replace(html, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
    html = replace(html, " ", "&nbsp;");
    return html;
  }
  
  public static String toSql(String str) {
    String sql = new String(str);
    return replace(sql, "'", "''");
  }
  
  public static String toDbString(String str) {
    String newStr = str;
    try {
      newStr = new String(str.getBytes(), "iso-8859-1");
    } catch (Exception exception) {}
    return newStr;
  }
  
  public static String toPageString(String str) {
    String newStr = str;
    if (newStr == null)
      return ""; 
    try {
      newStr = new String(str.getBytes("iso-8859-1"));
    } catch (Exception exception) {}
    return newStr;
  }
}
