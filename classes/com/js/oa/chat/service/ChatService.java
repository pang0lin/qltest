package com.js.oa.chat.service;

import com.js.oa.chat.bean.ChatBean;
import com.js.oa.chat.po.ChatPO;
import com.js.oa.chat.po.ChatUserPO;
import java.util.List;
import org.apache.log4j.Logger;

public class ChatService {
  private static Logger logger = Logger.getLogger(ChatService.class.getName());
  
  public boolean sendChat(ChatPO chatPO, String[] fileName, String[] saveName, String chatToId) {
    boolean result = (new ChatBean()).sendChat(chatPO, fileName, saveName, chatToId);
    return result;
  }
  
  public String getCountByHQL(String sql) {
    return (new ChatBean()).getCountByHQL(sql);
  }
  
  public ChatPO getSingleChat(String chatId) {
    return (new ChatBean()).getSingleChat(chatId);
  }
  
  public boolean deleteChat(String chatUserIds, String userId, String timeScan) {
    return (new ChatBean()).deleteChat(chatUserIds, userId, timeScan);
  }
  
  public boolean markAsRead(String chatUserIds, String userId, String timeScan) {
    return (new ChatBean()).markAsRead(chatUserIds, userId, timeScan);
  }
  
  public List getChatAttach(String chatId) {
    return (new ChatBean()).getChatAttach(chatId);
  }
  
  public String getName(String sendId) throws Exception {
    return (new ChatBean()).getName(sendId);
  }
  
  public ChatPO getByChatID(String chatId) throws Exception {
    return (new ChatBean()).getByChatID(chatId);
  }
  
  public long saveChat(ChatPO chatPO) throws Exception {
    Long chatid = null;
    chatid = Long.valueOf((new ChatBean()).addChat(chatPO));
    return chatid.longValue();
  }
  
  public long saveChatUser(ChatUserPO chatUserPO) throws Exception {
    Long chatid = null;
    chatid = Long.valueOf((new ChatBean()).addChatUser(chatUserPO));
    return chatid.longValue();
  }
}
