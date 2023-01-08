package com.js.sms.mac;

import com.js.util.util.PropertiesTrans;
import java.util.Date;

public class HuaWeiMac {
  private static HuaWeiMac instance = null;
  
  public static synchronized HuaWeiMac getInstance() {
    if (instance == null) {
      release();
      instance = new HuaWeiMac();
    } 
    return instance;
  }
  
  public HuaWeiMac() {
    init();
  }
  
  private void init() {
    getSQLConfigFile();
    String DB_IP = getForMas("dbIP_");
    String DB_UserName = getForMas("dbUser_");
    String DB_PWord = getForMas("dbPwd_");
    String MAS_UserName = getForMas("masUser");
    String MAS_PWord = getForMas("masPwd");
  }
  
  private static void release() {}
  
  public int sendShortMsg(String mobile, String content) {
    Date atTime = new Date();
    String sourceAddr = getForMas("sourceAddr");
    int needStateReport = Integer.parseInt(getForMas("needStateReport"));
    String ServiceID = getForMas("ServiceID");
    String feeType = getForMas("feeType");
    String feeCode = getForMas("feeCode");
    return 0;
  }
  
  private String getForMas(String key) {
    return PropertiesTrans.getValueByKey(key);
  }
  
  private void getSQLConfigFile() {
    String isUse = getForMas("useSQLFile");
    isUse.toLowerCase().equals("1");
  }
  
  public static void main(String[] args) {
    getInstance().sendShortMsg("1352148008", "sdfsdf sdf sd ");
  }
}
