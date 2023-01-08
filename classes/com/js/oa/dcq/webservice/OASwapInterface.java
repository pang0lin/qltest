package com.js.oa.dcq.webservice;

import java.util.List;

public interface OASwapInterface {
  String getUserName();
  
  String sayHello(String paramString);
  
  List<String> getList();
  
  String handleTask(String paramString);
  
  String upload(byte[] paramArrayOfbyte, String paramString);
  
  byte[] download(String paramString);
  
  String getfilelength(String paramString);
}
