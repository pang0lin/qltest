package com.qq.weixin.mp.test;

import com.qq.weixin.mp.message.resp.TextMessage;
import com.qq.weixin.mp.util.MessageUtil;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class CoreService3 {
  public static String processRequest(HttpServletRequest request) {
    String respMessage = null;
    try {
      String respContent = "请求处理异常，请稍候尝试！";
      Map<String, String> requestMap = MessageUtil.parseXml(request);
      String fromUserName = requestMap.get("FromUserName");
      String toUserName = requestMap.get("ToUserName");
      String msgType = requestMap.get("MsgType");
      TextMessage textMessage = new TextMessage();
      textMessage.setToUserName(fromUserName);
      textMessage.setFromUserName(toUserName);
      textMessage.setCreateTime((new Date()).getTime());
      textMessage.setMsgType("text");
      textMessage.setFuncFlag(0);
      if (msgType.equals("text")) {
        respContent = "您发送的是文本消息！";
      } else if (msgType.equals("image")) {
        respContent = "您发送的是图片消息！";
      } else if (msgType.equals("location")) {
        respContent = "您发送的是地理位置消息！";
      } else if (msgType.equals("link")) {
        respContent = "您发送的是链接消息！";
      } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
        respContent = "您发送的是音频消息！";
      } else if (msgType.equals("event")) {
        String eventType = requestMap.get("Event");
        if (eventType.equals("subscribe")) {
          respContent = "谢谢您的关注！";
        } else if (!eventType.equals("unsubscribe")) {
          if (eventType.equals("CLICK")) {
            String eventKey = requestMap.get("EventKey");
            if (eventKey.equals("11")) {
              respContent = "天气预报菜单项被点击！";
            } else if (eventKey.equals("12")) {
              respContent = "公交查询菜单项被点击！";
            } else if (eventKey.equals("13")) {
              respContent = "周边搜索菜单项被点击！";
            } else if (eventKey.equals("14")) {
              respContent = "历史上的今天菜单项被点击！";
            } else if (eventKey.equals("21")) {
              respContent = "歌曲点播菜单项被点击！";
            } else if (eventKey.equals("22")) {
              respContent = "经典游戏菜单项被点击！";
            } else if (eventKey.equals("23")) {
              respContent = "美女电台菜单项被点击！";
            } else if (eventKey.equals("24")) {
              respContent = "人脸识别菜单项被点击！";
            } else if (eventKey.equals("25")) {
              respContent = "聊天唠嗑菜单项被点击！";
            } else if (eventKey.equals("31")) {
              respContent = "Q友圈菜单项被点击！";
            } else if (eventKey.equals("32")) {
              respContent = "电影排行榜菜单项被点击！";
            } else if (eventKey.equals("33")) {
              respContent = "幽默笑话菜单项被点击！";
            } 
          } 
        } 
      } 
      textMessage.setContent(respContent);
      respMessage = MessageUtil.textMessageToXml(textMessage);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return respMessage;
  }
}
