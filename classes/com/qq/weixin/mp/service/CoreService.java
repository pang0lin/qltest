package com.qq.weixin.mp.service;

import com.qq.weixin.mp.bean.ChatRobot;
import com.qq.weixin.mp.bean.MsgXmlPacker;
import com.qq.weixin.mp.bean.Subscribe;
import com.qq.weixin.mp.pojo.EventKey;
import com.qq.weixin.mp.util.MessageUtil;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreService {
  private static Logger log = LoggerFactory.getLogger(CoreService.class);
  
  private static final String RESP_MESSAGE = "请求处理异常，请稍候尝试！";
  
  private static final String MENU_CLICK = "MENU_CLICK::";
  
  private static final String MENU_VIEW = "MENU_VIEW::";
  
  private static final String SUBSCRIBE = "SUBSCRIBE::";
  
  public static String processRequest(HttpServletRequest request) {
    String respMessage = null;
    try {
      String respContent = "请求处理异常，请稍候尝试！";
      Map<String, String> requestMap = MessageUtil.parseXml(request);
      respContent = processMessage(requestMap);
      log.debug("respContent = " + respContent);
      if (respContent.indexOf("MENU_CLICK::") != -1) {
        respContent = respContent.substring("MENU_CLICK::".length());
        log.debug("respContent = " + respContent);
        respMessage = MsgXmlPacker.packLinkNews(requestMap, respContent);
      } else if (respContent.indexOf("MENU_VIEW::") != -1) {
        respContent = respContent.substring("MENU_VIEW::".length());
        log.debug("接收到点击View类型Button事件");
      } else if (respContent.indexOf("SUBSCRIBE::") != -1) {
        respContent = respContent.substring("SUBSCRIBE::".length());
        log.debug("接收到用户的订阅事件");
        requestMap.put("title", "谢谢您的关注！");
        requestMap.put("description", respContent);
        requestMap.put("picUrl", null);
        respMessage = MsgXmlPacker.packNews(requestMap, null);
      } else {
        respMessage = MsgXmlPacker.packText(requestMap, respContent);
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DocumentException e) {
      e.printStackTrace();
    } 
    return respMessage;
  }
  
  private static String processMessage(Map<String, String> requestMap) {
    String respContent = "请求处理异常，请稍候尝试！";
    String msgType = requestMap.get("MsgType");
    if (msgType.equals("text")) {
      respContent = ChatRobot.answer(requestMap);
      log.debug("您发送的是文字消息");
    } else if (msgType.equals("image")) {
      respContent = "您发送的是图片消息！";
    } else if (msgType.equals("location")) {
      respContent = "您发送的是地理位置消息！";
    } else if (msgType.equals("link")) {
      respContent = "您发送的是链接消息！";
    } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
      respContent = "您发送的是音频消息！";
    } else if (msgType.equals("event")) {
      respContent = processEventPush(requestMap);
    } 
    return respContent;
  }
  
  private static String processEventPush(Map<String, String> requestMap) {
    String respContent = "请求处理异常，请稍候尝试！";
    String eventType = requestMap.get("Event");
    if (eventType.equals("subscribe")) {
      respContent = "SUBSCRIBE::" + Subscribe.getRespContent(requestMap);
    } else if (eventType.equals("unsubscribe")) {
      respContent = "接收到了用户取消订阅事件";
    } else if (eventType.equals("CLICK")) {
      String eventKey = requestMap.get("EventKey");
      respContent = processMenuClickEvent(eventKey);
    } else if (eventType.equals("VIEW")) {
      String eventKey = requestMap.get("EventKey");
      respContent = processMenuViewEvent(eventKey);
      log.debug("MessageUtil.EVENT_TYPE_VIEW = VIEW");
    } 
    log.debug("处理微信事件推送消息");
    return respContent;
  }
  
  private static String processMenuClickEvent(String eventKey) {
    String respContent = "请求处理异常，请稍候尝试！";
    if (eventKey.equals(EventKey.DEAL_WITH))
      respContent = "MENU_CLICK::" + EventKey.DEAL_WITH; 
    return respContent;
  }
  
  private static String processMenuViewEvent(String eventKey) {
    String respContent = "请求处理异常，请稍候尝试！";
    log.debug("processMenuViewEvent : respContent" + respContent);
    if (eventKey.equals(EventKey.DEAL_WITH))
      respContent = "MENU_VIEW::" + EventKey.DEAL_WITH; 
    log.debug("respContent = " + respContent);
    return respContent;
  }
}
