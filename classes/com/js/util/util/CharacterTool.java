package com.js.util.util;

import java.math.BigDecimal;
import org.apache.commons.lang.StringEscapeUtils;

public class CharacterTool {
  public static String escapeHTMLTags(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '<':
          stringBuffer.append("&lt;");
          break;
        case '>':
          stringBuffer.append("&gt;");
          break;
        case '\r':
          stringBuffer.append("<br>");
          break;
        case '\n':
          stringBuffer.append("<br>");
          break;
        case '\'':
          stringBuffer.append("&acute");
          break;
        case '"':
          stringBuffer.append("&quot");
          break;
        case ' ':
          stringBuffer.append("&nbsp;");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String escapeHTMLTags2(String str) {
    if (str != null && !"".equals(str)) {
      str = str.replaceAll("(?i)<script", "< script ");
      str = str.replaceAll("(?i)</script>", "< /script >");
      str = str.replaceAll("(?i)<iframe", "< iframe ");
      str = str.replaceAll("(?i)</iframe>", "< /iframe >");
    } 
    return str;
  }
  
  public static String escapeHTMLTagsGW(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '<':
          stringBuffer.append("&lt;");
          break;
        case '>':
          stringBuffer.append("&gt;");
          break;
        case '\'':
          stringBuffer.append("&acute");
          break;
        case '"':
          stringBuffer.append("&quot");
          break;
        case ' ':
          stringBuffer.append("&nbsp;");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String escapeHTMLTagsSimple(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '\n':
          stringBuffer.append("<br>");
          break;
        case ' ':
          stringBuffer.append("&nbsp;");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String deleteEnter(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '\n':
          stringBuffer.append(" ");
          break;
        case '\r':
          stringBuffer.append(" ");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String replaceXMLTags(String str) {
    StringBuffer result = new StringBuffer(str.length() + 20);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '<':
          result.append("&lt;");
          break;
        case '>':
          result.append("&gt;");
          break;
        case '&':
          result.append("&amp;");
          break;
        case '"':
          result.append("&quot;");
          break;
        case '\'':
          result.append("&#39;");
          break;
        default:
          result.append(c);
          break;
      } 
    } 
    return result.toString();
  }
  
  public static String escapeHTMLQuot(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if (c == '"')
        stringBuffer.append("\\"); 
      stringBuffer.append(c);
    } 
    return stringBuffer.toString();
  }
  
  public static String escapeHTMLQuotOther(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '\'':
          stringBuffer.append("&acute");
          break;
        case '"':
          stringBuffer.append("&quot;");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String changeToGB(String str) {
    return str;
  }
  
  public static String getFormValue(String str) {
    return str;
  }
  
  public static String getFilteScriptString(String str) {
    StringBuffer result = new StringBuffer(str.length() + 20);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '<':
          result.append("&lt;");
          break;
        case '>':
          result.append("&gt;");
          break;
        default:
          result.append(c);
          break;
      } 
    } 
    return result.toString();
  }
  
  public static String escapeJSTags(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '\r':
          stringBuffer.append("");
          break;
        case '\n':
          stringBuffer.append("");
          break;
        case '\'':
          stringBuffer.append("＇");
          break;
        case '"':
          stringBuffer.append("“");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String transactSqlInjection(String sql) {
    return sql.replaceAll(".*([';]+|(--)+).*", " ");
  }
  
  public static String escapeJSTagsRN(String str) {
    StringBuffer stringBuffer = new StringBuffer(str.length() + 6);
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      switch (c) {
        case '\r':
          stringBuffer.append("\\r");
          break;
        case '\n':
          stringBuffer.append("\\n");
          break;
        default:
          stringBuffer.append(c);
          break;
      } 
    } 
    return stringBuffer.toString();
  }
  
  public static String unescapeHtml(String str) {
    return StringEscapeUtils.unescapeHtml(str);
  }
  
  public static String getNumberByPoint(String number, int count) {
    BigDecimal bd = new BigDecimal(number);
    bd = bd.setScale(count, 4);
    return bd.toString();
  }
  
  public static String unescape(String src) {
    StringBuffer tmp = new StringBuffer();
    tmp.ensureCapacity(src.length());
    int lastPos = 0, pos = 0;
    while (lastPos < src.length()) {
      pos = src.indexOf("%", lastPos);
      if (pos == lastPos) {
        if (src.charAt(pos + 1) == 'u') {
          char c = (char)Integer.parseInt(
              src.substring(pos + 2, pos + 6), 16);
          tmp.append(c);
          lastPos = pos + 6;
          continue;
        } 
        char ch = (char)Integer.parseInt(
            src.substring(pos + 1, pos + 3), 16);
        tmp.append(ch);
        lastPos = pos + 3;
        continue;
      } 
      if (pos == -1) {
        tmp.append(src.substring(lastPos));
        lastPos = src.length();
        continue;
      } 
      tmp.append(src.substring(lastPos, pos));
      lastPos = pos;
    } 
    return tmp.toString();
  }
  
  public static String escape(String src) {
    StringBuffer tmp = new StringBuffer();
    tmp.ensureCapacity(src.length() * 6);
    for (int i = 0; i < src.length(); i++) {
      char j = src.charAt(i);
      if (Character.isDigit(j) || Character.isLowerCase(j) || 
        Character.isUpperCase(j)) {
        tmp.append(j);
      } else if (j < 'Ā') {
        tmp.append("%");
        if (j < '\020')
          tmp.append("0"); 
        tmp.append(Integer.toString(j, 16));
      } else {
        tmp.append("%u");
        tmp.append(Integer.toString(j, 16));
      } 
    } 
    return tmp.toString();
  }
}
