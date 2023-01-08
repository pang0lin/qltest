package com.js.sms.mac;

import com.jasson.im.api.APIClient;
import com.jasson.im.api.MOItem;
import com.jasson.im.api.RPTItem;
import com.js.util.util.PropertiesTrans;
import java.util.Random;

public class JassionMac {
  private static APIClient handler = new APIClient();
  
  private static JassionMac instance = null;
  
  private static long datetime = 0L;
  
  public static synchronized JassionMac getInstance() {
    if (instance != null) {
      long curentTime = System.currentTimeMillis();
      if (curentTime - datetime >= 3600000L) {
        release();
        instance = null;
      } 
    } 
    if (instance == null) {
      release();
      instance = new JassionMac();
      datetime = System.currentTimeMillis();
    } 
    return instance;
  }
  
  public JassionMac() {
    init();
  }
  
  private int init() {
    int connectRe = handler.init(getForMas("dbIP"), getForMas("dbUser"), 
        getForMas("dbPwd"), getForMas("apiCode"), 
        getForMas("dbName"));
    return connectRe;
  }
  
  public int sendSM(String mobile, String content) {
    return handler.sendSM(mobile, content, getRandom(0));
  }
  
  public int sendSM(String[] mobiles, String content) {
    return handler.sendSM(mobiles, content, getRandom(0), getRandom(1));
  }
  
  public int sendSM(String[] mobiles, String content, String sendTime) {
    return handler.sendSM(mobiles, content, sendTime, getRandom(0), getRandom(1));
  }
  
  public MOItem[] receiveSM() {
    MOItem[] mos = handler.receiveSM();
    return mos;
  }
  
  public RPTItem[] reciveRPT() {
    RPTItem[] rpts = handler.receiveRPT();
    return rpts;
  }
  
  public static void release() {
    handler.release();
  }
  
  private String getForMas(String key) {
    return PropertiesTrans.getValueByKey(key);
  }
  
  private long getRandom(int key) {
    Random random = new Random();
    long tmpSmId = 0L;
    long tmpSrcId = 0L;
    long returnTempId = 0L;
    switch (key) {
      case 0:
        tmpSmId = random.nextInt(1000);
        returnTempId = tmpSmId;
        break;
      case 1:
        tmpSrcId = random.nextInt(100);
        returnTempId = tmpSrcId;
        break;
    } 
    return returnTempId;
  }
  
  public static void main(String[] args) {
    JassionMac jm = new JassionMac();
    jm.getRandom(1);
  }
}
