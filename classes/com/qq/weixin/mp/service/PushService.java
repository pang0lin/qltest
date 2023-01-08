package com.qq.weixin.mp.service;

import com.js.oa.weixin.push.Message;
import com.qq.weixin.mp.pojo.push.NewsPush;
import com.qq.weixin.mp.push.JsonDelivery;
import com.qq.weixin.mp.push.MessageSorter;
import com.qq.weixin.mp.push.NewsConvertor;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class PushService {
  private static Logger log = Logger.getLogger(PushService.class);
  
  public static void processList(List<Message> msgList, String app) {
    log.debug("向微信客户端推送服务， 有消息更新");
    Map<Integer, List<Message>> map = MessageSorter.classify(msgList);
    List<NewsPush> pushList = NewsConvertor.convert(map);
    JsonDelivery.send(pushList, app);
  }
}
