package com.js.util.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ToolBox {
  public static String URLEncoder(String Str) throws UnsupportedEncodingException {
    String ENStr = "";
    if (Str != null) {
      ENStr = URLEncoder.encode(Str, "UTF-8");
      ENStr = ENStr.replace('%', '$');
    } 
    return ENStr;
  }
  
  public static String URLDecoder(String ENStr) throws UnsupportedEncodingException {
    String Str = "";
    if (ENStr != null) {
      ENStr = ENStr.replace('$', '%');
      URLDecoder urlDecoder = new URLDecoder();
      Str = URLDecoder.decode(ENStr, "UTF-8");
    } 
    return Str;
  }
}
