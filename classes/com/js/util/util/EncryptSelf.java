package com.js.util.util;

public class EncryptSelf {
  public static String selfDecoder(String str) {
    String decoder = "";
    if (str.startsWith("@jiusi@")) {
      try {
        str = "@jiusi@" + toTrans(str.substring(7));
        int index = (str.length() - 11) / 3;
        int index2 = (str.length() - 11) * 2 / 3;
        String jiusi = String.valueOf(str.substring(index + 7, index + 9)) + str.substring(index2 + 9, index2 + 11);
        DesUtil des = new DesUtil("jiu" + jiusi + "si");
        decoder = des.decrypt(String.valueOf(str.substring(7, index + 7)) + str.substring(index + 9, 
              index2 + 9) + str.substring(index2 + 11));
      } catch (Exception e) {
        e.printStackTrace();
        decoder = str;
      } 
    } else {
      decoder = str;
    } 
    return decoder;
  }
  
  public static String selfEncoder(String str) {
    String encoder = "";
    try {
      int rad = (int)Math.floor(Math.random() * 65530.0D);
      while (rad > 65530 || rad < 4100)
        rad = (int)Math.floor(Math.random() * 65530.0D); 
      String jiusi = (new StringBuilder(String.valueOf(Integer.toHexString(rad)))).toString();
      DesUtil des = new DesUtil("jiu" + jiusi + "si");
      encoder = des.encrypt(str);
      int index = encoder.length() / 3;
      int index2 = encoder.length() * 2 / 3;
      encoder = String.valueOf(encoder.substring(0, index)) + jiusi.substring(0, 2) + encoder
        .substring(index, index2) + jiusi.substring(2) + encoder.substring(index2);
      encoder = "@jiusi@" + toTransform(encoder);
    } catch (Exception e) {
      e.printStackTrace();
      encoder = str;
    } 
    return encoder;
  }
  
  public static String toTransform(String str) {
    String transStr = "";
    char[] tran = str.toCharArray();
    for (int i = 0; i < tran.length; i++) {
      String cha = (new StringBuilder(String.valueOf(tran[i]))).toString();
      if (tran[i] >= 'a') {
        int rad = (int)Math.floor(Math.random() * 100.0D) % 4;
        cha = (new StringBuilder(String.valueOf((char)(rad * 6 + tran[i])))).toString();
        if ((int)(Math.random() * 2.0D) % 2 == 0)
          cha = cha.toUpperCase(); 
      } 
      transStr = String.valueOf(transStr) + cha;
    } 
    return transStr;
  }
  
  public static String toTrans(String str) {
    String transStr = "";
    char[] tran = str.toLowerCase().toCharArray();
    for (int i = 0; i < tran.length; i++) {
      char cha = tran[i];
      if (cha >= 'a')
        cha = (char)((cha - 97) % 6 + 97); 
      transStr = String.valueOf(transStr) + cha;
    } 
    return transStr;
  }
  
  public static void main(String[] arg) {
    String str = "1234567890.34254325";
    String encoder = selfEncoder(str);
    System.out.println(encoder);
    String decoder = selfDecoder(encoder);
    System.out.println(decoder);
    if (str.equals(decoder))
      System.out.println("解密成功"); 
  }
}
