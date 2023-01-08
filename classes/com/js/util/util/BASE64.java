package com.js.util.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64 {
  public String BASE64Encoder(String str) {
    BASE64Encoder en = new BASE64Encoder();
    String bss = new String();
    try {
      bss = en.encode(str.getBytes("GBK"));
    } catch (Exception ex) {
      System.out.println("编码不成功！");
    } 
    return bss;
  }
  
  public String BASE64Decoder(String str) {
    BASE64Decoder dn = new BASE64Decoder();
    byte[] b = (byte[])null;
    try {
      b = dn.decodeBuffer(str);
      str = new String(b);
    } catch (Exception ex) {
      System.out.println("解码不成功");
    } 
    return str;
  }
  
  public static String BASE64EncoderNoBR(String str) {
    String bss = EncryptSelf.selfEncoder(str);
    return bss;
  }
  
  public static String BASE64DecoderNoBR(String str) {
    String bss = "";
    if (str.startsWith("@jiusi@")) {
      bss = EncryptSelf.selfDecoder(str);
    } else {
      if (str != null && (
        str.indexOf("@jiusi13@") >= 0 || str.indexOf("@jiusi10@") >= 0)) {
        String temp = str;
        StringBuffer sb = new StringBuffer();
        int index = str.indexOf("@jiusi13@");
        if (index >= 0) {
          while (index >= 0) {
            String qian = temp.substring(0, index);
            sb.append(qian);
            sb.append('\r');
            temp = temp.substring(index + 9);
            index = temp.indexOf("@jiusi13@");
            if (index < 0)
              sb.append(temp); 
          } 
          temp = sb.toString();
        } 
        sb = new StringBuffer();
        index = temp.indexOf("@jiusi10@");
        while (index >= 0) {
          String qian = temp.substring(0, index);
          sb.append(qian);
          sb.append('\n');
          temp = temp.substring(index + 9);
          index = temp.indexOf("@jiusi10@");
          if (index < 0)
            sb.append(temp); 
        } 
        str = sb.toString();
      } 
      BASE64Decoder dn = new BASE64Decoder();
      byte[] b = (byte[])null;
      try {
        b = dn.decodeBuffer(str);
        str = new String(b);
      } catch (Exception ex) {
        System.out.println("解码不成功");
      } 
      bss = str;
    } 
    return bss;
  }
}
