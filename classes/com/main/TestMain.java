package com.main;

import com.js.util.config.SystemCommon;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {
  public static void main(String[] agrs) throws Exception {
    String a = "<p style=text-indent:-36pt;text-align:center;>&nbsp;</p>";
    a = String.valueOf(a) + "<p style=text-indent:-36pt;text-align:center;><b><span style=font-size:22pt;font-family:宋体;>基建</span></b><b><span style=font-size:22pt;font-family:宋体;>业务流程单</span></b></p>";
    System.out.println("--->>>>" + filter(a));
  }
  
  public static String filter(String str) {
    if ("0".equals(SystemCommon.getContentFilter()))
      return str; 
    String s1 = str;
    s1 = s1.replaceAll("(</p>)|</P>", "<br>");
    s1 = s1.replaceAll("(</div>)|</DIV>", "<br>");
    try {
      Pattern pattern = Pattern.compile("(<[^<|^>]*>)|(\\{[^\\{|^\\}]*\\})", 2);
      Matcher matcher = pattern.matcher(s1);
      StringBuffer txt = new StringBuffer();
      while (matcher.find()) {
        String group = matcher.group();
        if (group.matches("<[ \\s]*>")) {
          matcher.appendReplacement(txt, group);
          continue;
        } 
        if (group.matches("<(IMG|img) [^<|^>]*>")) {
          if (group.indexOf("_wap") == -1)
            group = String.valueOf(group.substring(0, group.lastIndexOf("."))) + 
              
              group.substring(group.lastIndexOf("."), group.length()).toLowerCase(); 
          if (group.indexOf("bmp") != -1)
            group = group.replaceAll("", ""); 
          String tmpPath = group.split("src=\"")[1].split("\"")[0];
          String newImgTag = "<img src=\"" + tmpPath + "\"/>";
          matcher.appendReplacement(txt, newImgTag);
          continue;
        } 
        if (group.matches("<(BR|br)[^>]*>")) {
          matcher.appendReplacement(txt, "<br/>");
          continue;
        } 
        matcher.appendReplacement(txt, group);
      } 
      matcher.appendTail(txt);
      s1 = txt.toString();
      if (!s1.contains("<img"))
        s1.contains("<IMG"); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return s1;
  }
}
