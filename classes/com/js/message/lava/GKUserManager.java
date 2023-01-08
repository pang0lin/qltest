package com.js.message.lava;

import java.security.MessageDigest;
import java.util.List;

public class GKUserManager {
  private GKUtilClass gKUtilClass = new GKUtilClass();
  
  public void close() {
    this.gKUtilClass.close();
  }
  
  private static String MD5Encode(String origin) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
    } catch (Exception exception) {}
    return resultString;
  }
  
  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256; 
    int d1 = n / 16;
    int d2 = n % 16;
    return String.valueOf(hexDigits[d1]) + hexDigits[d2];
  }
  
  private static final String[] hexDigits = new String[] { 
      "0", "1", "2", "3", "4", "5", "6", "7", 
      "8", "9", 
      "A", "B", "C", "D", "E", "F" };
  
  private static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++)
      resultSb.append(byteToHexString(b[i])); 
    return resultSb.toString();
  }
  
  public boolean addUser(User user) {
    boolean flag = false;
    String Md5_pwd = user.getMd5_pwd().toLowerCase();
    String str = "<request type='user' subtype='adduser' msid=''><message><user \raccount='" + 

      
      user.getAccount() + "'\r" + 
      "name='" + user.getName() + "'\r" + 
      "display_name='" + user.getDisplayName() + "'\r" + 
      "pwd='" + user.getPwd() + "'\r" + 
      "md5_pwd='" + Md5_pwd + "'\r" + 
      "ug_code ='" + user.getUg_code() + "'\r" + 
      "state='" + user.getState() + "'\r" + 
      "sex='" + user.getSex() + "'\r" + 
      "birthday='" + user.getBirthday() + "'\r" + 
      "email = '" + user.getEmail() + "'\r" + 
      "mobile = '" + user.getMobile() + "'\r" + 
      "office_tel = '" + user.getOfficeTel() + "'\r" + 
      "fax = '" + user.getFax() + "'\r" + 
      "webaddress = '" + user.getWebaddress() + "'\r" + 
      "postcode = '" + user.getPostcode() + "'\r" + 
      "address='" + user.getAddress() + "'\r" + 
      "position='" + user.getPosition() + "'\r" + 
      "remark='" + user.getRemark() + "'/>" + 
      "</message></request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean delUser(String account) {
    boolean flag = false;
    if (account == null || "".equals(account))
      return flag; 
    String str = 
      "<request type='user' subtype='deluser' msid=''><message><user account='" + 
      account + "'/></message></request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean modPwd(String account, String password, String md5Pwd) {
    boolean flag = false;
    String str = 
      "<request type='user' subtype='modpass' msid='' ><message><user account='" + 
      account + "' password='" + password + "' md5_pwd=''/></message></request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean modUser(User user) {
    boolean flag = false;
    String str = "<request type='user' subtype='upduser' msid=''><message><user \raccount='" + 

      
      user.getAccount() + "'\r" + 
      "name='" + user.getName() + "'\r" + 
      "display_name='" + user.getDisplayName() + "'\r" + 
      "pwd='" + user.getPwd() + "'\r" + 
      "md5_pwd='" + user.getMd5_pwd() + "'\r" + 
      "ug_code ='" + user.getUg_code() + "'\r" + 
      "state='" + user.getState() + "'\r" + 
      "sex='" + user.getSex() + "'\r" + 
      "birthday='" + user.getBirthday() + "'\r" + 
      "email = '" + user.getEmail() + "'\r" + 
      "mobile = '" + user.getMobile() + "'\r" + 
      "office_tel = '" + user.getOfficeTel() + "'\r" + 
      "fax = '" + user.getFax() + "'\r" + 
      "webaddress = '" + user.getWebaddress() + "'\r" + 
      "postcode = '" + user.getPostcode() + "'\r" + 
      "address='" + user.getAddress() + "'\r" + 
      "position='" + user.getPosition() + "'\r" + 
      "remark='" + user.getRemark() + "'/>" + 
      "</message></request>";
    flag = this.gKUtilClass.getFlag(str);
    return flag;
  }
  
  public boolean saveUser(User user) {
    boolean flag = false;
    flag = addUser(user);
    return flag;
  }
  
  public boolean userIsExist(String account) {
    boolean rs = false;
    String str = 
      "<request type='user' subtype='getuser' msid=''><message><user account='" + 
      account + "'/></message></request>";
    try {
      if (this.gKUtilClass.getFlag(str))
        rs = true; 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return rs;
  }
  
  public List quaryAllUser() {
    List list = null;
    String str = 
      "<request type='user' subtype='getalluser' msid=''></request>";
    return list;
  }
  
  public boolean quaryUser(String account) {
    boolean ret = false;
    String str = 
      "<request type='user' subtype='getuser' msid=''><message><user account='" + 
      account + "'/></message></request>";
    ret = this.gKUtilClass.getFlag(str);
    return ret;
  }
  
  public String getUserGID(String account) {
    String str = 
      "<request type='user' subtype='getuser' msid=''><message><user account='" + 
      account + "'/></message></request>";
    return this.gKUtilClass.getUserGID(str);
  }
}
