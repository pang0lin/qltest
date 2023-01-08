package com.qq.weixin.mp.bean;

import com.qq.weixin.mp.util.DesUtil;
import com.qq.weixin.mp.util.GetUrlUtil;
import java.util.Date;
import org.apache.log4j.Logger;

public class TimeCrypt {
  private static Logger log = Logger.getLogger(TimeCrypt.class);
  
  private static final String KEY = "@$Encryption%Key&Decryption%Key$!";
  
  private static final String PARAM_HEAD = "&timeCipher=";
  
  private static final String END = "&END";
  
  private static final long TIME_INTERVAL = 60000L;
  
  public static String addEncryptedParam(String content) {
    String cipher = getEncryptedTime();
    String param = "&timeCipher=" + cipher;
    content = content.replace("&END", param);
    return content;
  }
  
  public static boolean isTimely(String urlQuery) {
    String cipher = GetUrlUtil.getMiddle(urlQuery, "&timeCipher=", "&");
    long timeInterval = (new Date()).getTime() - getDecryptedTime(cipher);
    log.debug("timeInterval = " + timeInterval);
    return (timeInterval < 60000L);
  }
  
  private static String getEncryptedTime() {
    String cipher = "";
    int count = 3;
    do {
      try {
        String time = Long.toString((new Date()).getTime());
        log.debug("currentTime = " + time);
        cipher = DesUtil.encrypt(time, "@$Encryption%Key&Decryption%Key$!");
      } catch (Exception e) {
        e.printStackTrace();
      } 
    } while (cipher.equals("") && --count != 0);
    log.debug("cipher = " + cipher);
    return cipher;
  }
  
  private static long getDecryptedTime(String cipher) {
    long time = 0L;
    log.debug("cipher = " + cipher);
    try {
      String plain = "";
      plain = DesUtil.decrypt(cipher, "@$Encryption%Key&Decryption%Key$!");
      log.debug("plain = " + plain);
      time = Long.valueOf(plain).longValue();
      log.debug("plainTime = " + time);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return time;
  }
}
