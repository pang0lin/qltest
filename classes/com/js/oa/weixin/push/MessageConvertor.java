package com.js.oa.weixin.push;

import com.js.system.service.messages.disrupter.MessagesBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

public class MessageConvertor {
  private static Logger log = Logger.getLogger(MessageConvertor.class);
  
  public static List<Message> convert(MessagesBean messagesBean) {
    log.debug("--- WeixinMsgConvertor --- convert : MessagesBean --> Message");
    List<Message> msgList = new ArrayList<Message>();
    Message msgTemp = new Message();
    String moduleType = messagesBean.getModuleType();
    log.debug("moduleType = " + moduleType);
    msgTemp.setType(moduleType);
    Date startTime = messagesBean.getStartTime();
    log.debug("startTime = " + startTime);
    msgTemp.setTime(startTime);
    String send_UserName = messagesBean.getSend_UserName();
    log.debug("send_UserName = " + send_UserName);
    msgTemp.setSendUserName(send_UserName);
    String userIds = messagesBean.getUserIds();
    log.debug("userIds = " + userIds);
    String url = messagesBean.getUrl();
    log.debug("url = " + url);
    msgTemp.setUrl(url);
    String title = messagesBean.getTitle();
    log.debug("title = " + title);
    msgTemp.setTitle(title);
    String[] userIdArr = userIds.split(",");
    for (int i = 0; i < userIdArr.length; i++) {
      msgTemp.setToUserId(Integer.valueOf(Integer.parseInt(userIdArr[i])));
      msgList.add(msgTemp);
    } 
    return msgList;
  }
}
