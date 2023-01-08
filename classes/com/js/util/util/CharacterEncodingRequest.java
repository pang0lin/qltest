package com.js.util.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class CharacterEncodingRequest extends HttpServletRequestWrapper {
  private Map<String, String> parmMap = new HashMap<String, String>();
  
  private String charset;
  
  public CharacterEncodingRequest(HttpServletRequest request) {
    super(request);
    this.charset = "GBK";
    String[] parms = request.getQueryString().split("&");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = parms).length, b = 0; b < i; ) {
      String parm = arrayOfString1[b];
      if (parm != null && !"null".equalsIgnoreCase(parm))
        if (parm.length() > 1)
          if ("=".equals(parm.substring(parm.length() - 1))) {
            this.parmMap.put(parm.substring(0, parm.length() - 1), null);
          } else {
            String[] subParm = parm.split("=");
            if (subParm.length == 1) {
              this.parmMap.put(subParm[0], "");
            } else {
              this.parmMap.put(subParm[0], subParm[1]);
            } 
          }   
      b++;
    } 
  }
  
  public CharacterEncodingRequest(HttpServletRequest request, String charset) {
    super(request);
    this.charset = "GBK";
    this.charset = charset;
    String[] parms = request.getQueryString().split("&");
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = parms).length, b = 0; b < i; ) {
      String parm = arrayOfString1[b];
      if (parm != null && !"null".equalsIgnoreCase(parm))
        if ("=".equals(parm.substring(parm.length() - 1))) {
          this.parmMap.put(parm.substring(0, parm.length() - 1), null);
        } else {
          String[] subParm = parm.split("=");
          this.parmMap.put(subParm[0], subParm[1]);
        }  
      b++;
    } 
  }
  
  public String getParameter(String name) {
    String value = super.getParameter(name);
    if (value == null || "".equals(value))
      return value; 
    if ((value.getBytes()).length == value.length())
      return value; 
    String result = convertToChinese(name, value);
    String res = result.replaceAll("[^一-龥]", "");
    if (res == null || "".equals(res)) {
      result = value;
      System.out.println(" -> (无需转码)");
    } else {
      System.out.println(" -> " + result);
    } 
    return result;
  }
  
  public String convertToChinese(String name, String target) {
    System.out.print("编码转换处理：" + target);
    try {
      String sourceTarget = target;
      target = new String(target.trim().getBytes("ISO-8859-1"), this.charset);
      if (target != null && target.indexOf("??") > -1 && 
        this.parmMap.get(name) != null)
        target = URLDecoder.decode(this.parmMap.get(name), "UTF-8"); 
    } catch (UnsupportedEncodingException e) {
      System.out.println(String.valueOf(target) + "->乱码处理失败");
    } 
    return target;
  }
}
