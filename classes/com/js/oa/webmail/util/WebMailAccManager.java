package com.js.oa.webmail.util;

import com.js.oa.webmail.po.WebMailAcc;
import com.js.oa.webmail.service.WebMailAccBD;
import com.js.util.util.EncryptSelf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebMailAccManager {
  private static WebMailAccManager webMailAccManager;
  
  private static Map webMailAccMap = new HashMap<Object, Object>();
  
  public static WebMailAccManager getInstance() {
    if (webMailAccManager == null) {
      webMailAccManager = new WebMailAccManager();
      webMailAccManager.init();
    } 
    return webMailAccManager;
  }
  
  public String getDisName(Long mailAccId) {
    String disName = "";
    try {
      disName = ((WebMailAcc)webMailAccMap.get(mailAccId)).getDisName();
    } catch (Exception e) {
      e.printStackTrace();
      return disName = null;
    } 
    return disName;
  }
  
  public Map getDefaultMail(Long userId) {
    Map<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      List<WebMailAcc> list = new ArrayList(webMailAccMap.values());
      if (list != null && list.size() > 0) {
        WebMailAcc temp = null;
        for (int i = 0; i < list.size(); i++) {
          temp = list.get(i);
          if (temp.getUserId().equals(userId) && temp.getDefaultFlag().equals("1")) {
            hashMap.put("MailAccUser", temp.getMailAccUser());
            hashMap.put("MailAccPwd", EncryptSelf.selfDecoder(temp.getMailAccPwd()));
            hashMap.put("SmtpHost", temp.getSmtp());
          } 
        } 
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return hashMap;
  }
  
  public String getDefaultFlag(Long mailAccId) {
    String flag = "";
    try {
      flag = ((WebMailAcc)webMailAccMap.get(mailAccId)).getDefaultFlag();
    } catch (Exception e) {
      e.printStackTrace();
      return flag = null;
    } 
    return flag;
  }
  
  public String getBakFlag(Long mailAccId) {
    String flag = "";
    try {
      flag = ((WebMailAcc)webMailAccMap.get(mailAccId)).getBakFlag();
    } catch (Exception e) {
      e.printStackTrace();
      return flag = null;
    } 
    return flag;
  }
  
  public String getMyChooseAcc(Long mailAccId) {
    String account = "";
    try {
      account = ((WebMailAcc)webMailAccMap.get(mailAccId)).getMailAccUser();
    } catch (Exception e) {
      e.printStackTrace();
      return account = null;
    } 
    return account;
  }
  
  public String getSmtp(Long mailAccId) {
    String smtp = "";
    try {
      smtp = ((WebMailAcc)webMailAccMap.get(mailAccId)).getSmtp();
    } catch (Exception e) {
      e.printStackTrace();
      return smtp = null;
    } 
    return smtp;
  }
  
  public int getSmtpPort(Long mailAccId) {
    int smtpPort = 25;
    try {
      smtpPort = ((WebMailAcc)webMailAccMap.get(mailAccId)).getSmtpPort();
    } catch (Exception e) {
      e.printStackTrace();
      return smtpPort;
    } 
    return smtpPort;
  }
  
  public String getSmtpJMFS(Long mailAccId) {
    String encryptionType = "SSL";
    try {
      encryptionType = ((WebMailAcc)webMailAccMap.get(mailAccId)).getSmtpJMFS();
    } catch (Exception e) {
      e.printStackTrace();
      return encryptionType;
    } 
    return encryptionType;
  }
  
  public int getPOPPort(Long mailAccId) {
    int popPort = 110;
    try {
      popPort = ((WebMailAcc)webMailAccMap.get(mailAccId)).getPopPort();
    } catch (Exception e) {
      e.printStackTrace();
      return popPort;
    } 
    return popPort;
  }
  
  public String getPOP(Long mailAccId) {
    String pop = "";
    try {
      pop = ((WebMailAcc)webMailAccMap.get(mailAccId)).getPop();
    } catch (Exception e) {
      e.printStackTrace();
      return pop = null;
    } 
    return pop;
  }
  
  public String getPopJMFS(Long mailAccId) {
    String encryptionType = "SSL";
    try {
      encryptionType = ((WebMailAcc)webMailAccMap.get(mailAccId)).getPopJMFS();
    } catch (Exception e) {
      e.printStackTrace();
      return encryptionType;
    } 
    return encryptionType;
  }
  
  public String getPWD(Long mailAccId) {
    String pwd = "";
    try {
      pwd = ((WebMailAcc)webMailAccMap.get(mailAccId)).getMailAccPwd();
      pwd = EncryptSelf.selfDecoder(pwd);
    } catch (Exception e) {
      e.printStackTrace();
      return pwd = null;
    } 
    return pwd;
  }
  
  public List getMyAccList(Long userId) {
    List<WebMailAcc> myAccList = null;
    try {
      List<WebMailAcc> list = new ArrayList(webMailAccMap.values());
      if (list != null && list.size() > 0) {
        myAccList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
          WebMailAcc temp = list.get(i);
          if (temp.getUserId().equals(userId))
            myAccList.add(temp); 
        } 
      } else {
        myAccList = new ArrayList<WebMailAcc>();
      } 
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return myAccList;
  }
  
  public void init() {
    try {
      webMailAccMap.clear();
      WebMailAccBD wma = new WebMailAccBD();
      List<WebMailAcc> list = wma.getMailAccList();
      if (list != null && list.size() > 0)
        for (int i = 0; i < list.size(); i++) {
          WebMailAcc temp = list.get(i);
          webMailAccMap.put(temp.getMailAccId(), temp);
        }  
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
