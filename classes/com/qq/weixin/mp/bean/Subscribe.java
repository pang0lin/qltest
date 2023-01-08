package com.qq.weixin.mp.bean;

import java.util.Map;

public class Subscribe {
  public static String getRespContent(Map<String, String> requestMap) {
    String respContent = "您的openId是：OPEN_ID，请在个人信息个人设置里添加微信openId！";
    String openId = requestMap.get("FromUserName");
    respContent = respContent.replace("OPEN_ID", openId);
    return respContent;
  }
}
