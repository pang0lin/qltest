package com.qq.weixin.mp.pojo;

import java.util.ArrayList;
import java.util.List;

public class WeixinServerInfo {
  private static String weixinServer = "qyapi.weixin.qq.com";
  
  private static List<String> ipList = new ArrayList<String>();
  
  public static String getWeixinServer() {
    return weixinServer;
  }
  
  public static void setWeixinServer(String weixinServer) {
    WeixinServerInfo.weixinServer = weixinServer;
  }
  
  public static void addServerIP(String ip) {
    if (!ipList.contains(ip))
      ipList.add(ip); 
  }
  
  public static List<String> getServerIPList() {
    return ipList;
  }
}
