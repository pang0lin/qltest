package com.js.oa.weixin.push;

import com.js.system.service.messages.disrupter.MessagesBean;
import com.qq.weixin.mp.service.PushService;
import java.util.List;
import org.apache.log4j.Logger;

public class WeixinPushAction {
  private static Logger log = Logger.getLogger(WeixinPushAction.class);
  
  public static void awaken(MessagesBean messagesBean, String app) {
    log.debug("向微信客户端推送消息的服务被唤醒");
    List<Message> msgList = MessageConvertor.convert(messagesBean);
    PushService.processList(msgList, app);
  }
}
