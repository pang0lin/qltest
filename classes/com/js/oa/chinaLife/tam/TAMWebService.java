package com.js.oa.chinaLife.tam;

import com.ws.client.Exception_Exception;
import com.ws.client.TAMUserCreateServiceService;

public class TAMWebService {
  public boolean addTAMUser(String uid) {
    TAMUserCreateServiceService a = new TAMUserCreateServiceService();
    boolean rValue = a.getTAMUserCreateServicePort().addTAMUserInfo(uid);
    System.out.println("在TAM中添加用户：" + uid + " 结果：" + rValue);
    return rValue;
  }
  
  public boolean modiTAMUser(String uid) {
    TAMUserCreateServiceService a = new TAMUserCreateServiceService();
    boolean rValue = false;
    try {
      rValue = a.getTAMUserCreateServicePort().modifyTAMUserInfo(uid);
    } catch (Exception_Exception e) {
      e.printStackTrace();
    } 
    System.out.println("在TAM中启用用户：" + uid + " 结果：" + rValue);
    return rValue;
  }
  
  public boolean delTAMUser(String uid) {
    TAMUserCreateServiceService a = new TAMUserCreateServiceService();
    boolean rValue = false;
    try {
      rValue = a.getTAMUserCreateServicePort().delTAMUserInfo(uid);
    } catch (Exception_Exception e) {
      e.printStackTrace();
    } 
    System.out.println("在TAM中禁用用户：" + uid + " 结果：" + rValue);
    return rValue;
  }
  
  public static void main(String[] args) {
    TAMWebService tam = new TAMWebService();
    tam.modiTAMUser("test4");
  }
}
