package com.qq.weixin.mp.bean;

import java.util.Map;
import org.apache.log4j.Logger;

public class ChatRobot {
  private static Logger log = Logger.getLogger(ChatRobot.class);
  
  public static String answer(Map<String, String> requestMap) {
    String res = "欢迎使用OA系统，回复openid可以查询您的openid";
    String content = requestMap.get("Content");
    log.debug("content = " + content);
    if (isInclude(content, new String[] { "openid", "openId", "OPENID" })) {
      res = "您的openid是:" + (String)requestMap.get("FromUserName");
    } else if (isInclude(content, new String[] { "您好", "你好", "hello" })) {
      res = "您好， 欢迎使用OA系统";
    } else if (isInclude(content, new String[] { "早上好" })) {
      res = "嗯，早上好";
    } else if (isInclude(content, new String[] { "高兴", "愉快", "轻松", "愉悦", "快乐" })) {
      res = "大家开心我也开心";
    } else if (isInclude(content, new String[] { "伤心", "失落", "心痛", "难过" })) {
      res = "我理解你的感受，这些多会过去的";
    } else if (isInclude(content, new String[] { "我想", "未来", "将来", "愿望" })) {
      res = "祝您心想事成";
    } else if (isInclude(content, new String[] { "你是" })) {
      res = "我是OA系统微信公众号";
    } else if (isInclude(content, new String[] { "怎么用", "用法", "使用", "说明" })) {
      res = "点击自定义菜单的按钮查看您的信息";
    } 
    return res;
  }
  
  private static boolean isInclude(String s, String... a) {
    boolean res = false;
    for (int i = 0; i < a.length; i++) {
      if (s.contains(a[i])) {
        res = true;
        break;
      } 
    } 
    return res;
  }
}
