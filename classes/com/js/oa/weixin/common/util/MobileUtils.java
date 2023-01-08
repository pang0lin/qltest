package com.js.oa.weixin.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileUtils {
  public static String changeImg(String content) {
    int s = -1, e = -1;
    if (content == null || "".equals(content))
      return content; 
    String temp = content.toLowerCase(), img = "", src = "";
    s = temp.indexOf("<img");
    while (s > -1) {
      temp = temp.substring(s);
      e = temp.indexOf(">") + 1;
      img = temp.substring(0, e);
      src = img.substring(img.indexOf("src="));
      if (src.indexOf(" ") > -1) {
        src = src.substring(0, src.indexOf(" "));
      } else if (src.indexOf("/>") > -1) {
        src = src.substring(0, src.indexOf("/>"));
      } else if (src.indexOf(">") > -1) {
        src = src.substring(0, src.indexOf(">"));
      } 
      if (!"".equals(src)) {
        src = "<img width=\"90%\" " + src + ">";
        content = content.replace(img, src);
      } 
      temp = temp.substring(e);
      s = temp.indexOf("<img");
    } 
    return content;
  }
  
  public static boolean likeAndroid(String userAgent) {
    String androidReg = "\\bandroid|Nexus\\b";
    Pattern androidPat = Pattern.compile(androidReg, 2);
    if (userAgent == null)
      userAgent = ""; 
    Matcher matcherAndroid = androidPat.matcher(userAgent);
    if (matcherAndroid.find())
      return true; 
    return false;
  }
  
  public static boolean likeIOS(String userAgent) {
    String iosReg = "ip(hone|od|ad)";
    Pattern iosPat = Pattern.compile(iosReg, 2);
    if (userAgent == null)
      userAgent = ""; 
    Matcher matcherIOS = iosPat.matcher(userAgent);
    if (matcherIOS.find())
      return true; 
    return false;
  }
}
