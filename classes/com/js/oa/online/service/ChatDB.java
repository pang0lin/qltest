package com.js.oa.online.service;

import com.js.oa.online.bean.ChatEJBBean;
import com.js.oa.online.po.ChatAccessoryPO;
import com.js.oa.online.po.ChatPO;
import com.js.util.hibernate.HibernateBase;
import java.util.List;

public class ChatDB extends HibernateBase {
  ChatEJBBean chatEJBBean = new ChatEJBBean();
  
  public long saveChat(ChatPO chatPO) throws Exception {
    Long chatid = null;
    chatid = Long.valueOf(this.chatEJBBean.addChat(chatPO));
    return chatid.longValue();
  }
  
  public List getChat(String userID) throws Exception {
    return this.chatEJBBean.get(userID);
  }
  
  public String getName(String sendId) throws Exception {
    return this.chatEJBBean.getName(sendId);
  }
  
  public void addChatAccessory(ChatAccessoryPO chatAccessoryPO) throws Exception {
    this.chatEJBBean.addChatAccessory(chatAccessoryPO);
  }
  
  public List getChatAccessory(String chatID) throws Exception {
    return this.chatEJBBean.getChatAccessory(chatID);
  }
  
  public ChatPO getByChatID(String chatId) throws Exception {
    return this.chatEJBBean.getByChatID(chatId);
  }
  
  public int getCountChat(String hql) {
    return this.chatEJBBean.getCountChat(hql);
  }
}
